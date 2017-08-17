/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bean.apply;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @Package: com.gy.hsxt.bs.bean.apply
 * @ClassName: EntBaseInfo
 * @Description: 申报企业信息（官网接口用）
 * 
 * @author: xiaofl
 * @date: 2015-12-11 下午4:10:00
 * @version V1.0
 */
public class EntInfo implements Serializable {

    private static final long serialVersionUID = 5831405643028617012L;

    /** 客户类型 **/
    private Integer custType;

    /** 企业中文名称 **/
    private String entName;

    /** 企业英文名称 **/
    private String entNameEn;

    /** 企业互生号 **/
    private String entResNo;

    /** 申报日期 **/
    private String applyDate;

    /** 国家代码 */
    private String countryNo;

    /** 省代码 */
    private String provinceNo;

    /** 城市代码 */
    private String cityNo;

    /** 工商登记地址 **/
    private String bizRegAddr;

    /** 工商登记电话 **/
    private String bizRegPhone;

    /** 应付金额 **/
    private String amount;

    /** 支付状态 **/
    private Integer payStatus;

    /** 办理期限 */
    private String toLimiteDate;

    /** 法人代表名称 */
    private String legalName;

    /** 联系人 */
    private String linkman;

    /** 联系人手机 */
    private String linkmanMobile;

    /** 联系人地址 */
    private String linkmanAddr;

    /** 企业类型 */
    private String entType;

    /** 营业执照号 */
    private String licenseNo;

    /** 组织机构代码 */
    private String orgNo;

    /** 纳税人识别号 */
    private String taxNo;

    /** 企业成立日期 */
    private String establishingDate;

    /** 营业期限 DATE OR long_term((长期) */
    private String limitDate;

    /** 经营范围 */
    private String dealArea;

    /** 邮箱地址 */
    private String email;

    /** 办公电话 */
    private String officeTel;

    /** 邮政编码 */
    private String zipCode;

    /** 职位 */
    private String job;

    /** 联系人工作QQ */
    private String qq;

    /** 企业网址 */
    private String webSite;

    /** 传真号码 */
    private String fax;

    /** 地址(国家 + 省 + 城市) */
    private String address;

    /** 申报订单号 **/
    private String orderNo;

    public Integer getCustType() {
        return custType;
    }

    public void setCustType(Integer custType) {
        this.custType = custType;
    }

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName;
    }

    public String getEntNameEn() {
        return entNameEn;
    }

    public void setEntNameEn(String entNameEn) {
        this.entNameEn = entNameEn;
    }

    public String getEntResNo() {
        return entResNo;
    }

    public void setEntResNo(String entResNo) {
        this.entResNo = entResNo;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getCountryNo() {
        return countryNo;
    }

    public void setCountryNo(String countryNo) {
        this.countryNo = countryNo;
    }

    public String getProvinceNo() {
        return provinceNo;
    }

    public void setProvinceNo(String provinceNo) {
        this.provinceNo = provinceNo;
    }

    public String getCityNo() {
        return cityNo;
    }

    public void setCityNo(String cityNo) {
        this.cityNo = cityNo;
    }

    public String getBizRegAddr() {
        return bizRegAddr;
    }

    public void setBizRegAddr(String bizRegAddr) {
        this.bizRegAddr = bizRegAddr;
    }

    public String getBizRegPhone() {
        return bizRegPhone;
    }

    public void setBizRegPhone(String bizRegPhone) {
        this.bizRegPhone = bizRegPhone;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public String getToLimiteDate() {
        return toLimiteDate;
    }

    public void setToLimiteDate(String toLimiteDate) {
        this.toLimiteDate = toLimiteDate;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getLinkmanMobile() {
        return linkmanMobile;
    }

    public void setLinkmanMobile(String linkmanMobile) {
        this.linkmanMobile = linkmanMobile;
    }

    public String getLinkmanAddr() {
        return linkmanAddr;
    }

    public void setLinkmanAddr(String linkmanAddr) {
        this.linkmanAddr = linkmanAddr;
    }

    public String getEntType() {
        return entType;
    }

    public void setEntType(String entType) {
        this.entType = entType;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }

    public String getTaxNo() {
        return taxNo;
    }

    public void setTaxNo(String taxNo) {
        this.taxNo = taxNo;
    }

    public String getEstablishingDate() {
        return establishingDate;
    }

    public void setEstablishingDate(String establishingDate) {
        this.establishingDate = establishingDate;
    }

    public String getLimitDate() {
        return limitDate;
    }

    public void setLimitDate(String limitDate) {
        this.limitDate = limitDate;
    }

    public String getDealArea() {
        return dealArea;
    }

    public void setDealArea(String dealArea) {
        this.dealArea = dealArea;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOfficeTel() {
        return officeTel;
    }

    public void setOfficeTel(String officeTel) {
        this.officeTel = officeTel;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
