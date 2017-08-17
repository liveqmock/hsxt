/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsi.redis.client.api;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.fastjson.JSON;

/**
 * 该类主要用于单机的redis或主从方式的redis连接
 * 
 * @Package: com.gy.hsi.redis.client.api
 * @ClassName: RedisUtil
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2015-10-20 下午2:30:40
 * @version V1.0
 * 
 * @param <T>
 */
public class RedisUtil<T> {
	public RedisTemplate<String, String> redisTemplate;
 
	/**
	 * 根据key值删除对象
	 * 
	 * @param key
	 */
	public void remove(String key) {
		System.out.println("Delete redis key:" + key);
		redisTemplate.delete(key);
	}

	/**
	 * key是否存在，如果isDelete为true，则删除，反之不删除
	 * 
	 * @param isDelete
	 *            是否删除查询到的key
	 * @return
	 */
	public boolean isExists(final String key, boolean isDelete) {
		if (!isDelete) {
			return redisTemplate.hasKey(key);
		} else {
			long result = (Long) redisTemplate
					.execute(new RedisCallback<Long>() {
						public Long doInRedis(RedisConnection connection)
								throws DataAccessException {
							return connection.del(key.getBytes());
						}
					});
			//System.out.println(key + " [isExists del count]:" + result);
			return result > 0;
		}
	}

	/**
	 * 根据key删除Hash类型的对象
	 * 
	 * @param key
	 *            key
	 * @param id
	 *            id
	 */
	public void remove(String key, String id) {
		//System.out.println("Delete redis hash key:" + key + ", id:" + id);
		redisTemplate.opsForHash().delete(key, id);
	}

	/**
	 * 设置倒计时的过期时间，单位是秒
	 * 
	 * @param key
	 * @param timeout
	 */
	public void setExpireInSecond(String key, long timeout) {
		//System.out.println("Set expire in second. key:" + key + ", timeout"
		//		+ timeout);
		setExpire(key, timeout, TimeUnit.SECONDS);
	}

	/**
	 * 设置倒计时的过期时间，单位是秒
	 * 
	 * @param key
	 * @param timeout
	 */
	public void setExpireInHours(String key, long timeout) {
		//System.out.println("Set expire in Hours. key:" + key + ", timeout"
		//		+ timeout);
		setExpire(key, timeout, TimeUnit.HOURS);
	}

	/**
	 * 设置倒计时的过期时间，单位是小时
	 * 
	 * @param key
	 * @param timeout
	 */
	public void setExpireInHour(String key, long timeout) {
		//System.out.println("Set expire in hour. key:" + key + ", timeout"
		//		+ timeout);
		setExpire(key, timeout, TimeUnit.HOURS);
	}

	/**
	 * 设置倒计时的过期时间，单位是分钟
	 * 
	 * @param key
	 * @param timeout
	 */
	public void setExpireInMinutes(String key, long timeout) {
		//System.out.println("Set expire in minutes. key:" + key + ", timeout"
		//		+ timeout);
		setExpire(key, timeout, TimeUnit.MINUTES);
	}

	/**
	 * 设置倒计时，单位自己设置
	 * 
	 * @param key
	 * @param timeout
	 * @param unit
	 */
	public void setExpire(String key, long timeout, TimeUnit unit) {
		//System.out.println("Set expire. unit:" + unit.name() + ",key:" + key
		//		+ ", timeout" + timeout);
		redisTemplate.expire(key, timeout, unit);
	}

	/**
	 * 添加key-map对象
	 * 
	 * @param objKey
	 *            key
	 * @param id
	 *            id
	 * @param obj
	 *            待存储的对象
	 */
	public void add(final String objKey, String id, final T obj) {
		String str = JSON.toJSONString(obj);
		redisTemplate.opsForHash().put(objKey, id, str);
	}

	/**
	 * 存储方式为key-value
	 * 
	 * @param objKey
	 *            key
	 * @param obj
	 *            待存储的对象
	 */

	public void add(final String objKey, final T obj) {
		String str = JSON.toJSONString(obj);
		redisTemplate.opsForValue().set(objKey, str);
	}

	/**
	 * 获取存储方式为key-value的值
	 * 
	 * @param objKey
	 * @return 返回String类型
	 */
	public String getToString(final String objKey) {
		//System.out.println("-- get objKey:" + objKey);
		String json = redisTemplate.opsForValue().get(objKey);
		try {
			return JSON.parseObject(json, String.class);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("获取值时发生错误，不是JSON格式的值，key=" + objKey);
			return json;
		}
	}

	/**
	 * 获取存储方式为key-value的值
	 * 
	 * @param key
	 *            key
	 * @param clz
	 *            待序列化的对象
	 * @return 返回自定义对象：clz
	 */
	@SuppressWarnings({ "unchecked" })
	public T get(final String key, Class<?> clz) {
		try {
			String str = redisTemplate.opsForValue().get(key);
			return (T) JSON.parseObject(str, clz);
		} catch (Exception e) {
			System.out.println(clz.getName() + "获取缓存对象失败，key:" + key);
			e.printStackTrace();
			return null;
		} finally {
		}
	}

	/**
	 * 获取key-map中的对象
	 * 
	 * @param objKey
	 *            key
	 * @param id
	 *            id
	 * @return object 返回获取的对象
	 */

