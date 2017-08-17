package com.gy.hsxt.kafka.test;

import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class TestProduce extends KafkaProducer<String, String> {

	public TestProduce(Map<String, Object> configs) {
		super(configs);
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) throws InterruptedException {
		ProducerRecord record = new ProducerRecord<String, String>("BBB", 0,
				"BBB", "look at");
		Producer produce = loadProperties();
		for (int i = 0; i < 5; i++) {
			produce.send(record);
		}
	}

	public static Producer<String, String> loadProperties() {
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
