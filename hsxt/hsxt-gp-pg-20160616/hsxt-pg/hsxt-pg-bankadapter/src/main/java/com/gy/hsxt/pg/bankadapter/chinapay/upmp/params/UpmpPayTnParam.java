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
import java.util.Date;

import com.gy.hsxt.pg.bankadapter.common.utils.DateUtils;

/***************************************************************************
 * <PRE>
 *  Project Name    : bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.chinapay.upmp.params
 * 
 *  File Name       : UpmpPayTnParam.java
 * 
 *  Creation Date   : 2014-11-21
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 中国银联UPMP手机支付参数
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/

public class UpmpPayTnParam {
	private String orderNo;
	private Date orderDate;
	private String currencyId;
	private BigInteger payAmount = new BigInteger("0");
	private String jumpUrl;
	private String notifyUrl;

	public UpmpPayTnParam() {
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = DateUtils.getYYYYMMddHHmmssDate(orderDate);
	}

	public BigInteger getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigInteger payAmount) {
		this.payAmount = payAmount;
	}

	public String getJumpUrl() {
		return jumpUrl;
	}

	public void setJumpUrl(String jumpUrl) {
		this.jumpUrl = jumpUrl;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
}
