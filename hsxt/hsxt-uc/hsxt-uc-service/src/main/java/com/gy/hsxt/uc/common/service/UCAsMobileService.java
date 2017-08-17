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
import com.gy.hsi.nt.api.beans.NoteBean;
import com.gy.hsi.nt.api.beans.SelfDynamicBizMsgBean;
import com.gy.hsi.nt.api.service.INtService;
import com.gy.hsxt.bs.common.enumtype.msgtpl.MsgBizType;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.RandomGuidAgent;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.Constants;
import com.gy.hsxt.uc.as.api.common.IUCAsMobileService;
import com.gy.hsxt.uc.as.api.common.IUCAsNetworkInfoService;
import com.gy.hsxt.uc.as.api.consumer.IUCAsNoCardHolderService;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.api.operator.IUCAsOperatorService;
import com.gy.hsxt.uc.as.bean.common.AsNetworkInfo;
import com.gy.hsxt.uc.cache.service.CommonCacheService;
import com.gy.hsxt.uc.common.SysConfig;
import com.gy.hsxt.uc.consumer.bean.CardHolder;
import com.gy.hsxt.uc.consumer.bean.NoCardHolder;
import com.gy.hsxt.uc.consumer.bean.RealNameAuth;
import com.gy.hsxt.uc.consumer.service.UCAsCardHolderService;
import com.gy.hsxt.uc.ent.bean.EntExtendInfo;
import com.gy.hsxt.uc.ent.bean.EntStatusInfo;
import com.gy.hsxt.uc.ent.mapper.EntExtendInfoMapper;
import com.gy.hsxt.uc.ent.mapper.EntStatusInfoMapper;
import com.gy.hsxt.uc.ent.service.AsEntService;
import com.gy.hsxt.uc.operator.bean.Operator;
import com.gy.hsxt.uc.operator.mapper.OperatorMapper;
import com.gy.hsxt.uc.util.CSPRNG;
import com.gy.hsxt.uc.util.CustIdGenerator;

/**
 * 
 * 
 * @Package: com.gy.hsxt.uc.common.service
 * @ClassName: UCAsMobileService
 * @Description: 手机短信相关接口管理类（供AS接入系统调用）
 * 
 * @author: tianxh
 * @date: 2015-12-14 下午8:26:19
 * @version V1.0
 */
@Service
public class UCAsMobileService implements IUCAsMobileService {
	private static final String MOUDLENAME = "com.gy.hsxt.uc.common.service.UCAsMobileService";
	private static final String NEWLINE = "\r\n";
	/**
	 * 通知服务
	 */
	@Autowired
	INtService ntService;

	/**
	 * 持卡人管理接口
	 */
	@Autowired
	UCAsCardHolderService cardHolderService;
	/**
	 * 非持卡人管理接口
	 */
	@Autowired
	IUCAsNoCardHolderService noCardHolderService;
	/**
	 * 管理员管理接口
	 */
	@Autowired
	IUCAsOperatorService operatorService;
	/**
	 * 企业管理操作接口
	 */
	@Autowired
	IUCAsEntService asEntService;

	@Autowired
	OperatorMapper operatorMapper;

	@Autowired
	EntExtendInfoMapper entExtendInfoMapper;

	@Autowired
	EntStatusInfoMapper entStatusInfoMapper;

	@Autowired
	CommonCacheService commonCacheService;

	@Autowired
	CommonService commonService;

	@Autowired
	AsEntService entService;
	
	@Autowired
	IUCAsNetworkInfoService networkInfoService;
	
	

	/**
	 * 企业发送授权码（重置交易密码）
	 * 
	 * @param entResNo
	 * @param custType
	 * @throws HsException
	 */

	public void sendAuthCodeSuccess(String entResNo, Integer custType)
			throws HsException {
		if (StringUtils.isEmpty(entResNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数企业互生号不能为空");
		}
		if (null == custType) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数客户类型不能为空");
		}
		String methodName = "sendAuthCodeSuccess";
		StringBuffer msg = new StringBuffer();
		String entCustId = commonCacheService.findEntCustIdByEntResNo(entResNo);
		CustIdGenerator.isEntExtendExist(entCustId, entResNo);
		EntExtendInfo extendInfo = commonCacheService
				.searchEntExtendInfo(entCustId);
		CustIdGenerator.isEntExtendExist(extendInfo, entCustId);
		String correctMobile = StringUtils.nullToEmpty(extendInfo
				.getContactPhone());
		msg.append("企业发送授权码（重置交易密码）异常,输入参数：entResNo[").append(entResNo)
				.append("],custType[").append(custType)
				.append("],correctMobile[").append(correctMobile).append("]");
		String authCode = CSPRNG.generateRandom(SysConfig.getCheckCodeLen());
		try {
			NoteBean noteBean = buildEntRestTranPwdMobileBean(extendInfo,
					authCode, custType);
			msg.append(",调用通知系统参数：noteBean[")
					.append(JSONObject.toJSONString(noteBean)).append("]")
					.append(NEWLINE);
			ntService.sendNote(noteBean);
			commonCacheService.addSmsAuthCodeCache(correctMobile, authCode);
			SystemLog.debug(MOUDLENAME, "sendAuthCodeSuccess", "noteBean["
					+ JSONObject.toJSONString(noteBean) + "]");
		} catch (Exception e) {
			commonService.handleHsException(MOUDLENAME, methodName,
					msg.toString(), ErrorCodeEnum.SM_CODE_NOT_MATCH, e);
		}
	}

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

	public String checkSmsCode(String mobile, String smsCode)
			throws HsException {
		if (StringUtils.isEmpty(smsCode)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数短信验证码为空");
		}
		if (StringUtils.isEmpty(mobile)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数手机号码为空");
		}

		// 比对验证码是否一致
		String code = commonCacheService.getSmsCodeCache(mobile);
		String isnoreCheckCode = StringUtils.nullToEmpty(SysConfig
				.getIgnoreCheckCode());
		if (!"1".equals(isnoreCheckCode)) {// 是否忽略手机短信验证码的验证（0：不忽略 1：忽略）
			if (StringUtils.isEmpty(code)) {
				throw new HsException(ErrorCodeEnum.SMS_IS_EXPIRED.getValue(),
						ErrorCodeEnum.SMS_IS_EXPIRED.getDesc());
			}
			if (!smsCode.equals(code)) {
				throw new HsException(ErrorCodeEnum.SMS_NOT_MATCH.getValue(),
						ErrorCodeEnum.SMS_NOT_MATCH.getDesc());
			}
		}
		commonCacheService.addSmsCodeCache(mobile, code);
		return code;
	}

	/**
	 * 验证手机短信验证码
	 * 
	 * @param mobile
	 *            手机号
	 * @param smsCode
	 *            短信验证码
	 * @throws HsException
	 */

	public String checkSmsResetCode(String mobile, String smsCode)
			throws HsException {
		if (StringUtils.isEmpty(smsCode)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数短信验证码为空");
		}
		if (StringUtils.isEmpty(mobile)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数手机号码为空");
		}
		// 比对验证码是否一致
		String code = commonCacheService.getSmsResetCodeCache(mobile);
		String isnoreCheckCode = StringUtils.nullToEmpty(SysConfig
				.getIgnoreCheckCode());
		if (!"1".equals(isnoreCheckCode)) {// 是否忽略手机短信验证码验证（0：不忽略 1：忽略）
			if (StringUtils.isEmpty(code)) {
				throw new HsException(ErrorCodeEnum.SMS_IS_EXPIRED.getValue(),
						ErrorCodeEnum.SMS_IS_EXPIRED.getDesc());
			}
			if (!smsCode.equals(code)) {
				throw new HsException(ErrorCodeEnum.SMS_NOT_MATCH.getValue(),
						ErrorCodeEnum.SMS_NOT_MATCH.getDesc());
			}
		}
		commonCacheService.addSmsResetCodeCache(mobile, code);
		return code;
	}

	/**
	 * 绑定手机（校验手机验证码，校验通过入库手机号码）
	 * 
	 * @param custId
	 * @param mobile
	 * @param smsCode
	 * @param userType
	 * @throws HsException
	 */

