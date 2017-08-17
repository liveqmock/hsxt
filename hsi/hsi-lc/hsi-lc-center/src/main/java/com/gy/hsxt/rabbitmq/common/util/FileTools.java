package com.gy.hsxt.rabbitmq.common.util;



import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gy.hsxt.rabbitmq.common.constant.ConfigConstant;
/**
 * 
* @ClassName: FileTools 
* @Description: 文件操作工具类
* @author tianxh 
* @date 2015-8-6 下午4:23:18 
*
 */
public class FileTools {
	private static final Logger log = LoggerFactory.getLogger(FileTools.class);
	public final  static int BUF_SIZE = 32 * 1024;
	public final  static String EMPTY = "";
	public final  static String FILE_HEAD = "file:/";
	public final  static String PROPERTIESP_PATH = "conf/rabbitmq/logCenter.properties";
	/**
	 * 
	* @Title: createMissingParentDirectories 
	* @Description: 根据参数file新建文件
	* @param @param file
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws
	 */
	public static  boolean createMissingParentDirectories(File file) {
		boolean isParentFileExist = true;//parent目录是否存在
		boolean createFileSucess = false;//文件是否创建成功
		File parentFile = file.getParentFile();
		if (!parentFile.exists() && !parentFile.mkdirs()) {//缺失parent目录且创建parent目录失败
			isParentFileExist = false;
		}
		if (isParentFileExist) {//parent目录创建成功
			if (!file.exists()) {//file文件不存在
				try {
					createFileSucess = file.createNewFile();//新建file
				} catch (IOException e) {
					e.printStackTrace();
					log.error(ConfigConstant.MOUDLENAME, "[FileTools]",
							ConfigConstant.FUNNAME,
							"[createMissingParentDirectories],code:", e.getCause(),
							",message:", e.getMessage());
					createFileSucess = false;
				}
			}
		}
		return createFileSucess;
	}
	/**
	 * 
	* @Title: copy 
	* @Description: copy源文件到目标文件
	* @param @param src 源文件
	* @param @param destination    目标文件 
	* @return void    返回类型 
	* @throws
	 */
	public static void copy(String src, String destination) {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(src));
			bos = new BufferedOutputStream(new FileOutputStream(destination));
			byte[] inbuf = new byte[BUF_SIZE];
			int n;
			while ((n = bis.read(inbuf)) != -1) {
				bos.write(inbuf, 0, n);
			}
		} catch (IOException ioe) {
			String msg = "Failed to copy [" + src + "] to [" + destination
					+ "]";
			ioe.printStackTrace();
			log.error(ConfigConstant.MOUDLENAME, "[FileTools]",
					ConfigConstant.FUNNAME,
					"[copy],",msg, ",code:", ioe.getCause(),
					",message:", ioe.getMessage());
		} finally {
			freeResource(bis, bos);
		}
	}
	/**
	 * 
	* @Title: getProperties 
	* @Description: 获取properites配置文件
	* @param @param projectFile
	* @param @return    设定文件 
	* @return Properties    返回类型 
	* @throws
	 */
	public static Properties getProperties(String projectFile) {
		String fullPath = correctFullFilePath(projectFile);
		InputStream in = null;
		Properties properties = new Properties();
		try {
			in = new FileInputStream(new File(fullPath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			log.error(ConfigConstant.MOUDLENAME, "[FileTools]",
					ConfigConstant.FUNNAME,
					"[getProperties]  FileNotFound ,code:", e.getCause(),
					",message:", e.getMessage());
		}
		try {
			properties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
			log.error(ConfigConstant.MOUDLENAME, "[FileTools]",
					ConfigConstant.FUNNAME,
					"[getProperties] properties文件加载失败 ,code:", e.getCause(),
					",message:", e.getMessage());
		}finally{
			freeResource(in, null);
		}
		return properties;
	}

	/**
	 * 
	* @Title: correctFullFilePath 
	* @Description: 获取全路径 
	* @param @param projectFile
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String correctFullFilePath(String projectFile) {
		String fullPath = System.getProperty("user.dir")  + File.separator + projectFile;
		fullPath = fullPath.replaceAll(FILE_HEAD, EMPTY);
		return fullPath;
	}
	/**
	 * 
	* @Title: emptyFileContent 
	* @Description: 清空文件内容
	* @param @param file    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	private static  void emptyFileContent(File file) {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);
			out.write(new byte[0]);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
			String message = "Can't not empty " + file.getName();
			log.error(ConfigConstant.MOUDLENAME, "[FileTools]",
					ConfigConstant.FUNNAME,
					"[emptyFileContent],",message,"code:", e.getCause(),
					",message:", e.getMessage());
		} finally {
			freeResource(null, out);
		}
	}
/**
 * 
* @Title: rename 
* @Description: 重命名源文件到目标文件 
* @param @param source  源文件
* @param @param newFilename 目标文件 
* @return void    返回类型 
* @throws
 */
	public static  void rename(String source, String newFilename) {
		File target = new File(newFilename); //目标文件
		File file = new File(source); //源文件
		boolean result = file.renameTo(target); 
		if (!result) {//更名失败，则采取变相的更名方法
			fileChannelCopy(file, target); //将旧文件拷贝给新文件
			emptyFileContent(file);// 清空旧文件
			file.delete();
		} 
	}
/**
 * 
* @Title: fileChannelCopy 
* @Description: 
* @param @param s
* @param @param t    设定文件 
* @return void    返回类型 
* @throws
 */
	private static  void fileChannelCopy(File s, File t) {
		FileInputStream fi = null;
		FileOutputStream fo = null;
		FileChannel in = null;
		FileChannel out = null;
		try {
			fi = new FileInputStream(s);// 得到对应的文件通道
			fo = new FileOutputStream(t);// 得到对应的文件通道
			in = fi.getChannel();
			out = fo.getChannel();
			in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
		} catch (IOException e) {
			e.printStackTrace();
			log.error(ConfigConstant.MOUDLENAME, "[FileTools]",
					ConfigConstant.FUNNAME,
					"[fileChannelCopy],","code:", e.getCause(),
					",message:", e.getMessage());
		} finally {
			freeResource(fi,fo,in,out);
		}
	}
	/**
	 * 
	* @Title: freeResource 
	* @Description: 释放资源 
	* @param @param in
	* @param @param out
	* @param @param fi
	* @param @param fo    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public static void freeResource(InputStream in,OutputStream out,FileChannel fi,FileChannel fo){
		if(null != fi){
			try {
				fi.close();
			} catch (IOException e) {
			e.printStackTrace();
				log.error(ConfigConstant.MOUDLENAME, "[FileTools]",
						ConfigConstant.FUNNAME,
						"[freeResource],","code:", e.getCause(),
						",message:", e.getMessage());
			}finally{
				fi = null;
			}
		}
		if(null != fo){
			try {
				fo.close();
			} catch (IOException e) {
				e.printStackTrace();
				log.error(ConfigConstant.MOUDLENAME, "[FileTools]",
						ConfigConstant.FUNNAME,
						"[freeResource],","code:", e.getCause(),
						",message:", e.getMessage());
			}finally{
				fo = null;
			}
		}
		if(null != in){
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
				log.error(ConfigConstant.MOUDLENAME, "[FileTools]",
						ConfigConstant.FUNNAME,
						"[freeResource],","code:", e.getCause(),
						",message:", e.getMessage());
			}finally{
				in = null;
			}
		}
		if(null != out){
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
				log.error(ConfigConstant.MOUDLENAME, "[FileTools]",
						ConfigConstant.FUNNAME,
						"[freeResource],","code:", e.getCause(),
						",message:", e.getMessage());
			}finally{
				out = null;
			}
		}
	}
	/**
	 * 
	* @Title: freeResource 
	* @Description: 释放资源 
	* @param @param in
	* @param @param out
	* @param @param fi
	* @param @param fo    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public static void freeResource(InputStream in,OutputStream out){
		if(null != in){
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
				log.error(ConfigConstant.MOUDLENAME, "[FileTools]",
						ConfigConstant.FUNNAME,
						"[freeResource],","code:", e.getCause(),
						",message:", e.getMessage());
			}finally{
				in = null;
			}
		}
		if(null != out){
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
				log.error(ConfigConstant.MOUDLENAME, "[FileTools]",
						ConfigConstant.FUNNAME,
						"[freeResource],","code:", e.getCause(),
						",message:", e.getMessage());
			}finally{
				out = null;
			}
		}
	}
	
	/**
	 * 
	* @Title: freeResource 
	* @Description: 释放资源 
	* @param @param in
	* @param @param out
	* @param @param fi
	* @param @param fo    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public static void freeResource(FileChannel fi,FileChannel fo){
		if(null != fi){
			try {
				fi.close();
			} catch (IOException e) {
				e.printStackTrace();
				log.error(ConfigConstant.MOUDLENAME, "[FileTools]",
						ConfigConstant.FUNNAME,
						"[freeResource],","code:", e.getCause(),
						",message:", e.getMessage());
			}finally{
				fi = null;
			}
		}
		if(null != fo){
			try {
				fo.close();
			} catch (IOException e) {
				e.printStackTrace();
				log.error(ConfigConstant.MOUDLENAME, "[FileTools]",
						ConfigConstant.FUNNAME,
						"[freeResource],","code:", e.getCause(),
						",message:", e.getMessage());
			}finally{
				fo = null;
			}
		}
	}
	
	public static String createAciveFileName(){
		
		return "";
	}
}
