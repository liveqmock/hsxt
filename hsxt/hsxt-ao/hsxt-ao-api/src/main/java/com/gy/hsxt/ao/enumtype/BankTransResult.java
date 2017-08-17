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
public enum BankTransResult {

    // 转账处理
    DISPOSE(0),
    // 转账成功
    SUCCESS(1),
    // 转账失败
    FAILED(2),
    // 银行退票
    BACK(3);

    private Integer code;

    BankTransResult(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
