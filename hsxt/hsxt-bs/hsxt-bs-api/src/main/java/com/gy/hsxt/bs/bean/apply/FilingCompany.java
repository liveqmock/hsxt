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
 * @ClassName: FilingCompany 
 * @Description: 报备企业信息
 *
 * @author: xiaofl 
 * @date: 2015-12-14 下午4:21:51 
 * @version V1.0 
 

 */ 
public class FilingCompany implements Serializable {

    private static final long serialVersionUID = 4745542246885184275L;

    /** 申请编号 */
    private String applyId;

    /** 企业名称 */
    private String entCustName;

    /** 企业性质 */
    private String entType;

    /** 企业地址 */
    private String entAddress;

    /** 企业法人代表 */
    private String legalName;

    /** 法人代表证件类型 */
    private Integer legalType;

    /** 法人代表证件号码 */
    private String legalNo;

    /** 联系人 */
    private String linkman;

    /** 联系人电话 */
    private String phone;

    /** 企业经营范围 */
    private String dealArea;

    /** 所属国家 */
    private String countryNo;

    /** 所属省份 */
    private String provinceNo;

    /** 所属城市 */
    private String cityNo;

    /** 营业执照号 */
    private String licenseNo;

    /** 创建日期 */
    private String createdDate;

    /** 创建者 */
    private String createdBy;

    /** 修改日期 */
    private String updatedDate;

    /** 修改者 */
    private String updatedBy;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getEntCustName() {
        return entCustName;
    }

    public void setEntCustName(String entCustName) {
        this.entCustName = entCustName;
    }

    public String getEntType() {
        return entType;
    }

    public void setEntType(String entType) {
        this.entType = entType;
    }

    public String getEntAddress() {
        return entAddress;
    }

    public void setEntAddress(String entAddress) {
        this.entAddress = entAddress;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public Integer getLegalType() {
        return legalType;
    }

    public void setLegalType(Integer legalType) {
        this.legalType = legalType;
    }

    public String getLegalNo() {
        return legalNo;
    }

    public void setLegalNo(String legalNo) {
        this.legalNo = legalNo;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDealArea() {
        return dealArea;
    }

    public void setDealArea(String dealArea) {
        this.dealArea = dealArea;
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

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
