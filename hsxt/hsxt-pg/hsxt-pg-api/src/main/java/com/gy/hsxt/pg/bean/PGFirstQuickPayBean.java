/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bean;

import com.gy.hsxt.pg.bean.parent.PGParentBean;


/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-api
 * 
 *  Package Name    : com.gy.hsxt.pg.bean
 * 
 *  File Name       : PGFirstQuickPayBean.java
 * 
 *  Creation Date   : 2015-10-9
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 快捷支付首次
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class PGFirstQuickPayBean extends PGParentBean {

	private static final long serialVersionUID = 331462243607531589L;

	/**银行账户，最大长度19*/
	private String bankCardNo;	
	
	/**借贷记标识，即：借记卡 or 信用卡
	1 - 借记卡
	2 - 贷记卡*/
	private int bankCardType;
	
	/**银行ID,参照《支付系统设计文档》附录银行名称-简码对照表,最大长度4*/
	private String bankId;
	
	/** 支付成功后，银行将会通过浏览器跳转到这个页面，,最大长度80 */
	private String jumpUrl;
	
	/** 传递商户私有数据,用于回调时带回,最大长度60 */
	private String privObligate;
	
	public PGFirstQuickPayBean() {
		super();
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

	public String getJumpUrl() {
		return jumpUrl;
	}

	public void setJumpUrl(String jumpUrl) {
		this.jumpUrl = jumpUrl;
	}

	public String getPrivObligate() {
		return privObligate;
	}

	public void setPrivObligate(String privObligate) {
		this.privObligate = privObligate;
	}

	@Override
	public String toString() {
		return "PGFirstQuickPayBean [bankCardNo=" + bankCardNo
				+ ", bankCardType=" + bankCardType + ", bankId=" + bankId
				+ ", jumpUrl=" + jumpUrl + ", privObligate=" + privObligate
				+ ", toString()=" + super.toString() + "]";
	}	
}
