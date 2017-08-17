/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.chinapay.upmp.worker;

import java.math.BigInteger;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.pg.bankadapter.chinapay.ChinapayOrderResultDTO;
import com.gy.hsxt.pg.bankadapter.chinapay.ChinapayOrderStatus;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.common.ChinapayHttpHelper;
import com.gy.hsxt.pg.bankadapter.chinapay.upmp.common.ChinapayUpmpFieldsKey.PayCallbackParamKey;
import com.gy.hsxt.pg.bankadapter.chinapay.upmp.common.ChinapayUpmpFieldsKey.PayReqParamKey;
import com.gy.hsxt.pg.bankadapter.chinapay.upmp.common.ChinapayUpmpFieldsKey.PayRespParamKey;
import com.gy.hsxt.pg.bankadapter.chinapay.upmp.common.UpmpConst;
import com.gy.hsxt.pg.bankadapter.chinapay.upmp.common.UpmpConst.CpUpmpRespCode;
import com.gy.hsxt.pg.bankadapter.chinapay.upmp.common.UpmpConst.CpUpmpTransStatus;
import com.gy.hsxt.pg.bankadapter.chinapay.upmp.common.UpmpConst.UpmpBankTransType;
import com.gy.hsxt.pg.bankadapter.chinapay.upmp.common.UpmpParamCheckHelper;
import com.gy.hsxt.pg.bankadapter.chinapay.upmp.common.sdk.util.HttpUtil;
import com.gy.hsxt.pg.bankadapter.chinapay.upmp.common.sdk.util.UpmpService;
import com.gy.hsxt.pg.bankadapter.chinapay.upmp.params.UpmpPayTnParam;
import com.gy.hsxt.pg.bankadapter.common.constants.BanksConstants;
import com.gy.hsxt.pg.bankadapter.common.exception.BankAdapterException;
import com.gy.hsxt.pg.bankadapter.common.utils.DateUtils;
import com.gy.hsxt.pg.bankadapter.common.utils.StringHelper;

