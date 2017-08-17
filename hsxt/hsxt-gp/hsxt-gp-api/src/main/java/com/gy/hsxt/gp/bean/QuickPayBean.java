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

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-api
 * 
 *  Package Name    : com.gy.hsxt.gp.bean
 * 
 *  File Name       : QuickPayBean.java
 * 
 *  Creation Date   : 2015-9-28
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 中国银联非首次支付beans
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class QuickPayBean implements Serializable{
	
	private static final long serialVersionUID = 7200148096586091075L;

	/** 商户号，1100000001：互商           1100000007：互生 第三方接入的酌情分配 */
	private String merId;
	
	/** 业务订单号，最大32位字符 */	
	private String orderNo;
	
	/** 签约号，最大24位字符 */	
	private String bindingNo;
	
	/** 短信验证码，最大10位字符 */
	private String smsCode;
	
	/** 
	 * 构造函数
	 */
	public QuickPayBean() {
	}

	/** 
	 * 构造函数
	 */
	public QuickPayBean(String merId, String orderNo, String bindingNo,
			String smsCode) {
		this.merId = merId;
		this.orderNo = orderNo;
		this.bindingNo = bindingNo;
		this.smsCode = smsCode;
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

	public String getBindingNo() {
		return bindingNo;
	}

	public void setBindingNo(String bindingNo) {
		this.bindingNo = bindingNo;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	@Override
	public String toString() {
		return "QuickPayBean [merId=" + merId + ", orderNo=" + orderNo
				+ ", bindingNo=" + bindingNo + ", smsCode=" + smsCode + "]";
	}
	
}
