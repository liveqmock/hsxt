package com.gy.hsxt.pg.controler.handlers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.pg.bankadapter.common.beans.BankPaymentOrderResultDTO;
import com.gy.hsxt.pg.bankadapter.common.utils.MoneyAmountHelper;
import com.gy.hsxt.pg.bankadapter.pingan.b2c.PinganB2cFacade;
import com.gy.hsxt.pg.bean.PGPaymentOrderState;
import com.gy.hsxt.pg.common.bean.CallbackRouterInfo;
import com.gy.hsxt.pg.common.constant.Constant.PGAmountScale;
import com.gy.hsxt.pg.common.utils.ModelAndViewHelper;
import com.gy.hsxt.pg.common.utils.StringUtils;
import com.gy.hsxt.pg.constant.PGConstant.PGCurrencyCode;
import com.gy.hsxt.pg.constant.PGConstant.PgCallbackJumpRespKey;
import com.gy.hsxt.pg.mapper.TPgBankPaymentOrderMapper;
import com.gy.hsxt.pg.mapper.vo.TPgBankPaymentOrder;
import com.gy.hsxt.pg.service.IPaymentNotifyService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.controler.pingan
 * 
 *  File Name       : PinganCallbackHandler.java
 * 
 *  Creation Date   : 2015-09-22
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 处理来自银行或第三方支付的http回调请求
 *  
 *                    访问路径规则：  http://unionpay.hsxt.net:18084/hsxt-pg/p/c/j/银行代号/100/100/1/
 *                              http://unionpay.hsxt.net:18084/hsxt-pg/p/c/n/银行代号/100/100/1/
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Component("pinganCallbackHandler")
public class PinganCallbackHandler {
	protected Logger logger = Logger.getLogger(getClass());

	@Autowired
	private IPaymentNotifyService notifyService;

	@Autowired
	private TPgBankPaymentOrderMapper payOrderMapper;

	@Autowired
	private PinganB2cFacade pinganB2cFacade;

	/**
	 * 处理回调页面跳转请求
	 * 
	 * @param request
	 * @param response
	 * @param routerInfo
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ModelAndView handleJumpRequest(HttpServletRequest request,
			HttpServletResponse response, CallbackRouterInfo routerInfo)
			throws Exception {

		BankPaymentOrderResultDTO resultDTO = pinganB2cFacade
				.getB2cPaymentWorker().parseResultFromBankCallback(request,
						true);

		TPgBankPaymentOrder chinapayOrder = payOrderMapper
				.selectByBankOrderNo(resultDTO.getOrderNo());

		// 查询数据库得到jumpUrl
		String jumpUrl = chinapayOrder.getJumpUrl();

		String transAmount = MoneyAmountHelper.formatAmountScale(
				chinapayOrder.getTransAmount(), PGAmountScale.SIX);

		// 将银行的支付结果
		Map<String, String> paramsMap = JSON.parseObject(
				chinapayOrder.getPrivObligate2(), HashMap.class);

		paramsMap.put(PgCallbackJumpRespKey.TRANS_AMOUNT, transAmount);
		paramsMap.put(PgCallbackJumpRespKey.PAY_CHANNEL,
				String.valueOf(chinapayOrder.getPayChannel()));
		paramsMap.put(PgCallbackJumpRespKey.TRANS_STATUS, resultDTO
				.getOrderStatus().getValue());
		paramsMap.put(PgCallbackJumpRespKey.FAIL_REASON,
				resultDTO.getErrorInfo());

		// 将银行的回调参数封装为内部跳转参数并进行内部URL跳转
		return ModelAndViewHelper.transRequest2NextJumpUrl(request, response,
				paramsMap, jumpUrl, "...");
	}

	/**
	 * 处理后台通知请求
	 * 
	 * @param request
	 * @param response
	 * @param routerInfo
	 * @throws Exception
	 */
	public void handleNotifyRequest(HttpServletRequest request,
			HttpServletResponse response, CallbackRouterInfo routerInfo)
			throws Exception {

		// 取得支付渠道
		int payChannel = routerInfo.getPayChannel();

		BankPaymentOrderResultDTO resultDTO = pinganB2cFacade
				.getB2cPaymentWorker().parseResultFromBankCallback(request,
						false);

		// 将银行的支付结果BankPaymentOrderResultDTO解析为PGPaymentOrderState
		PGPaymentOrderState paymentOrderState = parse2PaymentOrderState(resultDTO);
		paymentOrderState.setPayChannel(payChannel);

		// 根据支付结果更新数据库并通知重试模块通知支付系统
		notifyService.notifyOrderState(paymentOrderState);

		// 反馈给平安银行!!!!!!!!!!
		response.getWriter().write("ok");
	}

	/**
	 * 将银行的支付结果BankPaymentOrderResultDTO解析为PGPaymentOrderState
	 * 
	 * @param resultDTO
	 * @return
	 */
	private PGPaymentOrderState parse2PaymentOrderState(
			BankPaymentOrderResultDTO resultDTO) {

		String failedReason = StringUtils.cut2SpecialLength(
				resultDTO.getErrorInfo(), 240);

		String transAmount = MoneyAmountHelper.fromFenToYuan(
				resultDTO.getPayAmount(), PGAmountScale.SIX);

		PGPaymentOrderState paymentOrderState = new PGPaymentOrderState();
		paymentOrderState.setCurrencyCode(PGCurrencyCode.CNY);
		paymentOrderState.setBankOrderNo(resultDTO.getOrderNo());
		paymentOrderState.setFailReason(failedReason);
		paymentOrderState.setTransAmount(transAmount);
		paymentOrderState.setTransDate(resultDTO.getTranTime());
		paymentOrderState.setTransStatus(resultDTO.getOrderStatus()
				.getIntValue());
		paymentOrderState.setTransType(Integer.parseInt(resultDTO
				.getTransType()));
		paymentOrderState.setBankOrderSeqId(resultDTO.getBankSeqNo());
		paymentOrderState.setBankOrderStatus(resultDTO.getBankRespCode());

		return paymentOrderState;
	}

}