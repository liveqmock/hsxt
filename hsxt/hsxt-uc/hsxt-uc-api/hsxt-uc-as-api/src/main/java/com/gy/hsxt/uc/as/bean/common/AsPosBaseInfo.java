/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.uc.as.bean.common;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

/**
 * POS的公用信息
 * 
 * @Package: com.gy.hsxt.uc.as.bean.common  
 * @ClassName: AsPosCommonInfo 
 * @Description: TODO
 *
 * @author: lixuan 
 * @date: 2015-12-30 上午11:56:26 
 * @version V1.0
 */
public class AsPosBaseInfo implements Serializable{
	private static final long serialVersionUID = 7582067073564634904L;
	/**
	 * 积分兑换本地货币比率
	 */
	String pvExchangeCurrencyRate = "1";
	/**
	 * 互生币兑换本地货币比率
	 */
	String hsbExchangeCurrencyRate;
	/**
	 * 托管企业充值上限
	 */
	String entTChargeMaxValue;
	
	/**
	 * 托管企业充值下限
	 */
	String entTChargeMinValue;
	/**
	 * 成员企业充值上限
	 */
	String entBChargeMaxValue;
	/**
	 * 成员企业充值下限
	 */
	String entBChargeMinValue;
	/**
	 * 持卡人充值上限
	 */
	String carderChargeMaxValue;
	/**
	 * 持卡人充值下限
	 */
	String carderChargeMinValue;

	/**
	 * 电话
	 */
	String telephone;
	/**
	 * 网址
	 */
	String network;

	int version;
	
	/**
	 * 积分比例上限
	 */
	String pointRateMaxValue;
	/**
	 * 积分比例下限
	 */
	String pointRateMinValue;
	
	/**
	 * @return the 积分比例上限
	 */
	public String getPointRateMaxValue() {
		return pointRateMaxValue;
	}
	/**
	 * @param 积分比例上限 the pointRateMaxValue to set
	 */
	public void setPointRateMaxValue(String pointRateMaxValue) {
		this.pointRateMaxValue = pointRateMaxValue;
	}
	/**
	 * @return the 积分比例下限
	 */
	public String getPointRateMinValue() {
		return pointRateMinValue;
	}
	/**
	 * @param 积分比例下限 the pointRateMinValue to set
	 */
	public void setPointRateMinValue(String pointRateMinValue) {
		this.pointRateMinValue = pointRateMinValue;
	}
	/**
	 * @return the version
	 */
	public int getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(int version) {
		this.version = version;
	}
	
	/**
	 * @return the 电话
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * @param 电话
	 *           
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * @return the 网址
	 */
	public String getNetwork() {
		return network;
	}

	/**
	 * @param 网址
	 *           
	 */
	public void setNetwork(String network) {
		this.network = network;
	}
	
	/**
	 * @return the 积分兑换本地货币比率
	 */
	public String getPvExchangeCurrencyRate() {
		return pvExchangeCurrencyRate;
	}


	/**
	 * @param 积分兑换本地货币比率 the pvExchangeCurrencyRate to set
	 */
	public void setPvExchangeCurrencyRate(String pvExchangeCurrencyRate) {
		this.pvExchangeCurrencyRate = pvExchangeCurrencyRate;
	}


	/**
	 * @return the 互生币兑换本地货币比率
	 */
	public String getHsbExchangeCurrencyRate() {
		return hsbExchangeCurrencyRate;
	}


	/**
	 * @param 互生币兑换本地货币比率 the hsbExchangeCurrencyRate to set
	 */
	public void setHsbExchangeCurrencyRate(String hsbExchangeCurrencyRate) {
		this.hsbExchangeCurrencyRate = hsbExchangeCurrencyRate;
	}


	/**
	 * @return the 托管企业充值上限
	 */
	public String getEntTChargeMaxValue() {
		return entTChargeMaxValue;
	}


	/**
	 * @param 托管企业充值上限 the entTChargeMaxValue to set
	 */
	public void setEntTChargeMaxValue(String entTChargeMaxValue) {
		this.entTChargeMaxValue = entTChargeMaxValue;
	}


	/**
	 * @return the 托管企业充值下限
	 */
	public String getEntTChargeMinValue() {
		return entTChargeMinValue;
	}


	/**
	 * @param 托管企业充值下限 the entTChargeMinValue to set
	 */
	public void setEntTChargeMinValue(String entTChargeMinValue) {
		this.entTChargeMinValue = entTChargeMinValue;
	}


	/**
	 * @return the 成员企业充值上限
	 */
	public String getEntBChargeMaxValue() {
		return entBChargeMaxValue;
	}


	/**
	 * @param 成员企业充值上限 the entBChargeMaxValue to set
	 */
	public void setEntBChargeMaxValue(String entBChargeMaxValue) {
		this.entBChargeMaxValue = entBChargeMaxValue;
	}


	/**
	 * @return the 成员企业充值下限
	 */
	public String getEntBChargeMinValue() {
		return entBChargeMinValue;
	}


	/**
	 * @param 成员企业充值下限 the entBChargeMinValue to set
	 */
	public void setEntBChargeMinValue(String entBChargeMinValue) {
		this.entBChargeMinValue = entBChargeMinValue;
	}


	/**
	 * @return the 持卡人充值上限
	 */
	public String getCarderChargeMaxValue() {
		return carderChargeMaxValue;
	}


	/**
	 * @param 持卡人充值上限 the carderChargeMaxValue to set
	 */
	public void setCarderChargeMaxValue(String carderChargeMaxValue) {
		this.carderChargeMaxValue = carderChargeMaxValue;
	}


	/**
	 * @return the 持卡人充值下限
	 */
	public String getCarderChargeMinValue() {
		return carderChargeMinValue;
	}


	/**
	 * @param 持卡人充值下限 the carderChargeMinValue to set
	 */
	public void setCarderChargeMinValue(String carderChargeMinValue) {
		this.carderChargeMinValue = carderChargeMinValue;
	}


	public String toString(){
		return JSONObject.toJSONString(this);
	}
}
