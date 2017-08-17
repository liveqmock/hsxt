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
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.gy.hsxt.lcs.bean.CountryCurrency;
import com.gy.hsxt.lcs.interfaces.ICountryCurrencyService;

/**
 * 
 * 六种常用货币信息测试用例
 * @Package: com.gy.hsxt.lcs.service.testcase  
 * @ClassName: CountryCurrencyServiceTestCase 
 * @Description: TODO
 *
 * @author: liyh 
 * @date: 2015-12-11 下午2:22:20 
 * @version V1.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-global.xml")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class CountryCurrencyServiceTestCase {

	@Autowired
	private ICountryCurrencyService countryCurrencyService;


	@Test
	public void queryCountryCurrency() {
	   List<CountryCurrency> list=countryCurrencyService.queryCountryCurrency();
	   Assert.isTrue(list.size()>0);
	}
}
