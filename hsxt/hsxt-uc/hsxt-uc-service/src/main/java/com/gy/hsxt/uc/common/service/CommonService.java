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

package com.gy.hsxt.uc.common.service;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsec.external.api.EnterpriceService;
import com.gy.hsec.external.bean.ReturnResult;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsi.nt.api.exception.NoticeException;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.bean.consumer.AsCardHolder;
import com.gy.hsxt.uc.as.bean.consumer.AsNoCardHolder;
import com.gy.hsxt.uc.as.bean.ent.AsEntBaseInfo;
import com.gy.hsxt.uc.as.bean.enumerate.ChannelTypeEnum;
import com.gy.hsxt.uc.as.bean.operator.AsOperator;
import com.gy.hsxt.uc.cache.service.CommonCacheService;
import com.gy.hsxt.uc.common.SysConfig;
import com.gy.hsxt.uc.common.bean.CustPrivacy;
import com.gy.hsxt.uc.common.bean.NetworkInfo;
import com.gy.hsxt.uc.common.bean.UserInfo;
import com.gy.hsxt.uc.common.mapper.CustPrivacyMapper;
import com.gy.hsxt.uc.consumer.bean.CardHolder;
import com.gy.hsxt.uc.consumer.bean.HsCard;
import com.gy.hsxt.uc.consumer.bean.NoCardHolder;
import com.gy.hsxt.uc.consumer.bean.RealNameAuth;
import com.gy.hsxt.uc.consumer.mapper.NoCardHolderMapper;
import com.gy.hsxt.uc.device.bean.CardReaderDevice;
import com.gy.hsxt.uc.device.bean.PosDevice;
import com.gy.hsxt.uc.device.mapper.CardReaderDeviceMapper;
import com.gy.hsxt.uc.device.mapper.PosDeviceMapper;
import com.gy.hsxt.uc.ent.bean.EntExtendInfo;
import com.gy.hsxt.uc.ent.bean.EntStatusInfo;
import com.gy.hsxt.uc.ent.mapper.EntStatusInfoMapper;
import com.gy.hsxt.uc.operator.bean.Operator;
import com.gy.hsxt.uc.operator.mapper.OperatorMapper;
import com.gy.hsxt.uc.password.PasswordService;
import com.gy.hsxt.uc.password.bean.PasswordBean;
import com.gy.hsxt.uc.search.api.IUCUserInfoService;
import com.gy.hsxt.uc.search.bean.SearchUserInfo;
import com.gy.hsxt.uc.sysoper.bean.SysOperator;
import com.gy.hsxt.uc.sysoper.serivce.UCAsSysOperInfoService;
import com.gy.hsxt.uc.util.CustIdGenerator;
import com.gy.hsxt.uc.util.StringEncrypt;

/**
 * 
 * @Package: com.gy.hsxt.uc.common.service
 * @ClassName: CommonService
 * @Description: TODO
 * 
 * @author: lvyan
 * @date: 2015-12-21 上午11:24:27
 * @version V1.0
 */
@Service
public class CommonService {
	private final static String MOUDLENAME = "com.gy.hsxt.uc.common.service.CommonService";
	private final static String NEWLINE = "\r\n";
	@Resource
	CommonCacheService commonCacheService;

	// 通知系统接口
	// @Autowired
	// INtService ntService;
	// 电商接口
	@Autowired
	EnterpriceService enterpriceService;

	@Autowired
	NoCardHolderMapper noCardHolderMapper;
	@Autowired
	private OperatorMapper operatorMapper;
	@Autowired
	EntStatusInfoMapper entStatusInfoMapper;
	@Autowired
	PosDeviceMapper posDeviceMapper;
	@Autowired
	CardReaderDeviceMapper cardReaderDeviceMapper;

	@Autowired
	PasswordService passwordService;

	@Autowired
	// 搜索引擎接口
	IUCUserInfoService searchUserService;
	@Autowired
	CustPrivacyMapper custPrivacyMapper;

