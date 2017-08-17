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
 * @ClassName: GroupType
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-10 下午12:05:39
 * @version V3.0.0
 */
public enum GroupType {

    // 客服值班组
    SERVICE_GROUP(1),
    // 业务值班组
    BIZ_GROUP(2);

    private Integer code;

    GroupType(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
