/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.chinapay.upmp;

import org.apache.log4j.Logger;

import com.gy.hsxt.pg.bankadapter.chinapay.upmp.worker.UpmpOrderStateWorker;
import com.gy.hsxt.pg.bankadapter.chinapay.upmp.worker.UpmpPayWorker;
import com.gy.hsxt.pg.bankadapter.chinapay.upmp.worker.UpmpRefundWorker;

/***************************************************************************
 * <PRE>
 *  Project Name    : bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.chinapay.upmp
 * 
 *  File Name       : ChinapayUpmpFacade.java
 * 
 *  Creation Date   : 2014-11-21
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 中国银联UPMP手机支付门面类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class ChinapayUpmpFacade {
	protected final Logger logger = Logger.getLogger(this.getClass());

	// 商户号
	// (1) 测试：880000000000720
	// (2) 生产：802310048992592
	private String mechantNo;

	// 密钥
	// (1) 测试：Bmb0ryrl8Ov7aDOhAvdkUyh5dDnoqUyw
	// (2) 生产：zhx2WPbBqPn47XJBTIJU8nRfnwe7nzTa
	private String privateKey;

	// UPMP支付
	private UpmpPayWorker pay;

	// UPMP退款
	private UpmpRefundWorker refund;

	// UPMP订单状态查询
	private UpmpOrderStateWorker orderState;

	/**
	 * 构造函数
	 */
	public ChinapayUpmpFacade() {
	}

	public UpmpPayWorker getPayWorker() {
		return pay;
	}

	public UpmpRefundWorker getRefundWorker() {
		return refund;
	}

	public UpmpOrderStateWorker getOrderStateWorker() {
		return orderState;
	}

	public void setPay(UpmpPayWorker pay) {
		this.pay = pay;
		this.pay.initFacade(this);
	}

	public void setRefund(UpmpRefundWorker refund) {
		this.refund = refund;
		this.refund.initFacade(this);
	}

	public void setOrderState(UpmpOrderStateWorker orderState) {
		this.orderState = orderState;
		this.orderState.initFacade(this);
	}

	public String getMechantNo() {
		return mechantNo;
	}

	public void setMechantNo(String mechantNo) {
		this.mechantNo = mechantNo;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

}
