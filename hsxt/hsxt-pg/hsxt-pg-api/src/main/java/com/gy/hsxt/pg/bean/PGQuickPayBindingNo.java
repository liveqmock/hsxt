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
 *  Project Name    : hsxt-gp-api
 * 
 *  Package Name    : com.gy.hsxt.gp.bean
 * 
 *  File Name       : QuickPayBindingNo.java
 * 
 *  Creation Date   : 2015-9-28
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 快捷支付签约号bean
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class PGQuickPayBindingNo implements Serializable {

	private static final long serialVersionUID = -7059822804830500251L;

	/** 银行支付单号，最大16位字符 */
	private String bankOrderNo;

	/** 银行卡号,最大字符长度为19 */
	private String bankCardNo;

	/** 借贷记标识，即：借记卡 or 信用卡 [1-借记卡, 2-贷记卡] */
	private Integer bankCardType;

	/** 银行ID */
	private String bankId;

	/** 签约号,最大字符长度为24 */
	private String bindingNo;

	/** 过期日期,该字段只有“小额临时支付”才有值，格式：yyyyMMdd */
	private Date expireDate;

	/** 单次交易限额，该字段只有“小额临时支付”才有值。最大字符长度为28 */
	private String transLimit;

	/** 多次交易总的限额,该字段只有“小额临时支付”才有值，最大字符长度为28 */
	private String sumLimit;

	/**
	 * 构造函数1
	 */
	public PGQuickPayBindingNo() {
	}

	/**
	 * 构造函数2
	 * 
	 * @param bankCardNo
	 */
	public PGQuickPayBindingNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	/**
	 * 构造函数3
	 */
	public PGQuickPayBindingNo(String bankCardNo, String bindingNo,
			Date expireDate, String transLimit, String sumLimit) {
		this.bankCardNo = bankCardNo;
		this.bindingNo = bindingNo;
		this.expireDate = expireDate;
		this.transLimit = transLimit;
		this.sumLimit = sumLimit;
	}

	public String getBankOrderNo() {
		return bankOrderNo;
	}

	public void setBankOrderNo(String bankOrderNo) {
		this.bankOrderNo = bankOrderNo;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public Integer getBankCardType() {
		return bankCardType;
	}

	public void setBankCardType(Integer bankCardType) {
		this.bankCardType = bankCardType;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getBindingNo() {
		return bindingNo;
	}

	public void setBindingNo(String bindingNo) {
		this.bindingNo = bindingNo;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public String getTransLimit() {
		return transLimit;
	}

	public void setTransLimit(String transLimit) {
		this.transLimit = transLimit;
	}

	public String getSumLimit() {
		return sumLimit;
	}

	public void setSumLimit(String sumLimit) {
		this.sumLimit = sumLimit;
	}

	@Override
	public String toString() {
		return "PGQuickPayBindingNo [bankOrderNo=" + bankOrderNo
				+ ", bankCardNo=" + bankCardNo + ", bindingNo=" + bindingNo
				+ ", expireDate=" + expireDate + ", transLimit=" + transLimit
				+ ", sumLimit=" + sumLimit + "]";
	}

}
