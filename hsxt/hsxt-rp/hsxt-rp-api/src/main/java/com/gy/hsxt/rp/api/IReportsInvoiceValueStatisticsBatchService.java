/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.rp.api;

import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * 发票金额统计每日统计接口
 * @Package: com.gy.hsxt.rp.api  
 * @ClassName: IReportsBankrollReserveService 
 * @Description: TODO
 *
 * @author: maocan 
 * @date: 2015-12-28 下午6:14:32 
 * @version V1.0
 */
public interface IReportsInvoiceValueStatisticsBatchService {
	
	/**
	 * 发票金额统计每日统计
	 * @param batchJobName
	 * @param fileAddress
	 * @throws HsException
	 */
	public void invoiceValueStatistics(String batchJobName, String fileAddress, String batchDate) throws HsException;
}
