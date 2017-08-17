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

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderStatusInfoService;
import com.gy.hsxt.uc.common.SysConfig;
import com.gy.hsxt.uc.util.CSPRNG;
import com.gy.hsxt.uc.util.StringEncrypt;

/**
 * 
 * @Package: com.gy.hsxt.uc.consumer
 * @ClassName: UCAsCardHolderStatusInfoServiceTest
 * @Description: TODO
 * 
 * @author: tianxh
 * @date: 2015-11-5 下午7:50:04
 * @version V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/spring-uc.xml" })
public class UCAsCardHolderStatusInfoServiceTest {
	@Autowired
	private IUCAsCardHolderStatusInfoService iUCAsCardHolderStatusInfoService;
	 
	@Autowired
    SysConfig config;
	
	@SuppressWarnings("static-access")
	@Test
	public void updateHsCardStatus(){
		String secretKey = StringEncrypt.sha256(CSPRNG.generateRandom(config.getCsprLen())).substring(0,16);
		String perCustId = "0600211309920151207";
		String lossReason = "互生卡弄丢了123456";
		String loginPassword = StringEncrypt.encrypt(StringEncrypt.string2MD5("777777"), secretKey);
		Integer status = 2;
		iUCAsCardHolderStatusInfoService.updateHsCardStatus(perCustId, loginPassword, secretKey, status,lossReason);
	}
	
//	@Test
	public void searchHsCardStatusInfoBycustId(){
		String custId = "0500108181520151105";
		Map<String,String> map = iUCAsCardHolderStatusInfoService.searchHsCardStatusInfoBycustId(custId);
	}
//	@Test
	public void isVerifiedMobile(){
		String custId = "0500108181620151105";
		String mobile = "18721597300";
		boolean status = iUCAsCardHolderStatusInfoService.isVerifiedMobile(custId, mobile);
	}
	
}
