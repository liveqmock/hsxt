package com.gy.hsi.ds.login.service;

import java.util.List;

import com.gy.hsi.ds.login.beans.bo.Role;

/**
 * @author weiwei
 * @date 2013-12-24
 */
public interface IRoleMgr {

    public Role get(Integer roleId);

    public List<Role> findAll();

}
