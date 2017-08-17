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

import com.gy.hsxt.lcs.bean.Version;
import com.gy.hsxt.lcs.interfaces.IVersionService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-lcs-service
 * 
 *  Package Name    : com.gy.hsxt.lcs.service.testcase
 * 
 *  File Name       : VersionServiceTestCase.java
 * 
 *  Creation Date   : 2015-7-20
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 测试VersionService
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
public class VersionServiceTestCase {
	
	@Autowired
	IVersionService versionService;
	
	String versionCode = "T_TEST";
	
	Version version=new Version(versionCode,new Long(99),false);
	
	@Test
	public void queryVersion(){
		versionService.addOrUpdateVersion(versionCode);
		Version versionDB = versionService.queryVersion(versionCode);
		Assert.isTrue(versionCode.equals(versionDB.getVersionCode()));
		Assert.isTrue(!versionDB.isNotifyFlag());
	}

	@Test
	public void addOrUpdateVersion(){
		Assert.isTrue(1==versionService.addOrUpdateVersion(versionCode));
		Assert.isTrue(2==versionService.addOrUpdateVersion(versionCode));
	}
}
