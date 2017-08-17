package com.gy.hsxt.es.bean;
import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @description 冲正参数实体类
 * @author chenhz
 * @createDate 2015-7-27 下午2:30:12
 * @Company 深圳市归一科技研发有限公司
 * @version v0.0.1
 */
public class Correct implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8075005508761037090L;
	/** 交易类型 */
	@NotBlank(message = "交易类型不能为空")
	@Size(min =6, max = 6, message = "交易类型必须6位！")
	@Pattern(regexp = "^[A-Z][1-2][1-3][1-9][0-9]0$", message = "交易类型错误")
	private String transType;
	
	/** 企业互生号*/
	@NotBlank(message = "企业互生号不能为空")
	private String entResNo;
	
	/** 原始交易流水号 */
	@NotBlank(message = "原始交易流水号不能为空")
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

	/*备注 */
	private  String remark;

	/**订单号*/
	//@NotBlank(message = "订单号不能为空")
	private  String orderNo;

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
