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

import org.junit.After;
import org.junit.Before;
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

import com.gy.hsxt.lcs.bean.BizRoute;
import com.gy.hsxt.lcs.bean.Subsys;
import com.gy.hsxt.lcs.interfaces.IBizRouteService;
import com.gy.hsxt.lcs.interfaces.ISubsysService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-lcs-service
 * 
 *  Package Name    : com.gy.hsxt.lcs.service.testcase
 * 
 *  File Name       : BizRouteServiceTestCase.java
 * 
 *  Creation Date   : 2SYS_TEST5-7-17
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 测试BizRouteService
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/

@FixMethodOrder(MethodSorters.DEFAULT) 
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-global.xml")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class BizRouteServiceTestCase {
	
	@Autowired
	IBizRouteService bizRouteService;
	
	@Autowired
	ISubsysService subSysService;
	
	private BizRoute bizRoute;
	private Subsys subsys;
	
	String bizCode = "BIZ_CODE_TEST";
	String subSysCode = "SYS_TEST";
	String bizNameCn = "BIZ_NAME_CN_TEST";
	
	String bizCode02 = "BIZ_CODE_TEST_02";
	String bizNameCn02 = "BIZ_NAME_CN_TEST_02";
	
	
	
	
	@Before
	public void setUp() throws Exception {
		bizRoute = new BizRoute(bizCode);
		bizRoute.setSubSysCode(subSysCode);
		bizRoute.setBizNameCn(bizNameCn);
		bizRoute.setDelFlag(false);
		
		subsys = new Subsys();
		subsys.setSubsysCode(subSysCode);
		
		List<Subsys> subsysList = new ArrayList<Subsys>();
		subsysList.add(subsys);
		subSysService.addOrUpdateSubsys(subsysList, new Long(99));
	}

	@After
	public void tearDown() throws Exception {
	}
	
	
	@Test
	public void queryBizRouteWithPK(){
		Assert.isTrue(null==bizRouteService.queryBizRouteWithPK(bizCode));
		
		List<BizRoute> bizRouteList = new ArrayList<BizRoute>();
		bizRouteList.add(bizRoute);
		bizRouteService.addOrUpdateBizRoute(bizRouteList, new Long(99));
		Assert.notNull(bizRouteService.queryBizRouteWithPK(bizCode));
	}
	
	@Test
	public void addOrUpdateBizRoute(){
		List<BizRoute> bizRouteList = new ArrayList<BizRoute>();
		bizRouteList.add(bizRoute);
		BizRoute bizRoute2 = new BizRoute(bizCode02);
		bizRoute2.setSubSysCode(subSysCode);
		bizRoute2.setBizNameCn(bizNameCn02);
		bizRouteList.add(bizRoute2);
		
		int flag = bizRouteService.addOrUpdateBizRoute(bizRouteList, new Long(99));
		Assert.isTrue(1==flag);
		Assert.notNull(bizRouteService.queryBizRouteWithPK(bizCode));
		Assert.notNull(bizRouteService.queryBizRouteWithPK(bizCode02));
	}
}
