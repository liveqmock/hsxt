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

import com.gy.hsxt.bs.api.apply.IBSCertificateService;
import com.gy.hsxt.bs.bean.apply.Certificate;
import com.gy.hsxt.bs.bean.apply.TemplateAppr;
import com.gy.hsxt.bs.bean.apply.Templet;
import com.gy.hsxt.bs.bean.apply.TempletQuery;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;

/**
 * @Package : com.gy.hsxt.bs.apply.service
 * @ClassName : CertificateServiceTest
 * @Description : TODO
 * @Author : chenm
 * @Date : 2016/3/15 10:32
 * @Version V3.0.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test/spring-global.xml")
public class CertificateServiceTest {

    @Resource
    private IBSCertificateService certificateService;

    @Test
    public void testCreateTemplet() throws Exception {

        Templet templet = new Templet();
        templet.setCustType(4);
        // templet.setResType(2);
        templet.setTempletName("服务公司证书模版-1");
        templet.setTempPicId("TEMPLATE_PICTURE_1");
        templet.setTempFileId("SDFSDFSDFSDFSDG");
        templet.setCreatedBy("000000000156201525ss25");
        certificateService.createTemplet(templet);

    }

    @Test
    public void testModifyTemplet() throws Exception {

    }

    @Test
    public void testQueryTempletById() throws Exception {

        Templet templet = certificateService.queryTempletById("110120160315104841000000");
        System.out.println(templet);
    }

    @Test
    public void testQueryTempletList() throws Exception {
        Page page = new Page(1);
        TempletQuery query = new TempletQuery();
        query.setApprStatus(0);

        PageData<Templet> pageData = certificateService.queryTempletList(query, page);
        System.out.println(pageData);

    }

    @Test
    public void testQueryTemplet4Appr() throws Exception {
        Page page = new Page(1);
        TempletQuery query = new TempletQuery();
        // query.setApprStatus(0);
        query.setOptCustId("00000000156163438271051776");
        PageData<Templet> pageData = certificateService.queryTemplet4Appr(query, page);
        System.out.println(pageData);
    }

    @Test
    public void testApprTemplet() throws Exception {

        TemplateAppr appr = new TemplateAppr();
        appr.setApprOperator("00000000156163438271051776");
        appr.setTempletId("110120160315104841000000");
        appr.setApprResult(1);
        appr.setApprStatus(2);
        appr.setReqRemark("通过");
        certificateService.apprTemplet(appr);

    }

    @Test
    public void testEnableTemplet() throws Exception {

        certificateService.enableTemplet("110120160315104841000000", "sys-test");
    }

    @Test
    public void testDisableTemplet() throws Exception {
        certificateService.disableTemplet("110120160315104841000000", "sys-test-1");
    }

    @Test
    public void testQueryCreById() throws Exception {
        Certificate certificate = certificateService.queryCreById("06014000000MRTLPYXNBN");
        System.out.println(certificate);
    }

    @Test
    public void queryLastTemplateAppr() throws Exception {
        TemplateAppr certificate = certificateService.queryLastTemplateAppr("110120160317161834000000");
        System.out.println(certificate);
    }

    @Test
    public void queryThirdPartCer() throws Exception {
        System.out.println(certificateService.queryThirdPartCer("MRTLPYXNBN", "06014000000"));
    }

    @Test
    public void querySaleCer() throws Exception {
        System.out.println(certificateService.querySaleCer("0KLBE62LAO", "06010020000"));
    }
}
