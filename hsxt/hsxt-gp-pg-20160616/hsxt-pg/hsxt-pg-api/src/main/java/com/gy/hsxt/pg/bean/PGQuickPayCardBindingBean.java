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

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-api
 * 
 *  Package Name    : com.gy.hsxt.pg.bean
 * 
 *  File Name       : PGQuickPayCardBindingBean.java
 * 
 *  Creation Date   : 2016-5-25
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 中国银联快捷支付银行卡签约bean
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class PGQuickPayCardBindingBean implements Serializable {

	private static final long serialVersionUID = 8383756587437594462L;

	/** 请求id，最大16位字符, 在此只用作标识唯一 */
	private String requestId;
	
	/** 商户类型 100-互生线（默认） 101-互商线 200-第三方接入商户（预留，指淘宝、京东等） */
	private int merType;

	/** 客户号，用于向用户中心同步签约号,最大长度21 */
	private String custId;

	/** 银行账户，最大长度19 */
	private String bankCardNo;

	/** 借贷记标识，即：借记卡 or 信用卡 [1-借记卡, 2-贷记卡] */
	private int bankCardType;

	/** 银行ID,参照《支付系统设计文档》附录银行名称-简码对照表,最大长度4 */
	private String bankId;

	/**
	 * 构造函数
	 */
	public PGQuickPayCardBindingBean() {
	}
	
	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public int getMerType() {
		return merType;
	}

	public void setMerType(int merType) {
		this.merType = merType;
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

	@Override
	public String toString() {
		return "PGQuickPayCardBindingBean [requestId=" + requestId
				+ ", merType=" + merType + ", custId=" + custId
				+ ", bankCardNo=" + bankCardNo + ", bankCardType="
				+ bankCardType + ", bankId=" + bankId + "]";
	}

}
