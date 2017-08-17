/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gp.mapper.vo;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-service
 * 
 *  Package Name    : com.gy.hsxt.gp.mapper.vo
 * 
 *  File Name       : RefundVo.java
 * 
 *  Creation Date   : 2015-10-16
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class RefundVo {
	private String bankOrderNo;
	private String origOrderNo;
	private String merId;

	public String getBankOrderNo() {
		return bankOrderNo;
	}

	public void setBankOrderNo(String bankOrderNo) {
		this.bankOrderNo = bankOrderNo;
	}

	public String getOrigOrderNo() {
		return origOrderNo;
	}

	public void setOrigOrderNo(String origOrderNo) {
		this.origOrderNo = origOrderNo;
	}

	public String getMerId() {
		return merId;
	}

	public void setMerId(String merId) {
		this.merId = merId;
	}
}
