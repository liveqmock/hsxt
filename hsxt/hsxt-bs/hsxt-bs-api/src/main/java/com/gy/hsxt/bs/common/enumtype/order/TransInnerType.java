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
public enum TransInnerType {

    /** 积分转互生币 **/
    PV_TO_HSB("M12100"),

    /** 互生币转货币 **/
    HSB_TO_CURRENCY("M12101"),

    /** 代兑互生币 **/
    HSB_ENTERPRISE_TO_CUSTOMER("M18888");

    /** 枚举类型代码 **/
    private String code;

    TransInnerType(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

}
