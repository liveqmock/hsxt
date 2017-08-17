/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bean.quota.result;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 服务公司下的企业资源
 * 
 * @Package: com.gy.hsxt.bs.bean.quota.result
 * @ClassName: CompanyResS
 * @Description: TODO
 * @author: likui
 * @date: 2016年2月18日 上午9:42:15
 * @company: gyist
 * @version V3.0.0
 */
public class CompanyResS implements Serializable {

	private static final long serialVersionUID = -285917970244625016L;

	/** 服务公司互生号 **/
	private String entResNo;

	/** 服务公司名称 **/
	private String entCustName;

	/** 托管企业数量 **/
	private String totalNumT;

	/** 成员企业数量 **/
	private String totalNumB;

	public CompanyResS()
	{
		super();
	}

	public CompanyResS(String entResNo, String entCustName, String totalNumT, String totalNumB)
	{
		super();
		this.entResNo = entResNo;
		this.entCustName = entCustName;
		this.totalNumT = totalNumT;
		this.totalNumB = totalNumB;
	}

	public String getEntResNo()
	{
		return entResNo;
	}

	public void setEntResNo(String entResNo)
	{
		this.entResNo = entResNo;
	}

	public String getEntCustName()
	{
		return entCustName;
	}

	public void setEntCustName(String entCustName)
	{
		this.entCustName = entCustName;
	}

	public String getTotalNumT()
	{
		return totalNumT;
	}

	public void setTotalNumT(String totalNumT)
	{
		this.totalNumT = totalNumT;
	}

	public String getTotalNumB()
	{
		return totalNumB;
	}

	public void setTotalNumB(String totalNumB)
	{
		this.totalNumB = totalNumB;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
