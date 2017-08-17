/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.gpf.res.enumtype;

/**
 * 
 * @Package: com.gy.hsxt.gpf.res.enumtype
 * @ClassName: ResQuotaStatus
 * @Description: 资源状态枚举
 * 
 * @author: xiaofl
 * @date: 2015-12-17 下午3:56:01
 * @version V1.0
 */
public enum ResQuotaStatus {

    /** 未分配 **/
    UNASSIGNED(1),

    /** 申请中 **/
    APPLYING(2),

    /** 已分配 **/
    ASSIGNED(3);

    private int code;

    public int getCode() {
        return code;
    }

    ResQuotaStatus(int code) {
        this.code = code;
    }

}
