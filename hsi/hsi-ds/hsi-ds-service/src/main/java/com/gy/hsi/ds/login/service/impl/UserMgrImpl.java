package com.gy.hsi.ds.login.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsi.ds.common.thirds.ub.common.commons.ThreadContext;
import com.gy.hsi.ds.common.thirds.ub.common.log.AopLogFactory;
import com.gy.hsi.ds.login.beans.Visitor;
import com.gy.hsi.ds.login.beans.bo.User;
import com.gy.hsi.ds.login.beans.vo.VisitorVo;
import com.gy.hsi.ds.login.dao.IUserDao;
import com.gy.hsi.ds.login.service.IUserInnerMgr;
import com.gy.hsi.ds.login.service.IUserMgr;

/**
 * @author liaoqiqi
 * @version 2013-12-5
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserMgrImpl implements IUserMgr {

    protected final static Logger LOG = AopLogFactory.getLogger(UserMgrImpl.class);

    @Autowired
    private IUserInnerMgr userInnerMgr;

    @Autowired
    private IUserDao userDao;

    @Override
    public Visitor getVisitor(Long userId) {

        return userInnerMgr.getVisitor(userId);
    }

    @Override
    public VisitorVo getCurVisitor() {

        Visitor visitor = ThreadContext.getSessionVisitor();
        if (visitor == null) {
            return null;
        }

        VisitorVo visitorVo = new VisitorVo();
        visitorVo.setId(visitor.getId());
        visitorVo.setName(visitor.getLoginUserName());

        return visitorVo;
    }

    /**
     * 创建
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Long create(User user) {

        user = userDao.create(user);
        return user.getId();
    }

    /**
     *
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void create(List<User> users) {

        userDao.create(users);
    }

    @Override
    public List<User> getAll() {

        return userDao.findAll();
    }

    @Override
    public User getUser(Long userId) {

        return userDao.get(userId);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateUser(User user){
        userDao.update(user);
    }

}
