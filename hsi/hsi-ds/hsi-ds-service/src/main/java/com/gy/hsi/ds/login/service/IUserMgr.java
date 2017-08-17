package com.gy.hsi.ds.login.service;

import java.util.List;

import com.gy.hsi.ds.login.beans.Visitor;
import com.gy.hsi.ds.login.beans.bo.User;
import com.gy.hsi.ds.login.beans.vo.VisitorVo;

/**
 * @author liaoqiqi
 * @version 2013-11-28
 */
public interface IUserMgr {

    /**
     * 获取用户的基本信息（登录用户）
     *
     * @return
     */
    Visitor getVisitor(Long userId);

    VisitorVo getCurVisitor();

    User getUser(Long userId);

    /**
     * @return
     */
    Long create(User user);

    /**
     * @param user
     */
    void create(List<User> user);

    /**
     * @return
     */
    List<User> getAll();
    

    void updateUser(User user);

}
