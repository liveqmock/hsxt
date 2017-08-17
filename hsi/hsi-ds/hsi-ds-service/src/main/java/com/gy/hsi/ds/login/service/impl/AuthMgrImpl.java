package com.gy.hsi.ds.login.service.impl;

import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.ds.login.service.IAuthMgr;
import com.gy.hsi.ds.login.service.IUserInnerMgr;

/**
 * @author knightliao
 */
@Service
public class AuthMgrImpl implements IAuthMgr {

    @Autowired
    private IUserInnerMgr userInnerMgr;

    @Override
    public boolean verifyApp4CurrentUser(Long appId) {

        Set<Long> idsLongs = userInnerMgr.getVisitorAppIds();

        if (CollectionUtils.isEmpty(idsLongs)) {
            return true;
        }

        if (idsLongs.contains(appId)) {
            return true;
        } else {
            return false;
        }

    }

}
