package com.gy.hsxt.bs.bean.quota.result;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @className:ProvinceStatisticsRes
 * @author:likui
 * @date:2015年9月2日
 * @desc: 省资源配额统计详情， 省级统计资源详情
 * @company:
 */
public class QuotaDetailOfProvince implements Serializable {

	private static final long serialVersionUID = 8868888403508631133L;
	/**
	 * 省代码
	 */
	private String provinceNo;
	/**
	 * 城市数量
	 */
	private Integer cityNum;
	/**
	 * 服务公司配额
	 */
	private Integer sResNum;
	/**
	 * 已使用数量
	 */
	private Integer useSResNum;
	/**
	 * 城市配额列表
	 */
	private List<QuotaStatOfCity> cityList;

	public String getProvinceNo()
	{
		return provinceNo;
	}

	public void setProvinceNo(String provinceNo)
	{
		this.provinceNo = provinceNo;
	}

	public Integer getCityNum()
	{
		return cityNum;
	}

	public void setCityNum(Integer cityNum)
	{
		this.cityNum = cityNum;
	}

	public Integer getsResNum()
	{
		return sResNum;
	}

	public void setsResNum(Integer sResNum)
	{
		this.sResNum = sResNum;
	}

	public Integer getUseSResNum()
	{
		return useSResNum;
	}

	public void setUseSResNum(Integer useSResNum)
	{
		this.useSResNum = useSResNum;
	}

	public List<QuotaStatOfCity> getCityList()
	{
		return cityList;
	}

	public void setCityList(List<QuotaStatOfCity> cityList)
	{
		this.cityList = cityList;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
