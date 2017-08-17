/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.util;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsi.redis.client.api.RedisUtil;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderService;
import com.gy.hsxt.uc.as.api.consumer.IUCAsNoCardHolderService;
import com.gy.hsxt.uc.as.api.operator.IUCAsOperatorService;
import com.gy.hsxt.uc.as.api.sysoper.IUCAsSysOperInfoService;
import com.gy.hsxt.uc.as.bean.consumer.AsCardHolder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-uc.xml" })
public class CacheUtilTest {


	@Autowired
	private IUCAsCardHolderService cardHolderService;

	@Autowired
	private IUCAsNoCardHolderService iUCAsNoCardHolderService;

	@Autowired
	private IUCAsOperatorService operatorService;

	@Autowired
	private IUCAsSysOperInfoService iUCAsSysOperInfoService;
	
	@Resource
	RedisUtil<Object> changeRedisUtil;

	@SuppressWarnings("unchecked")
	//@Test
	public void addList() {
		AsCardHolder ch = new AsCardHolder();
		ch.setPerCustId("0012345");

		AsCardHolder ch1 = new AsCardHolder();
		ch1.setPerCustId("0012346");
		List<Object> list = new ArrayList<Object>();
		list.add(ch);
		list.add(ch1);
		
	}
	
	@Test
	public void testCache() {
		String key="UC.w_0600211171220151207";
		key="haha";
		changeRedisUtil.add(key, "sdfsfsdfsdfsdfsdf873284623qqqeeee");
		String token=changeRedisUtil.getString(key);
		System.out.println("key="+key+",value="+token);
		changeRedisUtil.remove(key);
		
	}
}
