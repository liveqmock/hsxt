package com.gy.hsxt.uc.as.bean.result;

import java.sql.Timestamp;

public class AsSysOperatorLoginResult extends LoginResult{
    /**
	 * 
	 */
	private static final long serialVersionUID = -8299926837922869385L;
	/** 操作员客户号或双签员客户号*/
	private String operCustId;
	/** 子系统code */
    private String subSystemCode;
    /** 平台code */
    private String platformCode;
    /** 用户名 */
    private String userName;
    /** 登录密码 */
    private String pwdLogin;
    /** 登录密码版本 */
    private String pwdLoginVer;
    /** 登录密码盐值 */
    private String pwdLoginSaltValue;
    /** 手机 */
    private String phone;
    /** 邮箱 */
    private String email;
    /** 姓名 */
    private String realName;
    /** 职务 */
    private String duty;
    /** 状态0：激活状态，1：冻结状态，2：注销状态 3:已删除状态 */
    private Integer status;
    /** 是否管理员1:管理员 0：非管理员 */
    private Integer isAdmin;
    /** 备注信息 */
    private String remark;
    /** 标记此条记录的状态Y:可用 N:不可用 */
    private String isactive;
    /** 创建时间创建时间，取记录创建时的系统时间 */
    private Timestamp createDate;
    /** 创建人由谁创建，值为用户的伪键ID */
    private String createdby;
    /** 更新时间更新时间，取记录更新时的系统时间 */
    private Timestamp updateDate;
    /** 更新人由谁更新，值为用户的伪键ID */
    private String updatedby;
    /** 关联操作员客户号*/
    private String operCustId2;

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

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby == null ? null : createdby.trim();
    }

    public String getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby == null ? null : updatedby.trim();
    }
    
	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public String getOperCustId2() {
		return operCustId2;
	}

	public void setOperCustId2(String operCustId2) {
		this.operCustId2 = operCustId2;
	}

       
}