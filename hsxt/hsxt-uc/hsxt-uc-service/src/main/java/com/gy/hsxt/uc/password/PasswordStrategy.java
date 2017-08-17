/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.password;

import com.gy.hsxt.common.utils.StringUtils;

/**
 * 密码加密策略
 * 
 * @Package: com.gy.hsxt.uc.password
 * @ClassName: PasswordStrategy
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2016-1-19 上午11:01:19
 * @version V1.0
 */
public abstract class PasswordStrategy {

	private static PasswordStrategy ecPasswordStrategy = new PasswordEcStrategy();
	private static PasswordStrategy hsPasswordStrategy = new PasswordHsStrategy();
	private static PasswordStrategy generalPasswordStrategy = new PasswordGeneralStrategy();

	public static PasswordStrategy getInstantce(String version) {
		if (StringUtils.isBlank(version)) {
			version = "3";
		}
		if (version.equals("1")) {
			return ecPasswordStrategy;
		} else if (version.equals("2")) {
			return hsPasswordStrategy;
		}
		return generalPasswordStrategy;
	}

	/**
	 * 根据明文密码生成消费者密码
	 * 
	 * @param username
	 *            用户名
	 * @param pwd
	 *            密码明文
	 * @param saltValue
	 *            盐值
	 * @return
	 */
	public abstract String genConsumerForBlankPwd(String username,
			String blankPwd, String saltValue);

	/**
	 * 根据密码明文生成企业密码
	 * 
	 * @param pwd
	 *            密码明文
	 * @param saltValue
	 *            盐值
	 * @return
	 */
	public abstract String genEntForBlankPwd(String blankPwd, String saltValue);

	/**
	 * 根据MD5密码生成消费者密码
	 * 
	 * @param username
	 *            用户名
	 * @param md5Pwd
	 *            MD5密码
	 * @param saltValue
	 *            盐值
	 * @return
	 */
	public abstract String genConsumerForMd5Pwd(String username, String md5Pwd,
			String saltValue);

	/**
	 * 根据MD5密码生成企业密码
	 * 
	 * @param md5Pwd
	 *            MD5密码
	 * @param saltValue
	 *            盐值
	 * @return
	 */
	public abstract String genEntForMd5Pwd(String md5Pwd, String saltValue);

}
