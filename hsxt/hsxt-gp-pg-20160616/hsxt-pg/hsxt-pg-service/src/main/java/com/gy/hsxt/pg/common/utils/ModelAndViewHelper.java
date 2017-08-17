/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.common.utils;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.pg.bankadapter.common.utils.HttpClientHelper;
import com.gy.hsxt.pg.common.bean.RequestProxyData;
import com.gy.hsxt.pg.common.constant.Constant;
import com.gy.hsxt.pg.common.constant.Constant.SessionKey;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.common.utils
 * 
 *  File Name       : ModelAndViewHelper.java
 * 
 *  Creation Date   : 2015-11-10
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 模型和视图帮助类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/

public class ModelAndViewHelper {
	/**
	 * 将请求转发至下一个前台页面url地址(页面形式, 外部网址)
	 * 
	 * @param request
	 * @param response
	 * @param paramsMap
	 * @param jumpUrl
	 *            (相对路径, 且不带jsp后缀)
	 * @param message
	 *            界面提示信息
	 * @return
	 */
	public static ModelAndView transRequest2NextJumpUrl(
			HttpServletRequest request, HttpServletResponse response,
			Map<String, String> paramsMap, String jumpUrl, String message) {
		// 重要！！！！
		if (StringUtils.isEmpty(jumpUrl)) {
			return null;
		}

		if (!jumpUrl.startsWith("http://") && !jumpUrl.startsWith("https://")) {
			jumpUrl = "http://" + jumpUrl;
		}
		
		// 设置编码格式
		try {
			request.setCharacterEncoding(Constant.ENCODING_UTF8);
			response.setContentType("text/html;charset=UTF-8");
		} catch (UnsupportedEncodingException e) {
		}

		String requestMethod = "get"; // request.getMethod()

		// 组装form html脚本
		String formHtml = assembleFormHtml(jumpUrl, paramsMap, requestMethod);

		// 将脚本存入session
		request.getSession().setAttribute(SessionKey.REQUEST_PROXY_DATA,
				new RequestProxyData(formHtml, message));

		return new ModelAndView("/request_proxy");
	}

	/**
	 * 将请求转发至下一个背景url地址(非页面,后台请求/通知方式, 内部地址, 不暴露)
	 * 
	 * @param req
	 * @param resp
	 * @param paramsMap
	 * @param notifyUrl
	 * @throws HsException
	 */
	public static void transRequest2ProxyNotifyUrl(HttpServletRequest req,
			HttpServletResponse resp, Map<String, String> paramsMap,
			String notifyUrl) throws HsException {
		if (StringUtils.isEmpty(notifyUrl)) {
			return;
		}

		try {
			String resultStr = HttpClientHelper.doPost(notifyUrl, paramsMap);

			PrintWriter pw = resp.getWriter();
			pw.write(resultStr);
		} catch (Exception e) {
			throw new HsException(RespCode.FAIL.getCode(), e.getMessage());
		}
	}

	/**
	 * 转向到错误页面
	 * 
	 * @param request
	 * @param errorMsg
	 * @return
	 */
	public static ModelAndView forward2ErrorView(HttpServletRequest request,
			String errorMsg) {
		String message = errorMsg;

		if (StringUtils.isEmpty(message)) {
			message = "非法请求！";
		}

		request.getSession()
				.setAttribute(SessionKey.ILLEGAL_REQ_ERROR, message);

		return new ModelAndView("/error");
	}

	/**
	 * 转向到结果页面
	 * 
	 * @param request
	 * @param response
	 * @param resultJson
	 * @return
	 */
	public static ModelAndView forward2ResultView(HttpServletRequest request,
			String resultJson) {
		// 将结果信息存入session
		request.getSession().setAttribute(SessionKey.REQUEST_RESULT_DATA,
				new RequestProxyData("", resultJson));

		return new ModelAndView("/result_page");
	}

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

			paramsMap.put(key, value);
		}

		return paramsMap;
	}

	/**
	 * 组装form html
	 * 
	 * @param actionUrl
	 * @param paramsMap
	 * @param method
	 * @return
	 */
	private static String assembleFormHtml(String actionUrl,
			Map<String, String> paramsMap, String method) {
		String key;
		String value;

		String reqMethod = ("get".equalsIgnoreCase(method)) ? method
				.toLowerCase() : "post";

		StringBuilder strBuild = new StringBuilder();
		strBuild.append("<form id=\"pform\" action=\"").append(actionUrl)
				.append("\" method=\"").append(reqMethod).append("\">");

		if ((null != paramsMap) && (0 < paramsMap.size())) {
			Set<String> keys = paramsMap.keySet();

			for (Iterator<?> it = keys.iterator(); it.hasNext();) {
				key = (String) it.next();
				value = StringUtils.avoidNull(paramsMap.get(key));

				strBuild.append("<input type=\"hidden\" name=\"").append(key)
						.append("\" value=\"").append(value).append("\" />");
			}
		}

		strBuild.append("</form>");

		strBuild.append("\n <script type=\"text/javascript\" language=\"javascript\">");
		strBuild.append("\n document.getElementById(\"pform\").submit();");
		strBuild.append("\n </script>");

		return strBuild.toString();
	}
}
