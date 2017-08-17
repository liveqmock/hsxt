package com.gy.hsxt.uc.sysoper.bean;

import java.sql.Timestamp;

import com.gy.hsxt.uc.common.bean.UserInfo;

public class SysOperator extends UserInfo{

	private static final long serialVersionUID = -8299926837922869385L;
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
    /**
	 * @return the 系统用户客户号
	 */
    public String getOperCustId() {
        return operCustId;
    }
    /**
	 * @param 系统用户客户号
	 *            the operCustId to set
	 */
    public void setOperCustId(String operCustId) {
        this.operCustId = operCustId == null ? null : operCustId.trim();
    }
    /**
	 * @return the 子系统code
	 */
    public String getSubSystemCode() {
        return subSystemCode;
    }
    /**
	 * @param 子系统code
	 *            the subSystemCode to set
	 */
    public void setSubSystemCode(String subSystemCode) {
        this.subSystemCode = subSystemCode == null ? null : subSystemCode.trim();
    }

    /**
	 * @return the 平台code
	 */
    public String getPlatformCode() {
        return platformCode;
    }
    /**
	 * @param 平台code
	 *            the platformCode to set
	 */
    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode == null ? null : platformCode.trim();
    }

    /**
	 * @return the 系统用户名
	 */
    public String getUserName() {
        return userName;
    }
    /**
	 * @param 系统用户名
	 *            the userName to set
	 */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
	 * @return the 联系电话
	 */
    public String getPhone() {
        return phone;
    }
    /**
	 * @param 联系电话
	 *            the phone to set
	 */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
	 * @return the 邮箱
	 */
    public String getEmail() {
        return email;
    }
    /**
	 * @param 邮箱
	 *            the email to set
	 */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
	 * @return the 姓名
	 */
    public String getRealName() {
        return realName;
    }
    /**
	 * @param 姓名
	 *            the realName to set
	 */
    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    /**
	 * @return the 职务
	 */
    public String getDuty() {
        return duty;
    }
    /**
	 * @param 职务
	 *            the duty to set
	 */
    public void setDuty(String duty) {
        this.duty = duty == null ? null : duty.trim();
    }

    /**
	 * @return the 状态
	 */
    public Short getStatus() {
        return status;
    }
    /**
	 * @param 状态
	 *            the status to set
	 */
    public void setStatus(Short status) {
        this.status = status;
    }

    /**
	 * @return the 是否管理员
	 */
    public Short getIsAdmin() {
        return isAdmin;
    }
    /**
	 * @param 是否管理员
	 *            the isAdmin to set
	 */
    public void setIsAdmin(Short isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
	 * @return the 最近一次登录IP
	 */
    public String getLastLoginIp() {
        return lastLoginIp;
    }
    /**
	 * @param 最近一次登录IP
	 *            the lastLoginIp to set
	 */
    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp == null ? null : lastLoginIp.trim();
    }

    /**
	 * @return the 备注
	 */
    public String getRemark() {
        return remark;
    }
    /**
	 * @param 企业客户号
	 *            the 备注 to set
	 */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
	 * @return the 标记此条记录的状态Y:可用 N:不可用
	 */
    public String getIsactive() {
        return isactive;
    }
    /**
	 * @param 标记此条记录的状态Y:可用 N:不可用
	 *            the isactive to set
	 */
    public void setIsactive(String isactive) {
        this.isactive = isactive == null ? null : isactive.trim();
    }

    /**
	 * @return the 上次登录时间
	 */
	public Timestamp getLastLoginDate() {
		return lastLoginDate;
	}
	/**
	 * @param 上次登录时间
	 *            the lastLoginDate to set
	 */
	public void setLastLoginDate(Timestamp lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	public String getOperCustId2() {
		return operCustId2;
	}
	public void setOperCustId2(String operCustId2) {
		this.operCustId2 = operCustId2;
	}
	
	
    
}