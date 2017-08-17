/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.service.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.pg.bankadapter.chinapay.BizValueMapSwapper;
import com.gy.hsxt.pg.bankadapter.chinapay.ChinapayOrderResultDTO;
import com.gy.hsxt.pg.bankadapter.chinapay.upacp.ChinapayUpacpFacade;
import com.gy.hsxt.pg.bankadapter.chinapay.upacp.params.UpacpPayTnParam;
import com.gy.hsxt.pg.bankadapter.chinapay.upmp.ChinapayUpmpFacade;
import com.gy.hsxt.pg.bankadapter.chinapay.upmp.params.UpmpPayTnParam;
import com.gy.hsxt.pg.bankadapter.common.constants.BanksConstants.ErrorCode;
import com.gy.hsxt.pg.bankadapter.common.exception.BankAdapterException;
import com.gy.hsxt.pg.bankadapter.common.utils.DateUtils;
import com.gy.hsxt.pg.bankadapter.common.utils.MoneyAmountHelper;
import com.gy.hsxt.pg.bean.PGMobilePayTnBean;
import com.gy.hsxt.pg.bean.PGPaymentOrderState;
import com.gy.hsxt.pg.common.bean.CallbackRouterInfo;
import com.gy.hsxt.pg.common.constant.Constant.PGCallbackBizType;
import com.gy.hsxt.pg.common.utils.CallbackUrlHelper;
import com.gy.hsxt.pg.constant.PGConstant.PGErrorCode;
import com.gy.hsxt.pg.constant.PGConstant.PGPayChannel;
import com.gy.hsxt.pg.service.IChinapayMobilePayAdapter;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.service.impl
 * 
 *  File Name       : ChinapayMobilePayAdapter.java
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
@Service("chinapayMobilePayAdapter")
public class ChinapayMobilePayAdapter implements IChinapayMobilePayAdapter {
	
	private Logger logger = Logger.getLogger(getClass());

	/** UPACP手机支付已经开启 (TODO 切换时要修改为true) **/
	private static final boolean UPACP_ENABLE = false;

	@Autowired
	private ChinapayUpmpFacade chinapayUpmpFacade;

	@Autowired
	private ChinapayUpacpFacade chinapayUpacpFacade;

	@Override
	public String doGetMobilePayTn(PGMobilePayTnBean mobilePayTnBean) {

		int payChannel = PGPayChannel.UPMP;
		int merType = mobilePayTnBean.getMerType();
		int bizType = PGCallbackBizType.CALLBACK_PAY;

		String jumpUrl = getJumpUrl(payChannel, merType, bizType);
		String notifyUrl = getNotifyUrl(payChannel, merType, bizType);

		String orderNo = mobilePayTnBean.getBankOrderNo();
		String orderDate = DateUtils.dateToString(
				mobilePayTnBean.getTransDate(), "yyyyMMdd");

		// 支付金额
		BigInteger payAmount = MoneyAmountHelper.fromYuanToFen(mobilePayTnBean
				.getTransAmount());

		try {
			UpacpPayTnParam params = new UpacpPayTnParam();
			params.setOrderNo(orderNo);
			params.setJumpUrl(jumpUrl);
			params.setNotifyUrl(notifyUrl);
			params.setOrderDate(orderDate);
			params.setPayAmount(payAmount);

			// UPACP全渠道
			if (UPACP_ENABLE) {
				return chinapayUpacpFacade.getPayWorker().doGetPayTN(params);
			}

			// UPMP手机支付
			return chinapayUpmpFacade.getPayWorker().doGetPayTN(
					(UpmpPayTnParam) params);
		} catch (BankAdapterException e) {
			if(ErrorCode.INVALID_PARAM == e.getErrorCode()) {
				throw new HsException(PGErrorCode.INVALID_PARAM, e.getErrorMsg());
			}
			
			throw e;
		} catch (Exception e) {
			logger.info("", e);

			throw new HsException(PGErrorCode.NET_ERROR, "PG支付网关与银行间通信失败!");
		}
	}

	@Override
	public ChinapayOrderResultDTO parseResultFromCallback(
			HttpServletRequest request, Map<String, String> cbParamMap,
			Integer bizType) throws Exception {

		ChinapayOrderResultDTO resultDTO = null;

		if (PGCallbackBizType.CALLBACK_PAY == bizType) {
			resultDTO = UPACP_ENABLE ? chinapayUpacpFacade.getPayWorker()
					.parseResultFromBankCallback(request) : chinapayUpmpFacade
					.getPayWorker().parseResultFromBankCallback(cbParamMap);
		} else if (PGCallbackBizType.CALLBACK_REFUND == bizType) {
			resultDTO = UPACP_ENABLE ? chinapayUpacpFacade.getRefundWorker()
					.parseResultFromBankCallback(request) : chinapayUpmpFacade
					.getRefundWorker().parseResultFromBankCallback(cbParamMap);
		}

		return resultDTO;
	}

	@Override
	public ChinapayOrderResultDTO getOrderStateFromBank(
			PGPaymentOrderState paymentOrderState) throws Exception {

		// 交易类型
		String bankTransType = BizValueMapSwapper
				.toUpmpBankTransType(paymentOrderState.getTransType());

		// 支付单号
		String bankOrderNo = paymentOrderState.getBankOrderNo();

		// 交易日期
		Date transDate = paymentOrderState.getTransDate();

		if (UPACP_ENABLE) {
			return chinapayUpacpFacade.getOrderStateWorker().doQueryOrderState(
					bankTransType, bankOrderNo, transDate);
		}

		return chinapayUpmpFacade.getOrderStateWorker().doQueryOrderState(
				bankTransType, bankOrderNo, transDate);
	}

	/**
	 * 组装notifyUrl
	 * 
	 * @param payChannel
	 * @param merType
	 * @param bizType
	 * @return
	 */
	private String getNotifyUrl(Integer payChannel, Integer merType,
			Integer bizType) {
		return CallbackUrlHelper
				.assembleCallbackNotifyUrl(new CallbackRouterInfo(payChannel,
						merType, bizType));
	}

	/**
	 * 组装jumpUrl
	 * 
	 * @param payChannel
	 * @param merType
	 * @param bizType
	 * @return
	 */
	private String getJumpUrl(Integer payChannel, Integer merType,
			Integer bizType) {
		return CallbackUrlHelper
				.assembleCallbackJumpUrl(new CallbackRouterInfo(payChannel,
						merType, bizType));
	}
}
