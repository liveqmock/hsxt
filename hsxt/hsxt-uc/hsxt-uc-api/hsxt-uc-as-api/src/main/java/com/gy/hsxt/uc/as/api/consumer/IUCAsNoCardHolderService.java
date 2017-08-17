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

package com.gy.hsxt.uc.as.api.consumer;



import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.consumer.AsNewMobileInfo;
import com.gy.hsxt.uc.as.bean.consumer.AsNoCardHolder;
import com.gy.hsxt.uc.as.bean.consumer.AsRegNoCardHolder;

/**
 * 
 * @Package: com.gy.hsxt.uc.as.api.consumer
 * @ClassName: IUAsCNoCardHolderService
 * @Description: 非持卡人基本信息管理（AS接入系统调用）
 * 
 * @author: tianxh
 * @date: 2015-10-19 下午4:50:32
 * @version V1.0
 */
public interface IUCAsNoCardHolderService {

	/**
	 * 非持卡人注册
	 * 
	 * @param asNoCardHolder
	 * @param secretKey  AES解密秘钥（随机报文校验token）
	 *            非持卡人注册信息
	 * @throws HsException
	 */
	public void regNoCardConsumer(AsRegNoCardHolder asNoCardHolder,String secretKey)
			throws HsException;


	/**
	 * 查询客户号
	 * 
	 * @param mobile
	 *            非持卡人手机号码
	 * @return 非持卡人的客户号
	 * @throws HsException
	 */
	public String findCustIdByMobile(String mobile) throws HsException;

	
	/**
	 * 是否注册
	 * @param mobile 手机号
	 * @return
	 */
	public boolean isRegister(String mobile);
	
	/**
	 * 销户
	 * 
	 * @param perCustId
	 *            非持卡人客户号
	 * @param operCustId
	 *            操作员客户号
	 * @throws HsException
	 */
	public void closeAccout(String perCustId, String operCustId)
			throws HsException;
	
	/**
	 * 通过手机号码查询非持卡人的信息
	 * @param mobile  手机号码
	 * @return
	 */
	public AsNoCardHolder searchNoCardHolderInfoByMobile(String mobile)throws HsException;
	
	/**
	 * 更新登录IP和时间
	 * @param custId	客户号
	 * @param loginIp	登录IP
	 * @param dateStr		登录日期   格式 yyyy-mm-dd hh:mm:ss
	 */
	public void updateLoginInfo(String custId,String loginIp,String dateStr)throws HsException;
	
	/**
	 * 	通过非持卡人客户号查询邮箱
	 * @param perCustId	非持卡人客户号
	 * @return		邮箱
	 * @throws HsException
	 */
	public String findEmailByCustId(String perCustId)throws HsException;
	
	/**
	 * 通过客户号查询非持卡人的信息
	 * @param mobile  手机号码
	 * @return
	 */
	public AsNoCardHolder searchNoCardHolderInfoByCustId(String perCustId)throws HsException;
	
	/**
	 * 修改登录密码
	 * @param custId 客户号
	 * @param oldPwd 旧登录密码
	 * @param newPwd  新登录密码
	 * @throws HsException
	 */
	public void modifyLoginPwd(String custId,String oldPwd, String newPwd) throws HsException;
	
	/**
	 * 设置新的登录密码
	 * @param userName	用户名
	 * @param newPwd	新登录密码
	 * @throws HsException
	 */
	public void setLoginPwd(String userName, String newPwd) throws HsException;
	
	/**
	 * 修改手机信息
	 * @param newMobileInfo
	 * @throws HsException
	 */
	public void modifyMobile(AsNewMobileInfo newMobileInfo) throws HsException;
	
	/**
	 * 非持卡人修改绑定邮箱
	 * @param perCustId 非持卡人客户号
	 * @param email	邮箱信息
	 * @param loginPassword  AES登录密码
	 * @param secretKey	AES秘钥
	 * @throws HsException	
	 */
	public void changeBindEmail(String perCustId, String email,
			String loginPassword, String secretKey) throws HsException;
	
	/**
	 * 获取登录密码失败次数
	 * @param mobile 手机号码
	 * @return
	 */
	public Integer getLoginFailTimes(String mobile);
	
	/**
	 * 获取交易密码失败次数
	 * @param mobile 手机号码
	 * @return
	 */
	public Integer getTransFailTimes(String mobile);
	
	/**
	 * 获取密保失败次数
	 * @param mobile 手机号码
	 * @return
	 */
	public Integer getPwdQuestionFailTimes(String mobile);
}
