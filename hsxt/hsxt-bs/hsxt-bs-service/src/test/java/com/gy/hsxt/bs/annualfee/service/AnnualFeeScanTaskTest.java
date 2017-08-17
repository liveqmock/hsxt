package com.gy.hsxt.bs.annualfee.service;/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

import javax.annotation.Resource;

import com.gy.hsxt.uc.bs.api.ent.IUCBsEntService;
import com.gy.hsxt.uc.bs.bean.ent.BsEntStatusInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.bs.batch.AnnualFeeScanTask;

import java.util.ArrayList;
import java.util.List;

/**
 * @Package : com.gy.hsxt.bs.annualfee.service
 * @ClassName : AnnualFeeScanTaskTest
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/12/25 15:05
 * @Version V3.0.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test/spring-global.xml")
public class AnnualFeeScanTaskTest {

    /**
     * 用户中心状态同步接口
     */
    @Resource
    private IUCBsEntService bsEntService;

    @Test
    public void testExcuteBatch() throws Exception {

        //刚到期的年费企业
        List<BsEntStatusInfo> infos = new ArrayList<>();
        //同步年费状态
        BsEntStatusInfo bsEntStatusInfo = new BsEntStatusInfo();
        bsEntStatusInfo.setEntCustId("0600102000020151215");//0600102000020151215
        bsEntStatusInfo.setIsOweFee(1);//1-欠费 0-未欠费
        bsEntStatusInfo.setExpireDate("2016-03-09 00:00:00");
        infos.add(bsEntStatusInfo);
        bsEntService.batchUpdateEntStatusInfo(infos);
    }
    @Test
    public void testBatch() throws Exception {

        //同步年费状态
//        BsEntStatusInfo bsEntStatusInfo = new BsEntStatusInfo();
//        bsEntStatusInfo.setEntCustId("0800101000020151222");//0800101000020151222
//        bsEntStatusInfo.setIsOweFee(1);//1-欠费 0-未欠费
//        bsEntStatusInfo.setExpireDate("2016-03-09 00:00:00");
//        bsEntService.updateEntStatusInfo(bsEntStatusInfo,"0000");
        BsEntStatusInfo bsEntStatusInfo = bsEntService.searchEntStatusInfo("0800101000020151222");
        System.out.println(bsEntStatusInfo);
    }

}
