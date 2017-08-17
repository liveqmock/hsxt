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

import chinapay.PrivateKey;
import chinapay.SecureLink;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.pg.bankadapter.chinapay.CpBizValueMapSwapper;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.abstracts.AbstractB2cWorker;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.common.ChinapayB2cConst;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.common.ChinapayB2cConst.BankTransStatus;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.common.ChinapayB2cConst.BankTransType;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.common.ChinapayB2cFieldsKey.PayCallbackParamKey;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.common.ChinapayB2cFieldsKey.PayReqParamKey;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.params.ChinapayPayParam;
import com.gy.hsxt.pg.bankadapter.common.beans.BankPaymentOrderResultDTO;
import com.gy.hsxt.pg.bankadapter.common.constants.PaymentOrderStatus;
import com.gy.hsxt.pg.bankadapter.common.exception.BankAdapterException;
import com.gy.hsxt.pg.bankadapter.common.utils.DateUtils;
import com.gy.hsxt.pg.bankadapter.common.utils.HttpRequestParamHelper;
import com.gy.hsxt.pg.bankadapter.common.utils.StringHelper;

/***************************************************************************
 * <PRE>
 *  Project Name    : bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.chinapay.b2c.worker
 * 
 *  File Name       : PayWorker.java
 * 
 *  Creation Date   : 2014-10-23
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 中国银联B2C页面支付(此类代码由lijiabei的代码中摘取)
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class PayWorker extends AbstractB2cWorker {
	// 银联B2C支付接口地址
	// (1) 测试 http://payment-test.chinapay.com/pay/TransGet?
	// (2) 生产 https://payment.chinapay.com/pay/TransGet?
	private String bankPayServAddress;
	
	/**
	 * 构造函数
	 */
	public PayWorker() {
	}
	
	/**
	 * 组装支付页面请求地址
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String doAssemblePayUrl(
			ChinapayPayParam params) throws Exception {
		String bankCode = params.getBankCode();
		String orderNo = params.getOrderNo();
		String clientIp = params.getClientIp();
		String currencyId = params.getCurrencyId();

		BigInteger payAmount = params.getPayAmount();

		// 这个地址用于接收银行服务的支付成功回调，这个地址在支付时发给银行。
		// 在接收回调时，还需要返回给银行一个商户显示成功支付的页面的地址。
		String notifyURL = params.getNotifyURL();

		// 显示支付成功页面
		String jumpURL = params.getJumpURL();
		
		if(StringHelper.isEmpty(currencyId)) {
			currencyId = ChinapayB2cConst.CurrencyEnum.RMB;
		}
		
		String transAmt = StringHelper.leftPad(payAmount.toString(), 12, '0'); //订单交易金额，单位为分，12位长度，左补0，必填
		String transDate = params.getOrderDate(); // 交易日期
		String transType = BankTransType.PAY;
		String version = ChinapayB2cConst.CHINAPAY_PAY_VERSION;
		String priv1 = "intergrowth"; // 商户私有域，长度不要超过60个字节，可选
		
		PrivateKey privateKey = new PrivateKey();
		
		if(!privateKey.buildKey(mechantNo , 0, privateKeyFilePath)){
			logger.error("doAssemblePayUrl: 中国银联私钥错误! mechantNo="
					+ mechantNo + " privateKeyFilePath =" + privateKeyFilePath);

			throw new BankAdapterException("支付失败，原因：中国银联私钥错误!");
		}
		
		// 防钓鱼版本签名方法
		String chkSrcStr = StringHelper.joinString(mechantNo, orderNo, transAmt,
				currencyId, transDate, transType, priv1, clientIp);

		// 签名结果数据
		String chkValue = new SecureLink(privateKey).Sign(chkSrcStr);
		
		if (logger.isDebugEnabled()) {
			logger.debug("doAssemblePayUrl: chinapay chkSrcStr=" + chkSrcStr
					+ ", chkValue=" + chkValue);
		}
		
		Map<String, String> map = new HashMap<String, String>();
		
		// 参数信息，同时也是数字签名源数据
		map.put(PayReqParamKey.MER_ID, mechantNo);     // MerId为ChinaPay统一分配给商户的商户号，15位长度，必填
		map.put(PayReqParamKey.ORDER_ID, orderNo);     // 商户提交给ChinaPay的交易订单号，16个字节的数字串，必填
		map.put(PayReqParamKey.TRANS_AMT, transAmt);   // 订单交易金额，单位为分，12位长度，左补0，必填
		map.put(PayReqParamKey.CURY_ID, currencyId);   // 交易币种（货币代码）, 长度为3个字节的数字串，目前只支持人民币，取值为"156"
		map.put(PayReqParamKey.TRANS_DATE, transDate); // 交易日期
		map.put(PayReqParamKey.TRANS_TYPE, transType); // 交易类型，长度为4个字节的数字串，取值范围为："0001"和"0002"，其中"0001"表示消费交易，"0002"表示退货交易。
		map.put(PayReqParamKey.VERSION, version);      // 支付接口版本号：防钓鱼版本 8位数字
		map.put(PayReqParamKey.BG_RET_URL, notifyURL); // 商户系统后台应答接受地址 不超过80字节
		map.put(PayReqParamKey.PAGE_RET_URL, jumpURL); // 商户系统前台应答接受地址 不超过80字节
		map.put(PayReqParamKey.PRIV1, priv1);        // 商户私有域，长度不要超过60个字节，可选
		map.put(PayReqParamKey.CHK_VALUE, chkValue); // 256字节长的ASCII码,为此次交易提交关键 数据的数字签名，必填
		map.put(PayReqParamKey.GATE_ID, bankCode);   // 支付网关号，可选 4位数字，可以为空
		map.put(PayReqParamKey.CLIENT_IP, clientIp); // 用户IP
		map.put(PayReqParamKey.EXT_FLAG, "00");

		return HttpRequestParamHelper.assembleHttpReqUrl(
				getBankPayServAddress(), map);
	}
	
	/**
	 * 处理银联支付回调接口方法
	 * 
	 * @param cbParamMap
	 * @return
	 * @throws Exception
	 */
	public BankPaymentOrderResultDTO parseResultFromBankCallback(
			Map<String, String> cbParamMap) throws Exception {
		// 校验签名
		this.verifySignatureOfCallBack(cbParamMap);
		
		// 支付订单数据准备
		String orderNo = cbParamMap.get(PayCallbackParamKey.ORDER_NO); // 16
		String transAmt = cbParamMap.get(PayCallbackParamKey.AMOUNT); // 12
		String curyId = cbParamMap.get(PayCallbackParamKey.CURRENCY_CODE);// 3
		String strTransDate = cbParamMap.get(PayCallbackParamKey.TRANS_DATE);// 8
		String transType = cbParamMap.get(PayCallbackParamKey.TRANS_TYPE);// 4
		Date transDate = DateUtils.parse2yyyyMMddDate(strTransDate);
		String priv1 = cbParamMap.get(PayCallbackParamKey.PRIV1);// 
		// status表示交易状态只有"1001"表示支付成功，其他状态均表示未成功的交易。
		// 因此验证签名数据通过后，还需要判定交易状态代码是否为"1001"
		String status = cbParamMap.get(PayCallbackParamKey.STATUS);
		
		// 将银行的交易类型枚举值适配成我们内部的交易类型枚举值
		int pgTransType = CpBizValueMapSwapper.toPgTransType(transType);

		BankPaymentOrderResultDTO returnValue = new BankPaymentOrderResultDTO(
				orderNo, PaymentOrderStatus.PAY_FAILED);
		returnValue.setCrrrency(curyId);
		returnValue.setPayAmount(transAmt);
		returnValue.setTranTime(transDate);
		returnValue.setTransType(pgTransType);
		returnValue.setPriv1(priv1);
		
		// 1001消费交易成功
		if (BankTransStatus.PAY_SUCCESS.equals(status)) {
			// 消费交易
			if (BankTransType.PAY.equals(transType)) {
				// 1001表示支付成功，之后为商户系统对成功订单的逻辑处理
				// 注意：如果您在提交时同时填写了页面返回地址和后台返回地址，且地址相同，请先做一次数据库查询判断订单状态，以防止重复处理该笔订单
				returnValue.setOrderStatus(PaymentOrderStatus.PAY_SUCCESS);
			}
		} else {
			logger.info("parseResultFromBankCallback : B2C支付失败, 返回的状态码为 status="
					+ status);

			returnValue.setErrorInfo("B2C支付失败，银行错误码：" + status);
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
	private boolean verifySignatureOfCallBack(Map<String, String> cbParamMap) throws Exception {
		// 创建公钥对象，用其验证银联的签名信息
		PrivateKey publicKey = new PrivateKey();
		
		if(!publicKey.buildKey(ChinapayB2cConst.PUB_MERID, 0, bankPubKeyPath)) {
			logger.error("verifySignatureOfCallBack: 中国银联公钥错误! mechantNo="
					+ mechantNo + " bankPubKeyPath =" + privateKeyFilePath);
			
			throw new BankAdapterException("处理中国银联支付回调时验证签名失败，原因：中国银联公钥错误!");
		}
		
		// 支付订单数据准备
		String merId = StringHelper.avoidNull(cbParamMap.get(PayCallbackParamKey.MER_ID)); // 15
		String ordId = StringHelper.avoidNull(cbParamMap.get(PayCallbackParamKey.ORDER_NO)); // 16
		String transAmt = StringHelper.avoidNull(cbParamMap.get(PayCallbackParamKey.AMOUNT));// 12
		String curyId = StringHelper.avoidNull(cbParamMap.get(PayCallbackParamKey.CURRENCY_CODE));// 3
		String transDate = StringHelper.avoidNull(cbParamMap.get(PayCallbackParamKey.TRANS_DATE));// 8
		String transType = StringHelper.avoidNull(cbParamMap.get(PayCallbackParamKey.TRANS_TYPE));// 4
		String status = StringHelper.avoidNull(cbParamMap.get(PayCallbackParamKey.STATUS));  // status表示交易状态只有"1001"表示支付成功，其他状态均表示未成功的交易。因此验证签名数据通过后，还需要判定交易状态代码是否为"1001"
		String chkValue = StringHelper.avoidNull(cbParamMap.get(PayCallbackParamKey.CHECK_VALUE));
		
		// 验证签名
		boolean isVerifySuccess = new SecureLink(publicKey)
				.verifyTransResponse(merId, ordId, transAmt, curyId, transDate,
						transType, status, chkValue);

		if (!isVerifySuccess) {
			logger.info("verifySignatureOfCallBack: 中国银联B2C支付回调时, 验证签名失败!!  callback data:"
					+ JSON.toJSONString(cbParamMap));
			
			throw new BankAdapterException("中国银联支付B2C回调时验证签名失败!");
		}

		return isVerifySuccess;
	}

	public void setBankPayServAddress(String bankPayServAddress) {
		this.bankPayServAddress = bankPayServAddress;
	}
	
	public String getBankPayServAddress() {
		return bankPayServAddress;
	}
}
