package com.gy.hsxt.kafka.test;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.PartitionInfo;

public class TestProduce extends KafkaProducer<String, String> {

	public TestProduce(Map<String, Object> configs) {
		super(configs);
	}

	@Override
	public List<PartitionInfo> partitionsFor(String topic) {
		return super.partitionsFor(topic);
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "unused", "rawtypes" })
	public static void main(String[] args) {
		ProducerRecord record = new ProducerRecord<String, String>("ucSYS", 0,
				"txh1", "look at");
		Producer produce = loadProperties();
		while (true) {
			produce.send(record);
			try {
				Thread.currentThread().sleep(3000L);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static Producer loadProperties() {
		// Properties properties = new Properties();
		// properties.load(ClassLoader.getSystemResourceAsStream("producer.properties"));
		Properties props = new Properties();
		props.put("bootstrap.servers", "192.168.229.64:9091");
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("batch.size", 16384);
		props.put("linger.ms", 1);
		props.put("buffer.memory", 33554432);
		props.put("key.serializer",
				"org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer",
				"org.apache.kafka.common.serialization.StringSerializer");
		Producer<String, String> producer = new KafkaProducer<>(props);
		return producer;
	}
}
