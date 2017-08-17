/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.utils;

import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.gpf.um.constants.SessionAttribute;
import com.gy.hsxt.gpf.um.enums.UMRespCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 会话令牌工具
 *
 * @Package : com.gy.hsxt.gpf.um.utils
 * @ClassName : TokenUtils
 * @Description : 会话令牌工具
 * @Author : chenm
 * @Date : 2016/2/1 18:03
 * @Version V3.0.0.0
 */
public abstract class TokenUtils {

    /**
     * cookie有效时间 30分钟
     */
    public static final int COOKIE_OUT_TIME = 1800000;

    /**
     * 从请求中获取令牌token
     *
     * @param request 请求
     * @return {@code String}
     */
    public static String takeToken(HttpServletRequest request) {

        //先从请求参数中取
        String token = request.getParameter(SessionAttribute.LOGIN_TOKEN_CODE);
        //从cookie 中获取
        if (StringUtils.isBlank(token)) {
            Cookie cookie = WebUtils.getCookie(request, SessionAttribute.LOGIN_TOKEN_CODE);
            token = cookie != null ? cookie.getValue() : null;
        }
        //先从会话中取
        if (StringUtils.isBlank(token)) {
            Object sessionToken = WebUtils.getSessionAttribute(request, SessionAttribute.LOGIN_VALID_CODE);
            token = sessionToken != null ? String.valueOf(sessionToken) : null;
        }
        HsAssert.hasText(token, UMRespCode.UM_LOGIN_TOKEN_EMPTY, "从请求[request]中获取登录令牌[token]失败");
        return token;
    }

    /**
     * 将token放入cookie/session
     *
     * @param request  请求
     * @param response 响应
     * @param token    令牌
     */
    public static void putToken(HttpServletRequest request, HttpServletResponse response, String token) {
        //将token设置在session中
        WebUtils.setSessionAttribute(request, SessionAttribute.LOGIN_TOKEN_CODE, token);
        //设置cookie
        Cookie cookie = new Cookie(SessionAttribute.LOGIN_TOKEN_CODE, token);
        //有效时间1小时
        cookie.setMaxAge(COOKIE_OUT_TIME);
        //设置cookie路径
        cookie.setPath("/");
        //添加到响应中
        response.addCookie(cookie);
    }

    /**
     * 移除token
     *
     * @param request  请求
     * @param response 响应
     */
    public static void removeToken(HttpServletRequest request, HttpServletResponse response) {
        //删除cookie
        Cookie cookie = new Cookie(SessionAttribute.LOGIN_TOKEN_CODE, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        //删除session中的值
        WebUtils.setSessionAttribute(request, SessionAttribute.LOGIN_TOKEN_CODE, null);
    }

}
