package com.gy.hsi.nt.api.common;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;


/**
 * 
 * @className:DateUtil
 * @author:likui
 * @date:2015年7月27日
 * @desc:日期工具类
 * @company:gyist
 */
public class DateUtil {
	
	private static final Logger LOG = Logger.getLogger(DateUtil.class);
	
	/**
	 * 默认的日期格式：yyyy-MM-dd
	 */
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

	/**
	 * 默认的是日期时间格式：yyyy-MM-dd HH:mm:ss
	 */
	public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_TIME_FORMAT = "yyyyMMddHHmmss";
	public static final String DATE_TIME_FORMAT_SSS = "yyyyMMddHHmmssSSS";
	public static final String DEFAULT_DATE_TIME_FORMAT_CN = "yyyy年MM月dd日 HH时mm分ss秒";
	public static final String DATE_FORMAT = "yyyyMMdd";
	
	
	public static final String START_MONTH_DAY="START_MONTH_DAY";
	public static final String END_MONTH_DAY="END_MONTH_DAY";
	public static final String START_WEEK_DAY="START_WEEK_DAY";
	public static final String END_WEEK_DAY="END_WEEK_DAY";
	
	public static final String START_DATE="START_DATE";
	public static final String END_DATE="END_DATE";
	
	
	
	/**
	 * 
	* @author:likui
	* @created:2015年7月27日下午4:23:12
	* @desc:当前日一个月的起始日期
	* @param:@return
	* @param:Map<String,Object>
	* @throws
	 */
	public static Map<String,Object> getMonthDay(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put(DateUtil.START_MONTH_DAY, sdf.format(calendar.getTime())+" 00:00:00");
		map.put(DateUtil.END_MONTH_DAY, sdf.format(new Date())+" 23:59:59");
		return map;
	}
	
