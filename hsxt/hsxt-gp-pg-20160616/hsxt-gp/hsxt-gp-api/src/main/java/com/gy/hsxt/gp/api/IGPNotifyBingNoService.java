/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gp.api;

import com.gy.hsxt.gp.bean.QuickPayBindingNo;

/***************************************************************************
 * 
 * <PRE>
 *  Project Name    : hsxt-gp-api
 * 
 *  Package Name    : com.gy.hsxt.gp.api
 * 
 *  File Name       : IGPNotifyBingNoService.java
 * 
 *  Creation Date   : 2015-10-9
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 快捷支付签约号同步
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public interface IGPNotifyBingNoService {
	/**
	 * 快捷支付签约号同步,“支付系统-->用户中心”的“快捷支付签约号”同步接口，该接口由支付系统定义，然后由用户中心实现
	 * 
	 * @param bindingNoBean
	 * @return 同步成功or失败
	 */
	public boolean notifyQuickPayBindingNo(QuickPayBindingNo quickPayBindingNo);
}
