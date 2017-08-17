package com.gy.hsxt.ps.bean;

import org.hibernate.validator.constraints.NotBlank;

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
	//@NotBlank(message = "批次号不能为空！")
	private String batchNo;

	/** 客户编号 **/
	private String custId;
	/** 互生号 **/
	//@NotBlank(message = "互生号不能为空！")
	private String resNo;

	/**订单号*/
	private String orderNo;

	/** 每页笔数 */
	private Integer count =15;
	/** 页号 */
	private Integer number = 1;
	
	/** 开始批次 **/
	private String beginBatchNo;
	
	/** 结束批次 **/
	private String endBatchNo;

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
		return transDate==null?null:(Date) transDate.clone();
	}

	public void setTransDate(Date transDate) {
		this.transDate = transDate==null?null:(Date)transDate.clone();
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

    /**
     * @return the 开始批次
     */
    public String getBeginBatchNo() {
        return beginBatchNo;
    }

    /**
     * @param 开始批次 the beginBatchNo to set
     */
    public void setBeginBatchNo(String beginBatchNo) {
        this.beginBatchNo = beginBatchNo;
    }

    /**
     * @return the 结束批次
     */
    public String getEndBatchNo() {
        return endBatchNo;
    }

    /**
     * @param 结束批次 the endBatchNo to set
     */
    public void setEndBatchNo(String endBatchNo) {
        this.endBatchNo = endBatchNo;
    }

    
	
	
}
