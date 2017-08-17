/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bean.pinganpay;

import com.gy.hsxt.pg.bean.parent.PGParentBean;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-api
 * 
 *  Package Name    : com.gy.hsxt.pg.bean.pinganpay
 * 
 *  File Name       : PGPinganQuickPaySmsCodeBean.java
 * 
 *  Creation Date   : 2016-7-8
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 平安快捷短信验证码
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class PGPinganQuickPaySmsCodeBean extends PGParentBean {

	private static final long serialVersionUID = -7464605814304077075L;

	/** 签约号,最大长度24 */
	private String bindingNo;

	/** 传递商户私有数据,用于回调时带回,最大长度60 */
	private String privObligate;

	public PGPinganQuickPaySmsCodeBean() {
	}

	public String getBindingNo() {
		return bindingNo;
	}

	public void setBindingNo(String bindingNo) {
		this.bindingNo = bindingNo;
	}

	public String getPrivObligate() {
		return privObligate;
	}

	public void setPrivObligate(String privObligate) {
		this.privObligate = privObligate;
	}

	@Override
	public String toString() {
		return "PGQuickPaySmsCodeBean [merType=" + getMerType()
				+ ", bankOrderNo=" + getBankOrderNo() + ", goodsName="
				+ getGoodsName() + ", transAmount=" + getTransAmount()
				+ ", transDate=" + getTransDate() + ", currencyCode="
				+ getCurrencyCode() + ", privObligate2=" + privObligate2
				+ "bindingNo=" + bindingNo + ", privObligate=" + privObligate
				+ "]";
	}

}
