/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.bean.tool.resultbean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 企业信息
 * 
 * @Package: com.gy.hsxt.bs.bean.tool.resultbean
 * @ClassName: EntInfo
 * @Description: TODO
 * @author: likui
 * @date: 2016年4月13日 上午9:54:04
 * @company: gyist
 * @version V3.0.0
 */
public class EntInfo implements Serializable {

	private static final long serialVersionUID = -1565316342314216728L;

	/** 企业互生号 **/
	private String entResNo;

	/** 客户名称 **/
	private String entCustName;

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

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
