package com.gy.hsxt.gpf.gcs.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.gpf.gcs.bean.SysUser;
import com.gy.hsxt.gpf.gcs.common.MD5Util;
import com.gy.hsxt.gpf.gcs.interfaces.IUserService;

@Controller
public class LoginController {

	@Resource(name = "userService")
	private IUserService IUserService;

	/**
	 * 用户登录
	 * 
	 * @param user
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/login/loginPost")
	@ResponseBody
	public int loginPost(SysUser user, HttpSession session) {
		// 对密码进行加密
		user.setUserPwd(MD5Util.Md5_16bit(user.getUserPwd()));
		SysUser u = IUserService.getUserName(user);
		// 返回用户名和密码不能空则登录成功
		if (u != null && u.getUserName() != null && !u.getUserName().equals("")
				&& u.getUserPwd() != null && !u.getUserPwd().equals("")) {
			session.setAttribute("userSessionName", u.getUserName());
			return 1;
		}
		return 0;
	}

	/**
	 * 用户退出
	 * 
	 * @param session
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/login/loginOut")
	public void loginOut(HttpSession session, HttpServletResponse response)
			throws IOException {
		session.removeAttribute("userSessionName");
		response.sendRedirect(session.getServletContext().getContextPath());
		response.getWriter().close();
	}

}