	@Transactional(rollbackFor = Exception.class)
	public void bindMobile(String custId, String mobile, String smsCode,
			String userType) throws HsException {
		if (UserTypeEnum.CARDER.getType().equals(userType)) {
			commonService.checkCarderIsAuthMobile(custId, mobile);
		}else if (UserTypeEnum.NO_CARDER.getType().equals(userType)){
			commonService.checkNoCarderIsAuthMobile(custId, mobile);
		}
		checkSmsCode(mobile, smsCode);
		if (StringUtils.isBlank(custId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数客户号为空");
		}
		if (StringUtils.isBlank(userType)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数用户类型为空");
		}
		updateMobileInfo(custId.trim(), mobile.trim(), userType.trim());
	}

	/**
	 * 发送重置后新的登录密码（持卡人重置登录密码）
	 * 
	 * @param custId
	 *            客户号（包含非持卡人、持卡人、操作员）
	 * @param userType
	 *            用户类型 1：非持卡人 2：持卡人 3：操作员
	 * @throws HsException
	 */
	public void sendCarderRestPwdByReChecker(CardHolder cardHolder,
			String cleartextPwd) throws HsException {
		String methodName = "sendCarderRestPwdByReChecker";
		StringBuffer msg = new StringBuffer();
		try {
			msg.append("发送重置后新的登录密码（持卡人重置登录密码）异常,输入参数：cleartextPwd[")
					.append(cleartextPwd).append("],cardHolder[")
					.append(JSON.toJSONString(cardHolder)).append("]");
			NoteBean noteBean = buildCarderRestPwdMobileBeanByReChecker(
					cardHolder, cleartextPwd, CustType.PERSON.getCode());
			msg.append(",调用通知系统参数：noteBean[")
					.append(JSONObject.toJSONString(noteBean)).append("]")
					.append(NEWLINE);
			ntService.sendNote(noteBean);
			SystemLog.debug(MOUDLENAME, "sendCarderRestPwdByReChecker",
					"noteBean[" + JSONObject.toJSONString(noteBean) + "]");
		} catch (Exception e) {
			commonService.handleHsException(MOUDLENAME, methodName,
					msg.toString(), ErrorCodeEnum.SM_SEND_FAILED, e);
		}
	}

	/**
	 * 发送重置后新的登录密码（操作员重置登录密码）
	 * 
	 * @param custId
	 *            客户号（包含非持卡人、持卡人、操作员）
	 * @param userType
	 *            用户类型 1：非持卡人 2：持卡人 3：操作员
	 * @throws HsException
	 */
	public void sendOperRestPwdByReChecker(EntExtendInfo extendInfo,
			String cleartextPwd) throws HsException {
		String methodName = "sendOperRestPwdByReChecker";
		StringBuffer msg = new StringBuffer();
		msg.append("发送重置后新的登录密码（操作员重置登录密码）异常,输入参数：cleartextPwd[")
				.append(cleartextPwd).append("],extendInfo[")
				.append(JSON.toJSONString(extendInfo)).append("]");
		try {
			NoteBean noteBean = buildOperRestPwdMobileBeanByReChecker(
					extendInfo, cleartextPwd);
			msg.append(",调用通知系统参数：noteBean[")
					.append(JSONObject.toJSONString(noteBean)).append("]")
					.append(NEWLINE);
			ntService.sendNote(noteBean);
			SystemLog.debug(MOUDLENAME, "sendOperRestPwdByReChecker",
					"noteBean[" + JSONObject.toJSONString(noteBean) + "]");
		} catch (Exception e) {
			commonService.handleHsException(MOUDLENAME, methodName,
					msg.toString(), ErrorCodeEnum.SM_SEND_FAILED, e);
		}
	}

	/**
	 * 企业申报成功发送登录密码（手机方式）
	 * 
	 * @param custId
	 *            客户号（包含非持卡人、持卡人、操作员）
	 * @param userType
	 *            用户类型 1：非持卡人 2：持卡人 3：操作员
	 * @throws HsException
	 */
	public void sendInitOperPwd(EntExtendInfo extendInfo, Operator adminOper,
			String cleartextPwd) throws HsException {
		String methodName = "sendInitOperPwd";
		StringBuffer msg = new StringBuffer();
		msg.append("企业申报成功发送登录密码（手机方式）异常,输入参数：extendInfo[")
				.append(JSONObject.toJSONString(extendInfo))
				.append("],adminOper[").append(JSON.toJSONString(adminOper))
				.append("],cleartextPwd[").append(cleartextPwd).append("]");
		try {
			NoteBean noteBean = buildInitOperPwdMobileBean(extendInfo,
					adminOper, cleartextPwd);
			msg.append(",调用通知系统参数：noteBean[")
					.append(JSONObject.toJSONString(noteBean)).append("]")
					.append(NEWLINE);
			ntService.sendNote(noteBean);
			SystemLog.debug(MOUDLENAME, methodName,
					"noteBean[" + JSONObject.toJSONString(noteBean) + "]");
		} catch (Exception e) {
			commonService.handleHsException(MOUDLENAME, methodName,
					msg.toString(), ErrorCodeEnum.SM_SEND_FAILED, e);
		}
	}

	/**
	 * 地区平台重置消费者的交易密码（手机方式通知）
	 * 
	 * @param cardHolder
	 * @param clearTranPwd
	 * @throws HsException
	 */
	public void sendRestTranPwdCodeByReChecker(CardHolder cardHolder,
			String clearTranPwd) throws HsException {
		String methodName = "sendRestTranPwdCodeByReChecker";
		StringBuffer msg = new StringBuffer();
		msg.append("地区平台重置消费者的交易密码（手机方式通知）异常,输入参数：clearTranPwd[")
				.append(clearTranPwd).append("],cardHolder[")
				.append(JSON.toJSONString(cardHolder)).append("]");
		try {
			NoteBean noteBean = buildCarderRestTranPwdMobileBeanByReChecker(
					cardHolder, clearTranPwd, CustType.PERSON.getCode());
			msg.append(",调用通知系统参数：noteBean[")
					.append(JSONObject.toJSONString(noteBean)).append("]")
					.append(NEWLINE);
			ntService.sendNote(noteBean);
			SystemLog.debug(MOUDLENAME, "sendRestTranPwdCodeByReChecker",
					"noteBean[" + JSONObject.toJSONString(noteBean) + "]");
		} catch (Exception e) {
			commonService.handleHsException(MOUDLENAME, methodName,
					msg.toString(), ErrorCodeEnum.SM_SEND_FAILED, e);
		}
	}

	/**
	 * 地区平台重置企业的交易密码（手机方式通知）
	 * 
	 * @param cardHolder
	 * @param clearTranPwd
	 * @throws HsException
	 */
	public void sendEntRestTranPwdCodeByReChecker(EntExtendInfo entExtendInfo,
			String clearTranPwd) throws HsException {
		String methodName = "sendEntRestTranPwdCodeByReChecker";
		StringBuffer msg = new StringBuffer();
		msg.append("地区平台重置企业的交易密码（手机方式通知）异常,输入参数：clearTranPwd[")
				.append(clearTranPwd).append("],entExtendInfo[")
				.append(JSON.toJSONString(entExtendInfo)).append("]");
		try {
			NoteBean noteBean = buildEntRestTranPwdMobileBeanByReChecker(
					entExtendInfo, clearTranPwd);
			msg.append(",调用通知系统参数：noteBean[")
					.append(JSONObject.toJSONString(noteBean)).append("]")
					.append(NEWLINE);
			ntService.sendNote(noteBean);
			SystemLog.debug(MOUDLENAME, methodName,
					"noteBean[" + JSONObject.toJSONString(noteBean) + "]");
		} catch (Exception e) {
			commonService.handleHsException(MOUDLENAME, methodName,
					msg.toString(), ErrorCodeEnum.SM_SEND_FAILED, e);
		}
	}

	/**
	 * 更新手机号码入库
	 * 
	 * @param custId
	 *            客户号
	 * @param mobile
	 *            手机号码
	 * @param userType
	 *            用户类型 非持卡1 持卡人2 操作员3 企业 4
	 * @throws HsException
	 */
	private void updateMobileInfo(String custId, String mobile, String userType)
			throws HsException {
		Timestamp now = StringUtils.getNowTimestamp();
		if (UserTypeEnum.CARDER.getType().equals(userType)) {
			CardHolder cardHolder = new CardHolder();
			cardHolder.setPerCustId(custId);
			cardHolder.setMobile(mobile);
			cardHolder.setIsAuthMobile(1);
			cardHolder.setUpdateDate(StringUtils.getNowTimestamp());
			cardHolder.setUpdatedby(custId);
			commonCacheService.updateCardHolderInfo(cardHolder);
		} else if (UserTypeEnum.OPERATOR.getType().equals(userType)) {
			Operator operator = new Operator();
			operator.setOperCustId(custId);
			operator.setPhone(mobile);
			operator.setUpdatedby(custId);
			operator.setUpdateDate(now);
			try {
				operatorMapper.updateByPrimaryKeySelective(operator);
			} catch (HsException e) {
				throw new HsException(
						ErrorCodeEnum.OPERATOR_UPDATE_ERROR.getValue(),
						e.getMessage());
			}
			commonCacheService.removeOperCache(custId);
		} else if (UserTypeEnum.ENT.getType().equals(userType)) {
			EntExtendInfo entExtendInfo = new EntExtendInfo();
			entExtendInfo.setEntCustId(custId);
			entExtendInfo.setContactPhone(mobile);
			entExtendInfo.setUpdateDate(now);
			entExtendInfo.setUpdatedby(custId);
			try {
				entExtendInfoMapper.updateByPrimaryKeySelective(entExtendInfo);
			} catch (HsException e) {
				throw new HsException(
						ErrorCodeEnum.ENTEXTENDINFO_UPDATE_ERROR.getValue(),
						e.getMessage());
			}
			commonCacheService.removeEntExtendInfoCache(custId);
			EntStatusInfo entStatusInfo = new EntStatusInfo();
			entStatusInfo.setEntCustId(custId);
			entStatusInfo.setIsAuthMobile(1);
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
		} else {
			if (StringUtils.isBlank(userType)) {
				throw new HsException(
						ErrorCodeEnum.USERTYPE_IS_ILLEGAL.getValue(),
						ErrorCodeEnum.USERTYPE_IS_ILLEGAL.getDesc());
			}
		}
	}

	/**
	 * 非持卡人获取短信验证码（注册的时候）
	 * 
	 * @param mobile
	 * @param custType
	 * @throws HsException
	 */
	public void sendNoCarderValidCode(String mobile, Integer custType)
			throws HsException {
		if (StringUtils.isBlank(mobile)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"输入参数手机号码为为空");
		} 
		if (null == custType) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"输入参数客户号类型为空");
		}
		String methodName = "sendNoCarderValidCode";
		StringBuffer msg = new StringBuffer();
		try {
			NoCardHolder noCardHolder = new NoCardHolder();
			String perCustId = commonCacheService.findCustIdByMobile(mobile);
			if(StringUtils.isNotBlank(perCustId)){
				throw new HsException(ErrorCodeEnum.USER_EXIST.getValue(),
						mobile +ErrorCodeEnum.USER_EXIST.getDesc());
			}
			noCardHolder.setMobile(mobile);
			String random = CSPRNG.generateRandom(SysConfig.getCheckCodeLen());// 生成随机数
			NoteBean noteBean = buildNoCarderValidMobileBean(noCardHolder,
					random, custType);
			msg.append("非持卡人获取短信验证码（注册的时候）异常,输入参数：").append("mobile[")
					.append(mobile).append("],custType[").append(custType)
					.append("]").append(",调用通知系统参数：noteBean[")
					.append(JSONObject.toJSONString(noteBean)).append("]")
					.append(NEWLINE);
			ntService.sendNote(noteBean);
			SystemLog.debug(MOUDLENAME, "sendNoCarderValidCode", "noteBean["
					+ JSONObject.toJSONString(noteBean) + "]");
			commonCacheService.addSmsCodeCache(mobile, random);
		} catch (Exception e) {
			commonService.handleHsException(MOUDLENAME, methodName,
					msg.toString(), ErrorCodeEnum.SM_SEND_FAILED, e);
		}
	}

	
	/**
	 * 非持卡人获取短信验证码（重置非持卡人交易密码）
	 * 
	 * @param mobile
	 * @param custType
	 * @throws HsException
	 */
	public void sendNoCarderSmscode(String mobile)
			throws HsException {
		if (StringUtils.isBlank(mobile)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"输入参数手机号码为为空");
		} 
		String methodName = "sendNoCarderSmscode";
		StringBuffer msg = new StringBuffer();
		try {
			String perCustId = commonCacheService.findCustIdByMobile(mobile);
			CustIdGenerator.isNoCarderExist(perCustId, mobile);
			NoCardHolder noCardHolder = commonCacheService.searchNoCardHolderInfo(perCustId);
			CustIdGenerator.isNoCarderExist(noCardHolder, perCustId);
			String random = CSPRNG.generateRandom(SysConfig.getCheckCodeLen());// 生成随机数
			NoteBean noteBean = buildNoCarderValidMobileBean(noCardHolder,
					random, CustType.NOT_HS_PERSON.getCode());
			msg.append("非持卡人获取短信验证码（重置非持卡人交易密码）异常,输入参数：").append("mobile[")
					.append(mobile).append("],custType[").append(CustType.NOT_HS_PERSON.getCode())
					.append("]").append(",调用通知系统参数：noteBean[")
					.append(JSONObject.toJSONString(noteBean)).append("]")
					.append(NEWLINE);
			ntService.sendNote(noteBean);
			SystemLog.debug(MOUDLENAME, methodName, "noteBean["
					+ JSONObject.toJSONString(noteBean) + "]");
			commonCacheService.addSmsCodeCache(mobile, random);
		} catch (Exception e) {
			commonService.handleHsException(MOUDLENAME, methodName,
					msg.toString(), ErrorCodeEnum.SM_SEND_FAILED, e);
		}
	}
	/**
	 * 持卡人获取短信验证码（验证手机的时候）
	 * 
	 * @param perCustId
	 * @param mobile
	 * @param custType
	 * @throws HsException
	 */
	public void sendCarderValidCode(String perCustId, String mobile,
			Integer custType) throws HsException {
		checkValidParams(perCustId, mobile, custType);
		String methodName = "sendCarderValidCode";
		StringBuffer msg = new StringBuffer();
		CardHolder cardHolder = commonCacheService
				.searchCardHolderInfo(perCustId);
		CustIdGenerator.isCarderExist(cardHolder, perCustId);
		commonService.checkCarderIsAuthMobile(perCustId, mobile);
		cardHolder.setMobile(mobile);
		String random = CSPRNG.generateRandom(SysConfig.getCheckCodeLen());// 生成随机数
		try {
			NoteBean noteBean = buildCarderValidMobileBean(cardHolder, random,
					custType);
			msg.append("持卡人获取短信验证码（验证手机的时候）异常,输入参数：").append("mobile[")
					.append(mobile).append("],custType[").append(custType)
					.append("]").append(",调用通知系统参数：noteBean[")
					.append(JSONObject.toJSONString(noteBean)).append("]")
					.append(NEWLINE);
			ntService.sendNote(noteBean);
			SystemLog.debug(MOUDLENAME, "sendCarderValidCode", "noteBean["
					+ JSONObject.toJSONString(noteBean) + "]");
			commonCacheService.addSmsCodeCache(mobile, random);
		} catch (Exception e) {
			commonService.handleHsException(MOUDLENAME, methodName,
					msg.toString(), ErrorCodeEnum.SM_SEND_FAILED, e);
		}

	}

	/**
	 * 成员企业、托管企业获取验证码（验证手机的时候）--接口保留
	 * 
	 * @param entCustId
	 * @param mobile
	 * @param custType
	 * @throws HsException
	 */
	public void sendCompanyValidCode(String entCustId, String mobile,
			Integer custType) throws HsException {
		checkValidParams(entCustId, mobile, custType);
		EntExtendInfo entExtendInfo = commonCacheService
				.searchEntExtendInfo(entCustId);
		CustIdGenerator.isEntExtendExist(entExtendInfo, entCustId);
		entExtendInfo.setContactPhone(mobile);
		try {
			String random = CSPRNG.generateRandom(SysConfig.getCheckCodeLen());// 生成随机数
			NoteBean noteBean = buildCompanyVallidMobileBean(entExtendInfo,
					random, custType);
			ntService.sendNote(noteBean);
			commonCacheService.addSmsCodeCache(mobile, random);
			SystemLog.debug(MOUDLENAME, "sendCompanyValidCode", "noteBean["
					+ JSONObject.toJSONString(noteBean) + "]");
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "sendCompanyValidCode",
					"成员企业、托管企业获取验证码（验证手机的时候）异常：\r\n", e);
			throw new HsException(ErrorCodeEnum.SM_SEND_FAILED.getValue(),
					e.getMessage());
		}

	}

	/**
	 * 服务公司获取验证码（验证手机的时候）--接口保留
	 * 
	 * @param entCustId
	 * @param mobile
	 * @param custType
	 * @throws HsException
	 */
	public void sendScsValidCode(String entCustId, String mobile,
			Integer custType) throws HsException {
		checkValidParams(entCustId, mobile, custType);
		EntExtendInfo entExtendInfo = commonCacheService
				.searchEntExtendInfo(entCustId);
		CustIdGenerator.isEntExtendExist(entExtendInfo, entCustId);
		entExtendInfo.setContactPhone(mobile);
		updateMobileInfo(entCustId, mobile, UserTypeEnum.ENT.getType());
		try {
			String random = CSPRNG.generateRandom(SysConfig.getCheckCodeLen());// 生成随机数
			NoteBean noteBean = buildScsValidMobileBean(entExtendInfo, random,
					custType);
			ntService.sendNote(noteBean);
			commonCacheService.addSmsCodeCache(mobile, random);
			SystemLog.debug(MOUDLENAME, "sendScsValidCode", "noteBean["
					+ JSONObject.toJSONString(noteBean) + "]");
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "sendScsValidCode",
					"服务公司获取验证码（验证手机的时候）异常：\r\n", e);
			throw new HsException(ErrorCodeEnum.SM_SEND_FAILED.getValue(),
					e.getMessage());
		}

	}

	/**
	 * 管理公司获取验证码（验证手机的时候）--接口保留
	 * 
	 * @param entCustId
	 * @param mobile
	 * @param custType
	 * @throws HsException
	 */
	public void sendMcsValidCode(String entCustId, String mobile,
			Integer custType) throws HsException {
		checkValidParams(entCustId, mobile, custType);
		EntExtendInfo entExtendInfo = commonCacheService
				.searchEntExtendInfo(entCustId);
		CustIdGenerator.isEntExtendExist(entExtendInfo, entCustId);
		entExtendInfo.setContactPhone(mobile);
		updateMobileInfo(entCustId, mobile, UserTypeEnum.ENT.getType());
		try {
			String random = CSPRNG.generateRandom(SysConfig.getCheckCodeLen());// 生成随机数
			NoteBean noteBean = buildMcsRestPwdMobileBean(entExtendInfo,
					random, custType);
			ntService.sendNote(noteBean);
			commonCacheService.addSmsCodeCache(mobile, random);
			SystemLog.debug(MOUDLENAME, "sendMcsValidCode", "noteBean["
					+ JSONObject.toJSONString(noteBean) + "]");
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "sendMcsValidCode",
					"管理公司获取验证码（验证手机的时候）异常：\r\n", e);
			throw new HsException(ErrorCodeEnum.SM_SEND_FAILED.getValue(),
					e.getMessage());
		}
	}

	/**
	 * 地区平台获取验证码（验证手机的时候）--接口保留
	 * 
	 * @param entCustId
	 * @param mobile
	 * @param custType
	 * @throws HsException
	 */
	public void sendApsValidCode(String entCustId, String mobile,
			Integer custType) throws HsException {
		checkValidParams(entCustId, mobile, custType);
		EntExtendInfo entExtendInfo = commonCacheService
				.searchEntExtendInfo(entCustId);
		CustIdGenerator.isEntExtendExist(entExtendInfo, entCustId);
		entExtendInfo.setContactPhone(mobile);
		updateMobileInfo(entCustId, mobile, UserTypeEnum.ENT.getType());
		try {
			String random = CSPRNG.generateRandom(SysConfig.getCheckCodeLen());// 生成随机数
			NoteBean noteBean = buildApsValidMobileBean(entExtendInfo, random,
					custType);
			ntService.sendNote(noteBean);
			commonCacheService.addSmsCodeCache(mobile, random);
			SystemLog.debug(MOUDLENAME, "sendApsValidCode", "noteBean["
					+ JSONObject.toJSONString(noteBean) + "]");
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "sendApsValidCode",
					"地区平台获取验证码（验证手机的时候）异常：\r\n", e);
			throw new HsException(ErrorCodeEnum.SM_SEND_FAILED.getValue(),
					e.getMessage());
		}

	}

	/**
	 * 非持卡人获取短信验证码（重置登录密码）
	 * 
	 * @param perCustId
	 * @param mobile
	 * @param custType
	 * @throws HsException
	 */
	public void sendNoCarderRestPwdCode(String perCustId, String mobile,
			Integer custType) throws HsException {
		checkValidParams(perCustId, mobile, custType);
		NoteBean noteBean = null;
		String methodName = "sendNoCarderRestPwdCode";
		StringBuffer msg = new StringBuffer();
		try {
			NoCardHolder noCardHolder = commonCacheService
					.searchNoCardHolderInfo(perCustId);
			CustIdGenerator.isNoCarderExist(noCardHolder, perCustId);
			String correctMobile = StringUtils.nullToEmpty(noCardHolder
					.getMobile());
			msg.append("非持卡人获取短信验证码（重置登录密码）异常,输入参数：").append("mobile[")
					.append(mobile).append("],custType[").append(custType)
					.append("]");
			if (!mobile.equals(correctMobile)) {
				commonService.handleHsException(MOUDLENAME, methodName,
						msg.toString(), ErrorCodeEnum.MOBILE_NOT_CORRECT);
			}
			String random = CSPRNG.generateRandom(SysConfig.getCheckCodeLen());// 生成随机数
			noteBean = buildNoCarderRestPwdMobileBean(noCardHolder, random,
					custType);
			msg.append(",调用通知系统参数：noteBean[")
					.append(JSONObject.toJSONString(noteBean)).append("]")
					.append(NEWLINE);
			ntService.sendNote(noteBean);
			SystemLog.debug(MOUDLENAME, "sendNoCarderRestPwdCode", "noteBean["
					+ JSONObject.toJSONString(noteBean) + "]");
			commonCacheService.addSmsCodeCache(mobile, random);
		} catch (Exception e) {
			commonService.handleHsException(MOUDLENAME, methodName,
					msg.toString(), ErrorCodeEnum.SM_SEND_FAILED, e);
		}
	}

	/**
	 * 持卡人获取短信验证码（重置登录密码）
	 * 
	 * @param perCustId
	 * @param mobile
	 * @param custType
	 * @throws HsException
	 */
	public void sendCarderRestPwdCode(String perCustId, String mobile,
			Integer custType) throws HsException {
		checkValidParams(perCustId, mobile, custType);
		NoteBean noteBean = null;
		String methodName = "sendCarderRestPwdCode";
		StringBuffer msg = new StringBuffer();
		CardHolder cardHolder = commonCacheService
				.searchCardHolderInfo(perCustId);
		CustIdGenerator.isCarderExist(cardHolder, perCustId);
		String correctMobile = StringUtils.nullToEmpty(cardHolder
				.getMobile());
		int isAuthMobile = 0;
		if (null != cardHolder.getIsAuthMobile()) {
			isAuthMobile = cardHolder.getIsAuthMobile();
		}
		msg.append("持卡人获取短信验证码（重置登录密码）异常,输入参数：perCustId[")
				.append(perCustId).append("],mobile[").append(mobile)
				.append("],custType[").append(custType)
				.append("],correctMobile[").append(correctMobile)
				.append("],isAuthMobile[" + isAuthMobile + "]");
		if (!mobile.equals(correctMobile)) {
			commonService.handleHsException(MOUDLENAME, methodName,
					msg.toString(), ErrorCodeEnum.MOBILE_NOT_CORRECT);
		}
		if (1 != isAuthMobile) {
			commonService.handleHsException(MOUDLENAME, methodName,
					msg.toString(), ErrorCodeEnum.MOBILE_NOT_CHECK);
		}
		try {
			String random = CSPRNG.generateRandom(SysConfig.getCheckCodeLen());// 生成随机数
			noteBean = buildCarderRestPwdMobileBean(cardHolder, random,
					custType);
			msg.append(",调用通知系统参数：noteBean[")
					.append(JSONObject.toJSONString(noteBean)).append("]")
					.append(NEWLINE);
			ntService.sendNote(noteBean);
			SystemLog.debug(MOUDLENAME, "sendCarderRestPwdCode", "noteBean["
					+ JSONObject.toJSONString(noteBean) + "]");
			commonCacheService.addSmsCodeCache(mobile, random);
		} catch (Exception e) {
			commonService.handleHsException(MOUDLENAME, methodName,
					msg.toString(), ErrorCodeEnum.SM_SEND_FAILED, e);
		}
	}

	/**
	 * 成员企业、托管企业获取短信验证码（重置登录密码）
	 * 
	 * @param entCustId
	 * @param mobile
	 * @param custType
	 * @throws HsException
	 */
	public void sendCompanyRestPwdCode(String operCustId, String mobile,
			Integer custType) throws HsException {
		checkValidParams(operCustId, mobile, custType);
		NoteBean noteBean = null;
		String methodName = "sendCompanyRestPwdCode";
		StringBuffer msg = new StringBuffer();
		Operator operator = commonCacheService
				.searchOperByCustId(operCustId);
		CustIdGenerator.isOperExist(operator, operCustId);
		String entCustId = operator.getEntCustId();
		EntExtendInfo entExtendInfo = commonCacheService
				.searchEntExtendInfo(entCustId);
		CustIdGenerator.isEntExtendExist(entExtendInfo, entCustId);
		String correctMobile = StringUtils.nullToEmpty(entExtendInfo
				.getContactPhone());
		msg.append("成员企业、托管企业获取短信验证码（重置登录密码）异常,输入参数：operCustId[")
				.append(operCustId).append("],mobile[").append(mobile)
				.append("],custType[").append(custType)
				.append("],correctMobile[").append(correctMobile)
				.append("]");
		if (!mobile.equals(correctMobile)) {
			commonService.handleHsException(MOUDLENAME, methodName,
					msg.toString(), ErrorCodeEnum.MOBILE_NOT_CORRECT);
		}
		String random = CSPRNG.generateRandom(SysConfig.getCheckCodeLen());// 生成随机数
		try {
			noteBean = buildCompanyRestPwdMobileBean(entExtendInfo, random,
					custType);
			msg.append(",调用通知系统参数：noteBean[")
					.append(JSONObject.toJSONString(noteBean)).append("]")
					.append(NEWLINE);
			ntService.sendNote(noteBean);
			SystemLog.debug(MOUDLENAME, "sendCompanyRestPwdCode", "noteBean["
					+ JSONObject.toJSONString(noteBean) + "]");
			commonCacheService.addSmsCodeCache(mobile, random);
		} catch (Exception e) {
			commonService.handleHsException(MOUDLENAME, methodName,
					msg.toString(), ErrorCodeEnum.SM_SEND_FAILED, e);
		}
	}

	/**
	 * 服务公司获取短信验证码（重置登录密码）
	 * 
	 * @param entCustId
	 * @param mobile
	 * @param custType
	 * @throws HsException
	 */
	public void sendScsRestPwdCode(String operCustId, String mobile,
			Integer custType) throws HsException {
		checkValidParams(operCustId, mobile, custType);
		NoteBean noteBean = null;
		String methodName = "sendScsRestPwdCode";
		StringBuffer msg = new StringBuffer();
		Operator operator = commonCacheService
				.searchOperByCustId(operCustId);
		CustIdGenerator.isOperExist(operator, operCustId);
		String entCustId = operator.getEntCustId();
		EntExtendInfo entExtendInfo = commonCacheService
				.searchEntExtendInfo(entCustId);
		CustIdGenerator.isEntExtendExist(entExtendInfo, entCustId);
		String correctMobile = StringUtils.nullToEmpty(entExtendInfo
				.getContactPhone());
		msg.append("服务公司获取短信验证码（重置登录密码）异常,输入参数：operCustId[")
				.append(operCustId).append("],mobile[").append(mobile)
				.append("],custType[").append(custType)
				.append("],correctMobile[").append(correctMobile)
				.append("]");
		if (!mobile.equals(correctMobile)) {
			commonService.handleHsException(MOUDLENAME, methodName,
					msg.toString(), ErrorCodeEnum.MOBILE_NOT_CORRECT);
		}
		String random = CSPRNG.generateRandom(SysConfig.getCheckCodeLen());// 生成随机数
		try {
			noteBean = buildScsRestPwdMobileBean(entExtendInfo, random,
					custType);
			msg.append(",调用通知系统参数：noteBean[")
					.append(JSONObject.toJSONString(noteBean)).append("]")
					.append(NEWLINE);
			ntService.sendNote(noteBean);
			SystemLog.debug(MOUDLENAME, "sendScsRestPwdCode", "noteBean["
					+ JSONObject.toJSONString(noteBean) + "]");
			commonCacheService.addSmsCodeCache(mobile, random);
		} catch (Exception e) {
			commonService.handleHsException(MOUDLENAME, methodName,
					msg.toString(), ErrorCodeEnum.SM_SEND_FAILED, e);
		}
	}

	private NoteBean buildScsRestPwdMobileBean(EntExtendInfo entExtendInfo,
			String random, Integer custType) {
		NoteBean noteBean = new NoteBean();
		noteBean.setMsgId(UUID.randomUUID().toString());
		noteBean.setHsResNo(entExtendInfo.getEntResNo());
		noteBean.setCustName(entExtendInfo.getEntCustName());
		noteBean.setMsgReceiver(new String[] { entExtendInfo.getContactPhone() });
		noteBean.setContent(constructScsValidMobileContent(entExtendInfo,
				random));
		noteBean.setCustType(custType);
		noteBean.setBizType(String.valueOf(MsgBizType.MSG_BIZ_TYPE101.getCode()));
		noteBean.setSender(Constants.SENDER_ENT);// 待争议
		int startResType = 0;
		if (null != entExtendInfo.getStartResType()) {
			startResType = entExtendInfo.getStartResType();
		}
		noteBean.setBuyResType(startResType);
		return noteBean;
	}

	/**
	 * 管理公司取短信验证码（重置登录密码）
	 * 
	 * @param entCustId
	 * @param mobile
	 * @param custType
	 * @throws HsException
	 */
	public void sendMcsRestPwdCode(String operCustId, String mobile,
			Integer custType) throws HsException {
		checkValidParams(operCustId, mobile, custType);
		NoteBean noteBean = null;
		String methodName = "sendMcsRestPwdCode";
		StringBuffer msg = new StringBuffer();
		Operator operator = commonCacheService
				.searchOperByCustId(operCustId);
		CustIdGenerator.isOperExist(operator, operCustId);
		String entCustId = operator.getEntCustId();
		EntExtendInfo entExtendInfo = commonCacheService
				.searchEntExtendInfo(entCustId);
		CustIdGenerator.isEntExtendExist(entExtendInfo, entCustId);
		String correctMobile = StringUtils.nullToEmpty(entExtendInfo
				.getContactPhone());
		msg.append("管理公司取短信验证码（重置登录密码）异常,输入参数：operCustId[")
				.append(operCustId).append("],mobile[").append(mobile)
				.append("],custType[").append(custType)
				.append("],correctMobile[").append(correctMobile)
				.append("]");
		if (!mobile.equals(correctMobile)) {
			commonService.handleHsException(MOUDLENAME, methodName,
					msg.toString(), ErrorCodeEnum.MOBILE_NOT_CORRECT);
		}
		String random = CSPRNG.generateRandom(SysConfig.getCheckCodeLen());// 生成随机数
		try {
			noteBean = buildMcsRestPwdMobileBean(entExtendInfo, random,
					custType);
			msg.append(",调用通知系统参数：noteBean[")
					.append(JSONObject.toJSONString(noteBean)).append("]")
					.append(NEWLINE);
			ntService.sendNote(noteBean);
			SystemLog.debug(MOUDLENAME, "sendMcsRestPwdCode", "noteBean["
					+ JSONObject.toJSONString(noteBean) + "]");
			commonCacheService.addSmsCodeCache(mobile, random);
		} catch (Exception e) {
			commonService.handleHsException(MOUDLENAME, methodName,
					msg.toString(), ErrorCodeEnum.SM_SEND_FAILED, e);
		}
	}

	/**
	 * 地区平台取短信验证码（重置登录密码）
	 * 
	 * @param operCustId
	 * @param mobile
	 * @param custType
	 * @throws HsException
	 */
	public void sendApsRestPwdCode(String operCustId, String mobile,
			Integer custType) throws HsException {
		checkValidParams(operCustId, mobile, custType);
		NoteBean noteBean = null;
		String methodName = "sendApsRestPwdCode";
		StringBuffer msg = new StringBuffer();
		Operator operator = commonCacheService
				.searchOperByCustId(operCustId);
		CustIdGenerator.isOperExist(operator, operCustId);
		String entCustId = operator.getEntCustId();
		EntExtendInfo entExtendInfo = commonCacheService
				.searchEntExtendInfo(entCustId);
		CustIdGenerator.isEntExtendExist(entExtendInfo, entCustId);
		String correctMobile = StringUtils.nullToEmpty(entExtendInfo
				.getContactPhone());
		msg.append("地区平台取短信验证码（重置登录密码）（重置登录密码）异常,输入参数：operCustId[")
				.append(operCustId).append("],mobile[").append(mobile)
				.append("],custType[").append(custType)
				.append("],correctMobile[").append(correctMobile)
				.append("]");
		if (!mobile.equals(correctMobile)) {
			commonService.handleHsException(MOUDLENAME, methodName,
					msg.toString(), ErrorCodeEnum.MOBILE_NOT_CORRECT);
		}
		String random = CSPRNG.generateRandom(SysConfig.getCheckCodeLen());// 生成随机数
		try {
			noteBean = buildApsRestPwdMobileBean(entExtendInfo, random,
					custType);
			msg.append(",调用通知系统参数：noteBean[")
					.append(JSONObject.toJSONString(noteBean)).append("]")
					.append(NEWLINE);
			ntService.sendNote(noteBean);
			SystemLog.debug(MOUDLENAME, "sendApsRestPwdCode", "noteBean["
					+ JSONObject.toJSONString(noteBean) + "]");
			commonCacheService.addSmsCodeCache(mobile, random);
		} catch (Exception e) {
			commonService.handleHsException(MOUDLENAME, methodName,
					msg.toString(), ErrorCodeEnum.SM_SEND_FAILED, e);
		}
	}

	private NoteBean buildApsRestPwdMobileBean(EntExtendInfo entExtendInfo,
			String random, Integer custType) {
		NoteBean noteBean = new NoteBean();
		noteBean.setMsgId(UUID.randomUUID().toString());
		noteBean.setHsResNo(entExtendInfo.getEntResNo());
		noteBean.setCustName(entExtendInfo.getEntCustName());
		noteBean.setMsgReceiver(new String[] { entExtendInfo.getContactPhone() });
		noteBean.setContent(constructApsValidMobileContent(entExtendInfo,
				random));
		noteBean.setCustType(custType);
		noteBean.setBizType(String.valueOf(MsgBizType.MSG_BIZ_TYPE101.getCode()));
		noteBean.setSender(Constants.SENDER_ENT);// 待争议
		Integer startResType = 0;
		if (null != entExtendInfo.getStartResType()) {
			startResType = entExtendInfo.getStartResType();
		}
		noteBean.setBuyResType(startResType);
		return noteBean;
	}

	private NoteBean buildApsValidMobileBean(EntExtendInfo entExtendInfo,
			String random, Integer custType) {
		NoteBean noteBean = new NoteBean();
		noteBean.setMsgId(UUID.randomUUID().toString());
		noteBean.setHsResNo(entExtendInfo.getEntResNo());
		noteBean.setCustName(entExtendInfo.getEntCustName());
		noteBean.setMsgReceiver(new String[] { entExtendInfo.getContactPhone() });
		noteBean.setContent(constructApsValidMobileContent(entExtendInfo,
				random));
		noteBean.setCustType(custType);
		noteBean.setBizType(String.valueOf(MsgBizType.MSG_BIZ_TYPE106.getCode()));
		noteBean.setSender(Constants.SENDER_ENT);// 待争议
		return noteBean;
	}

	private void checkValidParams(String custId, String mobile, Integer custType) {
		if (StringUtils.isBlank(custId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"输入参数客户号为空");
		}
		if (StringUtils.isBlank(mobile)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"输入参数手机号码为为空");
		}
		if (null == custType) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"输入参数客户号类型为空");
		}
	}

	public void sendSmsCodeForValidMobile(String custId, String mobile,
			Integer custType) throws HsException {
		if (CustType.NOT_HS_PERSON.getCode() == custType) {
			sendNoCarderValidCode(mobile, custType);
		} else if (CustType.PERSON.getCode() == custType) {
			sendCarderValidCode(custId, mobile, custType);
		} else if (CustType.MEMBER_ENT.getCode() == custType) {
			sendCompanyValidCode(custId, mobile, custType);
		} else if (CustType.TRUSTEESHIP_ENT.getCode() == custType) {
			sendCompanyValidCode(custId, mobile, custType);
		} else if (CustType.SERVICE_CORP.getCode() == custType) {
			sendScsValidCode(custId, mobile, custType);
		} else if (CustType.MANAGE_CORP.getCode() == custType) {
			sendMcsValidCode(custId, mobile, custType);
		} else if (CustType.AREA_PLAT.getCode() == custType) {
			sendApsValidCode(custId, mobile, custType);// 保留
		} else {
			throw new HsException(ErrorCodeEnum.PARAM_IS_ILLEGAL.getValue(),
					"客户类型非法,custType[" + custType + "]");
		}
	}

	public void sendSmsCodeForResetPwd(String custId, String userType,
			String mobile, Integer custType) throws HsException {
		if (CustType.NOT_HS_PERSON.getCode() == custType) {
			sendNoCarderRestPwdCode(custId, mobile, custType);
		} else if (CustType.PERSON.getCode() == custType) {
			sendCarderRestPwdCode(custId, mobile, custType);
		} else if (CustType.MEMBER_ENT.getCode() == custType) {
			sendCompanyRestPwdCode(custId, mobile, custType);
		} else if (CustType.TRUSTEESHIP_ENT.getCode() == custType) {
			sendCompanyRestPwdCode(custId, mobile, custType);
		} else if (CustType.SERVICE_CORP.getCode() == custType) {
			sendScsRestPwdCode(custId, mobile, custType);
		} else if (CustType.MANAGE_CORP.getCode() == custType) {
			sendMcsRestPwdCode(custId, mobile, custType);
		} else if (CustType.AREA_PLAT.getCode() == custType) {
			sendApsRestPwdCode(custId, mobile, custType);
		} else {
			throw new HsException(ErrorCodeEnum.PARAM_IS_ILLEGAL.getValue(),
					"客户类型非法,custType[" + custType + "]");
		}
	}
	
	/**
	 * 
	 * @param entResNo 企业互生号
	 * @param operNo 操作员登陆名
	 * @param perResNo 个人互生号
	 * @param adminOperCustId 管理员客户id
	 * @throws HsException
	 */
	public void sendHsCardBindMsg(String entResNo,String operNo,String perResNo,String adminOperCustId)  throws HsException{
		SelfDynamicBizMsgBean bean  = new SelfDynamicBizMsgBean();
		String msgId=RandomGuidAgent.getStringGuid("");
		String methodName = "sendHsCardBindMsg";
		StringBuffer msg = new StringBuffer();
		msg.append("操作员绑定互生卡发送消息异常,输入参数：entResNo[")
		.append(entResNo).append("],operNo[").append(operNo)
		.append("],perResNo[").append(perResNo)
		.append("],adminOperCustId[").append(adminOperCustId)
		.append("]");
		String perCustId = commonCacheService.findCustIdByResNo(perResNo);
		CustIdGenerator.isCarderExist(perCustId, perResNo);
		bean.setMsgId(msgId);
		bean.setHsResNo(entResNo);
		bean.setCustName(operNo);
		bean.setMsgCode("101");
		bean.setSubMsgCode("1010201");
		bean.setMsgTitle("【互生卡绑定确定】");
		bean.setMsgReceiver(new String[]{perCustId});
		bean.setSender(adminOperCustId);
		try {
			JSONObject jo = new JSONObject();
			jo.put("resNo", perResNo);
			jo.put("name", operNo);
			jo.put("entResNo", entResNo);
			bean.setMsgContent(jo.toJSONString());
			msg.append(",调用通知系统参数：bean[")
			.append(JSONObject.toJSONString(bean)).append("]")
			.append(NEWLINE);
			ntService.sendSelfDynamicBiz(bean);
			SystemLog.debug(MOUDLENAME, methodName, "bean["
					+ JSONObject.toJSONString(bean) + "]");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new HsException(ErrorCodeEnum.HSCARD_BIND_FAIL.getValue(),perResNo+ErrorCodeEnum.HSCARD_BIND_FAIL.getDesc());
		}
	}

	private Map<String, String> constructCompanyValidMobileContent(
			EntExtendInfo entExtendInfo, String random) {
		Map<String, String> mobileContent = new HashMap<String, String>();
		// 验证码
		mobileContent.put("{0}", random);
		// {2}--失效时间
		mobileContent
				.put("{1}", String.valueOf(SysConfig.getSmCodeValidTime()));
		return mobileContent;
	}

	private Map<String, String> constructScsValidMobileContent(
			EntExtendInfo entExtendInfo, String random) {
		Map<String, String> mobileContent = new HashMap<String, String>();
		// 验证码
		mobileContent.put("{0}", random);
		// {2}--失效时间
		mobileContent
				.put("{1}", String.valueOf(SysConfig.getSmCodeValidTime()));
		return mobileContent;
	}

	private Map<String, String> constructApsValidMobileContent(
			EntExtendInfo entExtendInfo, String random) {
		Map<String, String> mobileContent = new HashMap<String, String>();
		// 验证码
		mobileContent.put("{0}", random);
		// {2}--失效时间
		mobileContent
				.put("{1}", String.valueOf(SysConfig.getSmCodeValidTime()));
		return mobileContent;
	}

	private Map<String, String> constructNoCarderRestPwdMobileContent(
			NoCardHolder noCardHolder, String random) {
		Map<String, String> mobileContent = new HashMap<String, String>();
		// {0}--验证码
		mobileContent.put("{0}", random);
		// {1}--失效时间
		mobileContent
				.put("{1}", String.valueOf(SysConfig.getSmCodeValidTime()));
		return mobileContent;
	}

	private Map<String, String> constructNoCarderValidMobileContent(
			NoCardHolder noCardHolder, String random) {
		Map<String, String> mobileContent = new HashMap<String, String>();
		// 验证码
		mobileContent.put("{0}", random);
		// {2}--失效时间
		mobileContent
				.put("{1}", String.valueOf(SysConfig.getSmCodeValidTime()));
		return mobileContent;
	}

	private Map<String, String> constructCarderRestPwdMobileContent(
			CardHolder cardHolder, String random) {
		Map<String, String> mobileContent = new HashMap<String, String>();
		// {0}--验证码
		mobileContent.put("{0}", random);
		// {1}--失效时间
		mobileContent
				.put("{1}", String.valueOf(SysConfig.getSmCodeValidTime()));
		return mobileContent;
	}

	private Map<String, String> constructCarderValidMobileContent(
			CardHolder cardHolder, String random) {
		Map<String, String> mobileContent = new HashMap<String, String>();
		// {0}--验证码
		mobileContent.put("{0}", random);
		// {1}--失效时间
		mobileContent
				.put("{1}", String.valueOf(SysConfig.getSmCodeValidTime()));
		return mobileContent;
	}

	private Map<String, String> constructCarderRestPwdMobileContentByChecker(
			String clearLogPwd) {
		Map<String, String> mobileContent = new HashMap<String, String>();
		// {0}--明文密码
		mobileContent.put("{0}", clearLogPwd);
		return mobileContent;
	}

	private Map<String, String> constructEntRestTranPwdMobileConten(
			EntExtendInfo entExtendInfo, String authCode) {
		String start = DateUtil.DateTimeToString(new Date());
		String end = DateUtil.DateTimeToString(DateUtil.getAfterDay(new Date(),
				1));
		Map<String, String> mobileContent = new HashMap<String, String>();
		// {0}--企业名称
		mobileContent.put("{0}", entExtendInfo.getEntCustName());
		// {1}--授权码
		mobileContent.put("{1}", authCode);
		// {2}--起始有效期
		mobileContent.put("{2}", start);
		// {3}--终止有效期
		mobileContent.put("{3}", end);
		return mobileContent;
	}

	private Map<String, String> constructOperRestPwdMobileContentByChecker(
			String clearLogPwd) {
		Map<String, String> mobileContent = new HashMap<String, String>();
		// {0}--明文密码
		mobileContent.put("{0}", clearLogPwd);
		return mobileContent;
	}

	private Map<String, String> constructInitOperPwdMobileContent(
			EntExtendInfo entExtendInfo, Operator adminOper, String cleartextPwd) {
		Map<String, String> mobileContent = new HashMap<String, String>();
		// {0}--企业名称或消费者姓名
		mobileContent.put("{0}", entExtendInfo.getEntResNo());
		// {1}--账号
		mobileContent.put("{1}", adminOper.getOperNo());
		// {2}--初始密码
		mobileContent.put("{2}", cleartextPwd);
		return mobileContent;
	}

	private NoteBean buildNoCarderRestPwdMobileBean(NoCardHolder noCardHolder,
			String random, Integer custType) {
		String custName = getNoCarderCustId(noCardHolder.getPerCustId());
		NoteBean noteBean = new NoteBean();
		noteBean.setMsgId(UUID.randomUUID().toString());
		noteBean.setHsResNo(noCardHolder.getMobile());
		noteBean.setCustName(custName);
		noteBean.setMsgReceiver(new String[] { noCardHolder.getMobile() });
		noteBean.setContent(constructNoCarderRestPwdMobileContent(noCardHolder,
				random));
		noteBean.setCustType(custType);
		noteBean.setBizType(String.valueOf(MsgBizType.MSG_BIZ_TYPE101.getCode()));
		noteBean.setSender(Constants.SENDER_CONSUMER);// 待争议
		return noteBean;
	}

	private String getNoCarderCustId(String perCustId){
		AsNetworkInfo info = networkInfoService.searchByCustId(perCustId);
		String custName = "";
		if(null != info){
			custName = StringUtils.nullToEmpty(info.getName());
		}
		return custName;
	}
	private NoteBean buildNoCarderValidMobileBean(NoCardHolder noCardHolder,
			String random, Integer custType) {
		String custName = noCardHolder.getMobile();
		NoteBean noteBean = new NoteBean();
		noteBean.setMsgId(UUID.randomUUID().toString());
		noteBean.setHsResNo(noCardHolder.getMobile());
		noteBean.setCustName(custName);
		noteBean.setMsgReceiver(new String[] { noCardHolder.getMobile() });
		noteBean.setContent(constructNoCarderValidMobileContent(noCardHolder,
				random));
		noteBean.setCustType(custType);
		noteBean.setBizType(String.valueOf(MsgBizType.MSG_BIZ_TYPE101.getCode()));
		noteBean.setSender(Constants.SENDER_CONSUMER);// 待争议
		return noteBean;
	}

	private NoteBean buildEntRestTranPwdMobileBean(EntExtendInfo entExtendInfo,
			String authCode, Integer custType) {
		NoteBean noteBean = new NoteBean();
		noteBean.setMsgId(UUID.randomUUID().toString());
		noteBean.setHsResNo(entExtendInfo.getEntResNo());
		noteBean.setCustName(entExtendInfo.getEntCustName());
		noteBean.setMsgReceiver(new String[] { entExtendInfo.getContactPhone() });
		noteBean.setContent(constructEntRestTranPwdMobileConten(entExtendInfo,
				authCode));
		noteBean.setCustType(entExtendInfo.getCustType());
		noteBean.setBizType(String.valueOf(MsgBizType.MSG_BIZ_TYPE118.getCode()));
		int startResType = 0;
		if (null != entExtendInfo.getStartResType()) {
			startResType = entExtendInfo.getStartResType();
		}
		noteBean.setBuyResType(startResType);
		noteBean.setSender(Constants.SENDER_ENT);
		return noteBean;
	}

	private String getCarderCustId(String perCustId){
		String custName = "";
		RealNameAuth realNameAuth = commonCacheService.searchRealNameAuthInfo(perCustId);
		if(null != realNameAuth){
			custName = StringUtils.nullToEmpty(realNameAuth.getPerName());
		}
		return custName;
	}
	private NoteBean buildCarderRestPwdMobileBean(CardHolder cardHolder,
			String random, Integer custType) {
		String custName = getCarderCustId(cardHolder.getPerCustId());
		NoteBean noteBean = new NoteBean();
		noteBean.setMsgId(UUID.randomUUID().toString());
		noteBean.setHsResNo(cardHolder.getPerResNo());
		noteBean.setCustName(custName);
		noteBean.setMsgReceiver(new String[] { cardHolder.getMobile() });
		noteBean.setContent(constructCarderRestPwdMobileContent(cardHolder,
				random));
		noteBean.setCustType(custType);
		noteBean.setBizType(String.valueOf(MsgBizType.MSG_BIZ_TYPE101.getCode()));
		noteBean.setSender(Constants.SENDER_CONSUMER);// 待争议
		return noteBean;
	}

	private NoteBean buildCarderValidMobileBean(CardHolder cardHolder,
			String random, Integer custType) {
		String custName = getCarderCustId(cardHolder.getPerCustId());
		NoteBean noteBean = new NoteBean();
		noteBean.setMsgId(UUID.randomUUID().toString());
		noteBean.setHsResNo(cardHolder.getPerResNo());
		noteBean.setCustName(custName);
		noteBean.setMsgReceiver(new String[] { cardHolder.getMobile() });
		noteBean.setContent(constructCarderValidMobileContent(cardHolder,
				random));
		noteBean.setCustType(custType);
		noteBean.setBizType(String.valueOf(MsgBizType.MSG_BIZ_TYPE101.getCode()));
		noteBean.setSender(Constants.SENDER_CONSUMER);// 待争议
		return noteBean;
	}

	private NoteBean buildCarderRestPwdMobileBeanByReChecker(
			CardHolder cardHolder, String clearLogPwd, Integer custType) {
		String custName = getCarderCustId(cardHolder.getPerCustId());
		NoteBean noteBean = new NoteBean();
		noteBean.setMsgId(UUID.randomUUID().toString());
		noteBean.setHsResNo(cardHolder.getPerResNo());
		noteBean.setCustName(custName);
		noteBean.setMsgReceiver(new String[] { cardHolder.getMobile() });
		noteBean.setContent(constructCarderRestPwdMobileContentByChecker(clearLogPwd));
		noteBean.setCustType(custType);
		noteBean.setSender(Constants.SENDER_CONSUMER);// 待争议
		noteBean.setBizType(String.valueOf(MsgBizType.MSG_BIZ_TYPE106.getCode()));
		return noteBean;
	}

	private NoteBean buildOperRestPwdMobileBeanByReChecker(
			EntExtendInfo entExtendInfo, String clearLogPwd) {
		NoteBean noteBean = new NoteBean();
		noteBean.setMsgId(UUID.randomUUID().toString());
		noteBean.setHsResNo(entExtendInfo.getEntResNo());
		noteBean.setCustName(entExtendInfo.getEntCustName());
		noteBean.setMsgReceiver(new String[] { entExtendInfo.getContactPhone() });
		noteBean.setContent(constructOperRestPwdMobileContentByChecker(clearLogPwd));
		noteBean.setCustType(entExtendInfo.getCustType());
		noteBean.setBizType(String.valueOf(MsgBizType.MSG_BIZ_TYPE106.getCode()));
		int startResType = 0;
		if (null != entExtendInfo.getStartResType()) {
			startResType = entExtendInfo.getStartResType();
		}
		noteBean.setBuyResType(startResType);
		noteBean.setSender(Constants.SENDER_ENT);// 待争议
		return noteBean;
	}

	private NoteBean buildInitOperPwdMobileBean(EntExtendInfo entExtendInfo,
			Operator adminOper, String clearLogPwd) {
		NoteBean noteBean = new NoteBean();
		noteBean.setMsgId(UUID.randomUUID().toString());
		noteBean.setHsResNo(entExtendInfo.getEntResNo());
		noteBean.setCustName(entExtendInfo.getEntCustName());
		noteBean.setMsgReceiver(new String[] { entExtendInfo.getContactPhone() });
		noteBean.setContent(constructInitOperPwdMobileContent(entExtendInfo,
				adminOper, clearLogPwd));
		noteBean.setCustType(entExtendInfo.getCustType());
		noteBean.setBizType(String.valueOf(MsgBizType.MSG_BIZ_TYPE102.getCode()));
		noteBean.setSender(Constants.SENDER_ENT);// 待争议
		int startResType = 0;
		if (null != entExtendInfo.getStartResType()) {
			startResType = entExtendInfo.getStartResType();
		}
		noteBean.setBuyResType(startResType);
		return noteBean;
	}

	private NoteBean buildCarderRestTranPwdMobileBeanByReChecker(
			CardHolder cardHolder, String clearTranPwd, Integer custType) {
		String custName = getCarderCustId(cardHolder.getPerCustId());
		NoteBean noteBean = new NoteBean();
		noteBean.setMsgId(UUID.randomUUID().toString());
		noteBean.setHsResNo(cardHolder.getPerResNo());
		noteBean.setCustName(custName);
		noteBean.setMsgReceiver(new String[] { cardHolder.getMobile() });
		noteBean.setContent(constructCarderRestPwdMobileContentByChecker(clearTranPwd));
		noteBean.setCustType(custType);
		noteBean.setBizType(String.valueOf(MsgBizType.MSG_BIZ_TYPE107.getCode()));
		noteBean.setSender(Constants.SENDER_CONSUMER);// 待争议
		return noteBean;
	}

	private NoteBean buildEntRestTranPwdMobileBeanByReChecker(
			EntExtendInfo entExtendInfo, String clearTranPwd) {
		NoteBean noteBean = new NoteBean();
		noteBean.setMsgId(UUID.randomUUID().toString());
		noteBean.setHsResNo(entExtendInfo.getEntResNo());
		noteBean.setCustName(entExtendInfo.getEntCustName());
		noteBean.setMsgReceiver(new String[] { entExtendInfo.getContactPhone() });
		noteBean.setContent(constructCarderRestPwdMobileContentByChecker(clearTranPwd));
		noteBean.setCustType(entExtendInfo.getCustType());
		noteBean.setBizType(String.valueOf(MsgBizType.MSG_BIZ_TYPE107.getCode()));
		int startResType = 0;
		if (null != entExtendInfo.getStartResType()) {
			startResType = entExtendInfo.getStartResType();
		}
		noteBean.setBuyResType(startResType);
		noteBean.setSender(Constants.SENDER_ENT);// 待争议
		return noteBean;
	}

	private NoteBean buildCompanyRestPwdMobileBean(EntExtendInfo entExtendInfo,
			String random, Integer custType) {
		NoteBean noteBean = new NoteBean();
		noteBean.setMsgId(UUID.randomUUID().toString());
		noteBean.setHsResNo(entExtendInfo.getEntResNo());
		noteBean.setCustName(entExtendInfo.getEntCustName());
		noteBean.setMsgReceiver(new String[] { entExtendInfo.getContactPhone() });
		noteBean.setContent(constructCompanyValidMobileContent(entExtendInfo,
				random));
		noteBean.setCustType(custType);
		noteBean.setBizType(String.valueOf(MsgBizType.MSG_BIZ_TYPE101.getCode()));
		noteBean.setSender(Constants.SENDER_ENT);// 待争议
		int startResType = 0;
		if (null != entExtendInfo.getStartResType()) {
			startResType = entExtendInfo.getStartResType();
		}
		noteBean.setBuyResType(startResType);
		return noteBean;
	}

	private NoteBean buildCompanyVallidMobileBean(EntExtendInfo entExtendInfo,
			String random, Integer custType) {
		NoteBean noteBean = new NoteBean();
		noteBean.setMsgId(UUID.randomUUID().toString());
		noteBean.setHsResNo(entExtendInfo.getEntResNo());
		noteBean.setCustName(entExtendInfo.getEntCustName());
		noteBean.setMsgReceiver(new String[] { entExtendInfo.getContactPhone() });
		noteBean.setContent(constructCompanyValidMobileContent(entExtendInfo,
				random));
		noteBean.setCustType(custType);
		noteBean.setBizType(String.valueOf(MsgBizType.MSG_BIZ_TYPE106.getCode()));
		noteBean.setSender(Constants.SENDER_ENT);// 待争议
		return noteBean;
	}

	private NoteBean buildScsValidMobileBean(EntExtendInfo entExtendInfo,
			String random, Integer custType) {
		NoteBean noteBean = new NoteBean();
		noteBean.setMsgId(UUID.randomUUID().toString());
		noteBean.setHsResNo(entExtendInfo.getEntResNo());
		noteBean.setCustName(entExtendInfo.getEntCustName());
		noteBean.setMsgReceiver(new String[] { entExtendInfo.getContactPhone() });
		noteBean.setContent(constructScsValidMobileContent(entExtendInfo,
				random));
		noteBean.setCustType(custType);
		noteBean.setBizType(String.valueOf(MsgBizType.MSG_BIZ_TYPE106.getCode()));
		noteBean.setSender(Constants.SENDER_ENT);// 待争议
		return noteBean;
	}

	private NoteBean buildMcsRestPwdMobileBean(EntExtendInfo entExtendInfo,
			String random, Integer custType) {
		NoteBean noteBean = new NoteBean();
		noteBean.setMsgId(UUID.randomUUID().toString());
		noteBean.setHsResNo(entExtendInfo.getEntResNo());
		noteBean.setCustName(entExtendInfo.getEntCustName());
		noteBean.setMsgReceiver(new String[] { entExtendInfo.getContactPhone() });
		noteBean.setContent(constructMcsValidMobileContent(entExtendInfo,
				random));
		noteBean.setCustType(custType);
		noteBean.setBizType(String.valueOf(MsgBizType.MSG_BIZ_TYPE101.getCode()));
		noteBean.setSender(Constants.SENDER_ENT);// 待争议
		int startResType = 0;
		if (null != entExtendInfo.getStartResType()) {
			startResType = entExtendInfo.getStartResType();
		}
		noteBean.setBuyResType(startResType);
		return noteBean;
	}

	private Map<String, String> constructMcsValidMobileContent(
			EntExtendInfo entExtendInfo, String random) {
		Map<String, String> mobileContent = new HashMap<String, String>();
		// 验证码
		mobileContent.put("{0}", random);
		// {2}--失效时间
		mobileContent
				.put("{1}", String.valueOf(SysConfig.getSmCodeValidTime()));
		return mobileContent;
	}
}
