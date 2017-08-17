package com.gy.hsxt.uc.common;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.uc.as.api.common.IUCAsEmailService;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.bs.bean.ent.BsEntRegInfo;
import com.gy.hsxt.uc.bs.bean.ent.BsHkEntRegInfo;
import com.gy.hsxt.uc.common.service.UCAsPwdService;
import com.gy.hsxt.uc.consumer.service.UCAsCardHolderService;
import com.gy.hsxt.uc.consumer.service.UCAsNoCardHolderService;
import com.gy.hsxt.uc.device.bean.CardReaderDevice;
import com.gy.hsxt.uc.device.mapper.CardReaderDeviceMapper;
import com.gy.hsxt.uc.device.mapper.PosDeviceMapper;
import com.gy.hsxt.uc.device.service.BsDeviceService;
import com.gy.hsxt.uc.ent.service.BsEntService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/spring-uc.xml" })
public class AsBindEmail {
	@Autowired
	IUCAsEmailService emailService;
	@Autowired
	UCAsCardHolderService cardHolderService;
	@Autowired
	UCAsNoCardHolderService noCardHolderService;
	
	@Autowired
	BsEntService entService;
	
	@Autowired
	UCAsPwdService pwdService;

	
	@Autowired
	BsDeviceService bsDeviceService;
	@Test
	public void sendMailForValidEmail(){
		String email = "tianxh@gyist.com";
        String userName = "0000";
        String entResNo = "06015000001";
        String mobile = "15814759813";
        String secretKey = "7b8aea4fbdf8db98";
        String loginPassword = "orywJlpuYwMblUrCxPCcng==";
        
//        cardHolderService.changeBindEmail("0603311011720160416", email, loginPassword, secretKey);
//        noCardHolderService.changeBindEmail("905176245738597376", email, loginPassword, secretKey);
//        
//        
//        emailService.sendMailForValidEmail(email, mobile, "", UserTypeEnum.NO_CARDER.getType(), CustType.NOT_HS_PERSON.getCode());
//		emailService.sendMailForValidEmail(email, "06033110117", "", UserTypeEnum.CARDER.getType(), CustType.PERSON.getCode());
//		emailService.sendMailForValidEmail(email, userName, "06032000001", UserTypeEnum.ENT.getType(), CustType.MEMBER_ENT.getCode());
//		emailService.sendMailForValidEmail(email, userName, "06035110000", UserTypeEnum.ENT.getType(), CustType.TRUSTEESHIP_ENT.getCode());
//		emailService.sendMailForValidEmail(email, userName, "06046000000", UserTypeEnum.ENT.getType(), CustType.SERVICE_CORP.getCode());
//		emailService.sendMailForValidEmail(email, userName, "10000000000", UserTypeEnum.ENT.getType(), CustType.MANAGE_CORP.getCode());
//		emailService.sendMailForValidEmail(email, userName, "00000000156", UserTypeEnum.ENT.getType(), CustType.AREA_PLAT.getCode());
//
//		
		
	//	emailService.sendMailForRestPwd("0603311011720160416", UserTypeEnum.NO_CARDER.getType(), email, CustType.NOT_HS_PERSON.getCode());
//		emailService.sendMailForRestPwd("0603311011720160416", UserTypeEnum.CARDER.getType(), email, CustType.PERSON.getCode());
//		emailService.sendMailForRestPwd("0603200000100000000", UserTypeEnum.ENT.getType(), email, CustType.MEMBER_ENT.getCode());
//		emailService.sendMailForRestPwd("0603511000000000000", UserTypeEnum.ENT.getType(), email, CustType.TRUSTEESHIP_ENT.getCode());
//		emailService.sendMailForRestPwd("0604600000000000000", UserTypeEnum.ENT.getType(), email, CustType.SERVICE_CORP.getCode());
//		emailService.sendMailForRestPwd("1000000000000000000", UserTypeEnum.ENT.getType(), email, CustType.MANAGE_CORP.getCode());
//		emailService.sendMailForRestPwd("0000000015600000000", UserTypeEnum.ENT.getType(), email, CustType.AREA_PLAT.getCode());
//		
//		emailService.sendNoCarderMailForRestPwd("http://", "905176245738597376", email);
		
//		BsEntRegInfo regInfo = new BsEntRegInfo();
//		buildRegInfo(regInfo);
//		entService.addEnt(regInfo);
//		
//		
//		BsEntRegInfo regInfo2 = new BsEntRegInfo();
//		buildRegInfo2(regInfo2);
//		entService.addEnt(regInfo2);
		
		
//		pwdService.resetLogPwdForCarderByReChecker
//		(apsResNo, userName, loginPwd, secretKey, perCustId, operCustId);
//		pwdService.resetLogPwdForOperatorByReChecker
//		(apsResNo, userName, loginPwd, secretKey, entResNo, operCustId);
//		pwdService.resetTradePwdForCarderByReChecker(apsResNo, userName, loginPwd, secretKey, perCustId, operCustId);
//		
		
		//		emailService.sendCompanyValidEmail(entResNo, userName, email, custType);
//		emailService.sendScsValidEmail(entResNo, userName, email, custType);		emailService.sendMcsValidEmail(entResNo, userName, email, custType);
//		emailService.sendApsValidEmail(entResNo, userName, email, custType);
//        emailService.sendCarderMailForRestPwd("hsxt.dev.gy.com:9200/getpassword/getpassword_3.html?", "0600211170820151207", email);
        
 //        emailService.sendNoCarderRestPwdEmail(mobile, email, custType);
 //      emailService.sendCarderRestPwdEmail(perResNo, email, custType);
  //      emailService.sendNoCarderRestPwdMailForEC(resetUrl, perCustId, email, custType);
 //       emailService.sendCarderRestPwdMailForEC(resetUrl, perCustId, email, custType);
   //     emailService.sendCompanyRestPwdEmail(entCustId, email, custType);
    //    emailService.sendScsRestPwdEmail(entCustId, email, custType);
//        emailService.sendMcsRestPwdEmail(entCustId, email, custType);
   //     emailService.sendApsRestPwdEmail(entCustId, email, custType);
		
	}
	
