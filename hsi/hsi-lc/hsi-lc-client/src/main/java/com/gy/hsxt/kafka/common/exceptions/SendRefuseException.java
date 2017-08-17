package com.gy.hsxt.kafka.common.exceptions;

/**
 * 
 * @ClassName: SendRefuseException 
 * @Description: 发送消息到队列异常
 * @author Lee 
 * @date 2015-7-3 下午2:41:39
 */
@SuppressWarnings("serial")
public class SendRefuseException extends Exception {

	public SendRefuseException() { 
		super();
		// TODO Auto-generated constructor stub
	}

	public SendRefuseException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public SendRefuseException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public SendRefuseException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

}
