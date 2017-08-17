/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.tool.bean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 批量开卡参数Bean
 * 
 * @Package: com.hsxt.bs.btool.bean
 * @ClassName: BatchOpenCard
 * @Description: TODO
 * @author: likui
 * @date: 2015年10月8日 下午3:38:23
 * @company: gyist
 * @version V3.0.0
 */
public class BatchOpenCard implements Serializable {

	private static final long serialVersionUID = 8800723400578070501L;

	/** 结算代码 **/
	private String settleCode;
	/** 托管互生号 **/
	private String entResNo;
	/** 开启数量 **/
	private Integer openCount;
	/** 配置单号 **/
	private String confNo;
	/** 返回结果 **/
	private Integer result;

	public BatchOpenCard()
	{
		super();
	}

	public BatchOpenCard(String settleCode, String entResNo, Integer openCount,
			String confNo)
	{
		super();
		this.settleCode = settleCode;
		this.entResNo = entResNo;
		this.openCount = openCount;
		this.confNo = confNo;
	}

	public String getSettleCode()
	{
		return settleCode;
	}

	public void setSettleCode(String settleCode)
	{
		this.settleCode = settleCode;
	}

	public String getEntResNo()
	{
		return entResNo;
	}

	public void setEntResNo(String entResNo)
	{
		this.entResNo = entResNo;
	}

	public Integer getOpenCount()
	{
		return openCount;
	}

	public void setOpenCount(Integer openCount)
	{
		this.openCount = openCount;
	}

	public String getConfNo()
	{
		return confNo;
	}

	public void setConfNo(String confNo)
	{
		this.confNo = confNo;
	}

	public Integer getResult()
	{
		return result;
	}

	public void setResult(Integer result)
	{
		this.result = result;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}

}
