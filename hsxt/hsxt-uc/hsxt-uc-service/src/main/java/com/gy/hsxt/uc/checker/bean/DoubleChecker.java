package com.gy.hsxt.uc.checker.bean;

import java.io.Serializable;
import java.sql.Timestamp;

public class DoubleChecker implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6073871527411292780L;

	private String pwdLogin;

    private String pwdLoginVer;

    private String pwdLoginSaltValue;

    private Timestamp createDate;

    private String createdby;

    private Timestamp updateDate;

    private String updatedby;
    
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
    private Integer status;
    /** 是否管理员1:管理员 0：非管理员*/
    private Integer isAdmin;
    /** 上次登录时间*/
    private Timestamp lastLoginDate;
    /** 上次登录IP*/
    private String lastLoginIp;
    /** 备注信息*/
    private String remark;
    /** 标记此条记录的状态Y:可用 N:不可用*/
    private String isactive;

    public String getOperCustId() {
        return operCustId;
    }

    public void setOperCustId(String operCustId) {
        this.operCustId = operCustId == null ? null : operCustId.trim();
    }

    public String getSubSystemCode() {
        return subSystemCode;
    }

    public void setSubSystemCode(String subSystemCode) {
        this.subSystemCode = subSystemCode == null ? null : subSystemCode.trim();
    }

    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode == null ? null : platformCode.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPwdLogin() {
        return pwdLogin;
    }

    public void setPwdLogin(String pwdLogin) {
        this.pwdLogin = pwdLogin == null ? null : pwdLogin.trim();
    }

    public String getPwdLoginVer() {
        return pwdLoginVer;
    }

    public void setPwdLoginVer(String pwdLoginVer) {
        this.pwdLoginVer = pwdLoginVer == null ? null : pwdLoginVer.trim();
    }

    public String getPwdLoginSaltValue() {
        return pwdLoginSaltValue;
    }

    public void setPwdLoginSaltValue(String pwdLoginSaltValue) {
        this.pwdLoginSaltValue = pwdLoginSaltValue == null ? null : pwdLoginSaltValue.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty == null ? null : duty.trim();
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
        this.lastLoginIp = lastLoginIp == null ? null : lastLoginIp.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getIsactive() {
        return isactive;
    }

    public void setIsactive(String isactive) {
        this.isactive = isactive == null ? null : isactive.trim();
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
        this.createdby = createdby == null ? null : createdby.trim();
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
        this.updatedby = updatedby == null ? null : updatedby.trim();
    }
}