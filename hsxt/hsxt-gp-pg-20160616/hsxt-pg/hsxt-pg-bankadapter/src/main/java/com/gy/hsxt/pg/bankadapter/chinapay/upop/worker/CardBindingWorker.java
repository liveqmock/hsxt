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

import com.gy.hsxt.pg.bankadapter.chinapay.b2c.common.ChinapayB2cConst.BankTransType;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.common.ChinapayHttpHelper;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.abstracts.AbstractUpopWorker;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.common.ChinapayUpopConst;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.common.ChinapayUpopConst.UpopSubTransType;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.common.ChinapayUpopFieldsKey.CardBindingCbParamKey;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.common.ChinapayUpopFieldsKey.CardBindingReqParamKey;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.common.UpopParamChecker;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.params.UpopCardBindingParam;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.params.UpopBindingNoResult;
import com.gy.hsxt.pg.bankadapter.common.exception.BankAdapterException;
import com.gy.hsxt.pg.bankadapter.common.utils.HttpClientHelper;
import com.gy.hsxt.pg.bankadapter.common.utils.StringHelper;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.chinapay.upop.worker
 * 
 *  File Name       : CardBindingWorker.java
 * 
 *  Creation Date   : 2015年10月15日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 快捷支付银行卡签约
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class CardBindingWorker extends AbstractUpopWorker {
	
	/** 银行卡签约请求接口地址 **/
	private String bankCardBindingAddress;

	public CardBindingWorker() {
	}
	
	public String getBankCardBindingAddress() {
		return bankCardBindingAddress;
	}

	public void setBankCardBindingAddress(String bankCardBindingAddress) {
		this.bankCardBindingAddress = bankCardBindingAddress;
	}
	
	/**
	 * 发送独立签约请求
	 * 
	 * @param params
	 * @return
	 */
	public boolean doBankCardBindingRequest(UpopCardBindingParam params) {

		String respString = "";

		try {
			String url = doAssembleCardBindingUrl(params);

			respString = HttpClientHelper.doGet(url, "");
		} catch (Exception e) {
			logger.info("", e);
		}

		return !StringHelper.isEmpty(respString);
	}

	/**
	 * 发起银行卡签约请求, 返回的是html脚本, 这个脚本将会跳转到银联界面进行签约
	 * 
	 * @param params
	 * @return 银联的地址
	 * @throws Exception
	 */
	public String doAssembleCardBindingUrl(UpopCardBindingParam params)
			throws Exception {
		// 参数校验
		UpopParamChecker.checkCardBinding(params);
		
		PrivateKey privateKey = new PrivateKey();
		
		if(!privateKey.buildKey(mechantNo , 0, privateKeyFilePath)) {
			logger.error("doCardBindingRequest: 中国银联私钥错误! mechantNo="
					+ mechantNo + " privateKeyFilePath =" + privateKeyFilePath);

			throw new BankAdapterException("银行卡签约失败，原因：中国银联私钥错误!");
		}
		
		String merId = mechantNo;// 商户号,必输，数字，定长
		String gateId = ChinapayUpopConst.UPOP_GATE_ID;// 网关号,必输，固定值8619
		String transType = BankTransType.PAY;// 交易类型,必输，固定0001
		String subTransType = UpopSubTransType.CARD_BINDIND;// 交易子类型,必输，数字，定长(01-银行卡签约;02-短信验证码发送请求;03-签约支付)
		String transDate = params.getOrderDate();// 订单交易日期,必输，数字，格式：yyyyMMdd
		String cardNo = params.getCardNo(); // 卡号
		String cardType = StringHelper.leftPad(params.getCardType(), 2, '0'); // 卡类型
		String bankId = params.getBankId(); // 银行id
		String bgRetUrl = params.getNotifyURL(); // 后台通知url地址
		
		// 防钓鱼版本签名方法
		// MerId+GateId+TransType+TransDate+SubTransType+CardNo+CardType+BankId+BgRetUrl
		String chkSrcStr = StringHelper.joinString(merId, gateId, transType,
				transDate, subTransType, cardNo, cardType, bankId, bgRetUrl);

		// 签名结果数据
		String chkValue = new SecureLink(privateKey).Sign(chkSrcStr);
		
		if(logger.isDebugEnabled()){
			logger.debug("doCardBindingRequest: chinapay chkSrcStr="
					+ chkSrcStr + ", chkValue=" + chkValue);
		}
		
		Map<String, String> map = new HashMap<String, String>(12);

		// 参数信息，同时也是数字签名源数据
		map.put(CardBindingReqParamKey.MER_ID, merId); // 商户号
		map.put(CardBindingReqParamKey.GATE_ID, gateId); // 网关号
		map.put(CardBindingReqParamKey.TRANS_TYPE, transType); // 交易类型
		map.put(CardBindingReqParamKey.TRANS_DATE, transDate); // 订单交易日期
		map.put(CardBindingReqParamKey.SUB_TRANS_TYPE, subTransType); // 交易子类型
		map.put(CardBindingReqParamKey.CARD_NO, cardNo); // 卡号
		map.put(CardBindingReqParamKey.CARD_TYPE, cardType); // 借贷记标识
		map.put(CardBindingReqParamKey.BANK_ID, bankId); // 银行代码
		map.put(CardBindingReqParamKey.BG_RET_URL, bgRetUrl); // 签约后台应答地址
		map.put(CardBindingReqParamKey.CHK_VALUE, chkValue); // 签名
		
		// 银行响应的html
		return ChinapayHttpHelper.assembleBankUrl(getBankCardBindingAddress(),
				map);
	}

	/**
	 * 处理银联支付银行卡签约回调接口方法
	 * 
	 * @param cbParamMap
	 * @return
	 * @throws Exception
	 */
	public UpopBindingNoResult parseResultFromBankCallback(
			Map<String, String> cbParamMap) throws Exception {
		// 验证回调报文签名
		this.verifySignatureOfCallBack(cbParamMap);
		
		String merId = cbParamMap.get(CardBindingCbParamKey.MER_ID); // 商户号
		String gateId = cbParamMap.get(CardBindingCbParamKey.GATE_ID); // 网关号
		String stat = cbParamMap.get(CardBindingCbParamKey.STAT); // 签约状态
		String cardNo = cbParamMap.get(CardBindingCbParamKey.CARD_NO); // 银行卡号
		String bindingNo = cbParamMap.get(CardBindingCbParamKey.BINDING_NO); // 签约号
		String expiry = cbParamMap.get(CardBindingCbParamKey.EXPIRY); // 小额临时支付有效期
		String sumLimit = cbParamMap.get(CardBindingCbParamKey.SUM_LIMIT); // 小额临时支付总限额
		String transLimit = cbParamMap.get(CardBindingCbParamKey.TRANS_LIMIT); // 小额临时支付单笔限额

		UpopBindingNoResult result = new UpopBindingNoResult();
		result.setCardNo(cardNo);
		result.setBindingNo(bindingNo);
		result.setMerId(merId);
		result.setGateId(gateId);
		result.setStat(stat);
		result.setSumLimit(sumLimit);
		result.setTransLimit(transLimit);
		result.setExpiry(expiry);

		return result;
	}

	/**
	 * 处理银联支付回调接口方法
	 * 
	 * @param cbParamMap
	 * @return
	 * @throws Exception
	 */
	private boolean verifySignatureOfCallBack(Map<String, String> cbParamMap) throws Exception {
		String merId = cbParamMap.get(CardBindingCbParamKey.MER_ID); // 商户号
		String gateId = cbParamMap.get(CardBindingCbParamKey.GATE_ID); // 网关号
		String transType = cbParamMap.get(CardBindingCbParamKey.TRANS_TYPE); // 交易类型
		String subTransType = cbParamMap.get(CardBindingCbParamKey.SUB_TRANS_TYPE); // 交易子类型
		String stat = cbParamMap.get(CardBindingCbParamKey.STAT); // 签约状态
		String cardNo = cbParamMap.get(CardBindingCbParamKey.CARD_NO); // 银行卡号
		String bindingNo = cbParamMap.get(CardBindingCbParamKey.BINDING_NO); // 签约号
		String expiry = cbParamMap.get(CardBindingCbParamKey.EXPIRY); // 小额临时支付有效期
		String sumLimit = cbParamMap.get(CardBindingCbParamKey.SUM_LIMIT); // 小额临时支付总限额
		String transLimit = cbParamMap.get(CardBindingCbParamKey.TRANS_LIMIT); // 小额临时支付单笔限额
		String chkValue = cbParamMap.get(CardBindingCbParamKey.CHK_VALUE); // 签名
		
		// 创建公钥对象，用其验证银联的签名信息
		PrivateKey publicKey = new PrivateKey();
		
		if(!publicKey.buildKey(ChinapayUpopConst.PUB_MERID, 0, bankPubKeyPath)) {
			logger.error("verifySignatureOfCallBack: 中国银联公钥错误! mechantNo="
					+ mechantNo + " bankPubKeyPath =" + privateKeyFilePath);
			
			throw new BankAdapterException("处理中国银联快捷支付银行卡签约回调时验证签名失败，原因：中国银联公钥错误!");
		}
		
		// 防钓鱼版本签名方法
		// MerId+GateId+TransType+SubTransType+Stat+CardNo+BindingNo+expiry+sumLimit+transLimit
		String plainData = StringHelper.joinString(merId, gateId, transType,
				subTransType, stat, cardNo, bindingNo, expiry, sumLimit,
				transLimit);
		
		// 验证签名
		boolean isVerifySuccess = new SecureLink(publicKey).verifyAuthToken(
				plainData, chkValue);

		if (!isVerifySuccess) {
			logger.info("verifySignatureOfCallBack: 中国银联UPOP银行卡签约回调时, 验证签名失败!! cardNo="
					+ cardNo + ", plainData=" + plainData);

			throw new BankAdapterException("中国银联UPOP银行卡签约回调时, 验证签名失败!");
		}

		return isVerifySuccess;
	}
}