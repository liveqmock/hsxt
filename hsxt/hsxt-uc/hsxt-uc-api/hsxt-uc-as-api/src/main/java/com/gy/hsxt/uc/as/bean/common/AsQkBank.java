/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.as.bean.common;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

public class AsQkBank implements Serializable {
	private static final long serialVersionUID = -262951976360307363L;
	/**
	 * 快捷账号ID
	 */
	String accId;
	/**
	 * 客户号
	 */
	String custId;
	/**
	 * 互生号
	 */
	String resNo;
	/**
	 * 银行代码
	 */
	String bankCode;
	/**
	 * 银行名称
	 */
	String bankName;
	/**
	 * 银行账号
	 */
	String bankCardNo;
	/**
	 * 账户类型1：DR_CARD-借记卡 2：CR_CARD-贷记卡 3：PASSBOOK-存折
	 */
	private Integer bankCardType;
	/**
	 * 签约号
	 */
	String signNo;
	/**
	 * 小额支付有效期
	 */
	String smallPayExpireDate;
	/**
	 * 小额支付总限额
	 */
	String amountTotalLimit;
	/**
	 * 小额支付单笔限额
	 */
	String amountSingleLimit;

	/**
	 * 快捷账户编号（主键）
	 * 
	 * @return the accId
	 */
	public String getAccId() {
		return accId;
	}

	/**
	 * 快捷账户编号（主键）
	 * 
	 * @param accId
	 *            the accId to set
	 */
	public void setAccId(String accId) {
		this.accId = accId;
	}

	/**
	 * 企业或消费者客户号
	 * 
	 * @return the perCustId
	 */
	public String getCustId() {
		return custId;
	}

	/**
	 * 企业或消费者客户号
	 * 
	 * @param perCustId
	 *            the perCustId to set
	 */
	public void setCustId(String custId) {
		this.custId = custId;
	}

	/**
	 * 企业或消费者互生号
	 * 
	 * @return the perResNo
	 */
	public String getResNo() {
		return resNo;
	}

	/**
	 * 企业或消费者互生号
	 * 
	 * @param perResNo
	 *            the perResNo to set
	 */
	public void setResNo(String resNo) {
		this.resNo = resNo;
	}

	/**
	 * 开户行代码
	 * 
	 * @return
	 */
	public String getBankCode() {
		return bankCode;
	}

	/**
	 * 开户行代码
	 * 
	 * @param bankCode
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	/**
	 * 开户行名称
	 * 
	 * @return
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * 开户行名称
	 * 
	 * @param bankName
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * 银行卡号
	 * 
	 * @return
	 */
	public String getBankCardNo() {
		return bankCardNo;
	}

	/**
	 * 银行卡号
	 * 
	 * @param bankCardNo
	 */
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	/**
	 * 快捷账户签约号
	 * 
	 * @return
	 */
	public String getSignNo() {
		return signNo;
	}

	/**
	 * 快捷账户签约号
	 * 
	 * @param signNo
	 */
	public void setSignNo(String signNo) {
		this.signNo = signNo;
	}

	/**
	 * 小额支付有效期
	 * 
	 * @return
	 */
	public String getSmallPayExpireDate() {
		return smallPayExpireDate;
	}

	/**
	 * 小额支付有效期
	 * 
	 * @param smallPayExpireDate
	 */
	public void setSmallPayExpireDate(String smallPayExpireDate) {
		this.smallPayExpireDate = smallPayExpireDate;
	}

	/**
	 * 总限额
	 * 
	 * @return
	 */
	public String getAmountTotalLimit() {
		return amountTotalLimit;
	}

	/**
	 * 总限额
	 * 
	 * @param amountTotalLimit
	 */
	public void setAmountTotalLimit(String amountTotalLimit) {
		this.amountTotalLimit = amountTotalLimit;
	}

	/**
	 * 单笔限额
	 * 
	 * @return
	 */
	public String getAmountSingleLimit() {
		return amountSingleLimit;
	}

	/**
	 * 单笔限额
	 * 
	 * @param amountSingleLimit
	 */
	public void setAmountSingleLimit(String amountSingleLimit) {
		this.amountSingleLimit = amountSingleLimit;
	}
	
	
	/**
	 * @return the 账户类型1：DR_CARD-借记卡2：CR_CARD-贷记卡3：PASSBOOK-存折
	 */
	public Integer getBankCardType() {
		return bankCardType;
	}

	/**
	 * @param 账户类型1：DR_CARD-借记卡2：CR_CARD-贷记卡3：PASSBOOK-存折 the bankCardType to set
	 */
	public void setBankCardType(Integer bankCardType) {
		this.bankCardType = bankCardType;
	}

	public String toString(){
		return JSONObject.toJSONString(this);
	}
}
