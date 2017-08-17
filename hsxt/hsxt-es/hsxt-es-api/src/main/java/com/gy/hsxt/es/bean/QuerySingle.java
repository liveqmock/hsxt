package com.gy.hsxt.es.bean;

import java.io.Serializable;

/**
 * @description POS单笔查询
 * @author chenhz
 * @createDate 2015-8-18 上午10:19:53
 * @Company 深圳市归一科技研发有限公司
 * @version v0.0.1
 */
public class QuerySingle implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6118656780604418161L;

	/** 原交易流水号 */
	private String sourceTransNo;
	/** 互生号 */
	private String entResNo;
	/** 企业客户号 */
	private String entCustId;
	/** 设备编号 */
	private String equipmentNo;
	
	/**
	 * 获取原交易流水号
	 * @return sourceTransNo 原交易流水号
	 */
	public String getSourceTransNo()
	{
		return sourceTransNo;
	}
	/**
	 * 设置原交易流水号
	 * @param sourceTransNo 原交易流水号
	 */
	public void setSourceTransNo(String sourceTransNo)
	{
		this.sourceTransNo = sourceTransNo;
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

}
