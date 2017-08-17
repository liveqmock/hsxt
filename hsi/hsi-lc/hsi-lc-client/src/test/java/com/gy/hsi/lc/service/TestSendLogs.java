package com.gy.hsi.lc.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-test.xml" })
public class TestSendLogs {

	@Test
	public void teseSend() {
		for (int i = 0; i < 1000; i++) {
			SystemLog.info("modole["+i+"]", "method["+i+"]", "this is test ["+i+"]");
			BizLog.biz("modole["+i+"]", "method["+i+"]", "|列值1|列值2", "|恩，你哈|this is test ["+i+"]");
		}
	}
}
