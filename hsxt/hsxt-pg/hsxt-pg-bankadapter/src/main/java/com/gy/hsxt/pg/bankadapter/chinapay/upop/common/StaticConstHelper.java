/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.chinapay.upop.common;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.chinapay.upop.common
 * 
 *  File Name       : StaticConstHelper.java
 * 
 *  Creation Date   : 2015年10月14日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 缓存静态常量定义列表
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class StaticConstHelper {

	/** 缓存map **/
	private static final Map<Class<?>, List<Object>> cache = new HashMap<Class<?>, List<Object>>();

	/**
	 * 获得某个类中静态常量定义列表
	 * 
	 * @param clasz
	 * @return
	 */
	public static List<Object> getConstDefineList(Class<?> clasz) {
		List<Object> list = cache.get(clasz);

		if ((null != list) && (0 < list.size())) {
			return list;
		}

		synchronized (clasz) {
			list = cache.get(clasz);

			if ((null != list) && (0 < list.size())) {
				return list;
			}

			list = new ArrayList<Object>();

			Field[] fields = clasz.getDeclaredFields();

			for (Field field : fields) {
				try {
					Object value = field.get(null);

					if (null != value) {
						list.add(value);
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
				}
			}

			cache.put(clasz, list);
		}

		return list;
	}
}
