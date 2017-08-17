/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.common.enumtype.tempacct;

/**
 * 临账关联状态
 *
 * @Package :com.gy.hsxt.bs.common.enumtype.tempacct
 * @ClassName : LinkStatus
 * @Description : 临账关联状态 0：待复核 1：复核通过 2：复核驳回
 * @Author : chenm
 * @Date : 2015/12/21 18:40
 * @Version V3.0.0.0
 */
public enum LinkStatus {

    /**
     * 待复核
     */
    PENDING,
    /**
     * 复核通过
     */
    PASS,
    /**
     * 复核驳回
     */
    REJECT;

    /**
     * 检查状态
     *
     * @param code 状态代码
     * @return boolean
     */
    public static boolean checkStatus(int code) {
        for (LinkStatus status : LinkStatus.values()) {
            if (status.ordinal() == code) {
                return true;
            }
        }
        return false;
    }
}
