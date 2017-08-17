/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.ao.testcase;

import java.util.Random;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * 公共测试用例
 * 
 * @Package: com.gy.hsxt.ao.testcase
 * @ClassName: BaseTest
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-12-2 下午12:17:22
 * @version V1.0
 */
@FixMethodOrder(MethodSorters.DEFAULT)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test/spring-global.xml")
public class BaseTest {

    String hsResNo = "";

    String custId = "";

    String custName = "";

    String optCustId;

    String optCustName;

    String bankCardNo;

    @Before
    public void setUp() throws Exception {
        hsResNo = "0" + new Random().nextInt(9) + "" + new Random().nextInt(9) + "" + new Random().nextInt(9) + ""
                + new Random().nextInt(9) + "0" + new Random().nextInt(9) + "" + new Random().nextInt(9) + ""
                + new Random().nextInt(9) + "" + new Random().nextInt(9) + "" + new Random().nextInt(9);
    }

    public String getBankCardNo() {
        return "6221558812340000";
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getCustName() {
        return "我叫互生系统";
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getOptCustName() {
        return "gyhs";
    }

    public void setOptCustName(String optCustName) {
        this.optCustName = optCustName;
    }

    public String getOptCustId() {
        return getCustId();
    }

    public void setOptCustId(String optCustId) {
        this.optCustId = optCustId;
    }

    public String getHsResNo() {
        return "06186010006";
    }

    public void setHsResNo(String hsResNo) {
        this.hsResNo = hsResNo;
    }

    public String getCustId() {
        return "0618601000620151130";
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

}
