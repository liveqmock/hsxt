/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.service.testcase.apply;

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

import com.gy.hsxt.bs.api.apply.IBSDeclareService;
import com.gy.hsxt.bs.bean.apply.DebtOpenSys;
import com.gy.hsxt.bs.bean.apply.DeclareAppInfo;
import com.gy.hsxt.bs.bean.apply.DeclareAptitude;
import com.gy.hsxt.bs.bean.apply.DeclareBank;
import com.gy.hsxt.bs.bean.apply.DeclareBizRegInfo;
import com.gy.hsxt.bs.bean.apply.DeclareEntBaseInfo;
import com.gy.hsxt.bs.bean.apply.DeclareLinkman;
import com.gy.hsxt.bs.bean.apply.DeclareQueryParam;
import com.gy.hsxt.bs.bean.apply.DeclareRegInfo;
import com.gy.hsxt.bs.bean.base.ApprParam;
import com.gy.hsxt.bs.bean.base.OptInfo;
import com.gy.hsxt.bs.common.enumtype.LegalCreType;
import com.gy.hsxt.bs.common.enumtype.apply.AptitudeType;
import com.gy.hsxt.bs.common.enumtype.apply.FeeFlag;
import com.gy.hsxt.bs.common.enumtype.apply.ResType;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.uc.bs.api.ent.IUCBsEntService;

@FixMethodOrder(MethodSorters.DEFAULT)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test/spring-global.xml")
// @TransactionConfiguration(transactionManager = "transactionManager",
// defaultRollback = false)
// @Transactional
public class DeclareServiceTestCase {

    @Autowired
    IBSDeclareService declareInfoService;

    @Autowired
    private IUCBsEntService entService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void createDeclare() {

        // 服务公司
        DeclareRegInfo declareInfo = new DeclareRegInfo();
        declareInfo.setToCustType(2);
        declareInfo.setToBuyResRange(5);
        declareInfo.setToEntCustName("测试成员yangjg001");
        declareInfo.setToEntEnName("by yangjg");
        declareInfo.setToEntResNo("06007009000");
        declareInfo.setToMResNo("06000000000");
        declareInfo.setToSelectMode(0);
        declareInfo.setFrEntCustId("0600700000020151230");
        declareInfo.setFrEntCustName("李智服务公司5");
        declareInfo.setFrEntResNo("06007000000");
        declareInfo.setSpreadEntCustId("0600700000020151230");
        declareInfo.setSpreadEntCustName("李智服务公司5");
        declareInfo.setSpreadEntResNo("06007000000");
        declareInfo.setCountryNo("156");
        declareInfo.setProvinceNo("44");
        declareInfo.setCityNo("440104");
        declareInfo.setOptCustId("06007000000000020151230");
        declareInfo.setOptName("0000（李智慧）");
        declareInfo.setToPnodeCustId("");
        declareInfo.setToPnodeResNo("");
        declareInfo.setToInodeResNo("");
        declareInfo.setApplyId("");
        declareInfoService.createDeclare(declareInfo);

        /*
         * { "applyId": "", "cityNo": "440104", "countryNo": "156",
         * "frEntCustId": "0600700000020151230", "frEntCustName": "李智服务公司5",
         * "frEntResNo": "06007000000", "optCustId": "06007000000000020151230",
         * "optEntName": "李智服务公司5", "optName": "0000（李智慧）", "provinceNo": "44",
         * "spreadEntCustId": "0600700000020151230", "spreadEntCustName":
         * "李智服务公司5", "spreadEntResNo": "06007000000", "toBuyResRange": 5,
         * "toCustType": 2, "toEntCustName": "测试成员yangjg001", "toEntEnName":
         * "by yangjg", "toEntResNo": "06007009000", "toInodeResNo": "",
         * "toMResNo": "06000000000", "toPnodeCustId": "", "toPnodeResNo": "",
         * "toSelectMode": 0 }
         */

    }

