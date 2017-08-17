package com.gy.hsi.ds.login.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.gy.hsi.ds.common.constant.UserConstant;
import com.gy.hsi.ds.common.thirds.ub.common.commons.ThreadContext;
import com.gy.hsi.ds.login.beans.Visitor;
import com.gy.hsi.ds.login.beans.bo.User;
import com.gy.hsi.ds.login.service.ILoginService;
import com.gy.hsi.ds.login.service.IUserMgr;
/**
 * @author liaoqiqi
 * @version 2014-2-4
 */
public class LoginService implements ILoginService {

    @Autowired
    private IUserMgr userMgr;

    /**
     * 校验Redis是否登录
     */
    @Override
    public Visitor isLogin(HttpServletRequest request) {

        Long userId = 1L;

        User user = userMgr.getUser(userId);

        Visitor visitor = new Visitor();
        visitor.setId(userId);
        visitor.setLoginUserId(userId);
        visitor.setLoginUserName(user.getName());

        return visitor;
    }

    @Override
    public void login(HttpServletRequest request, User user, int expireTime) {

        Visitor visitor = new Visitor();

        visitor.setId(user.getId());
        visitor.setLoginUserId(user.getId());
        visitor.setLoginUserName(user.getName());
        visitor.setRoleId(user.getRoleId());
        visitor.setAppIds(user.getOwnApps());
        visitor.setEnvIds(user.getOwnEnvs());

        //
        // 更新session
        //
        updateSessionVisitor(request.getSession(), visitor);
    }

    @Override
    public void updateSessionVisitor(HttpSession session, Visitor visitor) {

        if (visitor != null) {
            // 更新
            session.setAttribute(UserConstant.USER_KEY, visitor);
        } else {

            // 删除
            session.removeAttribute(UserConstant.USER_KEY);
        }

        ThreadContext.putSessionVisitor(visitor);
    }

    @Override
    public void logout(HttpServletRequest request) {

        // 更新session
        updateSessionVisitor(request.getSession(), null);
    }
}
