/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.lcs.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsxt.lcs.bean.PointAssignRule;
import com.gy.hsxt.lcs.bean.Version;
import com.gy.hsxt.lcs.common.Constant;
import com.gy.hsxt.lcs.interfaces.IPointAssignRuleService;
import com.gy.hsxt.lcs.interfaces.IVersionService;
import com.gy.hsxt.lcs.mapper.PointAssignRuleMapper;


/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-lcs-service
 * 
 *  Package Name    : com.gy.hsxt.lcs.service
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
	public int addOrUpdateAssignRule(List<PointAssignRule> assignRuleList, Long version) {
		veresionService.addOrUpdateVersion4SyncData(new Version(Constant.GL_POINT_ASSIGN_RULE,version,true));
		List<PointAssignRule> assignRuleListAdd = new ArrayList<PointAssignRule>();
		List<PointAssignRule> assignRuleListUpdate = new ArrayList<PointAssignRule>();
		for(PointAssignRule obj : assignRuleList){
			if("1".equals(pointAssignRuleMapper.existAssignRule(obj.getTargetType(), obj.getActType()))){
				assignRuleListUpdate.add(obj);
			}else{
				assignRuleListAdd.add(obj);
			}
		}
		if(assignRuleListUpdate.size()>0){
			pointAssignRuleMapper.batchUpdate(assignRuleListUpdate);
		}
		
		if(assignRuleListAdd.size()>0){
			pointAssignRuleMapper.batchInsert(assignRuleListAdd);
		}
		return 1;
	}
}
