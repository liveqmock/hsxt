package com.gy.hsxt.rabbitmq.common.rabbitmq;





/**
 * 
 * @ClassName: EventProcesser 
 * @Description: 事件处理接口 
 * @author Lee 
 * @date 2015-7-3 下午2:44:43
 */
public interface EventProcesser {
	public void process(Object e);
}
