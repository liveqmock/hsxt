/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.common.utils;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.gy.hsxt.pg.bankadapter.common.utils.StringHelper;
import com.gy.hsxt.pg.common.constant.Constant;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.common.utils
 * 
 *  File Name       : RequestParserUtils.java
 * 
 *  Creation Date   : 2015-09-23
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 解析请求对象
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class RequestParserUtils {

	/**
	 * 从request对象中解析请求参数
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> parseRequestParams(
			HttpServletRequest request) {
		// 取出客户端传入的所有参数名
		Enumeration<?> requestKeys = request.getParameterNames();
		Map<String, String> paramsMap = new HashMap<String, String>(2);

		String key = null;
		String value = null;

		while (requestKeys.hasMoreElements()) {
			key = (String) requestKeys.nextElement();
			value = request.getParameter(key);

			if (StringHelper.isEmpty(key) || StringHelper.isEmpty(value)) {
				continue;
			}

			paramsMap.put(key, value);
		}

		return paramsMap;
	}

	/**
	 * 从request对象中解析请求参数
	 * 
	 * @param request
	 * @param encoding
	 * @return
	 */
	public static Map<String, String> parseRequestParams(
			HttpServletRequest request, String encoding) {

		Map<String, String> paramsMap = new HashMap<String, String>(2);

		try {
			request.setCharacterEncoding(Constant.ENCODING_ISO_8859_1);

			// 取出客户端传入的所有参数名
			Enumeration<?> requestKeys = request.getParameterNames();

			String key = null;
			String value = null;

			while (requestKeys.hasMoreElements()) {
				key = (String) requestKeys.nextElement();
				value = request.getParameter(key);

				if (StringHelper.isEmpty(key) || StringHelper.isEmpty(value)) {
					continue;
				}

				value = new String(
						value.getBytes(Constant.ENCODING_ISO_8859_1), encoding);

				paramsMap.put(key, value);
			}
		} catch (UnsupportedEncodingException e) {
		}

		return paramsMap;
	}
}
