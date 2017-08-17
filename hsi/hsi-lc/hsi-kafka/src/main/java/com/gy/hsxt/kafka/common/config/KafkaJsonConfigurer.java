package com.gy.hsxt.kafka.common.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gy.hsxt.kafka.common.constant.ConfigConstant;

public class KafkaJsonConfigurer {
	public static Map<String,Map<String,String>> sysConfigMap;

	public static Map<String,Map<String,String>> bizConfigMap;

	private static final String kafkaPath = System.getProperty("user.dir")
			+ File.separator + "conf/kafka" + File.separator;
	
	public void initJsonConfig() {
		initKafkaSyslogConfig();
		initKafkaBizlogConfig();
	}
public static void main(String[] args) {
	new KafkaJsonConfigurer().initJsonConfig();
}
	private void initKafkaSyslogConfig() {
		String fullFileName = kafkaPath + "syslog.json";
		File file = new File(fullFileName);
		BufferedReader reader = null;
		// 返回值,使用StringBuffer
		StringBuffer data = new StringBuffer();
		//
		try {
			reader = new BufferedReader(new FileReader(file));
			// 每次读取文件的缓存
			String temp = null;
			while ((temp = reader.readLine()) != null) {
				data.append(temp);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭文件流
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		sysConfigMap = convertJsonToMap(data.toString());
	}

	private void initKafkaBizlogConfig() {
		String fullFileName = kafkaPath + "bizlog.json";
		File file = new File(fullFileName);
		BufferedReader reader = null;
		// 返回值,使用StringBuffer
		StringBuffer data = new StringBuffer();
		//
		try {
			reader = new BufferedReader(new FileReader(file));
			// 每次读取文件的缓存
			String temp = null;
			while ((temp = reader.readLine()) != null) {
				data.append(temp);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭文件流
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		bizConfigMap = convertJsonToMap(data.toString());
	}
	
	private Map<String,Map<String,String>> convertJsonToMap(String json){
		JSONArray array = JSONArray.parseArray(json);
		Map<String,Map<String,String>> topicMap = new HashMap<String,Map<String,String>>();;
		Map<String,String> groupMap = null;
		for(int i=0;i<array.size();i++){
			groupMap = new HashMap<String, String>();
			JSONObject o = array.getJSONObject(i);
			groupMap.put(ConfigConstant.BOOTSTRAPSERVERS,o.getString(ConfigConstant.BOOTSTRAPSERVERS));
			groupMap.put(ConfigConstant.GROUPID, o.getString(ConfigConstant.GROUPID));
			groupMap.put(ConfigConstant.ENABLEAUTOCOMMIT, o.getString(ConfigConstant.ENABLEAUTOCOMMIT));
			groupMap.put(ConfigConstant.AUTOCOMMITINTERVALMS, o.getString(ConfigConstant.AUTOCOMMITINTERVALMS));
			groupMap.put(ConfigConstant.SESSIONTIMEOUTMS, o.getString(ConfigConstant.SESSIONTIMEOUTMS));
			groupMap.put(ConfigConstant.KEYDESERIALIZER, o.getString(ConfigConstant.KEYDESERIALIZER));
			groupMap.put(ConfigConstant.VALUEDESERIALIZER, o.getString(ConfigConstant.VALUEDESERIALIZER));
			groupMap.put(ConfigConstant.DESC, o.getString(ConfigConstant.DESC));
			groupMap.put(ConfigConstant.TOPIC, o.getString(ConfigConstant.TOPIC));
			groupMap.put(ConfigConstant.PARTITIONS, o.getString(ConfigConstant.PARTITIONS));
			groupMap.put(ConfigConstant.TOPICS, o.getString(ConfigConstant.TOPICS));
			topicMap.put(o.getString(ConfigConstant.TOPICS), groupMap);
		}
		return topicMap;
	}
 }
