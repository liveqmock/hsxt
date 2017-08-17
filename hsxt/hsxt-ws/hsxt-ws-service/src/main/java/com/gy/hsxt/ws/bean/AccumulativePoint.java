package com.gy.hsxt.ws.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 累计积分实体
 * 
 * @Package: com.gy.hsxt.ws.bean
 * @ClassName: AccumulativePoint
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-12-22 下午6:26:48
 * @version V1.0
 */
public class AccumulativePoint implements Serializable {
	/** 客户号（主键） */
	private String custId;

	/** 互生号 */
	private String hsResNo;

	/** 消费者姓名 */
	private String custName;

	/** 消费累计积分 */
	private BigDecimal consumePoint;

	/** 投资累计积分 */
	private BigDecimal investPoint;

	/** 记录创建时间 */
	private Timestamp createdDate;

	/** 记录更新时间 */
	private Timestamp updatedDate;
	
	/** 累计日期*/
	private String accountingDate;

	private static final long serialVersionUID = 1L;

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

	public BigDecimal getConsumePoint() {
		return consumePoint;
	}

	public void setConsumePoint(BigDecimal consumePoint) {
		this.consumePoint = consumePoint;
	}

	public BigDecimal getInvestPoint() {
		return investPoint;
	}

	public void setInvestPoint(BigDecimal investPoint) {
		this.investPoint = investPoint;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getAccountingDate() {
		return accountingDate;
	}

	public void setAccountingDate(String accountingDate) {
		this.accountingDate = accountingDate;
	}
	
	

}