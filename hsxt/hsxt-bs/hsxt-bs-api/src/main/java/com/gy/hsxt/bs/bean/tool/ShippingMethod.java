/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.tool;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.bs.bean.base.Base;

/**
 * 配送方式Bean
 * 
 * @Package: com.gy.hsxt.bs.bean.tool
 * @ClassName: ShippingMethod
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:33:18
 * @company: gyist
 * @version V3.0.0
 */
public class ShippingMethod extends Base implements Serializable {

	private static final long serialVersionUID = 5739876242315439471L;

	/** 配送方式编号 **/
	private String smId;

	/** *配送方式名称 **/
	private String smName;

	/** 配送方式图标 **/
	private String ico;

	/** 排序 **/
	private Integer sort;

	/** 配送方式描述 **/
	private String smDesc;

	public ShippingMethod()
	{
		super();
	}

	public ShippingMethod(String smId, String smName, String ico, Integer sort,
			String smDesc)
	{
		super();
		this.smId = smId;
		this.smName = smName;
		this.ico = ico;
		this.sort = sort;
		this.smDesc = smDesc;
	}

	public String getSmId()
	{
		return smId;
	}

	public void setSmId(String smId)
	{
		this.smId = smId;
	}

	public String getSmName()
	{
		return smName;
	}

	public void setSmName(String smName)
	{
		this.smName = smName;
	}

	public String getIco()
	{
		return ico;
	}

	public void setIco(String ico)
	{
		this.ico = ico;
	}

	public Integer getSort()
	{
		return sort;
	}

	public void setSort(Integer sort)
	{
		this.sort = sort;
	}

	public String getSmDesc()
	{
		return smDesc;
	}

	public void setSmDesc(String smDesc)
	{
		this.smDesc = smDesc;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
