/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bean.apply;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/** 
 * 
 * @Package: com.gy.hsxt.bs.bean.apply  
 * @ClassName: FilingApprParam 
 * @Description: 报备审批信息
 *
 * @author: xiaofl 
 * @date: 2015-12-14 下午4:21:13 
 * @version V1.0 
 

 */ 
public class FilingApprParam implements Serializable {

    private static final long serialVersionUID = -5755981119705505395L;

    /** 申请编号 **/
    private String applyId;

    /** 审批操作员客户号 **/
    private String apprOperator;

    /** 状态 **/
    private Integer status;

    /** 审批备注 **/
    private String apprRemark;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getApprOperator() {
        return apprOperator;
    }

    public void setApprOperator(String apprOperator) {
        this.apprOperator = apprOperator;
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
