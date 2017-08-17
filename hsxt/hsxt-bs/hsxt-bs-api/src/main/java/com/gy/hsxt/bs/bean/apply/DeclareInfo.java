/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.apply;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.bs.bean.base.OptInfo;

/**
 * 
 * @Package: com.gy.hsxt.bs.bean.apply
 * @ClassName: DeclareInfo
 * @Description: 申报信息
 * 
 * @author: xiaofl
 * @date: 2015-9-1 上午9:31:28
 * @version V1.0
 */
public class DeclareInfo extends OptInfo implements Serializable {

    private static final long serialVersionUID = 4297289392511242514L;

    /** 申请编号 */
    private String applyId;

    /** 被申报企业客户号 */
    private String toEntCustId;

    /** 被申报企业名称 */
    private String toEntCustName;

    /** 被申报企业英文名称 */
    private String toEntEnName;

    /** 被申报企业启用资源号 */
    private String toEntResNo;

    /**
     * 互生号选择方式 0-顺序选择 1-人工选择
     */
    private Integer toSelectMode;

    /** 被申报者所属管理公司资源号 */
    private String toMResNo;

    /** 被申报企业客户类型 */
    private Integer toCustType;

    /** 被申报企业购买资源段 */
    private Integer toBuyResRange;

    /** 被申报增值节点父节点客户号 */
    private String toPnodeCustId;

    /** 被申报增值节点父节点资源号 */
    private String toPnodeResNo;

    /** 被申报选择增值节点 */
    private String toInodeResNo;

    /** 被申报选择增值节点对应区域 */
    private Integer toInodeLorR;

    /** 被申报办理期限(截止办理日期) */
    private String toLimiteDate;

    /** 申报者企业客户号 */
    private String frEntCustId;

    /** 申报者企业名称 */
    private String frEntCustName;

    /** 申报者企业资源号 */
    private String frEntResNo;

    /** 推广企业企业客户号 */
    private String spreadEntCustId;

    /** 推广企业企业名称 */
    private String spreadEntCustName;

    /** 推广企业企业资源号 */
    private String spreadEntResNo;

    /** 申报者所属管理公司资源号 */
    private String frMEntResNo;

    /** 申报者所属管理公司名称 */
    private String frMCorpName;

    /** 申报状态 */
    private Integer appStatus;

    /** 资源费订单号 */
    private String orderNo;

    /** 资源费方案编号 */
    private String resFeeId;

    /** 资源费分配方式 */
    private Integer resFeeAllocMode;

    /** 资源费收费方式 */
    private Integer resFeeChargeMode;

    /** 是否已收费 */
    private Boolean chargeFlag;

    /** 企业开户标识 */
    private Boolean openAccFlag;

    /** 开启系统时间 */
    private String openAccDate;

    /** 增值开启标识 */
    private Boolean openVasFlag;

    /** 描述 */
    private String remark;

    /** 所属国家 */
    private String countryNo;

    /** 所属省份 */
    private String provinceNo;

    /** 所属城市 */
    private String cityNo;

    /** 企业性质 */
    private String entType;

    /** 法人代表名称 */
    private String legalName;

    /** 法人代表证件类型 */
    private Integer legalCreType;

    /** 法人代表证件号码 */
    private String legalCreNo;

    /** 营业期限 DATE OR long_term((长期) */
    private String limitDate;

    /** 企业工商登记地址 */
    private String entAddr;

    /** 企业工商登记电话 */
    private String phone;

    /** 营业执照号 */
    private String licenseNo;

    /** 组织机构代码 */
    private String orgNo;

    /** 纳税人识别号 */
    private String taxNo;

    /** 经营范围 */
    private String dealArea;

    /** 注册资金 */
    private String registerFee;

    /** 企业成立日期 */
    private String establishingDate;

    /** 最近年检 */
    private String yearCheck;

    /** 创建日期 */
    private String createdDate;

    /** 修改日期 */
    private String updateDate;

    /** 经营类型 0：普通 1：连锁企业 */
    private Integer toBusinessType;

    /**
     * 重新申报标识 true：已重新申报 false：未重新申报
     */
    private boolean redoFlag;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getToEntCustId() {
        return toEntCustId;
    }

    public void setToEntCustId(String toEntCustId) {
        this.toEntCustId = toEntCustId;
    }

    public String getToEntCustName() {
        return toEntCustName;
    }

    public void setToEntCustName(String toEntCustName) {
        this.toEntCustName = toEntCustName;
    }

    public String getToEntEnName() {
        return toEntEnName;
    }

    public void setToEntEnName(String toEntEnName) {
        this.toEntEnName = toEntEnName;
    }

    public String getToEntResNo() {
        return toEntResNo;
    }

    public void setToEntResNo(String toEntResNo) {
        this.toEntResNo = toEntResNo;
    }

    public String getToMResNo() {
        return toMResNo;
    }

    public void setToMResNo(String toMResNo) {
        this.toMResNo = toMResNo;
    }

    public Integer getToCustType() {
        return toCustType;
    }

    public void setToCustType(Integer toCustType) {
        this.toCustType = toCustType;
    }

    public Integer getToBuyResRange() {
        return toBuyResRange;
    }

    public void setToBuyResRange(Integer toBuyResRange) {
        this.toBuyResRange = toBuyResRange;
    }

    public String getToPnodeCustId() {
        return toPnodeCustId;
    }

    public void setToPnodeCustId(String toPnodeCustId) {
        this.toPnodeCustId = toPnodeCustId;
    }

