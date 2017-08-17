package com.gy.hsxt.keyserver.beans;

import com.alibaba.fastjson.JSONObject;

public class MacDecode implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -195347560534123441L;
	String mac;
	byte []data;
	public MacDecode(String mac,byte[] data){
		this.mac = mac;
		this.data = data;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public String toString() {
		return JSONObject.toJSONString(this);
	}
}
