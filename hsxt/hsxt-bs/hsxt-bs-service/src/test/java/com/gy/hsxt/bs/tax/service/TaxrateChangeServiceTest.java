package com.gy.hsxt.bs.tax.service;/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/

import com.gy.hsxt.bs.bean.tax.TaxrateChange;
import com.gy.hsxt.bs.tax.interfaces.ITaxrateChangeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * @Package : com.gy.hsxt.bs.tax.service
 * @ClassName : TaxrateChangeServiceTest
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/12/30 14:47
 * @Version V3.0.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test/spring-global.xml")
public class TaxrateChangeServiceTest {

    @Resource
    private ITaxrateChangeService taxrateChangeService;

    @Test
    public void testQueryValidTaxrateByCustId() throws Exception {

        String rate = taxrateChangeService.queryValidTaxrateByCustId("0600102000020151215");
        System.out.println(rate);
    }
}