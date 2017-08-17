/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.ps.bean;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/** 
 * @Package: com.gy.hsxt.ps.bean  
 * @ClassName: QueryPointRecord 
 * @Description: 查询积分记录参数(刷卡器)
 *
 * @author: chenhz 
 * @date: 2016-1-13 下午3:59:10 
 * @version V3.0  
 */
public class QueryPointRecord implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3815013265528975859L;
	/** 原交易流水号 */
	private String sourceTransNo;
	/** 消费者互生号 */
	private String hsResNo;
	/** 消费者客户号 */
	private String perCustId;
	/** 企业客户号 */
	@NotBlank(message = "企业客户号不能为空")
	private String entCustId;
	/** 开始时间 */
	@NotBlank(message = "开始时间不能为空")
	private String startDate;
	/** 结束时间 */
	@NotBlank(message = "结束时间不能为空")
	private String endDate;
	/** 业务类型 */
	@NotBlank(message = "业务类型不能为空")
	private String businessType;
	/** 每页笔数 */
	private Integer count;
	/** 页号 */
	private Integer pageNumber;
	
	/**
	 * 获取业务类型
	 * @return businessType 业务类型
	 */
	public String getBusinessType()
	{
		return businessType;
	}
	/**
	 * 设置业务类型
	 * @param businessType 业务类型
	 */
	public void setBusinessType(String businessType)
	{
		this.businessType = businessType;
	}
	/**
	 * 获取原交易流水号
	 * @return sourceTransNo 原交易流水号
	 */
	public String getSourceTransNo()
	{
		return sourceTransNo;
	}
	/**
	 * 设置原交易流水号
	 * @param sourceTransNo 原交易流水号
	 */
	public void setSourceTransNo(String sourceTransNo)
	{
		this.sourceTransNo = sourceTransNo;
	}
	/**
	 * 获取互生号
	 * @return hsResNo 互生号
	 */
	public String getHsResNo()
	{
		return hsResNo;
	}
	/**
	 * 设置互生号
	 * @param hsResNo 互生号
	 */
	public void setHsResNo(String hsResNo)
	{
		this.hsResNo = hsResNo;
	}

	/**
	 * 获取开始时间
	 * @return startDate 开始时间
	 */
	public String getStartDate()
	{
		return startDate;
	}
	/**
	 * 设置开始时间
	 * @param startDate 开始时间
	 */
	public void setStartDate(String startDate)
	{
		this.startDate = startDate;
	}
	/**
	 * 获取结束时间
	 * @return endDate 结束时间
	 */
	public String getEndDate()
	{
		return endDate;
	}
	/**
	 * 设置结束时间
	 * @param endDate 结束时间
	 */
	public void setEndDate(String endDate)
	{
		this.endDate = endDate;
	}
	/**
	 * 获取每页笔数
	 * @return count 每页笔数
	 */
	public Integer getCount()
	{
		return count;
	}
	/**
	 * 设置每页笔数
	 * @param count 每页笔数
	 */
	public void setCount(Integer count)
	{
		this.count = count;
	}
	/**
	 * 获取页号
	 * @return pageNumber 页号
	 */
	public Integer getPageNumber()
	{
		return pageNumber;
	}
	/**
	 * 设置页号
	 * @param pageNumber 页号
	 */
	public void setPageNumber(Integer pageNumber)
	{
		this.pageNumber = pageNumber;
	}
	/**
	 * 获取消费者客户号
	 * @return perCustId 消费者客户号
	 */
	public String getPerCustId()
	{
		return perCustId;
	}
	/**
	 * 设置消费者客户号
	 * @param perCustId 消费者客户号
	 */
	public void setPerCustId(String perCustId)
	{
		this.perCustId = perCustId;
	}
	/**
	 * 获取企业客户号
	 * @return entCustId 企业客户号
	 */
	public String getEntCustId()
	{
		return entCustId;
	}
	/**
	 * 设置企业客户号
	 * @param entCustId 企业客户号
	 */
	public void setEntCustId(String entCustId)
	{
		this.entCustId = entCustId;
	}

}
