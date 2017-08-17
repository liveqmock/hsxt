package com.gy.hsxt.ps.bean;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @description 积分/互生币明细
 * @author chenhz
 * @createDate 2015-8-18 上午10:18:53
 * @Company 深圳市归一科技研发有限公司
 * @version v0.0.1
 */
public class AllocDetail implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 2648735967527160977L;
	/** 交易流水号 **/
	private String transNo;
	/** 消费者互生号 **/
	private String perResNo;
	/** 原始交易时间 */
	private String sourceTransDate;
	/** 交易类型 **/
	private String transType;
	/** 获得积分值 **/
	private String pointVal;
	/**
	 * 获取交易流水号
	 * @return transNo 交易流水号
	 */
	public String getTransNo()
	{
		return transNo;
	}
	/**
	 * 设置交易流水号
	 * @param transNo 交易流水号
	 */
	public void setTransNo(String transNo)
	{
		this.transNo = transNo;
	}
	/**
	 * 获取消费者互生号
	 * @return perResNo 消费者互生号
	 */
	public String getPerResNo()
	{
		return perResNo;
	}
	/**
	 * 设置消费者互生号
	 * @param perResNo 消费者互生号
	 */
	public void setPerResNo(String perResNo)
	{
		this.perResNo = perResNo;
	}
	/**
	 * 获取原始交易时间
	 * @return sourceTransDate 原始交易时间
	 */
	public String getSourceTransDate()
	{
		return sourceTransDate;
	}
	/**
	 * 设置原始交易时间
	 * @param sourceTransDate 原始交易时间
	 */
	public void setSourceTransDate(String sourceTransDate)
	{
		this.sourceTransDate = sourceTransDate;
	}
	/**
	 * 获取交易类型
	 * @return transType 交易类型
	 */
	public String getTransType()
	{
		return transType;
	}
	/**
	 * 设置交易类型
	 * @param transType 交易类型
	 */
	public void setTransType(String transType)
	{
		this.transType = transType;
	}
	/**
	 * 获取获得积分值
	 * @return pointVal 获得积分值
	 */
	public String getPointVal()
	{
		if (StringUtils.isNotEmpty(pointVal)){
			return (new BigDecimal(pointVal)).toEngineeringString();
		}

		return pointVal;
	}
	/**
	 * 设置获得积分值
	 * @param pointVal 获得积分值
	 */
	public void setPointVal(String pointVal)
	{
		this.pointVal = pointVal;
	}

}
