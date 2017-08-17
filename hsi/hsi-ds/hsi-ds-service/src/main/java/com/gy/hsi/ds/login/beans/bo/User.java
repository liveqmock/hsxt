package com.gy.hsi.ds.login.beans.bo;

import com.gy.hsi.ds.common.constant.Columns;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.annotation.Column;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.annotation.Table;
import com.github.knightliao.apollo.db.bo.BaseObject;

/**
 * 用户表
 *
 * @author liaoqiqi
 * @version 2013-11-28
 */
@Table(db ="", name = "T_DS_USER", keyColumn = Columns.USER_ID)
public class User extends BaseObject<Long> {

    private static final long serialVersionUID = 1L;

    // 唯一
    @Column(value = Columns.NAME)
    private String name;

    // token
    @Column(value = Columns.TOKEN)
    private String token;

    // 密码
    @Column(value = Columns.PASSWORD)
    private String password;

    //
    @Column(value = Columns.OWNENVS)
    private String ownEnvs;
    
    //
    @Column(value = Columns.OWNAPPS)
    private String ownApps;

    /**
     * 角色ID
     */
    @Column(value = Columns.ROLE_ID)
    private int roleId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOwnApps() {
        return ownApps;
    }

    public void setOwnApps(String ownApps) {
        this.ownApps = ownApps;
    }
    
    public String getOwnEnvs() {
        return ownEnvs;
    }

    public void setOwnEnvs(String ownEnvs) {
        this.ownEnvs = ownEnvs;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "User{" +
                   "name='" + name + '\'' +
                   ", token='" + token + '\'' +
                   ", password='" + password + '\'' +
                   ", ownApps='" + ownApps + '\'' +
                   ", roleId=" + roleId +'\'' +
                   ", ownApps=" + ownApps +
                   '}';
    }
}
