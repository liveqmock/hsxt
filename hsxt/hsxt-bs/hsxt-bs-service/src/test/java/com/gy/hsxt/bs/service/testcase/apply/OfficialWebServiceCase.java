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

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.bs.api.apply.IBSOfficialWebService;
import com.gy.hsxt.bs.bean.tool.CardProvideApply;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;

@FixMethodOrder(MethodSorters.DEFAULT)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-global.xml")
// @TransactionConfiguration(transactionManager = "transactionManager",
// defaultRollback = false)
// @Transactional
public class OfficialWebServiceCase {

    @Autowired
    IBSOfficialWebService iBSOfficialWebService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void queryPlacardEnt() {
        System.out.println(iBSOfficialWebService.queryPlacardEnt());
    }

    @Test
    public void queryEntInfo() {
        System.out.println(iBSOfficialWebService.queryEntInfo("托管企业-创业"));
    }

    @Test
    public void queryEntInfoByAuthCode() {
        System.out.println(iBSOfficialWebService.queryEntInfoByAuthCode("110120160105192420000000"));
    }

    @Test
    public void queryContractContent() {
        System.out.println(iBSOfficialWebService.queryContractContent("XX4NX2T0MJ", "06023000000"));
    }

    @Test
    public void querySaleCre() {
        System.out.println(iBSOfficialWebService.querySaleCre("KLBE62LAO", "060100200000"));
    }

    @Test
    public void queryBizCre() {
        System.out.println(iBSOfficialWebService.queryBizCre("XY8P66WDS0", "06013000000"));
    }


    @Test
    public void queryCardProvideApplyByPage()
    {
        PageData<CardProvideApply> data = iBSOfficialWebService.queryCardProvideApplyByPage(null, null,new Page(1, 10));
        System.out.println("================" + data.getCount());
        System.out.println(JSON.toJSONString(data.getResult()));
    }
}
