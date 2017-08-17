package com.gy.hsxt.rabbitmq.center.framework.exception;


/**
 * 
 * @ClassName: DataAccessException 
 * @Description: 数据入库异常处理基类 
 * @author Lee 
 * @date 2015-6-30 下午12:24:09
 */
public class DataAccessException extends LogCenterException {
	private static final long serialVersionUID = 4712034900235857634L;

	public DataAccessException(String msg) {
        super(msg);
    }
    
    public DataAccessException(String code, String msg) {
        super(msg);
        this.code = code;
    }
    
    public DataAccessException(String msg, Throwable t) {
        super(msg, t);
        this.setStackTrace(t.getStackTrace());
    }

    public DataAccessException(String code, String msg, Throwable t) {
    	super(msg, t);
    	this.code = code;
    	this.setStackTrace(t.getStackTrace());
    }

}
