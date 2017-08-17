/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.service.testcase.entstatus;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.bs.api.entstatus.IBSRealNameAuthService;
import com.gy.hsxt.bs.bean.ChangeItem;
import com.gy.hsxt.bs.bean.base.ApprParam;
import com.gy.hsxt.bs.bean.entstatus.EntRealnameAuth;
import com.gy.hsxt.bs.bean.entstatus.PerRealnameAuth;
import com.gy.hsxt.bs.bean.entstatus.RealNameQueryParam;
import com.gy.hsxt.bs.common.enumtype.LegalCreType;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.constant.CustType;

@FixMethodOrder(MethodSorters.DEFAULT)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test/spring-global.xml")
// @TransactionConfiguration(transactionManager = "transactionManager",
// defaultRollback = false)
// @Transactional
public class RealNameAuthServiceTestCase {

    @Autowired
    IBSRealNameAuthService authService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void createPerRealnameAuth() {
        PerRealnameAuth perRealnameAuth = new PerRealnameAuth();
        perRealnameAuth.setPerResNo("06001020009");
        perRealnameAuth.setPerCustId("0600102000920151217");
        perRealnameAuth.setName("张小三");
        perRealnameAuth.setSex(1);
        perRealnameAuth.setCountryNo("156");
        perRealnameAuth.setCountryName("中国");
        perRealnameAuth.setNation("汉");
        perRealnameAuth.setBirthAddr("深圳");
        perRealnameAuth.setLicenceIssuing("深圳南山公安局");
        perRealnameAuth.setProfession("攻城狮");
        perRealnameAuth.setCertype(LegalCreType.ID_CARD.getCode());
        perRealnameAuth.setCredentialsNo("360733199010204522");
        perRealnameAuth.setCerpica("1234654654684");
        perRealnameAuth.setCerpicb("1234654654685");
        perRealnameAuth.setCerpich("1234654654686");
        perRealnameAuth.setValidDate("2020-10-01");
        perRealnameAuth.setPostScript("我要实名");
        perRealnameAuth.setOptCustId("0600102000120151217");

        // perRealnameAuth.setIssuePlace("中国深圳");
        // perRealnameAuth.setEntName("哈哈哈公司");
        // perRealnameAuth.setEntRegAddr("中国深圳福田");
        // perRealnameAuth.setEntType("私有");
        // perRealnameAuth.setEntBuildDate("2010-10-01");

        authService.createPerRealnameAuth(perRealnameAuth);
    }

    @Test
    public void modifyPerRealnameAuth() {
        PerRealnameAuth perRealnameAuth = new PerRealnameAuth();
        perRealnameAuth.setApplyId("110120151028054902000000");
        // perRealnameAuth.setPerResNo("85123120011");
        // perRealnameAuth.setPerCustId("123456879646565");
        perRealnameAuth.setName("张三X");
        perRealnameAuth.setSex(0);
        perRealnameAuth.setCountryNo("156X");
        perRealnameAuth.setCountryName("中国X");
        // perRealnameAuth.setNation("汉");
        perRealnameAuth.setBirthAddr("深圳X");
        // perRealnameAuth.setLicenceIssuing("深圳南山公安局");
        // perRealnameAuth.setProfession("攻城狮");
        perRealnameAuth.setCertype(LegalCreType.PASSPORT.getCode());
        perRealnameAuth.setCredentialsNo("36073319901020451X");
        perRealnameAuth.setCerpica("1234654654654X");
        perRealnameAuth.setCerpicb("1234654654655X");
        perRealnameAuth.setCerpich("1234654654656X");
        perRealnameAuth.setValidDate("2020-10-10");
        // perRealnameAuth.setPostScript("我要实名");
        // perRealnameAuth.setCreatedBy("张三");
        perRealnameAuth.setOptCustId("张大三");

        List<ChangeItem> itemList = new ArrayList<ChangeItem>();

        ChangeItem changeItem = new ChangeItem();
        changeItem.setProperty("NAME");
        changeItem.setOldValue("张三");
        changeItem.setNewValue("张大三");

        ChangeItem changeItem2 = new ChangeItem();
        changeItem2.setProperty("SEX");
        changeItem2.setOldValue("1");
        changeItem2.setNewValue("0");

        itemList.add(changeItem);
        itemList.add(changeItem2);
        perRealnameAuth.setChangeContent(JSON.toJSONString(itemList));

        authService.modifyPerRealnameAuth(perRealnameAuth);
    }

