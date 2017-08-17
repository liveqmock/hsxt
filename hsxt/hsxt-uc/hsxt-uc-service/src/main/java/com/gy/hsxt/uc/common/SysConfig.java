/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.common;

import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.common.utils.StringUtils;

import static com.gy.hsi.ds.param.HsPropertiesConfigurer.getProperty;

/**
 * 互生用户中心 UC的 配置文件
 * 
 * @Package: com.gy.hsxt.uc.conf
 * @ClassName: SysConfig
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-11-3 上午10:50:36
 * @version V1.0
 */
@Service
public class SysConfig {
	private final static String MOUDLENAME = "com.gy.hsxt.uc.common.SysConfig";

	/**
	 * 验证码长度
	 */
	public static int getCheckCodeLen() {
		return Integer.parseInt(getProperty("check.code.len"));
	}

	/**
	 * 设置随机token的超时时间
	 * @return
	 */
	public static long getRandomTokenTimeout(){
		String time = getProperty("random.token.timeout");
		if(time == null || "".equals(time)){
			return 86400L; // 如果没有设置，默认24小时
		}
		return Long.valueOf(time);
	}
	
	public static String getTransPwdFailUnlockTime() {
		String time = getProperty("transpwd.fail.unlock.time");
		if (time == null || "".equals(time)) {
			return "23:59:59";
		}
		return time;
	}

	/**
	 * 系统名称
	 */
	public static String getSystemName() {
		return getProperty("system.name");
	}

	/**
	 * 第一个密保问题
	 * 
	 * @return
	 */
	public static String getPwdQuestionFirst() {
		return getProperty("pwd.question.first");
	}

	/**
	 * 第二个密保问题
	 * 
	 * @return
	 */
	public static String getPwdQuestionSecond() {
		return getProperty("pwd.question.second");
	}

	/**
	 * 第三个密保问题
	 * 
	 * @return
	 */
	public static String getPwdQuestionThird() {
		return getProperty("pwd.question.third");
	}

	/**
	 * 从配置文件获取属性值
	 * 
	 * @param propName
	 * @return
	 */
	public static String getPropValue(String propName) {
		return getProperty(propName);
	}

	/**
	 * @return 登录失败后解锁时间
	 */
	public static String getLoginFailUnlockTime() {
		String time = getProperty("login.fail.unlock.time");
		if (time == null || "".equals(time)) {
			return "23:59:59";
		}
		return time;
	}

	/**
	 * POS重新签到时间
	 * @return
	 */
	public static String getPosReSignInTime() {
		String time = getProperty("pos.resigin.in.time");
		if (time == null || "".equals(time)) {
			return  "23:59:59";
		}
		return time;
	}
	
	/**
	 * @return the cspr长度
	 */
	public static int getCsprLen() {
		String prop = getProperty("cspr.len");
		if(StringUtils.isBlank(prop)){
			return 8;
		}
		return Integer.parseInt(prop);
	}

	/**
	 * @return the 登录密码失败次数
	 */
	public static int getLoginPwdFailedTimes() {
		String prop = getProperty("login.pwd.failed.times");
		return Integer.parseInt(prop);
		// return loginPwdFailedTimes;
	}

	/**
	 * @return the 交易密码失败次数
	 */
	public static int getTransPwdFailedTimes() {
		String prop = getProperty("trans.pwd.failed.times");
		return Integer.parseInt(prop);
	}

	/**
	 * @return the WEB方式登录有效时间，单位：秒
	 */
	public static int getWebLoginValidTime() {
		String prop = getProperty("web.login.valid.time");
		return Integer.parseInt(prop);
	}

	/**
	 * @return the 其他方式登录有效时间，单位：秒
	 */
	public static int getOtherLoginValidTime() {
		String prop = getProperty("other.login.valid.time");
		return Integer.parseInt(prop);
	}

	/**
	 * @return the 登录失败后解锁时间，单位：小时
	 */
	public static int getLoginFailLockedTime() {
		String prop = getProperty("login.fail.locked.time");
		return Integer.parseInt(prop);
	}

	/**
	 * @return the 短信验证码有效时间，单位：秒
	 */
	public static int getSmCodeValidTime() {
		String prop = getProperty("sm.code.valid.time");
		return Integer.parseInt(prop);
	}

	/**
	 * @return the 短信验证码有效时间，单位：小时
	 */
	public static int getSmAuthCodeValidTime() {
		String prop = getProperty("sm.auth.code.valid.time");
		return Integer.parseInt(prop);
	}

	/**
	 * @return the email有效时间单位：小时
	 */
	public static int getEmailValidTime() {
		String prop = getProperty("email.valid.time");
		return Integer.parseInt(prop);
	}

