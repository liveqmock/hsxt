/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.chinapay.b2c.common;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import org.apache.log4j.Logger;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.common.utils.connection.CPHttpConnection;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.common.utils.connection.CPHttpConnection.CPHttpSendResultCode;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.common.utils.connection.Http;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.common.utils.connection.HttpSSL;
import com.gy.hsxt.pg.bankadapter.common.exception.BankAdapterException;
import com.gy.hsxt.pg.bankadapter.common.utils.StringHelper;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.chinapay.b2c.common
 * 
 *  File Name       : ChinapayHttpHelper.java
 * 
 *  Creation Date   : 2015年9月28日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 中国银联Http工具类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class ChinapayHttpHelper {
	private static final Logger logger = Logger
			.getLogger(ChinapayHttpHelper.class);

	/** 默认超时时间 **/
	private static final int DEFAULT_TIMEOUT = 35000;

	/**
	 * 发送http post报文，并且接受响应信息
	 * 
	 * @param URL
	 * @param strMsg
	 * @return
	 */
	public static final String sendHttpMsg(String URL, String strMsg) {
		return sendHttpMsg(URL, strMsg, "", DEFAULT_TIMEOUT);
	}

	/**
	 * 发送http post报文，并且接受响应信息
	 * 
	 * @param URL
	 * @param strMsg
	 * @param timeOut
	 * @return
	 */
	public static final String sendHttpMsg(String URL, String strMsg,
			int timeOut) {
		return sendHttpMsg(URL, strMsg, "", timeOut);
	}

	/**
	 * 发送https post报文，并且接受响应信息 (安全性高)
	 * 
	 * @param URL
	 * @param strMsg
	 * @return
	 */
	public static final String sendHttpSSLMsg(String URL, String strMsg) {
		return sendHttpMsg(URL, strMsg, ChinapayB2cConst.HTTP_SSL,
				DEFAULT_TIMEOUT);
	}

	/**
	 * 发送https post报文，并且接受响应信息 (安全性高)
	 * 
	 * @param URL
	 * @param strMsg
	 * @param timeOut
	 * @return
	 */
	public static final String sendHttpSSLMsg(String URL, String strMsg,
			int timeOut) {
		return sendHttpMsg(URL, strMsg, ChinapayB2cConst.HTTP_SSL, timeOut);
	}

	/**
	 * 发送https post报文，并且接受响应信息 (安全性高)
	 * 
	 * @param URL
	 * @param reqParams
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static final String sendHttpSSLMsg(String URL,
			Map<String, String> reqParams) throws UnsupportedEncodingException {
		return sendHttpSSLMsg(URL, reqParams, DEFAULT_TIMEOUT);
	}

	/**
	 * 发送https post报文，并且接受响应信息 (安全性高)
	 * 
	 * @param URL
	 * @param reqParams
	 * @param timeOut
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static final String sendHttpSSLMsg(String URL,
			Map<String, String> reqParams, int timeOut)
			throws UnsupportedEncodingException {
		String strMsg = "";

		if ((null != reqParams) && (0 < reqParams.size())) {
			Set<String> keys = reqParams.keySet();

			StringBuilder strBuild = new StringBuilder();

			for (Iterator<?> it = keys.iterator(); it.hasNext();) {
				String key = (String) it.next();
				String value = StringHelper.avoidNull(reqParams.get(key));
				/* value = URLEncoder.encode(value, "UTF-8"); */

				strBuild.append(key).append("=").append(value).append("&");
			}

			int len = strBuild.length();

			strBuild.delete(len - 1, len);

			strMsg = strBuild.toString();
		}

		return sendHttpMsg(URL, strMsg, ChinapayB2cConst.HTTP_SSL, timeOut);
	}

	/**
	 * 解析为map
	 * 
	 * @param returnHtmlContent
	 * @return
	 */
	public static final Map<String, String> parseReturnStr2Map(
			String returnHtmlContent) {
		// 抓取body中的内容
		String returnedData = parseoutReturnedData(returnHtmlContent);
		Map<String, String> returnMap = new HashMap<String, String>();

		try {
			StringTokenizer tokenizer = new StringTokenizer(returnedData, "&");

			String pairs;
			String[] attribute;

			while (tokenizer.hasMoreElements()) {
				pairs = tokenizer.nextToken().trim();
				attribute = pairs.split("=");
				if (attribute != null && attribute.length == 2) {
					returnMap.put(attribute[0], attribute[1]);
				}
			}
		} catch (Exception e) {
			logger.error("响应的银联报文异常："+returnHtmlContent, e);
		}

		if (0 < returnMap.size()) {
			return returnMap;
		}
		
		return null;
	}

	/**
	 * 发送http post报文，并且接受响应信息
	 * 
	 * @param URL
	 * @param strMsg
	 *            需要发送的交易报文,格式遵循httppost参数格式
	 * @param httpType
	 * @param timeOut
	 * @return 服务器返回响应报文,如果处理失败，返回空字符串
	 */
	private static String sendHttpMsg(String URL, String strMsg,
			String httpType, int timeOut) {
		String returnMsg = "";
		String strTimeout = String.valueOf(timeOut);

		CPHttpConnection httpSend = null;

		if (ChinapayB2cConst.HTTP_SSL.equals(httpType)) {
			httpSend = new HttpSSL(URL, strTimeout);
		} else {
			httpSend = new Http(URL, strTimeout);
		}

		// 设置获得响应结果的限制
		httpSend.setLenType(0);

		// 设置字符编码
		httpSend.setMsgEncoding("GBK");

		// 发送Http(s)请求
		int returnCode = httpSend.sendMsg(strMsg);

		// 解析银联返回结果
		if (CPHttpSendResultCode.SUCCESS == returnCode) {
			try {
				returnMsg = new String(httpSend.getReceiveData(), "GBK").trim();
			} catch (UnsupportedEncodingException e) {
				logger.error("[getReceiveData Error!]");
			}
		} else {
			logger.error(new StringBuilder(
					"process message error, returnCode=[").append(returnCode)
					.append("]").toString());
		}

		return returnMsg;
	}

	/**
	 * 组装请求银行接口的URL地址
	 * 
	 * @param bankUrl
	 * @param paramsMap
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String assembleBankUrl(String bankUrl,
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
	 * 抽取响应的报文实际内容,去掉body标签
	 * 
	 * @param returnHtmlContent
	 * @return
	 */
	private static String parseoutReturnedData(String returnHtmlContent) {
		if (StringHelper.isEmpty(returnHtmlContent)) {
			throw new BankAdapterException("操作失败，原因：中国银联返回报文异常!");
		}

		// 提取内容
		// 抓取body中的内容
		/*
		 * String returnStr = returnHtmlContent.replaceAll(
		 * "^((.*)(\\<body\\>(\\s*)))|((\\s*)(\\</body\\>(.*)))$", "") .trim();
		 */
		String returnStr = null;
		
		if (returnHtmlContent.indexOf("<body>") != -1) {
			returnStr = returnHtmlContent.substring(
					returnHtmlContent.indexOf("<body>") + 6,
					returnHtmlContent.indexOf("</body>")).trim();
		} else {
			returnStr = returnHtmlContent;
		}
		
		return returnStr;
	}
	public static void main(String args[]){
		String contentString="MerId=808080112594511&GateId=8619&TransType=0001&OrdId=0115111071576084&TransDate=20151110&SubTransType=02&RespCode=000&ChkValue=03DD2413E94D18887EBA34E3C72586C3ED62A522C42BA1F2F8D6BD9BFB76D418B272D9406423B587412A4B82F647CF1F25D051B2436DE3CE1E19C66E93F68DD4B0FC1446831526ACED9AA390E68742E2E5CCA52B5E61FD08A113643A948E57FE93093F0E55552664568EEE9B76F5B6581B0371CCB2797EA0E022D2F52F4BDF6F&PhoneTail=5506";
		Map<String, String> responseDatas=parseReturnStr2Map(contentString);
		System.out.println(responseDatas);
	}
}
