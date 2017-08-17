/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.as.api.common;

import java.util.List;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;
import com.gy.hsxt.uc.as.bean.common.AsQkBank;

/**
 * 用户中心提供的银行账户服务接口
 * 
 * @Package: com.gy.hsxt.uc.as.api.common
 * @ClassName: IUCAsBankAcctInfoService
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2015-10-19 下午3:35:07
 * @version V1.0
 */
public interface IUCAsBankAcctInfoService {

	/**
	 * 是否绑定银行
	 * 
	 * @param custId
	 *            客户号
	 * @param userType
	 *            用户类型（持卡人和企业），企业类型使用Operator
	 * @return
	 */
	public boolean isBindAcct(String custId, String userType);

	/**
	 * 绑定银行账户
	 * 
	 * @param acctInfo
	 *            银行账户
	 * @param userType
	 *            用户类型	1：非持卡人 2：持卡人 3：操作员 4：企业
	 * @throws HsException
	 */
	public void bindBank(AsBankAcctInfo acctInfo, String userType)
			throws HsException;

	/**
	 * 解绑银行账户
	 * 
	 * @param accId
	 *            银行账户编号（主键 序列号）
	 * @param userType
	 *            用户类型	1：非持卡人 2：持卡人 3：操作员 4：企业
	 * @throws HsException
	 */
	public void unBindBank(Long accId, String userType)
			throws HsException;

	/**
	 * 解绑快捷银行账户
	 * 
	 * @param accId
	 *            快捷银行账户编号（主键 序列号）
	 * @param userType
	 *            用户类型	1：非持卡人 2：持卡人 3：操作员 4：企业
	 * @throws HsException
	 */
	public void unBindQkBank(Long accId, String userType)
			throws HsException;
	/**
	 * 设置默认银行账户
	 * 
	 * @param custId
	 *            客户号（持卡人、非持卡人、企业）
	 * @param userType
	 *            用户类型	1：非持卡人 2：持卡人 3：操作员 4：企业
	 * @param accId
	 *            银行账户编号（主键 序列号）
	 * @throws HsException
	 */
	public void setDefaultBank(String custId, String userType, Long accId)
			throws HsException;

	/**
	 * 修改银行账户转账状态
	 * 
	 * @param accId
	 *            银行账户编号（主键 序列号）
	 * @param useFlag
	 *            状态（0、从未转账（初始默认值） 1：转账失败 2、转账成功
	 * @param userType
	 *            用户类型
	 * @throws HsException
	 */
	public void updateTransStatus(Long accId, String  useFlag,
			String userType) throws HsException;

	/**
	 * 查询绑定的银行账户列表
	 * 
	 * @param custId
	 *            客户号（持卡人、非持卡人、企业）
	 * @param userType
	 *            用户类型	1：非持卡人 2：持卡人 3：操作员 4：企业
	 * @return
	 * @throws HsException
	 */
	public List<AsBankAcctInfo> listBanksByCustId(String custId,
			String userType) throws HsException;

	/**
	 * 快捷支付签约号同步
	 * 
	 * @param qkBank
	 *            快捷账户信息
	 * @param userType
	 *            用户类型	1：非持卡人 2：持卡人 3：操作员 4：企业
	 * @throws HsException
	 */
	public void setQkBank(AsQkBank qkBank, String userType)
			throws HsException;
	
	/**
	 * 快捷支付签约号同步
	 * 
	 * @param qkBank
	 *            快捷账户信息
	 * @param userType
	 *            用户类型	1：非持卡人 2：持卡人 3：操作员 4：企业
	 * @throws HsException
	 */
	public void setDefaultQkBank(String custId, String userType, Long accId)
			throws HsException;

	/**
	 * 查询绑定的快捷银行账户列表
	 * 
	 * @param custId
	 *            客户号（持卡人、非持卡人、企业）
	 * @param userType
	 *            用户类型	1：非持卡人 2：持卡人 3：操作员 4：企业
	 * @return 返回用户绑定的银行账户信息
	 * @throws HsException
	 */
	public List<AsQkBank> listQkBanksByCustId(String custId,
			String userType) throws HsException;

	/**
	 * 查询银行账户信息 通过银行账户ID
	 * 
	 * @param accId
	 *            银行账户编号（主键 序列号）
	 * @param userType
	 *            用户类型 1：非持卡人 2：持卡人 3：操作员 4：企业
	 * @return
	 * @throws HsException
	 */
	public AsBankAcctInfo findBankAccByAccId(Long accId, String userType)
			throws HsException;
	

	/**
	 * 查询默认银行账户
	 * 
	 * @param custId
	 *            客户号（持卡人、非持卡人、企业）
	 * @param userType
	 *            用户类型 1：非持卡人 2：持卡人 3：操作员 4：企业
	 * @return
	 * @throws HsException
	 */
	public AsBankAcctInfo searchDefaultBankAcc(String custId,
			String userType) throws HsException;
	
	/**
	 * 更新银行账户信息是否默认账户标识接口
	 * @param custId	可好
	 * @param accId		银行账户ID
	 * @param isDefaultAccount	是否默认银行账户	1：是	2：否
	 * @param userType	用户类型	1：非持卡人	2：持卡人	4：企业
	 * @throws HsException	
	 */
	public void updateBankAcctDefaultInfo(String custId,Long accId,Integer isDefaultAccount,String userType)throws HsException;

	/**
     * 通过互生号查询默认银行账户
     * 
     * @param resNo
     *            互生号（支持持卡人、企业，不支持非持卡人）
     * @return
     * @throws HsException
     */
    public AsBankAcctInfo searchDefaultBankAcc(String resNo) throws HsException;
	
}
