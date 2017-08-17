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

import com.gy.hsxt.pg.bankadapter.chinapay.ChinapayOrderResultDTO;
import com.gy.hsxt.pg.bankadapter.chinapay.upacp.common.UpacpConst.UpacpBankTransType;
import com.gy.hsxt.pg.bankadapter.chinapay.upacp.common.UpacpConst.UpacpRespCode;
import com.gy.hsxt.pg.bankadapter.chinapay.upacp.common.UpacpParamCheckHelper;
import com.gy.hsxt.pg.bankadapter.chinapay.upacp.params.UpacpBankRespParam;
import com.gy.hsxt.pg.bankadapter.common.constants.BanksConstants;
import com.gy.hsxt.pg.bankadapter.common.exception.BankAdapterException;
import com.gy.hsxt.pg.bankadapter.common.utils.StringHelper;
import com.unionpay.acp.sdk.SDKConfig;

/***************************************************************************
 * <PRE>
 *  Project Name    : bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.chinapay.upacp.worker
 * 
 *  File Name       : UpmpOrderStateWorker.java
 * 
 *  Creation Date   : 2014-11-21
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 处理银联UPACP手机支付的查询订单状态工作者类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/

public class UpacpOrderStateWorker extends AbstractUpacpBankWorker {
	// 银联UPACP对账接口地址
	// (1) 测试 http://222.66.233.198:8080/gateway/merchant/query?
	// (2) 生产 https://mgate.unionpay.com/gateway/merchant/query?
	private String bankGetOrderAddress;

	/**
	 * 构造函数
	 */
	public UpacpOrderStateWorker() {
	}

	/**
	 * 主动请求银联获取订单的状态信息
	 * 
	 * @param transType
	 *            交易类型(UPACP手机支付: 01 支付交易, 04退款交易)
	 * @param orderNo
	 *            订单号
	 * @param orderDate
	 *            订单日期
	 * @return
	 * @throws Exception
	 * @exception 异常
	 */
	public ChinapayOrderResultDTO doQueryOrderState(String transType,
			String orderNo, Date orderDate) throws Exception {
		return this.getPayOrderState(orderNo, orderDate);
	}

	/**
	 * UPACP手机支付：主动请求银行获取订单的状态信息
	 * 
	 * @param transType
	 * @param orderNo
	 * @param orderDate
	 * @return
	 * @throws Exception
	 */
	private ChinapayOrderResultDTO getPayOrderState(String orderNo,
			Date orderDate) throws Exception {
		// 参数校验
		UpacpParamCheckHelper.checkOrderStateParam(orderNo, orderDate);

		// 订单日期
		String strOrderTime = resetDateTime2Zero(orderDate);

		Map<String, String> data = new HashMap<String, String>(12);
		{
			// 版本号
			data.put("version", "5.0.0");
			// 字符集编码 默认"UTF-8"
			data.put("encoding", BanksConstants.DEFAULT_CHARSET);
			// 签名方法 01 RSA
			data.put("signMethod", "01");
			// 交易类型: 查询本身的交易类型00, 并发被查询的交易的交易类型
			data.put("txnType", UpacpBankTransType.QUERY);
			// 交易子类型
			data.put("txnSubType", "00");
			// 业务类型
			data.put("bizType", "000000");
			// 渠道类型，07-PC，08-手机
			data.put("channelType", "08");
			// 接入类型，商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
			data.put("accessType", "0");
			// 商户号码，请改成自己的商户号
			data.put("merId", mechantNo);
			// 商户订单号，请修改被查询的交易的订单号
			data.put("orderId", orderNo);
			// 订单发送时间，请修改被查询的交易的订单发送时间
			data.put("txnTime", strOrderTime);

			data = signData(data);
		}

		logger.info("UPACP查询订单请求报文=[" + data.toString() + "]");

		// 交易请求url 从配置文件读取
		String url = SDKConfig.getConfig().getSingleQueryUrl();

		Map<String, String> resmap = this.submit2Bank(data, url);

		logger.info("UPACP查询订单应答报文=[" + resmap.toString() + "]");

		return parse2ChinapayOrderResultDTO(resmap, orderNo);
	}

	/**
	 * 解析为ChinapayOrderResultDTO对象
	 * 
	 * @param resmap
	 * @param orderNo
	 * @return
	 */
	private ChinapayOrderResultDTO parse2ChinapayOrderResultDTO(
			Map<String, String> resmap, String orderNo) {

		String respCode = UpacpRespCode.OPT_FAILED.getErrCode();
		String respMsg = "";

		if ((null != resmap) && (0 < resmap.size())) {
			respCode = resmap.get("respCode");
			respMsg = resmap.get("respMsg");
		}

		// 判断当前查询是否成功
		if (!UpacpRespCode.OPT_SUCCESS.valueEquals(respCode)) {

			if (StringHelper.isEmpty(respMsg)) {
				respMsg = "中国银联返回报文格式错误或网络异常！";
			}

			logger.info("查询订单状态失败, 原因：" + respMsg + " , orderNo=" + orderNo);

			throw new BankAdapterException("查询订单状态失败, 原因：" + respMsg);
		}

		// txnType: 01-支付,04-退款
		UpacpBankRespParam bankRespParam = new UpacpBankRespParam(resmap);

		bankRespParam.setOrderNoKey("orderId").setPayAmountKey("txnAmt")
				.setTransTypeKey("txnType").setTranTimeKey("txnTime")
				.setQnKey("queryId").setPriv1Key("reqReserved")
				.setCrrrencyKey("currencyCode").setRespCodeKey("origRespCode")
				.setRespMsgKey("origRespMsg");

		return convert2ChinapayOrderResultDTO(bankRespParam);
	}

	public String getBankGetOrderAddress() {
		return bankGetOrderAddress;
	}

	public void setBankGetOrderAddress(String bankGetOrderAddress) {
		this.bankGetOrderAddress = bankGetOrderAddress;
	}

}