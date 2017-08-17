package com.gy.hsxt.rabbitmq.common.generator;



import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.support.PropertiesLoaderUtils;


/**
 * 
 * @ClassName: SystemPropUtil 
 * @Description: 读取系统属性配置文件类 
 * @author Lee 
 * @date 2015-6-25 下午2:26:16
 */
public class SystemPropUtil {

	
	public static Map<Object, Object> systemPropMap = new HashMap<Object,Object>();
	
	static {
		if(systemPropMap.isEmpty()){
			try {
				
				systemPropMap = PropertiesLoaderUtils.loadAllProperties("system.properties");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 获取 数字型 值
	 * @param key
	 * @return
	 */
	public static Integer getIntValue(String key){

		String  value = getStringValue(key);
			if (!"" .equals(value)){
				return  Integer.parseInt(value);
			
			
		}

		return null;
	}
	
	/**
	 * 获取 字符串型值
	 * @param key
	 * @return
	 */
	public static String getStringValue(String key){
		
		if(systemPropMap.containsKey(key)){
			Object value = systemPropMap.get(key);
			if (null != value){
				return (String)systemPropMap.get(key);
			}
			
		}
		return "";
	}
	
	

}
