/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.pingan.b2c.worker;

import com.gy.hsxt.pg.bankadapter.common.beans.BankPaymentOrderResultDTO;
import com.gy.hsxt.pg.bankadapter.pingan.b2c.abstracts.AbstractPinganB2cWorker;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.pingan.b2c.worker
 * 
 *  File Name       : PinganB2cRefundWorker.java
 * 
 *  Creation Date   : 2016年6月23日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 平安b2c网银退款工作者类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class PinganB2cOrderstateWorker extends AbstractPinganB2cWorker {

	/**
	 * 主动获取订单的状态信息
	 * 
	 * @param orderNo
	 *            订单号
	 * @return
	 * @throws Exception
	 */
	public BankPaymentOrderResultDTO doQueryPayOrderState(String orderNo)
			throws Exception {
		return null;
	}

	/**
	 * 查询退款订单状态
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public Object doQueryRefundOrderState(String beginDate, String endDate)
			throws Exception {
		return null;
	}
}
