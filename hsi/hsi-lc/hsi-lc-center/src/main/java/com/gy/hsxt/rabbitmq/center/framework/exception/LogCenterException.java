package com.gy.hsxt.rabbitmq.center.framework.exception;
import com.gy.hsxt.rabbitmq.common.constant.SystemConstant;


/**
 * 
 * @ClassName: LogCenterException 
 * @Description: 日志中心异常基类 
 * @author Lee 
 * @date 2015-6-30 下午12:23:07
 */
public class LogCenterException extends RuntimeException {
	private static final long serialVersionUID = 4712034900235857634L;
	/** 错误码 */
	protected String code;
	
	public LogCenterException(String msg) {
        super(msg);
        this.code = SystemConstant.LOGCENTER_T_011A02;
    }
    
    public LogCenterException(String code, String msg) {
        super(msg);
        this.code = code;
    }
    
    public LogCenterException(String msg, Throwable t) {
        super(msg, t);
        this.code = SystemConstant.LOGCENTER_T_011A02;
        this.setStackTrace(t.getStackTrace());
    }

    public LogCenterException(String code, String msg, Throwable t) {
    	super(msg, t);
    	this.code = code;
    	this.setStackTrace(t.getStackTrace());
    }

	public String getCode() {
		return code;
	}
    
}
