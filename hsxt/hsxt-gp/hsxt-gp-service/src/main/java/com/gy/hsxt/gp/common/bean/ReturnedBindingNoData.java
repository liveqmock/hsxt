/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gp.common.bean;

import java.io.Serializable;

import com.gy.hsxt.gp.bean.QuickPayBindingNo;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-service
 * 
 *  Package Name    : com.gy.hsxt.gp.common.bean
 * 
 *  File Name       : ReturnedBindingNoData.java
 * 
 *  Creation Date   : 2015年12月25日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 响应的签约号对象数据
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class ReturnedBindingNoData implements Serializable {

	private static final long serialVersionUID = 925214322845242641L;

	private QuickPayBindingNo bindingNoData;
	private String alertMsg;

	public ReturnedBindingNoData() {
	}

	public ReturnedBindingNoData(QuickPayBindingNo bindingNo, String alertMsg) {
		this.setAlertMsg(alertMsg);
		this.setBindingNoData(bindingNo);
	}

	public QuickPayBindingNo getBindingNoData() {
		return bindingNoData;
	}

	public void setBindingNoData(QuickPayBindingNo bindingNoData) {
		this.bindingNoData = bindingNoData;
	}

	public String getAlertMsg() {
		return alertMsg;
	}

	public void setAlertMsg(String alertMsg) {
		this.alertMsg = alertMsg;
	}

}
