/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.ao.enumtype;

/**
 * 货币币种枚举定义
 * 
 * @Package: com.gy.hsxt.ao.enumtype
 * @ClassName: MoneyType
 * @Description:
 * 
 * @author: kongsl
 * @date: 2016-2-17 上午9:35:19
 * @version V3.0.0
 */
public enum MoneyType {

    /**
     * 积分
     */
    PV("PV"),
    /**
     * 互生币
     */
    HSB("HSB"),
    /**
     * 货币
     */
    CUR("CNY");
    private String code;

    MoneyType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
