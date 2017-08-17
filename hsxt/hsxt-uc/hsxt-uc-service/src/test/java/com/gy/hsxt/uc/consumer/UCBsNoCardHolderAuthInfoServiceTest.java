package com.gy.hsxt.uc.consumer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.uc.as.bean.consumer.AsRealNameReg;
import com.gy.hsxt.uc.bs.bean.consumer.BsRealNameAuth;
import com.gy.hsxt.uc.consumer.service.UCBsNoCardHolderAuthInfoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/spring-uc.xml" })
public class UCBsNoCardHolderAuthInfoServiceTest {
	@Autowired
	UCBsNoCardHolderAuthInfoService noCardHolderAuthInfoService;
	
	
	@Test
	public void authByRealName(){
		BsRealNameAuth bsRealNameAuth = new BsRealNameAuth();
		bsRealNameAuth.setCustId("999162201803825152");
		bsRealNameAuth.setCerNo("433127198206095811");
		bsRealNameAuth.setCountryCode("86");
		bsRealNameAuth.setUserName("猪头");
		bsRealNameAuth.setCerType(1);
		bsRealNameAuth.setBirthAddress("爱时尚");
		bsRealNameAuth.setCerPica("/opt/121789");
		bsRealNameAuth.setCerPicb("/opt/123");
		bsRealNameAuth.setCerPich("/opt/456");
		bsRealNameAuth.setIssuePlace("爱啥啥");
		bsRealNameAuth.setIssuingOrg("沙河口设计");
		bsRealNameAuth.setJob("ask");
		bsRealNameAuth.setRealNameStatus("3");
		bsRealNameAuth.setSex(1);
		bsRealNameAuth.setValidDate("201-01-01 12:12:12");
		noCardHolderAuthInfoService.authByRealName(bsRealNameAuth, "123123");
	}
	
	@SuppressWarnings("unused")
	@Test
	public void searchRealNameAuthInfo(){
		BsRealNameAuth realNameAuth = noCardHolderAuthInfoService.searchRealNameAuthInfo("999162201803825152");
	}
	
	@SuppressWarnings("unused")
	@Test
	public void findAuthStatusByCustId(){
		String status = noCardHolderAuthInfoService.findAuthStatusByCustId("999162201803825152");
	}
	
	@Test
	public void updateRealNameAuthInfo(){
		BsRealNameAuth bsRealNameAuth = new BsRealNameAuth();
		bsRealNameAuth.setCustId("999162201803825152");
		bsRealNameAuth.setCerNo("433127198206095811");
		bsRealNameAuth.setCountryCode("86");
		bsRealNameAuth.setUserName("升级为猪头队长");
		bsRealNameAuth.setCerType(1);
		bsRealNameAuth.setBirthAddress("爱时尚");
		bsRealNameAuth.setCerPica("/opt/121789");
		bsRealNameAuth.setCerPicb("/opt/123");
		bsRealNameAuth.setCerPich("/opt/456");
		bsRealNameAuth.setIssuePlace("爱啥啥");
		bsRealNameAuth.setIssuingOrg("沙河口设计");
		bsRealNameAuth.setJob("ask");
		bsRealNameAuth.setRealNameStatus("3");
		bsRealNameAuth.setSex(1);
		bsRealNameAuth.setValidDate("201-01-01 12:12:12");
		noCardHolderAuthInfoService.updateRealNameAuthInfo(bsRealNameAuth, "456456");
	}
	
	@Test
	public void updateMainInfoApplyInfo(){
		BsRealNameAuth bsRealNameAuth = new BsRealNameAuth();
		bsRealNameAuth.setCustId("999162201803825152");
		bsRealNameAuth.setCerNo("433127198206095811");
		bsRealNameAuth.setCountryCode("86");
		bsRealNameAuth.setUserName("猎个痛快");
		bsRealNameAuth.setCerType(1);
		bsRealNameAuth.setBirthAddress("爱时尚");
		bsRealNameAuth.setCerPica("/opt/121789");
		bsRealNameAuth.setCerPicb("/opt/123");
		bsRealNameAuth.setCerPich("/opt/456");
		bsRealNameAuth.setIssuePlace("爱啥啥");
		bsRealNameAuth.setIssuingOrg("沙河口设计");
		bsRealNameAuth.setJob("ask");
		bsRealNameAuth.setRealNameStatus("3");
		bsRealNameAuth.setSex(1);
		bsRealNameAuth.setValidDate("201-01-01 12:12:12");
		noCardHolderAuthInfoService.updateMainInfoApplyInfo(bsRealNameAuth, "987123");
	}
	

	
	
}
