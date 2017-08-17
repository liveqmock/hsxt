///*
// * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
// *
// * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
// */
//
//package com.gy.hsxt.uc.ent.test;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import org.junit.Assert;
//import org.junit.FixMethodOrder;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.MethodSorters;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.alibaba.fastjson.JSON;
//import com.gy.hsxt.common.bean.Page;
//import com.gy.hsxt.common.bean.PageData;
//import com.gy.hsxt.common.constant.CustType;
//import com.gy.hsxt.common.utils.StringUtils;
//import com.gy.hsxt.uc.as.api.common.IUCAsPwdService;
//import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
//import com.gy.hsxt.uc.as.api.operator.IUCAsOperatorService;
//import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;
//import com.gy.hsxt.uc.as.bean.ent.AsBelongEntInfo;
//import com.gy.hsxt.uc.as.bean.ent.AsEntBaseInfo;
//import com.gy.hsxt.uc.as.bean.ent.AsEntContactInfo;
//import com.gy.hsxt.uc.as.bean.ent.AsEntExtendInfo;
//import com.gy.hsxt.uc.as.bean.ent.AsEntMainInfo;
//import com.gy.hsxt.uc.as.bean.ent.AsEntStatusInfo;
//import com.gy.hsxt.uc.as.bean.ent.AsEntTaxRate;
//import com.gy.hsxt.uc.as.bean.ent.AsEntUpdateLog;
//import com.gy.hsxt.uc.as.bean.ent.AsQueryBelongEntCondition;
//import com.gy.hsxt.uc.as.bean.ent.AsQueryEntCondition;
//import com.gy.hsxt.uc.as.bean.enumerate.AsEntUpdateLogTypeEnum;
//import com.gy.hsxt.uc.as.bean.operator.AsOperator;
//import com.gy.hsxt.uc.bs.api.ent.IUCBsEntService;
//import com.gy.hsxt.uc.bs.bean.ent.BsEntAllInfo;
//import com.gy.hsxt.uc.bs.bean.ent.BsEntInfo;
//import com.gy.hsxt.uc.bs.bean.ent.BsEntMainInfo;
//import com.gy.hsxt.uc.bs.bean.ent.BsEntRegInfo;
//import com.gy.hsxt.uc.bs.bean.ent.BsEntStatusInfo;
//import com.gy.hsxt.uc.bs.bean.ent.BsHkEntRegInfo;
//import com.gy.hsxt.uc.cache.service.CommonCacheService;
//import com.gy.hsxt.uc.ent.mapper.EntExtendInfoMapper;
//import com.gy.hsxt.uc.ent.service.AsEntGroupService;
//import com.gy.hsxt.uc.operator.test.OperatorServiceTest;
//import com.gy.hsxt.uc.util.StringEncrypt;
//
///**
// * 企业服务接口测试类
// * 
// * @Package: com.gy.hsxt.uc.ent.test
// * @ClassName: EntServiceTest
// * @Description: TODO
// * 
// * @author: huanggaoyang
// * @date: 2015-10-20 下午6:40:36
// * @version V1.0
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:spring/spring-uc.xml" })
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//public class EntServiceTest {
//
//	private static final String OPER_RES_NO = "50000200001";
//
//	private static final String TAX_RATE = "2.5";
//
//	private static final String OPERATOR_CUST_ID = "hwx81898";
//
//	public static final String ENT_RES_NO = "00000000156";
//
//	public static String ENT_CUST_ID = "46186000000162791814075392";
//
//	@Autowired
//	private IUCBsEntService bsEntService;
//	@Autowired
//	private IUCAsEntService asEntService;
//	@Autowired
//	private IUCAsOperatorService operatorService;
//
//	@Autowired
//	EntExtendInfoMapper extendInfoMapper;
//
//	@Autowired
//	CommonCacheService commonCacheService;
//
//	@Autowired
//	AsEntGroupService groupService;
//	@Autowired
//	IUCAsPwdService IUCAsPwdService;
//
//	@Test
//	public void addEnt() {
//		BsEntRegInfo regInfo = new BsEntRegInfo();
//		buildRegInfo(regInfo);
//		ENT_CUST_ID = bsEntService.addEnt(regInfo);
////		commonCacheService.removeEntCustIdCache("06071000001");
//	}
//
//	@Test
//	public void updateEntExtInfo() {
//		AsEntExtendInfo entExtInfo = new AsEntExtendInfo();
//		entExtInfo.setEntCustId("0601000000020151231");
//		entExtInfo.setEnsureInfo("wangjg001");
//		asEntService.updateEntExtInfo(entExtInfo, "06003000000000020160120");
//	}
//
//	@Test
//	public void test1_0_addHkEnt() {
//		// System.out.println("ssss");
//		// BsHkEntRegInfo regInfo = new BsHkEntRegInfo();
//		// buildRegInfo(regInfo);
//		// ENT_CUST_ID = bsEntService.addEnt(regInfo);
//		// System.out.println("entCustId-----------------" + ENT_CUST_ID);
//	}
//
//	// @Test
//	public void test1_2_getEntCustId() {
//		// cache.getChangeRedisUtil().flushDB();
//		// cache.getFixRedisUtil().flushDB();
//		// cache.getFixRedisUtil().remove("08186630000162206994400256");
//		ENT_CUST_ID = commonCacheService.findEntCustIdByEntResNo(ENT_RES_NO);
//	}
//
//	// @Test
//	public void test2_updateEntMainInfo() {
//		BsEntMainInfo entMainInfo = new BsEntMainInfo();
//		entMainInfo.setEntCustId(ENT_CUST_ID);
//		entMainInfo.setContactPhone("13081495920");
//		bsEntService.updateEntMainInfo(entMainInfo, OPERATOR_CUST_ID);
//		searchEntMainInfo();
//	}
//
//	@Test
//	public void searchEntMainInfo() {
//		BsEntMainInfo entMainInfo = bsEntService.searchEntMainInfo(ENT_CUST_ID);
//		System.out.println(JSON.toJSONString(entMainInfo));
//		Assert.assertEquals("13081495920", entMainInfo.getContactPhone());
//	}
//
//	// @Test
//	public void test3_updateEntBaseInfo() {
//		AsEntBaseInfo basInfo = new AsEntBaseInfo();
//		basInfo.setEntCustId(ENT_CUST_ID);
//		basInfo.setWebSite("wwww.bbb.net");
//		basInfo.setPostCode("123456");
//		asEntService.updateEntBaseInfo(basInfo, OPERATOR_CUST_ID);
//		searchEntBaseInfo();
//	}
//
//	public void searchEntBaseInfo() {
//		AsEntBaseInfo basInfo = asEntService.searchEntBaseInfo(ENT_CUST_ID);
//		System.out.println(JSON.toJSONString(basInfo));
//		Assert.assertEquals("wwww.bbb.net", basInfo.getWebSite());
//	}
//
//	// @Test
//	public void test4_updateEntStatusInfo() {
//		AsEntStatusInfo statusInfo = new AsEntStatusInfo();
//		statusInfo.setEntCustId(ENT_CUST_ID);
//		statusInfo.setOpenDate("2015-09-25 15:25:26");
//		statusInfo.setLastPointDate("2015-10-21 16:25:26");
//		statusInfo.setCountBuyCards(1000L);
//		statusInfo.setExpireDate("2016-12-21 16:25:26");
//		statusInfo.setIsReg(1);
//		asEntService.updateEntStatusInfo(statusInfo, OPERATOR_CUST_ID);
//	}
//
//	@SuppressWarnings("unused")
//	@Test
//	public void searchEntStatusInfo() {
//		AsEntStatusInfo statusInfo = asEntService
//				.searchEntStatusInfo("0600711000020151230");
//	}
//
//	// @Test
//	public void test5_bindMobile() {
//		String mobile = "15986815940";
//		asEntService.bindMobile(ENT_CUST_ID, mobile);
//
//		AsEntMainInfo mainInfo = asEntService.searchEntMainInfo(ENT_CUST_ID);
//		Assert.assertEquals(mobile, mainInfo.getContactPhone());
//	}
//
//	// @Test
//	public void test6_authMobile() {
//		String mobile = "15986815940";
//		String smsCode = "ssdfa";
//		asEntService.authMobile(ENT_CUST_ID, mobile, smsCode);
//
//		AsEntStatusInfo statusInfo = asEntService
//				.searchEntStatusInfo(ENT_CUST_ID);
//
//		Assert.assertEquals("0", statusInfo.getIsAuthEmail().toString());
//	}
//
//	// @Test
//	public void test7_bindEmail() {
//		String email = "bind@163.com";
//		asEntService.bindEmail(ENT_CUST_ID, email);
//
//		AsEntBaseInfo baseInfo = asEntService.searchEntBaseInfo(ENT_CUST_ID);
//		Assert.assertEquals(email, baseInfo.getContactEmail());
//	}
//
//	// @Test
//	public void test9_changeEntMainInfoStatus() {
//		String operator = OPERATOR_CUST_ID;
//		asEntService.changeEntMainInfoStatus(ENT_CUST_ID, "1", operator);
//
//		AsEntStatusInfo statusInfo = asEntService
//				.searchEntStatusInfo(ENT_CUST_ID);
//		Assert.assertEquals("1", statusInfo.getIsMaininfoChanged().toString());
//	}
//
//	// @Test
//	public void uest1_updateEntPointStatus() {
//		String operator = OPERATOR_CUST_ID;
//		asEntService.updateEntPointStatus(ENT_CUST_ID, "1", operator);
//
//		AsEntStatusInfo statusInfo = asEntService
//				.searchEntStatusInfo(ENT_CUST_ID);
//		Assert.assertEquals("1", statusInfo.getBaseStatus().toString());
//	}
//
//	// @Test
//	public void uest2_findEntCustIdByOperResNo() {
//		AsOperator oper = OperatorServiceTest.generateOperator(ENT_RES_NO,
//				ENT_CUST_ID, Integer.parseInt(OPER_RES_NO));
//		operatorService.addOper(oper, OPERATOR_CUST_ID);
//		// String entCustId =
//		// commonCacheService.findEntCustIdByOperResNo(OPER_RES_NO);
//		// Assert.assertEquals(ENT_CUST_ID, entCustId);
//	}
//
//	// @Test
//	public void uest3_findEntCustIdByEntResNo() {
//		String entCustId = commonCacheService
//				.findEntCustIdByEntResNo(ENT_RES_NO);
//		Assert.assertEquals(ENT_CUST_ID, entCustId);
//	}
//
//	// @Test
//	public void uest4_searchEntMainInfoForBs() {
//		BsEntMainInfo mainInfo = bsEntService.searchEntMainInfo(ENT_CUST_ID);
//		PrintClassProperty.printProerty(mainInfo);
//	}
//
//	// @Test
//	public void uest5_updateTaxRate() {
//		String taxRate = TAX_RATE;
//		bsEntService.updateTaxRate(ENT_CUST_ID, taxRate, OPERATOR_CUST_ID);
//		findTaxRate();
//	}
//
//	public void findTaxRate() {
//		String taxRate = bsEntService.findTaxRate(ENT_CUST_ID);
//		Assert.assertEquals(TAX_RATE, taxRate);
//	}
//
//	// @Test
//	public void uest6_SearcheAllInfo() {
//		BsEntAllInfo allInfo = bsEntService.searchEntAllInfo(ENT_CUST_ID);
//		System.out
//				.println("------------------------------------------------------------------"
//						+ JSON.toJSONString(allInfo));
//	}
//
//	@Test
//	public void uest7_listBelongEntInfo() {
//		Page page = new Page(1);
//		PageData<AsBelongEntInfo> listBelongEntInfo = asEntService
//				.listBelongEntInfo("00000000156",
//						CustType.SERVICE_CORP.getCode(), null, null, null, page);
//		System.out.println(JSON.toJSONString(listBelongEntInfo));
//	}
//
//	@Test
//	public void uest7_l_1_istBelongEntInfo() {
//		AsQueryBelongEntCondition condition = new AsQueryBelongEntCondition();
//		condition.setEntResNo("06001000000");
//		condition.setEntResNo("06007000000");
//
//		// condition.setBelongEntResNo("6007");
//		condition.setBelongEntName("智");
//		condition.setBelongEntContacts("李");
//		// condition.setBelongEntBaseStatus(AsEntBaseStatusEumn.NORMAL.getstatus());
//		condition.setBlongEntCustType(CustType.TRUSTEESHIP_ENT.getCode());// CustType.TRUSTEESHIP_ENT.getCode()
//		// condition.setEndDate("2015-12-31");
//		condition.setNoCancel("1");
//		PageData<AsBelongEntInfo> listBelongEntInfo = asEntService
//				.listBelongEntInfo(condition, new Page(1));
//		// 0600700000020151230
//		System.out.println(JSON.toJSONString(listBelongEntInfo));
//
//		try {
//			Thread.currentThread().sleep(5555);
//		} catch (Exception e) {
//		}
//	}
//
//	private void buildRegInfo(BsEntRegInfo regInfo) {
//		regInfo.setApplyEntResNo("04000000012");
//		regInfo.setEntResNo("06046009000");
//		regInfo.setEntName("深圳大展信息技术有限公司_ZSXSUU_001");
//		regInfo.setBuildDate("2015-08-01 00:00:00");
//		regInfo.setBusiArea(1000L);
//		regInfo.setBusiLicenseImg("营业执照图片dddddddddddsssff");
//		regInfo.setBusiLicenseNo("营业执照编号13454444");
//		regInfo.setCerDeposit("资金证明图片ddddddddddddddsssff");
//		regInfo.setCityCode("sz");
//		regInfo.setContactAddr("深圳市福田区福华路27号财富大厦");
//		regInfo.setContactDuty("业务经理");
//		regInfo.setContactEmail("tianxh@gyist.com");
//		regInfo.setContactPerson("李东");
////		regInfo.setContactPhone("15814759813");
//		regInfo.setCountryCode("CN");
//		regInfo.setCustType(2);
//		regInfo.setCreBackImg("法人代表背面图片ddddddddddddddsssff");
//		regInfo.setCreFaceImg("法人代表正面图片ddddddddddddddsssff");
//		regInfo.setCreName("李明昌");
//		regInfo.setCreNo("431102197706015689");
//		regInfo.setCreType(1);
//		regInfo.setEndDate("20150905");
//
//		regInfo.setEntNameEn("szsj");
//		regInfo.setEntRegAddr("深圳市福田区福华路27号财富大厦");
//
//		regInfo.setEntEmpNum(5000L);
//		regInfo.setIndustry(1);
//		regInfo.setIntroduction("深圳大展信信息技术有限公司 是一家好公司，优秀的软件企业");
//		regInfo.setLatitude("东经25C");
//		regInfo.setLongitude("西维36C");
//		regInfo.setNature("1");
//		regInfo.setOfficeFax("0755-2235430");
//		regInfo.setOfficeQq("864204356");
//		regInfo.setOfficeTel("075544003567");
//		regInfo.setOperator("0000");
//		regInfo.setOrgCodeImg("组织机构代图片：451333");
//		regInfo.setOrgCodeNo("组织机构代码证：4333");
//		regInfo.setPostCode("123456");
//		regInfo.setProvinceCode("gd");
//		regInfo.setWebSite("http://www.aaa.com");
//		regInfo.setSuperEntResNo("10000300003");
//		regInfo.setTaxNo("税务登记编号：5555555");
//		regInfo.setTaxRegImg("税务登记图片：5555555");
//		regInfo.setLogo("logo图片");
//		regInfo.setBusinessType(1);
//		if (regInfo instanceof BsHkEntRegInfo) {
//			BsHkEntRegInfo hkRegInfo = (BsHkEntRegInfo) regInfo;
//			hkRegInfo.setEntRegCode("企业注册编号：112");
//			hkRegInfo.setEntRegImg("企业注册证书：112");
//			hkRegInfo.setBusiRegCode("工商登记编号：5212");
//			hkRegInfo.setBusiRegImg("工商登记证书：5212");
//		}
//	}
//
//	@SuppressWarnings("unused")
//	@Test
//	public void searchEntAllInfoByResNo() {
//		BsEntAllInfo info = bsEntService.searchEntAllInfoByResNo("06010000000");
//		// AsEntAllInfo info =
//		// asEntService.searchEntAllInfo("06001000000163432404206592");
//
//	}
//
//	 @Test
//	public void listEntInfo() {
//		int custType = 4;
//		List<BsEntInfo> list = bsEntService.listEntInfo(custType);
//	}
//
//	@SuppressWarnings("unused")
//	@Test
//	public void searchEntContactInfo() {
//		String entCustId = "0800000000020151222";
//		AsEntContactInfo entContactInfo = asEntService
//				.searchEntContactInfo(entCustId);
//
//	}
//
//	@SuppressWarnings("unused")
//	@Test
//	public void searchEntExtInfo() {
//		AsEntExtendInfo entExtendInfo = asEntService
//				.searchEntExtInfo("0900000000020160304");
//		System.out.println("EntExtendInfo[" + JSON.toJSONString(entExtendInfo)
//				+ "]");
//		try {
//			Thread.currentThread().sleep(100000000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	@SuppressWarnings("unused")
//	@Test
//	public void updateMainInfoApplyInfo() {
//		
//		BsEntMainInfo entMainInfo = new BsEntMainInfo();
////		entMainInfo.
//		String entCustId = "0800101000020151222";
//		String operCustId = "更新人ID8866";
//		entMainInfo.setEntCustId(entCustId);
//		entMainInfo.setEntName("羔羊的猪头");
//		entMainInfo.setEntResNo("08001010000");
//		bsEntService.updateMainInfoApplyInfo(entMainInfo, operCustId);
//		
//		 BsEntStatusInfo  entStatusInfo  = bsEntService.searchEntStatusInfo(entCustId);
//	}
//
//	@Test
//	public void searchRegionalPlatform() {
//		asEntService.searchRegionalPlatform();
//	}
//
//	@SuppressWarnings("unused")
//	@Test
//	public void updateEntStatusInfo() {
////		String paramString = "777777";
////		AsEntStatusInfo paramAsEntStatusInfo = new AsEntStatusInfo();
////		paramAsEntStatusInfo.setEntCustId("06002110000164063559693312");
////		asEntService.updateEntStatusInfo(paramAsEntStatusInfo, paramString);
//		String operator = "123123";
//		String entCustId = "0604512000020160505";
//		BsEntStatusInfo entStatusInfo = new BsEntStatusInfo();
//		entStatusInfo.setEntCustId(entCustId);
//		entStatusInfo.setIsClosedEnt(1);
//		bsEntService.updateEntStatusInfo(entStatusInfo, operator);
//		
//	}
//
//	@SuppressWarnings("unused")
//	@Test
//	public void changeEntMainInfoStatus(){
//		String entCustId = "0600711000020151230";
//		String status = "1";
//		String operator = "id123123";
//		bsEntService.changeEntMainInfoStatus(entCustId, status, operator);
//		 BsEntStatusInfo  StatusInfo  = bsEntService.searchEntStatusInfo(entCustId);
//	}
//	@Test
//	public void updateEntMainInfo() {
//		
//		String operator = "21321213321";
//		BsEntMainInfo bsEntMainInfo = new BsEntMainInfo();
//		bsEntMainInfo.setEntCustId("06002110000164063559693312");// 企业客户号
//		bsEntMainInfo.setEntResNo("06002110000");// 企业互生号
//		bsEntMainInfo.setEntName("托管企业-创业资源4");// 企业注册名
//		bsEntMainInfo.setEntNameEn("ENG-12");// 企业英文名称
//		bsEntMainInfo.setEntRegAddr("深圳福田区xxx路");// 企业注册地址
//		bsEntMainInfo.setBusiLicenseNo("13333333333");// 企业营业执照号码
//		bsEntMainInfo.setBusiLicenseImg("333333333333");// 营业执照照片
//		bsEntMainInfo.setOrgCodeNo("55555555555555");// 组织机构代码证号
//		bsEntMainInfo.setOrgCodeImg("4444444444444");// 组织机构代码证图片
//		bsEntMainInfo.setTaxNo("6666666666666666");// 纳税人识别号
//		bsEntMainInfo.setTaxRegImg("555555555555");// 税务登记证正面扫描图片
//		bsEntMainInfo.setCreName("王五");// 法人代表
//		bsEntMainInfo.setCreType(1);// 法人证件类型
//		bsEntMainInfo.setCreNo("123132454564564");// 法人证件号码
//		bsEntMainInfo.setCreFaceImg("111111111111");// 法人身份证正面图片
//		bsEntMainInfo.setCreBackImg("22222222222222");// 法人身份证反面图片
//		bsEntMainInfo.setContactPerson("");// 联系人
//		bsEntMainInfo.setContactPhone("19632637944");// 联系人电话
//		bsEntMainInfo.setEntCustType(3);
//		bsEntService.updateEntMainInfo(bsEntMainInfo, operator);
//	}
//
//	@Test
//	public void batchQuery() {
//		List<String> list = new ArrayList<String>();
//		list.add("08186630000");
//		list.add("08001010000");
//		list.add("06013000000");
//		list.add("07186630000");
//		Map<String, String> map = asEntService.listEntCustId(list);
//		System.out.println("看看结果：" + JSON.toJSON(map));
//
//		try {
//			Thread.currentThread().sleep(10000000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void listEntCust() {
//		List<String> entResnoList = new ArrayList<String>();
//		entResnoList.add("06001000000");
//		entResnoList.add("08001010000");
//		entResnoList.add("09001050000");
//		Map<String, String> map = asEntService
//				.findEntCustIdByEntResNo(entResnoList);
//		System.out.println(JSON.toJSON(map));
//		try {
//			Thread.currentThread().sleep(10000000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void listEntTaxRate() {
//		commonCacheService.removeEntCustIdCache("06071000001");
//		String custId = commonCacheService.getEntCustIdCache("06071000001");
//
//	}
//
//	@Test
//	public void listBelongEntInfo() {
//		AsQueryBelongEntCondition condition = new AsQueryBelongEntCondition();
//		condition.setEntResNo("06033000000");
////		condition.setIsClosedEnt(1);
//		// condition.setBlongEntCustType(3);
//		// condition.setBeginDate("2015-12-21");
//		// condition.setEndDate("2015-12-21");
//		Page page = new Page(1, 10);
//		PageData<AsBelongEntInfo> p = asEntService.listBelongEntInfo(condition,
//				page);
//	}
//
//	@Test
//	public void findEntCustIdByEntResNo() {
//		List<String> entResnoList = new ArrayList<String>();
//		entResnoList.add("04000000000");
//		entResnoList.add("05000000000");
//		Map<String,String> resultMap = asEntService.listEntCustId(entResnoList);
//	}
//	
//	@Test
//	public void updateNoEffectEntTaxRateInfo() {
//		AsEntTaxRate entTaxRate = new AsEntTaxRate();
//		entTaxRate.setEntCustId("0601000000020151231");
//		entTaxRate.setEntResNo("06010000000");
//		entTaxRate.setEntTaxRate(new BigDecimal(0.55));
//		entTaxRate.setActiveDate(StringUtils.getNowTimestamp());
//		String operCustId = "12312311";
//		asEntService.updateNoEffectEntTaxRateInfo(entTaxRate, operCustId);
//	}
//
//	@Test
//	public void updateEntBaseInfo() {
//		String operator = "06002110000164063559726080";
//		AsEntBaseInfo entBaseInfo = new AsEntBaseInfo();
//		entBaseInfo.setOfficeTel("13588882222");
//		entBaseInfo.setBuildDate("2016-01-01");
//		entBaseInfo.setEndDate("2016-02-02");
//		entBaseInfo.setEntCustId("06002110000164063559693312");
//		entBaseInfo.setNature("vvk");
//		asEntService.updateEntBaseInfo(entBaseInfo, operator);
//	}
//
//	@Test
//	public void UpdateEntAndLog() {
//
//		String operCustId = "lv";
//		String regionalResNo = "00000000156";
//		String userName = "0000";
//		String pwd = "666666";
//		String secretKey = "1111111111111111";
//		// aes
//		String loginPwd = StringEncrypt.encrypt(pwd, secretKey);
//
//		String confirmId = IUCAsPwdService
//				.checkLoginPwdForCarderByReChecker(regionalResNo, userName,
//						loginPwd, secretKey, operCustId);
//		String entCustId = "07186630000163969843354624";
//		String updateField = "entCustName";
//		String updateFieldName = "企业名称";
//		String updateValueOld = "彩云国际2L";
//		String updateValueNew = "彩云国际2LV";
//		
//		AsEntExtendInfo asEntExtendInfo = new AsEntExtendInfo();
//		asEntExtendInfo.setEntCustId(entCustId);
//		asEntExtendInfo.setEntCustName(updateValueNew);
//		asEntExtendInfo.setEntCustNameEn("szsj L");
//
//		AsEntUpdateLog asEntUpdateLog = new AsEntUpdateLog();
//		asEntUpdateLog.setLogType(3);
//		asEntUpdateLog.setUpdateField(updateField);
//		asEntUpdateLog.setUpdateFieldName(updateFieldName);
//		asEntUpdateLog.setUpdateValueOld(updateValueOld);
//		asEntUpdateLog.setUpdateValueNew(updateValueNew);
//		// 测试修改企业名称
//		AsEntUpdateLog asEntUpdateLog2 = new AsEntUpdateLog();
//		asEntUpdateLog2.setLogType(3);
//		asEntUpdateLog2.setUpdateField("entCustNameEn");
//		asEntUpdateLog2.setUpdateFieldName("企业英文名称");
//		asEntUpdateLog2.setUpdateValueOld("szsj");
//		asEntUpdateLog2.setUpdateValueNew("szsj L");
//		
//		ArrayList logList = new ArrayList();
//		logList.add(asEntUpdateLog);
//		logList.add(asEntUpdateLog2);
//		
//		String retCustId = asEntService.UpdateEntAndLog(asEntExtendInfo,
//				logList, operCustId, confirmId);
//
//		System.out.println(JSON.toJSONString(retCustId));
//		// 测试查询修改日志列表
//		Object ret = asEntService.listEntUpdateLog(entCustId,null, null);
//
//		System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
//		System.out.println(JSON.toJSONString(ret));
//		System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
//
//	}
//	
//	@Test
//	public void UpdateEntAccountAndLog() {
//
//		String operCustId = "lv";
//		String regionalResNo = "00000000156";
//		String userName = "0000";
//		String pwd = "666666";
//		String secretKey = "1111111111111111";
//		// aes
//		String loginPwd = StringEncrypt.encrypt(pwd, secretKey);
//
//		String confirmId = IUCAsPwdService
//				.checkApsReCheckerLoginPwd(regionalResNo, userName,
//						loginPwd, secretKey, operCustId);
//		String entCustId = "07186630000163969843354624";
//		AsBankAcctInfo asBankAcctInfo = new AsBankAcctInfo();
//		asBankAcctInfo.setBankAccNo("12345");
//		asBankAcctInfo.setBankAccName("户名1");
//		asBankAcctInfo.setBankBranch("bankBranch");
//		asBankAcctInfo.setBankCardType("2");
//		asBankAcctInfo.setBankCode("Code");
//		asBankAcctInfo.setBankName("bankName");
//		asBankAcctInfo.setCustId(entCustId);
//
//		AsEntUpdateLog asEntUpdateLog = new AsEntUpdateLog();
//
//		asEntUpdateLog.setLogType(AsEntUpdateLogTypeEnum.ADD_ACC.ordinal());
//		asEntUpdateLog.setUpdateField("bankAccNo");
//		asEntUpdateLog.setUpdateFieldName("银行账号");
////		asEntUpdateLog.setUpdateValueOld(null);
//		asEntUpdateLog.setUpdateValueNew("12345");
//
//		AsEntUpdateLog asEntUpdateLog2 = new AsEntUpdateLog();
//
//		asEntUpdateLog2.setLogType(AsEntUpdateLogTypeEnum.ADD_ACC.ordinal());
//		asEntUpdateLog2.setUpdateField("bankAccName");
//		asEntUpdateLog2.setUpdateFieldName("账号户名");
////		asEntUpdateLog2.setUpdateValueOld(null);
//		asEntUpdateLog2.setUpdateValueNew("户名1");
//		//... 其他银行字段
//
//		
//		
//		ArrayList logList = new ArrayList();
//		logList.add(asEntUpdateLog);
//		logList.add(asEntUpdateLog2);
//		//... 其他银行字段
//		String retCustId=null;
////		retCustId = asEntService.UpdateEntAccountAndLog(asBankAcctInfo,
////				logList, AsEntUpdateLogTypeEnum.ADD_ACC.ordinal(),operCustId, confirmId);
//
//		System.out.println(JSON.toJSONString(retCustId));
//		// 测试查询修改日志列表
//		Object ret = asEntService.listEntUpdateLog(entCustId,"账号", null);
//
//		System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
//		System.out.println(JSON.toJSONString(ret));
//		System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
//
//	}
//
//	// 0600500900020160106
//	@Test
//	public void listEntUpdateLog() {
//		// 测试查询修改日志列表
//		Page page = new Page(1, 10);
//		Object ret = asEntService.listEntUpdateLog("0600500900020160106",null, page);
//		System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
//		System.out.println(JSON.toJSONString(ret));
//		System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
//	}
//
//	@Test
//	public void batchUpdateEntStatusInfo() {
//
//        //刚到期的年费企业
//        List<BsEntStatusInfo> infos = new ArrayList<>();
//        //同步年费状态
//        BsEntStatusInfo bsEntStatusInfo = new BsEntStatusInfo();
//        bsEntStatusInfo.setEntCustId("0600102000020151215");//0600102000020151215
//        bsEntStatusInfo.setIsOweFee(1);//1-欠费 0-未欠费
//        bsEntStatusInfo.setExpireDate("2016-03-09 00:00:00");
//        infos.add(bsEntStatusInfo);
//        bsEntService.batchUpdateEntStatusInfo(infos);
//		
//		
//		List<BsEntStatusInfo> list = new ArrayList<BsEntStatusInfo>();
//		BsEntStatusInfo b1 = new BsEntStatusInfo();
//		BsEntStatusInfo b2 = new BsEntStatusInfo();
//		b1.setEntCustId("0600711000020151230");
//		b2.setEntCustId("0600100000620151231");
//		b1.setExpireDate("2013-11-11 11:11:11");
//		b2.setExpireDate("2013-11-11 11:11:11");
//		b1.setIsOweFee(1);
//		b2.setIsOweFee(1);
//		list.add(b1);
//		list.add(b2);
//		bsEntService.batchUpdateEntStatusInfo(list);
//	}
//	
//	@Test
//	public void isEntExist(){
//		String entName="06001000000服务公司";
//		Integer custType =4;
//		boolean ret=asEntService.isEntExist(entName, custType);
//		System.out.println("HHHHHHHHHHHHHHH");
//		System.out.println(ret);
//		System.out.println("HHHHHHHHHHHHHHH");
//		System.exit(0);
//	}
//	
//	@Test
//	public void getbatch(){
//		AsQueryEntCondition condition = new AsQueryEntCondition();
//		condition.setEntResNo("060000");
//		condition.setBeginDate("2014-04-01");
//		condition.setEndDate("2016-05-29");
//		Page page = new Page(1,10);
//		PageData<AsBelongEntInfo>   data = asEntService.listEntInfoByCondition(condition, page);
//	}
//
//	@Test
//	public void cencel(){
//		String entCustId = "0604600900020160418";
//		String operCustId = "0604600900000000000";
//		bsEntService.entCancel(entCustId, operCustId);
//	}
//}
