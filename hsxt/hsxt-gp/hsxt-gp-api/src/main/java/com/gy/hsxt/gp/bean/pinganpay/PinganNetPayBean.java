/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gp.bean.pinganpay;

import java.io.Serializable;
import java.util.Date;

import com.gy.hsxt.gp.bean.NetPayBean;
import com.gy.hsxt.gp.constant.GPConstant.PinganPaymentUIType;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-api
 * 
 *  Package Name    : com.gy.hsxt.gp.bean.pinganpay
 * 
 *  File Name       : PinganNetPayBean.java
 * 
 *  Creation Date   : 2016-6-28
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 平安银行网银页面支付beans
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class PinganNetPayBean extends NetPayBean implements Serializable {

	private static final long serialVersionUID = -1983369128827491682L;
	
	/** 支付页面类型, 0:通用(默认), 1:单独显示B2B, 2:单独显示B2C */
	private int paymentUIType = PinganPaymentUIType.ALL;

	/**
	 * 构造函数1
	 */
	public PinganNetPayBean() {
	}

	/**
	 * 构造函数2
	 */
	public PinganNetPayBean(String merId, String orderNo, Date orderDate,
			String transAmount, String jumpUrl) {
		super(merId, orderNo, orderDate, transAmount, jumpUrl);
	}

	public int getPaymentUIType() {
		return paymentUIType;
	}

	public void setPaymentUIType(int paymentUIType) {
		this.paymentUIType = paymentUIType;
	}

	@Override
	public String toString() {
		return "PinganNetPayBean [paymentUIType=" + paymentUIType
				+ ", transDesc=" + getTransDesc() + ", currencyCode="
				+ getCurrencyCode() + ", goodsName=" + getGoodsName()
				+ ", privObligate=" + getPrivObligate() + ", merId="
				+ getMerId() + ", orderNo=" + getOrderNo()
				+ ", orderDate=" + getOrderDate() + ", transAmount="
				+ getTransAmount() + ", jumpUrl=" + getJumpUrl() + "]";
	}
}
