/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype.invoice;

import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.utils.HsAssert;

/**
 * 发票类型
 * 
 * @Package :com.gy.hsxt.bs.common.enumtype.invoice
 * @ClassName : InvoiceType
 * @Description : 发票类型 1-普通增值税发票 0-增值税专用发票
 * @Author : chenm
 * @Date : 2015/12/15 18:38
 * @Version V3.0.0.0
 */
public enum InvoiceType {

    /**
     * 增值税专用发票
     */
    SPECIAL ,
    /**
     * 普通增值税发票
     */
    NORMAL;

    /**
     * 校验发票类型
     * 
     * @param ordinal
     *            position
     * @return boolean
     */
    public static boolean check(Integer ordinal) {
        HsAssert.notNull(ordinal, RespCode.PARAM_ERROR, "发票类型[invoiceType]为null");
        for (InvoiceType invoiceType : InvoiceType.values())
        {
            if (invoiceType.ordinal() == ordinal)
            {
                return true;
            }
        }
        return false;
    }

}
