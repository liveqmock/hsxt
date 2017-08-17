/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.nt.api.beans;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSON;

/**
 * 公用分页查询返回参数类
 * 
 * @Package: com.gy.hsi.nt.api.beans
 * @ClassName: PageData
 * @Description: TODO
 * @author: likui
 * @date: 2016年3月26日 上午11:48:08
 * @company: gyist
 * @version V3.0.0
 * @param <T>
 */
public class PageData<T> implements Serializable {

	private static final long serialVersionUID = -2916318288115383843L;
	/** 分页查询时总记录数 **/
	private int count;
	/** 分页查询返回单页结果集 **/
	private List<T> result;

	public PageData()
	{
		super();
	}

	public PageData(int count, List<T> result)
	{
		super();
		this.count = count;
		this.result = result;
	}

	public int getCount()
	{
		return count;
	}

	public void setCount(int count)
	{
		this.count = count;
	}

	public List<T> getResult()
	{
		return result;
	}

	public void setResult(List<T> result)
	{
		this.result = result;
	}

	/**
	 * 构建分页查询结果
	 *
	 * @param count
	 *            总记录数
	 * @param result
	 *            数据
	 * @param <T>
	 *            数据类型
	 * @return list
	 */
	public static <T> PageData<T> bulid(int count, List<T> result)
	{
		return new PageData<>(count, result);
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
