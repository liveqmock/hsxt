package com.gy.hsxt.kafka.test;

import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

public class TestConsumer extends KafkaConsumer<String, String> {
	
	public TestConsumer(Map<String, Object> configs) {
		super(configs);
	}
	
	public static void main(String[] args) {
//		Properties properties = new Properties();
//      properties.load(ClassLoader.getSystemResourceAsStream("consumer.properties"));
//		props.put("bootstrap.servers", "192.168.229.64:2181,192.168.229.42:2181,192.168.229.107:2181");
		Properties props = new Properties();
		props.put("bootstrap.servers", "192.168.229.42:9092");
		props.put("group.id", "XXX");
		props.put("enable.auto.commit", "true");
		props.put("auto.commit.interval.ms", "1000");
		props.put("session.timeout.ms", "30000");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		
		String topic = "ucSYS";
	    TopicPartition partition0 = new TopicPartition(topic, 0);
		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
		consumer.assign(Arrays.asList(partition0));
		while (true) {
		    ConsumerRecords<String, String> records = consumer.poll(100);
	//	    System.out.println("size["+records.count()+"]");
		    
		    for (ConsumerRecord<String, String> record : records)
		        System.out.println("offset=["+record.offset()+"],record.key()"+record.key()+"],record.value()["+record.value()+"]");
		}
	}
}
