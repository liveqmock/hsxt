/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.util;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.gy.hsi.redis.client.api.RedisUtil;

/**
 * 读取redis缓存
 * 
 * @Package: com.gy.hsxt.uc.util
 * @ClassName: CacheUtil
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2015-10-20 下午7:23:48
 * @version V1.0
 * 
 * @param <T>
 */
@Component
public class CacheUtil<T> {

	@Resource
	RedisUtil<T> fixRedisUtil;
	@Resource
	RedisUtil<T> changeRedisUtil;

	/**
	 * 获取存储固定信息的缓存处理类
	 * 
	 * @return the fixRedisUtil
	 */
	public RedisUtil<T> getFixRedisUtil() {
		return fixRedisUtil;
	}

	/**
	 * 获取存储信息常变动的缓存处理类
	 * 
	 * @return the changeRedisUtil
	 */
	public RedisUtil<T> getChangeRedisUtil() {
		return changeRedisUtil;
	}

}
