/*
\ * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.uc.as.bean.common.AsMainInfo;
import com.gy.hsxt.uc.as.bean.common.AsNoCarderMainInfo;
import com.gy.hsxt.uc.as.bean.common.AsResetLoginPwd;
import com.gy.hsxt.uc.as.bean.common.AsUpdateTradePwd;
import com.gy.hsxt.uc.cache.service.CommonCacheService;
import com.gy.hsxt.uc.common.service.UCAsMobileService;
import com.gy.hsxt.uc.common.service.UCAsPwdService;
import com.gy.hsxt.uc.consumer.service.UCAsNoCardHolderService;
import com.gy.hsxt.uc.util.CSPRNG;
import com.gy.hsxt.uc.util.StringEncrypt;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/spring-uc.xml" })
public class UCAsPwdServiceTest {
	@Autowired
	private UCAsPwdService pwdService;
	@Autowired
	UCAsMobileService mobileService;

	@Autowired
	UCAsNoCardHolderService noCardHolderService;

	@Autowired
	CommonCacheService commonCacheService;

	@Test
	public void updateLoginPwd() {
//		for (int i = 0; i < 5; i++) {
			String custId = "905176245738597376";
			String userType = "1";
			String secretKey = "1281d175dd555b43";
			String oldLoginPwd = "epujSyHyJkVgg72rIf/+KA==";// 正
			String newLoginPwd = "jyvypLU+FXEs7IFEGrcETA==";// 正
				pwdService.updateLoginPwd(custId, userType, oldLoginPwd,
						newLoginPwd, secretKey);
			
		}

//	}
	//
	// MD5交易密码[80f4189ca1c9d4d9]
	// AES秘钥（交易密码）[764c268478e70e69]
	// AES交易密码[nSDweIa37fw1E7wQKCkORME7irDpuMTFMVheo/2YMJY=]
	// 交易密码密码盐值[97426772]
	// SHA256加密后的交易密码密码[3c80a8f66d42cadc1053a20b0d41a0d3cdc8e2bbf2944c4da807f88a799eee76]

	@Test
	public void updateTradePwd() {
		String custId = "0600211814720151207";
		String userType = "2";
		String newTradePwd = "It5D06qtPuM7Z2L9+uTyOa6uwYToI2zZyHc2h1oYaDVSa3ztQSFrBqcgq+Z8JZlM";// 正
		String oldTradePwd = "Cl7HPDkNmKkqZc4+8NyehrVV/Ypud5Re/3kMrmqoGJEPib+K2xDNwC8J8KMkX2Y4";// 正
		// VDP2YuZ5EriidwnRX8AwJw0cAWpw81J9VPJ7/3Zx/ng1fk0NvaJGIYi3bjqHV/Za

		// String newTradePwd =
		// "nSDweIa37fw1E7wQKCkORME7irDpuMTFMVheo/2YMJY=";//反
		// String oldTradePwd =
		// "ENHNzqFydh3I85NGIUkUyD1UzKf/abbPCOjVEZYolc4=";//反
		String secretKey = "f6f69e4450521bac";
		String operCustId = "06002110000164063559726080";
		AsUpdateTradePwd params = new AsUpdateTradePwd();
		params.setUserType(userType);
		params.setCustId(custId);
		params.setNewTradePwd(newTradePwd);
		params.setOldTradePwd(oldTradePwd);
		params.setOperCustId(operCustId);
		params.setSecretKey(secretKey);
		pwdService.updateTradePwd(params);
	}

	@Test
	public void isSetTradePwd() {
		String custId = "0618601000620151111";
		String userType = "1";
		pwdService.isSetTradePwd(custId, userType);
	}

	@Test
	public void checkTradePwd() {
		
//		明文交易密码[12341234]
//				MD5交易密码[ed2b1f468c5f915f3f1cf75d7068baae]
//				AES秘钥（交易密码）[7ed570d9876dc18e]
//				AES交易密码[Txbz0iZ32gGMLwgUdIIg9Fpikxl/n5e+XbjwoDCbem1wC7bNbHI0/gSwYUMkrPMW]
//				交易密码密码盐值[16236336]
//				SHA256加密后的交易密码[cfcf702106baa2ffe714c8c668b8a3baecae57c3e066fd64c7e69731f091db43]
	
		String userType = "4";
		String custId = "0200000000020160416";
		pwdService.checkTradePwd(custId, "Txbz0iZ32gGMLwgUdIIg9Fpikxl/n5e+XbjwoDCbem1wC7bNbHI0/gSwYUMkrPMW", userType, "7ed570d9876dc18e");

	}

	@Test
	public void checkLoginPwd() {
		// clean();

		String custId = "06007010000000320160108";
		String loginPwd = "svtmdKiwzW2w8G1rbPAp03NTPDuzRxINDrtcytvPp2g=";
		String userType = "3";
		String secretKey = "e0ae82528737ed55";
		pwdService.checkLoginPwd(custId, loginPwd, userType, secretKey);
	}

	@Test
	public void setTradePwd() {
		// String custId = commonCacheService.findCustIdByResNo("05001081234");
		String custId = "999541824075256680";
		// String custId = "0600211816520151207";
		String tradePwd = "iL3EtAywDDayOb3Y8KrQQ/2vGG14yhO3CBw2yWVXP1dTCo/oq/Ee8tfF6bhW3XwZ";
		String secretKey = "bbab805e974fc175";
		String userType = "1";
		String operCustId = "06003000000000020160120";
		pwdService.setTradePwd(custId, tradePwd, userType, secretKey,
				operCustId);
	}

	@Test
	public void resetLogPwdForCarderByReChecker() {
		String loginPwd = "xBQZnOeDi0Gc7z0ZwD/POA==";
		String secretKey = "f6f69e4450521bac";
		String perCustId = "0600211814120151207";
		String userName = "0003";
		String regionalResNo = "00000000156";
		String operCustId = "00000000156000420160107";
		pwdService.resetLogPwdForCarderByReChecker(regionalResNo, userName,
				loginPwd, secretKey, perCustId, operCustId);
	}

	@Test
	public void resetLogPwdForOperatorByReChecker() {
		String loginPwd = "xBQZnOeDi0Gc7z0ZwD/POA==";
		String secretKey = "f6f69e4450521bac";
		String entResNo = "06001000006";
		String userName = "0003";
		String regionalResNo = "00000000156";
		String operCustId = "00000000156000420160107";
		pwdService.resetLogPwdForOperatorByReChecker(regionalResNo, userName,
				loginPwd, secretKey, entResNo, operCustId);
	}

	@Test
	public void resetTradePwdForCarderByReChecker() {
		String loginPwd = "KfL+t5+yefjlyVLioMfxqA==";
		String secretKey = "7ed570d9876dc18e";
		String userName = "0004";
		String regionalResNo = "00000000156";
		String perCustId = "0500108181120160415";
		String operCustId = "0000000015640120000";
		pwdService.resetTradePwdForCarderByReChecker(regionalResNo, userName,
				loginPwd, secretKey, perCustId, operCustId);
	}

	@Test
	public void resetEntTradePwdByReChecker() {
		String loginPwd = "KfL+t5+yefjlyVLioMfxqA==";
		String secretKey = "7ed570d9876dc18e";
		String userName = "0004";
		String regionalResNo = "00000000156";
		String entResNo = "06032000001";
		String operCustId = "0000000015640120000";
		pwdService.resetEntTradePwdByReChecker(regionalResNo, userName,
				loginPwd, secretKey, entResNo, operCustId);
	}

	@Test
	public void findCustIdByUserName() {
		pwdService.findCustIdByUserName("02001000001", "3");
	}

	@Test
	public void resetLoginPwdByCrypt() {
		// comm.encrypt(666666,'48094b22907470a8')= "JPxUrstfQ7ztOBuhLycajw=="
		// comm.encrypt(111111,'48094b22907470a8') = "tVZFTOOJ2bdIVXVaWUu5Xg=="
		String custId = "0600211170820151207";
		String newLoginPwd = "RJbG+udIj2Lv7AyHbNUCYQ==";
		String random = "61021695";
		String userType = "2";
		String secretKey = "7ea722dee9766a4a";
		AsResetLoginPwd obj = new AsResetLoginPwd();
		obj.setCustId(custId);
		obj.setNewLoginPwd(newLoginPwd);
		obj.setRandom(random);
		obj.setUserType(userType);
		obj.setSecretKey(secretKey);

		pwdService.resetLoginPwdByCrypt(obj);
		System.out.println("HHHHHHHHHHHHHHH");
		System.out.println("HHHHHHHHHHHHHHH");
		System.out.println("解密前pwd=[" + newLoginPwd + "]，key=[" + secretKey
				+ "]");
		newLoginPwd = StringEncrypt.decrypt(newLoginPwd, secretKey);
		System.out
				.println("StringEncrypt.decrypt(newLoginPwd, secretKey)解密后pwd=["
						+ newLoginPwd + "]");
		// md5
		newLoginPwd = StringEncrypt.string2MD5(newLoginPwd);
		System.out.println("解密后pwd,md5=" + newLoginPwd);
		// 还原为密码为 md5(密码)
		String salt = CSPRNG.generateRandom(SysConfig.getCsprLen());
		newLoginPwd = StringEncrypt.sha256(newLoginPwd + salt);
		System.out.println("解密后pwd,md5,加盐[" + salt + "]sha256=" + newLoginPwd);
	}

	@SuppressWarnings("unused")
	@Test
	public void checkMain() {
		AsMainInfo mainInfo = new AsMainInfo();
		mainInfo.setCerNo("433127198206095910");
		mainInfo.setCerType("1");
		mainInfo.setLoginPwd("Tf6bJthTP8tdioWi3ThsuA==");
		mainInfo.setLoginPwdVer("3");
		mainInfo.setPerCustId("905176008085064704");
		mainInfo.setRealName("田学化");
		mainInfo.setSecretKey("7ed570d9876dc18e");
		// pwdService.checkMainInfo(mainInfo);
		// String random = pwdService.checkNoCarderMainInfo(mainInfo);
	}

	@Test
	public void sendSmsCode() {
		String mobile = "15814759813";
		mobileService.sendNoCarderSmscode(mobile);
	}

	@Test
	public void checkNoCarderMain() {
		AsNoCarderMainInfo mainInfo = new AsNoCarderMainInfo();
		mainInfo.setLoginPwd("Tf6bJthTP8tdioWi3ThsuA==");
		mainInfo.setMobile("15814759813");
		mainInfo.setValidCode("580828");
		mainInfo.setPerCustId("905176008085064704");
		mainInfo.setSecretKey("7ed570d9876dc18e");
		String code = pwdService.checkNoCarderMainInfo(mainInfo);
	}

	@Test
	public void resetNoCarderTrade() {
		String secretKey = "7ed570d9876dc18e";
		String mobile = "15814759813";
		String random = "580828";
		String newTradePwd = "S9IvxkaqgnbFAqLkGNGer3ey2RyWfy/ffnohEY3J/FTtyXcUeXiMDLt5UR8ByrKq";
		pwdService
				.resetNoCarderTradepwd(mobile, random, newTradePwd, secretKey);
	}

}
