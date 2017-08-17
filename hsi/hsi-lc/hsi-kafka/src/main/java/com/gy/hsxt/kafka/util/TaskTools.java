package com.gy.hsxt.kafka.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.gy.hsxt.kafka.common.util.StringUtils;



public class TaskTools {
	
	
	public Map<String,List<ConsumerRecord<String,String>>> partByTopic(ConsumerRecords<String,String> records,KafkaConsumer consumer){
		if(null == records || records.count() == 0){
			return null;
		}
		Map<String,List<ConsumerRecord<String,String>>> resultMap = new HashMap();
		List<ConsumerRecord<String,String>> list = null;
		for(ConsumerRecord<String,String> record : records){
			if(resultMap.containsKey(record.topic())){
				list = resultMap.get(record.topic());
			}else{
				list = new ArrayList<ConsumerRecord<String,String>>();
				resultMap.put(record.topic(), list);
			}
			list.add(record);
			consumer.commitSync();
		}
		
		return resultMap;
	}
}
