/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.common.utils;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.common.utils
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
	 * 是否存在空值
	 * 
	 * @param params
	 * @return
	 */
	public static boolean hasNull(Object[] params) {
		for (Object obj : params) {
			if (isEmpty(obj)) {
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
	 * 字符串转换为int型
	 * 
	 * @param value
	 * @return
	 */
	public static String int2Str(Integer value) {
		if (null != value) {
			return value.toString();
		}

		return "";
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
	 * 校验是否所有参数均为空
	 * 
	 * @param array
	 * @return
	 */
	public static boolean isAllEmpty(String... array) {
		boolean flag = true;

		for (String str : array) {
			if (!isEmptyTrim(str)) {
				flag = false;

				break;
			}
		}

		return flag;
	}

	/**
	 * 校验是否所有参数均不为空
	 * 
	 * @param array
	 * @return
	 */
	public static boolean isAllNotEmpty(String... array) {
		boolean flag = true;

		for (String str : array) {
			if (isEmptyTrim(str)) {
				flag = false;

				break;
			}
		}

		return flag;
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
	 * 对象转换为json字串
	 * 
	 * @param obj
	 * @return
	 */
	public static String object2Json(Object obj) {

		if (null != obj) {
			try {
				JSONObject json = (JSONObject) JSONObject.toJSON(obj);

				return json.toJSONString();
			} catch (Exception e) {
			}
		}

		return "";
	}

	/**
	 * 转换为json结构
	 * 
	 * @param obj
	 * @return
	 */
	public static String change2Json(Object obj) {

		if (null != obj) {
			return JSON.toJSONStringWithDateFormat(obj, "yyyy-MM-dd HH:mm:ss",
					SerializerFeature.WriteDateUseDateFormat);
		}

		return "";
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
			return "";
		}

		if (maxLen < str.length()) {
			return str.substring(0, maxLen);
		}

		return str;
	}

	/**
	 * 字符串list转为字符串
	 * 
	 * @param stringList
	 * @return
	 */
	public static String listToString(List<String> stringList) {
		if (stringList == null) {
			return null;
		}
		
		boolean flag = false;

		StringBuilder result = new StringBuilder();
		
		for (String string : stringList) {
			
			if (flag) {
				result.append(",");
			} else {
				flag = true;
			}
			
			result.append(string);
		}
		
		return result.toString();
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
			if(null != str) {
				temp.append(str);
			}
		}

		return temp.toString();
	}
	
	/**
	 * 比较两个字符串是否相等, 去掉前后空格
	 * 
	 * @param value1
	 * @param value2
	 * @return
	 */
	public static boolean compareTrim(String value1, String value2) {
		return avoidNullTrim(value1).equals(avoidNullTrim(value2));
	}
}