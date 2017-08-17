package com.gy.hsi.nt.server.entity.result;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 消息重发实体
 * 
 * @Package: com.gy.hsi.nt.server.entity.result
 * @ClassName: ResendMsg
 * @Description: TODO
 * @author: likui
 * @date: 2016年3月26日 下午3:14:50
 * @company: gyist
 * @version V3.0.0
 */
public class ResendMsg extends BaseMsg implements Serializable {

	private static final long serialVersionUID = -4505547325720377895L;
	/**
	 * 失败次数
	 */
	private int failedCounts;

	public ResendMsg()
	{
		super();
	}

	public int getFailedCounts()
	{
		return failedCounts;
	}

	public void setFailedCounts(int failedCounts)
	{
		this.failedCounts = failedCounts;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
