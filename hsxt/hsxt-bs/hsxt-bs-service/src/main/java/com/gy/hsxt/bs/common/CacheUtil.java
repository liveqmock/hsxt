package com.gy.hsxt.bs.common;

import java.util.HashMap;
import java.util.Map;

/**
 * BS本地缓存
 * 
 * @Package: com.gy.hsxt.bs.common
 * @ClassName: CacheUtil
 * @Description: TODO
 * @author: likui
 * @date: 2015年11月11日 上午10:05:47
 * @company: gyist
 * @version V3.0.0
 */
public class CacheUtil {

	private static final Map<String, Object> cache = new HashMap<String, Object>();

	private CacheUtil()
	{
		super();
	}

	/**
	 * 获取值
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月11日 上午11:40:49
	 * @param key
	 * @param clazz
	 * @return
	 * @return : T
	 * @version V3.0.0
	 */
	public static final <T extends Object> T getValue(String key, Class<T> clazz)
	{
		Object obj = cache.get(key);
		T value = null;
		if (obj != null && clazz.isInstance(obj))
		{
			value = clazz.cast(obj);
		}
		return value;
	}

	/**
	 * 添加键值对
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月11日 上午11:40:39
	 * @param key
	 * @param value
	 * @return : void
	 * @version V3.0.0
	 */
	public static final void put(String key, Object value)
	{
		cache.put(key, value);
	}

	/**
	 * 获取缓存
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月11日 上午11:40:32
	 * @return
	 * @return : Map<String,Object>
	 * @version V3.0.0
	 */
	public static final Map<String, Object> getCache()
	{
		return cache;
	}

	/**
	 * 清空缓存
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月11日 下午12:06:57
	 * @return : void
	 * @version V3.0.0
	 */
	public static final void clearCache()
	{
		cache.clear();
	}
}
