/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype.apply;

/**
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.apply
 * @ClassName: DeclareStatus
 * @Description: 申报状态
 * 
 * @author: xiaofl
 * @date: 2015-9-10 上午10:49:07
 * @version V1.0
 */
public enum DeclareStatus {

    /** 待提交内审 **/
    WAIT_TO_SUBMIT(0),

    /** 服务公司复核 **/
    WAIT_TO_INNER_APPR(1),

    /** 管理公司初审 **/
    WAIT_TO_FIRST_APPR(2),

    /** 管理公司复核 **/
    WAIT_TO_APPR_REVIEW(3),

    /** 服务公司复核驳回 **/
    INNER_REJECTED(4),

    /** 管理公司初审驳回 **/
    FIRST_APPR_REJECTED(5),

    /** 管理公司复核驳回 **/
    REVIEW_REJECTED(6),

    /** 办理期中 **/
    HANDLING(7),

    /** 待开启系统 **/
    WAIT_TO_OPER_OS(8),

    /** 开启系统成功 **/
    OPEN_OS_SUCCESS(9),

    /** 开启系统驳回 **/
    OPEN_OS_REJECTED(10),

    /** 失效 **/
    EXPIRED(11),

    /** 待管理公司审核(组合状态：管理公司初审+管理公司复核)，服务公司查询用 **/
    COMBO_MANAGE_APPR(23),

    /** 管理公司审核驳回(组合状态：管理公司初审驳回+管理公司复核驳回)，服务公司查询用 **/
    COMBO_MANAGE_REJECTED(56),

    ;

    private int code;

    DeclareStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
