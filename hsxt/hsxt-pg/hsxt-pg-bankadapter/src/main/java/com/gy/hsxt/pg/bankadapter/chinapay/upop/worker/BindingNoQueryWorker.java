/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.chinapay.upop.worker;

import java.util.HashMap;
import java.util.Map;

import chinapay.PrivateKey;
import chinapay.SecureLink;

import com.gy.hsxt.pg.bankadapter.chinapay.b2c.common.ChinapayHttpHelper;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.abstracts.AbstractUpopWorker;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.common.ChinapayUpopConst;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.common.ChinapayUpopConst.UpopCardBindingStat;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.common.ChinapayUpopConst.UpopRespCode;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.common.ChinapayUpopFieldsKey.BindingNoQryReqKey;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.common.ChinapayUpopFieldsKey.BindingNoQryRespKey;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.common.UpopParamChecker;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.params.UpopBindingNoResult;
import com.gy.hsxt.pg.bankadapter.common.exception.BankAdapterException;
import com.gy.hsxt.pg.bankadapter.common.utils.StringHelper;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.chinapay.upop.worker
 * 
 *  File Name       : BindingNoQueryWorker.java
 * 
 *  Creation Date   : 2015年9月25日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 快捷支付签约号查询
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class BindingNoQueryWorker extends AbstractUpopWorker {
	/** 快捷支付签约号查询接口地址 **/
	private String bankQryBindingNoAddress;
	
	/** 是否生产环境 **/
	private boolean isProductionEnv = false;

	public BindingNoQueryWorker() {
	}

	public String getBankQryBindingNoAddress() {
		return bankQryBindingNoAddress;
	}

	public void setBankQryBindingNoAddress(String bankQryBindingNoAddress) {
		this.bankQryBindingNoAddress = bankQryBindingNoAddress;
	}

	public boolean isProductionEnv() {
		return isProductionEnv;
	}

	public void setProductionEnv(boolean isProductionEnv) {
		this.isProductionEnv = isProductionEnv;
	}

	/**
	 * 查询快捷支付签约号
	 * 
	 * @param cardNo
	 *            卡号(必输，数字)
	 * @param bankId
	 *            银行代码(必输，参照银行名称-简码对照表)
	 * @param cardType
	 *            借贷记标识(必输，数字，定长[01-借记卡,02-贷记卡])
	 * @return
	 * @throws Exception
	 */
	public UpopBindingNoResult doQueryBindingNo(String cardNo, String bankId,
			String cardType) throws Exception {
		String _cardType = StringHelper.leftPad(cardType, 2, '0');
		
		// 参数校验
		UpopParamChecker.checkQryBindingNo(cardNo, bankId, _cardType);
		
		String merId = mechantNo;// 商户号,必输，数字，定长
		String gateId = ChinapayUpopConst.UPOP_GATE_ID;// 网关号,必输，固定值：8619

		PrivateKey privateKey = new PrivateKey();

		if (!privateKey.buildKey(mechantNo, 0, privateKeyFilePath)) {
			logger.error("doQueryBindingNo: 中国银联私钥错误! mechantNo=" + mechantNo
					+ " privateKeyFilePath =" + privateKeyFilePath);

			throw new BankAdapterException("快捷支付发送短信验证码失败，原因：中国银联私钥错误!");
		}
		
		// 对于测试环境使用挡板程序(摈弃这个做法, marked by: zhangysh  date: 2016-05-26)
		// if (!isProductionEnv()) {
		// return this.createBaffleForTest(merId, gateId, cardNo);
		// }

		// 防钓鱼版本签名方法
		// MerId+GateId+CardNo+BankId+CardType
		String chkSrcStr = StringHelper.joinString(merId, gateId, cardNo,
				bankId, _cardType);

		// 签名结果数据
		String chkValue = new SecureLink(privateKey).Sign(chkSrcStr);

		Map<String, String> map = new HashMap<String, String>();

		// 参数信息，同时也是数字签名源数据
		map.put(BindingNoQryReqKey.MER_ID, merId);// 商户号,必输，数字，定长
		map.put(BindingNoQryReqKey.GATE_ID, gateId);// 网关号,必输，固定值：8619
		map.put(BindingNoQryReqKey.CARD_NO, cardNo);// 卡号,必输，数字
		map.put(BindingNoQryReqKey.BANK_ID, bankId);// 银行代码,必输，参照银行名称-简码对照表
		map.put(BindingNoQryReqKey.CARD_TYPE, _cardType);// 借贷记标识,必输，数字，定长(01-借记卡;02-贷记卡;)
		map.put(BindingNoQryReqKey.CHK_VALUE, chkValue);// 签名, 必输, 定长

		// 银行响应的html
		String returnHtmlContent = ChinapayHttpHelper.sendHttpSSLMsg(
				getBankQryBindingNoAddress(), map);

		// 解析响应结果
		return this.parseResponseResult(returnHtmlContent);
	}

	/**
	 * 解析响应报文
	 * 
	 * @param returnHtmlContent
	 * @return
	 */
	private UpopBindingNoResult parseResponseResult(String returnHtmlContent) {
		// 将响应数据转换为map结构
		Map<String, String> map = ChinapayHttpHelper
				.parseReturnStr2Map(returnHtmlContent);

		if (null == map) {
			throw new BankAdapterException("操作失败，原因：中国银联返回报文格式异常，无法解析!");
		}

		String respCode = map.get(BindingNoQryRespKey.RESP_CODE);

		// 响应码如果不是000就是发送失败
		if (!UpopRespCode.OPT_SUCCESS.valueEquals(respCode)) {
			super.throwUpopBankErrorException("查询快捷支付签约号失败，原因：", respCode);
		}

		String merId = map.get(BindingNoQryRespKey.MER_ID);
		String gateId = map.get(BindingNoQryRespKey.GATE_ID);
		String stat = map.get(BindingNoQryRespKey.STAT);
		String cardNo = map.get(BindingNoQryRespKey.CARD_NO);
		String bindingNo = map.get(BindingNoQryRespKey.BINDING_NO);
		String expiry = map.get(BindingNoQryRespKey.EXPIRY);
		String transLimit = map.get(BindingNoQryRespKey.TRANS_LIMIT);
		String sumLimit = map.get(BindingNoQryRespKey.SUM_LIMIT);
		String issuerCode = map.get(BindingNoQryRespKey.ISSUER_CODE);
		String chkValue = map.get(BindingNoQryRespKey.CHK_VALUE);

		// 封装返回对象
		UpopBindingNoResult result = new UpopBindingNoResult();
		result.setMerId(merId);
		result.setGateId(gateId);
		result.setStat(stat);
		result.setCardNo(cardNo);
		result.setBindingNo(bindingNo);
		result.setExpiry(expiry);
		result.setTransLimit(transLimit);
		result.setSumLimit(sumLimit);
		result.setRespCode(respCode);
		result.setIssuerCode(issuerCode);
		result.setChkValue(chkValue);

		// 验签(由于生产环境签名验证失败, 还没有查到原因, 为了不影响上线, 暂时不验签)
		this.verifyAuthToken(result);

		return result;
	}

	/**
	 * 对响应报文进行验签
	 * 
	 * @param resp
	 * @return
	 */
	private boolean verifyAuthToken(UpopBindingNoResult resp) {
		// 创建公钥对象，用其验证银联的签名信息
		PrivateKey publicKey = new PrivateKey();

		if (!publicKey.buildKey(ChinapayUpopConst.PUB_MERID, 0, bankPubKeyPath)) {
			logger.error("verifyAuthToken: 中国银联公钥错误! mechantNo=" + mechantNo
					+ " bankPubKeyPath =" + bankPubKeyPath);

			throw new BankAdapterException("验证短信验证码发送响应签名失败，原因：中国银联公钥错误!");
		}

		String merId = resp.getMerId();
		String gateId = resp.getGateId();
		String respCode = resp.getRespCode();
		String stat = resp.getStat();
		String cardNo = resp.getCardNo();
		String bindingNo = resp.getBindingNo();
		String expiry = resp.getExpiry();
		String sumLimit = resp.getSumLimitStrValue();
		String transLimit = resp.getTransLimitStrValue();
		String issuerCode = resp.getIssuerCode();

		// 防钓鱼版本签名方法(特别说明：银联的接口文档中漏写了一个字段IssuerCode, 已经反馈给我银联, marked by: zhangysh)
		// MerId+GateId+RespCode+Stat+CardNo+BindingNo+expiry+sumLimit+transLimit+IssuerCode
		String plainData = StringHelper.joinString(merId, gateId, respCode,
				stat, cardNo, bindingNo, expiry, sumLimit, transLimit,
				issuerCode);

		// 验证签名
		boolean isVerifySignSuccess = new SecureLink(publicKey)
				.verifyAuthToken(plainData, resp.getChkValue());

		if (!isVerifySignSuccess) {
			logger.info("verifyAuthToken: 对银行响应报文验证签名失败! src=" + plainData);

			throw new BankAdapterException("验证'快捷支付签约号查询'响应签名失败，原因：验证银联签名失败!");
		}

		return true;
	}
	
	/**
	 * --------------------仅用于测试---------------<br>
	 * 
	 * 由于银联测试环境bug原因, 无法进行签约号的查询, <br>
	 * 所以对于测试环境编写了一个挡板程序, 使用测试账号的固定的签约号<br>
	 * 
	 * @param merId
	 * @param gateId
	 * @param cardNo
	 * @return
	 */
	@SuppressWarnings("unused")
	private UpopBindingNoResult createBaffleForTest(String merId,
			String gateId, String cardNo) {
		// 测试用的签约号, 对应的银行卡号是：6221558812340000
		String testBindingNo = "201503050109140001010000";
		
		UpopBindingNoResult bindingNo = new UpopBindingNoResult();
		bindingNo.setRespCode(UpopRespCode.OPT_SUCCESS.getCode());
		bindingNo.setBindingNo(testBindingNo);
		bindingNo.setStat(UpopCardBindingStat.SUCCESS);
		bindingNo.setMerId(merId);
		bindingNo.setGateId(gateId);
		bindingNo.setCardNo(cardNo);

		return bindingNo;
	}
}