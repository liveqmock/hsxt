package com.gy.hsxt.gp.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * 
 * @category 日期工具类
 * @className DateUtil
 * @description 日期工具类
 * @author zhucy
 * @createDate 2014-7-15 下午4:05:43
 * @updateUser zhucy
 * @updateDate 2014-7-15 下午4:05:43
 * @updateRemark 新增
 * @version v0.0.1
 */
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
	public static Date getYesterdayDate() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);

		return cal.getTime();
	}

	/**
	 * 获取昨天日期
	 * 
	 * @return
	 */
	public static String getYesterdayDate(String pattern) {
		return DateUtils.dateToString(DateUtils.getYesterdayDate(), pattern);
	}

	/**
	 * 将日期对象按照某种格式进行转换，返回转换后的字符串
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String dateToString(Date date, String pattern) {
		if (null == date) {
			return "";
		}

		return new SimpleDateFormat(pattern).format(date);
	}

	/**
	 * 将插入的字符串按格式转换成对应的日期对象
	 * 
	 * @param str
	 * @param pattern
	 * @return
	 */
	public static Date stringToDate(String str, String pattern) {
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
	 * 获取yy-MM-dd字符串日期的前一天
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */
	public static String getBeforeDate(String date, String pattern)
			throws ParseException {

		if (StringUtils.isEmpty(date)) {
			return "";
		}

		Calendar c = Calendar.getInstance();
		c.setTime(new SimpleDateFormat(pattern).parse(date));
		c.set(Calendar.DATE, c.get(Calendar.DATE) - 1);

		return new SimpleDateFormat(pattern).format(c.getTime());
	}

	/**
	 * 获取yy-MM-dd字符串日期的前一天
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String getBeforeDate(Date date, String pattern) {

		try {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.set(Calendar.DATE, c.get(Calendar.DATE) - 1);

			return new SimpleDateFormat(pattern).format(c.getTime());
		} catch (Exception e) {
		}

		return "";
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
	 * 得到指定天数之前的日期[当前日期 -天数]
	 * 
	 * @param days
	 * @return
	 */
	public static Date getDateBeforeDays(int days) {
		return getDateAfterDays(-days);
	}

	/**
	 * 得到指定天数之后的日期
	 * 
	 * @param days
	 * @return
	 */
	public static Date getDateAfterDays(int days) {
		long target = new Date().getTime()
				+ Integer.valueOf(days * 24 * 3600 * 1000).longValue();

		return new Date(target);
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
	 * 是否晚于
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isCurrDateAfter(Date date) {
		return getCurrentDate().after(date);
	}
}
