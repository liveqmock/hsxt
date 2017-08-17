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
 *  Project Name    : hsi-fs-common
 * 
 *  Package Name    : com.gy.hsi.fs.common.constant
 * 
 *  File Name       : CommonFileIdUtils.java
 * 
 *  Creation Date   : 2015年7月16日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 文件id工具类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class CommonFileIdUtils {
	
	/** 文件id前置 **/
	public static final char CHAR_PREFIX_FLAG = 'F';

	/** 特征符索引 **/
	public static final int PREFIX_LEN = String.valueOf(CHAR_PREFIX_FLAG)
			.trim().length();
	public static final int ROUTER_FLAG_LEN = 2;
	public static final int FILE_TYPE_FLAG_INDEX = PREFIX_LEN + ROUTER_FLAG_LEN + 0;
	public static final int FILE_ID_LEN_INDEX = PREFIX_LEN + ROUTER_FLAG_LEN + 1;

	/** 实名上传文件id特征码 **/
	public static final char CHAR_SECURE_FILE_FLAG = 'S';

	/** 匿名上传文件id特征码 **/
	public static final char CHAR_ANONY_FILE_FLAG = 'A';
	
	/** 偏移量 **/
	public static final int OFFSET_INT = 80;

	/**
	 * 判断是否为匿名公共资源文件
	 * 
	 * @param fileId
	 * @return
	 */
	public static boolean isAnonyUploadedFile(String fileId) {
		return (CHAR_ANONY_FILE_FLAG == fileId.charAt(FILE_TYPE_FLAG_INDEX));
	}

	/**
	 * 抽取出文件id和后缀
	 * 
	 * @param fileIdAndSuffix
	 * @return
	 */
	public static String[] extractFileIdAndSuffix(String fileIdAndSuffix) {
		
		//////////////////////////临时兼容代码//////////////////////
		if (!StringUtils.isEmpty(fileIdAndSuffix)
				&& (!fileIdAndSuffix.startsWith("" + CHAR_PREFIX_FLAG))) {
			return OldCommonFileIdUtils.extractFileIdAndSuffix(fileIdAndSuffix);
		}
		////////////////////////////////////////////////////////
		
		String fileId = "";
		String fileSuffix = "";

		try {
			if (null != fileIdAndSuffix) {
				fileId = fileIdAndSuffix;

				int fileIdLength = char2int(fileIdAndSuffix
						.charAt(FILE_ID_LEN_INDEX));

				if (fileIdAndSuffix.length() > fileIdLength) {
					fileId = fileIdAndSuffix.substring(0, fileIdLength);
					fileSuffix = fileIdAndSuffix.substring(fileIdLength);
				}
			}
		} catch (IndexOutOfBoundsException e) {
		}

		return new String[] { fileId, fileSuffix };
	}

	/**
	 * char to int
	 * 
	 * @param ch
	 * @return
	 */
	public static int char2int(char ch) {
		return ((int) ch) - OFFSET_INT;
	}

	/**
	 * int to char
	 * 
	 * @param intValue
	 * @return
	 */
	public static char int2char(int intValue) {
		return (char) (intValue + OFFSET_INT);
	}

	public static void main(String[] args) {
		String[] strs = extractFileIdAndSuffix("F00AIKdVBvCR1T_XBTxFG1T.jpg");

		System.out.println(strs[0] + ", " + strs[1]);
	}
}
