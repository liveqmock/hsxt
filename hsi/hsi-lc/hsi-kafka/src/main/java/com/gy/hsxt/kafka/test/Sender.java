package com.gy.hsxt.kafka.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Properties;
import java.util.Set;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.kafka.common.bean.BizLogInfo;
import com.gy.hsxt.kafka.common.bean.SysLogInfo;

public class Sender {
	private static String prefixPath = System.getProperty("user.dir")+ File.separator + "conf/kafka/";
	public static void main(String[] args) {
		send();
	}
	public static void send(){
		SysLogInfo sysLogInfo = new SysLogInfo();
		sysLogInfo.setEmail("lixuan@qq.com");
		sysLogInfo.setFunName("method[发射]");
		sysLogInfo.setHostName("猪头电脑");
		sysLogInfo.setLevel("DEBUG");
		sysLogInfo.setIpAddress("192.168.1.1");
		sysLogInfo.setMobile("18721597301");
		sysLogInfo.setMoudleName("sendder");
		sysLogInfo.setPlatformName("hsxtSys");
		sysLogInfo.setSystemName("uc");
		sysLogInfo.setSystemInstanceName("uc01");
		sysLogInfo.setTimeStamp(DateUtil.getCurrentDate2String());
		BizLogInfo bizLogInfo = new BizLogInfo();
		bizLogInfo.setColumns("|列名1|列名2");
		
		bizLogInfo.setFunName("BizMethod");
		bizLogInfo.setMoudleName("BizModule");
		bizLogInfo.setPlatformName("hsxtBiz");
		bizLogInfo.setSystemInstanceName("uc01");
		bizLogInfo.setSystemName("uc");
		bizLogInfo.setTimeStamp(DateUtil.getCurrentDate2String());
		
		Properties props = new Properties();
		 props.put("bootstrap.servers", "192.168.229.64:9092");
		 props.put("acks", "all");
		 props.put("retries", 0);
		 props.put("batch.size", 16384);
		 props.put("linger.ms", 1);
		 props.put("buffer.memory", 33554432);
		 props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		 props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);
		int count = 0;	
		Properties propsSys =  loadSysTopic();
		Properties propsBiz =  loadBizTopic();
		Set<String> sys = propsSys.stringPropertyNames();
		Set<String> biz = propsBiz.stringPropertyNames();
		try {
			for(String key : sys){
				for (int i = 0; i < 1000; i++) {
					sysLogInfo.setLogContent("this is test["+propsSys.getProperty(key)+"]");
					sysLogInfo.setOrder(i);
					sysLogInfo.setSystemName(key);
					sysLogInfo.setSystemInstanceName(key+"01");
					producer.send(new ProducerRecord<String, String>(propsSys.getProperty(key), Integer.toString(i), JSON.toJSONString(sysLogInfo)) );
					bizLogInfo.setOrder(i);
					bizLogInfo.setSystemName(key);
					bizLogInfo.setSystemInstanceName(key+"01");
					bizLogInfo.setContents("|列值"+i+"|列值"+propsBiz.getProperty(key)+"");
					producer.send(new ProducerRecord<String, String>(propsBiz.getProperty(key), Integer.toString(i), JSON.toJSONString(bizLogInfo)) );
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				Thread.currentThread().sleep(10000000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		
		 producer.close();
	}

	private static Properties loadBizTopic(){
		String fullPath = prefixPath + "bizToplic.properties";
		InputStream in = null;
		try {
			in = new FileInputStream(fullPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Properties props = new Properties();
		try {
			props.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props;
	}
	
	private static Properties loadSysTopic(){
		String fullPath = prefixPath + "sysToplic.properties";
		InputStream in = null;
		try {
			in = new FileInputStream(fullPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Properties props = new Properties();
		try {
			props.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props;
	}
}
