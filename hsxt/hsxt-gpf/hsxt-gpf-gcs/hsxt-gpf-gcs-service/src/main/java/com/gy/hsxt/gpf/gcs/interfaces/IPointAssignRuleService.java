/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gpf.gcs.interfaces;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.gy.hsxt.gpf.gcs.bean.PointAssignRule;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.interfaces
 * 
 *  File Name       : PointAssignRuleService.java
 * 
 *  Creation Date   : 2015-7-21
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 积分分配比例接口
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
public interface IPointAssignRuleService {
	/**
	 * 添加积分分配比例
	 * @param pointAssignRule
	 * @return
	 */
	@Transactional
	public int addAssignRule(PointAssignRule pointAssignRule);
	
	/**
	 * 逻辑删除分配比例，delFlag = 1
	 * @param targetType 分配对象
	 * @param actType 账户类型
	 * @return
	 */
	@Transactional
	public boolean delAssignRule(String targetType,String actType);
	/**
	 * 更新分配比例
	 * @param pointAssignRule
	 * @return
	 */
	@Transactional
	public boolean updateAssignRule(PointAssignRule pointAssignRule);
	/**
	 * 查询分配比例
	 * @param pointAssignRule
	 * @return
	 */
	public List<PointAssignRule> queryAssignRule(PointAssignRule pointAssignRule);
	/**
	 * 是否存在
	 * @param targetType
	 * @param actType
	 * @return
	 */
	public boolean existAssignRule(String targetType,String actType);
	/**
	 * 用主键查询积分分配比例
	 * @param targetType
	 * @param actType
	 * @return
	 */
	public PointAssignRule queryAssignRuleWithPK(String targetType,String actType);
	/**
	 * 查询出版本号大于子平台的所有积分分配比例
	 * @param version
	 * @return
	 */
	public List<PointAssignRule> queryAssignRule4SyncData(Long version);

}
