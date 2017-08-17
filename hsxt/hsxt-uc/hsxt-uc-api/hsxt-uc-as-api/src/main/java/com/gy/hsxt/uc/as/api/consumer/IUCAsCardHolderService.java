/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.as.api.consumer;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.consumer.AsCardHolder;
import com.gy.hsxt.uc.as.bean.consumer.AsCardHolderAllInfo;
import com.gy.hsxt.uc.as.bean.consumer.AsConsumerInfo;
import com.gy.hsxt.uc.as.bean.consumer.AsCustUpdateLog;
import com.gy.hsxt.uc.as.bean.consumer.AsHsCard;
import com.gy.hsxt.uc.as.bean.consumer.AsQueryConsumerCondition;
import com.gy.hsxt.uc.as.bean.consumer.AsRealNameAuth;
import com.gy.hsxt.uc.as.bean.consumer.AsUserActionLog;

/**
 * 
 * @Package: com.gy.hsxt.uc.as.api.consumer
 * @ClassName: IUCAsCardHolderService
 * @Description: 持卡人基本信息管理（AS接入系统调用）
 * 
 * @author: tianxh
 * @date: 2015-10-19 下午4:24:20
 * @version V1.0
 */
public interface IUCAsCardHolderService {

	/**
	 * 持卡人销户
	 * 
	 * @param perCustId
	 *            持卡人客户号
	 * @param operCustId
	 *            操作员客户号
	 * @throws HsException
	 */
	public void closeAccout(String perCustId, String operCustId) throws HsException;

	/**
	 * 查询已验证的手机号码
	 * 
	 * @param custId
	 *            持卡人客户号
	 * @return 持卡人的手机号码
	 * @throws HsException
	 */
	public String findMobileByCustId(String custId) throws HsException;

	/**
	 * 修改绑定邮箱
	 * 
	 * @param custId
	 *            持卡人客户号
	 * @param email
	 *            持卡人邮箱
	 * @param loginPwd
	 *            持卡人登录密码
	 * @param secretKey
	 *            AES秘钥（随机报文校验token）
	 * @throws HsException
	 */
	public void changeBindEmail(String custId, String email, String loginPwd, String secretKey)
			throws HsException;

	/**
	 * 查询已验证的邮箱
	 * 
	 * @param custId
	 *            持卡人客户号
	 * @return Map<String,String> key=email(持卡人的邮箱),key=isAuthEmail(是否验证邮箱 0：否，1：是)
	 * @throws HsException
	 */
	public Map<String,String> findEmailByCustId(String custId) throws HsException;

	/**
	 * 查询持卡人的客户号
	 * 
	 * @param resNo
	 *            持卡人互生号
	 * @return 持卡人客户号
	 * @throws HsException
	 */
	public String findCustIdByResNo(String resNo) throws HsException;

	/**
	 * 更新登录IP和时间
	 * 
	 * @param custId
	 *            客户号
	 * @param loginIp
	 *            登录IP
	 * @param dateStr
	 *            登录日期 格式 yyyy-mm-dd hh:mm:ss
	 */
	public void updateLoginInfo(String custId, String loginIp, String dateStr) throws HsException;

	/**
	 * 根据互生号查询持卡人的信息
	 * 
	 * @param resNo
	 *            互生号
	 * @return CardHolder(持卡人信息)
	 */
	public AsCardHolder searchCardHolderInfoByResNo(String resNo) throws HsException;

	/**
	 * 根据客户号查询持卡人的信息
	 * 
	 * @param custId
	 *            客户号
	 * @return CardHolder(持卡人信息)
	 */
	public AsCardHolder searchCardHolderInfoByCustId(String custId) throws HsException;

	/**
	 * 通过互生号查询互生卡的信息
	 * 
	 * @param resNo
	 *            持卡人互生号
	 * @return 互生卡信息
	 */
	public AsHsCard searchHsCardInfoByResNo(String resNo) throws HsException;

	/**
	 * 通过客户号查询互生卡信息
	 * 
	 * @param custId
	 *            持卡人客户号
	 * @return 互生卡信息
	 */
	public AsHsCard searchHsCardInfoByCustId(String custId) throws HsException;


