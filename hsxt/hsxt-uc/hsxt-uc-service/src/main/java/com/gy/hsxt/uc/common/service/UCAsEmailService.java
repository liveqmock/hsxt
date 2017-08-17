/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.common.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsi.nt.api.beans.EmailBean;
import com.gy.hsi.nt.api.beans.SelfEmailBean;
import com.gy.hsi.nt.api.service.INtService;
import com.gy.hsxt.bs.common.enumtype.msgtpl.MsgBizType;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.Base64Utils;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.Constants;
import com.gy.hsxt.uc.as.api.common.IUCAsEmailService;
import com.gy.hsxt.uc.as.api.consumer.IUCAsNoCardHolderService;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.uc.as.api.enumerate.TargetEnum;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.api.operator.IUCAsOperatorService;
import com.gy.hsxt.uc.cache.service.CommonCacheService;
import com.gy.hsxt.uc.common.SysConfig;
import com.gy.hsxt.uc.consumer.bean.CardHolder;
import com.gy.hsxt.uc.consumer.bean.NoCardHolder;
import com.gy.hsxt.uc.consumer.mapper.NoCardHolderMapper;
import com.gy.hsxt.uc.consumer.service.UCAsCardHolderService;
import com.gy.hsxt.uc.ent.bean.EntExtendInfo;
import com.gy.hsxt.uc.ent.bean.EntStatusInfo;
import com.gy.hsxt.uc.ent.mapper.EntExtendInfoMapper;
import com.gy.hsxt.uc.ent.mapper.EntStatusInfoMapper;
import com.gy.hsxt.uc.operator.bean.Operator;
import com.gy.hsxt.uc.operator.mapper.OperatorMapper;
import com.gy.hsxt.uc.util.CSPRNG;
import com.gy.hsxt.uc.util.CustIdGenerator;

/**
 * email发送验证处理类
 * 
 * @Package: com.gy.hsxt.uc.common.service
 * @ClassName: UCAsEmailService
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2015-11-25 下午3:30:05
 * @version V1.0
 */
@Service
public class UCAsEmailService implements IUCAsEmailService {
	private final static String MOUDLENAME = "com.gy.hsxt.uc.common.service.UCAsEmailService";
	/**
	 * 操作员
	 */
	@Autowired
	IUCAsOperatorService operatorService;
	/**
	 * 持卡人
	 */
	@Autowired
	UCAsCardHolderService cardHolderService;

	/**
	 * 非持卡人
	 */
	@Autowired
	IUCAsNoCardHolderService noCardHolderService;

	@Autowired
	EntStatusInfoMapper entStatusInfoMapper;
	/**
	 * 通知服务
	 */
	@Autowired
	INtService ntService;

	@Autowired
	IUCAsEntService asEntService;

	@Autowired
	OperatorMapper operatorMapper;

	@Autowired
	EntExtendInfoMapper entExtendInfoMapper;

	@Autowired
	NoCardHolderMapper noCardHolderMapper;

	@Autowired
	CommonCacheService commonCacheService;

	@Autowired
	CommonService commonService;
	private static final String NEWLINE = "\r\n";

