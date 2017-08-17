package com.gy.hsxt.rabbitmq.center.framework.exception;


/**
 * 
 * @ClassName: MsgParseException 
 * @Description: 报文解析异常处理
 * @author Lee 
 * @date 2015-6-30 下午12:27:10
 */
public class MsgParseException extends BusinessException {
	private static final long serialVersionUID = 4712034900235857634L;

	public MsgParseException(String msg) {
        super(msg);
    }
    
    public MsgParseException(String code, String msg) {
        super(msg);
        this.code = code;
    }
    
    public MsgParseException(String msg, Throwable t) {
        super(msg, t);
        this.setStackTrace(t.getStackTrace());
    }

    public MsgParseException(String code, String msg, Throwable t) {
    	super(msg, t);
    	this.code = code;
    	this.setStackTrace(t.getStackTrace());
    }

}
