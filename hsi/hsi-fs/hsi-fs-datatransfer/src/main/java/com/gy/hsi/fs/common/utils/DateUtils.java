/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-service
 * 
 *  Package Name    : com.gy.hsxt.uf.common.utils
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
	 * 默认的日期格式：yyyy-MM-dd
	 */
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

	/**
	 * 私有构造函数
	 */
	private DateUtils() {
	}

	/**
	 * 日期转换为字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String parseDate2String(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		return sdf.format(date);
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
		return new SimpleDateFormat("yyyyMMddHHmmss").format(date);
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
	 * 获得yyyyMMddHHmmss格式时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getYYYYMMddHHmmssDate(String date) {
		if (null == date) {
			return null;
		}

		try {
			date += "000000";

			if (14 <= date.length()) {
				date = date.substring(0, 14);

				return new SimpleDateFormat("yyyyMMddHHmmss").parse(date);
			}
		} catch (ParseException e) {
		}

		return null;
	}

	/**
	 * 获得yyyyMMddHHmmss格式时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getYYYYMMddDate(String date) {
		if (null == date) {
			return null;
		}

		try {
			if (8 <= date.length()) {
				date = date.substring(0, 8);

				return new SimpleDateFormat("yyyyMMdd").parse(date);
			}
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

		SimpleDateFormat dateFormat = new SimpleDateFormat(strFormat);
		Date newDate = null;

		try {
			newDate = dateFormat.parse(dateValue);
		} catch (ParseException pe) {
			newDate = null;
		}

		return newDate;
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
	 * 得到指定分钟之后的日期[当前时间+分钟]
	 * 
	 * @param minutes
	 * @return
	 */
	public static Date getDateAfterMinutes(int minutes) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MINUTE, minutes);

		return calendar.getTime();
	}

	/**
	 * 获得时区
	 * 
	 * @return
	 */
	public static TimeZone getTimeZone() {
		return Calendar.getInstance().getTimeZone();
	}

	/**
	 * 计算时间差
	 * 
	 * @param begin
	 * @param end
	 * @return
	 */
	public static long getTimeDiff(Date begin, Date end) {
		return end.getTime() - begin.getTime();
	}

	public static void main(String[] args) {
		System.out.println(getDateAfterMinutes(5));
	}
}
