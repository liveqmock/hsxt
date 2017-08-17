/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ps.distribution.bean;

import java.io.Serializable;

import com.gy.hsxt.ps.common.PropertyConfigurer;

/**
 * @Package: com.gy.hsxt.ps.distribution.bean
 * @ClassName: PointPage
 * @Description: 积分数据分页
 * 
 * @author: chenhongzhi
 * @date: 2015-12-3 上午10:51:29
 * @version V3.0
 */

public class PointPage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8651994209102933816L;

	/**执行时间*/
	private  String runDate;

	/** * 一共多少批数据 **/
	private int pageCount;

	/** * 每次加载数据最大数据 **/
	private int pageSize;

	/** * 数据总大小 **/
	private int pageSum;

	private int startsRow;

	private int endRow;

	public PointPage() {
		pageSize = Integer.parseInt(PropertyConfigurer.getProperty("ps.batchSum"));
	}

	/**
	 * 设置数据总大小
	 * 
	 * @param pageSum
	 * 数据总大小
	 */
	public void setPageSum(int pageSum) {
		this.pageSum = pageSum;
		this.pageCount = pageSum / pageSize;
		this.pageCount += (pageSum % pageSize != 0 ? 1 : 0);
	}

	public void setRow(int page) {
		this.startsRow = page * pageSize;
		this.endRow = (page + 1) * pageSize;
	}

	public int getPageCount() {
		return this.pageCount;
	}
	
	public int getPageSum() {
		return this.pageSum;
	}
	
	public int getStartsRow() {
		return this.startsRow;
	}
	
	public int getEndRow() {
		return this.endRow;
	}

	/**
	 * 获取每次加载数据最大数据
	 * @return int pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	public String getRunDate() {
		return runDate;
	}

	public void setRunDate(String runDate) {
		this.runDate = runDate;
	}
}
