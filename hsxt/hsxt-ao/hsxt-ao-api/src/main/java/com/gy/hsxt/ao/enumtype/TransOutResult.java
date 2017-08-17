/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.ao.enumtype;

/**
 * 银行转帐结果状态枚举定义
 * 
 * @Package: com.gy.hsxt.bs.order.enumtype
 * @ClassName: TransOutStatus
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-10-20 下午7:03:47
 * @version V3.0.0
 */
public enum TransOutResult {

    /** 待提交银行 **/
    TRANS_CASH_PRE(-1),
    /** 交易错误 **/
    TRANS_ERROR(-99),
    /** 银行转账成功 **/
    TRANS_CASH_OUT(1000),
    /** 提现失败 **/
    TRANS_CASH_BACK(1001),
    /** 银行处理中 **/
    REFUNDED(1002),
    /** 银行转账银行退票退回 **/
    TRANS_CASH_BANK_BACK(1003);

    private Integer code;

    TransOutResult(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
