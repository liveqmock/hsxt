package com.gy.hsxt.ws.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.ws.bean.ApprovalInfo;
import com.gy.hsxt.ws.bean.ApprovalQueryCondition;

/**
 * 审批记录Mapper
 * 
 * @Package: com.gy.hsxt.ws.mapper
 * @ClassName: ApprovalRecordMapper
 * @Description: TODO
 * 
 * @author: huanggy
 * @date: 2015-12-24 上午10:19:30
 * @version V1.0
 */
public interface ApprovalRecordMapper {
	/**
	 * 插入审批记录
	 * 
	 * @param record
	 *            审批记录
	 * @return
	 */
	int insertSelective(ApprovalInfo record);

	/**
	 * 查询审批记录 通过审批ID(主键)
	 * 
	 * @param approvalId
	 *            审批ID
	 * @return
	 */
	ApprovalInfo selectByPrimaryKey(String approvalId);

	/**
	 * 查询审批记录 通过审批步骤
	 * 
	 * @param approvalStep
	 *            审批步骤 0：初审 1：复审
	 * @param applyWelfareNo
	 *            申请单编号
	 * @return
	 */
	ApprovalInfo selectByApprovalStep(@Param("approvalStep") Integer approvalStep,
			@Param("applyWelfareNo") String applyWelfareNo);

	/**
	 * 更新审批记录
	 * 
	 * @param record
	 *            审批记录
	 * @return
	 */
	int updateByPrimaryKeySelective(ApprovalInfo record);

	/**
	 * 查询审批记录 通过工单任务ID
	 * 
	 * @param taskId
	 *            工单ID
	 * @return
	 */
	ApprovalInfo selectByTaskId(String taskId);

	/**
	 * 分院查询查询统计条数
	 * 
	 * @param queryCondition
	 *            查询条件
	 * @param approvalStatus
	 *            审批状态
	 * @return
	 */
	int countApprovalInfo(@Param("condition") ApprovalQueryCondition queryCondition,
			@Param("approvalStatus") Integer approvalStatus);

	/**
	 * 分院查询审批记录
	 * 
	 * @param queryCondition
	 *            分页查询条件
	 * @param page
	 *            分页参数
	 * @param approvalStatus
	 *            审批状态
	 * @return
	 */
	List<ApprovalInfo> pageApprovalInfo(@Param("condition") ApprovalQueryCondition queryCondition,
			@Param("page") Page page, @Param("approvalStatus") Integer approvalStatus);

	/**
	 * 更新审批单余额
	 * 
	 * @param hsResNo
	 *            互生号
	 * @param welfareType
	 *            福利类型 0 意外伤害 1 免费医疗 2 他人身故
	 * @param subsidyBalance
	 *            补贴余额
	 * @return
	 */
	int updateSubsidyBalance(@Param("hsResNo") String hsResNo,
			@Param("welfareType") Integer welfareType,
			@Param("subsidyBalance") BigDecimal subsidyBalance);

}