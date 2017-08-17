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

import com.gy.hsxt.pg.bankadapter.chinapay.b2c.common.ChinapayB2cConst.BankTransType;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.common.ChinapayB2cConst.CurrencyEnum;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.common.ChinapayHttpHelper;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.abstracts.AbstractUpopWorker;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.common.ChinapayUpopConst;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.common.ChinapayUpopConst.UpopRespCode;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.common.ChinapayUpopConst.UpopSubTransType;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.common.ChinapayUpopFieldsKey.SmsCodeReqKey;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.common.ChinapayUpopFieldsKey.SmsCodeRespKey;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.common.UpopParamChecker;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.params.UpopSmsCodeParam;
import com.gy.hsxt.pg.bankadapter.common.exception.BankAdapterException;
import com.gy.hsxt.pg.bankadapter.common.utils.StringHelper;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.chinapay.upop.worker
 * 
 *  File Name       : SmsCodeRequestWorker.java
 * 
 *  Creation Date   : 2015年9月25日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 快捷支付短信验证码发送
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class SmsCodeRequestWorker extends AbstractUpopWorker {
	/** 快捷支付短信验证码发送接口地址 **/
	private String bankSMSCodeAddress;

	public SmsCodeRequestWorker() {
	}

	public String getBankSMSCodeAddress() {
		return bankSMSCodeAddress;
	}

	public void setBankSMSCodeAddress(String bankSMSCodeAddress) {
		this.bankSMSCodeAddress = bankSMSCodeAddress;
	}

	/**
	 * 发送短信动态验证码
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public boolean doSendSmsCode(UpopSmsCodeParam param) throws Exception {
		// 参数校验
		UpopParamChecker.checkSendSmsCode(param);

		String merId = mechantNo;// 商户号,必输，数字，定长
		String gateId = ChinapayUpopConst.UPOP_GATE_ID;// 网关号,必输，固定值8619
		String transType = BankTransType.PAY;// 交易类型,必输，固定0001
		String ordId = param.getOrderNo();// 交易订单号,必输，数字，定长，商户系统当天唯一
		String transDate = param.getOrderDate();// 订单交易日期,必输，数字，格式：yyyyMMdd
		String curyId = CurrencyEnum.RMB;// 订单交易币种,必输，固定156，人民币
		String version = ChinapayUpopConst.SMS_CODE_VERSION;// 支付接入版本号,必输，固定20070129
		String pageRetUrl = param.getJumpURL();// 前台交易接收URL,长度不要超过80个字节，可与BgRetUrl保持一致
		String bgRetUrl = param.getNotifyURL();// 后台交易接收URL,长度不要超过80个字节

		// 商户私有域, 必输, 长度不要超过60个字节, 格式为：交易子类型|签约号
		String priv1 = UpopSubTransType.SMS_CODE_SEND + "|"
				+ param.getBindingNo();

		BigInteger payAmount = param.getPayAmount();

		// 订单交易金额,必输，数字，定长，单位为分，不足12位，左补零至12位,例如：000000001234表示12.34元
		String transAmt = StringUtils.leftPad(payAmount.toString(), 12, '0');

		PrivateKey privateKey = new PrivateKey();

		if (!privateKey.buildKey(mechantNo, 0, privateKeyFilePath)) {
			logger.error("doSendSmsCode: 中国银联私钥错误! mechantNo=" + mechantNo
					+ " privateKeyFilePath =" + privateKeyFilePath);

			throw new BankAdapterException("快捷支付发送短信验证码失败，原因：中国银联私钥错误!");
		}

		// 防钓鱼版本签名方法
		// MerId+OrdId+TransAmt+CuryId+TransDate+TransType+Priv1
		String chkSrcStr = StringHelper.joinString(merId, ordId, transAmt,
				curyId, transDate, transType, priv1);

		// 签名结果数据
		String chkValue = new SecureLink(privateKey).Sign(chkSrcStr);

		Map<String, String> map = new HashMap<String, String>();

		// 参数信息，同时也是数字签名源数据
		map.put(SmsCodeReqKey.MER_ID, merId);// 商户号,必输，数字，定长
		map.put(SmsCodeReqKey.GATE_ID, gateId);// 网关号,必输，固定值8619
		map.put(SmsCodeReqKey.TRANS_TYPE, transType);// 交易类型,必输，固定0001
		map.put(SmsCodeReqKey.ORDER_ID, ordId);// 交易订单号,必输，数字，定长，商户系统当天唯一
		map.put(SmsCodeReqKey.TRANS_DATE, transDate);// 订单交易日期,必输，数字，格式：yyyyMMdd
		map.put(SmsCodeReqKey.TRANS_AMT, transAmt);// 交易金额,必输，数字，定长，单位为分，不足12位，左补零至12位,例如：000000001234表示12.34元
		map.put(SmsCodeReqKey.CURY_ID, curyId);// 订单交易币种,必输，固定156，人民币
		map.put(SmsCodeReqKey.VERSION, version);// 支付接入版本号,必输，固定20070129
		map.put(SmsCodeReqKey.PAGE_RET_URL, pageRetUrl);// 前台交易接收URL,长度不要超过80个字节，可与BgRetUrl保持一致
		map.put(SmsCodeReqKey.BG_RET_URL, bgRetUrl);// 后台交易接收URL,长度不要超过80个字节
		map.put(SmsCodeReqKey.PRIV1, priv1);// 商户私有域,必输，长度不要超过60个字节,格式为：交易子类型|签约号
		map.put(SmsCodeReqKey.CHK_VALUE, chkValue);// 签名,必输，定长

		// 银行响应的html
		String returnHtmlContent = ChinapayHttpHelper.sendHttpSSLMsg(
				getBankSMSCodeAddress(), map);

		return this.parseResponseResult(returnHtmlContent);
	}

	/**
	 * 解析响应报文
	 * 
	 * @param returnHtmlContent
	 * @return
	 */
	private boolean parseResponseResult(String returnHtmlContent) {
		// 将响应数据转换为map结构
		Map<String, String> responseDatas = ChinapayHttpHelper
				.parseReturnStr2Map(returnHtmlContent);

		if (null == responseDatas) {
			throw new BankAdapterException("操作失败，原因：中国银联返回报文格式异常，无法解析!");
		}

		String respCode = responseDatas.get(SmsCodeRespKey.RESP_CODE);

		// 响应码如果不是000就是发送失败
		if (!UpopRespCode.OPT_SUCCESS.valueEquals(respCode)) {
			logger.error("parseResponseResult: 短信验证码发送失败! mechantNo="
					+ mechantNo + " respCode =" + respCode + ", OrdId="
					+ responseDatas.get(SmsCodeRespKey.ORDER_ID));

			super.throwUpopBankErrorException("短信验证码发送失败，原因：", respCode);
		}

		return this.verifyAuthToken(responseDatas);
	}

	/**
	 * 对响应报文进行验签
	 * 
	 * @param responseDatas
	 * @return
	 */
	private boolean verifyAuthToken(Map<String, String> responseDatas) {
		// 创建公钥对象，用其验证银联的签名信息
		PrivateKey publicKey = new PrivateKey();

		if (!publicKey.buildKey(ChinapayUpopConst.PUB_MERID, 0, bankPubKeyPath)) {
			logger.error("verifyAuthToken: 中国银联公钥错误! mechantNo=" + mechantNo
					+ " bankPubKeyPath =" + bankPubKeyPath);

			throw new BankAdapterException("验证短信验证码发送响应签名失败，原因：中国银联公钥错误!");
		}

		String merId = responseDatas.get(SmsCodeRespKey.MER_ID);
		String gateId = responseDatas.get(SmsCodeRespKey.GATE_ID);
		String transType = responseDatas.get(SmsCodeRespKey.TRANS_TYPE);
		String ordId = responseDatas.get(SmsCodeRespKey.ORDER_ID);
		String transDate = responseDatas.get(SmsCodeRespKey.TRANS_DATE);
		String subTransType = responseDatas.get(SmsCodeRespKey.SUB_TRANS_TYPE);
		String respCode = responseDatas.get(SmsCodeRespKey.RESP_CODE);
		String chkValue = responseDatas.get(SmsCodeRespKey.CHK_VALUE);

		// 防钓鱼版本签名方法
		// MerId+GateId+TransType+OrdId+TransDate+SubTransType+RespCode
		String plainData = StringHelper.joinString(merId, gateId, transType,
				ordId, transDate, subTransType, respCode);

		// 验证签名
		boolean isVerifySignSuccess = new SecureLink(publicKey)
				.verifyAuthToken(plainData, chkValue);

		if (!isVerifySignSuccess) {
			logger.info("verifyAuthToken: 对银行响应报文验证签名失败! src=" + plainData);

			throw new BankAdapterException("验证短信验证码发送响应签名失败，原因：验证银联签名失败!");
		}

		return true;
	}
}
