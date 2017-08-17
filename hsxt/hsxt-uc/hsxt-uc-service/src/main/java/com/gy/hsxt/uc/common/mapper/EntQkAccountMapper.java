package com.gy.hsxt.uc.common.mapper;

import java.util.List;

import com.gy.hsxt.uc.common.bean.EntQkAccount;

public interface EntQkAccountMapper {
    /**
	 * 删除企业快捷支付账户 通过主键ID
	 * 
	 * @param accId ID
	 * @return
	 */
	int deleteByPrimaryKey(Long accId);

	/**
	 * 插入企业快捷支付账户信息
	 * 
	 * @param record 快捷支付账户对象
	 * @return
	 */
	int insertSelective(EntQkAccount record);

	/**
	 * 查询企业快捷支付账户信息 通过主键ID
	 * 
	 * @param accId ID
	 * @return
	 */
	EntQkAccount selectByPrimaryKey(Long accId);

	/**
	 * 更新企业快捷支付账户 信息
	 * 
	 * @param record 快捷支付账户对象
	 * @return
	 */
	int updateByPrimaryKeySelective(EntQkAccount record);

	/**
	 * 查询企业快捷支付账户列表
	 * 
	 * @param custId 客户号
	 * @return
	 */
	List<EntQkAccount> listAccountByCustId(String custId);
}