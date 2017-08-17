package com.gy.hsxt.es.bean;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @description 撤单参数实体类
 * @author chenhz
 * @createDate 2015-7-27 下午2:30:12
 * @Company 深圳市归一科技研发有限公司
 * @version v0.0.1
 */
public class Cancel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1652125132878954902L;
	/*** 企业互生号*/
	@NotBlank(message = "企业互生号不能为空")
	@Size(min = 11, max = 11, message = "企业互生号必须11位！")
	private String entResNo;
	
	/** 交易类型  */
	@NotBlank(message = "交易类型不能为空")
	@Size(min =6, max = 6, message = "交易类型必须6位！")
	@Pattern(regexp = "^[A-Z][1-2][1-3][1-9][0-9]0$", message = "交易类型错误")
	private String transType;
	
	/** 设备编号  */
	private String equipmentNo;
	
	/** 原始电商交易号  */
	@NotBlank(message = "交易流水号不能为空")
	private String sourceTransNo;
	
	/** 消费积分原始交易流水号*/
	@NotBlank(message = "消费积分原始交易流水号不能为空")
	private String oldTransNo;
	
	/** 原批次号  */
	private String sourceBatchNo;
	
	/** 原始交易时间  */
	@NotBlank(message = "交易时间不能为空")
	private String sourceTransDate;
	
	/** 操作员  */
	private String operNo;
	
	/** 是否扣商业服务费  0 是(收费) 1 否(不收费)**/
	@NotNull(message = "是否扣费不能为空")
	private int isDeduction;

	/**订单号*/
	@NotBlank(message = "订单号不能为空")
	private  String orderNo;

	/*货币转换率 */
	private String  currencyRate;

	/**企业名称 */
//	private  String entName;

	/**店铺名称 */
//	private  String mallName;

	/*备注 */
	private  String remark;

	/**
	 * 获取企业互生号
	 *
	 * @return entResNo 企业互生号
	 */
	public String getEntResNo() {
		return entResNo;
	}

	/**
	 * 设置企业互生号
	 *
	 * @param entResNo 企业互生号
	 */
	public void setEntResNo(String entResNo) {
		this.entResNo = entResNo;
	}
	/**
	 * 获取原交易流水号
	 * @return oldTransNo 原交易流水号
	 */
	public String getOldTransNo() {
		return oldTransNo;
	}
	/**
	 * 设置原交易流水号
	 * @param oldTransNo 原交易流水号
	 */
	public void setOldTransNo(String oldTransNo) {
		this.oldTransNo = oldTransNo;
	}
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
	 * 获取原始交易时间
	 * @return sourceTransDate 原始交易时间
	 */
	public String getSourceTransDate() {
		return sourceTransDate;
	}
	/**
	 * 设置原始交易时间
	 * @param sourceTransDate 原始交易时间
	 */
	public void setSourceTransDate(String sourceTransDate) {
		this.sourceTransDate = sourceTransDate;
	}
	/**
	 * 获取操作员
	 * @return operNo 操作员
	 */
	public String getOperNo() {
		return operNo;
	}
	/**
	 * 设置操作员
	 * @param operNo 操作员
	 */
	public void setOperNo(String operNo) {
		this.operNo = operNo;
	}

	/**
	 * 获取是否扣商业服务费0是(收费)1否(不收费)
	 * @return isDeduction 是否扣商业服务费0是(收费)1否(不收费)
	 */
	public int getIsDeduction()
	{
		return isDeduction;
	}

	/**
	 * 设置是否扣商业服务费0是(收费)1否(不收费)
	 * @param isDeduction 是否扣商业服务费0是(收费)1否(不收费)
	 */
	public void setIsDeduction(int isDeduction)
	{
		this.isDeduction = isDeduction;
	}

	public String getCurrencyRate() {
		return currencyRate;
	}

	public void setCurrencyRate(String currencyRate) {
		this.currencyRate = currencyRate;
	}

	/*public String getEntName() {
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
	}*/

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
}
