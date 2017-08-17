package com.gy.hsxt.rabbitmq.center.framework.exception;

/**
 * 
 * @ClassName: LogConfigException 
 * @Description: 日志文件配置异常
 * @author Lee 
 * @date 2015-6-30 下午12:26:02
 */
public class LogConfigException extends SystemException{
	
	private static final long serialVersionUID = 1L;
	
	public LogConfigException(String msg) {
        super(msg);
    }
    
    public LogConfigException(String code, String msg) {
        super(msg);
        this.code = code;
    }
    
    public LogConfigException(String msg, Throwable t) {
        super(msg, t);
        this.setStackTrace(t.getStackTrace());
    }

    public LogConfigException(String code, String msg, Throwable t) {
    	super(msg, t);
    	this.code = code;
    	this.setStackTrace(t.getStackTrace());
    }
	
}
