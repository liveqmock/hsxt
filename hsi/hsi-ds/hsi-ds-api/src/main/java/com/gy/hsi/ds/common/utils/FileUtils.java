/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.ds.common.utils;

import java.io.File;
import java.util.List;

public class FileUtils {

	/**
	 * 删除文件
	 * 
	 * @param file
	 */
	public static void deleteFile(File file) {
		try {
			if (null != file) {
				file.delete();
			}
		} catch (Exception e) {
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param file
	 */
	public static void deleteFiles(List<File> files) {
		if (null == files) {
			return;
		}

		for (File f : files) {
			deleteFile(f);
		}
	}

	/**
	 * 根据user.dir指向的路径创建文件保存的临时路径
	 * 
	 * @param subDir
	 * @return
	 */
	public static String assembleFilePathByUserDir(String subDir) {
		String userDir = System.getProperty("user.dir");

		if (StringUtils.isEmpty(userDir)) {
			return subDir;
		}

		String path = "";

		if (userDir.endsWith("/")) {
			path = userDir + subDir;
		} else {
			path = userDir + "/" + subDir;
		}

		if (path.contains("/./") || path.contains("//")) {
			path = path.replace("/./", "/").replaceAll("/{2,}", "/");
		}

		return path;
	}
	
	public static void main(String[] args) {
		System.out.println(assembleFilePathByUserDir("./zhang"));
		
		System.setProperty("user.dir", "/opt/folder1");
		System.out.println(assembleFilePathByUserDir("./zhang"));
		
		System.setProperty("user.dir", "/opt/folder1///");
		System.out.println(assembleFilePathByUserDir("./zhang"));
		
		System.setProperty("user.dir", "");
		System.out.println(assembleFilePathByUserDir("./zhang"));
	}
}
