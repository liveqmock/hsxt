/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.pingan.union;

import com.gy.hsxt.pg.bankadapter.pingan.union.worker.CardBindingWorker;
import com.gy.hsxt.pg.bankadapter.pingan.union.worker.OrderStatesWorker;
import com.gy.hsxt.pg.bankadapter.pingan.union.worker.QuickPaymentWorker;
import com.gy.hsxt.pg.bankadapter.pingan.union.worker.RefundWorker;
import com.gy.hsxt.pg.bankadapter.pingan.union.worker.SmsCodeRequestWorker;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.pingan.union
 * 
 *  File Name       : PinganUnionFacade.java
 * 
 *  Creation Date   : 2016年6月23日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 平安快捷支付
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class PinganUnionFacade {

	private CardBindingWorker bindingWorker;
	private SmsCodeRequestWorker smsCodeWorker;
	private QuickPaymentWorker paymentWorker;
	private OrderStatesWorker orderStatesWorker;
	private RefundWorker refundWorker;	

	public PinganUnionFacade() {
	}

	public CardBindingWorker getBindingWorker() {
		return bindingWorker;
	}

	public void setBindingWorker(CardBindingWorker bindingWorker) {
		this.bindingWorker = bindingWorker;
	}

	public SmsCodeRequestWorker getSmsCodeWorker() {
		return smsCodeWorker;
	}

	public void setSmsCodeWorker(SmsCodeRequestWorker smsCodeWorker) {
		this.smsCodeWorker = smsCodeWorker;
	}

	public QuickPaymentWorker getPaymentWorker() {
		return paymentWorker;
	}

	public void setPaymentWorker(QuickPaymentWorker paymentWorker) {
		this.paymentWorker = paymentWorker;
	}
	
	public OrderStatesWorker getOrderStatesWorker() {
		return orderStatesWorker;
	}

	public void setOrderStatesWorker(OrderStatesWorker orderStatesWorker) {
		this.orderStatesWorker = orderStatesWorker;
	}

	public RefundWorker getRefundWorker() {
		return refundWorker;
	}

	public void setRefundWorker(RefundWorker refundWorker) {
		this.refundWorker = refundWorker;
	}

	/**
	 * 由Spring实例化bean时调用
	 */
	public void init() {
	}
}
