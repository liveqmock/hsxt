package com.gy.hsxt.point.client.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * 
 * @Package: com.gy.point.client.http
 * @ClassName: HttpRequestor
 * 
 * @author: liyh
 * @date: 2015-10-19 下午4:45:27
 * @version V3.0
 */
public class HttpRequestor {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(HttpRequestor.class);

	/** 字符编码 **/

	private String charset = "UTF-8";
	/** 连接超时 **/
	private Integer connectTimeout = null;
	/** socket连接超时 **/
	private Integer socketTimeout = null;
	/** 代理主机 **/
	private String proxyHost = null;
	/** 代理端口 **/
	private Integer proxyPort = null;

	/**
	 * doGet请求方法
	 * 
	 * @param url
	 *            请求URL
	 * @return
	 * @throws Exception
	 */
	public String doGet(String url) throws Exception {
		URL localURL = new URL(url);
		// 打开连接
		URLConnection connection = openConnection(localURL);
		HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
		// 设置字符编码
		httpURLConnection.setRequestProperty("Accept-Charset", charset);
		// 设置类型
		httpURLConnection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		StringBuffer resultBuffer = new StringBuffer();
		String tempLine = null;
		// 返回状态码大于300，为异常
		if (httpURLConnection.getResponseCode() >= 300) {
			throw new Exception(
					"HTTP Request is not success, Response code is "
							+ httpURLConnection.getResponseCode());
		}
		try {
			// 获的输入流
			inputStream = httpURLConnection.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream);
			reader = new BufferedReader(inputStreamReader);
			// 按行读取数据
			while ((tempLine = reader.readLine()) != null) {
				resultBuffer.append(tempLine);
			}
		} finally {
			// 关闭读取流
			if (reader != null) {
				reader.close();
			}
			// 关闭输入流
			if (inputStreamReader != null) {
				inputStreamReader.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
		}

		return resultBuffer.toString();
	}

