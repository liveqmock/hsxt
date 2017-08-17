package com.gy.hsxt.pg.controler.handlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.ChinapayUpopFacade;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.common.ChinapayUpopConst.UpopRespCode;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.common.UpopBankErrorException;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.params.UpopPaySecondParam;
import com.gy.hsxt.pg.bankadapter.common.beans.BankPaymentOrderResultDTO;
import com.gy.hsxt.pg.bankadapter.common.constants.BanksConstants.ErrorCode;
import com.gy.hsxt.pg.bankadapter.common.constants.PaymentOrderStatus;
import com.gy.hsxt.pg.bankadapter.common.exception.BankAdapterException;
import com.gy.hsxt.pg.bankadapter.common.utils.DateUtils;
import com.gy.hsxt.pg.bankadapter.common.utils.MoneyAmountHelper;
import com.gy.hsxt.pg.bean.PGCallbackJumpRespData;
import com.gy.hsxt.pg.common.constant.Constant;
import com.gy.hsxt.pg.common.utils.ModelAndViewHelper;
import com.gy.hsxt.pg.common.utils.StringUtils;
import com.gy.hsxt.pg.constant.PGConstant.PGErrorCode;
import com.gy.hsxt.pg.constant.PGConstant.PGPaymentStateCode;
import com.gy.hsxt.pg.constant.PGConstant.SecondQuickPayReqKey;
import com.gy.hsxt.pg.mapper.TPgBankPaymentOrderMapper;
import com.gy.hsxt.pg.mapper.vo.TPgBankPaymentOrder;
import com.gy.hsxt.pg.service.impl.check.BasicPropertyCheck;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.controler.chinapay
 * 
 *  File Name       : ChinapayInsideHandler.java
 * 
 *  Creation Date   : 2015-10-14
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 处理来自地区平台内部的http请求(中国银联)
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Component("chinapayInsideHandler")
public class ChinapayInsideHandler {

	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private ChinapayUpopFacade chinapayUpopFacade;

	@Autowired
	private TPgBankPaymentOrderMapper payOrderMapper;

	/**
	 * 接收非来自内部系统的[非首次]快捷支付请求 <br>
	 * 访问地址： http://127.0.0.1:8080/站点名称/p/inner/c/upop/paysecond <br>
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView handleInnerRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		PGCallbackJumpRespData respData = null;

		try {
			respData = this.doHandleRequest(request);
		} catch (Exception ex) {
			respData = this.assembleJumpRespData4Exception(ex);

			logger.info("调用银联接口进行非首次支付出现异常, 详情：" + respData.getFailReason(), ex);
		}

		// 页面跳转的url地址
		String jumpUrl = null;

		// 组装页面跳转传递的参数
		Map<String, String> paramsMap = null;

		try {
			// 页面跳转的url地址
			jumpUrl = respData.getJumpUrl();

			// 组装页面跳转传递的参数
			paramsMap = this.changeRespData2Map(respData);

			if (StringUtils.isEmpty(jumpUrl)) {
				// 回写响应信息到客户端
				doPrintWrite(JSON.toJSONString(paramsMap), response);
			}
		} catch (Exception e) {
			logger.info(e);
		}

		// 将银行的回调参数封装为内部跳转参数并进行内部URL跳转
		return ModelAndViewHelper.transRequest2NextJumpUrl(request, response,
				paramsMap, jumpUrl, "");
	}

	/**
	 * 封装请求数据
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private PGCallbackJumpRespData doHandleRequest(HttpServletRequest request)
			throws Exception {

		// 交易请求参数
		this.checkRequestParams(request);

		String bankOrderNo = request
				.getParameter(SecondQuickPayReqKey.BANK_ORDERNO);

		// 从数据库中查询出对应支付单信息
		TPgBankPaymentOrder chinapayOrder = payOrderMapper
				.selectByBankOrderNo(bankOrderNo);

		if (null == chinapayOrder) {
			throw new HsException(PGErrorCode.DATA_NOT_EXIST, "该支付单数据不存在!!");
		}

		// 从未提交到银行或短信验证码错误
		if (!isBankOrderSubmited(chinapayOrder)
				|| isSmsCodeError(chinapayOrder)) {
			// 根据request设置非首次支付参数
			UpopPaySecondParam params = getUpopPaySecondParam(request,
					chinapayOrder);

			BankPaymentOrderResultDTO resultDTO = chinapayUpopFacade
					.getQuickPaySecond().doSecondPayRequest(params);

			return assembleJumpRespData(chinapayOrder, resultDTO, true);
		}

		// 已经成功提交到银行了
		return assembleJumpRespData(chinapayOrder, null, false);
	}

	/**
	 * 组装页面跳转传递的参数
	 * 
	 * @param chinapayOrder
	 * @param resultDTO
	 * @param isByRespFromBank
	 * @return
	 */
	private PGCallbackJumpRespData assembleJumpRespData(
			TPgBankPaymentOrder chinapayOrder,
			BankPaymentOrderResultDTO resultDTO, boolean isByRespFromBank) {

		String transStatus;
		String failReason;
		String bankRespCode;

		// 抽取银行响应的数据
		if (isByRespFromBank) {
			transStatus = resultDTO.getOrderStatus().getValue();
			bankRespCode = resultDTO.getBankRespCode();
			failReason = resultDTO.getErrorInfo();
		}
		// 使用本地数据
		else {
			transStatus = StringUtils.int2Str(chinapayOrder.getTransStatus());
			bankRespCode = chinapayOrder.getBankOrderStatus();
			failReason = chinapayOrder.getFailReason();
		}

		if (PaymentOrderStatus.PAY_SUCCESS.valueEquals(transStatus)
				|| PaymentOrderStatus.PAY_PROCESSING.valueEquals(transStatus)) {
			failReason = "";
		} else if (StringUtils.isEmpty(failReason)) {
			failReason = "处理失败, 原因未知!!";
		}

		PGCallbackJumpRespData respData = JSON.parseObject(
				chinapayOrder.getPrivObligate2(), PGCallbackJumpRespData.class);

		if (null != respData) {
			respData.setCurrencyCode(chinapayOrder.getCurrencyCode());// 币种
			respData.setTransAmount(chinapayOrder.getTransAmount().toString());// 金额
			respData.setPayChannel(chinapayOrder.getPayChannel());// 渠道
			respData.setTransStatus(transStatus);// 交易状态-
			respData.setFailReason(failReason);// 失败原因-
			respData.setBankRespCode(bankRespCode);// 银行响应码
			respData.setJumpUrl(chinapayOrder.getJumpUrl()); // 跳转地址
		}

		return respData;
	}

