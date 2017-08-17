/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.gpf.res.service.testcase;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.gpf.res.api.IRESQuotaService;
import com.gy.hsxt.gpf.res.bean.ApprParam;
import com.gy.hsxt.gpf.res.bean.QuotaApp;
import com.gy.hsxt.gpf.res.bean.QuotaAppParam;
import com.gy.hsxt.gpf.res.enumtype.AppType;
import com.gy.hsxt.gpf.res.enumtype.QuotaAppStatus;

@FixMethodOrder(MethodSorters.DEFAULT)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test/spring-global.xml")
// @TransactionConfiguration(transactionManager = "transactionManager",
// defaultRollback = false)
// @Transactional
public class QuotaServiceTestCase {

    @Autowired
    private IRESQuotaService quotaService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addQuotaApp() {
        // QuotaApp quotaApp = new QuotaApp();
        // quotaApp.setEntResNo("06000000000");
        // quotaApp.setEntCustName("生六管理公司");
        // quotaApp.setPlatNo("156");
        // quotaApp.setApplyType(AppType.FIRST.getType());
        // quotaApp.setApplyNum(10);
        // quotaApp.setApplyList("06001000000,06002000000,06003000000,06004000000,06005000000,06006000000,06007000000,06008000000,06009000000,06010000000");
        // quotaApp.setReqRemark("初始化配额");
        // quotaApp.setReqOptId("123");
        // quotaApp.setReqOperator("王五");
        // quotaService.addQuotaApp(quotaApp);

        // QuotaApp quotaApp = new QuotaApp();
        // quotaApp.setEntResNo("08000000000");
        // quotaApp.setEntCustName("生八管理公司");
        // quotaApp.setPlatNo("156");
        // quotaApp.setApplyType(AppType.FIRST.getType());
        // quotaApp.setApplyNum(10);
        // String applyList = "";
        // for (int i = 1; i < 10; i++)
        // {
        // applyList += "," + "0800" + i + "000000";
        // }
        // applyList += "," + "08010000000";
        // applyList = applyList.substring(1);
        // quotaApp.setApplyList(applyList);
        // quotaApp.setReqRemark("增加配额");
        // quotaApp.setReqOptId("123");
        // quotaApp.setReqOperator("王五");
        // quotaService.addQuotaApp(quotaApp);

        QuotaApp quotaApp = new QuotaApp();
        quotaApp.setEntResNo("04000000000");
        quotaApp.setEntCustName("生四管理公司");
        quotaApp.setPlatNo("156");
        quotaApp.setApplyType(AppType.ADD.getType());
        quotaApp.setApplyNum(5);
        String applyList = "";
        for (int i = 56; i < 61; i++)
        {
            applyList += "," + "040" + i + "000000";
        }
        // applyList += "," + "08010000000";
        applyList = applyList.substring(1);
        quotaApp.setApplyList(applyList);
        quotaApp.setReqRemark("增加配额");
        quotaApp.setReqOptId("123");
        quotaApp.setReqOptName("王五");
        // quotaService.addQuotaApp(quotaApp);
    }

    @Test
    public void queryQuotaApp() {
        QuotaAppParam param = new QuotaAppParam();
        param.setPlatNo("156");
        // param.setEntResNo("06000000000");
        param.setStatus(QuotaAppStatus.WAIT_TO_APPR.getCode());
        // System.out.println(quotaService.queryQuotaApp(param, new Page(1)));
    }

    @Test
    public void apprQuotaApp() {
        // ApprParam apprParam = new ApprParam();
        // apprParam.setApplyId("00020151210063731000000");
        // apprParam.setIsPass(true);
        // apprParam.setApprNum(10);
        // apprParam.setResNoList("06001000000,06002000000,06003000000,06004000000,06005000000,06006000000,06007000000,06008000000,06009000000,06010000000");
        // apprParam.setApprRemark("可以");
        // apprParam.setApprOptId("123456");
        // apprParam.setApprOptName("Jack");
        // quotaService.apprQuotaApp(apprParam);

        // ApprParam apprParam = new ApprParam();
        // apprParam.setApplyId("156110120151222160732000000");
        // apprParam.setIsPass(true);
        // apprParam.setApprNum(90);
        // String resNoList = "";
        // for (int i = 11; i < 100; i++)
        // {
        // resNoList += "," + "080" + i + "000000";
        // }
        // resNoList += "," + "08100000000";
        // resNoList = resNoList.substring(1);
        // apprParam.setResNoList(resNoList);
        // // apprParam
        // //
        // .setResNoList("06001000000,06002000000,06003000000,06004000000,06005000000,06006000000,06007000000,06008000000,06009000000,06010000000");
        // apprParam.setApprRemark("可以");
        // apprParam.setApprOptId("123456");
        // apprParam.setApprOptName("Jack");
        // quotaService.apprQuotaApp(apprParam);

        ApprParam apprParam = new ApprParam();
        apprParam.setApplyId("00020151222183237000000");
        apprParam.setIsPass(true);
        apprParam.setApprNum(5);
        String resNoList = "";
        for (int i = 56; i < 61; i++)
        {
            resNoList += "," + "040" + i + "000000";
        }
        resNoList = resNoList.substring(1);
        apprParam.setResNoList(resNoList);
        // apprParam
        // .setResNoList("06001000000,06002000000,06003000000,06004000000,06005000000,06006000000,06007000000,06008000000,06009000000,06010000000");
        apprParam.setApprRemark("可以");
        apprParam.setApprOptId("123456");
        apprParam.setApprOptName("Jack");
        // quotaService.apprQuotaApp(apprParam);
    }

    @Test
    public void queryStatQuotaOfMent() {
        // System.out.println(quotaService.queryStatQuotaOfMent(null, new
        // Page(1)));
    }

    @Test
    public void syncQuotaAllotData() {
        // quotaService.syncQuotaAllotData("156110120151222160732000000");
    }

    @Test
    public void syncRouteData() {
        // quotaService.syncRouteData("00020151222180421000000");
    }
}
