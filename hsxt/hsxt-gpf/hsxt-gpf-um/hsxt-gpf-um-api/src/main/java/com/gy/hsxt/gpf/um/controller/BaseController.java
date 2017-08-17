/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.controller;

import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.um.bean.RespInfo;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 控制层基础类
 *
 * @Package : com.gy.hsxt.gpf.um.controller
 * @ClassName : BaseController
 * @Description : 控制层基础类
 * 所有的控制层实现类，都继承该类来实现异常统一处理。
 * @Author : chenm
 * @Date : 2016/1/26 9:22
 * @Version V3.0.0.0
 */
public abstract class BaseController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 非ajax 各子系统自行处理
     *
     * @param request
     * @param response
     * @param ex
     * @return
     */
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Exception ex) {

        return null;
    }

    /**
     * 集中处理异常方法
     *
     * @param request 请求
     * @param ex      异常
     * @return {@code ModelAndView}
     */
    @ExceptionHandler
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response, Exception ex) {

        if (!(request.getHeader("accept").contains("application/json") || (request
                .getHeader("X-Requested-With") != null && request
                .getHeader("X-Requested-With").contains("XMLHttpRequest")))) {
            //非 ajax请求，自行处理异常，可判断 Exception ex 实例返回不同的页面
            return this.doResolveException(request, response, ex);
        }
        //ajax 请求返回异常类对应的错误代码
        else {
            doAjaxException(response, ex);
        }
        return null;
    }

    /**
     * 处理ajax 请求异常 统一返回错误代码
     *
     * @param response 响应
     * @param ex       异常
     */
    private void doAjaxException(HttpServletResponse response, Exception ex) {
        int errorCode = RespCode.UNKNOWN.getCode();
        if (null != ex) {
            //需要添加根据错误码记录日志信息(此处需要修改 记录业务日志信息||自己实现记录业务日志信息)
            if (ex instanceof HsException) {
                errorCode = ((HsException) ex).getErrorCode();
            }
            logger.debug("=======异步请求抛出的异常信息[{}]=========", ex.getMessage());
        }
        response.setContentType("text/html;charset=utf-8");
        response.setHeader("cache-control", "no-cache");
        response.setHeader("pragma", "no-cache"); //设置禁用缓存
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.print(RespInfo.bulid(errorCode));
            out.flush();
        } catch (IOException e) {
            logger.error("=====ajax 返回数据异常！====", e);
        } finally {
            IOUtils.closeQuietly(out);
        }
    }
}
