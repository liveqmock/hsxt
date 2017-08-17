package com.gy.hsxt.ps.bean;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @description 批结算参数实体类
 * @author chenhz
 * @createDate 2015-7-27 下午2:30:12
 * @Company 深圳市归一科技研发有限公司
 * @version v0.0.1
 */
public class BatSettle implements Serializable {

	private static final long serialVersionUID = 5519430871977429048L;

	/** * 企业互生号 */
	private String entResNo;
	/** * 设备编号 */
	@NotBlank(message = "设备编号不能为空！")
	private String equipmentNo;
	/** * 设备类型 */
	@NotNull(message = "设备类型不能为空！")
	private Integer equipmentType;
	/** 原批次号 **/
	@NotBlank(message = "原批次号不能为空！")
	private String sourceBatchNo;
	/** * 积分笔数 */
	@NotNull(message = "积分笔数不能为空！")
	private Integer pointCnt = 0;
	/** * 积分交易总金额*/
	@NotBlank(message = "积分总数不能为空！")
	private String pointSum;
	/** * 积分撤单笔数 */
	@NotNull(message = "积分撤单笔数不能为空！")
	private Integer pointCancelCnt = 0;
	/** * 积分撤单总金额 */
	@NotBlank(message = "积分撤单总数不能为空！")
	private String pointCancelSum;
	/** * 积分退货笔数 */
	@NotNull(message = "积分退货笔数不能为空！")
	private Integer pointBackCnt = 0;
	/** * 积分退货总金额 */
	@NotBlank(message = "积分退货总金额不能为空！")
	private String pointBackSum;
	/** * 互生币笔数 */
	@NotNull(message = "互生币笔数不能为空！")
	private Integer hsbCnt = 0;
	/** * 互生币总金额 */
	@NotBlank(message = "互生币总数不能为空！")
	private String hsbSum;
	/** * 互生币退货笔数 */
	@NotNull(message = "互生币退货笔数不能为空！")
	private Integer hsbBackCnt = 0;
	/** * 互生币退货总金额 */
	@NotBlank(message = "互生币退货总金额不能为空！")
	private String hsbBackSum;

	/*** 互生币撤单笔数 */
	@NotNull(message = "互生币撤单笔数不能为空！")
	private Integer inHsbCancelCount = 0;
	/*** 互生币撤单总金额 */
	@NotBlank(message = "互生币撤单总金额不能为空！")
	private String inHsbCancelSum;

	/**
	 * 获取互生币撤单笔数
	 * 
	 * @return inHsbCancelCount 该批次内卡输入互生币撤单笔数
	 */
	public Integer getInHsbCancelCount() {
		return inHsbCancelCount;
	}

	/**
	 * 设置互生币撤单笔数
	 * 
	 * @param inHsbCancelCount
	 *            互生币撤单笔数
	 */
	public void setInHsbCancelCount(Integer inHsbCancelCount) {
		this.inHsbCancelCount = inHsbCancelCount;
	}

	/**
	 * 获取互生币撤单总金额
	 * 
	 * @return inHsbCancelSum 互生币撤单总金额
	 */
	public String getInHsbCancelSum() {
		return inHsbCancelSum;
	}

	/**
	 * 设置互生币撤单总金额
	 * 
	 * @param inHsbCancelSum
	 *            互生币撤单总金额
	 */
	public void setInHsbCancelSum(String inHsbCancelSum) {
		this.inHsbCancelSum = inHsbCancelSum;
	}

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
	 * @param entResNo
	 *            企业互生号
	 */
	public void setEntResNo(String entResNo) {
		this.entResNo = entResNo;
	}

	/**
	 * 获取设备编号
	 * 
	 * @return equipmentNo 设备编号
	 */
	public String getEquipmentNo() {
		return equipmentNo;
	}

	/**
	 * 设置设备编号
	 * 
	 * @param equipmentNo
	 *            设备编号
	 */
	public void setEquipmentNo(String equipmentNo) {
		this.equipmentNo = equipmentNo;
	}

