/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.rp.api;

import java.util.List;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.rp.bean.ReportsBalance;
import com.gy.hsxt.rp.bean.ReportsBankrollChange;

/**
 * 
 * 资金存量接口
 * @Package: com.gy.hsxt.rp.api  
 * @ClassName: IReportsBankrollReserveService 
 * @Description: TODO
 *
 * @author: maocan 
 * @date: 2015-10-19 下午6:14:32 
 * @version V1.0
 */
public interface IReportsBankrollReserveService {
	
	/**
	 * 账户余额查询
	 * @param custId
	 * @param custType
	 * @return
	 * @throws HsException
	 */
	public List<ReportsBalance> searchActualBankroll(String custId, int custType) throws HsException;
	
	/**
	 * 账户余额变化查询
	 * @param custId
	 * @param custType
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @throws HsException
	 */
	public List<ReportsBankrollChange> searchBankrollChange(String custId, int custType, String beginDate, String endDate) throws HsException;
	
	
}