	private void buildRegInfo(BsEntRegInfo regInfo) {
		regInfo.setApplyEntResNo("04000000012");
		regInfo.setEntResNo("06047029000");
		regInfo.setEntName("深圳大展信息技术有限公司_ASXSUU_001");
		regInfo.setBuildDate("2015-08-01 00:00:00");
		regInfo.setBusiArea(1000L);
		regInfo.setBusiLicenseImg("营业执照图片dddddddddddsssff");
		regInfo.setBusiLicenseNo("营业执照编号13454444");
		regInfo.setCerDeposit("资金证明图片ddddddddddddddsssff");
		regInfo.setCityCode("sz");
		regInfo.setContactAddr("深圳市福田区福华路27号财富大厦");
		regInfo.setContactDuty("业务经理");
		regInfo.setContactEmail("tianxh@gyist.com");
		regInfo.setContactPerson("李东");
//		regInfo.setContactPhone("15814759813");
		regInfo.setCountryCode("CN");
		regInfo.setCustType(2);
		regInfo.setCreBackImg("法人代表背面图片ddddddddddddddsssff");
		regInfo.setCreFaceImg("法人代表正面图片ddddddddddddddsssff");
		regInfo.setCreName("李明昌");
		regInfo.setCreNo("431102197706015689");
		regInfo.setCreType(1);
		regInfo.setEndDate("20150905");

		regInfo.setEntNameEn("szsj");
		regInfo.setEntRegAddr("深圳市福田区福华路27号财富大厦");

		regInfo.setEntEmpNum(5000L);
		regInfo.setIndustry(1);
		regInfo.setIntroduction("深圳大展信信息技术有限公司 是一家好公司，优秀的软件企业");
		regInfo.setLatitude("东经25C");
		regInfo.setLongitude("西维36C");
		regInfo.setNature("1");
		regInfo.setOfficeFax("0755-2235430");
		regInfo.setOfficeQq("864204356");
		regInfo.setOfficeTel("075544003567");
		regInfo.setOperator("0000");
		regInfo.setOrgCodeImg("组织机构代图片：451333");
		regInfo.setOrgCodeNo("组织机构代码证：4333");
		regInfo.setPostCode("123456");
		regInfo.setProvinceCode("gd");
		regInfo.setWebSite("http://www.aaa.com");
		regInfo.setSuperEntResNo("10000300003");
		regInfo.setTaxNo("税务登记编号：5555555");
		regInfo.setTaxRegImg("税务登记图片：5555555");
		regInfo.setLogo("logo图片");
		regInfo.setBusinessType(1);
		if (regInfo instanceof BsHkEntRegInfo) {
			BsHkEntRegInfo hkRegInfo = (BsHkEntRegInfo) regInfo;
			hkRegInfo.setEntRegCode("企业注册编号：112");
			hkRegInfo.setEntRegImg("企业注册证书：112");
			hkRegInfo.setBusiRegCode("工商登记编号：5212");
			hkRegInfo.setBusiRegImg("工商登记证书：5212");
		}
	}
	
