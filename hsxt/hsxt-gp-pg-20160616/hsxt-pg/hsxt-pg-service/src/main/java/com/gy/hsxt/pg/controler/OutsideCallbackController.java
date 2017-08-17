package com.gy.hsxt.pg.controler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.pg.bankadapter.chinapay.ChinapayOrderResultDTO;
import com.gy.hsxt.pg.bankadapter.chinapay.IChinapayResultAware;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.ChinapayFacade;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.ChinapayUpopFacade;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.common.ChinapayUpopConst.UpopSubTransType;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.params.UpopBindingNoResult;
import com.gy.hsxt.pg.bankadapter.common.exception.BankAdapterException;
import com.gy.hsxt.pg.bankadapter.common.utils.DateUtils;
import com.gy.hsxt.pg.bankadapter.common.utils.MoneyAmountHelper;
import com.gy.hsxt.pg.bean.PGPaymentOrderState;
import com.gy.hsxt.pg.bean.PGQuickPayBindingNo;
import com.gy.hsxt.pg.common.bean.CallbackRouterInfo;
import com.gy.hsxt.pg.common.constant.Constant.PGAmountScale;
import com.gy.hsxt.pg.common.constant.Constant.PGCallbackBizType;
import com.gy.hsxt.pg.common.constant.Constant.PGUrlKeywords;
import com.gy.hsxt.pg.common.constant.Constant.SessionKey;
import com.gy.hsxt.pg.common.utils.CallbackUrlHelper;
import com.gy.hsxt.pg.common.utils.ModelAndViewHelper;
import com.gy.hsxt.pg.common.utils.RequestParserUtils;
import com.gy.hsxt.pg.common.utils.StringUtils;
import com.gy.hsxt.pg.constant.PGConstant.PGCurrencyCode;
import com.gy.hsxt.pg.constant.PGConstant.PGPayChannel;
import com.gy.hsxt.pg.constant.PGConstant.PgCallbackJumpRespKey;
import com.gy.hsxt.pg.mapper.TPgChinapayOrderMapper;
import com.gy.hsxt.pg.mapper.vo.TPgChinapayOrder;
import com.gy.hsxt.pg.service.IChinapayCallbackService;
import com.gy.hsxt.pg.service.IChinapayMobilePayAdapter;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.controler
 * 
 *  File Name       : OutsideCallbackController.java
 * 
 *  Creation Date   : 2015-09-22
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 处理来自银行或第三方支付的http回调请求
 *  
 *                    访问路径规则：http://127.0.0.1:8080/站点名称/paygateway/cb/jmp/支付渠道/商户类型/业务类型/
 *                             http://127.0.0.1:8080/站点名称/paygateway/cb/ntf/支付渠道/商户类型/业务类型/
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Controller
@RequestMapping("/" + PGUrlKeywords.PAY_GATEWAY + "/" + PGUrlKeywords.CALLBACK)
public class OutsideCallbackController {
	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private IChinapayCallbackService callbackService;

	@Autowired
	private ChinapayUpopFacade chinapayUpopFacade;

	@Autowired
	private ChinapayFacade chinapayB2cFacade;

	@Autowired
	private IChinapayMobilePayAdapter mobilePayAdapter;
	
	@Autowired
	private TPgChinapayOrderMapper payOrderMapper;

	@RequestMapping(value = "/" + PGUrlKeywords.JUMP + "/**")
	public ModelAndView handleJumpRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.info("银联jump url:" + request.getRequestURL().toString());

