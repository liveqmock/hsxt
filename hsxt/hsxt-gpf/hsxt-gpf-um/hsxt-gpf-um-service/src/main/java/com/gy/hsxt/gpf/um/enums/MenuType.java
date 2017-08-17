/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.enums;

/**
 * @Package : com.gy.hsxt.gpf.um.enums
 * @ClassName : MenuType
 * @Description : TODO
 * @Author : chenm
 * @Date : 2016/2/1 11:10
 * @Version V3.0.0.0
 */
public enum MenuType {

    /**
     * 菜单组
     */
    BRANCH,

    /**
     * 菜单项
     */
    LEAF;

    /**
     * 校验菜单类型
     *
     * @param ordinal 值
     * @return boolean
     */
    public static boolean checkType(int ordinal) {
        for (MenuType type : MenuType.values()) {
            if (type.ordinal() == ordinal) {
                return true;
            }
        }
        return false;
    }

}
