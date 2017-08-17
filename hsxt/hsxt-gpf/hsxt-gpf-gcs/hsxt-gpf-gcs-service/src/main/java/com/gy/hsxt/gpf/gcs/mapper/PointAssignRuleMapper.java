/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gpf.gcs.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gy.hsxt.gpf.gcs.bean.PointAssignRule;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.mapper
 * 
 *  File Name       : PointAssignRuleMapper.java
 * 
 *  Creation Date   : 2015-7-21
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 积分分配比例mapper接口
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@Repository("PointAssignRuleMapper")
public interface PointAssignRuleMapper {
	
	public int addPointAssignRule(PointAssignRule pointAssignRule);

	public boolean deletePointAssignRule(PointAssignRule pointAssignRule);

	public boolean updatePointAssignRule(PointAssignRule pointAssignRule);

	public String existAssignRule(@Param("targetType")String targetType,@Param("actType")String actType);

	public PointAssignRule queryPointAssignRuleWithPK(@Param("targetType")String targetType,@Param("actType")String actType);
	
	public List<PointAssignRule> queryPointAssignRuleListPage(PointAssignRule pointAssignRule);
	
	public List<PointAssignRule> queryPointAssignRule4SyncData(@Param("version")long version);

}
