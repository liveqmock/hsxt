/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.common.enumtype.msgtpl;

/**
 * 模版状态枚举定义
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.msgtpl
 * @ClassName: MsgTempStatus
 * @Description:
 * 
 * @author: kongsl
 * @date: 2015-9-8 下午5:47:52
 * @version V1.0
 */
public enum MsgTempStatus {

    /**
     * 待复核
     */
    WAIT_REVIEW(1),
    /**
     * 已启用
     */
    ENABLED(2),
    /**
     * 待启用
     */
    WAIT_ENABLE(3),

    /**
     * 停用待复核
     */
    UNABLE_REVIEW(4),

    /**
     * 启用待复核
     */
    ENABLE_REVIEW(5), ;

    private int code;

    MsgTempStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
