/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.model;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.gy.hsxt.common.constant.IRespCode;


/**
 * @Package: com.gy.hsxt.access.pos.model  
 * @ClassName: SyncParamPosOut 
 * @Description: 同步参数到pos机 3.0拆分出企业信息一类 
 *
 * @author: wucl 
 * @date: 2015-11-10 下午3:11:07 
 * @version V1.0
 */
public class SyncParamPosOut {

	/**
	 * 基础信息版本号 N4
	 */
	private String baseInfoVersion;
	/**
	 * 基础信息企业名称 N40 3.0版扩容到ANS128    不足补零  
	 */
	private String entName;
	/**
	 * 基础信息企业类型名称N16
	 */
	private String entTypeName;
	/**
	 * 基础信息服务电话 N25
	 */
	private String servicePhone;
	/**
	 * 基础信息服务网址 N30
	 */
	private String serviceWebSite;
	/**
	 * 基础信息店名N40
	 */
	private String shopName;
	/**
	 * 企业充值最大限额N12 （兑换互生币最大值 3.0中命名 下同 kend）
	 */
	private BigDecimal creChargeMax;
	/**
	 * 企业充值最小限额N12 （兑换互生币最小值）
	 */
	private BigDecimal creChargeMin;
	/**
	 * 代充值最大限额N12 （代兑互生币最大值）
	 */
	private BigDecimal reChargeMax;
	/**
	 * 代充值最小限额N12 （代兑互生币最小值）
	 */
	private BigDecimal reChargeMin;
	
	/**
	 * 企业信息版本号 N4 3.0新增 kend
	 */
	private String entInfoVersion;
	/**
	 * 积分比例最大值  N4 3.0
	 */
	private BigDecimal rateMaxVal;
	/**
	 * 积分比例最小值  N4 3.0
	 */
	private BigDecimal rateMinVal;
	
	/**
	 * 积分信息版本号 N4
	 */
	private String pointInfoVersion;
	/**
	 * 积分转现比率 （积分兑本币比率）
	 */
	private BigDecimal pointExchangeRate;
	/**
	 * 互生币积分转现比率 （互生币兑本币比率）
	 */
	private BigDecimal hsbExchangeRate;
	/**
	 * 积分比例个数 N1  3.0 
	 */
	private int rateCount;
	/**
	 * 5个积分比例
	 */
	private BigDecimal[] pointRates;
	/**
	 * 货币信息版本号 N4
	 */
	private String currencyInfoVersion;
	/**
	 * 货币信息
	 */
	private List<CountryCurrency> currencyList;
	/**
	 * 国家代码版本号 N4
	 */
	private String countryCodeVersion;
	/**
	 * 国家个数
	 */
	private int countryCount;
	/**
	 * 国家代码信息
	 */
	private List<Country> countryList;
	
	//start--added by liuzh on 2016-06-24 --以下是3.01版本新增,软件分版本号600301
	/**
	 * 抵扣券信息版本号 N4 3.01新增 
	 */
	private String deductionVoucherInfoVersion;
	/**
	 * 抵扣劵个数
	 */
	private int deductionVoucherCount;
	/**
	 * 抵扣券张数列表信息
	 */
	private List<Integer> deductionVoucherCountList;
	/**
	 * 抵扣劵面值 
	 */
	private BigDecimal deductionVoucherParValue;
	/**
	 * 抵扣劵金额占比 
	 */
	private BigDecimal deductionVoucherRate;
	//end--added by liuzh on 2016-06-24

	private IRespCode respCode;

	public IRespCode getRespCode() {
		return respCode;
	}

	public void setRespCode(IRespCode respCode) {
		this.respCode = respCode;
	}

	public String getBaseInfoVersion() {
		return baseInfoVersion;
	}

	public void setBaseInfoVersion(String baseInfoVersion) {
		this.baseInfoVersion = baseInfoVersion;
	}

	public String getEntName() {
		return entName;
	}

	public void setEntName(String entName) {
		this.entName = entName;
	}

	public String getServicePhone() {
		return servicePhone;
	}

	public void setServicePhone(String servicePhone) {
		this.servicePhone = servicePhone;
	}

	public String getServiceWebSite() {
		return serviceWebSite;
	}

	public void setServiceWebSite(String serviceWebSite) {
		this.serviceWebSite = serviceWebSite;
	}

	public String getCurrencyInfoVersion() {
		return currencyInfoVersion;
	}

	public void setCurrencyInfoVersion(String currencyInfoVersion) {
		this.currencyInfoVersion = currencyInfoVersion;
	}

//	public List<CountryCurrency> getCurrencyList() {
//		return currencyList;
//	}
//
//	public void setCurrencyList(List<CountryCurrency> currencyList) {
//		this.currencyList = currencyList;
//	}

	public String getCountryCodeVersion() {
		return countryCodeVersion;
	}

