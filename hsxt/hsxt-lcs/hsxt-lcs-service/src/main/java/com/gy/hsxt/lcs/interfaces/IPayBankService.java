/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.lcs.interfaces;

import java.util.List;

import com.gy.hsxt.lcs.bean.PayBank;
/**
 * 
 * 银联列表查询接口
 * @Package: com.gy.hsxt.lcs.interfaces  
 * @ClassName: IUnionListService 
 * @Description: TODO
 *
 * @author: liyh 
 * @date: 2015-11-18 上午11:33:07 
 * @version V1.0
 */
public interface IPayBankService {
	
	/**
	 * 查询银联支付所有列表信息
	 * @return
	 */
	public List<PayBank>  queryPayBankAll();
	
	/**
	 * 根据银行简码查询银行信息
	 * @param bankCode 银行简码
	 * @return
	 */
	public PayBank queryPayBankByCode(String bankCode);

}
