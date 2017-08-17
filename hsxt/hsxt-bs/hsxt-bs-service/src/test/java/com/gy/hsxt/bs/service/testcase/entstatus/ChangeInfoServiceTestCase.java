/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.service.testcase.entstatus;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.bs.api.entstatus.IBSChangeInfoService;
import com.gy.hsxt.bs.bean.base.ApprParam;
import com.gy.hsxt.bs.bean.entstatus.ChangeInfoQueryParam;
import com.gy.hsxt.bs.bean.entstatus.EntChangeInfo;
import com.gy.hsxt.bs.bean.entstatus.PerChangeInfo;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.constant.CustType;

@FixMethodOrder(MethodSorters.DEFAULT)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test/spring-global.xml")
// @TransactionConfiguration(transactionManager = "transactionManager",
// defaultRollback = false)
// @Transactional
public class ChangeInfoServiceTestCase {

    @Autowired
    IBSChangeInfoService changeInfoService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void createPerChange() {
        PerChangeInfo perChangeInfo = new PerChangeInfo();
        perChangeInfo.setPerCustId("0600211666620151207");
        perChangeInfo.setPerResNo("06002116666");
        perChangeInfo.setPerCustName("张三");
        perChangeInfo.setNameOld("李四");
        perChangeInfo.setNameNew("李小四");
        perChangeInfo.setSexOld(2);
        perChangeInfo.setSexNew(1);
        perChangeInfo.setRegistorAddressOld("江西");
        perChangeInfo.setRegistorAddressNew("广东");
        perChangeInfo.setResidenceAddrPic("123132123132132132123");
        perChangeInfo.setChangeItem("姓名，性别，户籍");
        perChangeInfo.setMobile("13800000000");
        perChangeInfo.setOptCustId("张三");
        changeInfoService.createPerChange(perChangeInfo);
    }

    @Test
    public void revisePerChange() {
        // ReviseInfo reviseInfo = new ReviseInfo();
        // reviseInfo.setApplyId("110120151027100527000000");
        //
        // List<ChangeItem> itemList = new ArrayList<ChangeItem>();
        //
        // ChangeItem changeItem = new ChangeItem();
        // changeItem.setProperty(PerChangeKey.NAME.getCode());
        // changeItem.setOldValue("张小三");
        // changeItem.setNewValue("张大三");
        //
        // ChangeItem changeItem2 = new ChangeItem();
        // changeItem2.setProperty(PerChangeKey.SEX.getCode());
        // changeItem2.setOldValue("1");
        // changeItem2.setNewValue("0");
        //
        // itemList.add(changeItem);
        // itemList.add(changeItem2);
        // reviseInfo.setChangeItems(itemList);
        // reviseInfo.setOptCustId("Peter");
        // changeInfoService.revisePerChange(reviseInfo);
    }

    @Test
    public void queryPerChange() {
        Page page = new Page(1);
        ChangeInfoQueryParam changeInfoQueryParam = new ChangeInfoQueryParam();
        changeInfoQueryParam.setStartDate("2016-01-10");
        changeInfoQueryParam.setEndDate("2016-01-20");
        changeInfoQueryParam.setResNo("0600211");
        changeInfoQueryParam.setName("06");
        // changeInfoQueryParam.setStatus(0);
        System.out.println(changeInfoService.queryPerChange(changeInfoQueryParam, page));
    }

    @Test
    public void queryPerChange4Appr() {
        Page page = new Page(1);
        ChangeInfoQueryParam changeInfoQueryParam = new ChangeInfoQueryParam();
        changeInfoQueryParam.setOptCustId("00000000156000320160105");
        changeInfoQueryParam.setStartDate("2016-01-10");
        changeInfoQueryParam.setEndDate("2016-01-20");
        changeInfoQueryParam.setResNo("0600211");
        changeInfoQueryParam.setName("06");
        // changeInfoQueryParam.setStatus(0);
        System.out.println(changeInfoService.queryPerChange4Appr(changeInfoQueryParam, page));
    }

    @Test
    public void queryPerChangeById() {
        System.out.println(changeInfoService.queryPerChangeById("110120160122102355000000"));
    }

    @Test
    public void apprPerChange() {
        ApprParam apprParam = new ApprParam();
        apprParam.setApplyId("110120160121105025000000");
        apprParam.setOptCustId("00000000156000320160105");
        apprParam.setIsPass(true);
        apprParam.setApprRemark("approved");
        apprParam.setOptName("Jack");
        apprParam.setOptEntName("GUIYI");
        changeInfoService.apprPerChange(apprParam);
    }

    @Test
    public void reviewApprPerChange() {
        ApprParam apprParam = new ApprParam();
        apprParam.setApplyId("110120160121105025000000");
        apprParam.setOptCustId("00000000156000320160105");
        apprParam.setIsPass(true);
        apprParam.setApprRemark("approved");
        apprParam.setOptName("Jack");
        apprParam.setOptEntName("GUIYI");
        changeInfoService.reviewApprPerChange(apprParam);
    }

