/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-common
 * 
 *  Package Name    : com.gy.hsi.fs.common.utils
 * 
 *  File Name       : FileMD5Utils.java
 * 
 *  Creation Date   : 2015年5月21日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 文件MD5工具
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/

@SuppressWarnings("unused")
public class FileMD5Utils {
	/** 记录日志对象 **/
	protected static final Logger logger = Logger.getLogger(FileMD5Utils.class);

	private static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	private static MessageDigest messagedigest = null;

	static {
		try {
			messagedigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			logger.error("MD5FileUtil messagedigest初始化失败", e);
		}
	}
}
