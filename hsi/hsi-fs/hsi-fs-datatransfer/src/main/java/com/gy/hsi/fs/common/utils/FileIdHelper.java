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
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.gy.hsi.fs.common.exception.FsParameterException;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-server
 * 
 *  Package Name    : com.gy.hsi.fs.server.common.utils
 * 
 *  File Name       : FileIdHelper.java
 * 
 *  Creation Date   : 2015年5月22日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : FS文件id工具类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class FileIdHelper {

	/** 生成UUID时使用的计数器 **/
	private static long uuidCounts = 0;

	/** 随机数对象 **/
	private static final Random M_RANDOM = new Random();
	private static final Random N_RANDOM = new Random();
	private static final Random RANDOM = new Random();

	/** 特征符索引 **/
	/** 文件id前置 **/
	public static final int PREFIX_LEN = CommonFileIdUtils.PREFIX_LEN;
	private static final int ROUTER_FLAG_LEN = CommonFileIdUtils.ROUTER_FLAG_LEN;
	private static final int FILE_TYPE_FLAG_INDEX = CommonFileIdUtils.FILE_TYPE_FLAG_INDEX;
	private static final int FILE_ID_LEN_INDEX = CommonFileIdUtils.FILE_ID_LEN_INDEX;

	/** 实名上传文件id特征码 **/
	private static final char CHAR_SECURE_FILE_FLAG = CommonFileIdUtils.CHAR_SECURE_FILE_FLAG;

	/** 匿名上传文件id特征码 **/
	private static final char CHAR_ANONY_FILE_FLAG = CommonFileIdUtils.CHAR_ANONY_FILE_FLAG;

	/** 文件id前置 **/
	private static final char CHAR_PREFIX_FLAG = CommonFileIdUtils.CHAR_PREFIX_FLAG;

	/** 字母表 **/
	private static final char[] CHARS = new char[] { 'a', 'b', 'c', 'd', 'e',
			'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
			's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E',
			'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
			'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4',
			'5', '6', '7', '8', '9' };

	/**
	 * 生成受限权限文件id：protected、private
	 *
	 * @return
	 */
	public static String generateSecureFileId() {
		String fileId = generateUniqueId(CHAR_SECURE_FILE_FLAG);

		String routerFlag = getRouterFlag();
		String prefix = String.valueOf(CHAR_PREFIX_FLAG).trim();

		StringBuilder uniqueIdBld = new StringBuilder();
		uniqueIdBld.append(prefix);
		uniqueIdBld.append(routerFlag).append(fileId);
		uniqueIdBld.insert(FILE_TYPE_FLAG_INDEX, CHAR_SECURE_FILE_FLAG);

		int fileIdLen = uniqueIdBld.length() + 1;
		uniqueIdBld.insert(FILE_ID_LEN_INDEX,
				CommonFileIdUtils.int2char(fileIdLen));

		return uniqueIdBld.toString();
	}

	/**
	 * 生成公开权限文件id[仅针对匿名上传的]
	 * 
	 * @param fileStoreId
	 * @return
	 */
	public static String generateAnonyFileId(String fileStoreId) {
		String routerFlag = getRouterFlag();
		String prefix = String.valueOf(CHAR_PREFIX_FLAG).trim();

		StringBuilder uniqueIdBld = new StringBuilder();
		uniqueIdBld.append(fileStoreId);
		uniqueIdBld.reverse().insert(0, routerFlag);

		if (!StringUtils.isEmpty(prefix)) {
			uniqueIdBld.insert(0, prefix);
		}

		uniqueIdBld.insert(FILE_TYPE_FLAG_INDEX, CHAR_ANONY_FILE_FLAG);

		int fileIdLen = uniqueIdBld.length() + 1;
		uniqueIdBld.insert(FILE_ID_LEN_INDEX,
				CommonFileIdUtils.int2char(fileIdLen));

		return uniqueIdBld.toString();
	}

	/**
	 * 判断是否为公共资源文件
	 * 
	 * @param fileId
	 * @return
	 * @throws FsParameterException
	 */
	public static boolean isAnonyFileId(String fileId)
			throws FsParameterException {

		if (!checkFileId(fileId)) {
			throw new FsParameterException("This file id is illegal! ");
		}

		return CommonFileIdUtils.isAnonyUploadedFile(fileId);
	}

	/**
	 * 判断是否为受限资源文件
	 * 
	 * @param fileId
	 * @return
	 * @throws FsParameterException
	 */
	public static boolean isSecurityFileId(String fileId)
			throws FsParameterException {

		if (!checkFileId(fileId)) {
			throw new FsParameterException("This file id is illegal! ");
		}

		return (CHAR_SECURE_FILE_FLAG == fileId.charAt(FILE_TYPE_FLAG_INDEX));
	}

	/**
	 * 解析出公共权限文件id
	 * 
	 * @param fileId
	 * @return
	 * @throws FsParameterException
	 */
	public static String parseAnonyFileStoreId(String fileId)
			throws FsParameterException {

		StringBuilder uniqueIdBld = new StringBuilder(fileId);
		uniqueIdBld.replace(FILE_ID_LEN_INDEX, FILE_ID_LEN_INDEX + 1, "");// 先前
		uniqueIdBld.replace(FILE_TYPE_FLAG_INDEX, FILE_TYPE_FLAG_INDEX + 1, "");// 再后
		uniqueIdBld.replace(0, PREFIX_LEN + ROUTER_FLAG_LEN, "");

		return uniqueIdBld.reverse().toString();
	}

	/**
	 * 校验文件id是否合法
	 * 
	 * @param fileId
	 * @return
	 */
	public static boolean checkFileId(String fileId) {

		if (StringUtils.isEmpty(fileId)
				|| (FILE_ID_LEN_INDEX > fileId.length() - 1)) {
			return false;
		}

		char fileTypeFlag = fileId.charAt(FILE_TYPE_FLAG_INDEX);

		if ((CHAR_SECURE_FILE_FLAG == fileTypeFlag)
				|| (CHAR_ANONY_FILE_FLAG == fileTypeFlag)) {
			int fileIdLength = CommonFileIdUtils.char2int(fileId
					.charAt(FILE_ID_LEN_INDEX));

			if (fileId.length() == fileIdLength) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 生成100年都难以重复的UUID :)
	 * 
	 * @param key
	 * @return
	 */
	public static String generateUniqueId(Object key) {
		if (9223372036854775L <= uuidCounts) {
			uuidCounts = 0;
		}

		int idMaxLen = 23;
		int offsetLen = 32 - idMaxLen - 5;
		int mOffsetLen = idMaxLen - 5;

		if (0 >= offsetLen) {
			offsetLen = 0;
		}

		int offset = RANDOM.nextInt(offsetLen);

		StringBuilder strBld = new StringBuilder();
		strBld.append(++uuidCounts);
		strBld.append(UUID.randomUUID());
		strBld.append(RANDOM.nextDouble());
		strBld.append(System.currentTimeMillis());
		strBld.append(UUID.randomUUID());
		strBld.append(RANDOM.nextDouble());
		strBld.append(key).append(RANDOM.nextInt());

		UUID uuid = UUID.nameUUIDFromBytes(strBld.toString().getBytes());

		String strUUID = uuid.toString().replaceAll("-", "")
				.substring(offset, offset + idMaxLen);
		StringBuilder resultStrBuild = new StringBuilder(strUUID).append(
				CHARS[N_RANDOM.nextInt(CHARS.length)]).append(
				CHARS[N_RANDOM.nextInt(CHARS.length - 10)]);

		String replacer;
		int random;
		int counts = 0;

		for (int i = 0; i < idMaxLen; i++) {
			random = M_RANDOM.nextInt(2) + M_RANDOM.nextInt(mOffsetLen)
					+ M_RANDOM.nextInt(3);

			replacer = String.valueOf(resultStrBuild.charAt(random))
					.toUpperCase();

			if (replacer.matches("\\d{1,}")) {
				continue;
			}

			counts++;

			if (5 >= counts) {
				resultStrBuild.replace(random, random + 1, replacer);
			}
		}

		return resultStrBuild.toString();
	}

	/**
	 * 从url解析出文件id和后缀
	 * 
	 * @param request
	 * @return [文件id][文件后缀]
	 */
	public static String[] parseFileIdAndSuffix(HttpServletRequest request) {
		// 请求地址
		String fullUrl = request.getRequestURL().toString();
		fullUrl = fullUrl.replaceFirst("\\?.*", "");
		fullUrl = fullUrl.replaceFirst("/$", "");
		fullUrl = fullUrl.replaceFirst("(.*\\/)", "");
		fullUrl = fullUrl.replaceFirst("(.*\\\\)", "");

		return fullUrl.split("\\.");
	}
	
	public static String formatFileId(String fileId) {

		if (StringUtils.isEmpty(fileId)) {
			return "";
		}

		byte[] bytes;
		long sum = 0;

		StringBuilder buffer = new StringBuilder("#");

		try {
			bytes = fileId.getBytes("UTF-8");

			for (byte b : bytes) {
				sum += b;
			}

			buffer.append(sum);

			bytes = fileId.substring(7, 10).getBytes("UTF-8");

			for (byte b : bytes) {
				buffer.append(b);
			}
		} catch (UnsupportedEncodingException e) {
		}

		return fileId + buffer.toString();
	}
	
	public static String unformatFileId(String formatedFileId) {
		
		if (StringUtils.isEmpty(formatedFileId)) {
			return "";
		}
		
		int endIndex = formatedFileId.lastIndexOf("#");
		
		return formatedFileId.substring(0, endIndex);
	}

	/**
	 * 获取路由标志位
	 * 
	 * @return
	 */
	private static String getRouterFlag() {
		return "00";
	}

	public static void main(String[] args) throws FsParameterException {

//		for (int i = 0; i < 10000; i++) {
//			String anonyFileId = generateAnonyFileId("T1GFxTBX_T1RCvBVdK.jpg");
//			System.out.println(anonyFileId);
//			System.out.println(isAnonyFileId(anonyFileId));
//
//			String secureFileId = generateSecureFileId();
//
//			System.out.println(secureFileId);
//			System.out.println(isSecurityFileId(secureFileId));
//		}
//
//		System.out.println(parseAnonyFileStoreId("00AHKdVBvCR1T_XBTxFG1T"));
//
//		System.out.println(isAnonyFileId("00AHKdVBvCR1T_XBTxFG1T"));
//		System.out.println(isSecurityFileId("00SNb2d84eCE9C394d9A5769cfvZ"));
		
//		System.out.println(formatFileId("LLLKLLKKLdddd"));
//		System.out.println(unformatFileId(formatFileId("LLLKLLKKLdddd")));
		
		System.out.println(formatFileId("T19aJTByCv1R4cSCrK"));
	}
}
