package com.gy.hsxt.kafka.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.kafka.clients.consumer.KafkaConsumer;

public class Test {
	private static String prefixPath = System.getProperty("user.dir")+ File.separator + "conf/kafka/";
	public static void main(String[] args) {
		 Properties props = new Properties();
//	     props.put("bootstrap.servers", "192.168.229.42:9092");
		 props.put("bootstrap.servers", "192.168.229.100:9092");
	     props.put("group.id", "all");
	     props.put("enable.auto.commit", "false");
	     props.put("auto.commit.interval.ms", "1000");
	     props.put("session.timeout.ms", "30000");
	     props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
	     consumer.subscribe(getSubScript(loadBizTopic()));
	     Thread t1 = new Thread(new  KafkaConsumerRunner_bak(consumer));
	     t1.start();
	     props.put("group.id", "all");
	     consumer = new KafkaConsumer<>(props);
	     consumer.subscribe(getSubScript(loadSysTopic()));
	     Thread t2 = new Thread(new  KafkaConsumerRunner_bak(consumer));
//	    t2.start();
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
	
	private static  List<String> getSubScript(Properties props){
		Set<String> set = props.stringPropertyNames();
		List<String> list = new ArrayList<>();
		for (String key : set) {
			list.add(props.getProperty(key));
		}
		return list;
	}
}
