package com.gy.hsxt.bs.bean.quota;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.bs.bean.base.ApprBase;

/**
 * 
 * @className:PlatQuotaAppBean
 * @author:likui
 * @date:2015年8月27日
 * @desc:平台申请配额Bean
 * @company:gyist
 */
public class PlatQuotaApp extends ApprBase implements Serializable {

	private static final long serialVersionUID = -1508094649451208782L;

	/**
	 * 业务申请编号
	 */
	private String applyId;

	/**
	 * 管理公司互生号
	 */
	private String entResNo;

	/**
	 * 平台代码
	 */
	private String platNo;

	/**
	 * 管理公司名称
	 */
	private String entCustName;

	/**
	 * 申请类型1：首次配置 2：增加配额
	 */
	private Integer applyType;

	/**
	 * 申请数量
	 */
	private Integer applyNum;

	/**
	 * 批复数量
	 */
	private Integer apprNum;

	/**
	 * 批复资源号
	 */
	private String resNoList;

	/**
	 * 状态 0：待审核 1：审核通过 2：审核驳回 
	 */
	private Integer status;

	public PlatQuotaApp()
	{
		super();
	}

	public PlatQuotaApp(String applyId, String entResNo, String platNo,
			String entCustName, Integer applyType, Integer applyNum,
			Integer apprNum, String resNoList, Integer status)
	{
		super();
		this.applyId = applyId;
		this.entResNo = entResNo;
		this.platNo = platNo;
		this.entCustName = entCustName;
		this.applyType = applyType;
		this.applyNum = applyNum;
		this.apprNum = apprNum;
		this.resNoList = resNoList;
		this.status = status;
	}

	public String getApplyId()
	{
		return applyId;
	}

	public void setApplyId(String applyId)
	{
		this.applyId = applyId;
	}

	public String getEntResNo()
	{
		return entResNo;
	}

	public void setEntResNo(String entResNo)
	{
		this.entResNo = entResNo;
	}

	public String getPlatNo()
	{
		return platNo;
	}

	public void setPlatNo(String platNo)
	{
		this.platNo = platNo;
	}

	public String getEntCustName()
	{
		return entCustName;
	}

	public void setEntCustName(String entCustName)
	{
		this.entCustName = entCustName;
	}

	public Integer getApplyType()
	{
		return applyType;
	}

	public void setApplyType(Integer applyType)
	{
		this.applyType = applyType;
	}

	public Integer getApplyNum()
	{
		return applyNum;
	}

	public void setApplyNum(Integer applyNum)
	{
		this.applyNum = applyNum;
	}

	public Integer getApprNum()
	{
		return apprNum;
	}

	public void setApprNum(Integer apprNum)
	{
		this.apprNum = apprNum;
	}

	public String getResNoList()
	{
		return resNoList;
	}

	public void setResNoList(String resNoList)
	{
		this.resNoList = resNoList;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
