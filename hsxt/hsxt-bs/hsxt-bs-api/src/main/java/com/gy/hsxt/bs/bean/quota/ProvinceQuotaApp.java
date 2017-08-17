package com.gy.hsxt.bs.bean.quota;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.bs.bean.base.ApprBase;

/**
 * 
 * @className:ProvinceQuotaAppBean
 * @author:likui
 * @date:2015年8月27日
 * @desc:省级配额申请Bean
 * @company:gyist
 */
public class ProvinceQuotaApp extends ApprBase implements Serializable {

	private static final long serialVersionUID = -1085598461889031318L;

	/**
	 * 业务申请编号
	 */
	private String applyId;
	/**
	 * 管理公司互生号
	 */
	private String entResNo;
	/**
	 * 国家代码
	 */
	private String countryNo;
	/**
	 * 省代码
	 */
	private String provinceNo;
	/**
	 * 申请类型 1：首次配置 2：增加配额 3：减少配额
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

	public ProvinceQuotaApp()
	{
		super();
	}

	public ProvinceQuotaApp(String applyId, String entResNo, String countryNo,
			String provinceNo, Integer applyType, Integer applyNum,
			Integer apprNum, String resNoList, Integer status)
	{
		super();
		this.applyId = applyId;
		this.entResNo = entResNo;
		this.countryNo = countryNo;
		this.provinceNo = provinceNo;
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

	public String getCountryNo()
	{
		return countryNo;
	}

	public void setCountryNo(String countryNo)
	{
		this.countryNo = countryNo;
	}

	public String getProvinceNo()
	{
		return provinceNo;
	}

	public void setProvinceNo(String provinceNo)
	{
		this.provinceNo = provinceNo;
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
