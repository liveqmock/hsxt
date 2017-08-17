/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.gy.hsxt.pg.bankadapter.chinapay.ChinapayOrderResultDTO;
import com.gy.hsxt.pg.bean.PGMobilePayTnBean;
import com.gy.hsxt.pg.bean.PGPaymentOrderState;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.service
 * 
 *  File Name       : IChinapayMobilePayAdapter.java
 * 
 *  Creation Date   : 2016年4月26日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 手机支付接口适配器(适配UPMP、UPACP)
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public interface IChinapayMobilePayAdapter {

	/**
	 * 调用银行接口获取手机移动支付TN码
	 * 
	 * @param mobilePayTnBean
	 * @return
	 */
	public String doGetMobilePayTn(PGMobilePayTnBean mobilePayTnBean);

	/**
	 * 解析银联手机支付回调结果
	 * 
	 * @param request
	 * @param cbParamMap
	 * @param bizType
	 * @return
	 * @throws Exception
	 */
	public ChinapayOrderResultDTO parseResultFromCallback(
			HttpServletRequest request, Map<String, String> cbParamMap,
			Integer bizType) throws Exception;

	/**
	 * 从银联获取手机支付状态
	 * 
	 * @param paymentOrderState
	 * @return
	 * @throws Exception
	 */
	public ChinapayOrderResultDTO getOrderStateFromBank(
			PGPaymentOrderState paymentOrderState) throws Exception;
}
