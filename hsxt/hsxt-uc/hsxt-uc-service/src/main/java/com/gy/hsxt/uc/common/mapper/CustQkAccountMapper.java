package com.gy.hsxt.uc.common.mapper;

import java.util.List;

import com.gy.hsxt.uc.common.bean.CustQkAccount;

public interface CustQkAccountMapper {
    
    /**
	 * 根据ID删除
	 * @param accId
	 * @return
	 */
	int deleteByPrimaryKey(Long accId);

	/**
	 * 添加消费者快捷支付账户
	 * @param record
	 * @return
	 */
	int insertSelective(CustQkAccount record);
	/**
	 * 根据ID查询消费者快捷支付账户
	 * @param accId
	 * @return
	 */
	CustQkAccount selectByPrimaryKey(Long accId);

	/**
	 * 更新消费者快捷支付账户
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeySelective(CustQkAccount record);

	/**
	 * 根据客户号查询消费者快捷支付账户
	 * @param custId
	 * @return
	 */
	List<CustQkAccount> listAccountByCustId(String custId);

	/**
	 * 根据ID更新已输入的快捷帐户
	 * @param record
	 * @return
	 */
	int updateByUniqueKeySelective(CustQkAccount record);
}