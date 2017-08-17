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

import com.gy.hsxt.bs.api.entstatus.IBSMemberQuitService;
import com.gy.hsxt.bs.bean.base.ApprParam;
import com.gy.hsxt.bs.bean.entstatus.MemberQuit;
import com.gy.hsxt.bs.bean.entstatus.MemberQuitApprParam;
import com.gy.hsxt.bs.bean.entstatus.MemberQuitQueryParam;
import com.gy.hsxt.common.bean.Page;

@FixMethodOrder(MethodSorters.DEFAULT)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test/spring-global.xml")
// @TransactionConfiguration(transactionManager = "transactionManager",
// defaultRollback = false)
// @Transactional
public class MemberQuitServiceTestCase {

    @Autowired
    IBSMemberQuitService memberQuitService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void createMemberQuit() {
        MemberQuit memberQuit = new MemberQuit();
        memberQuit.setApplyType(1);
        memberQuit.setEntCustId("321132132132");
        memberQuit.setEntResNo("85123120000");
        memberQuit.setEntCustName("生一");
        memberQuit.setOldStatus(2);
        memberQuit.setBankAcctId("888888888888888");
        memberQuit.setCreatedBy("Jack");
        memberQuitService.createMemberQuit(memberQuit);

    }

    @Test
    public void serviceQueryMemberQuit() {
        Page page = new Page(1);
        MemberQuitQueryParam memberQuitQueryParam = new MemberQuitQueryParam();
        memberQuitQueryParam.setSerResNo("06001000000");
        // memberQuitQueryParam.setEntName("生");
        memberQuitQueryParam.setEntResNo("06001");
        memberQuitQueryParam.setLinkman("百里飘");
        // memberQuitQueryParam.setStatus(0);

        System.out.println(memberQuitService.serviceQueryMemberQuit(memberQuitQueryParam, page));
    }

    @Test
    public void platQueryMemberQuit() {
        Page page = new Page(1);
        MemberQuitQueryParam memberQuitQueryParam = new MemberQuitQueryParam();
        memberQuitQueryParam.setSerResNo("06001000000");
        // memberQuitQueryParam.setEntName("生");
        memberQuitQueryParam.setEntResNo("06001");
        memberQuitQueryParam.setLinkman("百里飘");
        // memberQuitQueryParam.setStatus(0);

        System.out.println(memberQuitService.platQueryMemberQuit(memberQuitQueryParam, page));
    }

    @Test
    public void platQueryMemberQuit4Appr() {
        Page page = new Page(1);
        MemberQuitQueryParam memberQuitQueryParam = new MemberQuitQueryParam();
        // memberQuitQueryParam.setSerResNo("06001000000");
        // memberQuitQueryParam.setEntName("生");
        // memberQuitQueryParam.setEntResNo("06001");
        // memberQuitQueryParam.setLinkman("百里飘");
        memberQuitQueryParam.setOptCustId("1231321321231");
        // memberQuitQueryParam.setStatus(0);

        System.out.println(memberQuitService.platQueryMemberQuit4Appr(memberQuitQueryParam, page));
    }

    @Test
    public void queryMemberQuitById() {
        System.out.println(memberQuitService.queryMemberQuitById("110120160225175256000000"));
    }

    @Test
    public void serviceApprMemberQuit() {
        MemberQuitApprParam apprParam = new MemberQuitApprParam();
        apprParam.setApplyId("110120151104020122000000");
        apprParam.setOptCustId("13212231121233");
        apprParam.setOptName("张三");
        apprParam.setOptEntName("归一");
        apprParam.setReportFile("11111111111111");
        apprParam.setStatementFile("2222222222222");
        apprParam.setIsPass(true);
        apprParam.setApprRemark("服务公司审批");
        memberQuitService.serviceApprMemberQuit(apprParam);
    }

    @Test
    public void platReviewApprMemberQuit() {
        ApprParam apprParam = new ApprParam();
        apprParam.setApplyId("110120151104020122000000");
        apprParam.setOptCustId("13212231121233");
        apprParam.setOptName("张三");
        apprParam.setOptEntName("归一");
        apprParam.setIsPass(true);
        apprParam.setApprRemark("平台审批");
        memberQuitService.platApprMemberQuit(apprParam);
    }

    @Test
    public void queryMemberQuitStatus() {
        System.out.println(memberQuitService.queryMemberQuitStatus("0600100000620151231"));
    }

}
