/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.common.enumtype.deduction;


/**互生币扣除状态
 * @Package : com.gy.hsxt.bs.common.enumtype.deduction
 * @ClassName : DeductionStatus
 * @Description : 互生币扣除状态
 * @Author : chenm
 * @Date : 2016/3/25 19:07
 * @Version V3.0.0.0
 */
public enum DeductionStatus {

    /**
     * 扣款待复核
     */
    WAIT_APPR,

    /**
     * 扣款成功
     */
    APPR_PASS,

    /**
     * 驳回扣款申请
     */
    APPR_REJECT;

    /**
     * 检查状态
     * @param status 状态
     * @return 结果
     */
    public static boolean checkStatus(Integer status) {
        if (status == null) return false;
        for (DeductionStatus deductionStatus : values()) {
            if (deductionStatus.ordinal() == status) {
                return true;
            }
        }
        return false;
    }
}
