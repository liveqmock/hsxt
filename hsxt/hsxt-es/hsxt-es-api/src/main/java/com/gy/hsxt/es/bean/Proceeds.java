/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.es.bean;

import java.io.Serializable;

/**
 * @Package: com.gy.hsxt.es.api
 * @ClassName: Proceeds
 * @Description: 企业收入详单
 * 
 * @author: chenhz
 * @date: 2016-1-16 上午11:31:19
 * @version V3.0
 */
public class Proceeds implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3943376626521914291L;
	/** 企业互生号 **/
	private String entResNo;
	/** 企业客户号 **/
	private String entCustId;
	/** 交易流水号 **/
	private String transNo;
	/** 会计时间 **/
	private String transDate;

	/**
	 * 获取企业互生号
	 * 
	 * @return entResNo 企业互生号
	 */
	public String getEntResNo()
	{
		return entResNo;
	}

	/**
	 * 设置企业互生号
	 * 
	 * @param entResNo
	 *            企业互生号
	 */
	public void setEntResNo(String entResNo)
	{
		this.entResNo = entResNo;
	}

	/**
	 * 获取企业客户号
	 * 
	 * @return entCustId 企业客户号
	 */
	public String getEntCustId()
	{
		return entCustId;
	}

	/**
	 * 设置企业客户号
	 * 
	 * @param entCustId
	 *            企业客户号
	 */
	public void setEntCustId(String entCustId)
	{
		this.entCustId = entCustId;
	}

	/**
	 * 获取交易流水号
	 * 
	 * @return transNo 交易流水号
	 */
	public String getTransNo()
	{
		return transNo;
	}

	/**
	 * 设置交易流水号
	 * 
	 * @param transNo
	 *            交易流水号
	 */
	public void setTransNo(String transNo)
	{
		this.transNo = transNo;
	}

	/**
	 * 获取会计时间
	 * 
	 * @return transDate 会计时间
	 */
	public String getTransDate()
	{
		return transDate;
	}

	/**
	 * 设置会计时间
	 * 
	 * @param transDate
	 *            会计时间
	 */
	public void setTransDate(String transDate)
	{
		this.transDate = transDate;
	}

}
