/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.pingan.b2c.params;

import java.math.BigInteger;
import java.util.Date;

import com.gy.hsxt.pg.bankadapter.common.utils.StringHelper;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.pingan.b2c.params
 * 
 *  File Name       : PinganB2cPayParam.java
 * 
 *  Creation Date   : 2016年6月23日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 平安网银支付参数beans
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class PinganB2cPayParam {

	/** 支付页面类型, 0:通用(默认), 1:单独显示B2B, 2:单独显示B2C */
	private int paymentUIType = 0;

	// 银行订单号(20位字符)
	private String orderNo;

	// 订单日期
	private Date orderDate;

	// 商品名称(128位字符)
	private String goodsName;

	// 币种
	private String currencyId;

	// 支付金额(必填, 单位：分)
	private BigInteger payAmount = new BigInteger("0");

	// 这个地址用于接收银行服务的支付成功回调，这个地址在支付时发给银行
	// 在接收回调时，还需要返回给银行一个商户显示成功支付的页面的地址
	private String notifyURL;

	// 显示支付成功页面
	private String jumpURL;

	// 备注
	private String remark;

	public int getPaymentUIType() {
		return paymentUIType;
	}

	public void setPaymentUIType(int paymentUIType) {
		this.paymentUIType = paymentUIType;
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

	public String getGoodsName() {
		return StringHelper.avoidNull(goodsName);
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getCurrencyId() {
		return StringHelper.avoidNull(currencyId);
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public BigInteger getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigInteger payAmount) {
		this.payAmount = payAmount;
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

	public String getRemark() {
		return StringHelper.avoidNull(remark);
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
