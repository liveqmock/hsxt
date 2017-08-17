package com.gy.hsxt.rabbitmq.center.framework.exception;


/**
 * 
 * @ClassName: BusinessException 
 * @Description: 业务异常基类
 * @author Lee 
 * @date 2015-6-30 下午12:23:54
 */
public class BusinessException extends LogCenterException {
	private static final long serialVersionUID = 4712034900235857634L;

	public BusinessException(String msg) {
        super(msg);
    }
    
    public BusinessException(String code, String msg) {
        super(msg);
        this.code = code;
    }
    
    public BusinessException(String msg, Throwable t) {
        super(msg, t);
        this.setStackTrace(t.getStackTrace());
    }

    public BusinessException(String code, String msg, Throwable t) {
    	super(msg, t);
    	this.code = code;
    	this.setStackTrace(t.getStackTrace());
    }

	public String getCode() {
		return code;
	}

}