    @Test
    public void queryPerRealnameAuth() {
        Page page = new Page(1);
        RealNameQueryParam realNameQueryParam = new RealNameQueryParam();
        realNameQueryParam.setStartDate("2016-01-10");
        realNameQueryParam.setEndDate("2016-01-20");
        realNameQueryParam.setResNo("0600211");
        realNameQueryParam.setName("润");
        System.out.println(authService.queryPerRealnameAuth(realNameQueryParam, page));
    }

    @Test
    public void queryPerRealnameAuth4Appr() {
        Page page = new Page(1);
        RealNameQueryParam realNameQueryParam = new RealNameQueryParam();
        realNameQueryParam.setStartDate("2016-01-10");
        realNameQueryParam.setEndDate("2016-01-20");
        realNameQueryParam.setResNo("0600211");
        realNameQueryParam.setName("润");
        realNameQueryParam.setOptCustId("123112313213212");
        System.out.println(authService.queryPerRealnameAuth4Appr(realNameQueryParam, page));
    }

    @Test
    public void queryPerRealnameAuthById() {
        System.out.println(authService.queryPerRealnameAuthById("0600211820420151207"));
    }

    @Test
    public void queryPerRealnameAuthByCustId() {
        System.out.println(authService.queryPerRealnameAuthByCustId("0600211820420151207"));
    }

    @Test
    public void apprPerRealnameAuth() {
        ApprParam apprParam = new ApprParam();
        apprParam.setApplyId("110120160126125008000000");
        apprParam.setIsPass(true);
        apprParam.setOptCustId("00000000156000220160105");
        apprParam.setOptName("小五");
        apprParam.setOptEntName("托管企业-创业");
        apprParam.setApprRemark("这个名字有意思，准了！");
        authService.apprPerRealnameAuth(apprParam);
    }

    @Test
    public void reviewApprPerRealnameAuth() {
        ApprParam apprParam = new ApprParam();
        apprParam.setApplyId("110120160115163213000000");
        apprParam.setIsPass(true);
        apprParam.setOptCustId("0600102000020151215");
        apprParam.setOptName("小五");
        apprParam.setOptEntName("托管企业-创业");
        apprParam.setApprRemark("同意");
        authService.reviewApprPerRealnameAuth(apprParam);
    }

    @Test
    public void queryPerRealnameAuthHis() {
        System.out.println(authService.queryPerRealnameAuthHis("110120151028090353000000", new Page(1)));
    }

    @Test
    public void createEntRealnameAuth() {
        EntRealnameAuth entRealnameAuth = new EntRealnameAuth();
        entRealnameAuth.setEntResNo("06001060000");
        entRealnameAuth.setEntCustId("0600106000020160108");
        entRealnameAuth.setCustType(CustType.TRUSTEESHIP_ENT.getCode());
        entRealnameAuth.setEntCustName("托管企业-创业资源");
        entRealnameAuth.setEntCustNameEn("ENG-12");
        entRealnameAuth.setCountryNo("156");
        entRealnameAuth.setProvinceNo("44");
        entRealnameAuth.setCityNo("440104");
        entRealnameAuth.setEntAddr("深圳福田区xxx路");
        entRealnameAuth.setLinkman("王五");
        entRealnameAuth.setMobile("13632637933");
        entRealnameAuth.setLegalName("王五");
        entRealnameAuth.setLegalCreType(LegalCreType.ID_CARD.getCode());
        entRealnameAuth.setLegalCreNo("123132454564564");
        entRealnameAuth.setLicenseNo("13333333333");
        entRealnameAuth.setOrgNo("55555555555555");
        entRealnameAuth.setTaxNo("6666666666666666");
        entRealnameAuth.setLrcFacePic("111111111111");
        entRealnameAuth.setLrcBackPic("22222222222222");
        entRealnameAuth.setLicensePic("333333333333");
        entRealnameAuth.setOrgPic("4444444444444");
        entRealnameAuth.setTaxPic("555555555555");
        entRealnameAuth.setOptCustId("Peter");

        authService.createEntRealnameAuth(entRealnameAuth);
    }

