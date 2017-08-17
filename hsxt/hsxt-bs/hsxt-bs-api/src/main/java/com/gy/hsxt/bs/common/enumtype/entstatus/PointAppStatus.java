/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype.entstatus;

/**
 * 参与、停止积分活动审批状态
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.entstatus
 * @ClassName: QuitStatus
 * @Description: 0：待服务公司审批 1：待地区平台审批 2：待地区平台复核 3：地区平台复核通过 4：服务公司审批驳回 5：地区平台审批驳回
 *               6：地区平台复核驳回
 * @author: xiaofl
 * @date: 2015-9-10 下午3:23:12
 * @version V1.0
 */
public enum PointAppStatus {

    /** 待服务公司审批 **/
    WAIT_TO_APPROVE(0),

    /** 待地区平台审批 **/
    SERVICE_APPROVED(1),

    /** 待地区平台复核 **/
    PLAT_APPROVED(2),

    /** 地区平台复核通过 **/
    PLAT_APPROVAL_REVIEWED(3),

    /** 服务公司审批驳回 **/
    SERVICE_REJECTED(4),

    /** 地区平台审批驳回 **/
    PLAT_REJECTED(5),

    /** 地区平台复核驳回 **/
    PLAT_REVIEW_REJECTED(6),

    /** 组合条件：待地区平台审批(包含待地区平台审批与待地区平台复核) **/
    COMBO_WAIT_PLAT_APPR(12),

    /** 组合条件：地区平台审批驳回(包含地区平台审批驳回与地区平台复核驳回) **/
    COMBO_PLAT_REJECTED(56),

    ;

    private int code;

    PointAppStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
