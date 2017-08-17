/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.druid.util.HexBin;
import com.gy.hsxt.keyserver.tools.Tools;
import com.gy.hsxt.uc.as.api.common.IUCAsDeviceSignInService;
import com.gy.hsxt.uc.util.RedisCache;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-uc.xml" })
public class DeviceSignServiceTest {

	@Autowired
	IUCAsDeviceSignInService signService;
	
	@Autowired
	private RedisCache<Object> cacheUtil;
	
	String entResNo = "10000100001";
	String entCustId = "10000100001160940429962240";
	String deviceNo = "pos0001";
	String operator = "amy";

//	@Test
//	public void posSign() {
//		// 组装缓存数据
//	//	String key = CacheKeyGen.genEntCustId(entResNo);
//		String key = "";
//		cacheUtil.getChangeRedisUtil().add(key, entCustId);
//
//		EntExtendInfo entExtendInfo = new EntExtendInfo();
//		entExtendInfo.setEntCustId(entCustId);
//		entExtendInfo.setModifyAcount(1);
//		entExtendInfo.setBuildDate(new Timestamp(System.currentTimeMillis()));
//		entExtendInfo.setCreateDate(new Timestamp(System.currentTimeMillis()));
//		entExtendInfo.setUpdateDate(new Timestamp(System.currentTimeMillis()));
//		entExtendInfo.fillRegParams(new BsEntRegInfo());
//		cacheUtil.getFixRedisUtil().add(entCustId, EntExtendInfo.class);
//
//		String key1 = CacheKeyGen.genStatusInfoKey(entCustId);
//		EntStatusInfo entStatusInfo = new EntStatusInfo();
//		// entStatusInfo.setApplyEntResNo("1");
//		entStatusInfo.setBaseStatus(1);
//		entStatusInfo.setCountBuyCards(1L);
//		// entStatusInfo.setCustType(1);
//		entStatusInfo.setEntCustId("1");
//		entStatusInfo.setEntResNo(entResNo);
//		entStatusInfo.setIsactive("Y");
//		entStatusInfo.setIsAuthEmail(1);
//		entStatusInfo.setIsAuthMobile(1);
//		entStatusInfo.setIsClosedEnt(1);
//		entStatusInfo.setIsKeyinfoChanged(1);
//		entStatusInfo.setIsOldEnt(1);
//		entStatusInfo.setIsOweFee(1);
//		entStatusInfo.setIsRealnameAuth(1);
//		entStatusInfo.setIsReg(1);
//		entStatusInfo.setPwdTrans("1");
//		entStatusInfo.setPwdTransSaltValue("a");
//		entStatusInfo.setPwdTransVer("1");
//		// entStatusInfo.setSuperEntResNo("1");
//		entStatusInfo.setUpdatedby("1");
//		cacheUtil.getChangeRedisUtil().add(key1, entStatusInfo);
//
//		// 测试
//		AsSignInInfo sign = new AsSignInInfo();
//		sign.setDeviceNo(deviceNo);
//		sign.setEntResNO(entResNo);
//		sign.setEntVer("1");
//		sign.setPwdVer("1");
//		sign.setUserName(operator);
//		signService.posSignIn(sign);
//	}

	public void padSign() {

	}

	public void cardReaderSign() {

	}

	 @Test
	public void encrypt() {
		String cnt = "test";
		byte[] data = Tools.getByteFromString(cnt, cnt.length());
		byte[] enRes = signService.getEncrypt(deviceNo, entResNo, data);
		System.out.println("加密后：" + HexBin.encode(enRes));

		byte[] deRes = signService.getDecrypt(deviceNo, entResNo, enRes);
		System.out.println("解密后：" + new String(deRes));
	}
	 
	 @Test
	 public void checkCardCode(){
		 String resNo="06002118120";
		 String pwd="11563916";
		 System.out.println("HHHHHHHHHHHHHHHHHHH");
		 System.out.println("HHHHHHHHHHHHHHHHHHH");
//		 cacheUtil.getChangeRedisUtil().flushDB();
//		 cacheUtil.getFixRedisUtil().flushDB();
		 signService.checkResNoAndCode(resNo, pwd);
		 System.out.println("HHHHHHHHHHHHHHHHHHH");
		 System.out.println("HHHHHHHHHHHHHHHHHHH");
		 
		 try {
//			Thread.sleep(10000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	 }
	 
	 @Test
	 public void checkMac(){
		 String str="BH&06001020000&0002&000001&000019&20160330183911&156&000000200000&1000&000000020000&000000200000&EB8AC091";
		 String resNo="06001020000";
		 String posNo="0002";
		 byte[] data=str.getBytes();
		 byte[] mac="B38D1567".getBytes();
		 
		 signService.checkMac(resNo, posNo, data, mac);
	 }

	public void check() {

	}

}