	/**
	 * 封装失败场景的响应信息
	 * 
	 * @param ex
	 * @return
	 */
	private PGCallbackJumpRespData assembleJumpRespData4Exception(Exception ex) {

		String errorMsg = "系统繁忙!";
		String bankRespCode = UpopRespCode.OPT_FAILED.getCode();

		// UpopBankErrorException异常
		if (ex instanceof UpopBankErrorException) {
			UpopBankErrorException e = (UpopBankErrorException) ex;

			errorMsg = e.getErrorMsg();
			bankRespCode = e.getBankErrorCode();
		}
		// HsException和BankAdapterException异常
		else {
			if (ex instanceof HsException) {
				HsException e = (HsException) ex;

				if (PGErrorCode.DATA_NOT_EXIST == e.getErrorCode()) {
					errorMsg = "该支付单数据不存在!";
				} else if (PGErrorCode.INVALID_PARAM == e.getErrorCode()) {
					errorMsg = "请求参数无效!";
				} else if ((PGErrorCode.DATA_EXIST == e.getErrorCode())
						|| (PGErrorCode.REPEAT_SUBMIT == e.getErrorCode())) {
					errorMsg = "该订单已经支付, 请不要重复提交!";
				}
			} else if (ex instanceof BankAdapterException) {
				BankAdapterException e = (BankAdapterException) ex;

				if (ErrorCode.DATA_NOT_EXIST == e.getErrorCode()) {
					errorMsg = "该支付单数据不存在!";
				} else if (ErrorCode.INVALID_PARAM == e.getErrorCode()) {
					errorMsg = "请求参数无效!";
				} else if ((ErrorCode.DATA_EXIST == e.getErrorCode())
						|| (ErrorCode.DUPLICATE_SUBMIT == e.getErrorCode())) {
					errorMsg = "该订单已经支付, 请不要重复提交!";
				}
			}

			errorMsg = StringUtils.joinString("[", bankRespCode, "]",
					"快捷支付失败, 原因： ", errorMsg);
		}

		PGCallbackJumpRespData respData = new PGCallbackJumpRespData();
		respData.setTransStatus(PGPaymentStateCode.PAY_FAILED);
		respData.setBankRespCode(bankRespCode);
		respData.setFailReason(errorMsg);

		return respData;
	}

