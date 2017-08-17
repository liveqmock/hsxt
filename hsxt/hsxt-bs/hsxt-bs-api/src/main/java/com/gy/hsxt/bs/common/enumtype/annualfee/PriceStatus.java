/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.common.enumtype.annualfee;

/**
 * 年费方案状态
 *
 * @Package :com.gy.hsxt.bs.common.enumtype.annualfee
 * @ClassName : PriceStatus
 * @Description : 年费方案状态
 * @Author : chenm
 * @Date : 2015/12/10 18:00
 * @Version V3.0.0.0
 */
public enum PriceStatus {

    /**
     * 待审批
     */
    PENDING,
    /**
     * 已启用
     */
    ENABLE,
    /**
     * 审批驳回
     */
    REJECT,
    /**
     * 已停用
     */
    DISABLE;


    /**
     * 检查参数是否符合条件
     *
     * @param code 状态码
     * @return boolean j检查结果
     */
    public static boolean check(int code) {
        boolean pass = false;
        for (PriceStatus priceStatus : PriceStatus.values()) {
            if (priceStatus.ordinal() == code) {
                pass = true;
                break;
            }
        }
        return pass;
    }
}
