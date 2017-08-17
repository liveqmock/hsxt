/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gp.api;

import com.gy.hsxt.gp.bean.PaymentOrderState;
/**
 * GP-BS支付，转账 结果异步通知
 * 
 * @param bindingNoBean
 * @return 同步成功or失败，如果失败抛出异常
 * @throws HsException
 */
import com.gy.hsxt.gp.bean.TransCashOrderState;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-api
 * 
 *  Package Name    : com.gy.hsxt.gp.api
 * 
 *  File Name       : IGPNotifyService.java
 * 
 *  Creation Date   : 2015-9-28
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : GP-BS支付，转账 结果异步通知
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public interface IGPNotifyService {
	/**
	 * GP-BS支付结果异步通知　 “支付系统-->业务系统”异步通知接口，该接口由支付系统定义，然后由BS业务系统实现。
	 * 
	 * @param paymentState
	 * @return
	 */
	public boolean notifyPaymentOrderState(PaymentOrderState paymentState);

	/**
	 * GP-BS转账结果异步通知,　　“支付系统-->业务系统”通知接口，该接口由支付系统定义，然后由BS业务系统实现。
	 * 
	 * @param TransCashOrderState
	 * @return
	 */
	public boolean notifyTransCashOrderState(
			TransCashOrderState transCashOrderState);

}
