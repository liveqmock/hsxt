package com.gy.hsi.ds.login;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.github.knightliao.apollo.utils.tool.TokenUtil;
import com.github.knightliao.apollo.utils.web.CookieUtils;
import com.gy.hsi.ds.common.constant.LoginConstant;
import com.gy.hsi.ds.common.constant.UserConstant;
import com.gy.hsi.ds.common.thirds.dsp.common.constant.ErrorCode;
import com.gy.hsi.ds.common.thirds.dsp.common.interceptor.WebCommonInterceptor;
import com.gy.hsi.ds.login.beans.Visitor;
import com.gy.hsi.ds.login.service.ILoginService;
import com.gy.hsi.ds.login.service.IUserMgr;

/**
 * 拦截器：所有请求（一个Session可能会有多个请求）均会通过此拦截器
 *
 * @author liaoqiqi
 * @version 2013-11-28
 */
public class LoginInterceptor extends WebCommonInterceptor {

	// private static final Logger LOG =
	// AopLogFactory.getLogger(LoginInterceptor.class);

	protected static final Logger LOG = Logger
			.getLogger(LoginInterceptor.class);

	@Resource
	private IUserMgr userMgr;

	@Autowired
	private ILoginService login;

	private List<String> notJsonPathList;

	private List<String> notInterceptPathList;
	private List<String> staticResourceList;

	// Cookie域
	private String XONE_COOKIE_DOMAIN_STRING = "127.0.0.1";

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
	}

	/**
	 * 采用两级缓存。先访问session,<br/>
	 * 如果存在，则直接使用，并更新 threadlocal <br/>
	 * 如果不存在，则访问 redis，<br/>
	 * 如果redis存在，则更新session和threadlocal<br/>
	 * 如果redis也不存在，则认为没有登录
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		LOG.info("request.getServletPath() : " + request.getServletPath());

		if ("/".equals(request.getServletPath())
				|| "/index.html".equals(request.getServletPath())) {
			response.sendRedirect("login.html");

			return false;
		}

		//
		// 去掉不需拦截的path
		//
		String requestPath = request.getRequestURI();

		// 显示所有用户的请求
		LOG.info(request.getRequestURI());

		if (notInterceptPathList != null) {
			// 更精确的定位
			for (String path : notInterceptPathList) {
				if (requestPath.contains(path)) {
					return true;
				}
			}
		}

		// added by zhangysh
		if (staticResourceList != null) {
			// 更精确的定位
			for (String resource : staticResourceList) {
				if (requestPath.contains(resource)
						|| requestPath.endsWith(".html")
						|| requestPath.equals("/")) {
					return true;
				}
			}
		}

		/**
		 * 种植Cookie, 暂时屏蔽, 没什么作用, marked by:zhangysh
		 */
		// this.plantCookie(request, response);

		// 判断session中是否有visitor
		HttpSession session = request.getSession();
		Visitor visitor = (Visitor) session.getAttribute(UserConstant.USER_KEY);

		//
		if (visitor == null) {
			// 没有登录
			returnJsonSystemError(request, response, "login.error",
					ErrorCode.LOGIN_ERROR);
			return false;
		} else {
			// 每次都更新session中的登录信息
			login.updateSessionVisitor(session, visitor);
		}

		return true;
	}

	/**
	 * 种植Cookie
	 *
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unused")
	private void plantCookie(HttpServletRequest request,
			HttpServletResponse response) {

		String xId = CookieUtils.getCookieValue(request,
				LoginConstant.XONE_COOKIE_NAME_STRING);

		// 没有Cookie 则生成一个随机的Cookie
		if (xId == null) {
			String cookieString = TokenUtil.generateToken();

			CookieUtils.setCookie(response,
					LoginConstant.XONE_COOKIE_NAME_STRING, cookieString,
					XONE_COOKIE_DOMAIN_STRING, LoginConstant.XONE_COOKIE_AGE);
		}
	}

	/**
	 * @return the notJsonPathList
	 */
	public List<String> getNotJsonPathList() {
		return notJsonPathList;
	}

	/**
	 * @param notJsonPathList
	 *            the notJsonPathList to set
	 */
	public void setNotJsonPathList(List<String> notJsonPathList) {
		this.notJsonPathList = notJsonPathList;
	}

	/**
	 * @return the notInterceptPathList
	 */
	public List<String> getNotInterceptPathList() {
		return notInterceptPathList;
	}

	/**
	 * @param notInterceptPathList
	 *            the notInterceptPathList to set
	 */
	public void setNotInterceptPathList(List<String> notInterceptPathList) {
		this.notInterceptPathList = notInterceptPathList;
	}

	public List<String> getStaticResourceList() {
		return staticResourceList;
	}

	public void setStaticResourceList(List<String> staticResourceList) {
		this.staticResourceList = staticResourceList;
	}

	public String getXONE_COOKIE_DOMAIN_STRING() {
		return XONE_COOKIE_DOMAIN_STRING;
	}

	public void setXONE_COOKIE_DOMAIN_STRING(String xONE_COOKIE_DOMAIN_STRING) {
		XONE_COOKIE_DOMAIN_STRING = xONE_COOKIE_DOMAIN_STRING;
	}

}
