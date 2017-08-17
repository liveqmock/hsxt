package com.gy.hsxt.pg.bankadapter.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

public class StringHelper {

	private static Random random = new Random();

	// 数字格式
	public static final DecimalFormat DECIMAL_FORMAT_0_00 = new DecimalFormat(
			"##########0.00");

	// 便于日期的计算
	public static long MILL_OF_DAY = 24 * 60 * 60 * 1000;

	public static SimpleDateFormat getYYYYMMddDateFormat() {
		return new SimpleDateFormat("yyyyMMdd");
	}

	public static SimpleDateFormat getHHmmssDateFormat() {
		return new SimpleDateFormat("HHmmss");
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
	 * 存在空值
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
	public static int str2Int(String str, int defaultValue) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
		}

		return defaultValue;
	}

	/**
	 * 字符串转换为double型
	 * 
	 * @param str
	 * @param defaultValue
	 * @return
	 */
	public static double str2Double(String str, double defaultValue) {
		try {
			return Double.parseDouble(str);
		} catch (NumberFormatException e) {
		}

		return defaultValue;
	}

	/**
	 * 把null转换为""
	 * 
	 * @param value
	 * @return
	 */
	public static String avoidNull(Object value) {
		if (null == value) {
			return "";
		}

		return avoidNullTrim(value.toString());
	}

	/**
	 * 把null转换为""
	 * 
	 * @param value
	 * @return
	 */
	public static String avoidNull(String value) {
		return avoidNullTrim(value);
	}

	/**
	 * 把null转换为""
	 * 
	 * @param value
	 * @return
	 */
	public static String avoidNullTrim(String value) {
		if (null == value) {
			return "";
		}

		return value.trim();
	}

	/**
	 * 生成不重复的序列号
	 * 
	 * @param length
	 * @return
	 */
	public static String generateSequenceNo(int length) {
		String randomStr = (System.currentTimeMillis() + String.valueOf(random
				.nextLong())).replaceAll("[^\\d]+", "");

		return leftPad(randomStr, length, '0');
	}

	/**
	 * 解码
	 * 
	 * @param str
	 * @return
	 */
	public static String decodeUrlString(String str) {
		return decodeUrlString(str, null);
	}

	/**
	 * 解码
	 * 
	 * @param str
	 * @param encode
	 * @return
	 */
	public static String decodeUrlString(String str, String encode) {
		try {
			if (isEmpty(encode)) {
				encode = "UTF-8";
			}

			return URLDecoder.decode(str, encode);
		} catch (UnsupportedEncodingException e) {
			return str;
		}
	}

	/**
	 * 生成一个不重复的id
	 * 
	 * @param key
	 * @return
	 */
	public static String generateUUID(Object key) {
		Random random = new Random();
		StringBuilder strBld = new StringBuilder();
		strBld.append(System.currentTimeMillis()).append("$");
		strBld.append(random.nextDouble()).append("%");
		strBld.append(UUID.randomUUID()).append("*");
		strBld.append(key).append(random.nextInt());

		UUID uuid = UUID.nameUUIDFromBytes(strBld.toString().getBytes());

		String strRandomInt = String.valueOf(random.nextInt(10));

		return uuid.toString().replaceAll("-", strRandomInt).toUpperCase();
	}

	/**
	 * 右填充空格函数 对传入的字符串参数destStr，右补空格
	 * 
	 * @param destStr
	 * @param length
	 * @return
	 */
	public static String rightPad(String originalStr, int size) {
		if (originalStr == null) {
			originalStr = "";
		}
		if (size > 0 && originalStr.length() > size) {
			return originalStr.substring(0, size);
		}
		return StringUtils.rightPad(originalStr, size, ' ');
	}

	/**
	 * 右填充字符函数 对传入的字符串参数destStr，右补足length-destStr.length()个空格。
	 * 
	 * @param destStr
	 * @param length
	 * @return
	 */
	public static String rightPad(String originalStr, int size, char padChar) {
		if (originalStr == null) {
			originalStr = "";
		}
		if (size > 0 && originalStr.length() > size) {
			return originalStr.substring(0, size);
		}
		return StringUtils.rightPad(originalStr, size, padChar);
	}

	/**
	 * 左填充空格函数 对传入的字符串参数destStr，左补空格
	 * 
	 * @param destStr
	 * @param length
	 * @return
	 */
	public static String leftPad(String originalStr, int size) {
		if (originalStr == null) {
			originalStr = "";
		}
		if (size > 0 && originalStr.length() > size) {
			return originalStr.substring(0, size);
		}
		return StringUtils.leftPad(originalStr, size, ' ');
	}

	/**
	 * 左填充字符函数 对传入的字符串参数destStr
	 * 
	 * @param destStr
	 * @param length
	 * @return
	 */
	public static String leftPad(String originalStr, int size, char padChar) {
		if (originalStr == null) {
			originalStr = "";
		}

		if (size > 0 && originalStr.length() > size) {
			return originalStr.substring(0, size);
		}

		return StringUtils.leftPad(originalStr, size, padChar);
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
			if (null != str) {
				temp.append(str);
			}
		}

		return temp.toString();
	}
}
