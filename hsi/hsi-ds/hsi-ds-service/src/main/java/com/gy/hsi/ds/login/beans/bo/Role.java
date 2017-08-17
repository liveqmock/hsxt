package com.gy.hsi.ds.login.beans.bo;

import com.gy.hsi.ds.common.constant.Columns;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.annotation.Column;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.annotation.Table;
import com.github.knightliao.apollo.db.bo.BaseObject;

/**
 * @author weiwei
 * @date 2013-12-24 下午2:43:37
 */
@Table(db ="", keyColumn = Columns.ROLE_ID, name = "T_DS_ROLE")
public class Role extends BaseObject<Integer> {

    private static final long serialVersionUID = 1L;

    @Column(value = Columns.RoleColumns.ROLE_NAME)
    private String roleName;

    @Column(value = Columns.CREATE_DATE)
    private String createDate;

    @Column(value = Columns.CREATE_BY)
    private String createBy;

    @Column(value = Columns.UPDATE_DATE)
    private String updateDate;

    @Column(value = Columns.UPDATE_BY)
    private String updateBy;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @Override
    public String toString() {
        return "Role [roleName=" + roleName + ", createDate=" + createDate + ", createBy=" + createBy + ", updateDate="
                + updateDate + ", updateBy=" + updateBy + "]";
    }



}
