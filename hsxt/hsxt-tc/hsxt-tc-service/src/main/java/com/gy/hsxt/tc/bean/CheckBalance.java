/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.tc.bean;

import java.io.Serializable;

/**
 * 调账申请
 * 
 * @author lixuan
 * 
 */
public class CheckBalance extends TcCheckBalance implements Serializable {
	private static final long serialVersionUID = -256942351815550443L;
	/** 起始日期 */
	private String startDate;
	/** 结束日期 */
	private String endDate;
	/** 当前页码 */
	private Integer curPage;
	/** 每页条数 */
	private Integer pageSize;
	/** 状态字符，多个状态使用英文逗号分隔 */
	private String statusStr;

	/**
	 * @return the 状态字符，多个状态使用英文逗号分隔
	 */
	public String getStatusStr() {
		if (getStatus() != null) {
			return getStatus() + "";
		}
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
	 * @return the 当前页码
	 */
	public Integer getCurPage() {
		return curPage;
	}

	/**
	 * @param 当前页码
	 *            the curPage to set
	 */
	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}

	/**
	 * @return the 每页条数
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	/**
	 * @param 每页条数
	 *            the pageSize to set
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
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