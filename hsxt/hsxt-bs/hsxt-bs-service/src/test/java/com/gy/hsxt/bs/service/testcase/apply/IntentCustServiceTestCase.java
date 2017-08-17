/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.service.testcase.apply;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.bs.api.apply.IBSIntentCustService;
import com.gy.hsxt.bs.bean.apply.IntentCust;
import com.gy.hsxt.bs.bean.apply.IntentCustBaseInfo;
import com.gy.hsxt.bs.bean.apply.IntentCustQueryParam;
import com.gy.hsxt.bs.common.enumtype.apply.EntType;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.CustType;

@FixMethodOrder(MethodSorters.DEFAULT)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test/spring-global.xml")
// @TransactionConfiguration(transactionManager = "transactionManager",
// defaultRollback = false)
// @Transactional
public class IntentCustServiceTestCase {

    @Autowired
    IBSIntentCustService intentCustService;

    IntentCust intentCust;

    @Before
    public void setUp() throws Exception {
        intentCust = new IntentCust();
        intentCust.setSerEntResNo("11111000000");
        intentCust.setSerEntCustId("111111");
        intentCust.setSerEntCustName("TEST");
        intentCust.setLicenseNo("TEST_LICENSE");
        intentCust.setEntCustName("TEST_ENT_NAME");
        intentCust.setAppType(CustType.MEMBER_ENT.getCode());
        intentCust.setEntType(EntType.PRIVATE_ENT.getCode());
        intentCust.setCountryNo("123");
        intentCust.setProvinceNo("456789");
        intentCust.setCityNo("1111111111");
        intentCust.setEntAddress("TEST ADDRESS");
        intentCust.setLinkman("TEST_LINKMAN");
        intentCust.setMobile("12345678900");
        intentCust.setOfficeTel("123456789");
        intentCust.setEmail("111@11");
        intentCust.setArea("100平方");
        intentCust.setStaffs(100);
        intentCust.setBizScope("TEST_BIZ_SCOPE");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void createIntentCust() {
        try
        {
            // intentCustService.createIntentCust(intentCust);
            // String applyId = StringUtil.getBsGuid();
            // System.out.println("=============applyId2="+applyId);
            // IntentCust intentCustDB =
            // intentCustService.queryIntentCustById(applyId);
            // Assert.notNull(intentCustDB);
            // Assert.isTrue("11111000000".equals(intentCustDB.getSerEntResNo()));
            // Assert.isTrue("111111".equals(intentCustDB.getSerEntCustId()));
            // Assert.isTrue("TEST".equals(intentCustDB.getSerEntCustName()));
            // Assert.isTrue("TEST_LICENSE".equals(intentCustDB.getLicenseNo()));
            // Assert.isTrue("TEST_ENT_NAME".equals(intentCustDB.getEntCustName()));
            // Assert.isTrue(CustType.MEMBER_ENT.getCode()==intentCustDB.getAppType());
            // Assert.isTrue(EntType.PRIVATE_ENT.getCode()==intentCustDB.getEntType());
            // Assert.isTrue("123".equals(intentCustDB.getCountryNo()));
            // Assert.isTrue("456789".equals(intentCustDB.getProvinceNo()));
            // Assert.isTrue("1111111111".equals(intentCustDB.getCityNo()));
            // Assert.isTrue("TEST ADDRESS".equals(intentCustDB.getEntAddress()));
            // Assert.isTrue("TEST_LINKMAN".equals(intentCustDB.getLinkman()));
            //
            // Assert.isTrue("12345678900".equals(intentCustDB.getMobile()));
            // Assert.isTrue("123456789".equals(intentCustDB.getOfficeTel()));
            // Assert.isTrue("111@11".equals(intentCustDB.getEmail()));
            // Assert.isTrue("100平方".equals(intentCustDB.getArea()));
            // Assert.isTrue(100==intentCustDB.getStaffs());
            // Assert.isTrue("TEST_BIZ_SCOPE".equals(intentCustDB.getBizScope()));

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Test
    public void apprIntentCust() {
        intentCustService.apprIntentCust("110120151211034544000000", "apprOperator", 2, "apprRemark");
    }

    @Test
    public void queryStatus() {
        System.out.println(intentCustService.queryStatus("110120151117095459000000", "TEST_LICENSE"));
    }

    @Test
    public void serviceEntQueryIntentCust() {
        // intentCustService.createIntentCust(intentCust);
        IntentCustQueryParam intentCustQueryParam = new IntentCustQueryParam();
        Page page = new Page(1);
        intentCustQueryParam.setSerEntResNo("06001010000");
        // intentCustQueryParam.setAppType(CustType.MEMBER_ENT.getCode());
        // intentCustQueryParam.setStatus(IntentCustStatus.WAIT_TO_HANDLE.getCode());
        // intentCustQueryParam.setEntCustName("TEST_ENT_NAME");
        intentCustQueryParam.setStartDate("2015-11-25");
        intentCustQueryParam.setEndDate("2015-12-18");
        PageData<IntentCustBaseInfo> pageList = intentCustService.serviceEntQueryIntentCust(intentCustQueryParam, page);
        System.out.println(pageList);
        // Assert.notNull(pageList);
        // Assert.isTrue(1==pageList.getCount());
        // Assert.isTrue(1==pageList.getResult().size());
        // Assert.isTrue("TEST_ENT_NAME".equals(pageList.getResult().get(0).getEntCustName()));
    }

    @Test
    public void platQueryIntentCust() {
        // intentCustService.createIntentCust(intentCust);
        // IntentCustQueryParam intentCustQueryParam = new
        // IntentCustQueryParam();
        Page page = new Page(1);
        // intentCustQueryParam.setAppType(CustType.MEMBER_ENT.getCode());
        // intentCustQueryParam.setStatus(IntentCustStatus.WAIT_TO_HANDLE.getCode());
        // intentCustQueryParam.setEntCustName("TEST_ENT_NAME");
        // intentCustQueryParam.setStartDate(DateUtil.DateToString(new Date()));
        // intentCustQueryParam.setEndDate(DateUtil.DateToString(new Date()));
        PageData<IntentCustBaseInfo> pageList = intentCustService.platQueryIntentCust(null, page);
        System.out.println(pageList);
        // Assert.notNull(pageList);
        // Assert.isTrue(1==pageList.getCount());
        // Assert.isTrue(1==pageList.getResult().size());
        // Assert.isTrue("TEST_ENT_NAME".equals(pageList.getResult().get(0).getEntCustName()));
    }

    @Test
    public void queryIntentCustById() {
        System.out.println(intentCustService.queryIntentCustById("110120151117095459000000"));
    }

}
