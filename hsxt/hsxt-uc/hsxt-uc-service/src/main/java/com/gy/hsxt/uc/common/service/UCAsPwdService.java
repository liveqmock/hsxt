/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.common.service;

import static com.gy.hsxt.common.utils.StringUtils.isBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.api.auth.IUCAsRoleService;
import com.gy.hsxt.uc.as.api.common.IUCAsPwdService;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderAuthInfoService;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.uc.as.api.enumerate.RoleEnum;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.bean.common.AsMainInfo;
import com.gy.hsxt.uc.as.bean.common.AsNoCarderMainInfo;
import com.gy.hsxt.uc.as.bean.common.AsResetLoginPwd;
import com.gy.hsxt.uc.as.bean.common.AsResetTradePwdAuthCode;
import com.gy.hsxt.uc.as.bean.common.AsResetTradePwdConsumer;
import com.gy.hsxt.uc.as.bean.common.AsUpdateTradePwd;
import com.gy.hsxt.uc.as.bean.consumer.AsRealNameAuth;
import com.gy.hsxt.uc.as.bean.sysoper.AsSysOper;
import com.gy.hsxt.uc.bs.api.consumer.IUCBsCardHolderService;
import com.gy.hsxt.uc.cache.service.CommonCacheService;
import com.gy.hsxt.uc.checker.bean.DoubleChecker;
import com.gy.hsxt.uc.common.SysConfig;
import com.gy.hsxt.uc.common.bean.UserInfo;
import com.gy.hsxt.uc.consumer.bean.CardHolder;
import com.gy.hsxt.uc.consumer.bean.NoCardHolder;
import com.gy.hsxt.uc.consumer.mapper.CardHolderMapper;
import com.gy.hsxt.uc.consumer.service.UCAsCardHolderService;
import com.gy.hsxt.uc.consumer.service.UCAsNoCardHolderAuthInfoService;
import com.gy.hsxt.uc.consumer.service.UCAsNoCardHolderService;
import com.gy.hsxt.uc.ent.bean.EntExtendInfo;
import com.gy.hsxt.uc.ent.bean.EntStatusInfo;
import com.gy.hsxt.uc.ent.service.AsEntService;
import com.gy.hsxt.uc.operator.bean.Operator;
import com.gy.hsxt.uc.operator.service.OperatorService;
import com.gy.hsxt.uc.password.PasswordService;
import com.gy.hsxt.uc.password.bean.PasswordBean;
import com.gy.hsxt.uc.sysoper.bean.SysOperator;
import com.gy.hsxt.uc.sysoper.serivce.UCAsSysOperInfoService;
import com.gy.hsxt.uc.util.CSPRNG;
import com.gy.hsxt.uc.util.CustIdGenerator;
import com.gy.hsxt.uc.util.StringEncrypt;

/**
 * 密码操作类，包括修改密码等
 * 
 * @Package: com.gy.hsxt.uc.common.service
 * @ClassName: UCAsPwdService
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2015-10-15 上午10:50:27
 * @version V1.0
 */
@Service
public class UCAsPwdService implements IUCAsPwdService {
	private final static String MOUDLENAME = "com.gy.hsxt.uc.common.service.UCAsPwdService";
	/**
	 * 持卡人管理接口
	 */
	@Autowired
	UCAsCardHolderService cardHolderService;

	@Autowired
	UCAsNoCardHolderAuthInfoService noCardHolderAuthInfoService;
	/**
	 * 操作员管理接口
	 */
	@Autowired
	OperatorService operatorService;
	/**
	 * 非持卡人管理接口
	 */
	@Autowired
	UCAsNoCardHolderService noCardHolderService;
	/**
	 * 企业管理接口
	 */
	@Autowired
	IUCAsEntService asEntService;
	/**
	 * 持卡人持卡人证件信息管理
	 */
	@Autowired
	IUCAsCardHolderAuthInfoService iUCAsCardHolderAuthInfoService;

	@Autowired
	AsEntService entService;

	@Autowired
	CommonCacheService commonCacheService;

	@Autowired
	CommonService commonService;

	/**
	 * BS持卡人接口
	 */
	@Autowired
	IUCBsCardHolderService bsCardHolderService;

	@Autowired
	UCAsMobileService mobileService;

	@Autowired
	IUCAsRoleService roleService;

	@Autowired
	PasswordService passwordService;

	@Autowired
	UCAsSysOperInfoService sysOperatorService;
	
	@Autowired
	private CardHolderMapper cardHolderMapper;

	/**
	 * 校验交易密码
	 * 
	 * @param custId
	 * @param tradePwd
	 *            使用AES(md5（密码）,secretKey） 加密
	 * @param userType
	 * @param secretKey
	 * @throws HsException
	 */
	@Override
	public void checkTradePwd(String custId, String tradePwd, String userType,
			String secretKey) throws HsException {
		// 验证数据
		validTradePwdParams(custId, tradePwd, userType, secretKey);
		// 还原为密码为 md5(密码)
		tradePwd = StringEncrypt.decrypt(tradePwd, secretKey);
		if (UserTypeEnum.CARDER.getType().equals(userType)) {
			CardHolder cardHolder = commonCacheService
					.searchCardHolderInfo(custId);
			CustIdGenerator.isCarderExist(cardHolder, custId);
			if (StringUtils.isBlank(cardHolder.getPwdTrans())) {
				throw new HsException(
						ErrorCodeEnum.TRANS_PWD_NOT_SET.getValue(),
						ErrorCodeEnum.TRANS_PWD_NOT_SET.getDesc());
			}
			commonService.checkCarderTradePwd(cardHolder, tradePwd);
		} else if (UserTypeEnum.ENT.getType().equals(userType)) {
			EntStatusInfo entStatusInfo = commonCacheService
					.searchEntStatusInfo(custId);
			CustIdGenerator.isEntStatusInfoExist(entStatusInfo, custId);
			if (StringUtils.isBlank(entStatusInfo.getPwdTrans())) {
				throw new HsException(
						ErrorCodeEnum.TRANS_PWD_NOT_SET.getValue(),
						ErrorCodeEnum.TRANS_PWD_NOT_SET.getDesc());
			}
			commonService.checkEntTradePwd(entStatusInfo, tradePwd);
		} else if (UserTypeEnum.NO_CARDER.getType().equals(userType)) {
			NoCardHolder noCarder = commonCacheService
					.searchNoCardHolderInfo(custId);
			CustIdGenerator.isNoCarderExist(noCarder, custId);
			if (StringUtils.isBlank(noCarder.getPwdTrans())) {
				throw new HsException(
						ErrorCodeEnum.TRANS_PWD_NOT_SET.getValue(),
						ErrorCodeEnum.TRANS_PWD_NOT_SET.getDesc());
			}
			commonService.checkNoCarderTradePwd(noCarder, tradePwd);
		} else {
			throw new HsException(ErrorCodeEnum.USERTYPE_IS_ILLEGAL.getValue(),
					ErrorCodeEnum.USERTYPE_IS_ILLEGAL.getDesc()
							+ "，实际传入的用户类型为：" + userType);
		}
	}


	/**
	 * 修改登陆密码
	 * 
	 * @param custId
	 *            客户号
	 * @param userType
	 *            用户类型
	 * @param oldLoginPwd
	 *            使用AES(md5（密码）,secretKey） 加密
	 * @param newLoginPwd
	 *            使用AES(md5（密码）,secretKey） 加密
	 * @param secretKey
	 * @throws HsException
	 */
	@Override
	public void updateLoginPwd(String custId, String userType,
			String oldLoginPwd, String newLoginPwd, String secretKey)
			throws HsException {
		// 检查入参
		validUpdateLoginPwdParams(custId, userType, oldLoginPwd, newLoginPwd,
				secretKey);
		// 验证登陆密码
		commonService.checkLoginPwdNoCountFailedTimes(UserTypeEnum.getUserTypeEnum(userType), custId,oldLoginPwd, secretKey);
		// 更新新的登陆密码
		saveLoginPwd(newLoginPwd, secretKey, userType, custId, custId);
	}

	@Override
	public void resetLoginPwdByMobile(AsResetLoginPwd resetLoginPwdMobile)
			throws HsException {
		// 检查入参
		validResetLoginPwd(resetLoginPwdMobile);
		String newLoginPwd = resetLoginPwdMobile.getNewLoginPwd().trim();
		String secretKey = resetLoginPwdMobile.getSecretKey().trim();
		String userType = resetLoginPwdMobile.getUserType().trim();
		String correctSmsCode = StringUtils.nullToEmpty(commonCacheService
				.getSmsCodeCache(resetLoginPwdMobile.getMobile()));
		String isnoreCheckCode = StringUtils.nullToEmpty(SysConfig
				.getIgnoreCheckCode());
		if (!"1".equals(isnoreCheckCode)) {// 是否忽略手机短信验证码的验证（0：不忽略 1：忽略）
			if (StringUtils.isBlank(correctSmsCode)) {
				throw new HsException(ErrorCodeEnum.SMS_IS_EXPIRED.getValue(),
						ErrorCodeEnum.SMS_IS_EXPIRED.getDesc());
			}
			if (!correctSmsCode.equals(resetLoginPwdMobile.getRandom())) {// 待改
				throw new HsException(ErrorCodeEnum.SMS_NOT_MATCH.getValue(),
						ErrorCodeEnum.SMS_NOT_MATCH.getDesc());
			}
		}
		commonCacheService.removeSmsCodeCache(resetLoginPwdMobile.getMobile());
		String custId = resetLoginPwdMobile.getCustId();
		// 重置登陆密码
		saveLoginPwd(newLoginPwd, secretKey, userType, custId, custId);
	}

	@Override
	public void resetLoginPwdByCrypt(AsResetLoginPwd resetLoginPwdCrypt)
			throws HsException {
		validResetLoginPwd(resetLoginPwdCrypt);
		String correctRandom = StringUtils.nullToEmpty(commonCacheService
				.getCryptCache(resetLoginPwdCrypt.getCustId()));
		String isnoreCheckCode = StringUtils.nullToEmpty(SysConfig
				.getIgnoreCheckCode());
		if (!"1".equals(isnoreCheckCode)) {// 是否忽略密保验证通过发送的随机数验证（0：不忽略 1：忽略）
			if (StringUtils.isBlank(correctRandom)) {
				throw new HsException(ErrorCodeEnum.SMS_IS_EXPIRED.getValue(),
						resetLoginPwdCrypt.getCustId() + "用户验证码已过期");
			}

			if (!correctRandom.equals(resetLoginPwdCrypt.getRandom())) {// 待改
				throw new HsException(ErrorCodeEnum.SMS_NOT_MATCH.getValue(),
						resetLoginPwdCrypt.getRandom() + "用户验证码不正确");
			}
		}
		String newLoginPwd = resetLoginPwdCrypt.getNewLoginPwd().trim();
		String secretKey = resetLoginPwdCrypt.getSecretKey().trim();
		String userType = resetLoginPwdCrypt.getUserType().trim();
		String custId = resetLoginPwdCrypt.getCustId();
		// 重置登陆密码
		saveLoginPwd(newLoginPwd, secretKey, userType, custId, custId);

	}

	/**
	 * 保存新的登陆密码
	 * 
	 * @param newLoginPwd
	 * @param secretKey
	 * @param userName
	 * @param userType
	 * @param entResNo
	 */

	private void saveLoginPwd(String newLoginPwd, String secretKey,
			String userType, String custId, String operCustId)
			throws HsException {
		// 还原为密码为 md5(密码)
		String salt = CSPRNG.generateRandom(SysConfig.getCsprLen());
		newLoginPwd = StringEncrypt.decrypt(newLoginPwd, secretKey);
		// md5
		newLoginPwd = StringEncrypt.string2MD5(newLoginPwd);
		newLoginPwd = StringEncrypt.sha256(newLoginPwd + salt);
		// 重置登录密码
		if (UserTypeEnum.NO_CARDER.getType().equals(userType)) {
			saveNoCardHolderLoginPwd(custId, newLoginPwd, salt);
		} else if (UserTypeEnum.CARDER.getType().equals(userType)) {
			saveCardHolderLoginPwd(custId, newLoginPwd, salt);
		} else if (UserTypeEnum.SYSTEM.getType().equals(userType)) {
			saveSystemLoginPwd(custId, newLoginPwd, salt);
		} else if (UserTypeEnum.CHECKER.getType().equals(userType)) {
			saveDoubleCheckerLoginPwd(custId, newLoginPwd, salt);
		} else {
			saveOpeLoginPwd(custId, newLoginPwd, salt, operCustId);
		}
	}

