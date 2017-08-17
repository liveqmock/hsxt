/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.common.bean;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-common
 * 
 *  Package Name    : com.gy.hsxt.common.bean
 * 
 *  File Name       : Page.java
 * 
 *  Creation Date   : 2015-8-28
 * 
 *  Author          : yangjianguo
 * 
 *  Purpose         : 公用分页参数类
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
public class Page implements Serializable {
	private static final long serialVersionUID = 2608208497639736461L;
	/** 默认每页10条记录  **/
	public static final int DEFAULT_PAGE_SIZE = 10; 
	
	/** 每页记录数  **/
	private int pageSize = DEFAULT_PAGE_SIZE; 		
	/** 当前第几页  **/
	private int curPage = 1;						  
	/** 结果总记录条数，分页插件用来返回分页查询时总记录数  **/
	private int count;							
	
	@SuppressWarnings("unused")
	private Page(){
	}
	
	public Page(int curPage){
		this.curPage=curPage;
	}
	public Page(int curPage, int pageSize){
		this.curPage=curPage;
		this.pageSize=pageSize;
	}
	public int getPageSize() {
		return pageSize;
	} 
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
