/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.uc.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.uc.as.api.common.IUCAsPwdQuestionService;
import com.gy.hsxt.uc.as.api.common.IUCAsPwdService;
import com.gy.hsxt.uc.as.bean.common.AsPwdQuestion;
import com.gy.hsxt.uc.as.bean.common.AsResetLoginPwd;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/spring-uc.xml" })
public class AsResetPwdByCrypt {
	@Autowired
	IUCAsPwdQuestionService pwdQuestionService;
	@Autowired
	IUCAsPwdService pwdService;
	@Test
	public void findCustIdByUserName(){
		String resNo = "06002110000";
		String userType = "3";
		String custId = pwdService.findCustIdByUserName(resNo, userType);
	}
	
	@Test
	public void checkCrypt(){
		String custId = "0600211258820151207";
		String usertype = "2";
		AsPwdQuestion question = new AsPwdQuestion();
		question.setQuestion("你猜不到1？");
		question.setAnswer("密码是什么呢1");
		String random = pwdQuestionService.checkPwdQuestion(custId, usertype, question);
	}
	
	@Test
	public void resetLoginPwdByEmail(){
		AsResetLoginPwd resetParams = new AsResetLoginPwd();
		resetParams.setCustId("0600211258820151207");
		resetParams.setNewLoginPwd("U+3HMyGWSyAA6sIJjL+yPP1tPZHxfp4q9bxQJcO9ikA=");
		resetParams.setRandom("26299396");
		resetParams.setSecretKey("ff7c41f6bd3a67b1");
		resetParams.setUserType("2");
		pwdService.resetLoginPwdByCrypt(resetParams);
	}
}
