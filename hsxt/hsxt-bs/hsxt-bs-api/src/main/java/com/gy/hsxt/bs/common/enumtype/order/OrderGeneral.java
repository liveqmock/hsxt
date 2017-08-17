/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.common.enumtype.order;

/**
 * 订单状态通用 的枚举定义,适用于是否需要发票、是否已开发票、是否已开发票、失效标识、是否平台代理
 *
 * @Package: com.gy.hsxt.bs.common.enumtype.order
 * @ClassName: OrderStatus
 * @Description: TODO
 *
 * @author: kongsl
 * @date: 2015-9-8 下午5:47:52
 * @version V1.0
 */
public enum OrderGeneral {

    // 否
    NO(0),
    // 是
    YES(1);

    private int code;

    OrderGeneral(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
