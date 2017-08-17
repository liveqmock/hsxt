/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.gpf.res.bean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @Package: com.gy.hsxt.gpf.res.bean
 * @ClassName: QuotaApp
 * @Description: 一级配额申请
 * 
 * @author: xiaofl
 * @date: 2015-12-17 下午3:53:11
 * @version V1.0
 */
public class QuotaAppInfo implements Serializable {

    private static final long serialVersionUID = -3083297379251471567L;

    /** 申请编号 **/
    private String applyId;

    /** 管理公司互生号 **/
    private String entResNo;

    /** 管理公司名称 */
    private String entCustName;

    /** 平台代码 **/
    private String platNo;

    /** 平台名称 **/
    private String platName;

    /**
     * 审批状态
     */
    private Integer status;

    /** 申请数量 **/
    private Integer applyNum;

    /** 申请号段 **/
    private String applyRange;

    /** 申请操作员编号 **/
    private String reqOptId;

    /** 申请操作员名称 **/
    private String reqOptName;

    /** 申请时间 **/
    private String reqTime;

    /** 申请说明 **/
    private String reqRemark;

    /** 审批数量 **/
    private Integer apprNum;

    /** 审批号段 **/
    private String apprRange;

    /** 审批操作员编号 **/
    private String apprOptId;

    /** 审批操作员名称 **/
    private String apprOptName;

    /** 审批时间 **/
    private String apprTime;

    /** 审批说明 **/
    private String apprRemark;

    /** 最大可批复数 **/
    private Integer maxApprNum;

    /** 初始化配额数 **/
    private Integer initQuota;

    /** 已分配数 **/
    private Integer assigned;

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

    public String getEntCustName() {
        return entCustName;
    }

    public void setEntCustName(String entCustName) {
        this.entCustName = entCustName;
    }

    public String getPlatNo() {
        return platNo;
    }

    public void setPlatNo(String platNo) {
        this.platNo = platNo;
    }

    public String getPlatName() {
        return platName;
    }

    public void setPlatName(String platName) {
        this.platName = platName;
    }

    public Integer getApplyNum() {
        return applyNum;
    }

    public void setApplyNum(Integer applyNum) {
        this.applyNum = applyNum;
    }

    public String getApplyRange() {
        return applyRange;
    }

    public void setApplyRange(String applyRange) {
        this.applyRange = applyRange;
    }

    public String getReqOptId() {
        return reqOptId;
    }

    public void setReqOptId(String reqOptId) {
        this.reqOptId = reqOptId;
    }

    public String getReqOptName() {
        return reqOptName;
    }

    public void setReqOptName(String reqOptName) {
        this.reqOptName = reqOptName;
    }

    public String getReqTime() {
        return reqTime;
    }

    public void setReqTime(String reqTime) {
        this.reqTime = reqTime;
    }

    public String getReqRemark() {
        return reqRemark;
    }

    public void setReqRemark(String reqRemark) {
        this.reqRemark = reqRemark;
    }

    public Integer getApprNum() {
        return apprNum;
    }

    public void setApprNum(Integer apprNum) {
        this.apprNum = apprNum;
    }

    public String getApprRange() {
        return apprRange;
    }

    public void setApprRange(String apprRange) {
        this.apprRange = apprRange;
    }

    public String getApprOptId() {
        return apprOptId;
    }

    public void setApprOptId(String apprOptId) {
        this.apprOptId = apprOptId;
    }

    public String getApprOptName() {
        return apprOptName;
    }

    public void setApprOptName(String apprOptName) {
        this.apprOptName = apprOptName;
    }

    public String getApprTime() {
        return apprTime;
    }

    public void setApprTime(String apprTime) {
        this.apprTime = apprTime;
    }

    public String getApprRemark() {
        return apprRemark;
    }

    public void setApprRemark(String apprRemark) {
        this.apprRemark = apprRemark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getMaxApprNum() {
        return this.initQuota - (this.assigned == null ? 0 : this.assigned);
    }

    /*
     * public void setMaxApprNum(Integer maxApprNum) { this.maxApprNum =
     * maxApprNum; }
     */

    public Integer getInitQuota() {
        return initQuota;
    }

    public void setInitQuota(Integer initQuota) {
        this.initQuota = initQuota;
    }

    public Integer getAssigned() {
        return assigned;
    }

    public void setAssigned(Integer assigned) {
        this.assigned = assigned;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
