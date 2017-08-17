/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype.invoice;

import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.utils.HsAssert;

/**
 * 配送方式枚举类
 * 
 * @Package :com.gy.hsxt.bs.common.enumtype.invoice
 * @ClassName : PostWay
 * @Description :
 *              <p>
 *              配送方式: 0-快递 1-自取
 *              </p>
 * @Author : chenm
 * @Date : 2015/12/16 10:53
 * @Version V3.0.0.0
 */
public enum PostWay {
    /**
     * 快递
     */
    EXPRESS,
    /**
     * 自取
     */
    PICKUP,
    /**
     * 其他
     */
    OTHER, ;

    /**
     * 校验状态
     * 
     * @param ordinal
     *            position
     * @return boolean
     */
    public static boolean check(Integer ordinal) {
        HsAssert.notNull(ordinal, RespCode.PARAM_ERROR, "配送方式[postWay]为空");
        for (PostWay postWay : PostWay.values())
        {
            if (postWay.ordinal() == ordinal)
            {
                return true;
            }
        }
        return false;
    }
}