	// http://61.144.241.233:8888/hsxt-pg/paygateway/inner/upop/paysecond?merType=&bankOrderNo=&bindingNo=&smsCode=
	/**
	 * 根据request设置非首次支付参数
	 * 
	 * @param request
	 * @param chinapayOrder
	 * @return
	 */
	private UpopPaySecondParam getUpopPaySecondParam(
			HttpServletRequest request, TPgBankPaymentOrder chinapayOrder) {

		String bankOrderNo = request
				.getParameter(SecondQuickPayReqKey.BANK_ORDERNO);
		String bindingNo = request
				.getParameter(SecondQuickPayReqKey.BINDING_NO);
		String smsCode = request.getParameter(SecondQuickPayReqKey.SMS_CODE);
		String orderDate = DateUtils.dateToString(chinapayOrder.getTransDate(),
				"yyyyMMdd");
		String orderAmounts = chinapayOrder.getTransAmount()
				.setScale(2, BigDecimal.ROUND_HALF_UP).toString();

		UpopPaySecondParam upopPaySecondParam = new UpopPaySecondParam();
		upopPaySecondParam.setOrderNo(bankOrderNo);
		upopPaySecondParam.setBindingNo(bindingNo);
		upopPaySecondParam.setSmsCode(smsCode);
		upopPaySecondParam.setOrderDate(orderDate);
		upopPaySecondParam.setPayAmount(MoneyAmountHelper
				.fromYuanToFen(orderAmounts));

		return upopPaySecondParam;
	}

	/**
	 * 转换为map结构
	 * 
	 * @param respData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, String> changeRespData2Map(
			PGCallbackJumpRespData respData) {

		if (null == respData) {
			return new HashMap<String, String>(1);
		}

		// 不需要返回jumpUrl
		respData.setJumpUrl(null);

		// 组装页面跳转传递的参数
		return JSON.parseObject(JSON.toJSONString(respData), HashMap.class);
	}

	/**
	 * 校验请求参数
	 * 
	 * @param request
	 * @return
	 */
	private boolean checkRequestParams(HttpServletRequest request) {

		// 商户类型
		Integer merType = StringUtils.str2Int(
				request.getParameter(SecondQuickPayReqKey.MER_TYPE), null);

		String bankOrderNo = request
				.getParameter(SecondQuickPayReqKey.BANK_ORDERNO);
		String bindingNo = request
				.getParameter(SecondQuickPayReqKey.BINDING_NO);
		String smsCode = request.getParameter(SecondQuickPayReqKey.SMS_CODE);

		BasicPropertyCheck.checkMerType(merType);
		BasicPropertyCheck.checkBankOrderNo(bankOrderNo);
		BasicPropertyCheck.checkBindingNo(bindingNo);
		BasicPropertyCheck.checkSmsCode(smsCode);

		return true;
	}

	/**
	 * 校验该支付单是否已经提交到银联
	 * 
	 * @param chinapayOrder
	 * @return
	 */
	private boolean isBankOrderSubmited(TPgBankPaymentOrder chinapayOrder) {

		int transStatus = chinapayOrder.getTransStatus();

		// 其他的状态都算已经提交到银联
		if ((PGPaymentStateCode.INVALID != transStatus)
				&& (PGPaymentStateCode.READY != transStatus)) {
			return true;
		}

		return false;
	}

	/**
	 * 校验该支付单是否已经提交到银联
	 * 
	 * @param chinapayOrder
	 * @return
	 */
	private boolean isSmsCodeError(TPgBankPaymentOrder chinapayOrder) {
		String bankOrderStatus = chinapayOrder.getBankOrderStatus();

		return UpopRespCode.E_086.valueEquals(bankOrderStatus);
	}

	/**
	 * 回写响应数据
	 * 
	 * @param msg
	 * @param response
	 * @throws IOException
	 */
	private void doPrintWrite(String msg, HttpServletResponse response)
			throws IOException {
		response.setContentType("text/javascript;charset=UTF-8");
		response.setCharacterEncoding(Constant.ENCODING_UTF8);

		PrintWriter pw = response.getWriter();
		pw.write(msg);
		pw.flush();
		pw.close();
	}
}
