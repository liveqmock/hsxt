/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.pg.api.IPGPaymentService;
import com.gy.hsxt.pg.bankadapter.common.beans.BankPaymentOrderResultDTO;
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
import com.gy.hsxt.pg.common.utils.StringUtils;
import com.gy.hsxt.pg.constant.PGConstant.PGErrorCode;
import com.gy.hsxt.pg.constant.PGConstant.PGPayChannel;
import com.gy.hsxt.pg.mapper.TPgBankPaymentOrderMapper;
import com.gy.hsxt.pg.mapper.vo.TPgBankPaymentOrder;
import com.gy.hsxt.pg.service.impl.check.BasicPropertyCheck;
import com.gy.hsxt.pg.service.impl.handlers.ChinapayPaymentHandler;
import com.gy.hsxt.pg.service.impl.handlers.PinganPaymentHandler;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.service.impl
 * 
 *  File Name       : PGPaymentService.java
 * 
 *  Creation Date   : 2015-10-12
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : PG支付接口服务
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Service("pgPaymentService")
public class PGPaymentService implements IPGPaymentService {

	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private TPgBankPaymentOrderMapper bankPaymentOrderMapper;

	@Autowired
	private ChinapayMobilePayAdapter mobilePayAdapter;

	@Autowired
	private ChinapayPaymentHandler chinapayPaymentHandler;

	@Autowired
	private PinganPaymentHandler pinganPaymentHandler;

	/**
	 * 获取网银支付URL
	 * 
	 * @param netPayBean
	 * @return url地址
	 * @throws HsException
	 */
	@Override
	public String getPinganNetPayUrl(PGPinganNetPayBean netPayBean)
			throws HsException {

		try {
			return pinganPaymentHandler.doGetPinganNetPayUrl(netPayBean);
		} catch (HsException e) {
			logger.error("", e);
			throw e;
		} catch (Exception e) {
			logger.error("", e);
			throw new HsException(PGErrorCode.INNER_ERROR, e.getMessage());
		}
	}

	/**
	 * 获取平安银行签约快捷支付URL接口(平安银行)
	 * 
	 * @param bean
	 * @return
	 * @throws HsException
	 */
	@Override
	public String getPinganQuickPayBindingUrl(PGPinganQuickPayBindingBean bean)
			throws HsException {

		try {
			return pinganPaymentHandler.doGetPinganQuickPayBindingUrl(bean);
		} catch (HsException e) {
			logger.error("", e);
			throw e;
		} catch (Exception e) {
			logger.error("", e);
			throw new HsException(PGErrorCode.INNER_ERROR, e.getMessage());
		}
	}

	/**
	 * 获取平安银行快捷支付短信验证码接口(平安银行)
	 * 
	 * @param bean
	 * @return
	 * @throws HsException
	 */
	@Override
	public boolean getPinganQuickPaySmsCode(PGPinganQuickPaySmsCodeBean bean)
			throws HsException {

		try {
			return pinganPaymentHandler.doGetPinganQuickPaySmsCode(bean);
		} catch (HsException e) {
			logger.error("", e);
			throw e;
		} catch (Exception e) {
			logger.error("", e);
			throw new HsException(PGErrorCode.INNER_ERROR, e.getMessage());
		}
	}

	/**
	 * 获取网银支付URL接口
	 * 
	 * @param bean
	 * @return
	 * @throws HsException
	 */
	@Override
	public String getPinganQuickPayUrl(PGPinganQuickPayBean bean)
			throws HsException {

		try {
			return pinganPaymentHandler.doGetPinganQuickPayUrl(bean);
		} catch (HsException e) {
			logger.error("", e);
			throw e;
		} catch (Exception e) {
			logger.error("", e);
			throw new HsException(PGErrorCode.INNER_ERROR, e.getMessage());
		}
	}

	/**
	 * 获取网银支付URL
	 * 
	 * @param netPayBean
	 * @return url地址
	 * @throws HsException
	 */
	@Override
	public String getNetPayUrl(PGNetPayBean netPayBean) throws HsException {

		try {
			// 平安银行
			if (netPayBean instanceof PGPinganNetPayBean) {
				return this.getPinganNetPayUrl((PGPinganNetPayBean) netPayBean);
			}
			// 中国银联
			return chinapayPaymentHandler.doGetNetPayUrl(netPayBean);
		} catch (HsException e) {
			logger.error("", e);
			throw e;
		} catch (Exception e) {
			logger.error("", e);
			throw new HsException(PGErrorCode.INNER_ERROR, e.getMessage());
		}
	}

	/**
	 * 获取银行卡签约快捷支付URL接口
	 * 
	 * @param cardBindingBean
	 * @return
	 * @throws HsException
	 */
	@Override
	public String getQuickPayCardBindingUrl(
			PGQuickPayCardBindingBean cardBindingBean) throws HsException {
		try {
			return chinapayPaymentHandler
					.doGetQuickPayCardBindingUrl(cardBindingBean);
		} catch (HsException e) {
			logger.error("", e);
			throw e;
		} catch (Exception e) {
			logger.error("", e);
			throw new HsException(PGErrorCode.INNER_ERROR, e.getMessage());
		}
	}

	/**
	 * 获取快捷支付URL(首次)
	 * 
	 * @param firstQuickPayBean
	 * @return 首次快捷支付URL地址
	 * @throws HsException
	 */
	@Override
	public String getFirstQuickPayUrl(PGFirstQuickPayBean firstQuickPayBean)
			throws HsException {
		try {
			return chinapayPaymentHandler
					.doGetFirstQuickPayUrl(firstQuickPayBean);
		} catch (HsException e) {
			logger.error("", e);
			throw e;
		} catch (Exception e) {
			logger.error("", e);
			throw new HsException(PGErrorCode.INNER_ERROR, e.getMessage());
		}
	}

