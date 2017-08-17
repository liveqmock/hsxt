/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.uf.common.spring;

import com.gy.hsi.ds.param.HsPropertiesConfigurer;
import com.gy.hsxt.uf.common.utils.StringUtils;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-api
 * 
 *  Package Name    : com.gy.hsxt.uf.common.spring
 * 
 *  File Name       : UfPropertyConfigurer.java
 * 
 *  Creation Date   : 2015-9-25
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 重写Spring属性文件扫描机制
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class UfPropertyConfigurer extends HsPropertiesConfigurer {

	public UfPropertyConfigurer() {
		super(null);
	}

	public UfPropertyConfigurer(String disconfFile) {
		super(disconfFile);
	}

	/**
	 * 获取属性值
	 * 
	 * @param key
	 * @return
	 */
	public static String getProperty(String key) {
		return HsPropertiesConfigurer.getProperty(key);
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

	/**
	 * 设置key-value
	 * 
	 * @param key
	 * @param value
	 */
	public static void setProperties(String key, String value) {
		HsPropertiesConfigurer.setProperties(key, value);
	}
}