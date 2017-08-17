package com.gy.hsxt.ps.bean;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @description 冲正参数实体类
 * @author chenhz
 * @createDate 2015-7-27 下午2:30:12
 * @Company 深圳市归一科技研发有限公司
 * @version v0.0.1
 */
public class Correct extends Result implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8075005508761037090L;
	/** 交易类型 */
	@NotBlank(message = "交易类型不能为空")
	@Size(min =6, max = 6, message = "交易类型必须6位！")
	@Pattern(regexp = "^[A-Z][1-2][1-3][0-9][1-9]0$", message = "交易类型错误")
	private String transType;
	/** 企业互生号*/
	@NotBlank(message = "企业互生号不能为空")
	private String entResNo;
	/** 设备编号 */
	private String equipmentNo;
	/** 渠道类型 */
	@NotNull(message = "渠道类型不能为空")
	private Integer channelType;
	/** 设备类型 */
	private Integer equipmentType;
	/** 原始交易流水号 */
	//@NotBlank(message = "原始交易流水号不能为空")
	private String sourceTransNo;
	/** 原批次号  */
	private String sourceBatchNo;
	/** 冲正原因 */
	@NotBlank(message = "冲正原因不能为空")
	private String returnReason;
	/** 冲正发起方 */
	@NotBlank(message = "冲正发起方不能为空")
	private String initiate;
	/** 交易时间 */
	@NotBlank(message = "交易时间不能为空")
	private String  transDate;
	/*** 终端流水号(POS) */
	@NotBlank(message = " 终端流水号(POS)不能为空")
	private String termRunCode;

	/*货币转换率 */
	private String  currencyRate;

	/**企业名称 */
	private  String entName;

	/**店铺名称 */
	private  String mallName;

	/*备注 */
	private  String remark;
	
	/**
	 * 获取交易类型
	 * @return transType 交易类型
	 */
	public String getTransType() {
		return transType;
	}
	/**
	 * 设置交易类型
	 * @param transType 交易类型
	 */
	public void setTransType(String transType) {
		this.transType = transType;
	}
	/**
	 * 获取企业互生号
	 * @return entResNo 企业互生号
	 */
	public String getEntResNo() {
		return entResNo;
	}
	/**
	 * 设置企业互生号
	 * @param entResNo 企业互生号
	 */
	public void setEntResNo(String entResNo) {
		this.entResNo = entResNo;
	}
	/**
	 * 获取设备编号
	 * @return equipmentNo 设备编号
	 */
	public String getEquipmentNo() {
		return equipmentNo;
	}
	/**
	 * 设置设备编号
	 * @param equipmentNo 设备编号
	 */
	public void setEquipmentNo(String equipmentNo) {
		this.equipmentNo = equipmentNo;
	}
	/**
	 * 获取渠道类型
	 * @return channelType 渠道类型
	 */
	public Integer getChannelType() {
		return channelType;
	}
	/**
	 * 设置渠道类型
	 * @param channelType 渠道类型
	 */
	public void setChannelType(Integer channelType) {
		this.channelType = channelType;
	}
	/**
	 * 获取设备类型
	 * @return equipmentType 设备类型
	 */
	public Integer getEquipmentType() {
		return equipmentType;
	}
	/**
	 * 设置设备类型
	 * @param equipmentType 设备类型
	 */
	public void setEquipmentType(Integer equipmentType) {
		this.equipmentType = equipmentType;
	}
	/**
	 * 获取原始交易号
	 * @return sourceTransNo 原始交易号
	 */
	public String getSourceTransNo() {
		return sourceTransNo;
	}
	/**
	 * 设置原始交易号
	 * @param sourceTransNo 原始交易号
	 */
	public void setSourceTransNo(String sourceTransNo) {
		this.sourceTransNo = sourceTransNo;
	}

	/**
	 * 获取原批次号
	 * @return String sourceBatchNo
	 */
	public String getSourceBatchNo() {
		return sourceBatchNo;
	}
	/**
	 * 设置原批次号
	 * @param sourceBatchNo 原批次号
	 */
	public void setSourceBatchNo(String sourceBatchNo) {
		this.sourceBatchNo = sourceBatchNo;
	}
	/**
	 * 获取冲正原因
	 * @return returnReason 冲正原因
	 */
	public String getReturnReason() {
		return returnReason;
	}
	/**
	 * 设置冲正原因
	 * @param returnReason 冲正原因
	 */
	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}
	/**
	 * 获取冲正发起方
	 * @return initiate 冲正发起方
	 */
	public String getInitiate() {
		return initiate;
	}
	/**
	 * 设置冲正发起方
	 * @param initiate 冲正发起方
	 */
	public void setInitiate(String initiate) {
		this.initiate = initiate;
	}
	/**
	 * 获取交易时间
	 * @return transDate 交易时间
	 */
	public String getTransDate() {
		return transDate;
	}
	/**
	 * 设置交易时间
	 * @param transDate 交易时间
	 */
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	public String getTermRunCode() {
		return termRunCode;
	}

	public void setTermRunCode(String termRunCode) {
		this.termRunCode = termRunCode;
	}

	public String getCurrencyRate() {
		return currencyRate;
	}

	public void setCurrencyRate(String currencyRate) {
		this.currencyRate = currencyRate;
	}

	public String getEntName() {
		return entName;
	}

	public void setEntName(String entName) {
		this.entName = entName;
	}

	public String getMallName() {
		return mallName;
	}

	public void setMallName(String mallName) {
		this.mallName = mallName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
