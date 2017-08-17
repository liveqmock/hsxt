/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO.,
 * LTD. All rights reserved.
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.common.enumtype.message;

/**
 * 消息发送状态 枚举类
 *
 * @Package : com.gy.hsxt.bs.common.enumtype.message
 * @ClassName : MessageStatus
 * @Description : 消息发送状态
 * @Author : liuhq
 * @Date : 2015-11-9 下午5:49:25
 * @Version V3.0
 */
public enum MessageStatus {
    /**
     * 未发送
     */
    NO(1),

    /**
     * 已发送
     */
    YES(2);

    private int code;

    MessageStatus(int code) {
        this.code = code;
    }


    public int getCode() {
        return code;
    }

    /**
     * 校验消息状态
     *
     * @param code 状态代码
     * @return boolean
     */
    public static boolean check(int code) {
        for (MessageStatus status : MessageStatus.values()) {
            if (status.getCode() == code) {
                return true;
            }
        }
        return false;
    }
}
