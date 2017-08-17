/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype.apply;

/**
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.apply
 * @ClassName: FeeFlag
 * @Description: 收费标识
 * 
 * @author: xiaofl
 * @date: 2015-9-10 上午10:31:06
 * @version V1.0
 */
public enum FeeFlag {

    /** 欠款 **/
    DEBT(1),

    /** 免费 **/
    FREE(2);

    private int code;

    FeeFlag(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
