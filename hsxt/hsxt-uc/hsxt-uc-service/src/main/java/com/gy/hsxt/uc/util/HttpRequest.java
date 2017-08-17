/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @Package: com.gy.hsxt.uc.util
 * @ClassName: HttpRequest
 * @Description: TODO
 * 
 * @author: lvyan
 * @date: 2016-5-12 上午10:42:52
 * @version V1.0
 */
public class HttpRequest {
	private static final String baiduMapUrl = "http://api.map.baidu.com/geocoder/v2/";
	private static final String ak = "DE62882740f1ba3905be84a1d20a1e9f";

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) {
		HttpURLConnection httpURLConnection = null;
		BufferedReader in = null;
		try {
			StringBuilder result = new StringBuilder();
			String urlNameString = getFullUrl(url, param);
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			httpURLConnection = (HttpURLConnection) connection;
			// 设置通用的请求属性
			connection.setConnectTimeout(300000);
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();

			// 获得服务器响应的结果和状态码
			int responseCode = httpURLConnection.getResponseCode();
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
			if (responseCode == 200) {
				return result.toString();
			} else {
				System.out.println(responseCode);
				System.out.println(result);
				// 获取所有响应头字段
				Map<String, List<String>> map = connection.getHeaderFields();
				// 遍历所有的响应头字段
				for (String key : map.keySet()) {
					System.out.println(key + "--->" + map.get(key));
				}
			}
		} catch (Exception e) {
			System.err.println("发送GET[" + url + "],[" + param + "]请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (httpURLConnection != null) {
				try {
					httpURLConnection.disconnect();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public static String getFullUrl(String url, String param) {
		if (url == null) {
			return null;
		} else if (param == null) {
			return url;
		}
		if (url.indexOf("?") > 1) {
			return url + "&" + param;
		} else {
			return url + "?" + param;
		}

	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		HttpURLConnection httpURLConnection = null;
		try {
			StringBuilder result = new StringBuilder();
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			httpURLConnection = (HttpURLConnection) conn;
			// 设置通用的请求属性
			conn.setConnectTimeout(300000);
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 获得服务器响应的结果和状态码
			int responseCode = httpURLConnection.getResponseCode();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
			if (responseCode == 200) {
				return result.toString();
			} else {
				System.out.println(responseCode);
				System.out.println(result);
			}
		} catch (Exception e) {
			System.err.println("发送 POST[" + url + "],[" + param + "]请求出现异常！"
					+ e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (httpURLConnection != null) {
				try {
					httpURLConnection.disconnect();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
		return null;
	}

	/**
	 * 获取百度地图位置
	 * 
	 * @param address
	 *            详细地址
	 * @param city
	 *            城市名称
	 * @return JSONObject as
	 *         {"result":{"confidence":80,"level":"道路","location":{
	 *         "lat":22.550696775819
	 *         ,"lng":114.07607551346},"precise":1},"status":0}
	 */
	public static JSONObject getLocationByBaiduMapApi(String address,
			String city) {

		try {
			// log.debug("###########################################");
			// log.debug("尝试获取经纬度信息,address:"+address+",city:"+city);
			// JSONObject params = new JSONObject();
			// params.put("output", "json");
			// params.put("ak", ak);
			// params.put("address", address);
			// params.put("city", city);

			StringBuilder sb = new StringBuilder();
			sb.append("output=json");
			sb.append("&ak=").append(ak);
			sb.append("&address=").append(address);
			sb.append("&city=").append(city);

			JSONObject result = JSONObject.parseObject(sendPost(baiduMapUrl,
					sb.toString()));
			// log.debug("获取结果:"+result.toJSONString());
			// log.debug("###########################################");
			return result;
		} catch (Exception e) {

		}
		return null;
	}

	public static void main(String[] args) {
		System.out.println(getLocationByBaiduMapApi("广东省深圳市福田区福中路66号", "深圳市"));

		// {"result":{"confidence":80,"level":"道路","location":{"lat":22.550696775819,"lng":114.07607551346},"precise":1},"status":0}
		// extInfo.setLongitude(location.getString("lng"));//经度 LONGITUDE
		// extInfo.setLatitude(location.getString("lat"));//纬度 LATITUDE
	}
}
