/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bean;

import java.io.Serializable;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-api
 * 
 *  Package Name    : com.gy.hsxt.pg.bean
 * 
 *  File Name       : PGQuickPayBean.java
 * 
 *  Creation Date   : 2015-10-8
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 中国银联快捷支付(非首次)
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class PGQuickPayBean implements Serializable {

	private static final long serialVersionUID = -5308914546993656787L;

	/** 商户类型 100-互生线（默认） 101-互商线 200-第三方接入商户（预留，指淘宝、京东等） */
	private int merType;

	/** 银行支付单号，最大16位字符 */
	private String bankOrderNo;

	/** 签约号，最大24位字符 */
	private String bindingNo;

	/** 短信验证码，最大10位字符 */
	private String smsCode;

	/**
	 * 构造函数
	 */
	public PGQuickPayBean() {
	}

	/**
	 * 构造函数
	 */
	public PGQuickPayBean(int merType, String bankOrderNo, String bindingNo,
			String smsCode) {
		this.merType = merType;
		this.bankOrderNo = bankOrderNo;
		this.bindingNo = bindingNo;
		this.smsCode = smsCode;
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

	public int getMerType() {
		return merType;
	}

	public void setMerType(int merType) {
		this.merType = merType;
	}

	public String getBankOrderNo() {
		return bankOrderNo;
	}

	public void setBankOrderNo(String bankOrderNo) {
		this.bankOrderNo = bankOrderNo;
	}

	@Override
	public String toString() {
		return "PGQuickPayBean [merType=" + merType + ", bankOrderNo="
				+ bankOrderNo + ", bindingNo=" + bindingNo + ", smsCode="
				+ smsCode + "]";
	}

}
