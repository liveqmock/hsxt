/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.es.distribution.service;

import com.gy.hsxt.common.exception.HsException;

/** 
 * @Package: com.gy.hsxt.es.api.IEsStatementService 
 * @ClassName: IEcStatement 
 * @Description: 结算接口
 *
 * @author: chenhongzhi 
 * @date: 2015-10-20 上午10:34:41 
 * @version V3.0  
 */
public interface IConfirmReceiptService
{
	/**
	 * 通过调用接口再调用文件批结单、确认收货
	 * @throws HsException
	 */
	public void batConfirmReceipt() throws HsException;
}
