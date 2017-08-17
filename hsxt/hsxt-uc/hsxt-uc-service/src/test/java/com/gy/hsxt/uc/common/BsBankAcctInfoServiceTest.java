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

import com.gy.hsxt.uc.bs.api.common.IUCBsBankAcctInfoService;
import com.gy.hsxt.uc.bs.bean.common.BsBankAcctInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/spring-uc.xml" })
public class BsBankAcctInfoServiceTest {
	@Autowired
	IUCBsBankAcctInfoService bankAcctInfoService;
	
	@Test
	public void findBankAccByAccId(){
		long accId = 11;
		String userType = "3";
		BsBankAcctInfo  bsBankAcctInfo = bankAcctInfoService.findBankAccByAccId(accId, userType);
		accId = 4;
		userType = "2";
		bsBankAcctInfo = bankAcctInfoService.findBankAccByAccId(accId, userType);
	}
	
	@Test
	public void searchDefaultBankAcc(){
		String custId = "00000000156163438270977024";
		String userType = "3";
		BsBankAcctInfo  bsBankAcctInfo = bankAcctInfoService.searchDefaultBankAcc(custId, userType);
	}
	
	@Test
	public void searchDefaultBankAccResNo(){
		String resNo = "00000000156";
		BsBankAcctInfo  bsBankAcctInfo = bankAcctInfoService.searchDefaultBankAcc(resNo);
	}
	

}