/***************************************************************************
 * <PRE>
 *  Project Name    : bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.chinapay.upmp.worker
 * 
 *  File Name       : UpmpPayWorker.java
 * 
 *  Creation Date   : 2014-11-21
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 处理银联UPMP手机支付类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class UpmpPayWorker extends AbstractBankWorker {
	protected final Logger logger = Logger.getLogger(this.getClass());

	// 银联UPMP支付接口地址
	// (1) 测试 http://222.66.233.198:8080/gateway/merchant/trade?
	// (2) 生产 https://mgate.unionpay.com/gateway/merchant/trade?
	private String bankPayServAddress;

	/**
	 * 构造函数
	 */
	public UpmpPayWorker() {
	}

	/**
	 * 订单推送方法, 接下来还需要将此方法返回的字符串(tn)发送到手机客户端, 在客户端APP使用此tn调用静态库完成支付操作
	 * 
	 * @param param
	 * @return tn
	 * @throws Exception
	 */
	public String doGetPayTN(UpmpPayTnParam param) throws Exception {
		// 参数校验
		UpmpParamCheckHelper.checkPayTnParams(param);

		String orderNo = param.getOrderNo();

		Date orderDate = param.getOrderDate();
		Date orderTimeout = new Date(orderDate.getTime()
				+ StringHelper.MILL_OF_DAY * 3);
		BigInteger payAmount = param.getPayAmount();
		String currencyId = StringHelper.isEmpty(param.getCurrencyId()) ? "156"
				: param.getCurrencyId();
		String jumpUrl = param.getJumpUrl();
		String notifyUrl = param.getNotifyUrl();

		String strOrderTime = resetDateTime2Zero(orderDate);
		String strOrderTimeout = resetDateTime2Zero(orderTimeout);

		// 保留域填充方法
		Map<String, String> merReservedMap = new HashMap<String, String>();
		merReservedMap.put(PayReqParamKey.TEST, "test");

		// 请求要素
		Map<String, String> reqMap = new HashMap<String, String>();
		reqMap.put(PayReqParamKey.VERSION, UpmpConst.CHINAPAY_SDK_VERSION);// 版本号
		reqMap.put(PayReqParamKey.CHARSET, BanksConstants.DEFAULT_CHARSET);// 字符编码
		reqMap.put(PayReqParamKey.TRANS_TYPE, UpmpBankTransType.PAY);// 交易类型
																		// 01消费;02预授权;04退款
		reqMap.put(PayReqParamKey.MER_ID, mechantNo);// 商户代码
		reqMap.put(PayReqParamKey.BACK_END_URL, notifyUrl);// 通知URL
		reqMap.put(PayReqParamKey.FRONT_END_URL, jumpUrl);// 前台通知URL(可选)
		reqMap.put(PayReqParamKey.ORDER_DESC, "");// 订单描述(可选)
		reqMap.put(PayReqParamKey.ORDER_TIME, strOrderTime);// 交易开始日期时间yyyyMMddHHmmss
		reqMap.put(PayReqParamKey.ORDER_TIMEOUT, strOrderTimeout);// 订单超时时间(可选),
																	// 设置为3天内
		reqMap.put(PayReqParamKey.ORDER_NUM, orderNo);// 订单号 最大40个字母、数字
		reqMap.put(PayReqParamKey.ORDER_AMOUNT, payAmount.toString());// 订单金额,
																		// 必须为整形
		reqMap.put(PayReqParamKey.ORDER_CURRENCY, currencyId);// 交易币种(可选)
		reqMap.put(PayReqParamKey.REQ_RESERVED, "intergrowth");// 请求方保留域(可选，用于透传商户信息)
		reqMap.put(PayReqParamKey.MER_RESERVED,
				UpmpService.buildReserved(merReservedMap));// 商户保留域(可选)

		String reqStr = UpmpService.buildReq(reqMap, privateKey);

		if (logger.isDebugEnabled()) {
			logger.debug("upmp request pay message =" + reqStr);
		}

		// 发送https请求
		String respString = HttpUtil.post(bankPayServAddress, reqStr);

		String returnValue = null;

		if (UpmpService.verifyResponse(respString, null, privateKey)) {
			// 服务器应答签名验证成功
			// 将返回键值数据填充到Map对象中，方便后续处理
			Map<String, String> returnMap = ChinapayHttpHelper
					.parseReturnStr2Map(respString);
				String respCode = returnMap.get(PayRespParamKey.RESP_CODE);
				String charset = returnMap.get(PayRespParamKey.CHARSET);

				// respCode是应答码，当应答码不是00时，respMsg描述错误信息
				if (CpUpmpRespCode.REQ_SUCCESS_00.equals(respCode)) {
					returnValue = returnMap.get(PayRespParamKey.TN);
				}
				// 01-请求发生错误
				else if (CpUpmpRespCode.REQ_ERR_01.equals(respCode)) {
					logger.error("respCode is wrong: respString="
							+ URLDecoder.decode(respString, charset));

					throw new BankAdapterException(
							"支付失败，原因：请求报文错误，请检查您传递的参数是否合法！");
				} else {
					logger.error("respCode is wrong: respString="
							+ URLDecoder.decode(respString, charset));

					throw new BankAdapterException("支付失败，银联错误码：" + respCode);
				}
		} else {
			// 服务器应答签名验证失败
			logger.error("verifyResponse is false: respString=" + respString);

			throw new BankAdapterException("支付失败，原因：验证中国银联签名失败！");
		}

		return returnValue;
	}

	/**
	 * 处理银联支付回调接口方法
	 * 
	 * @param cbParamMap
	 * @return
	 * @throws Exception
	 */
	public ChinapayOrderResultDTO parseResultFromBankCallback(
			Map<String, String> cbParamMap) throws Exception {
		// 验证回调报文签名
		this.verifySignatureOfCallBack(cbParamMap);

		String orderNo = cbParamMap.get(PayCallbackParamKey.ORDER_NUMBER);
		String orderTime = cbParamMap.get(PayCallbackParamKey.ORDER_TIME);
		String qn = cbParamMap.get(PayCallbackParamKey.QN);

		BigInteger amount = new BigInteger(
				cbParamMap.get(PayCallbackParamKey.SETTLE_AMOUNT));

		ChinapayOrderResultDTO returnValue = new ChinapayOrderResultDTO(
				orderNo, ChinapayOrderStatus.PAY_FAILED);
		returnValue.setCrrrency(cbParamMap
				.get(PayCallbackParamKey.SETTLE_CURRENCY));
		returnValue.setTransType(cbParamMap.get(PayCallbackParamKey.TRANS_TYPE));
		returnValue.setQn(qn); // 查询流水号
		returnValue.setTranTime(DateUtils.getYYYYMMddHHmmssDate(orderTime));
		returnValue.setPayAmount(amount);

		// 请在这里加上商户的业务逻辑程序代码
		// 获取通知返回参数，可参考接口文档中通知参数列表(以下仅供参考)
		String transStatus = cbParamMap.get(PayCallbackParamKey.TRANS_STATUS);// 交易状态

		// 00-交易成功
		if (CpUpmpTransStatus.TRANS_SUCCESS_00.equals(transStatus)) {
			returnValue.setOrderStatus(ChinapayOrderStatus.PAY_SUCCESS);
		}
		// 01-交易处理中
		else if (CpUpmpTransStatus.TRANS_PROCESSING_01.equals(transStatus)) {
			returnValue.setOrderStatus(ChinapayOrderStatus.PAY_PROCESSING);
		} else {
			returnValue.setErrorInfo("支付失败，银行错误码：" + transStatus);
		}

		return returnValue;
	}

	/**
	 * 处理银联支付回调接口方法
	 * 
	 * @param cbParamMap
	 * @return
	 * @throws Exception
	 */
	private boolean verifySignatureOfCallBack(Map<String, String> cbParamMap)
			throws Exception {

		if (!UpmpService.verifySignature(cbParamMap, privateKey)) {
			logger.info("verifySignatureOfCallBack: 中国银联UPMP支付回调时, 验证签名失败!!  callback data:"
					+ JSON.toJSONString(cbParamMap));

			throw new BankAdapterException("验证中国银联UPMP支付回调数字签名失败！");
		}

		return true;
	}

	public String getBankPayServAddress() {
		return bankPayServAddress;
	}

	public void setBankPayServAddress(String bankPayServAddress) {
		this.bankPayServAddress = bankPayServAddress;
	}

}
