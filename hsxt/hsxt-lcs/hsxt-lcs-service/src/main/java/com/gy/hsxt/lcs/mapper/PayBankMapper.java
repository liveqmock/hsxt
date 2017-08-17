/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.lcs.mapper;

import java.util.List;

import com.gy.hsxt.lcs.bean.PayBank;

public interface PayBankMapper {

	/**
	 * 查询银联支付所有银行列表
	 * @return
	 */
	public List<PayBank> queryPayBankAll() ;
	
	/**
	 * 根据银行简码查询银行信息
	 * @param bankCode
	 * @return
	 */
	public PayBank queryPayBankByCode(String bankCode);
}
