/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gp.api;

import java.util.List;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gp.bean.FirstQuickPayBean;
import com.gy.hsxt.gp.bean.MobilePayBean;
import com.gy.hsxt.gp.bean.NetPayBean;
import com.gy.hsxt.gp.bean.PaymentOrderState;
import com.gy.hsxt.gp.bean.QuickPayBean;
import com.gy.hsxt.gp.bean.QuickPayCardBindingBean;
import com.gy.hsxt.gp.bean.QuickPaySmsCodeBean;
import com.gy.hsxt.gp.bean.pinganpay.PinganNetPayBean;
import com.gy.hsxt.gp.bean.pinganpay.PinganQuickPayBean;
import com.gy.hsxt.gp.bean.pinganpay.PinganQuickPayBindingBean;
import com.gy.hsxt.gp.bean.pinganpay.PinganQuickPaySmsCodeBean;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-api
 * 
 *  Package Name    : com.gy.hsxt.gp.api
 * 
 *  File Name       : IGPPaymentService.java
 * 
 *  Creation Date   : 2015-9-28
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 中国银联业务接口，包括网银支付，快捷支付，手机支付
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public interface IGPPaymentService {

	/**
	 * 获取平安网银支付URL接口[平安银行]
	 * 
	 * @param netPayBean
	 *            封装的参数对象
	 * @param srcSubsysId
	 *            子系统标识
	 * @return url地址
	 * @throws HsException
	 */
	public String getPinganNetPayUrl(PinganNetPayBean netPayBean,
			String srcSubsysId) throws HsException;

	/**
	 * 获取平安快捷支付银行卡签约的URL[平安银行]
	 * 
	 * @param cardBindingBean
	 *            封装的参数对象
	 * @param srcSubsysId
	 *            子系统标识
	 * @return
	 */
	public String getPinganQuickPayBindingUrl(
			PinganQuickPayBindingBean cardBindingBean, String srcSubsysId);

	/**
	 * 获取平安快捷支付短信验证码[平安银行]
	 * 
	 * @param quickPaySmsCodeBean
	 *            封装的参数对象
	 * @return 成功 or 失败
	 * @throws HsException
	 */
	public boolean getPinganQuickPaySmsCode(
			PinganQuickPaySmsCodeBean quickPaySmsCodeBean, String srcSubsysId)
			throws HsException;

	/**
	 * 获取平安非首次快捷支付URL地址[平安银行]
	 * 
	 * @param quickPayBean
	 *            封装的参数对象
	 * @return 非首次快捷支付URL地址
	 * @throws HsException
	 */
	public String getPinganQuickPayUrl(PinganQuickPayBean quickPayBean,
			String srcSubsysId) throws HsException;

	/**
	 * 获取网银支付URL接口
	 * 
	 * @param netPayBean
	 *            封装的参数对象
	 * @return url地址
	 * @throws HsException
	 */
	public String getNetPayUrl(NetPayBean netPayBean, String srcSubsysId)
			throws HsException;

	/**
	 * 获取快捷支付银行卡签约的URL
	 * 
	 * @param cardBindingBean
	 *            封装的参数对象
	 * @param srcSubsysId
	 *            子系统标识
	 * @return
	 */
	public String getQuickPayCardBindingUrl(
			QuickPayCardBindingBean cardBindingBean, String srcString);

	/**
	 * 获取快捷支付URL(首次)
	 * 
	 * @param quickPayBean
	 *            封装的参数对象
	 * @return 首次快捷支付URL地址
	 * @throws HsException
	 */
	public String getFirstQuickPayUrl(FirstQuickPayBean firstQuickPayBean,
			String srcSubsysId) throws HsException;

	/**
	 * 获取非首次快捷支付URL地址
	 * 
	 * @param quickPayBean
	 *            封装的参数对象
	 * @return 非首次快捷支付URL地址
	 * @throws HsException
	 */
	public String getQuickPayUrl(QuickPayBean quickPayBean, String srcSubsysId)
			throws HsException;

	/**
	 * 获取快捷支付短信验证码
	 * 
	 * @param quickPaySmsCodeBean
	 *            封装的参数对象
	 * @return 成功 or 失败
	 * @throws HsException
	 */
	public boolean getQuickPaySmsCode(QuickPaySmsCodeBean quickPaySmsCodeBean,
			String srcSubsysId) throws HsException;

	/**
	 * 获取手机移动支付TN码
	 * 
	 * @param mobilePayBean
	 *            封装的参数对象
	 * @return TN交易流水号
	 * @throws HsException
	 */
	public String getMobilePayTn(MobilePayBean mobilePayBean, String srcSubsysId)
			throws HsException;

	/**
	 * 支付结果查询[单笔]
	 * 
	 * @param merId
	 *            商户号
	 * @param orderNo
	 *            业务订单号
	 * @return
	 * @throws HsException
	 */
	public PaymentOrderState getPaymentOrderState(String merId, String orderNo)
			throws HsException;

	/**
	 * 支付结果查询[批量]
	 * 
	 * @param merId
	 *            商户号
	 * @param orderNoList
	 *            业务订单号列表
	 * @return
	 * @throws HsException
	 */
	public List<PaymentOrderState> getPaymentOrderState(String merId,
			List<String> orderNoList) throws HsException;

}
