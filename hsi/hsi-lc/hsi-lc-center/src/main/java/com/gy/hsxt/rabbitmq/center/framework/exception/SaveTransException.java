package com.gy.hsxt.rabbitmq.center.framework.exception;

/**
 * 
 * @ClassName: SaveTransException 
 * @Description: 保存流水异常处理基类
 * @author Lee 
 * @date 2015-6-30 下午12:28:08
 */
public class SaveTransException extends BusinessException {
	private static final long serialVersionUID = 4712034900235857634L;

	public SaveTransException(String msg) {
        super(msg);
    }
    
    public SaveTransException(String code, String msg) {
        super(msg);
        this.code = code;
    }
    
    public SaveTransException(String msg, Throwable t) {
        super(msg, t);
        this.setStackTrace(t.getStackTrace());
    }

    public SaveTransException(String code, String msg, Throwable t) {
    	super(msg, t);
    	this.code = code;
    	this.setStackTrace(t.getStackTrace());
    }

}
