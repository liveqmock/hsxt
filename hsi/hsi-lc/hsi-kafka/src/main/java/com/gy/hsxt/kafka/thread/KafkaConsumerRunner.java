package com.gy.hsxt.kafka.thread;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;

import com.gy.hsxt.kafka.common.util.ToplicManager;
import com.gy.hsxt.kafka.util.TaskTools;

public class KafkaConsumerRunner implements Runnable {
     private final AtomicBoolean closed = new AtomicBoolean(false);
     private KafkaConsumer consumer;
     TaskTools  tools = new TaskTools();
     ToplicManager manager = ToplicManager.getInstance();// 获取系统缓存队列管理实例
     public KafkaConsumerRunner(KafkaConsumer consumer){
    	 this.consumer = consumer;
     }
     int count = 0;
     
	public void run() {
         try {
             while (!closed.get()) {
                 ConsumerRecords<String,String> records = consumer.poll(20000);
                 Map<String,List<ConsumerRecord<String,String>>> partMap = tools.partByTopic(records,consumer);
                 if(null != partMap && partMap.size() > 0){
                	Set<String> set = partMap.keySet();
                	for(String key : set){
                		 List<ConsumerRecord<String,String>> list = partMap.get(key);
                		 manager.push(key, list);
                	}
                 }
             }
         } catch (WakeupException e) {
             if (!closed.get()) throw e;
         } finally {
             consumer.close();
         }
     }

     // Shutdown hook which can be called from a separate thread
     public void shutdown() {
         closed.set(true);
         consumer.wakeup();
     }
 }