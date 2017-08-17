package com.gy.hsxt.bs.bean.quota.result;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @className QuotaStatOfCity
 * @author:likui
 * @date:2015年9月2日
 * @desc: 城市资源配额统计数据， 省级下市级分页返回数据
 * @company:gyist
 */
public class QuotaStatOfCity implements Serializable {

	private static final long serialVersionUID = 13301818703048788L;
	/**
	 * 城市代码
	 */
	private String cityNo;
	/**
	 * 人口数
	 */
	private String population;
	/**
	 * 城市配额数量(总)
	 */
	private String totalNum;
	/**
	 * 已使用城市配额数量
	 */
	private String usedNum;
	/**
	 * 尚可用配额总数
	 */
	private String usableNum;
	/**
	 * 尚可用配额未使用数量
	 */
	private String unUseNum;
	/**
	 * 尚可用配额拟数量(占用)
	 */
	private String usingNum;
	/**
	 * 是否初始化 true:是 false:否
	 */
	private Boolean isInit;

	public QuotaStatOfCity()
	{
		super();
	}

	public QuotaStatOfCity(String cityNo, String population, String totalNum,
			String usedNum, String usableNum, String unUseNum, String usingNum,
			Boolean isInit)
	{
		super();
		this.cityNo = cityNo;
		this.population = population;
		this.totalNum = totalNum;
		this.usedNum = usedNum;
		this.usableNum = usableNum;
		this.unUseNum = unUseNum;
		this.usingNum = usingNum;
		this.isInit = isInit;
	}

	public String getCityNo()
	{
		return cityNo;
	}

	public void setCityNo(String cityNo)
	{
		this.cityNo = cityNo;
	}

	public String getPopulation()
	{
		return population;
	}

	public void setPopulation(String population)
	{
		this.population = population;
	}

	public String getTotalNum()
	{
		return totalNum;
	}

	public void setTotalNum(String totalNum)
	{
		this.totalNum = totalNum;
	}

	public String getUsedNum()
	{
		return usedNum;
	}

	public void setUsedNum(String usedNum)
	{
		this.usedNum = usedNum;
	}

	public String getUsableNum()
	{
		return usableNum;
	}

	public void setUsableNum(String usableNum)
	{
		this.usableNum = usableNum;
	}

	public String getUnUseNum()
	{
		return unUseNum;
	}

	public void setUnUseNum(String unUseNum)
	{
		this.unUseNum = unUseNum;
	}

	public String getUsingNum()
	{
		return usingNum;
	}

	public void setUsingNum(String usingNum)
	{
		this.usingNum = usingNum;
	}

	public Boolean getIsInit()
	{
		return isInit;
	}

	public void setIsInit(Boolean isInit)
	{
		this.isInit = isInit;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
