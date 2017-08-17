/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.service.testcase.order;

import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gy.hsxt.bs.api.order.IBSOrderService;
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.bs.bean.order.OrderCustom;
import com.gy.hsxt.bs.bean.order.OrderQueryParam;
import com.gy.hsxt.bs.common.bean.PayUrl;
import com.gy.hsxt.bs.common.enumtype.order.OrderType;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.order.interfaces.IAccountRuleService;
import com.gy.hsxt.bs.order.interfaces.IOrderService;
import com.gy.hsxt.bs.service.testcase.BaseTest;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.constant.PayChannel;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;

/**
 * 业务订单单元测试类
 * 
 * @Package: com.gy.hsxt.bs.service.testcase.order
 * @ClassName: OrderServiceTest
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-10-12 下午2:18:07
 * @version V1.0
 */

public class OrderServiceTest extends BaseTest {
    /** 业务服务私有配置参数 **/
    @Autowired
    private BsConfig bsConfig;

    @Resource
    IBSOrderService orderService;

    @Resource
    IOrderService iOrderService;

    @Autowired
    IAccountRuleService accountRuleService;

    /**
     * 缓存验证
     */
    @Test
    public void testAccountRule() {
        // accountRuleService.setSysItemsValue("0600211172220151207");
        // BusinessCustParamItemsRedis bcpir =
        // businessParamSearch.searchCustParamItemsByIdKey(
        // "06000000000163439192367104",
        // BusinessParam.SINGLE_DAY_HAD_BUY_HSB.getCode());
        // System.out.println(bcpir);
        accountRuleService.checkHsbToPayRule("0600211811520151207", 1, "10");

    }

    // 测试保存订单数据（测试条件，需要开启配置平台服务）
    @Test
    public void testSaveOrder() {
        Order order = null;
        String resOrderNo = "";
        PayChannel[] pc = PayChannel.values();
        for (int i = 0; i < 1; i++)
        {
            order = new Order();
            order.setCustId(getCustId());
            order.setHsResNo(getHsResNo());
            order.setCustName("gy" + new Random().nextInt(100));

            String rand = String.valueOf(new Random().nextInt(10));
            //
            order.setCustType(CustType.SERVICE_CORP.getCode());
            order.setBizNo(new Random().nextInt(100) + "");
            order.setOrderType(OrderType.BUY_HSB.getCode());
            order.setIsProxy(false);
            order.setQuantity(new Random().nextInt(10000));
            order.setOrderUnit("个");
            order.setOrderOriginalAmount("999.88");
            order.setOrderDerateAmount("0.88");
            order.setOrderCashAmount("999");
            order.setCurrencyCode("RMB");
            order.setOrderHsbAmount("0");
            // 传正确的测试
            order.setExchangeRate("1");
            // 传错误的测试
            // order.setExchangeRate("111");
            order.setOrderRemark("兑换互生币");
            order.setDeliverId("");
            order.setOrderChannel(1);
            order.setOrderOperator("000" + new Random().nextInt(100));
            order.setPayOvertime(DateUtil.getCurrentDateTime());
            rand = String.valueOf(new Random().nextInt(5));
            if (!(rand.equals("0")))
            {
                rand = "1";
                order.setOrderStatus(Integer.parseInt(rand));
            }
            else
            {
                order.setOrderStatus(Integer.parseInt(rand));
            }
            order.setOrderStatus(1);
            order.setPayStatus(new Random().nextInt(2));

            order.setPayChannel(pc[new Random().nextInt(pc.length)].getCode());
            order.setPayTime(DateUtil.getCurrentDateTime());
            order.setIsNeedInvoice(1);
            order.setIsInvoiced(1);

            // resOrderNo = iOrderService.saveCommonOrder(order);
            orderService.saveOrder(order);
            System.out.println("返回订单号：" + resOrderNo + " 当前时间：" + new Date());
        }
    }

    @Test
    public void testGetOrderList() {
        OrderQueryParam orderQueryParam = new OrderQueryParam(); // Page page
        Page page = new Page(1);
        // orderQueryParam.setStartDate("2015-12-14");
        // orderQueryParam.setEndDate(DateUtil.getCurrentDateTime());
        // orderQueryParam.setOrderType(OrderType.ANNUAL_FEE.getCode());
        // orderQueryParam.setStatus(2);
        // orderQueryParam.setPayChannel(PayChannel.E_BANK_PAY.getCode());
        System.out.println(orderQueryParam);
        // orderQueryParam.setPayStatus(PayStatus.WAIT_PAY.getCode());
        orderQueryParam.setStartDate("2015-01-05");
        orderQueryParam.setEndDate("2016-02-05");
        // orderQueryParam.setHsCustId("75186630000162971967499264");
        page.setCurPage(1);
        page.setPageSize(10000);
        try
        {
            PageData<OrderCustom> orderList = orderService.getOrderList(orderQueryParam, page);

            List<OrderCustom> orders = orderList.getResult();
            for (OrderCustom o : orders)
            {
                System.out.println("订单号 = " + o);
            }
        }
        catch (HsException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void testGetOrder() throws HsException {
        OrderQueryParam orderQueryParam = new OrderQueryParam(); // Page page
        Page page = new Page(1);
        orderQueryParam.setStartDate("2015-10-01 00:00:00");
        orderQueryParam.setEndDate(DateUtil.getCurrentDateTime());

        PageData<OrderCustom> orderList = orderService.getOrderList(new OrderQueryParam(), page);

        List<OrderCustom> orders = orderList.getResult();
        for (OrderCustom o : orders)
        {
            OrderCustom orderCustom = orderService.getOrder(o.getOrderNo());
            System.out.println("订单详情：" + orderCustom);
        }
    }

    @Test
    public void testGetOrderByNo() throws HsException {
        Order orderCustom = iOrderService.getOrderByNo("110120160324174828000000");
        System.out.println("订单详情：" + orderCustom);
    }

    @Test
    public void testUpdateOrderStatus() {
    }

    /**
     * 获取网银支付URL
     */
    @Test
    public void testGetNetPayUrl() {
        Order order = null;
        order = iOrderService.getOrderByNo("110120151214050530000060");

        System.out.println("网银支付URL："
                + iOrderService.getPayUrl(PayUrl.bulid(order, PayChannel.E_BANK_PAY.getCode(), "http://www.baidu.com",
                        "201503050109140001010000", "111111")));
    }

    @Test
    public void testGetQuickPaySmsCode() {
        orderService.getQuickPaySmsCode("110120160112142947000000", "201503050109140001010000", "");
    }

    @Test
    public void testCloseOrder() {
        orderService.closeOrder("110120160301131933000000");
    }
}
