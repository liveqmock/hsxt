/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.gpf.res.enumtype;

/**
 * 
 * @Package: com.gy.hsxt.gpf.res.enumtype
 * @ClassName: AppType
 * @Description: 申请类型枚举
 * 
 * @author: xiaofl
 * @date: 2015-12-17 下午3:54:43
 * @version V1.0
 */
public enum AppType {

    /** 首次配置 */
    FIRST(1),

    /** 增加 */
    ADD(2),

    /** 减少 */
    LESSEN(3);

    private int type;

    public int getType() {
        return type;
    }

    AppType(int type) {
        this.type = type;
    }

}
