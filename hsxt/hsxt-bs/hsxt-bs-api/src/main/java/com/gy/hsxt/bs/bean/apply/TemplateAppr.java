/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.bean.apply;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * 模版审核信息
 *
 * @Package : com.gy.hsxt.bs.bean.apply
 * @ClassName : TemplateAppr
 * @Description : 模版审核信息
 * @Author : chenm
 * @Date : 2016/3/12 18:30
 * @Version V3.0.0.0
 */
public class TemplateAppr implements Serializable {

    private static final long serialVersionUID = -2176945105549917128L;
    /**
     * 申请ID
     */
    private String applyId;

    /**
     * 模版ID
     */
    private String templetId;

    /**
     *审核状态 2:启用待复核 3:停用待复核
     *
     * @see com.gy.hsxt.bs.common.enumtype.apply.TempletStatus
     */
    private Integer apprStatus;

    /**
     * 审核结果 0:待审核 1:审核通过 2:审核驳回
     */
    private Integer apprResult;

    /**
     * 申请操作员客户号
     */
    private String reqOperator;
    /**
     * 申请操作员名称
     */
    private String reqName;
    /**
     * 申请时间
     */
    private String reqTime;
    /**
     * 申请备注
     */
    private String reqRemark;
    /**
     * 审批操作员客户号
     */
    private String apprOperator;
    /**
     * 审批操作员名称
     */
    private String apprName;
    /**
     * 审批时间
     */
    private String apprTime;
    /**
     * 审批备注
     */
    private String apprRemark;

    /**
     * 模版
     */
    private Templet templet;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getTempletId() {
        return templetId;
    }

    public void setTempletId(String templetId) {
        this.templetId = templetId;
    }

    public Integer getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(Integer apprStatus) {
        this.apprStatus = apprStatus;
    }

    public Integer getApprResult() {
        return apprResult;
    }

    public void setApprResult(Integer apprResult) {
        this.apprResult = apprResult;
    }

    public String getReqOperator() {
        return reqOperator;
    }

    public void setReqOperator(String reqOperator) {
        this.reqOperator = reqOperator;
    }

    public String getReqName() {
        return reqName;
    }

    public void setReqName(String reqName) {
        this.reqName = reqName;
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

    public String getApprOperator() {
        return apprOperator;
    }

    public void setApprOperator(String apprOperator) {
        this.apprOperator = apprOperator;
    }

    public String getApprName() {
        return apprName;
    }

    public void setApprName(String apprName) {
        this.apprName = apprName;
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

    public Templet getTemplet() {
        return templet;
    }

    public void setTemplet(Templet templet) {
        this.templet = templet;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
