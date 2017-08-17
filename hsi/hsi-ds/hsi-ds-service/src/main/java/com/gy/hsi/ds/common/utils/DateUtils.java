package com.gy.hsi.ds.common.utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.gy.hsi.ds.common.constant.DataFormatConst;

public class DateUtils {
	
	/**
	 * 获取当前日期
	 * 
	 * @return
	 */
	public static Date getCurrentDate() {
		Calendar cal = Calendar.getInstance();
		Date currDate = cal.getTime();

		return currDate;
	}

	/**
	 * 获取昨天日期
	 * 
	 * @return
	 */
	public static Date getYesterDate() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);

		return cal.getTime();
	}

	/**
	 * 得到指定天数之后的日期[当前日期 +天数]
	 * 
	 * @param days
	 * @return
	 */
	public static Date getDateAfterDays(int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, days);

		return calendar.getTime();
	}

	/**
	 * 得到指定天数之前的日期[当前日期 -天数]
	 * 
	 * @param days
	 * @return
	 */
	public static Date getDateBeforeDays(int days) {
		return getDateAfterDays(-days);
	}

	/**
	 * 将插入的字符串按格式转换成对应的日期对象
	 * 
	 * @param str
	 * @param pattern
	 * @return
	 */
	public static Date StringToDate(String str, String pattern) {
		Date dateTime = null;

		try {
			if (str != null && !str.equals("")) {
				SimpleDateFormat formater = new SimpleDateFormat(pattern);
				dateTime = formater.parse(str);
			}
		} catch (Exception ex) {
		}

		return dateTime;
	}

	/**
	 * 给指定时间添加或减去多少分钟
	 * 
	 * @param date
	 * @param minutes
	 * @return
	 */
	public static Date addMinutes(Date date, int minutes) {
		Calendar cal = Calendar.getInstance();
		if (date != null) {
			cal.setTime(date);
		}
		cal.add(Calendar.MINUTE, minutes);
		return cal.getTime();
	}

	/**
	 * 给指定时间添加或减去多少小时
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date addDay(Date date, int day) {
		Calendar cal = Calendar.getInstance();

		if (date != null) {
			cal.setTime(date);
		}

		cal.add(Calendar.DAY_OF_MONTH, day);

		return cal.getTime();
	}

	public static String getSimpleDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
	
	/**
	 * 时间格式：yyyyMMdd
	 * 
	 * @return
	 */
	public static String getyyyyMMddNowDate() {
		return new SimpleDateFormat(
				"yyyyMMdd").format(new Date());
	}

	/**
	 * 日期是否在今天
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isTodayDate(Date date) {
		try {
			String strTodayDate = getSimpleDate(new Date()) + " 00:00:00";

			Date todayDate = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss").parse(strTodayDate);

			return date.getTime() >= todayDate.getTime();
		} catch (ParseException e) {
		}

		return false;
	}

	/**
	 * 获得格式时间
	 * 
	 * @param date
	 * @param dateFormat
	 * @return
	 */
	public static String getDateByformat(Date date, String dateFormat) {
		return new SimpleDateFormat(dateFormat).format(date);
	}

	/**
	 * Long时间格式化
	 * 
	 * @param date
	 * @return
	 */
	public static String formatLongDate(Long date) {
		SimpleDateFormat sdf = new SimpleDateFormat(
				DataFormatConst.DISPLAY_TIME_FORMAT);
		return sdf.format(date);
	}

	/**
	 * Date时间格式化
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		if(null == date) {
			return "";
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(
				DataFormatConst.DISPLAY_TIME_FORMAT);
		return sdf.format(date);
	}

	/**
	 * 获取yy-MM-dd字符串日期的后一天
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String getAfterDate(String date) throws ParseException {
		Calendar c = Calendar.getInstance();
		Date tmpDate = new SimpleDateFormat(
				DataFormatConst.DISPLAY_DATE_FORMAT).parse(date);
		c.setTime(tmpDate);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);
		String dayAfter = new SimpleDateFormat(
				DataFormatConst.DISPLAY_DATE_FORMAT).format(c.getTime());
		return dayAfter;
	}

	/**
	 * 获取yy-MM-dd字符串日期的前一天
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String getBeforeDate(String date) throws ParseException {
		Calendar c = Calendar.getInstance();
		Date tmpDate = new SimpleDateFormat(
				DataFormatConst.DISPLAY_DATE_FORMAT).parse(date);
		c.setTime(tmpDate);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);

		String dayBefore = new SimpleDateFormat(
				DataFormatConst.DISPLAY_DATE_FORMAT).format(c.getTime());
		return dayBefore;
	}

	/**
	 * 获取当前日期
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String getNowDate() {

		String nowDate = new SimpleDateFormat(
				DataFormatConst.DISPLAY_DATE_FORMAT).format(new Date());
		
		return nowDate;
	}
	
	/**
	 * 获取时间差, 精度2
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static String getTimeDiffByMinutes(Date start, Date end) {
		if(null == start || null == end) {
			return "0.00";
		}
		
		long diff = end.getTime() - start.getTime();

	    double minutes =  (diff / (float)(1000.00 * 60));
	    
	    BigDecimal bd = new BigDecimal(minutes);  
        bd = bd.setScale(2, BigDecimal.ROUND_UP);  
        
        return bd.toString();
	}
}
