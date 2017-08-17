package com.gy.hsxt.uc.as.bean.common;

import java.io.Serializable;
import java.sql.Timestamp;

public class AsSysBoSetting  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3369137367662131176L;
	/** 客户号 pk1*/
	private String custId;
	/** 操作名称 pk2 取值参考BoNameEnum*/
    private String settingName;

	/** 操作设置值*/  
    private String settingValue;
	/** 设置原因*/
    private String settingRemark;
	/** 是否有效*/
    private String isactive;
	/** 创建日期*/
    private Timestamp createDate;
	/** 创建者*/
    private String createdby;
	/** 修改日期*/
    private Timestamp updateDate;
	/** 修改者*/
    private String updatedby;
	/**
	 * @return the 客户号
	 */
	public String getCustId() {
		return custId;
	}
	/**
	 * @param 客户号 the custId to set
	 */
	public void setCustId(String custId) {
		this.custId = custId;
	}
	/**
	 * @return the 属性名称
	 */
	public String getSettingName() {
		return settingName;
	}
	/** 取值参考BoNameEnum
	 * @param 属性名称 the settingName to set
	 */
	public void setSettingName(String settingName) {
		this.settingName = settingName;
	}
	/**
	 * @return the 属性值
	 */
	public String getSettingValue() {
		return settingValue;
	}
	/**
	 * @param 属性值 the settingValue to set
	 */
	public void setSettingValue(String settingValue) {
		this.settingValue = settingValue;
	}
	/**
	 * @return the 设置原因
	 */
	public String getSettingRemark() {
		return settingRemark;
	}
	/**
	 * @param 设置原因 the settingRemark to set
	 */
	public void setSettingRemark(String settingRemark) {
		this.settingRemark = settingRemark;
	}
	/**
	 * @return the 是否有效
	 */
	public String getIsactive() {
		return isactive;
	}
	/**
	 * @param 是否有效 the isactive to set
	 */
	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}
	/**
	 * @return the 创建日期
	 */
	public Timestamp getCreateDate() {
		return createDate;
	}
	/**
	 * @param 创建日期 the createDate to set
	 */
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return the 创建者
	 */
	public String getCreatedby() {
		return createdby;
	}
	/**
	 * @param 创建者 the createdby to set
	 */
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	/**
	 * @return the 修改日期
	 */
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	/**
	 * @param 修改日期 the updateDate to set
	 */
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * @return the 修改者
	 */
	public String getUpdatedby() {
		return updatedby;
	}
	/**
	 * @param 修改者 the updatedby to set
	 */
	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

}