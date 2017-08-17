

package com.gy.hsxt.service.testcase;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.ac.api.IAccountEntryService;
import com.gy.hsxt.ac.bean.AccountEntry;
import com.gy.hsxt.ac.bean.AccountEntryInfo;
import com.gy.hsxt.ac.bean.AccountService;
import com.gy.hsxt.ac.bean.AccountServiceInfo;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.exception.HsException;

/**
 *  @Project Name    : hsxt-ac-service
 *  @Package Name    : com.gy.hsxt.service.testcase
 *  @File Name       : AccountEntryTestCase.java
 *  @Author          : weixz
 *  @Description     : 测试类
 *  @Creation Date   : 2015-8-26
 *  @version V1.0
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/spring/spring-global.xml"})
public class AccountEntryTestCase {
	@Autowired
	public IAccountEntryService iAccountEntryService;
	
	@Test
	public void test() throws java.text.ParseException{
		
		List<AccountService> accountServiceList = new ArrayList<AccountService>();
		AccountService accountService = new AccountService();
		accountService.setAccTypes("20110|20450");
		accountService.setAmount("10");
		accountService.setCustID("0608411000020160314");
		accountService.setCustType(3);
		accountService.setHsResNo("06084110000");
		accountService.setTransNo("2793411908453376");
		accountService.setTransSys("HSEC-TMS");
		accountService.setTransType("510000");
		accountServiceList.add(accountService);
		try{
		    System.out.println(JSON.toJSONString(accountServiceList));
//			iAccountEntryService.actualAccount(accountEntryList);
//		    iAccountEntryService.deductAccount(accountEntryList);
//		    iAccountEntryService.chargebackAccount(accountEntryList);
//		    iAccountEntryService.correctAccounts(accountEntryList);
		    List<AccountServiceInfo> list = iAccountEntryService.chargeServiceAccount(accountServiceList);
//			iAccountEntryService.correctAccount(accountEntryList);
//			iAccountEntryService.correctSingleAccount(accountEntry);
//			iAccountEntryService.obligateAccount(accountEntryList);
//			iAccountEntryService.releaseAccount(accountEntryList);
//			iAccountEntryService.frozenAccount(accountEntryList);
//          iAccountEntryService.thawAccount(accountEntryList);
		    System.out.println(JSON.toJSONString(list));
		}catch(HsException ex){
			fail("ErrorCode:" + ex.getErrorCode() + " ErrorMessage:" + ex.getMessage() + "\n" + ex.getStackTrace());
		}catch(Exception e){
		    e.getMessage();
		    throw e;
		}
	
	}
}
