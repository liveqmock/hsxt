/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.common.enumtype.tax;

/**
 * 税率调整状态
 *
 * @Package : com.gy.hsxt.bs.common.enumtype.tax
 * @ClassName : TaxrateStatus
 * @Description : 税率调整状态
 * 0-未审批 1-地区平台初审通过/待复核 2-地区平台复核通过 3-初审驳回 4-复核驳回
 * @Author : chenm
 * @Date : 2015/12/29 17:21
 * @Version V3.0.0.0
 */
public enum TaxrateStatus {

    /**
     * 待审批
     */
    PENDING,

    /**
     * 初审通过/待复审
     */
    INITIAL,

    /**
     * 复审通过
     */
    REVIEW,

    /**
     * 初审驳回
     */
    REJECT,
    /**
     * 复审驳回
     */
    REBUT;

    /**
     * 检查参数是否符合条件
     *
     * @param code 状态码
     * @return boolean j检查结果
     */
    public static boolean check(int code) {
        boolean pass = false;
        for (TaxrateStatus status : TaxrateStatus.values()) {
            if (status.ordinal() == code) {
                pass = true;
                break;
            }
        }
        return pass;
    }
}
