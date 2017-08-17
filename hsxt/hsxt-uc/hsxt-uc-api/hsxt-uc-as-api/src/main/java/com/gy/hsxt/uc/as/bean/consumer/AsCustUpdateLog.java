package com.gy.hsxt.uc.as.bean.consumer;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 消费者信息修改记录
 * 
 * @Package: com.gy.hsxt.uc.as.bean.consumer  
 * @ClassName: AsCustUpdateLog 
 * @Description: TODO
 *
 * @author: lvyan 
 * @date: 2016-3-9 下午12:25:31 
 * @version V1.0
 */
public class AsCustUpdateLog implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2520589344345141243L;

	/** 持卡人客户号 */
    private String perCustId;

    /** 更新字段 */
    private String updateField;
    /** 更新字段名称 */
    private String updateFieldName;
    /** 更新前内容  */
    private String updateValueOld;

    /** 更新后内容  */
    private String updateValueNew;

    /** 更新时间  */
    private Timestamp updateDate;
    
    /** 更新时间  */
    private String updateTime;

    /** 更新人，值为用户ID */
    private String updatedby;

    /** 日志类型:0删除银行账号，1新增银行账号，2修改注册信息， */
    private Integer logType;

    /** 备注 */
    private String logRem;

    /** 审核员ID */
    private String confirmId;

    /** 操作员姓名 */
    private String operName;

    /** 审核员姓名 */
    private String confirmName;

	

	/**
	 * @return the 持卡人客户号
	 */
	public String getPerCustId() {
		return perCustId;
	}

	/**
	 * @param 持卡人客户号 the perCustId to set
	 */
	public void setPerCustId(String perCustId) {
		this.perCustId = perCustId;
	}

	/**
	 * @return the 更新字段
	 */
	public String getUpdateField() {
		return updateField;
	}

	/**
	 * @param 更新字段 the updateField to set
	 */
	public void setUpdateField(String updateField) {
		this.updateField = updateField;
	}

	/**
	 * @return the 更新字段名称
	 */
	public String getUpdateFieldName() {
		return updateFieldName;
	}

	/**
	 * @param 更新字段名称 the updateFieldName to set
	 */
	public void setUpdateFieldName(String updateFieldName) {
		this.updateFieldName = updateFieldName;
	}

	/**
	 * @return the 更新前内容
	 */
	public String getUpdateValueOld() {
		return updateValueOld;
	}

	/**
	 * @param 更新前内容 the updateValueOld to set
	 */
	public void setUpdateValueOld(String updateValueOld) {
		this.updateValueOld = updateValueOld;
	}

	/**
	 * @return the 更新后内容
	 */
	public String getUpdateValueNew() {
		return updateValueNew;
	}

	/**
	 * @param 更新后内容 the updateValueNew to set
	 */
	public void setUpdateValueNew(String updateValueNew) {
		this.updateValueNew = updateValueNew;
	}

	/**
	 * @return the 更新时间
	 */
	public Timestamp getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param 更新时间 the updateDate to set
	 */
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the 更新人，值为用户ID
	 */
	public String getUpdatedby() {
		return updatedby;
	}

	/**
	 * @param 更新人，值为用户ID the updatedby to set
	 */
	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

	/**
	 * @return the 日志类型:0删除银行账号，1新增银行账号，2修改注册信息，
	 */
	public Integer getLogType() {
		return logType;
	}

	/**
	 * @param 日志类型:0删除银行账号，1新增银行账号，2修改注册信息， the logType to set
	 */
	public void setLogType(Integer logType) {
		this.logType = logType;
	}

	/**
	 * @return the 备注
	 */
	public String getLogRem() {
		return logRem;
	}

	/**
	 * @param 备注 the logRem to set
	 */
	public void setLogRem(String logRem) {
		this.logRem = logRem;
	}

	/**
	 * @return the 审核员ID
	 */
	public String getConfirmId() {
		return confirmId;
	}

	/**
	 * @param 审核员ID the confirmId to set
	 */
	public void setConfirmId(String confirmId) {
		this.confirmId = confirmId;
	}

	/**
	 * @return the 操作员姓名
	 */
	public String getOperName() {
		return operName;
	}

	/**
	 * @param 操作员姓名 the operName to set
	 */
	public void setOperName(String operName) {
		this.operName = operName;
	}

	/**
	 * @return the 审核员姓名
	 */
	public String getConfirmName() {
		return confirmName;
	}

	/**
	 * @param 审核员姓名 the confirmName to set
	 */
	public void setConfirmName(String confirmName) {
		this.confirmName = confirmName;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
}