/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.service.testcase.entstatus;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.bs.api.entstatus.IBSCloseOpenEntService;
import com.gy.hsxt.bs.bean.base.ApprParam;
import com.gy.hsxt.bs.bean.entstatus.CloseOpenEnt;
import com.gy.hsxt.bs.bean.entstatus.CloseOpenEntQueryParam;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.constant.CustType;

@FixMethodOrder(MethodSorters.DEFAULT)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test/spring-global.xml")
// @TransactionConfiguration(transactionManager = "transactionManager",
// defaultRollback = false)
// @Transactional
public class CloseOpenEntServiceTestCase {

    @Autowired
    IBSCloseOpenEntService closeOpenEntService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void closeEnt() {
        CloseOpenEnt closeOpenEnt = new CloseOpenEnt();
        closeOpenEnt.setEntCustId("0600105000020160108");
        closeOpenEnt.setEntResNo("06001050000");
        closeOpenEnt.setEntCustName("托管企业-创业-合同证书");
        closeOpenEnt.setCustType(CustType.TRUSTEESHIP_ENT.getCode());
        closeOpenEnt.setAddress("深圳福田");
        closeOpenEnt.setLinkman("张大山");
        closeOpenEnt.setMobile("13800001234");
        closeOpenEnt.setReqRemark("test");
        closeOpenEnt.setReqOptCustId("222222222222222");
        closeOpenEnt.setReqOptCustName("李四");
        closeOpenEntService.closeEnt(closeOpenEnt);
    }

    @Test
    public void openEnt() {
        CloseOpenEnt closeOpenEnt = new CloseOpenEnt();
        closeOpenEnt.setEntCustId("0600105000020160108");
        closeOpenEnt.setEntResNo("06001050000");
        closeOpenEnt.setEntCustName("托管企业-创业-合同证书");
        closeOpenEnt.setCustType(CustType.TRUSTEESHIP_ENT.getCode());
        closeOpenEnt.setAddress("深圳福田");
        closeOpenEnt.setLinkman("张大山");
        closeOpenEnt.setMobile("13800001234");
        closeOpenEnt.setReqRemark("test");
        closeOpenEnt.setReqOptCustId("222222222222222");
        closeOpenEnt.setReqOptCustName("李四");
        closeOpenEntService.openEnt(closeOpenEnt);
    }

    @Test
    public void queryCloseOpenEnt4Appr() {
        Page page = new Page(1);
        CloseOpenEntQueryParam closeOpenEntParam = new CloseOpenEntQueryParam();
        // closeOpenEntParam.setApplyType(CloseOpenEntType.CLOSE_SYS.getCode());
        // closeOpenEntParam.setCustType(CustType.SERVICE_CORP.getCode());
        // closeOpenEntParam.setEntCustName("生一");
        // closeOpenEntParam.setEntResNo("01");
        closeOpenEntParam.setLinkman("李");
        // // closeOpenEntParam.setOptCustId("222222222222222");
        System.out.println(closeOpenEntService.queryCloseOpenEnt4Appr(closeOpenEntParam, page));
    }

    @Test
    public void queryCloseOpenEntDetail() {
        System.out.println(closeOpenEntService.queryCloseOpenEntDetail("110120160121143841000000"));
    }

    @Test
    public void apprCloseOpenEnt() {
        ApprParam apprParam = new ApprParam();
        apprParam.setApplyId("110120160108154920000000");
        apprParam.setIsPass(true);
        apprParam.setApprRemark("test appr");
        apprParam.setOptCustId("333333333333333");
        apprParam.setOptName("王五");
        closeOpenEntService.apprCloseOpenEnt(apprParam);
    }

    @Test
    public void queryLastCloseOpenEntInfo() {
        System.out.println(closeOpenEntService.queryLastCloseOpenEntInfo("0600105000020160108", 1));
    }

    @Test
    public void queryCloseOpenEnt4ApprListPage() {
        CloseOpenEntQueryParam closeOpenEntParam = new CloseOpenEntQueryParam();
        closeOpenEntParam.setOptCustId("12313213");
        System.out.println(closeOpenEntService.queryCloseOpenEnt4Appr(closeOpenEntParam, new Page(1)));

    }

    @Test
    public void queryCloseOpenEnt() {
        CloseOpenEntQueryParam closeOpenEntParam = new CloseOpenEntQueryParam();
        // closeOpenEntParam.setOptCustId("12313213");
        closeOpenEntParam.setLinkman("李");
        closeOpenEntParam.setEntResNo("06");
        closeOpenEntParam.setEntCustName("服务");
        System.out.println(closeOpenEntService.queryCloseOpenEnt(closeOpenEntParam, new Page(1)));

    }
}
