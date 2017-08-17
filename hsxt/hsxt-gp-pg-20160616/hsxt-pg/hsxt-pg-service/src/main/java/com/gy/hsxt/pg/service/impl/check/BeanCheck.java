/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.service.impl.check;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.pg.bean.PGFirstQuickPayBean;
import com.gy.hsxt.pg.bean.PGMobilePayTnBean;
import com.gy.hsxt.pg.bean.PGNetPayBean;
import com.gy.hsxt.pg.bean.PGQuickPayBean;
import com.gy.hsxt.pg.bean.PGQuickPayCardBindingBean;
import com.gy.hsxt.pg.bean.PGQuickPaySmsCodeBean;
import com.gy.hsxt.pg.bean.PGTransCash;
import com.gy.hsxt.pg.constant.PGConstant.PGErrorCode;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.service.check
 * 
 *  File Name       : BeanCheck.java
 * 
 *  Creation Date   : 2015-11-2
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : bean校验
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class BeanCheck {
	
	/**
	 * 校验PGQuickPayCardBindingBean
	 * 
	 * @param cardBindingBean
	 * @throws HsException
	 */
	public static void isvalid(PGQuickPayCardBindingBean cardBindingBean)
			throws HsException {

		if (null == cardBindingBean) {
			throw new HsException(PGErrorCode.INVALID_PARAM,
					"传递的PGQuickPayCardBindingBean对象不能为空!");
		}

		// 判断merType是否有效
		BasicPropertyCheck.checkMerType(cardBindingBean.getMerType());

		// 判断requestId是否有效
		BasicPropertyCheck.checkBankOrderNo(cardBindingBean.getRequestId());

		// bankCardNo银行卡号
		BasicPropertyCheck.checkbankCardNo(cardBindingBean.getBankCardNo());

		// bankCardType借贷记标识
		BasicPropertyCheck.checkbankCardType(cardBindingBean.getBankCardType());

		// bankId银行代码
		BasicPropertyCheck.checkbankId(cardBindingBean.getBankId());
	}

	/**
	 * 校验PGFirstQuickPayBean
	 * 
	 * @param firstQuickPayBean
	 * @throws HsException
	 */
	public static void isvalid(PGFirstQuickPayBean firstQuickPayBean)
			throws HsException {

		if (null == firstQuickPayBean) {
			throw new HsException(PGErrorCode.INVALID_PARAM,
					"传递的PGFirstQuickPayBean对象不能为空!");
		}

		// 判断merType是否有效
		BasicPropertyCheck.checkMerType(firstQuickPayBean.getMerType());

		// 判断业务订单号bankOrderNo是否有效
		BasicPropertyCheck.checkBankOrderNo(firstQuickPayBean.getBankOrderNo());

		// 判断交易日期transDate是否有效
		BasicPropertyCheck.checkTransDate(firstQuickPayBean.getTransDate());

		// 判断交易金额transAmount是否有效
		BasicPropertyCheck.checkTransAmount(firstQuickPayBean.getTransAmount());

		// 判断币种currencyCode是否有效
		BasicPropertyCheck.checkCurrencyCode(firstQuickPayBean
				.getCurrencyCode().trim());

		// jumpUrl判断URL是否合法
		BasicPropertyCheck.checkjumpUrl(firstQuickPayBean.getJumpUrl());

		// 判断业务订单号privObligate是否有效
		BasicPropertyCheck.checkprivObligate(firstQuickPayBean
				.getPrivObligate());

		// bankCardNo银行卡号
		BasicPropertyCheck.checkbankCardNo(firstQuickPayBean.getBankCardNo());

		// bankCardType借贷记标识
		BasicPropertyCheck.checkbankCardType(firstQuickPayBean
				.getBankCardType());

		// bankId银行代码
		BasicPropertyCheck.checkbankId(firstQuickPayBean.getBankId());
	}

	/**
	 * 校验PGMobilePayTnBean
	 * 
	 * @param mobilePayBean
	 * @throws HsException
	 */
	public static void isvalid(PGMobilePayTnBean mobilePayBean)
			throws HsException {

		if (null == mobilePayBean) {
			throw new HsException(PGErrorCode.INVALID_PARAM,
					"传递的PGMobilePayTnBean对象不能为空!");
		}

		// 判断merType是否有效
		BasicPropertyCheck.checkMerType(mobilePayBean.getMerType());

		// 判断业务订单号bankOrderNo是否有效
		BasicPropertyCheck.checkBankOrderNo(mobilePayBean.getBankOrderNo());

		// 判断交易日期transDate是否有效
		BasicPropertyCheck.checkTransDate(mobilePayBean.getTransDate());

		// 判断交易金额transAmount是否有效
		BasicPropertyCheck.checkTransAmount(mobilePayBean.getTransAmount());

		// 判断币种currencyCode是否有效
		BasicPropertyCheck.checkCurrencyCode(mobilePayBean.getCurrencyCode()
				.trim());
	}

	/**
	 * 校验PGNetPayBean
	 * 
	 * @param netPayBean
	 * @throws HsException
	 */
	public static void isvalid(PGNetPayBean netPayBean) throws HsException {

		if (null == netPayBean) {
			throw new HsException(PGErrorCode.INVALID_PARAM,
					"传递的PGNetPayBean对象不能为空!");
		}

		// 判断merType是否有效
		BasicPropertyCheck.checkMerType(netPayBean.getMerType());

		// 判断业务订单号bankOrderNo是否有效
		BasicPropertyCheck.checkBankOrderNo(netPayBean.getBankOrderNo());

		// 判断交易日期transDate是否有效
		BasicPropertyCheck.checkTransDate(netPayBean.getTransDate());

		// 判断交易金额transAmount是否有效
		BasicPropertyCheck.checkTransAmount(netPayBean.getTransAmount());

		// 判断币种currencyCode是否有效
		BasicPropertyCheck.checkCurrencyCode(netPayBean.getCurrencyCode()
				.trim());

		// jumpUrl判断URL是否合法
		BasicPropertyCheck.checkjumpUrl(netPayBean.getJumpUrl());

		// 判断业务订单号privObligate是否有效
		BasicPropertyCheck.checkprivObligate(netPayBean.getPrivObligate());
	}

	/**
	 * 校验PGQuickPayBean
	 * 
	 * @param quickPayBean
	 * @throws HsException
	 */
	public static void isvalid(PGQuickPayBean quickPayBean) throws HsException {

		if (null == quickPayBean) {
			throw new HsException(PGErrorCode.INVALID_PARAM,
					"传递的PGQuickPayBean对象不能为空!");
		}

		// 判断merType是否有效
		BasicPropertyCheck.checkMerType(quickPayBean.getMerType());

		// 判断业务订单号bankOrderNo是否有效
		BasicPropertyCheck.checkBankOrderNo(quickPayBean.getBankOrderNo());

		// 判断签约号bindingNo是否有效
		BasicPropertyCheck.checkBindingNo(quickPayBean.getBindingNo());

		// 判断动态短信验证码smsCode是否有效
		BasicPropertyCheck.checkSmsCode(quickPayBean.getSmsCode());
	}

	/**
	 * 校验PGQuickPaySmsCodeBean
	 * 
	 * @param quickPaySmsCodeBean
	 * @throws HsException
	 */
	public static void isvalid(PGQuickPaySmsCodeBean quickPaySmsCodeBean)
			throws HsException {

		if (null == quickPaySmsCodeBean) {
			throw new HsException(PGErrorCode.INVALID_PARAM,
					"传递的PGQuickPaySmsCodeBean对象不能为空!");
		}

		// 判断merType是否有效
		BasicPropertyCheck.checkMerType(quickPaySmsCodeBean.getMerType());

		// 判断业务订单号bankOrderNo是否有效
		BasicPropertyCheck.checkBankOrderNo(quickPaySmsCodeBean
				.getBankOrderNo());

		// 判断交易日期transDate是否有效
		BasicPropertyCheck.checkTransDate(quickPaySmsCodeBean.getTransDate());

		// 判断交易金额transAmount是否有效
		BasicPropertyCheck.checkTransAmount(quickPaySmsCodeBean
				.getTransAmount());

		// 判断 签约号bindingNo是否有效
		BasicPropertyCheck.checkBindingNo(quickPaySmsCodeBean.getBindingNo());

		// 判断币种currencyCode是否有效
		BasicPropertyCheck.checkCurrencyCode(quickPaySmsCodeBean
				.getCurrencyCode().trim());

		// jumpUrl判断URL是否合法
		BasicPropertyCheck
				.checkjumpUrl(quickPaySmsCodeBean.getJumpUrl(), false);

		// 判断业务订单号privObligate是否有效
		BasicPropertyCheck.checkprivObligate(quickPaySmsCodeBean
				.getPrivObligate());
	}

	/**
	 * 校验PGTransCash
	 * 
	 * @param tranCash
	 * @throws HsException
	 */
	public static void isvalid(PGTransCash tranCash) throws HsException {

		if (null == tranCash) {
			throw new HsException(PGErrorCode.INVALID_PARAM,
					"传递的PGTransCash对象不能为空!");
		}

		// 判断业务订单号bankOrderNo是否有效
		BasicPropertyCheck.checkBankOrderNo(tranCash.getBankOrderNo());

		// 判断交易日期transDate是否有效
		BasicPropertyCheck.checkTransDate(tranCash.getTransDate());

		// 判断交易金额transAmount是否有效
		BasicPropertyCheck.checkTransAmount(tranCash.getTransAmount());

		// 判断币种currencyCode是否有效
		BasicPropertyCheck.checkCurrencyCode(tranCash.getCurrencyCode().trim());

		// 判断资金用途备注useDesc是否有效
		BasicPropertyCheck.checkUseDesc(tranCash.getUseDesc());

		// 判断收款人账户inAccNo是否有效
		BasicPropertyCheck.checkInAccNo(tranCash.getInAccNo());

		// 判断收款人账户名inAccName是否有效
		BasicPropertyCheck.checkInAccName(tranCash.getInAccName());

		// 判断收款人开户行名称inAccBankName是否有效
		BasicPropertyCheck.checkInAccBankName(tranCash.getInAccBankName());

		// 判断收款人开户银行代码inAccBankCode是否有效
		BasicPropertyCheck.checkInAccBankNode(tranCash.getInAccBankNode());

		// 判断收款账户银行开户省代码或省名称inAccProvinceCode是否有效
		BasicPropertyCheck.checkInAccProvinceCode(tranCash
				.getInAccProvinceCode());

		// 判断收款账户开户城市名称inAccCityName是否有效
		BasicPropertyCheck.checkInAccCityName(tranCash.getInAccCityName());

		// 判断转账通知的手机号码notifyMobile最多不能超过5个手机号码是否有效
		BasicPropertyCheck.checkNotifyMobile(tranCash.getNotifyMobile());

		// 转账加急标志sysFlag
		BasicPropertyCheck.checkSysFlag(tranCash.getSysFlag());

		// 判断付款银行账户outBankAccNo是否有效
		BasicPropertyCheck.checkOutBankAccNo(tranCash.getOutBankAccNo());

		// 判断行内跨行标志unionFlag
		BasicPropertyCheck.checkUnionFlag(tranCash.getUnionFlag());

		// 判断同城/异地标志addrFlag
		BasicPropertyCheck.checkAddrFlag(tranCash.getAddrFlag());
	}
}
