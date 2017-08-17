/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.common.bean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 验证码参数
 * 
 * @Package: com.gy.hsxt.access.web.bean
 * @ClassName: VaildCodeParam
 * @Description: TODO
 * @author: likui
 * @date: 2015年12月30日 下午8:31:33
 * @company: gyist
 * @version V3.0.0
 */
public class VaildCodeParam implements Serializable {

	private static final long serialVersionUID = 1719814018756428147L;

	/** 验证码个数 **/
	private int codeCount;

	/** 验证码类型 **/
	private int codeType;

	/** 定义图片的width **/
	private int width;

	/** 定义图片的height **/
	private int height;

	/** X坐标 **/
	private int codeX;

	/** Y坐标 **/
	private int codeY;

	/** 字体大小 **/
	private int fontSize;

	public VaildCodeParam(int codeCount, int codeType)
	{
		super();
		this.codeCount = codeCount;
		this.codeType = codeType;
	}

	public VaildCodeParam(int codeCount, int codeType, int width, int height, int codeX, int codeY, int fontSize)
	{
		super();
		this.codeCount = codeCount;
		this.codeType = codeType;
		this.width = width;
		this.height = height;
		this.codeX = codeX;
		this.codeY = codeY;
		this.fontSize = fontSize;
	}

	public int getCodeCount()
	{
		return codeCount;
	}

	public void setCodeCount(int codeCount)
	{
		this.codeCount = codeCount;
	}

	public int getCodeType()
	{
		return codeType;
	}

	public void setCodeType(int codeType)
	{
		this.codeType = codeType;
	}

	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public int getHeight()
	{
		return height;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

	public int getCodeX()
	{
		return codeX;
	}

	public void setCodeX(int codeX)
	{
		this.codeX = codeX;
	}

	public int getCodeY()
	{
		return codeY;
	}

	public void setCodeY(int codeY)
	{
		this.codeY = codeY;
	}

	public int getFontSize()
	{
		return fontSize;
	}

	public void setFontSize(int fontSize)
	{
		this.fontSize = fontSize;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
