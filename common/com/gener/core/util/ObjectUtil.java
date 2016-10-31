/**
 * Copyright(C) 2012-2014 Shang hai Gener-tech Co., Ltd. All Rights Reserved.<br/>
 * 版权所有(C)2012-2014 <br/>
 * 公司名称：上海杰之能信息科技有限公司<br/>
 * 公司地址：中国上海市闸北区广中西路777弄55号启迪大厦11楼<br/>
 * 网址：http://www.gener-tech.com<br/>
 * <p>标        题：</p>
 * <p>文  件  名：com.gener.core.util.ObjectUtil.java</p>
 * <p>部        门：研发一部
 * <p>版        本： 1.0</p>
 * <p>Compiler: JDK1.6.0_21</p>
 * <p>创  建  者：YIXW</p>
 * <p>创建时间：2013-12-13上午11:42:18</p>
 * <p>修  改  者：</p>
 * <p>修改时间：</p>
 */
package com.gener.core.util;

import java.lang.reflect.Method;
import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>描        述：对象处理类</p>
 * <p>创建时间：2013-12-13下午12:23:50</p>
 */
public class ObjectUtil extends AbstractUtil
{
    private static final Log logger = LogFactory.getLog(ObjectUtil.class);
    
    /**
     * 判断String类型对象是否为空(空串或null)
     * @param str
     * @return
     */
    public static boolean isEmpty(String str)
    {
        if (str == null || "".equals(str.trim()))
        {
            return true;
        }
        return false;
    }

    /**
     * 判断一个对象数组是否为空(没有成员)
     * @param array
     * @return
     */
    public static boolean isEmpty(Object[] array)
    {
        if (array == null || array.length == 0)
        {
            return true;
        }
        return false;
    }
    
    /**
     * 描述：判断String类型对象是否为空(空串或null)
     * @param Object
     * @return
     */
    public static boolean isEmpty(Object obj)
    {
        if (obj != null &&  !"".equals(obj.toString()))
        {
            return false;
        }
       return true;
    }

    /**
     * 获取值为0.00的BigDecimal对象
     * @return
     */
    public static BigDecimal getZeroBigDecimal()
    {
        return BigDecimal.ZERO;
    }
    
    /**
     * 根据属性名获取属性值
     * 
     * @param o
     *            目标对象
     * @param fieldName
     *            字段名
     * @return
     */
    public static Object getFieldValueByName(Object o, String fieldName)
    {
        try
        {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(o, new Object[] {});
            return value;
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}
