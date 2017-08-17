/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.common.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.common.utils
 * 
 *  File Name       : FileDownloadUtils.java
 * 
 *  Creation Date   : 2015-09-19
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 网络文件下载工具类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/

public class FileDownloadUtils {
	
	/** 缓冲区大小 **/
	private static final int BUFFER_SIZE = 8096;

	/** 最大迭代次数 **/
	private static final int MAX_LOOP_COUNTS = 5;

	/**
	 * 构造方法 　
	 */
	private FileDownloadUtils() {
	}

	/**
	 * 将HTTP资源另存为文件
	 * 
	 * @param remoteUrl
	 * @param localFilePath
	 * @throws Exception
	 */
	public static boolean download(String remoteUrl, String localFilePath)
			throws Exception {
		byte[] buf = new byte[BUFFER_SIZE];
		int size = 0;

		// http连接对象
		HttpURLConnection httpUrl = null;

		// 获取网络输入流
		BufferedInputStream bis = null;

		// 建立文件
		FileOutputStream fos = null;

		try {
			// 建立链接
			URL url = new URL(remoteUrl);
			httpUrl = (HttpURLConnection) url.openConnection();

			// 连接指定的资源
			httpUrl.connect();

			// 创建目录
			createDirectoryIfNotExist(localFilePath);

			bis = new BufferedInputStream(httpUrl.getInputStream());
			fos = new FileOutputStream(localFilePath);

			// 保存文件
			while (-1 != (size = bis.read(buf))) {
				fos.write(buf, 0, size);
			}

			return checkFileExist(localFilePath, 0);
		} finally {
			try {
				if (null != fos) {
					fos.close();
				}

				if (null != bis) {
					bis.close();
				}

				if (null != httpUrl) {
					httpUrl.disconnect();
				}
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @param localFilePath
	 * @return
	 */
	public static boolean isFileExist(String localFilePath) {
		return checkFileExist(localFilePath, MAX_LOOP_COUNTS);
	}

	/**
	 * 创建目录
	 * 
	 * @param localFilePath
	 */
	private static void createDirectoryIfNotExist(String localFilePath) {
		File file = new File(localFilePath);
		File directory = new File(file.getParent());

		if (!directory.exists()) {
			directory.mkdirs();
		}
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @param localFilePath
	 * @param loopCounts
	 * @return
	 */
	private static boolean checkFileExist(String localFilePath, int loopCounts) {
		File downloadFile = new File(localFilePath);

		if (downloadFile.isFile() && downloadFile.exists()
				&& (0 < downloadFile.length())) {
			return true;
		}

		supendAmoment(1500);

		// 最大迭代5次
		if (MAX_LOOP_COUNTS <= loopCounts) {
			return false;
		}

		return checkFileExist(localFilePath, loopCounts++);
	}

	/**
	 * 悬起一会
	 * 
	 * @param timeMills
	 */
	private static void supendAmoment(int timeMills) {
		try {
			Thread.sleep(timeMills);
		} catch (InterruptedException e) {
		}
	}

	public static void main(String argv[]) {
		try {
			FileDownloadUtils.download(
					"http://www.hsxt.net/image/logo-big.png",
					"C:/zhangpic/logo-big.png");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
