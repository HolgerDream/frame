/**
 * Copyright(C) 2012-2014 Shang hai Gener-tech Co., Ltd. All Rights Reserved.<br/>
 * 版权所有(C)2012-2014 <br/>
 * 公司名称：上海杰之能信息科技有限公司<br/>
 * 公司地址：中国上海市闸北区广中西路777弄55号启迪大厦11楼<br/>
 * 网址：http://www.gener-tech.com<br/>
 * <p>标        题：</p>
 * <p>文  件  名：com.gener.core.util.MoneyUtil.java</p>
 * <p>部        门：研发一部
 * <p>版        本： 1.0</p>
 * <p>Compiler: JDK1.6.0_21</p>
 * <p>创  建  者：YIXW</p>
 * <p>创建时间：2013-12-13下午1:09:58</p>
 * <p>修  改  者：</p>
 * <p>修改时间：</p>
 */
package com.gener.core.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>描        述：金额处理工具类</p>
 * <p>创建时间：2013-12-13下午1:12:19</p>
 */
public class MoneyUtil extends AbstractUtil
{
    private static final Log logger      = LogFactory.getLog(MoneyUtil.class);
    static int               indexOfPoint;
    static int               lengthOfInt;
    static int               numOfCommer;
    static int               indexOfFirstCommer;
    static String            intPart     = "";
    static String            decimalPart = "";

    private static String toChinese(String sStr, String sFour, int i, boolean bPre)
    {
        String sStruct = "";
        for (int j = 0; j < 4; j++)
        {
            if (sFour.charAt(j) != '0')
            {
                if (j == 0)
                {
                    if (!bPre)
                    {
                        sStruct = sStruct + '0';
                    }
                    sStruct = sStruct + sFour.charAt(j);
                }
                else
                {
                    if (sFour.charAt(j - 1) == '0')
                    {
                        sStruct = sStruct + '0';
                    }
                    sStruct = sStruct + sFour.charAt(j);
                }

                switch (j)
                {
                case 0:
                    if (i == 3)
                    {
                        sStruct = sStruct + 35282;
                        continue;
                    }

                    sStruct = sStruct + '\u4EDF';

                    break;
                case 1:
                    if (i == 3)
                    {
                        sStruct = sStruct + '\u5206';
                        continue;
                    }

                    sStruct = sStruct + '\u4F70';

                    break;
                case 2:
                    sStruct = sStruct + '\u62FE';
                    break;
                case 3:
                    if (sStruct.equals(""))
                        continue;
                    switch (i)
                    {
                    case 0:
                        sStruct = sStruct + "\u4EBF";
                        break;
                    case 1:
                        sStruct = sStruct + "\u4E07";
                        break;
                    case 2:
                        sStruct = sStruct + "\u5143";
                    }
                default:
                    break;
                }

            }
            else
            {
                if ((!sStruct.equals("")) && (j == 3))
                {
                    switch (i)
                    {
                    case 0:
                        sStruct = sStruct + "\u4EBF";
                        break;
                    case 1:
                        sStruct = sStruct + "\u4E07";
                    }

                }

                if ((i != 2) || (j != 3) || ((sStr.equals("")) && (sStruct.equals(""))))
                    continue;
                sStruct = sStruct + "\u5143";
            }
        }

        return sStruct;
    }

    public static String toRMB(String digit)
    {
        return toRMB(Double.parseDouble(digit));
    }

    public static String toRMB(double digit)
    {
        DecimalFormat df = new DecimalFormat("#.0000");
        StringBuffer sbDigit = new StringBuffer(df.format(digit));
        sbDigit.replace(sbDigit.length() - 2, sbDigit.length(), "00");
        String sDigit = "";
        sDigit = sbDigit.toString();
        sDigit = sDigit.substring(0, sDigit.length() - 5) + sDigit.substring(sDigit.length() - 4);

        if (sDigit.length() > 16)
        {
            return "\u6B3E\u9879\u8FC7\u5927\uFF01";
        }

        if (sDigit.length() < 16)
        {
            int iLength = 16 - sDigit.length();
            for (int i = 0; i < iLength; i++)
            {
                sDigit = "0" + sDigit;
            }
        }
        if (sDigit.equals("0000000000000000"))
        {
            return "\u96F6\u5143\u6574";
        }
        String sChinese = sDigit;
        String sFour = "";
        boolean bPreStr = true;
        sDigit = "";

        for (int i = 0; i < 4; i++)
        {
            sFour = toChinese(sDigit, sChinese.substring(i * 4, i * 4 + 4), i, bPreStr);

            if ((sFour.length() == 0) || (sFour.length() == 1))
            {
                bPreStr = false;
            }
            else if ((sFour.charAt(sFour.length() - 2) < '0') || (sFour.charAt(sFour.length() - 2) > '9'))
            {
                bPreStr = false;
            }
            else
            {
                bPreStr = true;
            }
            sDigit = sDigit + sFour;
        }

        while (sDigit.charAt(0) == '0')
        {
            sDigit = sDigit.substring(1);
            break;
        }

        sChinese = "";
        for (int i = 0; i < sDigit.length(); i++)
        {
            if ((sDigit.charAt(i) >= '0') && (sDigit.charAt(i) <= '9'))
                ;
            switch (sDigit.charAt(i))
            {
            case '1':
                sChinese = sChinese + "\u58F9";
                break;
            case '2':
                sChinese = sChinese + "\u8D30";
                break;
            case '3':
                sChinese = sChinese + "\u53C1";
                break;
            case '4':
                sChinese = sChinese + "\u8086";
                break;
            case '5':
                sChinese = sChinese + "\u4F0D";
                break;
            case '6':
                sChinese = sChinese + "\u9646";
                break;
            case '7':
                sChinese = sChinese + "\u67D2";
                break;
            case '8':
                sChinese = sChinese + "\u634C";
                break;
            case '9':
                sChinese = sChinese + "\u7396";
                break;
            case '0':
                sChinese = sChinese + "\u96F6";
            default:
                continue;
            }
            sChinese = sChinese + sDigit.charAt(i);
        }

        if (!sDigit.endsWith("\u5206"))
        {
            sChinese = sChinese + "\u6574";
        }

        return sChinese;
    }

