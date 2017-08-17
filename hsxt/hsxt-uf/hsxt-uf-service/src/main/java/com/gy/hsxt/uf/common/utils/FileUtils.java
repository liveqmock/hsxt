/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.uf.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-service
 * 
 *  Package Name    : com.gy.hsxt.uf.common.utils
 * 
 *  File Name       : FileUtils.java
 * 
 *  Creation Date   : 2015-9-29
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 文件读写工具类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class FileUtils {

	private FileUtils() {
	}

	/**
	 * 将inputstream转换为file对象
	 * 
	 * @param ins
	 * @param fileName
	 * @return
	 */
	public static File formatInputStream2File(InputStream ins, String fileName) {
		File file = new File(fileName);

		try {
			OutputStream os = new FileOutputStream(file);

			int bytesRead = 0;
			byte[] buffer = new byte[8192];

			while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}

			os.close();
			ins.close();
		} catch (Exception e) {
		}

		return file;
	}

	/**
	 * 向文件中写对象
	 * 
	 * @param path
	 * @param fileName
	 * @param data
	 * @throws Exception
	 */
	public static void writeObject(String path, String fileName, Object data)
			throws Exception {
		String fullPath = preHandle(path, fileName);

		// 用对象流将生成的密钥写入文件
		ObjectOutputStream oos = null;

		try {
			oos = new ObjectOutputStream(new FileOutputStream(fullPath));
			oos.writeObject(data);
		} finally {
			// 清空缓存，关闭文件输出流
			if (null != oos) {
				oos.close();
			}
		}
	}

	/**
	 * 从文件中读取对象数据
	 * 
	 * @param path
	 * @param fileName
	 * @return
	 */
	public static Object readKeyFromFile(String path, String fileName) {
		Object obj = null;

		try {
			if (!path.endsWith("/") && !path.endsWith("\\")) {
				path += "/";
			}

			String fullPath = path + fileName;

			// 将文件中的公钥对象读出
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fullPath));

			obj = ois.readObject();

			ois.close();
		} catch (Exception e) {
		}

		return obj;
	}

	/**
	 * 预处理
	 * 
	 * @param absolutePath
	 * @param fileName
	 * @return
	 */
	private static String preHandle(String absolutePath, String fileName) {
		File file = new File(absolutePath);

		if (!file.exists()) {
			file.mkdirs();
		}

		if (!absolutePath.endsWith("/") && !absolutePath.endsWith("\\")) {
			absolutePath += "/";
		}

		return absolutePath + fileName;
	}
}