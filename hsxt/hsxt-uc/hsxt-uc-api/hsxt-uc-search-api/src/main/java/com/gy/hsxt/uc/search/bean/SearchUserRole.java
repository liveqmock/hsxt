/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.search.bean;

import java.io.Serializable;

import org.apache.solr.client.solrj.beans.Field;

/**
 * 
 * 搜索基类，包含分页信息，所有搜索条件和搜索结果对象都需继承该对象
 * 
 * @Package: com.gy.hsxt.uc.search.bean
 * @ClassName: SearchUserRole 
 * @Description: TODO
 *
 * @author: lixuan 
 * @date: 2016-04-09 上午10:11:34 
 * @version V1.0
 */
public class SearchUserRole  extends Search implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -287684950208639158L;
	public final static String CORE_NAME = "userRole";
	/** ID， 规则为custId + roleId */
	@Field("id")
	private String id;
	/**
	 * 角色id
	 */
	@Field("roleId")
	private String roleId;
	/**
	 * 角色名称
	 */
	@Field("roleName")
	private String roleName;
	/**
	 * 角色说明
	 */
	@Field("roleDesc")
	private String roleDesc;
	
	/**
	 * 用户客户号
	 */
	@Field("custId")
	private String custId;
	/** 角色类型 */
	@Field("roleType")
	private String roleType;

	/**
	 * @return the ID
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param ID the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return 角色类型
	 */
	public String getRoleType() {
		return roleType;
	}
	/**
	 * @param 角色类型
	 */
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	/**
	 * @return 角色Id
	 */
	public String getRoleId() {
		return roleId;
	}
	/**
	 * @param 角色Id
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	/**
	 * @return 角色名称
	 */
	public String getRoleName() {
		return roleName;
	}
	/**
	 * @param 角色名称
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	/**
	 * @return 角色说明
	 */
	public String getRoleDesc() {
		return roleDesc;
	}
	/**
	 * @param 角色说明
	 */
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	/**
	 * @return 用户客户号
	 */
	public String getCustId() {
		return custId;
	}
	/**
	 * @param 用户客户号
	 */
	public void setCustId(String custId) {
		this.custId = custId;
	}
	
}
