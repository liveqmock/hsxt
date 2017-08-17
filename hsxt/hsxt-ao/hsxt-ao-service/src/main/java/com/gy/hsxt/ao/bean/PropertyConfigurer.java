package com.gy.hsxt.ao.bean;

import com.gy.hsi.ds.param.HsPropertiesConfigurer;

/**
 * Spring属性文件扫描类
 * 
 * @Package: com.gy.hsxt.ac.common.bean  
 * @ClassName: PropertyConfigurer 
 * @Description: Spring属性文件扫描
 *
 * @author: maocan 
 * @date: 2015-9-28 下午12:16:47 
 * @version V1.0
 */
public class PropertyConfigurer extends HsPropertiesConfigurer {
	
	public PropertyConfigurer() {
		this(null);
	}

	public PropertyConfigurer(String disconfFile) {
		super(disconfFile);
	}
	
	public static String getProperty(String key) {
		return HsPropertiesConfigurer.getProperty(key);
	}

	public static String getPropertyByUTF8(String key) {
		return HsPropertiesConfigurer.getPropertyByUTF8(key);
	}
}