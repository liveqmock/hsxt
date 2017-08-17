/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.ao.enumtype;

/**
 * 交易结果枚举定义
 * 
 * @Package: com.gy.hsxt.ao.enumtype
 * @ClassName: TransResult
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-26 上午11:21:40
 * @version V3.0.0
 */
public enum BuyHsbTransResult {

    // 等待付款
    WAIT_PAY(0),
    // 交易成功
    TRANS_SUCCESS(1),
    // 冲正撤单
    REVERSE(2),
    // 交易失败
    TRANS_FAILED(3),
    // 过期失效
    EXPIRED(4);

    private int code;

    BuyHsbTransResult(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
