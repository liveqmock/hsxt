/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.tool.bean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 重做互生卡号码Bean
 * 
 * @Package: com.gy.hsxt.bs.tool.bean
 * @ClassName: MakingCardInfo
 * @Description: TODO
 * @author: likui
 * @date: 2015年11月5日 下午2:35:10
 * @company: gyist
 * @version V3.0.0
 */
public class MakingCardInfo implements Serializable {

	private static final long serialVersionUID = 6443739982459260436L;

	/** 配置单号 **/
	private String confNo;

	/** 个人互生号 **/
	private String perResNo;

	/** 重做卡类型 1: 个人补卡 2:企业重做 **/
	private Integer remarkType;

	public MakingCardInfo()
	{
		super();
	}

	public MakingCardInfo(String confNo, String perResNo, Integer remarkType)
	{
		super();
		this.confNo = confNo;
		this.perResNo = perResNo;
		this.remarkType = remarkType;
	}

	public String getConfNo()
	{
		return confNo;
	}

	public void setConfNo(String confNo)
	{
		this.confNo = confNo;
	}

	public String getPerResNo()
	{
		return perResNo;
	}

	public void setPerResNo(String perResNo)
	{
		this.perResNo = perResNo;
	}

	public Integer getRemarkType()
	{
		return remarkType;
	}

	public void setRemarkType(Integer remarkType)
	{
		this.remarkType = remarkType;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
