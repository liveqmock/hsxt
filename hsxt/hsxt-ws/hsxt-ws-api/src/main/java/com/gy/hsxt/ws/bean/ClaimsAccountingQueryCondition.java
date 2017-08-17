/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ws.bean;

import static com.gy.hsxt.common.utils.StringUtils.isBlank;

import java.io.Serializable;

/**
 * 理赔核算单记录查询条件
 * 
 * @Package: com.gy.hsxt.ws.bean
 * @ClassName: ClaimsAccountingQueryCondition
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-12-4 上午11:34:18
 * @version V1.0
 */
public class ClaimsAccountingQueryCondition implements Serializable {

	private static final long serialVersionUID = 4102398318786193854L;
	/** 申请单编号 **/
	private String applyWelfareNo;
	/** 申请人互生号 **/
	private String proposerResNo;
	/** 申请人姓名 **/
	private String proposerName;
	/** 核算人客户号 **/
	private String accountPersonCustId;

	/**
	 * @return the 申请单编号
	 */
	public String getApplyWelfareNo() {
		return applyWelfareNo;
	}

	/**
	 * @param 申请单编号
	 *            the applyWelfareNo to set
	 */
	public void setApplyWelfareNo(String applyWelfareNo) {
		this.applyWelfareNo = isBlank(applyWelfareNo) ? null : applyWelfareNo
				.trim();
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
		this.proposerResNo = isBlank(proposerResNo) ? null : proposerResNo
				.trim();
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
		this.proposerName = isBlank(proposerName) ? null : proposerName.trim();
	}

	/**
	 * @return the 核算人客户号
	 */
	public String getAccountPersonCustId() {
		return accountPersonCustId;
	}

	/**
	 * @param 核算人客户号
	 *            the accountPersonCustId to set
	 */
	public void setAccountPersonCustId(String accountPersonCustId) {
		this.accountPersonCustId = isBlank(accountPersonCustId) ? null
				: accountPersonCustId.trim();
	}

}
