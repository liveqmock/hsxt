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

import com.gy.hsxt.gpf.gcs.bean.Province;
import com.gy.hsxt.gpf.gcs.interfaces.IProvinceService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gl-global
 * 
 *  Package Name    : com.gy.hsxt.gl.service
 * 
 *  File Name       : ProvinceServiceTest.java
 * 
 *  Creation Date   : 2015-7-17
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
public class ProvinceServiceTestCase {
	@Autowired
	private IProvinceService provinceService;

	/**
	 * 判断是否已存在相同
	 */
	@Test
	public void existProvince() {
		boolean bo = provinceService.existProvince("29");
		Assert.isTrue(!bo);
	}

	/**
	 * 插入记录
	 */
	@Test
	public void insert() {
		Province plat = new Province();
		plat.setCountryNo("100");
		plat.setProvinceName("ysa");
		plat.setProvinceNameCn("NameCn");
		plat.setProvinceNo("29");
		plat.setVersion(20);
		plat.setDelFlag(0);
		plat.setDirectedCity(1);
		int row = provinceService.insert(plat);
		Assert.isTrue(row == 1);
	}

	/**
	 * 读取某条记录
	 */
	public void getProvince() {
		Province plat = provinceService.getProvince("01");
		Assert.isTrue(!"".equals(plat.getProvinceNo()));
	}

	/**
	 * 更新某条记录
	 */
	@Test
	public void update() {
		Province plat = new Province();
		plat.setCountryNo("100");
		plat.setProvinceName("ysa");
		plat.setProvinceNameCn("NameCn");
		plat.setProvinceNo("001");
		plat.setVersion(20);
		plat.setDelFlag(0);
		plat.setDirectedCity(1);
		int row = provinceService.update(plat);
		Assert.isTrue(row == 1);
	}

	/**
	 * 获取列表
	 */
	@Test
	public void getProvinceListPage() {
		Province plat = new Province();
		List<Province> list = provinceService.getProvinceListPage(plat);
		Assert.isTrue(list.size() >= 1);
	}

	/**
	 * 删除某条记录
	 */
	@Test
	public void delete() {
		int row = provinceService.delete("001");
		Assert.isTrue(row == 1);
	}

	/**
	 * 读取大于某个版本号的数据
	 */
	@Test
	public void queryProvinceSyncData() {
		List<Province> list = provinceService.queryProvinceSyncData(Long
				.parseLong("5"));
		Assert.isTrue(list.size() >= 1);
	}

}
