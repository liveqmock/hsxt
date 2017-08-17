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
import com.gy.hsxt.pg.bankadapter.chinapay.upop.common.UpopParamChecker;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.common.ChinapayUpopConst.UpopRespCode;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.common.ChinapayUpopConst.UpopSubTransType;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.common.ChinapayUpopConst.UpopTransStatus;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.common.ChinapayUpopFieldsKey.PayFirstReqParamKey;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.params.UpopPayFirstParam;
import com.gy.hsxt.pg.bankadapter.common.exception.BankAdapterException;
import com.gy.hsxt.pg.bankadapter.common.utils.DateUtils;
import com.gy.hsxt.pg.bankadapter.common.utils.StringHelper;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.chinapay.upop.worker
 * 
 *  File Name       : QuickPayFirstWorker.java
 * 
 *  Creation Date   : 2015年9月25日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 快捷支付[首次]
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class QuickPayFirstWorker extends AbstractUpopWorker {
	/** 快捷支付[首次]接口地址 **/
	private String bankQuickPayFirstAddress;

	public QuickPayFirstWorker() {
	}

	public String getBankQuickPayFirstAddress() {
		return bankQuickPayFirstAddress;
	}

	public void setBankQuickPayFirstAddress(String bankQuickPayFirstAddress) {
		this.bankQuickPayFirstAddress = bankQuickPayFirstAddress;
	}

	/**
	 * 组装快捷支付[首次]页面请求地址
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String doAssemblePayFirstUrl(UpopPayFirstParam params)
			throws Exception {
		// 参数校验
		UpopParamChecker.checkPayFirst(params);

		String merId = mechantNo; // 银联分配给互生的商户号
		String gateId = ChinapayUpopConst.UPOP_GATE_ID; // 网关号, 必输, 固定值8619
		String transType = BankTransType.PAY; // 交易类型, 必输, 固定0001
		String transDate = params.getOrderDate();
		String ordId = params.getOrderNo();
		String subTransType = UpopSubTransType.PAY_FIRST;// 交易子类型(01：银行卡签约;02：短信验证码发送请求;03：签约支付;04：卡号签约并支付)
		String cardNo = params.getCardNo(); // 卡号
		String cardType = StringHelper.leftPad(params.getCardType(), 2, '0'); // 卡类型：借记卡or贷记卡
		String bankId = params.getBankId();
		String curyId = CurrencyEnum.RMB;
		String version = ChinapayUpopConst.PAY_FIRST_VERSION; // 接口版本号
		String pageRetUrl = params.getJumpURL();
		String bgRetUrl = params.getNotifyURL();
		String priv1 = params.getPriv1(); // 私有域

		BigInteger payAmount = params.getPayAmount();

		// 订单交易金额，单位为分，12位长度，左补0，必填;
		String transAmt = StringUtils.leftPad(payAmount.toString(), 12, '0');

		PrivateKey privateKey = new PrivateKey();

		if (!privateKey.buildKey(mechantNo, 0, privateKeyFilePath)) {
			logger.error("doAssemblePayFirstUrl: 中国银联私钥错误! mechantNo="
					+ mechantNo + " privateKeyFilePath =" + privateKeyFilePath);

			throw new BankAdapterException("组装快捷支付URL失败，原因：中国银联私钥错误!");
		}

		// 防钓鱼版本签名方法
		// MerId+GateId+TransType+TransDate+OrdId+SubTransType+CardNo+CardType
		// +BankId+TransAmt+CuryId+Version+PageRetUrl+BgRetUrl+Priv1
		String chkSrcStr = StringHelper.joinString(merId, gateId, transType,
				transDate, ordId, subTransType, cardNo, cardType, bankId,
				transAmt, curyId, version, pageRetUrl, bgRetUrl, priv1);

		// 签名结果数据
		String chkValue = new SecureLink(privateKey).Sign(chkSrcStr);

		if (logger.isDebugEnabled()) {
			logger.debug("doAssemblePayFirstUrl: chinapay chkSrcStr="
					+ chkSrcStr + ", chkValue=" + chkValue);
		}

		Map<String, String> map = new HashMap<String, String>();

		// 参数信息，同时也是数字签名源数据
		map.put(PayFirstReqParamKey.MER_ID, merId);// 商户号, 必输,数字,定长
		map.put(PayFirstReqParamKey.GATE_ID, gateId);// 网关号, 必输,固定值8619
		map.put(PayFirstReqParamKey.TRANS_TYPE, transType);// 交易类型, 必输,固定0001
		map.put(PayFirstReqParamKey.TRANS_DATE, transDate);// 交易日期,
															// 必输,数字,格式：yyyyMMdd
		map.put(PayFirstReqParamKey.ORDER_ID, ordId);// 订单号, 必输,数字,定长,商户系统当天唯一
		map.put(PayFirstReqParamKey.SUB_TRANS_TYPE, subTransType);// 交易子类型,
																	// 必输,数字,定长,(01：银行卡签约;02：短信验证码发送请求;03：签约支付;04：卡号签约并支付)
		map.put(PayFirstReqParamKey.CARD_NO, cardNo);// 银行卡号, 必输,数字
		map.put(PayFirstReqParamKey.CARD_TYPE, cardType);// 银行卡类型,必输,数字,定长,(01：借记卡;02：贷记卡)
		map.put(PayFirstReqParamKey.BANK_ID, bankId);// 银行id,必输,参照银行名称-简码对照表
		map.put(PayFirstReqParamKey.TRANS_AMT, transAmt);// 交易金额,必输,数字,定长,单位为分,不足12位,左补零至12位,例如：000000001234表示12.34元
		map.put(PayFirstReqParamKey.CURY_ID, curyId);// 币种,必输,固定156,人民币
		map.put(PayFirstReqParamKey.VERSION, version);// 接口版本,必输,固定20070129
		map.put(PayFirstReqParamKey.PAGE_RET_URL, pageRetUrl);// 页面跳转地址,长度不要超过80个字节,可与BgRetUrl保持一致
		map.put(PayFirstReqParamKey.BG_RET_URL, bgRetUrl);// 后台回调地址,长度不要超过80个字节
		map.put(PayFirstReqParamKey.PRIV1, priv1);// 私有域,长度不要超过60个字节
		map.put(PayFirstReqParamKey.CHK_VALUE, chkValue);// 签名, 必输,定长

		return ChinapayHttpHelper.assembleBankUrl(
				getBankQuickPayFirstAddress(), map);
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
		String transDate = cbParamMap.get(PayCallbackParamKey.TRANS_DATE);// 8
		String transType = cbParamMap.get(PayCallbackParamKey.TRANS_TYPE);// 4
		String status = cbParamMap.get(PayCallbackParamKey.STATUS); // status表示交易状态只有"1001"表示支付成功，其他状态均表示未成功的交易。因此验证签名数据通过后，还需要判定交易状态代码是否为"1001"
		String priv1 = cbParamMap.get(PayCallbackParamKey.PRIV1);//

		ChinapayOrderResultDTO returnValue = new ChinapayOrderResultDTO(
				orderNo, ChinapayOrderStatus.PAY_FAILED);
		returnValue.setTranTime(DateUtils.parse2yyyyMMddDate(transDate));
		returnValue.setCrrrency(curyId);
		returnValue.setPayAmount(transAmt);
		returnValue.setTransType(transType);
		returnValue.setPriv1(priv1);

		// 1001消费交易成功
		if (UpopTransStatus.TRANS_SUCCESS.equals(status)) {
			// 消费交易
			if (BankTransType.PAY.equals(transType)) {
				// 1001表示支付成功，之后为商户系统对成功订单的逻辑处理
				// 注意：如果您在提交时同时填写了页面返回地址和后台返回地址，且地址相同，请先做一次数据库查询判断订单状态，以防止重复处理该笔订单
				returnValue.setOrderStatus(ChinapayOrderStatus.PAY_SUCCESS);
				returnValue.setBankRespCode(UpopRespCode.OPT_SUCCESS.getCode());
			}
		} else if (UpopTransStatus.TRANS_UNKOWN.equals(status)) {
			returnValue.setOrderStatus(ChinapayOrderStatus.UNKNOWN);
			returnValue.setErrorInfo("快捷支付请求提交成功，请5分钟后查询最终支付结果。");

			logger.info("parseResultFromBankCallback: 非首次快捷支付操作成功，但订单状态未知，请发起查询命令进行查询。 orderNo="
					+ orderNo + ", status=" + status);
		}
		// 其他均为失败
		else {
			logger.info("parseResultFromBankCallback: 快捷支付失败, 返回的状态码为 status="
					+ status);

			String bankRespCode = adjustBankRespCode(status);
			String bankRespMsg = assembleErrorMsg("快捷支付失败，原因：", bankRespCode);

			returnValue.setBankRespCode(bankRespCode);
			returnValue.setErrorInfo(bankRespMsg);
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
		// 创建公钥对象，用其验证银联的签名信息
		PrivateKey publicKey = new PrivateKey();

		if (!publicKey.buildKey(ChinapayUpopConst.PUB_MERID, 0, bankPubKeyPath)) {
			logger.error("verifySignatureOfCallBack: 中国银联公钥错误! mechantNo="
					+ mechantNo + " bankPubKeyPath =" + privateKeyFilePath);

			throw new BankAdapterException("处理中国银联快捷支付回调时验证签名失败，原因：中国银联公钥错误!");
		}

		// 支付订单数据准备
		String merId = cbParamMap.get(PayCallbackParamKey.MER_ID); // 15
		String ordId = cbParamMap.get(PayCallbackParamKey.ORDER_NO); // 16
		String transAmt = cbParamMap.get(PayCallbackParamKey.AMOUNT);// 12
		String curyId = cbParamMap.get(PayCallbackParamKey.CURRENCY_CODE);// 3
		String transDate = cbParamMap.get(PayCallbackParamKey.TRANS_DATE);// 8
		String transType = cbParamMap.get(PayCallbackParamKey.TRANS_TYPE);// 4
		String status = cbParamMap.get(PayCallbackParamKey.STATUS); // status表示交易状态只有"1001"表示支付成功，其他状态均表示未成功的交易。因此验证签名数据通过后，还需要判定交易状态代码是否为"1001"
		String chkValue = cbParamMap.get(PayCallbackParamKey.CHECK_VALUE);

		// 验证签名
		boolean isVerifySuccess = new SecureLink(publicKey)
				.verifyTransResponse(merId, ordId, transAmt, curyId, transDate,
						transType, status, chkValue);

		if (!isVerifySuccess) {
			logger.info("verifySignatureOfCallBack: 中国银联UPOP首次快捷支付回调时, 验证签名失败!!  callback data:"
					+ JSON.toJSONString(cbParamMap));
			
			throw new BankAdapterException("中国银联UPOP首次快捷支付回调时, 验证签名失败!");
		}

		return isVerifySuccess;
	}
}
