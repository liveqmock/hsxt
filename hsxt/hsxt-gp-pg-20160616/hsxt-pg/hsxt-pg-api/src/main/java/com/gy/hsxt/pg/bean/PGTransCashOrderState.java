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
 *  Package Name    : com.gy.hsxt.pg.api
 * 
 *  File Name       : PGTransCashOrderState.java
 * 
 *  Creation Date   : 2015-10-12
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class PGTransCashOrderState implements Serializable {

	private static final long serialVersionUID = -3875031850317959448L;

	/** 银行提现单号，最大16位字符 */
	private String bankOrderNo;
	
	/** 银行订单流水号 （银联不一定返回），最大20位字符 */
	private String bankOrderSeqId;
	
	/** 精度为6，转账成功状态才有手续费 */
	private String bankFee;

	/** 交易金额，精度为6，传递值的精度小于6的自动补足，大于6的禁止； */
	private String transAmount;

	/** 币种 CNY 人民币（默认） */
	private String currencyCode;

	/** 交易状态 详见“支付状态枚举值定义” */
	private int transStatus;

	/** 交易时间，格式：yyyyMMdd */
	private Date transDate;
	
	/** 银行受理时间 */
	private Date bankSubmitDate;
	
	/** 银行主机记账时间 */
	private Date bankAccountDate;

	/** 失败原因 */
	private String failReason;

	public PGTransCashOrderState() {
	}
	
	public PGTransCashOrderState(String bankOrderNo, String transAmount,
			String currencyCode, int transStatus, Date transDate) {
		this.bankOrderNo = bankOrderNo;
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

	public String getBankFee() {
		return bankFee;
	}

	public void setBankFee(String bankFee) {
		this.bankFee = bankFee;
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

	public Date getBankSubmitDate() {
		return bankSubmitDate;
	}

	public void setBankSubmitDate(Date bankSubmitDate) {
		this.bankSubmitDate = bankSubmitDate;
	}

	public Date getBankAccountDate() {
		return bankAccountDate;
	}

	public void setBankAccountDate(Date bankAccountDate) {
		this.bankAccountDate = bankAccountDate;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	@Override
	public String toString() {
		return "PGTransCashOrderState [bankOrderNo=" + bankOrderNo
				+ ", bankOrderSeqId=" + bankOrderSeqId + ", bankFee=" + bankFee
				+ ", transAmount=" + transAmount + ", currencyCode="
				+ currencyCode + ", transStatus=" + transStatus
				+ ", transDate=" + transDate + ", bankSubmitDate="
				+ bankSubmitDate + ", bankAccountDate=" + bankAccountDate
				+ ", failReason=" + failReason + "]";
	}
	
}
