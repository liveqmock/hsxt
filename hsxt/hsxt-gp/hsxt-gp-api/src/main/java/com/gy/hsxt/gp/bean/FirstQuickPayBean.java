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
 *  File Name       : FirstQuickPayBean.java
 * 
 *  Creation Date   : 2015-9-28
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 中国银联快捷支付beans（首次）
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class FirstQuickPayBean implements Serializable {

	private static final long serialVersionUID = 771318323182323091L;

	/** 商户号，1100000001：互商 1100000007：互生 第三方接入的酌情分配 */
	private String merId;

	/** 业务订单号，最大32位字符 */
	private String orderNo;

	/** 业务订单日期，格式：yyyyMMdd */
	private Date orderDate;

	/** 交易金额，精度为6，传递值的精度小于6的自动补足，大于6的禁止； */
	private String transAmount;

	/** 交易描述 ,最大长度30 */
	private String transDesc;

	/** 币种 CNY 人民币（默认） */
	private String currencyCode;

	/** 支付成功后，银行将会通过浏览器跳转到这个页面，,最大长度80 */
	private String jumpUrl;

	/** 传递商户私有数据,用于回调时带回,最大长度60 */
	private String privObligate;

	/** 客户号，用于向用户中心同步签约号,最大长度21 */
	private String custId;

	/** 银行账户，最大长度19 */
	private String bankCardNo;

	/**
	 * 借贷记标识，即：借记卡 or 信用卡 1 - 借记卡 2 - 贷记卡
	 */
	private int bankCardType;

	/** 银行ID,参照《支付系统设计文档》附录银行名称-简码对照表,最大长度4 */
	private String bankId;

	/**
	 * 构造函数
	 */
	public FirstQuickPayBean() {
	}

	/**
	 * 构造函数
	 */
	public FirstQuickPayBean(String merId, String orderNo, Date orderDate,
			String transAmount, String jumpUrl, String custId,
			String bankCardNo, int bankCardType, String bankId) {
		this.merId = merId;
		this.orderNo = orderNo;
		this.orderDate = orderDate;
		this.transAmount = transAmount;
		this.jumpUrl = jumpUrl;
		this.custId = custId;
		this.bankCardNo = bankCardNo;
		this.bankCardType = bankCardType;
		this.bankId = bankId;
	}

	public String getMerId() {
		return merId;
	}

	public void setMerId(String merId) {
		this.merId = merId;
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

	public String getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(String transAmount) {
		this.transAmount = transAmount;
	}

	public String getTransDesc() {
		return transDesc;
	}

	public void setTransDesc(String transDesc) {
		this.transDesc = transDesc;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getJumpUrl() {
		return jumpUrl;
	}

	public void setJumpUrl(String jumpUrl) {
		this.jumpUrl = jumpUrl;
	}

	public String getPrivObligate() {
		return privObligate;
	}

	public void setPrivObligate(String privObligate) {
		this.privObligate = privObligate;
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
		return "FirstQuickPayBean [merId=" + merId + ", orderNo=" + orderNo
				+ ", orderDate=" + orderDate + ", transAmount=" + transAmount
				+ ", transDesc=" + transDesc + ", currencyCode=" + currencyCode
				+ ", jumpUrl=" + jumpUrl + ", privObligate=" + privObligate
				+ ", custId=" + custId + ", bankCardNo=" + bankCardNo
				+ ", bankCardType=" + bankCardType + ", bankId=" + bankId + "]";
	}

}
