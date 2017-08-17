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

import com.gy.hsxt.pg.bean.parent.PGParentBean;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-service
 * 
 *  Package Name    : com.gy.hsxt.gp.service
 * 
 *  File Name       : MobilePayTnBean.java
 * 
 *  Creation Date   : 2015-10-8
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
public class PGMobilePayTnBean extends PGParentBean implements Serializable {

	private static final long serialVersionUID = 4104092384139265875L;

	@Override
	public String toString() {
		return "PGMobilePayTnBean [getMerType()=" + getMerType()
				+ ", getBankOrderNo()=" + getBankOrderNo()
				+ ", getTransAmount()=" + getTransAmount()
				+ ", getTransDate()=" + getTransDate() + ", getCurrencyCode()="
				+ getCurrencyCode() + "]";
	}
}
