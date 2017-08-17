/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.api;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.pg.bean.PGPaymentOrderState;
import com.gy.hsxt.pg.bean.PGQuickPayBindingNo;
import com.gy.hsxt.pg.bean.PGTransCashOrderState;

/***************************************************************************
 * 
 * <PRE>
 *  Project Name    : hsxt-pg-api
 * 
 *  Package Name    : com.gy.hsxt.pg.api
 * 
 *  File Name       : IPGNotifyService.java
 * 
 *  Creation Date   : 2015-10-12
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : PG-GP支付结果异步通知,PG-GP提现结果异步通知,PG-GP快捷支付签约号异步通知
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public interface IPGNotifyService {
	
	/**
	 * PG-GP支付结果异步通知接口 注意：“支付网关-->支付系统”通知接口，该接口由支付网关定义，然后由支付系统实现。
	 * 
	 * @param pgPaymentState
	 * @return
	 * @throws HsException
	 */
	public boolean notifyPaymentOrderState(PGPaymentOrderState pgPaymentState)
			throws HsException;

	/**
	 * PG-GP提现结果异步通知接口
	 * 
	 * @param pgTransCashOrderState
	 * @return
	 * @throws HsException
	 */
	public boolean notifyTransCashOrderState(
			PGTransCashOrderState pgTransCashOrderState) throws HsException;

	/**
	 * PG-GP快捷支付签约号异步通知 　　注意：“支付网关-->支付系统”通知接口，该接口由支付网关定义，然后由支付系统实现。
	 * 
	 * @param pgQuickPayBindingNo
	 * @return
	 * @throws HsException
	 */
	public boolean notifyQuickPayBindingNo(PGQuickPayBindingNo pgQuickPayBindingNo)
			throws HsException;
}
