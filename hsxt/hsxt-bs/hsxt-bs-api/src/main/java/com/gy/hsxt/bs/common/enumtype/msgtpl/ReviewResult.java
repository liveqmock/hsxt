/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.common.enumtype.msgtpl;

/**
 * 复核结果枚举定义
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.msgtpl
 * @ClassName: ReviewResult
 * @Description:
 * 
 * @author: kongsl
 * @date: 2015-9-8 下午5:47:52
 * @version V1.0
 */
public enum ReviewResult {
    /**
     * 待复核
     */
    WAIT_REVIEW(1),
    /**
     * 通过
     */
    PASS(2),
    /**
     * 驳回
     */
    REJECTED(3), ;

    private int code;

    ReviewResult(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
