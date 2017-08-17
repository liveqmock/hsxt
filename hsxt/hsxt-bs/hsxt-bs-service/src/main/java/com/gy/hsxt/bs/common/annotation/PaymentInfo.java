/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.annotation;

import com.gy.hsxt.bs.common.enumtype.order.OrderType;
import com.gy.hsxt.common.constant.PayChannel;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @Package :com.gy.hsxt.bs.common.annotation
 * @ClassName : PaymentInfo
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/11/16 18:47
 * @Version V3.0.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface PaymentInfo {

    /**
     * The value may indicate a suggestion for a logical component name, to be
     * turned into a Spring bean in case of an autodetected component.
     *
     * @return the suggested component name, if any
     */
    String value() default "";

    /**
     * 支付类型
     *
     * @return PayChannel
     * @see PayChannel
     */
    PayChannel[] payChannel();

    /**
     * 订单类型
     *
     * @return OrderType
     * @see OrderType
     */
    OrderType[] orderType();
}
