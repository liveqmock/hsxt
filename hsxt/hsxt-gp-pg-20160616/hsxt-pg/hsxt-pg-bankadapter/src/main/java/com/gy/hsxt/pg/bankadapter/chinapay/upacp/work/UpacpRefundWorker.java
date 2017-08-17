/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.chinapay.upacp.work;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.pg.bankadapter.chinapay.ChinapayOrderResultDTO;
import com.gy.hsxt.pg.bankadapter.chinapay.upacp.common.UpacpConst.UpacpBankTransType;
import com.gy.hsxt.pg.bankadapter.chinapay.upacp.common.UpacpConst.UpacpRespCode;
import com.gy.hsxt.pg.bankadapter.chinapay.upacp.common.UpacpParamCheckHelper;
import com.gy.hsxt.pg.bankadapter.chinapay.upacp.params.UpacpBankRespParam;
import com.gy.hsxt.pg.bankadapter.chinapay.upacp.params.UpacpRefundParam;
import com.gy.hsxt.pg.bankadapter.common.constants.BanksConstants;
import com.gy.hsxt.pg.bankadapter.common.constants.BanksConstants.ErrorCode;
import com.gy.hsxt.pg.bankadapter.common.exception.BankAdapterException;
import com.unionpay.acp.sdk.SDKConfig;
import com.unionpay.acp.sdk.SDKConstants;
import com.unionpay.acp.sdk.SDKUtil;

/***************************************************************************
 * <PRE>
 *  Project Name    : bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.chinapay.upacp.worker
 * 
 *  File Name       : UpacpRefundWorker.java
 * 
 *  Creation Date   : 2014-11-21
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 处理银联UPACP手机支付-处理退款逻辑
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class UpacpRefundWorker extends AbstractUpacpBankWorker {
	protected final Logger logger = Logger.getLogger(this.getClass());

	// 银联UPACP退款接口地址(跟支付接口公用一个请求url地址)
	// (1) 测试 http://222.66.233.198:8080/gateway/merchant/trade?
	// (2) 生产 https://mgate.unionpay.com/gateway/merchant/trade?
	private String bankRefundAddress;

	/**
	 * 构造函数
	 */
	public UpacpRefundWorker() {
	}

	/**
	 * 进行退单操作
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public boolean doRefund(UpacpRefundParam param) throws Exception {

		// 参数校验
		UpacpParamCheckHelper.checkRefundParams(param);

		// 组装请求报文
		Map<String, String> data = new HashMap<String, String>(15);
		{
			// 版本号
			data.put("version", "5.0.0");
			// 字符集编码 默认"UTF-8"
			data.put("encoding", BanksConstants.DEFAULT_CHARSET);
			// 签名方法 01 RSA
			data.put("signMethod", "01");
			// 交易类型, 04-退款
			data.put("txnType", UpacpBankTransType.REFUND);
			// 交易子类型
			data.put("txnSubType", "00");
			// 业务类型
			data.put("bizType", "000201");
			// 渠道类型，07-PC，08-手机
			data.put("channelType", "08");
			// 后台通知地址
			data.put("backUrl", param.getNotifyUrl());
			// 接入类型，商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
			data.put("accessType", "0");
			// 商户号码，请改成自己的商户号
			data.put("merId", mechantNo);
			// 原消费的queryId，可以从查询接口或者通知接口中获取
			data.put("origQryId", param.getQn());
			// 商户订单号，8-40位数字字母，重新产生，不同于原消费
			data.put("orderId", param.getRefundOrderNo());
			// 订单发送时间，取系统时间
			data.put("txnTime", resetDateTime2Zero(new Date()));
			// 交易金额,单位：分
			data.put("txnAmt", param.toString());
			// 请求方保留域，透传字段，查询、通知、对账文件中均会原样出现
			// data.put("reqReserved", "透传信息");

			data = signData(data);
		}

		logger.info("UPACP退款请求报文=[" + data.toString() + "]");

		// 交易请求url 从配置文件读取
		String url = SDKConfig.getConfig().getBackRequestUrl();

		Map<String, String> resmap = this.submit2Bank(data, url);

		logger.info("UPACP退款应答报文=[" + resmap.toString() + "]");

		String respCode = resmap.get("respCode");

		// 退款成功00
		if (UpacpRespCode.OPT_SUCCESS.valueEquals(respCode)) {
			return true;
		}

		throw new BankAdapterException(ErrorCode.FAILED, "退款请求失败!! 来自银行提示:["
				+ respCode + "] " + UpacpRespCode.getDescByErrCode(respCode));
	}

	/**
	 * 处理银联退款回调接口方法
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public ChinapayOrderResultDTO parseResultFromBankCallback(
			HttpServletRequest req) throws Exception {
		// 取得银联默认编码
		String encoding = req.getParameter(SDKConstants.param_encoding);

		Map<String, String> valideData = parseRequestParams(req, encoding);

		// 验证回调报文签名
		this.verifySignatureOfCallBack(valideData, encoding);

		// txnType: 01-支付,04-退款
		UpacpBankRespParam bankRespParam = new UpacpBankRespParam(valideData);

		bankRespParam.setOrderNoKey("orderId").setPayAmountKey("txnAmt")
				.setTransTypeKey("txnType").setTranTimeKey("txnTime")
				.setQnKey("queryId").setPriv1Key("reqReserved")
				.setCrrrencyKey("currencyCode").setRespCodeKey("respCode")
				.setRespMsgKey("respMsg");

		return convert2ChinapayOrderResultDTO(bankRespParam);
	}

	public String getBankRefundAddress() {
		return bankRefundAddress;
	}

	public void setBankRefundAddress(String bankRefundAddress) {
		this.bankRefundAddress = bankRefundAddress;
	}

	/**
	 * 处理银联退款回调接口方法
	 * 
	 * @param cbParamMap
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	private boolean verifySignatureOfCallBack(Map<String, String> cbParamMap,
			String encoding) throws Exception {

		// 验证签名
		if (!SDKUtil.validate(cbParamMap, encoding)) {
			logger.info("verifySignatureOfCallBack: 中国银联UPACP退款回调时, 验证签名失败!!  callback data:"
					+ JSON.toJSONString(cbParamMap));

			throw new BankAdapterException("验证中国银联UPACP退款回调数字签名失败！");
		}

		return true;
	}

}
