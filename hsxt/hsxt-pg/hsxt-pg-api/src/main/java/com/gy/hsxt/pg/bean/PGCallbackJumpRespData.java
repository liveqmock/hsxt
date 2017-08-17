/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bean;

import java.io.Serializable;

import com.gy.hsi.common.utils.StringUtils;
import com.gy.hsxt.pg.constant.PGConstant.PGCurrencyCode;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-api
 * 
 *  Package Name    : com.gy.hsxt.pg.bean
 * 
 *  File Name       : PgCallbackJumpRespData.java
 * 
 *  Creation Date   : 2015年12月18日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 支付网关jump时传递的响应参数key
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class PGCallbackJumpRespData implements Serializable {

	private static final long serialVersionUID = -4068078956790385737L;

	private String orderNo;
	private String orderDate;
	private String transAmount;
	private String transStatus;
	private String currencyCode;
	private String payChannel;
	private String failReason;
	private String bankRespCode;

	private String jumpUrl;

	public PGCallbackJumpRespData() {
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(String transAmount) {
		this.transAmount = transAmount;
	}

	public String getTransStatus() {
		return transStatus;
	}

	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}
	
	public void setTransStatus(int transStatus) {
		this.transStatus = String.valueOf(transStatus);
	}

	public String getCurrencyCode() {
		if (StringUtils.isEmpty(currencyCode)) {
			currencyCode = PGCurrencyCode.CNY;
		}

		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}
	
	public void setPayChannel(int payChannel) {
		this.payChannel = String.valueOf(payChannel);
	}

	public String getBankRespCode() {
		return bankRespCode;
	}

	public void setBankRespCode(String bankRespCode) {
		this.bankRespCode = bankRespCode;
	}

	public String getJumpUrl() {
		return jumpUrl;
	}

	public void setJumpUrl(String jumpUrl) {
		this.jumpUrl = jumpUrl;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}
}
