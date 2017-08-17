/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.gpf.res.enumtype;

/**
 * 
 * @Package: com.gy.hsxt.gpf.res.enumtype
 * @ClassName: QuotaAppStatus
 * @Description: 配额申请状态枚举
 * 
 * @author: xiaofl
 * @date: 2015-12-17 下午3:54:59
 * @version V1.0
 */
public enum QuotaAppStatus {

    /** 待复核 **/
    WAIT_TO_APPR(0),

    /** 通过 **/
    PASS(1),

    /** 驳回 **/
    REJECT(2);

    private int code;

    public int getCode() {
        return code;
    }

    QuotaAppStatus(int code) {
        this.code = code;
    }

}
