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

import com.gy.hsxt.pg.bankadapter.chinapay.CpBizValueMapSwapper;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.common.ChinapayHttpHelper;
import com.gy.hsxt.pg.bankadapter.chinapay.upmp.common.ChinapayUpmpFieldsKey.OrderStateReqKey;
import com.gy.hsxt.pg.bankadapter.chinapay.upmp.common.ChinapayUpmpFieldsKey.OrderStateRespKey;
import com.gy.hsxt.pg.bankadapter.chinapay.upmp.common.UpmpConst;
import com.gy.hsxt.pg.bankadapter.chinapay.upmp.common.UpmpConst.CpUpmpRespCode;
import com.gy.hsxt.pg.bankadapter.chinapay.upmp.common.UpmpConst.CpUpmpTransStatus;
import com.gy.hsxt.pg.bankadapter.chinapay.upmp.common.UpmpConst.UpmpBankTransType;
import com.gy.hsxt.pg.bankadapter.chinapay.upmp.common.UpmpParamCheckHelper;
import com.gy.hsxt.pg.bankadapter.chinapay.upmp.common.sdk.util.HttpUtil;
import com.gy.hsxt.pg.bankadapter.chinapay.upmp.common.sdk.util.UpmpService;
import com.gy.hsxt.pg.bankadapter.common.beans.BankPaymentOrderResultDTO;
import com.gy.hsxt.pg.bankadapter.common.constants.BanksConstants;
import com.gy.hsxt.pg.bankadapter.common.constants.PaymentOrderStatus;
import com.gy.hsxt.pg.bankadapter.common.exception.BankAdapterException;
import com.gy.hsxt.pg.bankadapter.common.utils.DateUtils;
import com.gy.hsxt.pg.bankadapter.common.utils.StringHelper;

