package com.gy.hsxt.es.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * @description 查询积分/互生币明细传递的参数
 * @author chenhz
 * @createDate 2015-8-18 上午10:19:53
 * @Company 深圳市归一科技研发有限公司
 * @version v0.0.1
 */
public class QueryDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6118656780604418161L;

	/** 交易时间 */
	private Date transDate;

	/** 流水号 */
	private String transNo;

	/** 批次号 **/
	private String batchNo;

	/** 客户编号 **/
	private String custId;
	/** 互生号 **/
	private String resNo;

	/** 每页笔数 */
	private Integer count;
	/** 页号 */
	private Integer number;
	/** 开始时间 */
	private String beginDate;
	/** 结束时间 */
	private String endDate;

	/**
	 * 获取流水号
	 * 
	 * @return transNo 流水号
	 */
	public String getTransNo() {
		return transNo;
	}

	/**
	 * 设置流水号
	 * 
	 * @param transNo
	 *            流水号
	 */
	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}

	/**
	 * 获取每页笔数
	 * 
	 * @return count 每页笔数
	 */
	/**
	 * 获取每页笔数
	 * 
	 * @return count 每页笔数
	 */
	public Integer getCount() {
		return count;
	}

	/**
	 * 设置每页笔数
	 * 
	 * @param count
	 *            每页笔数
	 */
	public void setCount(Integer count) {
		this.count = count;
	}

	/**
	 * 获取页号
	 * 
	 * @return number 页号
	 */
	public Integer getNumber() {
		return number;
	}

	/**
	 * 设置页号
	 * 
	 * @param number
	 *            页号
	 */
	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getResNo() {
		return resNo;
	}

	public void setResNo(String resNo) {
		this.resNo = resNo;
	}

	public Date getTransDate() {
		return transDate;
	}

	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
