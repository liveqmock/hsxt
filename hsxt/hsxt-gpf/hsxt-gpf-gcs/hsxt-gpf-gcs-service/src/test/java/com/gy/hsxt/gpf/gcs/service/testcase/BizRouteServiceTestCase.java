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

import com.gy.hsxt.gpf.gcs.bean.BizRoute;
import com.gy.hsxt.gpf.gcs.bean.Subsys;
import com.gy.hsxt.gpf.gcs.bean.Version;
import com.gy.hsxt.gpf.gcs.common.Constant;
import com.gy.hsxt.gpf.gcs.interfaces.IBizRouteService;
import com.gy.hsxt.gpf.gcs.interfaces.ISubsysService;
import com.gy.hsxt.gpf.gcs.interfaces.IVersionService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.service.testcase
 * 
 *  File Name       : BizRouteServiceTestCase.java
 * 
 *  Creation Date   : 2015-7-17
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
	
	@Autowired
	IVersionService versionService;
	
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
		
		subSysService.insert(subsys);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	
	public void existBizRoute(){
		Assert.isTrue(!bizRouteService.existBizRoute(bizCode));
		
		bizRouteService.addBizRoute(bizRoute);
		Assert.isTrue(bizRouteService.existBizRoute(bizCode));
	}
	
	@Test
	public void queryBizRouteWithPK(){
		Assert.isTrue(null==bizRouteService.queryBizRouteWithPK(bizCode));
		
		bizRouteService.addBizRoute(bizRoute);
		Assert.notNull(bizRouteService.queryBizRouteWithPK(bizCode));
	}

	@Test
	public void addBizRoute(){
		
		int row = bizRouteService.addBizRoute(bizRoute);
		Assert.isTrue(1==row);
		BizRoute bizRouteDB = bizRouteService.queryBizRouteWithPK(bizCode);
		Assert.notNull(bizRouteDB);
		Assert.isTrue(bizCode.equals(bizRouteDB.getBizCode()));
		Assert.isTrue(subSysCode.equals(bizRouteDB.getSubSysCode()));
		Assert.isTrue(bizNameCn.equals(bizRouteDB.getBizNameCn()));
		Assert.isTrue(!bizRouteDB.isDelFlag());
		
		Version version = versionService.queryVersion(Constant.GL_BIZ_ROUTE);
		Assert.isTrue(version.getVersion()==bizRouteDB.getVersion());
	}
	
	@Test
	public void deleteBizRoute(){
		
		bizRouteService.addBizRoute(bizRoute);
		Assert.isTrue(bizRouteService.deleteBizRoute(bizCode));
		BizRoute bizRouteDB = bizRouteService.queryBizRouteWithPK(bizCode);
		Assert.notNull(bizRouteDB);
		Assert.isTrue(bizRouteDB.isDelFlag());
		
		Version version = versionService.queryVersion(Constant.GL_BIZ_ROUTE);
		Assert.isTrue(version.getVersion()==bizRouteDB.getVersion());
	}
	
	@Test
	public void updateBizRoute(){
		
		bizRouteService.addBizRoute(bizRoute);
		bizRoute.setBizNameCn("TEST_BIZ_NAME_UPDATED");
		Assert.isTrue(bizRouteService.updateBizRoute(bizRoute));
		BizRoute bizRouteDB = bizRouteService.queryBizRouteWithPK(bizCode);
		Assert.isTrue("TEST_BIZ_NAME_UPDATED".equals(bizRouteDB.getBizNameCn()));
		bizRoute.setBizNameCn(bizNameCn);
		
		Version version = versionService.queryVersion(Constant.GL_BIZ_ROUTE);
		Assert.isTrue(version.getVersion()==bizRouteDB.getVersion());
	}
	
	@Test
	public void queryBizRoute(){
		
		bizRouteService.addBizRoute(bizRoute);
		List<BizRoute> bizRouteList = bizRouteService.queryBizRoute(bizRoute);
		Assert.notEmpty(bizRouteList);
		Assert.isTrue(1==bizRouteList.size());
		Assert.isTrue(bizCode.equals(bizRouteList.get(0).getBizCode()));
		
		BizRoute bizRoute2 = new BizRoute(bizCode02);
		bizRoute2.setSubSysCode(subSysCode);
		bizRoute2.setBizNameCn(bizNameCn02);
		List<BizRoute> bizRouteList2 = bizRouteService.queryBizRoute(bizRoute2);
		Assert.isTrue(0==bizRouteList2.size());
		
		bizRouteService.addBizRoute(bizRoute2);
		BizRoute bizRoute3 = new BizRoute();
		bizRoute3.setBizNameCn("NAME");
		List<BizRoute> bizRouteList3 = bizRouteService.queryBizRoute(bizRoute3);
		Assert.isTrue(2==bizRouteList3.size());
	}
	

	
	
	@Test
	public void queryBizRoute4SyncData(){
//		List<BizRoute> bizRouteList = bizRouteService.queryBizRoute(new BizRoute());
//		int count = 0;
//		for(BizRoute biz:bizRouteList){
//			if(biz.getVersion()<=35){
//				count++;
//			}
//		}
//		
//		int bigThan35Count = bizRouteList.size()-count;
//		
//		Assert.isTrue(bigThan35Count==bizRouteService.queryBizRoute4SyncData(new Long(35)).size());
		
		
		List<BizRoute> bizRouteList = bizRouteService.queryBizRoute4SyncData(0);
		int count = 0;
		for(BizRoute biz:bizRouteList){
			if(biz.getVersion()<=35){
				count++;
			}
		}
		int bigThan35Count = bizRouteList.size()-count;
		Assert.isTrue(bigThan35Count==bizRouteService.queryBizRoute4SyncData(new Long(35)).size());
	}
}
