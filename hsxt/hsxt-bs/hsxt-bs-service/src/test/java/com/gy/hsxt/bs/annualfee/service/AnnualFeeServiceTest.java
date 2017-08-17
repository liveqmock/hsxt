package com.gy.hsxt.bs.annualfee.service;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.bs.annualfee.interfaces.IAnnualFeeService;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeeInfo;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeeOrder;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeeOrderQuery;
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.bs.common.enumtype.order.OrderChannel;
import com.gy.hsxt.bs.order.interfaces.IOrderService;
import com.gy.hsxt.bs.order.service.InvokeRemoteService;
import com.gy.hsxt.bs.order.service.OrderService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.PayChannel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * @Package :com.gy.hsxt.bs.annualfee.service
 * @ClassName : AnnualFeeServiceTest
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/12/11 20:56
 * @Version V3.0.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-global.xml")
public class AnnualFeeServiceTest {

    @Resource
    private IAnnualFeeService annualFeeService;

    @Test
    public void testQueryAnnualFeeInfo() throws Exception {
        AnnualFeeInfo annualFeeInfo = annualFeeService.queryAnnualFeeInfo("0601600000020160225");
        System.out.println(annualFeeInfo);
    }

    @Test
    public void testSubmitAnnualFeeOrder() throws Exception {
//        Order order = new Order();
//        order.setCustId("0609900000020160109");
//        order.setOrderHsbAmount("1000");
//        order.setOrderChannel(OrderChannel.WEB.getCode());
//        order.setOrderOperator("0000");
        String param = "{\"areaEndDate\":\"2017-03-09\",\"areaStartDate\":\"2016-03-10\",\"order\":{\"custId\":\"0605304000020160308\",\"orderChannel\":1,\"orderHsbAmount\":\"300.00\",\"orderOperator\":\"0000\"}}";
        AnnualFeeOrder annualFeeOrder = JSON.parseObject(param, AnnualFeeOrder.class);
        AnnualFeeOrder orderNo = annualFeeService.submitAnnualFeeOrder(annualFeeOrder);
        System.out.println(orderNo);
    }

    @Test
    public void testPayAnnualFeeOrder() throws Exception {
        Order order = new Order();
        order.setOrderNo("110120160116181530000000");
        order.setCustId("0609900000020160109");
        order.setOrderOperator("0000");
        order.setPayChannel(PayChannel.HS_COIN_PAY.getCode());
        AnnualFeeOrder annualFeeOrder = AnnualFeeOrder.bulid(order);
        String payUrl = annualFeeService.payAnnualFeeOrder(annualFeeOrder);
        System.out.println(payUrl);
    }

    @Test
    public void testQueryAnnualFeeOrder() throws Exception {
        AnnualFeeOrder annualFeeOrder = annualFeeService.queryAnnualFeeOrder("110120160512104443000000");
        System.out.println(annualFeeOrder);
    }

    @Test
    public void testQueryAnnualFeeOrderForPage() throws Exception {
        Page page = new Page(0,10);
        AnnualFeeOrderQuery query = new AnnualFeeOrderQuery();
//        query.setCustName("托管企业");
//        query.setStartDate("2015-12-17");
//        query.setEndDate("2015-12-17");
//        query.setResNo("06010000000");
        query.setCustId("0601000000020151231");
        PageData<AnnualFeeOrder> data = annualFeeService.queryAnnualFeeOrderForPage(page, query);
        System.out.println(data);

    }

    @Resource
    private OrderService orderService;

    @Resource
   private InvokeRemoteService invokeRemoteService;
    /**
     * 获取快捷支付URL
     */
    @Test
    public void testGetQuickPayUrl() {
        orderService.getQuickPaySmsCode("110120160115204727000000", "201503050109140001010000", "110");
        System.out.println("获取快捷支付URL："
                + invokeRemoteService.getQuickPayUrl("110120160115204727000000", "201503050109140001010000", "111111"));
    }
}