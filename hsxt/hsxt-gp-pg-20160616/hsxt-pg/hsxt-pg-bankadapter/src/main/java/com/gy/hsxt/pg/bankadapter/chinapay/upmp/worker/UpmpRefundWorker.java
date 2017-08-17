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
import com.gy.hsxt.pg.bankadapter.chinapay.upmp.common.ChinapayUpmpFieldsKey.RefundCallbackParamKey;
import com.gy.hsxt.pg.bankadapter.chinapay.upmp.common.ChinapayUpmpFieldsKey.RefundReqParamKey;
import com.gy.hsxt.pg.bankadapter.chinapay.upmp.common.ChinapayUpmpFieldsKey.RefundRespParamKey;
import com.gy.hsxt.pg.bankadapter.chinapay.upmp.common.UpmpConst;
import com.gy.hsxt.pg.bankadapter.chinapay.upmp.common.UpmpConst.CpUpmpRespCode;
import com.gy.hsxt.pg.bankadapter.chinapay.upmp.common.UpmpConst.CpUpmpTransStatus;
import com.gy.hsxt.pg.bankadapter.chinapay.upmp.common.UpmpConst.UpmpBankTransType;
import com.gy.hsxt.pg.bankadapter.chinapay.upmp.common.UpmpParamCheckHelper;
import com.gy.hsxt.pg.bankadapter.chinapay.upmp.common.sdk.util.HttpUtil;
import com.gy.hsxt.pg.bankadapter.chinapay.upmp.common.sdk.util.UpmpService;
import com.gy.hsxt.pg.bankadapter.chinapay.upmp.params.UpmpRefundParam;
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
 *  File Name       : UpmpRefundWorker.java
 * 
 *  Creation Date   : 2014-11-21
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 处理银联UPMP手机支付-处理退款逻辑
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class UpmpRefundWorker extends AbstractBankWorker {
	protected final Logger logger = Logger.getLogger(this.getClass());

	// 银联UPMP退款接口地址(跟支付接口公用一个请求url地址)
	// (1) 测试 http://222.66.233.198:8080/gateway/merchant/trade?
	// (2) 生产 https://mgate.unionpay.com/gateway/merchant/trade?
	private String bankRefundAddress;

	/**
	 * 构造函数
	 */
	public UpmpRefundWorker() {
	}

	/**
	 * 进行退单操作
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public boolean doRefund(UpmpRefundParam param) throws Exception {
		// 参数校验
		UpmpParamCheckHelper.checkRefundParams(param);
		
		BigInteger refundAmount = param.getRefundAmount();
		
		String qn = param.getQn();
		String notifyUrl = param.getNotifyUrl();
		String refundOrderNo = param.getRefundOrderNo();
		String strRefundDateTime = resetDateTime2Zero(new Date());
		
		// 保留域填充方法
		Map<String, String> merReservedMap = new HashMap<String, String>();
		merReservedMap.put(RefundReqParamKey.TEST, "test");
		
		// 请求要素
		Map<String, String> reqMap = new HashMap<String, String>();
		reqMap.put(RefundReqParamKey.VERSION, UpmpConst.CHINAPAY_SDK_VERSION);// 版本号
		reqMap.put(RefundReqParamKey.CHARSET, BanksConstants.DEFAULT_CHARSET);// 字符编码
		reqMap.put(RefundReqParamKey.TRANS_TYPE, UpmpBankTransType.REFUND);// 交易类型
		reqMap.put(RefundReqParamKey.MER_ID, mechantNo);// 商户代码
		reqMap.put(RefundReqParamKey.BACK_END_URL, notifyUrl);// 通知URL
		reqMap.put(RefundReqParamKey.ORDER_TIME, strRefundDateTime);// 交易开始日期时间yyyyMMddHHmmss（退货交易新交易日期，非原交易日期）
		reqMap.put(RefundReqParamKey.ORDER_NUM, refundOrderNo);// 订单号(退货交易新订单号，非原交易订单号)
		reqMap.put(RefundReqParamKey.ORDER_AMT, refundAmount.toString());// 订单金额
		// req.put("orderCurrency", "156");// 交易币种(可选)
		reqMap.put(RefundReqParamKey.QN, qn);// 查询流水号(原订单支付成功后获取的流水号)
		reqMap.put(RefundReqParamKey.REQ_RESERVED, "intergrowth");// 请求方保留域(可选，用于透传商户信息)
		reqMap.put(RefundReqParamKey.MER_RESERVED, UpmpService.buildReserved(merReservedMap));// 商户保留域(可选)

		String reqStr = UpmpService.buildReq(reqMap, privateKey);

		if (logger.isDebugEnabled()) {
			logger.debug("upmp request pay message =" + reqStr);
		}

		// 发送https请求
		String respString = HttpUtil.post(bankRefundAddress, reqStr);

		if (UpmpService.verifyResponse(respString, null, privateKey)) {
			// 服务器应答签名验证成功
			// 将返回键值数据填充到Map对象中，方便后续处理
			Map<String, String> returnMap = ChinapayHttpHelper
					.parseReturnStr2Map(respString);

			String respCode = returnMap.get(RefundRespParamKey.RESP_CODE);
			String respMsg = returnMap.get(RefundRespParamKey.RESP_MSG);
			String charset = returnMap.get(RefundRespParamKey.CHARSET);

			// 00-请求成功, respCode是应答码, 当应答码不是00时, respMsg描述错误信息
			if (CpUpmpRespCode.REQ_SUCCESS_00.equals(respCode)) {
				return true;
			}
			// 01-请求失败
			else if (CpUpmpRespCode.REQ_ERR_01.equals(respCode)) {
				logger.error("respCode is wrong: respString="
						+ URLDecoder.decode(respString, charset));

				throw new BankAdapterException("退款失败，原因：请求报文错误，请检查您传递的参数是否合法！");
			}
			// 42-交易超限
			else if (CpUpmpRespCode.REQ_OVER_LIMIT_42.equals(respCode)) {
				logger.error("respCode is wrong: respString="
						+ URLDecoder.decode(respString, charset));

				throw new BankAdapterException("退款失败，原因：交易超限，不可重复提交退款！");
			} else {
				logger.error("respCode is wrong: respString="
						+ StringHelper.decodeUrlString(respString, charset));

				StringBuilder strBuffer = new StringBuilder();
				strBuffer.append("退款操作失败，银联错误码：").append(respCode).append("，错误描述：")
						.append(respMsg);

				throw new BankAdapterException(strBuffer.toString());
			}
		} else {
			// 服务器应答签名验证失败
			logger.error("verifyResponse is false: respString=" + respString);

			throw new BankAdapterException("退款操作失败，原因：验证中国银联签名失败！");
		}
	}

	/**
	 * 处理银联退款回调接口方法
	 * 
	 * @param cbParamMap
	 * @return
	 * @throws Exception
	 */
	public ChinapayOrderResultDTO parseResultFromBankCallback(
			Map<String, String> cbParamMap) throws Exception {
		// 验证回调报文的签名
		this.verifySignatureOfCallBack(cbParamMap);
		
		// 该值即为：支付成功后notify的qn值
		String orderNo = cbParamMap.get(RefundCallbackParamKey.ORDER_NUMBER);
		String orderTime = cbParamMap.get(RefundCallbackParamKey.ORDER_TIME);
		BigInteger amount = new BigInteger(
				cbParamMap.get(RefundCallbackParamKey.SETTLE_AMOUNT));

		ChinapayOrderResultDTO returnValue = new ChinapayOrderResultDTO(
				orderNo, ChinapayOrderStatus.REFUND_FAILED);
		returnValue.setCrrrency(cbParamMap.get(RefundCallbackParamKey.SETTLE_CURRENCY));
		returnValue.setTransType(cbParamMap.get(RefundCallbackParamKey.TRANS_TYPE));
		returnValue.setTranTime(DateUtils.getYYYYMMddHHmmssDate(orderTime));
		returnValue.setPayAmount(amount);

		// 请在这里加上商户的业务逻辑程序代码
		// 获取通知返回参数，可参考接口文档中通知参数列表(以下仅供参考)
		String transStatus = cbParamMap.get(RefundCallbackParamKey.TRANS_STATUS);// 交易状态

		// 00-交易成功
		if (CpUpmpTransStatus.TRANS_SUCCESS_00.equals(transStatus)) {
			returnValue.setOrderStatus(ChinapayOrderStatus.REFUND_SUCCESS);
		}
		// 01-交易处理中
		else if (CpUpmpTransStatus.TRANS_PROCESSING_01.equals(transStatus)) {
			returnValue.setOrderStatus(ChinapayOrderStatus.REFUND_PROCESSING);
		} else {
			returnValue.setErrorInfo("退款操作失败，银行错误码：" + transStatus);
		}

		return returnValue;
	}
	
	/**
	 * 处理银联退款回调接口方法
	 * 
	 * @param cbParamMap
	 * @return
	 * @throws Exception
	 */
	private boolean verifySignatureOfCallBack(Map<String, String> cbParamMap)
			throws Exception {
		if(!UpmpService.verifySignature(cbParamMap, privateKey)) {
			logger.info("verifySignatureOfCallBack: 中国银联UPMP退款回调时, 验证签名失败!! callback data:"
					+ JSON.toJSONString(cbParamMap));
			
			throw new BankAdapterException("验证中国银联UPMP退款回调数字签名失败！");
		}
		
		return true;
	}

	public String getBankRefundAddress() {
		return bankRefundAddress;
	}

	public void setBankRefundAddress(String bankRefundAddress) {
		this.bankRefundAddress = bankRefundAddress;
	}

}
