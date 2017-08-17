/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.as.api.filter;

import static com.gy.hsi.ds.param.HsPropertiesConfigurer.getProperty;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.gy.hsxt.common.utils.StringUtils;

/**
 * 提供给互商的filter，用于从cookie中获取参数
 * 
 * @Package: com.gy.hsxt.uc.as.api.filter
 * @ClassName: EcParamFilter
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2015-12-17 下午6:18:22
 * @version V1.0
 */
public class EcParamFilter implements Filter {
	String[] ignoreUrls = null;

	/**
	 * 设置参数ignoreLoginUrls（不验证的URL，多个使用逗号分隔，如："/login,/register"
	 * 
	 * @param urls
	 */
	public void setIgnoreUrls() {
		String urls = getProperty("ignore.param.urls");
		if (urls != null && !urls.equals("")) {
			ignoreUrls = urls.split(",");
		}

	}

	/**
	 * 判断 是否需要登录和随机token验证
	 * 
	 * @param req
	 *            请求
	 * @return
	 */
	private boolean isFilter(ServletRequest req) {
		if (ignoreUrls == null) {
			return true;
		}
		String url = ((HttpServletRequest) req).getRequestURI();

		for (String u : ignoreUrls) {
			if (url.indexOf(u) > -1) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @param arg0
	 * @throws ServletException
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		setIgnoreUrls();
	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		if (!isFilter(req)) {
			chain.doFilter(req, resp);

		} else {
			resp.setContentType("text/html; charset=utf-8");
			resp.setCharacterEncoding("utf-8");
			// 从cookie中获取参数
			// 获取企业客户号
			String custId = getCookieValue((HttpServletRequest) req, "hs_id");
			// 获取登录后的token
			String loginToken = getCookieValue((HttpServletRequest) req,
					"hs_key");
			String channelType = getCookieValue((HttpServletRequest) req,
					"channelType");
			if (StringUtils.isEmpty(channelType)) {
				channelType = "1";
			}
			// 将获取的参数放入参数中
			req.setAttribute("custId", custId);
			req.setAttribute("loginToken", loginToken);
			req.setAttribute("channelType", channelType);

			chain.doFilter(req, resp);
		}
	}

	/**
	 * 获取cookie
	 * 
	 * @param req
	 * @param name
	 * @return
	 */
	private String getCookieValue(HttpServletRequest req, String name) {
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (name.equals(c.getName())) {
					return c.getValue();
				}
			}
		}
		return null;
	}

	/**
	 * 销毁方法
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {

	}

}
