/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gp.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gp.api.IGPPaymentService;
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
import com.gy.hsxt.gp.common.utils.StringUtils;
import com.gy.hsxt.gp.constant.GPConstant.GPErrorCode;
import com.gy.hsxt.gp.mapper.TGpPaymentOrderMapper;
import com.gy.hsxt.gp.mapper.vo.TGpPaymentOrder;
import com.gy.hsxt.gp.service.impl.check.BasicPropertyCheck;
import com.gy.hsxt.gp.service.impl.handlers.ChinapayPaymentHandler;
import com.gy.hsxt.gp.service.impl.handlers.PinganPaymentHandler;
import com.gy.hsxt.gp.service.impl.parent.ParentPaymentService;
import com.gy.hsxt.pg.api.IPGPaymentService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-service
 * 
 *  Package Name    : com.gy.hsxt.gp.service
 * 
 *  File Name       : GPPaymentService.java
 * 
 *  Creation Date   : 2015-9-28
 * 
 *  Author          : zhangysh, 由zhangysh优化
 * 
 *  Purpose         : 支付业务接口，包括中国银联网银支付，快捷支付，手机支付，全渠道，及平安银行的网银、快捷支付
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Service("gpPaymentService")
public class GPPaymentService extends ParentPaymentService implements
		IGPPaymentService {
	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private TGpPaymentOrderMapper tGpPaymentOrderMapper;

	@Autowired
	private IPGPaymentService pgPaymentService;

	@Autowired
	private ChinapayPaymentHandler chinapayHandler;

	@Autowired
	private PinganPaymentHandler pinganPayHandler;

	/**
	 * 获取平安网银支付URL接口(平安银行)
	 * 
	 * @param netPayBean
	 *            参数对象
	 * @param srcSubsysId
	 *            子系统标识
	 * @return url地址
	 * @throws HsException
	 */
	@Override
	public String getPinganNetPayUrl(PinganNetPayBean netPayBean,
			String srcSubsysId) throws HsException {
		return pinganPayHandler.doGetPinganNetPayUrl(netPayBean, srcSubsysId);
	}

	/**
	 * 获取平安快捷支付银行卡签约的URL[平安银行]
	 * 
	 * @param cardBindingBean
	 *            封装的参数对象
	 * @return
	 */
	@Override
	public String getPinganQuickPayBindingUrl(
			PinganQuickPayBindingBean cardBindingBean, String srcSubsysId) {
		return pinganPayHandler.doGetPinganQuickPayBindingUrl(cardBindingBean);
	}

	/**
	 * 获取平安快捷支付短信验证码[平安银行]
	 * 
	 * @param quickPaySmsCodeBean
	 *            封装的参数对象
	 * @return 成功 or 失败
	 * @throws HsException
	 */
	@Override
	public boolean getPinganQuickPaySmsCode(
			PinganQuickPaySmsCodeBean quickPaySmsCodeBean, String srcSubsysId)
			throws HsException {
		return pinganPayHandler.doGetPinganQuickPaySmsCode(quickPaySmsCodeBean,
				srcSubsysId);
	}

	/**
	 * 获取平安非首次快捷支付URL地址[平安银行]
	 * 
	 * @param quickPayBean
	 *            封装的参数对象
	 * @param srcSubsysId
	 *            子系统标识
	 * @return 非首次快捷支付URL地址
	 * @throws HsException
	 */
	@Override
	public String getPinganQuickPayUrl(PinganQuickPayBean quickPayBean,
			String srcSubsysId) throws HsException {
		return pinganPayHandler.doGetPinganQuickPayUrl(quickPayBean,
				srcSubsysId);
	}

	/**
	 * 获取网银支付URL
	 * 
	 * @param netPayBean
	 * @param srcSubsysId
	 * @return url地址
	 * @throws HsException
	 */
	@Override
	public String getNetPayUrl(NetPayBean netPayBean, String srcSubsysId)
			throws HsException {
		try {
			// 平安银行网银
			if (netPayBean instanceof PinganNetPayBean) {
				return this.getPinganNetPayUrl((PinganNetPayBean) netPayBean,
						srcSubsysId);
			}
			// 中国银联网银
			return chinapayHandler.doGetNetPayUrl(netPayBean, srcSubsysId);
		} catch (HsException e) {
			logger.error("", e);
			throw e;
		} catch (Exception e) {
			logger.error("", e);
			throw new HsException(GPErrorCode.INNER_ERROR, e.getMessage());
		}
	}

	/**
	 * 获取快捷支付银行卡签约的URL
	 * 
	 * @param cardBindingBean
	 * @param srcSubsysId
	 * @return
	 */
	@Override
	public String getQuickPayCardBindingUrl(
			QuickPayCardBindingBean cardBindingBean, String srcSubsysId) {
		try {
			return chinapayHandler.doGetQuickPayCardBindingUrl(cardBindingBean);
		} catch (HsException e) {
			logger.error("", e);
			throw e;
		} catch (Exception e) {
			logger.error("", e);
			throw new HsException(GPErrorCode.INNER_ERROR, e.getMessage());
		}
	}

	/**
	 * 获取快捷支付URL(首次)
	 * 
	 * @param quickPayBean
	 * @param srcSubsysId
	 * @return 首次快捷支付URL地址
	 * @throws HsException
	 */
	@Override
	public String getFirstQuickPayUrl(FirstQuickPayBean firstQuickPayBean,
			String srcSubsysId) throws HsException {
		try {
			return chinapayHandler.doGetFirstQuickPayUrl(firstQuickPayBean,
					srcSubsysId);
		} catch (HsException e) {
			logger.error("", e);
			throw e;
		} catch (Exception e) {
			logger.error("", e);
			throw new HsException(GPErrorCode.INNER_ERROR, e.getMessage());
		}
	}

	/**
	 * 获取非首次快捷支付URL地址
	 * 
	 * @param quickPayBean
	 * @param srcSubsysId
	 * @return 非首次快捷支付URL地址
	 * @throws HsException
	 */
	@Override
	public String getQuickPayUrl(QuickPayBean quickPayBean, String srcSubsysId)
			throws HsException {
		try {
			return chinapayHandler.doGetQuickPayUrl(quickPayBean, srcSubsysId);
		} catch (HsException e) {
			logger.error("", e);
			throw e;
		} catch (Exception e) {
			logger.error("", e);
			throw new HsException(GPErrorCode.INNER_ERROR, e.getMessage());
		}
	}

	/**
	 * 获取快捷支付短信验证码
	 * 
	 * @param quickPaySmsCodeBean
	 * @param srcSubsysId
	 * @return 成功 or 失败
	 * @throws HsException
	 */
	@Override
	public boolean getQuickPaySmsCode(QuickPaySmsCodeBean quickPaySmsCodeBean,
			String srcSubsysId) throws HsException {
		try {
			return chinapayHandler.doGetQuickPaySmsCode(quickPaySmsCodeBean,
					srcSubsysId);
		} catch (HsException e) {
			logger.error("", e);
			throw e;
		} catch (Exception e) {
			logger.error("", e);
			throw new HsException(GPErrorCode.INNER_ERROR, e.getMessage());
		}
	}

	/**
	 * 获取手机移动支付TN码
	 * 
	 * @param mobilePayBean
	 * @param srcSubsysId
	 * @return TN交易流水号
	 * @throws HsException
	 */
	@Override
	public String getMobilePayTn(MobilePayBean mobilePayBean, String srcSubsysId)
			throws HsException {
		try {
			return chinapayHandler.doGetMobilePayTn(mobilePayBean, srcSubsysId);
		} catch (HsException e) {
			logger.error("", e);
			throw e;
		} catch (Exception e) {
			logger.error("", e);
			throw new HsException(GPErrorCode.INNER_ERROR, e.getMessage());
		}
	}

	/**
	 * 支付结果查询
	 * 
	 * @param merId
	 *            商户号
	 * @param orderNo
	 *            业务订单号
	 * @return
	 * @throws HsException
	 */
	@Override
	public PaymentOrderState getPaymentOrderState(String merId, String orderNo)
			throws HsException {
		// 校验商户号
		BasicPropertyCheck.checkMerId(merId);

		// 校验订单号
		BasicPropertyCheck.checkOrderNo(orderNo);

		return getPaymentOrderState(merId,
				Arrays.asList(new String[] { orderNo })).get(0);
	}

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
	@Override
	public List<PaymentOrderState> getPaymentOrderState(String merId,
			List<String> orderNoList) throws HsException {
		// 校验商户号、业务订单号
		BasicPropertyCheck.checkMerId(merId);
		BasicPropertyCheck.checkOrderNoList(orderNoList);

		List<TGpPaymentOrder> validOrderList = new ArrayList<TGpPaymentOrder>(
				10);

		try {
			for (String orderNo : orderNoList) {
				TGpPaymentOrder paymentOrder = tGpPaymentOrderMapper
						.selectOneBySelective(merId, orderNo);

				if (null != paymentOrder) {
					validOrderList.add(paymentOrder);
				}
			}

			// GP不存在该支付单
			if (0 >= validOrderList.size()) {
				String orderNoListStr = orderNoList.toString().replaceAll(
						"\\[|\\]", "");

				throw new HsException(GPErrorCode.DATA_NOT_EXIST,
						StringUtils.joinString("要查询的记录不存在! ", " merId=", merId,
								", orderNo=", orderNoListStr));
			}

			// 从支付网关同步支付状态
			return syncPaymentStateFromPG(merId, validOrderList);
		} catch (HsException e) {
			logger.error("", e);
			throw e;
		} catch (Exception e) {
			logger.error("查询支付单状态失败: ", e);
			throw new HsException(GPErrorCode.INNER_ERROR, e.getMessage());
		}
	}
}