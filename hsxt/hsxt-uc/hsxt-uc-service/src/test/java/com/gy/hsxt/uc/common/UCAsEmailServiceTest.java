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

import com.gy.hsxt.common.utils.Base64Utils;
import com.gy.hsxt.uc.cache.service.CommonCacheService;
import com.gy.hsxt.uc.common.service.UCAsEmailService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/spring-uc.xml" })
public class UCAsEmailServiceTest {
	@Autowired
	UCAsEmailService emailService;
	@Autowired
	CommonCacheService commonCacheService;
	
	@Test
	public void sendMailForValidEmail() {
		String email = "tianxh@gyist.com";
		String userName = "18908621395";
		String entResNo = "";
		String userType = "1";
		
	}

	@Test
	public void sendMailForRestPwd() {
		// 02156630000162735796101120
		String custId = "06002110000164063559726080";
		String email = "huxl@gyist.com";
		String userType = "3";
	}



	@Test
	public void testemail(){
		String url = "hsxt.dev.gy.com:9200/getpassword/getpassword_3.html?";
		String custId1 = "0600211814120151207";
		String custId2 = "999162201803825152";
		String email = "tianxh@gyist.com";
		emailService.sendCarderMailForRestPwd(url, custId1, email);
		emailService.sendNoCarderMailForRestPwd(url, custId2, email);
	}
	
	@Test
	public void bindEmail(){
//		paramStr[{"custId":"0603911000020160412",
//			"email":"tianxh@gyist.com","random":"021250","userType":"4"}]
		
		
		String custId = "0603911000020160412";
		String validToken = "";
		String email = "tianxh@gyist.com";
		String userType = "1";
		emailService.bindEmail(custId, "021250", email, "4");
	}

	public static void main(String[] args) {
		String str1 = "eyJjdXN0SWQiOiIwNjAyODA4MDAwMDIwMTYwMzI0IiwiZW1haWwiOiJodWFuZ2d5QGd5aXN0LmNvbSIsInJhbmRvbSI6IjE5NDk4NCIsInVzZXJUeXBlIjoiNCJ9";
		String str2 = "eyJjdXN0SWQiOiIwNjAwMDAwMDAwMDE2MzQzOTE5MjM2NzEwNCIsImVtYWlsIjoiaHVhbmdneUBneWlzdC5jb20iLCJyYW5kb20iOiI2NzQ3OTUiLCJ1c2VyVHlwZSI6IjQifQ==";
		byte[] b1 = Base64Utils.decode(str1);
		byte[] b2 = Base64Utils.decode(str2);
    String paramStr1 = new String(b1);
    String paramStr2 = new String(b2);
    System.out.println("paramStr1["+paramStr1+"]");
    System.out.println("paramStr1["+paramStr2+"]");
	}
}