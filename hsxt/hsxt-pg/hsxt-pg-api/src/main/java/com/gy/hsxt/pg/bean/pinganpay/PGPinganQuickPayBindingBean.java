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

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-api
 * 
 *  Package Name    : com.gy.hsxt.pg.bean.pinganpay
 * 
 *  File Name       : PGPinganQuickPayBindingBean.java
 * 
 *  Creation Date   : 2016-5-25
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 平安快捷支付银行卡签约bean
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class PGPinganQuickPayBindingBean implements Serializable {

	private static final long serialVersionUID = -3396092961125122621L;

	/** 请求id，最大16位字符, 在此只用作标识唯一 */
	private String requestId;

	/** 商户类型 100-互生线（默认） 101-互商线 200-第三方接入商户（预留，指淘宝、京东等） */
	private int merType;

	/**
	 * 构造函数
	 */
	public PGPinganQuickPayBindingBean() {
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

	@Override
	public String toString() {
		return "PGQuickPayCardBindingBean [requestId=" + requestId
				+ ", merType=" + merType + "]";
	}

}
