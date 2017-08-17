/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.bean.deduction;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * 互生币扣除
 *
 * @Package : com.gy.hsxt.bs.bean.deduction
 * @ClassName : HsbDeduction
 * @Description : 互生币扣除
 * @Author : chenm
 * @Date : 2016/3/25 16:39
 * @Version V3.0.0.0
 */
public class HsbDeduction implements Serializable {

    private static final long serialVersionUID = 2862466353617082513L;
    /**
     * 申请ID
     */
    private String applyId;

    /**
     * 企业互生号
     */
    private String entResNo;

    /**
     * 企业客户号
     */
    private String entCustId;

    /**
     * 企业类型
     *
     * @see com.gy.hsxt.common.constant.CustType
     */
    private Integer custType;

    /**
     * 企业名称
     */
    private String entCustName;

    /**
     * 扣除金额
     */
    private String amount;

    /**
     * 审批状态 0-扣款待复核 1-扣款成功 2-驳回扣款申请
     *
     * @see com.gy.hsxt.bs.common.enumtype.deduction.DeductionStatus
     */
    private Integer status;

    /**
     * 扣款日期 格式 ： yyyy-MM-dd HH:mm:ss
     */
    private String deductDate;

    /**
     * 申请者客户号
     */
    private String applyOper;

    /**
     * 申请者名称
     */
    private String applyName;

    /**
     * 申请时间
     */
    private String applyDate;

    /**
     * 申请备注
     */
    private String applyRemark;


    /**
     * 审批者客户号
     */
    private String apprOper;

    /**
     * 审批者名称
     */
    private String apprName;

    /**
     * 审批时间
     */
    private String apprDate;

    /**
     * 审批备注
     */
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

    public Integer getCustType() {
        return custType;
    }

    public void setCustType(Integer custType) {
        this.custType = custType;
    }

    public String getEntCustName() {
        return entCustName;
    }

    public void setEntCustName(String entCustName) {
        this.entCustName = entCustName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDeductDate() {
        return deductDate;
    }

    public void setDeductDate(String deductDate) {
        this.deductDate = deductDate;
    }

    public String getApplyOper() {
        return applyOper;
    }

    public void setApplyOper(String applyOper) {
        this.applyOper = applyOper;
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
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

    public String getApprOper() {
        return apprOper;
    }

    public void setApprOper(String apprOper) {
        this.apprOper = apprOper;
    }

    public String getApprName() {
        return apprName;
    }

    public void setApprName(String apprName) {
        this.apprName = apprName;
    }

    public String getApprDate() {
        return apprDate;
    }

    public void setApprDate(String apprDate) {
        this.apprDate = apprDate;
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
