/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.ao.common;

/**
 * 业务类型枚举定义
 * 
 * @Package: com.gy.hsxt.ao.enumtype
 * @ClassName: BizType
 * @Description:
 * 
 * @author: kongsl
 * @date: 2016-1-18 下午5:47:42
 * @version V3.0.0
 */
public enum BizType {

    /**
     * 积分投资
     */
    PV_TO_INVEST(1),
    /**
     * 积分转互生币
     */
    PV_TO_HSB(2),
    /**
     * 互生币转货币
     */
    HSB_TO_CASH(3),
    /**
     * 货币转银行
     */
    CASH_TO_BANK(4),
    /**
     * 兑换互生币
     */
    BUY_HSB(5),
    /**
     * 代兑互生币
     */
    PROXY_BUY_HSB(6),
    //
    ;

    private int code;

    BizType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
