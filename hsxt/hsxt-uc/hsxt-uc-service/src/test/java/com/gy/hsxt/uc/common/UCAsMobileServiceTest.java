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

import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.uc.as.api.common.IUCAsMobileService;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderService;
import com.gy.hsxt.uc.cache.service.CommonCacheService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/spring-uc.xml" })
public class UCAsMobileServiceTest {

	@Autowired
	IUCAsMobileService mobileService;
	@Autowired
	IUCAsCardHolderService cardHolderService;
	@Autowired
	CommonCacheService commonCacheService;
	@Test
	public void sendSmsCodeForValidMobile() {
		String email = "tianxh@gyist.com";
        String userName = "0000";
        String entResNo = "06015000001";
        String mobile = "15814759813";
        String secretKey = "7b8aea4fbdf8db98";
        String loginPassword = "orywJlpuYwMblUrCxPCcng==";
		
		
		String entCustId = "";
		// 非持卡人注册短信
//		mobileService.sendSmsCodeForResetPwd("0601500000100000000", UserTypeEnum.no.getType(), "15814759813", CustType.MEMBER_ENT.getCode());
//		mobileService.sendSmsCodeForValidMobile("", "15814759813", CustType.NOT_HS_PERSON.getCode());
//		mobileService.bindMobile("0600211814120151207", "15814759813", "515703", UserTypeEnum.CARDER.getType());
//		mobileService.sendAuthCodeSuccess("06001000006", CustType.MEMBER_ENT.getCode());
//		
	}
	
	@Test
	public void sendSmsCodeForRegNoCardHolder(){
	   String mobile = "15814759813";
//	   mobileService.sendSmsCodeForValidMobile("905175622882754560", mobile, CustType.NOT_HS_PERSON.getCode());
	   mobileService.sendSmsCodeForValidMobile("0604611442220160418", mobile, CustType.PERSON.getCode());
//	   mobileService.sendSmsCodeForResetPwd("905176245738597376", UserTypeEnum.NO_CARDER.getType(), mobile, CustType.NOT_HS_PERSON.getCode());
//	   mobileService.sendSmsCodeForResetPwd("0600211814120151207", UserTypeEnum.CARDER.getType(), mobile, CustType.PERSON.getCode());
//	   mobileService.sendSmsCodeForResetPwd("06001000006000020151231", UserTypeEnum.ENT.getType(), mobile, CustType.MEMBER_ENT.getCode());
//	   mobileService.sendSmsCodeForResetPwd("06010160000000020160128", UserTypeEnum.ENT.getType(), mobile, CustType.TRUSTEESHIP_ENT.getCode());
//	   mobileService.sendSmsCodeForResetPwd("02000000000000020151214", UserTypeEnum.ENT.getType(), mobile, CustType.SERVICE_CORP.getCode());
//	   mobileService.sendSmsCodeForResetPwd("080000000002015122220160414", UserTypeEnum.ENT.getType(), mobile, CustType.MANAGE_CORP.getCode());
//	   mobileService.sendSmsCodeForResetPwd("00000000156163438271051776", UserTypeEnum.ENT.getType(), mobile, CustType.AREA_PLAT.getCode());
	}
	
	 @Test
	  public void bindMobile(){
		 String mobile = "15814759813";
		 String code = "111222";
		 commonCacheService.addSmsCodeCache(mobile, code);
//		  String custId = "0604611442220160418";
//		  String mobile = "15814759813";
//		  String  smsCode = "059669";
//		  String userType = "2";
//		  mobileService.bindMobile(custId, mobile, smsCode, userType);
	  }

	@Test
	public void sendSmsCodeForResetPwd(){
	       String userName = "06002118140";
	        String entResNo = "";
	        String userType = "2";
//	    mobileService.sendSmsCodeForResetPwd(userType, userName, entResNo);
	    
	}

	
	@Test
	public void sendAuthCodeSuccess(){
	    mobileService.sendAuthCodeSuccess("06001000008", CustType.SERVICE_CORP.getCode());
	}
}
