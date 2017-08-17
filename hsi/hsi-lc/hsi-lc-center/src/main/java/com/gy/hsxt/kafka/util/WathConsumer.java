package com.gy.hsxt.kafka.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class WathConsumer {
	public void intiConsumer1(){
		Properties props = new Properties();
	     props.put("bootstrap.servers", "192.168.229.107:9093");
	     props.put("group.id", "test");
	     props.put("enable.auto.commit", "true");
	     props.put("auto.commit.interval.ms", "1000");
	     props.put("session.timeout.ms", "30000");
	     props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
	     consumer.subscribe(Arrays.asList("AO", "bar"));
	     while (true) {
	         ConsumerRecords<String, String> records = consumer.poll(100);
	         for (ConsumerRecord<String, String> record : records){
	             System.out.printf("offset = %d, key = %s, value = %s", record.offset(), record.key(), record.value());
	             System.out.println();	
	             System.out.println("******************look at************************");
	             System.out.println("size  ["+records.count()+"]");	
	         		break;
	         }
	         		
	     }
	 
	}
	public void intiConsumer2(){
		Properties props = new Properties();
	     props.put("bootstrap.servers", "192.168.229.107:9093");
	     props.put("group.id", "test");
	     props.put("enable.auto.commit", "false");
	     props.put("auto.commit.interval.ms", "1000");
	     props.put("session.timeout.ms", "30000");
	     props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
	     consumer.subscribe(Arrays.asList("foo", "bar"));
	     final int minBatchSize = 200;
	     List<ConsumerRecord<String, String>> buffer = new ArrayList<>();
	     while (true) {
	         ConsumerRecords<String, String> records = consumer.poll(100);
	         for (ConsumerRecord<String, String> record : records) {
	             buffer.add(record);
	         }
	         if (buffer.size() >= minBatchSize) {
	        //     insertIntoDb(buffer);
	             consumer.commitSync();
	             buffer.clear();
	         }
	     }
	     
	}
	public static void main(String[] args) {
		new WathConsumer().intiConsumer1();
	}
}
