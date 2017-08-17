/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.tm.enumtype;

/**
 * 值班组类型定义
 * 
 * @Package: com.gy.hsxt.tm.enumtype
 * @ClassName: SendTaskMod
 * @Description:
 * 
 * @author: kongsl
 * @date: 2015-11-10 下午12:05:39
 * @version V3.0.0
 */
public enum SendTaskMod {

    /**
     * 自动派单
     */
    AUTO_SEND(0),

    /**
     * 手动派单
     */
    HAND_SEND(1);

    private Integer code;

    SendTaskMod(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
