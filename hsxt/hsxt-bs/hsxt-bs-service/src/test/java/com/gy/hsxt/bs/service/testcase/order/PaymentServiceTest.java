/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.service.testcase.order;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gy.hsxt.bs.api.order.IBSPaymentService;
import com.gy.hsxt.bs.bean.order.PayInfo;
import com.gy.hsxt.bs.service.testcase.BaseTest;
import com.gy.hsxt.common.constant.PayChannel;

public class PaymentServiceTest extends BaseTest {

    @Autowired
    IBSPaymentService paymentService;

    /**
     * 平安银行卡支付
     */
    @Test
    public void testGetPayUrl() {
        PayInfo payInfo = new PayInfo();
        payInfo.setTransNo("110120160615164033000000");// 交易流水号
        payInfo.setPayChannel(PayChannel.CARD_PAY.getCode());// 支付方式
        payInfo.setGoodsName("交易购买商品名称");// 交易购买商品名称
        payInfo.setTransDesc("交易描述");// 交易描述
        payInfo.setCallbackUrl("http://www.hsxt.com/aa/bb/cc/dd/xx");// 支付成功后，银行将会通过浏览器跳转到这个页面
        payInfo.setPrivObligate("私有数据");// 传递商户私有数据,用于回调时带回
        System.out.println(paymentService.getPayUrl(payInfo));
    }

    /**
     * 平安快捷绑定
     */
    @Test
    public void testGetQuickPayBindUrl() {
        System.out.println(paymentService.getQuickPayBindUrl(""));
    }

    /**
     * 
     */
    @Test
    public void testGetQuickPaySmsCode() {
        PayInfo payInfo = new PayInfo();
        payInfo.setTransNo("110120160615164033000000");// 交易流水号
        payInfo.setPayChannel(PayChannel.CARD_PAY.getCode());// 支付方式
        payInfo.setGoodsName("交易购买商品名称");// 交易购买商品名称
        payInfo.setTransDesc("交易描述");// 交易描述
        payInfo.setCallbackUrl("http://www.hsxt.com/aa/bb/cc/dd/xx");// 支付成功后，银行将会通过浏览器跳转到这个页面
        payInfo.setPrivObligate("私有数据");// 传递商户私有数据,用于回调时带回
        paymentService.getQuickPaySmsCode(payInfo);
    }

}