	public void validElectricityParams(String url, String custId, String email,
			Integer custType) throws HsException {
		if (StringUtils.isBlank(url)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"输入参数url不能为空");
		}
		if (StringUtils.isBlank(custId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"输入参数客户号不能为空");
		}
		if (StringUtils.isBlank(email)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"输入参数邮箱不能为空");
		}
		if (StringUtils.isBlank(custType)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"输入参客户类型不能为空");
		}
	}

	/**
	 * 验证邮件
	 * 
	 * @param email
	 * @param validToken
	 * @throws HsException
	 */
	@Override
	public String checkMail(String email, String validToken) throws HsException {
		if (StringUtils.isBlank(email)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"输入参数邮箱信息为空");
		}
		if (StringUtils.isBlank(validToken)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数验证邮件token为空");
		}
		// 获取存在缓存中的邮件找回密码的code
		String code = commonCacheService.getBindEmailCodeCache(email);
		String isnoreCheckCode = StringUtils.nullToEmpty(SysConfig
				.getIgnoreCheckCode());
		if (!"1".equals(isnoreCheckCode)) {// 是否忽略邮件的验证（0：不忽略 1：忽略）
			if (StringUtils.isEmpty(code)) {
				throw new HsException(ErrorCodeEnum.MAIL_IS_EXPIRED.getValue(),
						ErrorCodeEnum.MAIL_IS_EXPIRED.getDesc());
			}
			if (!code.equals(validToken.trim())) {
				throw new HsException(ErrorCodeEnum.MAIL_NOT_MATCH.getValue(),
						ErrorCodeEnum.MAIL_NOT_MATCH.getDesc() + "email["
								+ email + "],+code[" + code + "],validToken["
								+ validToken + "]");
			}
		}
		commonCacheService.addBindEmailCodeCache(email, code);
		return code;
	}

	/**
	 * 绑定邮箱（校验验证邮件token，校验通过，入库邮件信息）
	 * 
	 * @param custId
	 *            客户号
	 * @param validToken
	 *            验证邮件token
	 * @param email
	 *            邮箱
	 * @param userType
	 *            用户类型 非持卡人1 持卡人2 操作员3 企业 4
	 * @throws HsException
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void bindEmail(String custId, String validToken, String email,
			String userType) throws HsException {
		commonService.checkIsAuthEmail(custId, userType, email);
		checkMail(email, validToken);
		commonCacheService.removeBindEmailCodeCache(email);
		if (StringUtils.isBlank(custId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数客户号为空");
		}
		if (StringUtils.isBlank(userType)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数用户类型为空");
		}
		updateEmail(custId.trim(), email.trim(), userType.trim(), 1);
	}

	/**
	 * 更新邮箱入库
	 * 
	 * @param custId
	 *            客户号
	 * @param email
	 *            邮箱
	 * @param userType
	 *            用户类型 用户类型 非持卡1 持卡人2 操作员3 企业 4
	 */

	private void updateEmail(String custId, String email, String userType,
			Integer isAuthEmail) throws HsException {

		if (UserTypeEnum.NO_CARDER.getType().equals(userType)) {// 更新非持卡人的邮箱信息
			this.updateNoCardHolderEmail(custId, email, isAuthEmail);
		} else if (UserTypeEnum.CARDER.getType().equals(userType)) {// 更新持卡人的邮箱信息
			this.updateCardHolderEmail(custId, email, isAuthEmail);
		} else if (UserTypeEnum.OPERATOR.getType().equals(userType)) {// 更新持卡人的邮箱信息
			this.updateOperatorEmail(custId, email);
		} else if (UserTypeEnum.ENT.getType().equals(userType)) {
			this.updateEntEmail(custId, email, isAuthEmail);
		} else {
			throw new HsException(ErrorCodeEnum.SESSION_IS_ILLEGAL.getValue(),
					ErrorCodeEnum.SESSION_IS_ILLEGAL.getDesc());
		}
	}

	/**
	 * 组装重置密码的url
	 * 
	 * @param email
	 *            邮箱地址
	 * @return
	 */
	private String getResetPwdUrl(String custId, String email, String random,
			String userType) {
		StringBuffer url = new StringBuffer();
		if (UserTypeEnum.CARDER.getType().equals(userType)) {
			url.append(SysConfig.getEmailCardHolderResetUrl());
		} else if (UserTypeEnum.NO_CARDER.getType().equals(userType)) {
			url.append(SysConfig.getEmailCardHolderResetUrl());
		} else if (UserTypeEnum.ENT.getType().equals(userType)) {
			String resNo = "";
			if (custId.length() > 11) {
				resNo = custId.substring(0, 11);
			}
			if (HsResNoUtils.isManageResNo(resNo)) {
				url.append(SysConfig.getManagerEmailCardHolderResetUrl());
			} else {
				url.append(SysConfig.getEmailOperatorResetUrl());
			}

		}
		JSONObject param = new JSONObject();
		param.put("random", random);
		param.put("email", email);
		param.put("custId", custId);
		param.put("userType", userType);
		StringBuffer msg = new StringBuffer();
		String resetPwdUrl = url.append(
				"param=" + Base64Utils.encode(param.toJSONString().getBytes()))
				.toString();
		msg.append("custId[" + custId + "],email[" + email + "],random["
				+ random + "],userType[" + userType + "],resetPwdUrl["
				+ resetPwdUrl + "]");
		SystemLog.debug(MOUDLENAME, "getResetPwdUrl", msg.toString());
		return resetPwdUrl.toString();
	}

	private void updateNoCardHolderEmail(String custId, String email,
			int isAuthEmail) throws HsException {
		NoCardHolder noCardHolder = new NoCardHolder();
		noCardHolder.setPerCustId(custId);
		noCardHolder.setEmail(email);
		noCardHolder.setIsAuthEmail(isAuthEmail);
		noCardHolder.setUpdateDate(StringUtils.getNowTimestamp());
		noCardHolder.setUpdatedby(custId);
		try {
			noCardHolderMapper.updateByPrimaryKeySelective(noCardHolder);
		} catch (HsException e) {
			throw new HsException(
					ErrorCodeEnum.NOCARDHOLDER_UPDATE_ERROR.getValue(),
					e.getMessage());
		}
		commonCacheService.removeNoCarderCache(custId);
	}

	private void updateCardHolderEmail(String custId, String email,
			int isAuthEmail) throws HsException {
		CardHolder cardHolder = new CardHolder();
		cardHolder.setPerCustId(custId);
		cardHolder.setEmail(email);
		cardHolder.setUpdateDate(StringUtils.getNowTimestamp());
		cardHolder.setIsAuthEmail(isAuthEmail);
		cardHolder.setUpdatedby(custId);
		commonCacheService.updateCardHolderInfo(cardHolder);
		commonCacheService.removeCarderCache(custId);
	}

	private void updateOperatorEmail(String custId, String email)
			throws HsException {
		Operator operator = new Operator();
		operator.setOperCustId(custId);
		operator.setEmail(email);
		operator.setUpdateDate(StringUtils.getNowTimestamp());
		operator.setUpdatedby(custId);
		try {
			operatorMapper.updateByPrimaryKeySelective(operator);
		} catch (HsException e) {
			throw new HsException(
					ErrorCodeEnum.OPERATOR_UPDATE_ERROR.getValue(),
					e.getMessage());
		}
		commonCacheService.removeOperCache(custId);
	}

	private void updateEntEmail(String custId, String email, Integer isAuthEmail)
			throws HsException {
		Timestamp now = StringUtils.getNowTimestamp();
		if (isAuthEmail != null) {
			EntStatusInfo entStatusInfo = new EntStatusInfo();
			entStatusInfo.setEntCustId(custId);
			entStatusInfo.setIsAuthEmail(isAuthEmail);
			entStatusInfo.setUpdateDate(now);
			entStatusInfo.setUpdatedby(custId);
			try {
				entStatusInfoMapper.updateByPrimaryKeySelective(entStatusInfo);
			} catch (HsException e) {
				throw new HsException(
						ErrorCodeEnum.ENTSTATUSINFO_UPDATE_ERROR.getValue(),
						e.getMessage());
			}
			commonCacheService.removeEntStatusInfoCache(custId);
		}
	}

	/**
	 * 非持卡人发送邮件（绑定邮箱）
	 * 
	 * @param mobile
	 * @param email
	 * @param custType
	 * @throws HsException
	 */
	public void sendNoCarderValidEmail(String mobile, String email,
			Integer custType) throws HsException {
		if (StringUtils.isBlank(email)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"输入参数邮箱信息为空");
		}
		if (StringUtils.isBlank(mobile)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"输入参数验证手机号码为空");
		}
		if (null == custType) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"输入参数客户类型为空");
		}
		StringBuffer msg = new StringBuffer();
		String methodName = "sendNoCarderValidEmail";
		String custId = commonCacheService.findCustIdByMobile(mobile);
		CustIdGenerator.isNoCarderExist(custId, mobile);
		NoCardHolder noCarder = new NoCardHolder();
		noCarder.setEmail(email);
		noCarder.setPerCustId(custId);
		noCarder.setIsAuthEmail(0);
		commonCacheService.updateNoCardHolderInfo(noCarder);
		
		msg.append("非持卡人发送邮件（绑定邮箱）异常,输入参数：").append("mobile[").append(mobile)
				.append("],email[").append(email).append("],custType[")
				.append(custType).append("]");
		commonService.checkIsAuthEmail(custId,
				UserTypeEnum.NO_CARDER.getType(), email);
		EmailBean emailBean = null;
		String perCustId = commonCacheService.findCustIdByMobile(mobile);
		CustIdGenerator.isNoCarderExist(perCustId, mobile);
		NoCardHolder noCardHolder = commonCacheService
				.searchNoCardHolderInfo(perCustId);
		CustIdGenerator.isNoCarderExist(noCardHolder, perCustId);
		noCardHolder.setEmail(email);
		String random = CSPRNG.generateRandom(SysConfig.getCheckCodeLen());
		try {
			emailBean = buildNoCarderValidEmailBean(noCardHolder, random,
					custType);
			msg.append(",调用通知系统参数：emailBean[")
					.append(JSONObject.toJSONString(emailBean)).append("]")
					.append(NEWLINE);
			ntService.sendEmail(emailBean);
			SystemLog.debug(MOUDLENAME, methodName,
					"emailBean[" + JSONObject.toJSONString(emailBean) + "]");
			commonCacheService.addBindEmailCodeCache(email, random);
		} catch (Exception e) {
			commonService.handleHsException(MOUDLENAME, methodName,
					msg.toString(), ErrorCodeEnum.EMAIL_SEND_FAILED, e);
		}
	}

	/**
	 * 持卡人发送邮件（绑定邮箱）
	 * 
	 * @param perResNo
	 * @param email
	 * @param custType
	 * @throws HsException
	 */
	public void sendCarderValidEmail(String perResNo, String email,
			Integer custType) throws HsException {
		if (StringUtils.isBlank(email)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"输入参数邮箱信息为空");
		}
		if (StringUtils.isBlank(perResNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"输入参数验证持卡人互生号为空");
		}
		if (StringUtils.isBlank(custType)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"输入参数客户类型为空");
		}
		String methodName = "sendCarderValidEmail";
		StringBuffer msg = new StringBuffer();
		String custId = commonCacheService.findCustIdByResNo(perResNo);
		CustIdGenerator.isCarderExist(custId, perResNo);
		CardHolder carder = new CardHolder();
		carder.setEmail(email);
		carder.setPerCustId(custId);
		carder.setIsAuthEmail(0);
		commonCacheService.updateCardHolderInfo(carder);
		commonService.checkIsAuthEmail(custId, UserTypeEnum.CARDER.getType(),
				email);
		msg.append("持卡人发送邮件（绑定邮箱）异常,输入参数：").append("perResNo[")
				.append(perResNo).append("],email[").append(email)
				.append("],custType[").append(custType).append("]");
		EmailBean emailBean = null;
		String perCustId = commonCacheService.findCustIdByResNo(perResNo);
		CustIdGenerator.isCarderExist(perCustId, perResNo);
		CardHolder cardHolder = commonCacheService
				.searchCardHolderInfo(perCustId);
		CustIdGenerator.isCarderExist(cardHolder, perCustId);
		cardHolder.setEmail(email);
		String random = CSPRNG.generateRandom(SysConfig.getCheckCodeLen());// 生成随机数
		try {
			emailBean = buildCarderValidEmailBean(cardHolder, random, custType);
			msg.append(",调用通知系统参数：emailBean[")
					.append(JSONObject.toJSONString(emailBean)).append("]")
					.append(NEWLINE);
			ntService.sendEmail(emailBean);
			SystemLog.debug(MOUDLENAME, methodName,
					"emailBean[" + JSONObject.toJSONString(emailBean) + "]");
			commonCacheService.addBindEmailCodeCache(email, random);
		} catch (Exception e) {
			commonService.handleHsException(MOUDLENAME, methodName,
					msg.toString(), ErrorCodeEnum.EMAIL_SEND_FAILED, e);
		}
	}

	/**
	 * 成员企业、托管企业发送邮件（绑定邮箱）
	 * 
	 * @param entResNo
	 * @param userName
	 * @param email
	 * @param custType
	 * @throws HsException
	 */
	public void sendCompanyValidEmail(String entResNo, String userName,
			String email, Integer custType) throws HsException {
		if (StringUtils.isBlank(email)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"输入参数邮箱信息为空");
		}
		if (StringUtils.isBlank(userName)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"输入参数操作员账号为为空");
		}
		if (StringUtils.isBlank(entResNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"输入参数验证企业互生号为空");
		}
		if (StringUtils.isBlank(custType)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"输入参数客户号类型为空");
		}

		String methodName = "sendCompanyValidEmail";
		StringBuffer msg = new StringBuffer();
		String entCustId = commonCacheService.findEntCustIdByEntResNo(entResNo);
		CustIdGenerator.isEntExtendExist(entCustId, entResNo);
		msg.append("成员企业、托管企业发送邮件（绑定邮箱）异常,输入参数：").append("entResNo[")
				.append(entResNo).append("],userName[").append(userName)
				.append("],email[").append(email).append("],custType[")
				.append(custType).append("]");
		commonService.checkIsAuthEmail(entCustId, UserTypeEnum.ENT.getType(),
				email);
		EmailBean emailBean = null;
		EntExtendInfo entExtendInfo = commonCacheService
				.searchEntExtendInfo(entCustId);
		CustIdGenerator.isEntExtendExist(entExtendInfo, entCustId);
		entExtendInfo.setContactEmail(email);
		String random = CSPRNG.generateRandom(SysConfig.getCheckCodeLen());// 生成随机数
		try {
			emailBean = buildCompanyValidEmailBean(entExtendInfo, random,
					custType);
			msg.append(",调用通知系统参数：emailBean[")
					.append(JSONObject.toJSONString(emailBean)).append("]")
					.append(NEWLINE);
			ntService.sendEmail(emailBean);
			SystemLog.debug(MOUDLENAME, methodName,
					"emailBean[" + JSONObject.toJSONString(emailBean) + "]");
			commonCacheService.addBindEmailCodeCache(email, random);
		} catch (Exception e) {
			commonService.handleHsException(MOUDLENAME, methodName,
					msg.toString(), ErrorCodeEnum.EMAIL_SEND_FAILED, e);
		}
	}

	/**
	 * 服务公司发送邮件（绑定邮箱）
	 * 
	 * @param entResNo
	 * @param userName
	 * @param email
	 * @param custType
	 * @throws HsException
	 */
	public void sendScsValidEmail(String entResNo, String userName,
			String email, Integer custType) throws HsException {
		if (StringUtils.isBlank(email)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"输入参数邮箱信息为空");
		}
		if (StringUtils.isBlank(userName)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"输入参数操作员账号为为空");
		}
		if (StringUtils.isBlank(entResNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"输入参数验证企业互生号为空");
		}
		if (StringUtils.isBlank(custType)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"输入参数客户号类型为空");
		}
		String methodName = "sendScsValidEmail";
		StringBuffer msg = new StringBuffer();
		String entCustId = commonCacheService.findEntCustIdByEntResNo(entResNo);
		CustIdGenerator.isEntExtendExist(entCustId, entResNo);
		msg.append("服务公司发送邮件（绑定邮箱）异常,输入参数：").append("entResNo[")
				.append(entResNo).append("],userName[").append(userName)
				.append("],email[").append(email).append("],custType[")
				.append(custType).append("]");
		commonService.checkIsAuthEmail(entCustId, UserTypeEnum.ENT.getType(),
				email);
		EmailBean emailBean = null;
		EntExtendInfo entExtendInfo = commonCacheService
				.searchEntExtendInfo(entCustId);
		CustIdGenerator.isEntExtendExist(entExtendInfo, entCustId);
		entExtendInfo.setContactEmail(email);
		String random = CSPRNG.generateRandom(SysConfig.getCheckCodeLen());// 生成随机数
		try {
			emailBean = buildScsValidEmailBean(entExtendInfo, random, custType);
			msg.append(",调用通知系统参数：emailBean[")
					.append(JSONObject.toJSONString(emailBean)).append("]")
					.append(NEWLINE);
			ntService.sendEmail(emailBean);
			SystemLog.debug(MOUDLENAME, methodName,
					"emailBean[" + JSONObject.toJSONString(emailBean) + "]");
			commonCacheService.addBindEmailCodeCache(email, random);
		} catch (Exception e) {
			commonService.handleHsException(MOUDLENAME, methodName,
					msg.toString(), ErrorCodeEnum.EMAIL_SEND_FAILED, e);
		}
	}

	/**
	 * 管理公司企业发送邮件（绑定邮箱）
	 * 
	 * @param entResNo
	 * @param userName
	 * @param email
	 * @param custType
	 * @throws HsException
	 */
	public void sendMcsValidEmail(String entResNo, String userName,
			String email, Integer custType) throws HsException {
		if (StringUtils.isBlank(email)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"输入参数邮箱信息为空");
		}
		if (StringUtils.isBlank(userName)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"输入参数操作员账号为为空");
		}
		if (StringUtils.isBlank(entResNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"输入参数验证企业互生号为空");
		}
		if (StringUtils.isBlank(custType)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"输入参数客户号类型为空");
		}
		String methodName = "sendMcsValidEmail";
		StringBuffer msg = new StringBuffer();
		String entCustId = commonCacheService.findEntCustIdByEntResNo(entResNo);
		CustIdGenerator.isEntExtendExist(entCustId, entResNo);
		msg.append("管理公司企业发送邮件（绑定邮箱）异常,输入参数：").append("entResNo[")
				.append(entResNo).append("],userName[").append(userName)
				.append("],email[").append(email).append("],custType[")
				.append(custType).append("]");
		commonService.checkIsAuthEmail(entCustId, UserTypeEnum.ENT.getType(),
				email);
		EmailBean emailBean = null;
		EntExtendInfo entExtendInfo = commonCacheService
				.searchEntExtendInfo(entCustId);
		CustIdGenerator.isEntExtendExist(entExtendInfo, entCustId);
		entExtendInfo.setContactEmail(email);
		String random = CSPRNG.generateRandom(SysConfig.getCheckCodeLen());// 生成随机数
		try {
			emailBean = buildMcsValidEmailBean(entExtendInfo, random, custType);
			msg.append(",调用通知系统参数：emailBean[")
					.append(JSONObject.toJSONString(emailBean)).append("]")
					.append(NEWLINE);
			ntService.sendEmail(emailBean);
			SystemLog.debug(MOUDLENAME, methodName,
					"emailBean[" + JSONObject.toJSONString(emailBean) + "]");
			commonCacheService.addBindEmailCodeCache(email, random);
		} catch (Exception e) {
			commonService.handleHsException(MOUDLENAME, methodName,
					msg.toString(), ErrorCodeEnum.EMAIL_SEND_FAILED, e);
		}
	}

	/**
	 * 地区平台发送邮件（绑定邮箱）
	 * 
	 * @param entResNo
	 * @param userName
	 * @param email
	 * @param custType
	 * @throws HsException
	 */
	public void sendApsValidEmail(String entResNo, String userName,
			String email, Integer custType) throws HsException {
		if (StringUtils.isBlank(email)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"输入参数邮箱信息为空");
		}
		if (StringUtils.isBlank(userName)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"输入参数操作员账号为为空");
		}
		if (StringUtils.isBlank(entResNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"输入参数验证企业互生号为空");
		}
		if (StringUtils.isBlank(custType)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"输入参数客户号类型为空");
		}
		String methodName = "sendApsValidEmail";
		StringBuffer msg = new StringBuffer();
		String entCustId = commonCacheService.findEntCustIdByEntResNo(entResNo);
		CustIdGenerator.isEntExtendExist(entCustId, entResNo);
		msg.append("地区平台发送邮件（绑定邮箱）异常,输入参数：").append("entResNo[")
				.append(entResNo).append("],userName[").append(userName)
				.append("],email[").append(email).append("],custType[")
				.append(custType).append("]");
		commonService.checkIsAuthEmail(entCustId, UserTypeEnum.ENT.getType(),
				email);
		EmailBean emailBean = null;
		EntExtendInfo entExtendInfo = commonCacheService
				.searchEntExtendInfo(entCustId);
		CustIdGenerator.isEntExtendExist(entExtendInfo, entCustId);
		entExtendInfo.setContactEmail(email);
		String random = CSPRNG.generateRandom(SysConfig.getCheckCodeLen());// 生成随机数
		try {
			emailBean = buildApsValidEmailBean(entExtendInfo, random, custType);
			msg.append(",调用通知系统参数：emailBean[")
					.append(JSONObject.toJSONString(emailBean)).append("]")
					.append(NEWLINE);
			ntService.sendEmail(emailBean);
			SystemLog.debug(MOUDLENAME, methodName,
					"emailBean[" + JSONObject.toJSONString(emailBean) + "]");
			commonCacheService.addBindEmailCodeCache(email, random);
		} catch (Exception e) {
			commonService.handleHsException(MOUDLENAME, methodName,
					msg.toString(), ErrorCodeEnum.EMAIL_SEND_FAILED, e);
		}
	}

	
	private void checkUserEmailInfo(String email,String correctEmail,Integer isAuthEmail,String msg)throws HsException{
		String methodName = "checkUserEmailInfo";
		if(StringUtils.isBlank(correctEmail)){
			commonService.handleHsException(MOUDLENAME, methodName,
					msg, ErrorCodeEnum.EMAIL_NOT_SET);
		}
		if (!email.equals(correctEmail)) {// 邮箱不匹配
			commonService.handleHsException(MOUDLENAME, methodName,
					msg, ErrorCodeEnum.EMAIL_NOT_MATCH);
		}
		if (1 != isAuthEmail) {// 邮箱未通过验证
			commonService.handleHsException(MOUDLENAME, methodName,
					msg, ErrorCodeEnum.EMAIL_NOT_CHECK);
		}
	}
	/**
	 * 非持卡人发送邮件（重置登录密码）
	 * 
	 * @param mobile
	 * @param email
	 * @param custType
	 * @throws HsException
	 */
	public void sendNoCarderRestPwdEmail(String perCustId, String email,
			Integer custType) throws HsException {
		checkParams(perCustId, email, custType);
		EmailBean emailBean = null;
		String methodName = "sendNoCarderRestPwdEmail";
		StringBuffer msg = new StringBuffer();
		NoCardHolder noCardHolder = commonCacheService
				.searchNoCardHolderInfo(perCustId);
		CustIdGenerator.isNoCarderExist(noCardHolder, perCustId);
		String correctEmail = StringUtils.nullToEmpty(noCardHolder.getEmail());
		msg.append("非持卡人发送邮件（重置登录密码）异常,输入参数：").append("perCustId[")
				.append(perCustId).append("],email[").append(email)
				.append("],custType[").append(custType)
				.append("],correctEmail[" + correctEmail + "]");
		int isAuthEmail = 0;
		if (null != noCardHolder.getIsAuthEmail()) {
			isAuthEmail = noCardHolder.getIsAuthEmail();
		}
		checkUserEmailInfo(email, correctEmail, isAuthEmail, msg.toString());
		String random = CSPRNG.generateRandom(SysConfig.getCheckCodeLen());
		try {
			emailBean = buildNoCarderRestPwdEmailBean(noCardHolder, random,
					custType);
			msg.append(",调用通知系统参数：emailBean[")
					.append(JSONObject.toJSONString(emailBean)).append("]")
					.append(NEWLINE);
			ntService.sendEmail(emailBean);
			SystemLog.debug(MOUDLENAME, methodName,
					"emailBean[" + JSONObject.toJSONString(emailBean) + "]");
			commonCacheService.addBindEmailCodeCache(email, random);
		} catch (Exception e) {
			commonService.handleHsException(MOUDLENAME, methodName,
					msg.toString(), ErrorCodeEnum.EMAIL_SEND_FAILED, e);
		}
	}

	/**
	 * 企业申报成功发送登录密码（邮箱方式）
	 * 
	 * @param mobile
	 * @param email
	 * @param custType
	 * @throws HsException
	 */
	public void sendOperInitPwdByEmail(EntExtendInfo extendInfo,
			String cleartextPwd, Operator adminOper) throws HsException {
		SelfEmailBean emailBean = null;
		String methodName = "sendOperInitPwdByEmail";
		StringBuffer msg = new StringBuffer();
		msg.append("企业申报成功发送登录密码（邮箱方式）异常,输入参数：extendInfo[")
				.append(JSON.toJSONString(extendInfo)).append("],adminOper[")
				.append(JSON.toJSONString(adminOper)).append("],cleartextPwd[")
				.append(cleartextPwd).append("]");
		try {
			emailBean = buildEntLogPwd(extendInfo, cleartextPwd, adminOper);
			msg.append(",调用通知系统参数：emailBean[")
					.append(JSONObject.toJSONString(emailBean)).append("]")
					.append(NEWLINE);
			ntService.sendSelfEmail(emailBean);
			SystemLog.debug(MOUDLENAME, methodName,
					"emailBean[" + JSONObject.toJSONString(emailBean) + "]");
		} catch (Exception e) {
			commonService.handleHsException(MOUDLENAME, methodName,
					msg.toString(), ErrorCodeEnum.EMAIL_SEND_FAILED, e);
		}
	}

	// private void throwEmailNotMatchException
	/**
	 * 持卡人发送邮件（重置登录密码）
	 * 
	 * @param perResNo
	 * @param email
	 * @param custType
	 * @throws HsException
	 */
	public void sendCarderRestPwdEmail(String perCustId, String email,
			Integer custType) throws HsException {
		checkParams(perCustId, email, custType);
		EmailBean emailBean = null;
		String methodName = "sendCarderRestPwdEmail";
		StringBuffer msg = new StringBuffer();
		CardHolder cardHolder = commonCacheService
				.searchCardHolderInfo(perCustId);
		CustIdGenerator.isCarderExist(cardHolder, perCustId);
		String correctEmail = StringUtils.nullToEmpty(cardHolder.getEmail());
		int isAuthEmail = 0;
		if (null != cardHolder.getIsAuthEmail()) {
			isAuthEmail = cardHolder.getIsAuthEmail();
		}
		msg.append("持卡人发送邮件（重置登录密码）异常,输入参数：")
				.append("perCustId[")
				.append(perCustId)
				.append("],email[")
				.append(email)
				.append("],custType[")
				.append(custType)
				.append("],correctEmail[" + correctEmail + "],isAuthEmail["
						+ isAuthEmail + "]");
		checkUserEmailInfo(email, correctEmail, isAuthEmail, msg.toString());
		String random = CSPRNG.generateRandom(SysConfig.getCheckCodeLen());// 生成随机数
		try {
			emailBean = buildCarderRestPwdEmailBean(cardHolder, random,
					custType);
			msg.append(",调用通知系统参数：emailBean[")
					.append(JSONObject.toJSONString(emailBean)).append("]")
					.append(NEWLINE);
			ntService.sendEmail(emailBean);
			commonCacheService.addBindEmailCodeCache(email, random);
		} catch (Exception e) {
			commonService.handleHsException(MOUDLENAME, methodName,
					msg.toString(), ErrorCodeEnum.EMAIL_SEND_FAILED, e);
		}
	}

	/**
	 * 成员企业、托管企业发送邮件（重置登录密码）
	 * 
	 * @param entResNo
	 * @param userName
	 * @param email
	 * @param custType
	 * @throws HsException
	 */
	public void sendCompanyRestPwdEmail(String operCustId, String email,
			Integer custType) throws HsException {
		checkParams(operCustId, email, custType);
		String methodName = "sendCompanyRestPwdEmail";
		StringBuffer msg = new StringBuffer();
		EmailBean emailBean = null;
		Operator operator = commonCacheService.searchOperByCustId(operCustId);
		CustIdGenerator.isOperExist(operator, operCustId);
		String entCustId = operator.getEntCustId();
		EntExtendInfo entExtendInfo = commonCacheService
				.searchEntExtendInfo(entCustId);
		CustIdGenerator.isEntExtendExist(entExtendInfo, entCustId);
		String correctEmail = StringUtils.nullToEmpty(entExtendInfo
				.getContactEmail());
		EntStatusInfo entStatusInfo = commonCacheService
				.searchEntStatusInfo(entCustId);
		CustIdGenerator.isEntStatusInfoExist(entStatusInfo, entCustId);
		int isAuthEmail = 0;
		if (null != entStatusInfo.getIsAuthEmail()) {
			isAuthEmail = entStatusInfo.getIsAuthEmail();
		}
		msg.append("成员企业、托管企业发送邮件（重置登录密码）异常,输入参数：")
				.append("entCustId[")
				.append(entCustId)
				.append("],email[")
				.append(email)
				.append("],custType[")
				.append(custType)
				.append("],correctEmail[" + correctEmail + "],isAuthEmail["
						+ isAuthEmail + "]");
		checkUserEmailInfo(email, correctEmail, isAuthEmail, msg.toString());
		String random = CSPRNG.generateRandom(SysConfig.getCheckCodeLen());// 生成随机数
		try {
			emailBean = buildCompanyRestPwdEmailBean(entExtendInfo, random,
					custType, operCustId);
			msg.append(",调用通知系统参数：emailBean[")
					.append(JSONObject.toJSONString(emailBean)).append("]")
					.append(NEWLINE);
			ntService.sendEmail(emailBean);
			SystemLog.debug(MOUDLENAME, methodName,
					"emailBean[" + JSONObject.toJSONString(emailBean) + "]");
			commonCacheService.addBindEmailCodeCache(email, random);
		} catch (Exception e) {
			commonService.handleHsException(MOUDLENAME, methodName,
					msg.toString(), ErrorCodeEnum.EMAIL_SEND_FAILED, e);
		}
	}

	/**
	 * 服务公司发送邮件（重置登录密码）
	 * 
	 * @param entResNo
	 * @param userName
	 * @param email
	 * @param custType
	 * @throws HsException
	 */
	public void sendScsRestPwdEmail(String operCustId, String email,
			Integer custType) throws HsException {
		checkParams(operCustId, email, custType);
		String methodName = "sendScsRestPwdEmail";
		StringBuffer msg = new StringBuffer();
		EmailBean emailBean = null;
		Operator operator = commonCacheService.searchOperByCustId(operCustId);
		CustIdGenerator.isOperExist(operator, operCustId);
		String entCustId = operator.getEntCustId();
		EntExtendInfo entExtendInfo = commonCacheService
				.searchEntExtendInfo(entCustId);
		CustIdGenerator.isEntExtendExist(entExtendInfo, entCustId);
		String correctEmail = StringUtils.nullToEmpty(entExtendInfo
				.getContactEmail());
		EntStatusInfo entStatusInfo = commonCacheService
				.searchEntStatusInfo(entCustId);
		CustIdGenerator.isEntStatusInfoExist(entStatusInfo, entCustId);
		int isAuthEmail = 0;
		if (null != entStatusInfo.getIsAuthEmail()) {
			isAuthEmail = entStatusInfo.getIsAuthEmail();
		}
		msg.append("服务公司发送邮件（重置登录密码）异常,输入参数：")
				.append("entCustId[")
				.append(entCustId)
				.append("],email[")
				.append(email)
				.append("],custType[")
				.append(custType)
				.append("],correctEmail[" + correctEmail + "],isAuthEmail["
						+ isAuthEmail + "]");
		checkUserEmailInfo(email, correctEmail, isAuthEmail, msg.toString());
		String random = CSPRNG.generateRandom(SysConfig.getCheckCodeLen());// 生成随机数
		try {
			emailBean = buildScsRestPwdEmailBean(entExtendInfo, random,
					custType, operCustId);
			msg.append(",调用通知系统参数：emailBean[")
					.append(JSONObject.toJSONString(emailBean)).append("]")
					.append(NEWLINE);
			ntService.sendEmail(emailBean);
			SystemLog.debug(MOUDLENAME, methodName,
					"emailBean[" + JSONObject.toJSONString(emailBean) + "]");
			commonCacheService.addBindEmailCodeCache(email, random);
		} catch (Exception e) {
			commonService.handleHsException(MOUDLENAME, methodName,
					msg.toString(), ErrorCodeEnum.EMAIL_SEND_FAILED, e);
		}
	}

	/**
	 * 管理公司企业发送邮件（重置登录密码）
	 * 
	 * @param entResNo
	 * @param userName
	 * @param email
	 * @param custType
	 * @throws HsException
	 */
	public void sendMcsRestPwdEmail(String operCustId, String email,
			Integer custType) throws HsException {
		checkParams(operCustId, email, custType);
		EmailBean emailBean = null;
		String methodName = "sendMcsRestPwdEmail";
		StringBuffer msg = new StringBuffer();
		Operator operator = commonCacheService.searchOperByCustId(operCustId);
		CustIdGenerator.isOperExist(operator, operCustId);
		String entCustId = operator.getEntCustId();
		EntExtendInfo entExtendInfo = commonCacheService
				.searchEntExtendInfo(entCustId);
		CustIdGenerator.isEntExtendExist(entExtendInfo, entCustId);
		String correctEmail = StringUtils.nullToEmpty(entExtendInfo
				.getContactEmail());
		EntStatusInfo entStatusInfo = commonCacheService
				.searchEntStatusInfo(entCustId);
		CustIdGenerator.isEntStatusInfoExist(entStatusInfo, entCustId);
		int isAuthEmail = 0;
		if (null != entStatusInfo.getIsAuthEmail()) {
			isAuthEmail = entStatusInfo.getIsAuthEmail();
		}
		msg.append("管理公司企业发送邮件（重置登录密码）异常,输入参数：")
				.append("entCustId[")
				.append(entCustId)
				.append("],email[")
				.append(email)
				.append("],custType[")
				.append(custType)
				.append("],correctEmail[" + correctEmail + "],isAuthEmail["
						+ isAuthEmail + "]");
		checkUserEmailInfo(email, correctEmail, isAuthEmail, msg.toString());
		String random = CSPRNG.generateRandom(SysConfig.getCheckCodeLen());// 生成随机数
		try {
			emailBean = buildMcsRestPwdEmailBean(entExtendInfo, random,
					custType, operCustId);
			msg.append(",调用通知系统参数：emailBean[")
					.append(JSONObject.toJSONString(emailBean)).append("]")
					.append(NEWLINE);
			ntService.sendEmail(emailBean);
			SystemLog.debug(MOUDLENAME, methodName,
					"emailBean[" + JSONObject.toJSONString(emailBean) + "]");
			commonCacheService.addBindEmailCodeCache(email, random);
		} catch (Exception e) {
			commonService.handleHsException(MOUDLENAME, methodName,
					msg.toString(), ErrorCodeEnum.EMAIL_SEND_FAILED, e);
		}
	}

	private void checkParams(String entCustId, String email, Integer custType)
			throws HsException {
		if (StringUtils.isBlank(email)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"输入参数邮箱信息为空");
		}
		if (StringUtils.isBlank(entCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"输入参数客户号为为空");
		}
		if (StringUtils.isBlank(custType)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"输入参数客户号类型为空");
		}
	}

	/**
	 * 地区平台发送邮件（重置登录密码）
	 * 
	 * @param entResNo
	 * @param userName
	 * @param email
	 * @param custType
	 * @throws HsException
	 */
	public void sendApsRestPwdEmail(String operCustId, String email,
			Integer custType) throws HsException {
		checkParams(operCustId, email, custType);
		EmailBean emailBean = null;
		String methodName = "sendApsRestPwdEmail";
		StringBuffer msg = new StringBuffer();
		Operator operator = commonCacheService.searchOperByCustId(operCustId);
		CustIdGenerator.isOperExist(operator, operCustId);
		String entCustId = operator.getEntCustId();
		EntExtendInfo entExtendInfo = commonCacheService
				.searchEntExtendInfo(entCustId);
		CustIdGenerator.isEntExtendExist(entExtendInfo, entCustId);
		String correctEmail = StringUtils.nullToEmpty(entExtendInfo
				.getContactEmail());
		EntStatusInfo entStatusInfo = commonCacheService
				.searchEntStatusInfo(entCustId);
		CustIdGenerator.isEntStatusInfoExist(entStatusInfo, entCustId);
		int isAuthEmail = 0;
		if (null != entStatusInfo.getIsAuthEmail()) {
			isAuthEmail = entStatusInfo.getIsAuthEmail();
		}
		msg.append("地区平台发送邮件（重置登录密码）异常,输入参数：")
				.append("entCustId[")
				.append(entCustId)
				.append("],email[")
				.append(email)
				.append("],custType[")
				.append(custType)
				.append("],correctEmail[" + correctEmail + "],isAuthEmail["
						+ isAuthEmail + "]");
		checkUserEmailInfo(email, correctEmail, isAuthEmail, msg.toString());
		String random = CSPRNG.generateRandom(SysConfig.getCheckCodeLen());// 生成随机数
		try {
			emailBean = buildApsRestPwdEmailBean(entExtendInfo, random,
					custType, operCustId);
			msg.append(",调用通知系统参数：emailBean[")
					.append(JSONObject.toJSONString(emailBean)).append("]")
					.append(NEWLINE);
			ntService.sendEmail(emailBean);
			SystemLog.debug(MOUDLENAME, methodName,
					"emailBean[" + JSONObject.toJSONString(emailBean) + "]");
			commonCacheService.addBindEmailCodeCache(email, random);
		} catch (Exception e) {
			commonService.handleHsException(MOUDLENAME, methodName,
					msg.toString(), ErrorCodeEnum.EMAIL_SEND_FAILED, e);
		}
	}

	private Map<String, String> constructNoCarderValidMailContent(
			NoCardHolder noCardHolder, String url) {
		Map<String, String> mailContent = new HashMap<String, String>();
		// {0}--企业名称或消费者姓名
		mailContent.put("{0}", noCardHolder.getMobile());
		// {1}--时间(yyyy-MM-dd HH:mm:ss)
		mailContent.put("{1}", DateUtil.DateTimeToString(new Date()));
		// {2}--验证地址
		mailContent.put("{2}", url);
		// {3}--可以复制的验证地址
		mailContent.put("{3}", url);
		// {4}--失效时间
		mailContent.put("{4}", String.valueOf(SysConfig.getEmailValidTime()));
		// {5}--邮件后面签名
		String emailSign = SysConfig.getConsumerEmailSign();
		emailSign = StringUtils.isBlank(emailSign) ? Constants.SENDER_CONSUMER
				: commonService.convertUtf8(emailSign);
		mailContent.put("{5}", emailSign);
		// {6}--时间(yyyy-MM-dd)
		mailContent
				.put("{6}", DateUtil.DateToString(DateUtil.getCurrentDate()));

		return mailContent;
	}

	private Map<String, String> constructCarderValidMailContent(
			CardHolder cardHolder, String url) {
		Map<String, String> mailContent = new HashMap<String, String>();
		// {0}--企业名称或消费者姓名
		mailContent.put("{0}", cardHolder.getPerResNo());
		// {1}--时间(yyyy-MM-dd HH:mm:ss)
		mailContent.put("{1}", DateUtil.DateTimeToString(new Date()));
		// {2}--验证地址
		mailContent.put("{2}", url);
		// {3}--可以复制的验证地址
		mailContent.put("{3}", url);
		// {4}--失效时间
		mailContent.put("{4}", String.valueOf(SysConfig.getEmailValidTime()));
		// {5}--邮件后面签名
		String emailSign = SysConfig.getConsumerEmailSign();
		emailSign = StringUtils.isBlank(emailSign) ? Constants.SENDER_CONSUMER
				: commonService.convertUtf8(emailSign);
		mailContent.put("{5}", emailSign);
		// {6}--时间(yyyy-MM-dd)
		mailContent
				.put("{6}", DateUtil.DateToString(DateUtil.getCurrentDate()));
		return mailContent;
	}

	private Map<String, String> constructApsValidMailContent(
			EntExtendInfo entExtendInfo, String url) {
		Map<String, String> mailContent = new HashMap<String, String>();
		// {0}--企业名称或消费者姓名
		mailContent.put("{0}", entExtendInfo.getEntCustName());
		// {1}--时间(yyyy-MM-dd HH:mm:ss)
		mailContent.put("{1}", DateUtil.DateTimeToString(new Date()));
		// {2}--验证地址
		mailContent.put("{2}", url);
		// {3}--可以复制的验证地址
		mailContent.put("{3}", url);
		// {4}--失效时间
		mailContent.put("{4}", String.valueOf(SysConfig.getEmailValidTime()));
		// {5}--邮件后面签名
		String emailSignEnt = SysConfig.getEntEmailSign();
		emailSignEnt = StringUtils.isBlank(emailSignEnt) ? Constants.SENDER_ENT
				: commonService.convertUtf8(emailSignEnt);
		mailContent.put("{5}", emailSignEnt);
		// {6}--时间(yyyy-MM-dd)
		mailContent
				.put("{6}", DateUtil.DateToString(DateUtil.getCurrentDate()));
		return mailContent;
	}

	private Map<String, String> constructMcsValidMailContent(
			EntExtendInfo entExtendInfo, String url) {
		Map<String, String> mailContent = new HashMap<String, String>();
		// {0}--企业名称或消费者姓名
		mailContent.put("{0}", entExtendInfo.getEntCustName());
		// {1}--时间(yyyy-MM-dd HH:mm:ss)
		mailContent.put("{1}", DateUtil.DateTimeToString(new Date()));
		// {2}--验证地址
		mailContent.put("{2}", url);
		// {3}--可以复制的验证地址
		mailContent.put("{3}", url);
		// {4}--失效时间
		mailContent.put("{4}", String.valueOf(SysConfig.getEmailValidTime()));
		// {5}--邮件后面签名
		String emailSignEnt = SysConfig.getEntEmailSign();
		emailSignEnt = StringUtils.isBlank(emailSignEnt) ? Constants.SENDER_ENT
				: commonService.convertUtf8(emailSignEnt);
		mailContent.put("{5}", emailSignEnt);
		// {6}--时间(yyyy-MM-dd)
		mailContent
				.put("{6}", DateUtil.DateToString(DateUtil.getCurrentDate()));
		return mailContent;
	}

	private Map<String, String> constructScsValidMailContent(
			EntExtendInfo entExtendInfo, String url) {
		Map<String, String> mailContent = new HashMap<String, String>();
		// {0}--企业名称或消费者姓名
		mailContent.put("{0}", entExtendInfo.getEntCustName());
		// {1}--时间(yyyy-MM-dd HH:mm:ss)
		mailContent.put("{1}", DateUtil.DateTimeToString(new Date()));
		// {2}--验证地址
		mailContent.put("{2}", url);
		// {3}--可以复制的验证地址
		mailContent.put("{3}", url);
		// {4}--失效时间
		mailContent.put("{4}", String.valueOf(SysConfig.getEmailValidTime()));
		// {5}--邮件后面签名
		String emailSignEnt = SysConfig.getEntEmailSign();
		emailSignEnt = StringUtils.isBlank(emailSignEnt) ? Constants.SENDER_ENT
				: commonService.convertUtf8(emailSignEnt);
		mailContent.put("{5}", emailSignEnt);
		// {6}--时间(yyyy-MM-dd)
		mailContent
				.put("{6}", DateUtil.DateToString(DateUtil.getCurrentDate()));
		return mailContent;
	}

	private Map<String, String> constructCompanyValidMailContent(
			EntExtendInfo entExtendInfo, String url) {
		Map<String, String> mailContent = new HashMap<String, String>();
		// {0}--企业名称或消费者姓名
		mailContent.put("{0}", entExtendInfo.getEntCustName());
		// {1}--时间(yyyy-MM-dd HH:mm:ss)
		mailContent.put("{1}", DateUtil.DateTimeToString(new Date()));
		// {2}--验证地址
		mailContent.put("{2}", url);
		// {3}--可以复制的验证地址
		mailContent.put("{3}", url);
		// {4}--失效时间
		mailContent.put("{4}", String.valueOf(SysConfig.getEmailValidTime()));
		// {5}--邮件后面签名
		String emailSignEnt = SysConfig.getEntEmailSign();
		emailSignEnt = StringUtils.isBlank(emailSignEnt) ? Constants.SENDER_ENT
				: commonService.convertUtf8(emailSignEnt);
		mailContent.put("{5}", emailSignEnt);
		// {6}--时间(yyyy-MM-dd)
		// 验证地址后面跟的参数需要加密(AES加密)
		mailContent
				.put("{6}", DateUtil.DateToString(DateUtil.getCurrentDate()));
		return mailContent;
	}

	private Map<String, String> constructNoCarderRestPwdMailContent(
			NoCardHolder noCardHolder, String url) {
		Map<String, String> mailContent = new HashMap<String, String>();
		// {0}--企业名称或消费者姓名
		mailContent.put("{0}", noCardHolder.getMobile());
		// {1}--时间(yyyy-MM-dd HH:mm:ss)
		mailContent.put("{1}", DateUtil.DateTimeToString(new Date()));
		// {2}--验证地址
		mailContent.put("{2}", url);
		// {3}--可以复制的验证地址
		mailContent.put("{3}", url);
		// {4}--失效时间
		mailContent.put("{4}", String.valueOf(SysConfig.getEmailValidTime()));
		// {5}--邮件后面签名
		String emailSign = SysConfig.getConsumerEmailSign();
		emailSign = StringUtils.isBlank(emailSign) ? Constants.SENDER_CONSUMER
				: commonService.convertUtf8(emailSign);
		mailContent.put("{5}", emailSign);
		// {6}--时间(yyyy-MM-dd)
		mailContent
				.put("{6}", DateUtil.DateToString(DateUtil.getCurrentDate()));
		return mailContent;
	}

	private String constructEntApplyMailContent(EntExtendInfo entExtendInfo,
			String cleartextPwd, Operator adminOper) {
		// <P>{0}系统管理员：</P>
		// <p>您登录{1}的登录密码为：{2}</p>
		// <p>请及时登录系统进行密码修改。</p>
		// <p>此邮件为自动发送，请勿回复！</p>
		StringBuffer sb = new StringBuffer();
		sb.append("<P>").append(entExtendInfo.getEntCustName())
				.append("系统管理员：</P>").append(NEWLINE).append("<p>您登录")
				.append(entExtendInfo.getEntCustName()).append("的登录密码为：")
				.append(cleartextPwd).append("</p>").append(NEWLINE)
				.append("<p>请及时登录系统进行密码修改。</p>").append(NEWLINE)
				.append("<p>此邮件为自动发送，请勿回复！</p>").append(NEWLINE)
				.append("<div style=\"width:100%;text-align:right\">").append("【")
				.append(Constants.SENDER_ENT).append("】").append("</div>");
		return sb.toString();
	}

	private Map<String, String> constructCarderRestPwdMailContent(
			CardHolder cardHolder, String url) {
		Map<String, String> mailContent = new HashMap<String, String>();
		// {0}--企业名称或消费者姓名
		mailContent.put("{0}", cardHolder.getPerResNo());
		// {1}--时间(yyyy-MM-dd HH:mm:ss)
		mailContent.put("{1}", DateUtil.DateTimeToString(new Date()));
		// {2}--验证地址
		mailContent.put("{2}", url);
		// {3}--可以复制的验证地址
		mailContent.put("{3}", url);
		// {4}--失效时间
		mailContent.put("{4}", String.valueOf(SysConfig.getEmailValidTime()));
		// {5}--邮件后面签名
		String emailSign = SysConfig.getConsumerEmailSign();
		emailSign = StringUtils.isBlank(emailSign) ? Constants.SENDER_CONSUMER
				: commonService.convertUtf8(emailSign);
		mailContent.put("{5}", emailSign);
		// {6}--时间(yyyy-MM-dd)
		mailContent
				.put("{6}", DateUtil.DateToString(DateUtil.getCurrentDate()));
		return mailContent;
	}

	private Map<String, String> constructApsRestPwdMailContent(
			EntExtendInfo entExtendInfo, String url) {
		Map<String, String> mailContent = new HashMap<String, String>();
		// {0}--企业名称或消费者姓名
		mailContent.put("{0}", entExtendInfo.getEntCustName());
		// {1}--时间(yyyy-MM-dd HH:mm:ss)
		mailContent.put("{1}", DateUtil.DateTimeToString(new Date()));
		// {2}--验证地址
		mailContent.put("{2}", url);
		// {3}--可以复制的验证地址
		mailContent.put("{3}", url);
		// {4}--失效时间
		mailContent.put("{4}", String.valueOf(SysConfig.getEmailValidTime()));
		// {5}--邮件后面签名
		String emailSignEnt = SysConfig.getEntEmailSign();
		emailSignEnt = StringUtils.isBlank(emailSignEnt) ? Constants.SENDER_ENT
				: commonService.convertUtf8(emailSignEnt);
		mailContent.put("{5}", emailSignEnt);
		// {6}--时间(yyyy-MM-dd)
		mailContent
				.put("{6}", DateUtil.DateToString(DateUtil.getCurrentDate()));
		return mailContent;
	}

	private Map<String, String> constructMcsRestPwdMailContent(
			EntExtendInfo entExtendInfo, String url) {
		Map<String, String> mailContent = new HashMap<String, String>();
		// {0}--企业名称或消费者姓名
		mailContent.put("{0}", entExtendInfo.getEntCustName());
		// {1}--时间(yyyy-MM-dd HH:mm:ss)
		mailContent.put("{1}", DateUtil.DateTimeToString(new Date()));
		// {2}--验证地址
		mailContent.put("{2}", url);
		// {3}--可以复制的验证地址
		mailContent.put("{3}", url);
		// {4}--失效时间
		mailContent.put("{4}", String.valueOf(SysConfig.getEmailValidTime()));
		// {5}--邮件后面签名
		String emailSignEnt = SysConfig.getEntEmailSign();
		emailSignEnt = StringUtils.isBlank(emailSignEnt) ? Constants.SENDER_ENT
				: commonService.convertUtf8(emailSignEnt);
		mailContent.put("{5}", emailSignEnt);
		// {6}--时间(yyyy-MM-dd)
		mailContent
				.put("{6}", DateUtil.DateToString(DateUtil.getCurrentDate()));
		return mailContent;
	}

	private Map<String, String> constructScsRestPwdMailContent(
			EntExtendInfo entExtendInfo, String url) {
		Map<String, String> mailContent = new HashMap<String, String>();
		// {0}--企业名称或消费者姓名
		mailContent.put("{0}", entExtendInfo.getEntCustName());
		// {1}--时间(yyyy-MM-dd HH:mm:ss)
		mailContent.put("{1}", DateUtil.DateTimeToString(new Date()));
		// {2}--验证地址
		mailContent.put("{2}", url);
		// {3}--可以复制的验证地址
		mailContent.put("{3}", url);
		// {4}--失效时间
		mailContent.put("{4}", String.valueOf(SysConfig.getEmailValidTime()));
		// {5}--邮件后面签名
		String emailSignEnt = SysConfig.getEntEmailSign();
		emailSignEnt = StringUtils.isBlank(emailSignEnt) ? Constants.SENDER_ENT
				: commonService.convertUtf8(emailSignEnt);
		mailContent.put("{5}", emailSignEnt);
		// {6}--时间(yyyy-MM-dd)
		mailContent
				.put("{6}", DateUtil.DateToString(DateUtil.getCurrentDate()));
		return mailContent;
	}

	private Map<String, String> constructCompanyRestPwdMailContent(
			EntExtendInfo entExtendInfo, String url) {
		Map<String, String> mailContent = new HashMap<String, String>();
		// {0}--企业名称或消费者姓名
		mailContent.put("{0}", entExtendInfo.getEntCustName());
		// {1}--时间(yyyy-MM-dd HH:mm:ss)
		mailContent.put("{1}", DateUtil.DateTimeToString(new Date()));
		// {2}--验证地址
		mailContent.put("{2}", url);
		// {3}--可以复制的验证地址
		mailContent.put("{3}", url);
		// {4}--失效时间
		mailContent.put("{4}", String.valueOf(SysConfig.getEmailValidTime()));
		// {5}--邮件后面签名
		String emailSignEnt = SysConfig.getEntEmailSign();
		emailSignEnt = StringUtils.isBlank(emailSignEnt) ? Constants.SENDER_ENT
				: commonService.convertUtf8(emailSignEnt);
		mailContent.put("{5}", emailSignEnt);
		// {6}--时间(yyyy-MM-dd)
		mailContent
				.put("{6}", DateUtil.DateToString(DateUtil.getCurrentDate()));
		return mailContent;
	}

	private EmailBean buildNoCarderValidEmailBean(NoCardHolder noCardHolder,
			String random, Integer custType) {
		String url = getBindEmailUrl(noCardHolder.getPerCustId(),
				noCardHolder.getEmail(), random, TargetEnum.NOCARDER.getType(),
				UserTypeEnum.NO_CARDER.getType());
		String mailTitile = SysConfig.getEmailValidTitle();// 邮件标题
		mailTitile = StringUtils.isBlank(mailTitile) ? "互生系统邮箱绑定验证邮件"
				: commonService.convertUtf8(mailTitile);
		EmailBean emailBean = new EmailBean();
		emailBean.setMsgId(UUID.randomUUID().toString());
		emailBean.setHsResNo(noCardHolder.getMobile());
		emailBean.setMsgReceiver(new String[] { noCardHolder.getEmail() });
		emailBean.setMailTitle(mailTitile);
		emailBean.setContent(constructNoCarderValidMailContent(noCardHolder,
				url));
		emailBean.setCustType(custType);
		emailBean.setBizType(String.valueOf(MsgBizType.MSG_BIZ_TYPE113
				.getCode()));
		emailBean.setSender(Constants.SENDER_CONSUMER);// 待争议
		return emailBean;
	}

	private EmailBean buildCarderValidEmailBean(CardHolder cardHolder,
			String random, Integer custType) {
		String url = getBindEmailUrl(cardHolder.getPerCustId(),
				cardHolder.getEmail(), random, TargetEnum.CARDER.getType(),
				UserTypeEnum.CARDER.getType());
		String mailTitile = SysConfig.getEmailValidTitle();// 邮件标题
		mailTitile = StringUtils.isBlank(mailTitile) ? "互生系统邮箱绑定验证邮件"
				: commonService.convertUtf8(mailTitile);
		EmailBean emailBean = new EmailBean();
		emailBean.setMsgId(UUID.randomUUID().toString());
		emailBean.setHsResNo(cardHolder.getPerResNo());
		emailBean.setCustName(cardHolder.getPerResNo());
		emailBean.setMsgReceiver(new String[] { cardHolder.getEmail() });
		emailBean.setMailTitle(mailTitile);
		emailBean.setContent(constructCarderValidMailContent(cardHolder, url));
		emailBean.setCustType(custType);
		emailBean.setBizType(String.valueOf(MsgBizType.MSG_BIZ_TYPE113
				.getCode()));
		emailBean.setSender(Constants.SENDER_CONSUMER);// 待争议
		return emailBean;
	}

	private EmailBean buildCompanyValidEmailBean(EntExtendInfo entExtendInfo,
			String random, Integer custType) {
		String url = getBindEmailUrl(entExtendInfo.getEntCustId(),
				entExtendInfo.getContactEmail(), random,
				TargetEnum.COMPANY.getType(), UserTypeEnum.ENT.getType());
		String mailTitile = SysConfig.getEmailValidTitle();// 邮件标题
		mailTitile = StringUtils.isBlank(mailTitile) ? "互生系统邮箱绑定验证邮件"
				: commonService.convertUtf8(mailTitile);
		EmailBean emailBean = new EmailBean();
		emailBean.setMsgId(UUID.randomUUID().toString());
		emailBean.setCustName(entExtendInfo.getEntCustName());
		emailBean.setHsResNo(entExtendInfo.getEntResNo());
		emailBean
				.setMsgReceiver(new String[] { entExtendInfo.getContactEmail() });
		emailBean.setMailTitle(mailTitile);
		emailBean.setContent(constructCompanyValidMailContent(entExtendInfo,
				url));
		emailBean.setCustType(custType);
		emailBean.setBizType(String.valueOf(MsgBizType.MSG_BIZ_TYPE113
				.getCode()));
		int startResType = 0;
		if (null != entExtendInfo.getStartResType()) {
			startResType = entExtendInfo.getStartResType();
		}
		emailBean.setBuyResType(startResType);
		emailBean.setSender(Constants.SENDER_ENT);// 待争议
		return emailBean;
	}

	private EmailBean buildScsValidEmailBean(EntExtendInfo entExtendInfo,
			String random, Integer custType) {
		String url = getBindEmailUrl(entExtendInfo.getEntCustId(),
				entExtendInfo.getContactEmail(), random,
				TargetEnum.SCS.getType(), UserTypeEnum.ENT.getType());
		String mailTitile = SysConfig.getEmailValidTitle();// 邮件标题
		mailTitile = StringUtils.isBlank(mailTitile) ? "互生系统邮箱绑定验证邮件"
				: commonService.convertUtf8(mailTitile);
		EmailBean emailBean = new EmailBean();
		emailBean.setMsgId(UUID.randomUUID().toString());
		emailBean.setCustName(entExtendInfo.getEntCustName());
		emailBean.setHsResNo(entExtendInfo.getEntResNo());
		emailBean
				.setMsgReceiver(new String[] { entExtendInfo.getContactEmail() });
		emailBean.setMailTitle(mailTitile);
		emailBean.setContent(constructScsValidMailContent(entExtendInfo, url));
		emailBean.setCustType(custType);
		emailBean.setBizType(String.valueOf(MsgBizType.MSG_BIZ_TYPE113
				.getCode()));
		int startResType = 0;
		if (null != entExtendInfo.getStartResType()) {
			startResType = entExtendInfo.getStartResType();
		}
		emailBean.setBuyResType(startResType);
		emailBean.setSender(Constants.SENDER_ENT);// 待争议
		return emailBean;
	}

	private EmailBean buildMcsValidEmailBean(EntExtendInfo entExtendInfo,
			String random, Integer custType) {
		String url = getBindEmailUrl(entExtendInfo.getEntCustId(),
				entExtendInfo.getContactEmail(), random,
				TargetEnum.MCS.getType(), UserTypeEnum.ENT.getType());
		String mailTitile = SysConfig.getEmailValidTitle();// 邮件标题
		mailTitile = StringUtils.isBlank(mailTitile) ? "互生系统邮箱绑定验证邮件"
				: commonService.convertUtf8(mailTitile);
		EmailBean emailBean = new EmailBean();
		emailBean.setMsgId(UUID.randomUUID().toString());
		emailBean.setCustName(entExtendInfo.getEntCustName());
		emailBean.setHsResNo(entExtendInfo.getEntResNo());
		emailBean
				.setMsgReceiver(new String[] { entExtendInfo.getContactEmail() });
		emailBean.setMailTitle(mailTitile);
		emailBean.setContent(constructMcsValidMailContent(entExtendInfo, url));
		emailBean.setCustType(custType);
		emailBean.setBizType(String.valueOf(MsgBizType.MSG_BIZ_TYPE113
				.getCode()));
		int startResType = 0;
		if (null != entExtendInfo.getStartResType()) {
			startResType = entExtendInfo.getStartResType();
		}
		emailBean.setBuyResType(startResType);
		emailBean.setSender(Constants.SENDER_ENT);// 待争议
		return emailBean;
	}

	private EmailBean buildApsValidEmailBean(EntExtendInfo entExtendInfo,
			String random, Integer custType) {
		String url = getBindEmailUrl(entExtendInfo.getEntCustId(),
				entExtendInfo.getContactEmail(), random,
				TargetEnum.APS.getType(), UserTypeEnum.ENT.getType());
		String mailTitile = SysConfig.getEmailValidTitle();// 邮件标题
		mailTitile = StringUtils.isBlank(mailTitile) ? "互生系统邮箱绑定验证邮件"
				: commonService.convertUtf8(mailTitile);
		EmailBean emailBean = new EmailBean();
		emailBean.setMsgId(UUID.randomUUID().toString());
		emailBean.setHsResNo(entExtendInfo.getEntResNo());
		emailBean.setCustName(entExtendInfo.getEntCustName());
		emailBean
				.setMsgReceiver(new String[] { entExtendInfo.getContactEmail() });
		emailBean.setMailTitle(mailTitile);
		emailBean.setContent(constructApsValidMailContent(entExtendInfo, url));
		emailBean.setCustType(custType);
		emailBean.setBizType(String.valueOf(MsgBizType.MSG_BIZ_TYPE113
				.getCode()));
		int startResType = 0;
		if (null != entExtendInfo.getStartResType()) {
			startResType = entExtendInfo.getStartResType();
		}
		emailBean.setBuyResType(startResType);
		emailBean.setSender(Constants.SENDER_ENT);// 待争议
		return emailBean;
	}

	private EmailBean buildNoCarderRestPwdEmailBean(NoCardHolder noCardHolder,
			String random, Integer custType) {
		String url = getResetPwdUrl(noCardHolder.getPerCustId(),
				noCardHolder.getEmail(), random,
				UserTypeEnum.NO_CARDER.getType());
		String mailTitile = SysConfig.getEmailFindPwdTitle();// 邮件标题
		mailTitile = StringUtils.isBlank(mailTitile) ? "互生系统密码找回验证邮箱邮件"
				: commonService.convertUtf8(mailTitile);
		EmailBean emailBean = new EmailBean();
		emailBean.setMsgId(UUID.randomUUID().toString());
		emailBean.setHsResNo(noCardHolder.getMobile());
		emailBean.setCustName(noCardHolder.getMobile());
		emailBean.setMsgReceiver(new String[] { noCardHolder.getEmail() });
		emailBean.setMailTitle(mailTitile);
		emailBean.setContent(constructNoCarderRestPwdMailContent(noCardHolder,
				url));
		emailBean.setCustType(custType);
		emailBean.setBizType(String.valueOf(MsgBizType.MSG_BIZ_TYPE114
				.getCode()));
		emailBean.setSender(Constants.SENDER_CONSUMER);// 待争议
		return emailBean;
	}

	private SelfEmailBean buildEntLogPwd(EntExtendInfo entExtendInfo,
			String cleartextPwd, Operator adminOper) {
		String mailTitile = getMailTital(entExtendInfo.getCustType());
		SelfEmailBean emailBean = new SelfEmailBean();
		emailBean.setMsgId(UUID.randomUUID().toString());
		emailBean.setCustName(entExtendInfo.getEntCustName());
		emailBean.setHsResNo(entExtendInfo.getEntResNo());
		emailBean
				.setMsgReceiver(new String[] { entExtendInfo.getContactEmail() });
		emailBean.setMailTitle(mailTitile);
		emailBean.setMsgContent(constructEntApplyMailContent(entExtendInfo,
				cleartextPwd, adminOper));
		emailBean.setSender(Constants.SENDER_ENT);// 待争议
		return emailBean;
	}

	private String getMailTital(int custType) {
		StringBuffer mailTitile = new StringBuffer();
		mailTitile.append("互生系统");
		if (6 == custType) {
			mailTitile.append("地区平台");
		} else if (5 == custType) {
			mailTitile.append("管理公司");
		}
		mailTitile.append("系统管理员登录密码");

		return mailTitile.toString();
	}

	private EmailBean buildNoCarderRestPwdEmailBeanForEC(
			NoCardHolder noCardHolder, String random, Integer custType,
			String url) {
		String mailTitile = SysConfig.getEmailFindPwdTitle();// 邮件标题
		mailTitile = StringUtils.isBlank(mailTitile) ? "互生系统密码找回验证邮箱邮件"
				: commonService.convertUtf8(mailTitile);
		EmailBean emailBean = new EmailBean();
		emailBean.setMsgId(UUID.randomUUID().toString());
		emailBean.setHsResNo(noCardHolder.getMobile());
		emailBean.setCustName(noCardHolder.getMobile());
		emailBean.setMsgReceiver(new String[] { noCardHolder.getEmail() });
		emailBean.setMailTitle(mailTitile);
		emailBean.setContent(constructNoCarderRestPwdMailContent(noCardHolder,
				url));
		emailBean.setCustType(custType);
		emailBean.setBizType(String.valueOf(MsgBizType.MSG_BIZ_TYPE114
				.getCode()));
		emailBean.setSender(Constants.SENDER_CONSUMER);// 待争议
		return emailBean;
	}

	private EmailBean buildCarderRestPwdEmailBean(CardHolder cardHolder,
			String random, Integer custType) {
		String url = getResetPwdUrl(cardHolder.getPerCustId(),
				cardHolder.getEmail(), random, UserTypeEnum.CARDER.getType());
		String mailTitile = SysConfig.getEmailFindPwdTitle();// 邮件标题
		mailTitile = StringUtils.isBlank(mailTitile) ? "互生系统密码找回验证邮箱邮件"
				: commonService.convertUtf8(mailTitile);
		EmailBean emailBean = new EmailBean();
		emailBean.setMsgId(UUID.randomUUID().toString());
		emailBean.setHsResNo(cardHolder.getPerResNo());
		emailBean.setCustName(cardHolder.getMobile());
		emailBean.setMsgReceiver(new String[] { cardHolder.getEmail() });
		emailBean.setMailTitle(mailTitile);
		emailBean
				.setContent(constructCarderRestPwdMailContent(cardHolder, url));
		emailBean.setCustType(custType);
		emailBean.setBizType(String.valueOf(MsgBizType.MSG_BIZ_TYPE114
				.getCode()));
		emailBean.setSender(Constants.SENDER_CONSUMER);// 待争议
		return emailBean;
	}

	private EmailBean buildCarderRestPwdEmailBeanForEC(CardHolder cardHolder,
			Integer custType, String url) {
		String mailTitile = SysConfig.getEmailFindPwdTitle();// 邮件标题
		mailTitile = StringUtils.isBlank(mailTitile) ? "互生系统密码找回验证邮箱邮件"
				: commonService.convertUtf8(mailTitile);
		EmailBean emailBean = new EmailBean();
		emailBean.setMsgId(UUID.randomUUID().toString());
		emailBean.setHsResNo(cardHolder.getPerResNo());
		emailBean.setCustName(cardHolder.getMobile());
		emailBean.setMsgReceiver(new String[] { cardHolder.getEmail() });
		emailBean.setMailTitle(mailTitile);
		emailBean
				.setContent(constructCarderRestPwdMailContent(cardHolder, url));
		emailBean.setCustType(custType);
		emailBean.setBizType(String.valueOf(MsgBizType.MSG_BIZ_TYPE114
				.getCode()));
		emailBean.setSender(Constants.SENDER_CONSUMER);// 待争议
		return emailBean;
	}

	private EmailBean buildCompanyRestPwdEmailBean(EntExtendInfo entExtendInfo,
			String random, Integer custType, String operCustId) {
		String url = getResetPwdUrl(operCustId,
				entExtendInfo.getContactEmail(), random,
				UserTypeEnum.ENT.getType());
		String mailTitile = SysConfig.getEmailFindPwdTitle();// 邮件标题
		mailTitile = StringUtils.isBlank(mailTitile) ? "互生系统密码找回验证邮箱邮件"
				: commonService.convertUtf8(mailTitile);
		EmailBean emailBean = new EmailBean();
		emailBean.setHsResNo(entExtendInfo.getEntResNo());
		emailBean.setMsgId(UUID.randomUUID().toString());
		emailBean.setCustName(entExtendInfo.getEntCustName());
		emailBean
				.setMsgReceiver(new String[] { entExtendInfo.getContactEmail() });
		emailBean.setMailTitle(mailTitile);
		emailBean.setContent(constructCompanyRestPwdMailContent(entExtendInfo,
				url));
		emailBean.setCustType(custType);
		emailBean.setBizType(String.valueOf(MsgBizType.MSG_BIZ_TYPE114
				.getCode()));
		int startResType = 0;
		if (null != entExtendInfo.getStartResType()) {
			startResType = entExtendInfo.getStartResType();
		}
		emailBean.setBuyResType(startResType);
		emailBean.setSender(Constants.SENDER_ENT);// 待争议
		return emailBean;
	}

	private EmailBean buildScsRestPwdEmailBean(EntExtendInfo entExtendInfo,
			String random, Integer custType, String operCustId) {
		String url = getResetPwdUrl(operCustId,
				entExtendInfo.getContactEmail(), random,
				UserTypeEnum.ENT.getType());
		String mailTitile = SysConfig.getEmailFindPwdTitle();// 邮件标题
		mailTitile = StringUtils.isBlank(mailTitile) ? "互生系统密码找回验证邮箱邮件"
				: commonService.convertUtf8(mailTitile);
		EmailBean emailBean = new EmailBean();
		emailBean.setMsgId(UUID.randomUUID().toString());
		emailBean.setHsResNo(entExtendInfo.getEntResNo());
		emailBean.setCustName(entExtendInfo.getEntCustName());
		emailBean
				.setMsgReceiver(new String[] { entExtendInfo.getContactEmail() });
		emailBean.setMailTitle(mailTitile);
		emailBean
				.setContent(constructScsRestPwdMailContent(entExtendInfo, url));
		emailBean.setCustType(custType);
		emailBean.setBizType(String.valueOf(MsgBizType.MSG_BIZ_TYPE114
				.getCode()));
		int startResType = 0;
		if (null != entExtendInfo.getStartResType()) {
			startResType = entExtendInfo.getStartResType();
		}
		emailBean.setBuyResType(startResType);
		emailBean.setSender(Constants.SENDER_ENT);// 待争议
		return emailBean;
	}

	private EmailBean buildMcsRestPwdEmailBean(EntExtendInfo entExtendInfo,
			String random, Integer custType, String operCustId) {
		String url = getResetPwdUrl(operCustId,
				entExtendInfo.getContactEmail(), random,
				UserTypeEnum.ENT.getType());
		String mailTitile = SysConfig.getEmailFindPwdTitle();// 邮件标题
		mailTitile = StringUtils.isBlank(mailTitile) ? "互生系统密码找回验证邮箱邮件"
				: commonService.convertUtf8(mailTitile);
		EmailBean emailBean = new EmailBean();
		emailBean.setMsgId(UUID.randomUUID().toString());
		emailBean.setCustName(entExtendInfo.getEntCustName());
		emailBean.setHsResNo(entExtendInfo.getEntResNo());
		emailBean
				.setMsgReceiver(new String[] { entExtendInfo.getContactEmail() });
		emailBean.setMailTitle(mailTitile);
		emailBean
				.setContent(constructMcsRestPwdMailContent(entExtendInfo, url));
		emailBean.setCustType(custType);
		emailBean.setBizType(String.valueOf(MsgBizType.MSG_BIZ_TYPE114
				.getCode()));
		int startResType = 0;
		if (null != entExtendInfo.getStartResType()) {
			startResType = entExtendInfo.getStartResType();
		}
		emailBean.setBuyResType(startResType);
		emailBean.setSender(Constants.SENDER_ENT);// 待争议
		return emailBean;
	}

	private EmailBean buildApsRestPwdEmailBean(EntExtendInfo entExtendInfo,
			String random, Integer custType, String operCustId) {
		String url = getResetPwdUrl(operCustId,
				entExtendInfo.getContactEmail(), random,
				UserTypeEnum.ENT.getType());
		String mailTitile = SysConfig.getEmailFindPwdTitle();// 邮件标题
		mailTitile = StringUtils.isBlank(mailTitile) ? "互生系统密码找回验证邮箱邮件"
				: commonService.convertUtf8(mailTitile);
		EmailBean emailBean = new EmailBean();
		emailBean.setMsgId(UUID.randomUUID().toString());
		emailBean.setHsResNo(entExtendInfo.getEntResNo());
		emailBean.setCustName(entExtendInfo.getEntCustName());
		emailBean
				.setMsgReceiver(new String[] { entExtendInfo.getContactEmail() });
		emailBean.setMailTitle(mailTitile);
		emailBean
				.setContent(constructApsRestPwdMailContent(entExtendInfo, url));
		emailBean.setCustType(custType);
		emailBean.setBizType(String.valueOf(MsgBizType.MSG_BIZ_TYPE114
				.getCode()));
		int startResType = 0;
		if (null != entExtendInfo.getStartResType()) {
			startResType = entExtendInfo.getStartResType();
		}
		emailBean.setBuyResType(startResType);
		emailBean.setSender(Constants.SENDER_ENT);// 待争议
		return emailBean;
	}

	private String getBindEmailUrl(String custId, String email, String random,
			String target, String userType) {
		StringBuffer url = new StringBuffer();
		String urlInfo = "";
		switch (target) {
		case "1":
			urlInfo = SysConfig.getNoCarderEmailValidUrl();// 非持卡人验证邮箱链接
			break;
		case "2":
			urlInfo = SysConfig.getPersontEmailValidUrl();// 持卡人验证邮箱链接
			break;
		case "3":
			urlInfo = SysConfig.getCompanyEmailValidUrl();// 企业验证邮箱链接
			break;
		case "4":
			urlInfo = SysConfig.getScsEmailValidUrl();// 服务公司验证邮箱链接
			break;
		case "5":
			urlInfo = SysConfig.getMcsEmailValidUrl();// 管理公司验证邮箱链接
			break;
		case "6":
			urlInfo = SysConfig.getApsEmailValidUrl();// 地区平台验证邮箱链接
			break;
		default:
			break;
		}
		JSONObject param = new JSONObject();
		param.put("random", random);
		param.put("email", email);
		param.put("custId", custId);
		param.put("userType", userType);
		StringBuffer msg = new StringBuffer();
		String bindEmailUrl = "";
		bindEmailUrl = url
				.append(urlInfo)
				.append("param="
						+ Base64Utils.encode(param.toJSONString().getBytes()))
				.toString();
		msg.append("custId[" + custId + "],email[" + email + "],random["
				+ random + "],target[" + target + "],bindEmailUrl["
				+ bindEmailUrl + "],userType[" + userType + "]");
		SystemLog.debug(MOUDLENAME, "getBindEmailUrl", msg.toString());
		return bindEmailUrl;

	}

	/**
	 * 发送非持卡人验证邮件（重置非持卡人登录密码,电商调用）
	 * 
	 * @param resetUrl
	 * @param custId
	 * @param email
	 * @throws HsException
	 */
	public void sendNoCarderMailForRestPwd(String resetUrl, String perCustId,
			String email) throws HsException {
		validElectricityParams(resetUrl, perCustId, email,
				CustType.NOT_HS_PERSON.getCode());
		String methodName = "sendNoCarderMailForRestPwd";
		StringBuffer msg = new StringBuffer();
		// 检查邮箱是否正确
		NoCardHolder noCardHolder = commonCacheService
				.searchNoCardHolderInfo(perCustId);
		String correctEmail = StringUtils.nullToEmpty(noCardHolder.getEmail());
		int isAuthEmail = 0;
		if (null != noCardHolder.getIsAuthEmail()) {
			isAuthEmail = noCardHolder.getIsAuthEmail();
		}
		msg.append("发送非持卡人验证邮件（重置非持卡人登录密码,电商调用）异常,输入参数：perCustId[")
				.append(perCustId).append("],resetUrl[").append(resetUrl)
				.append("],email[").append(email).append("],isAuthEmail[")
				.append(isAuthEmail)
				.append("],correctEmail[" + correctEmail + "]");
		if (!email.equals(correctEmail)) {
			commonService.handleHsException(MOUDLENAME, methodName,
					msg.toString(), ErrorCodeEnum.EMAIL_NOT_MATCH);
		}
		if (1 != isAuthEmail) {
			commonService.handleHsException(MOUDLENAME, methodName,
					msg.toString(), ErrorCodeEnum.EMAIL_NOT_CHECK);
		}
		try {
			String random = CSPRNG.generateRandom(SysConfig.getCheckCodeLen());
			String completeUrl = appendUrlParams(resetUrl, random,
					correctEmail, noCardHolder.getPerCustId(),
					UserTypeEnum.NO_CARDER.getType());
			EmailBean emailBean = buildNoCarderRestPwdEmailBeanForEC(
					noCardHolder, random, CustType.NOT_HS_PERSON.getCode(),
					completeUrl);
			msg.append(",调用通知系统参数：emailBean[")
					.append(JSONObject.toJSONString(emailBean)).append("]")
					.append(NEWLINE);
			ntService.sendEmail(emailBean);
			SystemLog.debug(MOUDLENAME, methodName,
					"emailBean[" + JSONObject.toJSONString(emailBean) + "]");
			commonCacheService.addBindEmailCodeCache(email, random);
			SystemLog.debug(MOUDLENAME, "sendNoCarderRestPwdMailForEC",
					"emailBean[" + JSONObject.toJSONString(emailBean) + "]");
		} catch (Exception e) {
			commonService.handleHsException(MOUDLENAME, methodName,
					msg.toString(), ErrorCodeEnum.EMAIL_SEND_FAILED, e);
		}
	}

	/**
	 * 发送持卡人验证邮件（重置持卡人登录密码,电商调用）
	 * 
	 * @param resetUrl
	 * @param custId
	 * @param email
	 * @throws HsException
	 */
	public void sendCarderMailForRestPwd(String resetUrl, String perCustId,
			String email) throws HsException {
		validElectricityParams(resetUrl, perCustId, email,
				CustType.PERSON.getCode());
		String methodName = "sendCarderRestPwdCode";
		StringBuffer msg = new StringBuffer();
		// 检查邮箱是否正确
		CardHolder cardHolder = commonCacheService
				.searchCardHolderInfo(perCustId);
		String correctEmail = StringUtils.nullToEmpty(cardHolder.getEmail());
		int isAuthEmail = 0;
		if (null != cardHolder.getIsAuthEmail()) {
			isAuthEmail = cardHolder.getIsAuthEmail();
		}
		msg.append("发送持卡人验证邮件（重置持卡人登录密码,电商调用）异常,输入参数：perCustId[")
				.append(perCustId).append("],resetUrl[").append(resetUrl)
				.append("],email[").append(email).append("],isAuthEmail[")
				.append(isAuthEmail)
				.append("],correctEmail[" + correctEmail + "]");
		if (!email.equals(correctEmail)) {
			commonService.handleHsException(MOUDLENAME, methodName,
					msg.toString(), ErrorCodeEnum.EMAIL_NOT_MATCH);
		}
		if (1 != isAuthEmail) {
			commonService.handleHsException(MOUDLENAME, methodName,
					msg.toString(), ErrorCodeEnum.EMAIL_NOT_CHECK);
		}
		try {
			String random = CSPRNG.generateRandom(SysConfig.getCheckCodeLen());// 生成随机数
			String completeUrl = appendUrlParams(resetUrl, random,
					correctEmail, cardHolder.getPerCustId(),
					UserTypeEnum.CARDER.getType());
			EmailBean emailBean = buildCarderRestPwdEmailBeanForEC(cardHolder,
					CustType.PERSON.getCode(), completeUrl);
			msg.append(",调用通知系统参数：emailBean[")
					.append(JSONObject.toJSONString(emailBean)).append("]")
					.append(NEWLINE);
			ntService.sendEmail(emailBean);
			commonCacheService.addBindEmailCodeCache(email, random);
			SystemLog.debug(MOUDLENAME, methodName,
					"emailBean[" + JSONObject.toJSONString(emailBean) + "]");
		} catch (Exception e) {
			commonService.handleHsException(MOUDLENAME, methodName,
					msg.toString(), ErrorCodeEnum.EMAIL_SEND_FAILED, e);
		}

	}

	private String appendUrlParams(String url, String random, String email,
			String custId, String userType) {
		StringBuffer completeUrl = new StringBuffer();
		JSONObject param = new JSONObject();
		param.put("random", random);
		param.put("email", email);
		param.put("custId", custId);
		param.put("userType", userType);
		String resetPwdUrl = completeUrl
				.append(url)
				.append("param="
						+ Base64Utils.encode(param.toJSONString().getBytes()))
				.toString();
		return resetPwdUrl;
	}

	@Override
	public void sendMailForValidEmail(String email, String userName,
			String entResNo, String userType, Integer custType)
			throws HsException {
		if (CustType.NOT_HS_PERSON.getCode() == custType) {
			sendNoCarderValidEmail(userName, email, custType);
		} else if (CustType.PERSON.getCode() == custType) {
			sendCarderValidEmail(userName, email, custType);
		} else if (CustType.MEMBER_ENT.getCode() == custType) {
			sendCompanyValidEmail(entResNo, userName, email, custType);
		} else if (CustType.TRUSTEESHIP_ENT.getCode() == custType) {
			sendCompanyValidEmail(entResNo, userName, email, custType);
		} else if (CustType.SERVICE_CORP.getCode() == custType) {
			sendScsValidEmail(entResNo, userName, email, custType);
		} else if (CustType.MANAGE_CORP.getCode() == custType) {
			sendMcsValidEmail(entResNo, userName, email, custType);
		} else if (CustType.AREA_PLAT.getCode() == custType) {
			sendApsValidEmail(entResNo, userName, email, custType);
		} else {
			throw new HsException(ErrorCodeEnum.PARAM_IS_ILLEGAL.getValue(),
					"客户类型非法,custType[" + custType + "]");
		}
	}

	@Override
	public void sendMailForRestPwd(String custId, String userType,
			String email, Integer custType) throws HsException {
		if (CustType.NOT_HS_PERSON.getCode() == custType) {
			sendNoCarderRestPwdEmail(custId, email, custType);
		} else if (CustType.PERSON.getCode() == custType) {
			sendCarderRestPwdEmail(custId, email, custType);
		} else if (CustType.MEMBER_ENT.getCode() == custType) {
			sendCompanyRestPwdEmail(custId, email, custType);
		} else if (CustType.TRUSTEESHIP_ENT.getCode() == custType) {
			sendCompanyRestPwdEmail(custId, email, custType);
		} else if (CustType.SERVICE_CORP.getCode() == custType) {
			sendScsRestPwdEmail(custId, email, custType);
		} else if (CustType.MANAGE_CORP.getCode() == custType) {
			sendMcsRestPwdEmail(custId, email, custType);
		} else if (CustType.AREA_PLAT.getCode() == custType) {
			sendApsRestPwdEmail(custId, email, custType);
		} else {
			throw new HsException(ErrorCodeEnum.PARAM_IS_ILLEGAL.getValue(),
					"客户类型非法,custType[" + custType + "]");
		}
	}
}
