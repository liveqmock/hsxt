/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.consumer.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.RandomGuidAgent;
import com.gy.hsxt.common.utils.BeanCopierUtils;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.api.consumer.IUCAsNoCardHolderService;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.bean.consumer.AsNewMobileInfo;
import com.gy.hsxt.uc.as.bean.consumer.AsNoCardHolder;
import com.gy.hsxt.uc.as.bean.consumer.AsRegNoCardHolder;
import com.gy.hsxt.uc.cache.service.CommonCacheService;
import com.gy.hsxt.uc.common.SysConfig;
import com.gy.hsxt.uc.common.bean.NetworkInfo;
import com.gy.hsxt.uc.common.bean.PwdQuestion;
import com.gy.hsxt.uc.common.mapper.PwdQuestionMapper;
import com.gy.hsxt.uc.common.service.CommonService;
import com.gy.hsxt.uc.common.service.UCAsEmailService;
import com.gy.hsxt.uc.common.service.UCAsMobileService;
import com.gy.hsxt.uc.common.service.UCAsPwdService;
import com.gy.hsxt.uc.consumer.bean.NcRealNameAuth;
import com.gy.hsxt.uc.consumer.bean.NoCardHolder;
import com.gy.hsxt.uc.consumer.mapper.NcRealNameAuthMapper;
import com.gy.hsxt.uc.consumer.mapper.NoCardHolderMapper;
import com.gy.hsxt.uc.search.api.IUCUserInfoService;
import com.gy.hsxt.uc.search.bean.SearchUserInfo;
import com.gy.hsxt.uc.util.CSPRNG;
import com.gy.hsxt.uc.util.CustIdGenerator;
import com.gy.hsxt.uc.util.StringEncrypt;

/**
 * 
 * @Package: com.gy.hsxt.uc.consumer.service
 * @ClassName: UAsCNoCardHolderService
 * @Description: 非持卡人基本信息管理（AS接入系统调用）
 * 
 * @author: tianxh
 * @date: 2015-10-21 上午10:46:30
 * @version V1.0
 */
@Service
public class UCAsNoCardHolderService implements IUCAsNoCardHolderService {
	private final static String MOUDLENAME = "com.gy.hsxt.uc.consumer.service.UCAsNoCardHolderService";

	@Autowired
	private NoCardHolderMapper noCardHolderMapper;

	@Autowired
	private UCAsCardHolderService uCAsCardHolderService;

	@Autowired
	private CommonCacheService commonCacheService;

	@Autowired
	SysConfig config;

	@Autowired
	NcRealNameAuthMapper ncRealNameAuthMapper;

	@Autowired
	private PwdQuestionMapper pwdQuestionMapper;

	@Autowired
	CommonService commonService;

	@Autowired
	UCAsPwdService pwdService;

	@Autowired
	UCAsMobileService mobileService;

	@Autowired
	UCAsEmailService emailService;
	/**
	 * 非持卡人注册
	 * 
	 * @param 非持卡人注册信息
	 * @throws HsException
	 */
	@SuppressWarnings("static-access")
	@Override
	@Transactional
	public void regNoCardConsumer(AsRegNoCardHolder asRegNoCardHolder,
			String secretKey) throws HsException {
		if (null == asRegNoCardHolder) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数非持卡人信息为空");
		}
		if (StringUtils.isBlank(secretKey)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数AES秘钥为空");
		}
		if (StringUtils.isBlank(asRegNoCardHolder.getSmsCode())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数短信验证码为空");
		}
		if (StringUtils.isBlank(asRegNoCardHolder.getLoginPwd())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数录密码为空");
		}
		if (StringUtils.isBlank(asRegNoCardHolder.getMobile())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数手机号码为空");
		}

