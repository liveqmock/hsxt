/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.chinapay.upop.common;

import java.math.BigInteger;
import java.util.List;

import com.gy.hsxt.pg.bankadapter.chinapay.upop.common.ChinapayUpopConst.UpopCardType;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.params.UpopCardBindingParam;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.params.UpopPayFirstParam;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.params.UpopPaySecondParam;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.params.UpopSmsCodeParam;
import com.gy.hsxt.pg.bankadapter.common.constants.BanksConstants.ErrorCode;
import com.gy.hsxt.pg.bankadapter.common.exception.BankAdapterException;
import com.gy.hsxt.pg.bankadapter.common.utils.DateUtils;
import com.gy.hsxt.pg.bankadapter.common.utils.StringParamChecker;

/***************************************************************************
 * <PRE>
 *  Project Name    : bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.chinapay.upop.common
 * 
 *  File Name       : UpopParamChecker.java
 * 
 *  Creation Date   : 2015-09-17
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 中国银联UPOP传递的参数值校验帮助类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class UpopParamChecker {
	/**
	 * 校验签约号查询参数
	 * 
	 * @param param
	 * @return
	 */
	public static boolean checkQryBindingNo(String cardNo, String bankId,
			String cardType) {
		StringParamChecker.check("cardNo", cardNo, 1, 19);
		StringParamChecker.check("bankId", bankId, 4, 4);
		StringParamChecker.check("cardType", cardType, 2, 2);

		checkCardTypeValueRange(cardType);

		return true;
	}

	/**
	 * 校验银行卡绑定的参数
	 * 
	 * @param params
	 * @return
	 */
	public static boolean checkCardBinding(UpopCardBindingParam params) {
		// 卡号, 长度： 19(MAX), 必输 ，数字
		String cardNo = params.getCardNo();
		// 借贷记标识, 长度： 2, 必输，数字，定长(01：借记卡; 02：贷记卡)
		String cardType = params.getCardType();
		// 银行代码, 长度： 4, 必输，参照银行名称-简码对照表
		String bankId = params.getBankId();
		// 前台交易接收URL, 长度： 80(MAX)
		String notifyURL = params.getNotifyURL();
		// 订单交易日期, 长度：8, 必输，数字，格式：yyyyMMdd
		String orderDate = params.getOrderDate();

		// 参数校验
		{
			StringParamChecker.check("cardNo", cardNo, 1, 19);
			StringParamChecker.check("cardType", cardType, 2, 2);
			StringParamChecker.check("bankId", bankId, 4, 4);
			StringParamChecker.check("notifyURL", notifyURL, 10, 80);
			StringParamChecker.check("orderDate", orderDate, 8, 8);
		}
		
		checkCardTypeValueRange(cardType);
		checkOrderDate(orderDate);

		return true;
	}

	/**
	 * 校验快捷支付首次
	 * 
	 * @param params
	 * @return
	 */
	public static boolean checkPayFirst(UpopPayFirstParam params) {
		// 交易订单号, 长度： 16, 必输，数字，定长，商户系统当天唯一
		String orderNo = params.getOrderNo();
		// 订单交易日期, 长度：8, 必输，数字，格式：yyyyMMdd
		String orderDate = params.getOrderDate();
		// 卡号, 长度： 19(MAX), 必输 ，数字
		String cardNo = params.getCardNo();
		// 借贷记标识, 长度： 2, 必输，数字，定长(01：借记卡; 02：贷记卡)
		String cardType = params.getCardType();
		// 银行代码, 长度： 4, 必输，参照银行名称-简码对照表
		String bankId = params.getBankId();
		// 前台交易接收URL, 长度： 80(MAX)
		String notifyURL = params.getNotifyURL();
		// 后台交易接收URL, 长度： 80(MAX)
		String jumpURL = params.getJumpURL();

		// 交易金额(必填, 单位：分), 长度： 12, 必输，数字，定长，单位为分，不足12位，左补零至12位
		BigInteger payAmount = params.getPayAmount();

		// 参数校验
		{
			StringParamChecker.check("orderNo", orderNo, 16, 16);
			StringParamChecker.check("orderDate", orderDate, 8, 8);
			StringParamChecker.check("cardNo", cardNo, 1, 19);
			StringParamChecker.check("cardType", cardType, 2, 2);
			StringParamChecker.check("bankId", bankId, 4, 4);
			StringParamChecker.check("jumpURL", jumpURL, 10, 80);
			StringParamChecker.check("notifyURL", notifyURL, 10, 80);
		}
		
		checkCardTypeValueRange(cardType);
		checkOrderDate(orderDate);

		if (0 >= payAmount.longValue()) {
			throw new BankAdapterException(ErrorCode.INVALID_PARAM,
					"订单金额必须大于0！");
		}

		return true;
	}

	/**
	 * 校验非首次支付参数
	 * 
	 * @param params
	 * @return
	 */
	public static boolean checkPaySecond(UpopPaySecondParam params) {
		// 交易订单号, 长度： 16, 必输，数字，定长，商户系统当天唯一
		String orderNo = params.getOrderNo();
		// 订单交易日期, 长度：8, 必输，数字，格式：yyyyMMdd
		String orderDate = params.getOrderDate();
		// 交易金额(必填, 单位：分), 长度： 12
		BigInteger payAmount = params.getPayAmount();
		// 签约号, 长度：24, 必输，数字，定长
		String bindingNo = params.getBindingNo();
		// 短信验证码, 长度：6, 必输，数字，定长
		String smsCode = params.getSmsCode();

		// 参数校验
		{
			StringParamChecker.check("orderNo", orderNo, 16, 16);
			StringParamChecker.check("orderDate", orderDate, 8, 8);
			StringParamChecker.check("bindingNo", bindingNo, 24, 24);
			StringParamChecker.check("smsCode", smsCode, 6, 6);
		}

		checkOrderDate(orderDate);

		if (0 >= payAmount.longValue()) {
			throw new BankAdapterException(ErrorCode.INVALID_PARAM,
					"订单金额必须大于0！");
		}

		return true;
	}
	
	/**
	 * 校验短信验证码发送参数
	 * 
	 * @param params
	 * @return
	 */
	public static boolean checkSendSmsCode(UpopSmsCodeParam params) {
		// 交易订单号, 长度： 16, 必输，数字，定长，商户系统当天唯一
		String orderNo = params.getOrderNo();
		// 订单交易日期, 长度：8, 必输，数字，格式：yyyyMMdd
		String orderDate = params.getOrderDate();
		// 交易金额(必填, 单位：分), 长度： 12, 必输，数字，定长，单位为分，不足12位，左补零至12位
		BigInteger payAmount = params.getPayAmount();
		// 前台交易接收URL, 长度： 80(MAX)
		String notifyURL = params.getNotifyURL();
		// 后台交易接收URL, 长度： 80(MAX)
		String jumpURL = params.getJumpURL();
		// 签约号, 长度：24, 必输，数字，定长
		String bindingNo = params.getBindingNo();

		// 参数校验
		{
			StringParamChecker.check("orderNo", orderNo, 16, 16);
			StringParamChecker.check("orderDate", orderDate, 8, 8);
			StringParamChecker.check("jumpURL", jumpURL, 0, 80);
			StringParamChecker.check("notifyURL", notifyURL, 10, 80);
			StringParamChecker.check("bindingNo", bindingNo, 24, 24);
		}

		checkOrderDate(orderDate);

		if (0 >= payAmount.longValue()) {
			throw new BankAdapterException(ErrorCode.INVALID_PARAM,
					"订单金额必须大于0！");
		}
		
		return true;
	}

	/**
	 * 校验银行卡贷记标识取值
	 * 
	 * @param cardType
	 * @return
	 */
	private static boolean checkCardTypeValueRange(String cardType) {
		List<Object> cardTypeList = StaticConstHelper
				.getConstDefineList(UpopCardType.class);

		if (!cardTypeList.contains(cardType)) {
			throw new BankAdapterException(ErrorCode.INVALID_PARAM,
					"银行卡借贷记标识类型cardType取值范围必须是：" + cardTypeList.toString()
							+ "!");
		}

		return true;
	}

	/**
	 * 校验订单日期
	 * 
	 * @param orderDate
	 * @return
	 */
	private static boolean checkOrderDate(String orderDate) {

		if (null == DateUtils.parse2yyyyMMddDate(orderDate)) {
			throw new BankAdapterException(ErrorCode.INVALID_PARAM,
					"订单日期必须是有效的日期格式yyyyMMdd !");
		}

		return true;
	}
}