    @Test
    public void serviceModifyDeclare() {

        // DeclareRegInfo declareInfo = new DeclareRegInfo();
        // declareInfo.setApplyId("110120151124094810000000");
        // declareInfo.setToCustType(CustType.MEMBER_ENT.getCode());
        // declareInfo.setToEntCustName("生二X");
        // declareInfo.setToEntResNo("20123000002");
        //
        // // declareInfo.setFrEntCustId("111110000000000");
        // // declareInfo.setFrEntCustName("互生");
        // // declareInfo.setFrEntResNo("20123000000");
        // //
        // // declareInfo.setSpreadEntCustId("111110000000000");
        // // declareInfo.setSpreadEntCustName("互生");
        // // declareInfo.setSpreadEntResNo("20123000000");
        //
        // declareInfo.setCountryNo("124");
        // declareInfo.setProvinceNo("123457");
        // declareInfo.setCityNo("132456780");
        //
        // declareInfo.setOptCustId("Peter");
        //
        // declareInfoService.serviceModifyDeclare(declareInfo);

        DeclareRegInfo declareInfo = new DeclareRegInfo();
        declareInfo.setApplyId("110120160105192420000000");
        declareInfo.setToCustType(CustType.MEMBER_ENT.getCode());
        declareInfo.setToEntCustName("成员企业-重新提交申报");
        declareInfo.setToEntEnName("GDS");
        declareInfo.setToEntResNo("06001000008");
        declareInfo.setToBuyResRange(ResType.NORMAL_MEMBER_ENT.getCode());
        declareInfo.setFrEntCustId("06001000000163432404206592");
        declareInfo.setFrEntCustName("06001000000服务公司");
        declareInfo.setFrEntResNo("06001000000");
        declareInfo.setSpreadEntCustId("06001000000163432404206592");
        declareInfo.setSpreadEntCustName("06001000000服务公司");
        declareInfo.setSpreadEntResNo("06001000000");
        declareInfo.setCountryNo("156");
        declareInfo.setProvinceNo("44");
        declareInfo.setCityNo("440104");
        declareInfo.setOptCustId("Peter");
        declareInfo.setToMResNo("06000000000");
        declareInfo.setToPnodeCustId("06000000000");
        declareInfo.setToPnodeResNo("06000000000");
        declareInfo.setToInodeResNo("06000000000");
        declareInfo.setToInodeLorR(1);
        declareInfoService.serviceModifyDeclare(declareInfo);
    }

    @Test
    public void manageModifyDeclare() {

        DeclareRegInfo declareInfo = new DeclareRegInfo();
        declareInfo.setApplyId("110120151124094810000000");
        declareInfo.setToCustType(CustType.MEMBER_ENT.getCode());
        declareInfo.setToEntCustName("生二X");
        declareInfo.setToEntResNo("20123000002");

        // declareInfo.setFrEntCustId("111110000000000");
        // declareInfo.setFrEntCustName("互生");
        // declareInfo.setFrEntResNo("20123000000");
        //
        // declareInfo.setSpreadEntCustId("111110000000000");
        // declareInfo.setSpreadEntCustName("互生");
        // declareInfo.setSpreadEntResNo("20123000000");

        declareInfo.setCountryNo("124");
        declareInfo.setProvinceNo("123457");
        declareInfo.setCityNo("132456780");

        declareInfo.setOptCustId("Peter X");

        declareInfoService.manageModifyDeclare(declareInfo);
    }

    @Test
    public void createDeclareEnt() {
        // 服务公司
        // DeclareBizRegInfo declareBizRegInfo = new DeclareBizRegInfo();
        // declareBizRegInfo.setApplyId("110120151124023502000000");
        // declareBizRegInfo.setEntName("06001000000服务公司");
        // declareBizRegInfo.setEntAddress("深圳");
        // declareBizRegInfo.setLegalName("张三");
        // declareBizRegInfo.setLinkmanMobile("13800001234");
        // declareBizRegInfo.setEntType("私有");
        // declareBizRegInfo.setLicenseNo("111122223333");
        // declareBizRegInfo.setOrgNo("111111111111");
        // declareBizRegInfo.setTaxNo("222222222222");
        // declareBizRegInfo.setEstablishingDate("2010-01-01");
        // declareBizRegInfo.setLimitDate("2050-01-01");
        // declareBizRegInfo.setDealArea("IT");
        // declareBizRegInfo.setRemark("备注");
        // declareInfoService.createDeclareEnt(declareBizRegInfo);

        // // 托管企业
        // DeclareBizRegInfo declareBizRegInfo = new DeclareBizRegInfo();
        // declareBizRegInfo.setApplyId("110120151215200120000000");
        // declareBizRegInfo.setEntName("托管企业-创业");
        // declareBizRegInfo.setEntAddress("深圳福田区xxx路");
        // declareBizRegInfo.setLegalName("王五");
        // declareBizRegInfo.setLinkmanMobile("13632637934");
        // declareBizRegInfo.setEntType("私有");
        // declareBizRegInfo.setLicenseNo("333333333Lic");
        // declareBizRegInfo.setOrgNo("3333333Org");
        // declareBizRegInfo.setTaxNo("3333333Tax");
        // declareBizRegInfo.setEstablishingDate("2000-01-01");
        // declareBizRegInfo.setLimitDate("2050-01-01");
        // declareBizRegInfo.setDealArea("软件、硬件");
        // declareBizRegInfo.setOptRemark("备注");
        // declareInfoService.createDeclareEnt(declareBizRegInfo);

        // 成员企业
        DeclareBizRegInfo declareBizRegInfo = new DeclareBizRegInfo();
        declareBizRegInfo.setApplyId("110120160109162810000000");
        declareBizRegInfo.setEntName("生十三");
        declareBizRegInfo.setEntAddress("深圳福田区xxx路");
        declareBizRegInfo.setLegalName("王五");
        declareBizRegInfo.setLegalCreType(LegalCreType.ID_CARD.getCode());
        declareBizRegInfo.setLegalCreNo("360733199001011235");
        declareBizRegInfo.setLinkmanMobile("13632637934");
        declareBizRegInfo.setEntType("私有");
        declareBizRegInfo.setLicenseNo("333333333Lic");
        declareBizRegInfo.setOrgNo("3333333Org");
        declareBizRegInfo.setTaxNo("3333333Tax");
        declareBizRegInfo.setEstablishingDate("2000-01-01");
        declareBizRegInfo.setLimitDate("2050-01-01");
        declareBizRegInfo.setDealArea("软件、硬件");
        declareBizRegInfo.setOptRemark("备注");
        declareInfoService.createDeclareEnt(declareBizRegInfo);
    }

