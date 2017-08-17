/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.common.constants;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.common.constants
 * 
 *  File Name       : PaymentOrderTransType.java
 * 
 *  Creation Date   : 2016年6月23日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 订单状态枚举
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public enum PaymentOrderTransType {
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

	private PaymentOrderTransType(int value) {
		this.value = value;
	}

	public boolean valueEquals(int value) {
		return (getValue() == value);
	}
}
