package com.gy.hsxt.bs.invoice.service;/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/

import com.gy.hsxt.bs.bean.invoice.*;
import com.gy.hsxt.bs.common.enumtype.invoice.BizType;
import com.gy.hsxt.bs.common.enumtype.invoice.InvoiceMaker;
import com.gy.hsxt.bs.common.enumtype.invoice.InvoiceStatus;
import com.gy.hsxt.bs.common.enumtype.invoice.InvoiceType;
import com.gy.hsxt.bs.invoice.interfaces.IInvoiceService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @Package : com.gy.hsxt.bs.invoice.service
 * @ClassName : InvoiceServiceTest
 * @Description : TODO
 * @Author : chenm
 * @Date : 2016/1/4 17:01
 * @Version V3.0.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test/spring-global.xml")
public class InvoiceServiceTest {

    @Resource
    private IInvoiceService invoiceService;

    @Test
    public void testQueryInvoiceSum() throws Exception {
        InvoicePoolQuery query = new InvoicePoolQuery();

        query.setCustId("0601000000020151231");
//        query.setInvoiceMaker(InvoiceMaker.CUST.ordinal());

        InvoiceSum sum = invoiceService.queryInvoiceSum(query);

        System.out.println(sum);
    }

    @Test
    public void testQueryInvoicePoolListForPage() throws Exception {
        Page page = new Page(0, 10);
        InvoicePoolQuery query = new InvoicePoolQuery();
        query.setInvoiceMaker(InvoiceMaker.PLAT.ordinal());
        query.setResNo("09186630000");
        PageData<InvoicePool> pageData = invoiceService.queryInvoicePoolListForPage(page, query);

        System.out.println(pageData);

    }

    @Test
    public void testQueryInvoiceListForPage() throws Exception {
        Page page = new Page(0, 10);
        InvoiceQuery query = new InvoiceQuery();
        query.setCustId("0601000000020151231");
        query.setInvoiceMaker(InvoiceMaker.CUST.ordinal());
//        query.setQueryType(2);
//        query.setHasPending(false);
        PageData<Invoice> pageData = invoiceService.queryInvoiceListForPage(page, query);
        System.out.println(pageData);
    }

    @Test
    public void testQueryInvoiceDetail() throws Exception {

        Invoice invoice = invoiceService.queryInvoiceDetail("110120160105195228000000", InvoiceMaker.CUST);

        System.out.println(invoice);
    }

    @Test
    public void testCustOpenInvoice() throws Exception {

    }

    @Test
    public void testCustApplyInvoice() throws Exception {
        InvoicePlat invoicePlat = new InvoicePlat();
        invoicePlat.setBizType(BizType.PC_TAKE_OUT.getBizCode());
        invoicePlat.setResNo("09186630000");
        invoicePlat.setCustId("09186630000162706727708672");
        invoicePlat.setCustName("深圳捷成信息技术有限公司");
        invoicePlat.setApplyOperator("system");
        invoicePlat.setAddress("深圳市福田区福华路27号财富大厦");
        invoicePlat.setAllAmount("9000");
        invoicePlat.setBankBranchName("中国银行");
        invoicePlat.setBankNo("458958558555885");
        invoicePlat.setOpenContent("外卖月租费");
        invoicePlat.setTaxpayerNo("654656512385");
        invoicePlat.setTelephone("13248854474");
        invoicePlat.setInvoiceType(InvoiceType.NORMAL.ordinal());

        invoiceService.custApplyInvoice(invoicePlat);
    }

    @Test
    public void testPlatApproveInvoice() throws Exception {

    }

    @Test
    public void testPlatOpenInvoice() throws Exception {

    }

    @Test
    public void testSignInvoice() throws Exception {

    }

    @Test
    public void testChangePostWay() throws Exception {

    }
}