/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gp.service.impl.check;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gp.bean.FirstQuickPayBean;
import com.gy.hsxt.gp.bean.MobilePayBean;
import com.gy.hsxt.gp.bean.NetPayBean;
import com.gy.hsxt.gp.bean.QuickPayBean;
import com.gy.hsxt.gp.bean.QuickPayCardBindingBean;
import com.gy.hsxt.gp.bean.QuickPaySmsCodeBean;
import com.gy.hsxt.gp.bean.TransCash;
import com.gy.hsxt.pg.constant.PGConstant.PGErrorCode;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-service
 * 
 *  Package Name    : com.gy.hsxt.gp.service.check
 * 
 *  File Name       : BeanCheck.java
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
public class BeanCheck {

	/**
	 * 校验 QuickPayCardBindingBean
	 * 
	 * @param cardBindingBean
	 * @throws HsException
	 */
	public static void isvalid(QuickPayCardBindingBean cardBindingBean)
			throws HsException {

		if (null == cardBindingBean) {
			throw new HsException(PGErrorCode.INVALID_PARAM,
					"传递的QuickPayCardBindingBean对象不能为空!");
		}

		// 判断merId是否有效
		BasicPropertyCheck.checkMerId(cardBindingBean.getMerId());

		// 客户号
		BasicPropertyCheck.checkcustId(cardBindingBean.getCustId());

		// bankCardNo银行卡号
		BasicPropertyCheck.checkbankCardNo(cardBindingBean.getBankCardNo());

		// bankCardType借贷记标识
		BasicPropertyCheck.checkbankCardType(cardBindingBean.getBankCardType());

		// bankId银行代码
		BasicPropertyCheck.checkbankId(cardBindingBean.getBankId());
	}

	/**
	 * 校验 FirstQuickPayBean
	 * 
	 * @param firstQuickPayBean
	 * @throws HsException
	 */
	public static void isvalid(FirstQuickPayBean firstQuickPayBean)
			throws HsException {

		if (null == firstQuickPayBean) {
			throw new HsException(PGErrorCode.INVALID_PARAM,
					"传递的FirstQuickPayBean对象不能为空!");
		}

		// 判断merId是否有效
		BasicPropertyCheck.checkMerId(firstQuickPayBean.getMerId());

		// 判断业务订单号orderNo是否有效
		BasicPropertyCheck.checkOrderNo(firstQuickPayBean.getOrderNo());

		// 判断交易日期orderDate是否有效
		BasicPropertyCheck.checkOrderDate(firstQuickPayBean.getOrderDate());

		// 判断交易金额transAmount是否有效
		BasicPropertyCheck.checkTransAmount(firstQuickPayBean.getTransAmount());

		// 判断交易描述transDesc是否有效
		BasicPropertyCheck.checkTransDesc(firstQuickPayBean.getTransDesc());

		// 判断币种currencyCode是否有效
		BasicPropertyCheck.checkCurrencyCode(firstQuickPayBean
				.getCurrencyCode());

		// jumpUrl判断URL是否合法
		BasicPropertyCheck.checkjumpUrl(firstQuickPayBean.getJumpUrl());

		// 判断业务订单号privObligate是否有效
		BasicPropertyCheck.checkprivObligate(firstQuickPayBean
				.getPrivObligate());

		// 客户号
		BasicPropertyCheck.checkcustId(firstQuickPayBean.getCustId());

		// bankCardNo银行卡号
		BasicPropertyCheck.checkbankCardNo(firstQuickPayBean.getBankCardNo());

		// bankCardType借贷记标识
		BasicPropertyCheck.checkbankCardType(firstQuickPayBean
				.getBankCardType());

		// bankId银行代码
		BasicPropertyCheck.checkbankId(firstQuickPayBean.getBankId());
	}

	/**
	 * 校验MobilePayBean
	 * 
	 * @param mobilePayBean
	 * @throws HsException
	 */
	public static void isvalid(MobilePayBean mobilePayBean) throws HsException {

		if (null == mobilePayBean) {
			throw new HsException(PGErrorCode.INVALID_PARAM,
					"传递的MobilePayBean对象不能为空!");
		}

		// 判断merId是否有效
		BasicPropertyCheck.checkMerId(mobilePayBean.getMerId());

		// 判断业务订单号orderNo是否有效
		BasicPropertyCheck.checkOrderNo(mobilePayBean.getOrderNo());

		// 判断交易日期orderDate是否有效
		BasicPropertyCheck.checkOrderDate(mobilePayBean.getOrderDate());

		// 判断交易金额transAmount是否有效
		BasicPropertyCheck.checkTransAmount(mobilePayBean.getTransAmount());

		// 判断交易描述transDesc是否有效
		BasicPropertyCheck.checkTransDesc(mobilePayBean.getTransDesc());

		// 判断币种currencyCode是否有效
		BasicPropertyCheck.checkCurrencyCode(mobilePayBean.getCurrencyCode());

		// 判断业务订单号privObligate是否有效
		BasicPropertyCheck.checkprivObligate(mobilePayBean.getPrivObligate());
	}

