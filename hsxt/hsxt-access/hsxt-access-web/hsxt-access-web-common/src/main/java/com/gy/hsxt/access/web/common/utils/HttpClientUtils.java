/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.common.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;

/**
 * http请求
 * 
 * @Package: com.gy.hsxt.access.web.common.utils
 * @ClassName: HttpClient
 * @Description:
 * @author: likui
 * @date: 2016年1月15日 下午8:43:28
 * @company: gyist
 * @version V3.0.0
 */
public class HttpClientUtils {

	/**
	 * http GET请求
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月16日 上午9:12:49
	 * @param url
	 * @return
	 * @throws HsException
	 * @return : String
	 * @version V3.0.0
	 */
	public static String get(String url) throws HsException {
		BizLog.biz(HttpClientUtils.class.getName(), "get", "params==>" + url,
				"发起http GET请求");
		String responseBody = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();

		HttpGet httpGet = new HttpGet(url);
		try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				responseBody = EntityUtils.toString(entity,
						StandardCharsets.UTF_8);
			}
			response.close();
		} catch (Exception e) {
			SystemLog.error(HttpClientUtils.class.getName(), "get",
					"http GET请求失败", e);
			throw new HsException(RespCode.FAIL);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return responseBody;
	}

	/**
	 * POS请求
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月16日 上午9:08:36
	 * @param url
	 * @return
	 * @throws HsException
	 * @return : String
	 * @version V3.0.0
	 */
	public static String post(String url) throws HsException {
		return post(url, null);
	}

	/**
	 * 发送POST请求
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月16日 上午9:08:51
	 * @param url
	 * @param paramMap
	 * @return
	 * @throws HsException
	 * @return : String
	 * @version V3.0.0
	 */
	public static String post(String url, Map<String, String> paramMap)
			throws HsException {
		return post(url, paramMap, null);
	}

	/**
	 * 发送POST请求
	 * 
	 * @param uri
	 * @param paramMap
	 *            请求参数
	 * @param charset
	 *            参数编码
	 * @return
	 */
	public static String post(String url, Map<String, String> paramMap,
			String charset) throws HsException {
		BizLog.biz(HttpClientUtils.class.getName(), "post", "params==>" + url,
				"发起http POST请求");
		String responseBody = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpPost httpPost = new HttpPost(url);

			if (paramMap != null) {
				List<NameValuePair> nvps = new ArrayList<NameValuePair>(
						paramMap.size());
				for (Map.Entry<String, String> entry : paramMap.entrySet()) {
					NameValuePair nvp = new BasicNameValuePair(entry.getKey(),
							entry.getValue());
					nvps.add(nvp);
				}
				if (charset != null) {
					httpPost.setEntity(new UrlEncodedFormEntity(nvps, charset));
				} else {
					httpPost.setEntity(new UrlEncodedFormEntity(nvps));
				}
			}
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			responseBody = httpClient.execute(httpPost, responseHandler);
			SystemLog.info(HttpClientUtils.class.getName(), "POST", "resp:"
					+ responseBody);
		} catch (Exception ex) {
			SystemLog.error(HttpClientUtils.class.getName(), "post",
					"http POST请求失败", ex);
			throw new HsException(RespCode.FAIL);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseBody;
	}

	public static void main(String[] args) {
		String url = "http://192.168.229.24:8080/hsxt-access-web-official/gy/official/entHandle/query_all_ent_info_s";
		System.out.println(get(url));
	}
}
