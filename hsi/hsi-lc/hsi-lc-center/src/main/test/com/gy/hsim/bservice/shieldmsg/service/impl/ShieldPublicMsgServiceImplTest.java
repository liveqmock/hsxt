package com.gy.hsim.bservice.shieldmsg.service.impl;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gy.hsim.bservice.framework.bean.ErrorMsg;
import com.gy.hsim.bservice.shieldmsg.bean.PublicNOShieldReq;
import com.gy.hsim.bservice.shieldmsg.restful.SheldPublicMsgController;
import com.gy.hsim.bservice.shieldmsg.service.ShieldPublicMsgService;

public class ShieldPublicMsgServiceImplTest {

	private ShieldPublicMsgService shieldMsgService;
	
	@Before
	public void setUp() throws Exception {
		ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(new String[] { "spring/spring-global.xml" }); 
		 this.shieldMsgService = (ShieldPublicMsgService) appContext.getBean("shieldPublicMsgService");
	}

	@Test
	public void testUpdate() {
		PublicNOShieldReq req = new PublicNOShieldReq();
		
		req.setAccountId("nc_01051000003");
		
		req.setGroupId("0105100000320141203");
		
		req.setIsShield("1");
		
		ErrorMsg msg = new ErrorMsg();
		
		boolean result = shieldMsgService.update(req, msg);
		
		Assert.assertEquals(result, true);
		
	}
	
	

}
