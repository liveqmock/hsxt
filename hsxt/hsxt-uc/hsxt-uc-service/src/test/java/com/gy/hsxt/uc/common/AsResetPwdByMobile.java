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

import com.gy.hsxt.uc.as.api.common.IUCAsMobileService;
import com.gy.hsxt.uc.as.api.common.IUCAsPwdService;
import com.gy.hsxt.uc.as.bean.common.AsResetLoginPwd;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/spring-uc.xml" })
public class AsResetPwdByMobile {
	@Autowired
	IUCAsPwdService pwdService;
	@Autowired
	IUCAsMobileService mobileService;
	@Test
	public void findCustIdByUserName(){
		String resNo = "00000000156";
		String userType = "3";
		String custId = pwdService.findCustIdByUserName(resNo, "0006", userType);
	}
	@Test
	public void sendSmsCodeForResetPwd(){
//		 http://192.168.229.182:8080/reconsitution/findPwdController/
//		 sendCode?entUsername=0000&hsResNo=06007010000&mobile=13699836954&userType=3
		String custId = "00000000156000620160107";
		String userType = "3";
		String mobile = "15814759813";
//		mobileService.sendSmsCodeForResetPwd(custId, userType, mobile);
	}
	@Test
	public void checkSmsCode(){
		String mobile = "15814759813";
		String smsCode = "656927";
	//	mobileService.checkSmsCode(mobile, smsCode);
	}
	@Test
	public void resetLoginPwdByMobile(){
		AsResetLoginPwd resetParams = new AsResetLoginPwd();
		resetParams.setCustId("0600211814120151207");
		resetParams.setMobile("15814759813");
		resetParams.setNewLoginPwd("XUnhonZA0OFaWq+/3CAQOQ==");
		resetParams.setRandom("129883");
		resetParams.setSecretKey("8fa40735884e64b7");
		resetParams.setUserType("2");
		pwdService.resetLoginPwdByMobile(resetParams);
		
	}
}