	/**
	 * 修改交易密码
	 * 
	 * @param custId
	 *            客户号
	 * @param oldTradePwd
	 *            旧交易密码 使用AES(md5（密码）,secretKey） 加密
	 * @param newTradePwd
	 *            新交易密码 使用AES(md5（密码）,secretKey） 加密
	 * @param userType
	 *            用户类型
	 * @param secretKey
	 *            密钥
	 * @param operCustId
	 *            操作员客户号
	 * @throws HsException
	 */
	@Override
	public void updateTradePwd(AsUpdateTradePwd params) throws HsException {
		// 验证数据
		validUpdateTradePwdParams(params);
		String custId = params.getCustId().trim();
		String userType = params.getUserType().trim();
		String oldTradePwd = params.getOldTradePwd().trim();
		String secretKey = params.getSecretKey().trim();
		// 检查旧的交易密码
		// 还原为密码为 md5(密码)
		String tradePwd = StringEncrypt.decrypt(oldTradePwd, secretKey);
		commonService.checkTradePwdNoCountFailedTimes(custId, tradePwd,
				userType, secretKey);
		String newTradePwd = params.getNewTradePwd().trim();
		String operCustId = params.getOperCustId().trim();
		saveTradePwd(custId, newTradePwd, userType, secretKey, operCustId);
	}

	/**
	 * 覆盖更新交易密码
	 * 
	 * @param custId
	 * @param newTradePwd
	 *            AES加密交易密码
	 * @param userType
	 * @param secretKey
	 *            AES秘钥
	 * @param operCustId
	 */
	private void saveTradePwd(String custId, String newTradePwd,
			String userType, String secretKey, String operCustId)
			throws HsException {
		newTradePwd = StringEncrypt.decrypt(newTradePwd, secretKey);
		String salt = CSPRNG.generateRandom(SysConfig.getCsprLen());
		newTradePwd = StringEncrypt.sha256(newTradePwd + salt);
		// 更新新的交易密码
		if (UserTypeEnum.CARDER.getType().equals(userType)) {
			saveCardHolderTradePwd(custId, newTradePwd, salt);
		} else if (UserTypeEnum.ENT.getType().equals(userType)) {
			saveEntTradePwd(custId, newTradePwd, salt, operCustId.trim());
			Operator operator = commonCacheService
					.searchOperByCustId(operCustId);
			CustIdGenerator.isOperExist(operator, operCustId);
		} else if (UserTypeEnum.NO_CARDER.getType().equals(userType)) {
			saveNoCarderTradePwd(custId, newTradePwd, salt);
		} else {
			throw new HsException(ErrorCodeEnum.USERTYPE_IS_ILLEGAL.getValue(),
					ErrorCodeEnum.USERTYPE_IS_ILLEGAL.getDesc());
		}
	}

	/**
	 * 覆盖更新交易密码
	 * 
	 * @param custId
	 * @param newTradePwd
	 *            AES加密交易密码
	 * @param userType
	 * @param secretKey
	 *            AES秘钥
	 * @param operCustId
	 */
	private void saveTradePwdByReChecker(String custId, String newTradePwd,
			String userType, String secretKey, String operCustId, String salt)
			throws HsException {
		// 更新新的交易密码
		if (UserTypeEnum.CARDER.getType().equals(userType)) {
			saveCardHolderTradePwd(custId, newTradePwd, salt);
		} else if (UserTypeEnum.ENT.getType().equals(userType)) {
			saveEntTradePwd(custId, newTradePwd, salt, operCustId.trim());
			Operator operator = commonCacheService
					.searchOperByCustId(operCustId);
			CustIdGenerator.isOperExist(operator, operCustId);
		} else if (UserTypeEnum.NO_CARDER.getType().equals(userType)) {
			// 保留对非持卡人的交易密码更新
			return;
		} else {
			throw new HsException(ErrorCodeEnum.USERTYPE_IS_ILLEGAL.getValue(),
					ErrorCodeEnum.USERTYPE_IS_ILLEGAL.getDesc());
		}
	}

	/**
	 * 重置持卡人交易密码
	 * 
	 * @param custId
	 * @param newTraderPwd
	 *            使用AES(md5（密码）,secretKey） 加密
	 * @param secretKey
	 * @throws HsException
	 */
	@Override
	public void resetTradePwd(AsResetTradePwdConsumer params)
			throws HsException {
		// 验证数据
		validResetTradePwdConsumerParams(params);
		// 验证是否通过消费者身份验证
		validConsumerRandomToken(params);
		// 覆盖更新消费者交易密码
		String userType = params.getUserType().trim();
		String newTradePwd = params.getNewTraderPwd().trim();
		String secretKey = params.getSecretKey().trim();
		String custId = params.getCustId().trim();
		saveTradePwd(custId, newTradePwd, userType, secretKey, "");
	}

	/**
	 * 验证是否通过消费者身份验证
	 * 
	 * @param params
	 * @throws HsException
	 */
	private void validConsumerRandomToken(AsResetTradePwdConsumer params)
			throws HsException {

		String cerType = params.getCerType().trim();
		String cerNo = params.getCerNo().trim();
		String random = params.getRandom().trim();
		String certificate = commonCacheService.getCertificateCache(cerType,
				cerNo);
		String isnoreCheckCode = StringUtils.nullToEmpty(SysConfig
				.getIgnoreCheckCode());
		if (!"1".equals(isnoreCheckCode)) {// 是否忽略持卡人身份验证通过发送的随机数验证（0：不忽略 1：忽略）
			if (StringUtils.isBlank(certificate)) {
				throw new HsException(
						ErrorCodeEnum.CONSUMER_AUTHENTICATION_IS_EXPIRED.getValue(),

						ErrorCodeEnum.CONSUMER_AUTHENTICATION_IS_EXPIRED
								.getDesc());
			}

			if (!certificate.equals(random)) {
				throw new HsException(
						ErrorCodeEnum.NOT_PASS_CONSUMER_AUTHENTICATION
								.getValue(),
						ErrorCodeEnum.NOT_PASS_CONSUMER_AUTHENTICATION
								.getDesc());
			}
		}
	}

