package com.gy.hsi.nt.server.util.enumtype;
/**
 * 
 * @className:QueueName
 * @author:likui
 * @date:2015年7月27日
 * @desc:队列枚举类
 * @company:
 */
public enum QueueName {

	/**
	 * 短信队列--高优先级
	 */
	QU_NOTE_HIGH("qu.note.high"),
	/**
	 * 短信队列--中优先级
	 */
	QU_NOTE_MIDDLE("qu.note.middle"),
	/**
	 * 短信队列--低优先级
	 */
	QU_NOTE_LOW("qu.note.low"),
	/**
	 * 邮件队列--高优先级
	 */
	QU_EMAIL_HIGH("qu.email.high"),
	/**
	 * 邮件队列--中优先级
	 */
	QU_EMAIL_MIDDLE("qu.email.middle"),
	/**
	 * 邮件队列--低优先级
	 */
	QU_EMAIL_LOW("qu.email.low"),
	/**
	 * 互动信息队列--高优先级
	 */
	QU_DYNAMIC_HIGH("qu.dynamic.high"),
	/**
	 * 互动信息队列--中优先级
	 */
	QU_DYNAMIC_MIDDLE("qu.dynamic.middle"),
	/**
	 * 互动信息队列--低优先级
	 */
	QU_DYNAMIC_LOW("qu.dynamic.low");
		
	private String queueName;
	
	QueueName(String queueName){
        this.queueName=queueName;
    }
	
    public String getQueueName(){
        return queueName;
    }
}
