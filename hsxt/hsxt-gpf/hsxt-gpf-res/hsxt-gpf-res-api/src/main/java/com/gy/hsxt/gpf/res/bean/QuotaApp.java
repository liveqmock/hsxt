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
public class QuotaApp implements Serializable {

    private static final long serialVersionUID = 1554376180612533871L;

    /** 申请编号 **/
    private String applyId;

    /** 管理公司互生号 **/
    private String entResNo;

    /** 管理公司名称 */
    private String entCustName;

    /** 平台代码 **/
    private String platNo;

    /** 申请类型 **/
    private Integer applyType;

    /** 申请数量 **/
    private Integer applyNum;

    /** 申请号段 **/
    private String applyRange;

    /** 申请配额清单 **/
    private String applyList;

    /** 申请操作员编号 **/
    private String reqOptId;

    /** 申请操作员名称 **/
    private String reqOptName;

    /** 申请操作员(与BS同步的时候用) **/
    private String reqOperator;

    /** 申请时间 **/
    private String reqTime;

    /** 申请说明 **/
    private String reqRemark;

    /** 状态 **/
    private Integer status;

    /** 批复数量 **/
    private Integer apprNum;

    /** 复核配额清单 **/
    private String resNoList;

    /** 复核操作员编号 **/
    private String apprOptId;

    /** 复核操作员名称 **/
    private String apprOptName;

    /** 复核操作员(与BS同步的时候用) **/
    private String apprOperator;

    /** 复核时间 **/
    private String apprTime;

    /** 复核说明 **/
    private String apprRemark;

    /** 路由数据同步标识,默认false **/
    private Boolean routeSync = false;

    /** 配额分配同步标识,默认false **/
    private Boolean allotSync = false;

    public QuotaApp() {
    }

    /** 申请配额用的构造方法 **/
    public QuotaApp(String applyId, String entResNo, String entCustName, String platNo, Integer applyType,
            Integer applyNum, String applyList, String reqOptId, String apprOptName, String reqRemark) {
        this.applyId = applyId;
        this.entResNo = entResNo;
        this.entCustName = entCustName;
        this.platNo = platNo;
        this.applyType = applyType;
        this.applyNum = applyNum;
        this.applyList = applyList;
        this.reqOptId = reqOptId;
        this.apprOptName = apprOptName;
        this.reqRemark = reqRemark;
    }

    /** 审批配额用的构造方法 **/
    public QuotaApp(String applyId, Integer status, Integer apprNum, String resNoList, String apprOptId,
            String apprOptName, String apprRemark) {
        this.applyId = applyId;
        this.status = status;
        this.apprNum = apprNum;
        this.resNoList = resNoList;
        this.apprOptId = apprOptId;
        this.apprOptName = apprOptName;
        this.apprRemark = apprRemark;
    }

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

    public Integer getApplyType() {
        return applyType;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }

    public Integer getApplyNum() {
        return applyNum;
    }

    public void setApplyNum(Integer applyNum) {
        this.applyNum = applyNum;
    }

    public String getApplyList() {
        return applyList;
    }

    public void setApplyList(String applyList) {
        this.applyList = applyList;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getApprNum() {
        return apprNum;
    }

    public void setApprNum(Integer apprNum) {
        this.apprNum = apprNum;
    }

    public String getResNoList() {
        return resNoList;
    }

    public void setResNoList(String resNoList) {
        this.resNoList = resNoList;
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

    public Boolean getRouteSync() {
        return routeSync;
    }

    public void setRouteSync(Boolean routeSync) {
        this.routeSync = routeSync;
    }

    public Boolean getAllotSync() {
        return allotSync;
    }

    public void setAllotSync(Boolean allotSync) {
        this.allotSync = allotSync;
    }

    public String getApplyRange() {
        return applyRange;
    }

    public void setApplyRange(String applyRange) {
        this.applyRange = applyRange;
    }

    public String getReqOperator() {
        return reqOperator;
    }

    public void setReqOperator(String reqOperator) {
        this.reqOperator = reqOperator;
    }

    public String getApprOperator() {
        return apprOperator;
    }

    public void setApprOperator(String apprOperator) {
        this.apprOperator = apprOperator;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
