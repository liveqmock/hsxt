/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gp.service.impl.check;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gp.common.utils.NumbericUtils;
import com.gy.hsxt.gp.common.utils.StringParamChecker;
import com.gy.hsxt.gp.common.utils.StringUtils;
import com.gy.hsxt.gp.constant.GPConstant;
import com.gy.hsxt.gp.constant.GPConstant.BankCardType;
import com.gy.hsxt.gp.constant.GPConstant.GPCurrencyCode;
import com.gy.hsxt.gp.constant.GPConstant.GPErrorCode;
import com.gy.hsxt.gp.constant.GPConstant.MerId;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-service
 * 
 *  Package Name    : com.gy.hsxt.gp.service.check
 * 
 *  File Name       : BasicPropertyCheck.java
 * 
 *  Creation Date   : 2015-11-2
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : none
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class BasicPropertyCheck {

	/**
	 * 判断merId是否有效
	 * 
	 * @param merId
	 * @throws HsException
	 */
	public static void checkMerId(String merId) throws HsException {

		// 判断商户号是否为空
		if (StringUtils.isEmpty(merId)) {
			throw new HsException(GPErrorCode.INVALID_PARAM, "商户号不能为空!");
		}

		// 判断商户号是否为指定的值中
		if (!MerId.HS.equals(merId) && !MerId.HSEC.equals(merId)) {
			throw new HsException(GPErrorCode.INVALID_PARAM,
					"商户号不在指定的范围中! merId=" + merId);
		}
	}

	/**
	 * 判断业务订单号orderNo是否有效
	 * 
	 * @param orderNo
	 */
	public static void checkOrderNo(String orderNo) {
		StringParamChecker.check("业务订单号orderNo", orderNo, 1, 32);
	}

	/**
	 * 判断业务订单号orderNo是否有效
	 * 
	 * @param orderNoList
	 */
	public static void checkOrderNoList(List<String> orderNoList) {

		if ((null == orderNoList) || (0 >= orderNoList.size())) {
			throw new HsException(GPErrorCode.INVALID_PARAM,
					StringUtils.joinString("业务订单号不能为空! "));
		}

		// 校验订单号
		for (String orderNo : orderNoList) {
			BasicPropertyCheck.checkOrderNo(orderNo);
		}
	}

	/**
	 * 判断交易日期orderDate是否有效
	 * 
	 * @param orderDate
	 */
	public static void checkOrderDate(Date orderDate) {
		if (null == orderDate) {
			throw new HsException(GPErrorCode.INVALID_PARAM,
					"交易日期orderDate不能为空! ");
		}

	}

	/**
	 * 判断交易金额transAmount是否有效
	 * 
	 * @param transAmount
	 */
	public static void checkTransAmount(String transAmount) {
		if (!NumbericUtils.isNumeric(transAmount)) {
			throw new HsException(GPErrorCode.INVALID_PARAM,
					"交易金额transAmount不是有效数字! transAmount=" + transAmount);
		}
	}

	/**
	 * 判断币种currencyCode是否有效
	 * 
	 * @param currencyCode
	 * @throws HsException
	 */
	public static void checkCurrencyCode(String currencyCode)
			throws HsException {
		// currencyCode如果为空,如果不为空，且不是CNY，则报错
		if (StringUtils.isEmptyTrim(currencyCode)) {
			throw new HsException(GPErrorCode.INVALID_PARAM, "currencyCode不能为空");
		} 
		
		if (!GPCurrencyCode.CNY.equals(currencyCode)) {
			throw new HsException(GPErrorCode.INVALID_PARAM,
					"币种只支持人民币CNY! currencyCode=" + currencyCode);
		}
	}

	/**
	 * 判断交易描述transDesc是否有效
	 * 
	 * @param transDesc
	 * @throws HsException
	 */
	public static void checkTransDesc(String transDesc) throws HsException {
		StringParamChecker.check("交易描述transDesc", transDesc, 0, 30);
	}

	/**
	 * 判断私有域privObligate是否有效
	 * 
	 * @param privObligate
	 * @throws HsException
	 */
	public static void checkprivObligate(String privObligate)
			throws HsException {
		StringParamChecker.check("私有域privObligate", privObligate, 0, 60);
	}
	
	/**
	 * 判断私有域privObligate是否有效
	 * 
	 * @param privObligate
	 * @throws HsException
	 */
	public static void checkGoodsName(String goodsName)
			throws HsException {
		StringParamChecker.check("商品名称goodsName", goodsName, 1, 120);
	}


	/**
	 * 判断客户号custId是否有效
	 * 
	 * @param custId
	 * @throws HsException
	 */
	public static void checkcustId(String custId) throws HsException {
		StringParamChecker.check("客户号custId", custId, 1, 21);
	}

	/**
	 * 判断银行卡号bankCardNo是否有效
	 * 
	 * @param bankCardNo
	 * @throws HsException
	 */
	public static void checkbankCardNo(String bankCardNo) throws HsException {
		StringParamChecker.check("银行卡号bankCardNo", bankCardNo, 1, 19);
	}

	/**
	 * 判断bankCardType借贷记标识是否有效
	 * 
	 * @param bankCardType
	 * @throws HsException
	 */
	public static void checkbankCardType(int bankCardType) throws HsException {

		if ((BankCardType.DEBIT_CARD != bankCardType)
				&& (BankCardType.CREDIT_CARD != bankCardType)) {
			throw new HsException(GPErrorCode.INVALID_PARAM,
					"借贷记标识bankCardType必为: 1-借记卡或者2-贷记卡! bankCardType="
							+ bankCardType);
		}
	}

	/**
	 * 判断bankId银行代码是否有效
	 * 
	 * @param bankId
	 * @throws HsException
	 */
	public static void checkbankId(String bankId) throws HsException {
		StringParamChecker.check("银行代码bankId", bankId, 0, 4);
	}

	/**
	 * 判断jumpUrl银行代码是否有效
	 * 
	 * @param jumpUrl
	 * @throws HsException
	 */
	public static void checkjumpUrl(String jumpUrl) throws HsException {
		checkjumpUrl(jumpUrl, true);
	}

	/**
	 * 判断jumpUrl银行代码是否有效
	 * 
	 * @param jumpUrl
	 * @param isMust
	 * @throws HsException
	 */
	public static void checkjumpUrl(String jumpUrl, boolean isMust)
			throws HsException {

		if (StringUtils.isEmpty(jumpUrl)) {
			if (isMust) {
				throw new HsException(GPErrorCode.INVALID_PARAM,
						"跳转页面的jump url不合法! jumpUrl=" + jumpUrl);
			}

			return;
		}

		String regEx = "^(http://|https://)?((?:[A-Za-z0-9]+-[A-Za-z0-9]"
				+ "+|[A-Za-z0-9]" + "+).)+([A-Za-z]+)[/?:]?.*$";
		Pattern p = Pattern.compile(regEx);
		Matcher matcher = p.matcher(jumpUrl);

		if (matcher.matches()) {
			StringParamChecker.check("jumpUrl", jumpUrl, 20, 80);
		} else {
			throw new HsException(GPErrorCode.INVALID_PARAM,
					"支付成功跳转页面的jump url不合法! jumpUrl=" + jumpUrl);
		}
	}

	/**
	 * 判断签约号bindingNo是否有效
	 * 
	 * @param bindingNo
	 */
	public static void checkBindingNo(String bindingNo) {
		StringParamChecker.check("签约号bindingNo", bindingNo, 1, 24);
	}

	/**
	 * 判断动态短信验证码smsCode是否有效
	 * 
	 * @param smsCode
	 */
	public static void checkSmsCode(String smsCode) {
		if (StringUtils.isEmpty(smsCode)) {
			throw new HsException(GPErrorCode.INVALID_PARAM,
					"短信验证码smsCode不能为空! ");
		}
	}

	/**
	 * 判断批次号batchNo是否有效
	 * 
	 * @param batchNo
	 * @throws HsException
	 */
	public static void checkBatchNo(String batchNo) throws HsException {
		if (StringUtils.isEmpty(batchNo)) {
			throw new HsException(GPErrorCode.INVALID_PARAM, "批次号batchNo不能为空! ");
		}
	}

	/**
	 * 判断转账加急标志sysFlag是否有效
	 * 
	 * @param sysFlag
	 */
	public static void checkSysFlag(String sysFlag) {

		if (StringUtils.isEmpty(sysFlag)) {
			throw new HsException(GPErrorCode.INVALID_PARAM,
					"转账加急标志sysFlag不能为空! ");
		}

		sysFlag = sysFlag.trim();

		if ((1 != sysFlag.length()) || !"SNY12".contains(sysFlag)) {
			throw new HsException(GPErrorCode.INVALID_PARAM,
					"转账加急标志sysFlag必须为: N,Y,S,1,2");
		}
	}

	/**
	 * 判断资金用途备注useDesc是否有效
	 * 
	 * @param useDesc
	 */
	public static void checkUseDesc(String useDesc) {
		StringParamChecker.check("资金用途备注useDesc", useDesc, 0, 30);
	}

	/**
	 * 判断付款账户类型，outBankAccType是否有效
	 * 
	 * @param outBankAccType
	 */
	public static void checkOutBankAccType(int outBankAccType) {
		// 付款账户类型，outBankAccType,如果不传则默认100
		if (GPConstant.COMMON_OUT_BANK_ACC_TYPE != outBankAccType) {
			throw new HsException(GPErrorCode.INVALID_PARAM, "付款账户类型需为100");
		}
	}

	/**
	 * 判断收款人账户inAccNo是否有效
	 * 
	 * @param inAccNo
	 */
	public static void checkInAccNo(String inAccNo) {
		StringParamChecker.check("收款人账户inAccNo", inAccNo, 1, 32);
	}

	/**
	 * 判断收款人账户名inAccName是否有效
	 * 
	 * @param inAccName
	 */
	public static void checkInAccName(String inAccName) {
		StringParamChecker.check("收款人账户名inAccName", inAccName, 1, 60);
	}

	/**
	 * 判断收款人开户行名称inAccBankName是否有效
	 * 
	 * @param inAccBankName
	 */
	public static void checkInAccBankName(String inAccBankName) {
		StringParamChecker.check("收款人开户行名称inAccBankName", inAccBankName, 1, 60);
	}

	/**
	 * 判断收款人开户银行代码inAccBankCode是否有效
	 * 
	 * @param inAccBankCode
	 */
	public static void checkInAccBankNode(String inAccBankCode) {
		StringParamChecker
				.check("收款人开户银行代码inAccBankNode", inAccBankCode, 1, 12);
	}

	/**
	 * 判断收款账户银行开户省代码或省名称inAccProvinceCode是否有效
	 * 
	 * @param inAccProvinceCode
	 */
	public static void checkInAccProvinceCode(String inAccProvinceCode) {
		StringParamChecker.check("判断收款账户银行开户省代码或省名称inAccProvinceCode",
				inAccProvinceCode, 0, 10);
	}

	/**
	 * 判断收款账户开户城市名称inAccCityName是否有效
	 * 
	 * @param inAccCityName
	 */
	public static void checkInAccCityName(String inAccCityName) {
		StringParamChecker.check("收款账户开户城市名称inAccCityName", inAccCityName, 0,
				12);
	}

	/**
	 * 判断收款账户开户城市代码inAccCityCode是否有效
	 * 
	 * @param inAccCityCode
	 */
	public static void checkInAccCityCode(String inAccCityCode) {
		StringParamChecker.check("判断收款账户开户城市代码inAccCityCode", inAccCityCode, 1,
				10);
	}

	/**
	 * 判断转账通知的手机号码notifyMobile最多不能超过5个手机号码是否有效
	 * 
	 * @param notifyMobile
	 */
	public static void checkNotifyMobile(List<String> notifyMobile) {
		if ((null != notifyMobile) && (5 < notifyMobile.size())) {
			throw new HsException(GPErrorCode.INVALID_PARAM,
					"转账通知的手机号码notifyMobile最多不能超过5个手机号码");
		}
	}

	/**
	 * 判断来源系统id是否有效
	 * 
	 * @param srcSubsysId
	 */
	public static void checkSrcSubSystemId(String srcSubsysId) {
		StringParamChecker.check("开源系统标识srcSubsysId", srcSubsysId, 1, 10);
	}
}
