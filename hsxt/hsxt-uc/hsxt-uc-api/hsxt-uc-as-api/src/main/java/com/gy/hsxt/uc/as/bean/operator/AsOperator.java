/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.as.bean.operator;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsxt.uc.as.bean.auth.AsRole;
import com.gy.hsxt.uc.as.bean.common.AsNetworkInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntGroup;

/**
 * 操作员信息
 * 
 * @Package: com.gy.hsxt.uc.as.bean.operator
 * @ClassName: AsOperator
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-10-19 下午4:09:19
 * @version V1.0
 */
public class AsOperator implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 新增不需要填写、系统自动生成 修改、删除是比填 查询时返回 */
	private String operCustId;
	/** 企业客户号 */
	private String entCustId;
	/** 企业互生号 */
	private String entResNo;
	/** 用户名（员工账号） */
	private String userName;
	/** 真实姓名 */
	private String realName;
	/** 职务 */
	private String operDuty;
	/** 初始登录密码 */
	private String loginPwd;
	/** 登录盐值 */
	private String loginPWdSaltValue;
	/** 随机token */
	private String randomToken;
	/** 是否管理员 */
	private boolean isAdmin;
	/** 最后登录时间 */
	private String lastLoginDate;
	/** 最后登录IP */
	private String lastLoginIp;
	/** 描述 */
	private String remark;
	/** 操作员手机 */
	private String mobile;
	/** 操作员邮箱 */
	private String email;
	/** 操作员互生号 非绑定不填 */
	private String operResNo;
	/** 是否绑定互生号 1：绑定 0：未绑定,-1绑定中 */
	private Integer bindResNoStatus;
	/** 角色列表 添加、修改时不需要、查询需要返回角色信息 */
	private List<AsRole> roleList;
	/** 所属企业资源类型?3：托管企业，2 ：成员企业 */
	private String enterpriseResourceType;
	/** 用户组信息 */
	private List<AsEntGroup> groupList;
	/** 操作员状态 0：启用，1：禁用 2:已删除 */
	private Integer accountStatus;
	/** 网络信息 */
	private AsNetworkInfo networkInfo;
	
	/**
	 * @return the 网络信息
	 */
	public AsNetworkInfo getNetworkInfo() {
		return networkInfo;
	}

	/**
	 * @param 网络信息 the networkInfo to set
	 */
	public void setNetworkInfo(AsNetworkInfo networkInfo) {
		this.networkInfo = networkInfo;
	}
	/**
	 * @return the 随机token
	 */
	public String getRandomToken() {
		return randomToken;
	}

	/**
	 * @param 随机token the randomToken to set
	 */
	public void setRandomToken(String randomToken) {
		this.randomToken = randomToken;
	}

	/**
	 * @return the 所属企业资源类型?3：托管企业，2：成员企业
	 */
	public String getEnterpriseResourceType() {
		return enterpriseResourceType;
	}

	/**
	 * @param 所属企业资源类型
	 *            ?3：托管企业，2：成员企业 the enterpriseResourceType to set
	 */
	public void setEnterpriseResourceType(String enterpriseResourceType) {
		this.enterpriseResourceType = enterpriseResourceType;
	}

	/**
	 * @return the 新增不需要填写、系统自动生成修改、删除是比填查询时返回
	 */
	public String getOperCustId() {
		return operCustId;
	}

	/**
	 * @param 新增不需要填写
	 *            、系统自动生成修改、删除是比填查询时返回 the operCustId to set
	 */
	public void setOperCustId(String operCustId) {
		this.operCustId = operCustId;
	}

	/**
	 * @return the 企业客户号
	 */
	public String getEntCustId() {
		return entCustId;
	}

	/**
	 * @param 企业客户号
	 *            the entCustId to set
	 */
	public void setEntCustId(String entCustId) {
		this.entCustId = entCustId;
	}

	/**
	 * @return the 企业互生号
	 */
	public String getEntResNo() {
		return entResNo;
	}

	/**
	 * @param 企业互生号
	 *            the entResNo to set
	 */
	public void setEntResNo(String entResNo) {
		this.entResNo = entResNo;
	}

	/**
	 * @return the 用户名（员工账号）
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param 用户名
	 *            （员工账号） the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the 真实姓名
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * @param 真实姓名
	 *            the realName to set
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * @return the 职务
	 */
	public String getOperDuty() {
		return operDuty;
	}

	/**
	 * @param 职务
	 *            the operDuty to set
	 */
	public void setOperDuty(String operDuty) {
		this.operDuty = operDuty;
	}

	/**
	 * @return the 初始登录密码
	 */
	public String getLoginPwd() {
		return loginPwd;
	}

	/**
	 * @param 初始登录密码
	 *            the loginPwd to set
	 */
	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	/**
	 * @return the 登录盐值
	 */
	public String getLoginPWdSaltValue() {
		return loginPWdSaltValue;
	}

	/**
	 * @param 登录盐值
	 *            the loginPWdSaltValue to set
	 */
	public void setLoginPWdSaltValue(String loginPWdSaltValue) {
		this.loginPWdSaltValue = loginPWdSaltValue;
	}

	/**
	 * @return the 是否管理员
	 */
	public boolean isAdmin() {
		return isAdmin;
	}

	/**
	 * @param 是否管理员
	 *            the isAdmin to set
	 */
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	/**
	 * @return the 最后登录时间
	 */
	public String getLastLoginDate() {
		return lastLoginDate;
	}

	/**
	 * @param 最后登录时间
	 *            the lastLoginDate to set
	 */
	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	/**
	 * @return the 最后登录IP
	 */
	public String getLastLoginIp() {
		return lastLoginIp;
	}

	/**
	 * @param 最后登录IP
	 *            the lastLoginIp to set
	 */
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	/**
	 * @return the 描述
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param 描述
	 *            the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the 操作员手机
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param 操作员手机
	 *            the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the 操作员邮箱
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param 操作员邮箱
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the 操作员互生号非绑定不填
	 */
	public String getOperResNo() {
		return operResNo;
	}

	/**
	 * @param 操作员互生号非绑定不填
	 *            the operResNo to set
	 */
	public void setOperResNo(String operResNo) {
		this.operResNo = operResNo;
	}

	/**
	 * @return the 角色列表添加、修改时不需要、查询需要返回角色信息
	 */
	public List<AsRole> getRoleList() {
		return roleList;
	}

	/**
	 * @param 角色列表添加
	 *            、修改时不需要、查询需要返回角色信息 the roleList to set
	 */
	public void setRoleList(List<AsRole> roleList) {
		this.roleList = roleList;
	}

	/**
	 * @return the 是否绑定互生号1：绑定0：未绑定-1绑定中
	 */
	public Integer getBindResNoStatus() {
		return bindResNoStatus;
	}

	/**
	 * @param 是否绑定互生号1
	 *            ：绑定0：未绑定-1绑定中 the bindResNoStatus to set
	 */
	public void setBindResNoStatus(Integer bindResNoStatus) {
		this.bindResNoStatus = bindResNoStatus;
	}

	/**
	 * @return the 用户组信息
	 */
	public List<AsEntGroup> getGroupList() {
		return groupList;
	}

	/**
	 * @param 用户组信息
	 *            the groupList to set
	 */
	public void setGroupList(List<AsEntGroup> groupList) {
		this.groupList = groupList;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the 操作员状态0：启用，1：禁用2:已删除
	 */
	public Integer getAccountStatus() {
		return accountStatus;
	}

	/**
	 * @param 操作员状态0
	 *            ：启用，1：禁用2:已删除 the accountStatus to set
	 */
	public void setAccountStatus(Integer accountStatus) {
		this.accountStatus = accountStatus;
	}
	
	public String toString(){
		return JSONObject.toJSONString(this);
	}

}
