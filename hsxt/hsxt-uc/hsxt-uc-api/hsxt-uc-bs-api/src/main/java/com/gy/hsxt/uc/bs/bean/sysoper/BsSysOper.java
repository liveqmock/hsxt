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

package com.gy.hsxt.uc.bs.bean.sysoper;

import java.io.Serializable;
import java.sql.Timestamp;

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
public class BsSysOper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1696929747466114598L;
	private String operCustId;

    private String subSystemCode;

    private String platformCode;

    private String userName;

    private String pwdLogin;

    private String pwdLoginVer;

    private String pwdLoginSaltValue;

    private String phone;

    private String email;

    private String realName;

    private String duty;

    private Short status;

    private Short isAdmin;

    private Timestamp lastLoginDate;

    private String lastLoginIp;

    private String remark;

    private String isactive;

    private Timestamp createDate;

    private String createdby;

    private Timestamp updateDate;

    private String updatedby;

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
    
    
}
