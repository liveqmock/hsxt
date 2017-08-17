/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.consumer.service;

import java.sql.Timestamp;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.RandomGuidAgent;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderStatusInfoService;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.uc.as.bean.enumerate.ChannelTypeEnum;
import com.gy.hsxt.uc.cache.service.CommonCacheService;
import com.gy.hsxt.uc.common.service.CommonService;
import com.gy.hsxt.uc.common.service.UCAsPwdService;
import com.gy.hsxt.uc.consumer.bean.CardHolder;
import com.gy.hsxt.uc.consumer.bean.HsCard;
import com.gy.hsxt.uc.consumer.bean.UserActionLog;
import com.gy.hsxt.uc.consumer.mapper.UserActionLogMapper;
import com.gy.hsxt.uc.util.CustIdGenerator;
import com.gy.hsxt.uc.util.StringEncrypt;

/**
 * 
 * @Package: com.gy.hsxt.uc.consumer.service
 * @ClassName: UCAsCardHolderStatusInfoService
 * @Description: 持卡人状态信息管理
 * 
 * @author: tianxh
 * @date: 2015-10-27 上午11:31:09
 * @version V1.0
 */
@Service
public class UCAsCardHolderStatusInfoService implements
		IUCAsCardHolderStatusInfoService {
	@Autowired
	private UCAsCardHolderService uCAsCardHolderService;
	@Autowired
	private UCBsCardHolderStatusInfoService cardHolderStatusInfoService;
	@Autowired
	private CommonCacheService commonCacheService;
	@Autowired
	private UserActionLogMapper userActionLogMapper;
	@Autowired
	UCAsPwdService asPwdService;
	@Autowired
	CommonService commonService;

	/**
	 * 通过客户号查询互生卡的信息
	 * 
	 * @param custId
	 *            持卡人客户号
	 * @return Map key="perResNo"获取互生号，key="cardStatus"获取互生卡状态
	 * @throws HsException
	 */
	@Override
	public Map<String, String> searchHsCardStatusInfoBycustId(String perCustId)
			throws HsException {
		return uCAsCardHolderService.searchHsCardStatusInfoBycustId(perCustId);
	}

	/**
	 * 手机是否已验证
	 * 
	 * @param custId
	 *            客户号
	 * @param mobile
	 *            手机号码
	 * @return trus:已验证 false:未验证
	 * @throws HsException
	 *             java.lang.String)
	 */
	@Override
	public Boolean isVerifiedMobile(String perCustId, String mobile)
			throws HsException {
		if (StringUtils.isBlank(perCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数客户号为空");
		}
		if (StringUtils.isBlank(mobile)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数客手机号码为空");
		}
		String custId = perCustId.trim();
		String mobileNo = mobile.trim();
		CardHolder cardHolder = commonCacheService.searchCardHolderInfo(custId);
		CustIdGenerator.isCarderExist(cardHolder, custId);
		String correctMobile = StringUtils.nullToEmpty(cardHolder.getMobile());
		if (!mobileNo.equals(correctMobile)) {
			throw new HsException(ErrorCodeEnum.MOBILE_NOT_CORRECT.getValue(),
					ErrorCodeEnum.MOBILE_NOT_CORRECT.getDesc());
		}
		Integer isAuthMobile = cardHolder.getIsAuthMobile();
		return null == isAuthMobile ? false : 1 == isAuthMobile ? true : false;
	}

	/**
	 * 互生卡挂失/解挂
	 * 
	 * @param custId
	 *            客户号
	 * @param loginPwd
	 *            （AES加密）
	 * @param randomToken
	 *            （解密秘钥）
	 * @param status
	 *            (挂失使用2：挂失 解挂使用1：启用 )
	 * @param lossReason
	 *            (挂失原因（挂失必填；解挂不填，解挂成功后清空)
	 * @throws HsException
	 *             java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public void updateHsCardStatus(String perCustId, String loginPassword,
			String randomToken, Integer status, String lossReason)
			throws HsException {
		if (StringUtils.isBlank(perCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数客户号为空");
		}
		if (StringUtils.isBlank(loginPassword)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数登录密码为空");
		}
		if (null == status) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数互生卡状态为空");
		}
		if (StringUtils.isBlank(randomToken)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数AES秘钥为空");
		}
		// if (CardLossType.LOSS.getCode() == status &&
		// StringUtils.isBlank(lossReason)) {
		// throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
		// "传入参数挂失原因为空（挂失必填）为空");
		// }

		Timestamp now = StringUtils.getNowTimestamp();
		String custId = perCustId.trim();
		CardHolder cardHolder = commonCacheService.searchCardHolderInfo(custId);// 查询持卡人信息
		CustIdGenerator.isCarderExist(cardHolder, custId);

		String loginPwd = StringEncrypt.decrypt(loginPassword, randomToken);

		boolean isLoginPass = asPwdService.checkCardHolderkLoginPwd(cardHolder,
				loginPwd);
		if (!isLoginPass) {
			throw new HsException(ErrorCodeEnum.PWD_LOGIN_ERROR.getValue(),
					custId + ErrorCodeEnum.PWD_LOGIN_ERROR.getDesc() + loginPwd);
		}
		// 挂失或者解挂业务
		updateHsCardStatus(perCustId, status, lossReason, perCustId);
	}

	/**
	 * 持卡人身故状态更新
	 * 
	 * @param perCustId
	 *            持卡人客户号
	 * @param status
	 *            1：启用、2：挂失、3：停用
	 * @param lossReason
	 *            挂失原因,可为空
	 * @param operator
	 *            操作者id
	 * @throws HsException
	 */
	@Transactional
	public void updateHsCardStatus(String perCustId, Integer status,
			String lossReason, String operator) throws HsException {
		if (StringUtils.isBlank(perCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数客户号为空");
		}

		if (null == status) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数互生卡状态为空");
		}

		Timestamp now = StringUtils.getNowTimestamp();
		String custId = perCustId.trim();
		CardHolder cardHolder = commonCacheService.searchCardHolderInfo(custId);// 查询持卡人信息
		CustIdGenerator.isCarderExist(cardHolder, custId);

		//
		HsCard hsCard = new HsCard();
		hsCard.setPerCustId(custId);
		hsCard.setCardStatus(status);
		hsCard.setUpdatedby(operator);
		hsCard.setUpdateDate(now);
		lossReason = StringUtils.nullToEmpty(lossReason);

		UserActionLog userActionLog = new UserActionLog();
		if (1 == status.intValue()) {
			hsCard.setLossReason("");// 清空挂失原因
			userActionLog.setAction("1");
		} else if (2 == status.intValue()) {
			// 设置挂失原因
			hsCard.setLossReason(lossReason);
			hsCard.setLossDate(now);
			userActionLog.setAction("2");
		} else if (3 == status.intValue()) {
			// 删除登录缓存
			commonService.kickOutCardHolder(custId);
		
			userActionLog.setAction("3");
		} else {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数互生卡状态只接收  1，2，3");
		}
		userActionLog.setCreateDate(now);
		userActionLog.setLogId(RandomGuidAgent.getStringGuid(""));
		userActionLog.setCreatedby(operator);
		userActionLog.setCustId(custId);
		userActionLog.setRemark(lossReason);
		uCAsCardHolderService.updateHsCardInfo(hsCard);
		// 保存用户操作记录日志
		uCAsCardHolderService.saveUserActionRecord(userActionLog);
	}

	/**
	 * 查询重要信息变更状态
	 * 
	 * @param custId
	 *            客户号
	 * @return 持卡人重要信息变更状态（true:是 false:否）
	 * @throws HsException
	 */
	@Override
	public boolean isMainInfoApplyChanging(String custId) throws HsException {
		if (StringUtils.isBlank(custId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数客户号为空");
		}
		return cardHolderStatusInfoService.isMainInfoApplyChanging(custId
				.trim());
	}

}