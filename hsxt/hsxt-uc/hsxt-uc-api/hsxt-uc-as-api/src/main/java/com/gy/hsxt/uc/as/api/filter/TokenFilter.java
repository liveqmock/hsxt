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
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsi.redis.client.api.RedisUtil;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.uc.as.api.util.UcCacheKey;

/**
 * 随机token过滤器
 * 
 * @Package: com.gy.hsxt.uc.as.api.filter
 * @ClassName: TokenFilter
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2015-11-24 上午9:34:25
 * @version V1.0
 */
public class TokenFilter implements Filter {
	private final static String MOUDLENAME = "com.gy.hsxt.uc.as.api.filter.TokenFilter";
	RedisUtil<Object> changeRedisUtil;
	String[] ignoreUrls = null;// {
								// "/login/getRandomToken","/register/sendRegisterCode",
								// "/register/nocarder" };
	String[] checkRandomTokenUrls = null;
	boolean isGetCheckCode = false;
	static String LOGIN_CHECK_CODE_STR = "ChkCodeLogin";

	/**
	 * 设置参数ignoreLoginUrls（不验证的URL，多个使用逗号分隔，如："/login,/register"
	 * 
	 * @param urls
	 */
	public void setIgnoreUrls() {
		String urls = getProperty("ignore.token.urls");
		if (urls != null && !urls.equals("")) {
			ignoreUrls = urls.split(",");
		}
		String checkUrls = getProperty("check.random.token.urls");
		if (checkUrls != null && !checkUrls.equals("")) {
			checkRandomTokenUrls = checkUrls.split(",");
		}
	}

	/**
	 * 是否使用验证登录验证码的方式登录
	 * 
	 * @param req
	 * @return
	 */
	private boolean isCheckCodeLogin(ServletRequest req) {
		String isIgnore = getProperty("ignore.checkcode");
		if (StringUtils.isNotBlank(isIgnore) && isIgnore.trim().equals("1")) {
			return false;
		}
		String url = ((HttpServletRequest) req).getRequestURI();
		if (url.indexOf(LOGIN_CHECK_CODE_STR) > -1) {
			return true;
		}
		return false;
	}

	/**
	 * 判断 是否需要登录和随机token验证
	 * 
	 * @param req
	 *            请求
	 * @return
	 */
	private boolean isFilter(ServletRequest req) {
		if ((ignoreUrls == null || ignoreUrls.length == 0)
				&& (checkRandomTokenUrls == null || checkRandomTokenUrls.length == 0)) {
			return false;
		}
		// 是否有登录验证码需要校验
		String url = ((HttpServletRequest) req).getRequestURI();
		boolean result = true;
		isGetCheckCode = false;
		if (ignoreUrls != null) {
			for (String u : ignoreUrls) {
				if (url.indexOf("getCheckCode") > -1) {
					isGetCheckCode = true;
				}
				if (url.indexOf(u) > -1) {
					result = false;
				}
			}
		} else {
			result = false;
			for (String u : checkRandomTokenUrls) {
				if (url.indexOf("getCheckCode") > -1) {
					isGetCheckCode = true;
				}
				if (url.indexOf(u) > -1) {
					result = true;
				}
			}
		}
		return result;
	}

