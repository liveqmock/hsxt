/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.service.testcase.apply;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.bs.api.apply.IBSFilingService;
import com.gy.hsxt.bs.bean.apply.FilingApp;
import com.gy.hsxt.bs.bean.apply.FilingAppInfo;
import com.gy.hsxt.bs.bean.apply.FilingApprParam;
import com.gy.hsxt.bs.bean.apply.FilingAptitude;
import com.gy.hsxt.bs.bean.apply.FilingQueryParam;
import com.gy.hsxt.bs.bean.apply.FilingSameItem;
import com.gy.hsxt.bs.bean.apply.FilingShareHolder;
import com.gy.hsxt.bs.common.enumtype.LegalCreType;
import com.gy.hsxt.bs.common.enumtype.apply.AptitudeType;
import com.gy.hsxt.bs.common.enumtype.apply.FilingStatus;
import com.gy.hsxt.bs.common.enumtype.apply.ShareholderType;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;

@FixMethodOrder(MethodSorters.DEFAULT)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test/spring-global.xml")
// @TransactionConfiguration(transactionManager = "transactionManager",
// defaultRollback = false)
// @Transactional
public class FilingServiceTestCase {

    @Autowired
    IBSFilingService filingService;

    String applyId = "1120151014042905000000";

    String applyId2 = "1120151015120025000000";

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void createFiling() {
        FilingApp filingApp = new FilingApp();
        filingApp.setOpResNo("12345000000");
        filingApp.setEntCustName("TEST ENT NAME");
        filingApp.setEntType("私有企业T");
        filingApp.setEntAddress("TEST ADDRESS");
        filingApp.setLegalName("TEST LEGAL MAN");
        filingApp.setLegalType(LegalCreType.ID_CARD.getCode());
        filingApp.setLegalNo("TEST LEGAL NO");
        filingApp.setLinkman("TEST LINKMAN");
        filingApp.setPhone("TEST PHONE");
        filingApp.setDealArea("TEST AREA");
        filingApp.setCountryNo("123");
        filingApp.setProvinceNo("456780");
        filingApp.setCityNo("123456780");
        filingApp.setLicenseNo("12346578979T");
        filingApp.setReqReason("Test reqReason");
        filingApp.setCreatedBy("TESTER02");
        applyId = filingService.createFiling(filingApp);
    }

    @Test
    public void serviceModifyFiling() {
        FilingApp filingApp = new FilingApp();
        filingApp.setApplyId("110120151117032955000000");
        filingApp.setEntCustName("TEST ENT NAME X");
        filingApp.setEntType("私有企业X");
        filingApp.setEntAddress("TEST ADDRESS X");
        filingApp.setLegalName("TEST LEGAL MAN X");
        filingApp.setLegalType(LegalCreType.ID_CARD.getCode());
        filingApp.setLegalNo("TEST LEGAL NO X");
        filingApp.setLinkman("TEST LINKMAN X");
        filingApp.setPhone("TEST PHONE X");
        filingApp.setDealArea("TEST AREA X");
        filingApp.setCountryNo("123");
        filingApp.setProvinceNo("456780");
        filingApp.setCityNo("123456780");
        filingApp.setLicenseNo("12346578979X");
        filingApp.setReqReason("Test reqReason X");
        filingApp.setUpdatedBy("TESTER02 X");
        filingService.serviceModifyFiling(filingApp);
    }

    @Test
    public void platModifyFiling() {
        FilingApp filingApp = new FilingApp();
        filingApp.setApplyId("110120151117032955000000");
        filingApp.setEntCustName("TEST ENT NAME X");
        filingApp.setEntType("私有企业X");
        filingApp.setEntAddress("TEST ADDRESS X");
        filingApp.setLegalName("TEST LEGAL MAN X");
        filingApp.setLegalType(LegalCreType.ID_CARD.getCode());
        filingApp.setLegalNo("TEST LEGAL NO X");
        filingApp.setLinkman("TEST LINKMAN X");
        filingApp.setPhone("TEST PHONE X");
        filingApp.setDealArea("TEST AREA X");
        filingApp.setCountryNo("123");
        filingApp.setProvinceNo("456780");
        filingApp.setCityNo("123456780");
        filingApp.setLicenseNo("12346578979X");
        filingApp.setReqReason("Test reqReason X");
        filingApp.setUpdatedBy("TESTER02 Xx");
        filingApp.setOptCustId("tttttttt");
        filingApp.setDblOptCustId("qqqqqqqq");
        filingService.platModifyFiling(filingApp);
    }