		String salt = CSPRNG.generateRandom(config.getCsprLen());
		String loginPwd = uCAsCardHolderService.encryptAESPwd(asRegNoCardHolder
				.getLoginPwd().trim(), salt, secretKey);
		// 校验短信验证码
		String mobile = asRegNoCardHolder.getMobile().trim();
		String smsCode = asRegNoCardHolder.getSmsCode().trim();
		// 验证是否已注册
		boolean isRegister = isRegister(mobile);
		if (isRegister) {
			throw new HsException(ErrorCodeEnum.USER_EXIST.getValue(),
					ErrorCodeEnum.USER_EXIST.getDesc());
		}
		// 验证手机验证码是否正确
		uCAsCardHolderService.checkSmsCode(smsCode, mobile);
		// 保存非持卡人信息
		NoCardHolder noRegCardHolder = new NoCardHolder();
		String machineNo = "9" + config.getSystemInstanceNo();
		String perCustId = RandomGuidAgent.getStringGuid(machineNo);
		noRegCardHolder.setPerCustId(perCustId);
		noRegCardHolder.setPwdLogin(loginPwd);
		noRegCardHolder.setMobile(mobile);
		noRegCardHolder.setCreatedby(perCustId);
		noRegCardHolder.setPwdLoginSaltValue(salt);
		saveNoCardHolder(noRegCardHolder);
		// 保存网络信息
		NetworkInfo networkInfo = new NetworkInfo();
		networkInfo.setCreatedby(perCustId);
		networkInfo.setPerCustId(perCustId);
		String nickName = asRegNoCardHolder.getNickname();
		nickName = nickName == null ? mobile : nickName.trim();
		networkInfo.setNickname(nickName);
		uCAsCardHolderService.saveNetWorkInfo(networkInfo);
		// 保存密保信息
		PwdQuestion question = new PwdQuestion();
		question.setCustId(perCustId);
		question.setCreateBy(perCustId);
		pwdQuestionMapper.insertSelective(question);
		// 通知搜索引擎
		SearchUserInfo user = new SearchUserInfo();
		user.setUserType(0);
		user.setMobile(mobile);
		user.setCustId(perCustId);
		user.setNickName(nickName);
		user.setUsername(mobile);
		user.setIsLogin(1);
		try {
			commonService.solrAdd(user);
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "regNoCardConsumer", "通知搜索引擎失败：", e);
		}
	}

	/**
	 * 查询已验证邮箱
	 * 
	 * @param perCustId
	 *            非持卡人客户号
	 * @return 验证邮箱
	 * @throws HsException
	 */
	@Override
	public String findEmailByCustId(String perCustId) throws HsException {
		if (StringUtils.isBlank(perCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数客户号为空");
		}
		String custId = perCustId.trim();
		NoCardHolder noCardHolder = commonCacheService
				.searchNoCardHolderInfo(custId);
		CustIdGenerator.isNoCarderExist(noCardHolder, custId);
//		Integer isAuthEmail = noCardHolder.getIsAuthEmail();
//		if (null == isAuthEmail || 0 == isAuthEmail) {
//			throw new HsException(ErrorCodeEnum.EMAIL_NOT_CHECK.getValue(),
//					ErrorCodeEnum.EMAIL_NOT_CHECK.getDesc());
//		}
		return StringUtils.nullToEmpty(noCardHolder.getEmail());
	}
	


	/**
	 * 通过手机号码查找非持卡人信息
	 * 
	 * @param mobile
	 *            手机号码
	 * @return
	 */
	@Override
	public AsNoCardHolder searchNoCardHolderInfoByMobile(String mobile) {
		if (StringUtils.isBlank(mobile)) {
			throw new HsException(ErrorCodeEnum.USER_NOT_FOUND.getValue(),
					"手机号的用户不存在, 手机号：" + mobile);
		}
		String custId = commonCacheService.findCustIdByMobile(mobile);
		CustIdGenerator.isNoCarderExist(custId, mobile);
		NoCardHolder noCardHolder = commonCacheService
				.searchNoCardHolderInfo(custId);
		CustIdGenerator.isNoCarderExist(noCardHolder, custId);
		AsNoCardHolder asNoCardHolder = new AsNoCardHolder();
		BeanCopierUtils.copy(noCardHolder, asNoCardHolder);
		return asNoCardHolder;
	}

	/**
	 * 通过客户号查找非持卡人信息
	 * 
	 * @param perCustId
	 *            客户号
	 * @return 非持卡人信息
	 * @throws HsException
	 */
	@Override
	public AsNoCardHolder searchNoCardHolderInfoByCustId(String perCustId)
			throws HsException {
		if (StringUtils.isBlank(perCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数客户号为空");
		}
		String custId = perCustId.trim();
		NoCardHolder noCardHolder = commonCacheService
				.searchNoCardHolderInfo(custId);
		CustIdGenerator.isNoCarderExist(noCardHolder, custId);
		AsNoCardHolder asNoCardHolder = new AsNoCardHolder();
		BeanCopierUtils.copy(noCardHolder, asNoCardHolder);
		return asNoCardHolder;
	}

	/**
	 * 修改登录密码
	 * 
	 * @param custId
	 *            客户号
	 * @param oldPwd
	 *            旧登录密码
	 * @param newPwd
	 *            新登录密码
	 * @throws HsException
	 */
	@Override
	public void modifyLoginPwd(String custId, String oldPwd, String newPwd)
			throws HsException {
		AsNoCardHolder ac = searchNoCardHolderInfoByCustId(custId);
		if (ac == null) {
			throw new HsException(ErrorCodeEnum.USER_NOT_FOUND.getValue(),
					ErrorCodeEnum.USER_NOT_FOUND.getDesc());
		}
		// 对比旧密码
		String conrecctPwd = StringUtils.nullToEmpty(ac.getPwdLogin());
		String pwd = StringEncrypt.sha256(oldPwd + ac.getPwdLoginSaltValue());// 旧密码
		newPwd = StringEncrypt.sha256(newPwd + ac.getPwdLoginSaltValue());// 新密码
		if (!pwd.equals(conrecctPwd)) {
			throw new HsException(ErrorCodeEnum.PWD_LOGIN_ERROR.getValue(),
					ErrorCodeEnum.PWD_LOGIN_ERROR.getDesc());
		}
		// 如果对比成功，修改登录密码
		modifyLoginPwd(custId, newPwd);
	}

	/**
	 * 重置登录密码
	 * 
	 * @param custId
	 *            客户号
	 * @param newPwd
	 *            新密码
	 * @throws HsException
	 */
	@Override
	public void setLoginPwd(String custId, String newPwd) throws HsException {
		if (StringUtils.isEmpty(custId)) {
			throw new HsException(ErrorCodeEnum.USER_NOT_FOUND.getValue(),
					ErrorCodeEnum.USER_NOT_FOUND.getDesc());
		}
		modifyLoginPwd(custId, newPwd);
	}

	/**
	 * 是否注册
	 * 
	 * @param mobile
	 *            手机号
	 * @return
	 */
	@Override
	public boolean isRegister(String mobile) throws HsException {
		if (StringUtils.isBlank(mobile)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数手机号码为空");
		}
		try {
			String custId = commonCacheService.findCustIdByMobile(mobile);
			CustIdGenerator.isNoCarderExist(custId, mobile);
		} catch (HsException e) {
			if (ErrorCodeEnum.USER_NOT_FOUND.getValue() == e.getErrorCode()) {
				return false;
			} else {
				throw e;
			}
		}
		return true;
	}

	/**
	 * 注销
	 * 
	 * @param perCustId
	 *            非持卡人客户号
	 * @param operCustId
	 *            操作员客户号
	 * @throws HsException
	 */
	public void closeAccout(String perCustId, String operCustId)
			throws HsException {
		if (StringUtils.isBlank(perCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数持卡人客户号为空]");
		}
		if (StringUtils.isBlank(perCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数操作员客户号为空");
		}
		String operatorId = operCustId.trim();
		String consumerId = perCustId.trim();
		// 销户
		NoCardHolder noCardHolder = new NoCardHolder();
		noCardHolder.setIsactive("N");
		noCardHolder.setUpdateDate(StringUtils.getNowTimestamp());
		noCardHolder.setUpdatedby(operatorId);
		noCardHolder.setPerCustId(consumerId);
		updateNoCardHolderInfo(noCardHolder);
		// 通知搜索引擎
		try {
			commonService.solrDel(consumerId);
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "closeAccout", "通知搜索引擎失败：", e);
		}
	}

	/**
	 * 更新登录时间和登录IP
	 * 
	 * @param custId
	 *            客户号
	 * @param loginIp
	 *            登录IP
	 * @param date
	 *            登录时间 格式 yyyy-mm-dd hh:mm:ss
	 * @see com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderService#updateLoginInfo(java.lang.String,
	 *      java.lang.String, java.util.Date)
	 */
	public void updateLoginInfo(String perCustId, String lastLoginIp,
			String loginDate) {
		if (StringUtils.isBlank(perCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数客户号为空");
		}
		Timestamp lastLoginDate = StringUtils.getNowTimestamp();
		if (!StringUtils.isBlank(loginDate)) {
			lastLoginDate = Timestamp.valueOf(loginDate.trim());
		}
		commonCacheService.updateNoCarderLoginIn(perCustId, lastLoginIp.trim(),
				lastLoginDate);
	}

	/**
	 * 修改登录密码
	 * 
	 * @param custId
	 * @param newPwd
	 * @throws HsException
	 */
	public void modifyLoginPwd(String custId, String newPwd) throws HsException {
		// 更新新登录密码
		NoCardHolder noCardHolder = new NoCardHolder();
		noCardHolder.setPerCustId(custId);
		noCardHolder.setPwdLogin(newPwd);
		noCardHolder.setUpdatedby(custId);
		noCardHolder.setUpdateDate(StringUtils.getNowTimestamp());
		updateNoCardHolderInfo(noCardHolder);
	}

	/**
	 * 更新非持卡人信息
	 * 
	 * @param srcNoCardHolder
	 *            非持卡人信息
	 * @param isDel
	 *            是否删除 true:是 false:否
	 */
	public void updateNoCardHolderInfo(NoCardHolder noCardHolder) {
		commonCacheService.updateNoCardHolderInfo(noCardHolder);
	}

	/**
	 * 保存非持卡人信息
	 * 
	 * @param srcNoCardHolder
	 *            非持卡人信息
	 */
	public void saveNoCardHolder(NoCardHolder srcNoCardHolder) {
		try {
			noCardHolderMapper.insertSelective(srcNoCardHolder);
		} catch (Exception e) {
			throw new HsException(
					ErrorCodeEnum.NOCARDHOLDER_SAVE_FAIL.getValue(),
					e.getMessage());
		}
	}

	/**
	 * 通过手机号码查询客户号
	 * 
	 * @param mobile
	 *            手机号码
	 * @return 客户号
	 * @throws HsException
	 */
	@Override
	public String findCustIdByMobile(String mobile) throws HsException {
		if (StringUtils.isBlank(mobile)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数手机号码为空");
		}
		String custId = commonCacheService.findCustIdByMobile(mobile);
		CustIdGenerator.isNoCarderExist(custId, mobile);
		return custId;
	}

	/**
	 * 保存实名注册信息
	 * 
	 * @param realNameAuth
	 *            实名注册信息
	 */
	public void saveRealNameAuthInfo(NcRealNameAuth ncRealNameAuth) {
		try {
			ncRealNameAuthMapper.insertSelective(ncRealNameAuth);
			commonCacheService.addNoCarderRealAuthCache(
					ncRealNameAuth.getPerCustId(), ncRealNameAuth);
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "saveRealNameAuthInfo",
					"保存实名注册信息异常：\r\n", e);
		}

	}

	/**
	 * 查询持卡人实名认证状态
	 * 
	 * @param custId
	 *            客户号
	 * @return String 实名状态
	 * @throws HsException
	 */
	public String findAuthStatusByCustId(String perCustId) throws HsException {
		if (StringUtils.isBlank(perCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数客户号为空");
		}
		NoCardHolder noCardHolder = commonCacheService
				.searchNoCardHolderInfo(perCustId);
		CustIdGenerator.isNoCarderExist(noCardHolder, perCustId);
		String realNameStatus = "";
		if (null != noCardHolder.getIsRealnameAuth()) {
			realNameStatus = String.valueOf(noCardHolder.getIsRealnameAuth());
		}
		return realNameStatus;
	}

	/**
	 * 检查证件是否已被他人使用
	 * 
	 * @param perCustId
	 *            个人客户号,null则忽略该条件
	 * @param idType
	 *            证件类型1：身份证 2：护照 3：营业执照
	 * @param idNo
	 *            证件号码
	 * @param perName
	 *            ,null则忽略该条件 姓名
	 * @return 证件已被他人实名注册则返回true
	 */
	public boolean isIdNoExist(String perCustId, Integer idType, String idNo,
			String perName) {
		int count = 0;
		String isactive = "Y";
		try {
			count = ncRealNameAuthMapper.getIdCardNumer(idType, idNo, isactive,
					perCustId, perName);
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "isIdNoExist",
					ErrorCodeEnum.CHECK_ID_REGISTERED_FAIL.getDesc()
							+ ",perCustId[" + perCustId + ",idType[" + idType
							+ "],idNo" + idNo + "]", e);
		}
		return count >= 1 ? true : false;
	}

	@Override
	public void modifyMobile(AsNewMobileInfo newMobileInfo) throws HsException {
		validNewMobileInfo(newMobileInfo);
		String perCustId = newMobileInfo.getPerCustId();
		String newMobile = newMobileInfo.getNewMobile();
		String smsCode = newMobileInfo.getSmsCode();
		pwdService.checkLoginPwd(perCustId, newMobileInfo.getLoginPwd(),
				UserTypeEnum.NO_CARDER.getType(), newMobileInfo.getSecretKey());
		mobileService.bindMobile(perCustId, newMobile, smsCode,
				UserTypeEnum.NO_CARDER.getType());
	}

	

	private void validNewMobileInfo(AsNewMobileInfo newMobileInfo)
			throws HsException {
		if (null == newMobileInfo) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数  newMobileInfo 不能为空");
		}
		if (StringUtils.isBlank(newMobileInfo.getLoginPwd())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数  登录密码 不能为空");
		}
		if (StringUtils.isBlank(newMobileInfo.getNewMobile())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数  新的手机号码 不能为空");
		}
		if (StringUtils.isBlank(newMobileInfo.getPerCustId())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数客户号 不能为空");
		}
		if (StringUtils.isBlank(newMobileInfo.getSecretKey())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数  AES秘钥 不能为空");
		}
		if (StringUtils.isBlank(newMobileInfo.getSmsCode())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数  短信验证码 不能为空");
		}
	}
	
	public void changeBindEmail(String perCustId, String email,
			String loginPassword, String secretKey) throws HsException {
		if (StringUtils.isBlank(perCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数客户号为空");
		}
		if (StringUtils.isBlank(email)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数邮箱为空");
		}
		if (StringUtils.isBlank(loginPassword)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数登录密码为空");
		}
		if (StringUtils.isBlank(secretKey)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数秘钥为空");
		}
		// 核对登录密码是否正确
		commonService.checkIsAuthEmail(perCustId, UserTypeEnum.NO_CARDER.getType(),
				email);
		NoCardHolder noHolder = commonCacheService.searchNoCardHolderInfo(perCustId);
		// 验证密码是否正确
		pwdService.checkLoginPwd(perCustId, loginPassword,
				UserTypeEnum.NO_CARDER.getType(), secretKey);

		// 登录密码正确，则修改邮箱信息
//		NoCardHolder noCardHolder = new NoCardHolder();
//		noCardHolder.setPerCustId(perCustId);
//		noCardHolder.setEmail(email);
//		noCardHolder.setIsAuthEmail(0);
//		noCardHolder.setUpdateDate(StringUtils.getNowTimestamp());
//		noCardHolder.setUpdatedby(perCustId);
//		commonCacheService.updateNoCardHolderInfo(noCardHolder, false);
		// 登录密码正确，则发送验证邮件
		emailService.sendMailForValidEmail(email, noHolder.getMobile(), "",
				UserTypeEnum.NO_CARDER.getType(), CustType.NOT_HS_PERSON.getCode());
	}

	@Override
	public Integer getLoginFailTimes(String mobile) {
		return commonCacheService.getNoCardLoginFailTimesCache(mobile);
	}

	@Override
	public Integer getTransFailTimes(String mobile) {
		return commonCacheService.getCarderTradeFailTimesCache(mobile);
	}

	@Override
	public Integer getPwdQuestionFailTimes(String mobile) {
		return commonCacheService.getCarderPwdQuestionFailTimesCache(mobile);
	}
}
