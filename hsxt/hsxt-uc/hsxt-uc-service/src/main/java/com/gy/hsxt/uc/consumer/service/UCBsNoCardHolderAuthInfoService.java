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
import com.gy.hsxt.uc.bs.api.consumer.IUCBsNoCardHolderAuthInfoService;
import com.gy.hsxt.uc.bs.bean.consumer.BsRealNameAuth;
import com.gy.hsxt.uc.cache.service.CommonCacheService;
import com.gy.hsxt.uc.consumer.bean.NcRealNameAuth;
import com.gy.hsxt.uc.consumer.bean.NoCardHolder;
import com.gy.hsxt.uc.consumer.mapper.NcRealNameAuthMapper;
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
public class UCBsNoCardHolderAuthInfoService implements IUCBsNoCardHolderAuthInfoService {
	private static final String MOUDLENAME = "com.gy.hsxt.uc.consumer.service.UCBsNoCardHolderAuthInfoService";
	@Autowired
	UCAsNoCardHolderAuthInfoService noCardHolderAuthInfoService;
	@Autowired
	CommonCacheService commonCacheService;
	@Autowired
	NcRealNameAuthMapper ncRealNameAuthMapper;
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
	 *      java.lang.String)
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Map<String, String> authByRealName(BsRealNameAuth bsRealNameAuth, String operCustId)
			throws HsException {
		bsRealNameAuth.checkRealNameInfoComplete(bsRealNameAuth, operCustId);// 先检查实名认证的信息完整
		String perCustId = bsRealNameAuth.getCustId().trim();
		String operatorId = operCustId.trim();
		Timestamp now = StringUtils.getNowTimestamp();
		NoCardHolder noCardHolder = commonCacheService.searchNoCardHolderInfo(perCustId);
		CustIdGenerator.isNoCarderExist(noCardHolder, perCustId);
		Integer isRealNameStatus = noCardHolder.getIsRealnameAuth();
		if (2 != isRealNameStatus) {// 非实名注册不能做实名认证业务
			throw new HsException(ErrorCodeEnum.REALNAME_STATUS_NOT_REGISTERED.getValue(),
					ErrorCodeEnum.REALNAME_STATUS_NOT_REGISTERED.getDesc());
		}
		// 实名认证信息入库
		NcRealNameAuth ncRealNameAuth = new NcRealNameAuth();
		ncRealNameAuth.setBsRealNameAuthInfo(bsRealNameAuth);
		ncRealNameAuth.setUpdatedby(operatorId);
		ncRealNameAuth.setUpdateDate(now);
		commonCacheService.updateNoCarderRealNameAuthInfo(ncRealNameAuth);
		// 更新实名状态
		noCardHolder = new NoCardHolder();
		noCardHolder.setIsRealnameAuth(3);
		noCardHolder.setPerCustId(perCustId);
		noCardHolder.setUpdateDate(now);
		noCardHolder.setUpdatedby(operatorId);
		noCardHolder.setRealnameAuthDate(now);
		commonCacheService.updateNoCardHolderInfo(noCardHolder);
		// 查询持卡人信息
		noCardHolder = commonCacheService.searchNoCardHolderInfo(perCustId);
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("mobile", StringUtils.nullToEmpty(noCardHolder.getMobile()));
		resultMap.put("isRealNameAuth", String.valueOf(noCardHolder.getIsRealnameAuth()));
		return resultMap;
	}


	/**
	 * 通过客户号查询实名认证信息
	 * 
	 * @param perCustId
	 *            客户号
	 * @return Map<String,Object>
	 *         实名认证信息(key="bsRealNameAuth")和实名状态(key="realNameStatus")
	 * @throws HsException
	 */
	@Override
	public BsRealNameAuth searchRealNameAuthInfo(String perCustId) throws HsException {
		if (StringUtils.isBlank(perCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "传入参数客户号为空");
		}
		BsRealNameAuth bsRealNameReg = new BsRealNameAuth();
		NcRealNameAuth ncRealNameAuth = commonCacheService.searchNoCarderRealNameAuthInfo(perCustId);
		if (null != ncRealNameAuth) {
			ncRealNameAuth.fillBsRealNameAuthInfo(bsRealNameReg);
		}
		String realNameStatus = noCardHolderAuthInfoService.findAuthStatusByCustId(perCustId);
		bsRealNameReg.setRealNameStatus(realNameStatus);
		return bsRealNameReg;
	}

	/**
	 * 查询非持卡人实名认证状态
	 * 
	 * @param custId
	 *            客户号
	 * @return String 实名状态
	 * @throws HsException
	 */
	@Override
	public String findAuthStatusByCustId(String perCustId) throws HsException {
		return noCardHolderAuthInfoService.findAuthStatusByCustId(perCustId);
	}

	/**
	 * 修改实名认证信息
	 * 
	 * @param bsRealNameAuthInfo
	 *            实名认证信息
	 * @param operCustId
	 *            操作员客户号
	 * @throws HsException
	 */
	@Override
	public void updateRealNameAuthInfo(BsRealNameAuth bsRealNameAuthInfo, String operCustId)
			throws HsException {
		if (StringUtils.isBlank(operCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "传入参数操作员客户号为空");
		}
		if (null == bsRealNameAuthInfo) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "传入参数实名认证信息为空");
		}
		// 更新实名认证信息
		NcRealNameAuth ncRealNameAuth = new NcRealNameAuth();
		ncRealNameAuth.setBsRealNameAuthInfo(bsRealNameAuthInfo);
		commonCacheService.updateNoCarderRealNameAuthInfo(ncRealNameAuth);
		NoCardHolder noCardHolder = new NoCardHolder();
		// 设置重要信息变更期间标识为 否
		noCardHolder.setPerCustId(ncRealNameAuth.getPerCustId());
		noCardHolder.setUpdatedby(operCustId.trim());
		noCardHolder.setIsKeyinfoChanged(0);// 是否重要信息变更期间更改为0（1:是0：否）
		noCardHolder.setIsRealnameAuth(3);
		commonCacheService.updateNoCardHolderInfo(noCardHolder);
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
		NcRealNameAuth ncRealNameAuth = new NcRealNameAuth();
		ncRealNameAuth.setBsRealNameAuthInfo(bsRealNameAuthInfo);
		commonCacheService.updateNoCarderRealNameAuthInfo(ncRealNameAuth);
		NoCardHolder noCardHolder = new NoCardHolder();
		// 设置重要信息变更期间标识为 否
		noCardHolder.setPerCustId(ncRealNameAuth.getPerCustId());
		noCardHolder.setUpdatedby(operCustId.trim());
		noCardHolder.setIsKeyinfoChanged(0);// 是否重要信息变更期间更改为0（1:是0：否）
		commonCacheService.updateNoCardHolderInfo(noCardHolder);
	}

	/**
	 * 检查证件是否已被使用
	 * @param idType 证件类型1：身份证 2：护照 3：营业执照
	 * @param idNo 证件号码
	 * @return 证件已被实名注册则返回true
	 */
	@Override
	public boolean isIdNoExist(String perCustId, Integer idType, String idNo) {
		if (StringUtils.isBlank(perCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "传入参数客户号不能为空");
		}
		if (null == idType) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "传入参数证件类型不能为空");
		}
		if (StringUtils.isBlank(idNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "传入参数证件号码不能为空");
		}
		
		return   noCardHolderAuthInfoService.isIdNoExist(perCustId, idType, idNo, null);
	}
}
