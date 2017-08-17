/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ps.distribution.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
		
/**
 * @Package: com.gy.hsxt.ps.distribution.bean
 * @ClassName: PointAllot
 * @Description: 积分分配实体类
 * 
 * @author: chenhz
 * @date: 2016-2-24 下午2:05:55
 * @version V3.0
 */
public class PointAllot implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -299999753312417323L;
	/** 积分分配流水号 **/
	private String allotNo;
	/** 消费者互生号 **/
	private String perHsNo;
	/** 冲正标识 **/
	private String writeBack;
	/** 交易类型 **/
	private String transType;
	/** 关联交易流水号 **/
	private String relTransNo;
	/** 原交易时间 **/
	private Timestamp sourceTransDate;
	/** 批次号 **/
	private String batchNo;
	/** 原交易流水号 **/
	private String sourceTransNo;
	/** 是否结算 **/
	private Integer isSettle;
	/** 托管企业互生号 **/
	private String trusteeEntHsNo;
	/** 服务公司互生号 **/
	private String serviceEntHsNo;
	/** 管理公司互生号 **/
	private String manageEntHsNo;
	/** 地区平台互生号 **/
	private String paasEntHsNo;
	/** 托管企业增向积分额 **/
	private BigDecimal trusteeAddPoint;
	/** 服务公司增向积分额 **/
	private BigDecimal serviceAddPoint;
	/** 管理公司增向积分额 **/
	private BigDecimal manageAddPoint;
	/** 地区平台增向积分额 **/
	private BigDecimal paasAddPoint;
	/** 托管企业减向积分额 **/
	private BigDecimal trusteeSubPoint;
	/** 服务公司减向积分额 **/
	private BigDecimal serviceSubPoint;
	/** 管理公司减向积分额 **/
	private BigDecimal manageSubPoint;
	/** 地区平台减向积分额 **/
	private BigDecimal paasSubPoint;
	/** 结余减向积分额 **/
	private BigDecimal surplusSubPoint;
	/** 结余增向积分额 **/
	private BigDecimal surplusAddPoint;
	/** 备注 **/
	private String remark;
	/** 标记此条记录的状态 **/
	private String isActive;
	/** 创建时间 **/
	private Timestamp createdDate;
	/** 由谁创建，值为用户的伪键ID **/
	private String createdBy;
	/** 修改时间 **/
	private Timestamp updatedDate;
	/** 由谁更新，值为用户的伪键ID **/
	private String updatedBy;

	/** 汇总笔数 **/
	private Integer pvAddCount;

	/** 地区平台非持卡人**/
	private BigDecimal noCardPaasAddPoint;

	/**
	 * 获取积分分配流水号
	 * @return allotNo 积分分配流水号
	 */
	public String getAllotNo()
	{
		return allotNo;
	}
	/**
	 * 设置积分分配流水号
	 * @param allotNo 积分分配流水号
	 */
	public void setAllotNo(String allotNo)
	{
		this.allotNo = allotNo;
	}
	/**
	 * 获取消费者互生号
	 * @return perHsNo 消费者互生号
	 */
	public String getPerHsNo()
	{
		return perHsNo;
	}
	/**
	 * 设置消费者互生号
	 * @param perHsNo 消费者互生号
	 */
	public void setPerHsNo(String perHsNo)
	{
		this.perHsNo = perHsNo;
	}
	/**
	 * 获取冲正标识
	 * @return writeBack 冲正标识
	 */
	public String getWriteBack()
	{
		return writeBack;
	}
	/**
	 * 设置冲正标识
	 * @param writeBack 冲正标识
	 */
	public void setWriteBack(String writeBack)
	{
		this.writeBack = writeBack;
	}
	/**
	 * 获取交易类型
	 * @return transType 交易类型
	 */
	public String getTransType()
	{
		return transType;
	}
	/**
	 * 设置交易类型
	 * @param transType 交易类型
	 */
	public void setTransType(String transType)
	{
		this.transType = transType;
	}
	/**
	 * 获取关联交易流水号
	 * @return relTransNo 关联交易流水号
	 */
	public String getRelTransNo()
	{
		return relTransNo;
	}
	/**
	 * 设置关联交易流水号
	 * @param relTransNo 关联交易流水号
	 */
	public void setRelTransNo(String relTransNo)
	{
		this.relTransNo = relTransNo;
	}
	/**
	 * 获取原交易时间
	 * @return sourceTransDate 原交易时间
	 */
	public Timestamp getSourceTransDate()
	{
		return sourceTransDate == null?null: (Timestamp)sourceTransDate.clone();
	}
	/**
	 * 设置原交易时间
	 * @param sourceTransDate 原交易时间
	 */
	public void setSourceTransDate(Timestamp sourceTransDate)
	{
		this.sourceTransDate=sourceTransDate == null? null:(Timestamp)sourceTransDate.clone();
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
	 * 获取是否结算
	 * @return isSettle 是否结算
	 */
	public Integer getIsSettle()
	{
		return isSettle;
	}
	/**
	 * 设置是否结算
	 * @param isSettle 是否结算
	 */
	public void setIsSettle(Integer isSettle)
	{
		this.isSettle = isSettle;
	}
	/**
	 * 获取托管企业互生号
	 * @return trusteeEntHsNo 托管企业互生号
	 */
	public String getTrusteeEntHsNo()
	{
		return trusteeEntHsNo;
	}
	/**
	 * 设置托管企业互生号
	 * @param trusteeEntHsNo 托管企业互生号
	 */
	public void setTrusteeEntHsNo(String trusteeEntHsNo)
	{
		this.trusteeEntHsNo = trusteeEntHsNo;
	}
	/**
	 * 获取服务公司互生号
	 * @return serviceEntHsNo 服务公司互生号
	 */
	public String getServiceEntHsNo()
	{
		return serviceEntHsNo;
	}
	/**
	 * 设置服务公司互生号
	 * @param serviceEntHsNo 服务公司互生号
	 */
	public void setServiceEntHsNo(String serviceEntHsNo)
	{
		this.serviceEntHsNo = serviceEntHsNo;
	}
	/**
	 * 获取管理公司互生号
	 * @return manageEntHsNo 管理公司互生号
	 */
	public String getManageEntHsNo()
	{
		return manageEntHsNo;
	}
	/**
	 * 设置管理公司互生号
	 * @param manageEntHsNo 管理公司互生号
	 */
	public void setManageEntHsNo(String manageEntHsNo)
	{
		this.manageEntHsNo = manageEntHsNo;
	}
	/**
	 * 获取地区平台互生号
	 * @return paasEntHsNo 地区平台互生号
	 */
	public String getPaasEntHsNo()
	{
		return paasEntHsNo;
	}
	/**
	 * 设置地区平台互生号
	 * @param paasEntHsNo 地区平台互生号
	 */
	public void setPaasEntHsNo(String paasEntHsNo)
	{
		this.paasEntHsNo = paasEntHsNo;
	}
	/**
	 * 获取托管企业增向积分额
	 * @return trusteeAddPoint 托管企业增向积分额
	 */
	public BigDecimal getTrusteeAddPoint()
	{
		return trusteeAddPoint;
	}
	/**
	 * 设置托管企业增向积分额
	 * @param trusteeAddPoint 托管企业增向积分额
	 */
	public void setTrusteeAddPoint(BigDecimal trusteeAddPoint)
	{
		this.trusteeAddPoint = trusteeAddPoint;
	}
	/**
	 * 获取服务公司增向积分额
	 * @return serviceAddPoint 服务公司增向积分额
	 */
	public BigDecimal getServiceAddPoint()
	{
		return serviceAddPoint;
	}
	/**
	 * 设置服务公司增向积分额
	 * @param serviceAddPoint 服务公司增向积分额
	 */
	public void setServiceAddPoint(BigDecimal serviceAddPoint)
	{
		this.serviceAddPoint = serviceAddPoint;
	}
	/**
	 * 获取管理公司增向积分额
	 * @return manageAddPoint 管理公司增向积分额
	 */
	public BigDecimal getManageAddPoint()
	{
		return manageAddPoint;
	}
	/**
	 * 设置管理公司增向积分额
	 * @param manageAddPoint 管理公司增向积分额
	 */
	public void setManageAddPoint(BigDecimal manageAddPoint)
	{
		this.manageAddPoint = manageAddPoint;
	}
	/**
	 * 获取地区平台增向积分额
	 * @return paasAddPoint 地区平台增向积分额
	 */
	public BigDecimal getPaasAddPoint()
	{
		return paasAddPoint;
	}
	/**
	 * 设置地区平台增向积分额
	 * @param paasAddPoint 地区平台增向积分额
	 */
	public void setPaasAddPoint(BigDecimal paasAddPoint)
	{
		this.paasAddPoint = paasAddPoint;
	}
	/**
	 * 获取托管企业减向积分额
	 * @return trusteeSubPoint 托管企业减向积分额
	 */
	public BigDecimal getTrusteeSubPoint()
	{
		return trusteeSubPoint;
	}
	/**
	 * 设置托管企业减向积分额
	 * @param trusteeSubPoint 托管企业减向积分额
	 */
	public void setTrusteeSubPoint(BigDecimal trusteeSubPoint)
	{
		this.trusteeSubPoint = trusteeSubPoint;
	}
	/**
	 * 获取服务公司减向积分额
	 * @return serviceSubPoint 服务公司减向积分额
	 */
	public BigDecimal getServiceSubPoint()
	{
		return serviceSubPoint;
	}
	/**
	 * 设置服务公司减向积分额
	 * @param serviceSubPoint 服务公司减向积分额
	 */
	public void setServiceSubPoint(BigDecimal serviceSubPoint)
	{
		this.serviceSubPoint = serviceSubPoint;
	}
	/**
	 * 获取管理公司减向积分额
	 * @return manageSubPoint 管理公司减向积分额
	 */
	public BigDecimal getManageSubPoint()
	{
		return manageSubPoint;
	}
	/**
	 * 设置管理公司减向积分额
	 * @param manageSubPoint 管理公司减向积分额
	 */
	public void setManageSubPoint(BigDecimal manageSubPoint)
	{
		this.manageSubPoint = manageSubPoint;
	}
	/**
	 * 获取地区平台减向积分额
	 * @return paasSubPoint 地区平台减向积分额
	 */
	public BigDecimal getPaasSubPoint()
	{
		return paasSubPoint;
	}
	/**
	 * 设置地区平台减向积分额
	 * @param paasSubPoint 地区平台减向积分额
	 */
	public void setPaasSubPoint(BigDecimal paasSubPoint)
	{
		this.paasSubPoint = paasSubPoint;
	}
	/**
	 * 获取结余增向积分额
	 * @return surplusSubPoint 结余增向积分额
	 */
	public BigDecimal getSurplusSubPoint()
	{
		return surplusSubPoint;
	}
	/**
	 * 设置结余减向积分额
	 * @param surplusSubPoint 结余减向积分额
	 */
	public void setSurplusSubPoint(BigDecimal surplusSubPoint)
	{
		this.surplusSubPoint = surplusSubPoint;
	}
	/**
	 * 获取结余增向积分额
	 * @return surplusAddPoint 结余增向积分额
	 */
	public BigDecimal getSurplusAddPoint()
	{
		return surplusAddPoint;
	}
	/**
	 * 设置结余增向积分额
	 * @param surplusAddPoint 结余增向积分额
	 */
	public void setSurplusAddPoint(BigDecimal surplusAddPoint)
	{
		this.surplusAddPoint = surplusAddPoint;
	}
	/**
	 * 获取备注
	 * @return remark 备注
	 */
	public String getRemark()
	{
		return remark;
	}
	/**
	 * 设置备注
	 * @param remark 备注
	 */
	public void setRemark(String remark)
	{
		this.remark = remark;
	}
	/**
	 * 获取标记此条记录的状态
	 * @return isActive 标记此条记录的状态
	 */
	public String getIsActive()
	{
		return isActive;
	}
	/**
	 * 设置标记此条记录的状态
	 * @param isActive 标记此条记录的状态
	 */
	public void setIsActive(String isActive)
	{
		this.isActive = isActive;
	}
	/**
	 * 获取创建时间
	 * @return createdDate 创建时间
	 */
	public Timestamp getCreatedDate()
	{
		return createdDate == null?null: (Timestamp)createdDate.clone();
	}
	/**
	 * 设置创建时间
	 * @param createdDate 创建时间
	 */
	public void setCreatedDate(Timestamp createdDate)
	{
		this.createdDate = createdDate == null? null:(Timestamp)createdDate.clone();
	}
	/**
	 * 获取由谁创建，值为用户的伪键ID
	 * @return createdBy 由谁创建，值为用户的伪键ID
	 */
	public String getCreatedBy()
	{
		return createdBy;
	}
	/**
	 * 设置由谁创建，值为用户的伪键ID
	 * @param createdBy 由谁创建，值为用户的伪键ID
	 */
	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}
	/**
	 * 获取修改时间
	 * @return updatedDate 修改时间
	 */
	public Timestamp getUpdatedDate()
	{
		return updatedDate == null?null: (Timestamp)updatedDate.clone();
	}
	/**
	 * 设置修改时间
	 * @param updatedDate 修改时间
	 */
	public void setUpdatedDate(Timestamp updatedDate)
	{
		this.updatedDate = updatedDate == null? null: (Timestamp) updatedDate.clone();
	}
	/**
	 * 获取由谁更新，值为用户的伪键ID
	 * @return updatedBy 由谁更新，值为用户的伪键ID
	 */
	public String getUpdatedBy()
	{
		return updatedBy;
	}
	/**
	 * 设置由谁更新，值为用户的伪键ID
	 * @param updatedBy 由谁更新，值为用户的伪键ID
	 */
	public void setUpdatedBy(String updatedBy)
	{
		this.updatedBy = updatedBy;
	}

	/**
	 * 获取汇总笔数
	 *
	 * @return pvAddCount 汇总笔数
	 */
	public Integer getPvAddCount()
	{
		return pvAddCount;
	}

	/**
	 * 设置汇总笔数
	 *
	 * @param pvAddCount
	 *            汇总笔数
	 */
	public void setPvAddCount(Integer pvAddCount)
	{
		this.pvAddCount = pvAddCount;
	}

	public BigDecimal getNoCardPaasAddPoint() {
		return noCardPaasAddPoint;
	}

	public void setNoCardPaasAddPoint(BigDecimal noCardPaasAddPoint) {
		this.noCardPaasAddPoint = noCardPaasAddPoint;
	}
}

	