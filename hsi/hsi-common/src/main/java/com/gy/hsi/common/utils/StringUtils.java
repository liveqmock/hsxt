/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.common.utils;

import java.util.UUID;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-common
 * 
 *  Package Name    : com.gy.hsi.common.utils
 * 
 *  File Name       : StringUtils.java
 * 
 *  Creation Date   : 2014-5-26
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : String工具类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public final class StringUtils {

	/**
	 * 私有构造函数
	 */
	private StringUtils() {
	}

	/**
	 * 判断是否为空
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isEmpty(String value) {
		if ((null == value) || value.isEmpty()) {
			return true;
		}

		return false;
	}

	/**
	 * 判断是否为空
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isEmpty(Object value) {
		if ((null == value) || (0 == value.toString().length())) {
			return true;
		}

		return false;
	}

	/**
	 * 判断是否为空
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isEmptyTrim(String value) {
		if ((null == value) || value.trim().isEmpty()) {
			return true;
		}

		return false;
	}

	/**
	 * 把null转换为""
	 * 
	 * @param value
	 * @return
	 */
	public static String avoidNull(String value) {
		if (null == value) {
			return "";
		}

		return value;
	}

	/**
	 * 是否存在空值
	 * 
	 * @param params
	 * @return
	 */
	public static boolean hasNull(Object[] params) {
		for (Object obj : params) {
			if (StringUtils.isEmpty(obj)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 字符串转换为int型
	 * 
	 * @param str
	 * @return
	 */
	public static int str2Int(String str) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
		}

		return 0;
	}

	/**
	 * 字符串转换为int型
	 * 
	 * @param str
	 * @return
	 */
	public static int str2Int(Object str) {
		try {
			return Integer.parseInt((String) str);
		} catch (NumberFormatException e) {
		}

		return 0;
	}

	/**
	 * 字符串转换为int型
	 * 
	 * @param str
	 * @param defaultValue
	 * @return
	 */
	public static int str2Int(String str, int defaultValue) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
		}

		return defaultValue;
	}

	/**
	 * 字符串转换为byte型
	 * 
	 * @param str
	 * @param defaultValue
	 * @return
	 */
	public static byte str2Byte(String str, byte defaultValue) {
		try {
			return Byte.parseByte(str);
		} catch (NumberFormatException e) {
		}

		return defaultValue;
	}

	/**
	 * 字符串转换为long型
	 * 
	 * @param str
	 * @return
	 */
	public static long str2Long(String str) {
		try {
			return Long.parseLong(str);
		} catch (NumberFormatException e) {
		}

		return 0;
	}

	/**
	 * 字符串转换为long型
	 * 
	 * @param str
	 * @param defaultValue
	 * @return
	 */
	public static long str2Long(String str, long defaultValue) {
		try {
			return Long.parseLong(str);
		} catch (NumberFormatException e) {
		}

		return defaultValue;
	}

	/**
	 * 字符串转换为float型
	 * 
	 * @param str
	 * @return
	 */
	public static float str2Float(String str) {
		try {
			return Float.parseFloat(str);
		} catch (NumberFormatException e) {
		}

		return 0.00f;
	}

	/**
	 * 字符串转换为float型
	 * 
	 * @param str
	 * @param defaultValue
	 * @return
	 */
	public static Float str2Float(String str, float defaultValue) {
		try {
			return Float.parseFloat(str);
		} catch (NumberFormatException e) {
		}

		return defaultValue;
	}

	/**
	 * 字符串转换为boolean型
	 * 
	 * @param str
	 * @return
	 */
	public static boolean str2Bool(String str) {
		if (null != str) {
			if ("TRUE".equals(str.trim().toUpperCase())) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 字符串转换为boolean型
	 * 
	 * @param str
	 * @param defaultValue
	 * @return
	 */
	public static Boolean str2Bool(String str, Boolean defaultValue) {
		if (true == str2Bool(str)) {
			return true;
		}

		return (null == defaultValue) ? false : defaultValue;
	}

	/**
	 * 校验String数组是否为空
	 * 
	 * @param array
	 * @return
	 */
	public static boolean isStringArrayEmpty(String[] array) {
		if ((null == array) || (0 >= array.length)) {
			return true;
		}

		boolean isEmpty = true;

		for (String str : array) {
			if (!isEmptyTrim(str)) {
				isEmpty = false;
				break;
			}
		}

		return isEmpty;
	}

	/**
	 * 校验是否所有参数均不为空
	 * 
	 * @param array
	 * @return
	 */
	public static boolean isExistEmpty(String... array) {
		for (String str : array) {
			if (isEmptyTrim(str)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 验证ip是否合法
	 * 
	 * @param ip
	 * @return
	 */
	public static boolean checkIp(String ip) {
		if (!isEmptyTrim(ip)) {
			// 定义正则表达式
			String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
					+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
					+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
					+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";

			// 判断ip地址是否与正则表达式匹配
			return ip.matches(regex);
		}

		// 返回判断信息
		return false;
	}

	/**
	 * 截取指定长度
	 * 
	 * @param str
	 * @param maxLen
	 * @return
	 */
	public static String cut2SpecialLength(String str, int maxLen) {
		return doCut2SpecialLength(str, maxLen, "");
	}

	/**
	 * 截取指定长度, 并在结尾追加指定的字串
	 * 
	 * @param str
	 * @param maxLen
	 * @param fill2TailStr
	 */
	public static String cut2SpecialLength(String str, int maxLen,
			String fill2TailStr) {
		int iTailLength = fill2TailStr.length();
		int iMaxLengthOffset = maxLen - iTailLength - 3;

		if (1 >= iMaxLengthOffset) {
			iMaxLengthOffset = maxLen;
		}

		return doCut2SpecialLength(str, iMaxLengthOffset, fill2TailStr);
	}

	/**
	 * 生成一个唯一id
	 * 
	 * @param key
	 * @return
	 */
	public static String randomUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 连接字符串
	 * 
	 * @param strArray
	 * @return
	 */
	public static String catString(String... strArray) {
		if (null == strArray) {
			return "";
		}

		StringBuilder strBuilder = new StringBuilder();

		for (String str : strArray) {
			strBuilder.append(str);
		}

		return strBuilder.toString();
	}
	
	/**
	 * 转换为json结构
	 * 
	 * @param obj
	 * @return
	 */
	public static String change2Json(Object obj) {
		return JSON.toJSONStringWithDateFormat(obj, "yyyy-MM-dd HH:mm:ss",
				SerializerFeature.WriteDateUseDateFormat);
	}
	
	/**
	 * 截取指定长度
	 * 
	 * @param str
	 * @param maxLen
	 * @return
	 */
	private static String doCut2SpecialLength(String str, int maxLen, String fill2TailStr) {
		if (isEmpty(str)) {
			return "";
		}

		if (maxLen < str.length()) {
			return str.substring(0, maxLen) + fill2TailStr;
		}

		return str;
	}
	
	/**
	 * 拼接为字符串
	 * 
	 * @param strs
	 * @return
	 */
	public static String joinString(String... strs) {
		StringBuilder temp = new StringBuilder();

		for (String str : strs) {
			if(null != str) {
				temp.append(str);
			}
		}

		return temp.toString();
	}
}