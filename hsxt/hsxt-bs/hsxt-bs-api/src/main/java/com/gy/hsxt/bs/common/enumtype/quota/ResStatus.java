/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.common.enumtype.quota;

public enum ResStatus {


    /** 未使用 **/
    NOT_USED(0),

    /** 已使用 **/
    USED(1),

    /** 占用 **/
    OCCUPIED(2);

    private int code;

    ResStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }


    /**
     * 校验状态
     *
     * @param status 状态
     * @return {@code boolean}
     */
    public static boolean checkResStatus(Integer status) {
        if (status == null) return false;
        for (ResStatus resStatus : values()) {
            if (resStatus.getCode() == status) {
                return true;
            }
        }
        return false;
    }

}
