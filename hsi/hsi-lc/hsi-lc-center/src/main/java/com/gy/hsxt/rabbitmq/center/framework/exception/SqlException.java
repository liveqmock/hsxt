package com.gy.hsxt.rabbitmq.center.framework.exception;

/**
 * 
 * @ClassName: SqlException 
 * @Description: 数据入库Sql检查异常处理基类
 * @author Lee 
 * @date 2015-6-30 下午12:28:32
 */
public class SqlException extends DataAccessException {
	private static final long serialVersionUID = 4712034900235857634L;

	public SqlException(String msg) {
        super(msg);
    }
    
    public SqlException(String code, String msg) {
        super(msg);
        this.code = code;
    }
    
    public SqlException(String msg, Throwable t) {
        super(msg, t);
        this.setStackTrace(t.getStackTrace());
    }

    public SqlException(String code, String msg, Throwable t) {
    	super(msg, t);
    	this.code = code;
    	this.setStackTrace(t.getStackTrace());
    }

}
