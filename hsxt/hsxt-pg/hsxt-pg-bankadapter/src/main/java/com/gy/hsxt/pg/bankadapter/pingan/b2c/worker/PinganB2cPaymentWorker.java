/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.pingan.b2c.worker;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ecc.emp.data.KeyedCollection;
import com.gy.hsxt.pg.bankadapter.common.beans.BankPaymentOrderResultDTO;
import com.gy.hsxt.pg.bankadapter.common.constants.BanksConstants;
import com.gy.hsxt.pg.bankadapter.common.constants.BanksConstants.PGAmountScale;
import com.gy.hsxt.pg.bankadapter.common.constants.PaymentOrderTransType;
import com.gy.hsxt.pg.bankadapter.common.exception.BankAdapterException;
import com.gy.hsxt.pg.bankadapter.common.utils.DateUtils;
import com.gy.hsxt.pg.bankadapter.common.utils.HttpRequestParamHelper;
import com.gy.hsxt.pg.bankadapter.common.utils.MoneyAmountHelper;
import com.gy.hsxt.pg.bankadapter.pingan.b2c.abstracts.AbstractPinganB2cWorker;
import com.gy.hsxt.pg.bankadapter.pingan.b2c.common.PinganB2cConsts.PaymentUIType;
import com.gy.hsxt.pg.bankadapter.pingan.b2c.params.PinganB2cPayParam;
import com.gy.hsxt.pg.bankadapter.pingan.b2c.params.PinganB2cPayRespParam;
import com.sdb.payclient.core.PayclientInterfaceUtil;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.pingan.b2c.worker
 * 
 *  File Name       : PinganB2cPaymentWorker.java
 * 
 *  Creation Date   : 2016年6月23日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 平安b2c网银支付工作者类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class PinganB2cPaymentWorker extends AbstractPinganB2cWorker {

	/**
	 * 组装平安支付url地址
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public String buildB2cPayUrl(PinganB2cPayParam params) throws Exception {

		// 请求地址
		String bankUrl = getUrlByUIType(params.getPaymentUIType());

		// 支付金额
		String transAmount = MoneyAmountHelper.fromFenToYuan(
				params.getPayAmount(), PGAmountScale.TWO);

		// 订单时间
		String orderDate = DateUtils.dateToString(params.getOrderDate(),
				"yyyyMMddHHmmss");

		// 创建签名控件对象
		PayclientInterfaceUtil util = new PayclientInterfaceUtil();

		KeyedCollection input = new KeyedCollection("input");
		KeyedCollection signDataput = new KeyedCollection("signDataput");

		input.put("masterId", mechantNo); // 商户号
		input.put("orderId", params.getOrderNo()); // 订单号
		input.put("currency", "RMB"); // 只支持人民币
		input.put("amount", transAmount); // 支付金额
		input.put("paydate", orderDate); // 订单日期
		input.put("remark", params.getRemark());
		input.put("objectName", params.getGoodsName());
		input.put("validtime", 60 * 60 * 1000); // 有效10分钟

		String orig = ""; // 原始数据
		String sign = ""; // 产生签名
		
		String notifyUrl = params.getNotifyURL();
		String jumpUrl = params.getJumpURL();

		String encoding = BanksConstants.CHARSET_GBK;

		try {
			// 发送前，得到签名数据和签名后数据，单独使用
			signDataput = util.getSignData(input);

			orig = (String) signDataput.getDataValue("orig");
			sign = (String) signDataput.getDataValue("sign");

			orig = java.net.URLEncoder.encode(
					util.Base64Encode(orig, encoding), encoding);
			sign = java.net.URLEncoder.encode(
					util.Base64Encode(sign, encoding), encoding);
		} catch (Exception ex) {
			logger.error("组装平安网银支付URL签名异常：", ex);

			throw new BankAdapterException("组装平安网银支付URL签名异常, 原因: 内部错误!");
		}

		// 组装参数
		Map<String, String> paramsMap = new HashMap<String, String>(4);
		paramsMap.put("orig", URLEncoder.encode(orig, encoding));
		paramsMap.put("sign", URLEncoder.encode(sign, encoding));
		paramsMap.put("returnurl", URLEncoder.encode(jumpUrl, encoding));
		paramsMap.put("NOTIFYURL", URLEncoder.encode(notifyUrl, encoding));

		return HttpRequestParamHelper.assembleHttpReqUrl(bankUrl, paramsMap);
	}

	/**
	 * 处理平安网银支付回调接口方法
	 * 
	 * @param req
	 * @param isNeedUrlDecode
	 * @return
	 * @throws Exception
	 */
	public BankPaymentOrderResultDTO parseResultFromBankCallback(
			HttpServletRequest req, boolean isNeedUrlDecode) throws Exception {

		PayclientInterfaceUtil util = new PayclientInterfaceUtil();

		// 验证签名
		this.verifySignatureOfCallBack(req, util, BanksConstants.CHARSET_GBK,
				isNeedUrlDecode);

		// 银行返回后台通知原始数据
		String orig = req.getParameter("orig");
		orig = isNeedUrlDecode ? URLDecoder.decode(orig,
				BanksConstants.CHARSET_GBK) : orig;
		orig = PayclientInterfaceUtil.Base64Decode(orig,
				BanksConstants.CHARSET_GBK);

		KeyedCollection output = util.parseOrigData(orig);

		// txnType: 01-支付,04-退款
		PinganB2cPayRespParam bankRespParam = new PinganB2cPayRespParam(output);

		bankRespParam.setOrderNoKey("orderId").setPayAmountKey("amount")
				.setCurrencyKey("currency").setTranTimeKey("paydate")
				.setTranFinishTimeKey("date").setTranStatusKey("status")
				.setTranChargeKey("charge");

		return convert2BankPaymentOrderResultDTO(bankRespParam,
				PaymentOrderTransType.TYPE_PAY.getValue());
	}

	/**
	 * 处理银联支付回调接口方法
	 * 
	 * @param req
	 * @param util
	 * @param encoding
	 * @param isNeedUrlDecode 
	 * @return
	 * @throws Exception
	 */
	private boolean verifySignatureOfCallBack(HttpServletRequest req,
			PayclientInterfaceUtil util, String encoding,
			boolean isNeedUrlDecode) throws Exception {

		// 银行返回后台通知原始数据
		String orig = req.getParameter("orig");
		orig = isNeedUrlDecode ? URLDecoder.decode(orig, encoding) : orig;
		orig = PayclientInterfaceUtil.Base64Decode(orig, encoding);

		// 银行返回后台通知签名数据
		String sign = req.getParameter("sign");
		sign = isNeedUrlDecode ? URLDecoder.decode(sign, encoding) : sign;
		sign = PayclientInterfaceUtil.Base64Decode(sign, encoding);

		// 验证签名
		if (!util.verifyData(sign, orig)) {
			logger.info("verifySignatureOfCallBack: 平安银行网银支付回调时, 验证签名失败!!  "
					+ "callback data: orig=" + orig + ", sign=" + sign);

			throw new BankAdapterException("验证平安银行网银支付回调数字签名失败！");
		}

		return true;
	}

	/**
	 * @param type
	 * @return
	 */
	private String getUrlByUIType(int type) {

		// 企业B2B
		if (PaymentUIType.ONLY_B2B == type) {
			return this.getB2bPaymentUrl();
		}

		// 个人B2C
		if (PaymentUIType.ONLY_B2C == type) {
			return this.getB2cPaymentUrl();
		}

		// 通用
		return this.getCommonPaymentUrl();
	}
}
