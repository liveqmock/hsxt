/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bean.apply;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @Package: com.gy.hsxt.bs.bean.apply
 * @ClassName: DeclareAppInfo
 * @Description: 企业申报申请信息
 * 
 * @author: xiaofl
 * @date: 2015-12-14 下午4:04:20
 * @version V1.0
 */
public class DeclareAppInfo implements Serializable {

    private static final long serialVersionUID = -1832413111755459359L;

    /** 申请编号 */
    private String applyId;

    /** 申报企业中文名称 */
    private String toEntName;

    /** 申报企业英文名称 */
    private String toEntEnName;

    /** 申报企业所在地 */
    private String toEntAddress;

    /** 尚可用配额 */
    private Integer availableQuota;

    /** 拟用企业管理号 */
    private String toEntResNo;

    /**
     * 互生号选择方式 0-顺序选择 1-人工选择
     */
    private Integer toSelectMode;

    /** 启用资源类型 */
    private Integer toBuyResRange;

    /** 报送企业名称 */
    private String frEntCustName;

    /** 报送企业管理号 */
    private String frEntResNo;

    /** 申报操作员 */
    private String applyOperator;

    /** 报送时间 */
    private String applyDate;

    /** 所属国家 */
    private String countryNo;

    /** 所属省份 */
    private String provinceNo;

    /** 所属城市 */
    private String cityNo;

    /** 被申报企业客户类型 */
    private Integer toCustType;

    /** 服务公司可用互生号列表 **/
    private List<String> serResNoList;

    /** 经营类型 0：普通 1：连锁企业 */
    private Integer toBusinessType;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getToEntName() {
        return toEntName;
    }

    public void setToEntName(String toEntName) {
        this.toEntName = toEntName;
    }

    public String getToEntEnName() {
        return toEntEnName;
    }

    public void setToEntEnName(String toEntEnName) {
        this.toEntEnName = toEntEnName;
    }

    public String getToEntAddress() {
        return toEntAddress;
    }

    public void setToEntAddress(String toEntAddress) {
        this.toEntAddress = toEntAddress;
    }

    public Integer getAvailableQuota() {
        return availableQuota;
    }

    public void setAvailableQuota(Integer availableQuota) {
        this.availableQuota = availableQuota;
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

    public Integer getToBuyResRange() {
        return toBuyResRange;
    }

    public void setToBuyResRange(Integer toBuyResRange) {
        this.toBuyResRange = toBuyResRange;
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

    public String getApplyOperator() {
        return applyOperator;
    }

    public void setApplyOperator(String applyOperator) {
        this.applyOperator = applyOperator;
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

    public Integer getToCustType() {
        return toCustType;
    }

    public void setToCustType(Integer toCustType) {
        this.toCustType = toCustType;
    }

    public List<String> getSerResNoList() {
        return serResNoList;
    }

    public void setSerResNoList(List<String> serResNoList) {
        this.serResNoList = serResNoList;
    }

    public Integer getToBusinessType() {
        return toBusinessType;
    }

    public void setToBusinessType(Integer toBusinessType) {
        this.toBusinessType = toBusinessType;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
