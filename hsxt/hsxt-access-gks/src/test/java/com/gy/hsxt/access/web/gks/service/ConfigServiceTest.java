/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.gks.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/spring-consumer.xml" })
public class ConfigServiceTest {
	@Autowired
	IMaintConfigService configService;
	
	@Test
	public void queryAfterSecretKeyConfigByPage(){
		Page page = new Page(1,5);
		//PageData data = configService.queryAfterSecretKeyConfigByPage(null, null, "06002110000164063559726080", page);
		
		Object o = configService.queryAfterSecretKeyConfigByPage(null, null, "06002110000164063559726080", page);
		System.out.println("-----------" + o);
	}
	
	
	
}
