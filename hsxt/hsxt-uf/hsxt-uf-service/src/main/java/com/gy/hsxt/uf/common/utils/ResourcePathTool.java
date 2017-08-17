/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.uf.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.gy.hsxt.uf.common.constant.UFConstant;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-service
 * 
 *  Package Name    : com.gy.hsxt.uf.common.utils
 * 
 *  File Name       : ResourcePathTool.java
 * 
 *  Creation Date   : 2015-6-18
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 读取工程下特定文件资源的工具类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public final class ResourcePathTool {
	/** 单实例对象 **/
	private static final ResourcePathTool instance = new ResourcePathTool();

	/** 当前工程跟目录 **/
	private String rootPath;

	/** 扫描历史路径 **/
	private final List<String> scanningHistoryPaths = new ArrayList<String>(3);

	/**
	 * 构造函数
	 */
	private ResourcePathTool() {
		// 取得当前工程的根目录
		this.rootPath = getProjectRootPath();
	}

	/**
	 * 获得单实例对象
	 * 
	 * @return
	 */
	public static ResourcePathTool getInstance() {
		return instance;
	}

	/**
	 * 获得指定名称的配置文件
	 * 
	 * @param fileName
	 * @return
	 */
	public File getResourceFile(String fileName) {
		// 递归当前工程路径,找到指定的配置文件(如果没有找到则返回null)
		File configFile = findConfigResourceLevelByLevel(this.rootPath,
				fileName);

		if (null == configFile) {
			throw new IllegalArgumentException("严重文件IO错误：在工程目录中没有找到" + fileName
					+ "配置文件,请您检查该文件是否存在！ \n 您的工程目录是：" + this.rootPath);
		}

		return configFile;
	}

	/**
	 * 获得指定名称的配置文件
	 * 
	 * @param fileName
	 * @return
	 */
	public File getEcmsConfigFile(String fileName) {
		File configFile = scannFilePathFromJarFile(fileName);

		// 递归当前工程路径,找到指定的配置文件(如果没有找到则返回null)
		if (null == configFile) {
			configFile = findConfigResourceLevelByLevel(this.rootPath, fileName);
		}

		if (null == configFile) {
			throw new IllegalArgumentException("严重文件IO错误：在工程目录中没有找到" + fileName
					+ "配置文件,请您检查该文件是否存在！ \n 您的运行根目录是：" + this.rootPath);
		}

		return configFile;
	}

	/**
	 * 获得指定名称的配置文件(静默方式)
	 * 
	 * @param strPath
	 * @param fileName
	 * @return
	 */
	public File getResourceFileInSpecialPath(String strPath, String fileName) {
		if (StringUtils.isEmpty(strPath)) {
			return null;
		}

		File currFile;
		File dir = new File(strPath);
		File[] files = dir.listFiles();
		String currFileName;

		if (null == files) {
			return null;
		}

		// 从当前路径找到指定文件
		for (int i = 0; i < files.length; i++) {
			currFile = files[i];

			// 如果当前是文件
			if (currFile.isFile()) {
				// 取得当前文件的名称
				currFileName = currFile.getName();

				// 找到了指定的配置文件,直接返回之
				if (currFileName.equals(fileName.trim())) {
					return currFile;
				}
			}
		}

		return null;
	}

	/**
	 * 根据路径的层级逐级寻找指定的文件
	 * 
	 * @param filePath
	 * @param fileName
	 * @return
	 */
	private File findConfigResourceLevelByLevel(String filePath, String fileName) {
		File configFile = null;
		String strPath = filePath;

		for (int i = 0; i < 3; i++) {
			// 递归当前工程路径,找到指定的配置文件(如果没有找到则返回null)
			configFile = findConfigResource(strPath, fileName);

			this.scanningHistoryPaths.add(strPath);

			if (null == configFile) {
				strPath = getParentFilePath(strPath);
			} else {
				break;
			}
		}

		return configFile;
	}

	/**
	 * 递归当前工程路径,找到指定的配置文件(如果没有找到则返回null)
	 * 
	 * @param strPath
	 *            要遍历的文件路径
	 * @param fileName
	 *            要查找的文件名称
	 * @return
	 */
	private File findConfigResource(String strPath, String fileName) {
		if (scanningHistoryPaths.contains(strPath)) {
			return null;
		}

		String currFileName;

		File targetFile = null;
		File currFile = null;

		File dir = new File(strPath);
		File[] files = dir.listFiles();

		int filesSize = (null != files) ? files.length : 0;

		// 循环递归当前文件夹
		for (int i = 0; i < filesSize; i++) {
			currFile = files[i];

			// 如果当前是文件夹
			if (currFile.isDirectory()) {
				// 递归当前文件夹中的子路径,试图寻找指定的文件
				targetFile = findConfigResource(currFile.getAbsolutePath(),
						fileName);

				if (null != targetFile) {
					return targetFile;
				}
			}
			// 如果当前是文件
			else {
				// 取得当前文件的名称
				currFileName = currFile.getName();

				// 找到了指定的配置文件,直接返回之
				if (currFileName.equals(fileName.trim())) {
					return currFile;
				}
			}
		}

		return null;
	}

	/**
	 * 获得工程的根目录
	 * 
	 * @return
	 */
	private String getProjectRootPath() {
		// 类加载路径
		ClassLoader classLoader = ResourcePathTool.class.getClassLoader();

		// 根路径
		String rootPath = "";

		try {
			rootPath = classLoader.getResource("").toURI().getPath();
		} catch (Exception e) {
			try {
				rootPath = URLDecoder.decode(classLoader.getResource("")
						.getPath(), UFConstant.ENCODING_UTF8);
			} catch (Exception ex) {
			}

			if (StringUtils.isEmpty(rootPath)) {
				try {
					rootPath = new File(".").getCanonicalPath();
				} catch (Exception ex) {
				}
			}
		}

		if (!StringUtils.isEmpty(rootPath)) {
			return new File(rootPath).getAbsolutePath();
		}

		return "";
	}

	/**
	 * 获得上一级路径
	 * 
	 * @param referencePath
	 * @return
	 */
	private String getParentFilePath(String referencePath) {
		File dir = new File(referencePath);
		File parentFile = dir.getParentFile();

		if (null != parentFile) {
			return parentFile.getAbsolutePath();
		}

		return null;
	}

	/**
	 * 读取属性文件
	 * 
	 * @param file
	 * @return
	 */
	public Map<String, String> readPropertyFile(File file) {
		if (null != file) {
			return this.readPropertyFile(file.getAbsolutePath());
		}

		return new HashMap<String, String>();
	}

	/**
	 * 读取属性文件
	 * 
	 * @param filePath
	 * @return
	 */
	private Map<String, String> readPropertyFile(String filePath) {
		Map<String, String> dataMap = new HashMap<String, String>(2);

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(filePath)));

			for (String line = br.readLine(); (null != line); line = br
					.readLine()) {
				if (line.startsWith("#") || line.startsWith("<!--")
						|| line.startsWith("/**") || "".equals(line.trim())) {
					continue;
				}

				if (line.contains("=")) {
					String key = line.substring(0, line.lastIndexOf("="));
					String value = line.substring(line.lastIndexOf("=") + 1);

					dataMap.put(key.trim(), value.trim());
				}
			}

			br.close();
		} catch (Exception e) {
		}

		return dataMap;
	}

	/**
	 * 如果正常途径无法找到指定的文件,就说明可能是打成了jar包,需要在jar中进行查账
	 * 
	 * @param fileName
	 * @return
	 */
	private File scannFilePathFromJarFile(String fileName) {
		JarFile jarFile = null;
		String innerPath = null;

		try {
			String jarFilePath = ResourcePathTool.class.getProtectionDomain()
					.getCodeSource().getLocation().getFile();
			jarFilePath = java.net.URLDecoder.decode(jarFilePath,
					UFConstant.ENCODING_UTF8);

			jarFile = new JarFile(jarFilePath);

			Enumeration<JarEntry> entrys = jarFile.entries();

			while (entrys.hasMoreElements()) {
				innerPath = entrys.nextElement().getName();

				if (innerPath.endsWith(fileName)) {
					innerPath = correctFilePathPrefix(innerPath);

					InputStream inputStream = this.getClass()
							.getResourceAsStream(innerPath);

					return FileUtils.formatInputStream2File(inputStream,
							fileName);
				}
			}
		} catch (Exception ex) {
		} finally {
			try {
				jarFile.close();
			} catch (Exception e) {
			}
		}

		return null;
	}

	/**
	 * 文件路径完善路径前缀
	 * 
	 * @param path
	 * @return
	 */
	private String correctFilePathPrefix(String path) {
		if (!path.startsWith("/") && !path.startsWith(".")) {
			return "/" + path;
		}

		return path;
	}
}
