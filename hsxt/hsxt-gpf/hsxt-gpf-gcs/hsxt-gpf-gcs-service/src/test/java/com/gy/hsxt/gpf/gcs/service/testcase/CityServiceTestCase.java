/***************************************************************************
 * 
 * This document contains confidential and proprietary information
 * subject to non-disclosure agreements with GUIYI Technology, Ltd.
 * This information shall not be distributed or copied without written
 * permission from GUIYI technology, Ltd.
 * 
 ***************************************************************************/

package com.gy.hsxt.gpf.gcs.service.testcase;

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

import com.gy.hsxt.gpf.gcs.bean.City;
import com.gy.hsxt.gpf.gcs.interfaces.ICityService;

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
public class CityServiceTestCase
{
	
	@Autowired
	private ICityService	cityService;
	
	String					test	= "java_test_";
	
	/**
	 * 判断是否已存在相同
	 */
	@Test
	public void existCity()
	{
		try
		{
			boolean bo = cityService.existCity("50", "100", "001");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 插入记录
	 */
	@Test
	public void insert()
	{
		City city = new City();
		city.setCityFullName(test + "FullName");
		city.setCityName(test + "Name");
		city.setCityNameCn(test + "NameCn");
		city.setCityNo("29");
		city.setCountryNo("100");
		city.setPostCode("05");
		city.setProvinceNo("002");
		city.setPhonePrefix("2556");
		city.setVersion(20);
		city.setDelFlag(0);
		int row = cityService.insert(city);
		Assert.isTrue(row == 1);
	}
	
	/**
	 * 读取某条记录
	 */
	public void getCity()
	{
		City city = cityService.getCity("01");
		Assert.isTrue(!"".equals(city.getCityNo()));
	}
	
	/**
	 * 更新某条记录
	 */
	@Test
	public void update()
	{
		City city = new City();
		city.setCityFullName(test + "FullName");
		city.setCityName(test + "Name");
		city.setCityNameCn(test + "NameCn");
		city.setCityNo("09");
		city.setCountryNo("100");
		city.setPostCode("100");
		city.setProvinceNo("001");
		city.setPhonePrefix("2556");
		city.setVersion(20);
		city.setDelFlag(0);
		int row = cityService.update(city);
		Assert.isTrue(row == 1);
	}
	
	/**
	 * 获取列表
	 */
	@Test
	public void getCityListPage()
	{
		City city = new City();
		List<City> list = cityService.getCityListPage(city);
		Assert.isTrue(list.size() >= 1);
	}
	
	/**
	 * 读取某个省份的所以城市记录
	 */
	@Test
	public void queryCityByParent()
	{
		List<City> list = cityService.queryCityByParent("001");
		Assert.isTrue(list.size() >= 1);
	}
	
	/**
	 * 删除某条记录
	 */
	@Test
	public void delete()
	{
		int row = cityService.delete("01");
		Assert.isTrue(row == 1);
	}
	
	/**
	 * 读取大于某个版本号的数据
	 */
	@Test
	public void queryCitySyncData()
	{
		List<City> list = cityService.queryCitySyncData(Long.parseLong("5"));
		Assert.isTrue(list.size() >= 1);
	}
	
}
