package com.gy.hsxt.kafka.main;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import com.gy.hsi.nt.api.service.INtService;
import com.gy.hsxt.kafka.common.config.KafkaJsonConfigurer;
import com.gy.hsxt.kafka.common.task.KafkaBizConsumerTask;
import com.gy.hsxt.kafka.common.task.KafkaSysConsumerTask;

/**
 * 
 * @ClassName: SysInitor
 * @Description: 初始化类
 * @author Lee
 * @date 2015-6-29 下午2:33:06
 */
@Component
public class KafkaInitor implements InitializingBean, ServletContextAware {
	@Autowired
	INtService ntService;
	@Override
	public void afterPropertiesSet() throws Exception {
		/** 初始化/读取json配置文件 */
		initJsonConfig();
		/** 初始化每个子系统日志处理线程的参数 **/
		startConsumer();
	}

	/**
	 * 
	 * @Title: initJsonConfig
	 * @Description: 初始化Json配置文件
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void initJsonConfig() {
		KafkaJsonConfigurer config = new KafkaJsonConfigurer();
		config.initJsonConfig();
	}

	@Override
	public void setServletContext(ServletContext arg0) {
	}

	private void startSysConsumerThread() {
		ExecutorService exec = Executors.newFixedThreadPool(Runtime
				.getRuntime().availableProcessors() * 2);// 起线程池准备写文件
		 Map<String, Map<String, String>>  map = KafkaJsonConfigurer.sysConfigMap;
		Iterator<Entry<String, Map<String, String>>> it = map.entrySet().iterator();
		Entry<String, Map<String, String>> entry = null;
		int count = 0;
		while (it.hasNext()) {
			entry = it.next();
			exec.execute(new KafkaSysConsumerTask(entry.getKey(), entry.getValue(),ntService));
			count++;
			System.out.println("递增：count["+count+"]");
			try {
				Thread.currentThread().sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		 System.out.println("子系统消费端加载完毕");
		 try {
			Thread.currentThread().sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void startBizConsumerThread() {
		ExecutorService exec = Executors.newFixedThreadPool(Runtime
				.getRuntime().availableProcessors() * 2);// 起线程池准备写文件
		Iterator<Entry<String, Map<String, String>>> it = KafkaJsonConfigurer.bizConfigMap
				.entrySet().iterator();
		
		Entry<String, Map<String, String>> entry = null;
		while (it.hasNext()) {
			entry = it.next();
			System.out.println("key["+entry.getKey()+"],value["+entry.getValue()+"]");
			exec.execute(new KafkaBizConsumerTask(entry.getKey(), entry.getValue()));
			
		}
	}
	
	private void startConsumer(){
		startSysConsumerThread();
		startBizConsumerThread();
	}
}