	/**
	 * 获取非首次快捷支付URL地址
	 * 
	 * @param quickPayBean
	 * @return 非首次快捷支付URL地址
	 * @throws HsException
	 */
	@Override
	public String getQuickPayUrl(PGQuickPayBean quickPayBean)
			throws HsException {
		try {
			return chinapayPaymentHandler.doGetQuickPayUrl(quickPayBean);
		} catch (HsException e) {
			logger.error("", e);
			throw e;
		} catch (Exception e) {
			logger.error("", e);
			throw new HsException(PGErrorCode.INNER_ERROR, e.getMessage());
		}
	}

	/**
	 * 获取快捷支付短信验证码
	 * 
	 * @param quickPaySmsCodeBean
	 * @return 成功 or 失败
	 * @throws HsException
	 */
	@Override
	public boolean getQuickPaySmsCode(PGQuickPaySmsCodeBean quickPaySmsCodeBean)
			throws HsException {
		try {
			return chinapayPaymentHandler
					.doGetQuickPaySmsCode(quickPaySmsCodeBean);
		} catch (HsException e) {
			logger.error("", e);
			throw e;
		} catch (Exception e) {
			logger.error("", e);
			throw new HsException(PGErrorCode.INNER_ERROR, e.getMessage());
		}
	}

	/**
	 * 获取手机移动支付TN码
	 * 
	 * @param mobilePayTnBean
	 * @return TN交易流水号
	 * @throws HsException
	 */
	@Override
	public String getMobilePayTn(PGMobilePayTnBean mobilePayTnBean)
			throws HsException {
		try {
			return chinapayPaymentHandler.doGetMobilePayTn(mobilePayTnBean);
		} catch (HsException e) {
			logger.error("", e);
			throw e;
		} catch (Exception e) {
			logger.error("", e);
			throw new HsException(PGErrorCode.INNER_ERROR, e.getMessage());
		}
	}

	/**
	 * 支付退款接口
	 * 
	 * @param bankOrderNo
	 * @param origBankOrderNo
	 * @param merType
	 * @return
	 * @throws HsException
	 */
	@Override
	public boolean refund(String bankOrderNo, String origBankOrderNo,
			int merType) throws HsException {
		try {
			return chinapayPaymentHandler.doRefund(bankOrderNo,
					origBankOrderNo, merType);
		} catch (HsException e) {
			logger.error("", e);
			throw e;
		} catch (Exception e) {
			logger.error("", e);
			throw new HsException(PGErrorCode.INNER_ERROR, e.getMessage());
		}
	}

	@Override
	public List<PGPaymentOrderState> getPaymentOrderState(int merType,
			List<String> bankOrderNoList) throws HsException {

		BasicPropertyCheck.checkMerType(merType);
		BasicPropertyCheck.checkBankOrderNoList(bankOrderNoList);

		List<PGPaymentOrderState> orderStates = new ArrayList<PGPaymentOrderState>(
				10);
		PGPaymentOrderState paymentOrderState;

		try {
			for (String bankOrderNo : bankOrderNoList) {
				paymentOrderState = bankPaymentOrderMapper
						.selectStateByBankOrderNo(bankOrderNo);

				if (null != paymentOrderState) {
					orderStates.add(paymentOrderState);

					// 手机支付由于无法从银行进行自动对账,所以只能逐个查询
					synUpmpOrderStateFromBank(paymentOrderState);
				}
			}

			// PG不存在该支付单
			if (0 >= orderStates.size()) {
				String orderNoListStr = bankOrderNoList.toString().replaceAll(
						"\\[|\\]", "");

				throw new HsException(PGErrorCode.DATA_NOT_EXIST,
						StringUtils.joinString("要查询的记录不存在! ",
								" bankOrderNoList=", orderNoListStr));
			}

			return orderStates;
		} catch (HsException e) {
			logger.error("", e);
			throw e;
		} catch (Exception e) {
			logger.error("", e);
			throw new HsException(PGErrorCode.INNER_ERROR, e.getMessage());
		}
	}

	/**
	 * 从银联获取手机支付状态
	 * 
	 * @param paymentOrderState
	 */
	private void synUpmpOrderStateFromBank(PGPaymentOrderState paymentOrderState) {
		// 手机支付由于无法从银行进行自动对账,所以只能逐个查询
		if (PGPayChannel.UPMP != paymentOrderState.getPayChannel()) {
			return;
		}

		// 支付单号
		String bankOrderNo = paymentOrderState.getBankOrderNo();

		try {
			BankPaymentOrderResultDTO dto = mobilePayAdapter
					.getOrderStateFromBank(paymentOrderState);

			// 银行订单状态
			int orderStatusInBank = dto.getOrderStatus().getIntValue();

			// 判断支付网关的状态跟银联是否一致, 如果不一致以银行为准
			if (paymentOrderState.getTransStatus() != orderStatusInBank) {
				TPgBankPaymentOrder record = new TPgBankPaymentOrder();
				record.setBankOrderNo(bankOrderNo);
				record.setBankOrderStatus(String.valueOf(orderStatusInBank));

				bankPaymentOrderMapper.updateStatusByBankOrderNo(record);

				paymentOrderState.setTransStatus(orderStatusInBank);
			}
		} catch (Exception e) {
			logger.info("", e);
		}
	}

}