package com.gy.hsi.nt.server.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * 
 * @className:ReadProperties
 * @author:likui
 * @date:2015年7月29日
 * @desc:读取properties文件
 * @company:gyist
 */
public class ReadProperties {
	
	private static final Logger logger = Logger.getLogger(ReadProperties.class);
	
	private static final String filePath = "/config/hsi-nt-config.properties";
	
	
	private ReadProperties(){
		super();
	}
	
	/**
	 * 
	* @author:likui
	* @created:2015年7月29日上午9:55:16
	* @desc:根据key获取属性
	* @param:@param key
	* @param:@return
	* @param:String
	* @throws
	 */
	public static String readValue(String key){
		String param=null;
		Properties props = null;
		try {
			props = new Properties();
			InputStream in = new BufferedInputStream(new FileInputStream(ReadProperties.class.getResource(filePath).getPath()));
			props.load(in);
			param = props.getProperty(key);
			in.close();
		} catch (Exception e){
			logger.error("读取properties文件异常",e);
		}
		return param ;
	}
	
	/**
	 * 
	* @author:likui
	* @created:2015年7月29日上午10:02:33
	* @desc:读取所有的值
	* @param:@return
	* @param:Map<String,String>
	* @throws
	 */
	public static Map<String,String> readValueAll(){
		Properties props = null;
		Map<String,String> param =null;
		try {
			props = new Properties();
			InputStream in = new BufferedInputStream(new FileInputStream(ReadProperties.class.getResource(filePath).getPath()));
			props.load(in);
			Set<String> keySet=props.stringPropertyNames();
			if(!keySet.isEmpty()){
				param = new HashMap<String,String>();
				for(String key: keySet){
					param.put(key, props.getProperty(key));
				}
			}
			in.close();
		} catch (Exception e){
			logger.error("读取properties文件异常",e);
		}
		return param ;
	}
}