    @Test
    public void serviceModifyDeclareEnt() {

        DeclareBizRegInfo declareBizRegInfo = new DeclareBizRegInfo();
        declareBizRegInfo.setApplyId("110120151124094810000000");
        declareBizRegInfo.setEntName("生二XXX");
        declareBizRegInfo.setEntAddress("深圳");
        declareBizRegInfo.setLegalName("张三");
        declareBizRegInfo.setLinkmanMobile("13800001234");
        declareBizRegInfo.setEntType("私有");
        declareBizRegInfo.setLicenseNo("111122223333");
        declareBizRegInfo.setOrgNo("111111111111");
        declareBizRegInfo.setTaxNo("222222222222");
        declareBizRegInfo.setEstablishingDate("2010-01-01");
        declareBizRegInfo.setLimitDate("2050-01-01");
        declareBizRegInfo.setDealArea("IT");
        declareBizRegInfo.setOptRemark("备注");

        declareInfoService.serviceModifyDeclareEnt(declareBizRegInfo);
    }
    
    @Test
    public void queryDeclareHis() {

        System.out.println(declareInfoService.queryDeclareHis("110120160419095124000000",new Page(1)));
    }
    
    @Test
    public void serviceQueryDeclareHis() {

        System.out.println(declareInfoService.serviceQueryDeclareHis("110120160419095124000000",new Page(1)));
    }

    @Test
    public void createLinkman() {

        // 服务公司
        // DeclareLinkman declareLinkman = new DeclareLinkman();
        // declareLinkman.setApplyId("110120151124023502000000");
        // declareLinkman.setLinkman("李四");
        // declareLinkman.setPhone("0755-123456");
        // declareLinkman.setMobile("13800001234");
        // declareLinkman.setEmail("1@1.com");
        // declareLinkman.setZipCode("134564");
        // declareLinkman.setAddress("深圳福田");
        // declareLinkman.setJob("攻城狮");
        // declareLinkman.setCertificateFile("132153456456454");
        // declareLinkman.setQq("888888888");
        // declareLinkman.setWebSite("www.1111.com");
        // declareLinkman.setFax("1234156465456");
        // declareLinkman.setRemark("test 备注");
        // declareLinkman.setOptCustId("王五");
        // declareInfoService.createLinkman(declareLinkman);
        // // 托管企业
        // DeclareLinkman declareLinkman = new DeclareLinkman();
        // declareLinkman.setApplyId("110120151215200120000000");
        // declareLinkman.setLinkman("王五");
        // declareLinkman.setPhone("0755-123456");
        // declareLinkman.setMobile("13632637934");
        // declareLinkman.setEmail("3@3.com");
        // declareLinkman.setZipCode("134564");
        // declareLinkman.setAddress("深圳福田xxx路");
        // declareLinkman.setJob("攻城狮");
        // declareLinkman.setCertificateFile("333333333Cer");
        // declareLinkman.setQq("3333333QQ");
        // declareLinkman.setWebSite("www.333.com");
        // declareLinkman.setFax("33333333Fax");
        // declareLinkman.setOptRemark("test 备注");
        // declareLinkman.setOptCustId("王五");
        // declareInfoService.createLinkman(declareLinkman);
        // 成员企业
        DeclareLinkman declareLinkman = new DeclareLinkman();
        declareLinkman.setApplyId("110120160109162810000000");
        declareLinkman.setLinkman("王五");
        declareLinkman.setPhone("0755-123456");
        declareLinkman.setMobile("13632637934");
        declareLinkman.setEmail("xiaofl@gyist.com");
        declareLinkman.setZipCode("134564");
        declareLinkman.setAddress("深圳福田xxx路");
        declareLinkman.setJob("攻城狮");
        declareLinkman.setCertificateFile("333333333Cer");
        declareLinkman.setQq("3333333QQ");
        declareLinkman.setWebSite("www.333.com");
        declareLinkman.setFax("33333333Fax");
        declareLinkman.setOptRemark("test 备注");
        declareLinkman.setOptCustId("王五");
        declareInfoService.createLinkman(declareLinkman);
    }

