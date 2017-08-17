/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.order.enumtype;

/**
 * 投资状态枚举定义
 * 
 * @Package: com.gy.hsxt.bs.order.enumtype
 * @ClassName: InvestSatus
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-10-20 下午7:03:47
 * @version V3.0.0
 */
public enum InvestSatus {

    /** 积分投资 **/
    POINT_INVEST("T12102"),

    /** 积分投资分红 **/
    POINT_DIVIDEND("T12103");

    private String code;

    InvestSatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
