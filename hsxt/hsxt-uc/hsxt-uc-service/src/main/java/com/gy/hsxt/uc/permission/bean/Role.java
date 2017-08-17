package com.gy.hsxt.uc.permission.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 角色信息表实体bean
 * @Package: com.gy.hsxt.uc.permission.bean  
 * @ClassName: Role 
 * @Description: 角色信息表实体bean
 *
 * @author: lvyan 
 * @date: 2015-12-15 下午4:41:53 
 * @version V1.0
 */
public class Role implements Serializable {
    private static final long serialVersionUID = -7882979052354393240L;
    /**
     * 角色id
     */
    private String roleId;
    /**
     * 角色类型 1：全局:2：平台:3：私有
     */
    private Short roleType;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 企业客户号
     */
    private String entCustId;
    /**
     * 角色描述
     */
    private String roleDesc;
    /**
     * 子系统编码
     */
    private String subSystemCode;
    /**
     * 平台编码
     */
    private String platformCode;
    /**
     * 有效状态
     */
    private String isactive;
    /**
     * 创建日期
     */
    private Timestamp createDate;
    /**
     * 创建者
     */
    private String createdby;
    /**
     * 更新日期
     */
    private Timestamp updateDate;
    /**
     * 更新者
     */
    private String updatedby;
    
    /**
     * @return the 角色id
     */
    public String getRoleId() {
        return roleId;
    }
    /**
     * @param 角色id the roleId to set
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
    /**
     * @return the 角色类型1：全局:2：平台:3：私有
     */
    public Short getRoleType() {
        return roleType;
    }
    /**
     * @param 角色类型1：全局:2：平台:3：私有 the roleType to set
     */
    public void setRoleType(Short roleType) {
        this.roleType = roleType;
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
     * @return the 子系统编码
     */
    public String getSubSystemCode() {
        return subSystemCode;
    }
    /**
     * @param 子系统编码 the subSystemCode to set
     */
    public void setSubSystemCode(String subSystemCode) {
        this.subSystemCode = subSystemCode;
    }
    /**
     * @return the 平台编码
     */
    public String getPlatformCode() {
        return platformCode;
    }
    /**
     * @param 平台编码 the platformCode to set
     */
    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }
    /**
     * @return the 有效状态
     */
    public String getIsactive() {
        return isactive;
    }
    /**
     * @param 有效状态 the isactive to set
     */
    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }
    /**
     * @return the 创建日期
     */
    public Timestamp getCreateDate() {
        return createDate;
    }
    /**
     * @param 创建日期 the createDate to set
     */
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }
    /**
     * @return the 创建者
     */
    public String getCreatedby() {
        return createdby;
    }
    /**
     * @param 创建者 the createdby to set
     */
    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }
    /**
     * @return the 更新日期
     */
    public Timestamp getUpdateDate() {
        return updateDate;
    }
    /**
     * @param 更新日期 the updateDate to set
     */
    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }
    /**
     * @return the 更新者
     */
    public String getUpdatedby() {
        return updatedby;
    }
    /**
     * @param 更新者 the updatedby to set
     */
    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

}