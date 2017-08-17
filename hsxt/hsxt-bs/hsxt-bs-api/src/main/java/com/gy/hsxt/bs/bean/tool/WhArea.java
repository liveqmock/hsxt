/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.tool;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 仓库配送区域Bean
 * 
 * @Package: com.gy.hsxt.bs.bean.tool
 * @ClassName: WhArea
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:35:07
 * @company: gyist
 * @version V3.0.0
 */
public class WhArea implements Serializable {

	private static final long serialVersionUID = -6850474092798373871L;
	/** 仓库id **/
	private String whId;

	/** 国家编号 **/
	private String countryNo;

	/** 省编号 **/
	private String provinceNo;

	public WhArea()
	{
		super();
	}

	public WhArea(String whId, String countryNo, String provinceNo)
	{
		super();
		this.whId = whId;
		this.countryNo = countryNo;
		this.provinceNo = provinceNo;
	}

	public String getWhId()
	{
		return whId;
	}

	public void setWhId(String whId)
	{
		this.whId = whId;
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

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
