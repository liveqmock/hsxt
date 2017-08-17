/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.common.interfaces;

import com.gy.hsxt.bs.common.bean.PaymentNotify;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gp.api.IGPNotifyService;

/**
 * 网银支付回调业务层接口
 *
 * @Package :com.gy.hsxt.bs.common.interfaces
 * @ClassName : IPaymentNotifyService
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/11/16 17:26
 * @Version V3.0.0.0
 */
public interface IPaymentNotifyService extends IGPNotifyService {

    /**
     * 支付通知处理
     *
     * @param paymentNotify 支付通知实体
     * @return boolean
     * @throws HsException
     */
    boolean paymentNotifyHandle(PaymentNotify paymentNotify) throws HsException;
}
