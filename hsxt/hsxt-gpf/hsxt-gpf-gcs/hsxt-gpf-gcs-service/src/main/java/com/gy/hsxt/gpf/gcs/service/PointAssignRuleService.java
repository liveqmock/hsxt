/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gpf.gcs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsxt.gpf.gcs.bean.PointAssignRule;
import com.gy.hsxt.gpf.gcs.common.Constant;
import com.gy.hsxt.gpf.gcs.interfaces.IPointAssignRuleService;
import com.gy.hsxt.gpf.gcs.interfaces.IVersionService;
import com.gy.hsxt.gpf.gcs.mapper.PointAssignRuleMapper;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.service
 * 
 *  File Name       : PointAssignRuleService.java
 * 
 *  Creation Date   : 2015-7-21
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 积分分配比例接口实现
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@Service("pointAssignRuleService")
public class PointAssignRuleService implements IPointAssignRuleService {

	@Autowired
	private IVersionService veresionService;
	
	@Autowired
	PointAssignRuleMapper pointAssignRuleMapper;

	@Override
	@Transactional
	public int addAssignRule(PointAssignRule pointAssignRule) {
		long version = veresionService.addOrUpdateVersion(Constant.GL_POINT_ASSIGN_RULE);
		pointAssignRule.setVersion(version);
		return pointAssignRuleMapper.addPointAssignRule(pointAssignRule);
	}

	@Override
	@Transactional
	public boolean delAssignRule(String targetType, String actType) {
		long version = veresionService.addOrUpdateVersion(Constant.GL_POINT_ASSIGN_RULE);
		PointAssignRule pointAssignRule = new PointAssignRule(targetType,actType);
		pointAssignRule.setVersion(version);
		return pointAssignRuleMapper.deletePointAssignRule(pointAssignRule);
	}

	@Override
	@Transactional
	public boolean updateAssignRule(PointAssignRule pointAssignRule) {
		long version = veresionService.addOrUpdateVersion(Constant.GL_POINT_ASSIGN_RULE);
		pointAssignRule.setVersion(version);
		return pointAssignRuleMapper.updatePointAssignRule(pointAssignRule);
	}

	@Override
	public List<PointAssignRule> queryAssignRule(PointAssignRule pointAssignRule) {
		String assignMethod =pointAssignRule.getAssignMethod();
		if(null != assignMethod && !"".equals(assignMethod)){
			assignMethod = assignMethod.replaceAll("_", "/_").replaceAll("%", "/%");
			pointAssignRule.setAssignMethod(assignMethod);
		}
		return pointAssignRuleMapper.queryPointAssignRuleListPage(pointAssignRule);
	}

	@Override
	public boolean existAssignRule(String targetType, String actType) {
		boolean isExist= "1".equals(pointAssignRuleMapper.existAssignRule(targetType, actType));
		return isExist;
	}

	@Override
	public PointAssignRule queryAssignRuleWithPK(String targetType,
			String actType) {
		return pointAssignRuleMapper.queryPointAssignRuleWithPK(targetType, actType);
	}

	@Override
	public List<PointAssignRule> queryAssignRule4SyncData(Long version) {
		return pointAssignRuleMapper.queryPointAssignRule4SyncData(version);
	}
}
