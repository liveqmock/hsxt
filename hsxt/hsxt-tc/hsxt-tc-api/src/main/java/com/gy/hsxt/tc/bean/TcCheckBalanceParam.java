package com.gy.hsxt.tc.bean;

import java.io.Serializable;

import com.gy.hsxt.common.bean.Page;

/**
 * 搜索调账申请的条件参数
 * 
 * @author lixuan
 * 
 */
public class TcCheckBalanceParam implements Serializable {
	private static final long serialVersionUID = 2509955840691712196L;
	/** 资源号 */
	private String resNo;
	/** 状态 */
	private Integer status;
	/** 起始日期 */
	private String startDate;
	/** 结束日期 */
	private String endDate;
	/** 分页信息 */
	private Page page;
	/** 状态字符，多个状态使用英文逗号分隔 */
	private String statusStr;

	/**
	 * @return the 状态字符，多个状态使用英文逗号分隔
	 */
	public String getStatusStr() {
		return statusStr;
	}

	/**
	 * @param 状态字符
	 *            ，多个状态使用英文逗号分隔 the statusStr to set
	 */
	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	/**
	 * @return the 分页信息
	 */
	public Page getPage() {
		return page;
	}

	/**
	 * @param 分页信息
	 *            the page to set
	 */
	public void setPage(Page page) {
		this.page = page;
	}

	/**
	 * @return the 资源号
	 */
	public String getResNo() {
		return resNo;
	}

	/**
	 * @param 资源号
	 *            the resNo to set
	 */
	public void setResNo(String resNo) {
		this.resNo = resNo;
	}

	/**
	 * @return the 状态
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param 状态
	 *            the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the 起始日期
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param 起始日期
	 *            the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the 结束日期
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param 结束日期
	 *            the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
