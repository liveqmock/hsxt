package com.gy.hsxt.ps.common;


import com.gy.hsi.ds.param.HsPropertiesConfigurer;

/**
 * @Package: com.gy.hsxt.ps.common.PropertyConfigurer
 * @ClassName: PropertyConfigurer
 * @Description: TODO
 * 
 * @author: chenhongzhi
 * @date: 2015-10-20 上午10:34:41
 * @version V3.0
 */
public class PropertyConfigurer extends HsPropertiesConfigurer
{

	public PropertyConfigurer() {
		this(null);
	}

	public PropertyConfigurer(String disconfFile) {
		super(disconfFile);
	}
	
	public static String getProperty(String key) {
		return HsPropertiesConfigurer.getProperty(key);
	}

}