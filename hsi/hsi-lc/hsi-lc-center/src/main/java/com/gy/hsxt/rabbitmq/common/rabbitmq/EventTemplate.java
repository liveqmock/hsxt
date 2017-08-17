package com.gy.hsxt.rabbitmq.common.rabbitmq;



import com.gy.hsxt.rabbitmq.center.framework.exception.SendRefuseException;


/**
 * 
 * @ClassName: EventTemplate 
 * @Description: 事件模板接口
 * @author Lee 
 * @date 2015-7-3 下午2:45:16
 */
public interface EventTemplate {

	void send(String queueName, String exchangeName, Object eventContent) throws SendRefuseException;
		
	void send(String queueName, String exchangeName, Object eventContent, CodecFactory codecFactory) throws SendRefuseException;
}
