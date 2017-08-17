/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ws.bean;

import static com.gy.hsxt.common.utils.StringUtils.isBlank;
import static com.gy.hsxt.common.utils.StringUtils.isNotBlank;

import java.io.Serializable;
import java.math.BigDecimal;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ws.common.WsErrorCode;

/**
 * 医疗明细
 * 
 * @Package: com.gy.hsxt.ws.bean
 * @ClassName: MedicalDetail
 * @Description: 医疗明细
 * 
 * @author: chenhongzhi
 * @date: 2015-11-11 下午8:07:57
 * @version V3.0
 */
public class MedicalDetail implements Serializable {
	private static long serialVersionUID = -8483135722558835411L;
	/** 医疗明细ID */
	private String medicalId;
	/** 申请流水号 关联表 T_WS_APPLY_WELFARE */
	private String applyWelfareNo;
	/** 理赔核算单编号 */
	private String accountingId;
	/** 类别 **/
	private String category;
	/** 项目名称 **/
	private String itemName;
	/** 规格 **/
	private String standard;
	/** 数量 **/
	private Integer quantity;
	/** 单位 **/
	private Integer unit;
	/** 单价 **/
	private String price;
	/** 金额 **/
	private String amount;
	/** 比例 **/
	private String proportion;
	/** 医保支付金额 **/
	private String healthPayAmount;
	/** 个人支付金额 **/
	private String personalPayAmount;
	/** 互生支付金额 */
	private String hsPayAmount;
	/** 备注说明 */
	private String explain;
	
	/** 账单开始日期 */
	private String billsStartDate;
	
	/** 账单结束日期 */
	private String billsEndDate;

	/** 账单发生区域  省编码*/
	private String provinceNo;
	
	/** 账单发生区域  城市*/
	private String cityNo;

	/**
	 * 获取类别
	 * 
	 * @return category 类别
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * 设置类别
	 * 
	 * @param category
	 *            类别
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * 获取项目名称
	 * 
	 * @return itemName 项目名称
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * 设置项目名称
	 * 
	 * @param itemName
	 *            项目名称
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * 获取规格
	 * 
	 * @return standard 规格
	 */
	public String getStandard() {
		return standard;
	}

	/**
	 * 设置规格
	 * 
	 * @param standard
	 *            规格
	 */
	public void setStandard(String standard) {
		this.standard = standard;
	}

	/**
	 * 获取单价
	 * 
	 * @return price 单价
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * 设置单价
	 * 
	 * @param price
	 *            单价
	 */
	public void setPrice(String price) {
		this.price = price;
	}

	/**
	 * 获取金额
	 * 
	 * @return amount 金额
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * 设置金额
	 * 
	 * @param amount
	 *            金额
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * 获取比例
	 * 
	 * @return proportion 比例
	 */
	public String getProportion() {
		return proportion;
	}

	/**
	 * 设置比例
	 * 
	 * @param proportion
	 *            比例
	 */
	public void setProportion(String proportion) {
		this.proportion = proportion;
	}

	/**
	 * 获取医保支付金额
	 * 
	 * @return healthPayAmount 医保支付金额
	 */
	public String getHealthPayAmount() {
		return healthPayAmount;
	}

	/**
	 * 设置医保支付金额
	 * 
	 * @param healthPayAmount
	 *            医保支付金额
	 */
	public void setHealthPayAmount(String healthPayAmount) {
		this.healthPayAmount = healthPayAmount;
	}

	/**
	 * 获取个人支付金额
	 * 
	 * @return personalPayAmount 个人支付金额
	 */
	public String getPersonalPayAmount() {
		return personalPayAmount;
	}

	/**
	 * 设置个人支付金额
	 * 
	 * @param personalPayAmount
	 *            个人支付金额
	 */
	public void setPersonalPayAmount(String personalPayAmount) {
		this.personalPayAmount = personalPayAmount;
	}

	/**
	 * @return the 理赔核算单编号
	 */
	public String getAccountingId() {
		return accountingId;
	}

