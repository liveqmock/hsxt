/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Simple to Introduction
 * 
 * @category          Simple to Introduction
 * @projectName       hsxt-pg-service
 * @package           com.gy.hsxt.pg.common.utils.NumbericUtils.java
 * @className         NumbericUtils
 * @description       描述该类的功能 
 * @author            zhangysh
 * @createDate        2015-10-29 下午2:22:29  
 * @version              v0.0.1
 */
public class NumbericUtils {
	public static boolean isNumeric(String s) {
		if(StringUtils.isEmpty(s)) {
			return false;
		}
		
		Pattern pattern = Pattern
				.compile("^(([1-9]{1}\\d*)|([0]{1}))+(.[0-9]*)?$");
		Matcher isNum = pattern.matcher(s);
		if (!isNum.matches()) {
			return false;
		} else {
			return true;
		}
	}

	public static void main(String[] args) {
		System.out.println(isNumeric("10.00001"));
		System.out.println(isNumeric("0.00000"));
		System.out.println(isNumeric(" "));
	}
}
