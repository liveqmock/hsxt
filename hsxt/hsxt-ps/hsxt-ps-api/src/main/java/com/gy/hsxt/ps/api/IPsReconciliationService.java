package com.gy.hsxt.ps.api;

import com.gy.hsxt.common.exception.HsException;

 

/**   
 * @description  对账服务接口
 * @author       chenhz
 * @createDate   2015-7-27 上午10:14:12  
 * @Company      深圳市归一科技研发有限公司
 * @version      v0.0.1
 */

public interface IPsReconciliationService {

	// 账务明细对账
	void  balanceAccount(String runDate) throws HsException;
	

}
