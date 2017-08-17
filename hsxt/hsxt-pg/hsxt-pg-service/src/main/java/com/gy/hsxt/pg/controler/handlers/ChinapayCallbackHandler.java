package com.gy.hsxt.pg.controler.handlers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.ChinapayFacade;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.ChinapayUpopFacade;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.common.ChinapayUpopConst.UpopSubTransType;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.params.UpopBindingNoResult;
import com.gy.hsxt.pg.bankadapter.common.IPaymentResultAware;
import com.gy.hsxt.pg.bankadapter.common.beans.BankPaymentOrderResultDTO;
import com.gy.hsxt.pg.bankadapter.common.utils.DateUtils;
import com.gy.hsxt.pg.bankadapter.common.utils.MoneyAmountHelper;
import com.gy.hsxt.pg.bankadapter.common.utils.StringHelper;
import com.gy.hsxt.pg.bean.PGPaymentOrderState;
import com.gy.hsxt.pg.bean.PGQuickPayBindingNo;
import com.gy.hsxt.pg.common.bean.CallbackRouterInfo;
import com.gy.hsxt.pg.common.constant.Constant.PGAmountScale;
import com.gy.hsxt.pg.common.constant.Constant.PGCallbackBizType;
import com.gy.hsxt.pg.common.utils.ModelAndViewHelper;
import com.gy.hsxt.pg.common.utils.RequestParserUtils;
import com.gy.hsxt.pg.common.utils.StringUtils;
import com.gy.hsxt.pg.constant.PGConstant.PGCurrencyCode;
import com.gy.hsxt.pg.constant.PGConstant.PGPayChannel;
import com.gy.hsxt.pg.constant.PGConstant.PgCallbackJumpRespKey;
import com.gy.hsxt.pg.mapper.TPgBankPaymentOrderMapper;
import com.gy.hsxt.pg.mapper.vo.TPgBankPaymentOrder;
import com.gy.hsxt.pg.service.IPaymentNotifyService;
import com.gy.hsxt.pg.service.IChinapayCardBindingService;
import com.gy.hsxt.pg.service.IChinapayMobilePayAdapter;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.controler.chinapay
 * 
 *  File Name       : ChinapayCallbackHandler.java
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
@Component("chinapayCallbackHandler")
public class ChinapayCallbackHandler {
	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private IPaymentNotifyService notifyService;

	@Autowired
	private ChinapayUpopFacade chinapayUpopFacade;

	@Autowired
	private ChinapayFacade chinapayB2cFacade;

	@Autowired
	private IChinapayMobilePayAdapter mobilePayAdapter;

	@Autowired
	private TPgBankPaymentOrderMapper payOrderMapper;

	@Resource(name = "queryBindingNoExecutor")
	private ThreadPoolTaskExecutor queryBindingNoExecutor;

	@Autowired
	private IChinapayCardBindingService cardBindingService;

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
		// 取得支付渠道
		int payChannel = routerInfo.getPayChannel();

		// 业务类型
		int bizType = routerInfo.getBizType();

		// 根据不同渠道，解析银行返回结果
		Map<String, String> cbParamMap = RequestParserUtils
				.parseRequestParams(request);

		logger.info("银联jump回调参数：" + StringUtils.change2Json(cbParamMap));

		IPaymentResultAware resultAware = null;

		switch (payChannel) {
		// 网银支付
		case PGPayChannel.B2C: {
			resultAware = parseB2cResultFromCallback(cbParamMap, routerInfo);
			break;
		}
		// 快捷支付
		case PGPayChannel.UPOP: {
			resultAware = parseUpopResultFromCallback(cbParamMap, routerInfo);
			break;
		}
		// 手机支付(包括全渠道)
		case PGPayChannel.UPMP: {
			resultAware = mobilePayAdapter.parseResultFromCallback(request,
					cbParamMap, bizType);
			break;
		}
		default: {
		}
		}

