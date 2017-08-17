package com.gy.hsxt.rabbitmq.common.rabbitmq.impl;




import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.util.StringUtils;

import com.gy.hsxt.rabbitmq.center.framework.exception.SendRefuseException;
import com.gy.hsxt.rabbitmq.common.rabbitmq.CodecFactory;
import com.gy.hsxt.rabbitmq.common.rabbitmq.EventTemplate;

/**
 * 
 * @ClassName: DefaultEventTemplate 
 * @Description: 默认事件处理模板
 * @author Lee 
 * @date 2015-7-3 下午3:18:15
 */
public class DefaultEventTemplate implements EventTemplate {

	private static final Logger log = LoggerFactory
			.getLogger(DefaultEventTemplate.class);

	private AmqpTemplate eventAmqpTemplate;

	private CodecFactory defaultCodecFactory;

	private DefaultEventController eec;

	public DefaultEventTemplate(AmqpTemplate eopAmqpTemplate,
			CodecFactory defaultCodecFactory, DefaultEventController eec) {
		this.eventAmqpTemplate = eopAmqpTemplate;
		this.defaultCodecFactory = defaultCodecFactory;
		this.eec = eec;
	}
	
	public DefaultEventTemplate(AmqpTemplate eopAmqpTemplate,CodecFactory defaultCodecFactory) {
		this.eventAmqpTemplate = eopAmqpTemplate;
		this.defaultCodecFactory = defaultCodecFactory;
	}

	@Override
	public void send(String queueName, String exchangeName, Object eventContent)
			throws SendRefuseException {
		this.send(queueName, exchangeName, eventContent, defaultCodecFactory);
	}  

	@Override
	public void send(String queueName, String exchangeName, Object eventContent,
			CodecFactory codecFactory) throws SendRefuseException {
		if (StringUtils.isEmpty(queueName) || StringUtils.isEmpty(exchangeName)) {
			throw new SendRefuseException("queueName exchangeName can not be empty.");
		}
		
		if (!eec.beBinded(exchangeName, queueName))
			eec.declareBinding(exchangeName, queueName);

		byte[] eventContentBytes = null;
		if (codecFactory == null) {
			if (eventContent == null) {
				log.warn("Find eventContent is null,are you sure...");
			} else {
				throw new SendRefuseException(
						"codecFactory must not be null ,unless eventContent is null");
			}
		} else {
			try {
				eventContentBytes = codecFactory.serialize(eventContent);
			} catch (IOException e) {
				throw new SendRefuseException(e);
			}
		}

		// 构造成Message
		EventMessage msg = new EventMessage(queueName, exchangeName,
				eventContentBytes);
		try {
			eventAmqpTemplate.convertAndSend(exchangeName, queueName, msg);
		} catch (AmqpException e) {
			log.error("send event fail. Event Message : [" + eventContent + "]", e);
			throw new SendRefuseException("send event fail", e);
		}
	}
}
