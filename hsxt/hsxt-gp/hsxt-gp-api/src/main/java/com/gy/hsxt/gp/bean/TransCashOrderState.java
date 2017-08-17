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
 *  File Name       : TransCashOrderState.java
 * 
 *  Creation Date   : 2015-9-28
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 转账结果bean
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class TransCashOrderState implements Serializable {

	private static final long serialVersionUID = -8549224412343846370L;

	/** 交易日期，格式：yyyyMMddHHmmss */
	private Date bankSubmitDate;

	/** 银行交易流水号, 由平安银行返回 */
	private String bankOrderSeqId;

	/** 业务订单号，最大32位字符 */
	private String orderNo;

	/** 银行转账凭证号，最大32位字符 */
	private String bankOrderNo;

	/** 业务订单日期，格式：yyyyMMdd */
	private Date orderDate;

	/** 交易金额，精度为6，传递值的精度小于6的自动补足，大于6的禁止； */
	private String transAmount;

	/** 币种 CNY 人民币（默认） */
	private String currencyCode;

	/** 手续费，精度为6，传递值的精度小于6的自动补足，大于6的禁止； */
	private String bankFee;

	/** 交易状态 详见“提现状态枚举值定义” */
	private int transStatus;

	/** 失败原因 */
	private String failReason;

	/** 请求业务子系统ID */
	private String srcSubsysId;

	/**
	 * 构造函数
	 */
	public TransCashOrderState() {
	}

	/**
	 * 构造函数
	 * 
	 * @param bankSubmitDate
	 * @param orderNo
	 * @param orderDate
	 * @param transAmount
	 * @param currencyCode
	 * @param bankFee
	 * @param transStatus
	 * @param failReason
	 */
	public TransCashOrderState(Date bankSubmitDate, String orderNo,
			Date orderDate, String transAmount, String currencyCode,
			String bankFee, int transStatus, String failReason) {
		this.bankSubmitDate = bankSubmitDate;
		this.orderNo = orderNo;
		this.orderDate = orderDate;
		this.transAmount = transAmount;
		this.currencyCode = currencyCode;
		this.bankFee = bankFee;
		this.transStatus = transStatus;
		this.failReason = failReason;
	}

	public String getSrcSubsysId() {
		return srcSubsysId;
	}

	public void setSrcSubsysId(String srcSubsysId) {
		this.srcSubsysId = srcSubsysId;
	}

	public Date getBankSubmitDate() {
		return bankSubmitDate;
	}

	public void setBankSubmitDate(Date bankSubmitDate) {
		this.bankSubmitDate = bankSubmitDate;
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

	public String getBankFee() {
		return bankFee;
	}

	public void setBankFee(String bankFee) {
		this.bankFee = bankFee;
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
		return "TransCashOrderState [bankSubmitDate=" + bankSubmitDate
				+ ", bankOrderSeqId=" + bankOrderSeqId + ", orderNo=" + orderNo
				+ ", orderDate=" + orderDate + ", transAmount=" + transAmount
				+ ", currencyCode=" + currencyCode + ", bankFee=" + bankFee
				+ ", transStatus=" + transStatus + ", failReason=" + failReason
				+ ", srcSubsysId=" + srcSubsysId + "]";
	}

}
