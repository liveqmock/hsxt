/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.service;

import java.util.List;

import com.gy.hsxt.pg.bankadapter.chinapay.ChinapayOrderResultDTO;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.service
 * 
 *  File Name       : IChinapayOrderStateSyncService.java
 * 
 *  Creation Date   : 2015年12月25日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 中国银联支付单状态操作接口
 * 
 * 
 *  History         : None
 * 
 * </PRE>
 ***************************************************************************/
public interface IChinapayOrderStateSyncService {

	/**
	 * 执行支付单状态同步操作
	 * 
	 * @param orderResultList
	 */
	public void actionOrderStateSync(
			List<ChinapayOrderResultDTO> orderResultList);
}
