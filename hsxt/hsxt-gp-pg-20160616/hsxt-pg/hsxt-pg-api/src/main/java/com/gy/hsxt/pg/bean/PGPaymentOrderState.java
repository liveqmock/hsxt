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
import java.util.Date;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-api
 * 
 *  Package Name    : com.gy.hsxt.pg.bean
 * 
 *  File Name       : PGPaymentOrderState.java
 * 
 *  Creation Date   : 2015-10-12
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 支付状态
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class PGPaymentOrderState implements Serializable{

	private static final long serialVersionUID = 3232838459196055963L;

	/** 银行支付单号，最大16位字符 */
	private String bankOrderNo;

	/** 银行订单流水号 （银联不一定返回），最大20位字符 */
	private String bankOrderSeqId;
	
	/** 银行订单状态  */
	private String bankOrderStatus;

	/** 原银行支付单号 （只有退款交易才有值） */
	private String origBankOrderNo;

	/** int 支付渠道 100-网银支付 200-手机移动支付 300-快捷支付 */
	private int payChannel;

	/** 10 - 支付交易 20 - 退款交易 */
	private int transType;

	/** 交易金额，精度为6，传递值的精度小于6的自动补足，大于6的禁止； */
	private String transAmount;

	/** 币种 CNY 人民币（默认） */
	private String currencyCode;

	/** 交易状态 详见“支付状态枚举值定义” */
	private int transStatus;

	/** 交易时间，格式：yyyyMMdd */
	private Date transDate;

	/** 失败原因 */
	private String failReason;
	
	/** 内部处理使用的字段 */
    private String privObligate2;

	public PGPaymentOrderState() {
	}

	public PGPaymentOrderState(String bankOrderNo, String origBankOrderNo,
			int payChannel, int transType, String transAmount,
			String currencyCode, int transStatus, Date transDate) {
		this.bankOrderNo = bankOrderNo;
		this.origBankOrderNo = origBankOrderNo;
		this.payChannel = payChannel;
		this.transType = transType;
		this.transAmount = transAmount;
		this.currencyCode = currencyCode;
		this.transStatus = transStatus;
		this.transDate = transDate;
	}

	public String getBankOrderNo() {
		return bankOrderNo;
	}

	public void setBankOrderNo(String bankOrderNo) {
		this.bankOrderNo = bankOrderNo;
	}

	public String getBankOrderSeqId() {
		return bankOrderSeqId;
	}

	public void setBankOrderSeqId(String bankOrderSeqId) {
		this.bankOrderSeqId = bankOrderSeqId;
	}

	public String getBankOrderStatus() {
		return bankOrderStatus;
	}

	public void setBankOrderStatus(String bankOrderStatus) {
		this.bankOrderStatus = bankOrderStatus;
	}

	public String getOrigBankOrderNo() {
		return origBankOrderNo;
	}

	public void setOrigBankOrderNo(String origBankOrderNo) {
		this.origBankOrderNo = origBankOrderNo;
	}

	public int getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(int payChannel) {
		this.payChannel = payChannel;
	}

	public int getTransType() {
		return transType;
	}

	public void setTransType(int transType) {
		this.transType = transType;
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

	public int getTransStatus() {
		return transStatus;
	}

	public void setTransStatus(int transStatus) {
		this.transStatus = transStatus;
	}

	public Date getTransDate() {
		return transDate;
	}

	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public String getPrivObligate2() {
		return privObligate2;
	}

	public void setPrivObligate2(String privObligate2) {
		this.privObligate2 = privObligate2;
	}

	@Override
	public String toString() {
		return "PGPaymentOrderState [bankOrderNo=" + bankOrderNo
				+ ", bankOrderSeqId=" + bankOrderSeqId + ", origBankOrderNo="
				+ origBankOrderNo + ", payChannel=" + payChannel
				+ ", transType=" + transType + ", transAmount=" + transAmount
				+ ", currencyCode=" + currencyCode + ", transStatus="
				+ transStatus + ", transDate=" + transDate + ", failReason="
				+ failReason + "]";
	}
	
}
