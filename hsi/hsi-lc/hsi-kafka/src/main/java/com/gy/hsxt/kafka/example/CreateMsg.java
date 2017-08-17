package com.gy.hsxt.kafka.example;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class CreateMsg {

	public static void produce(){
		Properties props = new Properties();
		 props.put("bootstrap.servers", "192.168.229.42:9092");
		 props.put("acks", "all");
		 props.put("retries", 0);
		 props.put("batch.size", 16384);
		 props.put("linger.ms", 1);
		 props.put("buffer.memory", 33554432);
		 props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		 props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		 Producer<String, String> producer = new KafkaProducer<>(props);
		 for(int i = 0; i < 1; i++){
			 producer.send(new ProducerRecord<String, String>("foo", Integer.toString(i), Integer.toString(i)));
		 }
		 producer.close();
	}

	public static void main(String[] args) {
	//	CreateMsg.produce();
		String s = "55e5f2a3e07a203fa1adfafb32c86e679e1ef6628d4fe38f16dd48c33db8c2f4";
		String x = "55e5f2a3e07a203fa1adfafb32c86e679e1ef6628d4fe38f16dd48c33db8c2f4";
		if(s.equals(x)){
			System.out.println("xixi");
		}else{
			System.out.println("haa");
		}
	}
}
