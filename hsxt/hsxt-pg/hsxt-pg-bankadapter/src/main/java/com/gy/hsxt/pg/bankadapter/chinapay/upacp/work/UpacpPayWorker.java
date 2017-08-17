/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.chinapay.upacp.work;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.pg.bankadapter.chinapay.upacp.common.UpacpConst;
import com.gy.hsxt.pg.bankadapter.chinapay.upacp.common.UpacpConst.UpacpBankTransType;
import com.gy.hsxt.pg.bankadapter.chinapay.upacp.common.UpacpConst.UpacpRespCode;
import com.gy.hsxt.pg.bankadapter.chinapay.upacp.common.UpacpParamCheckHelper;
import com.gy.hsxt.pg.bankadapter.chinapay.upacp.params.UpacpBankRespParam;
import com.gy.hsxt.pg.bankadapter.chinapay.upacp.params.UpacpPayTnParam;
import com.gy.hsxt.pg.bankadapter.common.beans.BankPaymentOrderResultDTO;
import com.gy.hsxt.pg.bankadapter.common.constants.BanksConstants;
import com.gy.hsxt.pg.bankadapter.common.constants.BanksConstants.ErrorCode;
import com.gy.hsxt.pg.bankadapter.common.exception.BankAdapterException;
import com.gy.hsxt.pg.bankadapter.common.utils.HttpRequestParamHelper;
import com.gy.hsxt.pg.bankadapter.common.utils.StringHelper;
import com.unionpay.acp.sdk.SDKConfig;
import com.unionpay.acp.sdk.SDKConstants;
import com.unionpay.acp.sdk.SDKUtil;

/***************************************************************************
 * <PRE>
 *  Project Name    : bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.chinapay.upacp.worker
 * 
 *  File Name       : UpacpPayWorker.java
 * 
 *  Creation Date   : 2014-11-21
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 处理银联UPACP手机支付类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class UpacpPayWorker extends AbstractUpacpBankWorker {
	protected final Logger logger = Logger.getLogger(this.getClass());

	// 银联UPACP支付接口地址
	// (1) 测试 http://222.66.233.198:8080/gateway/merchant/trade?
	// (2) 生产 https://mgate.unionpay.com/gateway/merchant/trade?
	private String bankPayServAddress;

	/**
	 * 构造函数
	 */
	public UpacpPayWorker() {
	}

	/**
	 * 订单推送方法, 接下来还需要将此方法返回的字符串(tn)发送到手机客户端, 在客户端APP使用此tn调用静态库完成支付操作
	 * 
	 * @param param
	 * @return tn
	 * @throws Exception
	 */
	public String doGetPayTN(UpacpPayTnParam param) throws Exception {

		// 参数校验
		UpacpParamCheckHelper.checkPayTnParams(param);

		Date orderDate = param.getOrderDate();
		Date orderTimeout = new Date(orderDate.getTime()
				+ StringHelper.MILL_OF_DAY * 3);
		BigInteger payAmount = param.getPayAmount();

		String currencyId = StringHelper.isEmpty(param.getCurrencyId()) ? "156"
				: param.getCurrencyId();
		String jumpUrl = param.getJumpUrl();
		String notifyUrl = param.getNotifyUrl();

		String orderNo = param.getOrderNo();
		String strOrderTime = resetDateTime2Zero(orderDate);
		String strOrderTimeout = resetDateTime2Zero(orderTimeout);

		// 组装请求报文
		Map<String, String> data = new HashMap<String, String>(20);
		{
			// 版本号
			data.put("version", UpacpConst.ACP_VERSION);
			// 字符集编码 默认"UTF-8"
			data.put("encoding", BanksConstants.CHARSET_UTF8);
			// 签名方法 01 RSA
			data.put("signMethod", "01");
			// 交易类型 01-消费
			data.put("txnType", UpacpBankTransType.PAY);
			// 交易子类型 01:自助消费 02:订购 03:分期付款
			data.put("txnSubType", "01");
			// 业务类型
			data.put("bizType", "000201");
			// 渠道类型，07-PC，08-手机
			data.put("channelType", "08");
			// 前台通知地址 ，控件接入方式无作用
			data.put("frontUrl", jumpUrl);
			// 后台通知地址
			data.put("backUrl", notifyUrl);
			// 接入类型，商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
			data.put("accessType", "0");
			// 商户号码，请改成自己的商户号
			data.put("merId", mechantNo);
			// 商户订单号，8-40位数字字母
			data.put("orderId", orderNo);
			// 订单发送时间，取系统时间(格式：YYYYMMDDhhmmss)
			data.put("txnTime", strOrderTime);
			// 订单支付超时时间
			data.put("payTimeout", strOrderTimeout);
			// 交易金额，单位分
			data.put("txnAmt", payAmount.toString());
			// 交易币种
			data.put("currencyCode", currencyId);
			// 请求方保留域，透传字段，查询、通知、对账文件中均会原样出现
			data.put("reqReserved", "intergrowth");
			// 订单描述，可不上送，上送时控件中会显示该信息
			// data.put("orderDesc", "订单描述");

			// 进行数字签名
			data = signData(data);
		}

		logger.info("UPACP支付交易请求报文=[" + data.toString() + "]");

		// 交易请求url 从配置文件读取
		String requestAppUrl = SDKConfig.getConfig().getAppRequestUrl();

		// 发起http请求
		Map<String, String> resmap = this.submit2Bank(data, requestAppUrl);

		logger.info("UPACP支付交易应答报文=[" + resmap.toString() + "]");

		String respCode = resmap.get("respCode");

		// 支付成功00
		if (UpacpRespCode.OPT_SUCCESS.valueEquals(respCode)) {
			return resmap.get("tn");
		}

		throw new BankAdapterException(ErrorCode.FAILED, "获取TN码失败!! 来自银行提示:["
				+ respCode + "] " + UpacpRespCode.getDescByErrCode(respCode));
	}

	/**
	 * 处理银联支付回调接口方法
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public BankPaymentOrderResultDTO parseResultFromBankCallback(
			HttpServletRequest req) throws Exception {

		// 取得银联默认编码
		String encoding = req.getParameter(SDKConstants.param_encoding);

		Map<String, String> valideData = HttpRequestParamHelper
				.parseRequestParams(req, encoding);

		// 验证回调报文签名
		this.verifySignatureOfCallBack(valideData, encoding);

		// txnType: 01-支付,04-退款
		UpacpBankRespParam bankRespParam = new UpacpBankRespParam(valideData);

		bankRespParam.setOrderNoKey("orderId").setPayAmountKey("txnAmt")
				.setTransTypeKey("txnType").setTranTimeKey("txnTime")
				.setQnKey("queryId").setPriv1Key("reqReserved")
				.setCurrencyKey("currencyCode").setRespCodeKey("respCode")
				.setRespMsgKey("respMsg");

		return convert2BankPaymentOrderResultDTO(bankRespParam);
	}

	public String getBankPayServAddress() {
		return bankPayServAddress;
	}

	public void setBankPayServAddress(String bankPayServAddress) {
		this.bankPayServAddress = bankPayServAddress;
	}

	/**
	 * 处理银联支付回调接口方法
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
			logger.info("verifySignatureOfCallBack: 中国银联UPACP支付回调时, 验证签名失败!!  callback data:"
					+ JSON.toJSONString(cbParamMap));

			throw new BankAdapterException("验证中国银联UPACP支付回调数字签名失败！");
		}

		return true;
	}

}
