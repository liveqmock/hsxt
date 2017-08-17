/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.common.enumtype.task;

/**
 * 工单状态通用枚举定义,适用于分配状态、执行状态、是否催办
 *
 * @Package: com.gy.hsxt.bs.common.enumtype.task
 * @ClassName: TaskStatus
 * @Description: TODO
 *
 * @author: kongsl
 * @date: 2015-9-17 下午4:18:49
 * @version V1.0
 */
public enum TaskGeneral {

    // 分别代表未分配、未执行、未催办
    IS_NOT(0),
    // 分别代表已分配、已执行、已催办
    IS_OK(1),
    // 分配状态业务处理
    BIZ_ASSIGN(3),
    // 执行状态业务处理
    BIZ_EXECUTE(4),
    // 是否催办状态业务处理
    BIZ_PRESS(5);

    private int code;

    TaskGeneral(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
