/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.enums;

/**
 * 角色类型
 *
 * @Package : com.gy.hsxt.gpf.um.enums
 * @ClassName : RoleType
 * @Description : 角色类型
 * @Author : chenm
 * @Date : 2016/2/2 11:12
 * @Version V3.0.0.0
 */
public enum RoleType {

    /**
     * 普通类型
     */
    NORMAL,

    /**
     * 管理员类型
     */
    ADMIN;

    /**
     * 校验角色类型
     *
     * @param ordinal 值
     * @return boolean
     */
    public static boolean checkType(int ordinal) {
        for (RoleType type : RoleType.values()) {
            if (type.ordinal() == ordinal) {
                return true;
            }
        }
        return false;
    }
}
