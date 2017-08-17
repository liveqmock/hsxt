package com.gy.hsxt.ws.bean;

import static com.gy.hsxt.common.utils.StringUtils.isBlank;
import static com.gy.hsxt.common.utils.StringUtils.isNotBlank;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import org.springframework.beans.BeanUtils;

import com.gy.hsxt.ws.common.WsTools;

/**
 * 医疗明细 实体
 * 
 * @Package: com.gy.hsxt.ws.bean
 * @ClassName: MedicalDetailInfo
 * @Description: TODO
 * 
 * @author: huanggy
 * @date: 2015-12-24 下午4:54:24
 * @version V1.0
 */
public class MedicalDetailInfo implements Serializable {
	/** 医疗明细ID */
	private String medicalId;

	/** 申请流水号 关联表 T_WS_APPLY_WELFARE */
	private String applyWelfareNo;

	/** 类别 */
	private String category;

	/** 项目名称 */
	private String itemName;

	/** 规格 */
	private String standard;

	/** 数量 */
	private Integer quantity;

	/** 单位 */
	private Integer unit;

	/** 单价 */
	private BigDecimal price;

	/** 金额 */
	private BigDecimal amount;

	/** 比例 */
	private BigDecimal proportion;

	/** 医保支付金额 */
	private BigDecimal healthPayAmount;

	/** 个人支付金额 */
	private BigDecimal personalPayAmount;

	/** 互生支付金额 */
	private BigDecimal hsPayAmount;

	/** 说明 */
	private String explain;

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

	/** 理赔核算单编号 */
	private String accountingId;

	private static final long serialVersionUID = 1L;

	public String getMedicalId() {
		return medicalId;
	}

	public void setMedicalId(String medicalId) {
		this.medicalId = medicalId == null ? null : medicalId.trim();
	}

	public String getApplyWelfareNo() {
		return applyWelfareNo;
	}

	public void setApplyWelfareNo(String applyWelfareNo) {
		this.applyWelfareNo = applyWelfareNo == null ? null : applyWelfareNo.trim();
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category == null ? null : category.trim();
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName == null ? null : itemName.trim();
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard == null ? null : standard.trim();
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getUnit() {
		return unit;
	}

	public void setUnit(Integer unit) {
		this.unit = unit;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getProportion() {
		return proportion;
	}

	public void setProportion(BigDecimal proportion) {
		this.proportion = proportion;
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

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain == null ? null : explain.trim();
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

	public String getAccountingId() {
		return accountingId;
	}

	public void setAccountingId(String accountingId) {
		this.accountingId = accountingId == null ? null : accountingId.trim();
	}

	public static MedicalDetailInfo buildMedicalDetailInfo(MedicalDetail medicalDetail) {
		MedicalDetailInfo detailInfo = new MedicalDetailInfo();
		BeanUtils.copyProperties(medicalDetail, detailInfo);
		if (isNotBlank(medicalDetail.getPrice())) {
			detailInfo.setPrice(new BigDecimal(medicalDetail.getPrice()));
		}
		if (isNotBlank(medicalDetail.getProportion())) {
			detailInfo.setProportion(new BigDecimal(medicalDetail.getProportion()));
		}
		if (isNotBlank(medicalDetail.getAmount())) {
			detailInfo.setAmount(new BigDecimal(medicalDetail.getAmount()));
		}
		if (isNotBlank(medicalDetail.getHealthPayAmount())) {
			detailInfo.setHealthPayAmount(new BigDecimal(medicalDetail.getHealthPayAmount()));
		}
		if (isNotBlank(medicalDetail.getHsPayAmount())) {
			detailInfo.setHsPayAmount(new BigDecimal(medicalDetail.getHsPayAmount()));
		}
		if (isNotBlank(medicalDetail.getPersonalPayAmount())) {
			detailInfo.setPersonalPayAmount(new BigDecimal(medicalDetail.getPersonalPayAmount()));
		}
		if (isBlank(medicalDetail.getMedicalId())) {
			detailInfo.setMedicalId(WsTools.getGUID());
		}
		return detailInfo;
	}

	public MedicalDetail generateMedicalDetail() {
		MedicalDetail medicalDetail = new MedicalDetail();
		BeanUtils.copyProperties(this, medicalDetail);
		if (this.price != null) {
			medicalDetail.setPrice(this.price.toString());
		}
		if (this.proportion != null) {
			medicalDetail.setProportion(this.proportion.toString());
		}
		if (this.personalPayAmount != null) {
			medicalDetail.setPersonalPayAmount(this.personalPayAmount.toString());
		}
		if (this.healthPayAmount != null) {
			medicalDetail.setHealthPayAmount(this.healthPayAmount.toString());
		}
		if (this.hsPayAmount != null) {
			medicalDetail.setHsPayAmount(this.hsPayAmount.toString());
		}
		if (this.amount != null) {
			medicalDetail.setAmount(this.amount.toString());
		}
		return medicalDetail;
	}

}