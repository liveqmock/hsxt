/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.as.api.common;

import com.gy.hsxt.common.exception.HsException;

/**
 * 邮件处理类
 * 
 * @Package: com.gy.hsxt.uc.as.api.common
 * @ClassName: IUCAsEmailService
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2015-10-19 下午2:34:35
 * @version V1.0
 */
public interface IUCAsEmailService {
	
	 /**
	 * 验证邮箱
	 * 
	 * @param randomToken
	 *            为验证邮件临时生成的随机token
	 * @return 重置密码凭证
	 * @throws HsException
	 */
	public String checkMail(String email, String validCode) throws HsException;
	/**
	 * 绑定邮箱（校验验证邮件token，校验通过，入库邮件信息）
	 * @param custId		客户号
	 * @param validToken	验证邮件token
	 * @param email			邮箱
	 * @param userType		用户类型	 非持卡人1 持卡人2 操作员3 企业 4
	 * @throws HsException
	 */
	public void bindEmail(String custId, String validToken, String email,String userType) throws HsException;
	
/**
 * 发送验证邮件（验证邮件）
 * 
 * @param email
 *            邮箱
 * @param userName
 *            用户名 非持卡人填手机号码、持卡人填互生号、操作员填操作员账号、企业不填
 * @param entResNo
 *            操作员填企业互生号、企业填企业互生号，其他不填
 * @param userType
 *            用户类型 1：非持卡人 2：持卡人 3：操作员 4：企业
 *            和 3：操作员不支持
 * @param custType
 *         	  hsxt-common 中的枚举类 CustType
 * @throws HsException
 *      java.lang.String, java.lang.String, java.lang.String)
 */
public void sendMailForValidEmail(String email, String userName,
		String entResNo, String userType, Integer custType) throws HsException;

/**
 * 发送验证邮件（重置登录密码）
 * 
 * @param userName
 *            用户名 非持卡人填手机号码、持卡人填互生号、操作员填操作员账号、企业不填
 * @param entResNo
 *            企业互生号 操作员填企业互生号、企业填企业互生号，其他不填
 * @param userType
 *            用户类型 1：非持卡人 2：持卡人 3：操作员 4：企业
 * @throws HsException
 */
public void sendMailForRestPwd(String custId, String userType, String email,Integer custType)
		throws HsException;

/**
 * 发送持卡人验证邮件（重置持卡人登录密码,电商调用）
 * @param resetUrl
 * @param custId
 * @param email
 * @param custType
 *         	  hsxt-common 中的枚举类 CustType
 * @throws HsException
 */
public void sendCarderMailForRestPwd(String resetUrl,String custId,String email)
		throws HsException;

/**
 * 发送非持卡人验证邮件（重置非持卡人登录密码,电商调用）
 * @param resetUrl
 * @param custId
 * @param email
 * @param custType
 *         	  hsxt-common 中的枚举类 CustType
 * @throws HsException
 */
public void sendNoCarderMailForRestPwd(String resetUrl,String custId,String email)
		throws HsException;
}
