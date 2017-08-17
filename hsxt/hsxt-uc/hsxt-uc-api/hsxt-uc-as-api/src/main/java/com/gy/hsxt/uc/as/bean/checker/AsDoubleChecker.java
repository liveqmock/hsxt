package com.gy.hsxt.uc.as.bean.checker;

import java.io.Serializable;
import java.sql.Timestamp;

public class AsDoubleChecker implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7173702890656580146L;

	/** 双签员的登录密码 */
    private String pwdLogin;
    /** AES秘钥 */
    private String secretKey;
    /** 双签员客户号 */
	private String operCustId;
	/** 子系统code*/
    private String subSystemCode;
    /** 平台code*/
    private String platformCode;
    /** 双签员用户账号*/
    private String userName;
    /** 新登录密码*/
    private String newPwdLogin;
    /** 手机号码*/
    private String phone;
    /** 邮箱*/
    private String email;
    /** 真实姓名*/
    private String realName;
    /** 职务*/
    private String duty;
    /** 状态0：激活状态，1：冻结状态，2：注销状态 3:已删除状态 */
    private Integer status;
    /** 是否管理员1:管理员 0：非管理员*/
    private Integer isAdmin;
    /** 上次登录时间*/
    private Timestamp lastLoginDate;
    /** 上次登录IP*/
    private String lastLoginIp;
    /** 备注信息*/
    private String remark;
	public String getPwdLogin() {
		return pwdLogin;
	}
	public void setPwdLogin(String pwdLogin) {
		this.pwdLogin = pwdLogin;
	}
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(Integer isAdmin) {
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
	public String getNewPwdLogin() {
		return newPwdLogin;
	}
	public void setNewPwdLogin(String newPwdLogin) {
		this.newPwdLogin = newPwdLogin;
	}
    
    
}
