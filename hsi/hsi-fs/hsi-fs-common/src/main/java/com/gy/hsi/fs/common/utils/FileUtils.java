/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.common.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.gy.hsi.fs.common.constant.FsConstant;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-common
 * 
 *  Package Name    : com.gy.hsi.fs.common.utils
 * 
 *  File Name       : FileUtils.java
 * 
 *  Creation Date   : 2015年6月1日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 文件操作工具类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class FileUtils {

	/**
	 * 根据字节获得文件, 记得用完要及时删除
	 * 
	 * @param bfile
	 * @return
	 */
	public static File getTempFile(byte[] bfile) {

		File rootPath = new File(FsConstant.TEMP_FILE_ROOT_PATH);

		if (!rootPath.exists()) {
			rootPath.mkdirs();
		}

		return getFile(bfile, rootPath.getAbsolutePath(),
				StringUtils.randomUUID());
	}

	/**
	 * 根据字节获得文件
	 * 
	 * @param bfile
	 * @param filePath
	 * @param fileName
	 * @return
	 */
	public static File getFile(byte[] bfile, String filePath, String fileName) {
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;

		try {
			File dir = new File(filePath);

			// 判断文件目录是否存在
			if (!dir.exists() && dir.isDirectory()) {
				dir.mkdirs();
			}

			file = new File(filePath + "/" + fileName);
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(bfile);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}

		return file;
	}

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
