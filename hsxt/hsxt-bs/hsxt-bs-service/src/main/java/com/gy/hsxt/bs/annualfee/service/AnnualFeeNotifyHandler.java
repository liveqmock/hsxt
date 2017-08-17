/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.annualfee.service;

import com.gy.hsxt.bs.annualfee.interfaces.IAnnualFeeService;
import com.gy.hsxt.bs.common.annotation.PaymentInfo;
import com.gy.hsxt.bs.common.bean.PaymentNotify;
import com.gy.hsxt.bs.common.enumtype.order.OrderType;
import com.gy.hsxt.bs.common.interfaces.IPaymentNotifyHandler;
import com.gy.hsxt.common.constant.PayChannel;
import com.gy.hsxt.common.exception.HsException;

import javax.annotation.Resource;

/**
 * 年费网银支付结果业务处理
 *
 * @Package :com.gy.hsxt.bs.annualfee.service
 * @ClassName : AnnualFeeNotifyHandler
 * @Description : 年费网银支付结果业务处理
 * @Author : chenm
 * @Date : 2015/11/16 19:52
 * @Version V3.0.0.0
 */
@PaymentInfo(payChannel = {PayChannel.E_BANK_PAY, PayChannel.QUICK_PAY, PayChannel.MOBILE_PAY, PayChannel.TRANSFER_REMITTANCE, PayChannel.CARD_PAY}, orderType = OrderType.ANNUAL_FEE)
public class AnnualFeeNotifyHandler implements IPaymentNotifyHandler {

    /**
     * 注入年费信息接口
     */
    @Resource
    private IAnnualFeeService annualFeeService;

    /**
     * 处理支付结果方法
     *
     * @param paymentNotify 支付结果实体
     * @return true or false
     * @throws HsException 异常
     */
    @Override
    public boolean handle(PaymentNotify paymentNotify) throws HsException {
        return annualFeeService.callbackForPayment(paymentNotify);
    }
}
