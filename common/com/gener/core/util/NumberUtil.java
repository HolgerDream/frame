/**
 * Copyright(C) 2012-2014 Shang hai Gener-tech Co., Ltd. All Rights Reserved.<br/>
 * 版权所有(C)2012-2014 <br/>
 * 公司名称：上海杰之能信息科技有限公司<br/>
 * 公司地址：中国上海市闸北区广中西路777弄55号启迪大厦11楼<br/>
 * 网址：http://www.gener-tech.com<br/>
 * <p>标        题：</p>
 * <p>文  件  名：com.gener.core.util.NumberUtil.java</p>
 * <p>部        门：研发一部
 * <p>版        本： 1.0</p>
 * <p>Compiler: JDK1.6.0_21</p>
 * <p>创  建  者：YIXW</p>
 * <p>创建时间：2013-12-13上午11:39:19</p>
 * <p>修  改  者：</p>
 * <p>修改时间：</p>
 */
package com.gener.core.util;

import java.math.BigDecimal;
import java.util.regex.Pattern;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>描        述：数值工具类</p>
 * <p>创建时间：2013-12-13下午12:22:22</p>
 */
public class NumberUtil extends AbstractUtil
{
    private static final Log logger = LogFactory.getLog(NumberUtil.class);
    
    /**
     * 判断字符串是否为数字
     * 
     * @param num
     * @return
     */
    public static boolean isNumberic(String num)
    {
        return (null == num || num.length() <= 0 || num.matches("\\d{1,}")) ? true : false;
    }

    /**
     * @return 返回12位随机数
     */
    public static String randomNumber()
    {
        return RandomStringUtils.randomNumeric(12);
    }

    /**
     * @param parm
     * @return 返回指定位数随机数
     */
    public static String randomNumber(int parm)
    {
        return RandomStringUtils.randomNumeric(parm);
    }
    
    /** 
     * 判断当前值是否为整数 
     *  
     * @param value 
     * @return 
     */  
    public static boolean isInteger(Object value) {  
        if (ObjectUtil.isEmpty(value)) {  
            return false;  
        }  
        String mstr = value.toString();  
        Pattern pattern = Pattern.compile("^-?\\d+{1}");  
        return pattern.matcher(mstr).matches();  
    }  
  
    /** 
     * 判断当前值是否为数字(包括小数) 
     *  
     * @param value 
     * @return 
     */  
    public static boolean isDigit(Object value) {  
        if (ObjectUtil.isEmpty(value)) {  
            return false;  
        }  
        String mstr = value.toString();  
        Pattern pattern = Pattern.compile("^-?[0-9]*.?[0-9]*{1}");  
        return pattern.matcher(mstr).matches();  
    }  
  
    /** 
     * 将数字格式化输出 
     *  
     * @param value 
     *            需要格式化的值 
     * @param precision 
     *            精度(小数点后的位数) 
     * @return 
     */  
    public static String format(Object value, Integer precision) {  
        Double number = 0.0;  
        if (isDigit(value)) {  
            number = new Double(value.toString());  
        }  
        precision = (precision == null || precision < 0) ? 2 : precision;  
        BigDecimal bigDecimal = new BigDecimal(number);  
        return bigDecimal.setScale(precision, BigDecimal.ROUND_HALF_UP)  
                .toString();  
    }  
    
    /**
     * 描述：判断字符串中是否全为数字
     * @param str
     * @return
     */
    public static boolean isDigits(String str)
    {
        if (str == null || str.length() == 0)
            return false;
        for (int i = 0; i < str.length(); i++)
            if (!Character.isDigit(str.charAt(i)))
                return false;

        return true;
    }
  
    public static int stringToInt(String str)
    {
        return stringToInt(str, 0);
    }

    public static int stringToInt(String str, int defaultValue)
    {
        return Integer.parseInt(str);
    }
}
