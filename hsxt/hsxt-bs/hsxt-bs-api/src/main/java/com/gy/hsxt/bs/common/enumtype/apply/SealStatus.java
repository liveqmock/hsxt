/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype.apply;

/**
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.apply
 * @ClassName: SealStatus
 * @Description: 合同/证书状态
 * 
 * @author: xiaofl
 * @date: 2015-9-10 下午2:35:21
 * @version V1.0
 */
public enum SealStatus {

    /**
     * 待盖章
     **/
    WAIT_TO_SEAL,

    /**
     * 已生效
     **/
    TAKE_EFFECT,

    /**
     * 已失效
     **/
    LOSE_EFFICACY,

    /**
     * 须重新盖章状态
     */
    WAIT_TO_RESEAL;

    /**
     * 校验状态
     *
     * @param status 状态
     * @return {@code boolean}
     */
    public static boolean checkStatus(Integer status) {
        if (status == null) return false;
        for (SealStatus sealStatus : values()) {
            if (sealStatus.ordinal() == status) {
                return true;
            }
        }
        return false;
    }
}
