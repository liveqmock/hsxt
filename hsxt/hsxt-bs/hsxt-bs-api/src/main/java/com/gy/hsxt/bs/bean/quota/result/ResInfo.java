package com.gy.hsxt.bs.bean.quota.result;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @className:ResInfo
 * @author:likui
 * @date:2015年9月2日
 * @desc:服务资源信息，查看城市资源明细用
 * @company:gyist
 */
public class ResInfo implements Serializable {

	private static final long serialVersionUID = -3506998302237232249L;
	/**
	 * 互生号
	 */
	private String entResNo;
	/**
	 * 企业名称
	 */
	private String entCustName;
	/**
	 * 注册日期
	 */
	private String regDate;

	public String getEntResNo()
	{
		return entResNo;
	}

	public void setEntResNo(String entResNo)
	{
		this.entResNo = entResNo;
	}

	public String getEntCustName()
	{
		return entCustName;
	}

	public void setEntCustName(String entCustName)
	{
		this.entCustName = entCustName;
	}

	public String getRegDate()
	{
		return regDate;
	}

	public void setRegDate(String regDate)
	{
		this.regDate = regDate;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
