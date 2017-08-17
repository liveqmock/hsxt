package com.gy.hsxt.access.pos.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * *************************************************************************
 * 
 *  Project Name    : point-common
 * 
 *  Package Name    : com.gy.point.common.pojo
 * 
 *  File Name       : BaseModel.java
 * 
 *  Creation Date   : 2014-11-6
 * 
 *  Author          : huangfuhua
 * 
 *  Purpose         : TODO
 * 
 *  History         : TODO
 * 
 **************************************************************************
 */
public class BaseModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** ISACTIVE 是否可用,Y:可用、N:不可用 */
	protected String isActive = "Y";	
	/** 创建日期 */
	protected Date created;
	/** 创建人 */
	protected String createdBy = "BOOTUP";
	/** 更新日期 */
	protected Timestamp updated;
	/** 更新人 */
	protected String updatedBy = "BOOTUP";
	
	public BaseModel(){}
	
	public BaseModel(String isActive,Date created,String createdBy,
			Timestamp updated,String updatedBy){
		this.isActive = isActive;
		this.created = created;
		this.createdBy = createdBy;
		this.updated = updated;
		this.updatedBy = updatedBy;
	}
	
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Timestamp getUpdated() {
		return updated;
	}
	public void setUpdated(Timestamp updated) {
		this.updated = updated;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}