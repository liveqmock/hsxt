package com.gy.hsxt.kafka.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import com.gy.hsi.ds.param.HsPropertiesConfigurer;
import com.gy.hsxt.kafka.common.constant.ConfigConstant;
import com.gy.hsxt.kafka.common.task.KafkaBizConsumerTask;
import com.gy.hsxt.kafka.common.task.KafkaSysConsumerTask;
import com.gy.hsxt.kafka.thread.KafkaConsumerRunner;

/**
 * 
 * @ClassName: SysInitor
 * @Description: 初始化类
 * @author Lee
 * @date 2015-6-29 下午2:33:06
 */
@Component
public class KafkaInitor implements InitializingBean, ServletContextAware {
//	@Autowired
//	INtService ntService;
	
	private String prefixPath = System.getProperty("user.dir")+ File.separator + "conf/hsi-kafka/kafka/";
	
	@Override
	public void afterPropertiesSet() throws Exception {
		/** 初始化/读取json配置文件 */
		Properties propsSys =  loadSysTopic();
		Properties propsBiz =  loadBizTopic();
		KafkaConsumer<String, String> consumerSys = buildConsumer("All", propsSys);
		KafkaConsumer<String, String> consumerBiz = buildConsumer("All", propsBiz);
		Thread t1 = new Thread(new KafkaConsumerRunner(consumerSys));
		Thread t2 = new Thread(new KafkaConsumerRunner(consumerBiz));
		t1.start();
		t2.start();
		ScheduledExecutorService schedule = Executors.newScheduledThreadPool(2);
		
		/** 缓存消息 **/
		/** 消费消息**/
		schedule.scheduleWithFixedDelay(new KafkaBizConsumerTask(propsBiz), 5, 3000,
				TimeUnit.MILLISECONDS);
		schedule.scheduleWithFixedDelay(new KafkaSysConsumerTask(propsSys), 5, 3000,
				TimeUnit.MILLISECONDS);
	
//		schedule.shutdown();
	
	}

	private Properties loadBizTopic(){
		String fullPath = prefixPath + "bizToplic.properties";
		InputStream in = null;
		try {
			in = new FileInputStream(fullPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Properties props = new Properties();
		try {
			props.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props;
	}
	
	private Properties loadSysTopic(){
		String fullPath = prefixPath + "sysToplic.properties";
		InputStream in = null;
		try {
			in = new FileInputStream(fullPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Properties props = new Properties();
		try {
			props.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props;
	}


	@Override
	public void setServletContext(ServletContext arg0) {
		
	}
	
	private List<String> getSubScript(Properties props){
		Set<String> set = props.stringPropertyNames();
		List<String> list = new ArrayList<>();
		for (String key : set) {
			list.add(props.getProperty(key));
		}
		return list;
	}
	
	private KafkaConsumer<String, String> buildConsumer(String groupId,Properties config) {
//	    props.put("bootstrap.servers", "192.168.229.100:9092");
//	     props.put("group.id", groupId);
//	     props.put("enable.auto.commit", "false");
//	     props.put("auto.commit.interval.ms", "1000");
//	     props.put("session.timeout.ms", "30000");
//	     props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//	     props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//	     KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
//	     
		Properties props = new Properties();
		String ip = HsPropertiesConfigurer.getProperty(ConfigConstant.IP);
		String port = HsPropertiesConfigurer.getProperty(ConfigConstant.PORT);
	     props.put("bootstrap.servers", ip+":" + port);
	     props.put("group.id", groupId);
	     props.put("enable.auto.commit", "false");
	     props.put("auto.commit.interval.ms", "1000");
	     props.put("session.timeout.ms", "30000");
	     props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
	     consumer.subscribe(getSubScript(config));
		return consumer;
	}
}
