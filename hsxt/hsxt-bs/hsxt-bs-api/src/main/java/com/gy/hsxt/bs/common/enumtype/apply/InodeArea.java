/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype.apply;

/**
 * 增值区域
 *
 * @Package : com.gy.hsxt.bs.common.enumtype.apply
 * @ClassName : InodeArea
 * @Description : 增值区域
 * @Author : xiaofl
 * @Date : 2015-9-10 上午9:29:07
 * @Version V1.0
 */
public enum InodeArea {

    /**
     * 左增值区域
     **/
    LEFT(0),

    /**
     * 右增值区域
     **/
    RIGHT(1);

    private int code;

    InodeArea(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    /**
     * 校验增值区域
     *
     * @param area 区域
     * @return {@code boolean}
     */
    public static boolean checkArea(Integer area) {
        if (area == null) return false;
        for (InodeArea inodeArea : values()) {
            if (inodeArea.getCode() == area) {
                return true;
            }
        }
        return false;
    }
}
