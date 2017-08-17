/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.chinapay;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.chinapay
 * 
 *  File Name       : ChinapayOrderTransType.java
 * 
 *  Creation Date   : 2015年10月15日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 中国银联订单交易类型：支付 or退款
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public enum ChinapayOrderTransType {
	/** 未知 **/
	UNKNOWN(-1),

	/** 支付订单 **/
	TYPE_PAY(10),

	/** 退款订单 **/
	TYPE_REFUND(20);

	private int value;

	public int getValue() {
		return this.value;
	}

	private ChinapayOrderTransType(int value) {
		this.value = value;
	}

	public boolean valueEquals(int value) {
		return (getValue() == value);
	}
}
