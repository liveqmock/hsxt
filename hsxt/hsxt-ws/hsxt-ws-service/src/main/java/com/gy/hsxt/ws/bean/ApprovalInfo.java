package com.gy.hsxt.ws.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.ws.common.WsTools;

/**
 * 审批记录 实体
 * 
 * @Package: com.gy.hsxt.ws.bean
 * @ClassName: ApprovalInfo
 * @Description: TODO
 * 
 * @author: huanggy
 * @date: 2015-12-24 下午4:53:00
 * @version V1.0
 */
public class ApprovalInfo implements Serializable {
	/** 审批ID */
	private String approvalId;

	/** 工单编号 关联工单表 T_WS_TASK */
	private String taskId;

	/** 申请流水号 关联表 T_WS_APPLY_WELFARE */
	private String applyWelfareNo;

	/** 审批步骤 0：初审 1：复审 **/
	private Integer approvalStep;

	/** 审批批复金额 */
	private BigDecimal approvalAmount;

	/** 审批状态 0 待审批 1 通过 2 不通过驳回 */
	private Integer approvalStatus;

	/** 审批说明 */
	private String approvalReason;

	/** 审批人 */
	private String approver;

	/** 审批人客户号 */
	private String approverCustId;

	/** 审批日期 */
	private Timestamp approvalDate;

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

	/** 保障单余额 **/
	private BigDecimal subsidyBalance;

	// ------- 下面字段只供查询显示
	/** 积分福利类别 **/
	private Integer welfareType;
	/** 申请日期 **/
	private String applyDate;
	/** 申请人姓名 **/
	private String proposerName;
	/** 申请人电话 **/
	private String proposerPhone;
	/** 申请人互生号 **/
	private String proposerResNo;
	/** 保障单号 **/
	private String welfareId;

	/** 状态日期 */
	private String statusDate;

	private static final long serialVersionUID = 1L;

	public String getApprovalId() {
		return approvalId;
	}

	public void setApprovalId(String approvalId) {
		this.approvalId = approvalId == null ? null : approvalId.trim();
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId == null ? null : taskId.trim();
	}

	public String getApplyWelfareNo() {
		return applyWelfareNo;
	}

	public Integer getApprovalStep() {
		return approvalStep;
	}

	public void setApprovalStep(Integer approvalStep) {
		this.approvalStep = approvalStep;
	}

	public void setApplyWelfareNo(String applyWelfareNo) {
		this.applyWelfareNo = applyWelfareNo == null ? null : applyWelfareNo.trim();
	}

	public BigDecimal getApprovalAmount() {
		return approvalAmount;
	}

	public void setApprovalAmount(BigDecimal approvalAmount) {
		this.approvalAmount = approvalAmount;
	}

	public Integer getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(Integer approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getApprovalReason() {
		return approvalReason;
	}

	public void setApprovalReason(String approvalReason) {
		this.approvalReason = approvalReason == null ? null : approvalReason.trim();
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver == null ? null : approver.trim();
	}

	public String getApproverCustId() {
		return approverCustId;
	}

	public void setApproverCustId(String approverCustId) {
		this.approverCustId = approverCustId == null ? null : approverCustId.trim();
	}

	public Timestamp getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Timestamp approvalDate) {
		this.approvalDate = approvalDate;
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

	public Integer getWelfareType() {
		return welfareType;
	}

	public void setWelfareType(Integer welfareType) {
		this.welfareType = welfareType;
	}

	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public String getProposerName() {
		return proposerName;
	}

	public void setProposerName(String proposerName) {
		this.proposerName = proposerName;
	}

	public String getProposerPhone() {
		return proposerPhone;
	}

	public void setProposerPhone(String proposerPhone) {
		this.proposerPhone = proposerPhone;
	}

	public String getProposerResNo() {
		return proposerResNo;
	}

	public void setProposerResNo(String proposerResNo) {
		this.proposerResNo = proposerResNo;
	}

	public String getWelfareId() {
		return welfareId;
	}

	public void setWelfareId(String welfareId) {
		this.welfareId = welfareId;
	}

	public BigDecimal getSubsidyBalance() {
		return subsidyBalance;
	}

	public void setSubsidyBalance(BigDecimal subsidyBalance) {
		this.subsidyBalance = subsidyBalance;
	}

	public String getStatusDate() {
		return statusDate;
	}

	public void setStatusDate(String statusDate) {
		this.statusDate = statusDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static ApprovalInfo buildApprovalRecord() {
		ApprovalInfo approvlRecord = new ApprovalInfo();
		approvlRecord.setApprovalId(WsTools.getGUID());
		approvlRecord.setApprovalStatus(0);
		return approvlRecord;
	}

	public ApprovalRecord generateApprovalRecord() {
		ApprovalRecord record = new ApprovalRecord();
		BeanUtils.copyProperties(this, record);
		if (this.approvalDate != null) {
			record.setApprovalDate(DateUtil.DateTimeToString(new Date(this.approvalDate.getTime())));
		}
		if (this.approvalAmount != null) {
			record.setApprovalAmount(this.approvalAmount.toString());
		}
		if (this.subsidyBalance != null) {
			record.setSubsidyBalance(this.subsidyBalance.toString());
		}
		return record;
	}
}