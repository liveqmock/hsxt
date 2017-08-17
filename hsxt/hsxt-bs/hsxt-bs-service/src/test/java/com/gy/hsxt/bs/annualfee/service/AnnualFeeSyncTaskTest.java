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

import com.gy.hsxt.bs.batch.AnnualFeeSyncTask;

/**
 * @Package : com.gy.hsxt.bs.annualfee.service
 * @ClassName : AnnualFeeSyncTaskTest
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/12/28 10:44
 * @Version V3.0.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test/spring-global.xml")
public class AnnualFeeSyncTaskTest {

    @Resource
    private AnnualFeeSyncTask annualFeeSyncTask;

    @Test
    public void testExcuteBatch() throws Exception {

        // annualFeeSyncTask.excuteBatch(null);
    }
}
