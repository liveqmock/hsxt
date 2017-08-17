/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.chinapay.upop;

import org.apache.log4j.Logger;

import com.gy.hsxt.pg.bankadapter.chinapay.b2c.abstracts.AbstractChinapayB2cFacade;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.worker.BindingNoQueryWorker;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.worker.CardBindingWorker;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.worker.UpopDayBalanceWorker;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.worker.OrderStatesWorker;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.worker.QuickPayFirstWorker;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.worker.QuickPaySecondWorker;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.worker.RefundsWorker;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.worker.SmsCodeRequestWorker;

/***************************************************************************
 * <PRE>
 *  Project Name    : bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.chinapay.upop
 * 
 *  File Name       : ChinapayUpopFacade.java
 * 
 *  Creation Date   : 2014-11-21
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 中国银联UPOP快捷支付门面类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class ChinapayUpopFacade extends AbstractChinapayB2cFacade {
	protected final Logger logger = Logger.getLogger(this.getClass());

	// 快捷支付[首次]
	private QuickPayFirstWorker quickPayFirst;

	// 快捷支付[非首次]
	private QuickPaySecondWorker quickPaySecond;

	// 快捷支付短信验证码发送
	private SmsCodeRequestWorker smsCodeRequest;

	// 快捷支付签约号查询
	private BindingNoQueryWorker bindingNoQuery;

	// 快捷支付订单状态查询
	private OrderStatesWorker orderState;

	// 快捷支付退款
	private RefundsWorker refund;

	// 快捷支付银行卡绑定
	private CardBindingWorker cardBinding;

	// 快捷支付每日对账
	private UpopDayBalanceWorker dayBalance;

	/**
	 * 构造函数
	 */
	public ChinapayUpopFacade() {
	}

	public QuickPayFirstWorker getQuickPayFirst() {
		return quickPayFirst;
	}

	public QuickPaySecondWorker getQuickPaySecond() {
		return quickPaySecond;
	}

	public SmsCodeRequestWorker getSmsCodeRequest() {
		return smsCodeRequest;
	}

	public BindingNoQueryWorker getBindingNoQuery() {
		return bindingNoQuery;
	}

	public OrderStatesWorker getOrderState() {
		return orderState;
	}

	public RefundsWorker getRefund() {
		return refund;
	}

	public CardBindingWorker getCardBinding() {
		return cardBinding;
	}

	public UpopDayBalanceWorker getDayBalance() {
		return dayBalance;
	}

	public void setQuickPayFirst(QuickPayFirstWorker quickPayFirst) {
		this.quickPayFirst = quickPayFirst;
		this.quickPayFirst.initFacade(this);
	}

	public void setQuickPaySecond(QuickPaySecondWorker quickPaySecond) {
		this.quickPaySecond = quickPaySecond;
		this.quickPaySecond.initFacade(this);
	}

	public void setSmsCodeRequest(SmsCodeRequestWorker smsCodeRequest) {
		this.smsCodeRequest = smsCodeRequest;
		this.smsCodeRequest.initFacade(this);
	}

	public void setBindingNoQuery(BindingNoQueryWorker bindingNoQuery) {
		this.bindingNoQuery = bindingNoQuery;
		this.bindingNoQuery.initFacade(this);
	}

	public void setOrderState(OrderStatesWorker orderState) {
		this.orderState = orderState;
		this.orderState.initFacade(this);
	}

	public void setRefund(RefundsWorker refund) {
		this.refund = refund;
		this.refund.initFacade(this);
	}

	public void setCardBinding(CardBindingWorker cardBinding) {
		this.cardBinding = cardBinding;
		this.cardBinding.initFacade(this);
	}

	public void setDayBalance(UpopDayBalanceWorker dayBalance) {
		this.dayBalance = dayBalance;
		this.dayBalance.initFacade(this);
	}
}