	/**
	 * 当前日一周内的起始日期
	 * @author	leiyt
	 * @createDate	2014-10-31 上午10:07:32 
	 * @return
	 */
	public static Map<String,Object> getWeekDay(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -6);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put(DateUtil.START_WEEK_DAY, sdf.format(calendar.getTime())+" 00:00:00");
		map.put(DateUtil.END_WEEK_DAY, sdf.format(new Date())+" 23:59:59");
		return map;
	}

	/**
	 * 
	 * @author:likui
	 * @created:2014年9月10日上午11:49:18
	 * @desc:获取当前日期
	 * @param:@return
	 * @param:Date
	 * @throws
	 */
	public static Date getCurrentDate() {
		Calendar cal = Calendar.getInstance();
		Date currDate = cal.getTime();

		return currDate;
	}

	/**
	 * 
	 * @author:likui
	 * @created:2014年9月10日上午11:49:39
	 * @desc:根据日期格式获取当前日期
	 * @param:@param format
	 * @param:@return
	 * @param:Date
	 * @throws
	 */
	public static Date getCurrentDate(String format) {
		SimpleDateFormat dFormat = setFormat(format);
		try {
			return dFormat.parse(dFormat.format(getCurrentDate()));
		} catch (ParseException e) {
			LOG.error(e);
		}
		return null;
	}

	private static SimpleDateFormat setFormat(String format) {
		return new SimpleDateFormat(format);
	}

	/**
	 * 功能: 将日期对象按照某种格式进行转换，返回转换后的字符串
	 * 
	 * @param date
	 *            日期对象
	 * @param pattern
	 *            转换格式 例：yyyy-MM-dd
	 */
	public static String dateToString(Date date, String pattern) {
		String strDateTime = null;
		SimpleDateFormat formater = new SimpleDateFormat(pattern);
		strDateTime = date == null ? null : formater.format(date);
		return strDateTime;
	}

	/**
	 * 功能: 将传入的日期对象按照yyyy-MM-dd格式转换成字符串返回
	 * 
	 * @param date
	 *            日期对象
	 * @return String
	 */
	public static String dateToString(Date date) {
		return date == null ? null : dateToString(date, DEFAULT_DATE_FORMAT);
	}
	
	/**
	 * 功能: 将传入的日期对象按照yyyyMMdd格式转换成字符串返回
	 * 
	 * @return String
	 */
	public static String dateToString() {
		return dateToString(new Date(),DATE_FORMAT);
	}
	
	/**
	 * 功能: 将传入的日期对象按照yyMMdd格式转换成字符串返回
	 * 
	 * @return String
	 */
	public static String dateToStringYY() {
		return dateToString(new Date(),"yyMMdd");
	}
	
	/**
	 * 功能: 格式化日期(秒钟最大)
	 * 
	 * @return String
	 */
	public static String getDateStringMaxSecond(Date date){
		if (date == null) {
			return null;
		}else{
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(13, calendar.getActualMaximum(13));
			return dateToString(calendar.getTime(),DEFAULT_DATE_TIME_FORMAT);
		}
	}
	
	/**
	 * 功能: 格式化日期(秒钟最大)
	 * 
	 * @return Date
	 */
	public static Date getDateMaxSecond(Date date) {
		if (date == null) {
			return null;
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(13, calendar.getActualMaximum(13));
			return calendar.getTime();
		}
	}

	/**
	 * 功能: 将传入的日期对象按照yyyy-MM-dd HH:mm:ss格式转换成字符串返回
	 * 
	 * @param date
	 *            日期对象
	 * @return String
	 */
	public static String dateTimeToString(Date date) {
		return date == null ? null : dateToString(date,
				DEFAULT_DATE_TIME_FORMAT);
	}

	/**
	 * 功能: 将传入的日期对象按照yyyyMMdd HH:mm:ss格式转换成字符串返回
	 * 
	 * @param date
	 *            日期对象
	 * @return String
	 */
	public static String dateTimeToStringNO(Date date) {
		return date == null ? null : dateToString(date, DATE_TIME_FORMAT);
	}
	
	/**
	 * 功能: 将传入的日期对象按照yyyyMMddHHmmssSSS格式转换成字符串返回
	 * 
	 * @param date
	 *            日期对象
	 * @return String
	 */
	public static String dateTimeToStringNOSSS(Date date) {
		return date == null ? null : dateToString(date, DATE_TIME_FORMAT_SSS);
	}

	/**
	 * 功能: 将传入的日期对象按照yyyy-MM-dd HH:mm:ss格式转换成字符串返回
	 * 
	 * @param date
	 *            日期对象
	 * @return String
	 */
	public static String dateTimeToStringCN(Date date) {
		return date == null ? null : dateToString(date,
				DEFAULT_DATE_TIME_FORMAT_CN);
	}

	/**
	 * 功能: 将插入的字符串按格式转换成对应的日期对象
	 * 
	 * @param str
	 *            字符串
	 * @param pattern
	 *            格式
	 * @return Date
	 */
	public static Date stringToDate(String str, String pattern) {
		Date dateTime = null;
		try {
			if (str != null && !str.equals("")) {
				SimpleDateFormat formater = new SimpleDateFormat(pattern);
				dateTime = formater.parse(str);
			}
		} catch (Exception ex) {
			LOG.error(ex);
		}
		return dateTime;
	}

	/**
	 * 功能: 将传入的字符串按yyyy-MM-dd格式转换成对应的日期对象
	 * 
	 * @param str
	 *            需要转换的字符串
	 * @return Date 返回值
	 * @throws  
	 */
	public static Date stringToDate(String str) {
		return stringToDate(str, DEFAULT_DATE_FORMAT);
	}

	/**
	 * 功能: 将传入的字符串按yyyy年MM月dd日 HH时mm分ss秒格式转换成对应的日期对象
	 * 
	 * @param str
	 *            需要转换的字符串
	 * @return Date
	 * @throws  
	 */
	public static Date stringToDateTime(String str) {
		return stringToDate(str, DEFAULT_DATE_TIME_FORMAT);
	}

	/**
	 * 功能: 将传入的字符串转换成对应的Timestamp对象
	 * 
	 * @param str
	 *            待转换的字符串
	 * @return Timestamp 转换之后的对象
	 * @throws Exception
	 *             Timestamp
	 */
	public static Timestamp stringToDateHMS(String str) throws Exception {
		Timestamp time = null;
		time = Timestamp.valueOf(str);
		return time;
	}

	/**
	 * 功能: 根据传入的年月日返回相应的日期对象
	 * 
	 * @param year
	 *            年份
	 * @param month
	 *            月份
	 * @param day
	 *            天
	 * @return Date 日期对象
	 */
	public static Date ymdToDate(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);
		return calendar.getTime();
	}

	/**
	 * 功能: 将日期对象按照MM/dd HH:mm:ss的格式进行转换，返回转换后的字符串
	 * 
	 * @param date
	 *            日期对象
	 * @return String 返回值
	 */
	public static String communityDateToString(Date date) {
		SimpleDateFormat formater = new SimpleDateFormat("MM/dd HH:mm:ss");
		String strDateTime = date == null ? null : formater.format(date);
		return strDateTime;
	}

	/**
	 * <p>
	 * 功能：获取某天时间的最大值 如：Thu May 22 23:59:59 CST 2014
	 * </p>
	 * 
	 * @desc getMaxDateOfDay
	 * @param date
	 * @return
	 */
	public static Date getMaxDateOfDay(Date date) {
		if (date == null) {
			return null;
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(11, calendar.getActualMaximum(11));
			calendar.set(12, calendar.getActualMaximum(12));
			calendar.set(13, calendar.getActualMaximum(13));
			calendar.set(14, calendar.getActualMaximum(14));
			return calendar.getTime();
		}
	}
	
	/** 
	* @author:likui
	* @created:2015年4月14日下午5:08:42
	* @desc:获取某天时间的最大值 如：2015 04 14 23:59:59
	* @param:@param date
	* @param:@return
	* @param:String
	* @throws
	 */
	public static String getMaxDateOfDay(String date){
		if(StringUtils.isBlank(date)){
			return null;
		}else{
			return dateTimeToString(getMaxDateOfDay(stringToDate(date)));
		}
	}

	/**
	 * <p>
	 * 功能：获取某天时间的最小值 如：Thu May 22 00:00:00 CST 2014
	 * </p>
	 * 
	 * @desc getMinDateOfDay
	 * @param date
	 * @return
	 */
	public static Date getMinDateOfDay(Date date) {
		if (date == null) {
			return null;
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(11, calendar.getActualMinimum(11));
			calendar.set(12, calendar.getActualMinimum(12));
			calendar.set(13, calendar.getActualMinimum(13));
			calendar.set(14, calendar.getActualMinimum(14));
			return calendar.getTime();
		}
	}
	
	/**
	* @author:likui
	* @created:2015年4月14日下午5:06:56
	* @desc:：获取某天时间的最小值 如：2015 04 14 00:00:00
	* @param:@param date
	* @param:@return
	* @param:String
	* @throws
	 */
	public static String getMinDateOfDay(String date){
		if(StringUtils.isEmpty(date)){
			return null;
		}else{
			return dateTimeToString(getMinDateOfDay(stringToDate(date)));
		}
	}
	
	

	/**
	 * 功能：返回传入日期对象（date）之后days天数的日期对象 若days大于0获得后几天 若days小于0获得前几天
	 * 
	 * @param date
	 *            日期对象
	 * @param days
	 *            往后天数
	 * @return java.util.Date 返回值
	 */
	public static Date getAfterDay(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}

	// day
	/**
	 * 功能: 返回date1与date2相差的天数
	 * 
	 * @param date1
	 * @param date2
	 * @return int
	 */
	public static int dateDiff(Date date1, Date date2) {
		int i = (int) ((date1.getTime() - date2.getTime()) / 3600 / 24 / 1000);
		return i;
	}

	// min
	/**
	 * 功能: 返回date1与date2相差的分钟数
	 * 
	 * @param date1
	 * @param date2
	 * @return int
	 */
	public static int minDiff(Date date1, Date date2) {
		int i = (int) ((date1.getTime() - date2.getTime()) / 1000 / 60);
		return i;
	}

	// second
	/**
	 * 功能: 返回date1与date2相差的秒数
	 * 
	 * @param date1
	 * @param date2
	 * @return int
	 */
	public static int timeDiff(Date date1, Date date2) {
		int i = (int) ((date1.getTime() - date2.getTime()));
		return i;
	}
	
	/**
	 * 
	* @author:likui
	* @created:2015年5月13日下午5:27:59
	* @desc:给指定时间添加或减去多少秒
	* @param:@param date
	* @param:@param second
	* @param:@return
	* @param:Date
	* @throws
	 */
	public static Date addSecond(Date date,int second) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.SECOND, second);
		return cal.getTime();
	}
	
	/**
	 * 
	* @author:likui
	* @created:2015年5月13日下午5:32:49
	* @desc:给指定时间添加或减去多少分钟
	* @param:@param date
	* @param:@param minutes
	* @param:@return
	* @param:Date
	* @throws
	 */
	public static Date addMinutes(Date date,int minutes) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, minutes);
		return cal.getTime();
	}

	/**
	 * 
	 * @author:likui
	 * @created:2014年9月12日下午2:50:11
	 * @desc:给当前时间添加或减去多少分钟
	 * @param:@param minutes
	 * @param:@return
	 * @param:Date
	 * @throws
	 */
	public static Date addMinutes(int minutes) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, minutes);
		return cal.getTime();
	}

	/**
	 * 
	 * @author:likui
	 * @created:2014年9月12日下午2:57:39
	 * @desc:给当前时间添加或减去多少小时
	 * @param:@param hour
	 * @param:@return
	 * @param:Date
	 * @throws
	 */
	public static Date addHour(int hours) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR_OF_DAY, hours);
		return cal.getTime();
	}

	/**
	 * 
	 * @author:likui
	 * @created:2014年9月12日下午2:58:03
	 * @desc:给当前时间添加或减去多少天
	 * @param:@param days
	 * @param:@return
	 * @param:Date
	 * @throws
	 */
	public static Date addDays(int days) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, days);
		return cal.getTime();
	}

	/**
	 * 
	 * @author:likui
	 * @created:2014年9月12日下午2:58:08
	 * @desc:给指定时间添加或减去多少小时
	 * @param:@param date
	 * @param:@param hour
	 * @param:@return
	 * @param:Date
	 * @throws
	 */
	public static Date addHour(Date date, int hours) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, hours);
		return cal.getTime();
	}

	/**
	 * 
	 * @author:likui
	 * @created:2014年9月12日下午2:58:12
	 * @desc:给指定时间添加或减去多少天
	 * @param:@param date
	 * @param:@param days
	 * @param:@return
	 * @param:Date
	 * @throws
	 */
	public static Date addDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, days);
		return cal.getTime();
	}
	
	/**
	 * 
	* @author:likui
	* @created:2015年4月22日上午11:31:19
	* @desc:给指定时间添加或减去多少个月
	* @param:@param date
	* @param:@param days
	* @param:@return
	* @param:Date
	* @throws
	 */
	public static Date addMonth(Date date, int months){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, months);
		return cal.getTime();
	}

	/**
	 * 给指定时间添加或减去多少年
	 *@Description: addYear
	 *@author tanglc 
	 *@date:2015-4-23 下午5:06:18
	 *@param date
	 *@param year
	 *@return
	 */
	public static Date addYear(Date date, int years){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, years);
		return cal.getTime();
	}
	
	/**
	 * @return 当前日期
	 * @throws  
	 */
	public static Date today(){
		return getCurrentDate(DEFAULT_DATE_FORMAT);
	}

	/**
	 * @return 当前时间
	 * @throws  
	 */
	public static Date now(){
		return getCurrentDate(DEFAULT_DATE_TIME_FORMAT);
	}
	
	/**
	 * 
	*@Description: compareDate(比较两个日期相关的天数、月数、年数)
	*@param date1
	*@param date2
	*@param stype  返回值类型 0为比较天，1为比较月，2为比较年
	*@return int   返回类型
	 * @throws  
	 */
	public static int compareDate(Date date1, Date date2, int stype){
		return compareDate(dateToString(date1), dateToString(date2), stype);
	}
	
	/** 
	 * 比较两个日期相关的天数、月数、年数
	 *@author tanglc 
	 *@version 1.1.0
	 *@date:2014-10-22 下午5:56:17
	 *@Description: compareDate(比较两个日期相关的天数、月数、年数)
	 *@param date1 需要比较的时间 不能为空(null),需要正确的日期格式
	 *@param date2 被比较的时间 为空(null)则为当前时间
	 *@param stype 返回值类型 0为多少天，1为多少个月，2为多少年
	 *@return
	 */
	public static int compareDate(String date1, String date2, int stype) {
		int n = 0;
		String formatStyle = stype == 1 ? "yyyy-MM" : "yyyy-MM-dd";
		date2 = date2 == null ? dateToString(new Date()) : date2;
		DateFormat df = new SimpleDateFormat(formatStyle);
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			c1.setTime(df.parse(date1));
			c2.setTime(df.parse(date2));
		} catch (Exception ex) {
			LOG.error(ex);
		}
		while (!c1.after(c2)) { // 循环对比，直到相等，n 就是所要的结果
			n++;
			if (stype == 1) {
				c1.add(Calendar.MONTH, 1); // 比较月份，月份+1
			} else {
				c1.add(Calendar.DATE, 1); // 比较天数，日期+1
			}
		}

		n = n - 1;
		if (stype == 2) {
			n = (int) n / 365;
		}
		return n;
	}
	
	
	/**
	 * 当前日的前N年或后N年起始日期
	*@Description: getYesrDay
	*@author tanglc 
	*@date:2015-2-27 下午8:37:22
	* @return
	 */
	public static Map<String,Object> getYesrDay(int n){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, n);
		Map<String,Object> map=new HashMap<String,Object>();
		if(n>0){//之后n年
			map.put(DateUtil.START_DATE, sdf.format(new Date())+" 23:59:59");
			map.put(DateUtil.END_DATE, sdf.format(calendar.getTime())+" 00:00:00");			
		}else{//之前n年
			map.put(DateUtil.START_DATE, sdf.format(calendar.getTime())+" 00:00:00");
			map.put(DateUtil.END_DATE, sdf.format(new Date())+" 23:59:59");
		}		
		return map;
	}
	/**
	 * 当前日的前N月或后N月起始日期
	*@Description: getMonthDay
	*@author tanglc 
	*@date:2015-2-28 上午11:16:14
	* @param n
	* @return
	 */
	public static Map<String,Object> getMonthDay(int n){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, n);
		Map<String,Object> map=new HashMap<String,Object>();
		if(n>0){//之后n月
			map.put(DateUtil.START_DATE, sdf.format(new Date())+" 23:59:59");
			map.put(DateUtil.END_DATE, sdf.format(calendar.getTime())+" 00:00:00");			
		}else{//之前n月
			map.put(DateUtil.START_DATE, sdf.format(calendar.getTime())+" 00:00:00");
			map.put(DateUtil.END_DATE, sdf.format(new Date())+" 23:59:59");
		}		
		return map;
	}
	
	/**
	 * 当前日的前N天或后N天起始日期
	*@Description: getWeekDay
	*@author tanglc 
	*@date:2015-2-28 上午11:25:50
	* @return
	 */
	public static Map<String,Object> getWeekDay(int n){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, n);
		Map<String,Object> map=new HashMap<String,Object>();
		if(n>0){//之后n月
			map.put(DateUtil.START_DATE, sdf.format(new Date())+" 23:59:59");
			map.put(DateUtil.END_DATE, sdf.format(calendar.getTime())+" 00:00:00");			
		}else{//之前n月
			map.put(DateUtil.START_DATE, sdf.format(calendar.getTime())+" 00:00:00");
			map.put(DateUtil.END_DATE, sdf.format(new Date())+" 23:59:59");
		}
		return map;
	}
}
