/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ws.bean;

import java.io.Serializable;

/**
 * 积分福利审批记录单详情
 * 
 * @Package: com.gy.hsxt.ws.bean
 * @ClassName: ApprovalDetail
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-12-3 下午5:54:41
 * @version V1.0
 */
public class ApprovalDetail implements Serializable {
	private static final long serialVersionUID = 4893170833066309139L;

	/**
	 * 积分福利申请信息
	 */
	private WelfareApplyDetail applyInfo;

	/**
	 * 福利资格信息
	 */
	private WelfareQualify welfareQualify;

	/**
	 * 初审信息
	 */
	private ApprovalRecord firstApprovalInfo;

	/**
	 * 复核信息
	 */
	private ApprovalRecord reApprovalInfo;

	/**
	 * @return the 积分福利申请信息
	 */
	public WelfareApplyDetail getApplyInfo() {
		return applyInfo;
	}

	/**
	 * @param 积分福利申请信息
	 *            the applyInfo to set
	 */
	public void setApplyInfo(WelfareApplyDetail applyInfo) {
		this.applyInfo = applyInfo;
	}

	/**
	 * @return the 福利资格信息
	 */
	public WelfareQualify getWelfareQualify() {
		return welfareQualify;
	}

	/**
	 * @param 福利资格信息
	 *            the welfareQualify to set
	 */
	public void setWelfareQualify(WelfareQualify welfareQualify) {
		this.welfareQualify = welfareQualify;
	}

	/**
	 * @return the 初审信息
	 */
	public ApprovalRecord getFirstApprovalInfo() {
		return firstApprovalInfo;
	}

	/**
	 * @param 初审信息
	 *            the firstApprovalInfo to set
	 */
	public void setFirstApprovalInfo(ApprovalRecord firstApprovalInfo) {
		this.firstApprovalInfo = firstApprovalInfo;
	}

	public ApprovalRecord getReApprovalInfo() {
		return reApprovalInfo;
	}

	public void setReApprovalInfo(ApprovalRecord reApprovalInfo) {
		this.reApprovalInfo = reApprovalInfo;
	}

}
