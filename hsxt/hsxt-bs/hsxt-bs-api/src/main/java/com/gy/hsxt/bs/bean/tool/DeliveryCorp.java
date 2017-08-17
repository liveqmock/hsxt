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
 * 快递公司Bean
 * 
 * @Package: com.gy.hsxt.bs.bean.tool
 * @ClassName: DeliveryCorp
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:30:48
 * @company: gyist
 * @version V3.0.0
 */
public class DeliveryCorp extends Base implements Serializable {

	private static final long serialVersionUID = 319316564846546162L;

	/** 快递公司ID **/
	private String dcId;

	/** 快递公司名称 **/
	private String corpName;

	/** 快递公司网址 **/
	private String corpUrl;

	/** 快递公司代码 **/
	private String corpCode;

	/** 联系人名称 **/
	private String linkMan;

	/** 联系电话 **/
	private String phone;

	/** 快递公司址地 **/
	private String addr;

	/** 办公电话 **/
	private String officePhone;

	/** 快递公司说明 **/
	private String description;

	/** 排序 **/
	private Integer sort;

	public DeliveryCorp()
	{
		super();
	}

	public DeliveryCorp(String dcId, String corpName, String corpUrl,
			String corpCode, String linkMan, String phone, String addr,
			String officePhone, String description, Integer sort)
	{
		super();
		this.dcId = dcId;
		this.corpName = corpName;
		this.corpUrl = corpUrl;
		this.corpCode = corpCode;
		this.linkMan = linkMan;
		this.phone = phone;
		this.addr = addr;
		this.officePhone = officePhone;
		this.description = description;
		this.sort = sort;
	}

	public String getDcId()
	{
		return dcId;
	}

	public void setDcId(String dcId)
	{
		this.dcId = dcId;
	}

	public String getCorpName()
	{
		return corpName;
	}

	public void setCorpName(String corpName)
	{
		this.corpName = corpName;
	}

	public String getCorpUrl()
	{
		return corpUrl;
	}

	public void setCorpUrl(String corpUrl)
	{
		this.corpUrl = corpUrl;
	}

	public String getCorpCode()
	{
		return corpCode;
	}

	public void setCorpCode(String corpCode)
	{
		this.corpCode = corpCode;
	}

	public String getLinkMan()
	{
		return linkMan;
	}

	public void setLinkMan(String linkMan)
	{
		this.linkMan = linkMan;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getAddr()
	{
		return addr;
	}

	public void setAddr(String addr)
	{
		this.addr = addr;
	}

	public String getOfficePhone()
	{
		return officePhone;
	}

	public void setOfficePhone(String officePhone)
	{
		this.officePhone = officePhone;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Integer getSort()
	{
		return sort;
	}

	public void setSort(Integer sort)
	{
		this.sort = sort;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
