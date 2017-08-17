package com.gy.hsxt.ps.bean;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @description 批上送参数实体类
 * @author chenhz
 * @createDate 2015-7-27 下午2:30:12
 * @Company 深圳市归一科技研发有限公司
 * @version v0.0.1
 */
public class BatUpload implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -659880842218315561L;

	/**	 * 企业互生号	 */
	@NotBlank(message = "企业互生号不能为空")
	private String entResNo;
	/**	 * 设备编号	 */
	@NotBlank(message = "设备编号不能为空")
	private String equipmentNo;
	/**	 * 设备类型	 */
	@NotNull(message = "设备类型不能为空")
	private Integer equipmentType;
	/**	 * 原积分交易流水号	 */
	@NotBlank(message = "原积分交易流水号不能为空")
	private String transNo;
	/**	 * 原始交易号	 */
	@NotBlank(message = "原始交易号不能为空")
	private String sourceTransNo;
	/*** 交易类型 */
	@NotBlank(message = "交易类型不能为空")
	@Size(min = 6, max = 6, message = "交易类型最大长度是6位！")
	@Pattern(regexp = "^[A-Z][1-2][1-3][0-9][0-9]0$", message = "交易类型错误")
	private String transType;
	/**	 * 批次号	 */
	private String batchNo;
	/**	 * 交易金额	 */
	private String transAmount = "0";
	/**	 * 消费者互生号     */
	private String perResNo;
	/**	 * 积分比例	 */
	private String pointRate = "0";
	/**	 * 积分款	 */
	private String entPoint = "0"; 
	/**	 * 本次积分	 */
	private String perPoint = "0";
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
	 * 获取原交易流水号
	 * @return transNo 原交易流水号
	 */
	public String getTransNo() {
		return transNo;
	}
	/**
	 * 设置原交易流水号
	 * @param transNo 原交易流水号
	 */
	public void setTransNo(String transNo) {
		this.transNo = transNo;
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
	 * 获取交易类型
	 *
	 * @return transType 交易类型
	 */
	public String getTransType()
	{
		return transType;
	}

	/**
	 * 设置交易类型
	 *
	 * @param transType
	 *            交易类型
	 */
	public void setTransType(String transType)
	{
		this.transType = transType;
	}
	/**
	 * 获取批次号
	 * @return batchNo 批次号
	 */
	public String getBatchNo() {
		return batchNo;
	}
	/**
	 * 设置批次号
	 * @param batchNo 批次号
	 */
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	/**
	 * 获取交易金额
	 * @return transAmount 交易金额
	 */
	public String getTransAmount() {
		return transAmount;
	}
	/**
	 * 设置交易金额
	 * @param transAmount 交易金额
	 */
	public void setTransAmount(String transAmount) {
		this.transAmount = transAmount;
	}
	/**
	 * 获取消费者互生号
	 * @return perResNo 消费者互生号
	 */
	public String getPerResNo() {
		return perResNo;
	}
	/**
	 * 设置消费者互生号
	 * @param perResNo 消费者互生号
	 */
	public void setPerResNo(String perResNo) {
		this.perResNo = perResNo;
	}
	/**
	 * 获取积分比例
	 * @return pointRate 积分比例
	 */
	public String getPointRate() {
		return pointRate;
	}
	/**
	 * 设置积分比例
	 * @param pointRate 积分比例
	 */
	public void setPointRate(String pointRate) {
		this.pointRate = pointRate;
	}
	/**
	 * 获取积分预付款
	 * @return entPoint 积分预付款
	 */
	public String getEntPoint() {
		return entPoint;
	}
	/**
	 * 设置积分预付款
	 * @param entPoint 积分预付款
	 */
	public void setEntPoint(String entPoint) {
		this.entPoint = entPoint;
	}
	/**
	 * 获取本次积分
	 * @return perPoint 本次积分
	 */
	public String getPerPoint() {
		return perPoint;
	}
	/**
	 * 设置本次积分
	 * @param perPoint 本次积分
	 */
	public void setPerPoint(String perPoint) {
		this.perPoint = perPoint;
	}

 
	
	
	
 
}
