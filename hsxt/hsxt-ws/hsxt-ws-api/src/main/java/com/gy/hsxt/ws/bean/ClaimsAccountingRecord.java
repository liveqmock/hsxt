package com.gy.hsxt.ws.bean;

import java.io.Serializable;

/**
 * 理赔核算记录
 * 
 * @Package: com.gy.hsxt.ws.bean
 * @ClassName: ClaimsAccountingRecord
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-12-22 下午6:19:39
 * @version V1.0
 */
public class ClaimsAccountingRecord implements Serializable {
	/** 理赔核算单编号（主键） */
	private String accountingId;

	/** 申请单编号 */
	private String applyWelfareNo;

	/** 申请人互生号 **/
	private String hsResNo;

	/** 申请人姓名 **/
	private String proposerName;

	/** 工单编号 */
	private String taskId;

	/** 医保支付金额 总计 */
	private String healthPayAmount;

	/** 个人支付金额 总计 */
	private String personalPayAmount;

	/** 互生支付金额 总计 （核算金额） */
	private String hsPayAmount;

	/** 核算人姓名 */
	private String accountPersonName;

	/** 核算人客户号 */
	private String accountPersonCustId;

	/** 核算日期 */
	private String accountDate;

	/** 核算单状态 0 待核算、1 核算中、2 已核算 */
	private Integer status;

	/** 福利资格ID */
	private String welfareId;

	/** 账单开始日期 */
	private String billsStartDate;

	/** 账单结束日期 */
	private String billsEndDate;

	/** 账单发生区域 */
	private String billsArea;
	
	/** 账单发生区域  省编码*/
	private String provinceNo;
	
	/** 账单发生区域  城市*/
	private String cityNo;

	/** 账单发生医院 */
	private String hospital;

	private static final long serialVersionUID = 1L;

	public String getAccountingId() {
		return accountingId;
	}

	public void setAccountingId(String accountingId) {
		this.accountingId = accountingId == null ? null : accountingId.trim();
	}

	public String getApplyWelfareNo() {
		return applyWelfareNo;
	}

	public void setApplyWelfareNo(String applyWelfareNo) {
		this.applyWelfareNo = applyWelfareNo == null ? null : applyWelfareNo.trim();
	}

	public String getHsResNo() {
		return hsResNo;
	}

	public void setHsResNo(String hsResNo) {
		this.hsResNo = hsResNo;
	}

	public String getProposerName() {
		return proposerName;
	}

	public void setProposerName(String proposerName) {
		this.proposerName = proposerName;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId == null ? null : taskId.trim();
	}

	public String getAccountPersonName() {
		return accountPersonName;
	}

	public void setAccountPersonName(String accountPersonName) {
		this.accountPersonName = accountPersonName == null ? null : accountPersonName.trim();
	}

	public String getAccountPersonCustId() {
		return accountPersonCustId;
	}

	public void setAccountPersonCustId(String accountPersonCustId) {
		this.accountPersonCustId = accountPersonCustId == null ? null : accountPersonCustId.trim();
	}

	public String getAccountDate() {
		return accountDate;
	}

	public void setAccountDate(String accountDate) {
		this.accountDate = accountDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getHealthPayAmount() {
		return healthPayAmount;
	}

	public void setHealthPayAmount(String healthPayAmount) {
		this.healthPayAmount = healthPayAmount;
	}

	public String getPersonalPayAmount() {
		return personalPayAmount;
	}

	public void setPersonalPayAmount(String personalPayAmount) {
		this.personalPayAmount = personalPayAmount;
	}

	public String getHsPayAmount() {
		return hsPayAmount;
	}

	public void setHsPayAmount(String hsPayAmount) {
		this.hsPayAmount = hsPayAmount;
	}

	public String getWelfareId() {
		return welfareId;
	}

	public void setWelfareId(String welfareId) {
		this.welfareId = welfareId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getBillsStartDate() {
		return billsStartDate;
	}

	public void setBillsStartDate(String billsStartDate) {
		this.billsStartDate = billsStartDate;
	}

	public String getBillsEndDate() {
		return billsEndDate;
	}

	public void setBillsEndDate(String billsEndDate) {
		this.billsEndDate = billsEndDate;
	}

	public String getBillsArea() {
		return billsArea;
	}

	public void setBillsArea(String billsArea) {
		this.billsArea = billsArea;
	}

	public String getHospital() {
		return hospital;
	}

	public void setHospital(String hospital) {
		this.hospital = hospital;
	}

	public String getProvinceNo() {
		return provinceNo;
	}

	public void setProvinceNo(String provinceNo) {
		this.provinceNo = provinceNo;
	}

	public String getCityNo() {
		return cityNo;
	}

	public void setCityNo(String cityNo) {
		this.cityNo = cityNo;
	}
	
	

}