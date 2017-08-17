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
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.uc.bs.api.consumer.IUCBsCardHolderAuthInfoService;
import com.gy.hsxt.uc.bs.bean.consumer.BsRealNameAuth;
import com.gy.hsxt.uc.bs.bean.consumer.BsRealNameReg;
import com.gy.hsxt.uc.cache.service.CommonCacheService;
import com.gy.hsxt.uc.consumer.bean.CardHolder;
import com.gy.hsxt.uc.consumer.bean.RealNameAuth;
import com.gy.hsxt.uc.util.CustIdGenerator;

/**
 * 
 * @Package: com.gy.hsxt.uc.consumer.service
 * @ClassName: UCBsCardHolderAuthInfoService
 * @Description: 持卡人证件信息管理（BS系统调用）
 * 
 * @author: tianxh
 * @date: 2015-10-27 下午3:04:18
 * @version V1.0
 */
@Service
public class UCBsCardHolderAuthInfoService implements IUCBsCardHolderAuthInfoService {
	final static String MOUDLENAME="UCBsCardHolderAuthInfoService";
	@Autowired
	private UCAsCardHolderService uCAsCardHolderService;
	@Autowired
	CommonCacheService commonCacheService;

	/**
	 * 实名认证(insert)
	 * 
	 * @param bsRealNameAuthInfo
	 *            实名认证信息
	 * @param operCustId
	 *            操作员客户号
	 * @return Map<String,String> key="resNo" 获取互生号 key="isRealNameAuth"
	 *         获取实名认证状态
	 * @throws HsException
	 * @see com.gy.hsxt.uc.bs.api.consumer.IUCBsCardHolderAuthInfoService#authByRealName(com.gy.hsxt.uc.bs.bean.consumer.BsRealNameAuth,
	 *      java.lang.String)
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Map<String, String> authByRealName(BsRealNameAuth bsRealNameAuth, String operCustId)
			throws HsException {
		bsRealNameAuth.checkRealNameInfoComplete(bsRealNameAuth, operCustId);// 先检查实名认证的信息完整
		String custId = bsRealNameAuth.getCustId().trim();
		String operatorId = operCustId.trim();
		Timestamp now = StringUtils.getNowTimestamp();
		CardHolder cardHolder = commonCacheService.searchCardHolderInfo(custId);
		CustIdGenerator.isCarderExist(cardHolder, custId);
		Integer isRealNameStatus = cardHolder.getIsRealnameAuth();
		if (2 != isRealNameStatus) {// 非实名注册不能做实名认证业务
			throw new HsException(ErrorCodeEnum.REALNAME_STATUS_NOT_REGISTERED.getValue(),
					isRealNameStatus+ErrorCodeEnum.REALNAME_STATUS_NOT_REGISTERED.getDesc()+custId);
		}
		// 实名认证信息入库
		RealNameAuth realNameAuth = new RealNameAuth();
		realNameAuth.setBsRealNameAuthInfo(bsRealNameAuth);
		realNameAuth.setUpdatedby(operatorId);
		realNameAuth.setUpdateDate(now);
		realNameAuth.setRealNameAuthDate(now);
		uCAsCardHolderService.updateRealNameAuthInfo(realNameAuth);
		// 更新实名状态
		cardHolder = new CardHolder();
		cardHolder.setIsRealnameAuth(3);
		cardHolder.setPerCustId(custId);
		cardHolder.setUpdateDate(now);
		cardHolder.setUpdatedby(operatorId);
		cardHolder.setRealnameAuthDate(now);
		commonCacheService.updateCardHolderInfo(cardHolder);
		// 查询持卡人信息
		cardHolder = commonCacheService.searchCardHolderInfo(custId);
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("resNo", StringUtils.nullToEmpty(cardHolder.getPerResNo()));
		resultMap.put("isRealNameAuth", String.valueOf(cardHolder.getIsRealnameAuth()));
		return resultMap;
	}

	/**
	 * 通过客户号查询实名注册信息
	 * 
	 * @param perCustId
	 *            客户号
	 * @return 实名注册信息(
	 * @throws HsException
	 * @see com.gy.hsxt.uc.bs.api.consumer.IUCBsCardHolderAuthInfoService#searchRealNameRegInfo(java.lang.String)
	 */
	@Override
	public BsRealNameReg searchRealNameRegInfo(String perCustId) throws HsException {
		if (StringUtils.isBlank(perCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "传入参数客户号为空");
		}
		String custId = perCustId.trim();
		BsRealNameReg bsRealNameReg = new BsRealNameReg();
		RealNameAuth realNameAuth = commonCacheService.searchRealNameAuthInfo(custId);
		if (null != realNameAuth) {
			realNameAuth.fillBsRealNameRegInfo(bsRealNameReg);
		}
		String realNameStatus = uCAsCardHolderService.findAuthStatusByCustId(custId);
		bsRealNameReg.setRealNameStatus(realNameStatus);
		return bsRealNameReg;
	}

