package com.gy.hsi.ds.login.service;

import java.util.Set;

import com.gy.hsi.ds.login.beans.Visitor;

public interface IUserInnerMgr {

    /**
     * 获取用户的基本信息（登录用户）
     *
     * @return
     */
    Visitor getVisitor(Long userId);

    /**
     * @return
     */
    Set<Long> getVisitorAppIds();
    
    /**
     * @return
     */
    Set<Long> getVisitorEnvIds();
}
