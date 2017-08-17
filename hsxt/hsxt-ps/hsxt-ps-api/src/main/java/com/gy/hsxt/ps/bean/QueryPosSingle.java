package com.gy.hsxt.ps.bean;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @description POS单笔查询
 * @author chenhz
 * @createDate 2015-8-18 上午10:19:53
 * @Company 深圳市归一科技研发有限公司
 * @version v0.0.1
 */
public class QueryPosSingle implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 6118656780604418161L;

	/** 单据码生成时间 */
	@NotBlank(message = "单据码生成时间为空")
	private String sourcePosDate;
	/** 互生号 */
	@NotBlank(message = "互生号不能为空")
	private String entResNo;
	/** 企业客户号 */
	private String entCustId;
	/** 设备编号 */
	@NotBlank(message = "设备编号不能为空")
	private String equipmentNo;
	/*** 终端流水号(POS) */
	@NotBlank(message = "终端流水号不能为空")
	private String termRunCode;
	/** 批次号 */
	@NotBlank(message = "批次号不能为空")
	private String batchNo;

	/**
	 * 单据码生成时间
	 * @return sourcePosDate 单据码生成时间
	 */
	public String getSourcePosDate() {
		return sourcePosDate;
	}
	/**
	 * 单据码生成时间
	 * @param sourcePosDate 单据码生成时间
	 */
	public void setSourcePosDate(String sourcePosDate) {
		this.sourcePosDate = sourcePosDate;
	}

	/**
	 * 获取互生号
	 * @return entResNo 互生号
	 */
	public String getEntResNo()
	{
		return entResNo;
	}
	/**
	 * 设置互生号
	 * @param entResNo 互生号
	 */
	public void setEntResNo(String entResNo)
	{
		this.entResNo = entResNo;
	}
	/**
	 * 获取企业客户号
	 * @return entCustId 企业客户号
	 */
	public String getEntCustId()
	{
		return entCustId;
	}
	/**
	 * 设置企业客户号
	 * @param entCustId 企业客户号
	 */
	public void setEntCustId(String entCustId)
	{
		this.entCustId = entCustId;
	}
	/**
	 * 获取设备编号
	 * @return equipmentNo 设备编号
	 */
	public String getEquipmentNo()
	{
		return equipmentNo;
	}
	/**
	 * 设置设备编号
	 * @param equipmentNo 设备编号
	 */
	public void setEquipmentNo(String equipmentNo)
	{
		this.equipmentNo = equipmentNo;
	}
	/**
	 * 获取终端流水号
	 *
	 * @return termRunCode 终端流水号
	 */
	public String getTermRunCode()
	{
		return termRunCode;
	}

	/**
	 * 设置终端流水号
	 *
	 * @param termRunCode
	 *            终端流水号
	 */
	public void setTermRunCode(String termRunCode)
	{
		this.termRunCode = termRunCode;
	}
	/**
	 * 获取批次号
	 * @return batchNo 批次号
	 */
	public String getBatchNo()
	{
		return batchNo;
	}
	/**
	 * 设置批次号
	 * @param batchNo 批次号
	 */
	public void setBatchNo(String batchNo)
	{
		this.batchNo = batchNo;
	}
}
