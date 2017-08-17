/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bean.apply;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/** 
 * 
 * @Package: com.gy.hsxt.bs.bean.apply  
 * @ClassName: VarContent 
 * @Description: 合同占位符替换内容
 *
 * @author: xiaofl 
 * @date: 2015-12-14 下午4:31:20 
 * @version V1.0 
 

 */ 
public class VarContent implements Serializable {

    private static final long serialVersionUID = -8204543636795923416L;

    /** 合同唯一识别码 **/
    private String contractNo;

    /** 申报企业地址 **/
    private String frCorpAddr;

    /** 申报企业联系电话 **/
    private String frCorpContactsTel;

    /** 申报企业授权代表 **/
    private String frCorpContacts;

    /** 申报企业名称 **/
    private String frCorpName;

    /** 互生系统技术支持方地址 **/
    private String hsCorpAddr;

    /** 互生系统技术支持方联系电话 **/
    private String hsCorpContactsTel;

    /** 互生系统技术支持方授权代表 **/
    private String hsCorpContacts;

    /** 互生系统技术支持方名称 **/
    private String hsCorpName;

    /** 管理公司地址 **/
    private String mdCorpAddr;

    /** 管理公司联系电话 **/
    private String mdCorpContactsTel;

    /** 管理公司授权代表 **/
    private String mdCorpContacts;

    /** 管理公司名称 **/
    private String mdCorpName;

    /** 签约日期 **/
    private String signingDate;

    /** 被申报企业地址 **/
    private String toCorpAddr;

    /** 被申报企业联系电话 **/
    private String toCorpContactsTel;

    /** 被申报企业授权代表 **/
    private String toCorpContacts;

    /** 被申报企业名称 **/
    private String toCorpName;

    /** 合同编号 **/
    private String toCorpResNo;

    /** 积分预付款,小写金额 **/
    private String toPointAdvanceStr;
    
    /** 积分预付款,大写金额  **/
    private String toPointAdvanceAmt;

    /** 资源费用,小写金额 **/
    private String toResFeeStr;
    
    /** 资源费用,大写金额  **/
    private String toResFeeAmt;
    
    /** 年费,小写金额 **/
    private String toAnnualFeeStr;
    
    /** 年费,大写金额 **/
    private String toAnnualFeeAmt;

    /** 验证地址 **/
    private String verifyAddr;

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getFrCorpAddr() {
        return frCorpAddr;
    }

    public void setFrCorpAddr(String frCorpAddr) {
        this.frCorpAddr = frCorpAddr;
    }

    public String getFrCorpContactsTel() {
        return frCorpContactsTel;
    }

    public void setFrCorpContactsTel(String frCorpContactsTel) {
        this.frCorpContactsTel = frCorpContactsTel;
    }

    public String getFrCorpContacts() {
        return frCorpContacts;
    }

    public void setFrCorpContacts(String frCorpContacts) {
        this.frCorpContacts = frCorpContacts;
    }

    public String getFrCorpName() {
        return frCorpName;
    }

    public void setFrCorpName(String frCorpName) {
        this.frCorpName = frCorpName;
    }

    public String getHsCorpAddr() {
        return hsCorpAddr;
    }

    public void setHsCorpAddr(String hsCorpAddr) {
        this.hsCorpAddr = hsCorpAddr;
    }

    public String getHsCorpContactsTel() {
        return hsCorpContactsTel;
    }

    public void setHsCorpContactsTel(String hsCorpContactsTel) {
        this.hsCorpContactsTel = hsCorpContactsTel;
    }

    public String getHsCorpContacts() {
        return hsCorpContacts;
    }

    public void setHsCorpContacts(String hsCorpContacts) {
        this.hsCorpContacts = hsCorpContacts;
    }

    public String getHsCorpName() {
        return hsCorpName;
    }

    public void setHsCorpName(String hsCorpName) {
        this.hsCorpName = hsCorpName;
    }

    public String getMdCorpAddr() {
        return mdCorpAddr;
    }

    public void setMdCorpAddr(String mdCorpAddr) {
        this.mdCorpAddr = mdCorpAddr;
    }

    public String getMdCorpContactsTel() {
        return mdCorpContactsTel;
    }

    public void setMdCorpContactsTel(String mdCorpContactsTel) {
        this.mdCorpContactsTel = mdCorpContactsTel;
    }

    public String getMdCorpContacts() {
        return mdCorpContacts;
    }

    public void setMdCorpContacts(String mdCorpContacts) {
        this.mdCorpContacts = mdCorpContacts;
    }

    public String getMdCorpName() {
        return mdCorpName;
    }

    public void setMdCorpName(String mdCorpName) {
        this.mdCorpName = mdCorpName;
    }

    public String getSigningDate() {
        return signingDate;
    }

    public void setSigningDate(String signingDate) {
        this.signingDate = signingDate;
    }

    public String getToAnnualFeeStr() {
        return toAnnualFeeStr;
    }

    public void setToAnnualFeeStr(String toAnnualFeeStr) {
        this.toAnnualFeeStr = toAnnualFeeStr;
    }

    public String getToCorpAddr() {
        return toCorpAddr;
    }

    public void setToCorpAddr(String toCorpAddr) {
        this.toCorpAddr = toCorpAddr;
    }

    public String getToCorpContactsTel() {
        return toCorpContactsTel;
    }

    public void setToCorpContactsTel(String toCorpContactsTel) {
        this.toCorpContactsTel = toCorpContactsTel;
    }

    public String getToCorpContacts() {
        return toCorpContacts;
    }

    public void setToCorpContacts(String toCorpContacts) {
        this.toCorpContacts = toCorpContacts;
    }

    public String getToCorpName() {
        return toCorpName;
    }

    public void setToCorpName(String toCorpName) {
        this.toCorpName = toCorpName;
    }

    public String getToCorpResNo() {
        return toCorpResNo;
    }

    public void setToCorpResNo(String toCorpResNo) {
        this.toCorpResNo = toCorpResNo;
    }

    public String getToPointAdvanceStr() {
        return toPointAdvanceStr;
    }

    public void setToPointAdvanceStr(String toPointAdvanceStr) {
        this.toPointAdvanceStr = toPointAdvanceStr;
    }

    public String getToResFeeStr() {
        return toResFeeStr;
    }

    public void setToResFeeStr(String toResFeeStr) {
        this.toResFeeStr = toResFeeStr;
    }

    public String getVerifyAddr() {
        return verifyAddr;
    }

    public void setVerifyAddr(String verifyAddr) {
        this.verifyAddr = verifyAddr;
    }
    
    public String getToPointAdvanceAmt() {
        return toPointAdvanceAmt;
    }

    public void setToPointAdvanceAmt(String toPointAdvanceAmt) {
        this.toPointAdvanceAmt = toPointAdvanceAmt;
    }

    public String getToResFeeAmt() {
        return toResFeeAmt;
    }

    public void setToResFeeAmt(String toResFeeAmt) {
        this.toResFeeAmt = toResFeeAmt;
    }

    public String getToAnnualFeeAmt() {
        return toAnnualFeeAmt;
    }

    public void setToAnnualFeeAmt(String toAnnualFeeAmt) {
        this.toAnnualFeeAmt = toAnnualFeeAmt;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this,SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullStringAsEmpty);
    }

}
