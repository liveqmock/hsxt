/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.consumer.service;

import static com.gy.hsxt.common.utils.StringUtils.isBlank;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.BeanCopierUtils;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.PageUtil;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.api.common.IUCAsEmailService;
import com.gy.hsxt.uc.as.api.common.IUCAsNetworkInfoService;
import com.gy.hsxt.uc.as.api.common.IUCAsPwdService;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderAuthInfoService;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderService;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.bean.common.AsNetworkInfo;
import com.gy.hsxt.uc.as.bean.consumer.AsCardHolder;
import com.gy.hsxt.uc.as.bean.consumer.AsCardHolderAllInfo;
import com.gy.hsxt.uc.as.bean.consumer.AsConsumerInfo;
import com.gy.hsxt.uc.as.bean.consumer.AsCustUpdateLog;
import com.gy.hsxt.uc.as.bean.consumer.AsHsCard;
import com.gy.hsxt.uc.as.bean.consumer.AsQueryConsumerCondition;
import com.gy.hsxt.uc.as.bean.consumer.AsRealNameAuth;
import com.gy.hsxt.uc.as.bean.consumer.AsUserActionLog;
import com.gy.hsxt.uc.bs.enumerate.BaseStatusEnum;
import com.gy.hsxt.uc.cache.service.CommonCacheService;
import com.gy.hsxt.uc.common.SysConfig;
import com.gy.hsxt.uc.common.bean.NetworkInfo;
import com.gy.hsxt.uc.common.bean.PwdQuestion;
import com.gy.hsxt.uc.common.mapper.NetworkInfoMapper;
import com.gy.hsxt.uc.common.mapper.PwdQuestionMapper;
import com.gy.hsxt.uc.common.service.CommonService;
import com.gy.hsxt.uc.common.service.UCAsPwdService;
import com.gy.hsxt.uc.consumer.bean.CardHolder;
import com.gy.hsxt.uc.consumer.bean.HsCard;
import com.gy.hsxt.uc.consumer.bean.RealNameAuth;
import com.gy.hsxt.uc.consumer.bean.UserActionLog;
import com.gy.hsxt.uc.consumer.mapper.AsCustUpdateLogMapper;
import com.gy.hsxt.uc.consumer.mapper.AsCustUpdateLogNewMapper;
import com.gy.hsxt.uc.consumer.mapper.AsCustUpdateLogOldMapper;
import com.gy.hsxt.uc.consumer.mapper.CardHolderMapper;
import com.gy.hsxt.uc.consumer.mapper.HsCardMapper;
import com.gy.hsxt.uc.consumer.mapper.RealNameAuthMapper;
import com.gy.hsxt.uc.consumer.mapper.UserActionLogMapper;
import com.gy.hsxt.uc.operator.bean.Operator;
import com.gy.hsxt.uc.util.CustIdGenerator;
import com.gy.hsxt.uc.util.StringEncrypt;

/**
 * 
 * @Package: com.gy.hsxt.uc.consumer.service
 * @ClassName: UCAsCardHolderService
 * @Description: 持卡人基本信息管理（AS接入系统调用）
 * 
 * @author: tianxh
 * @date: 2015-10-21 上午11:34:03
 * @version V1.0
 */
@Service
public class UCAsCardHolderService implements IUCAsCardHolderService {
	final static String MOUDLENAME = "UCAsCardHolderService";
	@Autowired
	private HsCardMapper hsCardMapper;

	@Autowired
	private CardHolderMapper cardHolderMapper;

	@Autowired
	private RealNameAuthMapper realNameAuthMapper;

	@Autowired
	private NetworkInfoMapper networkInfoMapper;

	@Autowired
	private PwdQuestionMapper pwdQuestionMapper;

	@Autowired
	private CommonCacheService commonCacheService;

	@Autowired
	private IUCAsCardHolderAuthInfoService cardHolderAuthInfoService;

	@Autowired
	private IUCAsNetworkInfoService networkInfoService;

	@Autowired
	private UserActionLogMapper userActionLogMapper;

	@Autowired
	IUCAsEmailService emailService;

	@Autowired
	IUCAsPwdService pwdService;

	@Autowired
	AsCustUpdateLogMapper asCustUpdateLogMapper;
	@Autowired
	AsCustUpdateLogNewMapper asCustUpdateLogNewMapper;
	@Autowired
	AsCustUpdateLogOldMapper asCustUpdateLogOldMapper;
	@Autowired
	UCAsPwdService asPwdService;
	@Autowired
	CommonService commonService;
	/**
	 * 每次批量入库条数 =1000
	 */
	int batchCount = 1000;

	/**
	 * 修改绑定邮箱
	 * 
	 * @param perCustId
	 *            客户号
	 * @param email
	 *            邮箱
	 * @param loginPassword
	 *            登录密码(AES加密)
	 * @param secretKey
	 *            AES秘钥
	 * @throws HsException
	 */
	@Override
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
		String custId = perCustId.trim();
		String userName = "";
		if (custId.length() > 11) {
			userName = custId.substring(0, 11);
		}
		// 核对登录密码是否正确
		CardHolder cardHolder = commonCacheService
				.searchCardHolderInfo(perCustId);
		CustIdGenerator.isCarderExist(cardHolder, perCustId);
		commonService.checkIsAuthEmail(custId, UserTypeEnum.CARDER.getType(),
				email);
		// 验证密码是否正确
		pwdService.checkLoginPwd(custId, loginPassword,
				UserTypeEnum.CARDER.getType(), secretKey);

