/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.lcs.service.testcase;

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

import com.gy.hsxt.lcs.bean.Province;
import com.gy.hsxt.lcs.interfaces.IProvinceService;

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
	 * 读取某条记录
	 */
	public void getProvince() {
		Province province = provinceService.getProvince("156","11");
		System.out.println(province);
		Assert.isTrue(!"".equals(province.getProvinceNo()));
	}

	/**
	 * 批量更新数据
	 * 
	 * @param list
	 * @param version
	 * @return
	 */
	@Test
	public void addOrUpdateProvince() {
//		List<Province> list = provinceService.queryProvinceSyncData(Long
//				.parseLong("5"));
//		int row = provinceService.addOrUpdateProvince(list,
//				Long.parseLong("5"));
//		Assert.isTrue(row == 1);
	}
}