	/**
	 * 校验NetPayBean
	 * 
	 * @param netPayBean
	 * @throws HsException
	 */
	public static void isvalid(NetPayBean netPayBean) throws HsException {

		if (null == netPayBean) {
			throw new HsException(PGErrorCode.INVALID_PARAM,
					"传递的NetPayBean对象不能为空!");
		}

		// 判断merId是否有效
		BasicPropertyCheck.checkMerId(netPayBean.getMerId());

		// 判断业务订单号orderNo是否有效
		BasicPropertyCheck.checkOrderNo(netPayBean.getOrderNo());

		// 判断交易日期orderDate是否有效
		BasicPropertyCheck.checkOrderDate(netPayBean.getOrderDate());

		// 判断交易金额transAmount是否有效
		BasicPropertyCheck.checkTransAmount(netPayBean.getTransAmount());

		// 判断交易描述transDesc是否有效
		BasicPropertyCheck.checkTransDesc(netPayBean.getTransDesc());

		// 判断币种currencyCode是否有效
		BasicPropertyCheck.checkCurrencyCode(netPayBean.getCurrencyCode());

		// jumpUrl判断URL是否合法
		BasicPropertyCheck.checkjumpUrl(netPayBean.getJumpUrl());

		// 判断业务订单号privObligate是否有效
		BasicPropertyCheck.checkprivObligate(netPayBean.getPrivObligate());
	}

	/**
	 * 校验QuickPayBean
	 * 
	 * @param quickPayBean
	 * @throws HsException
	 */
	public static void isvalid(QuickPayBean quickPayBean) throws HsException {

		if (null == quickPayBean) {
			throw new HsException(PGErrorCode.INVALID_PARAM,
					"传递的QuickPayBean对象不能为空!");
		}

		// 判断merId是否有效
		BasicPropertyCheck.checkMerId(quickPayBean.getMerId());

		// 判断业务订单号orderNo是否有效
		BasicPropertyCheck.checkOrderNo(quickPayBean.getOrderNo());

		// 判断签约号bindingNo是否有效
		BasicPropertyCheck.checkBindingNo(quickPayBean.getBindingNo());

		// 判断动态短信验证码smsCode是否有效
		BasicPropertyCheck.checkSmsCode(quickPayBean.getSmsCode());
	}

	/**
	 * 校验QuickPaySmsCodeBean
	 * 
	 * @param quickPaySmsCodeBean
	 * @throws HsException
	 */
	public static void isvalid(QuickPaySmsCodeBean quickPaySmsCodeBean)
			throws HsException {

		if (null == quickPaySmsCodeBean) {
			throw new HsException(PGErrorCode.INVALID_PARAM,
					"传递的QuickPaySmsCodeBean对象不能为空!");
		}

		// 判断merId是否有效
		BasicPropertyCheck.checkMerId(quickPaySmsCodeBean.getMerId());

		// 判断业务订单号orderNo是否有效
		BasicPropertyCheck.checkOrderNo(quickPaySmsCodeBean.getOrderNo());

		// 判断交易日期orderDate是否有效
		BasicPropertyCheck.checkOrderDate(quickPaySmsCodeBean.getOrderDate());

		// 判断交易金额transAmount是否有效
		BasicPropertyCheck.checkTransAmount(quickPaySmsCodeBean
				.getTransAmount());

		// 判断交易描述transDesc是否有效
		BasicPropertyCheck.checkTransDesc(quickPaySmsCodeBean.getTransDesc());

		// 判断币种currencyCode是否有效
		BasicPropertyCheck.checkCurrencyCode(quickPaySmsCodeBean
				.getCurrencyCode());

		// jumpUrl判断URL是否合法, 非必输！！！！
		BasicPropertyCheck
				.checkjumpUrl(quickPaySmsCodeBean.getJumpUrl(), false);

		// 判断业务订单号privObligate是否有效
		BasicPropertyCheck.checkprivObligate(quickPaySmsCodeBean
				.getPrivObligate());
	}

	/**
	 * 校验TransCash
	 * 
	 * @param tranCash
	 * @throws HsException
	 */
	public static void isvalid(TransCash tranCash) throws HsException {

		if (null == tranCash) {
			throw new HsException(PGErrorCode.INVALID_PARAM,
					"传递的TransCash对象不能为空!");
		}

		// 判断业务订单号orderNo是否有效
		BasicPropertyCheck.checkOrderNo(tranCash.getOrderNo());

		// 判断交易日期orderDate是否有效
		BasicPropertyCheck.checkOrderDate(tranCash.getOrderDate());

		// 判断交易金额transAmount是否有效
		BasicPropertyCheck.checkTransAmount(tranCash.getTransAmount());

		// 判断币种currencyCode是否有效
		BasicPropertyCheck.checkCurrencyCode(tranCash.getCurrencyCode());

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

		// 判断收款账户开户城市代码inAccCityCode是否有效
		BasicPropertyCheck.checkInAccCityCode(tranCash.getInAccCityCode());

		// 判断转账通知的手机号码notifyMobile最多不能超过5个手机号码是否有效
		BasicPropertyCheck.checkNotifyMobile(tranCash.getNotifyMobile());

		// 转账加急标志sysFlag
		BasicPropertyCheck.checkSysFlag(tranCash.getSysFlag());
	}
}
