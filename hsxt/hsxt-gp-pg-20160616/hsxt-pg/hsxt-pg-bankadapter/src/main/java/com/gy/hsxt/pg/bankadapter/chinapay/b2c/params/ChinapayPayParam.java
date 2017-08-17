/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.chinapay.b2c.params;

import java.math.BigInteger;
import java.net.URLEncoder;

import org.apache.commons.lang3.StringUtils;

/***************************************************************************
 * <PRE>
 *  Project Name    : bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.chinapay.b2c.params
 * 
 *  File Name       : ChinapayPayParam.java
 * 
 *  Creation Date   : 2014-10-23
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 中国银联B2C页面支付beans
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class ChinapayPayParam {
	// 银行代号(可选)
	private String bankCode;

	// 订单号(16位字符)
	private String orderNo;

	// 币种
	private String currencyId;

	// 支付金额(必填, 单位：分)
	private BigInteger payAmount = new BigInteger("0");

	// 订单日期
	private String orderDate;

	// 客户端端ip
	private String clientIp;

	// 这个地址用于接收银行服务的支付成功回调，这个地址在支付时发给银行。
	// 在接收回调时，还需要返回给银行一个商户显示成功支付的页面的地址。
	private String notifyURL;

	// 显示支付成功页面
	private String jumpURL;

	public ChinapayPayParam() {
	}

	public String getBankCode() {
		return StringUtils.isNotBlank(bankCode) ? bankCode : "";
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getClientIp() {
		try {
			return URLEncoder.encode(clientIp, "UTF-8");
		} catch (Exception e) {
		}

		return "127.0.0.1";
	}

	public String getCurrencyId() {
		// 默认为: 156 人民币
		return StringUtils.isBlank(currencyId) ? "156" : currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public BigInteger getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigInteger payAmount) {
		this.payAmount = payAmount;
	}

	public void setPayAmount(String payAmount) {
		this.payAmount = new BigInteger(payAmount);
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getNotifyURL() {
		return notifyURL;
	}

	public void setNotifyURL(String notifyURL) {
		this.notifyURL = notifyURL;
	}

	public String getJumpURL() {
		return jumpURL;
	}

	public void setJumpURL(String jumpURL) {
		this.jumpURL = jumpURL;
	}

	public static ChinapayPayParam build() {
		return new ChinapayPayParam();
	}
}
