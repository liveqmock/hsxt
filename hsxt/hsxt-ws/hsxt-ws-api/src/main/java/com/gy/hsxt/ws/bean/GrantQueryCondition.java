/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ws.bean;

import java.io.Serializable;

/**
 * 积分福利发放记录查询条件
 * 
 * @Package: com.gy.hsxt.ws.bean
 * @ClassName: ApprovalQueryCondition
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-12-4 上午11:34:18
 * @version V1.0
 */
public class GrantQueryCondition implements Serializable {

	private static final long serialVersionUID = 4102398318786193854L;
	/** 福利类型 0 意外伤害 1 免费医疗 2 他人身故 必传 **/
	private Integer welfareType;

	/** 申请人互生号(被发放者互生号) **/
	private String proposerResNo;
	/** 申请人姓名(被发放者姓名) **/
	private String proposerName;

	/** 申请人证件号码（被发放者身份证号码）选传 **/
	private String proposerPapersNo;
	/** 发放人客户号 **/
	private String grantPersonCustId;

	/**
	 * @return the 福利类型0意外伤害1免费医疗2他人身故必传
	 */
	public Integer getWelfareType() {
		return welfareType;
	}

	/**
	 * @param 福利类型0意外伤害1免费医疗2他人身故必传
	 *            the welfareType to set
	 */
	public void setWelfareType(Integer welfareType) {
		this.welfareType = welfareType;
	}

	/**
	 * @return the 申请人互生号(被发放者互生号)
	 */
	public String getProposerResNo() {
		return proposerResNo;
	}

	/**
	 * @param 申请人互生号
	 *            (被发放者互生号) the proposerResNo to set
	 */
	public void setProposerResNo(String proposerResNo) {
		this.proposerResNo = proposerResNo;
	}

	/**
	 * @return the 申请人姓名(被发放者姓名)
	 */
	public String getProposerName() {
		return proposerName;
	}

	/**
	 * @param 申请人姓名
	 *            (被发放者姓名) the proposerName to set
	 */
	public void setProposerName(String proposerName) {
		this.proposerName = proposerName;
	}

	/**
	 * @return the 被发放者身份证号码选传
	 */
	public String getProposerPapersNo() {
		return proposerPapersNo;
	}

	/**
	 * @param 被发放者身份证号码选传
	 *            the proposerPapersNo to set
	 */
	public void setProposerPapersNo(String proposerPapersNo) {
		this.proposerPapersNo = proposerPapersNo;
	}

	/**
	 * @return the 发放人客户号
	 */
	public String getGrantPersonCustId() {
		return grantPersonCustId;
	}

	/**
	 * @param 发放人客户号
	 *            the grantPersonCustId to set
	 */
	public void setGrantPersonCustId(String grantPersonCustId) {
		this.grantPersonCustId = grantPersonCustId;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
