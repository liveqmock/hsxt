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

import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.bean.common.AsConsumerLoginInfo;
import com.gy.hsxt.uc.as.bean.common.AsOperatorLoginInfo;
import com.gy.hsxt.uc.as.bean.enumerate.ChannelTypeEnum;
import com.gy.hsxt.uc.as.bean.result.AsCardHolderLoginResult;
import com.gy.hsxt.uc.as.bean.result.AsNoCardHolderLoginResult;
import com.gy.hsxt.uc.as.bean.result.AsOperatorLoginResult;
import com.gy.hsxt.uc.cache.CacheKeyGen;
import com.gy.hsxt.uc.cache.service.CommonCacheService;
import com.gy.hsxt.uc.common.service.CommonService;
import com.gy.hsxt.uc.common.service.UCAsLoginService;
import com.gy.hsxt.uc.util.CSPRNG;
import com.gy.hsxt.uc.util.StringEncrypt;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/spring-uc.xml" })
public class LoginServiceTest {
	@Autowired
	CommonCacheService commonCacheService;
	@Autowired
	CommonService commonService;
	@Autowired
	UCAsLoginService loginService;

	// @Test
	public void testIsLogin() {
		try {
			AsConsumerLoginInfo user = new AsConsumerLoginInfo();
			user.setResNo("06186010001");
			// loginService.isLogin(UserTypeEnum.CARDER, user,
			// ChannelTypeEnum.WEB);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 测试密码错误锁定帐户
	 * 
	 */
	// @Test
	public void cardHolderLoginFail() {
		String authKey = "UC.c_06002114635";
//		cacheUtil.getChangeRedisUtil().remove("UC.0600211463520151207");
//		cacheUtil.getChangeRedisUtil().remove(authKey);
//		for (int i = 0; i < 6; i++) {
//			cardHolderLogin();
//			UserLoginAuth auth = (UserLoginAuth) cacheUtil.getChangeRedisUtil()
//					.get(authKey, UserLoginAuth.class);
//			System.out.println(i + ", auth:" + auth);
//		}
	}

	/**
	 * 测试持卡用户登录
	 */
	 @Test
	public void cardHolderLogin() {
			String userName = "06033110129";
			AsConsumerLoginInfo loginInfo = new AsConsumerLoginInfo();
			loginInfo.setChannelType(ChannelTypeEnum.SYS);
			loginInfo.setLoginPwd("LEvqYwf7C44mVGARHC2PWw==");
			loginInfo.setResNo(userName);
			loginInfo.setRandomToken("7b8aea4fbdf8db98");
			AsCardHolderLoginResult r = loginService.cardHolderLogin(loginInfo);
			System.out.println(r);
	}
	
	/**
	 * 测试已登录持卡用户异地登录
	 */
	// @Test
	public void cardHolderNoLocalLogin() {
		try {
			// 清理缓存
//			 cacheUtil.getChangeRedisUtil().remove("UC.0600211463520151207");
//			 cacheUtil.getChangeRedisUtil().remove("UC.c_06002114635");

			// 组装数据
			String pwdMd5 = StringEncrypt.string2MD5("666666");
			String token = StringEncrypt.sha256(
					CSPRNG.generateRandom(SysConfig.getCsprLen())).substring(0,
					16);
			String pwd = StringEncrypt.encrypt(pwdMd5, token);
			String userName = "06002114635";

			AsConsumerLoginInfo loginInfo = new AsConsumerLoginInfo();
			loginInfo.setChannelType(ChannelTypeEnum.SYS);
			loginInfo.setLoginPwd(pwd);
			loginInfo.setResNo(userName);
			loginInfo.setRandomToken(token);
			AsCardHolderLoginResult r = loginService.cardHolderUnLocalLogin(loginInfo);
			System.out.println(r);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// @Test
	public void isUserLogin() {
		// 删除登录缓存
//		String key = CacheKeyGen.genLoginKey(ChannelTypeEnum.SYS,
//				"0600211463520151207");
//		cacheUtil.getChangeRedisUtil().remove(key);
//		key = CacheKeyGen
//				.genLoginKey(ChannelTypeEnum.SYS, "999162201803825152");
//		cacheUtil.getChangeRedisUtil().remove(key);
//
//		AsConsumerLoginInfo user = new AsConsumerLoginInfo();
//		user.setMobile("13521594444");
//		user.setChannelType(ChannelTypeEnum.SYS);
//		boolean isLogin = loginService
//				.isUserLogin(UserTypeEnum.NO_CARDER, user);
//		System.out.println(isLogin);
//
//		user.setResNo("06002114635");
//		isLogin = loginService.isUserLogin(UserTypeEnum.CARDER, user);
//		System.out.println(isLogin);

	}

	/**
	 * 测试密码错误锁定帐户
	 * 
	 */
	// @Test
	public void noCardHolderLoginFail() {
		String userName = "13521594444";

	}

	/**
	 * 非持卡人登录
	 */
	 @Test
	public void noCarderLogin() {
			String mobile = "15814759813";
			
			AsConsumerLoginInfo loginInfo = new AsConsumerLoginInfo();
			loginInfo.setChannelType(ChannelTypeEnum.WEB);
			loginInfo.setLoginPwd("LEvqYwf7C44mVGARHC2PWw==");
			loginInfo.setRandomToken("7b8aea4fbdf8db98");
			loginInfo.setMobile(mobile);
			AsNoCardHolderLoginResult r = loginService
					.noCardHolderLogin(loginInfo);
			System.out.println(r);
	
	}

	//@Test
	public void operatorLoginFail(){
		String custId = "06002110000164673321567232";
		String entResNo = "06002110000";
		String un = "0006";
		
//		String key = CacheKeyGen.genLoginKey(ChannelTypeEnum.SYS, custId);
//		cacheUtil.getChangeRedisUtil().remove(key);
//		key = CacheKeyGen.genOperatorLoginAuthKey(entResNo, un);
//		cacheUtil.getChangeRedisUtil().remove(key);
//		
//		for (int i = 0; i < 6; i++) {
//			operatorLoginUseUsername();
//			UserLoginAuth auth = (UserLoginAuth) cacheUtil.getChangeRedisUtil()
//					.get(key, UserLoginAuth.class);
//			System.out.println(i + ", auth:" + auth);
//		}
	}
	
	 @Test
	public void operatorLoginUseUsername() {

		AsOperatorLoginInfo loginInfo = new AsOperatorLoginInfo();
		loginInfo.setResNo("02000000000");
		loginInfo.setUserName("0000");
		loginInfo.setLoginPwd("LEvqYwf7C44mVGARHC2PWw==");
		loginInfo.setRandomToken("7b8aea4fbdf8db98");
		loginInfo.setChannelType(ChannelTypeEnum.SYS);
		loginInfo.setCustType("5");
			AsOperatorLoginResult rs = loginService.operatorLogin(loginInfo);
			System.out.println(rs);
	}



	 @Test
	public void operatorLoginUseResNo() {
		
		 
		AsOperatorLoginInfo loginInfo = new AsOperatorLoginInfo();
		loginInfo.setResNo("05001081815");
		loginInfo.setUserName("0000");
		loginInfo.setLoginPwd("LEvqYwf7C44mVGARHC2PWw==");
		loginInfo.setRandomToken("7b8aea4fbdf8db98");
		loginInfo.setChannelType(ChannelTypeEnum.SYS);
		loginInfo.setCustType("3");
			AsOperatorLoginResult rs = loginService.operatorLoginByCard(loginInfo);
			System.out.println(rs);
	}

	//@Test
	public void isOperatorLogin() {
		AsOperatorLoginInfo user = new AsOperatorLoginInfo();
		user.setChannelType(ChannelTypeEnum.WEB);
		user.setResNo("06002110000");
		user.setUserName("0006");
		try {
			Boolean rs = loginService.isOperatorLogin(user);
			System.out.println(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 操作员双密码登录
	 */
	// @Test
//	public void operatorLoginUse2Pwd(){
//		String custId = "06002110000164673321567232";
//		String entResNo = "06002110000";
//		String un = "0006";
//		String pwdMd5 = StringEncrypt.string2MD5("4444441");
//		String pwdMd52 = StringEncrypt.string2MD5("666666");
//		// 删除登录缓存
//		String key = CacheKeyGen.genLoginKey(ChannelTypeEnum.SYS, custId);
//		cacheUtil.getChangeRedisUtil().remove(key);
//		key = CacheKeyGen.genOperatorLoginAuthKey(entResNo, un);
//		cacheUtil.getChangeRedisUtil().remove(key);
//		String token = StringEncrypt.sha256(
//				CSPRNG.generateRandom(SysConfig.getCsprLen())).substring(0, 16);
//		;
//		String pwd = StringEncrypt.encrypt(pwdMd5, token);
//		String pwd2= StringEncrypt.encrypt(pwdMd52, token);
//		AsOperatorLoginInfo loginInfo = new AsOperatorLoginInfo();
//		loginInfo.setResNo(entResNo);
//		loginInfo.setUserName(un);
//		loginInfo.setLoginPwd(pwd);
//		loginInfo.setRandomToken(token);
//		loginInfo.setChannelType(ChannelTypeEnum.SYS);
//		loginInfo.setCustType("3");
//		try {
//			AsOperatorLoginResult rs = loginService.operatorLogin2(loginInfo,pwd2);
//			System.out.println(rs);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
