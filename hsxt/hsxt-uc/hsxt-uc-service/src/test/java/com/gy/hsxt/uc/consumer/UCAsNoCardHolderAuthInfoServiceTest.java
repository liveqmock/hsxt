package com.gy.hsxt.uc.consumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.uc.as.bean.consumer.AsRealNameAuth;
import com.gy.hsxt.uc.as.bean.consumer.AsRealNameReg;
import com.gy.hsxt.uc.consumer.service.UCAsNoCardHolderAuthInfoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/spring-uc.xml" })
public class UCAsNoCardHolderAuthInfoServiceTest {
	
	@Autowired
	UCAsNoCardHolderAuthInfoService noCardHolderAuthInfoService;
	
	@SuppressWarnings("unused")
	@Test
	public void findAuthStatus(){
		String perCustId = "999882958095751352";
		String authStatus = noCardHolderAuthInfoService.findAuthStatusByCustId(perCustId);
	}

	@Test
	public void regByRealName(){
		AsRealNameReg realNameReg = new AsRealNameReg();
		realNameReg.setCustId("905176008085064704");
		realNameReg.setCerNo("433127198206095910");
		realNameReg.setCertype("1");
		realNameReg.setCountryCode("86");
		realNameReg.setRealName("田学化");
		noCardHolderAuthInfoService.regByRealName(realNameReg);
	}
	
	@SuppressWarnings("unused")
	@Test
	public void searchRealNameAuthInfo(){
		String perCustId = "999322668364276918";
		AsRealNameAuth realNameAuth = noCardHolderAuthInfoService.searchRealNameAuthInfo(perCustId);
	}
	
	@SuppressWarnings("unused")
	@Test
	public void listAuthStatus(){
		List<String> list = new ArrayList<String>();
		list.add("999322668364276918");
		list.add("999882958095751352");
		Map<String, String> map = noCardHolderAuthInfoService.listAuthStatus(list);
	}
	
	
}
