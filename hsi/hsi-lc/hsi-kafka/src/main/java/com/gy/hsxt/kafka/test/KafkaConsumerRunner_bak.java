package com.gy.hsxt.kafka.test;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;

public class KafkaConsumerRunner_bak implements Runnable {
     private final AtomicBoolean closed = new AtomicBoolean(false);
     private KafkaConsumer consumer;
     public KafkaConsumerRunner_bak(KafkaConsumer consumer){
    	 this.consumer = consumer;
     }
     int count = 0;
     public void run() {
         try {
             while (!closed.get()) {
                 ConsumerRecords<String,String> records = consumer.poll(5000);
                 for (ConsumerRecord<String, String> record : records) {
                	 count++;
                	 System.out.println("Runner,offset["+record.offset()+"],key["+record.key()+"],value["+record.value()+"],count["+count+"]");
		             consumer.commitSync();
		            
		         }
                 System.out.println("count["+count+"]");
                 try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
             }
         } catch (WakeupException e) {
             // Ignore exception if closing
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