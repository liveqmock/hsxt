/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.fss.interceptor;

import com.gy.hsxt.gpf.fss.bean.PageContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 前端分页拦截器
 *
 * @Package :com.gy.hsxt.gpf.fss.interceptor
 * @ClassName : PageInterceptor
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/12/7 11:54
 * @Version V3.0.0.0
 */
public class PageWebInterceptor extends HandlerInterceptorAdapter {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(PageWebInterceptor.class);

    /**
     * This implementation always returns {@code true}.
     *
     * @param request
     * @param response
     * @param handler
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //分页长度
        String iDisplayLength = request.getParameter("iDisplayLength");
        //起始编号
        String iDisplayStart = request.getParameter("iDisplayStart");
        //将分页长度设置到上下文
        String length = StringUtils.isBlank(iDisplayLength) ? "10" : iDisplayLength;
        PageContext.setPageSize(Integer.parseInt(length));
        //将起始编号设置到上下文
        String start = StringUtils.isBlank(iDisplayStart) ? "0" : iDisplayStart;
        PageContext.setOffset(Integer.parseInt(start));

        logger.debug("==========分页长度：[{}] ----  起始编号：[{}] ===============", length, start);
        return super.preHandle(request, response, handler);
    }
}
