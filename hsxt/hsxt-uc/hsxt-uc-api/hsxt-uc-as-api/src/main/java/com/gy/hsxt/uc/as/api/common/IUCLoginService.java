/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.as.api.common;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.bean.common.AsConsumerLoginInfo;
import com.gy.hsxt.uc.as.bean.common.AsOperatorLoginInfo;
import com.gy.hsxt.uc.as.bean.common.AsSysOperLoginInfo;
import com.gy.hsxt.uc.as.bean.consumer.AsCardHolder;
import com.gy.hsxt.uc.as.bean.result.AsCardHolderLoginResult;
import com.gy.hsxt.uc.as.bean.result.AsNoCardHolderLoginResult;
import com.gy.hsxt.uc.as.bean.result.AsOperatorLoginResult;
import com.gy.hsxt.uc.as.bean.result.AsSysOperatorLoginResult;

//import com.gy.hsxt.uf.api.IUFRegionPacketDataService;

/**
 * Simple to Introduction
 * 
 * @category Simple to Introduction
 * @projectName hsxt-uc-as-api
 * @package com.gy.hsxt.uc.as.api.common.IUCLoginService.java
 * @className IUCLoginService
 * @description 一句话描述该类的功能
 * @author lixuan
 * @createDate 2015-10-19 上午10:06:01
 * @updateUser lixuan
 * @updateDate 2015-10-19 上午10:06:01
 * @updateRemark 说明本次修改内容
 * @version v0.0.1
 */
public interface IUCLoginService {
	// extends IUFRegionPacketDataService
	/**
	 * 持卡人本地登录
	 * 
	 * @param loginInfo
	 * @return
	 * @throws HsException
	 */
	public AsCardHolderLoginResult cardHolderLogin(AsConsumerLoginInfo loginInfo)
			throws HsException;

	/**
	 * 持卡人异地登录
	 * 
	 * @param loginInfo
	 * @return
	 * @throws HsException
	 */
	public AsCardHolderLoginResult cardHolderUnLocalLogin(
			AsConsumerLoginInfo loginInfo) throws HsException;

	/**
	 * 非持卡人登录
	 * 
	 * @param loginInfo
	 * @return
	 * @throws HsException
	 */
	public AsNoCardHolderLoginResult noCardHolderLogin(
			AsConsumerLoginInfo loginInfo) throws HsException;

	/**
	 * 使用 企业互生号+操作号+操作员密码 登陆（单密码）
	 * 
	 * @param loginInfo
	 * @return
	 * @throws HsException
	 */
	public AsOperatorLoginResult operatorLogin(AsOperatorLoginInfo loginInfo)
			throws HsException;

	
	/**
	 * 使用 企业互生号+操作号+操作员密码 登陆（单密码）
	 * 
	 * @param loginInfo
	 * @return
	 * @throws HsException
	 */
	public AsSysOperatorLoginResult sysOperatorLogin(AsSysOperLoginInfo loginInfo)
			throws HsException;
	
	/**
	 * 系统管理员使用双密码登录
	 * @param loginInfo
	 * @return
	 */
	public AsSysOperatorLoginResult sysOperatorLoginUse2Pwd(AsSysOperLoginInfo loginInfo) ;
	/**
	 * 企业管理员双密码登陆，不区分密码顺序
	 * 
	 * @param loginInfo
	 *            操作员信息及密码1
	 * @param pwd2
	 *            登陆密码2
	 * @return
	 */
	public AsOperatorLoginResult operatorLogin2(AsOperatorLoginInfo loginInfo,
			String pwd2) throws HsException;

	/**
	 * 操作员双签登录区分密码顺序
	 * @param loginInfo
	 * @param pwd2
	 * @return
	 * @throws HsException
	 */
	public AsOperatorLoginResult operatorLogin2Order(
			AsOperatorLoginInfo loginInfo, String pwd2) throws HsException;
	/**
	 * 审批复核员验证
	 * 
	 * @param loginInfo
	 *            复核员登陆信息
	 * @param authUrl
	 *            审核通过url（权限url）
	 * @return true or false
	 */
	public boolean operatorAuth(AsOperatorLoginInfo loginInfo, String authUrl)
			throws HsException;

	/**
	 * 消费者是否登录
	 * 
	 * @param userType
	 *            用户类型：持卡人或非持卡人
	 * @param user
	 *            用户登录信息，如果是非持卡人登录，mobile为必填，如果是持卡人登录，resNo为必填，渠道channelType为必填
	 * @return
	 * @throws HsException
	 */
	public boolean isUserLogin(UserTypeEnum userType, AsConsumerLoginInfo user)
			throws HsException;

	/**
	 * 操作员是否登录
	 * 
	 * @param user
	 *            企业互生号，操作员登录名和渠道类型为必填
	 * @return
	 * @throws HsException
	 */
	public boolean isOperatorLogin(AsOperatorLoginInfo user) throws HsException;

	/**
	 * 持卡人异地登录后设置登录token和用户信息缓存
	 * 
	 * @param cardHolder
	 *            持卡人信息
	 * @param channelType
	 *            渠道类型
	 * @return 登录token
	 */
	public String cardHolderNoLocalLoginSet(AsCardHolder cardHolder,
			int channelType);

	/**
	 * 操作员异地登录
	 * 
	 * @param loginInfo
	 *            登录信息
	 * @return 登录后的信息
	 * @throws HsException
	 */
	public AsOperatorLoginResult operatorNoLocalLogin(
			AsOperatorLoginInfo loginInfo) throws HsException;
	
	/**
	 * 操作员使用绑定互生号+消费者卡密码登陆
	 * @param loginInfo	登录信息
	 * @return
	 * @throws HsException
	 */
	public AsOperatorLoginResult operatorLoginByCard(AsOperatorLoginInfo loginInfo)throws HsException;
	
	/**
	 * 操作员验证第二个登录密码，成功后登录
	 * @param loginInfo
	 * @return
	 */
	public AsOperatorLoginResult operatorUseTwoPwdAndLogin(
			AsOperatorLoginInfo loginInfo);
	
	/**
	 * 管理员或系统操作员双签登录
	 * @param operLoginInfo
	 * @throws HsException
	 */
	public void sysOperCommonLogin(AsSysOperLoginInfo operLoginInfo)throws HsException;
}
