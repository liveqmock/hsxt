package com.gy.hsxt.kafka.common.task;

import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.gy.hsxt.kafka.thread.BizConsumerRunner;


public class KafkaBizConsumerTask implements Runnable {

	int count = 0;
	private Properties topicNames;
	
	public KafkaBizConsumerTask(Properties topicNames){
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
			exec.execute(new BizConsumerRunner(topicName));
		}
		exec.shutdown();
	}
}
