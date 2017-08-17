/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.entstatus;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @Package: com.gy.hsxt.bs.bean.entstatus
 * @ClassName: EntRealnameBaseInfo
 * @Description: 企业实名认证基本信息
 * 
 * @author: xiaofl
 * @date: 2015-9-7 下午3:30:10
 * @version V1.0
 */
public class EntRealnameBaseInfo implements Serializable {

    private static final long serialVersionUID = 8495865863026808575L;

    /** 申请编号 **/
    private String applyId;

    /** 企业互生号 **/
    private String entResNo;

    /** 企业名称 **/
    private String entName;

    /** 联系人姓名 **/
    private String linkman;

    /** 联系人手机 **/
    private String linkmanMobile;

    /** 国家代码 **/
    private String countryNo;

    /** 省代码 **/
    private String provinceNo;

    /** 城市代码 **/
    private String cityNo;

    /** 申请时间 **/
    private String applyDate;

    /** 状态 **/
    private Integer status;

    /** 状态日期 **/
    private String statusDate;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getEntResNo() {
        return entResNo;
    }

    public void setEntResNo(String entResNo) {
        this.entResNo = entResNo;
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

    public String getLinkmanMobile() {
        return linkmanMobile;
    }

    public void setLinkmanMobile(String linkmanMobile) {
        this.linkmanMobile = linkmanMobile;
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

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(String statusDate) {
        this.statusDate = statusDate;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
