package com.gy.hsxt.uc.search.service;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

/**
 * 分页对象，包含当前页码、每页条数、查询结果总数等分页信息
 * 
 * @Package: com.gy.hsxt.uc.search.service  
 * @ClassName: Paginate 
 * @Description: TODO
 *
 * @author: lixuan 
 * @date: 2015-11-19 下午6:47:06 
 * @version V1.0
 */
public class Paginate implements Serializable {

	private static final long serialVersionUID = 4459229297203511938L;

	// 记录总数
	private long totalNum;

	// 当前页码
	private int currentPage = 1;

	// 每页条数
	private int pageSize = 1;

	// 总页数
	private int totalPage = 1;

	/**
	 * 
	 * @param pageSize
	 *            每页条数
	 */
	public Paginate(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 获得总页数
	 * 
	 * @return
	 */
	public int getTotalPage() {
		return totalPage;
	}

	/**
	 * 设置总页数
	 */
	public void setTotalPage() {
		if (totalNum > 0 && pageSize > 0) {
			Long page = totalNum / pageSize;
			if (totalNum % pageSize > 0) {
				totalPage = page.intValue() + 1;
			} else {
				totalPage = page.intValue();
			}
		}
	}

	/**
	 * 记录总数
	 * 
	 * @return
	 */
	public long getTotalNum() {
		return totalNum;
	}

	/**
	 * 记录总数
	 * 
	 * @param totalNum
	 */
	public void setTotalNum(long totalNum) {
		this.totalNum = totalNum;
		setTotalPage();
	}

	/**
	 * 当前页码
	 * 
	 * @return
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * 当前页码
	 * 
	 * @param currentPage
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * 获得每页条数
	 * 
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 是否有上一页
	 * 
	 * @return
	 */
	public boolean isHasPrevious() {
		return currentPage <= 1 ? false : true;
	}

	/**
	 * 是否有下一页
	 * 
	 * @return
	 */
	public boolean isHasNext() {
		return currentPage >= totalPage ? false : true;
	}

	/**
	 * 当前页起始记录数，第一条为0
	 * 
	 * @return
	 */
	public int getStartNum() {
		return (currentPage - 1) * pageSize;
	}

	/**
	 * 当前页结束记录数
	 * 
	 * @return
	 */
	public long getEndNum() {
		long endNum = currentPage * pageSize - 1;
		return endNum >= totalNum ? totalNum - 1 : endNum;
	}

	public String toString() {
		return JSONObject.toJSONString(this);
	}

}
