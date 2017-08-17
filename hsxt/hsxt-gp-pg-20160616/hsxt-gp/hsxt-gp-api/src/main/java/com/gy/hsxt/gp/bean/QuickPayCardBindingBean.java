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
 *  File Name       : QuickPayCardBindingBean.java
 * 
 *  Creation Date   : 2016-5-25
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 中国银联快捷支付银行卡签约bean
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class QuickPayCardBindingBean implements Serializable {

	private static final long serialVersionUID = -3717516450071868677L;

	/** 商户号，1100000001：互商 1100000007：互生 第三方接入的酌情分配 */
	private String merId;

	/** 客户号，用于向用户中心同步签约号,最大长度21 */
	private String custId;

	/** 银行账户，最大长度19 */
	private String bankCardNo;

	/** 借贷记标识，即：借记卡 or 信用卡 [1-借记卡, 2-贷记卡] */
	private int bankCardType;

	/** 银行ID,参照《支付系统设计文档》附录银行名称-简码对照表,最大长度4 */
	private String bankId;

	/**
	 * 构造函数
	 */
	public QuickPayCardBindingBean() {
	}

	/**
	 * 构造函数2
	 * 
	 * @param merId
	 * @param custId
	 * @param bankId
	 * @param bankCardNo
	 * @param bankCardType
	 */
	public QuickPayCardBindingBean(String merId, String custId, 
			String bankCardNo, int bankCardType, String bankId) {
		this.merId = merId;
		this.custId = custId;
		this.bankCardNo = bankCardNo;
		this.bankCardType = bankCardType;
		this.bankId = bankId;
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

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public int getBankCardType() {
		return bankCardType;
	}

	public void setBankCardType(int bankCardType) {
		this.bankCardType = bankCardType;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	@Override
	public String toString() {
		return "QuickPayCardBindingBean [merId=" + merId + ", custId=" + custId
				+ ", bankCardNo=" + bankCardNo + ", bankCardType="
				+ bankCardType + ", bankId=" + bankId + "]";
	}

}
