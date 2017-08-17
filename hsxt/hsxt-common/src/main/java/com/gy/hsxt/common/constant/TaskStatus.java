/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.common.constant;

/**
 * 工单状态定义
 * 
 * @Package: com.gy.hsxt.tm.enumtype
 * @ClassName: TaskStatus
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-10 下午12:05:39
 * @version V3.0.0
 */
public enum TaskStatus {

    // 未分派
    UNASSIGN(0),
    // 未受理
    UNACCEPT(1),
    // 办理中
    DEALLING(2),
    // 已完成
    COMPLETED(3),
    // 已挂起
    HANG_UP(4),
    // 已停止
    STOPPED(5);

    private Integer code;

    TaskStatus(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
