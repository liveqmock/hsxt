package com.gy.hsxt.es.distribution.bean;

import java.io.Serializable;

import com.gy.hsxt.es.common.PropertyConfigurer;

/**
 * Simple to Introduction
 * 
 * @category Simple to Introduction
 * @projectName hsxt-ps-service
 * @package com.gy.hsxt.ps.distribution.bean.PointPage.java
 * @className Page
 * @description 一句话描述该类的功能
 * @author liuchao
 * @createDate 2015-8-13 下午12:01:52
 * @updateUser chenhongzhi
 * @updateDate 2015-8-13 下午12:01:52
 * @updateRemark 说明本次修改内容
 * @version v0.0.1
 */
public class PointPage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8651994209102933816L;

	/** * 一共多少批数据 **/
	private int pageCount;

	/** * 每次加载数据最大数据 **/
	private int pageSize;

	/** * 数据总大小 **/
	private int pageSum;

	private int startsRow;

	private int endRow;

	public PointPage() {
		pageSize = Integer.parseInt(PropertyConfigurer.getProperty("es.batchSum"));
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


}