    @Test
    public void createShareHolder() {
        FilingShareHolder filingShareHolder = new FilingShareHolder();
        filingShareHolder.setApplyId("110120151117045400000000");
        filingShareHolder.setShName("HS");
        filingShareHolder.setShType(ShareholderType.BUSINESS_ENTITY.getCode());
        filingShareHolder.setIdType(LegalCreType.BIZ_LICENSE.getCode());
        filingShareHolder.setIdNo("license no");
        filingShareHolder.setPhone("123456789");
        filingShareHolder.setOptCustId("GUIYI");
        filingService.createShareHolder(filingShareHolder);
    }

    @Test
    public void serviceModifyShareHolder() {
        FilingShareHolder filingShareHolder = new FilingShareHolder();
        filingShareHolder.setFilingShId("20151117035908000000");
        filingShareHolder.setShName("GUIYI X");
        filingShareHolder.setShType(ShareholderType.MASS_ORGANIZATION_LEGAL_PERSON.getCode());
        filingShareHolder.setIdType(LegalCreType.ID_CARD.getCode());
        filingShareHolder.setIdNo("30454654659999 x");
        filingShareHolder.setPhone("1234567890 X");
        filingShareHolder.setOptCustId("GUIYI X");
        filingService.serviceModifyShareHolder(filingShareHolder);
    }

    @Test
    public void platModifyShareHolder() {

        // ChangeInfo changeInfo = new ChangeInfo();
        // //changeInfo.setApplyId("1120151016113232000000");
        // changeInfo.setId("20151016023553000001");
        // changeInfo.setOperator("Jack");
        // changeInfo.setDoubleOperator("Wade");
        // changeInfo.setRemark("test");
        //
        // List<ChangeItem> changeItems = new ArrayList<ChangeItem>();
        // ChangeItem changeItem = new ChangeItem();
        // changeItem.setProperty(FilingAppKey.SH_NAME.getCode());
        // changeItem.setOldValue("GuiYI");
        // changeItem.setNewValue("HS");
        //
        // ChangeItem changeItem2 = new ChangeItem();
        // changeItem2.setProperty(FilingAppKey.ID_NO.getCode());
        // changeItem2.setOldValue("12346578979T");
        // changeItem2.setNewValue("987654321");
        //
        // changeItems.add(changeItem);
        // changeItems.add(changeItem2);
        //
        // changeInfo.setChangeItems(changeItems);
        // filingService.platModifyShareHolder(changeInfo);

        FilingShareHolder filingShareHolder = new FilingShareHolder();
        filingShareHolder.setFilingShId("20151117035908000000");
        filingShareHolder.setShName("GUIYI X2");
        filingShareHolder.setShType(ShareholderType.MASS_ORGANIZATION_LEGAL_PERSON.getCode());
        filingShareHolder.setIdType(LegalCreType.ID_CARD.getCode());
        filingShareHolder.setIdNo("30454654659999 x2");
        filingShareHolder.setPhone("1234567890 X2");
        filingShareHolder.setOptCustId("GUIYI X2");
        filingShareHolder.setDblOptCustId("生一");
        filingService.platModifyShareHolder(filingShareHolder);
    }

    @Test
    public void deleteShareHolder() {
        filingService.deleteShareHolder("20151117035908000000", "Jack");
    }

