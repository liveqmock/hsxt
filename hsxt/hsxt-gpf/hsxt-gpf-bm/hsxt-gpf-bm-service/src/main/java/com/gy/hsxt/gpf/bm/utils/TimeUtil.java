package com.gy.hsxt.gpf.bm.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 
 *@Title:TimeUtil
 *@Description:时间工具类
 *@Copyright:Copyright (c) 2014
 *@Company:gykj
 *@author li.chang 
 *@version 1.0
 *@date:2015年2月3日 上午11:17:27
 */
public class TimeUtil {
	private int weeks = 0;
	
	/**
	 * 默认的日期格式：yyyy-MM-dd
	 */
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	
	public static final String DATE_FORMAT = "yyyyMMdd";
	
	public static String getWeek(String sdate) {
		Date date = strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return new SimpleDateFormat("EEEE").format(c.getTime());
	}

	public static Date strToDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		ParsePosition pos = new ParsePosition(0);
		return formatter.parse(strDate, pos);
	}

	public static long getDays(String date1, String date2) {
		if (StringUtils.isEmpty(date1)||StringUtils.isEmpty(date2)) {
			return 0L;
		}
		try {
			SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
			Date date = format.parse(date1);
			Date mydate = format.parse(date2);
			return (date.getTime() - mydate.getTime()) / 86400000L;
		} catch (Exception localException) {
			return 0L;
		}

	}

	/**
	 * 
	*@Description: getyd(获取昨天日期)
	*@return String   返回类型
	 */
	public String getyd() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return new SimpleDateFormat(DATE_FORMAT).format(cal.getTime());
	}

	/**
	 * 
	*@Description: getDefaultDay(获取本月最后一天日期)
	*@return String   返回类型
	 */
	public String getDefaultDay() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(5, 1);
		lastDate.add(2, 1);
		lastDate.add(5, -1);

		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 
	*@Description: getPreviousMonthFirst(获取上月第一天日期)
	*@return String   返回类型
	 */
	public String getPreviousMonthFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(5, 1);
		lastDate.add(2, -1);
		str = sdf.format(lastDate.getTime());

		return str;
	}

	/**
	 * 
	*@Description: getFirstDayOfMonth(获取本月第一天日期)
	*@return String   返回类型
	 */
	public String getFirstDayOfMonth() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(5, 1);
		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 
	*@Description: getCurrentWeekday(获取本周日的日期)
	*@return String   返回类型
	 */
	public String getCurrentWeekday() {
		this.weeks = 0;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(5, mondayPlus + 6);
		Date monday = currentDate.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		String preMonday = sdf.format(monday);

		return preMonday;
	}

	/**
	 * 
	*@Description: getNowTime(获取当天日期)
	*@param dateformat
	*@return String   返回类型
	 */
	public String getNowTime(String dateformat) {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);
		String hehe = dateFormat.format(now);

		return hehe;
	}

	public String getToday(String dateformat){
		if(StringUtils.isEmpty(dateformat)){
			dateformat=DATE_FORMAT;
		}
		SimpleDateFormat sf = new SimpleDateFormat(dateformat);
		Date date=new Date();
		long time = (date.getTime() / 1000) + 60 * 60 * 24;//秒
		date.setTime(time * 1000);//毫秒
		return  sf.format(date).toString();
	}

	private int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		int dayOfWeek = cd.get(7) - 1;
		if (dayOfWeek == 1) {
			return 0;
		}

		return (1 - dayOfWeek);
	}

	/**
	 * 
	*@Description: getMondayOFWeek(获取本周一日期)
	*@return String   返回类型
	 */
	public String getMondayOFWeek() {
		this.weeks = 0;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(5, mondayPlus);
		Date monday = currentDate.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		String preMonday = sdf.format(monday);

		return preMonday;
	}

	/**
	 * 
	*@Description: getPreviousWeekSunday(获取上周日日期)
	*@return String   返回类型
	 */
	public String getPreviousWeekSunday() {
		this.weeks = 0;
		this.weeks -= 1;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(5, mondayPlus + this.weeks);

		Date monday = currentDate.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		String preMonday = sdf.format(monday);

		return preMonday;
	}
	
	/**
	 * 
	*@Description: getPreviousWeekday(获取上周一日期)
	*@return String   返回类型
	 */
	public String getPreviousWeekday() {
		this.weeks -= 1;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(5, mondayPlus + 7 * this.weeks);
		Date monday = currentDate.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		String preMonday = sdf.format(monday);

		return preMonday;
	}

	/**
	 * 
	*@Description: getPreviousMonthEnd(获取上月最后一天的日期)
	*@return String   返回类型
	 */
	public String getPreviousMonthEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(2, -1);
		lastDate.set(5, 1);
		lastDate.roll(5, -1);
		str = sdf.format(lastDate.getTime());
		return str;
	}
	 
}
