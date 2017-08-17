/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.api;

import java.util.List;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.pg.bean.PGFirstQuickPayBean;
import com.gy.hsxt.pg.bean.PGMobilePayTnBean;
import com.gy.hsxt.pg.bean.PGNetPayBean;
import com.gy.hsxt.pg.bean.PGPaymentOrderState;
import com.gy.hsxt.pg.bean.PGQuickPayBean;
import com.gy.hsxt.pg.bean.PGQuickPayCardBindingBean;
import com.gy.hsxt.pg.bean.PGQuickPaySmsCodeBean;
import com.gy.hsxt.pg.bean.pinganpay.PGPinganNetPayBean;
import com.gy.hsxt.pg.bean.pinganpay.PGPinganQuickPayBean;
import com.gy.hsxt.pg.bean.pinganpay.PGPinganQuickPayBindingBean;
import com.gy.hsxt.pg.bean.pinganpay.PGPinganQuickPaySmsCodeBean;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-service
 * 
 *  Package Name    : com.gy.hsxt.gp.service
 * 
 *  File Name       : PGPaymentService.java
 * 
 *  Creation Date   : 2015-9-29
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 支付接口
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public interface IPGPaymentService {

	/**
	 * 获取平安银行网银支付URL接口(平安银行)
	 * 
	 * @param bean
	 * @return
	 * @throws HsException
	 */
	public String getPinganNetPayUrl(PGPinganNetPayBean bean)
			throws HsException;

	/**
	 * 获取平安银行签约快捷支付URL接口(平安银行)
	 * 
	 * @param bean
	 * @return
	 * @throws HsException
	 */
	public String getPinganQuickPayBindingUrl(PGPinganQuickPayBindingBean bean)
			throws HsException;

	/**
	 * 获取平安银行快捷支付URL接口(非首次, 平安银行)
	 * 
	 * @param bean
	 * @return
	 * @throws HsException
	 */
	public String getPinganQuickPayUrl(PGPinganQuickPayBean bean)
			throws HsException;

	/**
	 * 获取平安银行快捷支付短信验证码接口(平安银行)
	 * 
	 * @param bean
	 * @return
	 * @throws HsException
	 */
	public boolean getPinganQuickPaySmsCode(PGPinganQuickPaySmsCodeBean bean)
			throws HsException;

	/**
	 * 获取网银支付URL接口
	 * 
	 * @param bean
	 * @return
	 * @throws HsException
	 */
	public String getNetPayUrl(PGNetPayBean bean) throws HsException;

	/**
	 * 获取银行卡签约快捷支付URL接口
	 * 
	 * @param cardBindingBean
	 * @return
	 * @throws HsException
	 */
	public String getQuickPayCardBindingUrl(
			PGQuickPayCardBindingBean cardBindingBean) throws HsException;

	/**
	 * 获取快捷支付URL(首次)接口
	 * 
	 * @param firstQuickPayBean
	 * @return
	 * @throws HsException
	 */
	public String getFirstQuickPayUrl(PGFirstQuickPayBean firstQuickPayBean)
			throws HsException;

	/**
	 * 获取快捷支付URL(非首次)接口
	 * 
	 * @param quickPayBean
	 * @return
	 * @throws HsException
	 */
	public String getQuickPayUrl(PGQuickPayBean quickPayBean)
			throws HsException;

	/**
	 * 获取快捷支付短信验证码接口
	 * 
	 * @param bean
	 * @return
	 * @throws HsException
	 */
	public boolean getQuickPaySmsCode(PGQuickPaySmsCodeBean bean)
			throws HsException;

	/**
	 * 获取手机支付TN码接口
	 * 
	 * @param mobilePayTnBean
	 * @return
	 * @throws HsException
	 */
	public String getMobilePayTn(PGMobilePayTnBean mobilePayTnBean)
			throws HsException;

	/**
	 * 支付退款接口
	 * 
	 * @param bankOrderNo
	 * @param origBankOrderNo
	 * @param merType
	 * @return
	 * @throws HsException
	 */
	public boolean refund(String bankOrderNo, String origBankOrderNo,
			int merType) throws HsException;

	/**
	 * 查询支付单状态
	 * 
	 * @param merType
	 * @param bankOrderNoList
	 * @return
	 * @throws HsException
	 */
	public List<PGPaymentOrderState> getPaymentOrderState(int merType,
			List<String> bankOrderNoList) throws HsException;
}
