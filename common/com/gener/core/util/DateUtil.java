/**
 * Copyright(C) 2012-2014 Shang hai Gener-tech Co., Ltd. All Rights Reserved.<br/>
 * 版权所有(C)2012-2014 <br/>
 * 公司名称：上海杰之能信息科技有限公司<br/>
 * 公司地址：中国上海市闸北区广中西路777弄55号启迪大厦11楼<br/>
 * 网址：http://www.gener-tech.com<br/>
 * <p>标        题：</p>
 * <p>文  件  名：com.gener.core.util.DateUtil.java</p>
 * <p>部        门：研发一部
 * <p>版        本： 1.0</p>
 * <p>Compiler: JDK1.6.0_21</p>
 * <p>创  建  者：YIXW</p>
 * <p>创建时间：2013-12-13上午11:39:08</p>
 * <p>修  改  者：</p>
 * <p>修改时间：</p>
 */
package com.gener.core.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;

/**
 * <p>
 * 描 述：日期处理工具类
 * </p>
 * <p>
 * 创建时间：2013-12-13下午12:22:22
 * </p>
 */
public class DateUtil extends AbstractUtil {
	private static final Log logger = LogFactory.getLog(DateUtil.class);

	/**
	 * 获取yyyyMMdd格式的系统日期，默认取服务器当前日期
	 * 
	 * @return
	 */
	public static String getSysDate() {
		/* 默认取服务器日期 */
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd");
		String s = simpledateformat.format(Calendar.getInstance().getTime());
		return s;
	}
	public static Date getNowDate() {
		/* 默认取服务器日期 */
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}

	/**
	 * 获取yyyy格式的系统日期，默认取服务器当前日期
	 * 
	 * @return
	 */
	public static int getSysYear() {
		/* 默认取服务器日期 */
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy");
		int s = Integer.parseInt(simpledateformat.format(Calendar.getInstance()
				.getTime()));
		return s;
	}

	/**
	 * 获取kkmmss格式的系统时间
	 * 
	 * @return
	 */
	public static String getSysTime() {
		/* 默认取服务器日期 */
		SimpleDateFormat simpledateformat = new SimpleDateFormat("kkmmss");
		String s = simpledateformat.format(Calendar.getInstance().getTime());
		return s;
	}

	/**
	 * 判断参数字符串是否为yyyyMMdd格式的日期
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isDate(String date) {

		if (date.length() != 8)
			return false;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		try {
			dateFormat.parse(date);
		} catch (Exception e) {
			return false;
		}

		String year = date.substring(0, 4);
		int y = Integer.parseInt(year);
		if (y < 1975 || y > 2100)
			return false;

		String month = date.substring(4, 6);
		int m = Integer.parseInt(month);
		if (m < 1 || m > 12)
			return false;

		String day = date.substring(6);
		int d = Integer.parseInt(day);
		if (d < 1)
			return false;

		String lastday = getMonthLastDate(date);
		int ld = Integer.parseInt(lastday.substring(6));
		if (d > ld)
			return false;// 大于本月最后一天，返回假

		return true;
	}

	/**
	 * 判断参数字符串是否为kkmmss格式的时间
	 * 
	 * @param time
	 * @return
	 */
	public static boolean isTime(String time) {

		if (time.length() != 6)
			return false;

		String hour = time.substring(0, 2);
		int h = Integer.parseInt(hour);
		if (h < 0 || h > 24)
			return false;

		String miet = time.substring(2, 4);
		int m = Integer.parseInt(miet);
		if (m < 0 || m > 59)
			return false;
		if (h == 24 && m != 0)
			return false;

		String sd = time.substring(4);
		int s = Integer.parseInt(sd);
		if (s < 0 || s > 59)
			return false;
		if (h == 24 && s != 0)
			return false;

		return true;
	}

	/**
	 * 把YYYY-MM-DD格式日期字符串格式化成yyyyMMdd格式
	 * 
	 * @param date
	 *            YYYY-MM-DD格式日期字符串
	 * @return
	 */
	public static String format2DB(String date) {

		if (ObjectUtil.isEmpty(date))
			return "";
		return date.replaceAll("-", "");
	}

	/**
	 * 把kk:mm:ss格式时间字符串格式化成kkmmss格式
	 * 
	 * @param time
	 *            kk:mm:ss格式时间字符串
	 * @return
	 */
	public static String formatTime2DB(String time) {

		if (ObjectUtil.isEmpty(time))
			return "";
		return time.replaceAll(":", "");
	}

