/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.person.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import org.springframework.util.StringUtils;

/**
 * 
 * 文件操作
 * @Package: com.gy.hsxt.access.web.person.utils  
 * @ClassName: FileUtils 
 * @Description: TODO
 *
 * @author: liuxy 
 * @date: 2015-9-7 下午4:52:16 
 * @version V1.0
 */
public class FileUtils {
	
	/***文件大小单位byte**/
	public static final int BYTE=1;
	/***文件大小单位kb**/
	public static final int KB=2;
	/***文件大小单位mb**/
	public static final int MB=3;
	/***文件大小单位gb**/
	public static final int GB=4;

	
	/**
	 * 获得文件的大小
	 * @param filePath 文件在路径
	 * @param unit 文件大小的单位 
	 * @return
	 */
	public static double getFileSize(String filePath,int unit){
		File file=new File(filePath);
		long length=file.length();
		double result=0;
		
		if(length<=0){
			return 0;
		}
		
		switch (unit) {
			case 1:
				result= length;
				break;
			case 2:
				result=length/1024;
				break;
			case 3:
				result=length/1024/1024;
				break;
			case 4:
				result=length/1024/1024/1024;
				break;

			default:
				result= length;
				break;
		}
		return result;
	}
	
	/**
	 * 比较两个文件大小
	 * @param srcFilePath 源文件
	 * @param destFilePath 目标文件 
	 * @return 1:源文件比目标文件大，0:源文件与目标文件大小相等，-1:源文件小于目标文件
	 */
	public static int compareFileSize(String srcFilePath,String destFilePath){
		File srcFile=new File(srcFilePath);
		File descFile=new File(destFilePath);
		
		if(srcFile.length()>descFile.length()){
			return 1;
		}else if(srcFile.length()==descFile.length()){
			return 0;
		}else {
			return -1;
		}
	} 
	
	
	/**
	 * 
	 *  比较文件的大小与标准值
	 * @param filePath 文件路径
	 * @param benchmark 标准值(kb为单位)
	 * @return 1：文件大，0相等,-1标准值大
	 */
	public static int compareFileSize(String filePath,long benchmark) {
		File srcFile=new File(filePath);
		
		if(srcFile.length()>benchmark){
			return 1;
		}else if(srcFile.length()==benchmark){
			return 0;
		}else {
			return -1;
		}
	} 

	/**
	 * 获得文件的类型
	 * @param filePath
	 * @return 文件的后缀
	 */
	public static String getFileType(String filePath){
		int index=0;
		if(filePath==null){
			return null;
		}else if((index=filePath.lastIndexOf("."))>0&&index!=filePath.length()-1){
			return 	filePath.substring(filePath.lastIndexOf(".")+1,filePath.length());
			
		}else {
			return null;
		}
	};
	
	public static String getFileName(String filePath){
		if(!StringUtils.isEmpty(filePath)){
			return new File(filePath).getName();
		}
		return null;
	}
	
	/***
	 * 文件是否是指定的文件类型
	 * @param filePath 文件路径 
	 * @param types 文件类型集合
	 * @return 
	 */
	public static boolean isSpecifyFileType(String filePath ,String[] types){
		
		if(filePath==null){
			return false;
		}
		
		if(types==null||types.length<1){
			return false;
		}
		
		String fileType=getFileType(filePath);
		for(String t:types){
			if(!StringUtils.isEmpty(t)&&!StringUtils.isEmpty(fileType)&&t.equalsIgnoreCase(fileType)){
				return true;
			}
		}
		
		return false;
	}
	
	/***
	 * 文件复制 
	 * @param srcFilePath 源文件
	 * @param destFilePath 目标路径
	 */
	public static void fileChannelCopy(String srcFilePath, String destFilePath) {

        FileInputStream fi = null;

        FileOutputStream fo = null;

        FileChannel in = null;

        FileChannel out = null;

        try {

            fi = new FileInputStream(srcFilePath);

            fo = new FileOutputStream(destFilePath);

            in = fi.getChannel();//得到对应的文件通道

            out = fo.getChannel();//得到对应的文件通道

            in.transferTo(0, in.size(), out);//连接两个通道，并且从in通道读取，然后写入out通道

        } catch (IOException e) {
        	//输出日志
        	e.printStackTrace();
        } finally {

            try {
				if (fi != null) {

					fi.close();
				}

				if (in != null) {

					in.close();
				}
				if (fo != null) {

					fo.close();
				}

				if (out != null) {

					out.close();
				}

            } catch (IOException e) {

            	//输出日志

            }

        }

    }
}
