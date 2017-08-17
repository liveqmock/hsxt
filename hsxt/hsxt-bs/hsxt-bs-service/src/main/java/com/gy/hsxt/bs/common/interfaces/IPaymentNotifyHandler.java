/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.common.interfaces;

import com.gy.hsxt.bs.common.bean.PaymentNotify;
import com.gy.hsxt.common.exception.HsException;

/**
 * 支付结果业务处理接口类
 *
 * @Package :com.gy.hsxt.bs.common.interfaces
 * @ClassName : IPaymentNotifyHandler
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/11/16 18:35
 * @Version V3.0.0.0
 */
public interface IPaymentNotifyHandler {

    /**
     * 处理支付结果方法
     *
     * @param paymentNotify 支付结果实体
     * @return true or false
     * @throws HsException 异常
     */
    boolean handle(PaymentNotify paymentNotify) throws HsException;
}
