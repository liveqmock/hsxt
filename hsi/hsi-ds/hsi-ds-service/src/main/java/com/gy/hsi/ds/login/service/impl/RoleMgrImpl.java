package com.gy.hsi.ds.login.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.ds.login.beans.bo.Role;
import com.gy.hsi.ds.login.dao.IRoleDao;
import com.gy.hsi.ds.login.service.IRoleMgr;

/**
 *
 */
@Service
public class RoleMgrImpl implements IRoleMgr {

    @Autowired
    private IRoleDao roleDao;

    @Override
    public Role get(Integer roleId) {
        return roleDao.get(roleId);
    }

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

}