	/**
	 * 检查入参
	 * 
	 * @param params
	 * @throws HsException
	 */
	private void validResetTradePwdConsumerParams(AsResetTradePwdConsumer params)
			throws HsException {
		if (null == params) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"params is null");
		}
		if (isBlank(params.getCustId())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"客户号不能为空");
		}
		if (isBlank(params.getSecretKey())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"secretKey不能为空");
		}
		if (isBlank(params.getCerNo())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"证件号码不能为空");
		}
		if (isBlank(params.getCerType())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"证件类型不能为空");
		}
		if (isBlank(params.getNewTraderPwd())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"新交易密码不能为空");
		}
		if (isBlank(params.getRandom())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"随机token不能为空");
		}
		if (isBlank(params.getUserType())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"用户类型不能为空");
		}
	}

	@Override
	public void resetTradePwdByAuthCode(
			AsResetTradePwdAuthCode resetTradePwdAuthCode) throws HsException {
		validResetTradePwdAuthCodeParams(resetTradePwdAuthCode);
		String authCode = resetTradePwdAuthCode.getAuthCode().trim();
		// 还原为密码为 md5(密码)
		String secretKey = resetTradePwdAuthCode.getSecretKey().trim();
		String newTradePwd = resetTradePwdAuthCode.getNewTraderPwd().trim();
		newTradePwd = StringEncrypt.decrypt(newTradePwd, secretKey);
		EntExtendInfo entExtendInfo = commonCacheService.searchEntExtendInfo(resetTradePwdAuthCode.getCustId());
		CustIdGenerator.isEntExtendExist(entExtendInfo, resetTradePwdAuthCode.getCustId());
		// 验证授权码
		String conrrectAuthCode = StringUtils.nullToEmpty(commonCacheService
				.getSmsAuthCodeCache(entExtendInfo.getContactPhone()));
		String isnoreCheckCode = StringUtils.nullToEmpty(SysConfig
				.getIgnoreCheckCode());
		if (!"1".equals(isnoreCheckCode)) {// 是否忽略手机短信授权码验证（0：不忽略 1：忽略）
			if (StringUtils.isBlank(conrrectAuthCode)) {
				throw new HsException(
						ErrorCodeEnum.AUTHCODE_IS_EXPIRED.getValue(),
						ErrorCodeEnum.AUTHCODE_IS_EXPIRED.getDesc());

			}
			if (!authCode.trim().equals(conrrectAuthCode)) {
				throw new HsException(
						ErrorCodeEnum.AUTHCODE_NOT_MATCH.getValue(),
						ErrorCodeEnum.AUTHCODE_NOT_MATCH.getDesc());
			}
		}
		commonCacheService.removeSmsAuthCodeCache(entExtendInfo.getContactPhone());
		String salt = CSPRNG.generateRandom(SysConfig.getCsprLen());
		newTradePwd = StringEncrypt.sha256(newTradePwd + salt);
		// 设置交易密码
		String custId = resetTradePwdAuthCode.getCustId().trim();
		String operCustId = resetTradePwdAuthCode.getOperCustId().trim();
		saveEntTradePwd(custId, newTradePwd, salt, operCustId);
	}

	/**
	 * 检查入参
	 * 
	 * @param params
	 * @throws HsException
	 */
	public void validResetTradePwdAuthCodeParams(AsResetTradePwdAuthCode params)
			throws HsException {
		if (null == params) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"params不能为空");
		}
		if (isBlank(params.getCustId())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"客户号不能为空");
		}
		if (isBlank(params.getSecretKey())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"secretKey不能为空");
		}
		if (isBlank(params.getAuthCode())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"authCode不能为空");
		}
		if (isBlank(params.getNewTraderPwd())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"newTraderPwd不能为空");
		}

		if (isBlank(params.getOperCustId())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"操作员不能为空");
		}
	}

	/**
	 * 检查登陆密码
	 * 
	 * @param custId
	 * @param loginPwd
	 * @param userType
	 * @param secretKey
	 * @throws HsException
	 */
	@Override
	public void checkLoginPwd(String custId, String loginPwd, String userType,
			String secretKey) throws HsException {
		// 验证数据
		validLoginPwdParams(custId, loginPwd, userType, secretKey);
		// 检查登陆密码
		validLoginPwd(custId, userType, loginPwd, secretKey);

	}

	/**
	 * 检查入参
	 * 
	 * @param custId
	 * @param loginPwd
	 * @param userType
	 * @param secretKey
	 * @throws HsException
	 */
	private void validLoginPwdParams(String custId, String loginPwd,
			String userType, String secretKey) throws HsException {
		if (isBlank(custId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"custId不能为空");
		}
		if (isBlank(secretKey)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"secretKey不能为空");
		}
		if (isBlank(loginPwd)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"登陆密码不能为空");
		}
		if (isBlank(userType)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"userType不能为空");
		}
	}

	/**
	 * 设置交易密码（初始化）
	 * 
	 * @param custId
	 * @param newTradePwd
	 *            使用AES(md5（密码）,secretKey） 加密
	 * @param userType
	 * @param secretKey
	 * @throws HsException
	 */
	public void setTradePwd(String custId, String tradePwd, String userType,
			String secretKey, String operCustId) throws HsException {
		// 数据验证
		validSetTradePwdParams(custId, tradePwd, userType, secretKey,
				operCustId);
		saveTradePwd(custId, tradePwd, userType, secretKey, operCustId);
	}

	public void setLogPwd(String custId, String newLoginPwd, String userType,
			String secretKey) throws HsException {
		// 数据验证
		saveLoginPwd(newLoginPwd, secretKey, userType, custId, custId);
	}

	/**
	 * 重置消费者交易密码身份验证
	 * 
	 * @param mainInfo
	 *            消费者重要信息
	 * @return
	 * @throws HsException
	 */
	@Override
	public String checkMainInfo(AsMainInfo mainInfo) throws HsException {
		validMainInfo(mainInfo);
		// 验证登录密码
		matchAesLoginPwdAndProcFailed(UserTypeEnum.CARDER, null,
				mainInfo.getPerCustId(), null, mainInfo.getLoginPwd(),
				mainInfo.getSecretKey());
		checkMainRealNameInfo(mainInfo, UserTypeEnum.CARDER.getType());
		String certificate = CSPRNG.generateRandom(SysConfig.getCsprLen());
		String cerType = mainInfo.getCerType().trim();
		String cerNo = mainInfo.getCerNo().trim();
		commonCacheService.addCertificateCache(cerType, cerNo, certificate);
		return certificate;
	}

	/**
	 * 重置非持卡人交易密码身份验证
	 * 
	 * @param mainInfo
	 *            消费者重要信息
	 * @return
	 * @throws HsException
	 */
	@Override
	public String checkNoCarderMainInfo(AsNoCarderMainInfo mainInfo)
			throws HsException {
		validNoCarderMainInfo(mainInfo);
		String mobile = mainInfo.getMobile();
		String perCustId = commonCacheService.findCustIdByMobile(mobile);
		CustIdGenerator.isNoCarderExist(perCustId, mobile);
		// 验证登录密码
		matchAesLoginPwdAndProcFailed(UserTypeEnum.NO_CARDER, null,
				perCustId, null, mainInfo.getLoginPwd(),
				mainInfo.getSecretKey());
		//验证短信验证码
		String code = mobileService.checkSmsCode(mobile, mainInfo.getValidCode());
		commonCacheService.addSmsCodeCache(mobile, code);
		return code;
	}

	/**
	 * 检查消费者重要信息中的证件类型、证件号码和真实姓名
	 * 
	 * @param mainInfo
	 * @throws HsException
	 */
	private void checkMainRealNameInfo(AsMainInfo mainInfo, String userType)
			throws HsException {
		String custId = mainInfo.getPerCustId().trim();
		AsRealNameAuth asRealNameAuth = null;
		if (UserTypeEnum.CARDER.getType().equals(userType)) {
			asRealNameAuth = iUCAsCardHolderAuthInfoService
					.searchRealNameAuthInfo(custId);
		} else if (UserTypeEnum.NO_CARDER.getType().equals(userType)) {
			asRealNameAuth = noCardHolderAuthInfoService
					.searchRealNameAuthInfo(custId);
		}
		String correctName = getCorrectName(asRealNameAuth);
		String correctCerNo = StringUtils
				.nullToEmpty(asRealNameAuth.getCerNo());
		String realName = mainInfo.getRealName().trim();
		String cerNo = mainInfo.getCerNo().trim();
		if (!(realName).equals(correctName)) {
			throw new HsException(ErrorCodeEnum.REALNAME_NOT_MATCH.getValue(),
					ErrorCodeEnum.REALNAME_NOT_MATCH.getDesc());
		}
		if (!(correctCerNo.equals(cerNo))) {
			throw new HsException(ErrorCodeEnum.CERNO_NOT_MATCH.getValue(),
					ErrorCodeEnum.CERNO_NOT_MATCH.getDesc());
		}
		if (!"3".equals(asRealNameAuth.getRealNameStatus())) {
			throw new HsException(
					ErrorCodeEnum.NOT_THROUGH_REALNAME_AUTHENTICATION
							.getValue(),
					ErrorCodeEnum.NOT_THROUGH_REALNAME_AUTHENTICATION.getDesc());
		}
	}
	
	private String getCorrectName(AsRealNameAuth asRealNameAuth){
		String name = null;
		String cerType = asRealNameAuth.getCerType();
		if("1".equals(cerType) || "2".equals(cerType)){
			name =  StringUtils.nullToEmpty(asRealNameAuth.getUserName());
		}else if("3".equals(cerType)){
			name = StringUtils.nullToEmpty(asRealNameAuth.getEntName());
		}
		return name;
	}

	/**
	 * 检查消费者重要信息中的入参
	 * 
	 * @param mainInfo
	 * @throws HsException
	 */
	private void validMainInfo(AsMainInfo mainInfo) throws HsException {
		if (null == mainInfo) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"mainInfo is null");
		}
		if (StringUtils.isBlank(mainInfo.getPerCustId())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"客户号为空");
		}
		if (StringUtils.isBlank(mainInfo.getRealName())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"真实姓名为空");
		}
		if (StringUtils.isBlank(mainInfo.getCerNo())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"证件号码为空");
		}
		if (StringUtils.isBlank(mainInfo.getLoginPwd())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"登录密码为空 ");
		}
		if (StringUtils.isBlank(mainInfo.getSecretKey())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"AES秘钥为空");
		}
		if (StringUtils.isBlank(mainInfo.getCerType())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"证件类型");
		}
	}

	/**
	 * 检查消费者重要信息中的入参
	 * 
	 * @param mainInfo
	 * @throws HsException
	 */
	private void validNoCarderMainInfo(AsNoCarderMainInfo mainInfo) throws HsException {
		if (null == mainInfo) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"mainInfo is null");
		}
		if (StringUtils.isBlank(mainInfo.getPerCustId())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"客户号不能为空");
		}
		if (StringUtils.isBlank(mainInfo.getValidCode())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"短信验证码不能为空");
		}
		if (StringUtils.isBlank(mainInfo.getMobile())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"手机号码不能为空");
		}
		if (StringUtils.isBlank(mainInfo.getLoginPwd())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"登录密码为空 ");
		}
		if (StringUtils.isBlank(mainInfo.getSecretKey())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"AES秘钥为空");
		}

	}

	/**
	 * 检查持卡人由POS机渠道过来的登录密码
	 * 
	 * @param resNo
	 *            互生号
	 * @param loginPwd
	 *            登录密码
	 */
	@Override
	public void checkLoginPwdForPOS(String resNo, String loginPwd)
			throws HsException {
		if (StringUtils.isBlank(resNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"互生号为空");
		}
		if (StringUtils.isBlank(loginPwd)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"登录密码为空");
		}

		String custId = commonCacheService.findCustIdByResNo(resNo.trim());
		CustIdGenerator.isCarderExist(custId, resNo);

		// 验证用户名和密码是否正确
		boolean isMatch = checkCardHolderkLoginPwd(custId, loginPwd);
		if (!isMatch) {
			// System.err.println(custId+"密码校验失败：loginPwd="+loginPwd+ "" );
			throw new HsException(ErrorCodeEnum.PWD_LOGIN_ERROR.getValue(),
					resNo + ErrorCodeEnum.PWD_LOGIN_ERROR.getDesc() + loginPwd);
		}

	}

	public boolean checkCardHolderkLoginPwd(String custId, String loginPwd)
			throws HsException {
		if (StringUtils.isBlank(custId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"custId为空");
		}
		if (StringUtils.isBlank(loginPwd)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"登录密码为空");
		}
		CardHolder cardHolder = commonCacheService.searchCardHolderInfo(custId);
		CustIdGenerator.isCarderExist(cardHolder, cardHolder.getPerCustId());
		return checkCardHolderkLoginPwd(cardHolder, loginPwd);
	}

	public boolean checkCardHolderkLoginPwd(CardHolder cardHolder,
			String loginPwd) throws HsException {
		if (StringUtils.isBlank(cardHolder)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"cardHolder为空");
		}
		if (StringUtils.isBlank(loginPwd)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"登录密码为空");
		}

		PasswordBean passwordBean = new PasswordBean();
		passwordBean.setEnt(false);
		passwordBean.setOriginalPwd(cardHolder.getPwdLogin());
		passwordBean.setPwd(loginPwd);
		passwordBean.setRandomToken(null);
		passwordBean.setSaltValue(cardHolder.getPwdLoginSaltValue());
		passwordBean.setUsername(cardHolder.getPerResNo());
		passwordBean.setVersion(cardHolder.getPwdLoginVer());
		// 验证用户名和密码是否正确
		boolean isMatch = passwordService.matchBlankPassword(passwordBean);
		if (!isMatch) {
			String msg = cardHolder.getPerCustId() + "持卡人登陆密码校验失败：loginPwd="
					+ loginPwd + ", db密码=" + passwordBean.getOriginalPwd();
			SystemLog.info(MOUDLENAME, "checkCardHolderkLoginPwd", msg);

		}
		return isMatch;

	}

	/**
	 * 检查持卡人由POS机渠道过来的交易密码
	 * 
	 * @param resNo
	 *            互生号
	 * @param tradePwd
	 *            交易密码
	 */
	@Override
	public void checkTradePwdForPOS(String resNo, String tradePwd)
			throws HsException {
		if (StringUtils.isBlank(resNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"互生号为空");
		}
		if (StringUtils.isBlank(tradePwd)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"交易密码为空");
		}
		// 查找客户号
		String custId = commonCacheService.findCustIdByResNo(resNo.trim());
		CustIdGenerator.isCarderExist(custId, resNo);
		// 获取持卡人信息
		CardHolder cardHolder = commonCacheService
				.searchCardHolderInfo(custId);
		CustIdGenerator.isCarderExist(cardHolder, custId);
		if (StringUtils.isBlank(cardHolder.getPwdTrans())) {
			throw new HsException(
					ErrorCodeEnum.TRANS_PWD_NOT_SET.getValue(),
					ErrorCodeEnum.TRANS_PWD_NOT_SET.getDesc());
		}
		commonService.checkCarderTradePwd(cardHolder, tradePwd);
	}

	/**
	 * 检查用户的交易密码是否已设置（用户仅包含企业、持卡人）
	 * 
	 * @param custId
	 *            客户号
	 * @param userType
	 *            用户类型（1：非持卡人 2：持卡人 3：操作员 4：企业 ）
	 * @return true:已设置 false: 未设置
	 * @throws HsException
	 */
	@Override
	public boolean isSetTradePwd(String perCustId, String userType)
			throws HsException {
		// 验证数据
		if (StringUtils.isBlank(perCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"客户号为空");
		}
		if (StringUtils.isBlank(userType)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"用户类型为空");
		}
		String type = userType.trim();
		if (!UserTypeEnum.CARDER.getType().equals(type)
				&& (!UserTypeEnum.ENT.getType().equals(type))
				&& (!UserTypeEnum.NO_CARDER.getType().equals(type))) {
			throw new HsException(ErrorCodeEnum.USERTYPE_IS_ILLEGAL.getValue(),
					ErrorCodeEnum.USERTYPE_IS_ILLEGAL.getDesc());
		}

		// 检查持卡人交易密码
		boolean status = false;
		String custId = perCustId.trim();
		if (UserTypeEnum.CARDER.getType().equals(type)) {
			CardHolder cardHolder = commonCacheService.searchCardHolderInfo(perCustId);
			CustIdGenerator.isCarderExist(cardHolder, perCustId);
			status = StringUtils.isBlank(cardHolder.getPwdTrans()) ? false
					: true;
		}
		// 检查企业交易密码
		else if (UserTypeEnum.ENT.getType().equals(type)) {
			EntStatusInfo statusInfo = commonCacheService.searchEntStatusInfo(custId);
			CustIdGenerator.isEntStatusInfoExist(statusInfo, custId);
			status = StringUtils.isBlank(statusInfo.getPwdTrans()) ? false
					: true;
		}
		// 检查非持卡人交易密码
		else if (UserTypeEnum.NO_CARDER.getType().equals(type)) {
			NoCardHolder noCardHolder = commonCacheService
					.searchNoCardHolderInfo(custId);
			CustIdGenerator.isNoCarderExist(noCardHolder, custId);
			status = StringUtils.isBlank(noCardHolder.getPwdTrans()) ? false
					: true;
		}
		return status;
	}

	/**
	 * 通过邮箱重置登陆密码
	 * 
	 * @param resetLoginPwdEmail
	 * @throws HsException
	 */
	@Override
	public void resetLoginPwdByEmail(AsResetLoginPwd resetLoginPwdEmail)
			throws HsException {
		validResetLoginPwd(resetLoginPwdEmail);
		String random = resetLoginPwdEmail.getRandom().trim();
		String correctRandom = commonCacheService
				.getBindEmailCodeCache(resetLoginPwdEmail.getEmail());
		String isnoreCheckCode = StringUtils.nullToEmpty(SysConfig
				.getIgnoreCheckCode());
		if (!"1".equals(isnoreCheckCode)) {// 是否忽略邮件验证（0：不忽略 1：忽略）
			if (StringUtils.isBlank(correctRandom)) {
				throw new HsException(ErrorCodeEnum.MAIL_IS_EXPIRED.getValue(),
						ErrorCodeEnum.MAIL_IS_EXPIRED.getDesc());
			}
			if (!correctRandom.equals(random)) {
				throw new HsException(ErrorCodeEnum.MAIL_NOT_MATCH.getValue(),
						ErrorCodeEnum.MAIL_NOT_MATCH.getDesc());
			}
		}
		commonCacheService.removeBindEmailCodeCache(resetLoginPwdEmail.getEmail());
		String newLoginPwd = resetLoginPwdEmail.getNewLoginPwd().trim();
		String secretKey = resetLoginPwdEmail.getSecretKey().trim();
		String userType = resetLoginPwdEmail.getUserType().trim();
		String custId = resetLoginPwdEmail.getCustId();
		saveLoginPwd(newLoginPwd, secretKey, userType, custId, custId);
	}

	/**
	 * 检查入参
	 * 
	 * @param params
	 * @throws HsException
	 */
	private void validResetLoginPwd(AsResetLoginPwd params) throws HsException {
		// 验证数据
		if (null == params) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"params is null");
		}
		if (isBlank(params.getCustId())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"客户号不能为空");
		}
		if (isBlank(params.getNewLoginPwd())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"新登录密码不能为空");
		}
		if (isBlank(params.getSecretKey())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"secretKey不能为空");
		}
		if (isBlank(params.getUserType())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"用户类型不能为空");
		}
		if (isBlank(params.getRandom())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"随机数验证码不能为空");
		}
	}

	/**
	 * 保存持卡人登录密码
	 * 
	 * @param custId
	 * @param newPwd
	 * @throws HsException
	 */
	private void saveCardHolderLoginPwd(String custId, String newLoginPwd,
			String salt) throws HsException {
		CardHolder cardHolder = new CardHolder();
		cardHolder.setPerCustId(custId);
		cardHolder.setUpdatedby(custId);
		cardHolder.setPwdLogin(newLoginPwd);
		cardHolder.setPwdLoginSaltValue(salt);
		cardHolder.setUpdateDate(StringUtils.getNowTimestamp());
		cardHolder.setPwdLoginVer("3");
		commonCacheService.updateCardHolderInfo(cardHolder);
	}

	private void saveSystemLoginPwd(String custId, String newLoginPwd,
			String salt) throws HsException {
		SysOperator operator = new SysOperator();
		operator.setOperCustId(custId);
		operator.setUpdatedby(custId);
		operator.setPwdLogin(newLoginPwd);
		operator.setPwdLoginSaltValue(salt);
		operator.setUpdateDate(StringUtils.getNowTimestamp());
		operator.setPwdLoginVer("3");
		commonCacheService.updateSysOperInfo(operator);
	}

	private void saveDoubleCheckerLoginPwd(String custId, String newLoginPwd,
			String salt) throws HsException {
		DoubleChecker checker = new DoubleChecker();
		checker.setOperCustId(custId);
		checker.setUpdatedby(custId);
		checker.setPwdLogin(newLoginPwd);
		checker.setPwdLoginSaltValue(salt);
		checker.setUpdateDate(StringUtils.getNowTimestamp());
		checker.setPwdLoginVer("3");
		commonCacheService.updateDoubleCheckerInfo(checker);
	}

	/**
	 * 保存持卡人登录密码
	 * 
	 * @param custId
	 * @param newPwd
	 * @throws HsException
	 */
	private void saveCardHolderLoginPwd(String custId, String newLoginPwd,
			String salt, String operCustId) throws HsException {
		CardHolder cardHolder = new CardHolder();
		cardHolder.setPerCustId(custId);
		cardHolder.setUpdatedby(operCustId);
		cardHolder.setPwdLogin(newLoginPwd);
		cardHolder.setPwdLoginSaltValue(salt);
		cardHolder.setUpdateDate(StringUtils.getNowTimestamp());
		cardHolder.setPwdLoginVer("3");
		commonCacheService.updateCardHolderInfo(cardHolder);
	}

	/**
	 * 保存非持卡人登录密码
	 * 
	 * @param custId
	 * @param newPwd
	 * @throws HsException
	 */
	private void saveNoCardHolderLoginPwd(String custId, String newLoginPwd,
			String salt) throws HsException {
		NoCardHolder noCardHolder = new NoCardHolder();
		noCardHolder.setPerCustId(custId);
		noCardHolder.setUpdatedby(custId);
		noCardHolder.setPwdLogin(newLoginPwd);
		noCardHolder.setPwdLoginSaltValue(salt);
		noCardHolder.setUpdateDate(StringUtils.getNowTimestamp());
		noCardHolder.setPwdLoginVer("3");
		noCardHolderService.updateNoCardHolderInfo(noCardHolder);
	}

	/**
	 * 保存持卡人交易密码
	 * 
	 * @param custId
	 * @param newPwd
	 * @throws HsException
	 */
	private void saveCardHolderTradePwd(String custId, String newTradePwd,
			String salt) throws HsException {
		CardHolder cardHolder = new CardHolder();
		cardHolder.setPerCustId(custId);
		cardHolder.setUpdatedby(custId);
		cardHolder.setPwdTrans(newTradePwd);
		cardHolder.setPwdTransSaltValue(salt);
		cardHolder.setUpdateDate(StringUtils.getNowTimestamp());
		cardHolder.setPwdLoginVer("3");
		commonCacheService.updateCardHolderInfo(cardHolder);
	}

	private void saveNoCarderTradePwd(String custId, String newTradePwd,
			String salt) throws HsException {
		NoCardHolder noCardHolder = new NoCardHolder();
		noCardHolder.setPerCustId(custId);
		noCardHolder.setUpdatedby(custId);
		noCardHolder.setPwdTrans(newTradePwd);
		noCardHolder.setPwdTransSaltValue(salt);
		noCardHolder.setUpdateDate(StringUtils.getNowTimestamp());
		noCardHolder.setPwdLoginVer("3");
		commonCacheService.updateNoCardHolderInfo(noCardHolder);
	}

	/**
	 * 保存企业交易密码
	 * 
	 * @param custId
	 * @param newPwd
	 * @throws HsException
	 */
	private void saveEntTradePwd(String custId, String newTradePwd,
			String salt, String operCustId) throws HsException {
		EntStatusInfo entStatusInfo = new EntStatusInfo();
		entStatusInfo.setEntCustId(custId);
		entStatusInfo.setUpdatedby(custId);
		entStatusInfo.setPwdTrans(newTradePwd);
		entStatusInfo.setPwdTransSaltValue(salt);
		entStatusInfo.setUpdateDate(StringUtils.getNowTimestamp());
		entStatusInfo.setPwdTransVer("3");
		commonCacheService.updateEntStatusInfoChagTradePwd(entStatusInfo,
				operCustId);
	}

	/**
	 * 保存操作员的登陆密码
	 * 
	 * @param custId
	 * @param newLoginPwd
	 * @param salt
	 * @param operCustId
	 */
	private void saveOpeLoginPwd(String custId, String newLoginPwd,
			String salt, String operCustId) {
		Operator operator = new Operator();
		operator.setOperCustId(custId);
		operator.setUpdatedby(operCustId);
		operator.setPwdLogin(newLoginPwd);
		operator.setPwdLoginSaltValue(salt);
		operator.setUpdateDate(StringUtils.getNowTimestamp());
		operator.setPwdLoginVer("3");
		commonCacheService.updateOperChgPwd(operator);
	}

	/**
	 * 检查登陆密码是否正确
	 * 
	 * @param custId
	 *            客户号
	 * @param userType
	 *            用户类型
	 * @param oldLoginPwd
	 *            md5登陆密码
	 * @return
	 */
	private void validLoginPwd(String custId, String userType,
			String oldLoginPwd, String secretKey) throws HsException {
		// commonService.validLoginPwd(custId, userType, oldLoginPwd,
		// secretKey);
		// UserTypeEnum.CARDER.getType().equals(userType)
		UserTypeEnum ute = UserTypeEnum.getUserTypeEnum(userType);
		matchAesLoginPwdAndProcFailed(ute, null, custId, null, oldLoginPwd,
				secretKey);
	}

	/**
	 * 
	 * 检查入参
	 * 
	 * @param custId
	 *            客户号
	 * @param userType
	 *            用户类型
	 * @param oldLoginPwd
	 *            使用AES(md5（密码）,secretKey） 加密
	 * @param newLoginPwd
	 *            使用AES(md5（密码）,secretKey） 加密
	 * @param secretKey
	 * @throws HsException
	 */
	private void validUpdateLoginPwdParams(String custId, String userType,
			String oldLoginPwd, String newLoginPwd, String secretKey)
			throws HsException {
		if (isBlank(custId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"客户号不能为空");
		}
		if (isBlank(userType)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"用户类型不能为空");
		}
		if (isBlank(oldLoginPwd)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"旧登录密码不能为空");
		}
		if (isBlank(newLoginPwd)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"新登录密码不能为空");
		}
		if (isBlank(secretKey)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"secretKey不能为空");
		}
	}

	/**
	 * 检查入参
	 * 
	 * @param custId
	 * @param tradePwd
	 * @param userType
	 * @param secretKey
	 * @throws HsException
	 */
	private void validTradePwdParams(String custId, String tradePwd,
			String userType, String secretKey) throws HsException {
		if (isBlank(custId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"custId不能为空");
		}
		if (isBlank(secretKey)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"secretKey不能为空");
		}
		if (isBlank(tradePwd)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"tradePwd不能为空");
		}
		if (isBlank(userType)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"userType不能为空");
		}
	}

	/**
	 * 检查入参
	 * 
	 * @param params
	 * @throws HsException
	 */
	private void validUpdateTradePwdParams(AsUpdateTradePwd params)
			throws HsException {
		if (null == params) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"params is null");
		}
		if (isBlank(params.getCustId())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"客户号不能为空");
		}
		if (isBlank(params.getUserType())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"用户类型不能为空");
		}
		if (isBlank(params.getOldTradePwd())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"旧交易密码不能为空");
		}
		if (isBlank(params.getNewTradePwd())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"新交易密码不能为空");
		}
		if (isBlank(params.getOperCustId())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"操作员客户号不能为空");
		}
		if (isBlank(params.getSecretKey())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"secretKey不能为空");
		}
	}

	/**
	 * 检查入参
	 * 
	 * @param custId
	 * @param tradePwd
	 * @param userType
	 * @param secretKey
	 * @throws HsException
	 */
	private void validSetTradePwdParams(String custId, String tradePwd,
			String userType, String secretKey, String operCustId)
			throws HsException {
		if (StringUtils.isBlank(custId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"客户号为空");
		}
		if (StringUtils.isBlank(tradePwd)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"交易密码为空");
		}
		if (StringUtils.isBlank(userType)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"用户类型为空");
		}
		if (StringUtils.isBlank(secretKey)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"随机token为空");
		}
		if (UserTypeEnum.ENT.getType().equals(userType)
				&& StringUtils.isBlank(operCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"操作员客户号不能为空");
		}
	}

	@Override
	public String findCustIdByUserName(String resNo, String userType)
			throws HsException {
		if (StringUtils.isBlank(resNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"用户名不能为空");
		}
		if (StringUtils.isBlank(userType)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"用户类型不能为空");
		}
		String custId = "";
		if (UserTypeEnum.NO_CARDER.getType().equals(userType)) {
			custId = commonCacheService.findCustIdByMobile(resNo);
			CustIdGenerator.isNoCarderExist(custId, resNo);
		} else if (UserTypeEnum.CARDER.getType().equals(userType)) {
			custId = commonCacheService.findCustIdByResNo(resNo);
			CustIdGenerator.isCarderExist(custId, resNo);
		} else if (UserTypeEnum.OPERATOR.getType().equals(userType)
				|| UserTypeEnum.ENT.getType().equals(userType)) {
			// 企业查询管理员客户号兼容 usertype 3or4
			String userName = "0000";
			custId = commonCacheService.findOperCustId(resNo, userName);
			CustIdGenerator.isOperExist(custId, resNo, userName);
			Operator operator = commonCacheService.searchOperByCustId(custId);
			CustIdGenerator.isOperExist(operator, custId);
			commonCacheService.CheckEntClose(operator.getEntCustId());//检查企业是否已注销或已关闭
		} else {
			throw new HsException(ErrorCodeEnum.USERTYPE_IS_ILLEGAL.getValue(),
					userType + ErrorCodeEnum.USERTYPE_IS_ILLEGAL.getDesc());
		}
		SystemLog.debug(MOUDLENAME, "findCustIdByUserName", "custId[" + custId
				+ "],userType[" + userType + "]");
		return custId;
	}

	public String findCustIdByUserName(String entResNo, String userName,
			String userType) throws HsException {
		if (StringUtils.isBlank(userName)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"用户名不能为空");
		}
		if (StringUtils.isBlank(userType)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"用户类型不能为空");
		}
		if (UserTypeEnum.OPERATOR.getType().equals(userType)
				&& StringUtils.isBlank(entResNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"企业互生号不能为空");
		}
		String custId = "";
		if (UserTypeEnum.CARDER.getType().equals(userType)) {
			custId = commonCacheService.findCustIdByResNo(userName);
			CustIdGenerator.isCarderExist(custId, userName);
		} else if (UserTypeEnum.OPERATOR.getType().equals(userType)) {
			custId = commonCacheService.findOperCustId(entResNo, userName);
			CustIdGenerator.isOperExist(custId, entResNo, userName);
			Operator operator = commonCacheService.searchOperByCustId(custId);
			CustIdGenerator.isOperExist(operator, custId);
			commonCacheService.CheckEntClose(operator.getEntCustId());//检查企业是否已注销或已关闭
		} else if (UserTypeEnum.NO_CARDER.getType().equals(userType)) {
			custId = commonCacheService.findCustIdByMobile(userName);
			CustIdGenerator.isNoCarderExist(custId, userName);
		} else {
			throw new HsException(ErrorCodeEnum.USERTYPE_IS_ILLEGAL.getValue(),
					ErrorCodeEnum.USERTYPE_IS_ILLEGAL.getDesc());
		}
		SystemLog.debug(MOUDLENAME, "findCustIdByUserName", "custId[" + custId
				+ "],userType[" + userType + "]");
		return custId;
	}

	@Override
	public void resetLogPwdByAdmin(String operNo, String adminCustId,
			String secretKey, String newLoginPwd) throws HsException {
		validParamsAdmin(operNo, adminCustId, secretKey, newLoginPwd);
		Operator admin = commonCacheService.searchOperByCustId(adminCustId);
		CustIdGenerator.isOperExist(admin, adminCustId);
		if (null == admin.getIsAdmin() || 1 != admin.getIsAdmin()) {
			throw new HsException(ErrorCodeEnum.OPER_IS_NOT_ADMIN.getValue(),
					ErrorCodeEnum.OPER_IS_NOT_ADMIN.getDesc());
		}
		if (admin.getOperNo().equals(operNo.trim())) {
			throw new HsException(
					ErrorCodeEnum.CANOT_RESET_ADMIN_LOGPWD.getValue(),
					ErrorCodeEnum.CANOT_RESET_ADMIN_LOGPWD.getDesc());
		}
		String operCustId = commonCacheService.findOperCustId(
				admin.getEntResNo(), operNo);
		CustIdGenerator.isOperExist(operCustId, admin.getEntResNo(), operNo);
		saveLoginPwd(newLoginPwd, secretKey, UserTypeEnum.OPERATOR.getType(),
				operCustId, adminCustId);
	}

	private void validParamsAdmin(String operNo, String adminCustId,
			String secretKey, String newLoginPwd) throws HsException {
		if (StringUtils.isBlank(operNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"普通操作员账号不能为空");
		}
		if (StringUtils.isBlank(adminCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"超级管理员客户号不能为空");
		}
		if (StringUtils.isBlank(secretKey)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"AES秘钥不能为空");
		}
		if (StringUtils.isBlank(newLoginPwd)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"新密码不能为空");
		}
	}

	/**
	 * 验证地区平台的复核员登录密码
	 * 
	 * @param entResNo
	 *            企业互生号
	 * @param userName
	 *            复核员的账号
	 * @param loginPwd
	 *            AES登录密码
	 * @param secretKey
	 *            AES秘钥
	 */
	public String checkApsReCheckerLoginPwd(String apsResNo, String userName,
			String loginPwd, String secretKey) throws HsException {
		if (isBlank(apsResNo) || isBlank(userName)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"entResNo或userName为空");
		}
		String reCheckCustId = commonCacheService.findOperCustId(apsResNo,
				userName);
		CustIdGenerator.isOperExist(reCheckCustId, apsResNo, userName);
		Operator operator = commonCacheService
				.searchOperByCustId(reCheckCustId);
		CustIdGenerator.isOperExist(operator, reCheckCustId);
		String entCustId = StringUtils.nullToEmpty(operator.getEntCustId());
		if (isBlank(entCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"企业客户号为空");
		}
		EntExtendInfo entExtendInfo = commonCacheService
				.searchEntExtendInfo(entCustId);
		CustIdGenerator.isEntExtendExist(entExtendInfo, entCustId);
		Integer custType = entExtendInfo.getCustType();
		if (null == custType || 6 != custType) {
			throw new HsException(
					ErrorCodeEnum.RECHERCKER_IS_NOT_APS.getValue(),
					ErrorCodeEnum.RECHERCKER_IS_NOT_APS.getDesc());
		}
		checkLoginPwd(reCheckCustId, loginPwd, UserTypeEnum.OPERATOR.getType(),
				secretKey);
		return reCheckCustId;
	}

	/**
	 * 验证管理平台的复核员登录密码
	 * 
	 * @param entResNo
	 *            企业互生号
	 * @param userName
	 *            复核员的账号
	 * @param loginPwd
	 *            AES登录密码
	 * @param secretKey
	 *            AES秘钥
	 */
	public String checkMcsReCheckerLoginPwd(String mcsResNo, String userName,
			String loginPwd, String secretKey) throws HsException {
		if (isBlank(mcsResNo) || isBlank(userName)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"entResNo或userName为空");
		}
		String reCheckCustId = commonCacheService.findOperCustId(mcsResNo,
				userName);
		CustIdGenerator.isOperExist(reCheckCustId, mcsResNo, userName);
		Operator operator = commonCacheService
				.searchOperByCustId(reCheckCustId);
		CustIdGenerator.isOperExist(operator, reCheckCustId);
		String entCustId = StringUtils.nullToEmpty(operator.getEntCustId());
		if (isBlank(entCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"企业客户号为空");
		}
		EntExtendInfo entExtendInfo = commonCacheService
				.searchEntExtendInfo(entCustId);
		CustIdGenerator.isEntExtendExist(entExtendInfo, entCustId);
		Integer custType = entExtendInfo.getCustType();
		if (null == custType || 5 != custType) {
			throw new HsException(
					ErrorCodeEnum.RECHERCKER_IS_NOT_MCS.getValue(),
					ErrorCodeEnum.RECHERCKER_IS_NOT_MCS.getDesc());
		}
		checkLoginPwd(reCheckCustId, loginPwd, UserTypeEnum.OPERATOR.getType(),
				secretKey);
		return reCheckCustId;
	}

	private void validReCheckParams(String regionalResNo, String userName,
			String loginPwd, String secretKey, String perCustId)
			throws HsException {
		if (StringUtils.isBlank(regionalResNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"地区平台互生号不能为空");
		}
		if (StringUtils.isBlank(userName)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"复核员账号不能为空");
		}
		if (StringUtils.isBlank(loginPwd)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"登录密码不能为空");
		}
		if (StringUtils.isBlank(secretKey)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"秘钥不能为空");
		}
		if (StringUtils.isBlank(perCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"客户号/企业互生号不能为空");
		}
	}

	@Override
	public void resetLogPwdForCarderByReChecker(String apsResNo,
			String userName, String loginPwd, String secretKey,
			String perCustId, String operCustId) throws HsException {
		// 检查入参
		validReCheckParams(apsResNo, userName, loginPwd, secretKey, perCustId);
		CardHolder cardHolder =null;
//		cardHolder =commonCacheService.searchCardHolderInfo(perCustId);
		//重置持卡人密码时，从数据库中获取最新手机号
		cardHolder = cardHolderMapper.selectByPrimaryKey(perCustId);
		if (StringUtils.isBlank(cardHolder.getMobile())) {
			throw new HsException(ErrorCodeEnum.USER_NOT_SET_MOBILE.getValue(),
					"perCustId[" + perCustId + "],"
							+ ErrorCodeEnum.USER_NOT_SET_MOBILE.getDesc());
		}
		// 验证复核员登录密码
		String reCheckedCustId = checkApsReCheckerLoginPwd(apsResNo, userName,
				loginPwd, secretKey);
		// 双签角色权限检查
		doubleCheckAps(operCustId, reCheckedCustId);
		// 重置持卡人的登录密码
		String salt = CSPRNG.generateRandom(SysConfig.getCsprLen());
		String cleartextPwd = CSPRNG.generateRandom(6);
		PasswordBean passwordBean = new PasswordBean();
		passwordBean.setEnt(true);
		passwordBean.setVersion("3");
		passwordBean.setSaltValue(salt);
		passwordBean.setPwd(cleartextPwd);
		String pwd = passwordService.genDesByBlankPwd(passwordBean);
		saveCardHolderLoginPwd(perCustId, pwd, salt, reCheckedCustId);
		// 发送新生成的登录密码到客户的手机上
		mobileService.sendCarderRestPwdByReChecker(cardHolder, cleartextPwd);
	}

	@Override
	public void resetLogPwdForOperatorByReChecker(String apsResNo,
			String userName, String loginPwd, String secretKey,
			String entResNo, String operCustId) throws HsException {
		// 检查入参
		validReCheckParams(apsResNo, userName, loginPwd, secretKey, entResNo);

		String entCustId = commonCacheService.findEntCustIdByEntResNo(entResNo);
		EntExtendInfo extendInfo = commonCacheService
				.searchEntExtendInfo(entCustId);
		if (StringUtils.isBlank(extendInfo.getContactPhone())) {
			throw new HsException(ErrorCodeEnum.USER_NOT_SET_MOBILE.getValue(),
					"entCustId[" + entCustId + "],"
							+ ErrorCodeEnum.USER_NOT_SET_MOBILE.getDesc());
		}
		String adminUserName = "0000";
		String adminCustId = commonCacheService.findOperCustId(entResNo,
				adminUserName);
		CustIdGenerator.isOperExist(adminCustId, entResNo, adminUserName);
		// 验证复核员登录密码
		String reCheckedCustId = checkApsReCheckerLoginPwd(apsResNo, userName,
				loginPwd, secretKey);
		// 双签角色权限检查
		doubleCheckAps(operCustId, reCheckedCustId);
		// 重置操作员的登录密码
		String salt = CSPRNG.generateRandom(SysConfig.getCsprLen());
		String cleartextPwd = CSPRNG.generateRandom(6);

		PasswordBean passwordBean = new PasswordBean();
		passwordBean.setEnt(true);
		passwordBean.setVersion("3");
		passwordBean.setSaltValue(salt);
		passwordBean.setPwd(cleartextPwd);
		String pwd = passwordService.genDesByBlankPwd(passwordBean);

		saveOpeLoginPwd(adminCustId, pwd, salt, reCheckedCustId);
		// 发送新生成的登录密码到客户的手机上
		mobileService.sendOperRestPwdByReChecker(extendInfo, cleartextPwd);
	}

	/**
	 * 地区平台复核员双签角色权限检查
	 * 
	 * @param operCustId
	 *            当前登录的操作员的客户号
	 * @param adminCustId
	 *            复核员的客户号
	 * @throws HsException
	 */
	private void doubleCheckAps(String operCustId, String adminCustId)
			throws HsException {
		if (operCustId.equals(adminCustId)) {
			throw new HsException(
					ErrorCodeEnum.DUBLE_CHECK_MUST_TWO_PERSON.getValue(),
					ErrorCodeEnum.DUBLE_CHECK_MUST_TWO_PERSON.getDesc());
		}
		checkSameApsEnt(operCustId, adminCustId);
		boolean isChecker = roleService.hasRoleId(adminCustId,
				RoleEnum.PLAT_ENT_DOUBLE_CHECKER.getId());
		if (!isChecker) {
			throw new HsException(
					ErrorCodeEnum.APS_CHECKER_NOT_HAVE_AUTHORITY.getValue(),
					ErrorCodeEnum.APS_CHECKER_NOT_HAVE_AUTHORITY.getDesc()
							+ ",adminCustId[" + adminCustId + "],RoleEnum["
							+ RoleEnum.PLAT_ENT_DOUBLE_CHECKER.getId() + "]");
		}
	}

	/**
	 * 管理平台复核员双签角色权限检查
	 * 
	 * @param operCustId
	 *            当前登录的操作员的客户号
	 * @param adminCustId
	 *            复核员的客户号
	 * @throws HsException
	 */
	private void doubleCheckMcs(String operCustId, String adminCustId)
			throws HsException {
		if (operCustId.equals(adminCustId)) {
			throw new HsException(
					ErrorCodeEnum.DUBLE_CHECK_MUST_TWO_PERSON.getValue(),
					ErrorCodeEnum.DUBLE_CHECK_MUST_TWO_PERSON.getDesc());
		}
		checkSameMcsEnt(operCustId, adminCustId);
		boolean isChecker = roleService.hasRoleId(adminCustId,
				RoleEnum.MANAGE_ENT_DOUBLE_CHECKER.getId());
		if (!isChecker) {
			throw new HsException(
					ErrorCodeEnum.MCS_CHECKER_NOT_HAVE_AUTHORITY.getValue(),
					ErrorCodeEnum.MCS_CHECKER_NOT_HAVE_AUTHORITY.getDesc()
							+ ",adminCustId[" + adminCustId + "],RoleEnum["
							+ RoleEnum.MANAGE_ENT_DOUBLE_CHECKER.getId() + "]");
		}
	}

	private void checkSameMcsEnt(String operCustId, String adminCustId)
			throws HsException {
		Operator nomalOperator = commonCacheService
				.searchOperByCustId(operCustId);
		Operator adminOperator = commonCacheService
				.searchOperByCustId(adminCustId);
		CustIdGenerator.isOperExist(nomalOperator, operCustId);
		CustIdGenerator.isOperExist(adminOperator, adminCustId);
		if (!nomalOperator.getEntCustId().equals(adminOperator.getEntCustId())) {
			throw new HsException(ErrorCodeEnum.IS_NOT_ENT_MCS.getValue(),
					ErrorCodeEnum.IS_NOT_ENT_MCS.getDesc() + ",operCustId["
							+ operCustId + "],adminCustId[" + adminCustId + "]");
		}
	}

	private void checkSameApsEnt(String operCustId, String adminCustId)
			throws HsException {
		Operator nomalOperator = commonCacheService
				.searchOperByCustId(operCustId);
		Operator adminOperator = commonCacheService
				.searchOperByCustId(adminCustId);
		CustIdGenerator.isOperExist(nomalOperator, operCustId);
		CustIdGenerator.isOperExist(adminOperator, adminCustId);
		if (!nomalOperator.getEntCustId().equals(adminOperator.getEntCustId())) {
			throw new HsException(ErrorCodeEnum.IS_NOT_ENT_APS.getValue(),
					ErrorCodeEnum.IS_NOT_ENT_APS.getDesc() + ",operCustId["
							+ operCustId + "],adminCustId[" + adminCustId + "]");
		}
	}

	@Override
	public void resetTradePwdForCarderByReChecker(String apsResNo,
			String userName, String loginPwd, String secretKey,
			String perCustId, String operCustId) throws HsException {
		validReCheckParams(apsResNo, userName, loginPwd, secretKey, perCustId);
		// 验证复核员登录密码
		String reCheckedCustId = checkApsReCheckerLoginPwd(apsResNo, userName,
				loginPwd, secretKey);
		// 双签角色权限检查
		doubleCheckAps(operCustId, reCheckedCustId);
		// 重置持卡人的密码
		String salt = CSPRNG.generateRandom(SysConfig.getCsprLen());
		String clearTranPwd = CSPRNG.generateRandom(8);
		String newTradePwd = StringEncrypt.string2MD5(clearTranPwd) + salt;
		newTradePwd = StringEncrypt.sha256(newTradePwd);
		saveTradePwdByReChecker(perCustId, newTradePwd,
				UserTypeEnum.CARDER.getType(), secretKey, reCheckedCustId, salt);
		CardHolder cardHolder = commonCacheService
				.searchCardHolderInfo(perCustId);
		// 发送新生成的交易密码到客户的手机上
		mobileService.sendRestTranPwdCodeByReChecker(cardHolder, clearTranPwd);
	}

	@Override
	public void resetEntTradePwdByReChecker(String apsResNo, String userName,
			String loginPwd, String secretKey, String entResNo,
			String operCustId) throws HsException {
		validReCheckParams(apsResNo, userName, loginPwd, secretKey, entResNo);
		// 验证复核员登录密码
		String reCheckedCustId = checkApsReCheckerLoginPwd(apsResNo, userName,
				loginPwd, secretKey);
		// 双签角色权限检查
		doubleCheckAps(operCustId, reCheckedCustId);
		// 重置持卡人的密码
		String salt = CSPRNG.generateRandom(SysConfig.getCsprLen());
		String clearTranPwd = CSPRNG.generateRandom(8);
		String newTradePwd = StringEncrypt.string2MD5(clearTranPwd) + salt;
		newTradePwd = StringEncrypt.sha256(newTradePwd);
		String entCustId = commonCacheService.findEntCustIdByEntResNo(entResNo);
		saveTradePwdByReChecker(entCustId, newTradePwd,
				UserTypeEnum.ENT.getType(), secretKey, reCheckedCustId, salt);
		EntExtendInfo entExtendInfo = commonCacheService
				.searchEntExtendInfo(entCustId);
		// 发送新生成的交易密码到客户的手机上
		mobileService.sendEntRestTranPwdCodeByReChecker(entExtendInfo,
				clearTranPwd);
	}

	/**
	 * 比对AES的登录密码。比对失败后没有其他操作
	 * 
	 * @param passwordBean
	 * @return
	 */
	public boolean matchAesLoginPwd(PasswordBean passwordBean) {
		return passwordService.matchAesPassword(passwordBean);
	}

	/**
	 * 
	 * @param userType
	 *            用户类型
	 * @param entResNo
	 *            企业资源号，如果为消费者，该值为null
	 * @param custId
	 *            客户号
	 * @param pwd
	 *            AES加密后的密码
	 * @param randomToken
	 *            随机token
	 * @return
	 */
	public UserInfo matchAesLoginPwdAndProcByCustId(UserTypeEnum userType,
			String custId, String pwd, String randomToken) {
		return matchAesLoginPwdAndProcFailed(userType, null, custId, null, pwd,
				randomToken);
	}

	/**
	 * 比对AES登录密码，如果密码比对失败，用户的登录失败次数累加，直至锁定帐户
	 * 
	 * @param userType
	 *            用户类型
	 * @param entResNo
	 *            企业资源号，如果为消费者，该值为null
	 * @param username
	 *            用户名
	 * @param pwd
	 *            AES加密后的密码
	 * @param randomToken
	 *            随机token
	 * @return
	 */
	public UserInfo matchAesLoginPwdAndProcByName(UserTypeEnum userType,
			String entResNo, String username, String pwd, String randomToken) {
		return matchAesLoginPwdAndProcFailed(userType, entResNo, null,
				username, pwd, randomToken);
	}

	public UserInfo matchCarderAesPwdForLogin(String resNo, String pwd,
			String randomToken) throws HsException {
		String perCustId = commonCacheService.findCustIdByResNo(resNo);
		if (StringUtils.isBlank(perCustId)) {
			throw new HsException(ErrorCodeEnum.USER_NOT_FOUND.getValue(),
					"用户不存在或已注销或已禁用");
		}
		UserInfo userInfo = (UserInfo) commonCacheService
				.searchCardHolderInfo(perCustId);
		if (userInfo == null) {
			throw new HsException(ErrorCodeEnum.USER_NOT_FOUND.getValue(),
					"用户不存在或已注销或已禁用");
		}
		int loginFailTimes = commonCacheService.getCarderLoginFailTimesCache(resNo);
		// 验证密码失败次数是否达到上限
		commonService.isReachMaxLoginFailTimes(loginFailTimes, SysConfig.getLoginPwdFailedTimes());
		PasswordBean passwordBean = new PasswordBean();
		passwordBean.setEnt(false);
		passwordBean.setOriginalPwd(userInfo.getPwdLogin());
		passwordBean.setPwd(pwd);
		passwordBean.setRandomToken(randomToken);
		passwordBean.setSaltValue(userInfo.getPwdLoginSaltValue());
		passwordBean.setUsername(resNo);
		passwordBean.setVersion(userInfo.getPwdLoginVer());
		// 验证用户名和密码是否正确
		boolean isMatch = passwordService.matchAesPassword(passwordBean);
		// 登录不成功
		if (!isMatch) {
			// 处理登录密码验证失败
			processLockCarderAccount(resNo, loginFailTimes);
		} else {
			if (loginFailTimes > 0) {
				// 登录密码匹配成功，更新缓存中的登录密码失败次数
				commonCacheService.addCarderLoginFailTimesCache(resNo, 0);
			}
			// 更新密码
			upgradePwdAndVersion(passwordBean, perCustId,UserTypeEnum.CARDER);
		}

		// 登录密码匹配成功
		return userInfo;
	}

	/**
	 * 匹配AES的密码是否一致，如果密码错误，失败次数累加1
	 * 
	 * @param userType
	 *            用户类型
	 * @param entResNo
	 *            企业资源号，企业操作员登录需输入该值
	 * @param custId
	 *            客户号
	 * @param userName
	 *            用户名
	 * @param pwd
	 *            密码，AES
	 * @param randomToken
	 *            随机token
	 * @return
	 */
	private UserInfo matchAesLoginPwdAndProcFailed(UserTypeEnum userType,
			String entResNo, String custId, String userName, String pwd,
			String randomToken) throws HsException {
		// 组装查询的条件
		UserInfo userInfo = null;
		int loginFailTimes = 0;
		String userCustId = null;
		String operNo = null;
		PasswordBean passwordBean = null;
		// 组装数据
		if (userType == UserTypeEnum.CARDER) {
			CardHolder cardHolder = null;
			if (custId == null) {
				String perCustId = commonCacheService.findCustIdByResNo(userName);
				CustIdGenerator.isCarderExist(perCustId, userName);
				cardHolder = commonCacheService.searchCardHolderInfo(perCustId);
			} else {
				cardHolder = commonCacheService
						.searchCardHolderInfo(custId);
			}
			if (null == cardHolder) {
				throw new HsException(
						ErrorCodeEnum.USER_NOT_FOUND.getValue(),
						"用户不存在或已注销或已禁用");
			}
			userInfo = (UserInfo) cardHolder;
			userCustId = cardHolder.getPerCustId();
			userName = cardHolder.getPerResNo();
			operNo = cardHolder.getPerResNo();
			loginFailTimes = commonCacheService.getCarderLoginFailTimesCache(userName);
		} else if (userType == UserTypeEnum.NO_CARDER) {
			// 获取非持卡人信息
			NoCardHolder noCardHolder = null;
			if (custId == null) {
				String perCustId = commonCacheService.findCustIdByMobile(userName);
				CustIdGenerator.isNoCarderExist(perCustId, userName);
				noCardHolder = commonCacheService.searchNoCardHolderInfo(perCustId);
				
			} else {
				noCardHolder = commonCacheService.searchNoCardHolderInfo(custId);
			}
			if (null == noCardHolder) {
				throw new HsException(
						ErrorCodeEnum.USER_NOT_FOUND.getValue(),
						"用户不存在或已注销或已禁用");
			}
			userCustId = noCardHolder.getPerCustId();
			userName = noCardHolder.getMobile();
			operNo = noCardHolder.getMobile();
			userInfo = (NoCardHolder) noCardHolder;
			loginFailTimes = commonCacheService.getNoCardLoginFailTimesCache(userName);
		} else if (userType == UserTypeEnum.SYSTEM) {
			// 获取系统用户信息
			if (StringUtils.isBlank(custId)) {
				custId = sysOperatorService.findCustIdByUserName(userName);
			}
			userInfo = (UserInfo) sysOperatorService
					.searchSysOperInfoByCustIdNoWhere(custId);
			if (userInfo == null) {
				throw new HsException(ErrorCodeEnum.USER_NOT_FOUND.getValue(),
						"用户不存在或已注销或已禁用");
			}
			// 验证用户状态是否正常
			SysOperator oper = (SysOperator) userInfo;
			userName = oper.getUserName();
			if (oper.getIsactive().equals("N")) {
				throw new HsException(ErrorCodeEnum.USER_NOT_FOUND.getValue(),
						custId + "该操作员客户号找不到操作员信息");
				
			}
			operNo = oper.getUserName();
			userCustId = oper.getOperCustId();
			loginFailTimes = commonCacheService.getSysOperLoginFailTimesCache(userName);
		} else if (userType == UserTypeEnum.CHECKER) {
			// 获取系统用户信息
			if (StringUtils.isBlank(custId)) {
				custId = sysOperatorService
						.findCustIdSecondByUserName(userName);
			}
			userInfo = (UserInfo) sysOperatorService
					.searchSysOperSecondByCustId(custId);
			if (userInfo == null) {
				throw new HsException(ErrorCodeEnum.USER_NOT_FOUND.getValue(),
						"用户不存在或已注销或已禁用");
			}
			// 验证用户状态是否正常
			SysOperator oper = (SysOperator) userInfo;
			userName = oper.getUserName();
			if (oper.getIsactive().equals("N")) {
				throw new HsException(ErrorCodeEnum.USER_NOT_FOUND.getValue(),
						custId + "该操作员客户号找不到操作员信息");
			}
			operNo = oper.getUserName();
			loginFailTimes = commonCacheService.getCheckerLoginFailTimesCache(userName);
			userCustId = oper.getOperCustId();
		}
		else if(userType == UserTypeEnum.OPERATOR_USE_CARDER){
			// 如果是操作员使用消费者互生卡号登录，需验证消费者密码
			String operCustId = commonCacheService.findOperCustId(userName);//通过绑定的互生号查询操作员客户号
			CustIdGenerator.isOperExist(operCustId, userName);
			Operator operator = commonCacheService.searchOperByCustId(operCustId);
			if (null == operator) {
				throw new HsException(
						ErrorCodeEnum.USER_NOT_FOUND.getValue(),
						userName + "操作员用户不存在或已注销或已禁用, 操作员使用互生卡号登录异常");
			}
			// 获取消费者的登录密码
			String perCustId = commonCacheService.findCustIdByResNo(userName);//通过绑定的互生号查询消费者客户号
			CustIdGenerator.isCarderExist(perCustId, userName);
			CardHolder ch = commonCacheService
					.searchCardHolderInfo(perCustId);
			if (ch == null) {
				throw new HsException(
						ErrorCodeEnum.USER_NOT_FOUND.getValue(),
						userName + "持卡人用户不存在或已注销或已禁用, 操作员使用互生卡号登录异常");
			}
			// 获取操作员的信息
			userInfo = (UserInfo)operator;
			// 设置消费者的登录密码
			passwordBean =  new PasswordBean();
			passwordBean.setOriginalPwd(ch.getPwdLogin());
			passwordBean.setSaltValue(ch.getPwdLoginSaltValue());
			passwordBean.setVersion(ch.getPwdLoginVer());
			passwordBean.setUsername(userName);
			entResNo = operator.getEntResNo();
			operNo = operator.getOperNo();
			loginFailTimes = commonCacheService.getOperLoginFailTimesCache(operator.getEntResNo(), operator.getOperNo());
			userCustId = operator.getOperCustId();
		}
		else {
			// 获取企业用户信息
			Operator operator = null;
			if(StringUtils.isNotBlank(custId)){
				operator = commonCacheService.searchOperByCustId(custId);
			}else{
				custId = commonCacheService.findOperCustId(entResNo, userName);
				CustIdGenerator.isOperExist(custId, entResNo, userName);
				operator = commonCacheService.searchOperByCustId(custId);
			}
			operNo = operator.getOperNo();
			CustIdGenerator.isOperExist(operator, custId);
			userName = operator.getOperNo();
			entResNo = operator.getEntResNo();
			userInfo = (UserInfo)operator;
			loginFailTimes = commonCacheService.getOperLoginFailTimesCache(entResNo, userName);
			userCustId = operator.getOperCustId();
		}

		// 验证密码失败次数
		commonService.isReachMaxLoginFailTimes(loginFailTimes,SysConfig.getLoginPwdFailedTimes());
		if(passwordBean == null){
			passwordBean =  new PasswordBean();
			passwordBean.setOriginalPwd(userInfo.getPwdLogin());
			passwordBean.setSaltValue(userInfo.getPwdLoginSaltValue());
			passwordBean.setVersion(userInfo.getPwdLoginVer());
			passwordBean.setUsername(operNo);
		}
		passwordBean.setEnt((userType == UserTypeEnum.ENT)
				|| (userType == UserTypeEnum.OPERATOR));
		passwordBean.setPwd(pwd);
		passwordBean.setRandomToken(randomToken);
		
		// 验证用户名和密码是否正确
		boolean isMatch = passwordService.matchAesPassword(passwordBean);
		// 登录不成功
		if (!isMatch) {
			// 处理登录密码验证失败
			if (UserTypeEnum.NO_CARDER == userType) {
				processLockNoCarderAccount(operNo, loginFailTimes);
			} else if (UserTypeEnum.CARDER == userType) {
				processLockCarderAccount(operNo, loginFailTimes);
			} else if (UserTypeEnum.SYSTEM == userType) {
				processLockSysOperAccount(operNo, loginFailTimes);
			} else {
				processLockOperAccount(entResNo, operNo, loginFailTimes);
			}
		} else {
				// 登录密码匹配成功，更新缓存中的登录密码失败次数
				if (UserTypeEnum.NO_CARDER == userType) {
					commonCacheService.addNoCardLoginFailTimesCache(operNo, 0);
				} else if (UserTypeEnum.CARDER == userType) {
					commonCacheService.addCarderLoginFailTimesCache(operNo, 0);
				} else if (UserTypeEnum.SYSTEM == userType) {
					commonCacheService.addSysOperLoginFailTimesCache(operNo, 0);
				} else {
					commonCacheService.addOperLoginFailTimesCache(entResNo, operNo, 0);
				}
			// 更新密码
			upgradePwdAndVersion(passwordBean, userCustId,userType);
		}
		
		// 登录密码匹配成功
		return userInfo;
	}

	/**
	 * 处理锁定帐户方法
	 * 
	 * @param resNo
	 *            资源号或用户名
	 * @param custId
	 *            客户号
	 * @param key
	 *            缓存key
	 * @param userLoginAuth
	 *            登录token
	 * @throws HsException
	 */
	private void processLockCarderAccount(String resNo,
			int loginFailTimes) throws HsException {
		// 将登录失败存入缓存
		loginFailTimes++;
		int maxFailTimes = SysConfig.getLoginPwdFailedTimes();
		commonCacheService.addCarderLoginFailTimesCache(resNo, loginFailTimes);
		// 如果登录次数超过限制
		if (loginFailTimes >= maxFailTimes) {
			long second = commonService.computeUnlockSecond(SysConfig
					.getLoginFailUnlockTime());
			SystemLog.info(MOUDLENAME, "processLockAccount", "perResNo["
					+ resNo + "] 帐号已锁定" + second + "秒");
			commonCacheService.setCarderLoginFailTimesCacheKeyExpireInSecond(resNo, second);
		}
		commonService.loginFaiure(resNo, loginFailTimes, maxFailTimes,"持卡人登录失败");
	}

	/**
	 * 处理锁定帐户方法
	 * 
	 * @param resNo
	 *            资源号或用户名
	 * @param custId
	 *            客户号
	 * @param key
	 *            缓存key
	 * @param userLoginAuth
	 *            登录token
	 * @throws HsException
	 */
	private void processLockNoCarderAccount(String mobile,
			int loginFailTimes) throws HsException {
		// 将登录失败存入缓存
		loginFailTimes++;
		int maxFailTimes = SysConfig.getLoginPwdFailedTimes();
		commonCacheService.addNoCardLoginFailTimesCache(mobile, loginFailTimes);
		// 如果登录次数超过限制
		if (loginFailTimes >= maxFailTimes) {
			long second = commonService.computeUnlockSecond(SysConfig
					.getLoginFailUnlockTime());
			SystemLog.info(MOUDLENAME, "processLockAccount", "mobile["
					+ mobile+ "] 帐号已锁定" + second + "秒");
			commonCacheService.setNoCarderLoginFailTimesCacheKeyExpireInSecond(mobile, second);
		}
		commonService.loginFaiure(mobile, loginFailTimes, maxFailTimes,"非持卡人登录失败");
	}

	/**
	 * 处理锁定帐户方法
	 * 
	 * @param resNo
	 *            资源号或用户名
	 * @param custId
	 *            客户号
	 * @param key
	 *            缓存key
	 * @param userLoginAuth
	 *            登录token
	 * @throws HsException
	 */
	public void processLockOperAccount(String entResNo, String userName,
			int loginFailTimes) throws HsException {
		// 将登录失败存入缓存
		loginFailTimes++;
		int maxFailTimes = SysConfig.getLoginPwdFailedTimes();
		commonCacheService.addOperLoginFailTimesCache(entResNo, userName, loginFailTimes);
		// 如果登录次数超过限制
		if (loginFailTimes >= maxFailTimes) {
			long second = commonService.computeUnlockSecond(SysConfig
					.getLoginFailUnlockTime());
			SystemLog.info(MOUDLENAME, "processLockAccount", "企业互生号["
					+ entResNo + "],operNo["+ userName+"],帐号已锁定["+ second + "]秒");
			commonCacheService.setOperFailTimesCacheKeyExpireInSecond(entResNo,
					userName, second);
		}
		commonService.loginFaiure(userName, loginFailTimes, maxFailTimes, "操作员登录失败，企业互生号["
					+ entResNo + "] ");
	}

	/**
	 * 处理锁定帐户方法
	 * 
	 * @param resNo
	 *            资源号或用户名
	 * @param custId
	 *            客户号
	 * @param key
	 *            缓存key
	 * @param userLoginAuth
	 *            登录token
	 * @throws HsException
	 */
	private void processLockSysOperAccount(String userName,
			int loginFailTimes) throws HsException {
		// 将登录失败存入缓存
		loginFailTimes++;
		int maxFailTimes = SysConfig.getLoginPwdFailedTimes();
		commonCacheService.addSysOperLoginFailTimesCache(userName, loginFailTimes);
		// 如果登录次数超过限制
		if (loginFailTimes >= maxFailTimes) {
			long second = commonService.computeUnlockSecond(SysConfig
					.getLoginFailUnlockTime());
			SystemLog.info(MOUDLENAME, "processLockSysOperAccount", "系统用户 userName["
					+ userName + "] 帐号已锁定" + second + "秒");
			commonCacheService.setSysOperLoginFailTimesCacheKeyExpireInSecond(userName, second);
		}
		commonService.loginFaiure(userName, loginFailTimes, maxFailTimes, "系统用户登录失败");
	}

	/**
	 * 升级密码及版本
	 */
	private void upgradePwdAndVersion(PasswordBean passwordBean, String custId,
			UserTypeEnum userType) {
		// 判断密码版本
		if (StringUtils.isNotBlank(passwordBean.getVersion())
				&& passwordBean.getVersion().equals("3")) {
			// 不需更新密码及版本
			return;
		}
		// 如果需要升级，更新密码及版本号
		String saltValue = CSPRNG.generateRandom(SysConfig.getCsprLen());
		passwordBean.setSaltValue(saltValue);
		passwordBean.setVersion("3");
		String newLoginPwd = passwordService.getDesByAesPwd(passwordBean);
		// 重置登录密码
		if (UserTypeEnum.NO_CARDER == userType) {
			saveNoCardHolderLoginPwd(custId, newLoginPwd, saltValue);
		} else if (UserTypeEnum.CARDER == userType) {// || UserTypeEnum.OPERATOR_USE_CARDER == userType
			saveCardHolderLoginPwd(custId, newLoginPwd, saltValue);
		} else if (UserTypeEnum.SYSTEM != userType) { // operator
			saveOpeLoginPwd(custId, newLoginPwd, saltValue, null);
		}
	}

	@Override
	@Deprecated
	/**
	 * 已经废弃，改为checkApsReCheckerLoginPwd
	 * 地区平台双签复核员登陆密码校验
	 * @param regionalResNo
	 * @param userName
	 * @param loginPwd
	 * @param secretKey
	 * @param operCustId
	 * @return 复核员客户号
	 * @throws HsException 
	 */
	public String checkLoginPwdForCarderByReChecker(String apsResNo,
			String userName, String loginPwd, String secretKey,
			String operCustId) throws HsException {
		return this.checkApsReCheckerLoginPwd(apsResNo, userName, loginPwd,
				secretKey);
	}

	private void validReCheckParams(String resNo, String userName,
			String loginPwd, String secretKey) throws HsException {
		if (StringUtils.isBlank(resNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"地区平台互生号不能为空");
		}
		if (StringUtils.isBlank(userName)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"复核员账号不能为空");
		}
		if (StringUtils.isBlank(loginPwd)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"登录密码不能为空");
		}
		if (StringUtils.isBlank(secretKey)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"秘钥不能为空");
		}
	}

	/**
	 * 修改系统管理员的第2个密码
	 * 
	 * @param custId
	 *            客户号
	 * @param oldPwd
	 *            使用AES(密码,secretKey） 加密
	 * @param newPwd
	 *            使用AES(密码,secretKey） 加密
	 * @param secretKey
	 * @throws HsException
	 */
	@Override
	public void updateSysOperatorSecondPwd(String custId, String oldPwd,
			String newPwd, String secretKey) throws HsException {
		// 检查入参
		if (isBlank(custId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"客户号不能为空");
		}
		if (isBlank(oldPwd)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"旧登录密码不能为空");
		}
		if (isBlank(newPwd)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"新登录密码不能为空");
		}
		if (isBlank(secretKey)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"secretKey不能为空");
		}
		SysOperator oper = sysOperatorService.findSecondPwdByCustId(custId);
		if (oper == null) {
			throw new HsException(ErrorCodeEnum.SYSOPER_NOT_FOUND.getValue(),
					ErrorCodeEnum.SYSOPER_NOT_FOUND.getDesc());
		}

		// 验证登陆密码
		PasswordBean passwordBean = new PasswordBean();
		passwordBean.setEnt(true);
		passwordBean.setOriginalPwd(oper.getPwdLogin());
		passwordBean.setPwd(oldPwd);
		passwordBean.setRandomToken(secretKey);
		passwordBean.setSaltValue(oper.getPwdLoginSaltValue());
		passwordBean.setVersion(oper.getPwdLoginVer());
		// 验证用户名和密码是否正确
		boolean isMatch = passwordService.matchAesPassword(passwordBean);
		if (!isMatch) {
			throw new HsException(ErrorCodeEnum.PWD_LOGIN_ERROR.getValue(),
					"密码错误");
		}
		// 更新新的登陆密码
		String salt = CSPRNG.generateRandom(SysConfig.getCsprLen());
		PasswordBean pb = new PasswordBean();
		pb.setEnt(true);
		pb.setPwd(newPwd);
		pb.setRandomToken(secretKey);
		pb.setSaltValue(salt);
		pb.setVersion(oper.getPwdLoginVer());
		String pwd = passwordService.getDesByAesPwd(pb);
		// 重置登录密码
		SysOperator operator = new SysOperator();
		operator.setOperCustId(oper.getOperCustId());
		operator.setPwdLogin(pwd);
		operator.setPwdLoginSaltValue(salt);
		operator.setPwdLoginVer("3");
		sysOperatorService.updateSecondPwd(operator);

	}

	/**
	 * 修改系统管理员的第1个密码
	 * 
	 * @param custId
	 *            客户号
	 * @param oldPwd
	 *            使用AES(密码,secretKey） 加密
	 * @param newPwd
	 *            使用AES(密码,secretKey） 加密
	 * @param secretKey
	 * @throws HsException
	 */
	@Override
	public void updateSysOperatorLoginPwd(String custId, String oldPwd,
			String newPwd, String secretKey) throws HsException {
		// 检查入参
		if (isBlank(custId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"客户号不能为空");
		}
		if (isBlank(oldPwd)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"旧登录密码不能为空");
		}
		if (isBlank(newPwd)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"新登录密码不能为空");
		}
		if (isBlank(secretKey)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"secretKey不能为空");
		}

		SysOperator operator = (SysOperator) matchAesLoginPwdAndProcFailed(
				UserTypeEnum.SYSTEM, null, custId, null, oldPwd, secretKey);

		// 更新新的登陆密码
		String salt = CSPRNG.generateRandom(SysConfig.getCsprLen());
		PasswordBean pb = new PasswordBean();
		pb.setEnt(false);
		pb.setPwd(newPwd);
		pb.setRandomToken(secretKey);
		pb.setSaltValue(salt);
		pb.setVersion(operator.getPwdLoginVer());
		String pwd = passwordService.getDesByAesPwd(pb);
		// 重置登录密码
		AsSysOper newOper = new AsSysOper();
		newOper.setOperCustId(operator.getOperCustId());
		newOper.setPwdLogin(pwd);
		newOper.setPwdLoginSaltValue(salt);
		newOper.setPwdLoginVer("3");
		sysOperatorService.updateSysOper(newOper);

	}

	@Override
	public void addSysOperatorSecondPwd(String custId, String pwd,
			String secretKey) {
		// 验证客户号是否存在，且是否已设置密码
		SysOperator oper = sysOperatorService.findSecondPwdByCustId(custId);
		if (oper != null) {
			throw new HsException(
					ErrorCodeEnum.USER_SECOND_PWD_EXISTS.getValue(),
					ErrorCodeEnum.USER_SECOND_PWD_EXISTS.getDesc());
		}
		AsSysOper asOper = sysOperatorService.searchSysOperInfoByCustId(custId);
		if (asOper == null) {
			throw new HsException(ErrorCodeEnum.USER_NOT_FOUND.getValue(),
					ErrorCodeEnum.USER_NOT_FOUND.getDesc());
		}
		String salt = CSPRNG.generateRandom(SysConfig.getCsprLen());
		PasswordBean pb = new PasswordBean();
		pb.setEnt(true);
		pb.setPwd(pwd);
		pb.setRandomToken(secretKey);
		pb.setSaltValue(salt);
		pb.setVersion("3");
		String password = passwordService.getDesByAesPwd(pb);

		SysOperator operator = new SysOperator();
		operator.setOperCustId(custId);
		operator.setPwdLogin(password);
		operator.setPwdLoginVer("3");
		operator.setPwdLoginSaltValue(salt);
		operator.setUserName(asOper.getUserName());
		sysOperatorService.addSecondPwd(operator);
	}

	/**
	 * 验证系统管理员第2个密码
	 * 
	 * @param custId
	 * @param pwd
	 * @param secretKey
	 * @return
	 */
	@Override
	public boolean checkSysOperatorSecondPwd(String custId, String pwd,
			String secretKey) {
		SysOperator oper = sysOperatorService.findSecondPwdByCustId(custId);
		if (oper == null) {
			throw new HsException(ErrorCodeEnum.SYSOPER_NOT_FOUND.getValue(),
					ErrorCodeEnum.SYSOPER_NOT_FOUND.getDesc());
		}
		PasswordBean pb = new PasswordBean();
		pb.setEnt(true);
		pb.setPwd(pwd);
		pb.setOriginalPwd(oper.getPwdLogin());
		pb.setRandomToken(secretKey);
		pb.setSaltValue(oper.getPwdLoginSaltValue());
		pb.setVersion(oper.getPwdLoginVer());
		return passwordService.matchAesPassword(pb);
	}

	@Override
	public String checkApsReCheckerLoginPwd(String apsResNo, String userName,
			String loginPwd, String secretKey, String operCustId)
			throws HsException {
		// 检查入参
		validReCheckParams(apsResNo, userName, loginPwd, secretKey);
		// 验证复核员登录密码
		String reCheckedCustId = checkApsReCheckerLoginPwd(apsResNo, userName,
				loginPwd, secretKey);
		// 双签角色权限检查
		doubleCheckAps(operCustId, reCheckedCustId);
		return reCheckedCustId;
	}

	@Override
	public String checkMcsReCheckerLoginPwd(String mscResNo, String userName,
			String loginPwd, String secretKey, String operCustId)
			throws HsException {
		// 检查入参
		validReCheckParams(mscResNo, userName, loginPwd, secretKey);
		// 验证复核员登录密码
		String reCheckedCustId = checkMcsReCheckerLoginPwd(mscResNo, userName,
				loginPwd, secretKey);
		// 双签角色权限检查
		doubleCheckMcs(operCustId, reCheckedCustId);
		return reCheckedCustId;
	}

	@Override
	public void updateSysAdminSecondPwd(String custId, String oldPwd,
			String newLoginPwd, String secretKey) throws HsException {
		// 检查入参
		if (isBlank(custId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"客户号不能为空");
		}
		if (isBlank(oldPwd)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"旧登录密码不能为空");
		}
		if (isBlank(newLoginPwd)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"新登录密码不能为空");
		}
		if (isBlank(secretKey)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"secretKey不能为空");
		}

		SysOperator operator = (SysOperator) matchAesLoginPwdAndProcFailed(
				UserTypeEnum.CHECKER, null, custId, null, oldPwd, secretKey);

		// 更新新的登陆密码
		String salt = CSPRNG.generateRandom(SysConfig.getCsprLen());
		PasswordBean pb = new PasswordBean();
		pb.setEnt(false);
		pb.setPwd(newLoginPwd);
		pb.setRandomToken(secretKey);
		pb.setSaltValue(salt);
		pb.setVersion(operator.getPwdLoginVer());
		String pwd = passwordService.getDesByAesPwd(pb);
		// 重置登录密码
		AsSysOper newOper = new AsSysOper();
		newOper.setOperCustId(operator.getOperCustId());
		newOper.setPwdLogin(pwd);
		newOper.setPwdLoginSaltValue(salt);
		newOper.setPwdLoginVer("3");
		sysOperatorService.updateSysOper(newOper);
	}

	/**
	 * 检查企业交易密码
	 * 
	 * @param entCustId
	 * @param tradePwd
	 */
	public void checkEntTradePwd(String entCustId, String tradePwd)
			throws HsException {
		if (isBlank(entCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"企业客户号为空");
		}
		EntStatusInfo entStatusInfo = commonCacheService
				.searchEntStatusInfo(entCustId);
		CustIdGenerator.isEntStatusInfoExist(entStatusInfo, entCustId);
		if (StringUtils.isBlank(entStatusInfo.getPwdTrans())) {
			throw new HsException(ErrorCodeEnum.TRANS_PWD_NOT_SET.getValue(),
					ErrorCodeEnum.TRANS_PWD_NOT_SET.getDesc());
		}
		int times = commonCacheService
				.getEntTradeFailTimesCache(entStatusInfo.getEntResNo());

		if (times >= SysConfig.getTransPwdFailedTimes()) {
			throw new HsException(
					ErrorCodeEnum.TRADEPWD_USER_LOCKED.getValue(),
					ErrorCodeEnum.TRADEPWD_USER_LOCKED.getDesc());
		}
		String salt = StringUtils.nullToEmpty(entStatusInfo
				.getPwdTransSaltValue());
		tradePwd = StringEncrypt.sha256(tradePwd + salt);
		// 验证密码是否正确
		if (!entStatusInfo.getPwdTrans().equals(tradePwd)) {
			// 失败次数加1
			times += 1;
			commonCacheService.addEntTradeFailTimesCache(entStatusInfo.getEntResNo(), times);
			// 设置解锁时间
			if (times >= SysConfig.getTransPwdFailedTimes()) {
				long timeout = commonService.computeUnlockSecond(SysConfig
						.getTransPwdFailUnlockTime());
				commonCacheService.setEntTradePwdFailTimesKeyExpireInSecond(
						entStatusInfo.getEntResNo(), timeout);
			}
			throw new HsException(ErrorCodeEnum.PWD_TRADE_ERROR.getValue(),
					ErrorCodeEnum.PWD_TRADE_ERROR.getDesc());
		} else {
			// 更新缓存
			if (times > 0) {
				// 更新缓存
				commonCacheService.removeEntTradeFailTimesCache(entStatusInfo
						.getEntResNo());
			}
		}
	}

	@Override
	public void resetNoCarderTradepwd(String mobile,String random,String newTradePwd, String secretKey
			) throws HsException {
		validNoCarderParams(newTradePwd, secretKey, random);
		mobileService.checkSmsCode(mobile, random);
		String perCustId = commonCacheService.findCustIdByMobile(mobile);
		CustIdGenerator.isNoCarderExist(perCustId, mobile);
		saveTradePwd(perCustId, newTradePwd, UserTypeEnum.NO_CARDER.getType(), secretKey, perCustId);
	}
	
	private void validNoCarderParams(String newTradePwd, String secretKey,
			String random){
		if (isBlank(newTradePwd)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"手机号码不能为空");
		}
		if (isBlank(newTradePwd)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"新密码不能为空");
		}
		if (isBlank(secretKey)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"AES秘钥不能为空");
		}
		if (isBlank(random)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"随机数不能不为空");
		}
	}
	
}
