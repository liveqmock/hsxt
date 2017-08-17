/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gp.common.utils;

import org.apache.commons.lang3.StringUtils;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-service
 * 
 *  Package Name    : com.gy.hsxt.gp.common.utils
 * 
 *  File Name       : StringFormatHelper.java
 * 
 *  Creation Date   : 2015-09-25
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 字符串格式帮助类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class StringFormatHelper {

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
}
