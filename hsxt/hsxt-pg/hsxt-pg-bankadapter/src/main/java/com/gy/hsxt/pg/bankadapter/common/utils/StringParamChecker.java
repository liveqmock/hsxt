/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.common.utils;

import org.springframework.util.StringUtils;

import com.gy.hsxt.pg.bankadapter.common.constants.BanksConstants.ErrorCode;
import com.gy.hsxt.pg.bankadapter.common.exception.BankAdapterException;

/***************************************************************************
 * <PRE>
 *  Project Name    : bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.common.utils
 * 
 *  File Name       : StringParamChecker.java
 * 
 *  Creation Date   : 2014-11-5
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 用于参数校验的bean
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class StringParamChecker {

	/**
	 * 参数校验
	 * 
	 * @param paramName 参数名称
	 * @param paramValue 默认值
	 * @param paramMinLen 参数长度最小值
	 * @param paramMaxLen 参数长度最大值
	 * @return
	 */
	public static boolean check(String paramName, String paramValue,
			int paramMinLen, int paramMaxLen) {
		boolean isMust = (0 < paramMinLen);
		boolean isFixedLength = (paramMinLen == paramMaxLen);

		if (isMust && StringUtils.isEmpty(paramValue)) {
			throw new BankAdapterException(ErrorCode.INVALID_PARAM, "参数"
					+ paramName + "是必输项, 不能为空！");
		}

		if (!StringUtils.isEmpty(paramValue)) {
			// 是否超出了值范围
			boolean isExceedRange = (paramMinLen > paramValue.length())
					|| (paramMaxLen < paramValue.length());

			if (isExceedRange) {
				StringBuilder buffer = new StringBuilder();

				// 固定长度字符串
				if (isFixedLength) {
					buffer.append("参数").append(paramName).append("的长度必须是")
							.append(paramMinLen).append("个字符！")
							.append(paramName).append("=").append(paramValue);
				} else {
					buffer.append("参数").append(paramName).append("的长度必须在[")
							.append(paramMinLen).append(",")
							.append(paramMaxLen).append("]范围内！  ")
							.append(paramName).append("=").append(paramValue);
				}

				throw new BankAdapterException(ErrorCode.INVALID_PARAM,
						buffer.toString());
			}
		}

		return true;
	}
	
	/**
	 * 参数校验
	 * 
	 * @param paramName 参数名称
	 * @param paramValue 默认值
	 * @param paramMinLen 参数长度最小值
	 * @param paramMaxLen 参数长度最大值
	 * @param regEx 正则表达式
	 * @return
	 */
	public static boolean check(String paramName, String paramValue, int paramMinLen,
			int paramMaxLen, String regEx) {
		check(paramName, paramValue, paramMinLen, paramMaxLen);
		
		return true;
	}
}
