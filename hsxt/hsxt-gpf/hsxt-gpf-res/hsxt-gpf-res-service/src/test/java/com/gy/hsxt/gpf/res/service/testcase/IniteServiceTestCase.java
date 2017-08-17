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

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.gpf.gcs.api.IGCSRouteRuleService;
import com.gy.hsxt.gpf.res.api.IRESInitService;
import com.gy.hsxt.gpf.res.bean.MentInfo;
import com.gy.hsxt.gpf.res.bean.PlatInfo;
import com.gy.hsxt.gpf.res.bean.PlatMent;

@FixMethodOrder(MethodSorters.DEFAULT)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test/spring-global.xml")
// @TransactionConfiguration(transactionManager = "transactionManager",
// defaultRollback = false)
// @Transactional
public class IniteServiceTestCase {

    @Autowired
    private IRESInitService initService;

    @Autowired
    private IGCSRouteRuleService iGCSRouteRuleService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void queryUnInitPlatInfo() {
        System.out.println(initService.queryUnInitPlatInfo(new Page(1)));
    }

    @Test
    public void addPlatInfo() {
        PlatInfo plat = new PlatInfo();
        plat.setPlatNo("156");
        // plat.setEntResNo("");
        plat.setEntCustName("中国大陆");
        plat.setEmailA("zhangl@gyist.com");
        plat.setEmailB("zhangl@gyist.com");
        plat.setCreatedOptId("123");
        plat.setCreatedOptName("张三");

        initService.addPlatInfo(plat);
    }

    @Test
    public void queryPlatInfo() {
        System.out.println(initService.queryPlatInfo("156", "中国大陆", new Page(1)));
    }

    @Test
    public void saveMentInfo() {
        // MentInfo mentInfo = new MentInfo();
        // mentInfo.setEntResNo("06000000000");
        // mentInfo.setEntCustName("生六管理公司X");
        // mentInfo.setEmail("sheng6@6.com");
        // mentInfo.setReqOptId("123");
        // mentInfo.setReqOptName("李四");
        // initService.addMentInfo(mentInfo);

        MentInfo mentInfo = new MentInfo();
        mentInfo.setEntResNo("08000000000");
        mentInfo.setEntCustName("生八管理公司");
        mentInfo.setEmail("xiaofl@gyist.com");
        mentInfo.setCreatedOptId("123");
        mentInfo.setCreatedOptName("Tony");
        initService.addMentInfo(mentInfo);
    }

    @Test
    public void queryMentInfo() {
        System.out.println(initService.queryMentInfo(null, null, new Page(1)));
    }

    @Test
    public void addPlatMent() {
        PlatMent platMent = new PlatMent();
        platMent.setPlatNo("156");
        platMent.setEntResNo("08000000000");
        platMent.setCreatedOptId("123");
        platMent.setCreatedOptName("Tony");
        initService.addPlatMent(platMent);
    }

    @Test
    public void queryPlatMent() {
        System.out.println(initService.queryPlatMent("156", "06000000000"));
    }

    @Test
    public void syncPlatInfo() {
        initService.syncPlatInfo("156");
    }

    @Test
    public void syncPlatMent() {
        initService.syncPlatMent("156", "08000000000");
    }

    @Test
    public void iGCSRouteRuleService() {
        System.out.println(iGCSRouteRuleService.findRegionPlats());
    }

}