/***************************************************************************
 * <PRE>
 *  Project Name    : bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.chinapay.upmp.worker
 * 
 *  File Name       : UpmpOrderStateWorker.java
 * 
 *  Creation Date   : 2014-11-21
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 处理银联UPMP手机支付的查询订单状态工作者类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/

public class UpmpOrderStateWorker extends AbstractBankWorker {
	// 银联UPMP对账接口地址
	// (1) 测试 http://222.66.233.198:8080/gateway/merchant/query?
	// (2) 生产 https://mgate.unionpay.com/gateway/merchant/query?
	private String bankGetOrderAddress;

	/**
	 * 构造函数
	 */
	public UpmpOrderStateWorker() {
	}

	/**
	 * 主动请求银联获取订单的状态信息
	 * 
	 * @param transType 交易类型(UPMP手机支付: 01 支付交易, 04退款交易)
	 * @param orderNo 订单号
	 * @param orderDate 订单日期
	 * @return
	 * @throws Exception
	 * @exception 异常
	 */
	public BankPaymentOrderResultDTO doQueryOrderState(String transType,
			String orderNo, Date orderDate) throws Exception {
		return this.getPayOrderState(transType, orderNo, orderDate);
	}

	/**
	 * UPMP手机支付：主动请求银行获取订单的状态信息
	 * 
	 * @param transType
	 * @param orderNo
	 * @param orderDate
	 * @return
	 * @throws Exception
	 */
	private BankPaymentOrderResultDTO getPayOrderState(String transType, String orderNo,
			Date orderDate) throws Exception {
		// 参数校验
		UpmpParamCheckHelper.checkOrderStateParam(orderNo, orderDate);
		
		// 订单日期
		String strOrderTime = resetDateTime2Zero(orderDate);
		
		// 保留域填充方法
		Map<String, String> merReservedMap = new HashMap<String, String>(1);
		merReservedMap.put(OrderStateReqKey.TEST, "test");
		
		// 请求要素
		Map<String, String> reqMap = new HashMap<String, String>();
		reqMap.put(OrderStateReqKey.VERSION, UpmpConst.CHINAPAY_SDK_VERSION);// 版本号
		reqMap.put(OrderStateReqKey.CHARSET, BanksConstants.CHARSET_UTF8);// 字符编码
		reqMap.put(OrderStateReqKey.TRANS_TYPE, transType);// 交易类型
		reqMap.put(OrderStateReqKey.MER_ID, mechantNo);    // 商户代码
		reqMap.put(OrderStateReqKey.ORDER_TIME, strOrderTime);// 交易开始日期时间yyyyMMddHHmmss或yyyyMMdd
		reqMap.put(OrderStateReqKey.ORDER_NUM, orderNo);// 订单号
		reqMap.put(OrderStateReqKey.MER_RESERVED,
				     UpmpService.buildReserved(merReservedMap));// 商户保留域(可选)

		String reqStr = UpmpService.buildReq(reqMap, privateKey);
		String respString = HttpUtil.post(bankGetOrderAddress, reqStr);

		if (UpmpService.verifyResponse(respString, null, privateKey)) {
			// 服务器应答签名验证成功，将返回键值数据填充到Map对象中，方便后续处理
			Map<String, String> returnMap = ChinapayHttpHelper
					.parseReturnStr2Map(respString);

			String respCode = returnMap.get(OrderStateRespKey.RESP_CODE);
			String respMsg = returnMap.get(OrderStateRespKey.RESP_MSG);
			String charset = returnMap.get(OrderStateRespKey.CHARSET);
			String orderTime = returnMap.get(OrderStateRespKey.ORDER_TIME);

			Date transDate = DateUtils.getYYYYMMddHHmmssDate(orderTime);
			String settleAmount = returnMap.get(OrderStateRespKey.AMOUNT);
			
			// 将银行的交易类型枚举值适配成我们内部的交易类型枚举值
		    int pgTransType = CpBizValueMapSwapper.toPgTransType(transType);
			
			BankPaymentOrderResultDTO returnValue = new BankPaymentOrderResultDTO(orderNo,
					this.getDefaultStatus(transType));
			returnValue.setCrrrency(returnMap.get(OrderStateRespKey.CURRENCY));
			returnValue.setOrderNo(orderNo);
			returnValue.setTranTime(transDate);
			returnValue.setTransType(pgTransType);

			if (!StringHelper.isEmpty(settleAmount)) {
				returnValue.setPayAmount(new BigInteger(settleAmount));
			}

			// respCode是应答码，当应答码不是00时，respMsg描述错误信息, 00-请求成功
			if (CpUpmpRespCode.REQ_SUCCESS_00.equals(respCode)) {
				String retTransStatus = returnMap.get(OrderStateRespKey.TRANS_STATUS);
				String retOrderNum = returnMap.get(OrderStateRespKey.ORDER_NUM);

				if (CpUpmpTransStatus.TRANS_SUCCESS_00.equals(retTransStatus)
						&& orderNo.equals(retOrderNum)) {
					PaymentOrderStatus orderStatus = (UpmpBankTransType.PAY
							.equals(transType)) ? PaymentOrderStatus.PAY_SUCCESS
							: PaymentOrderStatus.REFUND_SUCCESS;

					returnValue.setOrderStatus(orderStatus);
				} else if (CpUpmpTransStatus.TRANS_PROCESSING_01
						.equals(retTransStatus) && orderNo.equals(retOrderNum)) {
					PaymentOrderStatus orderStatus = (UpmpBankTransType.PAY
							.equals(transType)) ? PaymentOrderStatus.PAY_PROCESSING
							: PaymentOrderStatus.REFUND_PROCESSING;

					returnValue.setOrderStatus(orderStatus);
				} else {
					logger.info("getPayOrderState: transStatus is wrong: respString="
							+ URLDecoder.decode(respString, charset));
				}

				return returnValue;
			}
			// 01-请求失败
			else if (CpUpmpRespCode.REQ_ERR_01.equals(respCode)) {
				logger.error("respCode is wrong: respString="
						           + URLDecoder.decode(respString, charset));

				throw new BankAdapterException("查询订单状态失败，原因：请求报文错误，请检查您传递的参数是否合法！");
			} else {
				logger.debug("getPayOrderState: respCode is wrong: respString="
						           + URLDecoder.decode(respString, charset));

				StringBuilder strBuffer = new StringBuilder();
				strBuffer.append("查询订单状态失败，中国银联返回的状态码异常，银联错误码：").append(respCode)
						.append("，错误描述：").append(respMsg);

				throw new BankAdapterException(strBuffer.toString());
			}
		} else {
			// 服务器应答签名验证失败
			logger.info("getPayOrderState: verifyTransResponse is false!");

			throw new BankAdapterException("查询订单状态失败, 原因：中国银联返回报文格式异常！");
		}
	}

	private PaymentOrderStatus getDefaultStatus(String transType) {
		if (UpmpBankTransType.PAY.equals(transType)) {
			return PaymentOrderStatus.PAY_FAILED;
		} else if (UpmpBankTransType.REFUND.equals(transType)) {
			return PaymentOrderStatus.REFUND_FAILED;
		}

		return PaymentOrderStatus.UNKNOWN;
	}

	public String getBankGetOrderAddress() {
		return bankGetOrderAddress;
	}

	public void setBankGetOrderAddress(String bankGetOrderAddress) {
		this.bankGetOrderAddress = bankGetOrderAddress;
	}

}
