/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.common.enumtype.task;

/**
 * 工单优先级枚举定义
 *
 * @Package: com.gy.hsxt.bs.common.enumtype.task
 * @ClassName: TaskStatus
 * @Description: TODO
 *
 * @author: kongsl
 * @date: 2015-9-17 下午4:18:49
 * @version V1.0
 */
public enum TaskLevel {

    // 低
    LOW(1),
    // 中
    MIDDLE(2),
    // 高
    HIGH(3);

    private int code;

    TaskLevel(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
