/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsi.redis.client;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsi.redis.client.api.RedisUtil;

/**
 * 该类用于测试单机redis或主从redis连接方式
 * 
 * @Package:   
 * @ClassName: RedisUtilTest 
 * @Description: TODO
 *
 * @author: lixuan 
 * @date: 2015-10-20 下午2:29:55 
 * @version V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/spring-global.xml" })
public class RedisUtilTest {

	@Resource
	RedisUtil<TestObject> fixRedisUtil;
	@Resource
	RedisUtil<TestObject> changeRedisUtil;
	//@Test
	public void add() {
		// 05221081815
//		changeRedisUtil.remove("w_0522108181520151111");
//		changeRedisUtil.remove("m_0522108181520151111");
//		
//		changeRedisUtil.remove("w_0500108181620151111");
//		changeRedisUtil.remove("m_0500108181620151111");
		
		org.springframework.data.redis.connection.jedis.JedisConnectionFactory jcf;
		RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration() .master("mymaster")
				.sentinel("127.0.0.1", 26379) .sentinel("127.0.0.1", 26380);
		jcf=new JedisConnectionFactory(sentinelConfig);
		 
		
	}
 
	@SuppressWarnings("unchecked")
	@Test
	public void list(){
		changeRedisUtil.remove("test-ch");
		TestObject ch = new TestObject();
		ch.setValue("0012345");
		ch.setId(1);
		
		TestObject ch1 = new TestObject();
		ch1.setValue("0012346");
		ch.setId(2);
		 
		List<TestObject> list = new ArrayList<TestObject>();
		list.add(ch);
		list.add(ch1);

		changeRedisUtil.addList("test-ch", list, TestObject.class);
		try {
			List<TestObject> rs = (List<TestObject>) changeRedisUtil
					.getList("test-ch", TestObject.class);
			System.out.println(JSONObject.toJSONString(rs));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

