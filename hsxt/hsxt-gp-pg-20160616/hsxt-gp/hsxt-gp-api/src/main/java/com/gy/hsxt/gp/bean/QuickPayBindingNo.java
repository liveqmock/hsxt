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
public class QuickPayBindingNo implements Serializable {

	private static final long serialVersionUID = 426906643225284638L;

	/** 互生客户号,最大字符长度为32 */
	private String custId;

	/** 银行卡号,最大字符长度为19 */
	private String bankCardNo;

	/** 借贷记标识 1-借记卡； 2-贷记卡(即:信用卡)； */
	private int bankCardType;

	/** 银行代码,参照本文档附录银行名称-简码对照表。最大字符长度为5 */
	private String bankId;
	
	/** 银行名称,参照本文档附录银行名称-简码对照表。 */
	private String bankName;

	/** 签约号,最大字符长度为24 */
	private String bindingNo;

	/** 过期日期,该字段只有“小额临时支付”才有值，格式：yyyyMMdd */
	private Date expireDate;

	/** 单次交易限额，该字段只有“小额临时支付”才有值。最大字符长度为28 */
	private String transLimit;

	/** 多次交易总的限额,该字段只有“小额临时支付”才有值，最大字符长度为28 */
	private String sumLimit;

	/**
	 * 构造函数
	 */
	public QuickPayBindingNo() {
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public int getBankCardType() {
		return bankCardType;
	}

	public void setBankCardType(int bankCardType) {
		this.bankCardType = bankCardType;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
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
		return "QuickPayBindingNo [custId=" + custId + ", bankCardNo="
				+ bankCardNo + ", bankCardType=" + bankCardType + ", bankId="
				+ bankId + ", bankName=" + bankName + ", bindingNo="
				+ bindingNo + ", expireDate=" + expireDate + ", transLimit="
				+ transLimit + ", sumLimit=" + sumLimit + "]";
	}

}
