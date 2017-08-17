/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.tc.service.util;

import static com.gy.hsi.ds.param.HsPropertiesConfigurer.getProperty;

/**
 * 系统参数帮助类
 * 
 * @Package: com.gy.hsxt.tc.service.util
 * @ClassName: SystemParameterUtil
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2016-03-16 上午11:17:35
 * @version V1.0
 */
public class SystemParameterUtil {
	/**
	 * 获取系统编号
	 * @return
	 */
	public static String getSystemNo(){
		return getProperty("system.instance.no");
	}
}
