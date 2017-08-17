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

import com.gy.hsxt.gpf.gcs.bean.Subsys;
import com.gy.hsxt.gpf.gcs.interfaces.ISubsysService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gl-global
 * 
 *  Package Name    : com.gy.hsxt.gl.service
 * 
 *  File Name       : SubsysService.java
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
public class SubsysServiceTestCase {
	@Autowired
	private ISubsysService subsysService;

	/**
	 * 判断是否已存在相同
	 */
	@Test
	public void existSubsys() {
		boolean bo = subsysService.existSubsys("03");
		Assert.isTrue(!bo);
	}

	/**
	 * 插入记录
	 */
	@Test
	public void insert() {
		Subsys subsys = new Subsys();
		subsys.setSubsysCode("100");
		subsys.setSysName("ysa");
		subsys.setSysDesc("sysDesc");
		subsys.setVersion(20);
		subsys.setDelFlag(0);
		int row = subsysService.insert(subsys);
		Assert.isTrue(row == 1);
	}

	/**
	 * 读取某条记录
	 */
	public void getSubsys() {
		Subsys subsys = subsysService.getSubsys("01");
		Assert.isTrue(!"".equals(subsys.getSubsysCode()));
	}

	/**
	 * 更新某条记录
	 */
	@Test
	public void update() {
		Subsys subsys = new Subsys();
		subsys.setSubsysCode("01");
		subsys.setSysName("ysa");
		subsys.setSysDesc("sysDesc");
		subsys.setVersion(20);
		subsys.setDelFlag(0);
		int row = subsysService.update(subsys);
		Assert.isTrue(row == 1);
	}

	/**
	 * 获取列表
	 */
	@Test
	public void getSubsysListPage() {
		Subsys subsys = new Subsys();
		List<Subsys> list = subsysService.getSubsysListPage(subsys);
		Assert.isTrue(list.size() >= 1);
	}

	/**
	 * 删除某条记录
	 */
	@Test
	public void delete() {
		int row = subsysService.delete("01");
		Assert.isTrue(row == 1);
	}

	/**
	 * 读取大于某个版本号的数据
	 */
	@Test
	public void querySubsysSyncData() {
		List<Subsys> list = subsysService.querySubsysSyncData(Long
				.parseLong("1"));
		Assert.isTrue(list.size() >= 1);
	}

}
