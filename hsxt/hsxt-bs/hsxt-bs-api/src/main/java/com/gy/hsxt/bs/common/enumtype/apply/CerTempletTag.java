/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype.apply;

public enum CerTempletTag {

    /** 平台名称(发证单位) **/
    getPlatName("getPlatName"),

    /** 服务公司互生号(所属服务机构互生号) **/
    getSerResNo("getSerResNo"),

    /** 服务公司名称 (所属服务机构) **/
    getSerEntName("getSerEntName"),

    /** 企业互生号(证书编号) **/
    getEntResNo("getEntResNo"),

    /** 企业名称(销售商) **/
    getEntCustName("getEntCustName"),

    /** 营业执照编号(证件号码) **/
    getLicenseNo("getLicenseNo"),

    /** 唯一识别码 **/
    getUniqueCode("getUniqueCode"),

    /** 验证地址 **/
    getCheckAddress("getCheckAddress"),

    /** 发证日期 **/
    getSendDate("getSendDate");

    private String code;

    CerTempletTag(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