	public void setCountryCodeVersion(String countryCodeVersion) {
		this.countryCodeVersion = countryCodeVersion;
	}

//	public List<Country> getCountryList() {
//		return countryList;
//	}
//
//	public void setCountryList(List<Country> countryList) {
//		this.countryList = countryList;
//	}

	public int getCountryCount() {
		return countryCount;
	}

	public void setCountryCount(int countryCount) {
		this.countryCount = countryCount;
	}

	public String getEntTypeName() {
		return entTypeName;
	}

	public void setEntTypeName(String entTypeName) {
		this.entTypeName = entTypeName;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getPointInfoVersion() {
		return pointInfoVersion;
	}

	public void setPointInfoVersion(String pointInfoVersion) {
		this.pointInfoVersion = pointInfoVersion;
	}

	public BigDecimal getPointExchangeRate() {
		return pointExchangeRate;
	}

	public void setPointExchangeRate(BigDecimal pointExchangeRate) {
		this.pointExchangeRate = pointExchangeRate;
	}

	public BigDecimal getHsbExchangeRate() {
		return hsbExchangeRate;
	}

	public void setHsbExchangeRate(BigDecimal hsbExchangeRate) {
		this.hsbExchangeRate = hsbExchangeRate;
	}

	public BigDecimal[] getPointRates() {
		return pointRates;
	}

	public void setPointRates(BigDecimal[] pointRates) {
		this.pointRates = pointRates;
	}
	
	

	/**
	 * 获取creChargeMax
	 * @return creChargeMax creChargeMax
	 */
	public BigDecimal getCreChargeMax() {
		return creChargeMax;
	}

	/**
	 * 设置creChargeMax
	 * @param creChargeMax creChargeMax
	 */
	public void setCreChargeMax(BigDecimal creChargeMax) {
		this.creChargeMax = creChargeMax;
	}

	/**
	 * 获取creChargeMin
	 * @return creChargeMin creChargeMin
	 */
	public BigDecimal getCreChargeMin() {
		return creChargeMin;
	}

	/**
	 * 设置creChargeMin
	 * @param creChargeMin creChargeMin
	 */
	public void setCreChargeMin(BigDecimal creChargeMin) {
		this.creChargeMin = creChargeMin;
	}

	public BigDecimal getReChargeMax() {
		return reChargeMax;
	}

	public void setReChargeMax(BigDecimal reChargeMax) {
		this.reChargeMax = reChargeMax;
	}

	public BigDecimal getReChargeMin() {
		return reChargeMin;
	}

	public void setReChargeMin(BigDecimal reChargeMin) {
		this.reChargeMin = reChargeMin;
	}
	
	

	public List<CountryCurrency> getCurrencyList() {
        return currencyList;
    }

    public void setCurrencyList(List<CountryCurrency> currencyList) {
        this.currencyList = currencyList;
    }

    public List<Country> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<Country> countryList) {
        this.countryList = countryList;
    }
    
    public String getEntInfoVersion() {
		return entInfoVersion;
	}

	public void setEntInfoVersion(String entInfoVersion) {
		this.entInfoVersion = entInfoVersion;
	}

	public BigDecimal getRateMaxVal() {
		return rateMaxVal;
	}

	public void setRateMaxVal(BigDecimal rateMaxVal) {
		this.rateMaxVal = rateMaxVal;
	}

	public BigDecimal getRateMinVal() {
		return rateMinVal;
	}

	public void setRateMinVal(BigDecimal rateMinVal) {
		this.rateMinVal = rateMinVal;
	}

	public int getRateCount() {
		return rateCount;
	}

	public void setRateCount(int rateCount) {
		this.rateCount = rateCount;
	}
	

    public String getDeductionVoucherInfoVersion() {
		return deductionVoucherInfoVersion;
	}

	public void setDeductionVoucherInfoVersion(String deductionVoucherInfoVersion) {
		this.deductionVoucherInfoVersion = deductionVoucherInfoVersion;
	}

	public int getDeductionVoucherCount() {
		return deductionVoucherCount;
	}

	public void setDeductionVoucherCount(int deductionVoucherCount) {
		this.deductionVoucherCount = deductionVoucherCount;
	}

	public List<Integer> getDeductionVoucherCountList() {
		return deductionVoucherCountList;
	}

	public void setDeductionVoucherCountList(List<Integer> deductionVoucherCountList) {
		this.deductionVoucherCountList = deductionVoucherCountList;
	}

	public BigDecimal getDeductionVoucherParValue() {
		return deductionVoucherParValue;
	}

	public void setDeductionVoucherParValue(BigDecimal deductionVoucherParValue) {
		this.deductionVoucherParValue = deductionVoucherParValue;
	}

	public BigDecimal getDeductionVoucherRate() {
		return deductionVoucherRate;
	}

	public void setDeductionVoucherRate(BigDecimal deductionVoucherRate) {
		this.deductionVoucherRate = deductionVoucherRate;
	}

	/**
	 * GengLian modified at 2015/02/17
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
		
	}
}
