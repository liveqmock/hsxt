/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.lcs.bean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 本地平台初始化信息
 * 
 * @Package: com.gy.hsxt.lcs.bean
 * @ClassName: LocalInfo
 * @Description: TODO
 * 
 * @author: yangjianguo
 * @date: 2015-12-15 下午2:01:38
 * @version V1.0
 */
public class LocalInfo implements Serializable {

    private static final long serialVersionUID = -3126047369479002175L;

    /** 平台代码 **/
    private String platNo;

    /** 平台中文名 **/
    private String platNameCn;

    /** 国家代码 **/
    private String countryNo;

    /** 国家中文名 **/
    private String countryNameCn;

    /** 国家名称 **/
    private String countryName;

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
    private String exchangeRate;

    /** 互生币代码 **/
    private String hsbCode;

    /** 积分代码 **/
    private String pointCode;

    /** 总平台代码 **/
    private String centerPlatNo;

    /** 互生系统技术支持方地址 **/
    private String techSupportAddress;

    /** 互生系统技术支持方联系电话 **/
    private String techSupportPhone;

    /** 互生系统技术支持方授权代表 **/
    private String techSupportContact;

    /** 互生系统技术支持方名称 **/
    private String techSupportCorpName;

    /** 互生合同证书验证地址 **/
    private String contractVerifyAddr;

    /** 互生地区平台门户 **/
    private String platWebSite;

    /** 互生管理公司门户 **/
    private String manageWebSite;

    /** 互生服务公司门户 **/
    private String serviceWebSite;

    /** 互生托管、成员企业门户 **/
    private String companyWebSite;

    /** 互生消费者门户 **/
    private String personWebSite;

    /** 互生支付结果跳转地址 **/
    private String webPayJumpUrl;

    /**
     * @return the centerPlatNo
     */
    public String getCenterPlatNo() {
        return centerPlatNo;
    }

    /**
     * @param centerPlatNo
     *            the centerPlatNo to set
     */
    public void setCenterPlatNo(String centerPlatNo) {
        this.centerPlatNo = centerPlatNo;
    }

    /**
     * @return the hsbCode
     */
    public String getHsbCode() {
        return hsbCode;
    }

    /**
     * @param hsbCode
     *            the hsbCode to set
     */
    public void setHsbCode(String hsbCode) {
        this.hsbCode = hsbCode;
    }

    /**
     * @return the pointCode
     */
    public String getPointCode() {
        return pointCode;
    }

    /**
     * @param pointCode
     *            the pointCode to set
     */
    public void setPointCode(String pointCode) {
        this.pointCode = pointCode;
    }

    /**
     * 获取平台互生号 ,等于00000000+国家代码
     */
    public String getPlatResNo() {
        return "00000000" + countryNo;
    }

    public void setPlatResNo(String platResNo) {
        // this.platResNo = platResNo;
    }

    public String getPlatNo() {
        return platNo;
    }

    public void setPlatNo(String platNo) {
        this.platNo = platNo;
    }

    public String getPlatNameCn() {
        return platNameCn;
    }

    public void setPlatNameCn(String platNameCn) {
        this.platNameCn = platNameCn;
    }

    public String getCountryNo() {
        return countryNo;
    }

    public void setCountryNo(String countryNo) {
        this.countryNo = countryNo;
    }

    public String getCountryNameCn() {
        return countryNameCn;
    }

    public void setCountryNameCn(String countryNameCn) {
        this.countryNameCn = countryNameCn;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getCurrencyNo() {
        return currencyNo;
    }

    public void setCurrencyNo(String currencyNo) {
        this.currencyNo = currencyNo;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyNameCn() {
        return currencyNameCn;
    }

    public void setCurrencyNameCn(String currencyNameCn) {
        this.currencyNameCn = currencyNameCn;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public int getPrecisionNum() {
        return precisionNum;
    }

    public void setPrecisionNum(int precisionNum) {
        this.precisionNum = precisionNum;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getTechSupportAddress() {
        return techSupportAddress;
    }

    public void setTechSupportAddress(String techSupportAddress) {
        this.techSupportAddress = techSupportAddress;
    }

    public String getTechSupportPhone() {
        return techSupportPhone;
    }

    public void setTechSupportPhone(String techSupportPhone) {
        this.techSupportPhone = techSupportPhone;
    }

    public String getTechSupportContact() {
        return techSupportContact;
    }

    public void setTechSupportContact(String techSupportContact) {
        this.techSupportContact = techSupportContact;
    }

    public String getTechSupportCorpName() {
        return techSupportCorpName;
    }

    public void setTechSupportCorpName(String techSupportCorpName) {
        this.techSupportCorpName = techSupportCorpName;
    }

    public String getContractVerifyAddr() {
        return contractVerifyAddr;
    }

    public void setContractVerifyAddr(String contractVerifyAddr) {
        this.contractVerifyAddr = contractVerifyAddr;
    }

    public String getPlatWebSite() {
        return platWebSite;
    }

    public void setPlatWebSite(String platWebSite) {
        this.platWebSite = platWebSite;
    }

    public String getManageWebSite() {
        return manageWebSite;
    }

    public void setManageWebSite(String manageWebSite) {
        this.manageWebSite = manageWebSite;
    }

    public String getServiceWebSite() {
        return serviceWebSite;
    }

    public void setServiceWebSite(String serviceWebSite) {
        this.serviceWebSite = serviceWebSite;
    }

    public String getCompanyWebSite() {
        return companyWebSite;
    }

    public void setCompanyWebSite(String companyWebSite) {
        this.companyWebSite = companyWebSite;
    }

    public String getPersonWebSite() {
        return personWebSite;
    }

    public void setPersonWebSite(String personWebSite) {
        this.personWebSite = personWebSite;
    }

    public String getWebPayJumpUrl() {
        return webPayJumpUrl;
    }

    public void setWebPayJumpUrl(String webPayJumpUrl) {
        this.webPayJumpUrl = webPayJumpUrl;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
