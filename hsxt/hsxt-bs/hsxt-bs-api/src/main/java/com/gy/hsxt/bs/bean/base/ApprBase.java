package com.gy.hsxt.bs.bean.base;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @className:AppBaseBean
 * @author:likui
 * @date:2015年8月27日
 * @desc:审批基础Bean
 * @company:gyist
 */
public class ApprBase implements Serializable {

	private static final long serialVersionUID = 5465989626285124314L;

	/**
	 * 申请操作员
	 */
	private String reqOperator;
	/**
	 * 申请时间
	 */
	private String reqTime;
	/**
	 * 申请说明
	 */
	private String reqRemark;
	/**
	 * 审批操作员
	 */
	private String apprOperator;
	/**
	 * 审批时间
	 */
	private String apprTime;
	/**
	 * 审批意见
	 */
	private String apprRemark;
	/**
	 * 经办人客户号
	 */
	private String exeCustId;

	public String getReqOperator()
	{
		return reqOperator;
	}

	public void setReqOperator(String reqOperator)
	{
		this.reqOperator = reqOperator;
	}

	public String getReqTime()
	{
		return reqTime;
	}

	public void setReqTime(String reqTime)
	{
		this.reqTime = reqTime;
	}

	public String getReqRemark()
	{
		return reqRemark;
	}

	public void setReqRemark(String reqRemark)
	{
		this.reqRemark = reqRemark;
	}

	public String getApprOperator()
	{
		return apprOperator;
	}

	public void setApprOperator(String apprOperator)
	{
		this.apprOperator = apprOperator;
	}

	public String getApprTime()
	{
		return apprTime;
	}

	public void setApprTime(String apprTime)
	{
		this.apprTime = apprTime;
	}

	public String getApprRemark()
	{
		return apprRemark;
	}

	public void setApprRemark(String apprRemark)
	{
		this.apprRemark = apprRemark;
	}

	public String getExeCustId()
	{
		return exeCustId;
	}

	public void setExeCustId(String exeCustId)
	{
		this.exeCustId = exeCustId;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
