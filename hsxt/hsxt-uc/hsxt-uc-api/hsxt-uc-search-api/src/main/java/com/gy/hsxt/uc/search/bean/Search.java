/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.search.bean;

import java.io.Serializable;

/**
 * 
 * 搜索基类，包含分页信息，所有搜索条件和搜索结果对象都需继承该对象
 * 
 * @Package: com.gy.hsxt.uc.search.bean 
 * @ClassName: Search 
 * @Description: TODO
 *
 * @author: lixuan 
 * @date: 2015-11-20 上午10:11:34 
 * @version V1.0
 */
public class Search implements Serializable {
	// 默认分页每页条数
	private static final int PAGE_SIZE = 25;

	private static final long serialVersionUID = 681856475023275809L;
	// 分页信息
	private Paginate paginate;

	// 搜索结果正序或倒序
	private boolean isAsc = true;
	// core名称
	private String coreName;
	// 排序字段名称
	private String sortName;
	/** 过滤字段 */
	private String filterField;
	/** 过滤查询  */
	private String[] filterQuery;

	/**
	 * @return the 过滤字段
	 */
	public String getFilterField() {
		return filterField;
	}

	/**
	 * @param 过滤字段 the filterField to set
	 */
	public void setFilterField(String filterField) {
		this.filterField = filterField;
	}

	/**
	 * @return the 过滤查询
	 */
	public String[] getFilterQuery() {
		return filterQuery;
	}

	/**
	 * @param 过滤查询 the filterQuery to set
	 */
	public void setFilterQuery(String[] filterQuery) {
		this.filterQuery = filterQuery;
	}

	/**
	 * 获取排序名称
	 * 
	 * @return
	 */
	public String getSortName() {
		return sortName;
	}

	/**
	 * 设置排序名称
	 * 
	 * @param sortName
	 */
	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	/**
	 * 设置core名称
	 * @param coreName
	 */
	public void setCoreName(String coreName) {
		this.coreName = coreName;
	}

	/**
	 * 获取core名称
	 * @return
	 */
	public String getCoreName() {
		return coreName;
	}

	/**
	 * 排序顺序是否是正序，由小到大，由低到高
	 * 
	 * @return false为为倒序，true为正序
	 */
	public boolean isAsc() {
		return isAsc;
	}

	/**
	 * 设置排序顺序是否是正序，由小到大，由低到高
	 * 
	 * @param isAsc
	 *            false为为倒序，true为正序
	 */
	public void setAsc(boolean isAsc) {
		this.isAsc = isAsc;
	}

	/**
	 * 获取分页信息
	 * 
	 * @return
	 */
	public Paginate getPaginate() {
		return paginate;
	}

	/**
	 * 设置分页信息
	 * 
	 * @param paginate
	 */
	public void setPaginate(Paginate paginate) {
		this.paginate = paginate;
	}

}
