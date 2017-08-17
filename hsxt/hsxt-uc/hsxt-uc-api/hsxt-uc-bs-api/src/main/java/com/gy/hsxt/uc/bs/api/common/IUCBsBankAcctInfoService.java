/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.bs.api.common;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.bs.bean.common.BsBankAcctInfo;
import com.gy.hsxt.uc.bs.enumerate.UserTypeEnum;

/**
 * 用户中心提供的银行账户服务接口
 * 
 * @Package: com.gy.hsxt.uc.bs.api.common
 * @ClassName: IUCBsBankAcctInfoService
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-11-12 下午2:52:17
 * @version V1.0
 */
public interface IUCBsBankAcctInfoService {

	/**
	 * 查询银行账户信息 通过银行账户ID
	 * 
	 * @param accId
	 *            银行账户编号（主键 序列号）
	 * @param userType
	 *            用户类型
	 * @return
	 * @throws HsException
	 */
	public BsBankAcctInfo findBankAccByAccId(Long accId, String userType)
			throws HsException;

	/**
	 * 通过客户号查询默认银行账户
	 * 
	 * @param custId
	 *            客户号（持卡人、非持卡人、企业）
	 * @param userType
	 *            用户类型
	 * @return
	 * @throws HsException
	 */
	public BsBankAcctInfo searchDefaultBankAcc(String custId,
			String userType) throws HsException;
	
	/**
     * 通过互生号查询默认银行账户
     * 
     * @param resNo
     *            互生号（支持持卡人、企业，不支持非持卡人）
     * @return
     * @throws HsException
     */
    public BsBankAcctInfo searchDefaultBankAcc(String resNo) throws HsException;
    
    
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
	public void updateTransStatus(Long accId, String isValidAccount, String userType)
			throws HsException;
}