	/**
	 * 验证随机token是否正确
	 * 
	 * @param req
	 * @param resp
	 * @param chain
	 * @throws IOException
	 * @throws ServletException
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 *      javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		// 判断是否需过滤的路径
		if (!isFilter(req)) {
			chain.doFilter(req, resp);
			return;
		}
		resp.setContentType("text/html; charset=utf-8");
		resp.setCharacterEncoding("utf-8");
		String custId = req.getParameter("custId");
		if (custId == null || custId.equals("")) {
			custId = (String) req.getAttribute("custId");
		}
		// 如果是登录验证码，只需验证随机token是否存在
		String randomToken = req.getParameter("randomToken");
		// 验证随机token是否为空
		if (randomToken == null || randomToken.equals("")) {
			PrintWriter out = resp.getWriter();
			HttpRespEnvelope en = new HttpRespEnvelope(
					ErrorCodeEnum.RANDOM_TOKEN_IS_EMPTY.getValue(),
					ErrorCodeEnum.RANDOM_TOKEN_IS_EMPTY.getDesc());

			out.print(JSONObject.toJSONString(en));
			out.close();
			return;
		}

		if (isGetCheckCode) {
			chain.doFilter(req, resp);
			return;
		}

		// 登录验证码
		if (isCheckCodeLogin(req)) {
			String chkCode = req.getParameter("chkCode");
			StringBuffer msg = new StringBuffer();
			if (chkCode == null || chkCode.equals("")) {
				msg.append("登录验证码不能为空,custId["+custId+"]");
				System.out.println(msg.toString());
				PrintWriter out = resp.getWriter();
				HttpRespEnvelope en = new HttpRespEnvelope(
						ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "登录验证码不能为空");
				out.print(JSONObject.toJSONString(en));
				out.close();
				return;
			}
			HttpServletRequest httpReq = (HttpServletRequest) req;
			// 验证登录验证码

			String code = changeRedisUtil.getToString(UcCacheKey
					.genCheckCodeKey(randomToken));
	//		String funName = "doFilter";
			// 是否已生成登录验证码
			if (code == null || code.equals("")) {
				HttpSession session=httpReq.getSession();//.getId()
				String sessionId=null;
				if(session!=null){
					sessionId=session.getId();
					session.setAttribute("randomToken", randomToken);
					Object sCode=session.getAttribute("checkCode");
					msg.append("sessionId["+sessionId+"]sCode["+sCode+"]");
				}
				msg.append("登录验证码不存在,randomToken["+randomToken+"]");
				System.out.println(msg.toString());
			//	SystemLog.warn(MOUDLENAME, funName, msg.toString());
				PrintWriter out = resp.getWriter();
				HttpRespEnvelope en = new HttpRespEnvelope(
						ErrorCodeEnum.LOGIN_CODE_NOT_FOUND.getValue(),
						ErrorCodeEnum.LOGIN_CODE_NOT_FOUND.getDesc());
				out.print(JSONObject.toJSONString(en));
				out.close();
				return;
			}

			// 如果不正确，返回提示信息
			if (!chkCode.equals(code)) {
				HttpSession session=httpReq.getSession();//.getId()
				String sessionId=null;
				if(session!=null){
					sessionId=session.getId();
					session.setAttribute("randomToken", randomToken);
					Object sCode=session.getAttribute("checkCode");
					msg.append("sessionId["+sessionId+"]sCode["+sCode+"]");
				}
				msg.append(sessionId+"登录验证码不正确,randomToken["+randomToken+"],传入的验证码["+chkCode+"],缓存中的验证码["+code+"]");
				System.out.println(msg.toString());
			//	SystemLog.warn(MOUDLENAME, funName, msg.toString());
				PrintWriter out = resp.getWriter();
				HttpRespEnvelope en = new HttpRespEnvelope(
						ErrorCodeEnum.LOGIN_CODE_WRONG.getValue(),
						chkCode+ErrorCodeEnum.LOGIN_CODE_WRONG.getDesc()+code);
				out.print(JSONObject.toJSONString(en));
				out.close();
				return;
			}
			// 如果正确继续下面的验证
		}

		
		
		resp.setCharacterEncoding("utf-8");

		// 匹配random
		boolean check = checkRandomToken(custId, randomToken);
		if (!check) {
			PrintWriter out = resp.getWriter();
			HttpRespEnvelope en = new HttpRespEnvelope(
					ErrorCodeEnum.RANDOM_TOKEN_NOT_MATCH.getValue(),
					ErrorCodeEnum.RANDOM_TOKEN_NOT_MATCH.getDesc());
			out.print(JSONObject.toJSONString(en));
			out.close();
			return;
		}
		chain.doFilter(req, resp);

	}

	/**
	 * 判断随机token是否正确
	 * 
	 * @param custId
	 * @param token
	 * @return
	 */
	private boolean checkRandomToken(String custId, String token) {
		// 如果客户号不为空
		String key = UcCacheKey.genRandomTokenKey(custId, token);
		return changeRedisUtil.isExists(key, true);
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
		changeRedisUtil = (RedisUtil<Object>) ctx.getBean("changeRedisUtil");
		setIgnoreUrls();
	}

	/**
	 * 销毁方法
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {

	}

}
