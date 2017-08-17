package com.gy.hsxt.access.web.common.bean;

import java.io.Serializable;

/**
 * 
 * web段返回实体对象
 * 
 * @category Simple to Introduction
 * @projectName hsec-common-pojo
 * @package com.gy.hsec.common.pojo.OperationPrompt.java
 * @className OperationPrompt
 * @description web段返回实体对象
 * @author caoyf
 * @createDate 2014-12-3 下午3:46:29
 * @updateUser caoyf
 * @updateDate 2014-12-3 下午3:46:29
 * @updateRemark 说明本次修改内容
 * @version v0.0.1
 */
public class OperationPrompt implements Serializable {

	private static final long serialVersionUID = 8814167379084661626L;

	private Object data;
	private Integer retCode;
	private Object rows;
	private Object msg;
	private Long currentPageIndex = 0L;
	private Long totalPage = 0L;
	
	public OperationPrompt() {

	}

	public OperationPrompt(Integer retCode) {
		this.retCode = retCode;
	}

	public OperationPrompt(Integer retCode, Object data) {

		this.data = data;
		this.retCode = retCode;
	}

	public Integer getRetCode() {
		return retCode;
	}

	public void setRetCode(Integer retCode) {
		this.retCode = retCode;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * 获取currentPageIndex
	 * @return currentPageIndex currentPageIndex
	 */
	public Long getCurrentPageIndex() {
		return currentPageIndex;
	}

	/**
	 * 设置currentPageIndex
	 * @param currentPageIndex currentPageIndex
	 */
	public void setCurrentPageIndex(Long currentPageIndex) {
		this.currentPageIndex = currentPageIndex;
	}

	/**
	 * 获取totalPage
	 * @return totalPage totalPage
	 */
	public Long getTotalPage() {
		return totalPage;
	}

	/**
	 * 设置totalPage
	 * @param totalPage totalPage
	 */
	public void setTotalPage(Long totalPage) {
		this.totalPage = totalPage;
	}

	/**
	 * 获取rows
	 * 
	 * @return rows rows
	 */
	public Object getRows() {
		return rows;
	}

	/**
	 * 设置rows
	 * 
	 * @param rows
	 *            rows
	 */
	public void setRows(Object rows) {
		this.rows = rows;
	}

	public Object getMsg() {
		return msg;
	}

	public void setMsg(Object msg) {
		this.msg = msg;
	}

}
