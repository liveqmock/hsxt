/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.common.constant;

/**
 * 货币代码枚举定义
 * 
 * @Package: com.gy.hsxt.ao.enumtype
 * @ClassName: CurrencyCode
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-26 上午11:30:18
 * @version V3.0.0
 */
public enum CurrencyCode {

    /**
     * 互生币
     */
    HSB("HSB"),
    /**
     * 货币支付
     */
    CNY("CNY");

    private String code;

    CurrencyCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
