package com.gy.hsxt.kafka.common.rabbitmq;
import com.gy.hsxt.kafka.common.exceptions.SendRefuseException;
import com.gy.hsxt.kafka.common.rabbitmq.impl.DefaultEventController;

public class RabbitMQSender {
	
	private String queue;
	
	private String exchange;
	
	private DefaultEventController controller;
	
	private EventTemplate eventTemplate;
	
	public RabbitMQSender(String host,String queue,String exchange) {
		
		this.queue = queue;
		
		this.exchange = exchange;
		
		EventControlConfig config = new EventControlConfig(host);
		
		controller = DefaultEventController.getInstance(config);
		
		eventTemplate = controller.getEopEventTemplate();
	}
	
	
	
	public void sendString(String str) throws SendRefuseException {
		eventTemplate.send(queue, exchange, str);
	}
	
	public void sendObject(Object obj) throws SendRefuseException{
		
		eventTemplate.send(queue, exchange, obj);
	}



	public String getQueue() {
		return queue;
	}



	public void setQueue(String queue) {
		this.queue = queue;
	}



	public String getExchange() {
		return exchange;
	}



	public void setExchange(String exchange) {
		this.exchange = exchange;
	}



	public DefaultEventController getController() {
		return controller;
	}



	public void setController(DefaultEventController controller) {
		this.controller = controller;
	}



	public EventTemplate getEventTemplate() {
		return eventTemplate;
	}



	public void setEventTemplate(EventTemplate eventTemplate) {
		this.eventTemplate = eventTemplate;
	}
	
	
	
}
