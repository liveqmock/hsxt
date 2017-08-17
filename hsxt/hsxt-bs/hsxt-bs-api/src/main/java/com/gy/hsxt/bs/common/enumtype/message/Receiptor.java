/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO.,
 * LTD. All rights reserved.
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.common.enumtype.message;

import com.gy.hsxt.common.utils.HsResNoUtils;

/**
 * @Package : com.gy.hsxt.bs.common.enumtype.message
 * @ClassName : Receiptor
 * @Description : 可查看公告消息的人群
 * <p/>
 * 0： 关联的全体客户组合(管理公司、服务公司、托管企业、成员企业、消费者)
 * <p/>
 * 1：全体下级管理公司 2：全体下级服务公司 3：全体下级托管企业 4：全体下级成员企业 5：全体下级消费者
 * <p/>
 * 6：全体下级服务公司 7：全体下级托管企业 8：全体下级成员企业 9：全体下级企业(托管、成员)
 * <p/>
 * 私信：（私信可以输入：接收者输入互生号，可输入多个）
 * @Author : liuhq
 * @Date : 2015-9-16 上午10:49:59
 * @Version V3.0
 */
public enum Receiptor {
    /**
     * (平台）关联的全体客户组合(管理公司、服务公司、托管企业、成员企业、消费者)
     */
    ALL,
    /**
     * (平台）全体下级管理公司
     */
    ALL_M,
    /**
     * (平台） 全体下级服务公司
     */
    ALL_S,
    /**
     * (平台）全体下级托管企业
     */
    ALL_T,
    /**
     * (平台）全体下级成员企业
     */
    ALL_B,
    /**
     * (平台）全体下级消费者
     */
    ALL_P,
    /**
     * （管理公司）全体下级服务公司
     */
    M_ALL_S,
    /**
     * （服务公司）全体下级托管企业
     */
    S_ALL_T,
    /**
     * （服务公司）全体下级成员企业
     */
    S_ALL_B,
    /**
     * （服务公司） 全体下级企业(托管、成员)
     */
    S_ALL;

    /**
     * 校验接收者类型
     *
     * @param name 名称
     * @return boolean
     */
    public static boolean checkName(String name) {
        for (Receiptor receiptor : Receiptor.values()) {
            if (receiptor.name().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 校验消息类型的归属
     *
     * @param name  名称
     * @param resNo 互生号
     * @return boolean
     */
    public static boolean checkBelong(String name, String resNo) {
        if (HsResNoUtils.isAreaPlatResNo(resNo)) {
            return name.startsWith(ALL.name());
        } else if (HsResNoUtils.isServiceResNo(resNo)) {
            return name.startsWith(S_ALL.name());
        } else {
            return name.equals(M_ALL_S.name());
        }
    }
}
