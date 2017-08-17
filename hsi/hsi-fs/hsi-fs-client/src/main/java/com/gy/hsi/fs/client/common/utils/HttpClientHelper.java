/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.client.common.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.gy.hsi.fs.common.constant.FsApiConstant.FileOptResultCode;
import com.gy.hsi.fs.common.constant.FsApiConstant.FsRespHeaderKey;
import com.gy.hsi.fs.common.exception.FsException;
import com.gy.hsi.fs.common.exception.FsServerNotAvailableException;
import com.gy.hsi.fs.common.utils.StringUtils;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-client
 * 
 *  Package Name    : com.gy.hsi.fs.client.common.utils
 * 
 *  File Name       : HttpClientHelper.java
 * 
 *  Creation Date   : 2015-5-28
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
	/** 缓冲区大小 **/
	private static final int BUFFER_SIZE = 8096;

	/** 最大迭代次数 **/
	private static final int MAX_LOOP_COUNTS = 5;

	/**
	 * 发送http post请求
	 * 
	 * @param url
	 * @param params
	 * @param timeout
	 * @return
	 * @throws Exception
	 */
	public static String doPost(String url, Map<String, String> params,
			int timeout) throws Exception {

		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(timeout).setSocketTimeout(timeout).build();

		// 创建默认的httpClient实例
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url);
		httppost.setConfig(requestConfig);

		List<NameValuePair> formparams = new ArrayList<NameValuePair>();

		Iterator<String> itor = params.keySet().iterator();

		String key;
		String value;

		while (itor.hasNext()) {
			key = itor.next();
			value = StringUtils.avoidNull(params.get(key));

			if (StringUtils.isEmpty(key)) {
				continue;
			}

			formparams.add(new BasicNameValuePair(key, value));
		}

		try {
			httppost.setEntity(new UrlEncodedFormEntity(formparams));
			CloseableHttpResponse response = httpclient.execute(httppost);

			int statusCode = response.getStatusLine().getStatusCode();

			if (HttpStatus.SC_OK == statusCode) {
				return EntityUtils.toString(response.getEntity());
			}
		} catch (HttpHostConnectException e) {
			throw new FsServerNotAvailableException(
					"Very bad, the FS server is not available!");
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				httpclient.close();
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
	 * @param timeout
	 * @return
	 * @throws Exception
	 */
	public static String doGet(String url, Map<String, String> params,
			int timeout) throws Exception {
		// 创建默认的httpClient实例
		CloseableHttpClient httpclient = HttpClients.createDefault();

		StringBuilder queryStr = new StringBuilder(url.endsWith("?") ? "" : "?");
		Iterator<String> itor = params.keySet().iterator();

		String key;
		String value;

		while (itor.hasNext()) {
			key = itor.next();
			value = StringUtils.avoidNull(params.get(key));

			if (StringUtils.isEmpty(key)) {
				continue;
			}

			queryStr.append(key).append("=").append(value).append("&");
		}

		// 取得结尾的&符号
		queryStr.replace(queryStr.length() - 1, queryStr.length(), "");

		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(timeout).setSocketTimeout(timeout).build();

		HttpGet httpGet = new HttpGet(url + queryStr.toString());
		httpGet.setConfig(requestConfig);

		try {
			CloseableHttpResponse response = httpclient.execute(httpGet);

			int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode == HttpStatus.SC_OK) {
				return EntityUtils.toString(response.getEntity());
			}
		} catch (HttpHostConnectException ex) {
			throw new FsServerNotAvailableException(
					"Connecting FS server failed!", ex);
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
			}
		}

		return "";
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
	 * 将HTTP资源另存为文件
	 * 
	 * @param remoteUrl
	 * @param localFilePath
	 * @param timeout
	 * @return
	 * @throws IOException
	 * @throws FsException
	 */
	public static boolean download(String remoteUrl, String localFilePath,
			int timeout) throws IOException, FsException {
		byte[] buf = new byte[BUFFER_SIZE];
		int size = 0;

		// http连接对象
		HttpURLConnection httpUrl = null;

		// 获取网络输入流
		BufferedInputStream bis = null;

		// 建立文件
		FileOutputStream fos = null;

		try {
			// 建立链接
			URL url = new URL(remoteUrl);
			httpUrl = (HttpURLConnection) url.openConnection();

			httpUrl.setConnectTimeout(timeout);
			httpUrl.setReadTimeout(timeout);

			// 连接指定的资源
			httpUrl.connect();

			// 校验响应码
			checkResponse(httpUrl);

			// 创建目录
			createDirectoryIfNotExist(localFilePath);

			bis = new BufferedInputStream(httpUrl.getInputStream());
			fos = new FileOutputStream(localFilePath);

			// 保存文件
			while (-1 != (size = bis.read(buf))) {
				fos.write(buf, 0, size);
			}

			return checkFileExist(localFilePath, 0);
		} finally {
			try {
				if (null != fos) {
					fos.close();
				}

				if (null != bis) {
					bis.close();
				}

				if (null != httpUrl) {
					httpUrl.disconnect();
				}
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 创建目录
	 * 
	 * @param localFilePath
	 */
	private static void createDirectoryIfNotExist(String localFilePath) {
		File file = new File(localFilePath);
		File directory = new File(file.getParent());

		if (!directory.exists()) {
			directory.mkdirs();
		}
	}

	/**
	 * 校验响应码
	 * 
	 * @param httpUrl
	 * @throws FsException
	 */
	private static void checkResponse(HttpURLConnection httpUrl)
			throws FsException {
		// 结果响应码
		int respCode = StringUtils.str2Int(httpUrl
				.getHeaderField(FsRespHeaderKey.RESULT_CODE));

		if (!FileOptResultCode.OPT_SUCCESS.valueEquals(respCode)) {
			throw new FsException(respCode,
					httpUrl.getHeaderField(FsRespHeaderKey.RESULT_DESC));
		}
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @param localFilePath
	 * @param loopCounts
	 * @return
	 */
	private static boolean checkFileExist(String localFilePath, int loopCounts) {
		File downloadFile = new File(localFilePath);

		if (downloadFile.isFile() && downloadFile.exists()) {
			return true;
		}

		supendAmoment(1500);

		// 最大迭代5次
		if (MAX_LOOP_COUNTS <= loopCounts) {
			return false;
		}

		return checkFileExist(localFilePath, loopCounts++);
	}

	/**
	 * 悬起一会
	 * 
	 * @param timeMills
	 */
	private static void supendAmoment(int timeMills) {
		try {
			Thread.sleep(timeMills);
		} catch (InterruptedException e) {
		}
	}
}
