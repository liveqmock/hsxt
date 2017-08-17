/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bean.tool;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 入库抽检Bean
 * 
 * @Package: com.gy.hsxt.bs.bean.tool
 * @ClassName: InstorageInspection
 * @Description: TODO
 * @author: likui
 * @date: 2015年10月26日 下午7:44:04
 * @company: gyist
 * @version V3.0.0
 */
public class InstorageInspection implements Serializable {

	private static final long serialVersionUID = -2716872406738804994L;

	/** 抽检id **/
	private String inspectionId;

	/** 入库批次号 **/
	private String enterNo;

	/** 抽检数量 **/
	private Integer quantity;

	/** 合格数量 **/
	private Integer passQuantity;

	/** 合格率 **/
	private String passRate;

	/** 抽检日期 **/
	private String inspectionDate;

	/** 操作员 **/
	private String operNo;

	/** 备注 **/
	private String remark;

	public InstorageInspection()
	{
		super();
	}

	public InstorageInspection(String inspectionId, String enterNo,
			Integer quantity, Integer passQuantity, String passRate,
			String inspectionDate, String operNo, String remark)
	{
		super();
		this.inspectionId = inspectionId;
		this.enterNo = enterNo;
		this.quantity = quantity;
		this.passQuantity = passQuantity;
		this.passRate = passRate;
		this.inspectionDate = inspectionDate;
		this.operNo = operNo;
		this.remark = remark;
	}

	public String getInspectionId()
	{
		return inspectionId;
	}

	public void setInspectionId(String inspectionId)
	{
		this.inspectionId = inspectionId;
	}

	public String getEnterNo()
	{
		return enterNo;
	}

	public void setEnterNo(String enterNo)
	{
		this.enterNo = enterNo;
	}

	public Integer getQuantity()
	{
		return quantity;
	}

	public void setQuantity(Integer quantity)
	{
		this.quantity = quantity;
	}

	public Integer getPassQuantity()
	{
		return passQuantity;
	}

	public void setPassQuantity(Integer passQuantity)
	{
		this.passQuantity = passQuantity;
	}

	public String getPassRate()
	{
		return passRate;
	}

	public void setPassRate(String passRate)
	{
		this.passRate = passRate;
	}

	public String getInspectionDate()
	{
		return inspectionDate;
	}

	public void setInspectionDate(String inspectionDate)
	{
		this.inspectionDate = inspectionDate;
	}

	public String getOperNo()
	{
		return operNo;
	}

	public void setOperNo(String operNo)
	{
		this.operNo = operNo;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}

}
