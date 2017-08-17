/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.common.utils;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.gy.hsxt.pg.bankadapter.common.constants.BanksConstants;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.common.utils
 * 
 *  File Name       : HttpRequestParamHelper.java
 * 
 *  Creation Date   : 2016年6月27日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : http请求参数工具类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class HttpRequestParamHelper {
	/**
	 * 组装请求银行接口的URL地址
	 * 
	 * @param bankUrl
	 * @param paramsMap
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String assembleHttpReqUrl(String bankUrl,
			Map<String, String> paramsMap) throws UnsupportedEncodingException {
		if (!bankUrl.startsWith("http://") && !bankUrl.startsWith("https://")) {
			bankUrl = "http://" + bankUrl;
		}

		if ((null != paramsMap) && (0 < paramsMap.size())) {
			String key;
			String value;

			Set<String> keys = paramsMap.keySet();

			StringBuilder strBuild = new StringBuilder();
			strBuild.append(bankUrl);

			// 配置的地址是否以问号?结束
			if (!bankUrl.endsWith("?")) {
				strBuild.append("?");
			}

			for (Iterator<?> it = keys.iterator(); it.hasNext();) {
				key = (String) it.next();

				value = StringHelper.avoidNull(paramsMap.get(key));
				/* value = URLEncoder.encode(value, "UTF-8"); */

				if (!StringHelper.isEmpty(value)) {
					strBuild.append(key).append("=").append(value).append("&");
				}
			}

			int len = strBuild.length();

			strBuild.delete(len - 1, len);

			bankUrl = strBuild.toString();
		}

		return bankUrl;
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

		if (StringHelper.isEmpty(encoding)) {
			encoding = BanksConstants.CHARSET_UTF8;
		}

		Map<String, String> paramsMap = new HashMap<String, String>(2);

		try {
			request.setCharacterEncoding(BanksConstants.ENCODING_ISO_8859_1);

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
						value.getBytes(BanksConstants.ENCODING_ISO_8859_1),
						encoding);

				paramsMap.put(key, value);
			}
		} catch (UnsupportedEncodingException e) {
		}

		return paramsMap;
	}

}
