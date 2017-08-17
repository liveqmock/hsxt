/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.interceptor;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.gpf.um.bean.RespInfo;
import com.gy.hsxt.gpf.um.constants.SessionAttribute;
import com.gy.hsxt.gpf.um.enums.UMRespCode;
import com.gy.hsxt.gpf.um.utils.TokenUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 登录令牌拦截器抽象方法
 *
 * @Package : com.gy.hsxt.gpf.um.api
 * @ClassName : AbstractLoginTokeInterceptor
 * @Description : 登录令牌拦截器抽象方法
 * @Author : chenm
 * @Date : 2016/1/30 17:15
 * @Version V3.0.0.0
 */
public abstract class AbstractLoginTokeInterceptor extends HandlerInterceptorAdapter {

    /**
     * Intercept the execution of a handler. Called after HandlerMapping determined
     * an appropriate handler object, but before HandlerAdapter invokes the handler.
     * <p>DispatcherServlet processes a handler in an execution chain, consisting
     * of any number of interceptors, with the handler itself at the end.
     * With this method, each interceptor can decide to abort the execution chain,
     * typically sending a HTTP error or writing a custom response.
     *
     * @param request  current HTTP request
     * @param response current HTTP response
     * @param handler  chosen handler to execute, for type and/or instance evaluation
     * @return {@code true} if the execution chain should proceed with the
     * next interceptor or the handler itself. Else, DispatcherServlet assumes
     * that this interceptor has already dealt with the response itself.
     * @throws Exception in case of errors
     */
    @Override
    public final boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            //先从请求参数中取token
            String token = TokenUtils.takeToken(request);
            //校验token
            boolean success = this.checkToken(token);

            if (success) {
                //更新cookie有效时长
                TokenUtils.putToken(request,response,token);
            } else {
                //移除token
                TokenUtils.removeToken(request, response);
            }

            HsAssert.isTrue(success, UMRespCode.UM_LOGIN_TOKEN_EXPIRE, "登录令牌[token]已失效");
        } catch (HsException e) {
            PrintWriter writer = null;
            try {
                //输出
                writer = response.getWriter();
                //返回错误码
                writer.print(RespInfo.bulid(e.getErrorCode()));
                //flush
                writer.flush();
            } catch (Exception xe) {
                //nothing
            } finally {
                IOUtils.closeQuietly(writer);
            }
            return false;
        }
        return true;
    }

    /**
     * 校验登录令牌
     *
     * @param token 令牌
     * @return {@code boolean}
     * @throws HsException
     */
    protected abstract boolean checkToken(String token) throws HsException;
}