		if (resultAware instanceof BankPaymentOrderResultDTO) {
			BankPaymentOrderResultDTO resultDTO = (BankPaymentOrderResultDTO) resultAware;

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
			ModelAndView modelAndView = ModelAndViewHelper
					.transRequest2NextJumpUrl(request, response, paramsMap,
							jumpUrl, "...");

			return modelAndView;
		}

		return null;
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

		// 业务类型
		int bizType = routerInfo.getBizType();

		IPaymentResultAware resultAware = null;

		Map<String, String> cbParamMap = RequestParserUtils
				.parseRequestParams(request);

		logger.info("银联notify回调参数：" + StringUtils.change2Json(cbParamMap));

		switch (payChannel) {
		// 网银支付
		case PGPayChannel.B2C: {
			resultAware = parseB2cResultFromCallback(cbParamMap, routerInfo);
			break;
		}
		// 快捷支付
		case PGPayChannel.UPOP: {
			resultAware = parseUpopResultFromCallback(cbParamMap, routerInfo);
			break;
		}
		// 手机支付(包括全渠道)
		case PGPayChannel.UPMP: {
			resultAware = mobilePayAdapter.parseResultFromCallback(request,
					cbParamMap, bizType);
			break;
		}
		default: {
		}
		}

		// 支付或退款回调
		if (resultAware instanceof BankPaymentOrderResultDTO) {
			BankPaymentOrderResultDTO resultDTO = (BankPaymentOrderResultDTO) resultAware;

			// 将银行的支付结果BankPaymentOrderResultDTO解析为PGPaymentOrderState
			PGPaymentOrderState paymentOrderState = this
					.parse2PaymentOrderState(resultDTO);
			paymentOrderState.setPayChannel(payChannel);

			// 根据支付结果更新数据库并通知重试模块通知支付系统
			notifyService.notifyOrderState(paymentOrderState);

			// 如果为首次快捷支付，获取并通知签约号
			if (isUpopFirstPay(routerInfo)) {
				this.fetchAndNotifyBindingNo(resultDTO.getPriv1(),
						resultDTO.getOrderNo(), routerInfo.getMerType());
			}
		}
		// 快捷支付银行卡签约回调(独立签约)
		else if (resultAware instanceof UpopBindingNoResult) {
			this.notifyUpopBindingNo(resultAware, routerInfo);
		}
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
		// paymentOrderState.setOrigBankOrderNo(resultDTO.get);
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

	/**
	 * 解析B2C回调
	 * 
	 * @param cbParamMap
	 * @param routerInfo
	 * @return
	 * @throws Exception
	 */
	private IPaymentResultAware parseB2cResultFromCallback(
			Map<String, String> cbParamMap, CallbackRouterInfo routerInfo)
			throws Exception {

		IPaymentResultAware resultAware = null;

		Integer bizType = routerInfo.getBizType();

		// 支付交易
		if (PGCallbackBizType.CALLBACK_PAY == bizType) {
			resultAware = chinapayB2cFacade.getPayWorker()
					.parseResultFromBankCallback(cbParamMap);
		}
		// 退款交易
		else if (PGCallbackBizType.CALLBACK_REFUND == bizType) {
			resultAware = chinapayB2cFacade.getRefundWorker()
					.parseResultFromBankCallback(cbParamMap);
		}

		return resultAware;
	}

	/**
	 * 解析UPOP快捷回调
	 * 
	 * @param cbParamMap
	 * @param routerInfo
	 * @return
	 * @throws Exception
	 */
	private IPaymentResultAware parseUpopResultFromCallback(
			Map<String, String> cbParamMap, CallbackRouterInfo routerInfo)
			throws Exception {
		IPaymentResultAware resultAware = null;
		Integer bizType = routerInfo.getBizType();

		// 支付交易
		if (PGCallbackBizType.CALLBACK_PAY == bizType) {
			// 非首次
			if (!isUpopFirstPay(routerInfo)) {
				resultAware = chinapayUpopFacade.getQuickPaySecond()
						.parseResultFromBankCallback(cbParamMap);
			}
			// 首次
			else {
				resultAware = chinapayUpopFacade.getQuickPayFirst()
						.parseResultFromBankCallback(cbParamMap);
			}
		}
		// 退款交易
		else if (PGCallbackBizType.CALLBACK_REFUND == bizType) {
			resultAware = chinapayUpopFacade.getRefund()
					.parseResultFromBankCallback(cbParamMap);
		}
		// 独立签约交易
		else if (PGCallbackBizType.CALLBACK_BINDING == bizType) {
			resultAware = chinapayUpopFacade.getCardBinding()
					.parseResultFromBankCallback(cbParamMap);
		}

		return resultAware;
	}

