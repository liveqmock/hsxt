package com.gy.hsxt.rabbitmq.center.framework.bean;



/**
 * 
 * @ClassName: BaseResponse 
 * @Description: 响应消息实体bean基类 
 * @author Lee 
 * @date 2015-6-30 下午4:41:30
 */
public class BaseResponse {
	
	private String retCode;
	
	private String message;

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
}	
