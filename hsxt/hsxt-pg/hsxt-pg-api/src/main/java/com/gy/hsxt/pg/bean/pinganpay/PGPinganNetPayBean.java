/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bean.pinganpay;

import java.io.Serializable;

import com.gy.hsxt.pg.bean.PGNetPayBean;
import com.gy.hsxt.pg.constant.PGConstant.PGPinganPaymentUIType;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-api
 * 
 *  Package Name    : com.gy.hsxt.pg.bean.pinganpay
 * 
 *  File Name       : PGPinganNetPayBean.java
 * 
 *  Creation Date   : 2016年6月23日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 平安银行网银支付
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class PGPinganNetPayBean extends PGNetPayBean implements Serializable {

	private static final long serialVersionUID = 623028424292478703L;

	/** 支付页面类型, 0:通用, 1:单独显示B2B, 2:单独显示B2C(默认) */
	private int paymentUIType = PGPinganPaymentUIType.ONLY_B2C;

	private String remark;

	public int getPaymentUIType() {
		return paymentUIType;
	}

	public void setPaymentUIType(int paymentUIType) {
		if ((0 > paymentUIType) || (2 < paymentUIType)) {
			paymentUIType = PGPinganPaymentUIType.ONLY_B2C;
		}

		this.paymentUIType = paymentUIType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "PGPinganNetPayBean [paymentUIType=" + paymentUIType
				+ ", goodsName=" + getGoodsName() + ", jumpUrl=" + getJumpUrl()
				+ ", privObligate=" + getPrivObligate() + ", merType="
				+ getMerType() + ", bankOrderNo=" + getBankOrderNo()
				+ ", transAmount=" + getTransAmount() + ", transDate="
				+ getTransDate() + ", currencyCode=" + getCurrencyCode()
				+ ", privObligate2=" + getPrivObligate2() + "]";
	}
}