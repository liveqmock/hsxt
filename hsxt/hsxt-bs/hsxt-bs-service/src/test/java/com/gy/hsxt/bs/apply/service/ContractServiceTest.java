package com.gy.hsxt.bs.apply.service;/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.bs.api.apply.IBSContractService;
import com.gy.hsxt.bs.bean.apply.ContractContent;

/**
 * @Package : com.gy.hsxt.bs.apply.service
 * @ClassName : ContractServiceTest
 * @Description : TODO
 * @Author : chenm
 * @Date : 2016/3/18 11:22
 * @Version V3.0.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test/spring-global.xml")
public class ContractServiceTest {

    @Resource
    private IBSContractService contractService;

    @Test
    public void testQueryContract4Seal() throws Exception {

        ContractContent contractContent = contractService.queryContractContent4Seal("060280000004UUCVQXFNV",false);
        System.out.println(contractContent);
    }

    @Test
    public void testQueryContract() throws Exception {

        System.out.println(contractService.queryContractMainInfo("XX4NX2T0MJ", "06023000000"));
    }
}
