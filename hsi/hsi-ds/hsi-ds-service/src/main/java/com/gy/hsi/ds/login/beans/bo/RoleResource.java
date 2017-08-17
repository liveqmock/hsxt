package com.gy.hsi.ds.login.beans.bo;

import com.gy.hsi.ds.common.constant.Columns;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.annotation.Column;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.annotation.Table;
import com.github.knightliao.apollo.db.bo.BaseObject;

/**
 *
 */
@Table(db ="", keyColumn = Columns.ROLE_RES_ID, name = "T_DS_ROLE_RESOURCE")
public class RoleResource extends BaseObject<Integer> {

    private static final long serialVersionUID = 1L;

    @Column(value = Columns.ROLE_ID)
    private Integer roleId;

    @Column(value = Columns.URL_PATTERN)
    private String urlPattern;

    @Column(value = Columns.URL_DESCRIPTION)
    private String urlDescription;

    @Column(value = Columns.METHOD_MASK)
    private String methodMask;

    @Column(value = Columns.UPDATE_DATE)
    private String updateDate;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getUrlPattern() {
        return urlPattern;
    }

    public void setUrlPattern(String urlPattern) {
        this.urlPattern = urlPattern;
    }

    public String getUrlDescription() {
        return urlDescription;
    }

    public void setUrlDescription(String urlDescription) {
        this.urlDescription = urlDescription;
    }

    public String getMethodMask() {
        return methodMask;
    }

    public void setMethodMask(String methodMask) {
        this.methodMask = methodMask;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "RoleResource [roleId=" + roleId + ", urlPattern=" + urlPattern + ", urlDescription=" + urlDescription +
                   ", methodMask=" + methodMask + ", updateTime=" + updateDate + "]";
    }

}
