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
 * 供应商Bean
 * 
 * @Package: com.gy.hsxt.bs.bean.tool
 * @ClassName: Supplier
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:33:29
 * @company: gyist
 * @version V3.0.0
 */
public class Supplier extends Base implements Serializable {

	private static final long serialVersionUID = -5437425560944560308L;

	/** 供应商id **/
	private String supplierId;

	/** 供应商名称 **/
	private String supplierName;

	/** 公司名称 **/
	private String corpName;

	/** 联系人 **/
	private String linkMan;

	/** 供应商详细地址 **/
	private String addr;

	/** 电话号码 **/
	private String phone;

	/** 手机号码 **/
	private String mobile;

	/** 传真号码 **/
	private String fax;

	/** 邮箱 **/
	private String email;

	/** 网址 **/
	private String website;

	/** 备注 **/
	private String remark;

	public Supplier()
	{
		super();
	}

	public Supplier(String supplierId, String supplierName, String corpName,
			String linkMan, String addr, String phone, String mobile,
			String fax, String email, String website, String remark)
	{
		super();
		this.supplierId = supplierId;
		this.supplierName = supplierName;
		this.corpName = corpName;
		this.linkMan = linkMan;
		this.addr = addr;
		this.phone = phone;
		this.mobile = mobile;
		this.fax = fax;
		this.email = email;
		this.website = website;
		this.remark = remark;
	}

	public String getSupplierId()
	{
		return supplierId;
	}

	public void setSupplierId(String supplierId)
	{
		this.supplierId = supplierId;
	}

	public String getSupplierName()
	{
		return supplierName;
	}

	public void setSupplierName(String supplierName)
	{
		this.supplierName = supplierName;
	}

	public String getCorpName()
	{
		return corpName;
	}

	public void setCorpName(String corpName)
	{
		this.corpName = corpName;
	}

	public String getLinkMan()
	{
		return linkMan;
	}

	public void setLinkMan(String linkMan)
	{
		this.linkMan = linkMan;
	}

	public String getAddr()
	{
		return addr;
	}

	public void setAddr(String addr)
	{
		this.addr = addr;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	public String getFax()
	{
		return fax;
	}

	public void setFax(String fax)
	{
		this.fax = fax;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getWebsite()
	{
		return website;
	}

	public void setWebsite(String website)
	{
		this.website = website;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
