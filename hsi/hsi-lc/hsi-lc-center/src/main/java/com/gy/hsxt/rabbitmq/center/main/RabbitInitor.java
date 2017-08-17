package com.gy.hsxt.rabbitmq.center.main;



import java.util.List;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.rabbitmq.center.bizlog.processer.BizLogProcesser;
import com.gy.hsxt.rabbitmq.center.framework.bean.ConfigQueue;
import com.gy.hsxt.rabbitmq.center.framework.bean.ConfigQueues;
import com.gy.hsxt.rabbitmq.center.syslog.processer.SysLogProcesser;
import com.gy.hsxt.rabbitmq.common.config.RabbitJsonConfigurer;
import com.gy.hsxt.rabbitmq.common.rabbitmq.MsgTransport;


/**
 * 
 * @ClassName: SysInitor
 * @Description: 初始化类
 * @author Lee
 * @date 2015-6-29 下午2:33:06
 */
@Component
public class RabbitInitor implements InitializingBean, ServletContextAware {

	private static final Logger log = LoggerFactory.getLogger(RabbitInitor.class);

	@Override
	public void afterPropertiesSet() throws Exception {
		
		/** 初始化/读取json配置文件 */
		initJsonConfig();

		/** 读取系统日志json配置文件String转换成list */
		List<ConfigQueue> syslogList = initSysLogQueueList();

		/** 初始化系统日志消费者线程池 */
		initSysLogConsumerPool(syslogList);

		/** 读取业务日志json配置文件String转换成list */
		List<ConfigQueue> bizlogList = initBizLogQueueList();

		/** 初始化业务日志消费者线程池 */
		initBizLogConsumerPool(bizlogList);
		
	}

	/**
	 * 
	 * @Title: initSysLogConsumerPool
	 * @Description: 初始化系统日志消费者线程池
	 * @param @param syslogList 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void initSysLogConsumerPool(List<ConfigQueue> syslogList) {
		log.info("initSysLogConsumerPool run");
		System.out.println("initSysLogConsumerPool run");
		for (ConfigQueue queue : syslogList) {
			/** 实例化消息消费者传输器，注册消息监听器，拉起消费者监听线程 */
			MsgTransport transport = new MsgTransport(queue.getHost(),
					queue.getQueue(), queue.getExchange(),
					queue.getMaxconsumers());
			/** 拉起消费者监听线程 */
			transport.startConsumers(new SysLogProcesser());
		}
	}

	/**
	 * 
	 * @Title: initBizLogConsumerPool
	 * @Description: 初始化系统日志消费者线程池
	 * @param @param bizlogList 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void initBizLogConsumerPool(List<ConfigQueue> bizlogList) {
		for (ConfigQueue queue : bizlogList) {
			/** 实例化消息消费者传输器，注册消息监听器 */
			MsgTransport transport = new MsgTransport(queue.getHost(),
					queue.getQueue(), queue.getExchange(),
					queue.getMaxconsumers());
			/** 拉起消费者监听线程 */
			transport.startConsumers(new BizLogProcesser());
		}
	}

	/**
	 * 
	 * @Title: initSysLogQueueList
	 * @Description: 初始化系统日志配置文件List
	 * @param @return 设定文件
	 * @return List<ConfigQueue> 返回类型
	 * @throws
	 */
	private List<ConfigQueue> initSysLogQueueList() {

		List<ConfigQueue> list = null;

		ConfigQueues queues = JSON.parseObject(RabbitJsonConfigurer.sysConfig,
				ConfigQueues.class);

		if (queues != null) {
			list = queues.getQueues();
		}

		return list;
	}

	/**
	 * 
	 * @Title: initBizLogQueueList
	 * @Description: 初始化业务日志配置文件List
	 * @param @return 设定文件
	 * @return List<ConfigQueue> 返回类型
	 * @throws
	 */
	private List<ConfigQueue> initBizLogQueueList() {
		List<ConfigQueue> list = null;
		ConfigQueues queues = JSON.parseObject(RabbitJsonConfigurer.bizConfig,
				ConfigQueues.class);
		if (queues != null) {
			list = queues.getQueues();
		}
		return list;
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
		RabbitJsonConfigurer config = new RabbitJsonConfigurer();
		config.initJsonConfig();
	}

	@Override
	public void setServletContext(ServletContext arg0) {
	}
}
