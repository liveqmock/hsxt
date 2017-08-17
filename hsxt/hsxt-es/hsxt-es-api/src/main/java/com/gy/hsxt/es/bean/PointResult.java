package com.gy.hsxt.es.bean;

import java.io.Serializable;

/**
 * @description 积分返回结果实体类
 * @author chenhz
 * @createDate 2015-7-27 下午2:30:12
 * @Company 深圳市归一科技研发有限公司
 * @version v0.0.1
 */
public class PointResult extends Result implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -485741277837777543L;
	/** 消费者积分 */
	private String perPoint;
	/** 原互商交易流水号 */
	private String sourceTransNo;
	/** 消费积分交易流水号 */
	private String transNo;
	/** 会计时间 */
	private String accountantDate;
	
	/**
	 * 获取积分
	 * @return perPoint 积分
	 */
	public String getPerPoint()
	{
		return perPoint;
	}
	/**
	 * 设置积分
	 * @param perPoint 积分
	 */
	public void setPerPoint(String perPoint)
	{
		this.perPoint = perPoint;
	}
	/**
	 * 获取交易流水号
	 * @return transNo 交易流水号
	 */
	public String getTransNo() {
		return transNo;
	}
	/**
	 * 设置交易流水号
	 * @param transNo 交易流水号
	 */
	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}
	/**
	 * 获取原互商交易流水号
	 * @return sourceTransNo 原互商交易流水号
	 */
	public String getSourceTransNo()
	{
		return sourceTransNo;
	}
	/**
	 * 设置原互商交易流水号
	 * @param sourceTransNo 原互商交易流水号
	 */
	public void setSourceTransNo(String sourceTransNo)
	{
		this.sourceTransNo = sourceTransNo;
	}
	/**
	 * 获取会计时间
	 * @return accountantDate 会计时间
	 */
	public String getAccountantDate()
	{
		return accountantDate;
	}
	/**
	 * 设置会计时间
	 * @param accountantDate 会计时间
	 */
	public void setAccountantDate(String accountantDate)
	{
		this.accountantDate = accountantDate;
	}

}
