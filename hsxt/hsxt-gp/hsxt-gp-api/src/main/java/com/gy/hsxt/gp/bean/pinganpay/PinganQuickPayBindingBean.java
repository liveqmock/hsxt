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

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-api
 * 
 *  Package Name    : com.gy.hsxt.gp.bean
 * 
 *  File Name       : PinganQuickPayBindingBean.java
 * 
 *  Creation Date   : 2016-5-25
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 平安银行快捷支付银行卡签约bean
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class PinganQuickPayBindingBean implements Serializable {

	private static final long serialVersionUID = -7716689361665669642L;

	/** 商户号，1100000001：互商 1100000007：互生 第三方接入的酌情分配 */
	private String merId;

	/** 客户号，用于向用户中心同步签约号,最大长度21 */
	private String custId;

	/**
	 * 构造函数
	 */
	public PinganQuickPayBindingBean() {
	}

	/**
	 * 构造函数2
	 * 
	 * @param merId
	 * @param custId
	 */
	public PinganQuickPayBindingBean(String merId, String custId) {
		this.merId = merId;
		this.custId = custId;
	}

	public String getMerId() {
		return merId;
	}

	public void setMerId(String merId) {
		this.merId = merId;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Override
	public String toString() {
		return "PinganQuickPayBindingBean [merId=" + merId + ", custId=" + custId
				+ "]";
	}

}
