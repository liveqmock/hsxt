package com.gy.hsxt.keyserver.beans;

public enum ErrorCode {
	OK(0),DATABASEERROR(1),NODATAFOUND(2),OTHERERROR(9999);
	
	private int code;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	ErrorCode(int code) {
		this.code = code;
	}
	public static ErrorCode getStatus(int code) {
		for (ErrorCode e : ErrorCode.values()) {
			if (e.getCode() == code) {
				return e;
			}
		}
		return null;
	}
}
