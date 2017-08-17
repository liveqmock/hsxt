/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/

package com.gy.hsxt.service.testcase;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.ac.api.IAccountEntryService;
import com.gy.hsxt.ac.bean.AccountEntry;
import com.gy.hsxt.ac.bean.AccountWriteBack;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.bean.ent.AsEntInfo;

/** 
 * 
 * @Package: cn.springmvc.controller  
 * @ClassName: AccountEntryServiceTest 
 * @Description: TODO
 *
 * @author: weixz 
 * @date: 2015-9-9 下午4:49:14 
 * @version V1.0 
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/spring/spring-global.xml"})
public class AccountEntryServiceTest {

	@Autowired
	private IAccountEntryService iAccountEntryService;
	
	@Autowired
	private IUCAsEntService entService;
	public static int line = 100000;
	
	

	@Test
	public void addaccountEntryList() {

		List<AccountEntry> acServiceList = new ArrayList<AccountEntry>();
		AccountEntry acService = new AccountEntry();
		acService.setCustID("0600211172520151207");
		acService.setBatchNo("20160128");
		acService.setHsResNo("06002111725");
		acService.setAccTypes("20120|20110");
		acService.setCustType(1);
		acService.setWriteBack(0);
		acService.setSubAmount("50");
		acService.setTransType("A10000");
		acService.setTransNo("A10000999999999999");
		acService.setSysEntryNo("A100009999999999991");
		acService.setTransDate("2016-01-28 15:30:30");
		acService.setFiscalDate("2016-01-28 15:30:30");
		acService.setGuaranteeIntegral("10000000");
		acServiceList.add(acService);
		
		AccountWriteBack accountWriteBack = new AccountWriteBack();
		accountWriteBack.setRelTransNo("110120160329183251000000");
		accountWriteBack.setTransType("F21110");
		accountWriteBack.setWriteBack(1);
		
//		iAccountEntryService.deductAccount(acServiceList);
		iAccountEntryService.correctSingleAccount(accountWriteBack);
		
//		AsEntInfo asEntInfo = entService.searchRegionalPlatform();
		
//		System.out.println(asEntInfo.getEntCustId());
//		System.out.println(asEntInfo.getEntResNo());
		
	}
	
	@Test
    public void chargebackAccountTest() {
	    List<AccountEntry> acServiceList = new ArrayList<AccountEntry>();
        AccountEntry acService = new AccountEntry();
        acService.setCustID("0603199000120160416");
        acService.setBatchNo("2016-04-21");
        acService.setHsResNo("06031990001");
        acService.setAccTypes("20120|20110");
        acService.setCustType(1);
        acService.setWriteBack(0);
        acService.setAddAmount("10");
        acService.setTransType("A10000");
        acService.setTransNo("A10000999999999999");
        acService.setSysEntryNo("A100009999999999991");
        acService.setRelTransNo("100120160421093448000000");
        acService.setRelSysEntryNo("100120160421093448000005");
        acService.setTransDate("2016-01-28 15:30:30");
        acService.setFiscalDate("2016-01-28 15:30:30");
//        acService.setGuaranteeIntegral("10000000");
        acServiceList.add(acService);
        
        iAccountEntryService.chargebackAccount(acServiceList);
	}
	
}
