/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.lcs.service.testcase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

import com.gy.hsxt.lcs.bean.PointAssignRule;
import com.gy.hsxt.lcs.interfaces.IPointAssignRuleService;
import com.gy.hsxt.lcs.interfaces.IVersionService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-lcs-service
 * 
 *  Package Name    : com.gy.hsxt.lcs.service.testcase
 * 
 *  File Name       : PointAssignRuleServiceTestCase.java
 * 
 *  Creation Date   : 2015-7-21
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 测试PointAssignRuleService
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
public class PointAssignRuleServiceTestCase {
	
	@Autowired
	IVersionService versionService;
	
	@Autowired
	IPointAssignRuleService pointAssignRuleService;
	
	PointAssignRule pointAssignRule;
	
	String targetType="W";
	String actType="待分配积分";
	BigDecimal assignRate=BigDecimal.TEN;
	String assignMethod="日终合并分配";
	
	String targetType02="E";
	String actType02="积分";
	BigDecimal assignRate02=new BigDecimal(50);
	String assignMethod02="日终合并分配";
	
	@Before
	public void setUp() throws Exception {
		pointAssignRule = new PointAssignRule(targetType,actType);
		pointAssignRule.setAssignRate(assignRate);
		pointAssignRule.setAssignMethod(assignMethod);
		pointAssignRule.setDelFlag(false);
	}
	
	
	@Test
	public void addOrUpdateAssignRule(){
		List<PointAssignRule> list = new ArrayList<PointAssignRule>();
		list.add(pointAssignRule);
		
		PointAssignRule pointAssignRule02 = new PointAssignRule(targetType02,actType02);
		pointAssignRule02.setAssignMethod(assignMethod02);
		pointAssignRule02.setAssignRate(assignRate02);
		list.add(pointAssignRule02);
		
		pointAssignRuleService.addOrUpdateAssignRule(list, new Long(99));
	}
}
