/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.order;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 收货信息实体类
 * 
 * @Package: com.gy.hsxt.bs.bean.order
 * @ClassName: DeliverInfoBean
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-9-1 上午9:04:37
 * @version V1.0
 */
public class DeliverInfo implements Serializable {
	private static final long serialVersionUID = 8388583446204321017L;

	/** 收货信息编号 **/
	private String deliverId;

	/** 客户号 **/
	private String hsCustId;

	/** 街道地址,不需要重复填写省市区县 **/
	private String streetAddr;

	/** 完整的详细地址,从省到具体地址 **/
	private String fullAddr;

	/** 收货联系人 **/
	private String linkman;

	/** 手机号码 **/
	private String mobile;

	/** 电话号码 **/
	private String phone;

	/** 邮政编码 **/
	private String zipCode;

	/** 记录是否有效Y：有效 N：无效 **/
	private String isactive;

	/** 地址类型 **/
	private String addrType;

	/** 创建时间 **/
	private String createdDate;

	/** 由谁创建 **/
	private String createdby;

	/** 更新时间 **/
	private String updatedDate;

	/** 由谁更新 **/
	private String updateby;

	public String getDeliverId()
	{
		return deliverId;
	}

	public void setDeliverId(String deliverId)
	{
		this.deliverId = deliverId == null ? null : deliverId.trim();
	}

	public String getHsCustId()
	{
		return hsCustId;
	}

	public void setHsCustId(String hsCustId)
	{
		this.hsCustId = hsCustId;
	}

	public String getStreetAddr()
	{
		return streetAddr;
	}

	public void setStreetAddr(String streetAddr)
	{
		this.streetAddr = streetAddr == null ? null : streetAddr.trim();
	}

	public String getFullAddr()
	{
		return fullAddr;
	}

	public void setFullAddr(String fullAddr)
	{
		this.fullAddr = fullAddr == null ? null : fullAddr.trim();
	}

	public String getLinkman()
	{
		return linkman;
	}

	public void setLinkman(String linkman)
	{
		this.linkman = linkman == null ? null : linkman.trim();
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile == null ? null : mobile.trim();
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone == null ? null : phone.trim();
	}

	public String getZipCode()
	{
		return zipCode;
	}

	public void setZipCode(String zipCode)
	{
		this.zipCode = zipCode == null ? null : zipCode.trim();
	}

	public String getAddrType()
	{
		return addrType;
	}

	public void setAddrType(String addrType)
	{
		this.addrType = addrType;
	}

	public String getIsactive()
	{
		return isactive;
	}

	public void setIsactive(String isactive)
	{
		this.isactive = isactive == null ? null : isactive.trim();
	}

	public String getCreatedDate()
	{
		return createdDate;
	}

	public void setCreatedDate(String createdDate)
	{
		this.createdDate = createdDate;
	}

	public String getCreatedby()
	{
		return createdby;
	}

	public void setCreatedby(String createdby)
	{
		this.createdby = createdby == null ? null : createdby.trim();
	}

	public String getUpdatedDate()
	{
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate)
	{
		this.updatedDate = updatedDate;
	}

	public String getUpdateby()
	{
		return updateby;
	}

	public void setUpdateby(String updateby)
	{
		this.updateby = updateby == null ? null : updateby.trim();
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
