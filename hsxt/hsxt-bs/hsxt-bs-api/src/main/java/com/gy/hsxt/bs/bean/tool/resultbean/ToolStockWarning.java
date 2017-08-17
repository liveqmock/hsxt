/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bean.tool.resultbean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 工具库存预警查询返回Bean
 * 
 * @Package: com.gy.hsxt.bs.bean.tool.resultbean
 * @ClassName: ToolStockWarning
 * @Description: TODO
 * @author: likui
 * @date: 2015年10月27日 上午11:15:22
 * @company: gyist
 * @version V3.0.0
 */
public class ToolStockWarning implements Serializable {

	private static final long serialVersionUID = 5539305630965692957L;

	/** 工具类别 **/
	private String categoryCode;

	/** 工具名称 **/
	private String productName;

	/** 仓库名称 **/
	private String whName;

	/** 预警状态 **/
	private Boolean warningStatus;

	/** 报损率 **/
	private String reportedRate;

	/** 使用率 **/
	private String useRate;

	public ToolStockWarning()
	{
		super();
	}

	public ToolStockWarning(String categoryCode, String productName, String whName, Boolean warningStatus,
			String reportedRate, String useRate)
	{
		super();
		this.categoryCode = categoryCode;
		this.productName = productName;
		this.whName = whName;
		this.warningStatus = warningStatus;
		this.reportedRate = reportedRate;
		this.useRate = useRate;
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

	public String getWhName()
	{
		return whName;
	}

	public void setWhName(String whName)
	{
		this.whName = whName;
	}

	public Boolean getWarningStatus()
	{
		return warningStatus;
	}

	public void setWarningStatus(Boolean warningStatus)
	{
		this.warningStatus = warningStatus;
	}

	public String getReportedRate()
	{
		return reportedRate;
	}

	public void setReportedRate(String reportedRate)
	{
		this.reportedRate = reportedRate;
	}

	public String getUseRate()
	{
		return useRate;
	}

	public void setUseRate(String useRate)
	{
		this.useRate = useRate;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}

}
