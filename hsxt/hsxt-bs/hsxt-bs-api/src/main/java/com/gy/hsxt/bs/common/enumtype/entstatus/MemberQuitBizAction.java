/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype.entstatus;

/**
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.entstatus
 * @ClassName: MemberQuitBizAction
 * @Description: 成员企业注销业务操作
 * 
 * @author: xiaofl
 * @date: 2015-12-29 下午4:56:29
 * @version V1.0
 */
public enum MemberQuitBizAction {
    /** 成员企业提交资料 **/
    MEMBER_ENT_SUBMIT(1),

    /** 服务公司提交资料 **/
    SERVICE_SUBMIT(2),

    /** 服务公司审批通过 **/
    SERVICE_PASS(3),

    /** 服务公司审批驳回 **/
    SERVICE_REJECTED(4),

    /** 地区平台审批通过 **/
    PLAT_APPR_PASS(5),

    /** 地区平台审批驳回 **/
    PLAT_APPR_REJECTED(6),

    /** 地区平台复核通过 **/
    PLAT_REVIEW_PASS(7),

    /** 地区平台复核驳回 **/
    PLAT_REVIEW_REJECTED(8),

    ;

    private int code;

    MemberQuitBizAction(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
