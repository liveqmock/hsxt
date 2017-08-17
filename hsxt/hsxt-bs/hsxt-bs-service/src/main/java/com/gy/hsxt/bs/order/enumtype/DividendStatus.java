/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.order.enumtype;

/**
 * 投资分红状态枚举定义
 *
 * @Package: com.gy.hsxt.bs.order.enumtype
 * @ClassName: DividendStatus
 * @Description: TODO
 *
 * @author: kongsl
 * @date: 2015-10-20 下午7:03:47
 * @version V3.0.0
 */
public enum DividendStatus {

    // 待分红年份
    WAIT_DIVIDEND(1),
    // 投资分红完成
    INVEST_DIVIDEND_COMPLETE(2),
    // 记帐分解完成
    ACCOUNT_DETAILS_COMPLETE(3),
    // 记帐文件生成完成
    ACCOUNT_FILE_COMPLETE(4),
    // 记帐成功
    ACCOUNT_SUCCESS(5),
    // 年度分红完成
    DIVIDEND_COMPLETE(6);

    private Integer code;

    DividendStatus(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
