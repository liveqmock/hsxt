package com.gy.hsi.nt.server.util;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.gy.hsi.nt.server.util.enumtype.ParamsKey;
/**
 * 
 * @className:CacheUtil
 * @author:likui
 * @date:2015年7月27日
 * @desc:缓存
 * @company:gyist
 */
public class CacheUtil {
	
	private static final Logger logger = Logger.getLogger(CacheUtil.class);
	
	private static Map<String, Object> cache= new HashMap<String, Object>();
	
	private CacheUtil(){
		super();
	}
	
	/**
	 * 获取值
	 * @param key
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public static final <T extends Object>  T getValue(String key, Class<T> clazz) {
		Object obj = cache.get(key);
		T value = null;
		if (obj != null && clazz.isInstance(obj)) {
			value = clazz.cast(obj);
		}
		return value;
	}

	/**
	 * 添加键值对
	 * @param key
	 * @param value
	 */
	public static final void put(String key,Object value) {
		cache.put(key, value);
	}

	/**
	 * 获取缓存
	 * @return
	 */
	public static final Map<String,Object> getCache() {
		logger.info(">>>>>>>>>>缓存大小：" + cache.size() + "<<<<<<<<<<<");
		return cache;
	}
	
	/**
	 * 获取properties配置缓存
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static final Map<String,String> getConfigCache(){
		return getValue(ParamsKey.NT_PARAMS.getKey(),Map.class);
	}
}
