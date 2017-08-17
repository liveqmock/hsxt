/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.common.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/***************************************************************************
 * <PRE>
 *  Project Name    : bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.common.utils
 * 
 *  File Name       : HttpClientHelper.java
 * 
 *  Creation Date   : 2014-10-28
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : Http client
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class HttpClientHelper {
	
	private static final Logger logger = Logger.getLogger(HttpClientHelper.class);

	/**
	 * 发送http post请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String doPost(String url, Map<String, String> params)
			throws Exception {

		// 创建默认的httpClient实例
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);

		List<NameValuePair> formParams = new ArrayList<NameValuePair>(5);

		Iterator<String> itor = params.keySet().iterator();

		String key;
		String value;

		while (itor.hasNext()) {
			key = itor.next();
			value = params.get(key);

			formParams.add(new BasicNameValuePair(key, value));
		}

		try {
			httpPost.setEntity(new UrlEncodedFormEntity(formParams));
			CloseableHttpResponse response = httpClient.execute(httpPost);

			int statusCode = response.getStatusLine().getStatusCode();

			if (HttpStatus.SC_OK == statusCode) {
				return EntityUtils.toString(response.getEntity());
			}
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
			}
		}

		return "";
	}

	/**
	 * 发送http get请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String doGet(String url, Map<String, String> params)
			throws Exception {
		// 创建默认的httpClient实例
		CloseableHttpClient httpClient = HttpClients.createDefault();

		StringBuilder queryStr = new StringBuilder(url.endsWith("?") ? "" : "?");
		Iterator<String> itor = params.keySet().iterator();

		String key;
		String value;

		while (itor.hasNext()) {
			key = itor.next();
			value = params.get(key);
			queryStr.append(key).append("=").append(value).append("&");
		}

		// 取得结尾的&符号
		queryStr.replace(queryStr.length() - 1, queryStr.length(), "");

		HttpGet httpGet = new HttpGet(url + queryStr.toString());

		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);

			int statusCode = response.getStatusLine().getStatusCode();

			if (HttpStatus.SC_OK == statusCode) {
				return EntityUtils.toString(response.getEntity());
			}
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
			}
		}

		return "";
	}

	/**
	 * 发送http get请求
	 * 
	 * @param url
	 * @param queryString
	 * @return
	 * @throws Exception
	 */
	public static String doGet(String url, String queryString) {

		queryString = StringHelper.avoidNull(queryString);

		if (!StringHelper.isEmpty(queryString) && !queryString.startsWith("?")) {
			queryString = "?".concat(queryString);
		}

		// 创建默认的httpClient实例
		CloseableHttpClient httpClient = HttpClients.createDefault();

		try {
			HttpGet httpGet = new HttpGet(url + queryString);

			CloseableHttpResponse response = httpClient.execute(httpGet);

			int statusCode = response.getStatusLine().getStatusCode();

			if (HttpStatus.SC_OK == statusCode) {
				return EntityUtils.toString(response.getEntity());
			}
		} catch (Exception e) {
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
			}
		}

		return "";
	}
}
