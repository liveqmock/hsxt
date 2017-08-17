/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ws.bean;

import java.io.Serializable;

/**
 * 积分福利审批记录
 * 
 * @Package: com.gy.hsxt.ws.bean
 * @ClassName: ApprovalRecord
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-12-3 下午5:52:04
 * @version V1.0
 */
public class ApprovalRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 审批编号（审批主键） **/
	private String approvalId;
	/** 申请流水号 **/
	private String applyWelfareNo;
	/** 积分福利类别 **/
	private Integer welfareType;
	/** 保障单号 **/
	private String welfareId;
	/** 保障单余额 **/
	private String subsidyBalance;
	/** 审批步骤 0：初审 1：复审 **/
	private Integer approvalStep;
	/** 审核结果 **/
	private Integer approvalStatus;
	/** 申请日期 **/
	private String applyDate;
	/** 申请人姓名 **/
	private String proposerName;
	/** 申请人电话 **/
	private String proposerPhone;
	/** 申请人互生号 **/
	private String proposerResNo;
	/** 审批批复金额 */
	private String approvalAmount;
	/** 审批说明 */
	private String approvalReason;
	/** 审批人 */
	private String approver;
	/** 审批日期 */
	private String approvalDate;
	/** 状态日期 */
	private String statusDate;
	/**工单编号 */
	private String taskId;

	/**
	 * @return the 审批编号（审批主键）
	 */
	public String getApprovalId() {
		return approvalId;
	}

	/**
	 * @param 审批编号
	 *            （审批主键） the approvalId to set
	 */
	public void setApprovalId(String approvalId) {
		this.approvalId = approvalId;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the 申请流水号
	 */
	public String getApplyWelfareNo() {
		return applyWelfareNo;
	}

	/**
	 * @param 申请流水号
	 *            the applyWelfareNo to set
	 */
	public void setApplyWelfareNo(String applyWelfareNo) {
		this.applyWelfareNo = applyWelfareNo;
	}

	/**
	 * @return the 积分福利类别
	 */
	public Integer getWelfareType() {
		return welfareType;
	}

	/**
	 * @param 积分福利类别
	 *            the welfareType to set
	 */
	public void setWelfareType(Integer welfareType) {
		this.welfareType = welfareType;
	}

	/**
	 * @return the 保障单号
	 */
	public String getWelfareId() {
		return welfareId;
	}

	/**
	 * @param 保障单号
	 *            the welfareId to set
	 */
	public void setWelfareId(String welfareId) {
		this.welfareId = welfareId;
	}

	/**
	 * @return the 保障单余额
	 */
	public String getSubsidyBalance() {
		return subsidyBalance;
	}

	/**
	 * @param 保障单余额
	 *            the subsidyBalance to set
	 */
	public void setSubsidyBalance(String subsidyBalance) {
		this.subsidyBalance = subsidyBalance;
	}

	/**
	 * @return the 审批步骤0：初审1：复审
	 */
	public Integer getApprovalStep() {
		return approvalStep;
	}

	/**
	 * @param 审批步骤0
	 *            ：初审1：复审 the approvalStep to set
	 */
	public void setApprovalStep(Integer approvalStep) {
		this.approvalStep = approvalStep;
	}

	/**
	 * @return the 审核结果
	 */
	public Integer getApprovalStatus() {
		return approvalStatus;
	}

	/**
	 * @param 审核结果
	 *            the approvalStatus to set
	 */
	public void setApprovalStatus(Integer approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	/**
	 * @return the 申请日期
	 */
	public String getApplyDate() {
		return applyDate;
	}

	/**
	 * @param 申请日期
	 *            the applyDate to set
	 */
	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	/**
	 * @return the 申请人姓名
	 */
	public String getProposerName() {
		return proposerName;
	}

	/**
	 * @param 申请人姓名
	 *            the proposerName to set
	 */
	public void setProposerName(String proposerName) {
		this.proposerName = proposerName;
	}

	/**
	 * @return the 申请人电话
	 */
	public String getProposerPhone() {
		return proposerPhone;
	}

	/**
	 * @param 申请人电话
	 *            the proposerPhone to set
	 */
	public void setProposerPhone(String proposerPhone) {
		this.proposerPhone = proposerPhone;
	}

	/**
	 * @return the 申请人互生号
	 */
	public String getProposerResNo() {
		return proposerResNo;
	}

	/**
	 * @param 申请人互生号
	 *            the proposerResNo to set
	 */
	public void setProposerResNo(String proposerResNo) {
		this.proposerResNo = proposerResNo;
	}

	/**
	 * @return the 审批批复金额
	 */
	public String getApprovalAmount() {
		return approvalAmount;
	}

	/**
	 * @param 审批批复金额
	 *            the approvalAmount to set
	 */
	public void setApprovalAmount(String approvalAmount) {
		this.approvalAmount = approvalAmount;
	}

	/**
	 * @return the 审批说明
	 */
	public String getApprovalReason() {
		return approvalReason;
	}

	/**
	 * @param 审批说明
	 *            the approvalReason to set
	 */
	public void setApprovalReason(String approvalReason) {
		this.approvalReason = approvalReason;
	}

	/**
	 * @return the 审批人
	 */
	public String getApprover() {
		return approver;
	}

	/**
	 * @param 审批人
	 *            the approver to set
	 */
	public void setApprover(String approver) {
		this.approver = approver;
	}

	/**
	 * @return the 审批日期
	 */
	public String getApprovalDate() {
		return approvalDate;
	}

	/**
	 * @param 审批日期
	 *            the approvalDate to set
	 */
	public void setApprovalDate(String approvalDate) {
		this.approvalDate = approvalDate;
	}

	/**
	 * @return the 状态日期
	 */
	public String getStatusDate() {
		return statusDate;
	}

	/**
	 * @param 状态日期
	 *            the statusDate to set
	 */
	public void setStatusDate(String statusDate) {
		this.statusDate = statusDate;
	}

	/**
	 * @return the 工单编号
	 */
	public String getTaskId() {
		return taskId;
	}

	/**
	 * @param 工单编号 the taskId to set
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	

}
