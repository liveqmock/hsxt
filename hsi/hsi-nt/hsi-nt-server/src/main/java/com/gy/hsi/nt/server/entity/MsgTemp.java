package com.gy.hsi.nt.server.entity;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @className:MsgTemp
 * @author:likui
 * @date:2015年7月27日
 * @desc:信息模板(邮件，短信)
 * @company:gyist
 */
public class MsgTemp implements Serializable {

	private static final long serialVersionUID = -4388939962931100244L;
	/**
	 * 模板编号
	 */
	@ExcelResources(title = "模板编号", order = 1)
	private String tempNo;
	/**
	 * 版本号
	 */
	@ExcelResources(title = "版本号", order = 2)
	private String version;
	/**
	 * 模板类型
	 */
	@ExcelResources(title = "模板类型", order = 3)
	private String tempType;
	/**
	 * 模板名称
	 */
	@ExcelResources(title = "模板名称", order = 4)
	private String tempName;
	/**
	 * 模板内容
	 */
	@ExcelResources(title = "模板内容", order = 5)
	private String tempContent;
	/**
	 * 是否可用(Y 可用N 不可用)
	 */
	@ExcelResources(title = "是否可用", order = 6)
	private String isUsable;
	/**
	 * 备注
	 */
	@ExcelResources(title = "备注", order = 7)
	private String mark;

	public MsgTemp()
	{
		super();
	}

	public MsgTemp(String tempNo, String version, String tempType, String tempName, String tempContent,
			String isUsable, String mark)
	{
		super();
		this.tempNo = tempNo;
		this.version = version;
		this.tempType = tempType;
		this.tempName = tempName;
		this.tempContent = tempContent;
		this.isUsable = isUsable;
		this.mark = mark;
	}

	public String getTempNo()
	{
		return StringUtils.isNotBlank(tempNo) ? tempNo.trim() : "";
	}

	public void setTempNo(String tempNo)
	{
		this.tempNo = tempNo;
	}

	public String getVersion()
	{
		return StringUtils.isNotBlank(version) ? version.trim() : "";
	}

	public void setVersion(String version)
	{
		this.version = version;
	}

	public String getTempType()
	{
		return StringUtils.isNotBlank(tempType) ? tempType.trim() : "";
	}

	public void setTempType(String tempType)
	{
		this.tempType = tempType;
	}

	public String getTempName()
	{
		return StringUtils.isNotBlank(tempName) ? tempName.trim() : "";
	}

	public void setTempName(String tempName)
	{
		this.tempName = tempName;
	}

	public String getTempContent()
	{
		return StringUtils.isNotBlank(tempContent) ? tempContent.trim() : "";
	}

	public void setTempContent(String tempContent)
	{
		this.tempContent = tempContent;
	}

	public String getIsUsable()
	{
		return StringUtils.isNotBlank(isUsable) ? isUsable.trim() : "";
	}

	public void setIsUsable(String isUsable)
	{
		this.isUsable = isUsable;
	}

	public String getMark()
	{
		return StringUtils.isNotBlank(mark) ? mark.trim() : "";
	}

	public void setMark(String mark)
	{
		this.mark = mark;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
