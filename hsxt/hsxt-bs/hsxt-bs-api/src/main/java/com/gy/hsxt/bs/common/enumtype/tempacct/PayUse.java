/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype.tempacct;

/**
 * 付款用途 枚举类
 *
 * @Package : com.gy.hsxt.bs.common.enumtype.tempacct
 * @ClassName : PayUse
 * @Description : 付款用途 枚举类
 * <p/>
 * 1：申报付款 2：申购工具 3：缴纳系统使用年费 4：积分预付款账户充值 5：积分卡重新制作 6：兑换互生币 备注：多选
 * @Author : liuhq
 * @Date : 2015-9-8 上午9:27:58
 * @Version V3.0
 */
public enum PayUse {
    /**
     * 申报付款
     */
    DECLARE(1),
    /**
     * 申购工具
     */
    PURCHASE(2),
    /**
     * 缴纳系统使用年费
     */
    PAY_ANNUAL_FEE(3),
    /**
     * 积分预付款账户充值
     */
    @Deprecated
    INTEGRAL_PAYMENT(4),
    /**
     * 积分卡重新制作
     */
    CARD_RE_PRODUCTION(5),
    /**
     * 兑换互生币
     */
    CURRENCY_EXCHANGE(6);
    private int code;

    public int getCode() {
        return code;
    }

    PayUse(int code) {
        this.code = code;
    }

    /**
     * 检查是否为付款用途
     *
     * @param code 用途代码
     * @return true or false
     */
    public static boolean checkUse(int code) {
        for (PayUse payUse : PayUse.values()) {
            if (payUse.getCode() == code) {
                return true;
            }
        }
        return false;
    }
}
