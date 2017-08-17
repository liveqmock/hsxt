/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.es.bean;

import java.io.Serializable;

/**
 * @Package: com.gy.hsxt.ps.bean
 * @ClassName: Result
 * @Description: 返回应答结果
 * 
 * @author: chenhongzhi
 * @date: 2015-10-27 下午2:40:31
 * @version V3.0
 */
public class Result implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5228350681525212416L;
	/** 应答码 **/
	private Integer resCode;
	/** 应答时间(会计时间) **/
	private String resDate;
	/** 应答描述 **/
	private String resMsg;

	/**
	 * @return the 应答码
	 */
	public Integer getResCode()
	{
		return resCode;
	}

	/**
	 * @param 应答码
	 *            the resCode to set
	 */
	public void setResCode(Integer resCode)
	{
		this.resCode = resCode;
	}

	/**
	 * @return the 应答描述
	 */
	public String getResMsg()
	{
		return resMsg;
	}

	/**
	 * @param 应答描述
	 *            the resMsg to set
	 */
	public void setResMsg(String resMsg)
	{
		this.resMsg = resMsg;
	}

	/**
	 * 获取应答时间
	 * @return resDate 应答时间
	 */
	public String getResDate()
	{
		return resDate;
	}

	/**
	 * 设置应答时间
	 * @param resDate 应答时间
	 */
	public void setResDate(String resDate)
	{
		this.resDate = resDate;
	}

}
