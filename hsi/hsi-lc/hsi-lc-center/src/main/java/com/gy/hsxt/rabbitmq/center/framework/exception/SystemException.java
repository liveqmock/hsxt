package com.gy.hsxt.rabbitmq.center.framework.exception;

/**
 * 
 * @ClassName: SystemException 
 * @Description: 系统异常处理基类
 * @author Lee 
 * @date 2015-6-30 下午12:28:51
 */
public class SystemException extends LogCenterException {
	private static final long serialVersionUID = 4712034900235857634L;

	public SystemException(String msg) {
        super(msg);
    }
    
    public SystemException(String code, String msg) {
        super(msg);
        this.code = code;
    }
    
    public SystemException(String msg, Throwable t) {
        super(msg, t);
        this.setStackTrace(t.getStackTrace());
    }

    public SystemException(String code, String msg, Throwable t) {
    	super(msg, t);
    	this.code = code;
    	this.setStackTrace(t.getStackTrace());
    }

}