	/**
	 * 通过客户号查询实名认证信息
	 * 
	 * @param perCustId
	 *            客户号
	 * @return Map<String,Object>
	 *         实名认证信息(key="bsRealNameAuth")和实名状态(key="realNameStatus")
	 * @throws HsException
	 * @see com.gy.hsxt.uc.bs.api.consumer.IUCBsCardHolderAuthInfoService#searchRealNameAuthInfo(java.lang.String)
	 */
	@Override
	public BsRealNameAuth searchRealNameAuthInfo(String perCustId) throws HsException {
		if (StringUtils.isBlank(perCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "传入参数客户号为空");
		}
		String custId = perCustId.trim();
		BsRealNameAuth bsRealNameReg = new BsRealNameAuth();
		RealNameAuth realNameAuth = commonCacheService.searchRealNameAuthInfo(custId);
		if (null != realNameAuth) {
			realNameAuth.fillBsRealNameAuthInfo(bsRealNameReg);
		}
		String realNameStatus = uCAsCardHolderService.findAuthStatusByCustId(custId);
		bsRealNameReg.setRealNameStatus(realNameStatus);
		return bsRealNameReg;
	}

	/**
	 * 查询持卡人实名认证状态
	 * 
	 * @param custId
	 *            客户号
	 * @return String 实名状态
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderAuthInfoService#findAuthStatusByCustId(java.lang.String)
	 */
	@Override
	public String findAuthStatusByCustId(String perCustId) throws HsException {
		return uCAsCardHolderService.findAuthStatusByCustId(perCustId);
	}

	/**
	 * 修改实名认证信息
	 * 
	 * @param bsRealNameAuthInfo
	 *            实名认证信息
	 * @param operCustId
	 *            操作员客户号
	 * @throws HsException
	 * @see com.gy.hsxt.uc.bs.api.consumer.IUCBsCardHolderAuthInfoService#updateRealNameAuthInfo(com.gy.hsxt.uc.bs.bean.consumer.BsRealNameAuth,
	 *      java.lang.String)
	 */
	@Deprecated
	public void updateRealNameAuthInfo(BsRealNameAuth bsRealNameAuthInfo, String operCustId)
			throws HsException {
		if (StringUtils.isBlank(operCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "传入参数操作员客户号为空");
		}
		if (null == bsRealNameAuthInfo) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "传入参数实名认证信息为空");
		}
		// 更新实名认证信息
		RealNameAuth realNameAuth = new RealNameAuth();
		realNameAuth.setBsRealNameAuthInfo(bsRealNameAuthInfo);
		realNameAuth.setUpdatedby(operCustId);
		Timestamp now = StringUtils.getNowTimestamp();
		realNameAuth.setUpdateDate(now);
		uCAsCardHolderService.updateRealNameAuthInfo(realNameAuth);
		
		
//		CardHolder cardHolder = new CardHolder();
//		// 设置重要信息变更期间标识为 否
//		cardHolder.setPerCustId(realNameAuth.getPerCustId());
//		cardHolder.setUpdatedby(operCustId.trim());
//		cardHolder.setIsKeyinfoChanged(0);// 是否重要信息变更期间更改为0（1:是0：否）
//		cardHolder.setIsRealnameAuth(3);
//		commonCacheService.updateCardHolderInfo(cardHolder, false);
	}

	@Override
	public void updateMainInfoApplyInfo(BsRealNameAuth bsRealNameAuthInfo, String operCustId)
			throws HsException {
		if (StringUtils.isBlank(operCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "传入参数操作员客户号为空");
		}
		if (null == bsRealNameAuthInfo) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "传入参数实名认证信息为空");
		}
		// 更新实名认证信息
		RealNameAuth realNameAuth = new RealNameAuth();
		realNameAuth.setBsRealNameAuthInfo(bsRealNameAuthInfo);
		uCAsCardHolderService.updateRealNameAuthInfo(realNameAuth);
		CardHolder cardHolder = new CardHolder();
		// 设置重要信息变更期间标识为 否
		cardHolder.setPerCustId(realNameAuth.getPerCustId());
		cardHolder.setUpdatedby(operCustId.trim());
		cardHolder.setIsKeyinfoChanged(0);// 是否重要信息变更期间更改为0（1:是0：否）
		commonCacheService.updateCardHolderInfo(cardHolder);
	}
	
	/**
	 * 检查证件是否已被使用
	 * @param perCustId 客户号
	 * @param idType 证件类型1：身份证 2：护照 3：营业执照
	 * @param idNo 证件号码
	 * @param perName 姓名
	 * @return 证件已被实名注册则返回true
	 */
	public boolean isIdNoExist(String perCustId,Integer idType,String idNo,String perName){

		if (null == idType) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "传入参数证件类型不能为空");
		}
		if (StringUtils.isBlank(idNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "传入参数证件号码不能为空");
		}
		if (null == perCustId) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "传入参数perCustId不能为空");
		}
		if (null == perName) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "传入参数perName不能为空");
		}

		return uCAsCardHolderService.isIdNoExist(perCustId, idType, idNo, perName);
		
	}
	
}
