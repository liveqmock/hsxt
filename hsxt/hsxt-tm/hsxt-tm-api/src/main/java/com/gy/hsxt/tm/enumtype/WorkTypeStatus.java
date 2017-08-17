/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.tm.enumtype;

/**
 * 值班状态定义
 * 
 * @Package: com.gy.hsxt.tm.enumtype
 * @ClassName: WorkTypeStatus
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-10 下午12:05:39
 * @version V3.0.0
 */
public enum WorkTypeStatus {

    // 早班
    MORNING_SHIFT(1, "早"),
    // 午班
    NOON_SHIFT(2, "午"),
    // 休假
    ON_HOLIDAY(3, "休"),
    // 旷工
    NEGLECT_WORK(4, "旷");

    WorkTypeStatus(Integer code, String describe) {
        this.code = code;
        this.setDescribe(describe);
    }

    /**
     * 根据code查找describe
     * 
     * @param code
     * @return
     */
    public static String getDescribe(int code) {
        for (WorkTypeStatus wts : WorkTypeStatus.values())
        {
            if (wts.getCode() == code)
            {
                return wts.getDescribe();
            }
        }
        return null;
    }

    /**
     * 根据describe查找code
     * 
     * @param describe
     * @return
     */
    public static Integer getCode(String describe) {
        for (WorkTypeStatus wts : WorkTypeStatus.values())
        {
            if (wts.getDescribe().equals(describe))
            {
                return wts.getCode();
            }
        }
        return null;
    }

    /**
     * 编号
     */
    private Integer code;

    /**
     * 描述
     */
    private String describe;

    /**
     * @return the 编号
     */
    public Integer getCode() {
        return code;
    }

    /**
     * @param 编号
     *            the code to set
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * @return the 描述
     */
    public String getDescribe() {
        return describe;
    }

    /**
     * @param 描述
     *            the describe to set
     */
    public void setDescribe(String describe) {
        this.describe = describe;
    }

}
