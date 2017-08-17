/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bean.quota.result;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 地区平台申请管理公司返回Bean
 * 
 * @Package: com.gy.hsxt.bs.bean.quota.result
 * @ClassName: PlatAppManage
 * @Description: TODO
 * @author: likui
 * @date: 2015年11月19日 下午12:17:55
 * @company: gyist
 * @version V3.0.0
 */
public class PlatAppManage implements Serializable {

	private static final long serialVersionUID = 156118076047625442L;

	/** 管理公司互生号 **/
	private String entResNo;

	/** 管理公司名称 **/
	private String entCustName;

	/** 总数 **/
	private String totalNum;

	/** 已分配数量 **/
	private String allotedNum;

	/** 可以申请数量 **/
	private String mayAppNum;

	/** 待审批数量 **/
	private String wApprNum;

	public PlatAppManage()
	{
		super();
	}

	public PlatAppManage(String entResNo, String entCustName, String totalNum,
			String allotedNum, String mayAppNum, String wApprNum)
	{
		super();
		this.entResNo = entResNo;
		this.entCustName = entCustName;
		this.totalNum = totalNum;
		this.allotedNum = allotedNum;
		this.mayAppNum = mayAppNum;
		this.wApprNum = wApprNum;
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

	public String getTotalNum()
	{
		return totalNum;
	}

	public void setTotalNum(String totalNum)
	{
		this.totalNum = totalNum;
	}

	public String getAllotedNum()
	{
		return allotedNum;
	}

	public void setAllotedNum(String allotedNum)
	{
		this.allotedNum = allotedNum;
	}

	public String getMayAppNum()
	{
		return mayAppNum;
	}

	public void setMayAppNum(String mayAppNum)
	{
		this.mayAppNum = mayAppNum;
	}

	public String getwApprNum()
	{
		return wApprNum;
	}

	public void setwApprNum(String wApprNum)
	{
		this.wApprNum = wApprNum;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
