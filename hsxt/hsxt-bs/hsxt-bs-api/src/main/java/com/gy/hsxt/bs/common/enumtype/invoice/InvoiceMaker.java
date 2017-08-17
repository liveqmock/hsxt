/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.common.enumtype.invoice;

import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.common.utils.HsResNoUtils;

/**
 * 开票方枚举类
 *
 * @Package :com.gy.hsxt.bs.common.enumtype.invoice
 * @ClassName : InvoiceMaker
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/12/15 11:54
 * @Version V3.0.0.0
 */
public enum InvoiceMaker {
    /**
     * 平台
     */
    PLAT,
    /**
     * 客户
     */
    CUST;

    /**
     * 根据互生号获取开票方
     *
     * @param bizType 发票业务类型
     * @return InvoiceMaker
     */
    public static InvoiceMaker makeOf(String bizType) {
        HsAssert.hasText(bizType, RespCode.PARAM_ERROR, "根据互生号获取开票方的参数[bizType]为空");
        return BizType.CP_ALL_INVOICE.getBizCode().equals(bizType) ? CUST : PLAT;
    }

    /**
     * 校验类型
     *
     * @param ordinal position
     * @return boolean
     */
    public static boolean check(int ordinal) {
        for (InvoiceMaker invoiceMaker : InvoiceMaker.values()) {
            if (invoiceMaker.ordinal() == ordinal) {
                return true;
            }
        }
        return false;
    }

}
