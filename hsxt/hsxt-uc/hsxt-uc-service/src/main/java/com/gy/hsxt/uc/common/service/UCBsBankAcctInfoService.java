/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.common.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;
import com.gy.hsxt.uc.bs.api.common.IUCBsBankAcctInfoService;
import com.gy.hsxt.uc.bs.bean.common.BsBankAcctInfo;
import com.gy.hsxt.uc.cache.service.CommonCacheService;
import com.gy.hsxt.uc.consumer.service.UCAsCardHolderService;
import com.gy.hsxt.uc.ent.service.BsEntService;
import com.gy.hsxt.uc.util.CustIdGenerator;

/**
 * 用户中心提供的银行账户服务接口
 * 
 * @Package: com.gy.hsxt.uc.common.service
 * @ClassName: UCBsBankAcctInfoService
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-11-12 下午2:55:15
 * @version V1.0
 */
@Service
public class UCBsBankAcctInfoService implements IUCBsBankAcctInfoService {
	/**
	 * 银行操作接口
	 */
	@Autowired
	private UCAsBankAcctInfoService bankService;
	/**
	 * 持卡人操作接口
	 */
	@Autowired
	private UCAsCardHolderService cardHolderService;
	
	@Autowired
	private CustIdGenerator generator;

	@Autowired
	private BsEntService entService;
	
	@Autowired
	CommonCacheService commonCacheService;
	/**
	 * 查询银行账户信息 通过银行账户ID
	 * 
	 * @param accId
	 *            银行账户编号（主键 序列号）
	 * @param userType
	 *            用户类型
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.bs.api.common.IUCBsBankAcctInfoService#findBankAccByAccId(java.lang.Long,
	 *      com.gy.hsxt.uc.bs.enumerate.UserTypeEnum)
	 */
	@Override
	public BsBankAcctInfo findBankAccByAccId(Long accId, String userType)
			throws HsException {
		AsBankAcctInfo asBankAcct = bankService.findBankAccByAccId(accId,
				userType);
		if(null != asBankAcct){
			BsBankAcctInfo bsAcct = new BsBankAcctInfo();
			BeanUtils.copyProperties(asBankAcct, bsAcct);
			return bsAcct;	
		}
		return null;
	}

	/**
	 * 查询默认银行账户
	 * 
	 * @param custId
	 *            客户号（持卡人、非持卡人、企业）
	 * @param userType
	 *            用户类型
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.bs.api.common.IUCBsBankAcctInfoService#searchDefaultBankAcc(java.lang.String,
	 *      com.gy.hsxt.uc.bs.enumerate.UserTypeEnum)
	 */
	@Override
	public BsBankAcctInfo searchDefaultBankAcc(String custId, String userType)
			throws HsException {
		AsBankAcctInfo asBankAcct = bankService.searchDefaultBankAcc(custId,
				userType);
		BsBankAcctInfo bsAcct = new BsBankAcctInfo();
		BeanUtils.copyProperties(asBankAcct, bsAcct);
		return bsAcct;
	}

	/**
	 * 通过企业的互生号查询企业的银行账户信息
	 * 
	 * @param resNo
	 *            企业互生号
	 * @return 企业的银行账户信息
	 * @throws HsException
	 * @see com.gy.hsxt.uc.bs.api.common.IUCBsBankAcctInfoService#searchDefaultBankAcc(java.lang.String)
	 */
	@Override
	public BsBankAcctInfo searchDefaultBankAcc(String resNo) throws HsException {
		if (StringUtils.isBlank(resNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"互生号为空");
		}
		String rno = resNo.trim();
		String userType = generator.getUserTypeByResNo(rno);
		String custId = "";
		if(UserTypeEnum.CARDER.getType().equals(userType)){
			custId = commonCacheService.findCustIdByResNo(rno);
			CustIdGenerator.isCarderExist(custId, resNo);
		}else if(UserTypeEnum.ENT.getType().equals(userType)){
			custId = commonCacheService.findEntCustIdByEntResNo(rno);
			if(StringUtils.isBlank(custId)){
				throw new HsException(ErrorCodeEnum.ENT_IS_NOT_FOUND.getValue(),
						ErrorCodeEnum.ENT_IS_NOT_FOUND.getDesc()+",entResNo["+rno+"]");
			}
		}else{
			throw new HsException(ErrorCodeEnum.USERTYPE_IS_ILLEGAL.getValue(),ErrorCodeEnum.USERTYPE_IS_ILLEGAL.getDesc());
		}
		BsBankAcctInfo acctInfo = searchDefaultBankAcc(custId, userType);
		return acctInfo;
	}
	
	/**
	 * 修改银行账户转账状态
	 * 
	 * @param accId
	 *            银行账户编号（主键 序列号）
	 * @param isValidAccount
	 *            是否已验证账户1:是 0：否
	 * @param userType
	 *            用户类型
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.common.IUCAsBankAcctInfoService#updateTransStatus(java.lang.Long,
	 *      java.lang.String, com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum)
	 */
	@Override
	public void updateTransStatus(Long accId, String isValidAccount, String userType)
			throws HsException {
		bankService.updateTransStatus(accId, isValidAccount, userType);
	}
}
