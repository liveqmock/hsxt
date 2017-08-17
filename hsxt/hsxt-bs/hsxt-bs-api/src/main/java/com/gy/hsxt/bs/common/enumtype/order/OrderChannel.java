/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.common.enumtype.order;

/**
 * 订单支付方式的枚举定义
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.order
 * @ClassName: OrderStatus
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-9-8 下午5:47:52
 * @version V1.0
 */
public enum OrderChannel {

    // 网页终端
    WEB(1),
    // 移动终端
    MOBILE(2),
    // 经营平台
    RUN_PLATE(3),
    // POS终端
    POS_TERMINAL(4);

    private int code;

    OrderChannel(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
