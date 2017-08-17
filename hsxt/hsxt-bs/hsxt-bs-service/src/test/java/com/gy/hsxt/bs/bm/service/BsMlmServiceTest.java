package com.gy.hsxt.bs.bm.service;/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/

import com.gy.hsxt.bs.bean.bm.BmlmDetail;
import com.gy.hsxt.bs.bean.bm.BmlmQuery;
import com.gy.hsxt.bs.bean.bm.MlmQuery;
import com.gy.hsxt.bs.bean.bm.MlmTotal;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * @Package : com.gy.hsxt.bs.bm.service
 * @ClassName : BsMlmServiceTest
 * @Description : TODO
 * @Author : chenm
 * @Date : 2016/5/14 11:07
 * @Version V3.0.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-global.xml")
public class BsMlmServiceTest {

    @Resource
    private BsMlmService bsMlmService;

    @Test
    public void queryBmlmByBizNo() throws Exception {
        BmlmDetail bmlmDetail = bsMlmService.queryBmlmByBizNo("110120160503095300000003");
        System.out.println(bmlmDetail);
    }

    @Test
    public void queryBmlmListPage() throws Exception {
        Page page = new Page(0, 10);
        BmlmQuery query = new BmlmQuery();
        query.setCustId("0603201000020160416");
//        query.setStartDate("2016-04-05");
//        query.setEndDate("2016-04-05");
        PageData<BmlmDetail> pageData = bsMlmService.queryBmlmListPage(page, query);
        System.out.println(pageData);
    }

    @Test
    public void queryMlmByBizNo() throws Exception {
        String totalId = "110120160509015635000004";
        MlmTotal mlmTotal = bsMlmService.queryMlmByBizNo(totalId);
        System.out.println(mlmTotal);
    }

    @Test
    public void queryMlmListPage() throws Exception {
        Page page = new Page(0, 10);
        MlmQuery query = new MlmQuery();
        query.setCustId("0600100000020160416");
//        query.setStartDate("2016-04-05");
//        query.setEndDate("2016-04-05");
        PageData<MlmTotal> pageData = bsMlmService.queryMlmListPage(page, query);
        System.out.println(pageData);
    }

}