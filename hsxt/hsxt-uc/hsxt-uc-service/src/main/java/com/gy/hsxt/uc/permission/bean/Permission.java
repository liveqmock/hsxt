package com.gy.hsxt.uc.permission.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 权限信息表数据实体bean
 * @Package: com.gy.hsxt.uc.permission.bean  
 * @ClassName: Permission 
 * @Description: 权限信息表数据实体bean
 *
 * @author: lvyan 
 * @date: 2015-12-15 下午4:40:48 
 * @version V1.0
 */
public class Permission implements Serializable {
    private static final long serialVersionUID = 921824019676092688L;

    /**
     * 权限id
     */
    private String permId;

    /**
     * 父权限id
     */
    private String parentId;

    /**
     * 权限名称
     */
    private String permName;

    /**
     * 权限编码
     */
    private String permCode;

    /**
     * 权限描述
     */
    private String permDesc;

    /**
     * 权限url
     */
    private String permUrl;

    /**
     * 权限类型 0：菜单 1：功能
     */
    private Short permType;

    /**
     * 排序数
     */
    private Short sortNum;

    /**
     * 菜单区域
     */
    private Short layout;

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
     * @return the 权限id
     */
    public String getPermId() {
        return permId;
    }

    /**
     * @param 权限id
     *            the permId to set
     */
    public void setPermId(String permId) {
        this.permId = permId;
    }

    /**
     * @return the 父权限id
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * @param 父权限id
     *            the parentId to set
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * @return the 权限名称
     */
    public String getPermName() {
        return permName;
    }

    /**
     * @param 权限名称
     *            the permName to set
     */
    public void setPermName(String permName) {
        this.permName = permName;
    }

    /**
     * @return the 权限编码
     */
    public String getPermCode() {
        return permCode;
    }

    /**
     * @param 权限编码
     *            the permCode to set
     */
    public void setPermCode(String permCode) {
        this.permCode = permCode;
    }

    /**
     * @return the 权限描述
     */
    public String getPermDesc() {
        return permDesc;
    }

    /**
     * @param 权限描述
     *            the permDesc to set
     */
    public void setPermDesc(String permDesc) {
        this.permDesc = permDesc;
    }

    /**
     * @return the 权限url
     */
    public String getPermUrl() {
        return permUrl;
    }

    /**
     * @param 权限url
     *            the permUrl to set
     */
    public void setPermUrl(String permUrl) {
        this.permUrl = permUrl;
    }

    /**
     * @return the 权限类型0：菜单1：功能
     */
    public Short getPermType() {
        return permType;
    }

    /**
     * @param 权限类型0
     *            ：菜单1：功能 the permType to set
     */
    public void setPermType(Short permType) {
        this.permType = permType;
    }

    /**
     * @return the 排序数
     */
    public Short getSortNum() {
        return sortNum;
    }

    /**
     * @param 排序数
     *            the sortNum to set
     */
    public void setSortNum(Short sortNum) {
        this.sortNum = sortNum;
    }

    /**
     * @return the 菜单区域
     */
    public Short getLayout() {
        return layout;
    }

    /**
     * @param 菜单区域
     *            the layout to set
     */
    public void setLayout(Short layout) {
        this.layout = layout;
    }

    /**
     * @return the 子系统编码
     */
    public String getSubSystemCode() {
        return subSystemCode;
    }

    /**
     * @param 子系统编码
     *            the subSystemCode to set
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
     * @param 平台编码
     *            the platformCode to set
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
     * @param 有效状态
     *            the isactive to set
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
     * @param 创建日期
     *            the createDate to set
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
     * @param 创建者
     *            the createdby to set
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
     * @param 更新日期
     *            the updateDate to set
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
     * @param 更新者
     *            the updatedby to set
     */
    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

}
