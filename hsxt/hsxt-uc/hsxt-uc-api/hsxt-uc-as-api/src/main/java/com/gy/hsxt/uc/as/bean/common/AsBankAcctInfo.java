/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.as.bean.common;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

/**
 * 银行帐户信息
 * 
 * @Package: com.gy.hsxt.uc.as.bean.common
 * @ClassName: AsBankAcctInfo
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2015-10-23 下午2:31:23
 * @version V1.0
 */
public class AsBankAcctInfo implements Serializable {

	private static final long serialVersionUID = -9157109586208153424L;
	
	/**
	 * 银行账户编号（主键系统自动生成）
	 */
	Long accId;
	/**
	 * 企业客户号或消费者客户号
	 */
	String custId;
	/**
	 * 企业互生号或消费者互生号
	 */
	String resNo;

	/**
	 * 银行账号
	 */
	String bankAccNo;
	
	/**
	 * 银行帐户户名
	 */
	String bankAccName;
	
	/**
	 * 开户行代码
	 */
	String bankCode;
	/**
	 * 开户行名称
	 */
	String bankName;
	/**
     * 姓名
     */
    String realName;
	/**
	 * 支行名称
	 */
	String bankBranch;
	/**
	 * 帐户类型 1：DR_CARD-借记卡2：CR_CARD-贷记卡3：PASSBOOK-存折 4：CORP_ACCT-对公帐号
	 */
	String bankCardType;
	/**
	 * 所在国家编号
	 */
	String countryNo;
	/**
	 * 所在省编号
	 */
	String provinceNo;
	/**
	 * 所在城市编号
	 */
	String cityNo;
	
	/**
	 * 是否默认账户（1:是 0：否）
	 */
	String isDefaultAccount;
	/**
	 * 账户转账标识 1:转账成功 0：从未转账 2：转账失败 只要一次转账成功，此状态以后就不再改变
	 */
	String usedFlag;
	/**
	 * 是否已验证，0否，1是，只有查询时才有该值
	 */
	int isValidAccount;

	
	/**
	 * @return the 银行帐户户名
	 */
	public String getBankAccName() {
		return bankAccName;
	}

	/**
	 * @param 银行帐户户名 the bankAccName to set
	 */
	public void setBankAccName(String bankAccName) {
		this.bankAccName = bankAccName;
	}

	/**
	 * @return the 银行账户编号（主键系统自动生成）
	 */
	public Long getAccId() {
		return accId;
	}

	/**
	 * @param 银行账户编号
	 *            （主键系统自动生成） the accId to set
	 */
	public void setAccId(Long accId) {
		this.accId = accId;
	}

	/**
	 * @return the 企业客户号或消费者客户号
	 */
	public String getCustId() {
		return custId;
	}

	/**
	 * @param 企业客户号或消费者客户号
	 *            the custId to set
	 */
	public void setCustId(String custId) {
		this.custId = custId;
	}

	/**
	 * @return the 企业互生号或消费者互生号
	 */
	public String getResNo() {
		return resNo;
	}

	/**
	 * @param 企业互生号或消费者互生号
	 *            the resNo to set
	 */
	public void setResNo(String resNo) {
		this.resNo = resNo;
	}

	/**
	 * @return the 开户行代码
	 */
	public String getBankCode() {
		return bankCode;
	}

	/**
	 * @param 开户行代码
	 *            the bankCode to set
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	/**
	 * @return the 开户行名称
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param 开户行名称
	 *            the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * @return the 支行名称
	 */
	public String getBankBranch() {
		return bankBranch;
	}

	/**
	 * @param 支行名称
	 *            the bankBranch to set
	 */
	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	/**
	 * @return the 帐户类型1：DR_CARD-借记卡2：CR_CARD-贷记卡3：PASSBOOK-存折4：CORP_ACCT-对公帐号
	 */
	public String getBankCardType() {
		return bankCardType;
	}

	/**
	 * @param 帐户类型1
	 *            ：DR_CARD-借记卡2：CR_CARD-贷记卡3：PASSBOOK-存折4：CORP_ACCT-对公帐号 the
	 *            bankCardType to set
	 */
	public void setBankCardType(String bankCardType) {
		this.bankCardType = bankCardType;
	}

	/**
	 * @return the 是否默认账户（1:是0：否）
	 */
	public String getIsDefaultAccount() {
		return isDefaultAccount;
	}

	/**
	 * @param 是否默认账户
	 *            （1:是0：否） the isDefaultAccount to set
	 */
	public void setIsDefaultAccount(String isDefaultAccount) {
		this.isDefaultAccount = isDefaultAccount;
	}

	/**
	 * @return the 账户转账标识1:转账成功0：从未转账2：转账失败只要一次转账成功，此状态以后就不再改变
	 */
	public String getUsedFlag() {
		return usedFlag;
	}

	/**
	 * @param 账户转账标识1
	 *            :转账成功0：从未转账2：转账失败只要一次转账成功，此状态以后就不再改变
	 */
	public void setUsedFlag(String usedFlag) {
		this.usedFlag = usedFlag;
	}

	/**
	 * @return 是否已验证，0否，1是，只有查询时才有该值
	 */
	public int getIsValidAccount() {
		return isValidAccount;
	}

	/**
	 * @param 是否已验证
	 *            ，0否，1是，只有查询时才有该值
	 */
	public void setIsValidAccount(int isValidAccount) {
		this.isValidAccount = isValidAccount;
	}

	/**
	 * 银行帐号
	 * 
	 * @return the ID
	 */
	public String getBankAccNo() {
		return bankAccNo;
	}

	/**
	 * 银行帐号
	 * 
	 * @param ID
	 *            the bankAccNo to set
	 */
	public void setBankAccNo(String bankAccNo) {
		this.bankAccNo = bankAccNo;
	}

	/**
	 * @return the 所在国家编号
	 */
	public String getCountryNo() {
		return countryNo;
	}

	/**
	 * @param 所在国家编号
	 *            the countryNo to set
	 */
	public void setCountryNo(String countryNo) {
		this.countryNo = countryNo;
	}

	/**
	 * @return the 所在省编号
	 */
	public String getProvinceNo() {
		return provinceNo;
	}

	/**
	 * @param 所在省编号
	 *            the provinceNo to set
	 */
	public void setProvinceNo(String provinceNo) {
		this.provinceNo = provinceNo;
	}

	/**
	 * @return the 所在城市编号
	 */
	public String getCityNo() {
		return cityNo;
	}

	/**
	 * @param 所在城市编号
	 *            the cityNo to set
	 */
	public void setCityNo(String cityNo) {
		this.cityNo = cityNo;
	}
	public String toString(){
		return JSONObject.toJSONString(this);
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

 
	
	
}
