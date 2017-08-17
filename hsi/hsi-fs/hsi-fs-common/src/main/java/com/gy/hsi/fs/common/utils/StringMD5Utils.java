/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.gy.hsi.fs.common.constant.FsConstant;
import com.gy.hsi.fs.common.utils.StringUtils;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-common
 * 
 *  Package Name    : com.gy.hsi.fs.common.utils
 * 
 *  File Name       : StringMD5Utils.java
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
public final class StringMD5Utils {
	/**
	 * 私有构造函数
	 */
	private StringMD5Utils() {
	}

	/**
	 * 进行MD5加密
	 * 
	 * @param str
	 * @return
	 */
	public static String getMD5Value(String str) {
		if (StringUtils.isEmptyTrim(str)) {
			return "";
		}

		String result = null;
		MessageDigest messageDigest = null;

		byte[] bytes = new byte[0];

		try {
			bytes = str.trim().getBytes(FsConstant.ENCODING_UTF8);

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