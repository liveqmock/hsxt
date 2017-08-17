package com.gy.hsi.ds.login.service.impl;

import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsi.ds.common.thirds.ub.common.commons.ThreadContext;
import com.gy.hsi.ds.common.thirds.ub.common.log.AopLogFactory;
import com.gy.hsi.ds.login.beans.Visitor;
import com.gy.hsi.ds.login.service.IUserInnerMgr;

/**
 * @author knightliao
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserInnerMgrImpl implements IUserInnerMgr {

    protected final static Logger LOG = AopLogFactory.getLogger(UserMgrImpl.class);

    @Override
    public Visitor getVisitor(Long userId) {

        if (userId == null || userId <= 0) {
            LOG.error("userId is null or <= 0, return null");
            return null;
        }

        return null;
    }

    /**
     *
     */
    @Override
    public Set<Long> getVisitorAppIds() {

        Visitor visitor = ThreadContext.getSessionVisitor();
        return visitor.getAppIds();
    }
    

    /**
     *
     */
    @Override
    public Set<Long> getVisitorEnvIds() {

        Visitor visitor = ThreadContext.getSessionVisitor();
        return visitor.getEnvIds();
    }
}
