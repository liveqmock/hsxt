/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.ao.enumtype;

/**
 * 转账状态枚举定义
 * 
 * @Package: com.gy.hsxt.ao.enumtype
 * @ClassName: TransStatus
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-26 下午2:42:33
 * @version V3.0.0
 */
public enum TransStatus {

    /**
     * 申请中（大额转账等待复核）
     */
    APPLYING(1),
    /**
     * 付款中（小额默认即付款中，大额允许转出后。暂未得到支付系统通知）
     */
    PAYING(2),
    /**
     * 已撤单（大额转账被拒绝）
     */
    REVOKED(3),
    /**
     * 已冲正（提交到支付系统，发生了交易错误，这种情况自动冲正）
     */
    REVERSED(4),
    /**
     * 银行退票（转账成功后，通过对账发现被退票）
     */
    BANK_BACK(5),
    /**
     * 转账失败（支付系统提交到银行网关后）
     */
    TRANS_FAILED(6),
    /**
     * 转账退款完成（转账失败和被银行退票，进行了失败退款处理）
     */
    BANK_BACK_COMPLETE(7),
    /**
     * 转账成功
     */
    TRANS_SUCCESS(8);

    private Integer code;

    TransStatus(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
