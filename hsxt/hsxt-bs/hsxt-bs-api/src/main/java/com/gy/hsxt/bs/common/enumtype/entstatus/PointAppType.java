/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype.entstatus;

/**
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.entstatus
 * @ClassName: PointAppType
 * @Description: 积分活动申请类型
 * 
 * @author: xiaofl
 * @date: 2015-9-10 下午3:14:06
 * @version V1.0
 */
public enum PointAppType {

    /** 停止积分活动申请 **/
    STOP_PONIT_ACITIVITY(0),

    /** 参与积分活动申请 **/
    JOIN_PONIT_ACTIVITY(1);

    private int code;

    PointAppType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
