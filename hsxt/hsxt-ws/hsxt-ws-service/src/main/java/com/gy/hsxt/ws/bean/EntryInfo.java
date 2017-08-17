package com.gy.hsxt.ws.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import org.springframework.beans.BeanUtils;

import com.gy.hsxt.ac.bean.AccountEntry;
import com.gy.hsxt.common.utils.DateUtil;

/**
 * 发放 分录实体
 * 
 * @Package: com.gy.hsxt.ws.bean
 * @ClassName: EntryInfo
 * @Description: TODO
 * 
 * @author: huanggy
 * @date: 2015-12-24 下午4:53:38
 * @version V1.0
 */
public class EntryInfo implements Serializable {
	private static final String SYS_NAME = "WS";

	/** 分录流水号 */
	private String entryNo;

	/** 客户号 */
	private String custId;

	/** 互生号 */
	private String hsResNo;

	/** 账户类型编码 */
	private String accType;

	/** 增向金额 */
	private BigDecimal addAmount;

	/** 减向金额 */
	private BigDecimal subAmount;

	/** 红冲标识 */
	private String writeBack;

	/** 交易类型 */
	private String transType;

	/** 批次号 */
	private String batchNo;

	/** 积分福利发放编号（业务流水号） */
	private String grantId;

	/** 客户类型 */
	private Integer custType;

	/** 标记此条记录的状态 */
	private String isactive;

	/** 创建时间，取记录创建时的系统时间 */
	private Timestamp createdDate;

	/** 由谁创建，值为用户的伪键ID */
	private String createdBy;

	/** 更新时间，取记录更新时的系统时间 */
	private Timestamp updatedDate;

	/** 由谁更新，值为用户的伪键ID */
	private String updatedBy;

	private static final long serialVersionUID = 1L;

	public String getEntryNo() {
		return entryNo;
	}

	public void setEntryNo(String entryNo) {
		this.entryNo = entryNo == null ? null : entryNo.trim();
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId == null ? null : custId.trim();
	}

	public String getHsResNo() {
		return hsResNo;
	}

	public void setHsResNo(String hsResNo) {
		this.hsResNo = hsResNo == null ? null : hsResNo.trim();
	}

	public String getAccType() {
		return accType;
	}

	public void setAccType(String accType) {
		this.accType = accType == null ? null : accType.trim();
	}

	public BigDecimal getAddAmount() {
		return addAmount;
	}

	public void setAddAmount(BigDecimal addAmount) {
		this.addAmount = addAmount;
	}

	public BigDecimal getSubAmount() {
		return subAmount;
	}

	public void setSubAmount(BigDecimal subAmount) {
		this.subAmount = subAmount;
	}

	public String getWriteBack() {
		return writeBack;
	}

	public void setWriteBack(String writeBack) {
		this.writeBack = writeBack == null ? null : writeBack.trim();
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType == null ? null : transType.trim();
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo == null ? null : batchNo.trim();
	}

	public String getGrantId() {
		return grantId;
	}

	public void setGrantId(String grantId) {
		this.grantId = grantId == null ? null : grantId.trim();
	}

	public Integer getCustType() {
		return custType;
	}

	public void setCustType(Integer custType) {
		this.custType = custType;
	}

	public String getIsactive() {
		return isactive;
	}

	public void setIsactive(String isactive) {
		this.isactive = isactive == null ? null : isactive.trim();
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy == null ? null : createdBy.trim();
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy == null ? null : updatedBy.trim();
	}

	public AccountEntry generateAccountEntry() {
		AccountEntry accountEntry = new AccountEntry();
		BeanUtils.copyProperties(this, accountEntry);
		accountEntry.setCustID(this.custId);
		accountEntry.setSysEntryNo(this.entryNo);
		accountEntry.setTransSys(SYS_NAME);
		accountEntry.setTransNo(this.grantId);
		accountEntry.setWriteBack(0);
		accountEntry.setTransDate(DateUtil.getCurrentDateTime());
		accountEntry.setFiscalDate(DateUtil.getCurrentDateTime());
		if (this.subAmount != null) {
			accountEntry.setSubAmount(this.subAmount.toString());
		}
		if (this.addAmount != null) {
			accountEntry.setAddAmount(this.addAmount.toString());
		}
		return accountEntry;
	}

}