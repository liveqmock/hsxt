/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.ds.common.spring;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.gy.hsi.common.utils.StringUtils;

public class MyPropertyConfigurer extends PropertyPlaceholderConfigurer {
	private static final Map<String, String> ctxPropertiesMap = new HashMap<String, String>();

	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props) throws BeansException {
		super.processProperties(beanFactoryToProcess, props);

		String keyStr;
		String value;

		for (Object key : props.keySet()) {
			keyStr = key.toString();
			value = props.getProperty(keyStr);

			ctxPropertiesMap.put(keyStr, value);
		}
	}

	public static String getProperty(String key) {
		return StringUtils.avoidNull(ctxPropertiesMap.get(key));
	}
	
	/**
	 * 获取属性值int类型
	 * 
	 * @param key
	 * @return
	 */
	public static int getPropertyIntValue(String key) {
		return StringUtils.str2Int(getProperty(key));
	}
}