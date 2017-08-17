/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gp.bean;

import java.io.Serializable;
import java.util.Date;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-api
 * 
 *  Package Name    : com.gy.hsxt.gp.bean
 * 
 *  File Name       : PaymentOrderState.java
 * 
 *  Creation Date   : 2015-9-28
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 支付结果bean
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class PaymentOrderState implements Serializable {

	private static final long serialVersionUID = 5533580103367696895L;

	/** 银行交易流水号, 由中国银联返回 */
	private String bankOrderSeqId;

	/** 业务订单号，最大32位字符 */
	private String orderNo;

	/** 银行支付订单号，最大32位字符 */
	private String bankOrderNo;

	/** 业务订单日期，格式：yyyyMMdd */
	private Date orderDate;

	/** 交易金额，精度为6，传递值的精度小于6的自动补足，大于6的禁止； */
	private String transAmount;

	/** 币种 CNY 人民币（默认） */
	private String currencyCode;

	/** int 支付渠道 100-网银支付 101-手机移动支付 102-快捷支付 */
	private int payChannel;

	/** 交易状态 详见“支付状态枚举值定义” */
	private int transStatus;

	/** 失败原因 */
	private String failReason;

	/** 请求业务子系统ID */
	private String srcSubsysId;

	/**
	 * 构造函数
	 */
	public PaymentOrderState() {
	}

	/**
	 * 构造函数
	 */
	public PaymentOrderState(String orderNo, Date orderDate,
			String transAmount, String currencyCode, int payChannel,
			int transStatus, String failReason) {
		this.orderNo = orderNo;
		this.orderDate = orderDate;
		this.transAmount = transAmount;
		this.currencyCode = currencyCode;
		this.payChannel = payChannel;
		this.transStatus = transStatus;
		this.failReason = failReason;
	}

	public String getSrcSubsysId() {
		return srcSubsysId;
	}

	public void setSrcSubsysId(String srcSubsysId) {
		this.srcSubsysId = srcSubsysId;
	}

	public String getBankOrderSeqId() {
		return bankOrderSeqId;
	}

	public void setBankOrderSeqId(String bankOrderSeqId) {
		this.bankOrderSeqId = bankOrderSeqId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getBankOrderNo() {
		return bankOrderNo;
	}

	public void setBankOrderNo(String bankOrderNo) {
		this.bankOrderNo = bankOrderNo;
	}

	public String getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(String transAmount) {
		this.transAmount = transAmount;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public int getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(int payChannel) {
		this.payChannel = payChannel;
	}

	public int getTransStatus() {
		return transStatus;
	}

	public void setTransStatus(int transStatus) {
		this.transStatus = transStatus;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	@Override
	public String toString() {
		return "PaymentOrderState [orderNo=" + orderNo + ", orderDate="
				+ orderDate + ", transAmount=" + transAmount
				+ ", currencyCode=" + currencyCode + ", payChannel="
				+ payChannel + ", transStatus=" + transStatus + ", failReason="
				+ failReason + ", srcSubsysId=" + srcSubsysId + "]";
	}
}