		// 登录密码正确，则修改邮箱信息
		// String newEmail = email.trim();
		// CardHolder cardhHolder = new CardHolder();
		// cardhHolder.setPerCustId(custId);
		// cardhHolder.setEmail(newEmail);
		// cardhHolder.setIsAuthEmail(0);
		// cardhHolder.setUpdateDate(StringUtils.getNowTimestamp());
		// cardhHolder.setUpdatedby(custId);
		// commonCacheService.updateCardHolderInfo(cardhHolder, false);
		// 登录密码正确，则发送验证邮件
		emailService.sendMailForValidEmail(email, userName, "",
				UserTypeEnum.CARDER.getType(), CustType.PERSON.getCode());
	}

	/**
	 * 根据互生号查询持卡人的信息
	 * 
	 * @param resNo
	 *            互生号
	 * @return CardHolder(持卡人信息)
	 */
	@Override
	public AsCardHolder searchCardHolderInfoByResNo(String resNo)
			throws HsException {
		if (StringUtils.isBlank(resNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数互生号为空");
		}
		String perResNo = resNo.trim();
		String custId = commonCacheService.findCustIdByResNo(perResNo);
		CustIdGenerator.isCarderExist(custId, perResNo);
		return searchCardHolderInfoByCustId(custId);
	}

	/**
	 * 通过互生号查询互生卡的信息
	 * 
	 * @param resNo
	 * @return
	 */
	@Override
	public AsHsCard searchHsCardInfoByResNo(String resNo) throws HsException {
		if (StringUtils.isBlank(resNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数互生号为空");
		}
		String perResNo = resNo.trim();
		// 根据资源号查询客户号
		String custId = commonCacheService.findCustIdByResNo(perResNo);
		CustIdGenerator.isCarderExist(custId, resNo);
		// 根据客户号查询卡信息
		return searchHsCardInfoByCustId(custId);
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
	 */
	@Override
	public void updateLoginInfo(String perCustId, String lastLoginIp,
			String loginDate) throws HsException {
		if (StringUtils.isBlank(perCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数客户号为空");
		}
		CardHolder ch = commonCacheService.searchCardHolderInfo(perCustId);
		commonCacheService.updateCarderLoginIn(ch, lastLoginIp);
	}

	/**
	 * 是否已设置交易密码
	 * 
	 * @param custId
	 * @return
	 */
	@Override
	public boolean isSetTransPwd(String custId) throws HsException {
		AsCardHolder ac = searchCardHolderInfoByCustId(custId);
		if (ac == null) {
			throw new HsException(ErrorCodeEnum.USER_NOT_FOUND.getValue(),
					ErrorCodeEnum.USER_NOT_FOUND.getDesc());
		}
		if (StringUtils.isEmpty(ac.getPwdTrans())) {
			return false;
		}
		return false;
	}

	/**
	 * @param custId
	 * @param newPwd
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderService#setLoginPwd(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public void setLoginPwd(String custId, String newPwd) throws HsException {
		// 查找客户号
		if (StringUtils.isEmpty(custId)) {
			throw new HsException(ErrorCodeEnum.USER_NOT_FOUND.getValue(),
					ErrorCodeEnum.USER_NOT_FOUND.getDesc());
		}
		// modifyLoginPwd(custId, newPwd);
	}

	/**
	 * 修改交易密码
	 * 
	 * @param custId
	 * @param newPwd
	 * @throws HsException
	 */
	@Override
	public void setTradePwd(String custId, String newPwd) throws HsException {
		// modifyTradePwd(custId, newPwd);
	}

	/**
	 * 通过客户号查询持卡人信息
	 * 
	 * @param custId
	 *            客户号
	 * @return 持卡人信息
	 * @throws HsException
	 */
	@Override
	public AsCardHolder searchCardHolderInfoByCustId(String custId)
			throws HsException {
		if (StringUtils.isBlank(custId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数客户号为空");
		}
		CardHolder cardHolder = commonCacheService.searchCardHolderInfo(custId);
		CustIdGenerator.isCarderExist(cardHolder, custId);
		AsCardHolder asCardHolder = new AsCardHolder();
		BeanCopierUtils.copy(cardHolder, asCardHolder);
		return asCardHolder;
	}

	/**
	 * 通过客户号查询互生卡信息
	 * 
	 * @param custId
	 *            客户号
	 * @return 互生卡信息
	 * @throws HsException
	 */
	@Override
	public AsHsCard searchHsCardInfoByCustId(String custId) throws HsException {
		if (StringUtils.isBlank(custId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数客户号为空");
		}
		HsCard hsCard = commonCacheService.searchHsCardInfo(custId);
		CustIdGenerator.isHsCardExist(hsCard, custId);
		AsHsCard asHsCard = new AsHsCard();
		BeanCopierUtils.copy(hsCard, asHsCard);
		return asHsCard;
	}

	/**
	 * 通过客户号查询互生卡状态信息
	 * 
	 * @param custId
	 * @return
	 * @throws HsException
	 */
	public Map<String, String> searchHsCardStatusInfoBycustId(String perCustId)
			throws HsException {
		if (StringUtils.isBlank(perCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数客户号为空");
		}
		HsCard hsCard = commonCacheService.searchHsCardInfo(perCustId);
		CustIdGenerator.isHsCardExist(hsCard, perCustId);
		Map<String, String> hsCardMap = new HashMap<String, String>();
		String perResNo = StringUtils.nullToEmpty(hsCard.getPerResNo());
		String lossReason = StringUtils.nullToEmpty(hsCard.getLossReason());
		String lossDate = DateUtil.timestampToString(hsCard.getLossDate(),
				"yyyy-MM-dd");
		String cardStatus = "";
		if (null != hsCard.getCardStatus()) {
			cardStatus = String.valueOf(hsCard.getCardStatus());
		}
		hsCardMap.put("perResNo", perResNo);// 互生号
		hsCardMap.put("cardStatus", cardStatus);// 互生卡状态
		hsCardMap.put("lossReason", lossReason);
		hsCardMap.put("lossDate", lossDate);
		return hsCardMap;
	}

	/**
	 * 通过互生号查询客户号
	 * 
	 * @param resNo
	 *            互生号
	 * @return 持卡人客户号
	 * @throws HsException
	 */
	@Override
	public String findCustIdByResNo(String resNo) throws HsException {
		if (StringUtils.isBlank(resNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数互生号为空");
		}
		String custId = commonCacheService.findCustIdByResNo(resNo);
		CustIdGenerator.isCarderExist(custId, resNo);
		return custId;
	}

	/**
	 * 查询已验证的邮箱
	 * 
	 * @param custId
	 *            持卡人客户号
	 * @return Map<String,String> key=email(持卡人的邮箱),key=isAuthEmail(是否验证邮箱
	 *         0：否，1：是)
	 * @throws HsException
	 */
	@Override
	public Map<String, String> findEmailByCustId(String perCustId)
			throws HsException {
		if (StringUtils.isBlank(perCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数客户号为空");
		}
		String custId = perCustId.trim();
		CardHolder cardHolder = commonCacheService.searchCardHolderInfo(custId);
		CustIdGenerator.isCarderExist(cardHolder, custId);
		int isAuthEmail = 0;
		if (null != cardHolder.getIsAuthEmail()) {
			isAuthEmail = cardHolder.getIsAuthEmail();
		}
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("email", StringUtils.nullToEmpty(cardHolder.getEmail()));
		resultMap.put("isAuthEmail", String.valueOf(isAuthEmail));
		return resultMap;
	}

	/**
	 * 通过客户号查找手机号码
	 * 
	 * @param perCustId
	 *            客户号
	 * @return 手机号码
	 * @throws HsException
	 */
	@Override
	public String findMobileByCustId(String perCustId) throws HsException {
		if (StringUtils.isBlank(perCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数客户号为空");
		}
		String custId = perCustId.trim();
		CardHolder cardHolder = commonCacheService.searchCardHolderInfo(custId);
		CustIdGenerator.isCarderExist(cardHolder, custId);
		return StringUtils.nullToEmpty(cardHolder.getMobile());
	}

	/**
	 * 持卡人注销
	 * 
	 * @param perCustId
	 *            持卡人客户号
	 * @param operCustId
	 *            操作员客户号
	 * @throws HsException
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void closeAccout(String perCustId, String operCustId)
			throws HsException {
		if (StringUtils.isBlank(perCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数持卡人客户号为空");
		}
		if (StringUtils.isBlank(operCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数操作员客户号为空");
		}
		String consumerCustId = perCustId.trim();
		String operatorId = operCustId.trim();
		CardHolder cardHolder = commonCacheService
				.searchCardHolderInfo(consumerCustId);
		CustIdGenerator.isCarderExist(cardHolder, consumerCustId);
		Integer baseStatus = cardHolder.getBaseStatus();
		if (null == baseStatus
				|| (BaseStatusEnum.SUBSIDE.getType() != baseStatus.intValue())) {
			throw new HsException(
					ErrorCodeEnum.CARDSTATUS_NOT_SUBSIDE.getValue(),
					ErrorCodeEnum.CARDSTATUS_NOT_SUBSIDE.getDesc());
		}
		List<String> list = new ArrayList<String>();
		list.add(consumerCustId);
		batchCloseAccout(operatorId, list);
	}

	/**
	 * 批量注销
	 * 
	 * @param baseStatus
	 * @param isactive
	 * @param updateDate
	 * @param updatedby
	 * @param list
	 */
	public void batchCloseAccout(String operCustId, List<String> list)
			throws HsException {
		String isactive = "N";
		Timestamp updateDate = StringUtils.getNowTimestamp();
		String updatedby = operCustId.trim();
		try {
			// 删除库中的持卡人信息、互生卡信息、实名认证信息
			batchDeleteData(isactive, updateDate, updatedby, list);
			// 删除缓存中的持卡人信息、互生卡信息、实名认证信息
			batchDeleteCache(list);
		} catch (HsException e) {
			throw new HsException(ErrorCodeEnum.CLOSE_ACCOUT_ERROR.getValue(),
					e.getMessage());
		}
	}

	/**
	 * 删除库里面的关联数据
	 * 
	 * @param isactive
	 * @param updateDate
	 * @param updatedby
	 * @param list
	 */
	private void batchDeleteData(String isactive, Timestamp updateDate,
			String updatedby, List<String> list) {
		cardHolderMapper.batchDeleteByPrimaryKey(isactive, updateDate,
				updatedby, list);
		hsCardMapper.batchDeleteByPrimaryKey(isactive, updateDate, updatedby,
				list);
		realNameAuthMapper.batchDeleteByPrimaryKey(isactive, updateDate,
				updatedby, list);
		networkInfoMapper.batchDeleteByPrimaryKey(isactive, updateDate,
				updatedby, list);
		removeNetworkInfoCache(list);// 移除缓存中的网络信息
		for (int i = 0; i < list.size(); i++) {
		}
		pwdQuestionMapper.batchDeleteByPrimaryKey(list);
	}

	private void removeNetworkInfoCache(List<String> list) {
		for (String custId : list) {
			commonCacheService.removeNetworkInfoCache(custId);
		}
	}

	/**
	 * 删除缓存中关联的用户信息
	 * 
	 * @param list
	 *            客户号列表
	 */
	private void batchDeleteCache(List<String> list) {
		String perResNo = null;
		for (String custId : list) {
			if (custId.length() > 10) {
				perResNo = custId.substring(0, 11);
				commonCacheService.removeCarderLoginFailTimesCache(perResNo);// 删除缓存中登录密码失败次数
				commonCacheService.removeCarderTradeFailTimesCache(perResNo);// 删除缓存中交易密码失败次数
				commonCacheService
						.removeCarderPwdQuestionFailTimesCache(perResNo);// 删除缓存中密保失败次数
				commonCacheService.removeCarderCustIdCache(perResNo);// 删除缓存中的客户号
				commonCacheService.removeLoginTokenCache(custId);// 删除所有渠道的已登录的token
				commonCacheService.removeCardCache(custId);// 删除缓存中的互生卡的信息
				commonCacheService.removeCarderCache(custId);// 删除缓存中的持卡人信息
				commonCacheService.removeRealAuthCache(custId);// 删除缓存中的实名认证信息
			}
		}
	}

	/**
	 * 验证登录密码是否正确
	 * 
	 * @param custId
	 *            客户号
	 * @param inputLoginPwd
	 *            登录密码
	 * @return true : 正确 false : 不正确
	 */
	public boolean checkLoginPwd(String custId, String loginPassword)
			throws HsException {
		CardHolder cardHolder = commonCacheService.searchCardHolderInfo(custId);
		CustIdGenerator.isCarderExist(cardHolder, custId);
		String loginPwd = StringUtils.nullToEmpty(cardHolder.getPwdLogin());
		if (loginPassword.equals(loginPwd)) {
			return true;
		}
		return false;
	}

	/**
	 * 更新实名认证信息
	 * 
	 * @param realNameAuth
	 *            实名认证信息
	 * @param isDel
	 *            删除标识 true:删除 false:更新
	 * @throws HsException
	 */
	public void updateRealNameAuthInfo(RealNameAuth realNameAuth) {
		commonCacheService.updateRealNameAuthInfo(realNameAuth);
	}

	/**
	 * 检查证件是否已注册
	 * 
	 * @param realNameAuth
	 * @param perCustId
	 */
	public void isRealNameRegistered(RealNameAuth realNameAuth, String perCustId)
			throws HsException {
		// 检查证件是否被使用
		if (IsIdNoReg(realNameAuth)) {
			throw new HsException(ErrorCodeEnum.ID_IS_REGISTERED.getValue(),
					String.valueOf(realNameAuth.getIdNo()) + "证件号已经实名注册");
		}
		// 检查持卡人是否已经注册
		if (checkRealNameStatus(perCustId)) {
			throw new HsException(ErrorCodeEnum.ID_IS_REGISTERED.getValue(),
					perCustId + "持卡人已经实名注册");
		}
	}

	/**
	 * 检查证件是否已实名注册
	 * 
	 * @param realNameAuth
	 */
	private boolean IsIdNoReg(RealNameAuth realNameAuth) throws HsException {
		Integer idType = realNameAuth.getIdType();
		String idNo = realNameAuth.getIdNo();
		String perName = realNameAuth.getPerName();
		if (idType == 3) {// 证件类型为营业执照,姓名字段填企业名称
			perName = realNameAuth.getEntName();
			realNameAuth.setPerName(perName);
		}
		return isIdNoExist(null, idType, idNo, perName);
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
			count = realNameAuthMapper.getIdCardNumer(idType, idNo, isactive,
					perCustId, perName);
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "isIdNoExist",
					ErrorCodeEnum.CHECK_ID_REGISTERED_FAIL.getDesc()
							+ ",perCustId[" + perCustId + ",idType[" + idType
							+ "],idNo" + idNo + "]", e);
		}
		return count >= 1 ? true : false;
	}

	/**
	 * 检查用户的实名状态 实名状态1：未实名注册、2：已实名注册（有名字和身份证）、3:已实名认证
	 * 
	 * @param perCustId
	 *            客户号
	 * @return false: 1：未实名注册 true : 其他
	 */
	private boolean checkRealNameStatus(String perCustId) {
		CardHolder cardHolder = commonCacheService
				.searchCardHolderInfo(perCustId);
		CustIdGenerator.isCarderExist(cardHolder, perCustId);
		Integer isRealNameStatus = cardHolder.getIsRealnameAuth();
		return 1 != isRealNameStatus ? true : false;
	}

	/**
	 * 入库实名注册信息
	 * 
	 * @param realNameAuth
	 *            实名注册信息
	 */
	public void saveRealNameAuthInfo(RealNameAuth realNameAuth)
			throws HsException {
		try {
			realNameAuthMapper.insertSelective(realNameAuth);
			commonCacheService.addRealAuthCache(realNameAuth.getPerCustId(),
					realNameAuth);
		} catch (Exception e) {
			throw new HsException(
					ErrorCodeEnum.REALNAME_REGINFO_SAVE_FAIL.getValue(),
					e.getMessage());
		}

	}

	/**
	 * 更新互生卡信息
	 * 
	 * @param srcHsCard
	 *            互生卡信息
	 */
	public void updateHsCardInfo(HsCard srcHsCard) throws HsException {
		try {
			hsCardMapper.updateByPrimaryKeySelective(srcHsCard);
		} catch (HsException e) {
			throw new HsException(ErrorCodeEnum.HSCARD_UPDATE_FAIL.getValue(),
					e.getMessage());
		}
		commonCacheService.removeCardCache(srcHsCard.getPerCustId());
	}

	public void saveUserActionRecord(UserActionLog record) throws HsException {
		try {
			userActionLogMapper.insertSelective(record);
		} catch (HsException e) {
			throw new HsException(
					ErrorCodeEnum.USER_OPERATELOG_SAVE_ERROR.getValue(),
					e.getMessage());
		}
	}

	/**
	 * 多线程批量开卡时异常回滚 删除batchSaveCardHolderInfo 里插入的数据
	 * 
	 * @param dataList
	 */
	public void batchSaveCardHolderInfoRollback(List<CardHolder> dataList) {
		// todo 删除批量开卡时入库的 持卡人信息，卡信息，密保信息，网络信息
		ArrayList<String> list = new ArrayList<String>(dataList.size());
		for (CardHolder user : dataList) {
			list.add(user.getPerCustId());
		}
		// maper.deleteByList(list);
	}

	/**
	 * 保存网络信息
	 * 
	 * @param networkInfo
	 *            单笔网络信息
	 */
	public void saveNetWorkInfo(NetworkInfo networkInfo) throws HsException {
		try {
			networkInfoMapper.insertSelective(networkInfo);
		} catch (Exception e) {
			throw new HsException(ErrorCodeEnum.NETWORK_SAVE_ERROR.getValue(),
					e.getMessage());
		}
	}

	/**
	 * 根据互生号查询记录数
	 * 
	 * @param resNo
	 *            互生号
	 * @return 记录数
	 */
	public int getHsCardCountsByResNo(String resNo) throws HsException {
		int count = 0;
		try {
			count = hsCardMapper.selectCountsByResNo(resNo, "Y");
		} catch (Exception e) {
			throw new HsException(
					ErrorCodeEnum.CUST_ID_IS_NOT_FOUND.getValue(),
					e.getMessage());
		}
		return count;
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
		CardHolder cardHolder = commonCacheService
				.searchCardHolderInfo(perCustId);
		CustIdGenerator.isCarderExist(cardHolder, perCustId);
		String realNameStatus = "";
		if (null != cardHolder.getIsRealnameAuth()) {
			realNameStatus = String.valueOf(cardHolder.getIsRealnameAuth());
		}
		return realNameStatus;
	}

	/**
	 * 对明文密码进行加密
	 * 
	 * @param password
	 *            明文密码
	 * @param salt
	 *            盐值
	 * @return 加密后的密码
	 */
	public String encryptNakedPwd(String nakedPwd, String salt)
			throws HsException {
		String md5Pwd = StringEncrypt.string2MD5(nakedPwd);
		String pwdStr = md5Pwd + salt;
		String cryptPwd = StringEncrypt.sha256(pwdStr);
		return cryptPwd;
	}

	/**
	 * 对AES密码解密成裸MD5密码，并用加盐的方式进行加密
	 * 
	 * @param aesPwd
	 *            AES加密后的密码
	 * @param salt
	 *            盐值
	 * @param secretKey
	 *            秘钥
	 * @return 加密后的密码
	 */
	public String encryptAESPwd(String aesPwd, String salt, String secretKey)
			throws HsException {
		String md5SaltPwd = StringEncrypt.string2MD5(StringEncrypt.decrypt(
				aesPwd, secretKey)) + salt;
		return StringEncrypt.sha256(md5SaltPwd);
	}

	/**
	 * 校验手机验证码
	 * 
	 * @param smsCode
	 *            手机验证码
	 * @param mobile
	 *            手机
	 */
	public void checkSmsCode(String smsCode, String mobile) throws HsException {
		String conrectSmsCode = commonCacheService.getSmsCodeCache(mobile);
		String isnoreCheckCode = StringUtils.nullToEmpty(SysConfig
				.getIgnoreCheckCode());
		if (!"1".equals(isnoreCheckCode)) {// 是否忽略手机短信验证码验证（0：不忽略 1：忽略）
			if (StringUtils.isBlank(conrectSmsCode)) {
				throw new HsException(ErrorCodeEnum.SMS_IS_EXPIRED.getValue(),
						ErrorCodeEnum.SMS_IS_EXPIRED.getDesc());
			}
			if (!smsCode.equals(conrectSmsCode)) {
				throw new HsException(ErrorCodeEnum.SMS_NOT_MATCH.getValue(),
						ErrorCodeEnum.SMS_NOT_MATCH.getDesc());
			}
		}
	}

	/**
	 * 分页查询消费者信息
	 * 
	 * @param condition
	 *            分页查询条件 必传
	 * @param page
	 *            分页参数 必传
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderService#listConsumerInfo(com.gy.hsxt.uc.as.bean.consumer.AsQueryConsumerCondition,
	 *      com.gy.hsxt.common.bean.Page)
	 */
	public PageData<AsConsumerInfo> listConsumerInfo(
			AsQueryConsumerCondition condition, Page page) throws HsException {
		// 数据验证
		if (isBlank(condition)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"必传参数[condition]为空");
		}
		if (isBlank(page)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"必传参数[page]为空");
		}

		// 查询统计数
		int count = cardHolderMapper.countConsumerInfo(condition);
		if (count < 1) {
			return null;
		}
		// 查询数据
		List<AsConsumerInfo> list = cardHolderMapper.pageConsumerInfo(
				condition, page);
		return new PageData<>(count, list);
	}

	/**
	 * 查询持卡人所有信息
	 * 
	 * @param custId
	 *            持卡人客户号
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderService#searchAllInfo(java.lang.String)
	 */
	public AsCardHolderAllInfo searchAllInfo(String custId) throws HsException {
		// 数据验证
		if (isBlank(custId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"必传参数[custId]为空");
		}
		AsCardHolderAllInfo allInfo = new AsCardHolderAllInfo();
		AsCardHolder baseInfo = searchCardHolderInfoByCustId(custId);
		AsHsCard cardInfo = searchHsCardInfoByCustId(custId);
		AsRealNameAuth authInfo = cardHolderAuthInfoService
				.searchRealNameAuthInfo(custId);
		AsNetworkInfo networkInfo = networkInfoService.searchByCustId(custId);
		allInfo.setBaseInfo(baseInfo);
		allInfo.setCardInfo(cardInfo);
		allInfo.setAuthInfo(authInfo);
		allInfo.setNetworkInfo(networkInfo);
		return allInfo;
	}

	@Override
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
	public PageData<AsUserActionLog> listUserActionLog(String custId,
			String action, String beginDate, String endDate, Page page)
			throws HsException {
		if (null == page) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"必传参数[page对象]为空");
		}
		// 查询总的记录数
		int count = userActionLogMapper.countAsUserActionLog(custId, action,
				beginDate, endDate);
		// 分页查询
		List<AsUserActionLog> resultList = userActionLogMapper
				.pageUserActionInfo(custId, action, beginDate, endDate, page);
		return new PageData<>(count, resultList);
	}

	/**
	 * 持卡人信息入库
	 * 
	 * @param cardHolder
	 *            持卡人信息
	 */
	@SuppressWarnings("unchecked")
	// @Transactional(propagation=Propagation.SUPPORTS)
	public void batchSaveCardHolderInfo(List<CardHolder> dataList)
			throws HsException {
		try {
			int len = dataList.size();// 数据长度
			int fromIndex = 0; // 子列表开始位置
			int toIndex = 0;// 子列表结束位置
			if (len > batchCount) {// 数据过多，需要分批多次入库
				int times = len / batchCount; // 循环次数
				for (int i = 0; i <= times; i++) {
					fromIndex = i * batchCount;
					if (i == times) {// 最后一次循环时，子列表末端为列表长度
						toIndex = len;
					} else {
						toIndex = fromIndex + batchCount;
					}

					if (fromIndex < len) {// 刚好 整数1000条时，最后一次 fromIndex==len
						@SuppressWarnings("rawtypes")
						List subList = dataList.subList(fromIndex, toIndex);
						cardHolderMapper.batchInsertSelective(subList);
					}
				}
			} else {
				cardHolderMapper.batchInsertSelective(dataList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new HsException(
					ErrorCodeEnum.CARDHOLDER_SAVE_ERROR.getValue(),
					e.getMessage());
		}
	}

	/**
	 * 互生卡信息批量入库
	 * 
	 * @param dataList
	 *            互生卡列表
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void batchSaveHsCardInfo(List<HsCard> dataList) throws HsException {
		try {
			int len = dataList.size();// 数据长度
			int fromIndex = 0; // 子列表开始位置
			int toIndex = 0;// 子列表结束位置
			if (len > batchCount) {// 数据过多，需要分批多次入库
				int times = len / batchCount; // 循环次数
				for (int i = 0; i <= times; i++) {
					fromIndex = i * batchCount;
					if (i == times) {// 最后一次循环时，子列表末端为列表长度
						toIndex = len;
					} else {
						toIndex = fromIndex + batchCount;
					}

					if (fromIndex < len) {// 刚好 整数1000条时，最后一次 fromIndex==len
						List subList = dataList.subList(fromIndex, toIndex);
						hsCardMapper.batchInsertSelective(subList);
					}
				}
			} else {
				hsCardMapper.batchInsertSelective(dataList);
			}
		} catch (HsException e) {
			e.printStackTrace();
			throw new HsException(ErrorCodeEnum.HSCARD_SAVE_ERROR.getValue(),
					e.getMessage());
		}
	}

	/**
	 * 网络信息批量入库
	 * 
	 * @param dataList
	 *            网络信息列表
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	// @Transactional(propagation=Propagation.SUPPORTS)
	public void batchSaveNetworkInfo(List<NetworkInfo> dataList)
			throws HsException {
		try {
			int len = dataList.size();// 数据长度
			int fromIndex = 0; // 子列表开始位置
			int toIndex = 0;// 子列表结束位置
			if (len > batchCount) {// 数据过多，需要分批多次入库
				int times = len / batchCount; // 循环次数
				for (int i = 0; i <= times; i++) {
					fromIndex = i * batchCount;
					if (i == times) {// 最后一次循环时，子列表末端为列表长度
						toIndex = len;
					} else {
						toIndex = fromIndex + batchCount;
					}

					if (fromIndex < len) {// 刚好 整数1000条时，最后一次 fromIndex==len
						List subList = dataList.subList(fromIndex, toIndex);
						networkInfoMapper.batchInsertSelective(subList);
					}
				}
			} else {
				networkInfoMapper.batchInsertSelective(dataList);
			}
		} catch (HsException e) {
			e.printStackTrace();
			throw new HsException(ErrorCodeEnum.NETWORK_SAVE_ERROR.getValue(),
					e.getMessage());
		}
	}

	/**
	 * 批量保存密保信息
	 * 
	 * @param dataList
	 *            密保信息列表
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	// @Transactional(propagation=Propagation.SUPPORTS)
	public void batchSavePwdQuestion(List<PwdQuestion> dataList)
			throws HsException {
		try {
			int len = dataList.size();// 数据长度
			int fromIndex = 0; // 子列表开始位置
			int toIndex = 0;// 子列表结束位置
			if (len > batchCount) {// 数据过多，需要分批多次入库
				int times = len / batchCount; // 循环次数
				for (int i = 0; i <= times; i++) {
					fromIndex = i * batchCount;
					if (i == times) {// 最后一次循环时，子列表末端为列表长度
						toIndex = len;
					} else {
						toIndex = fromIndex + batchCount;
					}

					if (fromIndex < len) {// 列表长度刚好 整数1000倍时，最后一次 fromIndex==len
						List subList = dataList.subList(fromIndex, toIndex);
						pwdQuestionMapper.batchInsertSelective(subList);
					}
				}
			} else {
				pwdQuestionMapper.batchInsertSelective(dataList);
			}
		} catch (HsException e) {
			throw new HsException(
					ErrorCodeEnum.PWD_QUESTION_SAVE_ERROR.getValue(),
					e.getMessage());
		}
	}

	/**
	 * 检查证件号是否重复
	 * 
	 * @param asRealNameAuth
	 */
	private void checkIdNo(AsRealNameAuth asRealNameAuth) {
		// if(true) return; //代码未完成，临时返回成功。

		String perCustId = asRealNameAuth.getCustId();
		String cerType = asRealNameAuth.getCerType();
		String cerNo = asRealNameAuth.getCerNo();
		String userName = asRealNameAuth.getUserName();
		if(StringUtils.isBlank(cerType)&&StringUtils.isBlank(cerNo)&&StringUtils.isBlank(userName)){
			//未修改证件信息
			return;
		}
		RealNameAuth realNameAuthOld = commonCacheService
				.searchRealNameAuthInfo(perCustId);
		if (realNameAuthOld == null) {
			throw new HsException(
					ErrorCodeEnum.REALNAMEAUTH_NOT_FOUND.getValue(), perCustId
							+ ErrorCodeEnum.REALNAMEAUTH_NOT_FOUND.getDesc());
		}
		Integer idType = null;
		String idNo = null;
		String perName = null;

		if (StringUtils.isBlank(cerType)) {
			idType = realNameAuthOld.getIdType();
		} else {
			idType = Integer.parseInt(cerType);
		}
		if (StringUtils.isBlank(cerNo)) {
			idNo = realNameAuthOld.getIdNo();
		} else {
			idNo = cerNo;
		}
		if (StringUtils.isBlank(userName)) {
			perName = realNameAuthOld.getPerName();
		} else {
			perName = userName;
		}
		boolean ret = this.isIdNoExist(perCustId, idType, idNo, perName);
		if (ret) {
			throw new HsException(ErrorCodeEnum.ID_IS_REGISTERED.getValue(),
					idNo + "证件号已经被[" + perName + "]实名注册");
		}
	}

	/**
	 * 地区平台修改关联消费者信息并记录修改日志
	 * 
	 * @param asRealNameAuth
	 *            实名注册信息
	 * @param logList
	 *            修改字段列表
	 * @param operCustId
	 *            操作员客户号
	 * @param confirmerCustId
	 *            复审员客户号
	 * @return 持卡人客户号
	 * @throws HsException
	 */
	@Transactional
	public String UpdateCustAndLog(AsRealNameAuth asRealNameAuth,
			List<AsCustUpdateLog> logList, String operCustId,
			String confirmerCustId) throws HsException {
		// 参数校验
		if (logList == null || logList.isEmpty() || asRealNameAuth == null
				|| asRealNameAuth.getCustId() == null) {
			throw new HsException(
					ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"必传参数不能为空,logList==null || logList.isEmpty()||asRealNameReg==null||asRealNameReg.getCustId()==null");
		}
		checkIdNo(asRealNameAuth); //检查证件信息是否已经被使用。

		String perCustId = asRealNameAuth.getCustId();

		// 保存更新字段记录
		this.saveCustUpdateLog(logList, perCustId, operCustId, confirmerCustId);

		// 修改消费者实名认证信息
		// 更新实名认证信息
		RealNameAuth realNameAuth = new RealNameAuth();
		realNameAuth.setAsRealNameAuthInfo(asRealNameAuth);
		this.updateRealNameAuthInfo(realNameAuth);
		return perCustId;
	}
	
	
	/**
	 * 地区平台修改关联消费者信息并记录修改日志
	 * 
	 * @param asRealNameAuth
	 *            实名注册信息
	 * @param logList
	 *            修改字段列表
	 * @param operCustId
	 *            操作员客户号
	 * @param confirmerCustId
	 *            复审员客户号
	 * @return 持卡人客户号
	 * @throws HsException
	 */
	@Transactional
	public String updateCustAndLog(AsRealNameAuth asRealNameAuth,
			List<AsCustUpdateLog> logList, String operCustId,
			String confirmerCustId,String mobile,String perCustId) throws HsException {
		// 参数校验
		if (logList == null || logList.isEmpty() || StringUtils.isBlank(perCustId) || StringUtils.isBlank(operCustId) || StringUtils.isBlank(confirmerCustId)) {
			throw new HsException(
					ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"必传参数不能为空,logList==null || logList.isEmpty()|| perCustId ==null || operCustId == null || confirmerCustId == null");
		}
		if(null != asRealNameAuth){
			asRealNameAuth.setCustId(perCustId);
			checkIdNo(asRealNameAuth); //检查证件信息是否已经被使用。
			RealNameAuth realNameAuth = new RealNameAuth();
			realNameAuth.setAsRealNameAuthInfo(asRealNameAuth);
			this.updateRealNameAuthInfo(realNameAuth);
		}
		// 保存更新字段记录
		this.saveCustUpdateLog(logList, perCustId, operCustId, confirmerCustId);
		// 修改消费者实名认证信息
		// 更新实名认证信息
		
		if (StringUtils.isNotBlank(mobile)) {
			CardHolder cardHolder = new CardHolder();
			cardHolder.setPerCustId(perCustId);
			cardHolder.setMobile(mobile);
			cardHolder.setIsAuthMobile(1);
			cardHolder.setUpdateDate(StringUtils.getNowTimestamp());
			cardHolder.setUpdatedby(perCustId);
			commonCacheService.updateCardHolderInfo(cardHolder);
		}
		
		return perCustId;
	}

	@Transactional
	public String saveCustUpdateLog(List<AsCustUpdateLog> logList,
			String perCustId, String operCustId, String confirmerCustId)
			throws HsException {
		// 参数校验
		if (logList == null || logList.isEmpty() || perCustId == null) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"必传参数不能为空,logList==null || logList.isEmpty()||perCustId==null");
		}

		String operName = null;
		String checkerName = null;
		Operator operator = commonCacheService.searchOperByCustId(operCustId);
		if (operator != null) {
			StringBuilder sb = new StringBuilder();
			sb.append(operator.getOperNo()).append("（");
			sb.append(operator.getOperName()).append("）");
			operName = sb.toString();
		}
		operator = commonCacheService.searchOperByCustId(confirmerCustId);
		if (operator != null) {
			StringBuilder sb = new StringBuilder();
			sb.append(operator.getOperNo()).append("（");
			sb.append(operator.getOperName()).append("）");
			checkerName = sb.toString();
		}
		Timestamp now = StringUtils.getNowTimestamp();

		// 修改日志入库
		for (AsCustUpdateLog asCustUpdateLog : logList) {
			asCustUpdateLog.setPerCustId(perCustId);
			asCustUpdateLog.setUpdatedby(operCustId);
			asCustUpdateLog.setConfirmId(confirmerCustId);
			asCustUpdateLog.setOperName(operName);
			asCustUpdateLog.setConfirmName(checkerName);
			asCustUpdateLog.setUpdateDate(now);
			// 修改日志入库
			asCustUpdateLogMapper.insertSelective(asCustUpdateLog);

		}
		return perCustId;
	}

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
	public PageData<AsCustUpdateLog> listCustUpdateLog(String perCustId,
			String updateFieldName, Page page) {
		AsCustUpdateLog asCustUpdateLog = new AsCustUpdateLog();
		asCustUpdateLog.setPerCustId(perCustId);
		asCustUpdateLog.setUpdateFieldName(updateFieldName);
		List<AsCustUpdateLog> dataList = asCustUpdateLogMapper
				.selectList(asCustUpdateLog);
		for (AsCustUpdateLog custUpdateLog : dataList) {
			if (null != custUpdateLog.getUpdateDate()) {
				custUpdateLog.setUpdateTime(DateUtil.timestampToString(
						custUpdateLog.getUpdateDate(), "yyyy-MM-dd HH:mm:ss"));
			}
		}
		return (PageData<AsCustUpdateLog>) PageUtil.subPage(page, dataList);
	}

	@Override
	public PageData<AsConsumerInfo> listAllConsumerInfo(
			AsQueryConsumerCondition condition, Page page) throws HsException {
		// 数据验证
		if (isBlank(condition)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"必传参数[condition]为空");
		}
		if (isBlank(page)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"必传参数[page]为空");
		}

		// 查询统计数
		int count = cardHolderMapper.countAllConsumerInfo(condition);
		if (count < 1) {
			return null;
		}
		// 查询数据
		List<AsConsumerInfo> list = cardHolderMapper.listAllConsumerInfo(
				condition, page);
		return new PageData<>(count, list);
	}

	@Override
	public Integer getLoginFailTimes(String perResNo) {
		return commonCacheService.getCarderLoginFailTimesCache(perResNo);
	}

	@Override
	public Integer getTransFailTimes(String perResNo) {
		return commonCacheService.getCarderTradeFailTimesCache(perResNo);
	}

	@Override
	public Integer getPwdQuestionFailTimes(String perResNo) {
		return commonCacheService.getCarderPwdQuestionFailTimesCache(perResNo);
	}

}