	/**
	 * 是否已设置交易密码
	 * 
	 * @param custId
	 *            客户号
	 * @return ture：已设置 false：未设置
	 */
	public boolean isSetTransPwd(String custId) throws HsException;

	/**
	 * 设置登录密码
	 * 
	 * @param userName
	 *            用户名
	 * @param newPwd
	 *            新的登录密码
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderService#setLoginPwd(java.lang.String,
	 *      java.lang.String)
	 */
	public void setLoginPwd(String userName, String newPwd) throws HsException;

	/**
	 * 修改交易密码
	 * 
	 * @param custId
	 *            客户号
	 * @param newPwd
	 *            新交易密码
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderService#modifyTradePwd(java.lang.String,
	 *      java.lang.String)
	 */
	public void setTradePwd(String custId, String newPwd) throws HsException;

	/**
	 * 分页查询消费者信息
	 * 
	 * @param condition
	 *            分页查询条件 必传
	 * @param page
	 *            分页参数 必传
	 * @return
	 * @throws HsException
	 */
	PageData<AsConsumerInfo> listConsumerInfo(AsQueryConsumerCondition condition, Page page)
			throws HsException;

	/**
	 * 查询持卡人所有信息
	 * 
	 * @param custId
	 *            持卡人客户号
	 * @return
	 * @throws HsException
	 */
	public AsCardHolderAllInfo searchAllInfo(String custId) throws HsException;
	
	/**
	 * 
	 * @param custId	客户号
	 * @param action	操作类型	 1：互生卡解挂	2：互生卡挂失
	 * @param beginDate 起始日期
	 * @param endDate	 终止日期
	 * @param page		 分页参数
	 * @return
	 * @throws HsException
	 */
	public PageData<AsUserActionLog> listUserActionLog(String custId,String action,String beginDate,String endDate, Page page) throws HsException;
	
	

	/**
	 * 获取消费者修改日志列表
	 * 
	 * @param perCustId
	 *            消费者客户号
	 * @param updateFieldName
	 *            修改信息
	 * @param page
	 *            分页条件，null则查询全部
	 * @return
	 */
	public PageData<AsCustUpdateLog> listCustUpdateLog(String perCustId,String updateFieldName, Page page);
	/**
	 * 地区平台修改关联消费者信息并记录修改日志
	 * @param asRealNameAuth 实名验证信息
	 * @param logList 修改字段列表
	 * @param operCustId 操作员客户号
	 * @param confirmerCustId 复审员客户号
	 * @return 持卡人客户号
	 * @throws HsException
	 */
	@Transactional
	public String UpdateCustAndLog(AsRealNameAuth asRealNameAuth,
			List<AsCustUpdateLog> logList, String operCustId,
			String confirmerCustId) throws HsException;
	
	/**
	 * 地区平台修改关联消费者信息并记录修改日志
	 * @param asRealNameAuth 实名验证信息
	 * @param logList 修改字段列表
	 * @param operCustId 操作员客户号
	 * @param confirmerCustId 复审员客户号
	 * @param mobile 待更新的手机号码
	 * @return 持卡人客户号
	 * @throws HsException
	 */
	public String updateCustAndLog(AsRealNameAuth asRealNameAuth,
			List<AsCustUpdateLog> logList, String operCustId,
			String confirmerCustId,String mobile,String perCustId) throws HsException;
	/**
	 * 分页查询所有消费者信息
	 * @param condition
	 * @param page
	 * @return
	 * @throws HsException
	 */
	public PageData<AsConsumerInfo> listAllConsumerInfo(
			AsQueryConsumerCondition condition, Page page) throws HsException;
	
	/**
	 * 获取登录密码失败次数
	 * @param perResNo 互生号
	 * @return
	 */
	public Integer getLoginFailTimes(String perResNo);
	
	/**
	 * 获取交易密码失败次数
	 * @param perResNo 互生号
	 * @return
	 */
	public Integer getTransFailTimes(String perResNo);
	
	/**
	 * 获取密保失败次数
	 * @param perResNo 互生号
	 * @return
	 */
	public Integer getPwdQuestionFailTimes(String perResNo);
}
