/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.chinapay.upmp.params;

import java.math.BigInteger;

/***************************************************************************
 * <PRE>
 *  Project Name    : bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.chinapay.upmp.params
 * 
 *  File Name       : UpmpPayParam.java
 * 
 *  Creation Date   : 2014-11-21
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 中国银联UPMP手机支付-退款
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class UpmpRefundParam {
	private String refundOrderNo;
	private BigInteger refundAmount = new BigInteger("0");
	private String qn;
	private String notifyUrl;

	public UpmpRefundParam() {
	}

	public String getRefundOrderNo() {
		return refundOrderNo;
	}

	public void setRefundOrderNo(String refundOrderNo) {
		this.refundOrderNo = refundOrderNo;
	}

	public BigInteger getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigInteger refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getQn() {
		return qn;
	}

	public void setQn(String qn) {
		this.qn = qn;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

}
