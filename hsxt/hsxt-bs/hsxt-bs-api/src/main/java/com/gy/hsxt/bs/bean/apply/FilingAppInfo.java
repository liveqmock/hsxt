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
 * @ClassName: FilingAppInfo
 * @Description: 报备申请信息
 * 
 * @author: xiaofl
 * @date: 2015-12-14 下午4:20:28
 * @version V1.0
 */
public class FilingAppInfo implements Serializable {

    private static final long serialVersionUID = -5097038906488054100L;

    /** 申请编号 */
    private String applyId;

    /** 企业名称 */
    private String entCustName;

    /** 联系人 */
    private String linkman;

    /** 联系人电话 */
    private String phone;

    /** 审核结果 */
    private Integer status;

    /** 报备日期 */
    private String filingDate;

    /** 是否存在相同项 */
    private Boolean existSameItem;

    /** 报备企业所在国家代码 */
    private String countryNo;

    /** 报备企业所在省代码 */
    private String provinceNo;

    /** 报备企业所在城市代码 */
    private String cityNo;

    /** 状态日期 */
    private String statusDate;

    /** 企业地址 */
    private String entAddress;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getFilingDate() {
        return filingDate;
    }

    public void setFilingDate(String filingDate) {
        this.filingDate = filingDate;
    }

    public Boolean getExistSameItem() {
        return existSameItem;
    }

    public void setExistSameItem(Boolean existSameItem) {
        this.existSameItem = existSameItem;
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

    public String getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(String statusDate) {
        this.statusDate = statusDate;
    }

    public String getEntAddress() {
        return entAddress;
    }

    public void setEntAddress(String entAddress) {
        this.entAddress = entAddress;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