    public String getToPnodeResNo() {
        return toPnodeResNo;
    }

    public void setToPnodeResNo(String toPnodeResNo) {
        this.toPnodeResNo = toPnodeResNo;
    }

    public String getToInodeResNo() {
        return toInodeResNo;
    }

    public void setToInodeResNo(String toInodeResNo) {
        this.toInodeResNo = toInodeResNo;
    }

    public String getToLimiteDate() {
        return toLimiteDate;
    }

    public void setToLimiteDate(String toLimiteDate) {
        this.toLimiteDate = toLimiteDate;
    }

    public String getFrEntCustId() {
        return frEntCustId;
    }

    public void setFrEntCustId(String frEntCustId) {
        this.frEntCustId = frEntCustId;
    }

    public String getFrEntCustName() {
        return frEntCustName;
    }

    public void setFrEntCustName(String frEntCustName) {
        this.frEntCustName = frEntCustName;
    }

    public String getFrEntResNo() {
        return frEntResNo;
    }

    public void setFrEntResNo(String frEntResNo) {
        this.frEntResNo = frEntResNo;
    }

    public String getSpreadEntCustId() {
        return spreadEntCustId;
    }

    public void setSpreadEntCustId(String spreadEntCustId) {
        this.spreadEntCustId = spreadEntCustId;
    }

    public String getSpreadEntCustName() {
        return spreadEntCustName;
    }

    public void setSpreadEntCustName(String spreadEntCustName) {
        this.spreadEntCustName = spreadEntCustName;
    }

    public String getSpreadEntResNo() {
        return spreadEntResNo;
    }

    public void setSpreadEntResNo(String spreadEntResNo) {
        this.spreadEntResNo = spreadEntResNo;
    }

    public String getFrMEntResNo() {
        return frMEntResNo;
    }

    public void setFrMEntResNo(String frMEntResNo) {
        this.frMEntResNo = frMEntResNo;
    }

    public String getFrMCorpName() {
        return frMCorpName;
    }

    public void setFrMCorpName(String frMCorpName) {
        this.frMCorpName = frMCorpName;
    }

    public Integer getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(Integer appStatus) {
        this.appStatus = appStatus;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getResFeeId() {
        return resFeeId;
    }

    public void setResFeeId(String resFeeId) {
        this.resFeeId = resFeeId;
    }

    public Integer getResFeeAllocMode() {
        return resFeeAllocMode;
    }

    public void setResFeeAllocMode(Integer resFeeAllocMode) {
        this.resFeeAllocMode = resFeeAllocMode;
    }

    public Integer getResFeeChargeMode() {
        return resFeeChargeMode;
    }

    public void setResFeeChargeMode(Integer resFeeChargeMode) {
        this.resFeeChargeMode = resFeeChargeMode;
    }

    public Boolean getChargeFlag() {
        return chargeFlag;
    }

    public void setChargeFlag(Boolean chargeFlag) {
        this.chargeFlag = chargeFlag;
    }

    public Boolean getOpenAccFlag() {
        return openAccFlag;
    }

    public void setOpenAccFlag(Boolean openAccFlag) {
        this.openAccFlag = openAccFlag;
    }

    public Boolean getOpenVasFlag() {
        return openVasFlag;
    }

    public void setOpenVasFlag(Boolean openVasFlag) {
        this.openVasFlag = openVasFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public Integer getLegalCreType() {
        return legalCreType;
    }

    public void setLegalCreType(Integer legalCreType) {
        this.legalCreType = legalCreType;
    }

    public String getLegalCreNo() {
        return legalCreNo;
    }

    public void setLegalCreNo(String legalCreNo) {
        this.legalCreNo = legalCreNo;
    }

    public String getLimitDate() {
        return limitDate;
    }

    public void setLimitDate(String limitDate) {
        this.limitDate = limitDate;
    }

    public String getEntAddr() {
        return entAddr;
    }

    public void setEntAddr(String entAddr) {
        this.entAddr = entAddr;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getDealArea() {
        return dealArea;
    }

    public void setDealArea(String dealArea) {
        this.dealArea = dealArea;
    }

    public String getRegisterFee() {
        return registerFee;
    }

    public void setRegisterFee(String registerFee) {
        this.registerFee = registerFee;
    }

    public String getEstablishingDate() {
        return establishingDate;
    }

    public void setEstablishingDate(String establishingDate) {
        this.establishingDate = establishingDate;
    }

    public String getYearCheck() {
        return yearCheck;
    }

    public void setYearCheck(String yearCheck) {
        this.yearCheck = yearCheck;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getToInodeLorR() {
        return toInodeLorR;
    }

    public void setToInodeLorR(Integer toInodeLorR) {
        this.toInodeLorR = toInodeLorR;
    }

    public String getEntType() {
        return entType;
    }

    public void setEntType(String entType) {
        this.entType = entType;
    }

    public String getOpenAccDate() {
        return openAccDate;
    }

    public void setOpenAccDate(String openAccDate) {
        this.openAccDate = openAccDate;
    }

    public Integer getToBusinessType() {
        return toBusinessType;
    }

    public void setToBusinessType(Integer toBusinessType) {
        this.toBusinessType = toBusinessType;
    }

    public boolean isRedoFlag() {
        return redoFlag;
    }

    public void setRedoFlag(boolean redoFlag) {
        this.redoFlag = redoFlag;
    }

    public Integer getToSelectMode() {
        return toSelectMode;
    }

    public void setToSelectMode(Integer toSelectMode) {
        this.toSelectMode = toSelectMode;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
