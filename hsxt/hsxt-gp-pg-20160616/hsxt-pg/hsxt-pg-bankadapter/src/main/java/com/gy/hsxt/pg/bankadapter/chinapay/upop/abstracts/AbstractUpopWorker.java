/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.chinapay.upop.abstracts;

import com.gy.hsxt.pg.bankadapter.chinapay.b2c.abstracts.AbstractB2cWorker;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.common.ChinapayUpopConst.UpopRespCode;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.common.UpopBankErrorException;
import com.gy.hsxt.pg.bankadapter.common.constants.BanksConstants;
import com.gy.hsxt.pg.bankadapter.common.constants.BanksConstants.ErrorCode;

/***************************************************************************
 * <PRE>
 *  Project Name    : bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.chinapay.upop.abstracts
 * 
 *  File Name       : AbstractUpopWorker.java
 * 
 *  Creation Date   : 2014-10-23
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 抽象工作者类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public abstract class AbstractUpopWorker extends AbstractB2cWorker {

	/**
	 * 抛出异常
	 * 
	 * @param msgPrefix
	 * @param bankErrorCode
	 */
	protected void throwUpopBankErrorException(String msgPrefix,
			String bankErrorCode) {

		ErrorCode errorCode = ErrorCode.FAILED;

		// 重复支付
		if (UpopRespCode.E07.valueEquals(bankErrorCode)
				|| UpopRespCode.E30.valueEquals(bankErrorCode)
				|| UpopRespCode.E42.valueEquals(bankErrorCode)
				|| UpopRespCode.E_094.valueEquals(bankErrorCode)) {
			// 094
			bankErrorCode = UpopRespCode.E_094.getCode();

			errorCode = ErrorCode.DUPLICATE_SUBMIT;
		}
		// 交易单不存在
		else if (UpopRespCode.E29.valueEquals(bankErrorCode)
				|| UpopRespCode.E_072.valueEquals(bankErrorCode)
				|| UpopRespCode.E_084.valueEquals(bankErrorCode)) {
			// 084
			bankErrorCode = UpopRespCode.E_084.getCode();

			errorCode = ErrorCode.DATA_NOT_EXIST;
		}

		// 错误信息描述
		String errorMsg = assembleErrorMsg(msgPrefix, bankErrorCode);

		UpopBankErrorException ex = new UpopBankErrorException(errorCode,
				errorMsg);
		ex.setBankErrorCode(bankErrorCode);

		throw ex;
	}
	
	/**
	 * 调整银行响应码
	 * 
	 * @param bankRespCode
	 * @return
	 */
	protected String adjustBankRespCode(String bankRespCode) {

		if ((4 == bankRespCode.length())
				&& (bankRespCode.matches("[^1|^\\D]{4}"))) {
			bankRespCode = bankRespCode.substring(1);

			if (UpopRespCode.containCode(bankRespCode)) {
				return bankRespCode;
			}
		}

		return bankRespCode;
	}

	/**
	 * 组装错误信息
	 * 
	 * @param msgPrefix
	 * @param bankErrorCode
	 * @return
	 */
	protected String assembleErrorMsg(String msgPrefix, String bankErrorCode) {

		StringBuilder buffer = new StringBuilder();
		buffer.append("[");
		buffer.append(BanksConstants.BANK_ERRCODE_SYMBOL);
		buffer.append("]");
		buffer.append(" ");
		buffer.append(msgPrefix);
		buffer.append(" ");

		if (!UpopRespCode.containCode(bankErrorCode)) {
			buffer.append("中国银联服务器繁忙");

			return buffer.toString();
		}

		buffer.append(BanksConstants.BANK_ERRMSG_SYMBOL);
		buffer.append("!");

		return formatWithPlaceholder(buffer, bankErrorCode);
	}

	/**
	 * 替换占位符
	 * 
	 * @param errMsgWithPlaceholder
	 * @param bankErrorCode
	 * @return
	 */
	private String formatWithPlaceholder(StringBuilder errMsgWithPlaceholder,
			String bankErrorCode) {

		String formatedMsg = errMsgWithPlaceholder.toString();

		formatedMsg = formatedMsg.replace(BanksConstants.BANK_ERRCODE_SYMBOL,
				bankErrorCode).replace(BanksConstants.BANK_ERRMSG_SYMBOL,
				UpopRespCode.getDescByCode(bankErrorCode));

		return formatedMsg;
	}
}
