/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.tool;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.bs.bean.base.Base;

/**
 * 仓库Bean
 * 
 * @Package: com.gy.hsxt.bs.bean.tool
 * @ClassName: Warehouse
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:34:53
 * @company: gyist
 * @version V3.0.0
 */
public class Warehouse extends Base implements Serializable {

	private static final long serialVersionUID = 2219545168383968967L;
	/** 仓库id **/
	private String whId;

	/** 仓库名称 **/
	private String whName;

	/** 仓库管理员角色编号 **/
	private String whRoleId;

	/** 手机号码 **/
	private String mobile;

	/** 电话号码 **/
	private String phone;

	/** 仓库地址 **/
	private String whAddr;

	/** 是否默认仓库 false:不是 true:是 **/
	private Boolean isDefault;

	/** 备注 **/
	private String remark;

	/** 仓库配送区域,为空时选择默认仓库 **/
	private List<WhArea> whArea;

	/** 修改时,该仓库包含的省代码 **/
	private List<String> provinceNos;

	public Warehouse()
	{
		super();
	}

	public Warehouse(String whId, String whName, String whRoleId, String mobile, String phone, String whAddr,
			Boolean isDefault, String remark, List<WhArea> whArea)
	{
		super();
		this.whId = whId;
		this.whName = whName;
		this.whRoleId = whRoleId;
		this.mobile = mobile;
		this.phone = phone;
		this.whAddr = whAddr;
		this.isDefault = isDefault;
		this.remark = remark;
		this.whArea = whArea;
	}

	public String getWhId()
	{
		return whId;
	}

	public void setWhId(String whId)
	{
		this.whId = whId;
	}

	public String getWhName()
	{
		return whName;
	}

	public void setWhName(String whName)
	{
		this.whName = whName;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getWhAddr()
	{
		return whAddr;
	}

	public void setWhAddr(String whAddr)
	{
		this.whAddr = whAddr;
	}

	public Boolean getIsDefault()
	{
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault)
	{
		this.isDefault = isDefault;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	public String getWhRoleId()
	{
		return whRoleId;
	}

	public void setWhRoleId(String whRoleId)
	{
		this.whRoleId = whRoleId;
	}

	public List<WhArea> getWhArea()
	{
		return whArea;
	}

	public void setWhArea(List<WhArea> whArea)
	{
		this.whArea = whArea;
	}

	public List<String> getProvinceNos()
	{
		return provinceNos;
	}

	public void setProvinceNos(List<String> provinceNos)
	{
		this.provinceNos = provinceNos;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
