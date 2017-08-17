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
 * @ClassName: CertificateSendHis
 * @Description: 证书发放历史信息
 * 
 * @author: xiaofl
 * @date: 2015-9-2 下午2:04:47
 * @version V1.0
 */
public class CertificateSendHis implements Serializable {

    private static final long serialVersionUID = -5978356924975068213L;

    /** 业务编号 **/
    private String bizNo;

    /** 证书唯一识别码 **/
    private String certificateNo;

    /** 状态 0：待盖章 1：已生效 2：已失效 3：需重新盖章 **/
    private Integer sealStatus;

    /** 盖章操作员 **/
    private String sealOperator;

    /** 盖章时间 **/
    private String sealDate;

    /** 打印状态 **/
    private Boolean isPrint;

    /** 打印时间 **/
    private String printDate;

    /** 打印操作员 **/
    private String printOperator;

    /** 收回说明 **/
    private String recRemark;

    /** 发放说明 **/
    private String sendRemark;

    /** 原件是否已收回 **/
    private Boolean originalIsRec;

    /** 发放操作员 **/
    private String sendOperator;

    public String getBizNo() {
        return bizNo;
    }

    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public Integer getSealStatus() {
        return sealStatus;
    }

    public void setSealStatus(Integer sealStatus) {
        this.sealStatus = sealStatus;
    }

    public String getSealOperator() {
        return sealOperator;
    }

    public void setSealOperator(String sealOperator) {
        this.sealOperator = sealOperator;
    }

    public String getSealDate() {
        return sealDate;
    }

    public void setSealDate(String sealDate) {
        this.sealDate = sealDate;
    }

    public Boolean getIsPrint() {
        return isPrint;
    }

    public void setIsPrint(Boolean isPrint) {
        this.isPrint = isPrint;
    }

    public String getPrintDate() {
        return printDate;
    }

    public void setPrintDate(String printDate) {
        this.printDate = printDate;
    }

    public String getPrintOperator() {
        return printOperator;
    }

    public void setPrintOperator(String printOperator) {
        this.printOperator = printOperator;
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

    public Boolean getOriginalIsRec() {
        return originalIsRec;
    }

    public void setOriginalIsRec(Boolean originalIsRec) {
        this.originalIsRec = originalIsRec;
    }

    public String getSendOperator() {
        return sendOperator;
    }

    public void setSendOperator(String sendOperator) {
        this.sendOperator = sendOperator;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
