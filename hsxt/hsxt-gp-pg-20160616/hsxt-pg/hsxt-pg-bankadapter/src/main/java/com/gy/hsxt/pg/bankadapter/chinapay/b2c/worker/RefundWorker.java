/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.chinapay.b2c.worker;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import chinapay.PrivateKey;
import chinapay.SecureLink;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.pg.bankadapter.chinapay.ChinapayOrderResultDTO;
import com.gy.hsxt.pg.bankadapter.chinapay.ChinapayOrderStatus;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.abstracts.AbstractB2cWorker;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.common.B2cParamCheckHelper;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.common.ChinapayB2cConst;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.common.ChinapayB2cConst.BankTransType;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.common.ChinapayB2cConst.CpRefundStatus;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.common.ChinapayB2cConst.CpRespCode;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.common.ChinapayB2cFieldsKey.RefundCallbackParamKey;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.common.ChinapayB2cFieldsKey.RefundReqParamKey;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.common.ChinapayHttpHelper;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.params.ChinapayRefundParam;
import com.gy.hsxt.pg.bankadapter.common.exception.BankAdapterException;
import com.gy.hsxt.pg.bankadapter.common.utils.DateUtils;
import com.gy.hsxt.pg.bankadapter.common.utils.StringHelper;

/***************************************************************************
 * <PRE>
 *  Project Name    : bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.chinapay.b2c.worker
 * 
 *  File Name       : RefundWorker.java
 * 
 *  Creation Date   : 2014-10-23
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 中国银联B2C退款业务
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/

public class RefundWorker extends AbstractB2cWorker {
	// 银联B2C退款接口地址
	// (1) 测试环境：http://payment-test.chinapay.com/refund1/SingleRefund.jsp?
	// (2) 生产环境：http://console.chinapay.com/refund/SingleRefund.jsp?
	private String bankRefundAddress;
	
	/**
	 * 构造函数
	 */
	public RefundWorker() {
	}
	
	/**
	 * 退款接口方法
	 * 
	 * @param params
	 * @return
	 * 
	 * demo:
	 * <html><head><meta http-equiv="Content-Type" content="text/html; charset=GB2312" /></head><body>ResponseCode=0&MerID=808080201303096&ProcessDate=20131226&SendTime=210029&TransType=0002&OrderId=1701131219000005&RefundAmout=000000000001&Status=1&Priv1=&CheckValue=0DF9704B60E3EEDC5436F74F7C348CC47D2BF6C02F713840019F30528DDFEC3F81877FE6F2B76336DB95983308A46E6E117F4E6F82CF6D181DEE49F008EFDC1DA40ED85BC033D7C8CF19F7E9F8305E4820D1234F46E2451B01DE44940F3BF597A48A483A7428B79FC9B60E04C22848BFB65DF333FFF6158E60B042EBD246FC63</body></html>
	 * 银联接口返回的数据如下：ResponseCode=0&MerID=808080201303096&ProcessDate=20131219&SendTime=101504&TransType=0002&OrderId=1701131219000001&RefundAmout=000000000001&Status=1&Priv1=null&CheckValue=B2473AA8AEC13EA09FFB6E263EC8A58A4A50AE63BD93ECDAA84A681ABFC54AEAC4EA812C595BDB066823CE305A4FB4AAB8BA3719E16CFD780BDF532ECD6826BECC407147FC06799126B8E7C62171015DDC4ED15AF2AEDDA3C6BF1750ED5ED9973C5D05F72E27513AA5306E50645B46A6EB98EA2FDC879F2C068A2F0974CAA741
	 */
	public ChinapayOrderResultDTO doRefund(ChinapayRefundParam params) throws Exception {
		// 进行退款的参数校验
		B2cParamCheckHelper.checkRefundParams(params);
		
		// 原订单号
		String orderNo = params.getOrderNo();
		// 退款金额，全额退款则为原交易金额
		BigInteger refundAmount = params.getRefundAmount();
		// 原交易日期
		Date translateDate = params.getTranslateDate();
		// 这个地址用于接收银行服务的退款成功回调，这个地址在支付时发给银行
		String notifyURL = params.getNotifyURL();
		
		String transAmt = StringUtils.leftPad(refundAmount.toString(), 12, '0'); // 订单交易金额，单位为分，12位长度，左补0，必填
		String transDate = DateUtils.format2yyyyMMddDate(translateDate);
		String transType = BankTransType.REFUND;
		String version = ChinapayB2cConst.CHINAPAY_REFUND_VERSION;
		String priv1 = "intergrowth";
		
		// 私钥对象
		PrivateKey privateKey = new PrivateKey();
		
		if(!privateKey.buildKey(mechantNo, 0, privateKeyFilePath)) {
			logger.error("doRefund:  中国银联私钥错误! mechantNo="
					+ mechantNo + " privateKeyFilePath =" + privateKeyFilePath);
			
			throw new BankAdapterException("退款操作失败，原因：中国银联私钥错误!");
		}
		
		// 防钓鱼版本签名方法
		String chkSrcStr = StringHelper.joinString(mechantNo, transDate, transType,
				orderNo, transAmt, priv1);
		
		//签名结果数据
		String chkValue = new SecureLink(privateKey).Sign(chkSrcStr);
		
		if (logger.isDebugEnabled()) {
			logger.debug("doRefund:  chinapay chkSrcStr =" + chkSrcStr
					+ ", chkValue =" + chkSrcStr);
		}
		
		Map<String, String> map = new HashMap<String, String>();
		map.put(RefundReqParamKey.MER_ID, mechantNo);
		map.put(RefundReqParamKey.ORDER_ID, orderNo);
		map.put(RefundReqParamKey.REFUND_AMOUNT, transAmt);
		map.put(RefundReqParamKey.TRANS_DATE, transDate);
		map.put(RefundReqParamKey.TRANS_TYPE, transType);
		map.put(RefundReqParamKey.VERSION, version); // 退款和支付支持的版本号不同，退款版本号20070129
		map.put(RefundReqParamKey.RETURN_URL, notifyURL);
		map.put(RefundReqParamKey.PRIV1, priv1);
		map.put(RefundReqParamKey.CHK_VALUE, chkValue);
		
		// 银行响应的html
		String returnHtmlContent = ChinapayHttpHelper.sendHttpSSLMsg(
				bankRefundAddress, map);

		// 将返回键值数据填充到Map对象中，方便后续处理
		Map<String, String> returnMap = ChinapayHttpHelper
				.parseReturnStr2Map(returnHtmlContent);

		if (null == returnMap) {
			throw new BankAdapterException("退款操作失败，原因：中国银联返回报文格式异常，无法解析!");
		}

		String retTransAmt = returnMap.get(RefundCallbackParamKey.REFUND_AMOUT);

		if (StringHelper.isEmpty(retTransAmt)) {
			retTransAmt = transAmt;
		}

		String respCode = returnMap.get(RefundCallbackParamKey.RESP_CODE);

		// 通过ResponeseCode可以判断查询是否成功, 查询成功ResponseCode的值为0
		if (CpRespCode.REQ_SUCCESS_0.equals(respCode)) {
			ChinapayOrderResultDTO returnValue = new ChinapayOrderResultDTO(
					orderNo, ChinapayOrderStatus.REFUND_FAILED);
			returnValue.setPayAmount(retTransAmt);
			returnValue.setTranTime(translateDate);
			returnValue.setTransType(transType);

			// 验证签名成功
			if (verifySignatureOfRefund(returnMap)) {
				// 1-退款提交成功, 3-退款成功, 8-退款失败
				String refundStatus = returnMap.get(RefundCallbackParamKey.STATUS);

				// 1-退款提交成功
				if (CpRefundStatus.PROCESSING_1.equals(refundStatus)) {
					returnValue.setOrderStatus(ChinapayOrderStatus.REFUND_PROCESSING);
				}
				// 3-退款成功
				else if (CpRefundStatus.SUCCESS_3.equals(refundStatus)) {
					returnValue.setOrderStatus(ChinapayOrderStatus.REFUND_SUCCESS);
				}
				// 8-退款失败
				else if (CpRefundStatus.FAILED_8.equals(refundStatus)) {
					returnValue.setOrderStatus(ChinapayOrderStatus.REFUND_FAILED);
				} else {
					logger.info("doRefund: 状态异常! Status=" + refundStatus);

					returnValue.setErrorInfo("退款操作失败，银行错误码：" + refundStatus);
				}
			}
			
			return returnValue;
		} 
		// 205-重复提交该笔交易
		else if (CpRespCode.DUPLICATE_RECORD_205.equals(respCode)) {
			throw new BankAdapterException("退款操作失败，原因：该退款已经被提交过一次，不能再次提交！");
		} 
		// 402-原始记录不存在, 无法退款操作
		else if (CpRespCode.ORI_DATA_NOT_EXIST_402.equals(respCode)) {
			throw new BankAdapterException("退款操作失败，原因：原始交易不存在，无法完成退款操作！");
		}
		// 其他错误
		else {
			logger.debug("doRefund: returnStr=" + returnHtmlContent + ", orderNo=" + orderNo);

			// 解析错误信息
			String failReason = returnHtmlContent.replaceAll(".*Message=", "")
					.replaceFirst("^\\r\\n", "");

			throw new BankAdapterException("退款操作失败，原因：" + failReason);
		}
	}
	
	/**
	 * 处理银联退款回调接口方法
	 * 
	 * @param cbParamMap
	 * @return
	 * @throws Exception
	 */
	public ChinapayOrderResultDTO parseResultFromBankCallback (
			Map<String, String> cbParamMap) throws Exception {
		// 验证回调的签名
		this.verifySignatureOfCallBack(cbParamMap);
		
		// 订单数据准备
		String resCode = cbParamMap.get(RefundCallbackParamKey.RESP_CODE);
		String ordId = cbParamMap.get(RefundCallbackParamKey.ORDER_ID);
		String refundAmout = cbParamMap.get(RefundCallbackParamKey.REFUND_AMOUT);
		String refundStatus = cbParamMap.get(RefundCallbackParamKey.STATUS);
		String errorMessage = cbParamMap.get(RefundCallbackParamKey.ERR_MSG);
		
		ChinapayOrderResultDTO returnValue = new ChinapayOrderResultDTO(ordId,
				ChinapayOrderStatus.REFUND_FAILED);
		returnValue.setPayAmount(refundAmout);
		returnValue.setTransType(BankTransType.REFUND);
		
		// ResponseCode为0，且Status为3或8的时候，需要对chinapay返回数据进行验签
		if (CpRespCode.REQ_SUCCESS_0.equals(resCode)) {
			// 1-退款提交成功
			if (CpRefundStatus.PROCESSING_1.equals(refundStatus)) {
				returnValue.setOrderStatus(ChinapayOrderStatus.REFUND_PROCESSING);
			}
			// 3-退款成功
			else if (CpRefundStatus.SUCCESS_3.equals(refundStatus)) {
				returnValue.setOrderStatus(ChinapayOrderStatus.REFUND_SUCCESS);
			}
			// 8-退款失败
			else if (CpRefundStatus.FAILED_8.equals(refundStatus)) {
				returnValue.setOrderStatus(ChinapayOrderStatus.REFUND_FAILED);
				returnValue.setErrorInfo("退款操作失败，银行错误码：" + refundStatus);
			} else {
				logger.info("parseResultFromBankCallback: refundStatus 状态异常! Status="
						+ refundStatus);
				returnValue.setErrorInfo("退款操作失败，银行错误码：" + refundStatus);
			}
		} else {
			logger.info("parseResultFromBankCallback: resCode和refundStatus异常 ! ResCode="
					+ resCode + ",refundStatus=" + refundStatus);

			returnValue.setErrorInfo(errorMessage);
		}
		
		return returnValue;
	}
	
	/**
	 * 验证签名
	 * 
	 * @param returnMap
	 * @return
	 */
	private boolean verifySignatureOfRefund(Map<String, String> returnMap) {
		PrivateKey publicKey = new PrivateKey();

		if (!publicKey.buildKey(ChinapayB2cConst.PUB_MERID, 0, bankPubKeyPath)) {
			logger.error("verifySignatureOfRefund:  中国银联公钥错误! mechantNo="
					+ mechantNo + " bankPubKeyPath =" + privateKeyFilePath);

			throw new BankAdapterException("退款操作失败，原因：中国银联公钥错误!");
		}
		
		String checkSrcStr = StringHelper.joinString(
				returnMap.get(RefundCallbackParamKey.MER_ID),
				returnMap.get(RefundCallbackParamKey.PROCESS_DATE),
				returnMap.get(RefundCallbackParamKey.TRANS_TYPE),
				returnMap.get(RefundCallbackParamKey.ORDER_ID),
				returnMap.get(RefundCallbackParamKey.REFUND_AMOUT),
				returnMap.get(RefundCallbackParamKey.STATUS),
				returnMap.get(RefundCallbackParamKey.PRIV1));

		String checkValue = StringHelper.avoidNull(returnMap
				.get(RefundCallbackParamKey.CHK_VALUE));

		// 验证签名
		boolean isVerifySignSuccess = new SecureLink(publicKey)
				.verifyAuthToken(checkSrcStr, checkValue);

		// 对响应结果进行验证签名
		if (!isVerifySignSuccess) {
			logger.info("verifySignatureOfRefund:  对银行回调请求验证签名失败! plainData="
					+ checkSrcStr + ", CheckValue=" + checkValue);

			throw new BankAdapterException("退款操作失败，原因：验证中国银联签名失败！");
		}

		return isVerifySignSuccess;
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
		// 创建公钥对象，用其验证银联的签名信息
		PrivateKey publicKey = new PrivateKey();

		if (!publicKey.buildKey(ChinapayB2cConst.PUB_MERID, 0, bankPubKeyPath)) {
			logger.error("verifySignatureOfCallBack: 中国银联公钥错误! mechantNo="
					+ mechantNo + " bankPubKeyPath =" + bankPubKeyPath);
			
			throw new BankAdapterException("处理银联退款回调失败，原因：中国银联公钥错误!");
		}
		
		// 订单数据准备
		String merId = cbParamMap.get(RefundCallbackParamKey.MER_ID);
		String ordId = cbParamMap.get(RefundCallbackParamKey.ORDER_ID);
		String transType = cbParamMap.get(RefundCallbackParamKey.TRANS_TYPE);
		String refundAmout = cbParamMap.get(RefundCallbackParamKey.REFUND_AMOUT);
		String processDate = cbParamMap.get(RefundCallbackParamKey.PROCESS_DATE);
		String status = cbParamMap.get(RefundCallbackParamKey.STATUS);
		String priv1 = cbParamMap.get(RefundCallbackParamKey.PRIV1);
		String checkValue = cbParamMap.get(RefundCallbackParamKey.CHK_VALUE);

		String checkSrcStr = StringHelper.joinString(merId, processDate,
				transType, ordId, refundAmout, status, priv1);

		// 验证签名
		boolean isVerifySignSuccess = new SecureLink(publicKey)
				.verifyAuthToken(checkSrcStr, checkValue);

		if (!isVerifySignSuccess) {
			logger.info("verifySignatureOfCallBack: 中国银联B2C退款回调时, 验证签名失败!!  callback data:"
					+ JSON.toJSONString(cbParamMap));

			throw new BankAdapterException("处理银联B2C退款回调失败，原因：验证银联签名失败!");
		}

		return isVerifySignSuccess;
	}

	public String getBankRefundAddress() {
		return bankRefundAddress;
	}

	public void setBankRefundAddress(String bankRefundAddress) {
		this.bankRefundAddress = bankRefundAddress;
	}
}