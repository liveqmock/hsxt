/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.services.common;

import java.io.Serializable;

/***
 * url地址鉴权
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.common
 * @ClassName: URLAuth
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2016-1-8 下午8:05:59
 * @version V1.0
 */
public class ConstantURLAuth implements Serializable {
    /**
     * 订单关闭
     */
    public final static String RECEIV_MANAGE_CLOSE_ORDER = "receivManage/close_order";

    /**
     * 工单挂起
     */
    public final static String WORK_ORDER_SUSPEND = "receivManage/suspend";

    /**
     * 工单终止
     */
    public final static String WORK_ORDER_TERMINATION = "receivManage/termination";
}
