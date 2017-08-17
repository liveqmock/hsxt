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
 * @ClassName: FilingQueryParam
 * @Description: 报备查询参数
 * 
 * @author: xiaofl
 * @date: 2015-12-14 下午4:22:07
 * @version V1.0
 */
public class FilingQueryParam implements Serializable {

    private static final long serialVersionUID = -8429862552964995380L;

    /** 操作员客户号 */
    private String optCustId;

    /** 服务公司互生号 */
    private String serResNo;

    /** 是否异议报备 */
    private Boolean isDisagreed;

    /** 报备开始时间 */
    private String startDate;

    /** 报备结束时间 */
    private String endDate;

    /** 报备企业名称 */
    private String entName;

    /** 联系人姓名 */
    private String linkman;

    /** 联系人电话 */
    private String linkmanTel;

    /** 股东姓名/名称 */
    private String shareHolder;

    /** 报备企业所在国家代码 */
    private String countryNo;

    /** 报备企业所在省代码 */
    private String provinceNo;

    /** 报备企业所在城市代码 */
    private String cityNo;

    /** 证件号码 */
    private String legalNo;

    /** 审核结果 */
    private Integer status;

    public String getOptCustId() {
        return optCustId;
    }

    public void setOptCustId(String optCustId) {
        this.optCustId = optCustId;
    }

    public String getSerResNo() {
        return serResNo;
    }

    public void setSerResNo(String serResNo) {
        this.serResNo = serResNo;
    }

    public Boolean getIsDisagreed() {
        return isDisagreed;
    }

    public void setIsDisagreed(Boolean isDisagreed) {
        this.isDisagreed = isDisagreed;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getLinkmanTel() {
        return linkmanTel;
    }

    public void setLinkmanTel(String linkmanTel) {
        this.linkmanTel = linkmanTel;
    }

    public String getShareHolder() {
        return shareHolder;
    }

    public void setShareHolder(String shareHolder) {
        this.shareHolder = shareHolder;
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

    public String getLegalNo() {
        return legalNo;
    }

    public void setLegalNo(String legalNo) {
        this.legalNo = legalNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