	/**
	 * 查询持卡人的客户号
	 * 
	 * @param resNo
	 *            持卡人互生号
	 * @return 持卡人客户号
	 * @throws HsException
	 */
	public String findCustIdByResNo(String resNo) throws HsException {
		// todo
		throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
				"代码逻辑未实现，请联系开发攻城狮");
		// return null;
	}

	/**
	 * 根据客户号查询持卡人的信息
	 * 
	 * @param custId
	 *            客户号
	 * @return CardHolder(持卡人信息)
	 */
	public CardHolder searchCardHolderInfoByCustId(String custId)
			throws HsException {
		// todo
		throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
				"代码逻辑未实现，请联系开发攻城狮");
	}

	/**
	 * 通过客户号查询互生卡信息
	 * 
	 * @param custId
	 *            持卡人客户号
	 * @return 互生卡信息
	 */
	public HsCard searchHsCardInfoByCustId(String custId) throws HsException {
		// todo

		throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
				"代码逻辑未实现，请联系开发攻城狮");
	}

	/**
	 * 
	 * @param custId
	 *            持卡人客户号
	 * @return 实名认证信息
	 * @throws HsException
	 */
	public RealNameAuth searchRealNameAuthInfo(String custId)
			throws HsException {
		// todo
		throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
				"代码逻辑未实现，请联系开发攻城狮");
	}

	/**
	 * 查询企业客户号 ，通过企业互生号
	 * 
	 * @param entResNo
	 *            企业互生号 ，必传
	 * @return
	 * @throws HsException
	 */
	public String findEntCustIdByEntResNo(String entResNo) throws HsException {
		// todo
		throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
				"代码逻辑未实现，请联系开发攻城狮");
	}

	/**
	 * 判断用户是否拥有对应url权限
	 * 
	 * @param custId
	 * @param url
	 * @return
	 */
	public boolean hashUrlPermission(String custId, String url) {
		return commonCacheService.hashUrlPermission(custId, url);
	}

	/**
	 * 计算解锁的倒计时时间，单位：秒
	 * 
	 * @param time
	 *            解锁时间，格式：hh:mm:ss
	 * @return
	 */
	public long computeUnlockSecond(String time) {
		return commonCacheService.computeUnlockSecond(time);
	}

	/**
	 * 判断用户是否已锁定
	 * 
	 * @param custId
	 *            客户号
	 * @param userType
	 *            用户类型 2：持卡人 3：操作员
	 */
	public void isCyptLockAccount(String custId, String userType)
			throws HsException {
		int pwdQuestionFailTimes = 0;
		if (UserTypeEnum.CARDER.getType().equals(userType)) {
			CardHolder cardHolder = commonCacheService
					.searchCardHolderInfo(custId);
			CustIdGenerator.isCarderExist(cardHolder, custId);
			pwdQuestionFailTimes = commonCacheService
					.getCarderPwdQuestionFailTimesCache(cardHolder
							.getPerResNo());
		} else if (UserTypeEnum.ENT.getType().equals(userType)
				|| UserTypeEnum.OPERATOR.getType().equals(userType)) {
			Operator operator = commonCacheService.searchOperByCustId(custId);
			CustIdGenerator.isOperExist(operator, custId);
			pwdQuestionFailTimes = commonCacheService
					.getEntPwdQuestionFailTimesCache(operator.getEntResNo());
		} else if (UserTypeEnum.NO_CARDER.getType().equals(userType)) {
			NoCardHolder noCardHolder = commonCacheService
					.searchNoCardHolderInfo(custId);
			CustIdGenerator.isNoCarderExist(noCardHolder, custId);
			pwdQuestionFailTimes = commonCacheService
					.getNoCardPwdQuestionFailTimesCache(noCardHolder
							.getMobile());
		} else {
			throw new HsException(ErrorCodeEnum.USERTYPE_IS_ILLEGAL.getValue(),
					ErrorCodeEnum.USERTYPE_IS_ILLEGAL.getDesc());
		}
		// 验证密码失败次数
		if (pwdQuestionFailTimes >= SysConfig.getPwdQuestionFailTimes()) {
			throw new HsException(ErrorCodeEnum.USER_LOCKED.getValue(),
					"密保匹配失败次数超过" + SysConfig.getPwdQuestionFailTimes()
							+ "次，用户已锁定，请于"
							+ SysConfig.getPwdQuestionFailUnlockTime()
							+ "后再次尝试");
		}
	}

	/**
	 * 当新增操作员不存在有效记录时， 检查新操作员客户号是否存在，如果已经存在，操作员客户号后7位取时间戳
	 * 
	 * @param entResNo
	 * @param userName
	 * @return
	 */
	public String checkAndGetOperCustId(String entResNo, String userName)
			throws HsException {
		// 新增操作员客户号
		String operCustId = entResNo + userName + "0000";
		int count = 0;
		try {
			count = operatorMapper.countOperCustId(operCustId);
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "checkAndGetOperCustId",
					"根据客户号查询操作员数量异常：\r\n", e);
		}
		if (count == 0) {
			return operCustId;
		}
		int tempInt = 0;
		String maxSeqNo = null;
		try {
			maxSeqNo = operatorMapper.selectMaxSeqNo(entResNo, userName);
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "checkAndGetOperCustId",
					"checkAndGetOperCustId 异常：\r\n", e);
		}
		tempInt = Integer.valueOf(maxSeqNo);
		tempInt++;
		String seqNo = buildSeqNo(tempInt);
		operCustId = entResNo + userName + seqNo;
		operCustId = getNoRepeatOperCustId(operCustId);
		return operCustId;

	}

	/**
	 * 获取无重复的客户号
	 * 
	 * @param operCustId
	 * @return
	 */
	private String getNoRepeatOperCustId(String operCustId) {
		String resultOperCustId = operCustId;
		int count = 0;
		try {
			count = operatorMapper.countOperCustId(resultOperCustId);
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "checkAndGetOperCustId",
					"根据客户号查询操作员数量异常：\r\n", e);
		}
		try {
			if (count > 0) {
				resultOperCustId = String.valueOf(Integer
						.parseInt(resultOperCustId) + 1);
				getNoRepeatOperCustId(resultOperCustId);
			}
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "checkAndGetOperCustId",
					"客户号中含有字符：\r\n", e);
		}
		return resultOperCustId;
	}

	private static String buildSeqNo(int tempInt) throws HsException {
		String seqNo = "" + tempInt;
		int length = seqNo.length();
		if (length > 4) {
			throw new HsException(
					ErrorCodeEnum.OPERRATOR_COUNT_NOT_OVER_TEN_THOUSAND
							.getValue(),
					ErrorCodeEnum.OPERRATOR_COUNT_NOT_OVER_TEN_THOUSAND
							.getDesc());
		}
		if (length == 4) {
			return seqNo;
		}
		if (length < 4) {
			seqNo = String.format("%04d", tempInt);
		}
		return seqNo;
	}

	public String convertUtf8(String str) {
		String temp = "";
		try {
			temp = new String(str.getBytes("ISO-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new HsException(
					ErrorCodeEnum.MAILTITLE_CONVERT_ERROR.getValue(),
					e.getMessage());
		}
		return temp;
	}

	public void checkIsAuthEmail(String custId, String userType, String email)
			throws HsException {
		String methodName = "checkIsAuthEmail";
		Integer isAuthEmail = null;
		String emailInfo = "";
		StringBuffer msg = new StringBuffer();
		msg.append("custId[").append(custId).append("],userType[")
				.append(userType).append("],email[").append(email).append("]");
		if (UserTypeEnum.NO_CARDER.getType().equals(userType)) {
			NoCardHolder noCardHolder = commonCacheService
					.searchNoCardHolderInfo(custId);
			isAuthEmail = noCardHolder.getIsAuthEmail();
			emailInfo = StringUtils.nullToEmpty(noCardHolder.getEmail());
		} else if (UserTypeEnum.CARDER.getType().equals(userType)) {
			CardHolder cardHolder = commonCacheService
					.searchCardHolderInfo(custId);
			isAuthEmail = cardHolder.getIsAuthEmail();
			emailInfo = StringUtils.nullToEmpty(cardHolder.getEmail());
		} else if (UserTypeEnum.ENT.getType().equals(userType)) {
			EntStatusInfo entStatusInfo = commonCacheService
					.searchEntStatusInfo(custId);
			isAuthEmail = entStatusInfo.getIsAuthEmail();
			EntExtendInfo entExtendInfo = commonCacheService
					.searchEntExtendInfo(custId);
			emailInfo = StringUtils
					.nullToEmpty(entExtendInfo.getContactEmail());
		} else {
			throw new HsException(ErrorCodeEnum.PARAM_IS_ILLEGAL.getValue(),
					"传入参数用户类型非法");
		}
		if (null != isAuthEmail && 1 == isAuthEmail && emailInfo.equals(email)) {
			handleHsException(MOUDLENAME, methodName, msg.toString(),
					ErrorCodeEnum.EMAIL_BIND_FAIL);
		}
	}

	public void checkCarderIsAuthMobile(String custId, String mobile)
			throws HsException {
		String methodName = "checkIsAuthMobile";
		StringBuffer msg = new StringBuffer();
		msg.append("custId[").append(custId).append("],mobile[").append(mobile)
				.append("]");
		CardHolder cardHolder = commonCacheService.searchCardHolderInfo(custId);
		Integer isAuthMobile = cardHolder.getIsAuthMobile();
		String conrrectMobile = StringUtils.nullToEmpty(cardHolder.getMobile());
		if (null != isAuthMobile && 1 == isAuthMobile
				&& conrrectMobile.equals(mobile)) {
			handleHsException(MOUDLENAME, methodName, msg.toString(),
					ErrorCodeEnum.MOBILE_BIND_FAIL);
		}
	}

	public void checkNoCarderIsAuthMobile(String custId, String mobile)
			throws HsException {
		// 先检查是否是重复绑定手机
		String methodName = "checkNoCarderIsAuthMobile";
		StringBuffer msg = new StringBuffer();
		msg.append("custId[").append(custId).append("],mobile[").append(mobile)
				.append("]");
		NoCardHolder noCardHolder = commonCacheService
				.searchNoCardHolderInfo(custId);
		String conrrectMobile = StringUtils.nullToEmpty(noCardHolder
				.getMobile());
		if (conrrectMobile.equals(mobile)) {
			handleHsException(MOUDLENAME, methodName, msg.toString(),
					ErrorCodeEnum.MOBILE_BIND_FAIL);
		}
		// 再检查手机是否被他人使用
		isNewMobileIsRegiter(mobile);
	}

	private void isNewMobileIsRegiter(String newMobile) throws HsException {
		String perCustId = commonCacheService.findCustIdByMobile(newMobile);
		String methodName = "isNewMobileIsRegiter";
		if (StringUtils.isNotBlank(perCustId)) {
			handleHsException(MOUDLENAME, methodName, "新的手机号码[" + newMobile
					+ "]", ErrorCodeEnum.USER_EXIST);
		}
	}

	/**
	 * 企业名称或联系电话有变需通知电商系统
	 * 
	 * @param source
	 * @param target
	 */
	public void isEntNameOrPhoneChag(EntExtendInfo source, EntExtendInfo target) {
		String oldEntName = StringUtils.nullToEmpty(source.getEntCustName());
		String newEntName = StringUtils.nullToEmpty(target.getEntCustName());
		boolean nameChange = false;
		if (!StringUtils.isBlank(newEntName)) {// 企业名称被修改
			nameChange = !oldEntName.equals(newEntName);
		}
		String oldPhone = StringUtils.nullToEmpty(source.getContactPhone());
		String newPhone = StringUtils.nullToEmpty(target.getContactPhone());
		boolean phoneChange = false;
		if (!StringUtils.isBlank(newPhone)) {// 联系电话被修改
			phoneChange = !oldPhone.equals(newPhone);
		}
		if (nameChange || phoneChange) {
			String entResNo = source.getEntResNo();
			try {
				// 调用电商接口通知电商
				ReturnResult returnResult = enterpriceService
						.updateCompanyName(entResNo, newEntName, newPhone);

				if (null != returnResult && 200 != returnResult.getRetCode()) {
					SystemLog.warn(MOUDLENAME, "isEntNameOrPhoneChag",
							" 修改企业名称或者联系方式异常：\r\n",
							new HsException(returnResult.getRetCode(),
									returnResult.getRetMsg()));
				}
				SystemLog.debug(MOUDLENAME, "isEntNameOrPhoneChag",
						"已经通知电商,entResNo[" + entResNo + "],entName["
								+ newEntName + "],phone[" + newPhone + "]");
			} catch (Exception e) {
				SystemLog.error(MOUDLENAME, "isEntNameOrPhoneChag",
						" 修改企业名称或者联系方式异常：\r\n", e);
				throw new HsException(
						ErrorCodeEnum.CHG_ENTPHONE_ENTNAME_ECSERVICE_ERROR
								.getValue(),
						e.getMessage());
			}
		} else {
			SystemLog.debug(MOUDLENAME, "isEntNameOrPhoneChag", "无需通知电商,"
					+ newEntName);
		}
	}

	public void entCancel(String entCustId, String operCustId) {

		// if (5 == StatusInfo.getBaseStatus()
		// && "N".equals(StatusInfo.getIsactive())) {
		// return ;
		// }
		Timestamp now = StringUtils.getNowTimestamp();
		EntStatusInfo entStatusInfo = new EntStatusInfo();
		entStatusInfo.setEntCustId(entCustId);
		entStatusInfo.setUpdateDate(now);
		entStatusInfo.setBaseStatus(5);// 企业基本状态更改为注销
		entStatusInfo.setUpdatedby(operCustId);
		entStatusInfo.setIsactive("N");
		commonCacheService.updateEntStatusInfo(entStatusInfo, operCustId);
		EntExtendInfo extendInfo = new EntExtendInfo();
		extendInfo.setEntCustId(entCustId);
		extendInfo.setUpdatedby(operCustId);
		extendInfo.setUpdateDate(now);
		extendInfo.setIsactive("N");
		commonCacheService.updateEntExtInfo(extendInfo, operCustId);

		EntStatusInfo StatusInfo = commonCacheService
				.searchEntStatusInfo(entCustId);
		if (StatusInfo != null) {
			commonCacheService.removeEntCustIdCache(StatusInfo.getEntResNo());
			commonCacheService.removeEntTradeFailTimesCache(StatusInfo
					.getEntResNo());
			commonCacheService.removeEntPwdQuestionFailTimesCache(StatusInfo
					.getEntResNo());
		} else {
			System.err
					.println(entCustId
							+ " commonCacheService.searchEntStatusInfo(entCustId) ==null");
		}
	}

	/**
	 * 检查持卡人交易密码
	 * 
	 * @param custId
	 *            客户号
	 * @param tradePwd
	 *            交易密码
	 * @throws HsException
	 */
	public void checkCarderTradePwd(CardHolder cardHolder, String tradePwd)
			throws HsException {
		String perResNo = cardHolder.getPerResNo();
		String conrectTranPwd = cardHolder.getPwdTrans();
		if (StringUtils.isBlank(conrectTranPwd)) {
			SystemLog.warn(MOUDLENAME, "checkCarderTradePwd", perResNo
					+ "交易密码未设置");
			throw new HsException(ErrorCodeEnum.TRANS_PWD_NOT_SET.getValue(),
					perResNo + "交易密码未设置");
		}
		int transFailTimes = commonCacheService
				.getCarderTradeFailTimesCache(perResNo);
		int maxFailTimes = SysConfig.getTransPwdFailedTimes();
		isReachMaxTradeFailTimes(transFailTimes, maxFailTimes);// 判断帐户是否已锁
		String salt = cardHolder.getPwdTransSaltValue();
		String tempTradePwd = StringEncrypt.sha256(tradePwd + salt);

		if (!isMatchTradePwd(tempTradePwd, conrectTranPwd)) {
			transFailTimes++;// 失败次数加1
			commonCacheService.addCarderTradeFailTimesCache(perResNo,
					transFailTimes);// 更新缓存

			String msg = ErrorCodeEnum.PWD_TRADE_ERROR.getDesc() + "salt=["
					+ salt + "]传入密码=[" + tradePwd + "" + "]密码密文=["
					+ tempTradePwd + "]";
			SystemLog.warn(MOUDLENAME, "checkCarderTradePwd", msg);
			StringBuffer appendMsg = new StringBuffer();
			appendMsg.append(" 传入密码[").append(tradePwd).append("],密码密文[")
					.append(tempTradePwd).append("],custId[")
					.append(cardHolder.getPerCustId()).append("],userType[2]");
			tradeFaiure(cardHolder.getPerResNo(), transFailTimes, maxFailTimes,
					appendMsg.toString());
		} else {
			if (transFailTimes > 0) {
				// 更新缓存
				commonCacheService.addCarderTradeFailTimesCache(perResNo, 0);
			}
		}
	}

	/**
	 * 检查企业交易密码
	 * 
	 * @param entCustId
	 * @param tradePwd
	 */
	public void checkEntTradePwd(EntStatusInfo entStatusInfo, String tradePwd)
			throws HsException {
		String entResNo = entStatusInfo.getEntResNo();
		String conrectTranPwd = entStatusInfo.getPwdTrans();
		if (StringUtils.isBlank(conrectTranPwd)) {
			SystemLog.warn(MOUDLENAME, "checkCarderTradePwd", entResNo
					+ "交易密码未设置");
			throw new HsException(ErrorCodeEnum.TRANS_PWD_NOT_SET.getValue(),
					entResNo + "交易密码未设置");
		}

		int tradeFailTimes = commonCacheService
				.getEntTradeFailTimesCache(entResNo);
		int maxFailTimes = SysConfig.getTransPwdFailedTimes();
		isReachMaxTradeFailTimes(tradeFailTimes, maxFailTimes);
		String salt = entStatusInfo.getPwdTransSaltValue();
		String tempTradePwd = StringEncrypt.sha256(tradePwd + salt);
		// 验证密码是否正确
		if (!isMatchTradePwd(tempTradePwd, entStatusInfo.getPwdTrans())) {
			// 失败次数加1
			tradeFailTimes += 1;
			commonCacheService.addEntTradeFailTimesCache(entResNo,
					tradeFailTimes);

			StringBuffer appendMsg = new StringBuffer();
			appendMsg.append(" 传入密码[").append(tradePwd).append("],密码密文[")
					.append(tempTradePwd).append("],entCustId[")
					.append(entStatusInfo.getEntCustId())
					.append("],userType[4]");
			SystemLog.warn(MOUDLENAME, "checkEntTradePwd", "交易密码错误,salt["
					+ salt + "]" + appendMsg);
			tradeFaiure(entStatusInfo.getEntResNo(), tradeFailTimes,
					maxFailTimes, appendMsg.toString());
		} else {
			if (tradeFailTimes > 0) {
				// 更新缓存
				commonCacheService.addEntTradeFailTimesCache(entResNo, 0);
			}
		}
	}

	public void checkNoCarderTradePwd(NoCardHolder noCarder, String tradePwd)
			throws HsException {
		String mobile = noCarder.getMobile();
		String conrectTranPwd = noCarder.getPwdTrans();
		if (StringUtils.isBlank(conrectTranPwd)) {
			SystemLog.warn(MOUDLENAME, "checkCarderTradePwd", mobile
					+ "交易密码未设置");
			throw new HsException(ErrorCodeEnum.TRANS_PWD_NOT_SET.getValue(),
					mobile + "交易密码未设置");
		}
		int transFailTimes = commonCacheService
				.getNoCardTradeFailTimesCache(mobile);
		int maxFailTimes = SysConfig.getTransPwdFailedTimes();
		// 判断帐户是否已锁
		isReachMaxTradeFailTimes(transFailTimes, maxFailTimes);
		String salt = noCarder.getPwdTransSaltValue();
		String tempTradePwd = StringEncrypt.sha256(tradePwd + salt);
		if (!isMatchTradePwd(tempTradePwd, conrectTranPwd)) {
			// 失败次数加1
			transFailTimes++;
			// 更新缓存
			commonCacheService.addNoCardTradeFailTimesCache(mobile,
					transFailTimes);

			String msg = transFailTimes + "," + maxFailTimes + ","
					+ ErrorCodeEnum.PWD_TRADE_ERROR.getDesc() + ",传入密码["
					+ tradePwd + "],密码密文[" + tempTradePwd + "],perCustId["
					+ noCarder.getPerCustId() + "],userType[1]";
			SystemLog.warn(MOUDLENAME, "checkNoCarderTradePwd", msg + "salt["
					+ salt + "]");
			tradeFaiure(noCarder.getMobile(), transFailTimes, maxFailTimes, msg);
		} else {
			if (transFailTimes > 0) {
				// 更新缓存
				commonCacheService.addNoCardTradeFailTimesCache(mobile, 0);
			}
		}
	}

	public void checkLoginPwdNoCountFailedTimes(UserTypeEnum userType,
			String custId, String oldLoginPwd, String secretKey) {
		// 组装查询的条件
		String operNo = null;
		UserInfo userInfo = null;
		// 组装数据
		if (userType == UserTypeEnum.CARDER) {
			CardHolder cardHolder = null;
			cardHolder = commonCacheService.searchCardHolderInfo(custId);
			if (null == cardHolder) {
				throw new HsException(ErrorCodeEnum.USER_NOT_FOUND.getValue(),
						"用户不存在或已注销或已禁用");
			}
			userInfo = (UserInfo) cardHolder;
			operNo = cardHolder.getPerResNo();
		} else if (userType == UserTypeEnum.NO_CARDER) {
			// 获取非持卡人信息
			NoCardHolder noCardHolder = null;
			noCardHolder = commonCacheService.searchNoCardHolderInfo(custId);
			if (null == noCardHolder) {
				throw new HsException(ErrorCodeEnum.USER_NOT_FOUND.getValue(),
						"用户不存在或已注销或已禁用");
			}
			userInfo = (UserInfo) noCardHolder;
			operNo = noCardHolder.getMobile();
		} else if (userType == UserTypeEnum.SYSTEM) {
			// 获取系统用户信息
			userInfo = commonCacheService.searchSysOperInfo(custId);

			if (userInfo == null) {
				throw new HsException(ErrorCodeEnum.USER_NOT_FOUND.getValue(),
						"用户不存在或已注销或已禁用");
			}
			// 验证用户状态是否正常
			SysOperator oper = (SysOperator) userInfo;
			if (oper.getIsactive().equals("N")) {
				throw new HsException(ErrorCodeEnum.USER_NOT_FOUND.getValue(),
						custId + "该操作员客户号找不到操作员信息");

			}
			operNo = oper.getUserName();
		} else if (userType == UserTypeEnum.CHECKER) {
			userInfo = commonCacheService.searchSysOperSecondInfo(custId);
			if (userInfo == null) {
				throw new HsException(ErrorCodeEnum.USER_NOT_FOUND.getValue(),
						"用户不存在或已注销或已禁用");
			}
			// 验证用户状态是否正常
			SysOperator oper = (SysOperator) userInfo;
			if (oper.getIsactive().equals("N")) {
				throw new HsException(ErrorCodeEnum.USER_NOT_FOUND.getValue(),
						custId + "该操作员客户号找不到操作员信息");
			}
			operNo = oper.getUserName();
		} else {
			// 获取企业用户信息
			Operator operator = commonCacheService.searchOperByCustId(custId);
			;
			operNo = operator.getOperNo();
			CustIdGenerator.isOperExist(operator, custId);
			userInfo = (UserInfo) operator;
		}
		PasswordBean passwordBean = new PasswordBean();
		passwordBean.setEnt((userType == UserTypeEnum.ENT)
				|| (userType == UserTypeEnum.OPERATOR));
		passwordBean.setOriginalPwd(userInfo.getPwdLogin());
		passwordBean.setPwd(oldLoginPwd);
		passwordBean.setRandomToken(secretKey);
		passwordBean.setSaltValue(userInfo.getPwdLoginSaltValue());
		passwordBean.setUsername(operNo);
		passwordBean.setVersion(userInfo.getPwdLoginVer());
		// 验证用户名和密码是否正确
		boolean isMatch = passwordService.matchAesPassword(passwordBean);
		// 登录不成功
		if (!isMatch) {
			StringBuffer msg = new StringBuffer();
			String funName = "checkLoginPwdNoCountFailedTimes";
			msg.append("传参  userType[" + userType.getType() + "],oldLoginPwd["
					+ oldLoginPwd + "],secretKey[" + secretKey + "]");
			SystemLog.info(MOUDLENAME, funName, "修改登录密码失败：" + msg.toString());
			// 处理登录密码验证失败
			throw new HsException(ErrorCodeEnum.USER_PWD_IS_WRONG.getValue(),
					msg.toString());
		}
	}

	public void checkTradePwdNoCountFailedTimes(String custId, String tradePwd,
			String userType, String secretKey) throws HsException {
		String conrectTranPwd = "";
		String tempTradePwd = "";
		if (UserTypeEnum.CARDER.getType().equals(userType)) {
			CardHolder cardHolder = commonCacheService
					.searchCardHolderInfo(custId);
			CustIdGenerator.isCarderExist(cardHolder, custId);
			if (StringUtils.isBlank(cardHolder.getPwdTrans())) {
				throw new HsException(
						ErrorCodeEnum.TRANS_PWD_NOT_SET.getValue(),
						ErrorCodeEnum.TRANS_PWD_NOT_SET.getDesc());
			}
			conrectTranPwd = cardHolder.getPwdTrans();
			tempTradePwd = StringEncrypt.sha256(tradePwd
					+ cardHolder.getPwdTransSaltValue());
		} else if (UserTypeEnum.ENT.getType().equals(userType)) {
			EntStatusInfo entStatusInfo = commonCacheService
					.searchEntStatusInfo(custId);
			CustIdGenerator.isEntStatusInfoExist(entStatusInfo, custId);
			if (StringUtils.isBlank(entStatusInfo.getPwdTrans())) {
				throw new HsException(
						ErrorCodeEnum.TRANS_PWD_NOT_SET.getValue(),
						ErrorCodeEnum.TRANS_PWD_NOT_SET.getDesc());
			}
			conrectTranPwd = entStatusInfo.getPwdTrans();
			tempTradePwd = StringEncrypt.sha256(tradePwd
					+ entStatusInfo.getPwdTransSaltValue());
		} else if (UserTypeEnum.NO_CARDER.getType().equals(userType)) {
			NoCardHolder noCarder = commonCacheService
					.searchNoCardHolderInfo(custId);
			CustIdGenerator.isNoCarderExist(noCarder, custId);
			if (StringUtils.isBlank(noCarder.getPwdTrans())) {
				throw new HsException(
						ErrorCodeEnum.TRANS_PWD_NOT_SET.getValue(),
						ErrorCodeEnum.TRANS_PWD_NOT_SET.getDesc());
			}
			conrectTranPwd = noCarder.getPwdTrans();
			tempTradePwd = StringEncrypt.sha256(tradePwd
					+ noCarder.getPwdTransSaltValue());
		} else {
			throw new HsException(ErrorCodeEnum.USERTYPE_IS_ILLEGAL.getValue(),
					ErrorCodeEnum.USERTYPE_IS_ILLEGAL.getDesc()
							+ "，实际传入的用户类型为：" + userType);
		}
		if (!isMatchTradePwd(tempTradePwd, conrectTranPwd)) {
			throw new HsException(ErrorCodeEnum.PWD_TRADE_ERROR.getValue(),
					ErrorCodeEnum.PWD_TRADE_ERROR.getDesc() + "，userType["
							+ userType + "],custId[" + custId
							+ "],tempTradePwd[" + tempTradePwd + "],secretKey["
							+ secretKey + "],conrectTranPwd[" + conrectTranPwd
							+ "]");
		}
	}

	private boolean isMatchTradePwd(String tradePwd, String conrectTranPwd) {
		return conrectTranPwd.equals(tradePwd);
	}

	public void handleHsException(String moudleName, String methodName,
			String msg, ErrorCodeEnum error) throws HsException {
		HsException exception = new HsException(error.getValue(),
				error.getDesc());
		SystemLog.error(moudleName, methodName, msg, exception);
		throw exception;
	}

	public void handleHsException(String moudleName, String methodName,
			String msg, ErrorCodeEnum error, Exception e) throws HsException {
		StringBuffer errorInfo = new StringBuffer();
		String errorCode = "";
		String errorMsg = "";
		if (e instanceof NoticeException) {
			errorInfo.append("通知系统errorCode[");
			NoticeException noticeException = (NoticeException) e;
			errorCode = noticeException.getErrorCode();
			errorMsg = noticeException.getMessage();
			errorInfo.append(errorCode).append("],通知系统errorMsg[")
					.append(errorMsg).append("],");
		} else if (e instanceof HsException) {
			HsException hsException = (HsException) e;
			errorCode = hsException.getErrorCode().toString();
			errorMsg = hsException.getMessage();
			errorInfo.append("用户中心errorCode[" + errorCode + "],errorMsg["
					+ errorMsg + "],");
		} else {
			errorInfo.append("其他异常errorCode[");
			errorInfo.append(e.getCause()).append("],其他异常errorMsg[")
					.append(e.getMessage()).append("],");
		}
		errorInfo.append(msg);
		HsException exception = new HsException(error.getValue(),
				error.getDesc());
		SystemLog.error(moudleName, methodName, errorInfo.toString(), e);
		throw exception;
	}

	public void kickedOut(String entCustId) {
		// 清除企业下所有的操作员的登录token
		removeAllOperLoginToken(entCustId);
		String isCloseDevice = SysConfig.getIsCloseDevice();
		if ("0".equals(isCloseDevice)) {
			SystemLog.info(MOUDLENAME, "kickedOut",
					"配置文件已设置isCloseDevice=0,不清除设备登陆token");
		} else {
			// 清除企业下所有POS机的登录token
			removeAllPosLoginToken(entCustId);
			// 清除企业下所有刷卡器的登录token
			removeAllCardReaderLoginToken(entCustId);
		}
	}

	private void removeAllOperLoginToken(String entCustId) {
		ChannelTypeEnum[] channels = ChannelTypeEnum.values();
		List<Operator> operList = null;
		try {
			operList = operatorMapper.listOperByEntCustId(entCustId);
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "removeAllOperLoginToken",
					"查询企业的操作员列表异常：" + NEWLINE, e);
		}
		if (null != operList && operList.size() > 0) {
			for (Operator operator : operList) {
				for (ChannelTypeEnum channel : channels) {
					commonCacheService.removeLoginTokenCache(channel,
							operator.getOperCustId());
				}
			}
		}
	}

	private void removeAllPosLoginToken(String entCustId) {
		List<PosDevice> posList = null;
		try {
			posList = posDeviceMapper.listAllPosDevice(entCustId);
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "removeAllPosLoginToken",
					"查询企业所有POS设备异常：" + NEWLINE, e);
		}
		if (null != posList && posList.size() > 0) {
			for (PosDevice pos : posList) {
				commonCacheService.removePosTokenCache(pos.getEntResNo(),
						pos.getDeviceSeq());
			}
		}
	}

	private void removeAllCardReaderLoginToken(String entCustId) {
		List<CardReaderDevice> cardReaderList = null;
		try {
			cardReaderList = cardReaderDeviceMapper
					.listAllCardReaderDevice(entCustId);
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "removeAllPosLoginToken",
					"查询企业所有刷卡器设备异常：" + NEWLINE, e);
		}
		if (null != cardReaderList && cardReaderList.size() > 0) {
			for (CardReaderDevice cardReader : cardReaderList) {
				commonCacheService.removeCardReaderTokenCache(
						cardReader.getEntResNo(), cardReader.getDeviceSeq());
			}
		}
	}

	/**
	 * 删除登录缓存
	 * 
	 * @param perCustId
	 *            持卡人客户号
	 */
	public void kickOutCardHolder(String perCustId) {
		for (ChannelTypeEnum channelType : ChannelTypeEnum.values()) {
			commonCacheService.removeLoginTokenCache(channelType, perCustId);// 删除登录缓存
		}
	}

	/**
	 * 用于登录的业务提示
	 * 
	 * @param currentFailTimes
	 * @param maxFailTimes
	 * @throws HsException
	 */
	public void isReachMaxLoginFailTimes(int currentFailTimes, int maxFailTimes)
			throws HsException {
		if (currentFailTimes >= maxFailTimes) {
			StringBuffer msg = new StringBuffer();
			msg.append("您因登录密码连续错误次数超过" + maxFailTimes
					+ "次被系统锁定，暂时不能登录系统，请明日进行登录");
			throw new HsException(
					ErrorCodeEnum.LOGINPWD_USER_LOCKED.getValue(),
					msg.toString());
		}
	}

	/**
	 * 用于交易密码失败次数的提示
	 * 
	 * @param currentFailTimes
	 * @param maxFailTimes
	 * @throws HsException
	 */
	public void isReachMaxTradeFailTimes(int currentFailTimes, int maxFailTimes)
			throws HsException {
		if (currentFailTimes >= maxFailTimes) {
			StringBuffer msg = new StringBuffer();
			msg.append("您因交易密码连续错误次数超过" + maxFailTimes
					+ "次被系统锁定，暂时不能进行业务交易，请明日进行尝试");
			throw new HsException(
					ErrorCodeEnum.TRADEPWD_USER_LOCKED.getValue(),
					msg.toString());
		}
	}

	public void loginFaiure(String userName, int curFailTimes,
			int maxFailTimes, String appendMsg) throws HsException {
		StringBuffer msg = new StringBuffer();
		isReachMaxLoginFailTimes(curFailTimes, maxFailTimes);
		msg.append(ErrorCodeEnum.USER_PWD_IS_WRONG.getDesc()).append(",")
				.append(appendMsg).append(" userName[").append(userName)
				.append("]");
		throw new HsException(ErrorCodeEnum.USER_PWD_IS_WRONG.getValue(),
				getFailTimesMsg(curFailTimes, maxFailTimes, msg.toString()));
	}

	public void tradeFaiure(String userName, int curFailTimes,
			int maxFailTimes, String appendMsg) throws HsException {
		isReachMaxTradeFailTimes(curFailTimes, maxFailTimes);// 判断帐户是否已锁
		StringBuffer msg = new StringBuffer();
		msg.append(ErrorCodeEnum.PWD_TRADE_ERROR.getDesc()).append(",")
				.append(appendMsg).append(" userName[").append(userName)
				.append("]");
		throw new HsException(ErrorCodeEnum.PWD_TRADE_ERROR.getValue(),
				getFailTimesMsg(curFailTimes, maxFailTimes, msg.toString()));
	}

	public String getFailTimesMsg(int curFailTimes, int maxFailTimes, String str) {
		StringBuffer msg = new StringBuffer();
		msg.append(curFailTimes).append(",").append(maxFailTimes).append(",")
				.append(str);
		return msg.toString();
	}

	public void solrAdd(SearchUserInfo user) {
		searchUserService.add(user);
	}

	public void solrDel(String custId) {
		searchUserService.delete(custId);
	}

	public void solrUpdate(SearchUserInfo user) {
		searchUserService.update(user);
	}

	public void solrUpdateSearchMode(String custId, String searchMode) {
		searchUserService.updateSearchMode(custId, searchMode);
	}

	public SearchUserInfo solrUpdateNetworkInfo(String custId) {
		// 组装搜索引擎需要更新的数据
		// 获取完整的网络信息
		NetworkInfo networkInfo = commonCacheService.searchNetworkInfo(custId);
		SearchUserInfo user = new SearchUserInfo();
		// 获取用户信息
		String userType = CustIdGenerator.getUserTypeByCustId(custId);
		if (UserTypeEnum.NO_CARDER.getType().equals(userType)) {
			NoCardHolder noCardHolder = commonCacheService
					.searchNoCardHolderInfo(custId);
			CustIdGenerator.isNoCarderExist(noCardHolder, custId);
			user.setMobile(noCardHolder.getMobile());
			user.setUsername(noCardHolder.getMobile());
			user.setUserType(0);
		} else if (UserTypeEnum.CARDER.getType().equals(userType)) {
			CardHolder cardHolder = commonCacheService
					.searchCardHolderInfo(custId);
			CustIdGenerator.isCarderExist(cardHolder, custId);
			user.setResNo(cardHolder.getPerResNo());
			user.setEntResNo(cardHolder.getEntResNo());
			user.setUserType(1);
		} else if (UserTypeEnum.OPERATOR.getType().equals(userType)) {
			//
			Operator operator = commonCacheService.searchOperByCustId(custId);
			CustIdGenerator.isOperExist(operator, custId);

			user.setResNo(operator.getOperResNo());
			user.setEntResNo(operator.getEntResNo());
			user.setUserType(Integer.valueOf(operator
					.getEnterpriseResourceType()));
			// 获取企业信息
			String entCustId = operator.getEntCustId();
			EntExtendInfo entInfo = commonCacheService
					.searchEntExtendInfo(entCustId);
			CustIdGenerator.isEntExtendExist(entInfo, entCustId);
			user.setParentEntResNo(entInfo.getSuperEntResNo());
			user.setOperDuty(operator.getOperDuty());
			user.setOperEmail(operator.getEmail());
			user.setOperName(operator.getOperName());
			user.setOperPhone(operator.getPhone());
			user.setEntCustId(entInfo.getEntCustId());
		}

		user.setAge(networkInfo.getAge());
		user.setArea(networkInfo.getArea());
		user.setCity(networkInfo.getCityNo());
		user.setProvince(networkInfo.getProvinceNo());
		user.setCustId(networkInfo.getPerCustId());
		user.setHeadImage(networkInfo.getHeadShot());
		user.setHobby(networkInfo.getHobby());
		user.setNickName(networkInfo.getNickname());
		user.setName(networkInfo.getName());
		user.setSex(String.valueOf(networkInfo.getSex()));
		user.setSign(networkInfo.getIndividualSign());
		user.setIsLogin(1);

		setSearchMode(user, custId);
		// 通知搜索引擎更新
		try {
			solrUpdate(user);
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "solrUpdateNetInfo", "通知搜索引擎失败", e);
		}
		return user;
	}
	private SearchUserInfo setSearchMode(SearchUserInfo user,String custId){
		CustPrivacy ret = custPrivacyMapper.selectByPrimaryKey(custId);
		if (ret == null) {
			user.setSearchMode("1");
		} else {
			user.setSearchMode(ret.getSearchMe());
		}
		return user;
	}
	
	public SearchUserInfo solrAddOper(Operator oper){
		SearchUserInfo user = genSearchUserInfo(oper);
		if(user==null){
			SystemLog.warn(MOUDLENAME, "solrAddOper", "genSearchUserInfo is null");
			return null;
		}
		user.setSearchMode("1");
		solrAdd(user);
		return user;
	}
	public SearchUserInfo solrUpdateOper(Operator oper){
		SearchUserInfo user = genSearchUserInfo(oper);
		if(user==null){
			SystemLog.warn(MOUDLENAME, "solrAddOper", "genSearchUserInfo is null");
			return null;
		}
		setSearchMode(user, oper.getOperCustId());
		solrAdd(user);
		return user;
	}
	private SearchUserInfo genSearchUserInfo(Operator oper){
		if(oper==null ){
			SystemLog.warn(MOUDLENAME, "genSearchUserInfo", "param is null");
			return null;
		}
		String entCustId=oper.getEntCustId();
		EntExtendInfo extendInfo = commonCacheService
				.searchEntExtendInfo(entCustId);
		if (extendInfo==null||extendInfo.getCustType() == null) {
			throw new HsException(
					ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					entCustId+"客户类型(custType)为空");
		}
		SearchUserInfo user = new SearchUserInfo();
		
		user.setUsername(oper.getOperNo());
		user.setCustId(oper.getOperCustId());
		user.setEntResNo(oper.getEntResNo());
		user.setEntCustId(entCustId);
		user.setParentEntResNo(extendInfo.getSuperEntResNo());
		user.setUserType(extendInfo.getCustType());		
		user.setOperEmail(oper.getEmail());
		user.setOperName(oper.getOperName());
		user.setOperPhone(oper.getPhone());
		user.setOperDuty(oper.getOperDuty());
		user.setIsLogin(1);
		user.setResNo(oper.getOperResNo());//绑定互生卡号
		
		// 获取操作员网络信息
		NetworkInfo net = commonCacheService.searchNetworkInfo(oper.getOperCustId());
		if(net != null){
			user.setAge(net.getAge());
			user.setArea(net.getArea());
			user.setCity(net.getCityNo());
			user.setHeadImage(net.getHeadShot());
			user.setHobby(net.getHobby());
			user.setMobile(net.getMobile());
			user.setName(net.getName());
			user.setNickName(net.getNickname());
			user.setProvince(net.getProvinceNo());
			if(net.getSex() != null){
				user.setSex(String.valueOf(net.getSex()));
			}
			user.setSign(net.getIndividualSign());
		}
		
//		user.setSearchMode("1");
		return user;
	}

}
