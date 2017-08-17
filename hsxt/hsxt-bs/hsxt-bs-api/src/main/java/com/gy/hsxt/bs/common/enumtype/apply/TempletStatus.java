/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype.apply;

/**
 * 合同模板状态
 *
 * @Package : com.gy.hsxt.bs.common.enumtype.apply
 * @ClassName : TempletStatus
 * @Description : 合同模板状态
 * 审批状态 0-已启用 1-待启用 2-待启用复核 3-待启用复核
 * @Author : xiaofl
 * @Date : 2015-9-10 下午2:13:40
 * @Version V1.0
 */
public enum TempletStatus {

    /**
     * 已启用
     **/
    ENABLED,

    /**
     * 停用/待启用
     **/
    STOP,

    /**
     * 启用待复核
     **/
    ENABLE_APPR,

    /**
     * 停用待复核
     **/
    STOP_APPR;

    /**
     * 校验状态
     *
     * @param status 状态
     * @return {@code boolean}
     */
    public static boolean checkStatus(Integer status) {
        if (status == null) return false;
        for (TempletStatus templetStatus : values()) {
            if (status == templetStatus.ordinal()) {
                return true;
            }
        }
        return false;
    }
}
