package com.gy.hsxt.kafka.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.ds.param.HsPropertiesConfigurer;
import com.gy.hsxt.kafka.common.bean.BizLogInfo;
import com.gy.hsxt.kafka.common.constant.ConfigConstant;

public class LogUtil {


	public static Producer<String, String> createProducer(String path) {
		Properties props = new Properties();
		FileInputStream in = null;
		try {
			in = new FileInputStream(new File(path));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			props.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Producer<String, String> producer = new KafkaProducer<>(props);
		producer = new KafkaProducer<>(props);
		return producer;
	}

	

	


	
	public static ProducerRecord<String, String> buildBizMessage(BizLogInfo info){
		String topic = HsPropertiesConfigurer.getProperty(ConfigConstant.BIZ_SYSTEM_NAME)+"BIZ";
		int partition = Integer.parseInt(HsPropertiesConfigurer
				.getProperty(ConfigConstant.PARTITION));
//		ProducerRecord<String, String> message = new ProducerRecord<String, String>(
//				topic, partition, info.getSystemName(), info.toString());
		String msg = JSON.toJSONString(info);
//		ProducerRecord<String, String> message = new ProducerRecord<String, String>(topic, String.valueOf(partition), msg);
//		System.out.println("msg ==>["+msg+"]");
//		System.out.println("value ==>["+message.value()+"]");
//		System.out.println("key ==>["+message.key()+"]");
//		System.out.println("topic ==>["+message.topic()+"]");
		ProducerRecord record = new ProducerRecord<String, String>(topic, 0,
				topic, msg);
		return record;
	}
}
