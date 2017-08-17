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
 * @ClassName: PointActivityBaseInfo
 * @Description: 积分活动申请基本信息
 *
 * @author: xiaofl
 * @date: 2015-9-8 下午12:05:15
 * @version V1.0
 */
public class PointActivityBaseInfo implements Serializable {

    private static final long serialVersionUID = -5583664385967596558L;

    /** 申请编号 **/
    private String applyId;

    /** 企业互生号 **/
    private String entResNo;

    /** 企业名称 **/
    private String entName;

    /** 企业地址 **/
    private String entAddress;

    /** 联系人姓名 **/
    private String linkman;

    /** 联系人手机 **/
    private String linkmanMobile;

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

    public String getEntAddress() {
        return entAddress;
    }

    public void setEntAddress(String entAddress) {
        this.entAddress = entAddress;
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
