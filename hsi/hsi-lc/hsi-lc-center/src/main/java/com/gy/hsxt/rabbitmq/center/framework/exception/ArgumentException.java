package com.gy.hsxt.rabbitmq.center.framework.exception;



/**
 * 
 * @ClassName: ArgumentException 
 * @Description: 该类用于处理参数无效的异常
 * @author Lee 
 * @date 2015-6-30 下午12:23:36
 */
public class ArgumentException extends BusinessException {
	private static final long serialVersionUID = 4712034900235857634L;

	public ArgumentException(String msg) {
        super(msg);
    }
    
    public ArgumentException(String code, String msg) {
        super(msg);
        this.code = code;
    }
    
    public ArgumentException(String msg, Throwable t) {
        super(msg, t);
        this.setStackTrace(t.getStackTrace());
    }

    public ArgumentException(String code, String msg, Throwable t) {
    	super(msg, t);
    	this.code = code;
    	this.setStackTrace(t.getStackTrace());
    }
}