	/**
	 * @param 理赔核算单编号
	 *            the accountingId to set
	 */
	public void setAccountingId(String accountingId) {
		this.accountingId = accountingId;
	}

	/**
	 * @return the 互生支付金额
	 */
	public String getHsPayAmount() {
		return hsPayAmount;
	}

	/**
	 * @param 互生支付金额
	 *            the hsPayAmount to set
	 */
	public void setHsPayAmount(String hsPayAmount) {
		this.hsPayAmount = hsPayAmount;
	}

	/**
	 * 获取说明
	 * 
	 * @return explain 说明
	 */
	public String getExplain() {
		return explain;
	}

	/**
	 * 设置说明
	 * 
	 * @param explain
	 *            说明
	 */
	public void setExplain(String explain) {
		this.explain = explain;
	}

	/**
	 * @return the 医疗明细ID
	 */
	public String getMedicalId() {
		return medicalId;
	}

	/**
	 * @param 医疗明细ID
	 *            the medicalId to set
	 */
	public void setMedicalId(String medicalId) {
		this.medicalId = medicalId;
	}

	/**
	 * @return the 数量
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * @param 数量
	 *            the quantity to set
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the 单位
	 */
	public Integer getUnit() {
		return unit;
	}

	/**
	 * @param 单位
	 *            the unit to set
	 */
	public void setUnit(Integer unit) {
		this.unit = unit;
	}

	/**
	 * @return the 申请流水号关联表T_WS_APPLY_WELFARE
	 */
	public String getApplyWelfareNo() {
		return applyWelfareNo;
	}

	/**
	 * @param 申请流水号关联表T_WS_APPLY_WELFARE
	 *            the applyWelfareNo to set
	 */
	public void setApplyWelfareNo(String applyWelfareNo) {
		this.applyWelfareNo = applyWelfareNo;
	}
	

	/**
	 * @return the 账单结束日期
	 */
	public String getBillsEndDate() {
		return billsEndDate;
	}

	/**
	 * @param 账单结束日期 the billsEndDate to set
	 */
	public void setBillsEndDate(String billsEndDate) {
		this.billsEndDate = billsEndDate;
	}

	/**
	 * @return the 账单开始日期
	 */
	public String getBillsStartDate() {
		return billsStartDate;
	}

	/**
	 * @param 账单开始日期 the billsStartDate to set
	 */
	public void setBillsStartDate(String billsStartDate) {
		this.billsStartDate = billsStartDate;
	}

	/**
	 * @return the 账单发生区域省编码
	 */
	public String getProvinceNo() {
		return provinceNo;
	}

	/**
	 * @param 账单发生区域省编码 the provinceNo to set
	 */
	public void setProvinceNo(String provinceNo) {
		this.provinceNo = provinceNo;
	}

	/**
	 * @return the 账单发生区域城市
	 */
	public String getCityNo() {
		return cityNo;
	}

	/**
	 * @param 账单发生区域城市 the cityNo to set
	 */
	public void setCityNo(String cityNo) {
		this.cityNo = cityNo;
	}

	public void validParams() {
		validEmptyStr(applyWelfareNo, "申请流水号[applyWelfareNo]不能为空");
		validEmptyStr(accountingId, "核算流水号[accountingId]不能为空");
		validEmptyStr(itemName, "项目名称[itemName]不能为空");
		validAmount(price, "价格金额[price]格式有误");
		validAmount(healthPayAmount, "医保支付金额[healthPayAmount]格式有误");
		validAmount(personalPayAmount, "个人支付金额[personalPayAmount]格式有误");
		validAmount(hsPayAmount, "互生支付金额[hsPayAmount]格式有误");
	}

	private void validEmptyStr(String param, String errorDesc) {
		if (isBlank(param)) {
			throw new HsException(WsErrorCode.PARAM_IS_NULL.getCode(),
					errorDesc);
		}
	}

	private void validAmount(String amount, String desc) {
		if (isNotBlank(amount)) {
			try {
				new BigDecimal(amount);
			} catch (Exception e) {
				throw new HsException(WsErrorCode.PARAM_IS_NULL.getCode(), desc);
			}
		}
	}

}