    @Test
    public void modifyEntRealnameAuth() {

        EntRealnameAuth entRealnameAuth = new EntRealnameAuth();
        entRealnameAuth.setApplyId("110120151029114121000000");
        // entRealnameAuth.setEntResNo("85123120000");
        // entRealnameAuth.setEntCustId("923456879646565");
        // entRealnameAuth.setCustType(CustType.MEMBER_ENT.getCode());
        entRealnameAuth.setEntCustName("生一X");
        entRealnameAuth.setEntCustNameEn("shengyikejiX");
        // entRealnameAuth.setCountryNo("123");
        // entRealnameAuth.setProvinceNo("123456");
        // entRealnameAuth.setCityNo("1234567890");
        entRealnameAuth.setEntAddr("深圳福田X");
        entRealnameAuth.setLinkman("王大五X");
        entRealnameAuth.setMobile("1364684445X");
        entRealnameAuth.setLegalName("王小五X");
        entRealnameAuth.setLegalCreType(LegalCreType.ID_CARD.getCode());
        entRealnameAuth.setLegalCreNo("123132454564564X");
        entRealnameAuth.setLicenseNo("13333333333X");
        entRealnameAuth.setOrgNo("55555555555555X");
        entRealnameAuth.setTaxNo("6666666666666666X");
        entRealnameAuth.setLrcFacePic("111111111111X");
        entRealnameAuth.setLrcBackPic("22222222222222X");
        entRealnameAuth.setLicensePic("333333333333X");
        entRealnameAuth.setOrgPic("4444444444444X");
        entRealnameAuth.setTaxPic("555555555555X");
        entRealnameAuth.setOptCustId("Peter");

        List<ChangeItem> itemList = new ArrayList<ChangeItem>();

        ChangeItem changeItem = new ChangeItem();
        changeItem.setProperty("NAME");
        changeItem.setOldValue("张三");
        changeItem.setNewValue("张大三");

        ChangeItem changeItem2 = new ChangeItem();
        changeItem2.setProperty("SEX");
        changeItem2.setOldValue("1");
        changeItem2.setNewValue("0");

        itemList.add(changeItem);
        itemList.add(changeItem2);
        // entRealnameAuth.setChangeItems(itemList);

        authService.modifyEntRealnameAuth(entRealnameAuth);
    }

    @Test
    public void queryEntRealnameAuth() {
        Page page = new Page(1);
        RealNameQueryParam realNameQueryParam = new RealNameQueryParam();
        // realNameQueryParam.setEndDate("2015-10-01");
        realNameQueryParam.setStartDate("2015-10-01");
        realNameQueryParam.setName("生");
        realNameQueryParam.setResNo("8512312");
        realNameQueryParam.setEntType(CustType.MEMBER_ENT.getCode());
        System.out.println(authService.queryEntRealnameAuth(realNameQueryParam, page));
    }

    @Test
    public void queryEntRealnameAuth4Appr() {
        Page page = new Page(1);
        RealNameQueryParam realNameQueryParam = new RealNameQueryParam();
        // realNameQueryParam.setEndDate("2015-10-01");
        realNameQueryParam.setStartDate("2015-10-01");
        realNameQueryParam.setName("生");
        realNameQueryParam.setResNo("8512312");
        realNameQueryParam.setEntType(CustType.MEMBER_ENT.getCode());
        realNameQueryParam.setOptCustId("231423132132");
        System.out.println(authService.queryEntRealnameAuth4Appr(realNameQueryParam, page));
    }

    @Test
    public void queryEntRealnameAuthById() {
        System.out.println(authService.queryEntRealnameAuthById("110120151029114121000000"));
    }

    @Test
    public void apprEntRealnameAuth() {
        ApprParam apprParam = new ApprParam();
        apprParam.setApplyId("110120160115174901000000");
        apprParam.setIsPass(true);
        apprParam.setOptCustId("06001000000163432404206592");
        apprParam.setOptName("名字");
        apprParam.setOptEntName("06001000000服务公司");
        authService.apprEntRealnameAuth(apprParam);
    }

    @Test
    public void reviewApprEntRealnameAuth() {
        ApprParam apprParam = new ApprParam();
        apprParam.setApplyId("110120160120175422000000");
        apprParam.setIsPass(true);
        apprParam.setOptCustId("00000000156000220160105");
        apprParam.setOptName("名字");
        apprParam.setOptEntName("06001000000服务公司");

        authService.reviewApprEntRealnameAuth(apprParam);
    }

    @Test
    public void queryEntRealnameAuthHis() {
        System.out.println(authService.queryEntRealnameAuthHis("110120151029114121000000", new Page(1)));
    }

    @Test
    public void queryEntRealnameAuthByCustId() {
        System.out.println(authService.queryEntRealnameAuthByCustId("0600104000020151221"));
    }

    @Test
    public void queryPerAuthRecord() {
        System.out.println(authService.queryPerAuthRecord("0600211810520151207", "2016-01-16", "2016-01-19",
                new Page(1)));
    }
}
