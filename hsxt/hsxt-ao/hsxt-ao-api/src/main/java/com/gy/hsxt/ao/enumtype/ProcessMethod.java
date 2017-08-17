/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.ao.enumtype;

/**
 * 处理方式枚举定义
 * 
 * @Package: com.gy.hsxt.bs.order.enumtype
 * @ClassName: ProcessMethod
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-10-22 下午5:13:49
 * @version V3.0.0
 */
public enum ProcessMethod {

    // 自动退回货币
    AUTO_BACK(1),
    // 线下人工处理
    OFFLINE_PROCESS(2);
    private Integer code;

    ProcessMethod(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
