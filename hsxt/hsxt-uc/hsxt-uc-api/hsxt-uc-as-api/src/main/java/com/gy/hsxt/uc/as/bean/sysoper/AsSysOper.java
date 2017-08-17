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

package com.gy.hsxt.uc.as.bean.sysoper;

import java.io.Serializable;
import java.sql.Timestamp;

import com.alibaba.fastjson.JSONObject;

/** 
 * 
 * @Package: com.gy.hsxt.uc.as.bean.sysoper  
 * @ClassName: AsSysOper 
 * @Description: 系统用户信息
 *
 * @author: tianxh
 * @date: 2015-10-30 下午4:11:47 
 * @version V1.0  
 */
public class AsSysOper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1696929747466114598L;
	
	/** 系统用户客户号 */
	private String operCustId;
	/** 子系统code*/
    private String subSystemCode;
    /** 平台code*/
    private String platformCode;
    /** 系统用户账号*/
    private String userName;
    
    /** 手机号码*/
    private String phone;
    /** 邮箱*/
    private String email;
    /** 真实姓名*/
    private String realName;
    /** 职务*/
    private String duty;
    /** 状态0：激活状态，1：冻结状态，2：注销状态 3:已删除状态 */
    private Short status;
    /** 是否管理员1:管理员 0：非管理员*/
    private Short isAdmin;
    /** 上次登录时间*/
    private Timestamp lastLoginDate;
    /** 上次登录IP*/
    private String lastLoginIp;
    /** 备注信息*/
    private String remark;
    /** 标记此条记录的状态Y:可用 N:不可用*/
    private String isactive;
    
    /** 关联操作员客户号*/
    private String operCustId2;
    
    private String pwdLogin;
    
    private String newPwdLogin;
    
    private String secondPwdLogin;

    private String pwdLoginVer;

    private String pwdLoginSaltValue;

    private Timestamp createDate;

    private String createdby;

    private Timestamp updateDate;

    private String updatedby;

    private String randomToken;

    private Integer isChecker;
	/**
	 * @return the randomToken
	 */
	public String getRandomToken() {
		return randomToken;
	}

	/**
	 * @param randomToken the randomToken to set
	 */
	public void setRandomToken(String randomToken) {
		this.randomToken = randomToken;
	}

	public String getOperCustId() {
		return operCustId;
	}

	public void setOperCustId(String operCustId) {
		this.operCustId = operCustId;
	}

	public String getSubSystemCode() {
		return subSystemCode;
	}

	public void setSubSystemCode(String subSystemCode) {
		this.subSystemCode = subSystemCode;
	}

	public String getPlatformCode() {
		return platformCode;
	}

	public void setPlatformCode(String platformCode) {
		this.platformCode = platformCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPwdLogin() {
		return pwdLogin;
	}

	public void setPwdLogin(String pwdLogin) {
		this.pwdLogin = pwdLogin;
	}

	public String getPwdLoginVer() {
		return pwdLoginVer;
	}

	public void setPwdLoginVer(String pwdLoginVer) {
		this.pwdLoginVer = pwdLoginVer;
	}

	public String getPwdLoginSaltValue() {
		return pwdLoginSaltValue;
	}

	public void setPwdLoginSaltValue(String pwdLoginSaltValue) {
		this.pwdLoginSaltValue = pwdLoginSaltValue;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Short getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Short isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Timestamp getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Timestamp lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIsactive() {
		return isactive;
	}

	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}
    
	public String toString(){
		return JSONObject.toJSONString(this);
	}

	public String getNewPwdLogin() {
		return newPwdLogin;
	}

	public void setNewPwdLogin(String newPwdLogin) {
		this.newPwdLogin = newPwdLogin;
	}

	public String getOperCustId2() {
		return operCustId2;
	}

	public void setOperCustId2(String operCustId2) {
		this.operCustId2 = operCustId2;
	}

	public String getSecondPwdLogin() {
		return secondPwdLogin;
	}

	public void setSecondPwdLogin(String secondPwdLogin) {
		this.secondPwdLogin = secondPwdLogin;
	}

	public Integer getIsChecker() {
		return isChecker;
	}

	public void setIsChecker(Integer isChecker) {
		this.isChecker = isChecker;
	}
	
	
	
}
