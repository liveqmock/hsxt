/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.base;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.StringUtils;

/**
 * 通用查询条件参数实体类
 * 
 * @Package: com.gy.hsxt.bs.bean.base
 * @ClassName: BaseParam
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-9-25 上午10:45:20
 * @version V1.0
 */
public class BaseParam implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 开始日期 **/
	private String startDate;

	/** 截止时期 **/
	private String endDate;

	/** 互生号 **/
	private String hsResNo;

	/** 客户号 **/
	private String hsCustId;

	/** 经办人客户号 **/
	private String exeCustId;

	/** 客户名称 **/
	private String hsCustName;

	/** 状态 **/
	private Integer status;

	/** 类型 **/
	private String type;

	/** 角色id **/
	private String roleId;

	/** 操作员 **/
	private String operNo;

	/** 渠道 **/
	private Integer channel;

	/** 角色id列表 **/
	private String[] roleIds;

	/** 订单号 **/
	private String orderNo;

	/** 仓库id **/
	private String whId;

	/** 名称 **/
	private String name;

	/** 订单类型 **/
	private String orderType;

	public BaseParam()
	{
		super();
	}

	public BaseParam(String hsResNo, String hsCustName, Integer status, String type)
	{
		super();
		this.hsResNo = hsResNo;
		this.hsCustName = hsCustName;
		this.status = status;
		this.type = type;
	}

	public String getStartDate()
	{
		return startDate;
	}

	public void setStartDate(String startDate)
	{
		this.startDate = startDate;
	}

	public String getEndDate()
	{
		return endDate;
	}

	public void setEndDate(String endDate)
	{
		this.endDate = endDate;
	}

	public String getHsResNo()
	{
		return hsResNo;
	}

	public void setHsResNo(String hsResNo)
	{
		this.hsResNo = hsResNo;
	}

	public String getHsCustId()
	{
		return hsCustId;
	}

	public void setHsCustId(String hsCustId)
	{
		this.hsCustId = hsCustId;
	}

	public String getExeCustId()
	{
		return exeCustId;
	}

	public void setExeCustId(String exeCustId)
	{
		this.exeCustId = exeCustId;
	}

	public String getHsCustName()
	{
		return hsCustName;
	}

	public void setHsCustName(String hsCustName)
	{
		this.hsCustName = hsCustName;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getRoleId()
	{
		return roleId;
	}

	public String getOperNo()
	{
		return operNo;
	}

	public void setOperNo(String operNo)
	{
		this.operNo = operNo;
	}

	public Integer getChannel()
	{
		return channel;
	}

	public void setChannel(Integer channel)
	{
		this.channel = channel;
	}

	public String[] getRoleIds()
	{
		return roleIds;
	}

	public void setRoleIds(String[] roleIds)
	{
		this.roleIds = roleIds;
	}

	public String getOrderNo()
	{
		return orderNo;
	}

	public void setOrderNo(String orderNo)
	{
		this.orderNo = orderNo;
	}

	public String getWhId()
	{
		return whId;
	}

	public void setWhId(String whId)
	{
		this.whId = whId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getOrderType()
	{
		return orderType;
	}

	public void setOrderType(String orderType)
	{
		this.orderType = orderType;
	}

	/**
	 * 设置查询的日期,开始日期为最小,结束日期为最大
	 * 
	 * @Desc:term
	 * @author: likui
	 * @created: 2015年10月14日 下午7:53:21
	 * @param :
	 * @return : void
	 * @version V3.0.0
	 */
	public void setQueryDate()
	{
		if (StringUtils.isNotBlank(getStartDate()))
		{
			setStartDate(DateUtil.getMinDateOfDayStr(getStartDate()));
		}
		if (StringUtils.isNotBlank(getEndDate()))
		{
			setEndDate(DateUtil.getMaxDateOfDayStr(getEndDate()));
		}
	}

	/**
	 * 设置角色id列表
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月20日 上午10:34:39
	 * @return : void
	 * @version V3.0.0
	 */
	public void setRoleId()
	{
		if (null != getRoleIds() && getRoleIds().length > 0)
		{
			this.roleId = "IN ('" + org.apache.commons.lang3.StringUtils.join(getRoleIds(), "','") + "')";
		}
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
