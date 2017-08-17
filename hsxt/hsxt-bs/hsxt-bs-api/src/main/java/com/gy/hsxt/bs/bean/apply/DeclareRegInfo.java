/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bean.apply;

import com.gy.hsxt.bs.bean.base.OptInfo;

/**
 * 申报信息
 *
 * @Package : com.gy.hsxt.bs.bean.apply
 * @ClassName : DeclareRegInfo
 * @Description : 申报信息
 * @Author : xiaofl
 * @Date : 2015-12-14 下午4:14:00
 * @Version V1.0
 */
public class DeclareRegInfo extends OptInfo {

    private static final long serialVersionUID = 2464203929196515789L;

    /**
     * 申请编号
     */
    private String applyId;

    /**
     * 被申报企业客户号
     */
    private String toEntCustId;

    /**
     * 被申报企业名称
     */
    private String toEntCustName;

    /**
     * 被申报企业英文名称
     */
    @Deprecated
    private String toEntEnName;

    /**
     * 企业所属国家
     */
    private String countryNo;

    /**
     * 企业所属省
     */
    private String provinceNo;

    /**
     * 企业所属城市
     */
    private String cityNo;

    /**
     * 币种代码
     */
    private String currencyCode;

    /**
     * 备注
     */
    private String remark;

    /**
     * 被申报企业购买资源段
     */
    private Integer toBuyResRange;

    /**
     * 被申报企业启用资源号
     */
    private String toEntResNo;

    /**
     * 资源号选择方式 0-顺序选择 1-人工选择
     */
    private Integer toSelectMode;

    /**
     * 被申报者所属管理公司资源号
     */
    private String toMResNo;

    /**
     * 被申报企业客户类型
     */
    private Integer toCustType;

    /**
     * 被申报增值节点父节点客户号
     */
    private String toPnodeCustId;

    /**
     * 被申报增值节点父节点资源号
     */
    private String toPnodeResNo;

    /**
     * 被申报选择增值节点
     */
    private String toInodeResNo;

    /**
     * 被申报选择增值节点对应区域
     */
    private Integer toInodeLorR;

    /**
     * 被申报办理期限(截止办理日期)
     */
    private String toLimiteDate;

    /**
     * 申报者企业客户号
     */
    private String frEntCustId;

    /**
     * 申报者企业名称
     */
    private String frEntCustName;

    /**
     * 申报者企业资源号
     */
    private String frEntResNo;

    /**
     * 推广企业企业客户号
     */
    private String spreadEntCustId;

    /**
     * 推广企业企业名称
     */
    private String spreadEntCustName;

    /**
     * 推广企业企业资源号
     */
    private String spreadEntResNo;

    /**
     * 申报者所属管理公司资源号
     */
    private String frMEntResNo;

    /**
     * 申报者所属管理公司名称
     */
    private String frMCorpName;

    /**
     * 系统注册日期
     **/
    private String createdDate;

    /**
     * 经营类型 0：普通 1：连锁企业
     */
    private Integer toBusinessType;

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

    @Deprecated
    public String getToEntEnName() {
        return toEntEnName;
    }

    @Deprecated
    public void setToEntEnName(String toEntEnName) {
        this.toEntEnName = toEntEnName;
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

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getToBuyResRange() {
        return toBuyResRange;
    }

    public void setToBuyResRange(Integer toBuyResRange) {
        this.toBuyResRange = toBuyResRange;
    }

    public String getToEntResNo() {
        return toEntResNo;
    }

    public void setToEntResNo(String toEntResNo) {
        this.toEntResNo = toEntResNo;
    }

    public Integer getToSelectMode() {
        return toSelectMode;
    }

    public void setToSelectMode(Integer toSelectMode) {
        this.toSelectMode = toSelectMode;
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

    public Integer getToInodeLorR() {
        return toInodeLorR;
    }

    public void setToInodeLorR(Integer toInodeLorR) {
        this.toInodeLorR = toInodeLorR;
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

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getToBusinessType() {
        return toBusinessType;
    }

    public void setToBusinessType(Integer toBusinessType) {
        this.toBusinessType = toBusinessType;
    }

}
