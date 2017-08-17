/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.chinapay.b2c.params;

import java.math.BigInteger;
import java.util.Date;

/***************************************************************************
 * <PRE>
 *  Project Name    : bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.chinapay.b2c.params
 * 
 *  File Name       : ChinapayRefundParam.java
 * 
 *  Creation Date   : 2014-10-23
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 中国银联B2C页面支付beans
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class ChinapayRefundParam {

	/** 原订单号 **/
	private String orderNo;

	/** 退款金额，全额退款则为原交易金额 (单位：分) **/
	private BigInteger refundAmount = new BigInteger("0");

	/** 原交易日期(格式： 20070801) **/
	private Date translateDate;

	/** 退款备注，可以为空 **/
	private String refundNote;

	/** 这个地址用于接收银行服务的退款成功回调，这个地址在支付时发给银行。 **/
	private String notifyURL;

	public ChinapayRefundParam() {
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public BigInteger getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigInteger refundAmount) {
		this.refundAmount = refundAmount;
	}

	public Date getTranslateDate() {
		return translateDate;
	}

	public void setTranslateDate(Date translateDate) {
		this.translateDate = translateDate;
	}

	public String getRefundNote() {
		return refundNote;
	}

	public void setRefundNote(String refundNote) {
		this.refundNote = refundNote;
	}

	public String getNotifyURL() {
		return notifyURL;
	}

	public void setNotifyURL(String notifyURL) {
		this.notifyURL = notifyURL;
	}

	public static ChinapayRefundParam build() {
		return new ChinapayRefundParam();
	}
}