    @Test
    public void saveAptitude() {
        List<FilingAptitude> filingAptitudes = new ArrayList<FilingAptitude>();
        FilingAptitude filingAptitude = new FilingAptitude();
        // filingAptitude.setFilingAptId("20151016024920000000");
        filingAptitude.setApplyId("110120151117045400000000");
        filingAptitude.setAptType(AptitudeType.BANK_ROLL_CRE.getCode());
        filingAptitude.setFileId("t0000001");
        filingAptitude.setAptName("test apt name1");
        filingAptitude.setCreatedBy("TESTER 000");

        FilingAptitude filingAptitude2 = new FilingAptitude();
        // filingAptitude2.setFilingAptId("20151016024920000001");
        filingAptitude2.setApplyId("110120151117045400000000");
        filingAptitude2.setAptType(AptitudeType.BIZ_LICENSE_CRE.getCode());
        filingAptitude2.setFileId("t0000002");
        filingAptitude2.setAptName("test apt name2");
        filingAptitude2.setCreatedBy("TESTER2");

        FilingAptitude filingAptitude3 = new FilingAptitude();
        // filingAptitude3.setFilingAptId("20151016024920000002");
        filingAptitude3.setApplyId("110120151117045400000000");
        filingAptitude3.setAptType(AptitudeType.ORGANIZER_CRE.getCode());
        filingAptitude3.setFileId("t0000002");
        filingAptitude3.setAptName("test apt name3");
        filingAptitude3.setCreatedBy("TESTER3");

        filingAptitudes.add(filingAptitude);
        filingAptitudes.add(filingAptitude2);
        filingAptitudes.add(filingAptitude3);

        filingService.saveAptitude(filingAptitudes);
    }

    @Test
    public void queryFilingBaseInfoById() {
        FilingApp filingApp = filingService.queryFilingBaseInfoById("110120151117032955000000");
        System.out.println("filingApp=" + filingApp);
    }

    @Test
    public void queryShByApplyId() {
        List<FilingShareHolder> shList = filingService.queryShByApplyId("110120151117032955000000");
        System.out.println("shList=" + shList);
    }

    @Test
    public void queryAptByApplyId() {
        List<FilingAptitude> aptList = filingService.queryAptByApplyId("110120151117032955000000");
        System.out.println("aptList=" + aptList);
    }

    @Test
    public void isExistSameOrSimilar() {
        Integer flag = filingService.isExistSameOrSimilar("110120151117032955000000");
        System.out.println("flag=" + flag);
    }

    @Test
    public void submitFiling() {
        filingService.submitFiling("110120151125055802000000", "xxx");
    }

    @Test
    public void serviceQueryFiling() {
        Page page = new Page(1);
        FilingQueryParam filingQueryParam = new FilingQueryParam();
        filingQueryParam.setIsDisagreed(false);
        filingQueryParam.setSerResNo("12345000000");
        filingQueryParam.setEntName("TEST");
        filingQueryParam.setStartDate("2015-10-16");
        filingQueryParam.setEndDate("2015-12-19");
        // filingQueryParam.setEndDate(DateUtil.DateToString(new Date()));
        filingQueryParam.setCountryNo("124");
        filingQueryParam.setProvinceNo("124");
        filingQueryParam.setCityNo("124");
        filingQueryParam.setLinkman("TEST");
        filingQueryParam.setLinkmanTel("124");
        filingQueryParam.setShareHolder("124");
        filingQueryParam.setStatus(0);

        PageData<FilingAppInfo> pageData = filingService.serviceQueryFiling(filingQueryParam, page);
        System.out.println("serviceQueryFiling=" + pageData);
    }

    @Test
    public void serviceQueryDisagreedFiling() {
        Page page = new Page(1);
        FilingQueryParam filingQueryParam = new FilingQueryParam();
        filingQueryParam.setIsDisagreed(true);
        filingQueryParam.setSerResNo("12345000000");
        filingQueryParam.setEntName("TEST");
        filingQueryParam.setStartDate("2015-10-16");
        filingQueryParam.setEndDate("2015-12-19");
        // filingQueryParam.setEndDate(DateUtil.DateToString(new Date()));
        filingQueryParam.setCountryNo("124");
        filingQueryParam.setProvinceNo("124");
        filingQueryParam.setCityNo("124");
        filingQueryParam.setLinkman("TEST LINKMAN");
        filingQueryParam.setLinkmanTel("124");
        filingQueryParam.setShareHolder("124");
        filingQueryParam.setStatus(0);

        PageData<FilingAppInfo> pageData = filingService.serviceQueryDisagreedFiling(filingQueryParam, page);
        System.out.println("serviceQueryDisagreedFiling=" + pageData);

    }

