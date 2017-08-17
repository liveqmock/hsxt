/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.service.testcase.order;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gy.hsxt.bs.order.service.InvestProfitTaskService;
import com.gy.hsxt.bs.service.testcase.BaseTest;

public class InvestProfitTaskServiceTest extends BaseTest {

    @Autowired
    InvestProfitTaskService investProfitTaskService;

    /**
     * 生成投资分红记录单元测试
     */
    @Test
    public void testGenPointDividendRecord() {
        investProfitTaskService.genPointDividendRecord("2015");
    }

}
