package com.gy.hsxt.ws.bean;

import static com.gy.hsxt.common.utils.StringUtils.isNotBlank;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import org.springframework.beans.BeanUtils;

import com.gy.hsxt.ws.common.WsTools;

/**
 * 理赔核算单 实体
 * 
 * @Package: com.gy.hsxt.ws.bean
 * @ClassName: ClaimsAccountingInfo
 * @Description: TODO
 * 
 * @author: huanggy
 * @date: 2015-12-24 下午4:53:16
 * @version V1.0
 */
public class ClaimsAccountingInfo implements Serializable {
	/** 理赔核算单编号（主键） */
	private String accountingId;

	/** 申请单编号 */
	private String applyWelfareNo;

	/** 工单编号 */
	private String taskId;

	/** 医保支付金额 */
	private BigDecimal healthPayAmount;

	/** 个人支付金额 */
	private BigDecimal personalPayAmount;

	/** 互生支付金额（核算金额） */
	private BigDecimal hsPayAmount;

	/** 核算人姓名 */
	private String accountPersonName;

	/** 核算人客户号 */
	private String accountPersonCustId;

	/** 核算日期 */
	private Timestamp accountDate;

	/** 核算单状态 0 待核算、1 核算中、2 已核算 */
	private Integer status;

	/** 标记此条记录的状态Y:可用 N:不可用' */
	private String isactive;

	/** 创建时间，取记录创建时的系统时间 */
	private Timestamp createDate;

	/** 创建人由谁创建，值为用户的伪键ID */
	private String createdby;

	/** 更新时间，取记录更新时的系统时间' */
	private Timestamp updateDate;

	/** 更新人由谁更新，值为用户的伪键ID */
	private String updatedby;

	private static final long serialVersionUID = 1L;

	// ---显示字段（只做数据查询，不做插入）----
	/** 申请人互生号 **/
	private String hsResNo;

	/** 申请人姓名 **/
	private String proposerName;

	/** 福利资格ID */
	private String welfareId;

	/** 账单开始日期 */
	private String billsStartDate;

	/** 账单结束日期 */
	private String billsEndDate;

	/** 账单发生区域 */
	private String billsArea;

	/** 账单发生医院 */
	private String hospital;

	/** 账单发生区域 省编码 */
	private String provinceNo;

	/** 账单发生区域 城市 */
	private String cityNo;

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

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId == null ? null : taskId.trim();
	}

	public BigDecimal getHealthPayAmount() {
		return healthPayAmount;
	}

	public void setHealthPayAmount(BigDecimal healthPayAmount) {
		this.healthPayAmount = healthPayAmount;
	}

	public BigDecimal getPersonalPayAmount() {
		return personalPayAmount;
	}

	public void setPersonalPayAmount(BigDecimal personalPayAmount) {
		this.personalPayAmount = personalPayAmount;
	}

	public BigDecimal getHsPayAmount() {
		return hsPayAmount;
	}

	public void setHsPayAmount(BigDecimal hsPayAmount) {
		this.hsPayAmount = hsPayAmount;
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

	public Timestamp getAccountDate() {
		return accountDate;
	}

	public void setAccountDate(Timestamp accountDate) {
		this.accountDate = accountDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getIsactive() {
		return isactive;
	}

	public void setIsactive(String isactive) {
		this.isactive = isactive == null ? null : isactive.trim();
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby == null ? null : createdby.trim();
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby == null ? null : updatedby.trim();
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

	public static ClaimsAccountingInfo buildClaimsAccountingInfo() {
		ClaimsAccountingInfo info = new ClaimsAccountingInfo();
		info.setAccountingId(WsTools.getGUID());
		return info;
	}

	public static ClaimsAccountingInfo buildClaimsAccountingInfo(ClaimsAccountingRecord record) {
		ClaimsAccountingInfo info = new ClaimsAccountingInfo();
		BeanUtils.copyProperties(record, info);
		if (isNotBlank(record.getHealthPayAmount())) {
			info.setHealthPayAmount(new BigDecimal(record.getHealthPayAmount()));
		}
		if (isNotBlank(record.getPersonalPayAmount())) {
			info.setPersonalPayAmount(new BigDecimal(record.getPersonalPayAmount()));
		}
		if (isNotBlank(record.getHsPayAmount())) {
			info.setHsPayAmount(new BigDecimal(record.getHsPayAmount()));
		}
		return info;
	}

	public ClaimsAccountingRecord generateClaimsAccountingRecord() {
		ClaimsAccountingRecord record = new ClaimsAccountingRecord();
		BeanUtils.copyProperties(this, record);
		if (this.hsPayAmount != null) {
			record.setHsPayAmount(hsPayAmount.toString());
		}
		if (this.personalPayAmount != null) {
			record.setPersonalPayAmount(personalPayAmount.toString());
		}
		if (this.healthPayAmount != null) {
			record.setHealthPayAmount(healthPayAmount.toString());
		}
		return record;
	}

}