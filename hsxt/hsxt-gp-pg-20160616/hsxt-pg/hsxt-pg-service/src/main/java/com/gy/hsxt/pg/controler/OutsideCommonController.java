package com.gy.hsxt.pg.controler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.controler
 * 
 *  File Name       : OutsideCommonController.java
 * 
 *  Creation Date   : 2015-09-22
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 处理来自外部第三方接入渠道的支付类请求
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Controller
@RequestMapping("/**")
public class OutsideCommonController {

	@RequestMapping("/**")
	public void handleOutsidePayRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 跳转到默认的index页面
		response.sendRedirect(request.getContextPath() + "/index.jsp");
	}
}