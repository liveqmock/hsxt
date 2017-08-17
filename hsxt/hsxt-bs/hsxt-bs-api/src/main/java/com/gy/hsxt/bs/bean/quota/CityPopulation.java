/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bean.quota;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 城市人口数Bean
 * 
 * @Package: com.gy.hsxt.bs.bean.quota
 * @ClassName: CityPopulation
 * @Description: TODO
 * @author: likui
 * @date: 2015年11月18日 下午6:06:11
 * @company: gyist
 * @version V3.0.0
 */
public class CityPopulation implements Serializable {

	private static final long serialVersionUID = -6212451323505465351L;

	/** 国家代码 **/
	private String countryNo;

	/** 省代码 **/
	private String provinceNo;

	/** 城市代码 **/
	private String cityNo;

	/** 人口数 **/
	private String population;

	public CityPopulation()
	{
		super();
	}

	public CityPopulation(String countryNo, String provinceNo, String cityNo,
			String population)
	{
		super();
		this.countryNo = countryNo;
		this.provinceNo = provinceNo;
		this.cityNo = cityNo;
		this.population = population;
	}

	public String getCountryNo()
	{
		return countryNo;
	}

	public void setCountryNo(String countryNo)
	{
		this.countryNo = countryNo;
	}

	public String getProvinceNo()
	{
		return provinceNo;
	}

	public void setProvinceNo(String provinceNo)
	{
		this.provinceNo = provinceNo;
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

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
