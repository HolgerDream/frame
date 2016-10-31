/**
 * Copyright(C) 2012-2014 Shang hai Gener-tech Co., Ltd. All Rights Reserved.<br/>
 * 版权所有(C)2012-2014 <br/>
 * 公司名称：上海杰之能信息科技有限公司<br/>
 * 公司地址：中国上海市闸北区广中西路777弄55号启迪大厦11楼<br/>
 * 网址：http://www.gener-tech.com<br/>
 * <p>标        题：</p>
 * <p>文  件  名：com.gener.core.struts.DateConverter.java</p>
 * <p>部        门：研发一部
 * <p>版        本： 1.0</p>
 * <p>Compiler: JDK1.6.0_21</p>
 * <p>创  建  者：Jimmy</p>
 * <p>创建时间：2014-1-8下午7:10:21</p>
 * <p>修  改  者：</p>
 * <p>修改时间：</p>
 */
/**
 * 
 */
package com.gener.core.struts;

import java.text.SimpleDateFormat;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.conversion.impl.DefaultTypeConverter;

import java.util.Date;
import java.util.Map;
import java.text.ParseException;

/**
 * <p>描        述：TODO</p>
 * <p>创  建  人：Jimmy</p>
 * <p>创建时间：2014-1-8下午7:10:21</p>
 */

public class DateConverter extends DefaultTypeConverter {

    private static Log log = LogFactory.getLog(DateConverter.class);

    private static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static final String[] DATE_PATTERNS = new String[] {
            "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyyMMddHHmmss",
            "yyyy-MM-dd", "yyyy-MM" };

    /**
     * Convert value between types
     */
    @SuppressWarnings("rawtypes")
    public Object convertValue(Map ognlContext, Object value, Class toType) {
        Object result = null;
        if (toType == Date.class) {
            result = doConvertToDate(value);
        } else if (toType == String.class) {
            result = doConvertToString(value);
        }
        return result;
    }
    
    /**
     * Convert String to Date
     * 
     * @param value
     * @return
     */
    private Date doConvertToDate(Object value) {
        Date result = null;
        if (value instanceof String) {
            try {
                result = DateUtils.parseDate((String) value, DATE_PATTERNS);
            } catch (ParseException e1) {
                log.error("Converting from dateFormat to Date fails!" + ", value : " + value);
            }
            if (result == null && StringUtils.isNotEmpty((String) value)) {
                try {
                    result = new Date(new Long((String) value).longValue());
                } catch (Exception e) {
                    log.error("Converting from milliseconds to Date fails!");
                }
            }
        } else if (value instanceof Object[]) {
            Object[] array = (Object[]) value;
            if ((array != null) && (array.length >= 1)) {
                value = array[0];
                result = doConvertToDate(value);
            }
        } else if (Date.class.isAssignableFrom(value.getClass())) {
            result = (Date) value;
        }
        return result;
    }

    /**
     * Convert Date to String
     * 
     * @param value
     * @return
     */
    private String doConvertToString(Object value) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                DATETIME_PATTERN);
        String result = null;
        if (value instanceof Date) {
            result = simpleDateFormat.format(value);
        }
        return result;
    }

}