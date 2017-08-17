/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype.message;

/**
 * 消息类型
 *
 * @version V1.0
 * @Package: com.gy.hsxt.bs.common.enumtype.message
 * @ClassName: MessageType
 * @Description: 消息类型 枚举类
 * <p/>
 * 11公告 12通知 20 私信
 * @author: liuhq
 * @date: 2015-9-16 上午10:35:50
 */
public enum MessageType {
    /**
     * 公告
     */
    ANNOUNCEMENT(11),
    /**
     * 通知
     */
    NOTICE(12),
    /**
     * 私信
     */
    PRIVATE_LETTER(20);

    private int code;

    public int getCode() {
        return code;
    }

    MessageType(int code) {
        this.code = code;
    }

    /**
     * 校验消息类型
     *
     * @param code 类型代码
     * @return boolean
     */
    public static boolean checkType(int code) {
        for (MessageType type : MessageType.values()) {
            if (type.getCode() == code) {
                return true;
            }
        }
        return false;
    }
}
