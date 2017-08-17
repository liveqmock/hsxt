/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gp.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-service
 * 
 *  Package Name    : com.gy.hsxt.gp.common.utils
 * 
 *  File Name       : NumbericUtils.java
 * 
 *  Creation Date   : 2015-10-21
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class NumbericUtils {
	
	/**
	 * 判断是否为数字
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isNumeric(String s) {
		if(StringUtils.isEmpty(s)) {
			return false;
		}
		
		Pattern pattern = Pattern
				.compile("^(([1-9]{1}\\d*)|([0]{1}))+(.[0-9]*)?$");
		Matcher isNum = pattern.matcher(s);
		if (!isNum.matches()) {
			return false;
		}

		return true;
	}

	/**
	 * 判断字符串是否是整数
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isInteger(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * 判断字符串是否是浮点数
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isDouble(String value) {
		try {
			Double.parseDouble(value);
			if (value.contains("."))
				return true;
			return false;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(isNumeric("10.00001"));
		System.out.println(isNumeric("0.9"));
	}
}
