/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype.entstatus;

/**
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.entstatus
 * @ClassName: PerChangeKey
 * @Description: 消费者重要信息变更key
 * 
 * @author: xiaofl
 * @date: 2015-12-15 下午3:07:07
 * @version V1.0
 */
public enum PerChangeKey {

    /** 姓名 **/
    NAME("NAME"),
    /** 性别 **/
    SEX("SEX"),
    /** 国籍 **/
    NATIONALITY("NATIONALITY"),
    /** 证件类型 **/
    CRE_TYPE("CRE_TYPE"),
    /** 证件号码 **/
    CRE_NO("CRE_NO"),
    /** 证件有效期 **/
    CRE_INDATE("CRE_INDATE"),
    /** 发证机关 **/
    CRE_AUTHORITY_ORG("CRE_AUTHORITY_ORG"),
    /** 户籍地址 **/
    CENSUS_REGISTOR_ADDRESS("CENSUS_REGISTOR_ADDRESS"),
    /** 职业 **/
    PROFESSION("PROFESSION"),
    /** 证件正面照 **/
    CRE_FACE_PIC("CRE_FACE_PIC"),
    /** 证件反面照 **/
    CRE_BACK_PIC("CRE_BACK_PIC"),
    /** 手持证件半身照 **/
    CRE_HOLD_PIC("CRE_HOLD_PIC"),
    /** 签发地点 **/
    ISSUE_PLACE("ISSUE_PLACE"),
    /** 企业名称 **/
    ENT_NAME("ENT_NAME"),
    /** 企业注册地址 **/
    ENT_REG_ADDR("ENT_REG_ADDR"),
    /** 企业类型 **/
    ENT_TYPE("ENT_TYPE"),
    /** 企业成立日期 **/
    ENT_BUILD_DATE("ENT_BUILD_DATE");

    private String code;

    PerChangeKey(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
