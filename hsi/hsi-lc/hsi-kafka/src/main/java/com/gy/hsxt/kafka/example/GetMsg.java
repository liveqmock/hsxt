package com.gy.hsxt.kafka.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

public class GetMsg {

	public static void main(String[] args) {
		GetMsg.consumerManu();
	}
	public static void consumerAuto(){
		 Properties props = new Properties();
	     props.put("bootstrap.servers", "192.168.229.42:9092");
	     props.put("group.id", "test");
	     props.put("enable.auto.commit", "true");
	     props.put("auto.commit.interval.ms", "1000");
	     props.put("session.timeout.ms", "30000");
	     props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
	     consumer.subscribe(Arrays.asList("foo", "bar"));
	     while (true) {
	         ConsumerRecords<String, String> records = consumer.poll(100);
	         for (ConsumerRecord<String, String> record : records)
	             System.out.printf("offset = %d, key = %s, value = %s", record.offset(), record.key(), record.value());
	     }
	}
	
	public static void consumerManu(){
		Properties props = new Properties();
	     props.put("bootstrap.servers", "192.168.229.42:9092");
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
	      //       insertIntoDb(buffer);
	             consumer.commitSync();
	             buffer.clear();
	         }
	     }
	}
	
	public void test(){
		Properties props = new Properties();
	     props.put("bootstrap.servers", "192.168.229.42:9092");
	     props.put("group.id", "test");
	     props.put("enable.auto.commit", "false");
	     props.put("auto.commit.interval.ms", "1000");
	     props.put("session.timeout.ms", "30000");
	     props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
		
		try {
	         while(true) {
	             ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);
	             for (TopicPartition partition : records.partitions()) {
	                 List<ConsumerRecord<String, String>> partitionRecords = records.records(partition);
	                 for (ConsumerRecord<String, String> record : partitionRecords) {
	                     System.out.println(record.offset() + ": " + record.value());
	                 }
	                 long lastOffset = partitionRecords.get(partitionRecords.size() - 1).offset();
	                 consumer.commitSync(Collections.singletonMap(partition, new OffsetAndMetadata(lastOffset + 1)));
	             }
	         }
	     } finally {
	       consumer.close();
	     }
	}
}
