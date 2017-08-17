/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.ao.enumtype;

/**
 * 支付状态枚举定义
 * 
 * @Package: com.gy.hsxt.ao.enumtype
 * @ClassName: BuyHsbPayStatus
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2016-1-8 上午11:40:32
 * @version V3.0.0
 */
public enum BuyHsbPayStatus {
    // 待支付
    READY(-1, "待支付"),
    // 无效
    INVALID(-2, "无效"),
    // 支付成功
    PAY_SUCCESS(100, "支付成功"),
    // 支付失败
    PAY_FAILED(101, "支付失败"),
    // 支付处理中
    PAY_HANDLING(102, "支付处理中");
    /**
     * 编号
     */
    private Integer code;

    /**
     * 描述
     */
    private String describe;

    BuyHsbPayStatus(Integer code, String describe) {
        this.code = code;
        this.setDescribe(describe);
    }

    /**
     * 根据code查找describe
     * 
     * @param code
     * @return
     */
    public static String getDescribe(int code) {
        for (BuyHsbPayStatus buyHsbPs : BuyHsbPayStatus.values())
        {
            if (buyHsbPs.getCode() == code)
            {
                return buyHsbPs.getDescribe();
            }
        }
        return null;
    }

    /**
     * 根据describe查找code
     * 
     * @param describe
     * @return
     */
    public static Integer getCode(String describe) {
        for (BuyHsbPayStatus buyHsbPs : BuyHsbPayStatus.values())
        {
            if (buyHsbPs.getDescribe().equals(describe))
            {
                return buyHsbPs.getCode();
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

}
