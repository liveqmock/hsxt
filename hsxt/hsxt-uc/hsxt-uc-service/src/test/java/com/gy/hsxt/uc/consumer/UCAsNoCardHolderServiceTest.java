/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.consumer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.uc.as.bean.consumer.AsNewMobileInfo;
import com.gy.hsxt.uc.as.bean.consumer.AsNoCardHolder;
import com.gy.hsxt.uc.as.bean.consumer.AsRegNoCardHolder;
import com.gy.hsxt.uc.cache.CacheKeyGen;
import com.gy.hsxt.uc.cache.service.CommonCacheService;
import com.gy.hsxt.uc.common.SysConfig;
import com.gy.hsxt.uc.consumer.bean.NoCardHolder;
import com.gy.hsxt.uc.consumer.mapper.NcRealNameAuthMapper;
import com.gy.hsxt.uc.consumer.service.UCAsNoCardHolderService;
import com.gy.hsxt.uc.util.CSPRNG;
import com.gy.hsxt.uc.util.StringEncrypt;

/**
 * 
 * @Package: com.gy.hsxt.uc.consumer
 * @ClassName: UCAsCNoCardHolderServiceTest
 * @Description: 非持卡人基本信息测试类
 * 
 * @author: tianxh
 * @date: 2015-10-21 上午11:21:12
 * @version V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/spring-uc.xml" })
public class UCAsNoCardHolderServiceTest {
	@Autowired
	private UCAsNoCardHolderService noCardHolderService;
	@Autowired
	CommonCacheService commonCacheService;

	
	@Autowired
	NcRealNameAuthMapper ncRealNameAuthMapper;
	
	@Autowired
    SysConfig config;
	 @Test
	    public void regNoCardConsumer() {
	    		String mobile = "15814759813";
	    		commonCacheService.addSmsCodeCache(mobile, "552156");
	 	        AsRegNoCardHolder asNoCardHolder = new AsRegNoCardHolder();
	 	        String secretKey = StringEncrypt.sha256(CSPRNG
	 	                .generateRandom(config.getCsprLen())).substring(0,16);
	 	        String aesPwd = StringEncrypt.encrypt(
	 	                StringEncrypt.string2MD5("777777"), secretKey);
	 	        asNoCardHolder.setLoginPwd(aesPwd);
	 	        asNoCardHolder.setMobile(mobile);
	 	        asNoCardHolder.setSmsCode("552156");
	 	        noCardHolderService.regNoCardConsumer(asNoCardHolder, secretKey);
	    }
	// @Test
	public void bindEmail() {
		String custId = "999161592514942976";
		String email = "ZhuiXunBBB@gmail.com";
	//	noCardHolderService.bindEmail(custId, email);
	}

	// @Test
	public void closeAccout() {
		String perCustId = "999161592514942976";
		String operCustId = "0500108181620151105";
		noCardHolderService.closeAccout(perCustId, operCustId);
	}

	 @Test
	public void findCustIdByMobile() {
		String mobile = "18721597301";
		String mobile_CustId = CacheKeyGen.genPerCustIdByMobile(mobile);
		String custId = commonCacheService.findCustIdByMobile(mobile);
	}

	// @Test
	public void validEmail() {
		String custId = "999161592514942976";
		String randomToken = StringEncrypt.sha256(CSPRNG.generateRandom(config.getCsprLen()));
		String emailKey = CacheKeyGen.genBindEmailCode(randomToken);
	}

	// @Test
	public void updateLoginInfo() {
		String custId = "999161592514942976";
		String loginIp = "192.168.1.128";
		noCardHolderService.updateLoginInfo(custId, loginIp,
				"2015-12-12 12:12:12");
	}

	 @Test
	public void findEmailByCustId() {
		String custId = "905176245738597376";
		String email = noCardHolderService.findEmailByCustId(custId);
	}

	 @SuppressWarnings("unused")
	@Test
	public void searchNoCardHolderInfoByCustId() {
		String custId = "905176245738597376";
		AsNoCardHolder noCardHolder = noCardHolderService.searchNoCardHolderInfoByCustId(custId);
	}

//	@Test
	public void searchNoCardHolderInfoByMobile() {
		String mobile = "18721597301";
		String custId = "999162703516467200";
		
		String gcust = "c_ph_cus_18721597301";
		AsNoCardHolder asNoCardHolder = noCardHolderService
				.searchNoCardHolderInfoByMobile(mobile);
	}
	

	
	@Test
	public void bindNewMobile(){
		String mobile = "13510913459";
		String smsCode = CSPRNG.generateRandom(6);
		AsNewMobileInfo newMobileInfo = new AsNewMobileInfo();
		newMobileInfo.setLoginPwd("Tf6bJthTP8tdioWi3ThsuA==");
		newMobileInfo.setNewMobile(mobile);
		newMobileInfo.setPerCustId("905176245738597376");
		newMobileInfo.setSecretKey("7ed570d9876dc18e");
		newMobileInfo.setSmsCode(smsCode);
		
		commonCacheService.addSmsCodeCache(mobile, smsCode);
		noCardHolderService.modifyMobile(newMobileInfo);
	}
	
	@Test
	public void changeBindEmail(){
		String perCustId = "905176245738597376";
		String email = "tianxh@gyist.com";
		String loginPassword = "Tf6bJthTP8tdioWi3ThsuA==";
		String secretKey = "7ed570d9876dc18e";
		noCardHolderService.changeBindEmail(perCustId, email, loginPassword, secretKey);
	}
	
	
}
