/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype.apply;

/**
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.apply
 * @ClassName: IntentCustStatus
 * @Description: 意向客户受理状态
 * 
 * @author: xiaofl
 * @date: 2015-9-10 上午9:39:27
 * @version V1.0
 */
public enum IntentCustStatus {

    /** 待处理 **/
    WAIT_TO_HANDLE(1),

    /** 客户联系中 **/
    CONTACTING_CUST(2),

    /** 申报中 **/
    APPLYING(3),

    /** 申报成功 **/
    APPLY_SUCCESS(4);

    private int code;

    IntentCustStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
