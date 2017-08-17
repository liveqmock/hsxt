package com.gy.hsi.fs.server.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gy.hsi.fs.server.controllers.parent.BasicController;

/***************************************************************************
 * <PRE>
 *  Project Name    : fs-server
 * 
 *  Package Name    : com.gy.hsi.fs.server.controlers
 * 
 *  File Name       : CommonController.java
 * 
 *  Creation Date   : 2015-05-14
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 接收来自外部系统的http文件请求
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Controller
@RequestMapping("/**")
public class CommonController extends BasicController {

	@RequestMapping("/**")
	public void handleCommonRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 跳转到默认的index页面
		response.sendRedirect(request.getContextPath() + "/index.jsp");
	}
}
