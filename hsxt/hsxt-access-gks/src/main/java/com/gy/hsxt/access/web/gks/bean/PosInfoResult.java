/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.gks.bean;

import java.io.Serializable;
import java.util.List;

import com.gy.hsxt.lcs.bean.Country;
import com.gy.hsxt.lcs.bean.CountryCurrency;

/***
 * POS机的相关数据信息
 * 
 * @Package: com.gy.hsxt.access.web.bean.posInterface
 * @ClassName: SyncParamOut
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-11-24 下午12:02:24
 * @version V1.0
 */
public class PosInfoResult implements Serializable {
    private static final long serialVersionUID = 7401244428850772246L;

    private String entNo;
    /** 企业资源号 */
    private String entResNo;
    
    private String posNo;

    private String baseInfoVersion;

    private String currencyVersion;

    private String countryVersion;

    private String pointInfoVersion;// 积分信息版本号

    private String entName;

    /**
     * 资源类型:T托管,B成员,S服务公司,M管理公司,F结算公司
     */
    private String entType;

    private List<CountryCurrency> currency;

    private int countryCount;

    private List<Country> countryList;

    private double exchangeRate;// 积分转现比率放大10000倍

    private double hsbExchangeRate;// 互生币转现比率放大10000倍

    private int pointRateCount;

    private String[] pointRates;

    /**
	 * @return the 企业资源号
	 */
	public String getEntResNo() {
		return entResNo;
	}

	/**
	 * @param 企业资源号 the entResNo to set
	 */
	public void setEntResNo(String entResNo) {
		this.entResNo = entResNo;
	}

	public String getEntNo() {
        return entNo;
    }

    public void setEntNo(String entNo) {
        this.entNo = entNo;
    }

    public String getPosNo() {
        return posNo;
    }

    public void setPosNo(String posNo) {
        this.posNo = posNo;
    }

    public String getBaseInfoVersion() {
        return baseInfoVersion;
    }

    public void setBaseInfoVersion(String baseInfoVersion) {
        this.baseInfoVersion = baseInfoVersion;
    }

    public String getCurrencyVersion() {
        return currencyVersion;
    }

    public void setCurrencyVersion(String currencyVersion) {
        this.currencyVersion = currencyVersion;
    }

    public String getCountryVersion() {
        return countryVersion;
    }

    public void setCountryVersion(String countryVersion) {
        this.countryVersion = countryVersion;
    }

    public String getPointInfoVersion() {
        return pointInfoVersion;
    }

    public void setPointInfoVersion(String pointInfoVersion) {
        this.pointInfoVersion = pointInfoVersion;
    }

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName;
    }

    public String getEntType() {
        return entType;
    }

    public void setEntType(String entType) {
        this.entType = entType;
    }

    public List<CountryCurrency> getCurrency() {
        return currency;
    }

    public void setCurrency(List<CountryCurrency> currency) {
        this.currency = currency;
    }

    public int getCountryCount() {
        return countryCount;
    }

    public void setCountryCount(int countryCount) {
        this.countryCount = countryCount;
    }

    public List<Country> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<Country> countryList) {
        this.countryList = countryList;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public double getHsbExchangeRate() {
        return hsbExchangeRate;
    }

    public void setHsbExchangeRate(double hsbExchangeRate) {
        this.hsbExchangeRate = hsbExchangeRate;
    }

    public int getPointRateCount() {
        return pointRateCount;
    }

    public void setPointRateCount(int pointRateCount) {
        this.pointRateCount = pointRateCount;
    }

    public String[] getPointRates() {
        return pointRates;
    }

    public void setPointRates(String[] pointRates) {
        this.pointRates = pointRates;
    }
}