	/**
	 * 把yyyyMMdd格式日期字符串格式化成YYYY-MM-DD格式
	 * 
	 * @param date
	 *            yyyyMMdd格式日期字符串
	 * @return
	 */
	public static String formatFromDB(String date) {

		if (ObjectUtil.isEmpty(date))
			return "";
		StringBuffer buf = new StringBuffer(date);
		return buf.insert(6, '-').insert(4, '-').toString();
	}

	/**
	 * 把kkmmss格式时间字符串格式化成kk:mm:ss格式
	 * 
	 * @param time
	 *            kkmmss格式时间字符串
	 * @return
	 */
	public static String formatTimeFromDB(String time) {

		if (ObjectUtil.isEmpty(time))
			return "";
		StringBuffer buf = new StringBuffer(time);
		return buf.insert(2, ':').insert(5, ':').toString();
	}

	/**
	 * 把yyyyMMdd格式日期字符串格式化成YYYY-MM-DD格式,把kkmmss格式时间字符串格式化成kk:mm:ss格式,中间加空格后拼接
	 * 
	 * @param date
	 *            yyyyMMdd格式日期字符串
	 * @param time
	 *            kkmmss格式时间字符串
	 * @return
	 */
	public static String formatDateTimeFromDB(String date, String time) {

		if (ObjectUtil.isEmpty(date) || date.length() < 8)
			date = "        ";
		if (ObjectUtil.isEmpty(time) || date.length() < 6)
			time = "      ";
		StringBuffer buf = new StringBuffer(date);
		buf.insert(6, '-').insert(4, '-');
		StringBuffer buf1 = new StringBuffer(time);
		buf1.insert(2, ':').insert(5, ':');
		return buf.toString() + " " + buf1.toString();
	}

	/**
	 * 根据当前年份获取取下拉列表年份集合，从2005年开始至当前年份后3年
	 * 
	 * @return
	 */
	public static List getYear() {

		List vRet = new ArrayList();
		int iCurrYear = Integer.parseInt((getSysDate()).substring(0, 4));
		for (int i = 0; i <= iCurrYear - 2005 + 3; i++) {
			vRet.add(Integer.toString(2005 + i));
		}
		return vRet;
	}

	/**
	 * 根据给定的年份和季度提取对应的季末日期
	 * 
	 * @param ogYear
	 *            年份
	 * @param iValue
	 *            季度
	 * @return
	 */
	public static String getQuarterOfLastDate(String ogYear, int iValue) {

		String strRetDate = "";
		switch (iValue) {
		case 1:
			strRetDate = ogYear + "0331";
			break;
		case 2:
			strRetDate = ogYear + "0630";
			break;
		case 3:
			strRetDate = ogYear + "0930";
			break;
		case 4:
			strRetDate = ogYear + "1231";
			break;
		}

		return strRetDate;
	}

	/**
	 * 根据参数ogdate，得到ogdate这个月的最后一天的日期，例如： getLastDate("200308")=20030831
	 * 参数ogdate必须是6位（yyyyMM）或8位（yyyyMMdd）
	 * 
	 * @param ogdate
	 * @return
	 */
	public static String getMonthLastDate(String ogdate) {

		if (ogdate.length() == 6)
			ogdate = ogdate + "01";
		else {// 把ogdate变成前6位加01的串，如20030805-->20030801
			ogdate = ogdate.substring(0, 6) + "01";
		}
		ogdate = getNextDateByMonth(ogdate, 1);
		ogdate = getNextDateByNum(ogdate, -1);
		return ogdate;
	}

	/**
	 * 根据参数ogdate，得到ogdate这个月的最后一个工作日，例如：
	 * getMonthLastDateNoWeekend("200308")=20030829
	 * 参数ogdate必须是6位（yyyyMM）或8位（yyyyMMdd）
	 * 
	 * @param ogdate
	 * @return
	 */
	public static String getMonthLastWorkDate(String ogdate) {

		String sDate = getMonthLastDate(ogdate);
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd");
		Date date = simpledateformat.parse(sDate, new ParsePosition(0));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int iWeek = calendar.get(Calendar.DAY_OF_WEEK);
		if (iWeek == 7)
			sDate = getNextDateByNum(sDate, -1);
		if (iWeek == 1)
			sDate = getNextDateByNum(sDate, -2);
		// calendar.add(2, i);
		date = calendar.getTime();
		return sDate;
	}

