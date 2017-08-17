/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.annualfee.interfaces;

import com.gy.hsxt.bs.api.annualfee.IBSAnnualFeeService;
import com.gy.hsxt.bs.common.bean.PaymentNotify;
import com.gy.hsxt.common.exception.HsException;

/**
 * @Package :com.gy.hsxt.bs.annualfee.interfaces
 * @ClassName : IAnnualFeeService
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/12/9 15:52
 * @Version V3.0.0.0
 */
public interface IAnnualFeeService extends IBSAnnualFeeService {

    /**
     * 年费支付回调
     *
     * @param paymentNotify 支付通知
     * @return false or true
     * @throws HsException
     */
    boolean callbackForPayment(PaymentNotify paymentNotify) throws HsException;
}
