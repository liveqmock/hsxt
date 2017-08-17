/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.bean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 官网接入返回对象
 * 
 * @Package: com.gy.hsxt.access.web.bean
 * @ClassName: HttpResp
 * @Description: TODO
 * @author: likui
 * @date: 2015年12月10日 上午10:27:05
 * @company: gyist
 * @version V3.0.0
 */
public class HttpResp implements Serializable {

	private static final long serialVersionUID = -3210686267415500761L;

	/** 返回代码 **/
	private int retCode;

	/** 返回描述 **/
	private String resultDesc;

	/** 返回数据 **/
	private Object retData;

	public HttpResp(int retCode)
	{
		super();
		this.retCode = retCode;
	}

	public HttpResp(int retCode, Object retData)
	{
		super();
		this.retCode = retCode;
		this.retData = retData;
	}

	public HttpResp(int retCode, String resultDesc, Object retData)
	{
		super();
		this.retCode = retCode;
		this.resultDesc = resultDesc;
		this.retData = retData;
	}

	public int getRetCode()
	{
		return retCode;
	}

	public void setRetCode(int retCode)
	{
		this.retCode = retCode;
	}

	public String getResultDesc()
	{
		return resultDesc;
	}

	public void setResultDesc(String resultDesc)
	{
		this.resultDesc = resultDesc;
	}

	public Object getRetData()
	{
		return retData;
	}

	public void setRetData(Object retData)
	{
		this.retData = retData;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
