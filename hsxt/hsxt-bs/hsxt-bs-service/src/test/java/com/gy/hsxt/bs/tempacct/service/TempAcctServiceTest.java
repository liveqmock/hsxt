package com.gy.hsxt.bs.tempacct.service;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.bs.bean.tempacct.Debit;
import com.gy.hsxt.bs.bean.tempacct.DebitQuery;
import com.gy.hsxt.bs.bean.tempacct.DebitSum;
import com.gy.hsxt.bs.bean.tempacct.TempOrderQuery;
import com.gy.hsxt.bs.common.enumtype.tempacct.DebitStatus;
import com.gy.hsxt.bs.common.enumtype.tempacct.PayType;
import com.gy.hsxt.bs.tempacct.interfaces.ITempAcctService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package :com.gy.hsxt.bs.tempacct.service
 * @ClassName : TempAcctServiceTest
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/12/19 18:26
 * @Version V3.0.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test/spring-global.xml")
public class TempAcctServiceTest {

    @Resource
    private ITempAcctService tempAcctService;

    @Test
    public void testCreateDebit() throws Exception {
        Debit debit = new Debit();
        debit.setPayAmount("5000000");
        debit.setPayTime("2016-01-28");
        debit.setPayType(PayType.CASH.getCode());
        debit.setPayEntCustName("深圳市百乐集团");
        debit.setPayBankName("中国银行");
        debit.setPayBankNo("2346858452852285");
        debit.setPayUse("1|2|3");
        debit.setUseEntCustName("06104000000");
        debit.setAccountInfoId("110120151225110437000000");
        debit.setAccountReceiveTime("2016-01-28");
        debit.setCreatedBy("00000000156000520160107");
        debit.setCreatedName("system-test");

        tempAcctService.createDebit(debit);

    }

    @Test
    public void testModifyDebit() throws Exception {

        String json = "{\"accountInfoId\":\"110120151214062619000000\",\"accountReceiveTime\":\"2015-08-27 00:00:00\",\"createdby\":\"00000000156163438271051776\",\"debitId\":\"110120151219161855000000\",\"debitStatus\":0,\"description\":\"归一系统使用年费\",\"isPlatformFilling\":0,\"payAmount\":\"665.3\",\"payBankName\":\"中国银行\",\"payBankNo\":\"65897\",\"payEntCustName\":\"归一\",\"payTime\":\"2015-12-19 00:00:00\",\"payType\":2,\"payUse\":\"3\",\"updatedby\":\"00000000156163438271051776\",\"useEntCustName\":\"归一互生\"}";

        Debit debit = JSON.parseObject(json, Debit.class);

        tempAcctService.modifyDebit(debit);

    }

    @Test
    public void testQueryDebitListPage() throws Exception {
        Page page = new Page(0, 10);
        DebitQuery query = new DebitQuery();
        query.setDebitStatus(DebitStatus.LINKABLE.ordinal());
        PageData<Debit> pageData = tempAcctService.queryDebitListPage(page, query);
        System.out.println(pageData);

    }

    @Test
    public void testGetDebitBean() throws Exception {

    }

    @Test
    public void testApproveDebit() throws Exception {

        Debit debit = new Debit();
        debit.setDebitId("110120160219091853000000");
        debit.setDebitStatus(DebitStatus.LINKABLE.ordinal());
        debit.setUpdatedBy("00000000156000220160204");
        debit.setUpdatedName("0002-所有菜单权限");
        debit.setAuditRecord("审核通过");

        tempAcctService.approveDebit(debit);
    }

    @Test
    public void testQueryDebitCountListPage() throws Exception {

    }

    @Test
    public void testQueryDebitCountDetailListPage() throws Exception {

    }

    @Test
    public void testExportDebitData() throws Exception {

    }

    @Test
    public void testTurnNotMovePayment() throws Exception {

    }

    @Test
    public void testQueryTempTaskListPage() throws Exception {
        Page page = new Page(0, 10);
        TempOrderQuery query = new TempOrderQuery();
        query.setOptCustId("00000000156163438271051776");
//        query.setHsResNo("06010160000");
        PageData<Order> pageData = tempAcctService.queryTempTaskListPage(page, query);
        System.out.println(pageData);
    }

    @Test
    public void testQueryDebitTaskListPage() throws Exception {
        Page page = new Page(0, 10);
        DebitQuery query = new DebitQuery();
        query.setDebitStatus(DebitStatus.REFUNDING.ordinal());
        query.setOptCustId("00000000156000220160105");
        PageData<Debit> pageData = tempAcctService.queryDebitTaskListPage(page, query);
        System.out.println(pageData);
    }

    @Test
    public void testQueryTempOrderListPage() throws Exception {

    }

    @Test
    public void testQueryDebitSumListPage() throws Exception {
        Page page = new Page(0, 10);
        DebitQuery query = new DebitQuery();
//        query.setAccountName("110120151221093507000000");
        PageData<DebitSum> pageData = tempAcctService.queryDebitSumListPage(page, query);
        System.out.println(pageData);
    }

    @Test
    public void testQueryDebitDetail() throws Exception {

        Debit debit = tempAcctService.queryDebitDetail("110120160116120819000000");
        System.out.println(debit);

    }

    @Test
    public void testQueryDebitListByQuery() throws Exception {
        DebitQuery query = new DebitQuery();
        query.setUseEntCustName("06001020000");
        List<Debit> debitList = tempAcctService.queryDebitListByQuery(query);
        System.out.println(debitList);
    }
}