    @Test
    public void createEntChange() {
        EntChangeInfo changeInfo = new EntChangeInfo();
        changeInfo.setEntCustId("0600100900120160106");
        changeInfo.setEntResNo("06001009001");
        changeInfo.setEntCustName("成员企业-免费");
        changeInfo.setCustType(CustType.MEMBER_ENT.getCode());
        changeInfo.setLinkman("王五");
        changeInfo.setMobile("13632637934");
        changeInfo.setImptappPic("22222222222222");

        changeInfo.setTaxpayerNoOld("123456789");
        changeInfo.setTaxpayerNoNew("123456780");

        changeInfo.setBizLicenseCrePicOld("123456781");
        changeInfo.setBizLicenseCrePicNew("123456782");
        changeInfo.setOptCustId("Jack");
        System.out.println(changeInfo);
        // changeInfoService.createEntChange(changeInfo);
    }

    @Test
    public void reviseEntChange() {
        // ReviseInfo reviseInfo = new ReviseInfo();
        // reviseInfo.setApplyId("110120151028093437000000");
        //
        // List<ChangeItem> itemList = new ArrayList<ChangeItem>();
        //
        // ChangeItem changeItem = new ChangeItem();
        // changeItem.setProperty(EntChangeKey.CUST_NAME.getCode());
        // changeItem.setOldValue("生一");
        // changeItem.setNewValue("生一三");
        //
        // ChangeItem changeItem2 = new ChangeItem();
        // changeItem2.setProperty(EntChangeKey.LEGAL_REP.getCode());
        // changeItem2.setOldValue("张三");
        // changeItem2.setNewValue("李四一");
        //
        // itemList.add(changeItem);
        // itemList.add(changeItem2);
        // reviseInfo.setChangeItems(itemList);
        // reviseInfo.setOptCustId("Peter");
        // changeInfoService.reviseEntChange(reviseInfo);
    }

    @Test
    public void entQueryChange() {
        Page page = new Page(1);
        ChangeInfoQueryParam changeInfoQueryParam = new ChangeInfoQueryParam();
        changeInfoQueryParam.setResNo("85125");
        changeInfoQueryParam.setName("一");
        changeInfoQueryParam.setStartDate("2015-12-11");
        changeInfoQueryParam.setEndDate("2015-12-11");
        System.out.println(changeInfoService.entQueryChange(changeInfoQueryParam, page));
    }

    @Test
    public void platQueryEntChange() {
        Page page = new Page(1);
        ChangeInfoQueryParam changeInfoQueryParam = new ChangeInfoQueryParam();
        changeInfoQueryParam.setEntType(CustType.MEMBER_ENT.getCode());
        changeInfoQueryParam.setResNo("85125");
        changeInfoQueryParam.setName("一");
        changeInfoQueryParam.setStartDate("2015-12-11");
        changeInfoQueryParam.setEndDate("2015-12-11");

        System.out.println(changeInfoService.platQueryEntChange(changeInfoQueryParam, page));
    }

    @Test
    public void platQueryEntChange4Appr() {
        Page page = new Page(1);
        ChangeInfoQueryParam changeInfoQueryParam = new ChangeInfoQueryParam();
        changeInfoQueryParam.setEntType(CustType.MEMBER_ENT.getCode());
        // changeInfoQueryParam.setResNo("85125");
        // changeInfoQueryParam.setName("一");
        // changeInfoQueryParam.setStartDate("2015-12-11");
        // changeInfoQueryParam.setEndDate("2015-12-11");
        changeInfoQueryParam.setOptCustId("00000000156000320160105");
        System.out.println(changeInfoService.platQueryEntChange4Appr(changeInfoQueryParam, page));
    }

    @Test
    public void queryEntChangeById() {
        System.out.println(changeInfoService.queryEntChangeById("110120151028093437000000"));
    }

    @Test
    public void apprEntChange() {
        ApprParam apprParam = new ApprParam();
        apprParam.setApplyId("110120160121111245000000");
        apprParam.setIsPass(true);
        apprParam.setOptCustId("00000000156000320160105");
        apprParam.setOptName("Tony");
        apprParam.setOptEntName("Tony");
        apprParam.setApprRemark("同意");
        changeInfoService.apprEntChange(apprParam);
    }

    @Test
    public void reviewApprEntChange() {
        ApprParam apprParam = new ApprParam();
        apprParam.setApplyId("110120160121111245000000");
        apprParam.setIsPass(true);
        apprParam.setOptCustId("00000000156000320160105");
        apprParam.setOptName("Tony");
        apprParam.setOptEntName("Tony");
        apprParam.setApprRemark("同意");
        changeInfoService.reviewApprEntChange(apprParam);
    }

    @Test
    public void queryEntChangeHis() {
        System.out.println(changeInfoService.queryEntChangeHis("110120151207052716000000", new Page(1)));
    }

    @Test
    public void queryPerChagneRecord() {
        System.out.println(changeInfoService.queryPerChagneRecord("0600211666620151207", "2016-01-21", "2016-01-21",
                new Page(1)));
    }

    @Test
    public void queryEntChangeByCustId() {
        System.out.println(changeInfoService.queryEntChangeByCustId("0601000000020151231"));
    }
}
