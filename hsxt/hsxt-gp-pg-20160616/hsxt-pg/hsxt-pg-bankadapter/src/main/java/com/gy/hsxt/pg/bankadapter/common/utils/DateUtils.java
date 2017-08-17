/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/***************************************************************************
 * <PRE>
 *  Project Name    : bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.common.utils
 * 
 *  File Name       : DateUtils.java
 * 
 *  Creation Date   : 2014-6-4
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 日期工具类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public final class DateUtils {
	/**
	 * 私有构造函数
	 */
	private DateUtils() {
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
				dateTime = new SimpleDateFormat(pattern).parse(str);
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
	 * 给指定时间添加或减去多少秒
	 * 
	 * @param date
	 * @param seconds
	 * @return
	 */
	public static Date addSeconds(Date date, int seconds) {
		Calendar cal = Calendar.getInstance();
		
		if (date != null) {
			cal.setTime(date);
		}
		
		cal.add(Calendar.SECOND, seconds);
		
		return cal.getTime();
	}

	/**
	 * 日期转换为字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String parseDate2String(Date date) {
		return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(date);
	}

	/**
	 * 获得yyyyMMdd格式日期
	 * 
	 * @param date
	 * @return
	 */
	public static String format2yyyyMMddDate(Date date) {
		if (null == date) {
			return "";
		}

		return new SimpleDateFormat("yyyyMMdd").format(date);
	}

	/**
	 * 获得yyyyMMddHHmmss格式时间
	 * 
	 * @param date
	 * @return
	 */
	public static String getYYYYMMddHHmmssDate(Date date) {
		try {
			return new SimpleDateFormat("yyyyMMddHHmmss").format(date);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获得yyyyMMddHHmmss格式时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getYYYYMMddHHmmssDate(String dateStr) {
		String dateformat = "yyyyMMddHHmmss";

		try {
			dateStr = StringHelper.rightPad(dateStr, dateformat.length(), '0');

			return new SimpleDateFormat(dateformat).parse(dateStr);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获得yyyyMMdd格式日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date parse2yyyyMMddDate(String date) {
		try {
			return new SimpleDateFormat("yyyyMMdd").parse(date);
		} catch (ParseException e) {
		}

		return null;
	}

	/**
	 * Parse a string and return the date value in the specified format
	 * 
	 * @param strFormat
	 * @param dateValue
	 * @return
	 */
	public static Date parseDate(String strFormat, String dateValue) {
		if (null == dateValue) {
			return null;
		}

		Date newDate = null;

		try {
			newDate = new SimpleDateFormat(strFormat).parse(dateValue);
		} catch (ParseException pe) {
		}

		return newDate;
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
	 * 得到指定天数之前的日期[当前日期 -天数]
	 * 
	 * @param days
	 * @return
	 */
	public static Date getDateBeforeDays(int days) {
		return getDateAfterDays(-days);
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

		if (null == date) {
			return "";
		}

		return new SimpleDateFormat(pattern).format(date);
	}
	
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
	 * 获取yy-MM-dd字符串日期的前一天
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */
	public static String getBeforeDate(String date, String pattern)
			throws ParseException {

		if (StringHelper.isEmpty(date)) {
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
		
		if(null == date) {
			return false;
		}
		
		return getCurrentDate().after(date);
	}
}
