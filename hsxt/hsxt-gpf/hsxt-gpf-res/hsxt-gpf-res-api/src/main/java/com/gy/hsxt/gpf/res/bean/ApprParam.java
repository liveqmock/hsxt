/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.gpf.res.bean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.gpf.res.enumtype.QuotaAppStatus;

/**
 * 
 * @Package: com.gy.hsxt.gpf.res.bean
 * @ClassName: ApprParam
 * @Description: 审批参数
 * 
 * @author: xiaofl
 * @date: 2015-12-17 下午3:51:36
 * @version V1.0
 */
public class ApprParam implements Serializable {

    private static final long serialVersionUID = 5428887301698123720L;

    /** 申请编号 **/
    private String applyId;

    /** 是否通过 **/
    private Boolean isPass;

    /** 批复数量 **/
    private Integer apprNum;

    /** 批复号段 **/
    private String apprRange;

    /** 复核配额清单 **/
    private String resNoList;

    /** 复核操作员编号 **/
    private String apprOptId;

    /** 复核操作员名称 **/
    private String apprOptName;

    /** 复核意见 **/
    private String apprRemark;

    /** 状态 **/
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public Boolean getIsPass() {
        return isPass;
    }

    public void setIsPass(Boolean isPass) {
        this.isPass = isPass;
        if (isPass)
        {
            this.status = QuotaAppStatus.PASS.getCode();
        }
        else
        {
            this.status = QuotaAppStatus.REJECT.getCode();
        }
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

    public String getApprRemark() {
        return apprRemark;
    }

    public void setApprRemark(String apprRemark) {
        this.apprRemark = apprRemark;
    }

    public String getApprRange() {
        return apprRange;
    }

    public void setApprRange(String apprRange) {
        this.apprRange = apprRange;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
