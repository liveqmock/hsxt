/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsi.nt.server.test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsi.nt.api.beans.NoteBean;
import com.gy.hsi.nt.api.exception.NoticeException;
import com.gy.hsi.nt.api.service.INtQueryService;
import com.gy.hsi.nt.api.service.INtService;

@FixMethodOrder(MethodSorters.DEFAULT)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:dev-config/spring-context.xml")
// @ContextConfiguration(locations = "classpath:test/spring-context.xml")
public class NtTest {

	@Autowired
	INtService ntService;

	@Autowired
	INtQueryService ntQueryService;

	String hsResNo = "06007030000";
	String custName = "浮世托管企业";

	// 发送短信
	@Test
	public void sendNote()
	{
		NoteBean bean = new NoteBean();
		bean.setMsgId(UUID.randomUUID().toString());
		bean.setHsResNo(hsResNo);
		bean.setMsgReceiver(new String[]
		{ "13530501387" });
		Map<String, String> content = new HashMap<String, String>();
		content.put("{0}", "13246");
		content.put("{1}", "120");
		bean.setContent(content);
		bean.setCustType(3);
		bean.setCustName(custName);
		bean.setBizType("101");
		bean.setSender("systemPlat");
		try
		{
			ntService.sendNote(bean);
		} catch (NoticeException e)
		{
			e.printStackTrace();
		}
	}
}
