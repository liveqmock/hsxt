package com.gy.hsxt.kafka.common.rabbitmq;

import java.util.Map;

/**
 * 
 * @ClassName: EventController 
 * @Description: 事件控制器接口，用来处理处理队列,启动监听
 * @author Lee 
 * @date 2015-7-3 下午2:45:41
 */
public interface EventController {
	
	/**
	 * 控制器启动方法
	 */
	void start();
	
	/**
	 * 获取发送模版
	 */
	EventTemplate getEopEventTemplate();
	
	/**
	 * 绑定消费程序到对应的exchange和queue
	 */
	EventController add(String queueName, String exchangeName, EventProcesser eventProcesser);
	
	/*in map, the key is queue name, but value is exchange name*/
	EventController add(Map<String, String> bindings, EventProcesser eventProcesser);
	
}
