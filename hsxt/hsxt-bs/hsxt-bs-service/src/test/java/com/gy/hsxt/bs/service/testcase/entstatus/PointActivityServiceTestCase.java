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

import com.gy.hsxt.bs.api.entstatus.IBSPointActivityService;
import com.gy.hsxt.bs.bean.base.ApprParam;
import com.gy.hsxt.bs.bean.entstatus.PointActivity;
import com.gy.hsxt.bs.bean.entstatus.PointActivityQueryParam;
import com.gy.hsxt.bs.common.enumtype.entstatus.PointAppType;
import com.gy.hsxt.common.bean.Page;

@FixMethodOrder(MethodSorters.DEFAULT)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test/spring-global.xml")
// @TransactionConfiguration(transactionManager = "transactionManager",
// defaultRollback = false)
// @Transactional
public class PointActivityServiceTestCase {

    @Autowired
    IBSPointActivityService pointActivityService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void createPointActivity() {
        PointActivity pointActivity = new PointActivity();
        pointActivity.setEntResNo("06001010000");
        pointActivity.setEntCustId("06001010000163521987431424");
        pointActivity.setEntCustName("生三 托管企业");

        pointActivity.setOldStatus(1);
        pointActivity.setApplyType(PointAppType.STOP_PONIT_ACITIVITY.getCode());
        pointActivity.setApplyReason("经营困难");
        pointActivity.setBizApplyFile("12321321313132");
        pointActivity.setCreatedBy("Jack");
        pointActivityService.createPointActivity(pointActivity);
    }

    @Test
    public void serviceQueryPointActivity() {
        Page page = new Page(1);
        PointActivityQueryParam pointActivityQueryParam = new PointActivityQueryParam();
        pointActivityQueryParam.setSerResNo("85123000000");
        pointActivityQueryParam.setApplyType(PointAppType.STOP_PONIT_ACITIVITY.getCode());
        pointActivityQueryParam.setEntName("一");
        pointActivityQueryParam.setStatus(0);
        System.out.println(pointActivityService.serviceQueryPointActivity(pointActivityQueryParam, page));
    }

    @Test
    public void platQueryPointActivity4Appr() {
        Page page = new Page(1);
        PointActivityQueryParam pointActivityQueryParam = new PointActivityQueryParam();
        pointActivityQueryParam.setSerResNo("85123000000");
        pointActivityQueryParam.setApplyType(PointAppType.STOP_PONIT_ACITIVITY.getCode());
        pointActivityQueryParam.setEntName("一");
        pointActivityQueryParam.setStatus(0);
        System.out.println(pointActivityService.platQueryPointActivity4Appr(pointActivityQueryParam, page));
    }

    @Test
    public void platQueryPointActivity() {
        Page page = new Page(1);
        PointActivityQueryParam pointActivityQueryParam = new PointActivityQueryParam();
        pointActivityQueryParam.setSerResNo("85123000000");
        pointActivityQueryParam.setApplyType(PointAppType.STOP_PONIT_ACITIVITY.getCode());
        pointActivityQueryParam.setEntName("一");
        pointActivityQueryParam.setStatus(0);
        System.out.println(pointActivityService.platQueryPointActivity(pointActivityQueryParam, page));
    }

    @Test
    public void queryPointActivityById() {
        System.out.println(pointActivityService.queryPointActivityById("110120151204041203000000"));
    }

    @Test
    public void serviceApprPointActivity() {
        ApprParam apprParam = new ApprParam();
        apprParam.setApplyId("110120160113145913000000");
        apprParam.setOptCustId("13212231121233");
        apprParam.setOptName("张三");
        apprParam.setOptEntName("归一");
        apprParam.setIsPass(true);
        apprParam.setApprRemark("服务公司审批");
        pointActivityService.serviceApprPointActivity(apprParam);
    }

    @Test
    public void platApprPointActivity() {
        ApprParam apprParam = new ApprParam();
        apprParam.setApplyId("110120160113145913000000");
        apprParam.setOptCustId("13212231121233");
        apprParam.setOptName("张三");
        apprParam.setOptEntName("归一");
        apprParam.setIsPass(true);
        apprParam.setApprRemark("平台审批");
        pointActivityService.platApprPointActivity(apprParam);
    }

    @Test
    public void platReviewPointActivity() {
        ApprParam apprParam = new ApprParam();
        apprParam.setApplyId("110120160113145913000000");
        apprParam.setOptCustId("13212231121233");
        apprParam.setOptName("张三");
        apprParam.setOptEntName("归一");
        apprParam.setIsPass(true);
        apprParam.setApprRemark("平台复核");
        pointActivityService.platReviewPointActivity(apprParam);
    }

    @Test
    public void queryPointActivityStatus() {
        System.out.println(pointActivityService.queryPointActivityStatus("0601011000020160109"));
    }
}
