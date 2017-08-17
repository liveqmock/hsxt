/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.common;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsi.redis.client.api.RedisUtil;
import com.gy.hsxt.uc.as.api.common.IUCAsTokenService;
import com.gy.hsxt.uc.as.api.util.UcCacheKey;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-uc.xml" })
public class ASTokenServiceTest {

	@Autowired
	IUCAsTokenService ucAsTokenService;
	@Resource
	RedisUtil changeRedisUtil;

	@Test
	public void testIsLogin() throws Exception {
		long begin=System.currentTimeMillis();
		try {
			String custId="00000000156778820160129";
			String randomToken = ucAsTokenService.getRandomToken(custId);
			boolean ret=ucAsTokenService.checkRandomToken(custId, randomToken);
			System.out.println(randomToken+" HHHHHHHHHHHH "+ret);
			
			
			String key = UcCacheKey.genRandomTokenKey(custId, randomToken);
			boolean ret2=changeRedisUtil.isExists(key, true);
			
			System.out.println(randomToken+" HHHHHHHHHHHH "+ret2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		long end=System.currentTimeMillis();
		System.out.println();
		System.out.println(" =================  ");
		System.out.println(" ================= cost "+ (end-begin));
		System.out.println(" =================  ");
		System.out.println();
		Thread.sleep(1111111);
	}
}
