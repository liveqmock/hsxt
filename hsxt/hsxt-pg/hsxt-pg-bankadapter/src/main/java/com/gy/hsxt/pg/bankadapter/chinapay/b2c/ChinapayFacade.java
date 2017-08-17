/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.chinapay.b2c;

import org.apache.log4j.Logger;

import com.gy.hsxt.pg.bankadapter.chinapay.b2c.abstracts.AbstractChinapayB2cFacade;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.worker.B2cDayBalanceWorker;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.worker.OrderStateWorker;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.worker.PayWorker;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.worker.RefundWorker;

/***************************************************************************
 * <PRE>
 *  Project Name    : bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.chinapay.b2c
 * 
 *  File Name       : ChinaPayFacade.java
 * 
 *  Creation Date   : 2014-10-22
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 中国银联B2C接口的门面类(此类由zhangysh修改, lijiabei的代码请见ChinaPayFacadeOlder.java类)
 *                    由于银联的无卡支付与B2C接口完全一致, 所以共用此类, 只是商户号、私钥、公钥不同而已
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class ChinapayFacade extends AbstractChinapayB2cFacade {
	protected final Logger logger = Logger.getLogger(this.getClass());

	// B2C页面支付
	private PayWorker pay;

	// B2C退款
	private RefundWorker refund;

	// B2C订单状态查询
	private OrderStateWorker orderState;

	// B2C每日对账
	private B2cDayBalanceWorker dayBalance;

	/**
	 * 构造函数
	 */
	public ChinapayFacade() {
	}

	/**
	 * 获取B2C支付工作者对象
	 * 
	 * @return
	 */
	public PayWorker getPayWorker() {
		return pay;
	}

	/**
	 * 获取B2C退款工作者对象
	 * 
	 * @return
	 */
	public RefundWorker getRefundWorker() {
		return refund;
	}

	/**
	 * 获取查询B2C订单状态的工作者对象
	 * 
	 * @return
	 */
	public OrderStateWorker getOrderStateWorker() {
		return orderState;
	}

	/**
	 * 获取查询B2C订单状态的工作者对象
	 * 
	 * @return
	 */
	public B2cDayBalanceWorker getDayBalanceWorker() {
		return dayBalance;
	}

	public void setPay(PayWorker pay) {
		this.pay = pay;
		this.pay.initFacade(this);
	}

	public void setRefund(RefundWorker refund) {
		this.refund = refund;
		this.refund.initFacade(this);
	}

	public void setOrderState(OrderStateWorker orderState) {
		this.orderState = orderState;
		this.orderState.initFacade(this);
	}

	public void setDayBalance(B2cDayBalanceWorker dayBalance) {
		this.dayBalance = dayBalance;
		this.dayBalance.initFacade(this);
	}
}
