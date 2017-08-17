/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype;

/**
 * 
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype
 * @ClassName: OptTable
 * @Description: 审批表名常量
 * 
 * @author: xiaofl
 * @date: 2015-10-29 下午3:33:12
 * @version V1.0
 */
public enum OptTable {

    /** 意向客户操作审批表 **/
    T_BS_INTENT_CUST_APPR("T_BS_INTENT_CUST_APPR"),

    /** 企业报备操作审批表 **/
    T_BS_FILING_APPR("T_BS_FILING_APPR"),

    /** 申报企业操作审批表 **/
    T_BS_DECLARE_ENT_APPR("T_BS_DECLARE_ENT_APPR"),

    /** 企业实名认证审批表 **/
    T_BS_ENT_REALNAME_APPR("T_BS_ENT_REALNAME_APPR"),

    /** 个人实名认证审批表 **/
    T_BS_PER_REALNAME_APPR("T_BS_PER_REALNAME_APPR"),

    /** 企业重要信息审批表 **/
    T_BS_ENT_CHANGE_INFO_APPR("T_BS_ENT_CHANGE_INFO_APPR"),

    /** 个人重要信息审批表 **/
    T_BS_PER_CHANGE_INFO_APPR("T_BS_PER_CHANGE_INFO_APPR"),

    /** 托管企业积分活动审批表 **/
    T_BS_POINTGAME_APPR("T_BS_POINTGAME_APPR"),

    /** 成员企业注销/报停审批表 **/
    T_BS_COMPANY_STOP_APPR("T_BS_COMPANY_STOP_APPR"),

    /** 企业税率调整审批表 **/
    T_BS_ENT_TAXRATE_CHANGE_APPR("T_BS_TAXRATE_CHANGE_APPR");

    private String code;

    OptTable(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
