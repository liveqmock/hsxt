/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.common.constant;

/**
 * 支付系统商品名称定义
 * 
 * @Package: com.gy.hsxt.common.constant
 * @ClassName: GoodsName
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2016-7-1 上午9:45:28
 * @version V1.0
 */
public enum GoodsName {

    /** 系统使用年费 **/
    ANNUAL_FEE("系统使用年费"),

    /** 系统销售费订单 **/
    RES_FEE_ALLOT("系统销售费订单"),

    /** 兑换互生币 **/
    BUY_HSB("兑换互生币"),

    /** 申购工具 **/
    BUY_TOOL("申购工具"),

    /** 售后服务 **/
    AFTER_SERVICE("售后服务"),

    /** 个人补卡 **/
    REMAKE_MY_CARD("个人补卡"),

    /** 企业重做卡 **/
    REMAKE_BATCH_CARD("企业重做卡"),

    /** 定制卡样费用 **/
    CARD_STYLE_FEE("定制卡样费用"),

    /** 消费者系统资源 **/
    APPLY_PERSON_RESOURCE("消费者系统资源");

    private String code;

    GoodsName(String code)
    {
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }

}
