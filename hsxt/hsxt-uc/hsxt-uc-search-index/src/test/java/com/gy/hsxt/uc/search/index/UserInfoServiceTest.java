/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.search.index;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.search.api.IUCUserInfoService;
import com.gy.hsxt.uc.search.bean.SearchUserInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/spring-solr-server.xml" })
public class UserInfoServiceTest {

	@Autowired
	IUCUserInfoService userInfoService;

	@Test
	public void add() {
		SearchUserInfo user = new SearchUserInfo();
		user.setCustId("123456");
		user.setMobile("13425421212");
		user.setNickName("hello");
		user.setResNo("05001081515");
		user.setUserType(1);
		try {
		//	userInfoService.add(user);
		} catch (HsException e) {
			e.printStackTrace();
		}
	}

	//@Test
	public void update() {
		SearchUserInfo user = new SearchUserInfo();
		user.setCustId("123456");
		user.setMobile("13425421212");
		user.setNickName("amy");
		user.setResNo("05001081515");
		user.setUserType(1);
		try {
			userInfoService.update(user);
		} catch (HsException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(){
		try {
			userInfoService.delete("123456");
		} catch (HsException e) {
			e.printStackTrace();
		}
	}
}
