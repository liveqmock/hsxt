/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype.tempacct;

/**
 *
 *
 * @Package: com.gy.hsxt.bs.common.enumtype.tempacct
 * @ClassName: ReceiveAccountType
 * @Description: 收款账户类型 枚举类
 *
 *               1：收款账户 2：节余不动款账户
 *
 * @author: liuhq
 * @date: 2015-9-8 上午9:22:06
 * @version V1.0
 */
public enum ReceiveAccountType {
    /**
     * 收款账户
     */
    RECEIVE(1),
    /**
     * 节余不动款账户
     */
    NOMOVE(2);
    private int code;

    public int getCode() {
        return code;
    }

    ReceiveAccountType(int code) {
        this.code = code;
    }

    /**
     * 临账收款账户类型校验
     *
     * @param code 类型代码
     * @return boolean
     */
    public static boolean checkType(int code) {
        for (ReceiveAccountType type : ReceiveAccountType.values()) {
            if (type.getCode() == code) {
                return true;
            }
        }
        return false;
    }
}
