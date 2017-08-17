/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.server.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.gy.hsi.fs.common.constant.FsConstant;
import com.gy.hsi.fs.common.exception.FsException;
import com.gy.hsi.fs.common.utils.StringUtils;
import com.gy.hsi.fs.server.common.constant.FsServerConstant;

/***************************************************************************
 * <PRE>
 *  Project Name    : fs-server
 * 
 *  Package Name    : com.gy.hsi.fs.server.common.utils
 * 
 *  File Name       : FileCacheHelper.java
 * 
 *  Creation Date   : 2015年5月23日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 文件上传和下载缓存操作工具类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class FileCacheHelper {
	/** 记录日志对象 **/
	private static final Logger logger = Logger.getLogger(FileCacheHelper.class);

	/** 缓存到本地的文件夹数量 **/
	private static final int TOTAL_SUBFOLDER_COUNTS = 1000;

	/**
	 * 创建缓存目录
	 */
	public static void createCacheDirectory() {
		String[] cacheDirs = new String[] { FsServerConstant.UPLOAD_FILE_TEMPCACHE_ROOT_DIR };

		File file;

		for (String cacheDir : cacheDirs) {
			file = new File(cacheDir);

			if (!file.exists()) {
				file.mkdirs();
			}
		}

		for (String cacheDir : cacheDirs) {
			for (int index = 0; TOTAL_SUBFOLDER_COUNTS > index; index++) {
				file = new File(cacheDir + index);

				if (!file.exists()) {
					file.mkdirs();
				}
			}
		}
	}

	/**
	 * 获得文件上传的缓存目录
	 * 
	 * @param fileName
	 * @param suffix
	 * @return
	 */
	public static String getFileUploadCacheDirectory(String fileName,
			String suffix) {
		int subFolderIndex = ThreadLocalRandom.current().nextInt(
				TOTAL_SUBFOLDER_COUNTS);

		return getFileCacheDirectory(
				FsServerConstant.UPLOAD_FILE_TEMPCACHE_ROOT_DIR,
				subFolderIndex, fileName, suffix);
	}

	/**
	 * 将文件流输出到HttpReponse中(实现效率较低, 废弃)
	 * 
	 * @param filePath
	 * @param response
	 * @param customFileName
	 * @throws FsException
	 */
	@Deprecated
	public static void outputFileStream2Reponse(String filePath,
			HttpServletResponse response, String customFileName)
			throws FsException {
		OutputStream outputStream = null;
		InputStream inputStream = null;

		try {
			response.setCharacterEncoding(FsConstant.ENCODING_UTF8);

			// 流能够自动识别文件的类型
			response.setContentType("application/octet-stream");

			if (!StringUtils.isEmpty(customFileName)) {
				response.addHeader("Content-Disposition",
						"attachment;filename=" + customFileName);
			}

			outputStream = response.getOutputStream();
			inputStream = new FileInputStream(filePath);

			// 通过控制一次读取文件的长度 来避免一次性将文件全部读出来造成性能的损耗
			byte[] byteData = new byte[10240];
			int num;

			while ((num = inputStream.read(byteData)) != -1) {
				outputStream.write(byteData, 0, num);
			}
		} catch (IOException ex) {
			String errorMsg = "File service system failed to handle reponse file stream !!";

			logger.error(errorMsg);

			throw new FsException(errorMsg, ex);
		} finally {
			if (null != inputStream) {
				try {
					inputStream.close();
				} catch (IOException e) {
				}
			}

			if (null != outputStream) {
				try {
					outputStream.close();
				} catch (IOException e) {
				}
			}
		}
	}

	/**
	 * 将文件流输出到HttpReponse中(实现效率较低, 废弃)
	 * 
	 * @param filePath
	 * @param response
	 * @param customFileName
	 * @throws FsException
	 */
	@Deprecated
	public static void outputFileStream2Reponse2(String filePath,
			HttpServletResponse response, String customFileName)
			throws FsException {
		OutputStream outputStream = null;
		InputStream inputStream = null;

		try {
			response.setCharacterEncoding(FsConstant.ENCODING_UTF8);

			// 流能够自动识别文件的类型
			response.setContentType("application/octet-stream");

			if (!StringUtils.isEmpty(customFileName)) {
				response.addHeader("Content-Disposition",
						"attachment;filename=" + customFileName);
			}

			outputStream = response.getOutputStream();
			inputStream = new FileInputStream(filePath);

			// 通过控制一次读取文件的长度 来避免一次性将文件全部读出来造成性能的损耗
			byte[] byteData = new byte[10240];
			int num;

			while ((num = inputStream.read(byteData)) != -1) {
				outputStream.write(byteData, 0, num);
			}
		} catch (IOException ex) {
			String errorMsg = "File service system failed to handle reponse file stream !!";

			logger.error(errorMsg);

			throw new FsException(errorMsg, ex);
		} finally {
			if (null != inputStream) {
				try {
					inputStream.close();
				} catch (IOException e) {
				}
			}

			if (null != outputStream) {
				try {
					outputStream.close();
				} catch (IOException e) {
				}
			}
		}
	}

	/**
	 * 获得文件的缓存目录
	 * 
	 * @param rootPath
	 * @param subFolderIndex
	 * @param fileName
	 * @param suffix
	 * @return
	 */
	private static String getFileCacheDirectory(String rootPath,
			int subFolderIndex, String fileName, String suffix) {

		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(rootPath);
		strBuilder.append(subFolderIndex);
		strBuilder.append(fileName);

		if (!StringUtils.isEmpty(suffix)) {
			strBuilder.append(".");
			strBuilder.append(suffix);
		}

		return strBuilder.toString();
	}
}
