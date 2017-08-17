/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.ao.bean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 地区平台信息实体
 * 
 * @Package: com.gy.hsxt.ao.bean
 * @ClassName: AreaPlateInfo
 * @Description: TODO
 * 
 * @author: liyh
 * @date: 2015-12-17 上午11:16:51
 * @version V1.0
 */
public class AreaPlateInfo implements Serializable {

	private static final long serialVersionUID = -3126047369479002175L;

	/** 平台代码 **/
	private String platNo;

	/** 平台中文名 **/
	private String platNameCn;

	/** 国家代码 **/
	private String countryNo;

	/** 语言代码 **/
	private String languageCode;

	/** 币种代码 **/
	private String currencyNo;

	/** 币种英文代码 **/
	private String currencyCode;

	/** 币种中文名称 **/
	private String currencyNameCn;

	/** 币种符号 **/
	private String currencySymbol;

	/** 币种精度 **/
	private int precisionNum;

	/** 货币单位 **/
	private String unitCode;

	/** 货币转换比率 **/
	private Double exchangeRate;

	/** 互生币代码 **/
	private String hsbCode;

	/** 积分代码 **/
	private String pointCode;

	/** 总平台代码 **/
	private String centerPlatNo;

	/** 平台客户号 **/
	private String plateCustId;

	/**
	 * @return the 平台代码
	 */
	public String getPlatNo() {
		return platNo;
	}

	/**
	 * @param 平台代码
	 *            the platNo to set
	 */
	public void setPlatNo(String platNo) {
		this.platNo = platNo;
	}

	/**
	 * @return the 平台中文名
	 */
	public String getPlatNameCn() {
		return platNameCn;
	}

	/**
	 * @param 平台中文名
	 *            the platNameCn to set
	 */
	public void setPlatNameCn(String platNameCn) {
		this.platNameCn = platNameCn;
	}

	/**
	 * @return the 国家代码
	 */
	public String getCountryNo() {
		return countryNo;
	}

	/**
	 * @param 国家代码
	 *            the countryNo to set
	 */
	public void setCountryNo(String countryNo) {
		this.countryNo = countryNo;
	}

	/**
	 * @return the 语言代码
	 */
	public String getLanguageCode() {
		return languageCode;
	}

	/**
	 * @param 语言代码
	 *            the languageCode to set
	 */
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	/**
	 * @return the 币种代码
	 */
	public String getCurrencyNo() {
		return currencyNo;
	}

	/**
	 * @param 币种代码
	 *            the currencyNo to set
	 */
	public void setCurrencyNo(String currencyNo) {
		this.currencyNo = currencyNo;
	}

	/**
	 * @return the 币种英文代码
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @param 币种英文代码
	 *            the currencyCode to set
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * @return the 币种中文名称
	 */
	public String getCurrencyNameCn() {
		return currencyNameCn;
	}

	/**
	 * @param 币种中文名称
	 *            the currencyNameCn to set
	 */
	public void setCurrencyNameCn(String currencyNameCn) {
		this.currencyNameCn = currencyNameCn;
	}

	/**
	 * @return the 币种符号
	 */
	public String getCurrencySymbol() {
		return currencySymbol;
	}

	/**
	 * @param 币种符号
	 *            the currencySymbol to set
	 */
	public void setCurrencySymbol(String currencySymbol) {
		this.currencySymbol = currencySymbol;
	}

	/**
	 * @return the 币种精度
	 */
	public int getPrecisionNum() {
		return precisionNum;
	}

	/**
	 * @param 币种精度
	 *            the precisionNum to set
	 */
	public void setPrecisionNum(int precisionNum) {
		this.precisionNum = precisionNum;
	}

	/**
	 * @return the 货币单位
	 */
	public String getUnitCode() {
		return unitCode;
	}

	/**
	 * @param 货币单位
	 *            the unitCode to set
	 */
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	/**
	 * @return the 货币转换比率
	 */
	public Double getExchangeRate() {
		return exchangeRate;
	}

	/**
	 * @param 货币转换比率
	 *            the exchangeRate to set
	 */
	public void setExchangeRate(Double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	/**
	 * @return the 互生币代码
	 */
	public String getHsbCode() {
		return hsbCode;
	}

	/**
	 * @param 互生币代码
	 *            the hsbCode to set
	 */
	public void setHsbCode(String hsbCode) {
		this.hsbCode = hsbCode;
	}

	/**
	 * @return the 积分代码
	 */
	public String getPointCode() {
		return pointCode;
	}

	/**
	 * @param 积分代码
	 *            the pointCode to set
	 */
	public void setPointCode(String pointCode) {
		this.pointCode = pointCode;
	}

	/**
	 * @return the 总平台代码
	 */
	public String getCenterPlatNo() {
		return centerPlatNo;
	}

	/**
	 * @param 总平台代码
	 *            the centerPlatNo to set
	 */
	public void setCenterPlatNo(String centerPlatNo) {
		this.centerPlatNo = centerPlatNo;
	}

	/**
	 * @return the 平台客户号
	 */
	public String getPlateCustId() {
		return plateCustId;
	}

	/**
	 * @param 平台客户号
	 *            the plateCustId to set
	 */
	public void setPlateCustId(String plateCustId) {
		this.plateCustId = plateCustId;
	}

	/**
	 * 获取平台互生号 ,等于00000000+国家代码
	 * 
	 * @return the countryNo
	 */
	public String getPlatResNo() {
		return "00000000" + countryNo;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
