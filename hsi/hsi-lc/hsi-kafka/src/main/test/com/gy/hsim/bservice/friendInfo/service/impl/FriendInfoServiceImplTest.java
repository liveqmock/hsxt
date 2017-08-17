package com.gy.hsim.bservice.friendInfo.service.impl;

import static org.junit.Assert.fail;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gy.hsim.bservice.framework.bean.BaseReponse;
import com.gy.hsim.bservice.friendInfo.bean.QueryFriendReq;
import com.gy.hsim.bservice.friendInfo.service.FriendInfoService;

public class FriendInfoServiceImplTest {

	
	private FriendInfoService friendInfoService;
	
	@Before
	public void setUp() throws Exception {
		ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(new String[] { "spring/spring-global.xml" }); 
		 this.friendInfoService = (FriendInfoService) appContext.getBean("friendInfoService");
	}

//	@Test
	public void testQuery() {
		QueryFriendReq req = new QueryFriendReq();
		
		req.setAccountId("c_06186010001");
		
		List list = friendInfoService.query(req);
		
		Assert.assertNotNull(list);
	}

//	@Test
	public void testCount() {
		
		QueryFriendReq req = new QueryFriendReq();
		
		req.setAccountId("c_06186010001");
		
		req.setFriendId("c_06186010003");
		
		int count = friendInfoService.count(req);
		
		System.out.println(count);
	}

//	@Test
	public void testInsert() {
		
		QueryFriendReq req = new QueryFriendReq();
		
		req.setAccountId("c_06186010001");
		
		req.setFriendId("c_06186010003");
		
		req.setFriendStatus(2);
		
		BaseReponse resp = new BaseReponse();
		
		boolean result = friendInfoService.insert(req,resp);
		
		System.out.println(result);
	}

//	@Test
	public void testQueryWhoAddMeList() {
		QueryFriendReq req = new QueryFriendReq();
		
		req.setAccountId("c_06186010001");
		
		req.setFriendStatus(1);
		
		List list = friendInfoService.queryWhoAddMeList(req);
		
		Assert.assertNotNull(list);
	}

	@Test
	public void testShieldFriendMsg() {
		QueryFriendReq req = new QueryFriendReq();
		
		req.setAccountId("c_06186010001");
		
		req.setFriendId("c_06186010003");
		
		req.setIsShield("1");
		
		BaseReponse resp = new BaseReponse();
		
		boolean result = friendInfoService.shieldFriendMsg(req, resp);
		System.out.println(result);
	}

//	@Test
	public void testQueryShieldFriendMsg() {
		QueryFriendReq req = new QueryFriendReq();
		
		req.setAccountId("c_06186010001");
		
		req.setFriendId("c_06186010003");
		
		
		BaseReponse resp = new BaseReponse();
		
		List list = friendInfoService.query(req);
		Assert.assertNotNull(list);
	}

}
