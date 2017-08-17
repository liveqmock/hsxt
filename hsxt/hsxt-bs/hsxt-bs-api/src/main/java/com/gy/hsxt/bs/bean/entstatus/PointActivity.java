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
 * @ClassName: PointActivity
 * @Description: 积分活动申请信息
 * 
 * @author: xiaofl
 * @date: 2015-9-8 上午11:52:16
 * @version V1.0
 */
public class PointActivity implements Serializable {

    private static final long serialVersionUID = -3837315710037692170L;

    /** 申请编号 */
    private String applyId;

    /** 企业互生号 */
    private String entResNo;

    /** 企业客户号 */
    private String entCustId;

    /** 企业名称 */
    private String entCustName;

    /** 企业地址 */
    private String entAddress;

    /** 联系人 */
    private String linkman;

    /** 联系电话 */
    private String linkmanMobile;

    /** 成员企业状态 */
    private Integer oldStatus;

    /** 服务公司客户号 */
    private String serEntCustId;

    /** 服务公司互生号 */
    private String serEntResNo;

    /** 服务公司名称 */
    private String serEntCustName;

    /** 申请类型 */
    private Integer applyType;

    /** 申请原因 */
    private String applyReason;

    /** 状态 */
    private Integer status;

    /** 业务办理申请书 */
    private String bizApplyFile;

    /** 创建日期 */
    private String createdDate;

    /** 创建者 */
    private String createdBy;

    /** 修改日期 */
    private String updateDate;

    /** 修改者 */
    private String updatedBy;

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

    public String getEntCustId() {
        return entCustId;
    }

    public void setEntCustId(String entCustId) {
        this.entCustId = entCustId;
    }

    public String getEntCustName() {
        return entCustName;
    }

    public void setEntCustName(String entCustName) {
        this.entCustName = entCustName;
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

    public Integer getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(Integer oldStatus) {
        this.oldStatus = oldStatus;
    }

    public String getSerEntCustId() {
        return serEntCustId;
    }

    public void setSerEntCustId(String serEntCustId) {
        this.serEntCustId = serEntCustId;
    }

    public String getSerEntResNo() {
        return serEntResNo;
    }

    public void setSerEntResNo(String serEntResNo) {
        this.serEntResNo = serEntResNo;
    }

    public String getSerEntCustName() {
        return serEntCustName;
    }

    public void setSerEntCustName(String serEntCustName) {
        this.serEntCustName = serEntCustName;
    }

    public Integer getApplyType() {
        return applyType;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }

    public String getApplyReason() {
        return applyReason;
    }

    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getBizApplyFile() {
        return bizApplyFile;
    }

    public void setBizApplyFile(String bizApplyFile) {
        this.bizApplyFile = bizApplyFile;
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

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
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
