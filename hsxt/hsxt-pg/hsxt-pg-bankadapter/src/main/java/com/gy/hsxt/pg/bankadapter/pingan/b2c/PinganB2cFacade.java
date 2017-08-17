/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.pingan.b2c;

import com.gy.hsxt.pg.bankadapter.pingan.b2c.worker.PinganB2cOrderstateWorker;
import com.gy.hsxt.pg.bankadapter.pingan.b2c.worker.PinganB2cPaymentWorker;
import com.gy.hsxt.pg.bankadapter.pingan.b2c.worker.PinganB2cRefundWorker;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.pingan.b2c
 * 
 *  File Name       : PinganB2cFacade.java
 * 
 *  Creation Date   : 2016年6月23日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 平安b2c网银支付
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class PinganB2cFacade {

	/** 平安银行b2c网银支付 */
	private PinganB2cPaymentWorker b2cPaymentWorker;

	/** 平安银行b2c网银退款 */
	private PinganB2cRefundWorker b2cRefundWorker;

	/** 平安银行b2c网银状态查询 */
	private PinganB2cOrderstateWorker b2cOrderstateWorker;

	public PinganB2cPaymentWorker getB2cPaymentWorker() {
		return b2cPaymentWorker;
	}

	public void setB2cPaymentWorker(PinganB2cPaymentWorker b2cPaymentWorker) {
		this.b2cPaymentWorker = b2cPaymentWorker;
	}

	public PinganB2cRefundWorker getB2cRefundWorker() {
		return b2cRefundWorker;
	}

	public void setB2cRefundWorker(PinganB2cRefundWorker b2cRefundWorker) {
		this.b2cRefundWorker = b2cRefundWorker;
	}

	public PinganB2cOrderstateWorker getB2cOrderstateWorker() {
		return b2cOrderstateWorker;
	}

	public void setB2cOrderstateWorker(
			PinganB2cOrderstateWorker b2cOrderstateWorker) {
		this.b2cOrderstateWorker = b2cOrderstateWorker;
	}

	/**
	 * 由Spring实例化bean时调用
	 */
	public void init() {
	}
}