	/**
	 * POST请求
	 * 
	 * @param url
	 *            请求URL
	 * @param parameterMap
	 *            参数Map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "finally", "deprecation" })
	public JSONObject doPost(String url, Map<String, Object> parameterMap)
			throws Exception {
		// 参数字符buffer
		StringBuffer parameterBuffer = new StringBuffer();
		// 判断参数MAP是否为空
		if (parameterMap != null) {
			// 迭代
			Iterator<String> iterator = parameterMap.keySet().iterator();
			// 键
			String key = null;
			// 值
			Object value = null;
			// 遍历参数Map
			while (iterator.hasNext()) {
				// 获取键
				key = (String) iterator.next();
				// 根据键获取值
				if (parameterMap.get(key) != null) {
					value = parameterMap.get(key);
				}
				// 拼接参数
				value = java.net.URLEncoder.encode(value.toString());
				parameterBuffer.append(key).append("=").append(value);
				if (iterator.hasNext()) {
					parameterBuffer.append("&");
				}
			}
		}
		System.out.println("POST 输入参数 : " + parameterBuffer.toString());

		// System.out.println("POST 输入参数 : " + parameterStr);
		// 获取连接URL
		URL localURL = new URL(url);
		// 打开连接，获取连接对象
		URLConnection connection = openConnection(localURL);
		HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

		// 发送POST请求必须设置如下两行
		httpURLConnection.setDoOutput(true);
		httpURLConnection.setDoInput(true);
		// 设置请求类型为post
		httpURLConnection.setRequestMethod("POST");

		// 设置字符编码
		httpURLConnection.setRequestProperty("Accept-Charset", charset);
		httpURLConnection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded;charset=UTF-8");
		// 设置参数
		httpURLConnection.setRequestProperty("Content-Length",
				String.valueOf(parameterBuffer.length()));
		OutputStream outputStream = null;
		OutputStreamWriter outputStreamWriter = null;
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		JSONObject jsonObject = null;
		JSONObject jsonObject2 = null;
		try {
			// 获取输出流
			outputStream = httpURLConnection.getOutputStream();

			outputStreamWriter = new OutputStreamWriter(outputStream);
			outputStreamWriter.write(parameterBuffer.toString());
			outputStreamWriter.flush();
			@SuppressWarnings("unused")
			StringBuffer resultBufferExceptions = new StringBuffer();
			inputStream = httpURLConnection.getInputStream();
			// 把输入流转换成字符数组
			byte[] data = readStream(inputStream);
			// 把字符数组转换成字符串
			String json = new String(data, charset);
			jsonObject = JSONObject.parseObject(json);
			String retCode = jsonObject.getString("retCode");// 22000
			System.out.println("返回值：" + jsonObject);
			String resultDesc = null;
			// 正常返回
			if (!retCode.equals("22000")) {
				jsonObject2 = new JSONObject();
				resultDesc = jsonObject.getString("resultDesc");
				jsonObject2.put("resultDesc", resultDesc);
				jsonObject = jsonObject2;
			}
		} catch (Exception e) {
			jsonObject2 = new JSONObject();
			jsonObject2.put("resultDesc", "连接后台服务异常，请确认配置信息是否正确，后台服务是否正常启动;  "+e.getMessage());
			jsonObject = jsonObject2;
			e.printStackTrace();
			LOGGER.error("接入层服务端异常，返回数据错误：" + e);
		} finally {
			if (outputStreamWriter != null) {
				outputStreamWriter.close();
			}
			if (outputStream != null) {
				outputStream.close();
			}
			if (reader != null) {
				reader.close();
			}
			if (inputStreamReader != null) {
				inputStreamReader.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
			return jsonObject;
		}
	}

	/**
	 * 打开连接
	 * 
	 * @param localURL
	 *            URL
	 * @return
	 * @throws IOException
	 */
	private URLConnection openConnection(URL localURL) throws IOException {
		URLConnection connection;
		if (proxyHost != null && proxyPort != null) {
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
					proxyHost, proxyPort));
			connection = localURL.openConnection(proxy);
		} else {
			connection = localURL.openConnection();
		}
		return connection;
	}

	/**
	 * 设置请求
	 * 
	 * @param connection
	 */
	@SuppressWarnings("unused")
	private void renderRequest(URLConnection connection) {
		if (connectTimeout != null) {
			connection.setConnectTimeout(connectTimeout);
		}
		if (socketTimeout != null) {
			connection.setReadTimeout(socketTimeout);
		}

	}

	/**
	 * 读数据流
	 * 
	 * @param inputStream
	 *            输入流
	 * @return
	 * @throws Exception
	 */
	public static byte[] readStream(InputStream inputStream) throws Exception {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inputStream.read(buffer)) != -1) {
			bout.write(buffer, 0, len);
		}
		bout.close();
		inputStream.close();
		return bout.toByteArray();
	}

	public static byte[] readStreams(InputStream inputStream) throws Exception {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inputStream.read(buffer)) != -1) {
			bout.write(buffer, 0, len);
		}
		bout.close();
		inputStream.close();
		return bout.toByteArray();
	}

	/**
	 * @return the 字符编码
	 */
	public String getCharset() {
		return charset;
	}

	/**
	 * @param 字符编码
	 *            the charset to set
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}

	/**
	 * @return the 连接超时
	 */
	public Integer getConnectTimeout() {
		return connectTimeout;
	}

	/**
	 * @param 连接超时
	 *            the connectTimeout to set
	 */
	public void setConnectTimeout(Integer connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	/**
	 * @return the socket连接超时
	 */
	public Integer getSocketTimeout() {
		return socketTimeout;
	}

	/**
	 * @param socket连接超时
	 *            the socketTimeout to set
	 */
	public void setSocketTimeout(Integer socketTimeout) {
		this.socketTimeout = socketTimeout;
	}

	/**
	 * @return the 代理主机
	 */
	public String getProxyHost() {
		return proxyHost;
	}

	/**
	 * @param 代理主机
	 *            the proxyHost to set
	 */
	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	/**
	 * @return the 代理端口
	 */
	public Integer getProxyPort() {
		return proxyPort;
	}

	/**
	 * @param 代理端口
	 *            the proxyPort to set
	 */
	public void setProxyPort(Integer proxyPort) {
		this.proxyPort = proxyPort;
	}

}