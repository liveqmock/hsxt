package com.gy.hsxt.bs.bean.base;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
/**
 * 
 * @className:BaseBean
 * @author:likui
 * @date:2015年8月27日
 * @desc:基础Bean
 * @company:gyist
 */
public abstract class Base implements Serializable {

	private static final long serialVersionUID = 6465176672821210342L;
	
	/**
	 * 记录有效标识 Y:有效 N:无效
	 */
	private String isActive;
	/**
	 * 创建日期
	 */
	private String createdDate;
	/**
	 * 创建者
	 */
	private String createdBy;

	/**
	 * 创建者名称
	 */
	private String createdName;
	/**
	 * 修改日期
	 */
	private String updatedDate;
	/**
	 * 修改者
	 */
	private String updatedBy;

	/**
	 * 修改者名称
	 */
	private String updatedName;
	
	
	public String getIsActive() {
		return isActive;
	}
	
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	public String getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public String getUpdatedDate() {
		return updatedDate;
	}
	
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	public String getUpdatedBy() {
		return updatedBy;
	}
	
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getCreatedName() {
		return createdName;
	}

	public void setCreatedName(String createdName) {
		this.createdName = createdName;
	}

	public String getUpdatedName() {
		return updatedName;
	}

	public void setUpdatedName(String updatedName) {
		this.updatedName = updatedName;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