	/**
	 * 获取设备类型
	 * 
	 * @return equipmentType 设备类型
	 */
	public Integer getEquipmentType() {
		return equipmentType;
	}

	/**
	 * 设置设备类型
	 * 
	 * @param equipmentType
	 *            设备类型
	 */
	public void setEquipmentType(Integer equipmentType) {
		this.equipmentType = equipmentType;
	}

	/**
	 * 获取批次号
	 * 
	 * @return sourceBatchNo 批次号
	 */
	public String getSourceBatchNo() {
		return sourceBatchNo;
	}

	/**
	 * 设置批次号
	 * 
	 * @param sourceBatchNo
	 *            批次号
	 */
	public void setSourceBatchNo(String sourceBatchNo) {
		this.sourceBatchNo = sourceBatchNo;
	}

	/**
	 * 获取积分笔数
	 * 
	 * @return pointCnt 积分笔数
	 */
	public Integer getPointCnt() {
		return pointCnt;
	}

	/**
	 * 设置积分笔数
	 * 
	 * @param pointCnt
	 *            积分笔数
	 */
	public void setPointCnt(Integer pointCnt) {
		this.pointCnt = pointCnt;
	}

	/**
	 * 获取积分总数
	 * 
	 * @return pointSum 积分总数
	 */
	public String getPointSum() {
		return pointSum;
	}

	/**
	 * 设置积分总数
	 * 
	 * @param pointSum
	 *            积分总数
	 */
	public void setPointSum(String pointSum) {
		this.pointSum = pointSum;
	}

	/**
	 * 获取积分撤单笔数
	 * 
	 * @return pointCancelCnt 积分撤单笔数
	 */
	public Integer getPointCancelCnt() {
		return pointCancelCnt;
	}

	/**
	 * 设置积分撤单笔数
	 * 
	 * @param pointCancelCnt
	 *            积分撤单笔数
	 */
	public void setPointCancelCnt(Integer pointCancelCnt) {
		this.pointCancelCnt = pointCancelCnt;
	}

	/**
	 * 获取积分撤单总数
	 * 
	 * @return pointCancelSum 积分撤单总数
	 */
	public String getPointCancelSum() {
		return pointCancelSum;
	}

	/**
	 * 设置积分撤单总数
	 * 
	 * @param pointCancelSum
	 *            积分撤单总数
	 */
	public void setPointCancelSum(String pointCancelSum) {
		this.pointCancelSum = pointCancelSum;
	}

	/**
	 * 获取积分退货笔数
	 * 
	 * @return pointBackCnt 积分退货笔数
	 */
	public Integer getPointBackCnt() {
		return pointBackCnt;
	}

	/**
	 * 设置积分退货笔数
	 * 
	 * @param pointBackCnt
	 *            积分退货笔数
	 */
	public void setPointBackCnt(Integer pointBackCnt) {
		this.pointBackCnt = pointBackCnt;
	}

	/**
	 * 获取积分退货总数
	 * 
	 * @return pointBackSum 积分退货总数
	 */
	public String getPointBackSum() {
		return pointBackSum;
	}

	/**
	 * 设置积分退货总数
	 * 
	 * @param pointBackSum
	 *            积分退货总数
	 */
	public void setPointBackSum(String pointBackSum) {
		this.pointBackSum = pointBackSum;
	}

	public Integer getHsbCnt() {
		return hsbCnt;
	}

	public void setHsbCnt(Integer hsbCnt) {
		this.hsbCnt = hsbCnt;
	}

	public String getHsbSum() {
		return hsbSum;
	}

	public void setHsbSum(String hsbSum) {
		this.hsbSum = hsbSum;
	}

	public Integer getHsbBackCnt() {
		return hsbBackCnt;
	}

	public void setHsbBackCnt(Integer hsbBackCnt) {
		this.hsbBackCnt = hsbBackCnt;
	}

	public String getHsbBackSum() {
		return hsbBackSum;
	}

	public void setHsbBackSum(String hsbBackSum) {
		this.hsbBackSum = hsbBackSum;
	}
}
