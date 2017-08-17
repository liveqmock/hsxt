/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype.resfee;

/**
 * 资源费分配方式
 *
 * @Package: com.gy.hsxt.bs.common.enumtype.resfee
 * @ClassName: AllocMethod
 * @Description: 资源费分配规则--分配方式 枚举类
 * @author: liuhq
 * @date: 2015-9-2 下午2:19:14
 * @version V1.0
 */
public enum AllocWay {
    /**
     * 按比例分配
     */
    PROPORTION(1),
    /**
     * 按金额分配
     */
    AMOUNT(2);
    private int code;

    public int getCode() {
        return code;
    }

    AllocWay(int code) {
        this.code = code;
    }

    /**
     * 校验分配方式
     *
     * @param code 代码
     * @return boolean
     */
    public static boolean checkWay(int code) {
        for (AllocWay way : AllocWay.values()) {
            if (way.getCode() == code) {
                return true;
            }
        }
        return false;
    }
}
