/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.common.utils
 * 
 *  File Name       : ChineseCodeHelper.java
 * 
 *  Creation Date   : 2016年5月28日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 中文编码工具类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class ChineseCodeHelper {

	/**
	 * 判断是否为中文字符
	 * 
	 * @param c
	 * @return
	 */
	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);

		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}

		return false;
	}

	/**
	 * 判断是为乱码
	 * 
	 * @param strName
	 * @return
	 */
	public static boolean isNormalEncoding(String strName) {

		Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");
		Matcher m = p.matcher(strName);

		String after = m.replaceAll("");
		String temp = after.replaceAll("\\p{P}", "");

		char[] ch = temp.trim().toCharArray();

		float chLength = ch.length;
		float count = 0;

		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];

			if (!Character.isLetterOrDigit(c)) {
				if (!isChinese(c)) {
					count = count + 1;
				}
			}
		}

		float result = count / chLength;

		if (result > 0.4) {
			return false;
		}

		return true;
	}
	
	public static void main(String[] args) {
		System.out.println(isNormalEncoding("[来自银行提示] E00007 ���������쳣[������]"));  
	    System.out.println(isNormalEncoding("haha"));  
	}
}
