/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.common.enumtype.order;

/**
 * 转账结果的枚举定义
 *
 * @Package: com.gy.hsxt.bs.common.enumtype.order
 * @ClassName: OrderStatus
 * @Description: TODO
 *
 * @author: kongsl
 * @date: 2015-9-8 下午5:47:52
 * @version V1.0
 */
public enum ProcessStatus {

    // 处理中
    BEING_PROCESSED(0),
    // 待平台审核
    WAIT_PLATFORM_AUDIT(1),
    // 平台审批通过
    PLATFORM_AUDIT_PASS(2),
    // 平台审批拒绝
    PLATFORM_AUDIT_REFUSE(3);

    private int code;

    ProcessStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
