package com.gy.hsi.nt.server.entity.result;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 彻底失败消息实体
 * 
 * @Package: com.gy.hsi.nt.server.entity.result
 * @ClassName: AbandonMsg
 * @Description: TODO
 * @author: likui
 * @date: 2016年3月26日 下午3:14:23
 * @company: gyist
 * @version V3.0.0
 */
public class AbandonMsg extends BaseMsg implements Serializable {

	private static final long serialVersionUID = 3127733135512378819L;

	/**
	 * 失败次数
	 */
	private int failedCounts;

	public AbandonMsg()
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
