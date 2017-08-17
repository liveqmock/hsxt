package com.gy.hsxt.bs.annualfee.service;/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsxt.bs.batch.AnnualFeeParseTask;

/**
 * @Package : com.gy.hsxt.bs.annualfee.service
 * @ClassName : AnnualFeeParseTaskTest
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/12/26 15:39
 * @Version V3.0.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test/spring-global.xml")
@Transactional
public class AnnualFeeParseTaskTest {

    @Resource
    private AnnualFeeParseTask annualFeeParseTask;

    @Test
    public void testExcuteBatch() throws Exception {

        // annualFeeParseTask.excuteBatch(null);
    }
}
