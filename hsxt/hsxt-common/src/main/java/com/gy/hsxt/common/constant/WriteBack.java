/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.common.constant;

/**
 * 红冲标识枚举定义
 * 
 * @Package: com.gy.hsxt.common.constant
 * @ClassName: WriteBack
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-12-11 下午8:38:02
 * @version V3.0.0
 */
public enum WriteBack {

    // 正常
    NORMAL(0),
    // 自动冲正
    AUTO_REVERSE(1),
    // 手工红冲
    HANDWORK_REVERSE(2);

    private Integer code;

    WriteBack(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
