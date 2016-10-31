package com.gener.core.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StringUtil extends AbstractUtil
{
    private static final Log logger = LogFactory.getLog(StringUtil.class);
    
	/**
	 * 编码方式：sha-1
	 */
	public static final String ENCRYPT_TYPE_SHA = "sha-1";

	public static synchronized String encode(String pw)
	{
		return encode(pw, ENCRYPT_TYPE_SHA);
	}

	public static synchronized String encode(String pw, String algorithm)
	{
		String newpw = null;
		if (pw == null)
			return null;
		try
		{
			MessageDigest mg = MessageDigest.getInstance(algorithm);
			mg.update(pw.getBytes("UTF-8"));
			byte[] digesta = mg.digest();
			newpw = byte2hex(digesta);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			return null;
		}
		return newpw;
	}

	public static synchronized String byte2hex(byte[] b) // 二行制转字符串
	{
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++)
		{
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs;
		// return hs.toUpperCase();
	}

	public static String getUUID()
	{
		return UUID.randomUUID().toString().replace("-", "");
	}

	public static String getTrace(Throwable t)
	{
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		t.printStackTrace(writer);
		StringBuffer buffer = stringWriter.getBuffer();
		return buffer.toString();
	}

	public static String getRunTime()
	{
		return null;
	}
	
	
	public static long evaluateTimeDifference(String startTime, String endTime) throws Exception
	{
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm");
		java.util.Date start = sdf.parse(startTime);
		java.util.Date end = sdf.parse(endTime);
		long cha = end.getTime() - start.getTime();
		long result = cha / (1000 * 60);
		return result;
	}

	public static String ransformTime(long minutes)
	{

		long hours = minutes / 60;
		long min = minutes % 60;
		NumberFormat nf = NumberFormat.getInstance();
		nf.setGroupingUsed(false);
		nf.setMaximumIntegerDigits(2);
		nf.setMinimumIntegerDigits(2);
		return nf.format(hours) + ":" + nf.format(min);
	}
	
	
	/**
     * 判断String是否为数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str)
    {

        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches())
        {
            return false;
        }
        return true;
    }

    public static long genEventCode()
    {

        return System.currentTimeMillis();
    }

    /**
     * 把参数word的第一个字母大写
     * @param word
     * @return 
     */
    public static final String upperFirstWord(String word)
    {

        String firstWord = word.substring(0, 1).toUpperCase();
        return firstWord + word.substring(1);
    }

    /**
     * 把参数word的第一个字母小写
     * @param word
     * @return 
     */
    public static final String lowerFirstWord(String word)
    {

        String firstWord = word.substring(0, 1).toLowerCase();
        return firstWord + word.substring(1);
    }

    /**
     * 将字节数组转换为指定编码的字符串
     * 
     * @param strbyte
     * @param destEncoding
     *            目标编码
     * @return
     */
    public static String encodeByte(byte[] strbyte, String destEncoding)
    {

        String ret = null;

        try
        {
            ret = new String(strbyte, destEncoding);
        }
        catch (Exception e)
        {
            ret = "byte to " + destEncoding + " error. str=[" + strbyte + "]";
        }
        return (ret);
    }

    /**
     * 将字节数组转换为指定编码的字符串
     * 
     * @param str
     * @param srcEncoding
     *            原字符串编码
     * @param destEncoding
     *            目标编码
     * @return
     */
    public static String encodeStr(String str, String srcEncoding, String destEncoding)
    {

        String ret = null;

        try
        {
            ret = new String(str.getBytes(srcEncoding), destEncoding);
        }
        catch (Exception e)
        {
            ret = srcEncoding + " to " + destEncoding + " error. str=" + str;
            Debug.exception(logger, e);
        }
        return (ret);
    }

    /**
     * ISO-8859-1转UTF-8
     * 
     * @param str
     * @return
     */
    public static String ISO2UTF8(String str)
    {

        String ret = null;

        try
        {
            ret = new String(str.getBytes("ISO-8859-1"), "UTF-8");
        }
        catch (Exception e)
        {
            ret = "ISO2UTF8 error. str=" + str;
            Debug.exception(logger, e);
        }
        return (ret);
    }

    /**
     * UTF-8转ISO-8859-1
     * 
     * @param str
     * @return
     */
    public static String UTF82ISO(String str)
    {

        String ret = null;

        try
        {
            ret = new String(str.getBytes("UTF-8"), "ISO-8859-1");
        }
        catch (Exception e)
        {
            ret = "UTF82ISO error. str=" + str;
            Debug.exception(logger, e);
        }
        return (ret);
    }

    /**
     * ISO-8859-1转GBK
     * 
     * @param str
     * @return
     */
    public static String ISO2GBK(String str)
    {

        String ret = null;

        try
        {
            ret = new String(str.getBytes("ISO-8859-1"), "GBK");
        }
        catch (Exception e)
        {
            ret = "ISO2GBK error. str=" + str;
            Debug.exception(logger, e);
        }
        return (ret);
    }

    /**
     * GBK转ISO-8859-1
     * 
     * @param str
     * @return
     */
    public static String GBK2ISO(String str)
    {

        String ret = null;

        try
        {
            ret = new String(str.getBytes("GBK"), "ISO-8859-1");
        }
        catch (Exception e)
        {
            ret = "GBK2ISO error. str=" + str;
            Debug.exception(logger, e);
        }
        return (ret);
    }

    /**
     * 把源串sOld中为sPartten的字符串用sReplace替换，
     * 
     * @param sOld
     *            源字符串
     * @param sPartten
     *            要替换的字符串
     * @param sReplace
     *            替换成的字符串
     * @return
     */
    public static String replace(String sOld, String sPartten, String sReplace)
    {

        if (sOld == null)
        {
            return null;
        }
        if (sPartten == null)
        {
            return sOld;
        }
        if (sReplace == null)
        {
            sReplace = "";
        }
        StringBuffer sBuffer = new StringBuffer();
        int isOldLen = sOld.length();
        int isParttenLen = sPartten.length();
        int iIndex = -1;
        int iHead = 0;
        while ((iIndex = sOld.indexOf(sPartten, iHead)) > -1)
        {
            sBuffer.append(sOld.substring(iHead, iIndex)).append(sReplace);
            iHead = iIndex + isParttenLen;
        }
        sBuffer.append(sOld.substring(iHead, isOldLen));
        return sBuffer.toString();
    }

    /**
     * 把输入值格式化保留两位小数，如price=4535.234095 则此函数返回为4535.23
     * 
     * @param price
     * @return
     */
    public static String format2Decimal(double price)
    {

        java.text.NumberFormat nf = java.text.NumberFormat.getInstance(java.util.Locale.CHINESE);
        java.text.DecimalFormat df = (java.text.DecimalFormat) nf;
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);
        String pattern = "#0.00";
        df.applyPattern(pattern);
        df.setDecimalSeparatorAlwaysShown(true);
        return df.format(price);
    }

    /**
     * 把输入值格式化保留两位小数，如price=4535.234095 则此函数返回为4535.23
     * 
     * @param price
     * @return
     */
    public static String format2Decimal(String price)
    {

        double d = 0.0;
        if (ObjectUtil.isEmpty(price))
        {
            d = 0.0;
        }
        else
        {
            try
            {
                d = Double.parseDouble(price);
            }
            catch (java.lang.NumberFormatException ex)
            {
                d = 0.0;
            }
            catch (java.lang.Exception ex)
            {
                d = 0.0;
            }
        }
        return format2Decimal(d);
    }

    /**
     * 将金额转换成千分位格式
     * 
     * @param price
     * @return
     */
    public static String format2Amt(String price)
    {

        double d = 0.0;
        if (ObjectUtil.isEmpty(price))
        {
            d = 0.0;
        }
        else
        {
            try
            {
                d = Double.parseDouble(price);
            }
            catch (java.lang.NumberFormatException ex)
            {
                d = 0.0;
            }
            catch (java.lang.Exception ex)
            {
                d = 0.0;
            }
        }
        return MoneyUtil.toAmtString(format2Decimal(d));
    }

    /**
     * 将金额转换成千分位后添加人民币符号与"元"
     * 
     * @param price
     * @return
     */
    public static String format2AmtWithYuan(String price)
    {

        return "￥" + format2Amt(price) + "元";
    }

    /**
     * 按分隔符号读出字符串的内容
     * 
     * @param strlist
     *            含有分隔符号的字符串
     * @param ken
     *            分隔符号
     * @return 列表
     */
    public static final List parseStringToArrayList(String strlist, String ken)
    {

        StringTokenizer st = new StringTokenizer(strlist, ken);
        if (strlist == null || strlist.equals("") || st.countTokens() <= 0)
        {
            return new ArrayList();
        }
        int size = st.countTokens();
        List strv = new ArrayList();
        for (int i = 0; i < size; i++)
        {
            String nextstr = st.nextToken();
            if (!nextstr.equals(""))
            {
                strv.add(nextstr);
            }
        }
        return strv;
    }

    /**
     * 把源串str以delim为分割符号，分成一个String数组返回，如 str="aaa,bbb,ccc" , delim="," 则此函数返回为 String[0]="aaa" String[1]="bbb"
     * String[2]="ccc"
     * 
     * @param str
     *            待处理字符串
     * @param delim
     *            分隔符
     * @return
     */
    public static String[] split(String str, String delim)
    {

        if (ObjectUtil.isEmpty(delim))
        {
            String[] strReturn = new String[1];
            strReturn[0] = str;
        }
        StringTokenizer st = new StringTokenizer(str, delim);
        int size = st.countTokens();
        if (size < 0)
        {
            return null;
        }
        String[] strReturn = new String[size];
        int i = 0;
        while (st.hasMoreTokens())
        {
            strReturn[i++] = st.nextToken();
        }
        return strReturn;
    }

    /**
     * 把源串str以delim为分割符号，分成一个String数组返回，<br>
     * 如 str="aaa,,bbb,ccc" , delim=","<br>
     * 则此函数返回为 String[0]="aaa"<br>
     * String[1]="" <br>
     * String[2]="bbb"<br>
     * String[3]="ccc"
     * 
     * @param str
     *            待处理字符串
     * @param delim
     *            分割符
     * @return 如果传入的字符为空，返回数组string[0]=""
     */
    public static String[] splitInNull(String str, String delim)
    {

        if (ObjectUtil.isEmpty(delim))
        {
            String[] strReturn = new String[1];
            strReturn[0] = str;
            return strReturn;
        }
        if (ObjectUtil.isEmpty(str))
        {
            String[] strReturn = new String[1];
            strReturn[0] = str;
            return strReturn;
        }

        StringTokenizer st = new StringTokenizer(str, delim, true);
        int i = st.countTokens();

        StringBuffer tempString = new StringBuffer();
        for (int j = 0; j < i; j++)
        {
            tempString.append(" " + st.nextToken() + " ");
        }

        st = new StringTokenizer(tempString.toString(), delim);
        Debug.info(logger, tempString.toString());
        i = st.countTokens();

        String[] tempArray = new String[i];
        for (int j = 0; j < i; j++)
        {
            tempArray[j] = st.nextToken().trim();
        }
        return tempArray;
    }

    /**
     * 将传入的字符串数组转换成字符串，转换之后的格式：'xxx','xxx',...,'xxx'
     * 
     * @param stringArray
     * @return
     */
    public static String Array2String(String stringArray[])
    {

        try
        {
            String StringResult = "";
            if (ObjectUtil.isEmpty(stringArray))
            {
                return StringResult;
            }
            int size = stringArray.length;
            for (int i = 0; i < size; i++)
            {
                if (ObjectUtil.isEmpty((String) stringArray[i]))
                {
                    continue;
                }
                StringResult += "'" + (String) stringArray[i] + "',";
            }
            StringResult = StringResult.substring(0, StringResult.length() - 1);
            return StringResult;
        }
        catch (Exception ex)
        {
            Debug.exception(logger, ex);
            return "";
        }
    }

    /**
     * 将传入的字符串数组转换成指定分隔符的字符串 以delim为分隔，如 Array2String(array[],"-")，则返回串是'-'分隔的
     * 
     * @param stringArray
     * @param delim
     *            分割符
     * @return
     */
    public static String Array2String(String stringArray[], String delim)
    {

        try
        {
            String StringResult = "";
            if (ObjectUtil.isEmpty(stringArray))
            {
                return StringResult;
            }
            int size = stringArray.length;
            if (size == 1)
            {
                return stringArray[0];
            }
            for (int i = 0; i < size - 1; i++)
            {
                if (ObjectUtil.isEmpty((String) stringArray[i]))
                {
                    continue;
                }
                StringResult += (String) stringArray[i] + delim;
            }
            StringResult += (String) stringArray[size - 1];
            return StringResult;
        }
        catch (Exception ex)
        {
            Debug.exception(logger, ex);
            return "";
        }
    }

    /**
     * 将字符串格式化成 HTML 代码输出
     * 
     * @param string
     *            要格式化的字符串
     * @return 格式化后的字符串
     */
    public static String toHtml(String string)
    {
        if (string == null)
        {
            return "";
        }
        string = string.replaceAll("&", "&amp;");
        string = string.replaceAll("\"", "&quot;");
        string = string.replaceAll("<", "&lt;");
        string = string.replaceAll(">", "&gt;");
        // string = string.replaceAll("\r\n", "\n");
        string = string.replaceAll("\n", "<br>\n");
        string = string.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
        string = string.replaceAll(" ", "&nbsp;");
        return string;
    }

    /**
     * 描述：html 标记反转显示
     * @param string   例如： &#123;#enter#  id:&quot;123&quot;,#enter#  name:&quot;张三&quot;#enter#&#125;
     * @return
     */
    public static String toReversalHtml(String string)
    {
        if (string == null)
        {
            return "";
        }
        
        string = string.replaceAll("&amp;", "&");
        string = string.replaceAll("&quot;", "\"");
        string = string.replaceAll("&apos;", "\'");
        string = string.replaceAll("&lt;", "<");
        string = string.replaceAll("&gt;", ">");
        string = string.replaceAll("&#123;", "{");
        string = string.replaceAll("&#125;", "}");
        string = string.replaceAll("&nbsp;", " ");
        string = string.replaceAll("#enter#", "\n");
        return string;
    }

    /**
     * 将sql内的'转换成''
     * 
     * @param sql
     * @return
     */
    public static String toSql(String sql)
    {

        if (sql == null)
        {
            return "";
        }
        sql = sql.replaceAll("'", "''");
        return sql;
    }

    /**
     * 将sql内的''转换成'
     * 
     * @param sql
     * @return
     */
    public static String fromSql(String sql)
    {

        if (sql == null)
        {
            return "";
        }
        sql = sql.replaceAll("''", "'");
        return sql;
    }

    /**
     * 得到一对象toString的值，如果是null则返回""
     * 
     * @param obj
     * @return
     */
    public static String getString(Object obj)
    {

        if (null == obj || ObjectUtil.isEmpty(obj.toString()))
        {
            return "";
        }
        else
        {
            return obj.toString();
        }
    }

    /**
     * 得到一对象toString的值，如果是null则返回默认值，如果默认值为null则返回""
     * 
     * @param obj
     * @param defaultObj
     *            默认值
     * @return
     */
    public static String getString(Object obj, Object defaultObj)
    {

        if (null == obj || ObjectUtil.isEmpty(obj.toString()))
        {
            if (null == defaultObj)
                return "";
            else
                return defaultObj.toString();
        }
        else
        {
            return obj.toString();
        }
    }

    /**
     * 从字符串首位开始获取指定字符串的指定长度的子串，不考虑中文情况造成的字符串长度变化问题
     * 
     * @param sourceString
     *            源字符串
     * @param maxLength
     *            截取最大长度
     * @return 剪切后的结果，如果源字符串为null，返回空串
     */
    public static String subString(String sourceString, int maxLength)
    {

        String innerSourceString = sourceString;
        if (null == sourceString)
        // 如果为null，返回空串
        {
            innerSourceString = "";
        }
        String endString = "";
        int trueLength = innerSourceString.length();
        if (trueLength > maxLength)
        // 实际长度大于需要的长度
        {
            endString = innerSourceString.substring(0, maxLength);
        }
        else
        {
            endString = innerSourceString;
        }
        return endString;
    }

    /**
     * 从字符串首位开始获取指定字符串的指定长度的子串，使用byte形式获取字符串长度避免中文情况造成的字符串长度变化问题
     * 
     * @param sourceString
     * @param maxLength
     *            截取最大长度
     * @return 剪切后的结果，如果源字符串为null，返回空串
     */
    public static String subStringByBytes(String sourceString, int maxLength)
    {

        String innerSourceString = sourceString;
        if (null == sourceString)
        // 如果为null，返回空串
        {
            innerSourceString = "";
        }
        String endString = "";
        byte[] sourceByte = sourceString.getBytes();

        byte[] retByte = new byte[maxLength];
        int trueLength = sourceByte.length;
        if (trueLength > maxLength)
        // 实际长度大于需要的长度
        {
            System.arraycopy(sourceByte, 0, retByte, 0, maxLength);
            endString = new String(retByte);
        }
        else
        {
            endString = innerSourceString;
        }
        return endString;
    }

    /**
     * 从指定偏移位置开始获取指定字符串的指定长度的子串，使用byte形式获取字符串长度避免中文情况造成的字符串长度变化问题
     * 
     * @param sourceString
     * @param offset
     *            偏移位置
     * @param maxLength
     *            截取最大长度
     * @return 剪切后的结果，如果源字符串为null，返回空串
     */
    public static String subStringByBytes(String sourceString, int offset, int maxLength)
    {

        if (maxLength <= 0)
            return "";
        if (offset <= 0)
            return subStringByBytes(sourceString, maxLength);
        String endString = "";
        byte[] sourceByte = sourceString.getBytes();
        byte[] retByte;
        int trueLength = sourceByte.length;
        if (trueLength > (maxLength + offset))
        // 实际长度大于需要的长度
        {
            retByte = new byte[maxLength];
            System.arraycopy(sourceByte, offset, retByte, 0, maxLength);
            endString = new String(retByte);
        }
        else if (trueLength > offset)
        {
            retByte = new byte[trueLength - offset];
            System.arraycopy(sourceByte, offset, retByte, 0, trueLength - offset);
            endString = new String(retByte);
        }
        else
        {
            endString = "";
        }
        return endString;
    }

    /**
     * 按照所设字符集，返回字符串的长度，使用byte形式获取字符串长度避免中文情况造成的字符串长度变化问题
     * 
     * @param str
     *            判断字符串
     * @param encoding
     *            编码设定
     * @return 此编码的长度
     */
    public static int getStrLenByBytes(String str, String encoding)
    {

        int retInt = -1;

        try
        {
            if (ObjectUtil.isEmpty(str))
            {
                retInt = 0;
            }
            else
            {

                byte[] byArr = str.getBytes(encoding);

                retInt = byArr.length;
            }
        }
        catch (UnsupportedEncodingException e)
        {
            Debug.exception(logger, e);
        }
        return retInt;
    }

    /**
     * 按照系统字符集，返回字符串的长度，使用byte形式获取字符串长度避免中文情况造成的字符串长度变化问题
     * 
     * @param str
     *            判断字符串
     * @return 此编码的长度
     */
    public static int getStrLenByBytes(String str)
    {

        return getStrLenByBytes(str, System.getProperty("file.encoding"));
    }

    /**
     * 根据给定的位数，用给定的字符前补足字符串的位数，不考虑中文情况造成的字符串长度变化问题
     * 
     * @param strValue
     *            源字条串
     * @param ch
     *            要补的字符
     * @param iSign
     *            补足后的长度
     * @return
     */
    public static String fillString(String strValue, char ch, int iSign)
    {

        try
        {
            StringBuffer strTemp = new StringBuffer();
            int iDifference = iSign - strValue.length();
            if (iDifference <= 0)
            {
                return strValue;
            }
            for (int i = 0; i < iDifference; i++)
            {
                strTemp.append(ch);
            }
            strTemp.append(strValue);
            return strTemp.toString();
        }
        catch (Exception ex)
        {
            Debug.exception(logger, ex);
            return "";
        }
    }

    /**
     * 根据给定的位数，用给定的字符后补足字符串的位数，不考虑中文情况造成的字符串长度变化问题
     * 
     * @param strValue
     *            源字条串
     * @param ch
     *            要补的字符
     * @param iSign
     *            补足后的长度
     * @return
     */
    public static String fillStringOnTail(String strValue, char ch, int iSign)
    {

        try
        {
            StringBuffer strTemp = new StringBuffer();
            strTemp.append(strValue);
            int iDifference = iSign - strValue.length();

            if (iDifference <= 0)
            {
                return strValue;
            }
            for (int i = 0; i < iDifference; i++)
            {
                strTemp.append(ch);
            }

            return strTemp.toString();
        }
        catch (Exception ex)
        {
            Debug.exception(logger, ex);
            return "";
        }
    }

    /**
     * 根据给定的位数，用给定的字符前补足字符串的位数，使用byte形式获取字符串长度避免中文情况造成的字符串长度变化问题
     * 
     * @param strValue
     *            源字条串
     * @param ch
     *            要补的字符
     * @param iSign
     *            补足后的长度
     * @return
     */
    public static String fillStringByBytes(String strValue, char ch, int iSign)
    {

        try
        {
            StringBuffer strTemp = new StringBuffer();
            int iDifference = iSign - strValue.getBytes().length;
            if (iDifference <= 0)
            {
                return strValue;
            }
            for (int i = 0; i < iDifference; i++)
            {
                strTemp.append(ch);
            }
            strTemp.append(strValue);
            return strTemp.toString();
        }
        catch (Exception ex)
        {
            Debug.exception(logger, ex);
            return "";
        }

    }

    /**
     * 根据给定的位数，用给定的字符后补足字符串的位数，使用byte形式获取字符串长度避免中文情况造成的字符串长度变化问题
     * 
     * @param strValue
     *            源字条串
     * @param ch
     *            要补的字符
     * @param iSign
     *            补足后的长度
     * @return
     */
    public static String fillStringByBytesOnTail(String strValue, char ch, int iSign)
    {

        try
        {
            StringBuffer strTemp = new StringBuffer();
            strTemp.append(strValue);
            int iDifference = iSign - strValue.getBytes().length;

            if (iDifference <= 0)
            {
                return strValue;
            }
            for (int i = 0; i < iDifference; i++)
            {
                strTemp.append(ch);
            }

            return strTemp.toString();
        }
        catch (Exception ex)
        {
            Debug.exception(logger, ex);
            return "";
        }
    }

    /**
     * 比较两个字符串是否相等（任意一个可以为Null）
     * 
     * @param str1
     *            判断对象String1
     * @param str2
     *            判断对象String2
     * @return 相等返回true，反之为false
     */
    public static boolean equals(String str1, String str2)
    {

        return str1 != null ? str1.equals(str2) : str2 == null;
    }

    /**
     * 判断字符串中是否包含所需判断的字符
     * 
     * @param str
     *            基础字符串
     * @param searchChar
     *            判断用字符
     * @return 包含时返回true，反之为false
     */
    public static boolean contains(String str, char searchChar)
    {

        if (ObjectUtil.isEmpty(str))
        {
            return false;
        }
        else
        {
            return str.indexOf(searchChar) >= 0;
        }
    }

    /**
     * 判断字符串中是否包含所需判断的字符串
     * 
     * @param str
     *            基础字符串
     * @param searchChar
     *            判断用字符串
     * @return 包含时返回true，反之为false
     */
    public static boolean contains(String str, String searchStr)
    {

        if (ObjectUtil.isEmpty(str) || ObjectUtil.isEmpty(searchStr))
        {
            return false;
        }
        else
        {
            return str.indexOf(searchStr) >= 0;
        }
    }
    
    /**
     * 获取帕斯卡命名字符串
     * @param longname
     * @param regex
     * @return
     */
    public static String formatPascalName(String longname, String regex){
        String[] names = longname.split(regex);
        for (int i = 0, len = names.length; i < len; i++)
        {
            String s = names[i];
            if(!ObjectUtil.isEmpty(s))
                names[i] = s.replace(s.charAt(0),  Character.toUpperCase(s.charAt(0)));
        }
        
        return StringUtils.join(names);
    }
    
    /**
     * 将List的对象格式化成字符串，用连接符拼接。
     * 例如 List list = [{name:张三},{name:李四},{name:王五}];
     *         String value = formatListToString(list, ",");
     *         value : 张三,李四,王五
     * @param list
     * @param fieldName
     * @param joinWord
     * @return
     */
    public static String formatListToString(List list, String fieldName, String joinWord){
        StringBuffer sb = new StringBuffer();
        for (int i = 0, len = list.size(); i < len; i++)
        {
            Object obj = list.get(i);
            sb.append(ObjectUtil.getFieldValueByName(obj, fieldName)).append(joinWord);
        }
        if(sb.length()>1) return sb.substring(0, sb.length()-1);
        return sb.toString();
    }
    
    public static String formatListToString(List list, String fieldName){
        final String joinWord = ",";
        return formatListToString(list, fieldName, joinWord);
    }
}
