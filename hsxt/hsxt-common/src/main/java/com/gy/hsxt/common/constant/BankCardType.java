/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.common.constant;

/**
 * 银行卡类型定义
 * 
 * @Package: com.gy.hsxt.common.constant
 * @ClassName: BankCardType
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2016-1-12 下午6:32:33
 * @version V3.0.0
 */
public enum BankCardType {
    /** 借记卡 **/
    DEBIT_CARD(1),

    /** 贷记卡（信用卡） **/
    CREDIT_CARD(2), ;

    private int code;

    BankCardType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
