package com.gy.hsxt.gpf.um.service.impl;/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/

import com.gy.hsxt.gpf.um.bean.Operator;
import com.gy.hsxt.gpf.um.service.IOperatorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @Package : com.gy.hsxt.gpf.um.service.impl
 * @ClassName : OperatorServiceTest
 * @Description : TODO
 * @Author : chenm
 * @Date : 2016/1/27 11:27
 * @Version V3.0.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-global.xml")
public class OperatorServiceTest {

    @Resource
    private IOperatorService operatorService;


    @Test
    public void testSaveBean() throws Exception {
        Operator operator = new Operator();
        operator.setLoginUser("admin12");
        operator.setLoginPwd("11111111");
        operator.setName("admin12");
        operator.setDuty("管理员12");
        operator.setCreatedBy("creator12");

        operatorService.saveBean(operator);
    }

    @Test
    public void testRemoveBeanById() throws Exception {

    }

    @Test
    public void testModifyBean() throws Exception {

    }

    @Test
    public void testQueryBeanById() throws Exception {

    }

    @Test
    public void testQueryBeanByQuery() throws Exception {

    }

    @Test
    public void testQueryBeanListByQuery() throws Exception {

    }
}