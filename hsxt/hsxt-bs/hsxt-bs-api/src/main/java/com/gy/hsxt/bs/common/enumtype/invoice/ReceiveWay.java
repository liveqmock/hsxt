/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.common.enumtype.invoice;

/**
 * 签收方式
 * @Package : com.gy.hsxt.bs.common.enumtype.invoice
 * @ClassName : ReceiveWay
 * @Description : 签收方式
 *  0-物流单号确认签收、1-电话确认签收、2-到期自动签收
 * @Author : chenm
 * @Date : 2015/12/31 11:16
 * @Version V3.0.0.0
 */
public enum ReceiveWay {

    /**
     * 物流单号确认签收
     */
    EXPRESS,
    /**
     * 电话确认签收
     */
    TELEPHONE,
    /**
     * 到期自动签收
     */
    AUTO;

    /**
     * 校验状态
     *
     * @param ordinal position
     * @return boolean
     */
    public static boolean check(int ordinal) {
        for (ReceiveWay type : ReceiveWay.values()) {
            if (type.ordinal() == ordinal) {
                return true;
            }
        }
        return false;
    }
}
