/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.common.enumtype.invoice;

/**
 * 发票状态
 *
 * @Package :com.gy.hsxt.bs.common.enumtype.invoice
 * @ClassName : InvoiceStatus
 * @Description :
 * <p>申请状态 0-待审核 1-审核驳回 2-待配送/开票成功 3-待签收/已配送 4-已签收  5-拒签</p>
 * @Author : chenm
 * @Date : 2015/12/15 15:59
 * @Version V3.0.0.0
 */
public enum InvoiceStatus {

    /**
     * 待审核
     */
    PENDING,
    /**
     * 审核驳回
     */
    REJECTED,
    /**
     * 待配送/开票成功
     */
    POSTING,
    /**
     * 待签收/已配送
     */
    RECEIVING,
    /**
     * 已签收
     */
    RECEIVED,
    /**
     * 拒签
     */
    RETURN;

    /**
     * 校验发票状态
     *
     * @param ordinal position
     * @return boolean
     */
    public static boolean check(int ordinal) {
        for (InvoiceStatus invoiceStatus : InvoiceStatus.values()) {
            if (invoiceStatus.ordinal() == ordinal) {
                return true;
            }
        }
        return false;
    }

}
