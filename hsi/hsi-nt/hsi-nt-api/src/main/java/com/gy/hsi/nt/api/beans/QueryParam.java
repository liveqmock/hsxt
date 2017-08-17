/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsi.nt.api.beans;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 查询参数
 * 
 * @Package: com.gy.hsi.nt.api.beans
 * @ClassName: QueryParam
 * @Description: TODO
 * @author: likui
 * @date: 2016年3月26日 下午5:54:14
 * @company: gyist
 * @version V3.0.0
 */
public class QueryParam implements Serializable {

	private static final long serialVersionUID = -8888767638317952990L;

	/** 默认每页10条记录 **/
	public static final int DEFAULT_PAGE_SIZE = 10;

	/**
	 * 互生号
	 */
	private String hsResNo;
	/**
	 * 客户名称
	 */
	private String custName;
	/**
	 * 客户类型
	 */
	private Integer custType;

	/**
	 * 状态
	 */
	private String status;

	/**
	 * 每页条数
	 */
	private int pageSize = DEFAULT_PAGE_SIZE;

	/**
	 * 页数
	 */
	private int pages;
	/**
	 * 起始行数
	 */
	private int startCount = 0;

	public String getHsResNo()
	{
		return hsResNo;
	}

	public void setHsResNo(String hsResNo)
	{
		this.hsResNo = hsResNo;
	}

	public String getCustName()
	{
		return custName;
	}

	public void setCustName(String custName)
	{
		this.custName = custName;
	}

	public Integer getCustType()
	{
		return custType;
	}

	public void setCustType(Integer custType)
	{
		this.custType = custType;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public int getPageSize()
	{
		return pageSize;
	}

	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}

	public int getPages()
	{
		return pages;
	}

	public void setPages(int pages)
	{
		this.pages = pages;
	}

	public int getStartCount()
	{
		return startCount;
	}

	public void setStartCount(int startCount)
	{
		this.startCount = startCount;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
