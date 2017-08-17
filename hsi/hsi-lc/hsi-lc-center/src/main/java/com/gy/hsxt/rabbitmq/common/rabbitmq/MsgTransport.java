package com.gy.hsxt.rabbitmq.common.rabbitmq;



import com.gy.hsxt.rabbitmq.center.framework.exception.SendRefuseException;
import com.gy.hsxt.rabbitmq.common.rabbitmq.impl.DefaultEventController;

/**
 * 
 * @ClassName: MsgTransport 
 * @Description:消息传输器实现类,注册消息监听器
 * @author Lee 
 * @date 2015-7-9 下午12:25:14
 */
public class MsgTransport {
	
	private String queue;
	
	private String exchange;
	
	private DefaultEventController controller;
	
	private EventTemplate eventTemplate;
	
	public MsgTransport(String host,String queue,String exchange,int maxconsumers) {
		
		this.queue = queue;
		
		this.exchange = exchange;
		
		EventControlConfig config = new EventControlConfig(host,maxconsumers);
		
		controller = DefaultEventController.getInstance(config);
		
		eventTemplate = controller.getEopEventTemplate();
	}
	
	public void startConsumers(EventProcesser processer) {
		
		controller.add(queue, exchange, processer);
		
		controller.start();
	}
	
	
	public void startBinds(){
		controller.startBinds();
	}
	
	
	public void sendString(String str) throws SendRefuseException {
		eventTemplate.send(queue, exchange, str);
	}
	
	public void sendObject(String queue,String exchange,Object obj) throws SendRefuseException{
		eventTemplate.send(queue, exchange, obj);
	}
	
	
}
	
	