package com.gy.hsxt.ws.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.ws.common.WsTools;

/**
 * 福利发放记录 实体
 * 
 * @Package: com.gy.hsxt.ws.bean
 * @ClassName: GrantInfo
 * @Description: TODO
 * 
 * @author: huanggy
 * @date: 2015-12-24 下午4:54:02
 * @version V1.0
 */
public class GrantInfo implements Serializable {
	/** 福利发放主键ID */
	private String givingId;

	/** 申请流水号 关联表 T_WS_APPLY_WELFARE */
	private String applyWelfareNo;

	/** 补贴总金额 */
	private BigDecimal subsidyTotalAmount;

	/** 互生支付金额(审批,批复,发放金额) */
	private BigDecimal hsPayAmount;

	/** 补贴余额 */
	private BigDecimal subsidyBalance;

	/** 保障单号 */
	private String welfareNo;

	/** 保单开始日期 */
	private Timestamp startDate;

	/** 保单结束日期 */
	private Timestamp endDate;

	/** 批准时间 */
	private Timestamp approvalDate;

	/** 交易流水号 */
	private String transNo;

	/** 是否发放 0 未发放 1已发放 */
	private Integer ifgiving;

	/** 发放补贴说明 */
	private String description;

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

	/** 发放工单编号 */
	private String taskId;

	/** 发放人姓名 */
	private String givingPersonName;

	/** 发放人客户号 */
	private String givingPersonCustId;

	private static final long serialVersionUID = 1L;

	// -----------只做数据分页查询时使用
	/** 互生号 **/
	private String hsResNo;
	/** 客户ID **/
	private String custId;
	/** 申请人姓名 **/
	private String proposerName;
	/** 申请人电话 */
	private String proposerPhone;
	/** 申请人证件号码 **/
	private String proposerPapersNo;
	/** 福利类型 **/
	private Integer welfareType;

	public String getProposerPhone() {
		return proposerPhone;
	}

	public void setProposerPhone(String proposerPhone) {
		this.proposerPhone = proposerPhone;
	}

	public String getHsResNo() {
		return hsResNo;
	}

	public void setHsResNo(String hsResNo) {
		this.hsResNo = hsResNo;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getProposerName() {
		return proposerName;
	}

	public void setProposerName(String proposerName) {
		this.proposerName = proposerName;
	}

	public String getProposerPapersNo() {
		return proposerPapersNo;
	}

	public void setProposerPapersNo(String proposerPapersNo) {
		this.proposerPapersNo = proposerPapersNo;
	}

	public Integer getWelfareType() {
		return welfareType;
	}

	public void setWelfareType(Integer welfareType) {
		this.welfareType = welfareType;
	}

	public String getGivingId() {
		return givingId;
	}

	public void setGivingId(String givingId) {
		this.givingId = givingId == null ? null : givingId.trim();
	}

	public String getApplyWelfareNo() {
		return applyWelfareNo;
	}

	public void setApplyWelfareNo(String applyWelfareNo) {
		this.applyWelfareNo = applyWelfareNo == null ? null : applyWelfareNo.trim();
	}

	public BigDecimal getSubsidyTotalAmount() {
		return subsidyTotalAmount;
	}

	public void setSubsidyTotalAmount(BigDecimal subsidyTotalAmount) {
		this.subsidyTotalAmount = subsidyTotalAmount;
	}

	public BigDecimal getHsPayAmount() {
		return hsPayAmount;
	}

	public void setHsPayAmount(BigDecimal hsPayAmount) {
		this.hsPayAmount = hsPayAmount;
	}

	public BigDecimal getSubsidyBalance() {
		return subsidyBalance;
	}

	public void setSubsidyBalance(BigDecimal subsidyBalance) {
		this.subsidyBalance = subsidyBalance;
	}

	public String getWelfareNo() {
		return welfareNo;
	}

	public void setWelfareNo(String welfareNo) {
		this.welfareNo = welfareNo == null ? null : welfareNo.trim();
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public Timestamp getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Timestamp approvalDate) {
		this.approvalDate = approvalDate;
	}

	public String getTransNo() {
		return transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo == null ? null : transNo.trim();
	}

	public Integer getIfgiving() {
		return ifgiving;
	}

	public void setIfgiving(Integer ifgiving) {
		this.ifgiving = ifgiving;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
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

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId == null ? null : taskId.trim();
	}

	public String getGivingPersonName() {
		return givingPersonName;
	}

	public void setGivingPersonName(String givingPersonName) {
		this.givingPersonName = givingPersonName == null ? null : givingPersonName.trim();
	}

	public String getGivingPersonCustId() {
		return givingPersonCustId;
	}

	public void setGivingPersonCustId(String givingPersonCustId) {
		this.givingPersonCustId = givingPersonCustId == null ? null : givingPersonCustId.trim();
	}

	public static GrantInfo buildDefaultGrantInfo() {
		GrantInfo grantInfo = new GrantInfo();
		grantInfo.setGivingId(WsTools.getGUID());
		grantInfo.setIfgiving(0);
		return grantInfo;
	}

	public GrantRecord generateGrantRecord() {
		GrantRecord grantRecord = new GrantRecord();
		BeanUtils.copyProperties(this, grantRecord);
		if (this.hsPayAmount != null) {
			grantRecord.setHsPayAmount(this.hsPayAmount.toString());
		}
		if (this.subsidyBalance != null) {
			grantRecord.setSubsidyBalance(this.subsidyBalance.toString());
		}
		if (this.approvalDate != null) {
			grantRecord
					.setApprovalDate(DateUtil.DateTimeToString(new Date(approvalDate.getTime())));
		}
		return grantRecord;
	}

}