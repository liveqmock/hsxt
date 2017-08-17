/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ws.bean;

import static com.gy.hsxt.common.utils.StringUtils.isBlank;

import java.io.Serializable;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ws.common.WsErrorCode;

/**
 * 积分福利审批记录查询条件
 * 
 * @Package: com.gy.hsxt.ws.bean
 * @ClassName: ApprovalQueryCondition
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-12-4 上午11:34:18
 * @version V1.0
 */
public class ApprovalQueryCondition implements Serializable {

	private static final long serialVersionUID = 4102398318786193854L;
	/** 福利类型 0 意外伤害 1 免费医疗 2 他人身故 **/
	private Integer welfareType;
	/** 审批步骤 0：初审 1：复审 **/
	private Integer approvalStep;
	/** 开始查询时间(申请时间) **/
	private String startTime;
	/** 结束查询时间(申请时间) **/
	private String endTime;
	/** 申请人互生号 **/
	private String proposerResNo;
	/** 申请人姓名 **/
	private String proposerName;
	/** 审批人客户号 **/
	private String approvalCustId;

	/**
	 * @return the 福利类型0意外伤害1免费医疗2他人身故
	 */
	public Integer getWelfareType() {
		return welfareType;
	}

	/**
	 * @param 福利类型0意外伤害1免费医疗2他人身故
	 *            the welfareType to set
	 */
	public void setWelfareType(Integer welfareType) {
		this.welfareType = welfareType;
	}

	/**
	 * @return the 开始查询时间(申请时间)
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param 开始查询时间
	 *            (申请时间) the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the 结束查询时间(申请时间)
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param 结束查询时间
	 *            (申请时间) the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
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
	 * @return the 审批人客户号
	 */
	public String getApprovalCustId() {
		return approvalCustId;
	}

	/**
	 * @param 审批人客户号
	 *            the approvalCustId to set
	 */
	public void setApprovalCustId(String approvalCustId) {
		this.approvalCustId = approvalCustId;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void validParam() {
		if (isBlank(this.approvalCustId)) {
			throw new HsException(WsErrorCode.PARAM_IS_NULL.getCode(), "必传参数[approvalCustId]为空");
		}
	}

}
