/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.model;

/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.model  
 * @ClassName: Afd 
 * @Description: 处理请求域的基类
 *
 * @author: wucl 
 * @date: 2015-11-10 下午3:01:32 
 * @version V1.0
 */
public class Afd {

	/**
	 * 请求域处理
	 */
	public String doRequestProcess(String data) throws Exception {
		return data;
	}

	/**
	 * 请求域处理
	 */
	public String doRequestProcess(String data, byte[] partVersion) throws Exception {
		return data;
	}

	/**
	 * 请求域处理
	 */
	public Object doRequestProcess(String msgId, String data) throws Exception {
		return data;
	}

	/**
	 * 请求域处理
	 */
	public Object doRequestProcess(String msgId, String data, byte[] partVersion) throws Exception {
		return data;
	}

	/**
	 * 请求域处理
	 */
	public Object doRequestProcess(String msgId, byte[] dat, byte[] partVersion) throws Exception {
		return dat;
	}

	/**
	 * 应答域处理
	 */
	public String doResponseProcess() throws Exception {
		return null;
	}

	/**
	 * 应答域处理
	 */
	public String doResponseProcess(Object data) throws Exception {
		return (String) data;
	}

	/**
	 * 应答域处理
	 */
	public String doResponseProcess(String msgId, Object data) throws Exception {
		return (String) data;
	}

	/**
	 * 应答域处理
	 */
	public String doResponseProcess(String msgId, Object data, byte[] pversion) throws Exception {
		return (String) data;
	}

	/**
	 * 应答域处理
	 */
	public String doResponseProcess(String msgId, Object data, Object extend, byte[] pversion) throws Exception {
		return (String) data;
	}

}
