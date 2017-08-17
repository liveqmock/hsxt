/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.task.enumtype;

/**
 * 优先级别枚举定义
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype
 * @ClassName: TaskLevel
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-10-29 下午9:16:18
 * @version V3.0.0
 */
public enum TaskLevel {

    /** 低 **/
    LOW(1),
    /** 中 **/
    MIDDLE(2),
    /** 高 **/
    HIGH(2);

    private int code;

    TaskLevel(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
