/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.ao.enumtype;

/**
 * 银行转账提交类型枚举定义
 * 
 * @Package: com.gy.hsxt.ao.enumtype
 * @ClassName: CommitType
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2016-1-18 下午5:47:42
 * @version V3.0.0
 */
public enum CommitType {

    // 单笔自动
    ONE_AUTO(1),
    // 批量自动
    BATCH_AUTO(2),
    // 手工提交
    HAND_COMMIT(3);
    private Integer code;

    CommitType(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
