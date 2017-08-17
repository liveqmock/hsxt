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
 *  Project Name    : bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.chinapay
 * 
 *  File Name       : ChinapayOrderStatus.java
 * 
 *  Creation Date   : 2014-11-6
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 银行返回的订单状态，一般用于查询单笔订单状态时，或者银行回调时，返回此笔订单的状态
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/

public enum ChinapayOrderStatus {
	/** 未知 **/
	UNKNOWN("-1"),

	/** 无效订单 **/
	INVALID("-2"),

	/** 支付成功 **/
	PAY_SUCCESS("100"),

	/** 支付失败 **/
	PAY_FAILED("101"),

	/** 支付处理中 **/
	PAY_PROCESSING("102"),

	/** 支付取消成功(只有UPMP手机支付采用此状态, 暂时无用) **/
	PAY_CANCELED("103"),

	/** 退款成功 **/
	REFUND_SUCCESS("200"),

	/** 退款失败 **/
	REFUND_FAILED("201"),

	/** 退款处理中 **/
	REFUND_PROCESSING("202"),

	/** 退款撤销成功(只有UPMP手机支付采用此状态, 暂时无用) **/
	REFUND_CANCEL_SUCCESS("203");

	private String value;

	public String getValue() {
		return this.value;
	}

	public int getIntValue() {
		return Integer.parseInt(this.value);
	}

	public boolean valueEquals(int value) {
		return (getIntValue() == value);
	}

	public boolean valueEquals(String value) {
		return this.value.equals(value);
	}

	private ChinapayOrderStatus(String value) {
		this.value = value;
	}
}
