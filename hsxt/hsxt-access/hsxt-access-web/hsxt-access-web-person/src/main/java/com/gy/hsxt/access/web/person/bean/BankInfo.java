package com.gy.hsxt.access.web.person.bean;

/***************************************************************************
 * <PRE>
 * Description 		: Simple to Introduction
 * 
 * Project Name   	: hsxt-access-web-person
 * 
 * Package Name     : com.gy.hsxt.access.web.mapvo
 * 
 * File Name        : BankInfo
 * 
 * Author           : LiZhi Peter
 * 
 * Create Date      : 2015-9-1 下午12:06:31  
 * 
 * Update User      : LiZhi Peter
 * 
 * Update Date      : 2015-9-1 下午12:06:31
 * 
 * UpdateRemark 	: 说明本次修改内容
 * 
 * Version          : v0.0.1
 * 
 * </PRE>
 ***************************************************************************/
public class BankInfo {
	
	/** 银行卡id */
	private String bankId;
	
	/** 客户号 */
	private String custId;
	
	/** 互生卡号 */
	private String pointNo;
	
	/** 真实姓名 */
	private String name;
	
	/** 结算币种 */
	private String currency;
	
	/** 开户银行 */
	private String bankCode;
	
	/** 默认银行账号*/
	private String isDefault;
	
	/** 开户省份 */
	private String provinceCode;
	
	/** 开户城市 */
	private String cityCode;
	
	/** 银行卡号 */
	private String bankNo;
	
	/** 确认卡号 */
	private String bankNoRe;

	
	
	/**
	 * 获取银行卡id
	 * @return bankId 银行卡id
	 */
	public String getBankId() {
		return bankId;
	}

	/**
	 * 设置银行卡id
	 * @param bankId 银行卡id
	 */
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	/**
	 * 获取客户号
	 * @return custId 客户号
	 */
	public String getCustId() {
		return custId;
	}

	/**
	 * 设置客户号
	 * @param custId 客户号
	 */
	public void setCustId(String custId) {
		this.custId = custId;
	}

	/**
	 * 获取互生卡号
	 * @return pointNo 互生卡号
	 */
	public String getPointNo() {
		return pointNo;
	}

	/**
	 * 设置互生卡号
	 * @param pointNo 互生卡号
	 */
	public void setPointNo(String pointNo) {
		this.pointNo = pointNo;
	}

	/**
	 * 获取真实姓名
	 * @return name 真实姓名
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置真实姓名
	 * @param name 真实姓名
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取结算币种
	 * @return currency 结算币种
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * 设置结算币种
	 * @param currency 结算币种
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}


	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	/**
	 * 获取默认银行账号
	 * @return isDefault 默认银行账号
	 */
	public String getIsDefault() {
		return isDefault;
	}

	/**
	 * 设置默认银行账号
	 * @param isDefault 默认银行账号
	 */
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	

	/**
	 * 获取开户省份
	 * @return provinceCode 开户省份
	 */
	public String getProvinceCode() {
		return provinceCode;
	}

	/**
	 * 设置开户省份
	 * @param provinceCode 开户省份
	 */
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	/**
	 * 获取开户城市
	 * @return cityCode 开户城市
	 */
	public String getCityCode() {
		return cityCode;
	}

	/**
	 * 设置开户城市
	 * @param cityCode 开户城市
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	/**
	 * 获取银行卡号
	 * @return bankNo 银行卡号
	 */
	public String getBankNo() {
		return bankNo;
	}

	/**
	 * 设置银行卡号
	 * @param bankNo 银行卡号
	 */
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	/**
	 * 获取确认卡号
	 * @return bankNoRe 确认卡号
	 */
	public String getBankNoRe() {
		return bankNoRe;
	}

	/**
	 * 设置确认卡号
	 * @param bankNoRe 确认卡号
	 */
	public void setBankNoRe(String bankNoRe) {
		this.bankNoRe = bankNoRe;
	}
	
	
	
	
}
