/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.tm.enumtype;

/**
 * 值班组状态定义
 * 
 * @Package: com.gy.hsxt.tm.enumtype
 * @ClassName: GroupType
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-10 下午12:05:39
 * @version V3.0.0
 */
public enum GroupStatus {

    // 开启
    OPENED(1),
    // 关闭
    CLOSED(0);

    private Integer code;

    GroupStatus(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
