/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/

package com.gy.hsxt.bp.bean;

import java.io.Serializable;

/** 
 * 业务操作许可设置
 * @Package: com.gy.hsxt.bp.bean  
 * @ClassName: BusinessSysBoSetting 
 * @Description: TODO
 *
 * @author: maocan 
 * @date: 2016-2-24 下午3:05:48 
 * @version V1.0 
 */
public class BusinessSysBoSetting implements Serializable{

    
	private static final long serialVersionUID = 1319739802969208106L;
	
	/** 业务操作许可设置主键 **/
	private String	    sysBoSettingNo;
	
	/** 客户ID **/
    private String     custId;
    
    /** 设置项 **/
    private String     settingName;
    
    /** 设置值 0默认 1禁止 **/
    private String     settingValue;
    
    /** 设置原因  **/
    private String     settingRemark;
    
    /** 标记此条记录的状态Y:可用 N:不可用  **/
    private String		isActive;
    
    /** 创建者 **/
    private String     createdby;
    
    /** 创建时间 **/
    private String     createdDate;
       
    /** 更新者 **/
    private String     updatedby;
    
    /** 更新时间**/
    private String     updatedDate;

	/**
	 * @return the 业务操作许可设置主键
	 */
	public String getSysBoSettingNo() {
		return sysBoSettingNo;
	}

	/**
	 * @param 业务操作许可设置主键 the sysBoSettingNo to set
	 */
	public void setSysBoSettingNo(String sysBoSettingNo) {
		this.sysBoSettingNo = sysBoSettingNo;
	}

	/**
	 * @return the 客户ID
	 */
	public String getCustId() {
		return custId;
	}

	/**
	 * @param 客户ID the custId to set
	 */
	public void setCustId(String custId) {
		this.custId = custId;
	}

	/**
	 * @return the 设置项
	 */
	public String getSettingName() {
		return settingName;
	}

	/**
	 * @param 设置项 the settingName to set
	 */
	public void setSettingName(String settingName) {
		this.settingName = settingName;
	}

	/**
	 * @return the 设置值0默认1禁止
	 */
	public String getSettingValue() {
		return settingValue;
	}

	/**
	 * @param 设置值0默认1禁止 the settingValue to set
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
	 * @return the 标记此条记录的状态Y:可用N:不可用
	 */
	public String getIsActive() {
		return isActive;
	}

	/**
	 * @param 标记此条记录的状态Y:可用N:不可用 the isactive to set
	 */
	public void setIsActive(String isActive) {
		this.isActive = isActive;
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
	 * @return the 创建时间
	 */
	public String getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param 创建时间 the createdDate to set
	 */
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the 更新者
	 */
	public String getUpdatedby() {
		return updatedby;
	}

	/**
	 * @param 更新者 the updatedby to set
	 */
	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

	/**
	 * @return the 更新时间
	 */
	public String getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @param 更新时间 the updatedDate to set
	 */
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
    
}
