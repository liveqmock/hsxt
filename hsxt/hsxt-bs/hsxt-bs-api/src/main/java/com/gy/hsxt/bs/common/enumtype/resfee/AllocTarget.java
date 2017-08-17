/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype.resfee;

/**
 * 资源费分配对象
 *
 * @Package: com.gy.hsxt.bs.common.enumtype.resfee
 * @ClassName: allocTarget
 * @Description: 资源费分配规则--分配对象 枚举类
 * @author: liuhq
 * @date: 2015-9-2 下午2:14:24
 * @version V1.0
 */
public enum AllocTarget {
    /**
     * 推广公司
     */
    EXTENSION(1),
    /**
     * 上级管理公司
     */
    MANAGE(2),
    /**
     * 地区平台
     */
    AREA(3);
    private int code;

    public int getCode() {
        return code;
    }

    AllocTarget(int code) {
        this.code = code;
    }

    /**
     * 校验分配对象
     *
     * @param code 代码
     * @return boolean
     */
    public static boolean checkTarget(int code) {
        for (AllocTarget target : AllocTarget.values()) {
            if (target.getCode() == code) {
                return true;
            }
        }
        return false;
    }
}
