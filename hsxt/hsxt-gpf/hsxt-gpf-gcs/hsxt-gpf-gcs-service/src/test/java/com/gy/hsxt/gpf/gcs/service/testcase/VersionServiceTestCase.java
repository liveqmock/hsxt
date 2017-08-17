/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gpf.gcs.service.testcase;

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

import com.gy.hsxt.gpf.gcs.bean.Version;
import com.gy.hsxt.gpf.gcs.interfaces.IVersionService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.service.testcase
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
		versionService.addVersion(version);
		Version versionDB = versionService.queryVersion(versionCode);
		Assert.isTrue(versionCode.equals(versionDB.getVersionCode()));
		Assert.isTrue(99==versionDB.getVersion());
		Assert.isTrue(!versionDB.isNotifyFlag());
	}

	@Test
	public void addVersion(){
		Assert.isTrue(versionService.addVersion(version));
		Version versionDB = versionService.queryVersion(versionCode);
		Assert.isTrue(versionCode.equals(versionDB.getVersionCode()));
	}

	@Test
	public void updateVersion(){
		versionService.addVersion(version);
		Version versionNew = new Version(versionCode,new Long(100),true);
		Assert.isTrue(versionService.updateVersion(versionNew));
		Version versionDB = versionService.queryVersion(versionCode);
		Assert.isTrue(100==versionDB.getVersion());
		Assert.isTrue(versionDB.isNotifyFlag());
	}

	@Test
	public void addOrUpdateVersion(){
		Assert.isTrue(1==versionService.addOrUpdateVersion(versionCode));
		Assert.isTrue(2==versionService.addOrUpdateVersion(versionCode));
	}
}
