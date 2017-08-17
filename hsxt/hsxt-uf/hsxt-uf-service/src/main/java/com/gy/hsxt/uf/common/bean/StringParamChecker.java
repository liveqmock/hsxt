/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.uf.common.bean;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uf.common.constant.UFResultCode;
import com.gy.hsxt.uf.common.utils.StringUtils;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-service
 * 
 *  Package Name    : com.gy.hsxt.uf.common.bean
 * 
 *  File Name       : StringParamChecker.java
 * 
 *  Creation Date   : 2015-11-5
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
	
	private String paramName;
	private String paramValue;
	private int paramMinLen;
	private int paramMaxLen;
	private String regEx;

	/**
	 * 构造函数
	 * 
	 * @param paramName 参数名称
	 * @param paramValue 默认值
	 * @param paramMinLen 参数长度最小值
	 * @param paramMaxLen 参数长度最大值
	 */
	public StringParamChecker(String paramName, String paramValue,
			int paramMinLen, int paramMaxLen) {
		this.paramName = paramName;
		this.paramValue = paramValue;
		this.paramMinLen = paramMinLen;
		this.paramMaxLen = paramMaxLen;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public int getParamMinLen() {
		return paramMinLen;
	}

	public void setParamMinLen(int paramMinLen) {
		this.paramMinLen = paramMinLen;
	}

	public int getParamMaxLen() {
		return paramMaxLen;
	}

	public void setParamMaxLen(int paramMaxLen) {
		this.paramMaxLen = paramMaxLen;
	}

	public String getRegEx() {
		return regEx;
	}

	public void setRegEx(String regEx) {
		this.regEx = regEx;
	}
	
	public boolean actionCheck() throws HsException {
		return check(paramName, paramValue, paramMinLen, paramMaxLen);
	}

	/**
	 * 参数校验
	 * 
	 * @param paramName 参数名称
	 * @param paramValue 默认值
	 * @param paramMinLen 参数长度最小值
	 * @param paramMaxLen 参数长度最大值
	 * @return
	 * @throws HsException 
	 */
	public static boolean check(String paramName, String paramValue,
			int paramMinLen, int paramMaxLen) throws HsException {
		boolean isMust = (0 < paramMinLen);
		boolean isFixedLength = (paramMinLen == paramMaxLen);

		if (isMust && StringUtils.isEmpty(paramValue)) {
			throw new HsException(UFResultCode.PARAM_INVALID, "参数"
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

				throw new HsException(UFResultCode.PARAM_INVALID,
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
	 * @throws HsException 
	 */
	public static boolean check(String paramName, String paramValue,
			int paramMinLen, int paramMaxLen, String regEx) throws HsException {
		check(paramName, paramValue, paramMinLen, paramMaxLen);

		return true;
	}
}
