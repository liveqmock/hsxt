/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.tool.resultbean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 分页秘钥订单返回Bean
 * 
 * @Package: com.gy.hsxt.bs.bean.tool.pageresult
 * @ClassName: PosOrderInfoPage
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:25:46
 * @company: gyist
 * @version V3.0.0
 */
public class SecretKeyOrderInfoPage implements Serializable {

	private static final long serialVersionUID = 3261262394948427194L;

	/** 企业互生号 **/
	private String entResNo;

	/** 企业客户号 **/
	private String entCustId;

	/** 客户名称 **/
	private String custName;

	/** 订单号 **/
	private String orderNo;

	/** 配置单号 **/
	private String confNo;

	/** 工具类别 **/
	private String categoryCode;

	/** 工具名称 **/
	private String productName;

	/** 已配置数量 **/
	private Integer confedNum;

	/** 未配置数量 **/
	private Integer confingNum;

	/** 配置状态 **/
	private Integer status;

	/** 配置时间 **/
	private String confDate;

	public SecretKeyOrderInfoPage()
	{
		super();
	}

	public SecretKeyOrderInfoPage(String entResNo, String entCustId,
			String custName, String orderNo, String confNo,
			String categoryCode, String productName, Integer confedNum,
			Integer confingNum, Integer status, String confDate)
	{
		super();
		this.entResNo = entResNo;
		this.entCustId = entCustId;
		this.custName = custName;
		this.orderNo = orderNo;
		this.confNo = confNo;
		this.categoryCode = categoryCode;
		this.productName = productName;
		this.confedNum = confedNum;
		this.confingNum = confingNum;
		this.status = status;
		this.confDate = confDate;
	}

	public String getEntResNo()
	{
		return entResNo;
	}

	public void setEntResNo(String entResNo)
	{
		this.entResNo = entResNo;
	}

	public String getEntCustId()
	{
		return entCustId;
	}

	public void setEntCustId(String entCustId)
	{
		this.entCustId = entCustId;
	}

	public String getCustName()
	{
		return custName;
	}

	public void setCustName(String custName)
	{
		this.custName = custName;
	}

	public String getOrderNo()
	{
		return orderNo;
	}

	public void setOrderNo(String orderNo)
	{
		this.orderNo = orderNo;
	}

	public String getConfNo()
	{
		return confNo;
	}

	public void setConfNo(String confNo)
	{
		this.confNo = confNo;
	}

	public String getCategoryCode()
	{
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode)
	{
		this.categoryCode = categoryCode;
	}

	public String getProductName()
	{
		return productName;
	}

	public void setProductName(String productName)
	{
		this.productName = productName;
	}

	public Integer getConfedNum()
	{
		return confedNum;
	}

	public void setConfedNum(Integer confedNum)
	{
		this.confedNum = confedNum;
	}

	public Integer getConfingNum()
	{
		return confingNum;
	}

	public void setConfingNum(Integer confingNum)
	{
		this.confingNum = confingNum;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public String getConfDate()
	{
		return confDate;
	}

	public void setConfDate(String confDate)
	{
		this.confDate = confDate;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
