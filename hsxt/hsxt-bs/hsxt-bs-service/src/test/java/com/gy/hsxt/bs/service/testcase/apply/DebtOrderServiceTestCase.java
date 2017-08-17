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

import com.gy.hsxt.bs.api.apply.IBSDebtOrderService;
import com.gy.hsxt.bs.bean.apply.DebtOrderParam;
import com.gy.hsxt.common.bean.Page;

@FixMethodOrder(MethodSorters.DEFAULT)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test/spring-global.xml")
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
//@Transactional
public class DebtOrderServiceTestCase {

    @Autowired
    IBSDebtOrderService debtOrderService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void queryDebtOrder(){
        DebtOrderParam  debtOrderParam  = new DebtOrderParam ();
        debtOrderParam.setEntName("企业");
        debtOrderParam.setStartDate("2015-12-10");
        debtOrderParam.setEndDate("2015-12-16");
        debtOrderParam.setEntResNo("06001020000");
        System.out.println(debtOrderService.queryDebtOrder(debtOrderParam, new Page(1)));
    }
}
