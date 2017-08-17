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

import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.lcs.bean.Plat;
import com.gy.hsxt.lcs.interfaces.IPlatService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gl-global
 * 
 *  Package Name    : com.gy.hsxt.gl.service
 * 
 *  File Name       : PlatServiceTest.java
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
public class PlatServiceTestCase {
	@Autowired
	private IPlatService platService;


	/**
	 * 读取某条记录
	 */
	public void getPlat() {
		Plat plat = platService.getPlat("01");
		Assert.isTrue(!"".equals(plat.getPlatNo()));
	}

	/**
	 * 批量更新数据
	 * 
	 * @param list
	 * @param version
	 * @return
	 */
//	@Test
	public void addOrUpdatePlat() {
//		List<Plat> list = platService.queryPlatSyncData(Long.parseLong("5"));
//		int row = platService.addOrUpdatePlat(list, Long.parseLong("5"));
//		Assert.isTrue(row == 1);
	}
	
	@Test
    public void testLocalInfo() {
	    LocalInfo localInfo = platService.getLocalInfo();
	    System.out.println(localInfo.getCenterPlatNo());
	   Assert.notNull(localInfo);
    }
}
