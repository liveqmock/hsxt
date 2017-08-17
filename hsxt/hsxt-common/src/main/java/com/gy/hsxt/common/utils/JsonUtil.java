package com.gy.hsxt.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gy.hsxt.common.exception.HsException;

/**
 * json转换工具类
 * @author guiyi147
 *
 */
public class JsonUtil {

	 
	/**
	 * object 对象转换成 json字符串
	 * @param value
	 * @return
	 */
	public static String createJsonString(Object value) {
		String json = JSON.toJSONString(value);
		return json;
	}
	
	/**
	 * 生成返回界面数据的特有jsonp方法</br>
	 * {"code":${String code},"data":{${Object value}}}
	 * @param value		界面接受数据
	 * @param code		编码编号结果
	 * @return
	 */
	public static String createReturnJsonString(Object value,String code){
		
		JSONObject jo = new JSONObject();
		jo.put("code", code);
		jo.put("data", value);
		
		return JSON.toJSONString(jo);
	}
	
	/**
	 * 获取json字符串中一个属性value
	 * @param jsonStr
	 * @param key
	 * @return
	 */
	public static Object getJsonValue(String jsonStr,String key){
		Object obj = null;
		try {
			JSONObject jsonObject= JSON.parseObject(jsonStr); 
			obj = jsonObject.get(key);
		} catch (Exception e) {
		}
		
		return obj ;
	}
	
	/**
	 * 在json字符串中添加一对key-value属性
	 * @param jsonStr json字符串
	 * @param key 添加属性key
	 * @param rate 添加属性value
	 * @return 添加后的json字符串
	 */
	public static String putJsonValue(String jsonStr,String key,Object rate){ 
		try {
			JSONObject jsonObject= JSON.parseObject(jsonStr);
			jsonObject.put(key, rate);
			return JsonUtil.createJsonString(jsonObject);
		} catch (Exception e) {
			return jsonStr;
		}
	}
	/**
	 * json 字符串转换成 Object
	 * @param jsonString
	 * @param cls
	 * @return
	 */
	public static <T> T getObject(String jsonString, Class<T> cls) {
		T t = null;
		try {
			t = (T) JSON.parseObject(jsonString, cls);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return t;
	}
	
	
	public  static String ConvertStream2Json(InputStream inputStream) {
		String jsonStr = "";
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		// 将输入流转移到内存输出流中
		try {
			while ((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
				out.write(buffer, 0, len);
			}
			// 将内存流转换为字符串
			jsonStr = new String(out.toByteArray());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonStr;
	}
	

}
