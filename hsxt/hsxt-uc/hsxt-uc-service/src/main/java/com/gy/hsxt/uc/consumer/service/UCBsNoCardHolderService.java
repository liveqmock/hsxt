/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.consumer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.BeanCopierUtils;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.uc.bs.api.consumer.IUCBsNoCardHolderService;
import com.gy.hsxt.uc.bs.bean.consumer.BsNoCardHolder;
import com.gy.hsxt.uc.cache.service.CommonCacheService;
import com.gy.hsxt.uc.consumer.bean.NoCardHolder;
import com.gy.hsxt.uc.util.CustIdGenerator;

/**
 * 
 * @Package: com.gy.hsxt.uc.consumer.service
 * @ClassName: UCBsNoCardHolderService
 * @Description: TODO
 * 
 * @author: tianxh
 * @date: 2015-10-22 下午3:35:38
 * @version V1.0
 */
@Service
public class UCBsNoCardHolderService implements IUCBsNoCardHolderService {
	@Autowired
	UCAsNoCardHolderService uCAsNoCardHolderService;
	@Autowired
	CommonCacheService commonCacheService;
	/**
	 * 销户
	 * 
	 * @param perCustId
	 *            非持卡人客户号
	 * @param operCustId
	 *            操作员客户号
	 * @throws HsException
	 *      java.lang.String)
	 */
	@Override
	public void closeAccout(String perCustId, String operCustId)
			throws HsException {
		uCAsNoCardHolderService.closeAccout(perCustId, operCustId);
	}

	/**
	 * 通过手机号码查找非持卡人信息
	 * 
	 * @param mobile
	 *            手机号码
	 * @return 非持卡人信息
	 */
	@Override
	public BsNoCardHolder searchNoCardHolderInfoByMobile(String mobile)throws HsException {
		String custId = findCustIdByMobile(mobile);
		if (StringUtils.isBlank(custId)) {
			throw new HsException(ErrorCodeEnum.USER_NOT_FOUND.getValue(),
					"手机号的用户不存在, 手机号：" + mobile);
		}
		NoCardHolder noCardHolder = commonCacheService
				.searchNoCardHolderInfo(custId);
		CustIdGenerator.isNoCarderExist(noCardHolder,custId);
		BsNoCardHolder bsNoCardHolder = new BsNoCardHolder();
		BeanCopierUtils.copy(noCardHolder, bsNoCardHolder);
		return bsNoCardHolder;
	}

	/**
	 * 通过手机号码查找客户号
	 * 
	 * @param mobile
	 *            手机号码
	 * @return 客户号
	 */
	@Override
	public String findCustIdByMobile(String mobile) throws HsException{
		if (StringUtils.isBlank(mobile)) {
			throw new HsException(ErrorCodeEnum.USER_NOT_FOUND.getValue(),
					"手机号的用户不存在, 手机号：" + mobile);
		}
		String custId = commonCacheService.findCustIdByMobile(mobile);
		CustIdGenerator.isNoCarderExist(custId, mobile);
		return custId;
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
	 *      java.lang.String, java.util.Date)
	 */
	@Override
	public void updateLoginInfo(String custId, String loginIp, String dateStr) {
		uCAsNoCardHolderService.updateLoginInfo(custId, loginIp, dateStr);
	}
}
