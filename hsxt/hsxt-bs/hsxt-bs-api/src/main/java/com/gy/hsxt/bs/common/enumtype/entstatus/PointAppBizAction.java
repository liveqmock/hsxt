/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype.entstatus;

/**
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.entstatus
 * @ClassName: PointAppBizAction
 * @Description: 积分活动业务业务操作
 * 
 * @author: xiaofl
 * @date: 2015-12-29 下午4:56:29
 * @version V1.0
 */
public enum PointAppBizAction {

    /** 服务公司审批托管企业积分活动 **/
    SER_APPR_POINT_ACTIVITY(1),
    /** 平台审批托管企业积分活动 **/
    PLAT_APPR_POINT_ACTIVITY(2),
    /** 平台复核托管企业积分活动 **/
    PLAT_REVIEW_POINT_ACTIVITY(3);

    private int code;

    PointAppBizAction(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
