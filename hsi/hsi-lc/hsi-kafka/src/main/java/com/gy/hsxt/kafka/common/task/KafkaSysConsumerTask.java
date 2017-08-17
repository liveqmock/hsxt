package com.gy.hsxt.kafka.common.task;

import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.gy.hsxt.kafka.thread.SysConsumerRunner;


public class KafkaSysConsumerTask implements Runnable{
	private Properties topicNames;
	int count;
	public KafkaSysConsumerTask(Properties topicNames){
		this.topicNames = topicNames;
	}
	@Override
	public void run() {
		ExecutorService exec = Executors.newFixedThreadPool(Runtime
				.getRuntime().availableProcessors() * 2);// 起线程池准备写文件
		Set<String> set = topicNames.stringPropertyNames();
		String topicName = "";
		for (String key : set) {
			topicName = topicNames.getProperty(key);
			exec.execute(new SysConsumerRunner(topicName));
		}
		exec.shutdown();
	}
	
}
