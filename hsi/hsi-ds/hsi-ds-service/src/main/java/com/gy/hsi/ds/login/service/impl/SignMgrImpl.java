package com.gy.hsi.ds.login.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsi.ds.common.thirds.ub.common.log.AopLogFactory;
import com.gy.hsi.ds.common.utils.SignUtils;
import com.gy.hsi.ds.login.beans.bo.User;
import com.gy.hsi.ds.login.dao.IUserDao;
import com.gy.hsi.ds.login.service.ISignMgr;

/**
 * 与登录登出相关的
 *
 * @author liaoqiqi
 * @version 2014-2-6
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SignMgrImpl implements ISignMgr {

    protected final static Logger LOG = AopLogFactory.getLogger(SignMgrImpl.class);

    @Autowired
    private IUserDao userDao;

    /**
     * 根据 用户名获取用户
     */
    @Override
    public User getUserByName(String name) {

        return userDao.getUserByName(name);
    }

    /**
     * 验证密码是否正确
     *
     * @param token
     * @param password
     *
     * @return
     */
    public boolean validate(String userPassword, String passwordToBeValidate) {

        String data = SignUtils.createPassword(passwordToBeValidate);

        if (data.equals(userPassword)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 登录
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public User signin(String userName) {

        //
        // 获取用户
        //
        User user = userDao.getUserByName(userName);

        userDao.update(user);

        return user;
    }
    

}
