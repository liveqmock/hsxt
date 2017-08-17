/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bean.quota.result;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 企业资源Bean实体
 * 
 * @Package: com.gy.hsxt.bs.bean.quota.result
 * @ClassName: CompanyRes
 * @Description: TODO
 * @author: likui
 * @date: 2016年2月18日 上午9:32:58
 * @company: gyist
 * @version V3.0.0
 */
public class CompanyRes implements Serializable {

	private static final long serialVersionUID = 4208211344405732362L;

	/** 管理公司互生号 **/
	private String mEntResNo;

	/** 托管总数 **/
	private String totalNumT;

	/** 成员总数 **/
	private String totalNumB;

	/** 托管已使用数量 **/
	private String usedNumT;

	/** 成员已使用数量 **/
	private String usedNumB;

	/** 托管尚可用数量 **/
	private String mayUseNumT;

	/** 成员尚可用数量 **/
	private String mayUseNumB;

	public CompanyRes()
	{
		super();
	}

	public CompanyRes(String mEntResNo, String totalNumT, String totalNumB, String usedNumT, String usedNumB,
			String mayUseNumT, String mayUseNumB)
	{
		super();
		this.mEntResNo = mEntResNo;
		this.totalNumT = totalNumT;
		this.totalNumB = totalNumB;
		this.usedNumT = usedNumT;
		this.usedNumB = usedNumB;
		this.mayUseNumT = mayUseNumT;
		this.mayUseNumB = mayUseNumB;
	}

	public String getmEntResNo()
	{
		return mEntResNo;
	}

	public void setmEntResNo(String mEntResNo)
	{
		this.mEntResNo = mEntResNo;
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

	public String getUsedNumT()
	{
		return usedNumT;
	}

	public void setUsedNumT(String usedNumT)
	{
		this.usedNumT = usedNumT;
	}

	public String getUsedNumB()
	{
		return usedNumB;
	}

	public void setUsedNumB(String usedNumB)
	{
		this.usedNumB = usedNumB;
	}

	public String getMayUseNumT()
	{
		return mayUseNumT;
	}

	public void setMayUseNumT(String mayUseNumT)
	{
		this.mayUseNumT = mayUseNumT;
	}

	public String getMayUseNumB()
	{
		return mayUseNumB;
	}

	public void setMayUseNumB(String mayUseNumB)
	{
		this.mayUseNumB = mayUseNumB;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
