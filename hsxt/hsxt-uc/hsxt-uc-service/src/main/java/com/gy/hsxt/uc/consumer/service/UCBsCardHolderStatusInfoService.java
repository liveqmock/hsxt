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
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.uc.bs.api.consumer.IUCBsCardHolderStatusInfoService;
import com.gy.hsxt.uc.cache.service.CommonCacheService;
import com.gy.hsxt.uc.consumer.bean.CardHolder;
import com.gy.hsxt.uc.consumer.bean.HsCard;
import com.gy.hsxt.uc.consumer.bean.UserActionLog;
import com.gy.hsxt.uc.util.CustIdGenerator;
import com.gy.hsxt.uc.util.StringEncrypt;

/**
 * 
 * @Package: com.gy.hsxt.uc.consumer.service
 * @ClassName: UCBsCardHolderStatusInfoService
 * @Description: 持卡人状态信息管理（BS业务系统调用）
 * 
 * @author: tianxh
 * @date: 2015-10-27 下午3:04:39
 * @version V1.0
 */
@Service
public class UCBsCardHolderStatusInfoService implements
		IUCBsCardHolderStatusInfoService {

	@Autowired
	private UCAsCardHolderService uCAsCardHolderService;
	@Autowired
	private UCAsCardHolderStatusInfoService uCAsCardHolderStatusInfoService;
	
	@Autowired
	private CommonCacheService commonCacheService;
	/**
	 * 通过客户号查询互生卡状态信息
	 * 
	 * @param custId
	 *            持卡人客户号
	 * @return Map key="perResNo"获取互生号，key="cardStatus"获取互生卡状态
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderStatusInfoService#searchHsCardInfoBycustId(java.lang.String)
	 */
	@Override
	public Map<String, String> searchHsCardStatusInfoBycustId(String perCustId)
			throws HsException {
		return uCAsCardHolderService.searchHsCardStatusInfoBycustId(perCustId);
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
	public boolean isMainInfoApplyChanging(String perCustId) throws HsException {
		if (StringUtils.isBlank(perCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数客户号为空");
		}
		CardHolder cardHolder = commonCacheService.searchCardHolderInfo(perCustId);
		CustIdGenerator.isCarderExist(cardHolder, perCustId);
		Integer isChag = cardHolder.getIsKeyinfoChanged();
		return null == isChag ? false : 1 == isChag ? true : false;
	}

	/**
	 * 重要信息变更申请
	 * 
	 * @param custId
	 *            客户号
	 * @param status
	 *            重要信息变更状态
	 * @throws HsException
	 * @see com.gy.hsxt.uc.bs.api.consumer.IUCBsCardHolderStatusInfoService#changeApplyMainInfoStatus(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public void changeApplyMainInfoStatus(String perCustId, String status)
			throws HsException {
		if (StringUtils.isBlank(perCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数客户号为空");
		}
		if (StringUtils.isBlank(status)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入重要信息变更状态为空");
		}
		String custId = perCustId.trim();
		String chagStatus = status.trim();
		CardHolder cardHolder = new CardHolder();
		cardHolder.setPerCustId(custId);
		cardHolder.setIsKeyinfoChanged(Integer.parseInt(chagStatus));
		cardHolder.setUpdateDate(StringUtils.getNowTimestamp());
		cardHolder.setUpdatedby(custId);
		commonCacheService.updateCardHolderInfo(cardHolder);
	}
	
	/**
	 * 持卡人身故时调用	
	 * @param perCustId 持卡人客户号
	 * @param status 1：启用、2：挂失、3：停用
	 * @param lossReason 挂失原因,可为空
	 * @param operator 操作员id
	 * @throws HsException
	 */
	@Transactional
	public void updateHsCardStatus(String perCustId,  Integer status, String lossReason,String operator)
			throws HsException {
		uCAsCardHolderStatusInfoService.updateHsCardStatus(perCustId, status, lossReason, operator);
	}
}
