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
 * @ClassName: MemberQuit
 * @Description: 成员企业注销信息
 * 
 * @author: xiaofl
 * @date: 2015-9-7 下午4:19:54
 * @version V1.0
 */
public class MemberQuit implements Serializable {

    private static final long serialVersionUID = 6042691611373803029L;

    /** 申请编号 */
    private String applyId;

    /** 申请类型 */
    private Integer applyType;

    /** 成员企业客户号 */
    private String entCustId;

    /** 成员企业互生号 */
    private String entResNo;

    /** 成员企业名称 */
    private String entCustName;

    /** 企业地址 */
    private String entAddress;

    /** 联系人 */
    private String linkman;

    /** 联系电话 */
    private String linkmanMobile;

    /** 申请原因 */
    private String applyReason;

    /** 成员企业状态 */
    private Integer oldStatus;

    /** 附件-业务办理申请书 */
    private String bizApplyFile;

    /** 企业实地考察报告 */
    private String reportFile;

    /** 企业双方声明文件 */
    private String statementFile;

    /** 其它附件 */
    private String otherFile;

    /** 转现银行账户ID */
    private String bankAcctId;

    /** 服务公司客户号 */
    private String serEntCustId;

    /** 服务公司互生号 */
    private String serEntResNo;

    /** 服务公司名称 */
    private String serEntCustName;

    /** 状态 */
    private Integer status;

    /** 销户进度 **/
    private Integer progress;

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

    public Integer getApplyType() {
        return applyType;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }

    public String getEntCustId() {
        return entCustId;
    }

    public void setEntCustId(String entCustId) {
        this.entCustId = entCustId;
    }

    public String getEntResNo() {
        return entResNo;
    }

    public void setEntResNo(String entResNo) {
        this.entResNo = entResNo;
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

    public String getApplyReason() {
        return applyReason;
    }

    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason;
    }

    public Integer getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(Integer oldStatus) {
        this.oldStatus = oldStatus;
    }

    public String getBizApplyFile() {
        return bizApplyFile;
    }

    public void setBizApplyFile(String bizApplyFile) {
        this.bizApplyFile = bizApplyFile;
    }

    public String getReportFile() {
        return reportFile;
    }

    public void setReportFile(String reportFile) {
        this.reportFile = reportFile;
    }

    public String getStatementFile() {
        return statementFile;
    }

    public void setStatementFile(String statementFile) {
        this.statementFile = statementFile;
    }

    public String getOtherFile() {
        return otherFile;
    }

    public void setOtherFile(String otherFile) {
        this.otherFile = otherFile;
    }

    public String getBankAcctId() {
        return bankAcctId;
    }

    public void setBankAcctId(String bankAcctId) {
        this.bankAcctId = bankAcctId;
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

    public Integer getStatus() {
        return status;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
