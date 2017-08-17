package com.gy.hsxt.bs.tax.service;/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.bs.bean.tax.TaxrateChange;
import com.gy.hsxt.bs.bean.tax.TaxrateChangeQuery;
import com.gy.hsxt.bs.common.enumtype.tax.TaxpayerType;
import com.gy.hsxt.bs.common.enumtype.tax.TaxrateStatus;
import com.gy.hsxt.bs.tax.interfaces.ITaxrateChangeAllService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;

/**
 * @Package : com.gy.hsxt.bs.tax.service
 * @ClassName : TaxrateChangeAllServiceTest
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/12/30 13:07
 * @Version V3.0.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test/spring-global.xml")
public class TaxrateChangeAllServiceTest {

    @Resource
    private ITaxrateChangeAllService taxrateChangeAllService;

    @Test
    public void testCreateTaxrateChange() throws Exception {
        TaxrateChange taxrateChange = new TaxrateChange();
        taxrateChange.setResNo("06005009000");
        taxrateChange.setCustId("0600500900020160106");
        taxrateChange.setCustName("服务公司-成员企业-01");
        taxrateChange.setLinkman("李刚");
        taxrateChange.setTelephone("13410805752");
        taxrateChange.setCreatedBy("0601001000120151231");
        taxrateChange.setCreatedName("system");
        taxrateChange.setApplyReason("调整税率");
        taxrateChange.setApplyTaxrate("0.23");
        taxrateChange.setProveMaterialFile("http://www.baidu.com");
        taxrateChange.setTaxpayerType(TaxpayerType.NORMAL.ordinal());

        taxrateChangeAllService.createTaxrateChange(taxrateChange);

    }

    @Test
    public void testQueryTaxrateChangeListPage() throws Exception {
        Page page = new Page(0, 10);
        TaxrateChangeQuery query = new TaxrateChangeQuery();
        query.setCustId("0600104000320151221");

        PageData<TaxrateChange> pageData = taxrateChangeAllService.queryTaxrateChangeListPage(page, query);

        System.out.println(pageData);

    }

    @Test
    public void testQueryTaxrateChangeById() throws Exception {

        TaxrateChange taxrateChange = taxrateChangeAllService.queryTaxrateChangeById("110120160107120208000000");
        System.out.println(taxrateChange);
    }

    @Test
    public void testApprTaxrateChange() throws Exception {
        TaxrateChange taxrateChange = new TaxrateChange();
        // taxrateChange.setResNo("06002110000");
        taxrateChange.setCustId("0601000000020151231");
        // taxrateChange.setCustName("托管企业-创业");
        // taxrateChange.setCreatedBy("system");
        taxrateChange.setApplyReason("复核通过");
        // taxrateChange.setApplyTaxrate("0.03");
        // taxrateChange.setCurrTaxrate("0.0");
        // taxrateChange.setProveMaterialFile("http://www.baidu.com");
        // taxrateChange.setTaxpayerType(TaxpayerType.SMALL.ordinal());

        taxrateChange.setApplyId("110120160301140403000000");
        taxrateChange.setStatus(TaxrateStatus.REVIEW.ordinal());
        taxrateChange.setUpdatedBy("8654654654864564654");
        taxrateChange.setUpdatedName("system实撒旦发射的");

        taxrateChangeAllService.apprTaxrateChange(taxrateChange);
    }

    @Test
    public void testQueryLastHisByCustId() throws Exception {

        TaxrateChange taxrateChange = taxrateChangeAllService.queryLastHisByCustId("0600104000320151221");
        System.out.println(taxrateChange);
    }
}
