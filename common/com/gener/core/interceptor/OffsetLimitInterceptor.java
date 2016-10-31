package com.gener.core.interceptor;

import ibator.dialect.Dialect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

import com.gener.core.util.PropertiesUtil;
import com.gener.core.util.StringUtil;
import com.gener.core.web.paging.QueryFilter;

@Intercepts(
{ @Signature(type = Executor.class, method = "query", args =
{ MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }) })
public class OffsetLimitInterceptor implements Interceptor
{

    protected transient final Log logger                 = LogFactory.getLog(getClass());

    static int                    MAPPED_STATEMENT_INDEX = 0;

    static int                    PARAMETER_INDEX        = 1;

    static int                    ROWBOUNDS_INDEX        = 2;

    static int                    RESULT_HANDLER_INDEX   = 3;

    Dialect                       dialect;

    @Override
    public Object intercept(Invocation invocation) throws Throwable
    {

        processIntercept(invocation.getArgs());
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target)
    {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties)
    {
        String dialectClass = new PropertiesUtil(properties).getRequiredString("dialectClass");
        try
        {
            dialect = (Dialect) Class.forName(dialectClass).newInstance();
        }
        catch (Exception e)
        {
            throw new RuntimeException("cannot create dialect instance by dialectClass:" + dialectClass, e);
        }
        String dialectInfo = OffsetLimitInterceptor.class.getSimpleName() + ".dialect=" + dialectClass;
        logger.debug(dialectInfo);
    }

    void processIntercept(final Object[] queryArgs) throws Throwable
    {
        // queryArgs = query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler)
        MappedStatement ms = (MappedStatement) queryArgs[MAPPED_STATEMENT_INDEX];
        Object parameterObject = queryArgs[PARAMETER_INDEX];
        final RowBounds rowBounds = (RowBounds) queryArgs[ROWBOUNDS_INDEX];

        BoundSql boundSql = ms.getBoundSql(parameterObject);
        String originalSql = boundSql.getSql().trim();
System.out.println(originalSql);
        int offset = RowBounds.NO_ROW_OFFSET;
        int limit = RowBounds.NO_ROW_LIMIT;
        // offset=rowBounds.getOffset();
        // limit=rowBounds.getLimit();

        if (parameterObject instanceof QueryFilter)
        {
            QueryFilter queryFilter = (QueryFilter) parameterObject;
            offset = queryFilter.getPagingBean().getStart();
            limit = queryFilter.getPagingBean().getPageSize();

            StringBuffer countSql = new StringBuffer(originalSql.length() + 100);
            countSql.append("select count(1) from (").append(originalSql).append(") t");
            Connection connection = null;
            PreparedStatement countStmt = null;
            ResultSet rs = null;

            try
            {
                connection = ms.getConfiguration().getEnvironment().getDataSource().getConnection();
                countStmt = connection.prepareStatement(countSql.toString());
                BoundSql countBS = new BoundSql(ms.getConfiguration(), countSql.toString(),
                        boundSql.getParameterMappings(), parameterObject);
                setParameters(countStmt, ms, countBS, parameterObject);
                rs = countStmt.executeQuery();
                int totalItems = 0;
                if (rs.next())
                {
                    totalItems = rs.getInt(1);
                }
                queryFilter.getPagingBean().setTotalItems(totalItems);
            }
            catch (Exception e)
            {
                logger.error(StringUtil.getTrace(e));
            }
            finally
            {
                try
                {
                    if (rs != null)
                    {
                        rs.close();
                    }
                }
                catch (Exception e1)
                {
                    logger.error(StringUtil.getTrace(e1));
                }

                try
                {
                    if (countStmt != null)
                    {
                        countStmt.close();
                    }
                }
                catch (Exception e1)
                {
                    logger.error(StringUtil.getTrace(e1));
                }

                try
                {
                    if (connection != null)
                    {
                        connection.close();
                    }
                }
                catch (Exception e1)
                {
                    logger.error(StringUtil.getTrace(e1));
                }
            }
            // if(StringUtils.isNotEmpty(queryFilter.getSortName())&&StringUtils.isNotEmpty(queryFilter.getSortOrder()))
            // {
            // originalSql+=" order by "+queryFilter.getSortName().trim()+ " " + queryFilter.getSortOrder().trim()+" ";
            // }
        }

        if (dialect != null
                && dialect.supportsLimit()
                && (offset != RowBounds.NO_ROW_OFFSET || limit != RowBounds.NO_ROW_LIMIT))
        {
            String pageSql;
            if (dialect.supportsLimitOffset())
            {
                pageSql = dialect.getLimitString(originalSql, offset, limit);
                offset = RowBounds.NO_ROW_OFFSET;
            }
            else
            {
                pageSql = dialect.getLimitString(originalSql, 0, limit);
            }
            limit = RowBounds.NO_ROW_LIMIT;
            queryArgs[ROWBOUNDS_INDEX] = new RowBounds(offset, limit);
            BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), pageSql, boundSql.getParameterMappings(),
                    boundSql.getParameterObject());
            MappedStatement newMs = copyFromMappedStatement(ms, new BoundSqlSqlSource(newBoundSql));
            queryArgs[MAPPED_STATEMENT_INDEX] = newMs;
            System.out.println(newBoundSql);
        }
    }

    private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource)
    {
        Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource,
                ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        // builder.keyProperty(ms.getKeyProperty());
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.cache(ms.getCache());
        MappedStatement newMs = builder.build();
        return newMs;
    }

    private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql,
            Object parameterObject) throws SQLException
    {
        ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (parameterMappings != null)
        {
            Configuration configuration = mappedStatement.getConfiguration();
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            MetaObject metaObject = parameterObject == null ? null : configuration.newMetaObject(parameterObject);
            for (int i = 0; i < parameterMappings.size(); i++)
            {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT)
                {
                    Object value;
                    String propertyName = parameterMapping.getProperty();
                    PropertyTokenizer prop = new PropertyTokenizer(propertyName);
                    if (parameterObject == null)
                    {
                        value = null;
                    }
                    else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass()))
                    {
                        value = parameterObject;
                    }
                    else if (boundSql.hasAdditionalParameter(propertyName))
                    {
                        value = boundSql.getAdditionalParameter(propertyName);
                    }
                    else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX)
                            && boundSql.hasAdditionalParameter(prop.getName()))
                    {
                        value = boundSql.getAdditionalParameter(prop.getName());
                        if (value != null)
                        {
                            value = configuration.newMetaObject(value).getValue(
                                    propertyName.substring(prop.getName().length()));
                        }
                    }
                    else
                    {
                        value = metaObject == null ? null : metaObject.getValue(propertyName);
                    }
                    TypeHandler typeHandler = parameterMapping.getTypeHandler();
                    if (typeHandler == null)
                    {
                        throw new ExecutorException("There was no TypeHandler found for parameter "
                                + propertyName
                                + " of statement "
                                + mappedStatement.getId());
                    }
                    typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());
                }
            }
        }
    }

    public static class BoundSqlSqlSource implements SqlSource
    {
        BoundSql boundSql;

        public BoundSqlSqlSource(BoundSql boundSql)
        {
            this.boundSql = boundSql;
        }

        public BoundSql getBoundSql(Object parameterObject)
        {
            return boundSql;
        }
    }

}