	@SuppressWarnings({ "unchecked" })
	public T get(final String objKey, String id, Class<?> cls) {
		try {
			String json = (String) redisTemplate.opsForHash().get(objKey, id);
			return (T) JSON.parseObject(json, cls);
		} catch (Exception e) {
			System.out.println("获取缓存对象失败，key:" + objKey);
			e.printStackTrace();
			return null;
		} finally {
			// redisTemplate.setValueSerializer(srs);
		}
	}

	/**
	 * 获取key-map中的对象
	 * 
	 * @param key
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> getAll(final String key, Class cls) {
		try {
			List<Object> objs = redisTemplate.opsForHash().values(key);
			List<T> list = new ArrayList<>();
			for (Object s : objs) {
				T t = (T) JSON.parseObject((String) s, cls);
				list.add(t);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 存储key-list的对象
	 * 
	 * @param key
	 * @param value
	 * @param clz
	 * @return
	 */
	public Long addToList(String key, T value, Class<?> clz) {
		String json = JSON.toJSONString(value);
		return redisTemplate.opsForList().rightPush(key, json);
	}

	/**
	 * 存储key-list的对象
	 * 
	 * @param key
	 *            key
	 * @param value
	 *            待存储的对象
	 * @param clz
	 *            存储对象的class
	 * @return
	 */
	public Long addList(String key, List<T> value, Class<?> clz) {
		List<String> list = new ArrayList<>();
		for (T t : value) {
			list.add(JSON.toJSONString(t));
		}
		return redisTemplate.opsForList().rightPushAll(key, list);
	}

	/**
	 * list的长度
	 * 
	 * @param key
	 *            key
	 * @return list长度
	 */
	public Long lengthFromList(String key) {
		return redisTemplate.opsForList().size(key);
	}

	/**
	 * 获取key-list的对象
	 * 
	 * @param key
	 *            key
	 * @param clz
	 *            存储对象的class
	 * @return
	 */
	public List<T> getList(String key, Class<?> clz) {
		return rangeFromList(key, 0, -1, clz);
	}

	/**
	 * 搜索位置为start和end之间的对象
	 * 
	 * @param key
	 *            key
	 * @param start
	 *            起始值，从0开始
	 * @param end
	 *            结束值，如果查询全部，该值为-1
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<T> rangeFromList(String key, int start, int end, Class clz) {
		List<String> json = redisTemplate.opsForList().range(key, start, end);
		List<T> list = new ArrayList<>();
		for (String s : json) {
			T t = (T) JSON.parseObject(s, clz);
			list.add(t);
		}
		return list;
	}

	/**
	 * 删除位置为index的对象
	 * 
	 * @param key
	 *            key
	 * @param index
	 *            待删除的对象的位置
	 * @param value
	 *            删除对象的value
	 */
	public void removeFromList(String key, long index, Object value) {
		String v = JSON.toJSONString(value);
		redisTemplate.opsForList().remove(key, index, v);
	}

	/**
	 * 根据index查找
	 * 
	 * @param key
	 *            key
	 * @param index
	 *            待删除的对象的位置
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public T getFromList(String key, long index, Class<?> clz) {
		String json = redisTemplate.opsForList().index(key, index);
		try {
			return (T) JSON.parseObject(json, clz);
		} catch (Exception e) {
			System.out.println("获取redis list后转换JSON错误，key=" + key + ", json="
					+ json);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 替换
	 * 
	 * @param key
	 *            key
	 * @param index
	 *            替换对象的位置
	 * @param value
	 *            替换的对象
	 */
	public void replaceToList(String key, long index, T value) {
		String str = JSON.toJSONString(value);
		redisTemplate.opsForList().set(key, index, str);
	}

	/**
	 * 把字符串值存入redis，使用该方法存入的数据，必须使用getString方法获取
	 * 
	 * @param key
	 *            缓存key
	 * @param value
	 *            缓存值
	 */
	public void setString(String key, String value) {
		redisTemplate.opsForValue().set(key, value);
	}

	/**
	 * 获取字符串值，使用该方法获取的数据，必须使用setString方法存入
	 * 
	 * @param key
	 *            缓存key
	 * @return
	 */
	public String getString(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	/**
	 * 把字符串值存入redis hash
	 * 
	 * @param key
	 *            缓存key
	 * @param hashKey
	 *            hashmap key
	 * @param value
	 *            缓存值
	 */
	public void setStringToHash(String key, String hashKey, String value) {
		redisTemplate.opsForHash().put(key, hashKey, value);
	}

	/**
	 * 从redis hash获取字符串值
	 * 
	 * @param key
	 *            缓存key
	 * @param hashKey
	 *            hashmap key
	 */
	public String getStringFromHash(String key, String hashKey) {
		Object ret = redisTemplate.opsForHash().get(key, hashKey);
		return (String) ret;
	}

	/**
	 * 从redis hash获取字符串值
	 * 
	 * @param key
	 *            缓存key
	 * @param hashKey
	 *            hashmap key
	 */
	public void delStringFromHash(String key, String hashKey) {
		redisTemplate.opsForHash().delete(key, hashKey);

	}

	/**
	 * 设置redis模板
	 * 
	 * @param redisTemplate
	 *            the redisTemplate to set
	 */
	public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 清空缓存
	 * 
	 * @return
	 */
	public String flushDB() {
		return redisTemplate.execute(new RedisCallback<String>() {
			public String doInRedis(RedisConnection connection)
					throws DataAccessException {
				connection.flushDb();
				return "ok";
			}
		});
	}

	/**
	 * 获取剩余时间
	 * 
	 * @param key
	 * @return
	 */
	public long getExpiredTime(String key) {
		return redisTemplate.getExpire(key, TimeUnit.SECONDS);
	}
}