    public static String toAmtString(String amt)
    {
        String ret = "";
        BigDecimal d = ObjectUtil.getZeroBigDecimal();
        boolean flag = false;
        try
        {
            d = new BigDecimal(amt);
            if (d.compareTo(ObjectUtil.getZeroBigDecimal()) < 0)
            {
                d = d.abs();
                flag = true;
            }
        }
        catch (NumberFormatException localNumberFormatException)
        {
        }
        dividedString(d.toString());
        ret = convertIntPart(intPart) + convertDecimalPart(decimalPart, 2);
        if (flag)
            ret = "-" + ret;
        return ret;
    }

    public static String save2DecimalPart(String amt)
    {
        dividedString(amt);
        return intPart + convertDecimalPart(decimalPart, 2);
    }

    public static String saveNDecimalPart(String amt, int scale)
    {
        dividedString(amt);
        return intPart + convertDecimalPart(decimalPart, scale);
    }

    private static void dividedString(String amt)
    {
        try
        {
            Double.parseDouble(amt);
        }
        catch (Exception e)
        {
            Debug.info(logger, "\u4E0D\u80FD\u8F6C\u6362\u975E\u6570\u503C\u578B\u7684\u5B57\u7B26\u4E32");
            return;
        }

        if (amt.indexOf(".") == -1)
        {
            indexOfPoint = amt.length();
            lengthOfInt = amt.length();
            intPart = amt;
            decimalPart = "";
        }
        else
        {
            indexOfPoint = amt.indexOf(".");
            lengthOfInt = amt.substring(0, indexOfPoint).length();
            intPart = amt.substring(0, indexOfPoint);
            decimalPart = amt.substring(indexOfPoint + 1, amt.length());
        }
    }

    private static String convertIntPart(String intPart)
    {
        int pointer = 0;
        String result = "";
        if (lengthOfInt % 3 == 0)
        {
            numOfCommer = lengthOfInt / 3 - 1;
            indexOfFirstCommer = 3;
        }
        else
        {
            numOfCommer = lengthOfInt / 3;
            indexOfFirstCommer = lengthOfInt % 3;
        }

        for (int i = 0; i <= numOfCommer;)
        {
            if (i == 0)
            {
                result = intPart.substring(pointer, indexOfFirstCommer);
                pointer += indexOfFirstCommer;
            }
            else if (i != numOfCommer)
            {
                result = result + "," + intPart.substring(pointer, pointer + 3);
                pointer += 3;
            }
            else
            {
                result = result + "," + intPart.substring(pointer, pointer + 3);
            }
            i++;
        }

        return result;
    }

    private static String convertDecimalPart(String decimalPart, int decimalNum)
    {
        if (decimalPart.length() > decimalNum)
        {
            decimalPart = decimalPart.substring(0, decimalNum);
            return "." + decimalPart;
        }

        if ((decimalPart.equals("")) || (decimalPart == null))
        {
            for (int i = 0; i < decimalNum; i++)
                decimalPart = decimalPart + "0";
            return "." + decimalPart;
        }

        if (decimalPart.length() < decimalNum)
        {
            for (int i = 0; i < decimalNum - decimalPart.length(); i++)
                decimalPart = decimalPart + "0";
            return "." + decimalPart;
        }

        return "." + decimalPart;
    }

    public static void main(String[] args)
    {
        String a = toAmtString("12");
        Debug.info(logger, "result=" + a);
        a = toAmtString("12.");
        Debug.info(logger, "result=" + a);
        a = toAmtString("12.0");
        Debug.info(logger, "result=" + a);
        a = toAmtString("12.1");
        Debug.info(logger, "result=" + a);
        a = toAmtString("12.12");
        Debug.info(logger, "result=" + a);
        a = toAmtString("12.123");
        Debug.info(logger, "result=" + a);

        Debug.info(logger, "");
        Debug.info(logger, save2DecimalPart("1"));
        Debug.info(logger, save2DecimalPart("1."));
        Debug.info(logger, save2DecimalPart("1.0"));
        Debug.info(logger, save2DecimalPart("1.1"));
        Debug.info(logger, save2DecimalPart("1.12"));
        Debug.info(logger, save2DecimalPart("1.123"));
    }
}
