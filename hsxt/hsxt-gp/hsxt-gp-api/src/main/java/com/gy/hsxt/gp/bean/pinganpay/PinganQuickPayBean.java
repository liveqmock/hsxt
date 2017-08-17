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

import com.gy.hsxt.gp.bean.QuickPayBean;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-api
 * 
 *  Package Name    : com.gy.hsxt.gp.bean.pinganpay
 * 
 *  File Name       : PinganQuickPayBean.java
 * 
 *  Creation Date   : 2015-9-28
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 平安银行获取快捷支付短信验证码beans
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class PinganQuickPayBean extends QuickPayBean implements Serializable {
	private static final long serialVersionUID = -6122851026531607026L;

	public PinganQuickPayBean() {
	}

	@Override
	public String toString() {
		return "PinganQuickPayBean [merId=" + getMerId() + ", orderNo="
				+ getOrderNo() + ", bindingNo=" + getBindingNo() + ", smsCode="
				+ getSmsCode() + "]";
	}
}
