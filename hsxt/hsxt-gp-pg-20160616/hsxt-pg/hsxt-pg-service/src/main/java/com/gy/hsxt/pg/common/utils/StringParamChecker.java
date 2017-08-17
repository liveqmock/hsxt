/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.common.utils;

import org.springframework.util.StringUtils;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.pg.constant.PGConstant.PGErrorCode;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.common.utils
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
 *  History         : 1.2
 *  更新了错误时抛出的异常为HsException
 * 
 * </PRE>
 ***************************************************************************/
public class StringParamChecker {
	private StringParamChecker() {
	}

	private static boolean isMust(int paramMinLen) {
		return (0 < paramMinLen);
	}

	public static boolean check(String paramName, String paramValue,
			int paramMinLen, int paramMaxLen) throws HsException {

		if (isMust(paramMinLen) && StringUtils.isEmpty(paramValue)) {
			throw new HsException(PGErrorCode.INVALID_PARAM, "参数" + paramName
					+ "是必输项, 不能为空！");
		}

		if (!StringUtils.isEmpty(paramValue)) {
			if ((paramMinLen > paramValue.length())
					|| (paramMaxLen < paramValue.length())) {
				StringBuilder buffer = new StringBuilder();
				buffer.append("参数").append(paramName).append("的长度必须在[")
						.append(paramMinLen).append(",").append(paramMaxLen)
						.append("]范围内！  ").append(paramName).append("=")
						.append(paramValue);

				throw new HsException(PGErrorCode.INVALID_PARAM,
						buffer.toString());
			}
		}

		return true;
	}
}