    @Test
    public void platQueryFiling() {
        Page page = new Page(1);
        FilingQueryParam filingQueryParam = new FilingQueryParam();
        // filingQueryParam.setIsDisagreed(true);
        // filingQueryParam.setSerResNo("12345000000");
        // filingQueryParam.setEntName("TEST");
        // filingQueryParam.setStartDate("2015-10-16");
        // filingQueryParam.setEndDate("2015-10-19");
        // //filingQueryParam.setEndDate(DateUtil.DateToString(new Date()));
        // // filingQueryParam.setCountryNo("124");
        // // filingQueryParam.setProvinceNo("124");
        // // filingQueryParam.setCityNo("124");
        // // filingQueryParam.setLinkman("Wade");
        // // filingQueryParam.setLinkmanTel("124");
        // // filingQueryParam.setShareHolder("124");
        // filingQueryParam.setStatus(0);
        PageData<FilingAppInfo> pageData = filingService.platQueryFiling(filingQueryParam, page);
        System.out.println("serviceQueryFiling=" + pageData);

    }

    @Test
    public void platQueryApprFiling() {

        Page page = new Page(1);
        FilingQueryParam filingQueryParam = new FilingQueryParam();
        filingQueryParam.setIsDisagreed(false);
        filingQueryParam.setOptCustId("00000000156163438270977024");
        filingQueryParam.setSerResNo("12345000000");
        filingQueryParam.setEntName("TEST");
        filingQueryParam.setStartDate("2015-10-16");
        filingQueryParam.setEndDate("2015-10-19");
        filingQueryParam.setCountryNo("124");
        filingQueryParam.setProvinceNo("124");
        filingQueryParam.setCityNo("124");
        filingQueryParam.setLinkman("Wade");
        filingQueryParam.setLinkmanTel("124");
        filingQueryParam.setShareHolder("124");
        filingQueryParam.setStatus(0);

        PageData<FilingAppInfo> pageData = filingService.platQueryApprFiling(filingQueryParam, page);
        System.out.println("serviceQueryFiling=" + pageData);

    }

    @Test
    public void platQueryDisagreedFiling() {

        Page page = new Page(1);
        FilingQueryParam filingQueryParam = new FilingQueryParam();
        filingQueryParam.setIsDisagreed(true);
        filingQueryParam.setSerResNo("12345000000");
        filingQueryParam.setEntName("TEST");
        filingQueryParam.setStartDate("2015-10-16");
        filingQueryParam.setEndDate("2015-10-19");
        // filingQueryParam.setEndDate(DateUtil.DateToString(new Date()));
        // filingQueryParam.setCountryNo("124");
        // filingQueryParam.setProvinceNo("124");
        // filingQueryParam.setCityNo("124");
        // filingQueryParam.setLinkman("Wade");
        filingQueryParam.setLinkmanTel("124");
        filingQueryParam.setShareHolder("124");
        filingQueryParam.setStatus(0);

        PageData<FilingAppInfo> pageData = filingService.platQueryDisagreedFiling(filingQueryParam, page);
        System.out.println("serviceQueryFiling=" + pageData);

    }

    @Test
    public void queryFilingById() {
        Map<String, Object> map = filingService.queryFilingById("110120151202040251000000");

        System.out.println("map=" + map);
    }

    @Test
    public void querySameItem() {
        FilingSameItem filingSameItem = filingService.querySameItem("110120151117032955000000");
        System.out.println("querySameItem=" + filingSameItem);
    }

    @Test
    public void disagreedReject() {
        filingService.disagreedReject("110120151117045400000000", "ttt", "testR");
    }

    @Test
    public void apprFiling() {
        FilingApprParam filingApprParam = new FilingApprParam();
        filingApprParam.setApplyId("110120151202040251000000");
        filingApprParam.setStatus(FilingStatus.FILING_APP_PASS.getCode());
        filingApprParam.setApprOperator("0000000000");
        filingService.apprFiling(filingApprParam);
    }

    @Test
    public void apprDisagreeFiling() {
        FilingApprParam filingApprParam = new FilingApprParam();
        filingService.apprDisagreeFiling(filingApprParam);
    }

    @Test
    public void deleteFilingById() {
        filingService.deleteFilingById("110120151127085939000000x");
    }

}
