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

package com.gy.hsxt.uc.as.bean.auth;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @Package: com.gy.hsxt.uc.as.bean.auth
 * @ClassName: AsPermission0
 * @Description: TODO
 * 
 * @author: lvyan
 * @date: 2015-10-19 下午5:52:21
 * @version V1.0
 */
public class AsPermission  implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -2923700082351064L;

    /** 权限编号ID */
    String permId;

    /** 父级权限 */
    String parentId;

    /** 权限名称 */
    String permName;

    /** 权限代码 */
    String permCode;

    /** 权限URL */
    String permUrl;

    /** 权限描述 */
    String permDesc;

    /** 权限类型 */
    String permType;

    /** 排序 */
    String sortNum;

    /** 菜单编排区域 */
    String layout;

    /** 子系统代码 */
    String subSystemCode;

    /** 平台代码 */
    String platformCode;
    
    /** 创建人 */
    String createdby;

    /** 创建时间 */
    Date createDate;

    /** 修改人 */
    String updatedby;

    /** 修改时间 */
    Date updateDate;
    
    /**
     * 有效状态
     */
    private String isactive;

    /**
     * @return the 权限编号ID
     */
    public String getPermId() {
        return permId;
    }

    /**
     * @param 权限编号ID
     *            the permId to set
     */
    public void setPermId(String permId) {
        this.permId = permId;
    }

    /**
     * @return the 父级权限
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * @param 父级权限
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
     * @return the 权限代码
     */
    public String getPermCode() {
        return permCode;
    }

    /**
     * @param 权限代码
     *            the permCode to set
     */
    public void setPermCode(String permCode) {
        this.permCode = permCode;
    }

    /**
     * @return the 权限URL
     */
    public String getPermUrl() {
        return permUrl;
    }

    /**
     * @param 权限URL
     *            the permUrl to set
     */
    public void setPermUrl(String permUrl) {
        this.permUrl = permUrl;
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
     * @return the 权限类型
     */
    public String getPermType() {
        return permType;
    }

    /**
     * @param 权限类型
     *            the permType to set
     */
    public void setPermType(String permType) {
        this.permType = permType;
    }

    /**
     * @return the 排序
     */
    public String getSortNum() {
        return sortNum;
    }

    /**
     * @param 排序
     *            the sortNum to set
     */
    public void setSortNum(String sortNum) {
        this.sortNum = sortNum;
    }

    /**
     * @return the 菜单编排区域
     */
    public String getLayout() {
        return layout;
    }

    /**
     * @param 菜单编排区域
     *            the layout to set
     */
    public void setLayout(String layout) {
        this.layout = layout;
    }

    /**
     * @return the 子系统代码
     */
    public String getSubSystemCode() {
        return subSystemCode;
    }

    /**
     * @param 子系统代码
     *            the subSystemCode to set
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
     * @param 平台代码
     *            the platformCode to set
     */
    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

   

    /**
     * @return the 创建人
     */
    public String getCreatedby() {
        return createdby;
    }

    /**
     * @param 创建人 the createdby to set
     */
    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    /**
     * @return the 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param 创建时间 the createDate to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the 修改人
     */
    public String getUpdatedby() {
        return updatedby;
    }

    /**
     * @param 修改人 the updatedby to set
     */
    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

    /**
     * @return the 修改时间
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * @param 修改时间
     *            the updateDate to set
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
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
    
    

}