	/**
	 * @return the持卡人 email验证邮箱链接
	 */
	public static String getPersontEmailValidUrl() {
		String prop = getProperty("email.valid.person.url");
		return (prop);
	}

	/**
	 * @return the 非持卡人 email验证邮箱链接
	 */
	public static String getNoCarderEmailValidUrl() {
		String prop = getProperty("email.valid.nocarder.url");
		if (StringUtils.isBlank(prop)) {
			return getPersontEmailValidUrl();
		}
		return (prop);
	}

	/**
	 * @return the企业 email验证邮箱链接
	 */
	public static String getCompanyEmailValidUrl() {
		String prop = getProperty("email.valid.company.url");
		return (prop);
	}

	/**
	 * @return the管理公司 email验证邮箱链接
	 */
	public static String getMcsEmailValidUrl() {
		String prop = getProperty("email.valid.mcs.url");
		return (prop);
	}

	/**
	 * @return the 服务公司email验证邮箱链接
	 */
	public static String getScsEmailValidUrl() {
		String prop = getProperty("email.valid.scs.url");
		return (prop);
	}

	/**
	 * @return the 地区平台email验证邮箱链接
	 */
	public static String getApsEmailValidUrl() {
		String prop = getProperty("email.valid.aps.url");
		return (prop);
	}

	/**
	 * @return the emailsign
	 */
	public static String getEmailSign() {
		String prop = getProperty("email.sign");
		return (prop);
	}
	
	/**
	 * @return the emailsign
	 */
	public static String getConsumerEmailSign() {
		String prop = getProperty("email.consumer.sign");
		return (prop);
	}
	
	
	/**
	 * @return the emailsign
	 */
	public static String getEntEmailSign() {
		String prop = getProperty("email.ent.sign");
		return (prop);
	}
	


	/**
	 * @return the email验证邮箱的标题
	 */
	public static String getEmailValidTitle() {
		String prop = getProperty("email.valid.title");
		return (prop);
	}

	/**
	 * @return the email找回密码的标题
	 */
	public static String getEmailFindPwdTitle() {
		String prop = getProperty("email.find.pwd.title");
		return (prop);
	}

	/**
	 * @return the Pos加密内容长度
	 */
	public static int getPosEncryptCntLen() {
		String prop = getProperty("pos.encrypt.cnt.len");
		return Integer.parseInt(prop);
	}

	/**
	 * @return the pos的mat匹配错误失败次数
	 */
	public static int getPosMatMatchFailTimes() {
		String prop = getProperty("pos.mat.match.fail.times");
		return Integer.parseInt(prop);
	}

	/**
	 * @return the 默认积分比例
	 */
	public static String getDefaultPointRate() {
		String prop = getProperty("default.point.rate");
		SystemLog.info(MOUDLENAME, "getDefaultPointRate", "积分比例值：" + prop);
		return (prop);
	}

	/**
	 * 密保匹配失败次数
	 * 
	 * @return
	 */
	public static int getPwdQuestionFailTimes() {
		String prop = getProperty("pwd.question.failed.times");
		return Integer.parseInt(prop);
	}

	/**
	 * 密保解锁失败时间
	 * 
	 * @return
	 */
	public static String getPwdQuestionFailUnlockTime() {
		String prop = getProperty("pwd.question.fail.unlock.time");
		return prop;
	}

	/**
	 * 获取系统实例名
	 * 
	 * @return
	 */
	public static String getSystemInstanceNo() {
		return getProperty("system.instance.no");
	}

	/**
	 * 获取管理员的默认帐号，默认为：0000
	 * 
	 * @return
	 */
	public static String getAdminAcctName() {
		String operNo = getProperty("admin.acct.name");	
		return operNo;
	}

	/**
	 * 获取管理员的名称，默认为：超级管理员
	 * 
	 * @return
	 */
	public static String getAdminUserName() {
		return getProperty("admin.username");
	}

	/**
	 * @return the email验证邮箱链接
	 */
	public static String getEmailCardHolderResetUrl() {
		String prop = getProperty("email.cardHolder.reset.url");
		return (prop);
	}

	/**
	 * @return the 持卡人 email验证邮箱链接
	 */
	public static String getManagerEmailCardHolderResetUrl() {
		String prop = getProperty("email.cardHolder.reset.url.mcs");
		return (prop);
	}

	public static String getEmailNocardHolderResetUrl() {
		String prop = getProperty("email.nocardHolder.reset.url");
		return (prop);
	}

	public static String getEmailOperatorResetUrl() {
		String prop = getProperty("email.operator.reset.url");
		return (prop);
	}

	public static String getIgnoreCheckCode() {
		String prop = getProperty("ignoreCheckCode");
		return (prop);
	}
	
	public static String getIsCloseDevice() {
		String prop = getProperty("isCloseDevice");
		return (prop);
	}
	
}