		try {
			return this.doHandleJumpRequest(request, response);
		} catch (Exception e) {
			logger.error("", e);

			if (e instanceof BankAdapterException) {
				request.getSession().setAttribute(SessionKey.ILLEGAL_REQ_ERROR,
						"解析银联响应报文发生错误!");
			} else {
				request.getSession().setAttribute(SessionKey.ILLEGAL_REQ_ERROR,
						"非法访问!");
			}

			return new ModelAndView("/error");
		}
	}

	@RequestMapping(value = "/" + PGUrlKeywords.NOTIFY + "/**")
	public void handleNotifyRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("银联notify url:" + request.getRequestURL().toString());

		try {
			this.doHandleNotifyRequest(request, response);
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	/**
	 * 处理回调页面跳转请求
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private ModelAndView doHandleJumpRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 解析回调地址路由信息
		CallbackRouterInfo routerInfo = CallbackUrlHelper
				.getCallbackRouterInfo(request);

		if (null == routerInfo) {
			throw new IllegalArgumentException("错误：银行回调地址非法！");
		}

		// 取得支付渠道
		int payChannel = routerInfo.getPayChannel();
		
		// 业务类型
		int bizType = routerInfo.getBizType();
		
		// 根据不同渠道，解析银行返回结果
		Map<String, String> cbParamMap = RequestParserUtils
				.parseRequestParams(request);

		logger.info("银联jump回调参数：" + JSON.toJSONString(cbParamMap));

		IChinapayResultAware resultAware = null;

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
		
		if (resultAware instanceof ChinapayOrderResultDTO) {
			
			ChinapayOrderResultDTO resultDTO = (ChinapayOrderResultDTO) resultAware;

			TPgChinapayOrder chinapayOrder = payOrderMapper
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
	 * @throws Exception
	 */
	private void doHandleNotifyRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 解析回调地址路由信息
		CallbackRouterInfo routerInfo = CallbackUrlHelper
				.getCallbackRouterInfo(request);

		if (null == routerInfo) {
			throw new IllegalArgumentException("错误：银行回调地址非法！");
		}

		// 取得支付渠道
		int payChannel = routerInfo.getPayChannel();

		// 业务类型
		int bizType = routerInfo.getBizType();
		
		// 商户类型
		int merType = routerInfo.getMerType();
		
		IChinapayResultAware resultAware = null;

		Map<String, String> cbParamMap = RequestParserUtils
				.parseRequestParams(request);

		logger.info("银联notify回调参数：" + JSON.toJSONString(cbParamMap));

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
		if (resultAware instanceof ChinapayOrderResultDTO) {
			ChinapayOrderResultDTO resultDTO = (ChinapayOrderResultDTO) resultAware;

			// 将银行的支付结果ChinapayOrderResultDTO解析为PGPaymentOrderState
			PGPaymentOrderState paymentOrderState = this
					.parse2PaymentOrderState(resultDTO);
			paymentOrderState.setPayChannel(payChannel);

			// 根据支付结果更新数据库并通知重试模块通知支付系统
			callbackService.notifyOrderState(paymentOrderState);

			// 如果为首次快捷支付，获取并通知签约号
			if (isUpopFirstPay(routerInfo)) {
				callbackService.fetchAndNotifyBindingNo(resultDTO.getPriv1(),
						resultDTO.getOrderNo(), merType);
			}
		}
		// 快捷支付银行卡签约回调(独立签约)
		else if (resultAware instanceof UpopBindingNoResult) {
			this.notifyUpopBindingNo(resultAware, routerInfo);
		}
	}

	/**
	 * 将银行的支付结果ChinapayOrderResultDTO解析为PGPaymentOrderState
	 * 
	 * @param resultDTO
	 * @return
	 */
	private PGPaymentOrderState parse2PaymentOrderState(
			ChinapayOrderResultDTO resultDTO) {

		String failedReason = StringUtils.cut2SpecialLength(
				resultDTO.getErrorInfo(), 240);
		
		String transAmount = MoneyAmountHelper.fromFenToYuan(resultDTO
				.getPayAmount(), PGAmountScale.SIX);

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
	private IChinapayResultAware parseB2cResultFromCallback(
			Map<String, String> cbParamMap, CallbackRouterInfo routerInfo)
			throws Exception {

		IChinapayResultAware resultAware = null;

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
	private IChinapayResultAware parseUpopResultFromCallback(
			Map<String, String> cbParamMap, CallbackRouterInfo routerInfo)
			throws Exception {
		IChinapayResultAware resultAware = null;
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
	private void notifyUpopBindingNo(IChinapayResultAware resultAware,
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
			callbackService.notifyBindingNo(quickPayBindingNo,
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

}