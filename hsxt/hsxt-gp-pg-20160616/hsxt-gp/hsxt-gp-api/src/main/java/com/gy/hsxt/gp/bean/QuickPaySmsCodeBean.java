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
 *  File Name       : QuickPaySmsCodeBean.java
 * 
 *  Creation Date   : 2015-9-28
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 中国银联页面获取快捷支付短信验证码beans
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class QuickPaySmsCodeBean implements Serializable{

	private static final long serialVersionUID = 5265195704319746653L;

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

	/** 签约号,最大长度24 */
	private String bindingNo;

	/**
	 * 构造函数
	 */
	public QuickPaySmsCodeBean() {
	}

	/**
	 * 构造函数
	 */
	public QuickPaySmsCodeBean(String merId, String orderNo, Date orderDate,
			String transAmount, String jumpUrl, String bindingNo) {
		this.merId = merId;
		this.orderNo = orderNo;
		this.orderDate = orderDate;
		this.transAmount = transAmount;
		this.jumpUrl = jumpUrl;
		this.bindingNo=bindingNo;
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

	public String getBindingNo() {
		return bindingNo;
	}

	public void setBindingNo(String bindingNo) {
		this.bindingNo = bindingNo;
	}

	@Override
	public String toString() {
		return "QuickPaySmsCodeBean [merId=" + merId + ", orderNo=" + orderNo
				+ ", orderDate=" + orderDate + ", transAmount=" + transAmount
				+ ", transDesc=" + transDesc + ", currencyCode=" + currencyCode
				+ ", jumpUrl=" + jumpUrl + ", privObligate=" + privObligate
				+ ", bindingNo=" + bindingNo + "]";
	}
	
}
