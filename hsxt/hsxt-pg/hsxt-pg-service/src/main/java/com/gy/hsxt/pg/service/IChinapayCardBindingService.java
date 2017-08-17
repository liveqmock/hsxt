/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.service;

import com.gy.hsxt.pg.bean.PGQuickPayBindingNo;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.service
 * 
 *  File Name       : IChinapayCardBindingService.java
 * 
 *  Creation Date   : 2016年6月7日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 银联卡绑定快捷信息服务接口
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public interface IChinapayCardBindingService {

	/**
	 * 通知签约号
	 * 
	 * @param quickPayBindingNo
	 * @param merType
	 */
	public void notifyBindingNo(PGQuickPayBindingNo quickPayBindingNo,
			int merType);

	/**
	 * 获取并通知签约号到GP->UC
	 * 
	 * @param bankCardNo
	 * @param bankId
	 * @param bankCardType
	 * @param bankOrderNo
	 * @param merType
	 */
	public void fetchBindingNoFromCP(String bankCardNo, String bankId,
			int bankCardType, String bankOrderNo, int merType);

	/**
	 * 插入签约信息到临时表
	 * 
	 * @param bankCardNo
	 * @param bankId
	 * @param bankCardType
	 */
	public void insertTempBankCardBinding(String bankCardNo, String bankId,
			int bankCardType);
}
