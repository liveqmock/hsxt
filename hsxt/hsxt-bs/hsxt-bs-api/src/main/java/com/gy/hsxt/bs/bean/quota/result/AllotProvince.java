/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bean.quota.result;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 地区平台分配的省Bean
 * 
 * @Package: com.gy.hsxt.bs.bean.quota.result
 * @ClassName: AllotProvince
 * @Description: TODO
 * @author: likui
 * @date: 2015年11月25日 下午4:49:05
 * @company: gyist
 * @version V3.0.0
 */
public class AllotProvince implements Serializable {

	private static final long serialVersionUID = -8145161932340802346L;

	/** 管理公司互生号 **/
	private String mEntResNo;

	/** 省编号 **/
	private String provinceNo;

	public String getmEntResNo()
	{
		return mEntResNo;
	}

	public void setmEntResNo(String mEntResNo)
	{
		this.mEntResNo = mEntResNo;
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
