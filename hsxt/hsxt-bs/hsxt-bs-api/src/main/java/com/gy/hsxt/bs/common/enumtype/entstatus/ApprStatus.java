/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype.entstatus;

/**
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.entstatus
 * @ClassName: ApprStatus
 * @Description: 审批状态
 * 
 * @author: xiaofl
 * @date: 2015-9-10 下午3:01:29
 * @version V1.0
 */
public enum ApprStatus {

    /** 待审批(待平台审批) **/
    WAIT_TO_APPROVE(0),

    /** 待复核(平台审批通过) **/
    APPROVED(1),

    /** 复核通过 **/
    APPROVAL_REVIEWED(2),

    /** 审批驳回(平台审批驳回) **/
    REJECTED(3),

    /** 复核驳回(平台复核驳回) **/
    REVIEW_REJECTED(4),

    /** 查询用组合状态：待审批(包含待审批与待复核) **/
    COMBO_WAIT_APPROVE(10),

    /** 查询用组合条件：审批驳回(包含审批驳回与复核驳回) **/
    COMBO_APPR_REJECTED(43),

    ;

    private int code;

    ApprStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    /**
     * 校验审批状态类型
     * 
     * @param code
     *            状态代码
     * @return boolean
     */
    public static boolean checkStatus(int code) {
        for (ApprStatus apprStatus : ApprStatus.values())
        {
            if (apprStatus.getCode() == code)
            {
                return true;
            }
        }
        return false;
    }

}
