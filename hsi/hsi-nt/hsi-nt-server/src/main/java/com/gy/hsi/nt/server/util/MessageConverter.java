package com.gy.hsi.nt.server.util;

import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.AbstractMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;

import com.alibaba.fastjson.JSONObject;
/**
 * 
 * @className:MessageConverter
 * @author:likui
 * @date:2015年7月28日
 * @desc:MQ参数转换成json处理器
 * @company:gyist
 */
public class MessageConverter extends AbstractMessageConverter {
	
	private static final Logger logger = Logger.getLogger(MessageConverter.class);
	 
    public static final String DEFAULT_CHARSET = "UTF-8";
 
    private volatile String defaultCharset = DEFAULT_CHARSET;
       
    public MessageConverter(){
    	super();
    }
    
    public void setDefaultCharset(String defaultCharset) {
        this.defaultCharset = (defaultCharset != null) ? defaultCharset
                : DEFAULT_CHARSET;
    }
    
    @Override
	public Object fromMessage(Message arg0) throws MessageConversionException {
		return null;
	}
     
    @SuppressWarnings("unchecked")
    public <T> T fromMessage(Message message,T t) {
        String json = "";
        try {
            json = new String(message.getBody(),defaultCharset);
        } catch (UnsupportedEncodingException e) {
            logger.error("Failed to convert Message content:", e);
        }
        return (T) JSONObject.parseObject(json, t.getClass());
    }   
     
	@Override
	protected Message createMessage(Object objectToConvert,
            MessageProperties messageProperties)
            throws MessageConversionException {
        byte[] bytes = null;
        try {
            String jsonString = JSONObject.toJSONString(objectToConvert);
            bytes = jsonString.getBytes(this.defaultCharset);
        } catch (UnsupportedEncodingException e) {
        	logger.error("Failed to convert Message content:", e);
            throw new MessageConversionException(
                    "Failed to convert Message content", e);
        } 
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
        messageProperties.setContentEncoding(this.defaultCharset);
        if (bytes != null) {
            messageProperties.setContentLength(bytes.length);
        }
        return new Message(bytes, messageProperties);
	}
}
