package com.gy.hsxt.rabbitmq.common.config;


import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * 
 * @ClassName: PropertyConfigurer 
 * @Description: 解析配置文件
 * @author Lee 
 * @date 2015-7-7 下午2:59:22
 */
public class PropertyConfigurer extends PropertyPlaceholderConfigurer {
	
	private static final Map<String, String> ctxPropertiesMap = new HashMap<String, String>();

	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props) throws BeansException {
		String key;
		String value;

		for (Object keyObj : props.keySet()) {
			key = keyObj.toString();
			value = props.getProperty(key);

			ctxPropertiesMap.put(key, value);
		}

		// 覆盖掉spring自身扫描属性列表
		super.processProperties(beanFactoryToProcess, props);
	}

	public static String getProperty(String key) {
		return ctxPropertiesMap.get(key).trim();
	}

	public static String getPropertyByUTF8(String key) {
		try {
			String value = ctxPropertiesMap.get(key).trim();

			return new String(value.getBytes("ISO-8859-1"), "UTF-8");
		} catch (Exception e) {
		}

		return "";
	}
}