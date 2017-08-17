package com.gy.hsxt.rabbitmq.center.framework.exception;


/**
 * 
 * @ClassName: DuplicateCheckException 
 * @Description: 业务查重校验异常处理基类 
 * @author Lee 
 * @date 2015-6-30 下午12:25:44
 */
public class DuplicateCheckException extends BusinessException {
	private static final long serialVersionUID = 4712034900235857634L;

	public DuplicateCheckException(String msg) {
        super(msg);
    }
    
    public DuplicateCheckException(String code, String msg) {
        super(msg);
        this.code = code;
    }
    
    public DuplicateCheckException(String msg, Throwable t) {
        super(msg, t);
        this.setStackTrace(t.getStackTrace());
    }

    public DuplicateCheckException(String code, String msg, Throwable t) {
    	super(msg, t);
    	this.code = code;
    	this.setStackTrace(t.getStackTrace());
    }

}
