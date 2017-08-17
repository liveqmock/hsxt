/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ws.bean;

import java.io.Serializable;

/**
 * 积分福利申请记录 实体
 * 
 * @Package: com.gy.hsxt.ws.bean
 * @ClassName: WelfareApplyRecord
 * @Description: 消费者积分福利查询实体类
 * 
 * @author: chenhongzhi
 * @date: 2015-11-26 下午12:08:17
 * @version V3.0
 */
public class WelfareApplyRecord implements Serializable {
	private static final long serialVersionUID = -4581940504040266713L;
	/** 申请流水号 **/
	private String applyWelfareNo;
	/** 福利类型 0 意外伤害 1 免费医疗 2 身故保障 **/
	private Integer welfareType;
	/** 审核结果 **/
	private Integer approvalStatus;
	/** 申请日期 **/
	private String applyDate;
	/** 批复金额 **/
	private String approvalAmount;
	
	/** 保障单号 **/
	private String welfareId;

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
	 * @return the 批复金额
	 */
	public String getApprovalAmount() {
		return approvalAmount;
	}

	/**
	 * @param 批复金额
	 *            the approvalAmount to set
	 */
	public void setApprovalAmount(String approvalAmount) {
		this.approvalAmount = approvalAmount;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the 保障单号
	 */
	public String getWelfareId() {
		return welfareId;
	}

	/**
	 * @param 保障单号 the welfareId to set
	 */
	public void setWelfareId(String welfareId) {
		this.welfareId = welfareId;
	}

	
}
