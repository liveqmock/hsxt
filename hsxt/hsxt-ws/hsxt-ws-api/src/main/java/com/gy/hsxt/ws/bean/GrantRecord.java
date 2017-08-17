/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ws.bean;

import java.io.Serializable;

/**
 * 积分福利发放记录
 * 
 * @Package: com.gy.hsxt.ws.bean
 * @ClassName: GrantRecord
 * @Description: TODO
 * 
 * @author: chenhongzhi
 * @date: 2015-11-11 下午8:15:26
 * @version V3.0
 */
public class GrantRecord implements Serializable {

	private static final long serialVersionUID = -2902410792330857939L;
	/** 发放流水号 **/
	private String givingId;
	/** 是否发放 0 已发放 1 未发放 **/
	private Integer ifgiving;
	/** 申请流水号 **/
	private String applyWelfareNo;
	/** 互生号 **/
	private String hsResNo;
	/** 客户ID **/
	private String custId;
	/** 申请人姓名 **/
	private String proposerName;
	/** 申请人证件号码 **/
	private String proposerPapersNo;
	/** 福利类型 **/
	private Integer welfareType;
	/** 福利编号 **/
	private String welfareNo;
	/** 审批时间 **/
	private String approvalDate;
	/** 补贴余额 **/
	private String subsidyBalance;
	/** 互生支付金额 **/
	private String hsPayAmount;
	/** 发放补贴说明 **/
	private String description;
	/**工单编号 */
	private String taskId;

	/**
	 * @return the 是否发放0已发放1未发放
	 */
	public Integer getIfgiving() {
		return ifgiving;
	}

	/**
	 * @param 是否发放0已发放1未发放
	 *            the ifgiving to set
	 */
	public void setIfgiving(Integer ifgiving) {
		this.ifgiving = ifgiving;
	}

	/**
	 * @return the 发放补贴说明
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param 发放补贴说明
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the 发放流水号
	 */
	public String getGivingId() {
		return givingId;
	}

	/**
	 * @param 发放流水号
	 *            the givingId to set
	 */
	public void setGivingId(String givingId) {
		this.givingId = givingId;
	}

	/**
	 * @return the 福利编号
	 */
	public String getWelfareNo() {
		return welfareNo;
	}

	/**
	 * @param 福利编号
	 *            the welfareNo to set
	 */
	public void setWelfareNo(String welfareNo) {
		this.welfareNo = welfareNo;
	}

	/**
	 * 获取申请流水号
	 * 
	 * @return applyWelfareNo 申请流水号
	 */
	public String getApplyWelfareNo() {
		return applyWelfareNo;
	}

	/**
	 * 设置申请流水号
	 * 
	 * @param applyWelfareNo
	 *            申请流水号
	 */
	public void setApplyWelfareNo(String applyWelfareNo) {
		this.applyWelfareNo = applyWelfareNo;
	}

	/**
	 * 获取互生号
	 * 
	 * @return hsResNo 互生号
	 */
	public String getHsResNo() {
		return hsResNo;
	}

	/**
	 * 设置互生号
	 * 
	 * @param hsResNo
	 *            互生号
	 */
	public void setHsResNo(String hsResNo) {
		this.hsResNo = hsResNo;
	}

	/**
	 * 获取客户ID
	 * 
	 * @return custId 客户ID
	 */
	public String getCustId() {
		return custId;
	}

	/**
	 * 设置客户ID
	 * 
	 * @param custId
	 *            客户ID
	 */
	public void setCustId(String custId) {
		this.custId = custId;
	}

	/**
	 * 获取申请人姓名
	 * 
	 * @return proposerName 申请人姓名
	 */
	public String getProposerName() {
		return proposerName;
	}

	/**
	 * 设置申请人姓名
	 * 
	 * @param proposerName
	 *            申请人姓名
	 */
	public void setProposerName(String proposerName) {
		this.proposerName = proposerName;
	}

	/**
	 * 获取申请人证件号码
	 * 
	 * @return proposerPapersNo 申请人证件号码
	 */
	public String getProposerPapersNo() {
		return proposerPapersNo;
	}

	/**
	 * 设置申请人证件号码
	 * 
	 * @param proposerPapersNo
	 *            申请人证件号码
	 */
	public void setProposerPapersNo(String proposerPapersNo) {
		this.proposerPapersNo = proposerPapersNo;
	}

	/**
	 * @return the 福利类型
	 */
	public Integer getWelfareType() {
		return welfareType;
	}

	/**
	 * @param 福利类型
	 *            the welfareType to set
	 */
	public void setWelfareType(Integer welfareType) {
		this.welfareType = welfareType;
	}

	/**
	 * 获取审批时间
	 * 
	 * @return approvalDate 审批时间
	 */
	public String getApprovalDate() {
		return approvalDate;
	}

	/**
	 * 设置审批时间
	 * 
	 * @param approvalDate
	 *            审批时间
	 */
	public void setApprovalDate(String approvalDate) {
		this.approvalDate = approvalDate;
	}

	/**
	 * 获取补贴余额
	 * 
	 * @return subsidyBalance 补贴余额
	 */
	public String getSubsidyBalance() {
		return subsidyBalance;
	}

	/**
	 * 设置补贴余额
	 * 
	 * @param subsidyBalance
	 *            补贴余额
	 */
	public void setSubsidyBalance(String subsidyBalance) {
		this.subsidyBalance = subsidyBalance;
	}

	/**
	 * 获取互生支付金额
	 * 
	 * @return hsPayAmount 互生支付金额
	 */
	public String getHsPayAmount() {
		return hsPayAmount;
	}

	/**
	 * 设置互生支付金额
	 * 
	 * @param hsPayAmount
	 *            互生支付金额
	 */
	public void setHsPayAmount(String hsPayAmount) {
		this.hsPayAmount = hsPayAmount;
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
