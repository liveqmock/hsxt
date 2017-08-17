/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.common.enumtype.msgtpl;

/**
 * 操作类型枚举定义
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.msgtpl
 * @ClassName: MsgOptType
 * @Description:
 * 
 * @author: kongsl
 * @date: 2015-9-8 下午5:47:52
 * @version V1.0
 */
public enum MsgOptType {

    /**
     * 启用
     */
    START(1),
    /**
     * 停用
     */
    STOP(0), ;

    private int code;

    MsgOptType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
