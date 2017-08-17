/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype.apply;

public enum TempletTag {

    /** 合同唯一识别码 **/
    getContractNo("getContractNo"),

    /** 申报企业地址 **/
    getFrCorpAddr("getFrCorpAddr"),

    /** 申报企业联系电话 **/
    getFrCorpContactsTel("getFrCorpContactsTel"),

    /** 申报企业授权代表 **/
    getFrCorpContacts("getFrCorpContacts"),

    /** 申报企业名称 **/
    getFrCorpName("getFrCorpName"),

    /** 互生系统技术支持方地址 **/
    getHsCorpAddr("getHsCorpAddr"),

    /** 互生系统技术支持方联系电话 **/
    getHsCorpContactsTel("getHsCorpContactsTel"),

    /** 互生系统技术支持方授权代表 **/
    getHsCorpContacts("getHsCorpContacts"),

    /** 互生系统技术支持方名称 **/
    getHsCorpName("getHsCorpName"),

    /** 管理公司地址 **/
    getMdCorpAddr("getMdCorpAddr"),

    /** 管理公司联系电话 **/
    getMdCorpContactsTel("getMdCorpContactsTel"),

    /** 管理公司授权代表 **/
    getMdCorpContacts("getMdCorpContacts"),

    /** 管理公司授名称 **/
    getMdCorpName("getMdCorpName"),

    /** 签约日期 **/
    getSigningDate("getSigningDate"),

    /** 年费 **/
    getToAnnualFeeStr("getToAnnualFeeStr"),
    
    /** 年费，大写金额 **/
    getToAnnualFeeAmt("getToAnnualFeeAmt"),

    /** 被申报企业地址 **/
    getToCorpAddr("getToCorpAddr"),

    /** 被申报企业联系电话 **/
    getToCorpContactsTel("getToCorpContactsTel"),

    /** 被申报企业授权代表 **/
    getToCorpContacts("getToCorpContacts"),

    /** 被申报企业名称 **/
    getToCorpName("getToCorpName"),

    /** 合同编号 **/
    getToCorpResNo("getToCorpResNo"),

    /** 积分预付款 **/
    getToPointAdvanceStr("getToPointAdvanceStr"),
    
    /** 积分预付款，大写金额 **/
    getToPointAdvanceAmt("getToPointAdvanceAmt"),

    /** 资源费用 **/
    getToResFeeStr("getToResFeeStr"),
    
    /** 资源费用，大写金额
     *  **/
    getToResFeeAmt("getToResFeeAmt"),

    /** 验证地址 **/
    getVerifyAddr("getVerifyAddr");

    private String code;

    TempletTag(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
