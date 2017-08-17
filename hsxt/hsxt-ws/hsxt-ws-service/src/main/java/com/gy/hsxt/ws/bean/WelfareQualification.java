package com.gy.hsxt.ws.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.gy.hsxt.common.utils.DateUtil;

/**
 * 福利资格 实体
 * 
 * @Package: com.gy.hsxt.ws.bean
 * @ClassName: WelfareQualification
 * @Description: TODO
 * 
 * @author: huanggy
 * @date: 2015-12-24 下午4:55:08
 * @version V1.0
 */
public class WelfareQualification implements Serializable {
	/** 福利资格ID */
	private String welfareId;

	/** 客户号 */
	private String custId;

	/** 互生号 */
	private String hsResNo;

	/** 消费者姓名 */
	private String custName;

	/** 补贴总金额 */
	private BigDecimal subsidyTotalAmount;

	/** 已发放的金额 */
	private BigDecimal grantAmount;

	/** 补贴余额 */
	private BigDecimal subsidyBalance;

	/** 福利分类 0 一年意外保障 1免费医疗补贴 */
	private Integer welfareType;

	/** 福利资格是否有效 0 无效 1 有效 */
	private Integer isvalid;

	/** 福利生效日期 */
	private Timestamp effectDate;

	/** 福利失效日期 */
	private Timestamp failureDate;

	/** 失效原因 1正常过期失效 2 保障升级而失效 3 持续7天积分不足而失效 4 其他原因失效 */
	private Integer failureReason;

	/** 福利持续失效累积天数 */
	private Integer durInvalidDays;

	/** 所有失效日期UUID 关联表 T_WS_FAILURE 失效记录ID */
	private String allFailureDate;

	private List<String> allFailureDateList;

	/** 最后一次上报为失效的日期 */
	private Timestamp lastInvalidDate;

	/** 创建时间 */
	private Timestamp createdDate;

	/** 最后一次更新时间 */
	private Timestamp updatedDate;

	/** 累计消费积分 */
	private String consumePoint;

	/** 累计投资积分 */
	private String investPoint;

	/** 福利资格生效时累计积分：一年意外保障为消费累计积分数，免费医疗补贴为投资累计积分数 */
	private String effectPoint;

	private static final long serialVersionUID = 1L;

	public String getWelfareId() {
		return welfareId;
	}

	public void setWelfareId(String welfareId) {
		this.welfareId = welfareId == null ? null : welfareId.trim();
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

	public BigDecimal getSubsidyTotalAmount() {
		return subsidyTotalAmount;
	}

	public void setSubsidyTotalAmount(BigDecimal subsidyTotalAmount) {
		this.subsidyTotalAmount = subsidyTotalAmount;
	}

	public BigDecimal getGrantAmount() {
		return grantAmount;
	}

	public void setGrantAmount(BigDecimal grantAmount) {
		this.grantAmount = grantAmount;
	}

	public BigDecimal getSubsidyBalance() {
		return subsidyBalance;
	}

	public void setSubsidyBalance(BigDecimal subsidyBalance) {
		this.subsidyBalance = subsidyBalance;
	}

	public Integer getWelfareType() {
		return welfareType;
	}

	public void setWelfareType(Integer welfareType) {
		this.welfareType = welfareType;
	}

	public Integer getIsvalid() {
		return isvalid;
	}

	public void setIsvalid(Integer isvalid) {
		this.isvalid = isvalid;
	}

	public Timestamp getEffectDate() {
		return effectDate;
	}

	public void setEffectDate(Timestamp effectDate) {
		this.effectDate = effectDate;
	}

	public Timestamp getFailureDate() {
		return failureDate;
	}

	public void setFailureDate(Timestamp failureDate) {
		this.failureDate = failureDate;
	}

	public Integer getFailureReason() {
		return failureReason;
	}

	public void setFailureReason(Integer failureReason) {
		this.failureReason = failureReason;
	}

	public Integer getDurInvalidDays() {
		return durInvalidDays;
	}

	public void setDurInvalidDays(Integer durInvalidDays) {
		this.durInvalidDays = durInvalidDays;
	}

	public String getAllFailureDate() {
		return allFailureDate;
	}

	public void setAllFailureDate(String allFailureDate) {
		this.allFailureDate = allFailureDate == null ? null : allFailureDate.trim();
	}

	public Timestamp getLastInvalidDate() {
		return lastInvalidDate;
	}

	public void setLastInvalidDate(Timestamp lastInvalidDate) {
		this.lastInvalidDate = lastInvalidDate;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
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

	public List<String> getAllFailureDateList() {
		return allFailureDateList;
	}

	public void setAllFailureDateList(List<String> allFailureDateList) {
		this.allFailureDateList = allFailureDateList;
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

	public String getConsumePoint() {
		return consumePoint;
	}

	public void setConsumePoint(String consumePoint) {
		this.consumePoint = consumePoint;
	}

	public String getInvestPoint() {
		return investPoint;
	}

	public void setInvestPoint(String investPoint) {
		this.investPoint = investPoint;
	}

	public String getEffectPoint() {
		return effectPoint;
	}

	public void setEffectPoint(String effectPoint) {
		this.effectPoint = effectPoint;
	}

	public WelfareQualify generateWelfareQualify() {
		WelfareQualify welfareQualify = new WelfareQualify();
		BeanUtils.copyProperties(this, welfareQualify);
		if (this.subsidyTotalAmount != null) {
			welfareQualify.setSubsidyTotalAmount(this.subsidyTotalAmount.toString());
		}
		if (this.grantAmount != null) {
			welfareQualify.setGrantAmount(this.grantAmount.toString());
		}
		if (this.subsidyBalance != null) {
			welfareQualify.setSubsidyBalance(this.subsidyBalance.toString());
		}
		if (this.effectDate != null) {
			welfareQualify.setEffectDate(DateUtil.DateTimeToString(this.effectDate));
		}
		if (this.failureDate != null) {
			welfareQualify.setFailureDate(DateUtil.DateTimeToString(failureDate));
		}
		if (this.lastInvalidDate != null) {
			welfareQualify.setLastInvalidDate(DateUtil.DateTimeToString(lastInvalidDate));
		}
		return welfareQualify;
	}

}