package com.gy.hsxt.bs.bean.quota.result;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @className QuotaStatOfProvince
 * @author:likui
 * @date:2015年9月2日
 * @desc: 省资源统计数据，管理公司下省级分页返回数据
 * @company:gyist
 */
public class QuotaStatOfProvince implements Serializable {

	private static final long serialVersionUID = 1882583548004434696L;
	/**
	 * 省代码
	 */
	private String provinceNo;
	/**
	 * 币种编号
	 */
	private String currencyNo;
	/**
	 * 计划服务公司资源数
	 */
	private Integer planSResNum;
	/**
	 * 当前服务公司数
	 */
	private Integer useSResNum;

	public String getProvinceNo()
	{
		return provinceNo;
	}

	public void setProvinceNo(String provinceNo)
	{
		this.provinceNo = provinceNo;
	}

	public String getCurrencyNo()
	{
		return currencyNo;
	}

	public void setCurrencyNo(String currencyNo)
	{
		this.currencyNo = currencyNo;
	}

	public Integer getPlanSResNum()
	{
		return planSResNum;
	}

	public void setPlanSResNum(Integer planSResNum)
	{
		this.planSResNum = planSResNum;
	}

	public Integer getUseSResNum()
	{
		return useSResNum;
	}

	public void setUseSResNum(Integer useSResNum)
	{
		this.useSResNum = useSResNum;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