    @Test
    public void updateLinkman() {
        DeclareLinkman declareLinkman = new DeclareLinkman();
        declareLinkman.setApplyId("110120151124094810000000");
        declareLinkman.setLinkman("李四");
        declareLinkman.setPhone("0755-123456");
        declareLinkman.setMobile("13800001234");
        declareLinkman.setEmail("1@1.com");
        declareLinkman.setZipCode("134564");
        declareLinkman.setAddress("深圳福田");
        declareLinkman.setJob("攻城狮");
        declareLinkman.setCertificateFile("132153456456454");
        declareLinkman.setQq("888888888");
        declareLinkman.setWebSite("www.1111.com");
        declareLinkman.setFax("1234156465456");
        declareLinkman.setOptRemark("test 备注");
        declareInfoService.serviceModifyLinkman(declareLinkman);
    }

    @Test
    public void manageModifyLinkman() {

        DeclareLinkman declareLinkman = new DeclareLinkman();
        declareLinkman.setApplyId("110120151231094757000000");
        declareLinkman.setLinkman("王五");
        declareLinkman.setPhone("0755-123456");
        declareLinkman.setMobile("13632637934");
        declareLinkman.setEmail("xiaofl@gyist.com");
        declareLinkman.setZipCode("134564");
        declareLinkman.setAddress("深圳福田xxx路");
        declareLinkman.setJob("攻城狮");
        declareLinkman.setCertificateFile("333333333Cer");
        declareLinkman.setQq("3333333QQ");
        declareLinkman.setWebSite("www.333.com");
        declareLinkman.setFax("33333333Fax");
        declareLinkman.setOptRemark("test 备注");
        declareLinkman.setOptCustId("王五");
        declareLinkman
                .setChangeContent("[{linkman：{old:王五,new:王小五}},{email：{old:xiaofl@gyist.com,new:xfl@gyist.com}}]");
        declareInfoService.manageModifyLinkman(declareLinkman);
    }

