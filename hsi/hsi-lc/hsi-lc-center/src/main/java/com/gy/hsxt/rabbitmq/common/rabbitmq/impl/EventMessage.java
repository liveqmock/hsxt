package com.gy.hsxt.rabbitmq.common.rabbitmq.impl;



import java.io.Serializable;
import java.util.Arrays;

/**
 * 
 * @ClassName: EventMessage 
 * @Description: 在App和RabbitMQ之间转送的消息
 * @author Lee 
 * @date 2015-7-3 下午3:18:01
 */
@SuppressWarnings("serial")
public class EventMessage implements Serializable{

	private String queueName;
	
	private String exchangeName;
	
	private byte[] eventData;

	public EventMessage(String queueName, String exchangeName, byte[] eventData) {
		this.queueName = queueName;
		this.exchangeName = exchangeName;
		this.eventData = eventData;
	}

	public EventMessage() {
	}	

	public String getQueueName() {
		return queueName;
	}

	public String getExchangeName() {
		return exchangeName;
	}

	public byte[] getEventData() {
		return eventData;
	}

	@Override
	public String toString() {
		return "EopEventMessage [queueName=" + queueName + ", exchangeName="
				+ exchangeName + ", eventData=" + Arrays.toString(eventData)
				+ "]";
	}
}
