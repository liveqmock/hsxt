/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.common.utils;


/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-uf-service
 * 
 *  Package Name    : com.gy.hsxt.uf.common.utils
 * 
 *  File Name       : StringUtils.java
 * 
 *  Creation Date   : 2015-9-26
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
	 * @param defaultValue
	 * @return
	 */
	public static Integer str2Int(String str, Integer defaultValue) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
		}

		return defaultValue;
	}

	/**
	 * 字符串转换为long型
	 * 
	 * @param str
	 * @param defaultValue
	 * @return
	 */
	public static String long2Str(Long value, long defaultValue) {
		if (null == value) {
			return String.valueOf(defaultValue);
		}

		return value.toString();
	}

	/**
	 * 字符串转换为boolean型
	 * 
	 * @param str
	 * @return
	 */
	public static boolean str2Bool(String str) {
		if (isEmpty(str) || "TRUE".equals(str.trim().toUpperCase())) {
			return true;
		}

		return false;
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

		for (String str : array) {
			if (!isEmptyTrim(str)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 校验是否所有参数均为空
	 * 
	 * @param array
	 * @return
	 */
	public static boolean isAllEmpty(String... array) {
		for (String str : array) {
			if (!isEmptyTrim(str)) {
				return false;
			}
		}

		return true;
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
		if (isEmpty(str)) {
			return str;
		}

		if (maxLen < str.length()) {
			return str.substring(0, maxLen);
		}

		return str;
	}
	
	/**
	 * 判断结尾的/
	 * 
	 * @param rootPath
	 * @param fileName
	 * @return
	 */
	public static String joinFilePath(String rootPath, String fileName) {
		
		if (!rootPath.endsWith("/")) {
			rootPath += "/";
		}

		return rootPath += fileName;
	}
}