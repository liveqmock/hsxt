/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.as.api.filter;

import static com.gy.hsi.ds.param.HsPropertiesConfigurer.getProperty;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

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
import com.gy.hsxt.uc.as.api.auth.IUCAsRoleService;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.uc.as.api.util.UcCacheKey;

/**
 * 权限过滤器
 * 
 * @Package: com.gy.hsxt.uc.as.api.filter
 * @ClassName: PermissionFilter
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2015-11-24 上午9:34:16
 * @version V1.0
 */
public class PermissionFilter implements Filter {
	String[] ignoreUrls = null;// { "" };
	RedisUtil<Object> fixRedisUtil;
	IUCAsRoleService roleService;
	//final static String PERFIX_ROLE_URL = "UC.role_url_";
	//final static String PERFIX_ROLES = "UC.cust_role_";
	
	/**
	 * 设置参数ignoreLoginUrls（不验证的URL，多个使用逗号分隔，如："/login,/register"
	 * 
	 * @param urls
	 */
	public void setIgnoreUrls() {
		if (ignoreUrls == null) {
			String urls = getProperty("ignore.permission.urls");
			if (urls != null && !urls.equals("")) {
				ignoreUrls = urls.split(",");
			}
		}
	}
	
	/**
	 * 判断 是否需要权限验证
	 * 
	 * @param req
	 *            请求
	 * @return
	 */
	public boolean isFilter(ServletRequest req) {
		String url = ((HttpServletRequest) req).getRequestURI();
		for (String u : ignoreUrls) {
			if (url.indexOf(u) >= 0) {
				return false;
			}
		}
		return true;
	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		String url = ((HttpServletRequest) req).getServletPath();
		resp.setContentType("text/html; charset=utf-8");
		resp.setCharacterEncoding("utf-8");
		// 判断是否需过滤的路径
		if (!isFilter(req)) {
			chain.doFilter(req, resp);
		} else {
			String custId = (String) req.getParameter("custId");
			if (custId == null || custId.equals("")) {
				custId = (String) req.getAttribute("custId");
			}
			// 验证用户是否有权限访问资源
			if (isPermission(custId, url)) {
				chain.doFilter(req, resp);
			} else {
				resp.setCharacterEncoding("utf-8");
				PrintWriter out = resp.getWriter();
				HttpRespEnvelope en = new HttpRespEnvelope(
						ErrorCodeEnum.USER_NO_PERMISSION.getValue(),
						ErrorCodeEnum.USER_NO_PERMISSION.getDesc());
				out.print(JSONObject.toJSONString(en));
				out.close();
				return;

			}

		}
	}

	/**
	 * 判断用户是否拥有对应url权限
	 * 
	 * @param custId
	 * @param url
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private boolean isPermission(String custId, String url) {
		if(custId == null || custId.equals("")){
			return false;
		}
		// 从缓存获取用户拥有的角色
		String key = UcCacheKey.genCustRoleCacheKey(custId);
		// 从缓存中获取
		Set custRoleCache = fixRedisUtil.redisTemplate.opsForSet().members(key);
		if (custRoleCache == null || custRoleCache.isEmpty()) { 
			// 如果缓存中没有，从DB中获取并放入缓存
			custRoleCache = roleService.resetCustRoleCache(custId);
			if (custRoleCache == null || custRoleCache.isEmpty()) {
				return false;
			}
		}
		String urlCacheKey; // roleUrl权限缓存keygenRoleUrlCacheKey
		for (Object roleId : custRoleCache) {
			urlCacheKey = UcCacheKey.genRoleUrlCacheKey((String)roleId);
			if (fixRedisUtil.redisTemplate.opsForSet().isMember(urlCacheKey,
					url)) {// 该角色拥有指定 url权限
				return true;
			}
		}
		return false;
	}

	/**
	 * 初始化方法
	 * 
	 * @param config
	 * @throws ServletException
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@SuppressWarnings("unchecked")
	public void init(FilterConfig config) throws ServletException {
		ServletContext context = config.getServletContext();
		ApplicationContext ctx = WebApplicationContextUtils
				.getWebApplicationContext(context);
		fixRedisUtil = (RedisUtil<Object>) ctx.getBean("fixRedisUtil");
		roleService = (IUCAsRoleService) ctx.getBean("roleService");
		setIgnoreUrls();
	}

	public void destroy() {

	}

}
