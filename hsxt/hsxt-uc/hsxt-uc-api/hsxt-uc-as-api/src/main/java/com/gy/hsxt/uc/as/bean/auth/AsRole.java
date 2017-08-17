package com.gy.hsxt.uc.as.bean.auth;

import java.io.Serializable;


public class AsRole  implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = -8020135579456950207L;

    /** 角色编号ID */
    String roleId;

    /** 角色名称 */
    String roleName;

    /** 企业客户号 */
    String entCustId;

    /** 角色描述 */
    String roleDesc;

    /** 子系统代码 */
    String subSystemCode;

    /** 平台代码 */
    String platformCode;

    /** 角色类型 */
    String roleType;

    /**
     * @return the 角色编号ID
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * @param 角色编号ID the roleId to set
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    /**
     * @return the 角色名称
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * @param 角色名称 the roleName to set
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * @return the 企业客户号
     */
    public String getEntCustId() {
        return entCustId;
    }

    /**
     * @param 企业客户号 the entCustId to set
     */
    public void setEntCustId(String entCustId) {
        this.entCustId = entCustId;
    }

    /**
     * @return the 角色描述
     */
    public String getRoleDesc() {
        return roleDesc;
    }

    /**
     * @param 角色描述 the roleDesc to set
     */
    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    /**
     * @return the 子系统代码
     */
    public String getSubSystemCode() {
        return subSystemCode;
    }

    /**
     * @param 子系统代码 the subSystemCode to set
     */
    public void setSubSystemCode(String subSystemCode) {
        this.subSystemCode = subSystemCode;
    }

    /**
     * @return the 平台代码
     */
    public String getPlatformCode() {
        return platformCode;
    }

    /**
     * @param 平台代码 the platformCode to set
     */
    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    /**
     * @return the 角色类型
     */
    public String getRoleType() {
        return roleType;
    }

    /**
     * @param 角色类型 the roleType to set
     */
    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    
}
