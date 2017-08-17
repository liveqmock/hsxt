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
 * @ClassName: ContractSendHis
 * @Description: 合同发放历史信息
 *
 * @author: xiaofl
 * @date: 2015-9-2 下午12:23:04
 * @version V1.0
 */
public class ContractSendHis implements Serializable {

    private static final long serialVersionUID = 3458394329060157608L;

    /** 业务编号 **/
    private String bizNo;

    /** 合同或证书 唯一识别码 **/
    private String uniqueNo;

    /** 原件是否已收回:1:已收回 0:未收回 **/
    private Boolean originalIsRec;

    /** 收回说明 **/
    private String recRemark;

    /** 发放说明 **/
    private String sendRemark;

    /** 操作员 **/
    private String operator;

    /** 发放时间 **/
    private String operatedDate;

    public String getBizNo() {
        return bizNo;
    }

    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }

    public String getUniqueNo() {
        return uniqueNo;
    }

    public void setUniqueNo(String uniqueNo) {
        this.uniqueNo = uniqueNo;
    }

    public Boolean getOriginalIsRec() {
        return originalIsRec;
    }

    public void setOriginalIsRec(Boolean originalIsRec) {
        this.originalIsRec = originalIsRec;
    }

    public String getRecRemark() {
        return recRemark;
    }

    public void setRecRemark(String recRemark) {
        this.recRemark = recRemark;
    }

    public String getSendRemark() {
        return sendRemark;
    }

    public void setSendRemark(String sendRemark) {
        this.sendRemark = sendRemark;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperatedDate() {
        return operatedDate;
    }

    public void setOperatedDate(String operatedDate) {
        this.operatedDate = operatedDate;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
