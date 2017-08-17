/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gpf.gcs.service.testcase;

import java.math.BigDecimal;
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
import org.springframework.util.Assert;

import com.gy.hsxt.gpf.gcs.bean.PointAssignRule;
import com.gy.hsxt.gpf.gcs.bean.Version;
import com.gy.hsxt.gpf.gcs.common.Constant;
import com.gy.hsxt.gpf.gcs.interfaces.IPointAssignRuleService;
import com.gy.hsxt.gpf.gcs.interfaces.IVersionService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.service.testcase
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
	public void addAssignRule(){
		int row = pointAssignRuleService.addAssignRule(pointAssignRule);
		Assert.isTrue(1==row);
		PointAssignRule pointAssignRuleDB = pointAssignRuleService.queryAssignRuleWithPK(targetType, actType);
		Assert.isTrue(targetType.equals(pointAssignRuleDB.getTargetType()));
		Assert.isTrue(actType.equals(pointAssignRuleDB.getActType()));
		Assert.isTrue(0==assignRate.compareTo((pointAssignRuleDB.getAssignRate())));
		Assert.isTrue(assignMethod.equals(pointAssignRuleDB.getAssignMethod()));
		Assert.isTrue(!pointAssignRuleDB.isDelFlag());
		
		Version version = versionService.queryVersion(Constant.GL_POINT_ASSIGN_RULE);
		Assert.isTrue(version.getVersion()==pointAssignRuleDB.getVersion());
	}
	
	@Test
	public void delAssignRule(){
		pointAssignRuleService.addAssignRule(pointAssignRule);
		Assert.isTrue(pointAssignRuleService.delAssignRule(targetType, actType));
		PointAssignRule pointAssignRuleDB = pointAssignRuleService.queryAssignRuleWithPK(targetType, actType);
		Assert.notNull(pointAssignRuleDB);
		Assert.isTrue(pointAssignRuleDB.isDelFlag());
		
		Version version = versionService.queryVersion(Constant.GL_POINT_ASSIGN_RULE);
		Assert.isTrue(version.getVersion()==pointAssignRuleDB.getVersion());
	}
	
	@Test
	public void updateAssignRule(){
		pointAssignRuleService.addAssignRule(pointAssignRule);
		
		PointAssignRule pointAssignRule02 = new PointAssignRule(targetType,actType);
		pointAssignRule02.setAssignMethod(assignMethod02);
		pointAssignRule02.setAssignRate(assignRate02);
		
		Assert.isTrue(pointAssignRuleService.updateAssignRule(pointAssignRule02));
		PointAssignRule pointAssignRuleDB = pointAssignRuleService.queryAssignRuleWithPK(targetType, actType);
		Assert.isTrue(0==assignRate02.compareTo((pointAssignRuleDB.getAssignRate())));
		Assert.isTrue(assignMethod02.equals(pointAssignRuleDB.getAssignMethod()));
	 
		Version version = versionService.queryVersion(Constant.GL_POINT_ASSIGN_RULE);
		Assert.isTrue(version.getVersion()==pointAssignRuleDB.getVersion());
	}
	
	@Test
	public void queryAssignRule(){
		pointAssignRuleService.addAssignRule(pointAssignRule);
		List<PointAssignRule> list = pointAssignRuleService.queryAssignRule(pointAssignRule);
		Assert.isTrue(1==list.size());
	}
	
	@Test
	public void existAssignRule(){
		Assert.isTrue(!pointAssignRuleService.existAssignRule(targetType, actType));
		pointAssignRuleService.addAssignRule(pointAssignRule);
		Assert.isTrue(pointAssignRuleService.existAssignRule(targetType, actType));
	}
	
	@Test
	public void queryAssignRuleWithPK(){
		pointAssignRuleService.addAssignRule(pointAssignRule);
		PointAssignRule pointAssignRuleDB = pointAssignRuleService.queryAssignRuleWithPK(targetType, actType);
		Assert.isTrue(targetType.equals(pointAssignRuleDB.getTargetType()));
		Assert.isTrue(actType.equals(pointAssignRuleDB.getActType()));
		Assert.isTrue(0==assignRate.compareTo((pointAssignRuleDB.getAssignRate())));
		Assert.isTrue(assignMethod.equals(pointAssignRuleDB.getAssignMethod()));
	}
	
	@Test
	public void queryAssignRule4SyncData(){
		List<PointAssignRule> list = pointAssignRuleService.queryAssignRule4SyncData(new Long(0));
		int count =0;
		for(PointAssignRule pointAssignRule:list){
			if(pointAssignRule.getVersion()<=4){
				count++;
			}
		}
		
		int bigThan4Count = list.size() - count;
		
		Assert.isTrue(bigThan4Count==pointAssignRuleService.queryAssignRule4SyncData(new Long(4)).size());
	}
	
}
