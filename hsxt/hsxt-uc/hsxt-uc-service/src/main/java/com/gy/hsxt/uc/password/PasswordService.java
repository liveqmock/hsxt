/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.password;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.password.bean.PasswordBean;
import com.gy.hsxt.uc.util.StringEncrypt;

@Component
public class PasswordService {
	private final static String MOUDLENAME = "com.gy.hsxt.uc.password.PasswordService";

	/**
	 * 对比密码
	 * 
	 * @param passwordBean
	 * @return
	 */
	public boolean matchBlankPassword(PasswordBean passwordBean) {
		String ver = passwordBean.getVersion();
		// 根据版本获取密码加密算法
		PasswordStrategy strategy = PasswordStrategy.getInstantce(ver);
		StringBuilder sb = new StringBuilder();
		String pwd = passwordBean.getPwd();
		String salt = passwordBean.getSaltValue();
		String password; //密码密文
		if (!passwordBean.isEnt()) {
			sb.append("调用消费者生成密码,");
			sb.append("用户名：[").append(passwordBean.getUsername()).append("]");
			// SystemLog.debug(MOUDLENAME, "matchBlankPassword", sb.toString());
			password = strategy.genConsumerForBlankPwd(
					passwordBean.getUsername(), pwd, salt);
		} else {
			sb.append("调用企业生成密码,");
			// SystemLog.debug(MOUDLENAME, "matchBlankPassword", sb.toString());
			password = strategy.genEntForBlankPwd(pwd, salt);
		}

		// 对比密码
		boolean ret = false;
		if(password!=null) {
			ret=password.equals(passwordBean.getOriginalPwd());
		}
		if (!ret) {
			sb.append("【密码校验失败】：密码原文：[").append(pwd).append("]");
			sb.append("密码版本：[").append(ver).append("]");
			sb.append("密码密文：[").append(password).append("]");
			sb.append("db密码：[").append(passwordBean.getOriginalPwd())
					.append("]");
			SystemLog.debug(MOUDLENAME, "matchBlankPassword", sb.toString());
		}
		return ret;
	}

	/**
	 * 对比AES密码
	 * 
	 * @param passwordBean
	 * @return
	 */
	public boolean matchAesPassword(PasswordBean passwordBean) {
		// 解密aes
		String pwd = StringEncrypt.decrypt(passwordBean.getPwd(),
				passwordBean.getRandomToken());
		// 使用md5加密
		// pwd = StringEncrypt.string2MD5(pwd);
		PasswordBean pb = new PasswordBean();
		BeanUtils.copyProperties(passwordBean, pb);
		pb.setPwd(pwd);
		return matchBlankPassword(pb);
	}

	/**
	 * 根据MD5密码生成DES的密码
	 * 
	 * @param passwordBean
	 *            密码为MD5密码
	 * @return
	 */
	public String genDesPwd(PasswordBean passwordBean) {
		PasswordStrategy strategy = PasswordStrategy.getInstantce(passwordBean
				.getVersion());
		String password;
		SystemLog.debug(
				MOUDLENAME,
				"genDesPwd",
				"密码版本：" + passwordBean.getVersion() + ", 用户名："
						+ passwordBean.getUsername());
		if (!passwordBean.isEnt()) {
			SystemLog.debug(MOUDLENAME, "genDesPwd", "调用消费者生成密码");
			password = strategy.genConsumerForMd5Pwd(
					passwordBean.getUsername(), passwordBean.getPwd(),
					passwordBean.getSaltValue());
		} else {
			SystemLog.debug(MOUDLENAME, "genDesPwd", "调用企业生成密码");
			password = strategy.genEntForMd5Pwd(passwordBean.getPwd(),
					passwordBean.getSaltValue());
		}
		return password;
	}

	/**
	 * 根据明文密码生成DES密码
	 * 
	 * @param passwordBean
	 * @return
	 */
	public String genDesByBlankPwd(PasswordBean passwordBean) {
		PasswordStrategy strategy = PasswordStrategy.getInstantce(passwordBean
				.getVersion());
		String password;
		String pwd = passwordBean.getPwd();
		String salt = passwordBean.getSaltValue();
		SystemLog.debug(
				MOUDLENAME,
				"genDesByBlankPwd",
				" 密码版本：" + passwordBean.getVersion() + ", 用户名："
						+ passwordBean.getUsername());
		if (!passwordBean.isEnt()) {
			SystemLog.debug(MOUDLENAME, "genDesByBlankPwd", "调用消费者生成密码");
			password = strategy.genConsumerForBlankPwd(
					passwordBean.getUsername(), passwordBean.getPwd(),
					passwordBean.getSaltValue());
		} else {

			password = strategy.genEntForBlankPwd(passwordBean.getPwd(),
					passwordBean.getSaltValue());
			String msg = "pwd[" + pwd + "]salt[" + salt + "]调用企业生成密码["
					+ password + "]";
			SystemLog.debug(MOUDLENAME, "genDesByBlank", msg);
		}
		return password;
	}

	/**
	 * 根据AES密码生成DES密码
	 * 
	 * @param passwordBean
	 * @return
	 */
	public String getDesByAesPwd(PasswordBean passwordBean) {
		// 解密aes
		String src_pwd = passwordBean.getPwd();
		String src_token = passwordBean.getRandomToken();
		String pwd = StringEncrypt.decrypt(src_pwd, src_token);
		PasswordBean pb = new PasswordBean();
		BeanUtils.copyProperties(passwordBean, pb);
		pb.setPwd(pwd);
		if (StringUtils.isBlank(pwd) || pwd.length() > 8) {
			SystemLog.warn(MOUDLENAME, "getDesByAesPwd", "src_pwd[" + src_pwd
					+ "]src_token[" + src_token + "]解密后密码[" + pwd + "]");
		} else {
//			SystemLog.debug(MOUDLENAME, "getDesByAesPwd", "解密后密码[" + pwd + "]");
		}
		return genDesByBlankPwd(pb);
	}
}
