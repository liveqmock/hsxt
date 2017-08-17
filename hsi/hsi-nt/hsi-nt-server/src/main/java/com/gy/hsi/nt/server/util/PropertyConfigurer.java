/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsi.nt.server.util;

import java.util.Map;

import com.gy.hsi.ds.param.HsPropertiesConfigurer;

/**
 * Spring属性文件扫描类
 * 
 * @Package: com.gy.hsxt.common.utils
 * @ClassName: PropertyConfigurer
 * @Description: TODO
 * @author: likui
 * @date: 2015年10月14日 下午3:18:00
 * @company: gyist
 * @version V3.0.0
 */
public class PropertyConfigurer {

	public static Map<String, String> getCtxpropertiesmap()
	{
		return HsPropertiesConfigurer.getPropertyMap();
	}
}