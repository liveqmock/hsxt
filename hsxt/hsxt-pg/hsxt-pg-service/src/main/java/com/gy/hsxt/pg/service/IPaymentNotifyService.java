/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.service;

import com.gy.hsxt.pg.bean.PGPaymentOrderState;
import com.gy.hsxt.pg.bean.PGQuickPayBindingNo;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.service
 * 
 *  File Name       : IPaymentNotifyService.java
 * 
 *  Creation Date   : 2015年12月25日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 中国银联回调notify处理接口
 * 
 * 
 *  History         : None
 * 
 * </PRE>
 ***************************************************************************/
public interface IPaymentNotifyService {

	/**
	 * 根据支付结果更新数据库并通知重试模块通知支付系统
	 * 
	 * @param paymentOrderState
	 */
	public void notifyOrderState(PGPaymentOrderState paymentOrderState);

	/**
	 * 通知签约号
	 * 
	 * @param quickPayBindingNo
	 * @param merType
	 */
	public void notifyBindingNo(PGQuickPayBindingNo quickPayBindingNo,
			int merType);
}
