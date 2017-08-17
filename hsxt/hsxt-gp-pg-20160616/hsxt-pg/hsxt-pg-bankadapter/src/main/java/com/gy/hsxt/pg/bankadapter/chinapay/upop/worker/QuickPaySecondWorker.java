/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.chinapay.upop.worker;

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
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.common.ChinapayB2cConst.BankTransType;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.common.ChinapayB2cConst.CurrencyEnum;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.common.ChinapayB2cFieldsKey.PayCallbackParamKey;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.common.ChinapayHttpHelper;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.abstracts.AbstractUpopWorker;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.common.ChinapayUpopConst;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.common.ChinapayUpopFieldsKey.PaySecondReqKey;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.common.UpopParamChecker;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.common.ChinapayUpopConst.UpopRespCode;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.common.ChinapayUpopConst.UpopSubTransType;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.common.ChinapayUpopConst.UpopTransStatus;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.common.ChinapayUpopFieldsKey.PaySecondRespKey;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.params.UpopPaySecondParam;
import com.gy.hsxt.pg.bankadapter.common.exception.BankAdapterException;
import com.gy.hsxt.pg.bankadapter.common.utils.DateUtils;
import com.gy.hsxt.pg.bankadapter.common.utils.MoneyAmountHelper;
import com.gy.hsxt.pg.bankadapter.common.utils.StringHelper;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.chinapay.upop.worker
 * 
 *  File Name       : QuickPaySecondWorker.java
 * 
 *  Creation Date   : 2015年9月25日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 快捷支付[非首次]
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class QuickPaySecondWorker extends AbstractUpopWorker {
	/** 快捷支付[非首次]接口地址 **/
	private String bankQuickPaySecondAddress;

	public QuickPaySecondWorker() {
	}

	public String getBankQuickPaySecondAddress() {
		return bankQuickPaySecondAddress;
	}

	public void setBankQuickPaySecondAddress(String bankQuickPaySecondAddress) {
		this.bankQuickPaySecondAddress = bankQuickPaySecondAddress;
	}

	/**
	 * 请求快捷支付[非首次]
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public ChinapayOrderResultDTO doSecondPayRequest(UpopPaySecondParam params)
			throws Exception {
		// 参数校验
		UpopParamChecker.checkPaySecond(params);

		PrivateKey privateKey = new PrivateKey();

		if (!privateKey.buildKey(mechantNo, 0, privateKeyFilePath)) {
			logger.error("doSecondPayRequest: 中国银联私钥错误! mechantNo=" + mechantNo
					+ " privateKeyFilePath =" + privateKeyFilePath);

			throw new BankAdapterException("非首次快捷支付请求失败，原因：中国银联私钥错误!");
		}

		String merId = mechantNo;// 商户号,必输，数字，定长
		String gateId = ChinapayUpopConst.UPOP_GATE_ID;// 网关号,必输，固定值8619
		String transType = BankTransType.PAY;// 交易类型,必输，固定0001
		String ordId = params.getOrderNo();// 交易订单号,必输，数字，商户系统当0.天唯一
		String transDate = params.getOrderDate();// 订单交易日期,必输，数字，格式：yyyyMMdd
		String curyId = CurrencyEnum.RMB;// 订单交易币种,必输，固定156，人民币
		String subTransType = UpopSubTransType.PAY_SECOND;// 交易子类型,必输，数字，定长(01-银行卡签约;02-短信验证码发送请求;03-签约支付)
		String bindingNo = params.getBindingNo();// 签约号,必输，数字，定长
		String smsCode = params.getSmsCode();// 短信验证码,必输，数字，定长
		String version = ChinapayUpopConst.PAY_SECOND_VERSION;// 支付接入版本号,必输，固定20070129

		BigInteger payAmount = params.getPayAmount();

		// 订单交易金额，单位为分，12位长度，左补0，必填;
		String transAmt = StringUtils.leftPad(payAmount.toString(), 12, '0');

		// 防钓鱼版本签名方法
		// MerId+GateId+TransType+OrdId+TransDate+TransAmt+CuryId+SubTransType+BindingNo+SMS+Version
		String chkSrcStr = StringHelper.joinString(merId, gateId, transType,
				ordId, transDate, transAmt, curyId, subTransType, bindingNo,
				smsCode, version);

		// 签名结果数据
		String chkValue = new SecureLink(privateKey).Sign(chkSrcStr);

		if (logger.isDebugEnabled()) {
			logger.debug("doSecondPayRequest: chinapay chkSrcStr=" + chkSrcStr
					+ ", chkValue=" + chkValue);
		}

		Map<String, String> map = new HashMap<String, String>();

		// 参数信息，同时也是数字签名源数据
		map.put(PaySecondReqKey.MER_ID, merId);// 商户号,必输,数字,定长
		map.put(PaySecondReqKey.GATE_ID, gateId);// 网关号,必输,固定值8619
		map.put(PaySecondReqKey.TRANS_TYPE, transType);// 交易类型,必输,固定0001
		map.put(PaySecondReqKey.ORDER_ID, ordId);// 交易订单号,必输,数字,商户系统当天唯一
		map.put(PaySecondReqKey.TRANS_DATE, transDate);// 订单交易日期,必输,数字,格式：yyyyMMdd
		map.put(PaySecondReqKey.TRANS_AMT, transAmt);// 交易金额,必输,数字,定长,单位为分,不足12位,左补零至12位,例如：000000001234表示12.34元
		map.put(PaySecondReqKey.CURY_ID, curyId);// 订单交易币种,必输,固定156,人民币
		map.put(PaySecondReqKey.SUB_TRANS_TYPE, subTransType);// 交易子类型,必输,数字,定长(01-银行卡签约;02-短信验证码发送请求;03-签约支付)
		map.put(PaySecondReqKey.BINDING_NO, bindingNo);// 签约号,必输,数字,定长
		map.put(PaySecondReqKey.SMS, smsCode);// 短信验证码,必输,数字,定长
		map.put(PaySecondReqKey.VERSION, version);// 支付接入版本号,必输,固定20070129
		map.put(PaySecondReqKey.CHK_VALUE, chkValue);// 签名,必输,定长

		// 银行响应的html
		String returnHtmlContent = ChinapayHttpHelper.sendHttpSSLMsg(
				getBankQuickPaySecondAddress(), map);

		return this.parseResponseResult(returnHtmlContent);
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

		// 支付订单数据准备
		String orderNo = cbParamMap.get(PayCallbackParamKey.ORDER_NO); // 16
		String transAmt = cbParamMap.get(PayCallbackParamKey.AMOUNT); // 12
		String curyId = cbParamMap.get(PayCallbackParamKey.CURRENCY_CODE);// 3
		String strTransDate = cbParamMap.get(PayCallbackParamKey.TRANS_DATE);// 8
		String transType = cbParamMap.get(PayCallbackParamKey.TRANS_TYPE);// 4
		String status = cbParamMap.get(PayCallbackParamKey.STATUS); // 4，
																	// status表示交易状态只有"1001"表示支付成功，其他状态均表示未成功的交易。因此验证签名数据通过后，还需要判定交易状态代码是否为"1001"

		Date transDate = DateUtils.parse2yyyyMMddDate(strTransDate);

		ChinapayOrderResultDTO returnValue = new ChinapayOrderResultDTO(
				orderNo, ChinapayOrderStatus.PAY_FAILED);
		returnValue.setTranTime(transDate);
		returnValue.setCrrrency(curyId);
		returnValue.setPayAmount(transAmt);
		returnValue.setTransType(transType);

		// 1001消费交易成功
		if (UpopTransStatus.TRANS_SUCCESS.equals(status)) {
			// 消费交易
			if (BankTransType.PAY.equals(transType)) {
				// 1001表示支付成功，之后为商户系统对成功订单的逻辑处理
				// 注意：如果您在提交时同时填写了页面返回地址和后台返回地址，且地址相同，请先做一次数据库查询判断订单状态，以防止重复处理该笔订单
				returnValue.setOrderStatus(ChinapayOrderStatus.PAY_SUCCESS);
				returnValue.setBankRespCode(UpopRespCode.OPT_SUCCESS.getCode());
			}
		}
		// 其他均为失败
		else {
			String bankRespCode = adjustBankRespCode(status);
			String bankRespMsg = assembleErrorMsg("快捷支付失败，原因：", bankRespCode);

			returnValue.setBankRespCode(bankRespCode);
			returnValue.setErrorInfo(bankRespMsg);

			logger.info("parseResultFromBankCallback: 非首次快捷支付失败，银行响应的交易状态status=" + status + ", 错误提示："
					+ bankRespMsg);
		}

		return returnValue;
	}

	/**
	 * 解析响应结果
	 * 
	 * @param returnHtmlContent
	 * @return
	 */
	private ChinapayOrderResultDTO parseResponseResult(String returnHtmlContent) {
		// 将响应数据转换为map结构
		Map<String, String> responseDatas = ChinapayHttpHelper
				.parseReturnStr2Map(returnHtmlContent);

		if (null == responseDatas) {
			throw new BankAdapterException("快捷支付操作失败，原因：中国银联返回报文格式异常，无法解析!");
		}

		String respCode = responseDatas.get(PaySecondRespKey.RESP_CODE);

		// 响应码如果不是000就是发送失败
		if (!UpopRespCode.OPT_SUCCESS.valueEquals(respCode)) {
			super.throwUpopBankErrorException("快捷支付失败，原因：", respCode);
		}

		// 对响应报文进行验签
		this.verifyRespAuthToken(responseDatas);

		String transType = responseDatas.get(PaySecondRespKey.TRANS_TYPE);
		String ordId = responseDatas.get(PaySecondRespKey.ORDER_ID);
		String transDate = responseDatas.get(PaySecondRespKey.TRANS_DATE);
		String transAmt = responseDatas.get(PaySecondRespKey.TRANS_AMT);
		String curyId = responseDatas.get(PaySecondRespKey.CURY_ID);
		String transStatus = responseDatas.get(PaySecondRespKey.TRANS_STAUT);
		String bankRespCode = adjustBankRespCode(respCode); // 银行错误响应码, 000-成功, 其他均为失败

		ChinapayOrderResultDTO dto = new ChinapayOrderResultDTO();
		dto.setBankRespCode(bankRespCode);
		dto.setCrrrency(MoneyAmountHelper.formatRMB2CNY(curyId));
		dto.setTranTime(DateUtils.parse2yyyyMMddDate(transDate));
		dto.setBankRespCode(respCode);
		dto.setTransType(transType);
		dto.setPayAmount(transAmt);
		dto.setOrderNo(ordId);

		if (UpopTransStatus.TRANS_SUCCESS.equals(transStatus)) {
			dto.setOrderStatus(ChinapayOrderStatus.PAY_SUCCESS);
		} else if (UpopTransStatus.TRANS_UNKOWN.equals(transStatus)) {
			dto.setOrderStatus(ChinapayOrderStatus.UNKNOWN);
			dto.setErrorInfo("快捷支付请求提交成功，请5分钟后查询最终支付结果。");
			dto.setBankRespCode(null);

			logger.info("parseResponseResult: 非首次快捷支付操作成功，但订单状态未知，请发起查询命令进行查询。 ordId="
					+ ordId + ", transStatus=" + transStatus);
		} else {
			String bankRespMsg = assembleErrorMsg("快捷支付失败，原因：", bankRespCode);
			
			dto.setErrorInfo(bankRespMsg);
			dto.setOrderStatus(ChinapayOrderStatus.PAY_FAILED);

			logger.info("parseResponseResult: 非首次快捷支付失败，银行响应的交易状态TransStaut="
					+ transStatus + ", 错误提示：" + bankRespMsg);
		}

		return dto;
	}

	/**
	 * 对响应报文进行验签
	 * 
	 * @param responseDatas
	 * @return
	 */
	private boolean verifyRespAuthToken(Map<String, String> responseDatas) {
		// 创建公钥对象，用其验证银联的签名信息
		PrivateKey publicKey = new PrivateKey();

		if (!publicKey.buildKey(ChinapayUpopConst.PUB_MERID, 0, bankPubKeyPath)) {
			logger.error("verifyRespAuthToken: 中国银联公钥错误! mechantNo="
					+ mechantNo + " bankPubKeyPath =" + bankPubKeyPath);

			throw new BankAdapterException("验证非首次支付同步响应签名失败，原因：中国银联公钥错误!");
		}

		String merId = responseDatas.get(PaySecondRespKey.MER_ID);
		String gateId = responseDatas.get(PaySecondRespKey.GATE_ID);
		String transType = responseDatas.get(PaySecondRespKey.TRANS_TYPE);
		String ordId = responseDatas.get(PaySecondRespKey.ORDER_ID);
		String transDate = responseDatas.get(PaySecondRespKey.TRANS_DATE);
		String transAmt = responseDatas.get(PaySecondRespKey.TRANS_AMT);
		String curyId = responseDatas.get(PaySecondRespKey.CURY_ID);
		String subTransType = responseDatas
				.get(PaySecondRespKey.SUB_TRANS_TYPE);
		String respCode = responseDatas.get(PaySecondRespKey.RESP_CODE);
		String transStatus = responseDatas.get(PaySecondRespKey.TRANS_STAUT);

		String chkValue = responseDatas.get(PaySecondRespKey.CHK_VALUE);

		// 防钓鱼版本签名方法
		// MerId+GateId+ TransType + OrdId + TransDate + TransAmt + CuryId+
		// SubTransType+ RespCode +TransStaut
		String plainData = StringHelper.joinString(merId, gateId, transType,
				ordId, transDate, transAmt, curyId, subTransType, respCode,
				transStatus);

		// 验证签名
		boolean isVerifySignSuccess = new SecureLink(publicKey)
				.verifyAuthToken(plainData, chkValue);

		if (!isVerifySignSuccess) {
			logger.info("verifyRespAuthToken: 对银行响应报文验证签名失败! src=" + plainData);

			throw new BankAdapterException("验证非首次支付同步响应签名失败，原因：验证银联签名失败!");
		}

		return true;
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
		// 支付订单数据准备
		String merId = cbParamMap.get(PayCallbackParamKey.MER_ID); // 15
		String ordId = cbParamMap.get(PayCallbackParamKey.ORDER_NO); // 16
		String transAmt = cbParamMap.get(PayCallbackParamKey.AMOUNT);// 12
		String curyId = cbParamMap.get(PayCallbackParamKey.CURRENCY_CODE);// 3
		String transDate = cbParamMap.get(PayCallbackParamKey.TRANS_DATE);// 8
		String transType = cbParamMap.get(PayCallbackParamKey.TRANS_TYPE);// 4
		String status = cbParamMap.get(PayCallbackParamKey.STATUS); // status表示交易状态只有"1001"表示支付成功，其他状态均表示未成功的交易。因此验证签名数据通过后，还需要判定交易状态代码是否为"1001"
		String chkValue = cbParamMap.get(PayCallbackParamKey.CHECK_VALUE);

		// 创建公钥对象，用其验证银联的签名信息
		PrivateKey publicKey = new PrivateKey();

		if (!publicKey.buildKey(ChinapayUpopConst.PUB_MERID, 0, bankPubKeyPath)) {
			logger.error("verifySignatureOfCallBack: 中国银联公钥错误! mechantNo="
					+ mechantNo + " bankPubKeyPath =" + privateKeyFilePath);

			throw new BankAdapterException("处理中国银联快捷支付回调时验证签名失败，原因：中国银联公钥错误!");
		}

		// 验证签名
		boolean isVerifySuccess = new SecureLink(publicKey)
				.verifyTransResponse(merId, ordId, transAmt, curyId, transDate,
						transType, status, chkValue);

		if (!isVerifySuccess) {
			logger.info("verifySignatureOfCallBack: 中国银联UPOP非首次快捷支付回调时, 验证签名失败!!  callback data:"
					+ JSON.toJSONString(cbParamMap));

			throw new BankAdapterException("中国银联UPOP非首次快捷支付回调时, 验证签名失败!");
		}

		return isVerifySuccess;
	}
}