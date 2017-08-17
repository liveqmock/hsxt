/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.uc.common;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.uc.cache.CacheKeyGen;
import com.gy.hsxt.uc.cache.service.CommonCacheService;
import com.gy.hsxt.uc.util.CSPRNG;
import com.gy.hsxt.uc.util.StringEncrypt;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-uc.xml" })
public class CommonCacheTest {

	@Autowired
	CommonCacheService commonCacheService;
	
//	@Test
//	/** 重置持卡人密码，登陆密码666666，交易密码88888888*/
//	public void testRoleCache() {
//		String roleId = "301";
//		//UC.role_url_1000
//		String key = CacheKeyGen.genRoleUrlCacheKey(roleId);
//		Set<Object> ret=cache.getFixRedisUtil().redisTemplate.opsForSet().members(key);
//		
//		System.out.println(key+ret);
//		String myUrl="/companyLogin/appGetUserResult/";
//		if(cache.getFixRedisUtil().redisTemplate.opsForSet().isMember(key,myUrl)){
//			System.out.println(myUrl+"pass,key="+key);
//		}else{
//			System.err.println(myUrl+ret+key);
//		}
//		
//		try {
//			Thread.currentThread().sleep(5555);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		
//	}

	public static void main(String[] args) {
		// for(int i=0;i<8;i++){
		String key = "261037b1a15f1f24";
		String md5Login = StringEncrypt.string2MD5("666666");//登录密码
		String md5Tran = StringEncrypt.string2MD5("88888888");//交易密码
		String aes = StringEncrypt.encrypt(md5Login, key);
		
		String traAes = StringEncrypt.encrypt(md5Tran, key);
		// String salt1 = CSPRNG.generateRandom(8);
	//	String salt1 = CSPRNG.generateRandom(8);
		String salt1 = "35664644";
		String salt2 = CSPRNG.generateRandom(8);
		String pwdLogin = md5Login + salt1;
		String pwdTran = md5Tran + salt2;
		System.out.println();
		System.out.println();
		pwdLogin = StringEncrypt.sha256(pwdLogin);
		pwdTran = StringEncrypt.sha256(pwdTran);
		System.out.println("MD5登录密码[" + md5Login + "]");
		System.out.println("AES秘钥（登录密码）[" + key + "]");
		System.out.println("AES登录密码[" + aes + "]");
		System.out.println("登录密码盐值[" + salt1 + "]");
		System.out.println("SHA256加密后的登录密码[" + pwdLogin + "]");
		System.out.println();
		System.out.println("MD5交易密码[" + md5Tran + "]");
		System.out.println("AES秘钥（交易密码）[" + key + "]");
		System.out.println("AES交易密码[" + traAes + "]");
		System.out.println("交易密码密码盐值[" + salt2 + "]");
		System.out.println("SHA256加密后的交易密码密码[" + pwdTran + "]");
		// }
	}
}