	/**
	 * 得到输入日期+i天以后的日期
	 * 
	 * @param s
	 *            yyyyMMdd格式日期
	 * @param i
	 *            可以是负数
	 * @return
	 */
	public static String getNextDateByNum(String s, int i) {

		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd");
		Date date = simpledateformat.parse(s, new ParsePosition(0));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(5, i);
		date = calendar.getTime();
		s = simpledateformat.format(date);
		return s;
	}

	/**
	 * 得到输入日期+i毫秒以后的日期
	 * 
	 * @param s
	 * 
	 * @param i
	 *            可以是负数
	 * @return
	 */
	public static Date getNextDateByNum(Date date, int i) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MILLISECOND, i);
		date = calendar.getTime();
		return date;
	}

	/**
	 * 得到输入日期+i月以后的日期
	 * 
	 * @param s
	 *            yyyyMMdd格式日期
	 * @param i
	 *            可以是负数
	 * @return
	 */
	public static String getNextDateByMonth(String s, int i) {

		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd");
		Date date = simpledateformat.parse(s, new ParsePosition(0));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(2, i);
		date = calendar.getTime();
		s = simpledateformat.format(date);
		return s;
	}

	/**
	 * 得到输入日期+i年以后的日期
	 * 
	 * @param s
	 *            yyyyMMdd格式日期
	 * @param i
	 *            可以是负数
	 * @return
	 */
	public static String getNextDateByYear(String s, int i) {

		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd");
		Date date = simpledateformat.parse(s, new ParsePosition(0));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, i);
		date = calendar.getTime();
		s = simpledateformat.format(date);
		return s;
	}

	/**
	 * 得到"yyyy年MM月dd日"格式日期
	 * 
	 * @param fdate
	 *            yyyyMMdd格式日期
	 * @return
	 */
	public static String getCNDate(String fdate) {
		if (ObjectUtil.isEmpty(fdate))
			return "";
		String cur_date = fdate;
		cur_date = cur_date.substring(0, 4) + "年" + cur_date.substring(4, 6)
				+ "月" + cur_date.substring(6) + "日";
		return cur_date;
	}

	/**
	 * 得到"kk时mm分ss秒"格式时间
	 * 
	 * @param ftime
	 *            kkmmss格式时间
	 * @return
	 */
	public static String getCNTime(String ftime) {
		String cur_time = ftime;
		cur_time = cur_time.substring(0, 2) + "时" + cur_time.substring(2, 4)
				+ "分" + cur_time.substring(4) + "秒";
		return cur_time;
	}

	/**
	 * 计算两个日期相差的天数
	 * 
	 * @param startDate
	 *            格式：yyyy-MM-dd
	 * @param endDate
	 *            格式：yyyy-MM-dd
	 * @return 返回两日期相差的天数
	 */
	public static int getDatePeriod(String startDate, String endDate) {
		try {
			Date sdate = getDateByString(startDate, "yyyy-MM-dd");

			Date edate = getDateByString(endDate, "yyyy-MM-dd");
			long s = sdate.getTime();

			long e = edate.getTime();

			int days = (int) ((e - s) / (1000 * 60 * 60 * 24));
			return days;
		} catch (Exception e) {
			logger.error(StringUtil.getTrace(e));
			return 0;
		}
	}

	/**
	 * 计算两个日期相差的天数
	 * 
	 * @param startDate
	 *            格式：yyyy-MM-dd
	 * @param endDate
	 *            格式：yyyy-MM-dd
	 * @return 返回两日期相差的天数
	 */
	public static int getDays(String startdate, String enddate)
			throws Exception {
		Date sdate = getDateByString(startdate, "yyyy-MM-dd");

		Date edate = getDateByString(enddate, "yyyy-MM-dd");
		long s = sdate.getTime();

		long e = edate.getTime();

		int days = (int) ((e - s) / (1000 * 60 * 60 * 24));
		return days;
	}

	/**
	 * 计算两个日期相差的天数
	 * 
	 * @param startDate
	 *            格式：yyyyMMdd
	 * @param endDate
	 *            格式：yyyyMMdd
	 * @return 返回两日期相差的天数
	 */
	public static int dateMargin(String startDate, String endDate) {

		String d1 = format2DB(startDate);
		String d2 = format2DB(endDate);
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(df.parse(d1, new ParsePosition(0)));
		Date end = df.parse(d2, new ParsePosition(0));

		int margin = 0;
		int step = startDate.compareTo(endDate) > 0 ? -1 : 1;
		while (calendar.getTime().compareTo(end) != 0) {
			calendar.add(Calendar.DATE, step);
			margin += step;

		}
		return margin;

	}

	/**
	 * 计算两个时间的差（单位：秒）
	 * 
	 * @param begin_dt
	 *            格式：yyyyMMddkkmmss
	 * @param end_dt
	 *            格式：yyyyMMddkkmmss
	 * @return 差（秒）
	 */
	public static int getSecsDiff(String begin_dt, String end_dt) {

		if (begin_dt == null || end_dt == null)
			return 0;
		if (begin_dt.length() == 8)
			begin_dt = begin_dt + "000000";
		if (begin_dt.length() == 6)
			begin_dt = getSysDate() + begin_dt;
		if (end_dt.length() == 8)
			end_dt = end_dt + "000000";
		if (end_dt.length() == 6)
			end_dt = getSysDate() + end_dt;

		int iBYYYY = Integer.parseInt(begin_dt.substring(0, 4));
		int iBMM = Integer.parseInt(begin_dt.substring(4, 6));
		int iBDD = Integer.parseInt(begin_dt.substring(6, 8));
		int iBhh = Integer.parseInt(begin_dt.substring(8, 10));
		int iBmm = Integer.parseInt(begin_dt.substring(10, 12));
		int iBss = Integer.parseInt(begin_dt.substring(12, 14));
		int iEYYYY = Integer.parseInt(end_dt.substring(0, 4));
		int iEMM = Integer.parseInt(end_dt.substring(4, 6));
		int iEDD = Integer.parseInt(end_dt.substring(6, 8));
		int iEhh = Integer.parseInt(end_dt.substring(8, 10));
		int iEmm = Integer.parseInt(end_dt.substring(10, 12));
		int iEss = Integer.parseInt(end_dt.substring(12, 14));
		Calendar BeginDate = new GregorianCalendar(iBYYYY, iBMM, iBDD, iBhh,
				iBmm, iBss);
		Calendar EndDate = new GregorianCalendar(iEYYYY, iEMM, iEDD, iEhh,
				iEmm, iEss);
		long lBegin = BeginDate.getTime().getTime();
		long lEnd = EndDate.getTime().getTime();
		// long lDiff = (lEnd > lBegin) ? (lEnd - lBegin) : (lBegin - lEnd);
		long lDiff = lBegin - lEnd;
		BeginDate = null;
		EndDate = null;
		return (int) (lDiff / 1000);
	}

	/**
	 * 判断传入日期是否为工作日（参数必须是14位或8位）
	 * 
	 * @param date
	 *            格式：yyyyMMddkkmmss或yyyyMMdd
	 * @return
	 */
	public static boolean isWorkDay(String date) {

		if (date == null)
			return false;
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		try {
			Date newDate = dateFormat.parse(date);
			calendar.setTime(newDate);
		} catch (Exception e) {
			Debug.exception(logger, e);
			return false;
		}
		if (Calendar.SATURDAY == calendar.get(Calendar.DAY_OF_WEEK)
				|| Calendar.SUNDAY == calendar.get(Calendar.DAY_OF_WEEK)) {
			return false;
		}
		return true;
	}

	/**
	 * 判断传入日期是否为周五
	 * 
	 * @param date
	 *            格式：yyyyMMdd
	 * @return
	 */
	public static boolean isWeekEndFRIDAY(String date) {

		if (date == null)
			return false;
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		try {
			Date newDate = dateFormat.parse(date);
			calendar.setTime(newDate);
		} catch (Exception e) {
			Debug.exception(logger, e);
			return false;
		}
		if (Calendar.FRIDAY == calendar.get(Calendar.DAY_OF_WEEK)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断传入日期是否为周一
	 * 
	 * @param date
	 *            格式：yyyyMMdd
	 * @return
	 */
	public static boolean isWeekStartMONDAY(String date) {

		if (date == null)
			return false;
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		try {
			Date newDate = dateFormat.parse(date);
			calendar.setTime(newDate);
		} catch (Exception e) {
			Debug.exception(logger, e);
			return false;
		}
		if (Calendar.MONDAY == calendar.get(Calendar.DAY_OF_WEEK)) {
			return true;
		}
		return false;
	}

	/**
	 * 取年月日中的年
	 * 
	 * @param fdate
	 *            格式：yyyyMMdd或yyyy-MM-dd
	 * @return
	 */
	public static String getYear(String fdate) {

		if (ObjectUtil.isEmpty(fdate))
			return "";
		String cur_date = fdate;
		cur_date = cur_date.substring(0, 4);
		return cur_date;
	}

	/**
	 * 取年月日中的月
	 * 
	 * @param fdate
	 *            格式：yyyyMMdd
	 * @return
	 */
	public static String getMonth(String fdate) {

		if (ObjectUtil.isEmpty(fdate))
			return "";
		String cur_date = fdate;
		cur_date = cur_date.substring(4, 6);
		return cur_date;
	}

	/**
	 * 取年月日中的日
	 * 
	 * @param fdate
	 *            格式：yyyyMMdd
	 * @return
	 */
	public static String getDay(String fdate) {

		if (ObjectUtil.isEmpty(fdate))
			return "";
		String cur_date = fdate;
		cur_date = cur_date.substring(6);
		return cur_date;
	}

	/**
	 * 判断日期是否符合格式要求
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isInPattern(String date, String pattern) {

		if (date == null)
			return false;
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		try {
			dateFormat.parse(date);
			return true;
		} catch (Exception e) {
			// Debug.exception(logger,e);
			return false;
		}
	}

	/**
	 * 根据日期字符串获得java.util.Date对象
	 * 
	 * @param String类型的日期
	 * @param 输入日期的格式
	 * @return Date对象
	 * */
	public static Date getDateByString(String dateByString, String pattern)
			throws Exception {

		Date date = null;
		if (dateByString == null || dateByString.trim().equals(""))
			return null;
		try {
			SimpleDateFormat format = new SimpleDateFormat(pattern);

			date = format.parse(dateByString);
		} catch (Exception e) {
			String error = "输入的日期格式不正确，请输入" + pattern + "格式的日期";
			throw new Exception(error, e);
		}
		return date;
	}
	
	/**
	 * 根据java.util.Date对象获得指定格式的日期字符串
	 * 
	 * @param Date对象
	 * @param 输出日期的格式
	 * @return String类型的日期
	 * */
	public static String getStringByDate(Date date, String pattern) {

		if (date == null)
			return null;
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	/**
	 * 获取当前年月
	 * */
	public static String getCorrentYearAndMonth() {
		return new DateTime().toString("yyyyMM");
	}

	/**
	 * 如果参数年月不为空，在返回;否则返回当前年月
	 * */
	public static String processDateTime(String datetime) {
		return StringUtils.isNotEmpty(datetime) ? datetime.trim()
				: getCorrentYearAndMonth();
	}

	/**
	 * 描述：获得上年的同一时间
	 * 
	 * @param date
	 *            yyyymm
	 * @return yyyymm
	 */
	public static String getSameLastYear(String date) {
		int year = Integer.valueOf(DateUtil.getYear(date));
		String lastyear = String.valueOf(year - 1);
		String month = DateUtil.getMonth(date);

		return lastyear + month + "01";
	}

	/**
	 * 描述：拿到上一个月的同一天
	 * 
	 * @param date
	 *            yyyymmdd
	 * @return yyyymmdd
	 */

	public static String getLastMonth(String date) {

		int year = Integer.valueOf(DateUtil.getYear(date));
		int month = Integer.valueOf(DateUtil.getMonth(date));
		switch (month) {
		case 1:
			year--;
			month = 12;
			break;
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
		default:
			month--;
			break;
		}
		
		String strMonth;
		if (month < 10){
			strMonth = "0"+String.valueOf(month);
		}else{
			strMonth = String.valueOf(month);
		}
		
		return String.valueOf(year) + strMonth + "01";
	}
	
	public static boolean isMonthFirstDay(){
	    Calendar c = Calendar.getInstance();
	    int today = c.get(Calendar.DAY_OF_MONTH);
	    return today == 1;
	}
	
	public static boolean isYearFirstDay(){
	    Calendar c = Calendar.getInstance();
	    int today = c.get(Calendar.DAY_OF_YEAR);
	    return today == 1;
	}
	
	public static Date getNextDateByDay(Date date, int i) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(5, i);
		date = calendar.getTime();
		return date;
	}
}
