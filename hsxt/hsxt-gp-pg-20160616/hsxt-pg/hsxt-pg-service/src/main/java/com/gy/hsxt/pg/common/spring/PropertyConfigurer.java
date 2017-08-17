/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.common.spring;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import com.gy.hsi.ds.param.HsPropertiesConfigurer;
import com.gy.hsxt.pg.common.constant.ConfigConst;
import com.gy.hsxt.pg.common.constant.Constant.ProductionEnv;
import com.gy.hsxt.pg.common.utils.StringUtils;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.common.spring
 * 
 *  File Name       : PropertyConfigurer.java
 * 
 *  Creation Date   : 2014-9-25
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
public class PropertyConfigurer extends HsPropertiesConfigurer {

	public PropertyConfigurer() {
		this(null);
	}

	public PropertyConfigurer(String disconfFile) {
		super(disconfFile);
	}
	
	public static int getPropertyIntValue(String key) {
		return StringUtils.str2Int(getProperty(key));
	}

	public static String getProperty(String key) {
		return HsPropertiesConfigurer.getProperty(key);
	}

	@Override
	protected void doCacheProperties(Properties props) {
		super.doCacheProperties(props);

		// 如果是测试环境
		if (!ProductionEnv.TRUE.equals(getProperty(ConfigConst.PRODUCTION_ENV))) {
			this.overrideByTestValue(props);

			this.updateCacheProps(props, ConfigConst.IS_PRODUCTION_ENV,
					Boolean.FALSE.toString());
		}
		// 如果是生产环境
		else {
			this.updateCacheProps(props, ConfigConst.IS_PRODUCTION_ENV,
					Boolean.TRUE.toString());
		}

		Iterator<String> itor = propertiesMap.keySet().iterator();

		while (itor.hasNext()) {
			String key = (String) itor.next();

			if (key.startsWith("test.")) {
				continue;
			}
		}
	}

	/**
	 * 使用测试环境的配置进行覆盖
	 * 
	 * @param props
	 */
	private void overrideByTestValue(Properties props) {
		final String testPrefix = "test.";

		List<String> testKeyList = new ArrayList<String>(10);
		Iterator<String> itor = propertiesMap.keySet().iterator();

		String key;
		String testValue;

		while (itor.hasNext()) {
			key = itor.next();

			if (key.startsWith(testPrefix)) {
				testKeyList.add(key);
			}
		}

		for (String testKey : testKeyList) {
			key = testKey.replaceFirst(testPrefix, "");

			testValue = propertiesMap.get(testKey);

			this.updateCacheProps(props, key, testValue);
			this.removeCacheProps(props, testKey);
		}
	}

	/**
	 * 更新properties
	 * 
	 * @param props
	 * @param key
	 * @param value
	 */
	private void updateCacheProps(Properties props, String key, String value) {
		propertiesMap.put(key, value);
		props.setProperty(key, value);
	}

	/**
	 * 删除properties
	 * 
	 * @param key
	 * @param props
	 */
	private void removeCacheProps(Properties props, String key) {
		propertiesMap.remove(key);
		props.remove(key);
	}
}