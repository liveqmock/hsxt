/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.common.enumtype.invoice;

/**
 * 邮寄状态
 *
 * @Package :com.gy.hsxt.bs.common.enumtype.invoice
 * @ClassName : PostStatus
 * @Description :
 * <p>邮寄状态： 0-待配送 1-待签收/已配送 2-已签收</p>
 * @Author : chenm
 * @Date : 2015/12/16 10:51
 * @Version V3.0.0.0
 */
public enum PostStatus {

    /**
     * 待配送
     */
    POSTING,
    /**
     * 待签收
     */
    RECEIVING,
    /**
     * 已签收
     */
    RECEIVED;

    /**
     * 校验状态
     *
     * @param ordinal position
     * @return boolean
     */
    public static boolean check(int ordinal) {
        for (PostStatus postStatus : PostStatus.values()) {
            if (postStatus.ordinal() == ordinal) {
                return true;
            }
        }
        return false;
    }
}