	private void buildRegInfo2(BsEntRegInfo regInfo) {
		regInfo.setApplyEntResNo("04000000012");
		regInfo.setEntResNo("06047019000");
		regInfo.setEntName("深圳大展信息技术有限公司_JSXSUU_001");
		regInfo.setBuildDate("2015-08-01 00:00:00");
		regInfo.setBusiArea(1000L);
		regInfo.setBusiLicenseImg("营业执照图片dddddddddddsssff");
		regInfo.setBusiLicenseNo("营业执照编号13454444");
		regInfo.setCerDeposit("资金证明图片ddddddddddddddsssff");
		regInfo.setCityCode("sz");
		regInfo.setContactAddr("深圳市福田区福华路27号财富大厦");
		regInfo.setContactDuty("业务经理");
//		regInfo.setContactEmail("tianxh@gyist.com");
		regInfo.setContactPerson("李东");
		regInfo.setContactPhone("15814759813");
		regInfo.setCountryCode("CN");
		regInfo.setCustType(2);
		regInfo.setCreBackImg("法人代表背面图片ddddddddddddddsssff");
		regInfo.setCreFaceImg("法人代表正面图片ddddddddddddddsssff");
		regInfo.setCreName("李明昌");
		regInfo.setCreNo("431102197706015689");
		regInfo.setCreType(1);
		regInfo.setEndDate("20150905");

		regInfo.setEntNameEn("szsj");
		regInfo.setEntRegAddr("深圳市福田区福华路27号财富大厦");

		regInfo.setEntEmpNum(5000L);
		regInfo.setIndustry(1);
		regInfo.setIntroduction("深圳大展信信息技术有限公司 是一家好公司，优秀的软件企业");
		regInfo.setLatitude("东经25C");
		regInfo.setLongitude("西维36C");
		regInfo.setNature("1");
		regInfo.setOfficeFax("0755-2235430");
		regInfo.setOfficeQq("864204356");
		regInfo.setOfficeTel("075544003567");
		regInfo.setOperator("0000");
		regInfo.setOrgCodeImg("组织机构代图片：451333");
		regInfo.setOrgCodeNo("组织机构代码证：4333");
		regInfo.setPostCode("123456");
		regInfo.setProvinceCode("gd");
		regInfo.setWebSite("http://www.aaa.com");
		regInfo.setSuperEntResNo("10000300003");
		regInfo.setTaxNo("税务登记编号：5555555");
		regInfo.setTaxRegImg("税务登记图片：5555555");
		regInfo.setLogo("logo图片");
		regInfo.setBusinessType(1);
		if (regInfo instanceof BsHkEntRegInfo) {
			BsHkEntRegInfo hkRegInfo = (BsHkEntRegInfo) regInfo;
			hkRegInfo.setEntRegCode("企业注册编号：112");
			hkRegInfo.setEntRegImg("企业注册证书：112");
			hkRegInfo.setBusiRegCode("工商登记编号：5212");
			hkRegInfo.setBusiRegImg("工商登记证书：5212");
		}
	}
}
