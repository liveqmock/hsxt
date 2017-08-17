package com.gy.hsi.redis.client.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.JedisCluster;

import com.alibaba.fastjson.JSONObject;

/**
 * 用于Redis集群的读写操作。用于Redis3.0版本以上。单机的Redis不能使用该类。 该类提供了两种方式存取缓存对象。<BR/>
 * 一种是键值对方式，即key-value方式，该类型可设置过期时间，单位为秒。到期后可自动删除。<BR/>
 * 另一种是将某一类型的对象归为一组，好处是可以查找该类型的所有数据<BR/>
 * 如用户信息，将所有UserInfo放至一个集合中，每个UserInfo可使用ID单独存取。 <BR/>
 * 在获取该组的对象时，可根据ID查找，也可查找该组的所有对象。<BR/>
 * 还可获取整组的ID。<BR/>
 * 在存入组中对象时，提供了2种方法存入，一种是默认key，即使用传入的Class类名做为key（组名）<BR/>
 * 另一种是自定义key，可自己定义key值
 * 
 * @category Simple to Introduction
 * @projectName hsxt-uc-service
 * @package com.gy.hsxt.uc.util.RedisCachedUtil.java
 * @className RedisCachedUtil
 * @description 一句话描述该类的功能
 * @author lixuan
 * @createDate 2015-10-13 下午6:32:44
 * @updateUser lixuan
 * @updateDate 2015-10-13 下午6:32:44
 * @updateRemark 说明本次修改内容
 * @version v0.0.1
 */
//@Component
public class ClusterClient {

	@Autowired
	JedisCluster jedisCluster;

	/**
	 * 存入缓存，该方法会将T的类名做为类型名称，所有该对象会存在一起 如存入UserInfo，key为用户对象的ID值，o为真正存储的对象 存入缓存
	 * 
	 * @param id
	 *            查找对象的key，一般为ID
	 * @param obj
	 *            ，缓存的对象，不能为List，Map，Object等对象
	 */
	public void setGroup(String id, Object obj) {
		jedisCluster.hset(obj.getClass().getSimpleName(), id,
				JSONObject.toJSONString(obj));
	}

	public void setGroup(String key, String id, Object obj) {
		jedisCluster.hset(key, id, JSONObject.toJSONString(obj));
	}

	/**
	 * 获取缓存中的数据
	 * 
	 * @param key
	 * @param cls
	 * @return
	 */
	public <T> T getFromGroup(String id, Class<T> cls) {
		String json = jedisCluster.hget(cls.getSimpleName(), id);
		return JSONObject.parseObject(json, cls);
	}
	
	/**
	 * 获取缓存中的数据
	 * 
	 * @param key
	 * @param cls
	 * @return
	 */
	public <T> T getFromGroup(String key, String id, Class<T> cls) {
		String json = jedisCluster.hget(key, id);
		return JSONObject.parseObject(json, cls);
	}

	/**
	 * 根据key获取对象列表
	 * 
	 * @param key
	 * @param cls
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<?> getAllFromGroup(String key) {
		List<String> list = jedisCluster.hvals(key);

		List result = new ArrayList();
		for (String s : list) {
			result.add(JSONObject.parse(s));
		}
		return result;
	}

	/**
	 * 根据key获取对象列表的id
	 * 
	 * @param key
	 * @return
	 */
	public List<String> getIdsFromGroup(String key) {
		Set<String> keys = jedisCluster.hkeys(key);
		List<String> list = new ArrayList<String>();
		list.addAll(keys);
		return list;
	}

	/**
	 * 获取存储的cls类型的个数
	 * 
	 * @param cls
	 * @return
	 */
	public long getGroupSize(String key) {
		return jedisCluster.hlen(key);
	}

	public void delFromGroup(String key, Class<?> cls) {
		jedisCluster.hdel(cls.getSimpleName(), key);
	}

	/**
	 * 存入缓存并设置到期时间
	 * 
	 * @param key
	 *            存入缓存的key
	 * @param obj
	 *            存入缓存的对象
	 * @param seconds
	 *            到期时间，单位为秒
	 */
	public void setObjectAndExpire(String key, Object obj, int seconds) {
		jedisCluster.set(key, JSONObject.toJSONString(obj));
		jedisCluster.expire(key, seconds);
	}

	/**
	 * 根据key获取缓存数据，并转换成cls的对象
	 * 
	 * @param key
	 */
	public Object getObject(String key, Class<?> cls) {
		String json = jedisCluster.get(key);
		return JSONObject.parseObject(json, cls);
	}

	/**
	 * 根据key获取缓存中的字符类型数据
	 * 
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		return jedisCluster.get(key);
	}

	/**
	 * 根据key删除缓存
	 * 
	 * @param key
	 */
	public void delete(String key) {
		jedisCluster.del(key);
	}
}
