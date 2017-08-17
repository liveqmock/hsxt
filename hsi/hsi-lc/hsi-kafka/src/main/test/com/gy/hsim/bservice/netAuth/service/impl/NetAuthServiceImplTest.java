package com.gy.hsim.bservice.netAuth.service.impl;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gy.hsim.bservice.framework.bean.BaseRequest;
import com.gy.hsim.bservice.netAuth.service.NetAuthService;

public class NetAuthServiceImplTest {
	
private NetAuthService netAuthService;
	
	@Before
	public void setUp() throws Exception {
		ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(new String[] { "spring/spring-global.xml" }); 
		 this.netAuthService = (NetAuthService) appContext.getBean("netAuthService");
	}


	@Test
	public void testAuth() {
		
		BaseRequest req = new BaseRequest();
		
		req.setAccessId("10000");
		
		req.setAccessPasswd("1NTdFMEY1MzFGQkFBQ0MwE4RTJ");
		
		boolean result = netAuthService.auth(req);
		
		Assert.assertEquals(result, true);
	}

}
