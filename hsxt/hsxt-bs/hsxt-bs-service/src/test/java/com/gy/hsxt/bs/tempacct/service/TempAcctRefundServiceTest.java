package com.gy.hsxt.bs.tempacct.service;

import com.gy.hsxt.bs.bean.tempacct.TempAcctRefund;
import com.gy.hsxt.bs.common.enumtype.tempacct.LinkStatus;
import com.gy.hsxt.bs.tempacct.interfaces.ITempAcctRefundService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * @Package :com.gy.hsxt.bs.tempacct.service
 * @ClassName : TempAcctRefundServiceTest
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/12/23 9:52
 * @Version V3.0.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test/spring-global.xml")
public class TempAcctRefundServiceTest {

    @Resource
    private ITempAcctRefundService tempAcctRefundService;

    @Test
    public void testSaveBean() throws Exception {
        TempAcctRefund tempAcctRefund = new TempAcctRefund();

        tempAcctRefund.setDebitId("110120160116120819000000");
        tempAcctRefund.setReqOperator("system-test");
        tempAcctRefund.setRefundAmount("123.000000");
        tempAcctRefundService.saveBean(tempAcctRefund);
    }

    @Test
    public void testModifyBean() throws Exception {
        TempAcctRefund tempAcctRefund = new TempAcctRefund();

        tempAcctRefund.setDebitId("110120160116120819000000");
        tempAcctRefund.setApprOperator("00000000156000220160105");
        tempAcctRefund.setApprRemark("system appr");
        tempAcctRefund.setStatus(LinkStatus.PASS.ordinal());

        tempAcctRefundService.modifyBean(tempAcctRefund);

    }
}