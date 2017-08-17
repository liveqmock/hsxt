/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.uf.common.utils.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-service
 * 
 *  Package Name    : com.gy.hsxt.uf.common.utils.security
 * 
 *  File Name       : MD5Utils.java
 * 
 *  Creation Date   : 2014-6-13
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : MD5工具类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public final class MD5Utils {
	/**
	 * 私有构造函数
	 */
	private MD5Utils() {
	}

	/**
	 * 进行MD5加密
	 * 
	 * @param str
	 * @return
	 */
	public static String getMD5Value(String str) {
		if (null == str || str.isEmpty()) {
			return "";
		}

		String result = null;
		MessageDigest messageDigest = null;

		byte[] bytes = new byte[0];

		try {
			bytes = str.trim().getBytes("UTF-8");

			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(bytes);
			bytes = messageDigest.digest();
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			// e.printStackTrace();
		} finally {
			messageDigest = null;
		}

		result = getMD5Value(bytes);

		return result;
	}

	/**
	 * 进行MD5加密
	 * 
	 * @param bytes
	 * @return
	 */
	private static String getMD5Value(byte[] bytes) {
		StringBuilder md5 = new StringBuilder(50);

		for (int i = 0; i < bytes.length; i++) {
			if (1 == Integer.toHexString(0xFF & bytes[i]).length()) {
				md5.append("0").append(Integer.toHexString(0xFF & bytes[i]));
			} else {
				md5.append(Integer.toHexString(0xFF & bytes[i]));
			}
		}

		return md5.toString();
	}
}