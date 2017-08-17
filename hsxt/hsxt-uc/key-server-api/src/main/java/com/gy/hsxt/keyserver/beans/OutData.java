package com.gy.hsxt.keyserver.beans;

import com.alibaba.fastjson.JSONObject;


public class OutData implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7842961707297824913L;
	ErrorCode status;
	Object data;
	public OutData(){
	
	}
	public OutData(ErrorCode status,Object data){
		this.status = status;
		this.data = data;
	}
	public ErrorCode getStatus() {
		return status;
	}
	public void setStatus(ErrorCode status) {
		this.status = status;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	public String toString() {
		return JSONObject.toJSONString(this);
	}
}
