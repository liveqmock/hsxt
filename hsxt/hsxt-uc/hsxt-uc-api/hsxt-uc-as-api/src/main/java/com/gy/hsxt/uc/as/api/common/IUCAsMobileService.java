/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.as.api.common;

import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * 手机短信操作处理类
 * @Package: com.gy.hsxt.uc.as.api.common
 * @ClassName: IUCAsMobileService
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2015-10-19 下午2:29:43
 * @version V1.0 
 */
public interface IUCAsMobileService {

    /**
     * 绑定手机（校验手机验证码，校验通过入库手机号码）
     * @param custId	客户号
     * @param mobile	手机号码
     * @param smsCode	手机验证码
     * @param userType	用户类型 	持卡人2  操作员3  4 企业
     * @throws HsException
     */
    public void bindMobile(String custId,String mobile,String smsCode,String userType) throws HsException;
    
    
    /**
	 * 验证手机短信验证码
	 * 
	 * @param mobile
	 *            手机号
	 * @param smsCode
	 *            短信验证码
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.common.IUCAsMobileService#checkSmsCode(java.lang.String,
	 *      java.lang.String)
	 */
	public String checkSmsCode(String mobile, String smsCode) throws HsException;
	


	
	/**
	 * 验证手机短信验证码(重置登录密码)
	 * 
	 * @param mobile
	 *            手机号
	 * @param smsCode
	 *            短信验证码
	 * @throws HsException
	 *      java.lang.String)
	 */
	public String checkSmsResetCode(String mobile, String smsCode)
			throws HsException;
	

	 /**
	  * 发送手机短信验证码（非持卡人注册、持卡人验证手机）
	  * @param custId	客户号（持卡人验证手机必填，非持卡人注册不填）
	  * @param mobile
	  * @param custType
	  *  	  hsxt-common 中的枚举类 CustType
	  * @throws HsException
	  */
	public void sendSmsCodeForValidMobile(String custId,String mobile,Integer custType) throws HsException;
	
	/**
	 * 发送手机验证码(重置登录密码) 
	 * @param custId
	 * @param userType
	 * @param mobile
	 * @param custType
	  *  	  hsxt-common 中的枚举类 CustType
	 * @throws HsException
	 */
	public void sendSmsCodeForResetPwd(String custId, String userType,
			String mobile,Integer custType) throws HsException;
	
	/**
	 * 发送授权码
	 * 
	 * @param entResNo
	 *            企业互生号
	 * @param custType
	 *  	  hsxt-common 中的枚举类 CustType
	 * @throws HsException
	 */
	public void sendAuthCodeSuccess(String entResNo,Integer custType) throws HsException;
	
	/**
	 * 发送手机验证码（重置非持卡人交易密码）
	 * @param mobile	手机号码
	 * @throws HsException
	 */
	public void sendNoCarderSmscode(String mobile)
			throws HsException;
}