    @Test
    public void createBank() {
        // 服务公司
        // DeclareBank declareBank = new DeclareBank();
        // declareBank.setApplyId("110120151124023502000000");
        // declareBank.setBankCode("116");
        // declareBank.setAccountName("06001000000服务公司");
        // declareBank.setAccountNo("123123132");
        // declareBank.setCountryNo("156");
        // declareBank.setProvinceNo("44");
        // declareBank.setCityNo("440104");
        // declareBank.setIsDefault(true);
        // declareBank.setOptCustId("Pulo");
        // declareInfoService.createBank(declareBank);

        DeclareBank declareBank = new DeclareBank();
        declareBank.setApplyId("110120160109162810000000");
        declareBank.setBankCode("116");
        declareBank.setAccountName("成员企业-test");
        declareBank.setAccountNo("3333333333");
        declareBank.setCountryNo("156");
        declareBank.setProvinceNo("44");
        declareBank.setCityNo("440104");
        declareBank.setIsDefault(true);
        declareBank.setOptCustId("Pulo");
        try
        {
            declareInfoService.createBank(declareBank);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void serviceModifyBank() {
        DeclareBank declareBank = new DeclareBank();
        declareBank.setAccountId("20151124115831000000");
        declareBank.setBankCode("12349");
        declareBank.setAccountName("归一9");
        declareBank.setAccountNo("1231231329");
        declareBank.setCountryNo("321");
        declareBank.setProvinceNo("654321");
        declareBank.setCityNo("987654321");
        declareBank.setIsDefault(true);
        declareBank.setOptCustId("Pulo9");

        declareInfoService.serviceModifyBank(declareBank);
    }

    @Test
    public void manageSaveBank() {
        DeclareBank declareBank = new DeclareBank();
        declareBank.setAccountId("20151124115831000000");
        declareBank.setBankCode("12349");
        declareBank.setAccountName("归一9");
        declareBank.setAccountNo("1231231329");
        declareBank.setCountryNo("321");
        declareBank.setProvinceNo("654321");
        declareBank.setCityNo("987654321");
        declareBank.setIsDefault(true);
        declareBank.setOptCustId("Pulo9");
        declareInfoService.manageSaveBank(declareBank);
    }

    @Test
    public void serviceSaveDeclareAptitude() {
        // 服务公司
        // List<DeclareAptitude> declareAptitudes = new
        // ArrayList<DeclareAptitude>();
        // DeclareAptitude declareAptitude = new DeclareAptitude();
        // // declareAptitude.setAptitudeId("20151124121629000000");
        // declareAptitude.setApplyId("110120151124023502000000");
        // declareAptitude.setAptitudeType(AptitudeType.TAXPAYER_CRE.getCode());
        // declareAptitude.setFileId("t0000001");
        // declareAptitude.setAptitudeName("test apt name1");
        // declareAptitude.setOptCustId("TESTER 000xxxx");
        //
        // DeclareAptitude declareAptitude2 = new DeclareAptitude();
        // //declareAptitude2.setAptitudeId("20151016024920000001");
        // declareAptitude2.setApplyId("110120151124023502000000");
        // declareAptitude2.setAptitudeType(AptitudeType.BANK_ROLL_CRE.getCode());
        // declareAptitude2.setFileId("t0000002");
        // declareAptitude2.setAptitudeName("test apt name2");
        // declareAptitude2.setOptCustId("TESTER2");
        // // DeclareAptitude declareAptitude3 = new DeclareAptitude();
        // // //declareAptitude3.setAptitudeId("20151016024920000002");
        // // declareAptitude3.setApplyId("1120151019080458000000");
        // //
        // declareAptitude3.setAptitudeType(AptitudeType.ORGANIZER_CRE.getCode());
        // // declareAptitude3.setFileId("t0000002");
        // // declareAptitude3.setAptitudeName("test apt name3");
        // // declareAptitude3.setCreatedBy("TESTER3");
        //
        // declareAptitudes.add(declareAptitude);
        // declareAptitudes.add(declareAptitude2);
        // // declareAptitudes.add(declareAptitude3);
        // System.out.println(declareInfoService.serviceSaveDeclareAptitude(declareAptitudes));

        // 托管企业
        List<DeclareAptitude> declareAptitudes = new ArrayList<DeclareAptitude>();
        DeclareAptitude declareAptitude = new DeclareAptitude();
        declareAptitude.setApplyId("110120160116104206000000");
        declareAptitude.setAptitudeType(AptitudeType.LEGAL_REP_CRE_FACE.getCode());
        declareAptitude.setFileId("333333333001");
        declareAptitude.setAptitudeName("法人代表证件正面");
        declareAptitude.setOptCustId("王五");

        DeclareAptitude declareAptitude2 = new DeclareAptitude();
        declareAptitude2.setApplyId("110120160116104206000000");
        declareAptitude2.setAptitudeType(AptitudeType.LEGAL_REP_CRE_BACK.getCode());
        declareAptitude2.setFileId("333333333002");
        declareAptitude2.setAptitudeName("法人代表证件反面");
        declareAptitude2.setOptCustId("王五");

        DeclareAptitude declareAptitude3 = new DeclareAptitude();
        declareAptitude3.setApplyId("110120160116104206000000");
        declareAptitude3.setAptitudeType(AptitudeType.BIZ_LICENSE_CRE.getCode());
        declareAptitude3.setFileId("333333333003");
        declareAptitude3.setAptitudeName("营业执照正本扫描件");
        declareAptitude3.setOptCustId("王五");

        DeclareAptitude declareAptitude4 = new DeclareAptitude();
        declareAptitude4.setApplyId("110120160116104206000000");
        declareAptitude4.setAptitudeType(AptitudeType.ORGANIZER_CRE.getCode());
        declareAptitude4.setFileId("333333333004");
        declareAptitude4.setAptitudeName("组织机构代码证正本扫描件");
        declareAptitude4.setOptCustId("王五");

        DeclareAptitude declareAptitude5 = new DeclareAptitude();
        declareAptitude5.setApplyId("110120160116104206000000");
        declareAptitude5.setAptitudeType(AptitudeType.TAXPAYER_CRE.getCode());
        declareAptitude5.setFileId("333333333005");
        declareAptitude5.setAptitudeName("税务登记证正本扫描件");
        declareAptitude5.setOptCustId("王五");

        DeclareAptitude declareAptitude6 = new DeclareAptitude();
        declareAptitude6.setApplyId("110120160116104206000000");
        declareAptitude6.setAptitudeType(AptitudeType.VENTURE_BEFRIEND_PROTOCOL.getCode());
        declareAptitude6.setFileId("333333333006");
        declareAptitude6.setAptitudeName("创业帮扶协议");
        declareAptitude6.setOptCustId("王五");

        declareAptitudes.add(declareAptitude);
        declareAptitudes.add(declareAptitude2);
        declareAptitudes.add(declareAptitude3);
        declareAptitudes.add(declareAptitude4);
        declareAptitudes.add(declareAptitude5);
        declareAptitudes.add(declareAptitude6);
        System.out.println(declareInfoService.serviceSaveDeclareAptitude(declareAptitudes, new OptInfo()));
    }

    @Test
    public void submitDeclare() {
        OptInfo operator = new OptInfo();
        operator.setOptCustId("06001000000163432404206592");
        operator.setOptName("张三");
        operator.setOptEntName("06001000000服务公司");
        declareInfoService.submitDeclare("110120160116104206000000", operator);
    }

    @Test
    public void serviceQueryDeclare() {
        Page page = new Page(1, 100);
        DeclareQueryParam declareQueryParam = new DeclareQueryParam();
        // declareQueryParam.setCustType(CustType.TRUSTEESHIP_ENT.getCode());
        declareQueryParam.setDeclarerResNo("06001000000");
        // declareQueryParam.setOperatorCustId("2222222222");
        // declareQueryParam.setLinkman("离");
        // declareQueryParam.setLinkmanMobile("13410805752");
        // declareQueryParam.setStartDate("2016-01-01");
        // declareQueryParam.setEndDate("2016-10-10");
        // declareQueryParam.setEntName("创业");
        // declareQueryParam.setResNo("0600106");
        PageData<DeclareEntBaseInfo> pageData = declareInfoService.serviceQueryDeclare(declareQueryParam, page);
        System.out.println("pageData=" + pageData);
    }

    @Test
    public void serviceQueryDeclareAppr() {
        Page page = new Page(1);
        DeclareQueryParam declareQueryParam = new DeclareQueryParam();
        declareQueryParam.setCustType(CustType.MEMBER_ENT.getCode());
        declareQueryParam.setDeclarerResNo("10123000000");
        declareQueryParam.setOperatorCustId("2222222222");
        PageData<DeclareEntBaseInfo> pageData = declareInfoService.serviceQueryDeclareAppr(declareQueryParam, page);
        System.out.println("pageData=" + pageData);
    }

    @Test
    public void manageQueryDeclareAppr() {
        Page page = new Page(1);
        DeclareQueryParam declareQueryParam = new DeclareQueryParam();
        declareQueryParam.setCustType(CustType.MEMBER_ENT.getCode());
        // declareQueryParam.setDeclarerResNo("10123000000");
        declareQueryParam.setOperatorCustId("2222222222");
        declareQueryParam.setManageResNo("06000000000");
        // declareQueryParam.setStartDate("2015-10-10");
        // declareQueryParam.setEndDate("2016-10-10");
        PageData<DeclareEntBaseInfo> pageData = declareInfoService.manageQueryDeclareAppr(declareQueryParam, page);
        System.out.println("pageData=" + pageData);
    }

    @Test
    public void manageQueryDeclareReview() {
        Page page = new Page(1);
        DeclareQueryParam declareQueryParam = new DeclareQueryParam();
        declareQueryParam.setCustType(CustType.MEMBER_ENT.getCode());
        // declareQueryParam.setDeclarerResNo("10123000000");
        declareQueryParam.setOperatorCustId("2222222222");
        declareQueryParam.setManageResNo("06000000000");
        // declareQueryParam.setStartDate("2015-01-01");
        // declareQueryParam.setEndDate("2017-01-01");
        PageData<DeclareEntBaseInfo> pageData = declareInfoService.manageQueryDeclareReview(declareQueryParam, page);
        System.out.println("pageData=" + pageData);
    }

    @Test
    public void manageQueryDeclareList() {
        Page page = new Page(1);
        DeclareQueryParam declareQueryParam = new DeclareQueryParam();
        declareQueryParam.setCustType(CustType.MEMBER_ENT.getCode());
        // declareQueryParam.setDeclarerResNo("10123000000");
        declareQueryParam.setManageResNo("06000000000");
        PageData<DeclareEntBaseInfo> pageData = declareInfoService.manageQueryDeclareList(declareQueryParam, page);
        System.out.println("pageData=" + pageData);
    }

    @Test
    public void platQueryOpenSys() {
        Page page = new Page(1);
        DeclareQueryParam declareQueryParam = new DeclareQueryParam();
        declareQueryParam.setCustType(CustType.MEMBER_ENT.getCode());
        declareQueryParam.setOperatorCustId("132132132121");
        declareQueryParam.setResNo("0600100000");
        declareQueryParam.setEntName("test");
        declareQueryParam.setEndDate("2016-01-01");
        // declareQueryParam.setManageResNo("06000000000");
        PageData<DeclareEntBaseInfo> pageData = declareInfoService.platQueryOpenSys(declareQueryParam, page);
        System.out.println("pageData=" + pageData);
    }

    @Test
    public void serviceApprDeclare() {
        // 服务公司
        // ApprParam apprParam = new ApprParam();
        // apprParam.setApplyId("110120151124023502000000");
        // apprParam.setIsPass(true);
        // apprParam.setOptCustId("0000000015620151015");
        // apprParam.setOptName("张三");
        // apprParam.setOptEntName("中国大陆地区平台");
        // apprParam.setApprRemark("中国大陆地区平台内审");
        // declareInfoService.serviceApprDeclare(apprParam);
        ApprParam apprParam = new ApprParam();
        apprParam.setApplyId("110120160108184542000000");
        apprParam.setIsPass(true);
        apprParam.setOptCustId("06001000000163432404206592");
        apprParam.setOptName("张三");
        apprParam.setOptEntName("06001000000服务公司");
        apprParam.setApprRemark("生二服务公司内审");
        declareInfoService.serviceApprDeclare(apprParam);

    }

    @Test
    public void manageApprDeclare() {
        // 服务公司
        // ApprParam apprParam = new ApprParam();
        // apprParam.setApplyId("110120151124023502000000");
        // apprParam.setIsPass(true);
        // apprParam.setOptCustId("0600000000020151015");
        // apprParam.setOptName("张大三");
        // apprParam.setOptEntName("生六");
        // apprParam.setApprRemark("生六初审");
        // declareInfoService.managerApprDeclare(apprParam);
        // 托管企业
        // ApprParam apprParam = new ApprParam();
        // apprParam.setApplyId("110120151215200120000000");
        // apprParam.setIsPass(true);
        // apprParam.setOptCustId("06000000000163439192367104");
        // apprParam.setOptName("张大三");
        // apprParam.setOptEntName("06001000000服务公司");
        // apprParam.setApprRemark("管理公司公司初审");
        // declareInfoService.manageApprDeclare(apprParam);

        // 成员企业
        ApprParam apprParam = new ApprParam();
        apprParam.setApplyId("110120160109162810000000");
        apprParam.setIsPass(true);
        apprParam.setOptCustId("06000000000163439192367104");
        apprParam.setOptName("张大三");
        apprParam.setOptEntName("06001000000服务公司");
        apprParam.setApprRemark("管理公司公司初审");
        declareInfoService.manageApprDeclare(apprParam);
    }

    @Test
    public void manageReviewDeclare() {
        // //服务公司
        // ApprParam apprParam = new ApprParam();
        // apprParam.setApplyId("110120151126110201000000");
        // apprParam.setIsPass(true);
        // apprParam.setOptCustId("0600000000020151015");
        // apprParam.setOptName("张大大三");
        // apprParam.setOptEntName("生六");
        // apprParam.setApprRemark("生六复审");
        // declareInfoService.managerReviewDeclare(apprParam);
        // 托管企业
        ApprParam apprParam = new ApprParam();
        apprParam.setApplyId("110120160315200730000000");
        apprParam.setIsPass(true);
        apprParam.setOptCustId("06000000000163439192419328");
        apprParam.setOptName("0000-超级管理员");
        apprParam.setOptEntName("生六管理公司");
        apprParam.setApprRemark("sdfsdf");
        declareInfoService.manageReviewDeclare(apprParam);
    }

    @Test
    public void apprDebtOpenSys() {
        // OpenSys openSystem = new OpenSys();
        // openSystem.setApplyId("110120151202062256000000");
        // openSystem.setIsOpen(true);
        // openSystem.setOptCustId("06000000000163439192367104");
        // openSystem.setResFeeAllocMode(ResFeeAllocMode.ALLOCATE.getCode());
        // openSystem.setResFeeChargeMode(ResFeeChargeMode.ALL.getCode());
        // declareInfoService.prepareOpenSys(openSystem);

        DebtOpenSys openSys = new DebtOpenSys();
        openSys.setApplyId("110120160109162810000000");
        openSys.setFeeFlag(FeeFlag.DEBT.getCode());
        openSys.setOptCustId("06000000000163439192367104");
        openSys.setOptRemark("同意欠款");
        declareInfoService.apprDebtOpenSys(openSys);
    }

    @Test
    public void openSystem() {
        // OpenSys openSystem = new OpenSys();
        // openSystem.setApplyId("110120151202062256000000");
        // openSystem.setIsOpen(true);
        // openSystem.setOptCustId("06000000000163439192367104");
        // openSystem.setResFeeAllocMode(ResFeeAllocMode.ALLOCATE.getCode());
        // openSystem.setResFeeChargeMode(ResFeeChargeMode.ALL.getCode());
        //
        // declareInfoService.openSystem(openSystem);
        ApprParam apprParam = new ApprParam();
        apprParam.setApplyId("110120160122112943000000");
        apprParam.setIsPass(true);
        apprParam.setOptCustId("00000000156163438270977024");
        apprParam.setApprRemark("同意开系统");
        declareInfoService.openSystem(apprParam);
    }

    @Test
    public void queryDeclareAppInfoByApplyId() {
        DeclareAppInfo appInfo = declareInfoService.queryDeclareAppInfoByApplyId("110120160318113842000000");
        System.out.println(appInfo);
    }

    @Test
    public void queryDeclareRegInfoByApplyId() {
        System.out.println(declareInfoService.queryDeclareRegInfoByApplyId("110120151127110602000000"));
    }

    @Test
    public void queryDeclareBizRegInfoByApplyId() {
        System.out.println(declareInfoService.queryDeclareBizRegInfoByApplyId("110120151126110201000000"));
    }

    @Test
    public void queryLinkmanByApplyId() {
        System.out.println(declareInfoService.queryLinkmanByApplyId("110120151126110201000000xx"));
    }

    @Test
    public void queryBankByApplyId() {
        System.out.println(declareInfoService.queryBankByApplyId("110120151126110201000000"));
    }

    @Test
    public void queryAptitudeByApplyId() {
        System.out.println(declareInfoService.queryAptitudeByApplyId("110120151126110201000000"));
    }

    @Test
    public void getNotSerResNoList() {
        System.out.println(declareInfoService.getNotSerResNoList("06001010000", CustType.MEMBER_ENT.getCode(), null));
    }

    // @Autowired
    // private IOperationService operationService;
    //
    // @Test
    // public void queryApprHisByPage(){
    // Page page = new Page(1,2);
    // System.out.println(operationService.queryApprHisByPage("1120151019080458000000",ApprTable.T_BS_DECLARE_ENT_APPR.getCode(),page));
    // }

    @Test
    public void queryManageEntAndQuota() {
        System.out.println(declareInfoService.queryManageEntAndQuota("156", "45", "450100"));
    }

    @Test
    public void searchEntAllInfoByResNo() {
        System.out.println(entService.searchEntAllInfoByResNo("06001010000"));
    }

    @Test
    public void serviceQueryAuthCode() {
        System.out.println(declareInfoService.serviceQueryAuthCode("06010000000", null, null, null, new Page(1)));
    }

    @Test
    public void platQueryAuthCode() {
        System.out.println(declareInfoService.platQueryAuthCode(null, null, null, new Page(1)));
    }

    @Test
    public void platQueryDeclareList() {
        DeclareQueryParam declareQueryParam = new DeclareQueryParam();
        declareQueryParam.setCustType(CustType.TRUSTEESHIP_ENT.getCode());
        System.out.println(declareInfoService.platQueryDeclareList(declareQueryParam, new Page(1)));
    }

    @Test
    public void getServiceQuota() {
        System.out.println(declareInfoService.getServiceQuota("156", "31", "310000"));
    }

    @Test
    public void queryDeclareProgress() {
        System.out.println(declareInfoService.queryDeclareProgress("服务公司-10026"));
    }

    @Test
    public void queryIncrementList() {
        DeclareQueryParam declareQueryParam = new DeclareQueryParam();
        declareQueryParam.setStartDate("2015-11-01");
        declareQueryParam.setEndDate("2015-12-29");
        declareQueryParam.setEntName("");
        declareQueryParam.setResNo("");
        System.out.println(declareInfoService.queryIncrementList(declareQueryParam, new Page(1)));
    }

    @Test
    public void queryIncrement() {
        System.out.println(declareInfoService.queryIncrement("06001000000"));
    }

    @Test
    public void getSerResNos() {
        System.out.println(declareInfoService.getSerResNos("156", "44", "440104", "0609", new Page(1)));
    }

    @Test
    public void getEntResNos() {
        System.out.println(declareInfoService.getEntResNos("06001000000", CustType.MEMBER_ENT.getCode(),
                ResType.FREE_MEMBER_ENT.getCode(), "0600100901", new Page(1)));
    }

    @Test
    public void reApplyDeclare() {
        String oldApplyId = "110120151230174817000000";
        OptInfo optInfo = new OptInfo();
        optInfo.setOptCustId("0600700000020151230");
        System.out.println(declareInfoService.reApplyDeclare(oldApplyId, optInfo));
    }

    @Test
    public void queryResFeeAllocById() {
        System.out.println(declareInfoService.queryResFeeAllocById("110120160218183054000003"));
    }

}
