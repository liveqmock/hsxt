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

import com.gy.hsxt.lcs.bean.Country;
import com.gy.hsxt.lcs.interfaces.ICountryService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gl-global
 * 
 *  Package Name    : com.gy.hsxt.gl.service
 * 
 *  File Name       : CountryServiceTest.java
 * 
 *  Creation Date   : 2015-7-16
 * 
 *  Author          : liuhq
 * 
 *  Purpose         : TODO
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-global.xml")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class CountryServiceTestCase {

	@Autowired
	private ICountryService countryService;
	String test = "java_test_";


	/**
	 * 读取某条记录
	 */
	@Test
	public void getCountry() {
		Country city = countryService.getCountry("101");
		Assert.isTrue(!"".equals(city.getCountryNo()));
	}


	/**
	 * 获取下拉菜单列表
	 */
	@Test
	public void findContryAll() {
		List<Country> list = countryService.findContryAll();
		Assert.isTrue(list.size() >= 1);
	}



	/**
	 * 批量更新数据
	 */
	@Test
	public void addOrUpdateCountry() {
		List<Country> list = countryService.findContryAll();
		int row = countryService.addOrUpdateCountry(list, Long.parseLong("5"));
		Assert.isTrue(row == 1);
	}
}
