/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.bs.common.enumtype.resfee;

/**
 * @Description: 资源费分配类型
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.resfee
 * @ClassName: AllocType
 * @author: yangjianguo
 * @date: 2016-3-31 上午10:25:54
 * @version V1.0
 */
public enum AllocType {
    /**
     * 1、申报兑换互生币
     */
    ENT_BUY_HSB(1),
    /**
     * 2、劳务费收入
     */
    SERVICE_FEE(2),
    /**
     * 3、管理费收入
     */
    MANAGE_FEE(3),
    /**
     * 4、 平台资源费收入
     */
    PLAT_RES_FEE(4),

    ;
    private int code;

    public int getCode() {
        return code;
    }

    AllocType(int code) {
        this.code = code;
    }
}
