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
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.uc.as.api.common.IUCAsPwdQuestionService;
import com.gy.hsxt.uc.as.api.common.IUCAsPwdService;
import com.gy.hsxt.uc.as.bean.common.AsPwdQuestion;
import com.gy.hsxt.uc.as.bean.common.AsResetLoginPwd;
import com.gy.hsxt.uc.common.service.UCAsEmailService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/spring-uc.xml" })
public class AsResetPwdByEmail {
	@Autowired
	IUCAsPwdQuestionService questionService;
	@Autowired
	IUCAsPwdService pwdService;
	@Autowired UCAsEmailService emailService;
	@Test
	public void findCustIdByUserName(){
		String resNo = "06002111708";
		String userType = "2";
		String custId = pwdService.findCustIdByUserName(resNo, userType);
	}

	@Test
	public void checkMail(){
		String custId = "06002110000164063559726080";
		String usertype = "3";
		AsPwdQuestion question = new AsPwdQuestion();
		String validCode = "863118";
	//	questionService.checkPwdQuestion(custId, usertype, question);
	}
	@Test
	public void resetLoginPwdByEmail(){
		AsResetLoginPwd resetParams = new AsResetLoginPwd();
		resetParams.setCustId("06002110000164063559726080");
		resetParams.setEmail("tianxh@gyist.com");
		resetParams.setNewLoginPwd("U+3HMyGWSyAA6sIJjL+yPP1tPZHxfp4q9bxQJcO9ikA=");
		resetParams.setRandom("863118");
		resetParams.setSecretKey("ff7c41f6bd3a67b1");
		resetParams.setUserType("3");
		pwdService.resetLoginPwdByEmail(resetParams);
		
	}
	
	
	
	@Test
	public void sendLogs(){
		for(int i=0;i<10000;i++){
			SystemLog.info("test", "test method", "this is test["+i+"]");
		}
	}
}
