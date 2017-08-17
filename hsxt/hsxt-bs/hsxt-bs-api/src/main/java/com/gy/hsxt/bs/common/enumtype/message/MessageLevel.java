/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype.message;

/**
 * @version V1.0
 * @Package: com.gy.hsxt.bs.common.enumtype.message
 * @ClassName: MessageLevel
 * @Description: 消息级别 枚举类
 * <p/>
 * 1一般，2重要
 * @author: liuhq
 * @date: 2015-9-16 上午10:43:32
 */
public enum MessageLevel {
    /**
     * 一般
     */
    GENERAL(1),
    /**
     * 重要
     */
    IMPORTANT(2);

    private int code;

    MessageLevel(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    /**
     * 校验级别代码
     *
     * @param code 级别代码
     * @return boolean
     */
    public static boolean checkLevel(int code) {
        for (MessageLevel level : MessageLevel.values()) {
            if (level.getCode() == code) {
                return true;
            }
        }
        return false;
    }
}
