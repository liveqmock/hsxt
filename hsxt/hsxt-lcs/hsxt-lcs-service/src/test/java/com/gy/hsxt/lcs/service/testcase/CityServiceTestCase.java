/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.lcs.service.testcase;

import java.util.ArrayList;
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

import com.gy.hsxt.lcs.bean.City;
import com.gy.hsxt.lcs.interfaces.ICityService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gl-global
 * 
 *  Package Name    : com.gy.hsxt.gl.service
 * 
 *  File Name       : CityServiceTest.java
 * 
 *  Creation Date   : 2015-7-15
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
public class CityServiceTestCase {

	@Autowired
	private ICityService cityService;

	String test = "java_test_";

	/**
	 * 读取某条记录
	 */
	@Test
	public void getCity() {
		City city = cityService.getCity("156","11","110000");
		Assert.isTrue(!"".equals(city.getCityNo()));
	}

	/**
	 * 读取某个省份的所以城市记录
	 */
	@Test
	public void queryCityByParent() {
		List<City> list = cityService.queryCityByParent("156","11");
		System.out.print(list);
		Assert.isTrue(list.size() >= 1);
	}

	/**
	 * 批量更新数据
	 * 
	 * @param list
	 * @param version
	 * @return
	 */
	@Test
	public void addOrUpdateCity() {
		List<City> list = new ArrayList<City>();
		City m = new City();
		m.setCityNo("265");
		m.setCityFullName("sasas");
		m.setCountryNo("100");
		m.setProvinceNo("001");
		m.setCityName("sa");
		m.setVersion(300);
		list.add(m);
		int row = cityService.addOrUpdateCity(list, Long.parseLong("5"));
		Assert.isTrue(row == 1);
	}
}
