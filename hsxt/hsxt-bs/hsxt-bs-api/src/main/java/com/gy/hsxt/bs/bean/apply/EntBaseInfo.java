/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bean.apply;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @Package: com.gy.hsxt.bs.bean.apply
 * @ClassName: EntBaseInfo
 * @Description: 申报企业基本信息（官网接口用）
 * 
 * @author: xiaofl
 * @date: 2015-12-11 下午4:10:00
 * @version V1.0
 */
public class EntBaseInfo implements Serializable {

	private static final long serialVersionUID = -5870220793595814762L;

	/** 企业名称 **/
	private String entName;

	/** 客户类型 **/
	private Integer custType;

	/** 申报日期 **/
	private String applyDate;

	/** 国家代码 */
	private String countryNo;

	/** 省代码 */
	private String provinceNo;

	/** 城市代码 */
	private String cityNo;

	/** 状态 */
	private Integer status;

	/** 地址(国家 + 省 + 城市) */
	private String address;

	public String getEntName()
	{
		return entName;
	}

	public void setEntName(String entName)
	{
		this.entName = entName;
	}

	public Integer getCustType()
	{
		return custType;
	}

	public void setCustType(Integer custType)
	{
		this.custType = custType;
	}

	public String getApplyDate()
	{
		return applyDate;
	}

	public void setApplyDate(String applyDate)
	{
		this.applyDate = applyDate;
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

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}

}
