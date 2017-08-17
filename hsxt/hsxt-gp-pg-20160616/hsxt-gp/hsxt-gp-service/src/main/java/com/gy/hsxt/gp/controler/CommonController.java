package com.gy.hsxt.gp.controler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-service
 * 
 *  Package Name    : com.gy.hsxt.gp.controler
 * 
 *  File Name       : CommonController.java
 * 
 *  Creation Date   : 2015-09-22
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 接收http请求
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Controller
@RequestMapping("/**")
public class CommonController {

	@RequestMapping("/**")
	public void handleOutsidePayRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// 跳转到默认的index欢迎页面
		response.sendRedirect(request.getContextPath() + "/index.jsp");
	}
}
