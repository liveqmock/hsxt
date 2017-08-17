/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.lcs.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.gy.hsxt.lcs.bean.PointAssignRule;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-lcs-service
 * 
 *  Package Name    : com.gy.hsxt.lcs.mapper
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
public interface PointAssignRuleMapper {

    /**
     * 判断数据是否已存在
     * @param targetType
     * @param actType
     * @return
     */
	public String existAssignRule(@Param("targetType")String targetType,@Param("actType")String actType);
	
	/**
	 * 批量更新数据
	 * @param pointAssignRuleListUpdate
	 */
	public void batchUpdate(List<PointAssignRule> pointAssignRuleListUpdate);

	/**
	 * 批量插入数据
	 * @param pointAssignRuleListAdd
	 */
	public void batchInsert(List<PointAssignRule> pointAssignRuleListAdd);

}
