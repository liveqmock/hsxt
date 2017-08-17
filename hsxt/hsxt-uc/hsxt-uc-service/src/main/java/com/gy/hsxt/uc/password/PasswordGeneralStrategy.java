/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.password;

import com.gy.hsxt.uc.util.StringEncrypt;

/**
 * 3.0版本加密方式
 * 
 * @Package: com.gy.hsxt.uc.password
 * @ClassName: PasswordEcStrategy
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2016-1-19 上午11:01:19
 * @version V1.0
 */
public class PasswordGeneralStrategy extends PasswordStrategy {

	/**
	 * 根据明文密码生成消费者密码
	 * @param username 用户名
	 * @param pwd 密码明文
	 * @param saltValue 盐值
 	 * @return
	 */
	@Override
	public String genConsumerForBlankPwd(String username, String pwd, String saltValue) {
		String md5 = StringEncrypt.string2MD5(pwd);
		return StringEncrypt.sha256(md5 + saltValue);
	}

	/**
	 * 根据MD5密码生成消费者密码
	 * @param username 用户名
	 * @param md5Pwd MD5密码
	 * @param saltValue 盐值
	 * @return
	 */
	public String genConsumerForMd5Pwd(String username, String md5Pwd,
			String saltValue){
		return StringEncrypt.sha256(md5Pwd + saltValue);
	}
	
	/**
	 * 根据密码明文生成企业密码
	 * @param pwd 密码明文
	 * @param saltValue 盐值
	 * @return
	 */
	@Override
	public String genEntForBlankPwd(String pwd, String saltValue) {
		String password = StringEncrypt.string2MD5(pwd);
		return genEntForMd5Pwd(password, saltValue);
	}

	
	
	/**
	 * 根据MD5密码生成企业密码
	 * @param md5Pwd MD5密码
	 * @param saltValue 盐值
	 * @return
	 */
	public String genEntForMd5Pwd(String md5Pwd, String saltValue){
		return StringEncrypt.sha256(md5Pwd + saltValue);
	}
}
