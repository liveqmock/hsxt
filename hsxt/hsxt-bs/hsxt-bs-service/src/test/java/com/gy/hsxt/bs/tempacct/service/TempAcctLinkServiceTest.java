package com.gy.hsxt.bs.tempacct.service;

import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.bs.bean.tempacct.TempAcctLink;
import com.gy.hsxt.bs.bean.tempacct.TempAcctLinkDebit;
import com.gy.hsxt.bs.common.enumtype.tempacct.LinkStatus;
import com.gy.hsxt.bs.order.interfaces.IOrderService;
import com.gy.hsxt.bs.tempacct.interfaces.ITempAcctLinkService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @Package :com.gy.hsxt.bs.tempacct.service
 * @ClassName : TempAcctLinkServiceTest
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/12/22 18:40
 * @Version V3.0.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test/spring-global.xml")
public class TempAcctLinkServiceTest {

    @Resource
    private IOrderService orderService;

    @Resource
    private ITempAcctLinkService tempAcctLinkService;

    @Test
    public void testSaveBean() throws Exception {
        String orderNo = "110120160105152146000000";
        Order order = orderService.getOrderByNo(orderNo);

        //110120151222183134000000
        TempAcctLink tempAcctLink = new TempAcctLink();
        tempAcctLink.setOrderNo(orderNo);
//        tempAcctLink.setApprOperator("system");
        tempAcctLink.setCashAmount(order.getOrderCashAmount());
        tempAcctLink.setTotalLinkAmount("150.000000");
        tempAcctLink.setReqOperator("00000000156000220160105");
        tempAcctLink.setReqOperatorName("申请人名称");
        tempAcctLink.setReqRemark("交年度保护费");

        TempAcctLinkDebit linkDebit = new TempAcctLinkDebit();
        linkDebit.setDebitId("110120160219091853000000");
        linkDebit.setOrderNo(orderNo);

        List<TempAcctLinkDebit> linkDebits = new ArrayList<>();
        linkDebits.add(linkDebit);

        tempAcctLink.setTempAcctLinkDebits(linkDebits);


//        System.out.println(tempAcctLink);
        tempAcctLinkService.saveBean(tempAcctLink);

    }

    @Test
    public void testModifyBean() throws Exception {

        String orderNo = "110120160105152146000000";
        Order order = orderService.getOrderByNo(orderNo);

        //110120151222183134000000
        TempAcctLink tempAcctLink = new TempAcctLink();
        tempAcctLink.setApplyId("110120160219093712000000");
        tempAcctLink.setOrderNo(orderNo);
        tempAcctLink.setApprOperator("00000000156000220160204");
        tempAcctLink.setApprOperatorName("0002-所有菜单权限01");
        tempAcctLink.setApprRemark("复核通过");
        tempAcctLink.setCashAmount(order.getOrderCashAmount());
        tempAcctLink.setTotalLinkAmount("5000000.000000");
        tempAcctLink.setStatus(LinkStatus.PASS.ordinal());

        TempAcctLinkDebit linkDebit = new TempAcctLinkDebit();
        linkDebit.setDebitId("110120160219091853000000");
        linkDebit.setOrderNo(orderNo);
        linkDebit.setLinkAmount("150.000000");
        List<TempAcctLinkDebit> linkDebits = new ArrayList<>();
        linkDebits.add(linkDebit);

        tempAcctLink.setTempAcctLinkDebits(linkDebits);

        tempAcctLinkService.modifyBean(tempAcctLink);

    }

    @Test
    public void testQueryBeanById() throws Exception {
        String orderNo = "110120151222183134000000";
        TempAcctLink tempAcctLink = tempAcctLinkService.queryBeanByOrderNo(orderNo);
        System.out.println(tempAcctLink);
    }

    @Test
    public void testQueryListByQuery() throws Exception {

    }

    @Test
    public void testQueryListForPage() throws Exception {

    }

    @Test
    public void testQueryTempOrderListPage() throws Exception {

    }

    @Test
    public void testQueryTempOrderByDebitId() throws Exception {
        List<Order> orders = tempAcctLinkService.queryTempOrderByDebitId("110120151225115420000000");
        System.out.println(orders);
    }

    @Test
    public void testQueryBeanDetail() throws Exception {
        TempAcctLink tempAcctLink = tempAcctLinkService.queryBeanDetail("110120151215181752000000", "110120151225115420000000");
        System.out.println(tempAcctLink);
    }
}