/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.lcs.service.testcase;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.gy.hsxt.lcs.api.ILCSBaseDataService;
import com.gy.hsxt.lcs.bean.CountryCurrency;
import com.gy.hsxt.lcs.bean.Currency;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.lcs.bean.PayBank;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-lcs-service
 * 
 *  Package Name    : com.gy.hsxt.lcs.service.testcase
 * 
 *  File Name       : BaseDataServiceTestCase.java
 * 
 *  Creation Date   : 2015-8-21
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : TODO
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@FixMethodOrder(MethodSorters.DEFAULT) 
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-global.xml")
public class BaseDataServiceTestCase {
	
	@Autowired
	ILCSBaseDataService baseDataService;
	
	@Test
	public void getLocalInfo(){
		LocalInfo localInfo = baseDataService.getLocalInfo();
		Assert.isTrue(!"".equals(localInfo.getHsbCode()));
	}
	

	@Test
	public void queryPayBankAll(){
		List<PayBank> list =baseDataService.queryPayBankAll();
		PayBank u=list.get(0);
		Assert.isTrue(list.size() >= 1);
	}
	
	@Test
	public void queryPayBankByCode(){
		PayBank payBank  =baseDataService.queryPayBankByCode("0056");
		Assert.isTrue(!"".equals(payBank.getBankCode()));
	}
	
	@Test
	public void queryCurrencyAll(){
		List<Currency>  list =baseDataService.queryCurrencyAll();
		Currency u=list.get(0);
		Assert.isTrue(list.size() >= 1);
	}
	
	@Test
	public void queryCurrencyByCode(){
		Currency currency  =baseDataService.queryCurrencyByCode("CNY");
		Assert.isTrue(!"".equals(currency.getCurrencyNameCn()));
	}
	
	@Test
	public void queryCountryCurrency() {
	   List<CountryCurrency> list=baseDataService.queryCountryCurrency();
	   System.out.println(list);
		Assert.isTrue(list.size()>0);
	}
}
