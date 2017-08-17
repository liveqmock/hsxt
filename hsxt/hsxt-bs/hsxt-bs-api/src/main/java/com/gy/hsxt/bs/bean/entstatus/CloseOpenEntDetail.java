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
 * @ClassName: CloseOpenEnt
 * @Description: 关闭开启系统详情
 * 
 * @author: xiaofl
 * @date: 2015-11-30 上午11:01:01
 * @version V1.0
 */
public class CloseOpenEntDetail implements Serializable {

    private static final long serialVersionUID = -3134016097026720375L;

    /** 申请编号 */
    private String applyId;

    /** 企业互生号 */
    private String entResNo;

    /** 企业客户号 */
    private String entCustId;

    /** 企业名称 */
    private String entCustName;

    /** 企业类型 */
    private Integer custType;

    /** 申请类别 */
    private Integer applyType;

    /** 企业地址 */
    private String address;

    /** 联系人 */
    private String linkman;

    /** 联系电话 */
    private String mobile;

    /** 上一次申请操作员客户号 */
    private String lastApplyOptCustId;

    /** 上一次申请操作员名称 */
    private String lastApplyOptCustName;

    /** 上一次申请日期 */
    private String lastApplyDate;

    /** 上一次申请说明 */
    private String lastApplyRemark;

    /** 申请操作员客户号 */
    private String applyOptCustId;

    /** 申请操作员名称 */
    private String applyOptCustName;

    /** 申请日期 */
    private String applyDate;

    /** 申请说明 */
    private String applyRemark;

    /** 审批操作员客户号 */
    private String apprOptCustId;

    /** 审批操作员名称 */
    private String apprOptCustName;

    /** 审批日期 */
    private String apprDate;

    /** 审批结果 */
    private Integer status;

    /** 审批说明 */
    private String apprRemark;

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

    public Integer getCustType() {
        return custType;
    }

    public void setCustType(Integer custType) {
        this.custType = custType;
    }

    public Integer getApplyType() {
        return applyType;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLastApplyOptCustId() {
        return lastApplyOptCustId;
    }

    public void setLastApplyOptCustId(String lastApplyOptCustId) {
        this.lastApplyOptCustId = lastApplyOptCustId;
    }

    public String getLastApplyOptCustName() {
        return lastApplyOptCustName;
    }

    public void setLastApplyOptCustName(String lastApplyOptCustName) {
        this.lastApplyOptCustName = lastApplyOptCustName;
    }

    public String getLastApplyDate() {
        return lastApplyDate;
    }

    public void setLastApplyDate(String lastApplyDate) {
        this.lastApplyDate = lastApplyDate;
    }

    public String getLastApplyRemark() {
        return lastApplyRemark;
    }

    public void setLastApplyRemark(String lastApplyRemark) {
        this.lastApplyRemark = lastApplyRemark;
    }

    public String getApplyOptCustId() {
        return applyOptCustId;
    }

    public void setApplyOptCustId(String applyOptCustId) {
        this.applyOptCustId = applyOptCustId;
    }

    public String getApplyOptCustName() {
        return applyOptCustName;
    }

    public void setApplyOptCustName(String applyOptCustName) {
        this.applyOptCustName = applyOptCustName;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getApplyRemark() {
        return applyRemark;
    }

    public void setApplyRemark(String applyRemark) {
        this.applyRemark = applyRemark;
    }

    public String getApprOptCustId() {
        return apprOptCustId;
    }

    public void setApprOptCustId(String apprOptCustId) {
        this.apprOptCustId = apprOptCustId;
    }

    public String getApprOptCustName() {
        return apprOptCustName;
    }

    public void setApprOptCustName(String apprOptCustName) {
        this.apprOptCustName = apprOptCustName;
    }

    public String getApprDate() {
        return apprDate;
    }

    public void setApprDate(String apprDate) {
        this.apprDate = apprDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getApprRemark() {
        return apprRemark;
    }

    public void setApprRemark(String apprRemark) {
        this.apprRemark = apprRemark;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
