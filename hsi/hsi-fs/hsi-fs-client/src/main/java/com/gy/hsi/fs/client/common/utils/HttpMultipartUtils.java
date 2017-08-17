/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.client.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.gy.hsi.fs.common.utils.StringUtils;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-client
 * 
 *  Package Name    : com.gy.hsi.fs.client.common.utils
 * 
 *  File Name       : HttpMultipartUtils.java
 * 
 *  Creation Date   : 2015年5月29日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : http上传文件工具类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class HttpMultipartUtils {

	/**
	 * 上传文件
	 * 
	 * @param httpUrl
	 * @param files
	 * @param textParams
	 * @param timeout
	 * @return
	 * @throws Exception
	 */
	public static String doMultipartPost(String httpUrl, File[] files,
			Map<String, String> textParams, int timeout) throws Exception {
		try {
			return handleMultipartPost(httpUrl, files, textParams, timeout);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 上传文件
	 * 
	 * @param httpUrl
	 * @param file
	 * @param textParams
	 * @param timeout
	 * @return
	 * @throws Exception
	 */
	public static String doMultipartPost(String httpUrl, File file,
			Map<String, String> textParams, int timeout) throws Exception {
		try {
			return handleMultipartPost(httpUrl, new File[] { file },
					textParams, timeout);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 上传文件
	 * 
	 * @param httpUrl
	 * @param localFileNames
	 * @param textParams
	 * @param timeout
	 * @return
	 * @throws Exception
	 */
	public static String doMultipartPost(String httpUrl,
			String[] localFileNames, Map<String, String> textParams, int timeout)
			throws Exception {
		try {
			File[] files = new File[localFileNames.length];

			for (int i = 0; i < localFileNames.length; i++) {
				files[i] = new File(localFileNames[i]);
			}

			return handleMultipartPost(httpUrl, files, textParams, timeout);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 上传文件
	 * 
	 * @param httpUrl
	 * @param localFileName
	 * @param textParams
	 * @param timeout
	 * @return
	 * @throws Exception
	 */
	public static String doMultipartPost(String httpUrl, String localFileName,
			Map<String, String> textParams, int timeout) throws Exception {
		try {
			File file = new File(localFileName);

			return handleMultipartPost(httpUrl, new File[] { file },
					textParams, timeout);
		} catch (IOException e) {
			throw e;
		}
	}

	/**
	 * 下载文件
	 * 
	 * @param httpUrl
	 * @param localFileName
	 * @param timeout
	 * @return
	 * @throws Exception
	 */
	public static boolean downloadFile(String httpUrl, String localFileName,
			int timeout) throws Exception {
		// 建立文件
		FileOutputStream fos = null;

		try {
			return HttpClientHelper.download(httpUrl, localFileName, timeout);
		} catch (IOException e) {
			throw new Exception("Failed to download file! httpUrl=" + httpUrl,
					e);
		} finally {
			if (null != fos) {
				try {
					fos.close();
				} catch (IOException e) {
				}
			}
		}
	}

	/**
	 * 上传文件
	 * 
	 * @param httpUrl
	 * @param files
	 * @param textParams
	 * @param timeout
	 * @return
	 * @throws IOException
	 */
	private static String handleMultipartPost(String httpUrl, File[] files,
			Map<String, String> textParams, int timeout) throws IOException {
		HttpEntity responseEntity = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();

		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(timeout).setSocketTimeout(timeout).build();

		try {
			MultipartEntityBuilder entityBuilder = MultipartEntityBuilder
					.create();

			if (null != files) {
				int index = 0;

				for (File f : files) {
					entityBuilder.addPart(String.valueOf(index++),
							new FileBody(f));
				}
			}

			if (null != textParams) {
				Entry<String, String> entry;

				Iterator<Entry<String, String>> itor = textParams.entrySet()
						.iterator();
				String key;
				String value;

				while (itor.hasNext()) {
					entry = itor.next();
					key = StringUtils.avoidNull(entry.getKey());
					value = StringUtils.avoidNull(entry.getValue());

					if (StringUtils.isEmpty(key)) {
						continue;
					}

					entityBuilder.addTextBody(key, value,
							ContentType.TEXT_PLAIN);
				}
			}

			HttpPost httpPost = new HttpPost(httpUrl);
			httpPost.setConfig(requestConfig);
			httpPost.setEntity(entityBuilder.build());

			// 执行请求
			CloseableHttpResponse response = httpclient.execute(httpPost);

			// 请求响应码
			int respCode = response.getStatusLine().getStatusCode();

			try {
				if (HttpStatus.SC_OK == respCode) {
					responseEntity = response.getEntity();

					InputStream in = (null != responseEntity) ? responseEntity
							.getContent() : null;

					return parseResponseInputStream(in);
				}
			} finally {
				if (null != responseEntity) {
					EntityUtils.consume(responseEntity);
				}

				response.close();
			}
		} catch (FileNotFoundException ex) {
			throw ex;
		} finally {
			httpclient.close();
		}

		throw new IOException("HTTP request exception!");
	}

	/**
	 * 解析响应的输入流
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	private static String parseResponseInputStream(InputStream in)
			throws IOException {
		if (null == in) {
			throw new IOException("The response stream is null!");
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		String line = null;
		StringBuilder sb = new StringBuilder();

		while ((line = br.readLine()) != null) {
			sb.append(line);
		}

		return sb.toString();
	}
}
