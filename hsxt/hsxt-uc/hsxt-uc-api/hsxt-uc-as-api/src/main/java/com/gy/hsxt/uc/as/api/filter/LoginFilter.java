/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.as.api.filter;

import static com.gy.hsi.ds.param.HsPropertiesConfigurer.getProperty;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsi.redis.client.api.RedisUtil;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.uc.as.api.util.CommonUtil;

/**
 * 登录过滤
 * 
 * @Package: com.gy.hsxt.uc.as.api.filter
 * @ClassName: LoginFilter
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2015-11-24 上午9:39:53
 * @version V1.0
 */
public class LoginFilter implements Filter {
	RedisUtil<Object> changeRedisUtil;
	CommonUtil commonUtil;
	
	String[] ignoreLoginUrls = null;// { "/login", "/register" };

	/**
	 * 设置参数ignoreLoginUrls（不验证的URL，多个使用逗号分隔，如："/login,/register"
	 * 
	 * @param urls
	 */
	public void setIgnoreLoginUrls() {
		if (ignoreLoginUrls == null) {
			String urls = getProperty("ignore.login.urls");
			if (urls != null && !urls.equals("")) {
				ignoreLoginUrls = urls.split(",");
			}
			else{
				ignoreLoginUrls = new String[1];
				ignoreLoginUrls[0] = "/login";
			}
		}
	}

	/**
	 * 判断 是否需要登录验证
	 * 
	 * @param req
	 *            请求
	 * @return
	 */
	private boolean isLoginFilter(ServletRequest req) {
		if (ignoreLoginUrls == null) {
			return true;
		}
		String url = ((HttpServletRequest) req).getRequestURI();
		for (String u : ignoreLoginUrls) {
			if (url.indexOf(u) > -1) {
				return false;
			}
		}
		return true;
	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		resp.setContentType("text/html; charset=utf-8");
		resp.setCharacterEncoding("utf-8");
		// 判断是否需过滤的路径
		if (!isLoginFilter(req)) {
			chain.doFilter(req, resp);

		} else {
			// 设置custId
			String custId = (String) req.getParameter("custId");
			if (custId == null || custId.equals("")) {
				custId = (String) req.getAttribute("custId");
			}
			// 设置登录token
			String loginToken = (String) req.getParameter("loginToken");
			if( (loginToken == null || loginToken.equals("")))
			{
				loginToken =  (String) req.getParameter("token");
			}
			
			if (loginToken == null || loginToken.equals("")) {
				loginToken = (String) req.getAttribute("loginToken");
			} 
			
			// 设置渠道类型
			String channelType = (String) req.getParameter("channelType");
			if (channelType == null || channelType.equals("")) {
				channelType = (String) req.getAttribute("channelType");
			}
			
			// 企业资源号，如果有该值，表示操作员登录
			// String entResNo = (String) req.getParameter("entResNo");

			resp.setCharacterEncoding("utf-8");
			
			// 验证访问资源是否已登录，验证登录token是否一致
			if (isLoginFilter(req)) {
				if(loginToken == null || loginToken.equals("")){
					PrintWriter out = resp.getWriter();
					HttpRespEnvelope en = new HttpRespEnvelope(
							ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
							"参数(loginToken不能为空");
					out.print(JSONObject.toJSONString(en));
					out.close();
					return;
				}
				int isLogin = commonUtil.isLogin( custId,
						loginToken,channelType);
				if (isLogin != 1) {
					PrintWriter out = resp.getWriter();
					HttpRespEnvelope en = new HttpRespEnvelope(
							ErrorCodeEnum.NO_LOGIN.getValue(),
							ErrorCodeEnum.NO_LOGIN.getDesc());
					out.print(JSONObject.toJSONString(en));
					out.close();
					return;
				}
			}
			// 验证用户是否有权限访问资源
			chain.doFilter(req, resp);

		}
	}
 
	/**
	 * 初始化方法
	 * 
	 * @param config
	 * @throws ServletException
	 */
	@SuppressWarnings("unchecked")
	public void init(FilterConfig config) throws ServletException {
		ServletContext context = config.getServletContext();
		ApplicationContext ctx = WebApplicationContextUtils
				.getWebApplicationContext(context);
		changeRedisUtil = (RedisUtil<Object>) ctx.getBean("changeRedisUtil");
		commonUtil = (CommonUtil) ctx.getBean("commonUtil");
		setIgnoreLoginUrls();
	}

	/**
	 * 销毁方法
	 */ 
	public void destroy() {

	}
}
