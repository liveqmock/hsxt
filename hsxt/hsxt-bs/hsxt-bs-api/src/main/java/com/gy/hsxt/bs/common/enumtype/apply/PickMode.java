/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.common.enumtype.apply;

/**
 * 申报选号方式
 * @Package : com.gy.hsxt.bs.common.enumtype.apply
 * @ClassName : PickMode
 * @Description : 申报选号方式 0-顺序选择 1-人工选择
 * @Author : chenm
 * @Date : 2016/3/11 14:31
 * @Version V3.0.0.0
 */
public enum  PickMode {

    /**
     * 顺序选择
     */
    SEQUENTIAL,
    /**
     * 人工选择
     */
    ARTIFICIAL;

    /**
     * 校验方式
     * @param ordinal 方式编号
     * @return {@code boolean}
     */
    public static boolean checkMode(Integer ordinal) {
        for (PickMode mode : values()) {
            if (ordinal == mode.ordinal()) {
                return true;
            }
        }
        return false;
    }
}