	/**
	 * 通知快捷支付签约号(独立签约模式)
	 * 
	 * @param resultAware
	 * @param routerInfo
	 */
	private void notifyUpopBindingNo(IPaymentResultAware resultAware,
			CallbackRouterInfo routerInfo) {

		UpopBindingNoResult bindingNoResult = (UpopBindingNoResult) resultAware;

		// 临时支付过期时间
		Date expiryDate = formatUpopExpireDate(bindingNoResult.getExpiry());

		String transLimit = MoneyAmountHelper.fromFenToYuan(
				bindingNoResult.getTransLimit(), PGAmountScale.SIX);

		String sumLimit = MoneyAmountHelper.fromFenToYuan(
				bindingNoResult.getSumLimit(), PGAmountScale.SIX);

		PGQuickPayBindingNo quickPayBindingNo = new PGQuickPayBindingNo(
				bindingNoResult.getCardNo(), bindingNoResult.getBindingNo(),
				expiryDate, transLimit, sumLimit);
		quickPayBindingNo.setBankOrderNo(routerInfo.getCustomizeType());

		try {
			notifyService.notifyBindingNo(quickPayBindingNo,
					routerInfo.getMerType());
		} catch (Exception e) {
			logger.info("", e);
		}
	}

	/**
	 * 判断是否为快捷支付首次
	 * 
	 * @param routerInfo
	 * @return
	 */
	private boolean isUpopFirstPay(CallbackRouterInfo routerInfo) {
		// 取得支付渠道
		int payChannel = routerInfo.getPayChannel();

		// 业务类型
		int bizType = routerInfo.getBizType();

		// 子类型
		String upopSubTransType = routerInfo.getCustomizeType();

		boolean isUpopFirstPay = (PGPayChannel.UPOP == payChannel);
		isUpopFirstPay &= (PGCallbackBizType.CALLBACK_PAY == bizType);
		isUpopFirstPay &= UpopSubTransType.PAY_FIRST.equals(upopSubTransType);

		// 判断首次快捷支付
		return isUpopFirstPay;
	}

	/**
	 * 格式化过期时间
	 * 
	 * @param expireDate
	 * @return
	 */
	private Date formatUpopExpireDate(String expireDate) {

		if (StringUtils.isEmpty(expireDate)) {
			return null;
		}

		return DateUtils.getYYYYMMddHHmmssDate(expireDate + "235959");
	}

	/**
	 * 获取并通知签约号到用户中心
	 * 
	 * @param priv1
	 * @param bankOrderNo
	 * @param merType
	 * @throws Exception
	 */
	private void fetchAndNotifyBindingNo(final String priv1,
			final String bankOrderNo, final int merType) throws Exception {

		// 私有域 (银行卡号,借贷记标识,银行id)
		final String[] privs = StringUtils.avoidNull(priv1).split("\\,");

		if ((null == privs) || (3 != privs.length)) {
			return;
		}

		// 线程池中运行
		queryBindingNoExecutor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					String bankCardNo = privs[0]; // 卡号
					String bankCardType$ = privs[1]; // 卡类型
					String bankId = privs[2]; // 银行标识

					// 卡类型
					Integer bankCardType = StringHelper.str2Int(bankCardType$);

					// 获取并通知签约号
					cardBindingService.fetchBindingNoFromCP(bankCardNo, bankId,
							bankCardType, bankOrderNo, merType);
				} catch (Exception e) {
					logger.error("获取并通知GP签约号时, 发生异常！！", e);
				}
			}
		});
	}
}