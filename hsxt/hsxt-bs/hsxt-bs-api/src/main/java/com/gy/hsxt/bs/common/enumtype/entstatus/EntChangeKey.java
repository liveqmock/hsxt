/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype.entstatus;

/** 
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.entstatus  
 * @ClassName: EntChangeKey 
 * @Description: 企业重要信息变更Key
 *
 * @author: xiaofl 
 * @date: 2015-12-15 下午3:06:08 
 * @version V1.0 
 

 */ 
public enum EntChangeKey {
    
    /** 企业名称 **/
    CUST_NAME("CUST_NAME"),
    /** 企业英文名称 **/
    CUST_NAME_EN("CUST_NAME_EN"),
    /** 企业地址 **/
    CUST_ADDRESS("CUST_ADDRESS"),
    /** 联系人姓名 **/
    LINKMAN("LINKMAN"),
    /** 联系人手机 **/
    LINK_MOBILE("LINK_MOBILE"),
    /** 法人代表 **/
    LEGAL_REP("LEGAL_REP"),
    /** 法人代表证件类型 **/
    LEGAL_REP_CRE_TYPE("LEGAL_REP_CRE_TYPE"),
    /** 法人代表证件号码 **/
    LEGAL_REP_CRE_NO("LEGAL_REP_CRE_NO"),
    /** 营业执照号 **/
    BIZ_LICENCE_NO("BIZ_LICENCE_NO"),
    /** 组织代码证号 **/
    ORGANIZER_NO("ORGANIZER_NO"),
    /** 纳税人识别号 **/
    TAXPAYER_NO("TAXPAYER_NO"),
    /** 法人代表证件正面 **/
    LEGAL_REP_CRE_FACE_PIC("LEGAL_REP_CRE_FACE_PIC"),
    /** 法人代表证件反面 **/
    LEGAL_REP_CRE_BACK_PIC("LEGAL_REP_CRE_BACK_PIC"),
    /** 营业执照正本扫描件 **/
    BIZ_LICENCE_CRE_PIC("BIZ_LICENCE_CRE_PIC"),
    /** 组织结构代码证正本扫描件 **/
    ORGANIZER_CRE_PIC("ORGANIZER_CRE_PIC"),
    /** 税务登记证正本扫描件 **/
    TAXPAYER_CRE_PIC("TAXPAYER_CRE_PIC"),
    /** 联系人授权委托书 **/
    LINK_AUTHORIZE_PIC("LINK_AUTHORIZE_PIC");

    private String code;

    EntChangeKey(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
