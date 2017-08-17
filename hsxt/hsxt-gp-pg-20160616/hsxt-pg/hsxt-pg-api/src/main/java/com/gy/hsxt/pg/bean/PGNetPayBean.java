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
 *  File Name       : PGNetPayBean.java
 * 
 *  Creation Date   : 2015-10-9
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 网银支付
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class PGNetPayBean extends PGParentBean {

	private static final long serialVersionUID = 6115900347808815509L;
	
	/** 支付成功后，银行将会通过浏览器跳转到这个页面，,最大长度80 */
	private String jumpUrl;
	
	/** 传递商户私有数据,用于回调时带回,最大长度60 */	
	private String privObligate;		

	public PGNetPayBean() {
		super();
	}
	
	public String getPrivObligate() {
		return privObligate;
	}

	public void setPrivObligate(String privObligate) {
		this.privObligate = privObligate;
	}

	public String getJumpUrl() {
		return jumpUrl;
	}

	public void setJumpUrl(String jumpUrl) {
		this.jumpUrl = jumpUrl;
	}

	@Override
	public String toString() {
		return "PGNetPayBean [jumpUrl=" + jumpUrl + ", privObligate="
				+ privObligate + ", toString()=" + super.toString() + "]";
	}
}
