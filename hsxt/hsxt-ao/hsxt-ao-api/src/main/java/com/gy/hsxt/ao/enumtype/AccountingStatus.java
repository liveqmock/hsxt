/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.ao.enumtype;

/**
 * 记帐状态枚举定义
 * 
 * @Package: com.gy.hsxt.ao.enumtype
 * @ClassName: AccountingStatus
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-26 下午2:42:33
 * @version V3.0.0
 */
public enum AccountingStatus {

    // 未记账
    UN_ACC(0),
    // 已记账
    IS_ACC(1),
    // 已冲正
    IS_REVERSE(2);
    private Integer code;

    AccountingStatus(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
